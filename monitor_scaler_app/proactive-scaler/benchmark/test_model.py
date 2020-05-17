from keras.models import model_from_json
import numpy as np

def load_model(model_dir="demo"):
    json_file = open(model_dir + '/model.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights(model_dir + "/model.h5")
    print("Loaded model from disk")
    return loaded_model

model = load_model()
model.summary()

history = [156, 1456, 1500, 167, 289, 1572, 1305, 167]
X_test = np.array(history).reshape(1, 8)
print(X_test)
y_predicted = model.predict(X_test)

print(y_predicted)