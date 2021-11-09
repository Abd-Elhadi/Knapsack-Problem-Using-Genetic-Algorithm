import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	
	public static void PrintAnswer(String Answer, ArrayList<Pair> weights_and_values, int Case) {
		int chosenItemsCount = 0;
		int totalValuePerCase = 0;
		
		// Answer consists of string of zeros and ones
		// 0 means the item is not being selected, whereas 1 means the item is being selected
		// count the number of the chosen items as well as their values
		for (int i = 0; i < Answer.length(); i++) if (Answer.charAt(i) == '1') {
			chosenItemsCount++;
			totalValuePerCase += weights_and_values.get(i).value();
		}
		
		System.out.println("Case# " + Case + ": " +totalValuePerCase);
		System.out.println(chosenItemsCount);
		for (int i = 0; i < Answer.length(); i++) {
			if (Answer.charAt(i) == '1')
				System.out.println(weights_and_values.get(i).weight() + " " + weights_and_values.get(i).value());
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		// Open the file
		FileInputStream fstream = new FileInputStream("input_example.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		int Case = 0;
		while ((strLine = br.readLine()) != null)   {
			int numberOfTastCases = Integer.parseInt(strLine);
			while(numberOfTastCases != 0) {
				ArrayList<Pair> weights_and_values = new ArrayList<>();
				// read the empty line
				strLine = br.readLine();
				
				//read the number of items
				strLine = br.readLine();
				int numberOfItems = Integer.parseInt(strLine);
				
				//read the backpack weight
				strLine = br.readLine();
				int weight = Integer.parseInt(strLine);
				
				//get the pairs of weight and value of each item
				for (int i = 0; i < numberOfItems; i++) {
					strLine = br.readLine();
					String[] weightValuePair = strLine.split(" ");
					int Weight = Integer.parseInt(weightValuePair[0]);
					int Value = Integer.parseInt(weightValuePair[1]);
					Pair pair = new Pair(Weight, Value);
					weights_and_values.add(pair);
				}
								
				KnapsackProblem knapsack = new KnapsackProblem(numberOfItems, weights_and_values, weight, 100 * numberOfItems, 50, 0.01);
				String Answer = knapsack.findBestIndividual();
				PrintAnswer(Answer, weights_and_values, ++Case);
				numberOfTastCases--;
				strLine = br.readLine(); 
			}
		}
		//Close the input stream
		fstream.close();
	}
}
