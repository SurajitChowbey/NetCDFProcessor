package com.nc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.ogc.OGCGeometry;
import com.esri.core.geometry.ogc.OGCPoint;
import com.nc.data.FileSettings;
import com.nc.data.InputData;
import com.nc.data.OutputData;
import com.nc.data.TerrainPoint;
import com.nc.exception.LatLonOrderNotFoundException;
import com.nc.exception.LocationOutOfBoundException;
import com.nc.exception.VariablesNotFoundException;

import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * Process netCDF file
 * 
 * @author Surajit
 * 
 * @see NetcdfFile
 * @see FileSettings
 * @see InputData
 * @see OutputData
 */
public class Reader {

	public static final short LATLON_ORDER = 1;
	public static final short LONLAT_ORDER = 2;

	/**
	 * Process a netCDF file.
	 * <p>
	 * It will give the followings as output
	 * </p>
	 * 
	 * <p>
	 * No. of points within the radius
	 * </p>
	 * <p>
	 * Min, Max, Average, Median values of the heights of all points within the
	 * radius
	 * </p>
	 * <p>
	 * Weighted average of the Altitude value of the points within the radius.
	 * Weight the closest points at {@link InputData#getClosestPointWeight()} and
	 * {@link InputData#getFurthestPointWeight()} points at 50% with a linear
	 * distribution
	 * </p>
	 * 
	 * @param ncfile
	 * @param fileSettings
	 * @param inputData
	 * @return An object of <tt>OutputData</tt>
	 * @throws IOException
	 * @throws LocationOutOfBoundException
	 * @throws VariablesNotFoundException
	 * @throws LatLonOrderNotFoundException
	 * 
	 * @see NetcdfFile
	 * @see FileSettings
	 * @see InputData
	 * @see OutputData
	 */
	public static OutputData processFile(NetcdfFile ncfile, FileSettings fileSettings, InputData inputData)
			throws VariablesNotFoundException, IOException, LocationOutOfBoundException, LatLonOrderNotFoundException {

		OGCPoint givenPoint = new OGCPoint(new Point(inputData.getLon(), inputData.getLat()),
				SpatialReference.create(inputData.getSrid()));
		OGCPoint givenPointMarcetor = TerrainPoint.toMarcetor(givenPoint);

		OGCGeometry polygon = givenPointMarcetor.buffer(inputData.getRadius());

		Variable lat = ncfile.findVariable(fileSettings.getLatName());
		Variable lon = ncfile.findVariable(fileSettings.getLonName());
		Variable band = ncfile.findVariable(fileSettings.getBandName());

		if (lat == null || lon == null || band == null) {
			throw new VariablesNotFoundException("Target variable(s) not found");
		}

		Array latData = lat.read();
		Array lonData = lon.read();
		Array bandData = band.read();

		// get order

		int indexOfLatInBand = band.getDimensions().indexOf(lat.getDimension(0));
		int indexOfLonInBand = band.getDimensions().indexOf(lon.getDimension(0));

		if (indexOfLatInBand == indexOfLonInBand || indexOfLatInBand < 0 || indexOfLonInBand < 0) {
			throw new LatLonOrderNotFoundException("Unable to find the order of lon, lat in band");
		}

		double[] minXY = new double[2];
		double[] maxXY = new double[2];

		int lonSize = (int) lonData.getSize();
		int latSize = (int) latData.getSize();

		minXY[0] = lonData.getDouble(0);
		maxXY[0] = lonData.getDouble(lonSize - 1);

		minXY[1] = latData.getDouble(0);
		maxXY[1] = latData.getDouble(latSize - 1);

		// double correct it
		minXY[0] = Math.min(minXY[0], maxXY[0]);
		maxXY[0] = Math.max(minXY[0], maxXY[0]);

		minXY[1] = Math.min(minXY[1], maxXY[1]);
		maxXY[1] = Math.max(minXY[1], maxXY[1]);

		// transform it
		OGCPoint minPointMarcetor = TerrainPoint.toMarcetor(TerrainPoint.getOGCPoint(minXY[0], minXY[1]));
		OGCPoint maxPointMarcetor = TerrainPoint.toMarcetor(TerrainPoint.getOGCPoint(maxXY[0], maxXY[1]));

		String polygonBoundWKT = "POLYGON ((" + minPointMarcetor.X() + " " + minPointMarcetor.Y() + ", "
				+ maxPointMarcetor.X() + " " + minPointMarcetor.Y() + ", " + maxPointMarcetor.X() + " "
				+ maxPointMarcetor.Y() + ", " + minPointMarcetor.X() + " " + maxPointMarcetor.Y() + ", "
				+ minPointMarcetor.X() + " " + minPointMarcetor.Y() + "))";

		OGCGeometry dataBoundBox = OGCGeometry.fromText(polygonBoundWKT);
		dataBoundBox.setSpatialReference(SpatialReference.create(3857));

		if (!polygon.intersects(dataBoundBox)) {
			throw new LocationOutOfBoundException("Not within the boundary of data");
		}

		short order;
		Array row, column;
		if (indexOfLonInBand < indexOfLatInBand) {
			order = LONLAT_ORDER;
			row = lonData;
			column = latData;
		} else {
			order = LATLON_ORDER;
			row = latData;
			column = lonData;
		}

		List<Double> listOfHeights = new ArrayList<Double>();
		List<TerrainPoint> listOfPoints = new ArrayList<TerrainPoint>();

		double heightSum = 0d;
		double weightedHeightSum = 0d;
		float closestPointWF = inputData.getClosestPointWeight() / 100;
		float furthestPointWF = inputData.getFurthestPointWeight() / 100;
		double radius = inputData.getRadius();

		for (long i = 0; i < row.getSize() && row.hasNext(); i++) {
			double lonValue = 0d, latValue = 0d;

			if (order == LONLAT_ORDER) {
				lonValue = row.nextDouble();
			} else {
				latValue = row.nextDouble();
			}

			column.resetLocalIterator();
			for (long j = 0; j < column.getSize() && column.hasNext() && bandData.hasNext(); j++) {

				if (order == LONLAT_ORDER) {
					latValue = column.nextDouble();
				} else {
					lonValue = column.nextDouble();
				}

				// default projection is 4326
				OGCPoint currentPoint = (OGCPoint) OGCPoint.fromText("POINT(" + lonValue + " " + latValue + ")");
				OGCPoint currentPointMarcetor = TerrainPoint.toMarcetor(currentPoint);
				double height = bandData.nextDouble();
				if (polygon.contains(currentPointMarcetor)) {
					listOfHeights.add(Double.valueOf(height));
					listOfPoints.add(new TerrainPoint(currentPoint, height));

					heightSum += height;

					double distance = currentPointMarcetor.distance(givenPointMarcetor);
					double wf = (furthestPointWF + (closestPointWF - furthestPointWF) * (radius - distance) / radius);
					weightedHeightSum += (height * wf);
				}
			}
		}

		OutputData outputData = new OutputData();

		// sorting the height list for median calculation
		listOfHeights.sort(new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		});

		if (listOfHeights.size() > 0) {
			int noOfPoints = listOfHeights.size();
			outputData.setNoOfPoints(noOfPoints);
			outputData.setMinHeight(listOfHeights.get(0));
			outputData.setMaxHeight(listOfHeights.get(listOfHeights.size() - 1));
			outputData.setAverageHeight(heightSum / noOfPoints);

			double medianOfHeight = 0d;
			int midPosition = noOfPoints / 2;

			if (noOfPoints < 3) {
				medianOfHeight = heightSum / noOfPoints;
			} else if ((noOfPoints % 2) == 1) {
				medianOfHeight = listOfHeights.get(midPosition);
			} else {
				medianOfHeight = (listOfHeights.get(midPosition) + listOfHeights.get(midPosition + 1)) / 2;
			}

			outputData.setMedianOfHeight(medianOfHeight);
			outputData.setWeightedAverage(weightedHeightSum / noOfPoints);
			outputData.setTerrrainPointList(listOfPoints);
		}

		return outputData;

	}

}
