from keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
from sklearn.model_selection import GridSearchCV
import time
import numpy as np 
import matplotlib.pyplot as plt

from preparedata import get_data
from models import CNN_LSTM
from config import epochs, batch_size

X_train, Y_train, _, _ = get_data()

model_obj = CNN_LSTM()
X_train = model_obj.transform_data(X_train)
keras_model = KerasRegressor(build_fn=model_obj.get_model)

start_time = time.time()
# define the grid search parameters
batch_size = [100, 250, 500]
epochs = [5, 6]

param_grid = dict(batch_size=batch_size, epochs=epochs)

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

def plot_grid_search(cv_results, grid_param_1, grid_param_2, name_param_1, name_param_2):
    # Get Test Scores Mean and std for each grid search
    scores_mean = cv_results['mean_test_score']
    print(scores_mean)
    scores_mean = np.array(scores_mean).reshape(len(grid_param_1),len(grid_param_2))
    scores_mean = scores_mean.transpose()
    print(scores_mean)
    # Plot Grid search scores
    _, ax = plt.subplots(1,1)

    # Param1 is the X-axis, Param 2 is represented as a different curve (color line)
    for idx, val in enumerate(grid_param_2):
        ax.plot(grid_param_1, scores_mean[idx,:], '-o', label= name_param_2 + ': ' + str(val))

    ax.set_title("Grid Search Scores", fontsize=20, fontweight='bold')
    ax.set_xlabel(name_param_1, fontsize=16)
    ax.set_ylabel('CV Average Score', fontsize=16)
    ax.legend(loc="best", fontsize=15)
    ax.grid('on')
    plt.show()

# Calling Method 
plot_grid_search(grid_result.cv_results_, batch_size, epochs, 'Batch size', 'Epochs')
