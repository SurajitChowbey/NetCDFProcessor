# NetCDFProcessor
Java program to read a netCDF file (point cloud – sample attached) and for a given center lat-lon and radius(mtr.) (user input; must be within the file read in) generate the following data:

a.                   No. of points within the radius

b.                   Min, Max, Average, Median values of the heights of all points within the radius

c.                   Weighted average of the Altitude value of the points within the radius. Weight the closest points at 100%(user input) and furthest points at 50%(user input) with a linear distribution

### Build and run

#### Prerequisites

- Java 8+
- Maven 3.0+

#### From Eclipse

Directly import from the GIT project url as Maven project

To Run it:

1 Goto com.nc.example

2 Right click on App.java

3 Run As -> Run Configurations

4 In Program Arguments under Arguments tab add the path to .nc file

5 Apply -> Run

You can also call the App.main method, from your class with [path to .nc file] in first position in main String[] argument.
 
