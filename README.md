# K-means algorithm with hadoop
This is an implementation of k-means algorithm for distributed systems

## Generating dataset

You can generate dataset with 
```
python datasetgen.py N K STD
```
where N is the number of points you want to generate, K is the number of clusters and STD is standard deviation of points from clusters, for example:
```
python datasetgen.py 1000 3 0.45
```

## Plotting

After running program you can plot results with 
```
python plot.py
```
## Other k-means versions
- [Sequential](https://github.com/MarcoSolarino/Midterm_Parallel_Computing_K-means)
- [CUDA](https://github.com/daikon899/Midterm_K-means_CUDA)
