import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasRegressor
from keras import losses
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
import numpy as np
from pandas import DataFrame
from pandas import concat

from train_config import data_file, feature_no

def read(data_file):
    dataframe = pd.read_csv(data_file, header=None)
    return dataframe.values

def normalize(data):
    min_d = np.amin(data)
    max_d = np.amax(data)
    normalizer = lambda t: (t - min_d) / (max_d - min_d)
    normalized = np.array([normalizer(xi) for xi in data])
    return normalized

def to_supervised(data, feature_no=10):
    dataset_size = data.shape[0] - feature_no - 1
    res = np.zeros((dataset_size, feature_no + 1))
    normalized = normalize(data)
    for idx in range(0, dataset_size, 1):
        x_row = normalized[idx:idx + feature_no]
        y_row = data[idx + feature_no]
        res[idx,:] = np.append(x_row, y_row)
    return res

grouped = read(data_file)
dataset = to_supervised(grouped, feature_no)

print(dataset)

