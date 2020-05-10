import pandas as pd
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

