import pandas as pd
import numpy as np

from prep_config import ts_file, window_size, feature_no

df = pd.read_csv(ts_file, header=None, names=["Ts"])
df["Ts"] = pd.to_datetime(df["Ts"], unit='s')
df["Count"] = 1
df.set_index("Ts", inplace=True)
gdf = df.groupby(pd.Grouper(freq=window_size)).sum()

data = np.asarray(gdf["Count"])
np.savetxt('grouped.txt', data, delimiter=',', fmt='%d')

min_d = np.amin(data)
max_d = np.amax(data)
normalizer = lambda t: (t - min_d) / (max_d - min_d)
normalized = np.array([normalizer(xi) for xi in data])
np.savetxt('normalized.txt', normalized, delimiter=',', fmt='%f')


file_name = 'dataset.csv'
f = open(file_name, 'w+')
for idx in range(0, normalized.shape[0] - feature_no - 1, 1):
    row = normalized[idx:idx + feature_no + 1]
    rowstr = ','.join(['%f' % num for num in row])
    f.write(rowstr + "\n")
f.close()