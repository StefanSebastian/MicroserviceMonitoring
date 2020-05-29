import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

dfn = pd.read_csv('60n.csv')
dfr = pd.read_csv('60r.csv')
dfp = pd.read_csv('60p.csv')

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

def check_mean_duration(df, freq='3s'):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq=freq)).agg({"duration": q90})
    #gdf = ts_df.groupby(pd.Grouper(freq=freq)).agg({"duration": np.mean})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    gdf.plot()
    plt.ylim(0, 6000)
    plt.axhline(y=3500, color='r', linestyle='-')
    plt.show()

def get_grouped(df, freq='3s'):
    ts_df = df[["timestamp", "duration"]]
    ts_df["timestamp"] = pd.to_datetime(ts_df["timestamp"], unit='ms')
    ts_df.set_index("timestamp", inplace=True)
    gdf = ts_df.groupby(pd.Grouper(freq=freq)).agg({"duration": q90})
    gdf.drop(gdf.tail(1).index,inplace=True) # drop last interval 
    return gdf

def check_mean_duration_unified(dfn, dfr, dfp):
    gdf_n = get_grouped(dfn)
    gdf_r = get_grouped(dfr)
    gdf_p = get_grouped(dfp)
    ax = gdf_n.plot()
    ax = gdf_r.plot(ax=ax)
    gdf_p.plot(ax=ax)
    plt.axhline(y=3500, color='r', linestyle='-')
    plt.show()


def transform_store(dfn, dfr, dfp):
    gdf_n = get_grouped(dfn)
    gdf_r = get_grouped(dfr)
    gdf_p = get_grouped(dfp)

    gdf_n.to_csv('60n_transformed.csv')
    gdf_r.to_csv('60r_transformed.csv')
    gdf_p.to_csv('60p_transformed.csv')

def plot_all():
    tdfn = pd.read_csv('60n_transformed.csv')
    tdfr = pd.read_csv('60r_transformed.csv')
    tdfp = pd.read_csv('60p_transformed.csv')
    tdfn = tdfn["duration"]
    tdfr = tdfr["duration"]
    tdfp = tdfp["duration"]

    ax1 = tdfn.plot()
    ax2 = tdfr.plot(ax=ax1)
    tdfp.plot(ax=ax2)
    plt.axhline(y=3500, color='r', linestyle='-')
    plt.show()

check_mean_duration(dfp)
