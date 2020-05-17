import psycopg2
import sched, time
import numpy as np

from model.model import MLP
from config import retrain_window, microservice_name, monitor_hist

def query_db():
    query = """select count(*), date_trunc('minute', to_timestamp(timestamp/1000)) as selected_minute 
               from request_log
               where microservice_name='{}'
               group by selected_minute 
               order by selected_minute desc""".format(microservice_name)

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

def prepare_data(reqs):
    reqs = np.array(reqs)
    reqs = np.flip(reqs)
    train = to_supervised(reqs, reqs, monitor_hist)
    return split_target_var(train, monitor_hist)

def to_supervised(data, normalized, feature_no=8):
    print("Converting to supervised dataset, with no features " + str(feature_no))
    dataset_size = data.shape[0] - feature_no - 2
    res = np.zeros((dataset_size, feature_no + 1))
    for idx in range(0, dataset_size, 1):
        x_row = normalized[idx:idx + feature_no]
        y_row = data[idx + feature_no + 1]
        res[idx,:] = np.append(x_row, y_row)
    print("New shape: " + str(res.shape))
    return res

def split_target_var(data, feature_no):
    X = data[:,0:feature_no]
    Y = data[:,feature_no]
    return X, Y

def store_model(model, model_dir="model/trained"):
    # serialize model
    model_json = model.to_json()
    with open(model_dir + "/model.json", "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights(model_dir + "/model.h5")
    print("Saved model to disk")

def main_loop():
    print("Training model")
    reqs = query_db()
    X_train, Y_train = prepare_data(reqs)

    model_obj = MLP()
    model = model_obj.get_model()
    model.fit(X_train, Y_train, epochs=100, batch_size=8)

    store_model(model)

main_loop()
s = sched.scheduler(time.time, time.sleep)
def monitor_requests(sc):
    main_loop()
    s.enter(retrain_window, 1, monitor_requests, (sc,))
s.enter(retrain_window, 1, monitor_requests, (s,))
s.run()
