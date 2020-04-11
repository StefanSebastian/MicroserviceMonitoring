import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Dropout
from keras.wrappers.scikit_learn import KerasRegressor
from keras import losses
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from matplotlib import pyplot
import numpy as np
from keras.callbacks import EarlyStopping

from train_config import data_file, feature_no

dataframe = pd.read_csv(data_file, header=None)
dataset = dataframe.values

# split into train and test sets
X = dataset[:, 0:feature_no]
Y = dataset[:, feature_no]
cutoff = int(0.66 * len(X))
X_train, X_test = X[:cutoff], X[cutoff:]
y_train, y_test = Y[:cutoff], Y[cutoff:]

# reshape input to be [samples, time steps, features]
X_train = np.reshape(X_train, (X_train.shape[0], 1, X_train.shape[1]))
X_test = np.reshape(X_test, (X_test.shape[0], 1, X_test.shape[1]))

model = Sequential()
model.add(LSTM(300, input_shape=(X_train.shape[1], X_train.shape[2])))
model.add(Dropout(0.2))
model.add(Dense(1))
model.compile(loss='mse', optimizer='adam',  metrics=['mse', 'mae', 'mape'])
 

history = model.fit(X_train, y_train, epochs=200, batch_size=5, validation_data=(X_test, y_test), 
                    callbacks=[EarlyStopping(monitor='val_loss', patience=10)], verbose=1, shuffle=False)

model.summary()