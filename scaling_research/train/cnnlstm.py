from keras.models import Sequential
from keras.layers import LSTM
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import TimeDistributed
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D
from keras.callbacks import EarlyStopping

from keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold

from data_prepare import get_split_dataset

X, Y = get_split_dataset()

# todo maybe move to a fc
cutoff = int(0.9 * len(X))
X_train, X_test = X[:cutoff], X[cutoff:]
y_train, y_test = Y[:cutoff], Y[cutoff:]
print(X_train)
print(y_train)

X_train = X_train.reshape((X_train.shape[0], 5, 2, 1))
X_test = X_test.reshape((X_test.shape[0], 5, 2, 1))

def cnnlstm_model():
    model = Sequential()
    model.add(TimeDistributed(Conv1D(filters=64, kernel_size=1, activation='relu'), input_shape=(None, 2, 1)))
    model.add(TimeDistributed(MaxPooling1D(pool_size=2)))
    model.add(TimeDistributed(Flatten()))
    model.add(LSTM(50, activation='relu'))
    model.add(Dense(1))
    model.compile(optimizer='adam', loss='mse',  metrics=['mse', 'mae', 'mape'])
    return model

import time
start_time = time.time()

# evaluate model
estimator = KerasRegressor(build_fn=cnnlstm_model, epochs=10, batch_size=5)
kfold = KFold(n_splits=3, shuffle=False)
results = cross_val_score(estimator, X=X_train, y=y_train, cv=kfold, verbose=1)
print("Baseline: %f %f " % (results.mean(), results.std()))

print("--- %s seconds ---" % (time.time() - start_time))
