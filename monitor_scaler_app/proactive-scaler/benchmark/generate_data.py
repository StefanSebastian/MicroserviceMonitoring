from random import randint
import numpy as np

def get_data():
    data = generate_reqs()
    # split into train and test
    train, test = split(data)
    # skip normalization ; dataset is very basic
    sv_train = to_supervised(train, train, 8)
    sv_test = to_supervised(test, test, 8)
    # extract target variable
    X_train, Y_train = split_target_var(sv_train, 8)
    X_test, Y_test = split_target_var(sv_test, 8)

    return X_train, Y_train, X_test, Y_test

def generate_reqs():
    data = np.zeros(2000)
    for i in range(2000):
        if i % 4 == 0 or i % 4 == 1:
            data[i] = randint(100, 300)
        else:
            data[i] = randint(1300, 1700)
    return data

def split(data):
    print("Splitting data set of size : " + str(len(data)))
    print("Ratio of " + str(0.9))
    cutoff = int(0.9 * len(data))
    train = data[:cutoff]
    test = data[cutoff - 8:] # add buffer
    print("Train size : " + str(len(train)))
    print("Test size: " + str(len(test)))
    return train, test

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

if (__name__ == '__main__'):
    X_train, Y_train, X_test, Y_test = get_data()
    print(X_train, Y_train, X_test, Y_test)