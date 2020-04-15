import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.wrappers.scikit_learn import KerasRegressor
from keras import losses
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold

from train_config import data_file, feature_no

dataframe = pd.read_csv(data_file, header=None)
dataset = dataframe.values

X = dataset[:,0:feature_no]
Y = dataset[:,feature_no]

# define base model
def baseline_model():
	# create model
	model = Sequential()
	model.add(Dense(feature_no, input_dim=feature_no, kernel_initializer='normal', activation='relu'))
	model.add(Dense(1, kernel_initializer='normal'))
	# Compile model
	model.compile(loss=losses.mse, optimizer='adam', metrics=['mse', 'mae', 'mape'])
	return model

# evaluate model
estimator = KerasRegressor(build_fn=baseline_model, epochs=100, batch_size=5)
kfold = KFold(n_splits=3)
results = cross_val_score(estimator, X, Y, cv=kfold)
print("Baseline: %f %f " % (results.mean(), results.std()))