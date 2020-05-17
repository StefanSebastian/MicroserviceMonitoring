from model import MLP 
from keras.models import model_from_json


def load_model(model_dir="model"):
    json_file = open(model_dir + '/model.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights(model_dir + "/model.h5")
    print("Loaded model from disk")
    return loaded_model

def store_model(model, model_dir="model"):
    # serialize model
    model_json = model.to_json()
    with open(model_dir + "/model.json", "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights(model_dir + "/model.h5")
    print("Saved model to disk")


model_obj = MLP()
model = model_obj.get_model()