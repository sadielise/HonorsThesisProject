package src.enzymeKinetcs;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Calibrate {

	ArrayList<Double> SlopeIntercept;
	ArrayList<Double> BG4;
	ArrayList<Double> BG5;
	ArrayList<Double> Calibration;
	ArrayList<Double> CalibrationConcs;
	ArrayList<Double> BGAvg4;
	ArrayList<Double> Norm4;

	public Calibrate(ArrayList<Double> BG4, ArrayList<Double> BG5, ArrayList<Double> Calibration, ArrayList<Double> CalibrationConcs){

		this.BG4 = BG4;
		this.BG5 = BG5;
		this.Calibration = Calibration;
		this.CalibrationConcs = CalibrationConcs;
	}

	protected void AverageData(){

		// initialize arrays and variable
		BGAvg4 = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.0000");

		// find BG4 averages
		for(int row = 0; row < 8; row++){
			double temp = 0;
			temp = (BG4.get(row) + BG5.get(row))/2.0;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			BGAvg4.add(temp);
		}
	}

	protected void NormalizeData() {

		// initialize arrays and variables
		Norm4 = new ArrayList<Double>();
		double temp = 0;

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.0000");

		// find normalized version of Calibration
		for(int row = 0; row < 8; row++){
			temp = (Calibration.get(row) - BGAvg4.get(row));
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			Norm4.add(temp);
		}
	}

	protected void FindSlopeIntercept(){

		// initialize array
		SlopeIntercept = new ArrayList<Double>();

		// decimal format
		DecimalFormat dfSlope = new DecimalFormat("0.0");
		DecimalFormat dfIntercept = new DecimalFormat("0.0000");


		SimpleRegression sr = new SimpleRegression();

		for(int row = 0; row < 8; row++){
			sr.addData(CalibrationConcs.get(row), Norm4.get(row));
		}

		double slope = sr.getSlope();
		String strTemp1 = dfSlope.format(slope);
		slope = Double.parseDouble(strTemp1);
		SlopeIntercept.add(slope);

		double intercept = sr.getIntercept();
		String strTemp2 = dfIntercept.format(intercept);
		intercept = Double.parseDouble(strTemp2);
		SlopeIntercept.add(intercept);
	}

	protected void PrintAveragedData() {

		System.out.println("Calibration Averaged Data");
		for(int row = 0; row < 8; row++){
			System.out.println(BGAvg4.get(row));
		}
		System.out.println();
	}

	protected void PrintNormalizedData() {

		System.out.println("Calibration Normalized Data");
		for(int row = 0; row < 8; row++){
			System.out.println(Norm4.get(row));
		}
		System.out.println();
	}

	protected void PrintSlopeIntercept() {

		System.out.println("Calibration Slope: " + SlopeIntercept.get(0));
		System.out.println("Calibration Intercept: " + SlopeIntercept.get(1));
		System.out.println();
	}

}
