from keras.models import Sequential
from keras.layers import LSTM
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import TimeDistributed
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D

from config import cnn_seq, cnn_steps_per_seq

class CNN_LSTM:
    def __init__(self):
        super().__init__()

    def get_model(self):
        model = Sequential()
        model.add(TimeDistributed(Conv1D(filters=64, kernel_size=1, activation='relu'), input_shape=(None, cnn_steps_per_seq, 1)))
        model.add(TimeDistributed(MaxPooling1D(pool_size=2)))
        model.add(TimeDistributed(Flatten()))
        model.add(LSTM(50, activation='relu'))
        model.add(Dense(1))
        model.compile(optimizer='adam', loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], cnn_seq, cnn_steps_per_seq, 1))

