

def store_model(model, model_dir="model"):
    # serialize model
    model_json = model.to_json()
    with open(model_dir + "/model.json", "w") as json_file:
        json_file.write(model_json)
    # serialize weights to HDF5
    model.save_weights(model_dir + "/model.h5")
    print("Saved model to disk")
