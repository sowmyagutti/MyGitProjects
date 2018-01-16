import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class TheaterSeating {

	public static void main(String[] args) {
		/*INSTRUCTIONS
		Input test value format is Theater row size value followed by seating size matrix , orders size followed by list if orders
		Example : 4 6,6 8,2,5,4 4,6,6,4 2,8,8,2 6,6 8 Smith,2 Jones,5 Davis,6 Wilson,100 Jhonson,3 williams,4 Brown,8 Miller,12
        4 is row size 8 is the orders size
		test input values are saved in TheaterSeatingTestInput.txt file in the project directory */

		ArrayList<String> testInput =  readInputTestCasesFile("TheaterSeatingTestInput.txt");		
		for(String testString:testInput)
		{
			System.out.println("********************** Testing start **********************************");
			System.out.println("Running test case for input -- "+ testInput);
			assignSeating(testString);
			System.out.println("********************** Testing Ends **********************************");
			System.out.println("                                                                       ");
		}
	}
	public static void assignSeating(String testString) {
		String[] values = testString.split(" ");
		int rowsize = Integer.valueOf(values[0]);	
		String [] input = new String [rowsize];
		//Taking the seating limit values from the program arguments
		System.out.println("                  ");
		System.out.println("---Seating structure---");
		for(int i=0;i<rowsize;i++)
		{
			input[i]=values[i+1];			
			System.out.println(input[i]);
		}			
		int[][] seating = new int[rowsize][];
		String[] stringArray;
		int[] intArray ;
		//creating two dimensional array for seating size for each row
		for(int k=0; k<input.length; k++)
		{
			stringArray = input[k].split(",");
			intArray = new int[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				String numberAsString = stringArray[i];
				intArray[i] = Integer.parseInt(numberAsString);


			}
			seating [k] = new int[intArray.length];
			seating [k] = intArray;
		}

		Map<String,Integer> orders = new LinkedHashMap<String, Integer>();
		int orderSize = Integer.valueOf(values[rowsize+2]);	
		System.out.println("                  ");
		System.out.println("---Orders----");
		for(int i=rowsize+3 ; i< rowsize+3+orderSize; i++)
		{
			System.out.println(values[i]);
			orders.put(values[i].split(",")[0],Integer.valueOf(values[i].split(",")[1]));
		}	
		System.out.println("                  ");
		System.out.println("----Output----");
		for(String name: orders.keySet()) {			
			int tickets = orders.get(name);		
			boolean closer = true;
			int maxSeating = getMaxValue (seating);
			int totalSeating= getTotal(seating);	
			boolean containsValue= checkForValue(seating, tickets);
			outer: for(int i=0; i<seating.length;i++ ) {
				inner: for(int j=0; j<seating[i].length; j++) {
					if(tickets == seating[i][j] && closer && containsValue) {
						if(i >= Math.round(seating.length/2))
						{ 
							closer=false;
							i=-1;
							continue outer;
						}
						System.out.println(name + " Row " + (i+1) + " Section "+ (j+1));
						seating[i][j] = seating[i][j]-tickets;
						break outer;
					}		
					else if(!closer && tickets == seating[i][j] ) {
						System.out.println(name + " Row " + (i+1) + " Section "+ (j+1));
						seating[i][j] = seating[i][j] - tickets;
						break outer;
					}
					else if(!closer && tickets < seating[i][j] ) {
						System.out.println(name + " Row " + (i+1) + " Section "+ (j+1));
						seating[i][j] = seating[i][j] - tickets;
						break outer;
					}
					else if(closer && tickets < seating[i][j] &&!containsValue) {
						System.out.println(name + " Row " + (i+1) + " Section "+ (j+1));
						seating[i][j] = seating[i][j] - tickets;
						break outer;
					}
					else if( tickets > maxSeating && tickets < totalSeating ) {			
						System.out.println(name + " Call to split party");
						break outer;
					}
					else if(tickets > totalSeating) {
						System.out.println(name + " Sorry, we can't handle your party.");
						break outer;
					}
				} 
			}
		}
	}


	public static int getMaxValue(int[][] numbers) {
		int maxValue = numbers[0][0];
		for (int j = 0; j < numbers.length; j++) {
			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] > maxValue) {
					maxValue = numbers[j][i];
				}
			}
		}
		return maxValue;
	}

	public static int getTotal(int[][] numbers) {
		int total = 0;
		for (int j = 0; j < numbers.length; j++) {
			for (int i = 0; i < numbers[j].length; i++) {         
				total = total+numbers[j][i];                
			}
		}
		return total;
	}
	private static boolean checkForValue(int[][] seating,int val){
		boolean exists = false;
		for(int i = 0; i < seating.length; i++){
			for(int j = 0; j < seating[i].length; j++){
				if(seating[i][j] == val) exists = true;

			}
		}

		return exists;
	}

	private static ArrayList<String> readInputTestCasesFile(String filename){

		BufferedReader br = null;	
		ArrayList<String> testInput = new  ArrayList<String>();
		try {		
			br = new BufferedReader(new FileReader(filename));	
			String line;
			while ((line = br.readLine()) != null) {
				testInput.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return testInput;

	}
}



