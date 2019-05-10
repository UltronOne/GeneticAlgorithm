package sample;

import java.util.Random;

public class DNA {

    char[] genes;

    float fitness;

    // Constructor (random DNA)
    DNA(int num) {
        genes = new char[num];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char) getRandomNumberInRange(32,128);  // Pick from range of chars
        }
    }

    // Converts character array to a String
    String getPhrase() {
        return new String(genes);
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    // Fitness function (returns floating point % of "correct" characters)
    void fitness (String target) {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = (float)Math.pow(2,score);
    }

    // Crossover
    DNA crossover(DNA partner) {
        // A new child
        DNA child = new DNA(genes.length);

        int midpoint = (int)(Math.random()*genes.length); // Pick a midpoint

        // Half from one, half from the other
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) child.genes[i] = genes[i];
            else              child.genes[i] = partner.genes[i];
        }
        return child;
    }

    // Based on a mutation probability, picks a new random character
    void mutate(float mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (Math.random()*1 < mutationRate) {
                genes[i] = (char) getRandomNumberInRange(32,128);
            }
        }
    }
}


