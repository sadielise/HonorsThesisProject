package enzymeKinetics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kinetics {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/kinetics";

	static final String USER = "root";
	static final String PASS = "Pr3point!";
	
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
		
		PrintWriter pw = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String outputFile = "/home/pi/Desktop/" + args[1] + " " + df.format(date) + ".txt"; 
			File outFile = new File(outputFile);
			pw = new PrintWriter(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("********TAKING PICTURES********");
		System.out.println();

		TakePictures tp = new TakePictures(args[1]);
		try {
			tp.takePictures();
		} catch (InterruptedException e1) {
			System.err.println("ERROR: Could not take pictures.");
		}
		
		try {
			tp.readPixelValues();
			tp.printPixels(pw);
		} catch (IOException e) {
			System.err.println("ERROR: Could not read pixel values.");
		}
		
		
		System.out.println("********READING DATA********");
		System.out.println();

		// read concentrations
		ReadConfigurationData rd = new ReadConfigurationData(args[0]);
		rd.ReadData();
		rd.PrintConfigurationData(pw);

		// read min1 data
		String picture1 = "/home/pi/Desktop/" + args[1] + "-Image1.jpg";
		ProcessPicture process1 = new ProcessPicture(picture1, rd.ColorChannel);
		process1.ExtractPixels(tp.getPixels());
		process1.PrintRawData(pw);

		// read min2 data
		String picture2 = "/home/pi/Desktop/" + args[1] + "-Image2.jpg";
		ProcessPicture process2 = new ProcessPicture(picture2, rd.ColorChannel);
		process2.ExtractPixels(tp.getPixels());
		process2.PrintRawData(pw);

		// read min3 data
		String picture3 = "/home/pi/Desktop/" + args[1] + "-Image3.jpg";
		ProcessPicture process3 = new ProcessPicture(picture3, rd.ColorChannel);
		process3.ExtractPixels(tp.getPixels());
		process3.PrintRawData(pw);

		System.out.println("********GETTING CALIBRATION DATA********");
		System.out.println();

		// find min1 calibration equation
		Calibrate cb1 = new Calibrate(process1.BG4, process1.BG5, process1.Calibration, rd.CalibrationConcs);
		cb1.AverageData();
		cb1.PrintAveragedData(pw);
		cb1.NormalizeData();
		cb1.PrintNormalizedData(pw);
		cb1.FindSlopeIntercept();
		cb1.PrintSlopeIntercept(pw);

		// find min2 calibration equation
		Calibrate cb2 = new Calibrate(process2.BG4, process2.BG5, process2.Calibration, rd.CalibrationConcs);
		cb2.AverageData();
		cb2.PrintAveragedData(pw);
		cb2.NormalizeData();
		cb2.PrintNormalizedData(pw);
		cb2.FindSlopeIntercept();
		cb2.PrintSlopeIntercept(pw);

		// find min3 calibration equation
		Calibrate cb3 = new Calibrate(process3.BG4, process3.BG5, process3.Calibration, rd.CalibrationConcs);
		cb3.AverageData();
		cb3.PrintAveragedData(pw);
		cb3.NormalizeData();
		cb3.PrintNormalizedData(pw);
		cb3.FindSlopeIntercept();
		cb3.PrintSlopeIntercept(pw);

		System.out.println("********PROCESSING DATA********");
		System.out.println();

		// process min1 data
		ProcessData min1 = new ProcessData(process1.BG1, process1.Sample1, process1.BG2, process1.Sample2, process1.BG3, process1.Sample3, process1.BG4, cb1.SlopeIntercept);
		min1.AverageData();
		min1.PrintAveragedData(pw);
		min1.NormalizeData();
		min1.PrintNormalizedData(pw);
		min1.FindConcentrations();
		min1.PrintConcentrationData(pw);

		// process min2 data
		ProcessData min2 = new ProcessData(process2.BG1, process2.Sample1, process2.BG2, process2.Sample2, process2.BG3, process2.Sample3, process2.BG4, cb2.SlopeIntercept);
		min2.AverageData();
		min2.PrintAveragedData(pw);
		min2.NormalizeData();
		min2.PrintNormalizedData(pw);
		min2.FindConcentrations();
		min2.PrintConcentrationData(pw);

		// process min3 data
		ProcessData min3 = new ProcessData(process3.BG1, process3.Sample1, process3.BG2, process3.Sample2, process3.BG3, process3.Sample3, process3.BG4, cb3.SlopeIntercept);
		min3.AverageData();
		min3.PrintAveragedData(pw);
		min3.NormalizeData();
		min3.PrintNormalizedData(pw);
		min3.FindConcentrations();
		min3.PrintConcentrationData(pw);

		System.out.println("********COMBINING DATA********");
		System.out.println();

		FindSlopes fs = new FindSlopes(min1.ColConcs1, min1.ColConcs2, min1.ColConcs3, min2.ColConcs1, min2.ColConcs2, min2.ColConcs3, min3.ColConcs1, min3.ColConcs2, min3.ColConcs3);
		fs.FindRates();
		fs.PrintRates(pw);
		fs.FindRatesAvg();
		fs.PrintAverageRates(pw);
		fs.FindRatesStdDev();
		fs.PrintStdDevRates(pw);

		FindVmaxKm fvk = new FindVmaxKm(fs.AvgRates, rd.SubstrateConcs);
		fvk.FindValues(pw);
		fvk.PrintVmaxKm(pw);

		/*long endTime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		totalTime += endTime - startTime;
		totalMemory += runtime.totalMemory() - runtime.freeMemory();
				System.out.println((count+1) + ": " + (endTime - startTime) + " milliseconds");

				}

		System.out.println("Avg time: " + (totalTime/100) + " ms");
		System.out.println("Avg memory: " + (totalMemory/100) + " bytes");
		 */

		try {
			Class.forName(JDBC_DRIVER);
			//System.out.println("Driver registered.");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: MySQL Driver not found.");
			System.exit(-1);
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected to MySQL database.");
			Statement stmt = conn.createStatement();
			String timestamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new java.util.Date());
			String insert = "INSERT INTO results VALUES (" + fvk.getVmax() + ", " + fvk.getKm() + ", '" + timestamp + "')";
			stmt.executeUpdate(insert);
			System.out.println("Values and timestamp inserted into database.");
		} catch (SQLException e){
			System.out.println("Error: Could not connect to MySQL database.");
			System.exit(-1);
		}

		if(conn != null){
			//System.out.println("Connection established!");
		} else {
			System.out.println("Error: Connection failed.");
		}

		System.out.println("\nProgram complete.");
		pw.close();

		//		long endTime = System.currentTimeMillis();
		//		Runtime runtime = Runtime.getRuntime();
		//		runtime.gc();
		//		totalTime += endTime - startTime;
		//		totalMemory += runtime.totalMemory() - runtime.freeMemory();
		//		System.out.println((count+1) + ": " + (endTime - startTime) + " milliseconds");

		//	}
	}
}

