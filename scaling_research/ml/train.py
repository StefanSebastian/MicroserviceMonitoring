from keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.model_selection import GridSearchCV
import pickle
import time
import numpy as np 
import matplotlib.pyplot as plt

from preparedata import get_data
from models import BaselineMLP, VariableMLP
from config import epochs, batch_size

X_train, Y_train, _, _ = get_data()

model_obj = VariableMLP()
X_train = model_obj.transform_data(X_train)
keras_model = KerasRegressor(build_fn=model_obj.get_model)

start_time = time.time()
# define the grid search parameters
def epoch_batch_grid():
    batch_size = [4, 8, 16, 32]
    epochs = [50, 100, 250]
    param_grid = dict(batch_size=batch_size, epochs=epochs)
    return param_grid

def activation_grid():
    activation = ['softmax', 'softplus', 'softsign', 'relu', 'tanh', 'hard_sigmoid', 'linear']
    param_grid = dict(activation=activation, epochs=[100], batch_size=[8])
    return param_grid

def optimizer_grid():
    optimizer = ['RMSprop', 'Adagrad', 'Adadelta', 'Adam', 'Adamax', 'Nadam']
    param_grid = dict(optimizer=optimizer, epochs=[100], batch_size=[8])
    return param_grid

def layers_grid():
    layers = [(150,100),(150,100,50),(50,50,50),(50,50,50,50),(10,10,10,10),(75,25,5),(100,50,25,20,10),(10,10,10,10,10,10),(100,100,100,100,100,100)]
    param_grid = dict(layers=layers, epochs=[100], batch_size=[8])
    return param_grid

param_grid = layers_grid()
grid = GridSearchCV(estimator=keras_model, param_grid=param_grid, n_jobs=-1, cv=3)
grid_result = grid.fit(X_train, Y_train)

print("--- %s seconds ---" % (time.time() - start_time))

# summarize results
print("Best: %f using %s" % (grid_result.best_score_, grid_result.best_params_))
means = grid_result.cv_results_['mean_test_score']
stds = grid_result.cv_results_['std_test_score']
params = grid_result.cv_results_['params']
for mean, stdev, param in zip(means, stds, params):
    print("%f (%f) with: %r" % (mean, stdev, param))

outfile = open("ml/gsresults",'wb')
pickle.dump(grid_result.cv_results_, outfile)
outfile.close()