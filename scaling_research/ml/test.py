from keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.metrics import mean_squared_error, mean_absolute_error
import matplotlib.pyplot as plt
import time
from keras.utils import plot_model
import numpy as np
from datetime import datetime
import os
from shutil import copyfile

from preparedata import get_data
from config import epochs, batch_size, report_dir
from models import CNN_LSTM

def plot_train_history(history, rep_folder):
    plt.clf()
    plt.plot(history.history['loss'])
    plt.title('Model loss')
    plt.ylabel('Loss')
    plt.xlabel('Epoch')
    plt.savefig(rep_folder + "/train_loss.png")

# line plot of observed vs predicted
def plot_predictions(Y_test, y_predicted, rep_folder):
    plt.clf()
    test_ln, = plt.plot(Y_test)
    predicted_ln, = plt.plot(y_predicted)
    plt.xlabel("Time window")
    plt.ylabel("Workload")
    plt.legend((test_ln, predicted_ln), ("Actual", "Predicted"))
    plt.savefig(rep_folder + "/predictions.png")

def mean_absolute_percentage_error(y_true, y_pred): 
    y_true, y_pred = np.array(y_true), np.array(y_pred)
    return np.mean(np.abs((y_true - y_pred) / y_true)) * 100

def report_performance(Y_test, y_predicted):
    mse = mean_squared_error(Y_test, y_predicted)
    mape = mean_absolute_percentage_error(Y_test, y_predicted)
    mae = mean_absolute_error(Y_test, y_predicted)
    report = 'MSE: %.3f, MAE: %.3f, MAPE: %.3f' % (mse, mae, mape)
    print(report)
    return report

def create_report_folder():
    now = datetime.now()
    timestamp = datetime.timestamp(now)
    dest = report_dir + str(int(timestamp))
    if not os.path.exists(dest):
        os.makedirs(dest)
    return dest

def write_summary(rep_folder, perf_rep):
    f = open(rep_folder + "/report.txt", "w")
    f.write(perf_rep)
    f.close()

def store_model(rep_folder, model):
    # serialize model
    model_json = model.to_json()
    with open(rep_folder + "/model.json", "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights(rep_folder + "/model.h5")
    print("Saved model to disk")

if (__name__ == '__main__'):
    # create report folder 
    rep_folder = create_report_folder()

    # extract data
    X_train, Y_train, X_test, Y_test = get_data()

    # select model 
    model_obj = CNN_LSTM()

    # transform input data
    X_train = model_obj.transform_data(X_train)
    X_test = model_obj.transform_data(X_test)

    # fit model
    model = model_obj.get_model()
    plot_model(model, to_file=rep_folder + '/model.png')
    history = model.fit(X_train, Y_train, epochs=epochs, batch_size=batch_size)

    # plot history 
    plot_train_history(history, rep_folder)

    # make predictions
    y_predicted = model.predict(X_test)

    # report performance
    perf_rep = report_performance(Y_test, y_predicted)
    plot_predictions(Y_test, y_predicted, rep_folder)

    # store reports in file 
    write_summary(rep_folder, perf_rep)
    copyfile("ml/config.py", rep_folder + "/config.py")

    # store model 
    store_model(rep_folder, model)





