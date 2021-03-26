import matplotlib.pyplot as plt
import pandas as pd
# import os

fig = plt.figure()
# file_name = os.listdir("output").pop(0) # get part-r-00000
file = open("output/part-r-00000", "r").readlines()

data = []
for line in file:
    line = line.replace('\t', ',').rstrip('\n').split(',')
    for j in range(len(line)):
        line[j] = float(line[j])
        data.append(line)

df = pd.DataFrame(data, columns=['cid', 'cx', 'cy', 'cz', '-', 'x', 'y', 'z'])
centroids = df.drop_duplicates(subset=['cid'])[['cid', 'cx', 'cy', 'cz']]
k = len(centroids.index)

for i in range(k):
    cluster = df.loc[df['cid'] == centroids.iloc[i]['cid']]
    plt.scatter(cluster['x'], cluster['y'])

for i in range(k):
    c = centroids.iloc[i]
    plt.scatter(c['cx'], c['cy'], c='black', marker='*')

plt.show()
