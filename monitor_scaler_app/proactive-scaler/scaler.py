from model.model import MLP 
from keras.models import model_from_json
import pandas as pd
import numpy as np
import psycopg2
import sched, time
import requests
import json
from math import ceil

from config import monitor_window, monitor_hist, microservice_name, scaleup_url, services_url, sla_threshold

def query_db():
    query = """select count(*), date_trunc('minute', to_timestamp(timestamp/1000)) as selected_minute 
               from request_log
               where microservice_name='{}'
               group by selected_minute 
               order by selected_minute desc
               limit {} offset 1""".format(microservice_name, monitor_hist)

    print("Querying db")
    con = psycopg2.connect(database="microserv_monitor", user="microserv_monitor", password="m", host="127.0.0.1", port="5432")
    cur = con.cursor()
    cur.execute(query)
    rows = cur.fetchall()
    print("Operation done successfully")
    con.close()
    data = []
    for row in rows:
        data.append(row[0])
    return data

def transform_data(reqs):
    reqs = np.array(reqs)
    reqs = np.flip(reqs)
    return reqs

def load_model(model_dir="model"):
    json_file = open(model_dir + '/model.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights(model_dir + "/model.h5")
    print("Loaded model from disk")
    return loaded_model

def online_serv():
    r = requests.get(services_url)
    print(r.text)
    services = json.loads(r.text)
    count = 0
    for service in services:
        if service["name"] == microservice_name:
            count += 1
    return count

def scale(count, should_have):
    if should_have > count:
        print("Scaling up")
        for i in range(should_have - count):
            requests.get(scaleup_url)
    elif should_have == count:
        print("No scaling needed")
    else:
        if should_have < 1:
            print("Won't scale lower than 1 service")
        diff = count - should_have
        print("Scaledown {}".format(diff))
        print("Not currently implemented")

def main_loop():
    print("Checking requests..")

    reqs = query_db()
    history = transform_data(reqs)
    #history = np.array([156, 1456, 1500, 167, 289, 1572, 1305, 167]) -- used for benchmark
    history = history.reshape(1, monitor_hist)
    print(history)

    model = load_model()
    y_pred = model.predict(history)
    print("Predicted traffic : {}".format(y_pred))

    should_have = ceil(y_pred[0] / (sla_threshold * monitor_window))
    print("This means we should have {} instances".format(should_have))

    count = online_serv()
    print("We currently have {}".format(count))

    scale(count, should_have)

main_loop()
s = sched.scheduler(time.time, time.sleep)
def monitor_requests(sc):
    main_loop()
    s.enter(monitor_window, 1, monitor_requests, (sc,))
s.enter(monitor_window, 1, monitor_requests, (s,))
s.run()

