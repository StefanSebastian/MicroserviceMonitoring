import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

df = pd.read_csv('msc.csv')
df.info()

def check_nr_req(df):
    ts_df = df[["timestamp"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df["count"] = 1
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq='30s')).sum()
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.plot()
    plt.show()

def check_mean_duration(df):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq='30s')).mean()
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
    gdf = ts_df.groupby(pd.Grouper(freq='30s')).agg({"duration": q90, "count": np.sum})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.drop(gdf.tail(2).index,inplace=True) # from prev analysis, the performance in the last 2 windows was already impacted; not relevant as SLA was already passed
    print(gdf)
    gdf.plot(x="count", y="duration")
    plt.show()
    
msc_calcuate(df)