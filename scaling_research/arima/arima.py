import pandas as pd 
import numpy as np
import matplotlib.pyplot as plt
from pandas.plotting import autocorrelation_plot
from statsmodels.tsa.arima_model import ARIMA
from statsmodels.tsa.arima_model import ARIMAResults
from datetime import datetime
import time

from config import ts_file, window_size, test_split

def transform_series():
    df = pd.read_csv(ts_file, header=None, names=["Ts"])
    df["Ts"] = pd.to_datetime(df["Ts"], unit='s')
    df["Count"] = 1
    df.set_index("Ts", inplace=True)
    series = df.groupby(pd.Grouper(freq=window_size)).sum()

series = pd.read_pickle("arima/pickled_series")

def check_stationary(df):
    from statsmodels.tsa.stattools import adfuller
    from numpy import log
    result = adfuller(df)
    print(result)
    print('ADF Statistic: %f' % result[0])
    print('p-value: %f' % result[1])
#check_stationary(series)

def show_pacf(df):
    from statsmodels.graphics.tsaplots import plot_pacf
    plot_pacf(df)
    plt.show()
#show_pacf(series)

def show_acf(df):
    from statsmodels.graphics.tsaplots import plot_acf
    plot_acf(df)
    plt.show()
#show_acf(series)

train, test = np.split(series, [int(test_split*len(series))])


def fit_model():
    start_time = time.time()
    model = ARIMA(train, order=(1,0,20))
    model_fit = model.fit()
    print(model_fit.summary())
    train_time = time.time() - start_time
    print("--- %s seconds for training ---" % (train_time))
    model_fit.save('arima/model.pkl')

# load model
loaded = ARIMAResults.load('arima/model.pkl')
test = test["Count"].values
fc, _, _ = loaded.forecast(len(test)) 


from perf_report import report_performance
report_performance(test, fc)

def plot_predictions(Y_test, y_predicted):
    plt.clf()
    test_ln, = plt.plot(Y_test)
    predicted_ln, = plt.plot(y_predicted)
    plt.xlabel("Time window")
    plt.ylabel("Workload")
    plt.legend((test_ln, predicted_ln), ("Actual", "Predicted"))
    plt.show()
plot_predictions(test, fc)