package enzymeKinetics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ProcessPicture {
	
	protected BufferedImage image = null;	
	protected String filename;
	protected int ShiftValue;
	protected ArrayList<Double> BG1;
	protected ArrayList<Double> BG2;
	protected ArrayList<Double> BG3;
	protected ArrayList<Double> BG4;
	protected ArrayList<Double> BG5;
	protected ArrayList<Double> Sample1;
	protected ArrayList<Double> Sample2;
	protected ArrayList<Double> Sample3;
	protected ArrayList<Double> Calibration;
	
	public ProcessPicture(String filename, String ColorChannel){
		String channel = ColorChannel;
		if(channel.equals("red")){
			this.ShiftValue = 16;
		}
		else if(channel.equals("green")){
			this.ShiftValue = 8;
		}
		else if(channel.equals("blue")){
			this.ShiftValue = 0;
		}
		this.filename = filename;
	}
	
	public void ExtractPixels(ArrayList<Integer[]> pixels){
		
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e){
			System.err.println("ERROR: Cannot read file.");
		}
		
		BG1 = new ArrayList<Double>();
		BG2 = new ArrayList<Double>();
		BG3 = new ArrayList<Double>();
		BG4 = new ArrayList<Double>();
		BG5 = new ArrayList<Double>();
		Sample1 = new ArrayList<Double>();
		Sample2 = new ArrayList<Double>();
		Sample3 = new ArrayList<Double>();
		Calibration = new ArrayList<Double>();
		int pixelCounter = 0;
		
		for(int row = 0; row < 8; row++){
						
			for(int col = 0; col < 9; col++){
				
				Integer[] xy = pixels.get(pixelCounter);
				int x = xy[0];
				int y = xy[1];
				
				//System.out.print("(" + x + "," + y + ") ");
				int pixel = image.getRGB(x, y);
				int colorVal = (pixel >> this.ShiftValue) & 255;
				double value = Math.abs(colorVal - 255);
				
				switch(col){
					case 0: BG1.add(value); break;
					case 1: Sample1.add(value); break;
					case 2: BG2.add(value); break;
					case 3: Sample2.add(value); break;
					case 4: BG3.add(value); break;
					case 5: Sample3.add(value); break;
					case 6: BG4.add(value); break;
					case 7: Calibration.add(value); break;
					case 8: BG5.add(value); break;
					default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}

				pixelCounter++;
			}
		}
	}
	
	protected void PrintRawData(PrintWriter pw) {

		pw.println("Raw Data");

		for(int row = 0; row < 8; row++){
				pw.print(BG1.get(row) + "\t");
				pw.print(Sample1.get(row) + "\t");
				pw.print(BG2.get(row) + "\t"); 
				pw.print(Sample2.get(row) + "\t"); 
				pw.print(BG3.get(row) + "\t"); 
				pw.print(Sample3.get(row) + "\t"); 
				pw.print(BG4.get(row) + "\t"); 
				pw.print(Calibration.get(row) + "\t"); 
				pw.println(BG5.get(row));
		}

		pw.println();
	}
}

