from keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import KFold
import time

from preparedata import get_data
from models import CNN_LSTM
from config import epochs, batch_size

X_train, Y_train, X_test, Y_test = get_data()

model_obj = CNN_LSTM()
X_train = model_obj.transform_data(X_train)
X_test = model_obj.transform_data(X_test)

def train_model():
    start_time = time.time()
    estimator = KerasRegressor(build_fn=model_obj.get_model, epochs=epochs, batch_size=batch_size)
    kfold = KFold(n_splits=3, shuffle=False)
    results = cross_val_score(estimator, X=X_train, y=Y_train, cv=kfold, verbose=1)
    print("Baseline: %f %f " % (results.mean(), results.std()))
    print("--- %s seconds ---" % (time.time() - start_time))

train_model()
