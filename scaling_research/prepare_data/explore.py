import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 
import seaborn as sns 

from prep_config import ts_file

df = pd.read_csv(ts_file, header=None, names=["Ts"])
df["Ts"] = pd.to_datetime(df["Ts"], unit='s')
df['Count'] = 1
df.info()

df.set_index('Ts', inplace=True)

