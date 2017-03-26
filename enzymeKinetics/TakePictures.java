package enzymeKinetics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TakePictures {

	private ArrayList<Integer[]> pixels = new ArrayList<Integer[]>();
	private String experimentName;
	
	public TakePictures(String experimentName){
		this.experimentName = experimentName;
	}

	public void takePictures() throws InterruptedException{

		try {
			String command = "python /home/pi/Desktop/take_pictures.py" + " " + experimentName;
			Runtime.getRuntime().exec(command);
			Thread.sleep(186000);
		} catch (IOException e) {
			System.err.println("ERROR: Could not take pictures.");
		}
	}

	public void readPixelValues() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("/home/pi/Desktop/pixels.txt"));
		String row;
		while((row = br.readLine()) != null){
			String[] pixelValues = row.split(" ");
			for(String value: pixelValues){
				String[] xyString = value.split(",");
				Integer[] xyInt = new Integer[2];
				xyInt[0] = Integer.valueOf(xyString[0]);
				xyInt[1] = Integer.valueOf(xyString[1]);
				pixels.add(xyInt);
			}
		}
		
		br.close();
	}
	
	public ArrayList<Integer[]> getPixels(){
		return pixels;
	}
	
	public void printPixels(PrintWriter pw){
		
		pw.println("Pixel Values");
		
		int pixelCounter = 0;
		
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 9; col++){
					pw.print(pixels.get(pixelCounter)[0] + "," + pixels.get(pixelCounter)[1] + " ");
					pixelCounter++;
			}
			pw.println();
		}
		pw.println();
	}

}