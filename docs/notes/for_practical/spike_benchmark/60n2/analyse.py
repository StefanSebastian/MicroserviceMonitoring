import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

df = pd.read_csv('60r.csv')
df.info()

def reqs_per_sec(df, freq='30s'):
    ts_df = df[["timestamp"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df["count"] = 1
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq=freq)).sum()
    gdf.plot()
    plt.show()

def duration_plot(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    ts_df.plot()
    plt.show()

# 90th Percentile
def q90(x):
    return x.quantile(0.9)

def msc_calcuate(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    ts_df["count"] = 1
    gdf = ts_df.groupby(pd.Grouper(freq='30s')).agg({"duration": q90, "count": np.sum})
    print(gdf)
    gdf.plot(x="count", y="duration")
    plt.show()

def check_mean_duration(df, freq='10s'):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq=freq)).agg({"duration": q90})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.plot()
    plt.axhline(y=3700, color='r', linestyle='-')
    plt.show()

#duration_plot(df)
check_mean_duration(df)