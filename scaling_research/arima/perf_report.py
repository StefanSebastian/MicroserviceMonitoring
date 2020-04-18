from sklearn.metrics import mean_squared_error, mean_absolute_error
import numpy as np 

def percentage_error(actual, predicted):
    res = np.empty(actual.shape)
    for j in range(actual.shape[0]):
        if actual[j] != 0:
            res[j] = (actual[j] - predicted[j]) / actual[j]
        else:
            res[j] = predicted[j] / np.mean(actual)
    return res

def mean_absolute_percentage_error(y_true, y_pred): 
    return np.mean(np.abs(percentage_error(np.asarray(y_true), np.asarray(y_pred)))) * 100

def report_performance(Y_test, y_predicted):
    mse = mean_squared_error(Y_test, y_predicted)
    mape = mean_absolute_percentage_error(Y_test, y_predicted)
    mae = mean_absolute_error(Y_test, y_predicted)
    report = 'MSE: %.3f, MAE: %.3f, MAPE: %.3f' % (mse, mae, mape)
    print(report)
    return report