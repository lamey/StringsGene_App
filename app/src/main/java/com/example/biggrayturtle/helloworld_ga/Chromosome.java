package com.example.biggrayturtle.helloworld_ga;

import java.util.Random;

/**
 * A class that a single Chromosome that has its fitness and gene aka its random String
 */
public class Chromosome implements Comparable<Chromosome> {

	//to get target gene
	static MainActivity main = new MainActivity();

	private final String gene;
	private final int fitness;

	private static final Random rand = new Random();

	/**
	 * Constructor for each Chromosome, initializing gene and fitness
	 * @param gene random String that is generated from generateRandom()
     */
	public Chromosome(String gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	/**
	 * returns the Chromosome's String
	 * @return gene is this Chromosome's String
     */
	public String getGene() {
		return gene;
	}

	/**
	 * returns the Chromosome's fitness
	 * @return fitness from calculateFitness(String gene)
     */
	public int getFitness() {
		return fitness;
	}

	/**
	 * Calculates fitness with the abs val of the difference
	 * between the gene and the target gene. If the fitness is closer
	 * to 0, it has higher fitness.
	 * @param gene the random String that the Chromosome is assigned with
	 * @return fitness is the fitness score calculated from gene and the target gene
     */
	private int calculateFitness(String gene) {
		int fitness = 0;
		char[] arr = gene.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			fitness += Math.abs(((int)arr[i]) - ((int) main.getTarget().toCharArray()[i]));
		}
		return fitness;
	}

	/**
	 * Chooses random character in the gene to mutate
	 * @return a new gene
     */
	public Chromosome mutate() {
		char[] arr = gene.toCharArray();
		int idx = rand.nextInt(arr.length);
		int delta = (rand.nextInt() % 90) + 32;
		arr[idx] = (char) ((arr[idx] + delta) % 122);

		return new Chromosome(String.valueOf(arr));
	}

	/**
	 * Mates by copying from halves that are create from a randomly chosen pivot point
	 * @param mate the gene that is being mated with this Chromosome's gene
	 * @return two new chromosomes, the children
     */
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

	/**
	 * generates random String, aka the gene
	 * @return a random gene
     */
	public static Chromosome generateRandom() {
		char[] arr = new char[main.getTarget().toCharArray().length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (char)(rand.nextInt(90) + 32);
		}

		return new Chromosome(String.valueOf(arr));
	}

	/**
	 * overrides compareTo to compare fitness scores for tournament selection
	 * @param c The Chromosome that is be compared to this Chromosome
	 * @return -1 if fitness < c.fitness, 1 vice versa, 0 if neither
     */
	public int compareTo(Chromosome c) {
		if (fitness < c.fitness) {
			return -1;
		} else if (fitness > c.fitness) {
			return 1;
		}
		return 0;
	}
}
