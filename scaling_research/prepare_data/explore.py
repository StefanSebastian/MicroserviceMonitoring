import pandas as pd
import numpy as np
import matplotlib.pyplot as plt 
import seaborn as sns 

from prep_config import explore_file, ts_file

df = pd.read_csv(explore_file, header=None, names=["Reqs"])
df.info()
df.plot()
plt.show()