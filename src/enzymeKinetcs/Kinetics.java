package enzymeKinetcs;

public class Kinetics {

	public static void main(String[] args) {

		// variables to add times and memory
//		long totalTime = 0;
//		long totalMemory = 0;
		// program timing
//		for(int count = 0; count < 100; count++){
//			long startTime = System.currentTimeMillis();

			if(args.length != 5){
				System.err.println("ERROR: Incorrect number of arguments.");
				System.exit(-1);
			}
			
			System.out.println("********READING DATA********");
			System.out.println();
			
			// read concentrations
			ReadData rd = new ReadData();
			rd.ReadSampleConcs(args[0]);
			rd.PrintSampleConcs();
			rd.ReadCalibrationConcs(args[1]);
			rd.PrintCalibrationConcs();
			
			// read min1 data
			ReadData rd1 = new ReadData();
			rd1.ReadFile(args[2]);
			rd1.PrintRawData();
			
			// read min2 data
			ReadData rd2 = new ReadData();
			rd2.ReadFile(args[3]);
			rd2.PrintRawData();
			
			// read min3 data
			ReadData rd3 = new ReadData();
			rd3.ReadFile(args[4]);
			rd3.PrintRawData();
			
			
			System.out.println("********GETTING CALIBRATION DATA********");
			System.out.println();
			
			// find min1 calibration equation
			Calibrate cb1 = new Calibrate(rd1.BG4, rd1.BG5, rd1.Calibration, rd.CalibrationConcs);
			cb1.AverageData();
			cb1.PrintAveragedData();
			cb1.NormalizeData();
			cb1.PrintNormalizedData();
			cb1.FindSlopeIntercept();
			cb1.PrintSlopeIntercept();
			
			// find min2 calibration equation
			Calibrate cb2 = new Calibrate(rd2.BG4, rd2.BG5, rd2.Calibration, rd.CalibrationConcs);
			cb2.AverageData();
			cb2.PrintAveragedData();
			cb2.NormalizeData();
			cb2.PrintNormalizedData();
			cb2.FindSlopeIntercept();
			cb2.PrintSlopeIntercept();
			
			// find min3 calibration equation
			Calibrate cb3 = new Calibrate(rd3.BG4, rd3.BG5, rd3.Calibration, rd.CalibrationConcs);
			cb3.AverageData();
			cb3.PrintAveragedData();
			cb3.NormalizeData();
			cb3.PrintNormalizedData();
			cb3.FindSlopeIntercept();
			cb3.PrintSlopeIntercept();
			
			
			System.out.println("********PROCESSING DATA********");
			System.out.println();

			// process min1 data
			ProcessData min1 = new ProcessData(rd1.BG1, rd1.Sample1, rd1.BG2, rd1.Sample2, rd1.BG3, rd1.Sample3, rd1.BG4, cb1.SlopeIntercept);
			min1.AverageData();
			min1.PrintAveragedData();
			min1.NormalizeData();
			min1.PrintNormalizedData();
			min1.FindConcentrations();
			min1.PrintConcentrationData();
			
			// process min2 data
			ProcessData min2 = new ProcessData(rd2.BG1, rd2.Sample1, rd2.BG2, rd2.Sample2, rd2.BG3, rd2.Sample3, rd2.BG4, cb2.SlopeIntercept);
			min2.AverageData();
			min2.PrintAveragedData();
			min2.NormalizeData();
			min2.PrintNormalizedData();
			min2.FindConcentrations();
			min2.PrintConcentrationData();

			// process min3 data
			ProcessData min3 = new ProcessData(rd3.BG1, rd3.Sample1, rd3.BG2, rd3.Sample2, rd3.BG3, rd3.Sample3, rd3.BG4, cb3.SlopeIntercept);
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
			fvk.LineweaverBurk();
			fvk.PrintVmaxKm();
//			ArrayList<Double> VmaxKm = Combine.FindVmaxKm(RatesAvg, pd.Concentrations);
//			Combine.PrintVmaxKm(VmaxKm);

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

