from keras.models import Sequential
from keras.layers import LSTM
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import TimeDistributed
from keras.layers import Dropout
from keras.layers.convolutional import Conv1D
from keras.layers.convolutional import MaxPooling1D
from keras import optimizers

from config import cnn_seq, feature_no, dense_units

class CNN_LSTM:
    # by default take from config
    def __init__(self, cnn_seq=cnn_seq, cnn_steps_per_seq=int(feature_no / cnn_seq)):
        super().__init__()
        self.cnn_seq = cnn_seq
        self.cnn_steps_per_seq = cnn_steps_per_seq

    def get_model(self, lstm_units=500):
        model = Sequential()
        model.add(TimeDistributed(Conv1D(filters=32, kernel_size=1, activation='relu'), input_shape=(None, self.cnn_steps_per_seq, 1)))
        model.add(TimeDistributed(MaxPooling1D(pool_size=2)))
        model.add(TimeDistributed(Flatten()))
        model.add(LSTM(lstm_units, activation='relu'))
        model.add(Dense(1))
        ada_dlt = optimizers.Adadelta(clipnorm=1.)
        model.compile(optimizer=ada_dlt, loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], self.cnn_seq, self.cnn_steps_per_seq, 1))

class CNN_LSTMv2:
    # by default take from config
    def __init__(self):
        super().__init__()
        
    def get_model(self, lstm_units=500):
        model = Sequential()
        model.add(Conv1D(filters=32, kernel_size=8, strides=1, activation='relu', padding='same'))
        model.add(LSTM(lstm_units, activation='relu'))
        model.add(Dense(1))
        ada_dlt = optimizers.Adadelta(clipnorm=1.)
        model.compile(optimizer=ada_dlt, loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], data.shape[1], 1))

class MLP:
    def __init__(self, dense_units=dense_units):
        super().__init__()
        self.dense_units = dense_units

    def get_model(self, optimizer="adadelta", activation="relu"):
        model = Sequential()
        model.add(Dense(100, activation=activation, input_dim=feature_no))
        model.add(Dense(50, activation=activation))
        model.add(Dense(25, activation=activation))
        model.add(Dense(20, activation=activation))
        model.add(Dense(10, activation=activation))
        model.add(Dense(1))
        model.compile(optimizer=optimizer, loss='mse', metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data

class VariableMLP:
    def __init__(self):
        super().__init__()

    def get_model(self, optimizer="adadelta", activation="relu", layers=(100,150)):
        model = Sequential()
        for size in layers:
            model.add(Dense(size))
        model.add(Dense(1))
        model.compile(optimizer=optimizer, loss='mse', metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data

class BaseLSTM:
    def get_model(self):
        model = Sequential()
        model.add(LSTM(150, input_shape=(1, feature_no)))
        model.add(Dropout(0.2))
        model.add(Dense(1))
        model.compile(loss='mse', optimizer='adam',  metrics=['mse', 'mae', 'mape'])
        return model 
        
    def transform_data(self, data):
        return data.reshape((data.shape[0], 1, data.shape[1]))

class CNN:
    def __init__(self):
        super().__init__()
    
    def get_model(self, optimizer='adadelta', activation='softplus'):
        model = Sequential()
        model.add(Conv1D(filters=64, kernel_size=2, activation='relu', input_shape=(feature_no, 1)))
        model.add(MaxPooling1D(pool_size=2))
        model.add(Flatten())
        model.add(Dense(150, activation=activation))
        model.add(Dense(1))
        model.compile(optimizer=optimizer, loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], data.shape[1], 1))

class DeeperCNN:
    def __init__(self):
        super().__init__()
    
    def get_model(self, optimizer='adadelta', activation='softplus', layers=(100,20,10,5)):
        model = Sequential()
        model.add(Conv1D(filters=64, kernel_size=2, activation='relu', input_shape=(feature_no, 1)))
        model.add(MaxPooling1D(pool_size=2))
        model.add(Flatten())
        for size in layers:
            if size != 0:
                model.add(Dense(size))
        model.add(Dense(1))
        model.compile(optimizer=optimizer, loss='mse',  metrics=['mse', 'mae', 'mape'])
        return model

    def transform_data(self, data):
        return data.reshape((data.shape[0], data.shape[1], 1))
