package com.nc.example;

import com.nc.Reader;
import com.nc.data.FileSettings;
import com.nc.data.InputData;

import ucar.nc2.NetcdfFile;


public class App {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		NetcdfFile ncfile = null;
		String filePath = null;
		try {
			
			if(args != null && args.length > 0) {
				//C:\\Users\\SKC\\Downloads\\terrain_37_38_-123_-122_cut.nc
				filePath = args[0];
			} else {
				throw new Exception("Invalid input");
			}
			
			ncfile = NetcdfFile.open(filePath);
			
			FileSettings fileSettings = new FileSettings("lat", "lon", "Band1");
			InputData inputData = new InputData(-122.4096296296715, 37.76450528869417, 7640, 100f, 50f);
			long time = System.currentTimeMillis();
			System.out.println(Reader.processFile(ncfile, fileSettings, inputData));;
			System.out.println("Taken: " + ((System.currentTimeMillis() - time) / 1000f) + " sec." );
		} finally {
			if (ncfile != null) {
				ncfile.close();
			}
		}

	}

}
