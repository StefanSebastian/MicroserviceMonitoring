import pandas as pd
import numpy as np

from config import data_file, feature_no, train_test_split

def get_data():
    # read ts from file ; number of requests per time window format
    data = read(data_file)
    # split into train and test
    train, test = split(data)
    # normalize data
    norm_train, norm_test = normalize(train, test)
    # transform dataset into supervised problem
    sv_train = to_supervised(train, norm_train, feature_no)
    sv_test = to_supervised(test, norm_test, feature_no)
    # extract target variable
    X_train, Y_train = split_target_var(sv_train, feature_no)
    X_test, Y_test = split_target_var(sv_test, feature_no)

    return X_train, Y_train, X_test, Y_test

def get_sanity_check_data():
    # read ts from file ; number of requests per time window format
    data = read(data_file)
    # split into train and test
    train, test = split(data)
    # transform dataset into supervised problem
    sv_train = to_supervised(train, train, 1)
    sv_test = to_supervised(test, test, 1)
    # extract target variable
    X_train, Y_train = split_target_var(sv_train, 1)
    X_test, Y_test = split_target_var(sv_test, 1)
    return X_train, Y_train, X_test, Y_test

def read(data_file):
    print("Reading data from : " + data_file)
    dataframe = pd.read_csv(data_file, header=None)
    return dataframe.values

def split(data):
    print("Splitting data set of size : " + str(len(data)))
    print("Ratio of " + str(train_test_split))
    cutoff = int(train_test_split * len(data))
    train = data[:cutoff]
    test = data[cutoff - feature_no:] # add buffer
    print("Train size : " + str(len(train)))
    print("Test size: " + str(len(test)))
    return train, test

# normalize with same parameters as train
def normalize(train, test):
    normalizer = MinMaxScaler()
    norm_train = normalizer.fit_transform(train)
    norm_test = normalizer.transform(test)
    return norm_train, norm_test

def to_supervised(data, normalized, feature_no=10):
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

class MinMaxScaler:
    def __init__(self):
        self.min_d = None
        self.max_d = None

    def fit_transform(self, data):
        self.min_d = np.amin(data)
        self.max_d = np.amax(data)
        return self.transform(data)

    def transform(self, data):
        print("Normalizing data with min : " + str(self.min_d) + " max " + str(self.max_d))
        normalizer = lambda t: (t - self.min_d) / (self.max_d - self.min_d)
        normalized = np.array([normalizer(xi) for xi in data])
        return normalized

if (__name__ == '__main__'):
    X_train, Y_train, X_test, Y_test = get_data()
    print(X_train, Y_train, X_test, Y_test)
