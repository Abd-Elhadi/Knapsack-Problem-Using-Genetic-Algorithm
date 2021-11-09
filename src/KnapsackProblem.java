import java.util.ArrayList;

// this will store the pairs of weight and value
class Pair {
    private final int weight;
    private final int value;

    public Pair(int _weight, int _value) {
        weight   = _weight;
        value = _value;
    }

    public int weight()   { return weight; }
    public int value() { return value; }
}

// The big boy that solves the Knapsack problem using Genetic Algorithm techniques
public class KnapsackProblem {
	private int ItemsCount;
	ArrayList<Pair> Weights_and_values;
	private double MaxWeight;
	private static int Population;
	private static int MaximumGeneration;
	private static double MutationProbability;
	private ArrayList<String> People;
	private ArrayList<String> SurvivingPeople;

	public KnapsackProblem(int itemsCount, ArrayList<Pair> weights_and_values, double maxWeight, int population, int maximumGeneration, double mutationProbability) {
		ItemsCount = itemsCount;
		Weights_and_values = weights_and_values;
		MaxWeight = maxWeight;
		Population = population;
		MaximumGeneration = maximumGeneration;
		MutationProbability = mutationProbability;

		People = new ArrayList<String>();
		SurvivingPeople = new ArrayList<String>();
	}

	public String findBestIndividual() {
		generatePeople();
		
		//go forward till reach generation limit
		for (int i = 0; i < MaximumGeneration; i++)       
			newGeneration();

		String Answer = getBestChild(People);
		return Answer;
	}

	private void generatePeople() {
		String individual;

		for (int i = 0; i < Population; i++) {
			individual = "";

			for (int j = 0; j < ItemsCount; j++) {
				double chance = Math.random();
				
				//ignore it
//				if (chance == 0.5) {
//					j--;
//					continue;
//				}

				if (chance < 0.5)
					individual += "1";
				else
					individual += "0";
			}
			People.add(individual);
		}
	}

	private double getFitness(String individual) {
		double fitness = 0;
		double weight = 0;

		for (int i = 0; i < individual.length(); i++) {
			if (individual.charAt(i) == '1') {
				weight += Weights_and_values.get(i).weight();
				fitness += Weights_and_values.get(i).value();
			}
		}

		if (weight > MaxWeight)
			return -1;
		else
			return fitness;
	}

	private String crossOver(String individual1, String individual2) {
		String crossedOver = "";

		for (int i = 0; i < individual1.length(); i++) {
			if (Math.random() >= 0.5)
				crossedOver += individual1.charAt(i);
			else
				crossedOver += individual2.charAt(i);
		}

		return crossedOver;
	}

	private String mutate(String candidate) {
		String mutated = "";

		for (int i = 0; i < candidate.length(); i++) {
			if (Math.random() <= MutationProbability) {
				if (candidate.charAt(i) == '0')
					mutated += '1';

				if (candidate.charAt(i) == '1')
					mutated += '0';
			} else {
				mutated += candidate.charAt(i);
			}
		}
		return mutated;
	}

	private String getBestChild(ArrayList<String> Persons) {
		double bestFitness = -1;
		String bestChild = null;

		for (int i = 0; i < Persons.size(); i++) {
			double newFitness = getFitness(Persons.get(i));
			if (newFitness != -1) {
				if (newFitness >= bestFitness) {
					bestChild = Persons.get(i);
					bestFitness = newFitness;
				}
			}
		}
		return bestChild;
	}

	private void newGeneration() {
		//choose 10 person to survive
		// it could be any number though 
		for (int i = 0; i < 10; i++) {
			String individual = getBestChild(People);
			People.remove(individual);
			SurvivingPeople.add(individual);
		}
		
		//kill poor people
		People = null;                              
		People = new ArrayList<String>();
		
		// get two good individual randomly, apply crossOver on them and then apply mutate operator on their result
		for (int i = 1; i < Population; i++) {
			String firstIndividual = getOneGoodIndividualRandomly(SurvivingPeople);
			String secondIndividual = getOneGoodIndividualRandomly(SurvivingPeople);

			People.add(mutate(crossOver(firstIndividual, secondIndividual)));
		}
		
		//move Survivors to people
		for (int i = 0; i < 5; i++) {
			String individual = SurvivingPeople.get(i);
			People.add(individual);
		}
		
		//reset SurvivorsList for next generation
		SurvivingPeople = null;                      
		SurvivingPeople = new ArrayList<String>();
	}

	private String getOneGoodIndividualRandomly(ArrayList<String> persons) {
		String individual;
		// % to stay in the range of persons.size
		int index = ((int) (Math.random() * persons.size())) % persons.size();
		individual = persons.get(index);

		return individual;
	}
}