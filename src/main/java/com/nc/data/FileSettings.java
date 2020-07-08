package com.nc.data;

/**
 * The class {@code FileSettings} is setup class to set the variable names for
 * Longitude, Latitude and Band
 * 
 * @author Surajit
 * @see Reader#processFile(ucar.nc2.NetcdfFile, FileSettings, InputData)
 *
 */
public class FileSettings {

	private String latName;
	private String lonName;
	private String bandName;

	public FileSettings(String latName, String lonName, String bandName) {
		this.latName = latName;
		this.lonName = lonName;
		this.bandName = bandName;
	}

	public String getLatName() {
		return latName;
	}

	public String getLonName() {
		return lonName;
	}

	public String getBandName() {
		return bandName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bandName == null) ? 0 : bandName.hashCode());
		result = prime * result + ((latName == null) ? 0 : latName.hashCode());
		result = prime * result + ((lonName == null) ? 0 : lonName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FileSettings))
			return false;
		FileSettings other = (FileSettings) obj;
		if (bandName == null) {
			if (other.bandName != null)
				return false;
		} else if (!bandName.equals(other.bandName))
			return false;
		if (latName == null) {
			if (other.latName != null)
				return false;
		} else if (!latName.equals(other.latName))
			return false;
		if (lonName == null) {
			if (other.lonName != null)
				return false;
		} else if (!lonName.equals(other.lonName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileSettings [latName=" + latName + ", lonName=" + lonName + ", bandName=" + bandName + "]";
	}

}
