package com.nc.data;

import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.ogc.OGCGeometry;
import com.esri.core.geometry.ogc.OGCPoint;

/**
 * 
 * @author SKC
 *
 */
public class TerrainPoint {

	private OGCPoint point;
	private double height;

	public TerrainPoint() {

	}

	public TerrainPoint(OGCPoint point, double height) {
		super();
		this.point = point;
		this.height = height;
	}

	public OGCPoint getPoint() {
		return point;
	}

	public void setPoint(OGCPoint point) {
		this.point = point;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TerrainPoint))
			return false;
		TerrainPoint other = (TerrainPoint) obj;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.asText().equals(other.point.asText()))
			return false;
		return true;
	}

	public static double distance(OGCPoint pointA, OGCPoint pointB) {
		return pointA.distance(pointB);
	}
	
	public double distance(OGCPoint pointA) {
		return distance(this.getPoint(), pointA);
	}

	private static int sign(double x) {
		return (x < 0) ? -1 : (x > 0) ? 1 : 0;
	}
	
	public static OGCPoint getOGCPoint(double x, double y) {
		return (OGCPoint)OGCGeometry.fromText("POINT(" + x + " " + y + ")");
	}
	
	public static OGCPoint toMarcetor(OGCPoint point) {

		double[] lonLat = new double[] { point.X(), point.Y() };

		double D2R = Math.PI / 180,
				// 900913 properties
				A = 6378137.0, MAXEXTENT = 20037508.342789244;
//		A = 6371000.0, MAXEXTENT = 20037508.342789244;

		// compensate longitudes passing the 180th meridian
		// from https://github.com/proj4js/proj4js/blob/master/lib/common/adjust_lon.js
		double adjusted = (Math.abs(lonLat[0]) <= 180) ? lonLat[0] : (lonLat[0] - (sign(lonLat[0]) * 360));
		double[] xy = new double[] { A * adjusted * D2R,
				A * Math.log(Math.tan((Math.PI * 0.25) + (0.5 * lonLat[1] * D2R))) };

		// if xy value is beyond maxextent (e.g. poles), return maxextent
		if (xy[0] > MAXEXTENT)
			xy[0] = MAXEXTENT;
		if (xy[0] < -MAXEXTENT)
			xy[0] = -MAXEXTENT;
		if (xy[1] > MAXEXTENT)
			xy[1] = MAXEXTENT;
		if (xy[1] < -MAXEXTENT)
			xy[1] = -MAXEXTENT;

		return new OGCPoint(new Point(xy[0], xy[1]), SpatialReference.create(3857));
	}
	
	public static OGCPoint toWGS84(OGCPoint point) {
		double R2D = 180 / Math.PI;
	    double A = 6378137.0;
//	    double[] lonLat = new double[] { point.X(), point.Y() };
	    double x =  (point.X() * R2D / A);
	    double y =  ((Math.PI * 0.5) - 2.0 * Math.atan(Math.exp(-point.Y() / A))) * R2D;
	    return new OGCPoint(new Point(x, y), SpatialReference.create(4326));
	}

}
