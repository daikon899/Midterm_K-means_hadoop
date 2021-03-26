import sys
import matplotlib.pyplot as plt
import numpy as np
import sklearn.datasets as sk

points_num = int(sys.argv[1])
X, y = sk.make_blobs(n_samples=points_num, n_features=2, centers=4, cluster_std=1)
array = X.tolist()
for a in array:
    a.append(0)
nparray = np.array(array)
np.savetxt("input/dataset.csv", nparray, delimiter=",", fmt='%f')
print('dataset of ' + str(points_num) + ' points created!')
