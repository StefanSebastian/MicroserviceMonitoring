from keras.models import Sequential
from keras.layers import LSTM
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import TimeDistributed
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D
from keras.callbacks import EarlyStopping

from data_prepare import get_split_dataset

X, Y = get_split_dataset()

# todo maybe move to a fc
cutoff = int(0.66 * len(X))
X_train, X_test = X[:cutoff], X[cutoff:]
y_train, y_test = Y[:cutoff], Y[cutoff:]

X_train = X_train.reshape((X_train.shape[0], 5, 2, 1))
X_test = X_test.reshape((X_test.shape[0], 5, 2, 1))

model = Sequential()
model.add(TimeDistributed(Conv1D(filters=64, kernel_size=1, activation='relu'), input_shape=(None, 2, 1)))
model.add(TimeDistributed(MaxPooling1D(pool_size=2)))
model.add(TimeDistributed(Flatten()))
model.add(LSTM(50, activation='relu'))
model.add(Dense(1))
model.compile(optimizer='adam', loss='mse',  metrics=['mse', 'mae', 'mape'])

history = model.fit(X_train, y_train, epochs=100, batch_size=5, validation_data=(X_test, y_test), 
                    callbacks=[EarlyStopping(monitor='val_loss', patience=10)], verbose=1, shuffle=False)

model.summary()
