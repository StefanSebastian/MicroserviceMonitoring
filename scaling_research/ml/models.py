from keras.models import Sequential
from keras.layers import LSTM
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import TimeDistributed
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D

from config import cnn_seq, cnn_steps_per_seq, lstm_units

class CNN_LSTM:
    # by default take from config
    def __init__(self, cnn_seq=cnn_seq, cnn_steps_per_seq=cnn_steps_per_seq, lstm_units=lstm_units):
        super().__init__()
        self.cnn_seq = cnn_seq
        self.cnn_steps_per_seq = cnn_steps_per_seq
        self.lstm_units = lstm_units

    def get_model(self):
        model = Sequential()
        model.add(TimeDistributed(Conv1D(filters=64, kernel_size=1, activation='relu'), input_shape=(None, self.cnn_steps_per_seq, 1)))
        model.add(TimeDistributed(MaxPooling1D(pool_size=2)))
        model.add(TimeDistributed(Flatten()))
        model.add(LSTM(self.lstm_units, activation='relu'))
        model.add(Dense(1))
        model.compile(optimizer='adam', loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], self.cnn_seq, self.cnn_steps_per_seq, 1))

