package com.nc.data;

/**
 * The class {@code OutputData} is used to store the input of {@code Reader#processFile(ucar.nc2.NetcdfFile, FileSettings, InputData)}
 * 
 * @author Surajit
 * @see Reader#processFile(ucar.nc2.NetcdfFile, FileSettings, InputData)
 *
 */
public class InputData {

	private double lon;
	private double lat;
	private double radius;
	
	private float closestPointWeight;
	private float furthestPointWeight;
	
	private int srid = 4326;
	
	public InputData(double lon, double lat, double radius, float closestPointWeight, float furthestPointWeight) {
		this.lon = lon;
		this.lat = lat;
		this.radius = radius;
		this.closestPointWeight = closestPointWeight;
		this.furthestPointWeight = furthestPointWeight;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}


	public void setClosestPointWeight(float closestPointWeight) {
		this.closestPointWeight = closestPointWeight;
	}


	public void setFurthestPointWeight(float furthestPointWeight) {
		this.furthestPointWeight = furthestPointWeight;
	}


	public int getSrid() {
		return srid;
	}

	public void setSrid(int srid) {
		this.srid = srid;
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}

	public double getRadius() {
		return radius;
	}

	public float getClosestPointWeight() {
		return closestPointWeight;
	}

	public float getFurthestPointWeight() {
		return furthestPointWeight;
	}

	@Override
	public String toString() {
		return "InputData [lon=" + lon + ", lat=" + lat + ", radius=" + radius + ", closestPointWeight=" + closestPointWeight
				+ ", furthestPointWeight=" + furthestPointWeight + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(closestPointWeight);
		result = prime * result + Float.floatToIntBits(furthestPointWeight);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof InputData))
			return false;
		InputData other = (InputData) obj;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		if (Float.floatToIntBits(closestPointWeight) != Float.floatToIntBits(other.closestPointWeight))
			return false;
		if (Float.floatToIntBits(furthestPointWeight) != Float.floatToIntBits(other.furthestPointWeight))
			return false;
		return true;
	}
	
	
	
}
