from keras.models import Sequential
from keras.layers import Dense
from keras import optimizers

class MLP:
    def get_model(self, optimizer="adadelta", activation="relu", feature_no=8):
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