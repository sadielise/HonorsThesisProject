package enzymeKinetics;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Calibrate {

	ArrayList<Double> SlopeIntercept;
	ArrayList<Double> BG4;
	ArrayList<Double> BG5;
	ArrayList<Double> Calibration;
	ArrayList<Double> SampleConcs;
	ArrayList<Double> BGAvg4;
	ArrayList<Double> Norm4;

	public Calibrate(ArrayList<Double> BG4, ArrayList<Double> BG5, ArrayList<Double> Calibration, ArrayList<Double> SampleConcs){

		this.BG4 = BG4;
		this.BG5 = BG5;
		this.Calibration = Calibration;
		this.SampleConcs = SampleConcs;
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


		SimpleRegression sr = new SimpleRegression(true);

		for(int row = 0; row < 8; row++){
			sr.addData(SampleConcs.get(row), Norm4.get(row));
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

	protected void PrintAveragedData(PrintWriter pw) {

		pw.println("Calibration Averaged Data");
		for(int row = 0; row < 8; row++){
			pw.println(BGAvg4.get(row));
		}
		pw.println();
	}

	protected void PrintNormalizedData(PrintWriter pw) {

		pw.println("Calibration Normalized Data");
		for(int row = 0; row < 8; row++){
			pw.println(Norm4.get(row));
		}
		pw.println();
	}

	protected void PrintSlopeIntercept(PrintWriter pw) {

		pw.println("Calibration Slope: " + SlopeIntercept.get(0));
		pw.println("Calibration Intercept: " + SlopeIntercept.get(1));
		pw.println();
	}

}
