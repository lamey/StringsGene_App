package com.example.biggrayturtle.helloworld_ga;

import android.support.v7.app.AppCompatActivity;
import java.util.Random;

public class Chromosome extends AppCompatActivity implements Comparable<Chromosome> {

	//to get target gene
	static MainActivity main = new MainActivity();

	private final String gene;
	private final int fitness;

	private static final Random rand = new Random();

	public Chromosome(String gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	public String getGene() {
		return gene;
	}

	public int getFitness() {
		return fitness;
	}

	//calculates fitness with the abs val of the difference
	//between the gene and the target gene. If the fitness is closer
	//to 0, it has higher fitness.
	private int calculateFitness(String gene) {
		int fitness = 0;
		char[] arr = gene.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			fitness += Math.abs(((int)arr[i]) - ((int) main.getTarget().toCharArray()[i]));
		}
		return fitness;
	}

	//change a random charactor in the gene
	public Chromosome mutate() {
		char[] arr = gene.toCharArray();
		int idx = rand.nextInt(arr.length);
		int delta = (rand.nextInt() % 90) + 32;
		arr[idx] = (char) ((arr[idx] + delta) % 122);

		return new Chromosome(String.valueOf(arr));
	}

	//mates by copying from halves that are create from a randomly chosen
	//pivot point
	public Chromosome[] mate(Chromosome mate) {
		char[] gene1 = gene.toCharArray();
		char[] gene2 = mate.gene.toCharArray();

		int pivot = rand.nextInt(gene1.length);

		char[] child1 = new char[gene.length()];
		char[] child2 = new char[gene.length()];

		System.arraycopy(gene1, 0, child1, 0, pivot);
		System.arraycopy(gene2, pivot, child1, pivot, (child1.length - pivot));

		System.arraycopy(gene2, 0, child2, 0, pivot);
		System.arraycopy(gene1, pivot, child2, pivot, (child2.length - pivot));

		return new Chromosome[] {new Chromosome(String.valueOf(child1)),
				new Chromosome(String.valueOf(child2))};
	}

	//makes random string
	public static Chromosome generateRandom() {
		char[] arr = new char[main.getTarget().toCharArray().length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (char)(rand.nextInt(90) + 32);
		}

		return new Chromosome(String.valueOf(arr));
	}

	//overrides compareTo
	public int compareTo(Chromosome c) {
		if (fitness < c.fitness) {
			return -1;
		} else if (fitness > c.fitness) {
			return 1;
		}
		return 0;
	}

}
