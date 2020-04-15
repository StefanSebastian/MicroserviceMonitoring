import pandas as pd
import numpy as np

from prep_config import ts_file, window_size

df = pd.read_csv(ts_file, header=None, names=["Ts"])
df["Ts"] = pd.to_datetime(df["Ts"], unit='s')
df["Count"] = 1
df.set_index("Ts", inplace=True)
gdf = df.groupby(pd.Grouper(freq=window_size)).sum()

data = np.asarray(gdf["Count"])
np.savetxt('grouped.txt', data, delimiter=',', fmt='%d')