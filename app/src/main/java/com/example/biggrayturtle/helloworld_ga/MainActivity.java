package com.example.biggrayturtle.helloworld_ga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createEvolution(View v) throws InterruptedException{
        TextView textView = (TextView) findViewById(R.id.text);
        TextView textViewBest = (TextView) findViewById(R.id.textBest);
        textView.setText("");

        EditText editText = (EditText) findViewById(R.id.editText);
        setTarget(editText.getText().toString());

        final int populationSize = 2048;

        final int maxGenerations = 16384;

        final float crossoverRatio = 0.8f;

        //ratio of the population that will stay the same
        final float elitismRatio = 0.1f;

        final float mutationRatio = 0.03f;
        //initialize the population
        Population population = new Population(populationSize, crossoverRatio,
                elitismRatio, mutationRatio);

        //evolve the population, stop when maxGenerations is reached or when
        // we find the target
        int i = 0;
        Chromosome best = population.getPopulation()[0];

        while ((i++ <= maxGenerations) && (best.getFitness() != 0)) {
            textViewBest.setText("Most fit: " + best.getGene());
            textView.append("Gen " + i + ": " + best.getGene() + "\n");
            population.evolve();
            best = population.getPopulation()[0];
            //Thread.sleep(70);
        }
        textViewBest.setText("Most fit: " + best.getGene());
        textView.append("Gen " + i + ": " + best.getGene());
    }

    public void setTarget(String editText) {
        target = editText;
    }
    public String getTarget() {
        return target;
    }
}
