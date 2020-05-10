import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

df = pd.read_csv('request_log.csv')
df.info()

ts_df = df[["timestamp", "duration"]]
ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
ts_df.set_index("timestamp", inplace=True)
ts_df.plot()
plt.axhline(y=50, color='r', linestyle='-')
plt.show()
