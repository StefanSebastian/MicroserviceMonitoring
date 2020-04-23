import pickle
import matplotlib.pyplot as plt
import numpy as np

infile = open("ml/gsresults",'rb')
gs_res = pickle.load(infile)
infile.close()

means = gs_res['mean_test_score']
stds = gs_res['std_test_score']
params = gs_res['params']
for mean, stdev, param in zip(means, stds, params):
    print("%f (%f) with: %r" % (mean, stdev, param))

print(means)
print(stds)
print(params)


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
    ax.set_ylabel('Average MSE', fontsize=16)
    ax.legend(loc="best", fontsize=15)
    ax.grid('on')
    plt.show()

#batch_size = [4, 8, 16, 32]
#epochs = [50, 100, 250]
#plot_grid_search(gs_res, batch_size, epochs, 'Batch size', 'Epochs')
