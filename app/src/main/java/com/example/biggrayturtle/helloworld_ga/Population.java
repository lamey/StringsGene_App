package com.example.biggrayturtle.helloworld_ga;
import java.util.Arrays;
import java.util.Random;

public class Population {

	private float elitismRatio;
	private float mutationRatio;
	private float crossoverRatio;
	private Chromosome[] popArr;

	private static final int TOURNAMENT_SIZE = 3;

	private static Random rand = new Random();

	public Population(int size, float crossoverRatio, float elitismRatio, float mutationRatio) {
		this.crossoverRatio = crossoverRatio;
		this.elitismRatio = elitismRatio;
		this.mutationRatio = mutationRatio;

		//make initial population with random strings
		this.popArr = new Chromosome[size];
		for (int i = 0; i < size; i++) {
			this.popArr[i] = Chromosome.generateRandom();
		}

		Arrays.sort(this.popArr);
	}

	public void evolve() {
		Chromosome[] buffer = new Chromosome[popArr.length];

		int idx = Math.round(popArr.length * elitismRatio);
		System.arraycopy(popArr, 0, buffer, 0, idx);

		while (idx < buffer.length) {
			//check to see if we should perform a crossover
			if (rand.nextFloat() <= crossoverRatio) {
				//select parents and mate them to get children
				Chromosome[] parents = selectParents();
				Chromosome[] children = parents[0].mate(parents[1]);

				//check if the 1st child should be mutated
				if (rand.nextFloat() <= mutationRatio) {
					buffer[idx++] = children[0].mutate();
				} else {
					buffer[idx++] = children[0];
				}

				//repeat for 2nd child if there's room
				if (idx < buffer.length) {
					if (rand.nextFloat() <= mutationRatio) {
						buffer[idx] = children[1].mutate();
					} else {
						buffer[idx] = children[1];
					}
				}

			} else {
				if (rand.nextFloat() <= mutationRatio) {
					buffer[idx] = popArr[idx].mutate();
				} else {
					buffer[idx] = popArr[idx];
				}
			}

			++idx;
		}
		Arrays.sort(buffer);

		popArr = buffer;
	}

	public Chromosome[] getPopulation() {
		return popArr;
	}

	//choose 2 random parents from the population to crossover
	//using tournament selection
	private Chromosome[] selectParents() {
		Chromosome[] parents = new Chromosome[2];

		for (int i = 0; i < 2; i ++) {
			parents[i] = popArr[rand.nextInt(popArr.length)];
			for (int j = 0; j < TOURNAMENT_SIZE; j++) {
				int idx = rand.nextInt(popArr.length);

				//overrided compareTo for choosing the one w/ best fitness
				if (popArr[idx].compareTo(parents[i]) < 0) {
					parents[i] = popArr[idx];
				}
			}
		}
		return parents;
	}


}
