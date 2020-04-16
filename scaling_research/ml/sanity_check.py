import matplotlib.pyplot as plt
import numpy as np

from preparedata import get_sanity_check_data
from config import epochs, batch_size, report_dir

from test import report_performance
from test import plot_predictions

def dummy_predictions(X_test):
    y_pred = np.zeros(X_test.shape[0])
    for i in range(len(X_test)):
        x = X_test[i]
        y_pred[i] = x[0]
    return y_pred

if (__name__ == '__main__'):
    # extract data
    X_train, Y_train, X_test, Y_test = get_sanity_check_data()

    # make predictions
    y_predicted = dummy_predictions(X_test)
    
    # report performance
    perf_rep = report_performance(Y_test, y_predicted)
    plot_predictions(Y_test, y_predicted, ".")
