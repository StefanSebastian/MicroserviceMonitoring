import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

df = pd.read_csv('1serv.csv')
df.info()

def check_nr_req(df):
    ts_df = df[["timestamp"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df["count"] = 1
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq='45s')).sum()
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.plot()
    plt.show()

def check_mean_duration(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq='45s')).mean()
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.plot()
    plt.show()

# 90th Percentile
def q90(x):
    return x.quantile(0.9)

def check_count_duration(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    ts_df["count"] = 1
    gdf = ts_df.groupby(pd.Grouper(freq='30s')).agg({"duration": q90, "count": np.sum})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    print(gdf)
    gdf.plot(x="count", y="duration")
    plt.show()

def msc_calcuate(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    ts_df["count"] = 1
    gdf = ts_df.groupby(pd.Grouper(freq='1min')).agg({"duration": q90, "count": np.sum})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    print(gdf)
    gdf.plot(x="count", y="duration")
    plt.show()

check_nr_req(df)
check_mean_duration(df)
msc_calcuate(df)