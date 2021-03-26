import matplotlib.pyplot as plt
import pandas as pd
import os

fig = plt.figure()
file_name = os.listdir("output").pop(0) #get part-r-00000
file = open("output/" + file_name, "r").readlines()

data = []
for line in file:
    line = line.replace('\t', ',').rstrip('\n').split(',')
    for j in range(len(line)):
        line[j] = float(line[j])
    data.append(line)

df = pd.DataFrame(data, columns=['cid', 'cx', 'cy', 'cz', '-', 'x', 'y', 'z'])
k = df['cid'].drop_duplicates().size

for i in range(k):
    cluster = df.loc[df['cid'] == i]
    plt.scatter(cluster['x'], cluster['y'])
plt.show()