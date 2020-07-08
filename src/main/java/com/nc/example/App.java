package com.nc.example;

import com.nc.Reader;
import com.nc.data.FileSettings;
import com.nc.data.InputData;

import ucar.nc2.NetcdfFile;


public class App {

	public static String filePath = "[path to .nc file]";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		NetcdfFile ncfile = null;
		try {
			
			if(args != null && args.length > 0) {
				filePath = args[0];
			} else {
				throw new Exception("Invalid input");
			}
			
			ncfile = NetcdfFile.open(filePath);
			
			FileSettings fileSettings = new FileSettings("lat", "lon", "Band1");
			InputData inputData = new InputData(-122.4096296296715, 37.76450528869417, 7640, 100f, 50f);
			long time = System.currentTimeMillis();
			System.out.println(Reader.processFile(ncfile, fileSettings, inputData));;
			System.out.println("sec taken: " + (System.currentTimeMillis() - time) / 1000f);
		} finally {
			if (ncfile != null) {
				ncfile.close();
			}
		}

	}

}
