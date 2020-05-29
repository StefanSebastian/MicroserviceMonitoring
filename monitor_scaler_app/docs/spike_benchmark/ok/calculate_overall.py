import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 

dfn = pd.read_csv('60n.csv')
dfr = pd.read_csv('60r.csv')
dfp = pd.read_csv('60p.csv')


def stats(df):
    ts = df["timestamp"].iloc[0]
    cutoff = ts + 60000

    dfilt = df[df["timestamp"] > cutoff] # drop first minute
    print(dfilt["duration"].mean())
    print(dfilt["duration"].quantile(q=0.9))

stats(dfn)
stats(dfr)
stats(dfp)