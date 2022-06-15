![last commit](https://img.shields.io/github/last-commit/sim-pez/distributed_kmeans) ![](https://img.shields.io/github/languages/top/sim-pez/distributed_kmeans)

# Intro

This is an implementation of [k-means  clustering algorithm](https://en.wikipedia.org/wiki/K-means_clustering) for distributed systems using Hadoop.
You will need to manually specify the number of clusters _K_ when launching the program.

# Generating dataset
You can generate an _N_ points dataset using ```datasetgen.py```. You have to write also the number of clusters _K_ and the standard deviation. The command will be like:
```
python datasetgen.py N K STD
```
example:
```
python datasetgen.py 1000 3 0.45
```


# Plotting
If you are running the program on a single node for testing purposes, you can also plot the result using:
```
python plot.py
```


# Other k-means versions
We made also:
- [Sequential version in C++](https://github.com/MarcoSolarino/Midterm_Parallel_Computing_K-means)
- [CUDA](https://github.com/sim-pez/k_means_gpu)


# Acknowledgments
Parallel Computing - Computer Engineering Master Degree @[University of Florence](https://www.unifi.it/changelang-eng.html).
