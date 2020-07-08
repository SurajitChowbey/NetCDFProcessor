package com.nc.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code OutputData} is used to store the output of {@code Reader#processFile(ucar.nc2.NetcdfFile, FileSettings, InputData)}
 * 
 * @author Surajit
 * @see Reader#processFile(ucar.nc2.NetcdfFile, FileSettings, InputData)
 *
 */
public class OutputData {

	private long noOfPoints;
	private double minHeight;
	private double maxHeight;
	private double averageHeight;
	private double medianOfHeight;
	private double weightedAverage;
	
	private List<TerrainPoint> terrrainPointList;

	public long getNoOfPoints() {
		return noOfPoints;
	}

	public void setNoOfPoints(long noOfPoints) {
		this.noOfPoints = noOfPoints;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	public double getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
	}

	public double getAverageHeight() {
		return averageHeight;
	}

	public void setAverageHeight(double averageHeight) {
		this.averageHeight = averageHeight;
	}

	public double getMedianOfHeight() {
		return medianOfHeight;
	}

	public void setMedianOfHeight(double medianOfHeight) {
		this.medianOfHeight = medianOfHeight;
	}

	public double getWeightedAverage() {
		return weightedAverage;
	}

	public void setWeightedAverage(double weightedAverage) {
		this.weightedAverage = weightedAverage;
	}
	
	public List<TerrainPoint> getTerrrainPointList() {
		return terrrainPointList;
	}

	public void setTerrrainPointList(List<TerrainPoint> terrrainPointList) {
		this.terrrainPointList = terrrainPointList;
	}

	public OutputData() {
		this.terrrainPointList = new ArrayList<>();
	}

	public OutputData(long noOfPoints, double minHeight, double maxHeight, double averageHeight, double medianOfHeight,
			double weightedAverage) {
		super();
		this.noOfPoints = noOfPoints;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.averageHeight = averageHeight;
		this.medianOfHeight = medianOfHeight;
		this.weightedAverage = weightedAverage;
		this.terrrainPointList = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "OutputData [noOfPoints=" + noOfPoints + ", minHeight=" + minHeight + ", maxHeight=" + maxHeight
				+ ", averageHeight=" + averageHeight + ", medianOfHeight=" + medianOfHeight + ", weightedAverage="
				+ weightedAverage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(averageHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(medianOfHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minHeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (noOfPoints ^ (noOfPoints >>> 32));
		temp = Double.doubleToLongBits(weightedAverage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OutputData))
			return false;
		OutputData other = (OutputData) obj;
		if (Double.doubleToLongBits(averageHeight) != Double.doubleToLongBits(other.averageHeight))
			return false;
		if (Double.doubleToLongBits(maxHeight) != Double.doubleToLongBits(other.maxHeight))
			return false;
		if (Double.doubleToLongBits(medianOfHeight) != Double.doubleToLongBits(other.medianOfHeight))
			return false;
		if (Double.doubleToLongBits(minHeight) != Double.doubleToLongBits(other.minHeight))
			return false;
		if (noOfPoints != other.noOfPoints)
			return false;
		if (Double.doubleToLongBits(weightedAverage) != Double.doubleToLongBits(other.weightedAverage))
			return false;
		return true;
	}
	
	

}
