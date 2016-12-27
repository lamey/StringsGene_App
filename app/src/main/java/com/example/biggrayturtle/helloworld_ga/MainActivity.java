package com.example.biggrayturtle.helloworld_ga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

/** John Tran
 *  Main class. Intializes population and serves as a driver class
 *  TODO: Make this program more efficient to where it can deal with more chars quicker
 */
public class MainActivity extends AppCompatActivity {

    private static String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Creates initial population every time onClick is called from activity_main.xml
     * and drives "evolution" and prints the most fit String.
     * @param v action variable from onClick.
     * @throws InterruptedException
     */
    public void createEvolution(View v) throws InterruptedException{
        TextView textView = (TextView) findViewById(R.id.text);
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
            textView.append("Gen " + i + ": " + best.getGene() + "\n");
            population.evolve();
            best = population.getPopulation()[0];
            //Thread.sleep(70);
        }

        textView.append("Gen " + i + ": " + best.getGene());
    }

    /**
     * Sets target gene
     * @param editText is from user input
     */
    public void setTarget(String editText) {
        target = editText;
    }

    /**
     * returns target gene
     * @return target is the target gene
     */
    public String getTarget() {
        return target;
    }
}
