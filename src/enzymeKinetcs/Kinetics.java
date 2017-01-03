package enzymeKinetcs;

import java.io.IOException;

import enzymeKinetcs.ProcessPicture;

public class Kinetics {
	
	public void TakePictures() throws InterruptedException{
		
		try {
			Runtime.getRuntime().exec("python /home/pi/take_pictures.py");
			Thread.sleep(210000);
		} catch (IOException e) {
			System.err.println("ERROR: Could not take pictures.");
		}
	}

	public static void main(String[] args) {
		
		// variables to add times and memory
//		long totalTime = 0;
//		long totalMemory = 0;
		// program timing
//		for(int count = 0; count < 100; count++){
//			long startTime = System.currentTimeMillis();

			if(args.length != 2){
				System.err.println("ERROR: Incorrect number of arguments.");
				System.exit(-1);
			}
			
			Kinetics k = new Kinetics();
			try {
				k.TakePictures();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("********READING DATA********");
			System.out.println();
			
			// read concentrations
			ReadConcentrationData rd = new ReadConcentrationData();
			rd.ReadSampleConcs(args[0]);
			rd.PrintSampleConcs();
			rd.ReadCalibrationConcs(args[1]);
			rd.PrintCalibrationConcs();
			
			// read min1 data
			ProcessPicture process1 = new ProcessPicture(args[2]);
			process1.ExtractPixels();
			process1.PrintRawData();
			
			// read min2 data
			ProcessPicture process2 = new ProcessPicture(args[3]);
			process2.ExtractPixels();
			process2.PrintRawData();
			
			// read min3 data
			ProcessPicture process3 = new ProcessPicture(args[4]);
			process3.ExtractPixels();
			process3.PrintRawData();
			
			System.out.println("********GETTING CALIBRATION DATA********");
			System.out.println();
			
			// find min1 calibration equation
			Calibrate cb1 = new Calibrate(process1.BG4, process1.BG5, process1.Calibration, rd.CalibrationConcs);
			cb1.AverageData();
			cb1.PrintAveragedData();
			cb1.NormalizeData();
			cb1.PrintNormalizedData();
			cb1.FindSlopeIntercept();
			cb1.PrintSlopeIntercept();
			
			// find min2 calibration equation
			Calibrate cb2 = new Calibrate(process2.BG4, process2.BG5, process2.Calibration, rd.CalibrationConcs);
			cb2.AverageData();
			cb2.PrintAveragedData();
			cb2.NormalizeData();
			cb2.PrintNormalizedData();
			cb2.FindSlopeIntercept();
			cb2.PrintSlopeIntercept();
			
			// find min3 calibration equation
			Calibrate cb3 = new Calibrate(process3.BG4, process3.BG5, process3.Calibration, rd.CalibrationConcs);
			cb3.AverageData();
			cb3.PrintAveragedData();
			cb3.NormalizeData();
			cb3.PrintNormalizedData();
			cb3.FindSlopeIntercept();
			cb3.PrintSlopeIntercept();
			
			System.out.println("********PROCESSING DATA********");
			System.out.println();

			// process min1 data
			ProcessData min1 = new ProcessData(process1.BG1, process1.Sample1, process1.BG2, process1.Sample2, process1.BG3, process1.Sample3, process1.BG4, cb1.SlopeIntercept);
			min1.AverageData();
			min1.PrintAveragedData();
			min1.NormalizeData();
			min1.PrintNormalizedData();
			min1.FindConcentrations();
			min1.PrintConcentrationData();
			
			// process min2 data
			ProcessData min2 = new ProcessData(process2.BG1, process2.Sample1, process2.BG2, process2.Sample2, process2.BG3, process2.Sample3, process2.BG4, cb2.SlopeIntercept);
			min2.AverageData();
			min2.PrintAveragedData();
			min2.NormalizeData();
			min2.PrintNormalizedData();
			min2.FindConcentrations();
			min2.PrintConcentrationData();

			// process min3 data
			ProcessData min3 = new ProcessData(process3.BG1, process3.Sample1, process3.BG2, process3.Sample2, process3.BG3, process3.Sample3, process3.BG4, cb3.SlopeIntercept);
			min3.AverageData();
			min3.PrintAveragedData();
			min3.NormalizeData();
			min3.PrintNormalizedData();
			min3.FindConcentrations();
			min3.PrintConcentrationData();

			System.out.println("********COMBINING DATA********");
			System.out.println();
			
			FindSlopes fs = new FindSlopes(min1.ColConcs1, min1.ColConcs2, min1.ColConcs3, min2.ColConcs1, min2.ColConcs2, min2.ColConcs3, min3.ColConcs1, min3.ColConcs2, min3.ColConcs3);
			fs.FindRates();
			fs.PrintRates();
			fs.FindRatesAvg();
			fs.PrintAverageRates();
			fs.FindRatesStdDev();
			fs.PrintStdDevRates();
			
			FindVmaxKm fvk = new FindVmaxKm(fs.AvgRates, rd.SampleConcs);
			fvk.FindValues();
			fvk.PrintVmaxKm();

//			long endTime = System.currentTimeMillis();
//			Runtime runtime = Runtime.getRuntime();
//			runtime.gc();
//			totalTime += endTime - startTime;
//			totalMemory += runtime.totalMemory() - runtime.freeMemory();
//			System.out.println((count+1) + ": " + (endTime - startTime) + " milliseconds");
		
//		}
		
//		System.out.println("Avg time: " + (totalTime/100) + " ms");
//		System.out.println("Avg memory: " + (totalMemory/100) + " bytes");
	}
}

