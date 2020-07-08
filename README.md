# NetCDFProcessor
Java program to read a netCDF file (point cloud – sample attached) and for a given center lat-lon and radius(mtr.) (user input; must be within the file read in) generate the following data:

a.                   No. of points within the radius

b.                   Min, Max, Average, Median values of the heights of all points within the radius

c.                   Weighted average of the Altitude value of the points within the radius. Weight the closest points at 100%(user input) and furthest points at 50%(user input) with a linear distribution
