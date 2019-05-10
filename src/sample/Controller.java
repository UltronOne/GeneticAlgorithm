package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controller {

    public Text txtBestPhrase;
    public Text txtTotalGenerations;
    public Text txtaverageFitness;
    public Text txttotalpopulation;
    public Text txtMutationRate;
    public Text txtall;
    String target;
    int popmax;
    float mutationRate;
    Population population;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    public Controller()throws Exception{

    }

    Thread one = new Thread() {
        public void run() {
            try {
                while(true) {
                    long millis = System.currentTimeMillis();
                    try {
                        draw();
                    }catch (Exception e){

                    }

                    Thread.sleep(10);
                }//While
            } catch(InterruptedException v) {
                System.out.println(v);
            }
        }
    };

     public void draw()throws Exception{

// Generate mating pool
         population.naturalSelection();
         //Create next generation
         population.generate();
         // Calculate fitness
         population.calcFitness();
         infoZeichnen();

         // If we found the target phrase, stop
         if (population.finished()) {
            one.stop();
         }

     }

     private void infoZeichnen(){

         String answer = population.getBest();
         System.out.println(answer);
         javafx.application.Platform.runLater( () -> txtBestPhrase.setText(answer));
         javafx.application.Platform.runLater( () -> txtTotalGenerations.setText("total generations: " + population.getGenerations()));
         javafx.application.Platform.runLater( () -> txtaverageFitness.setText("average fitness: " + population.getAverageFitness()));
         javafx.application.Platform.runLater( () -> txttotalpopulation.setText("total populationation: " + popmax));
         javafx.application.Platform.runLater( () -> txtMutationRate.setText("mutation rate: " + (int)(mutationRate * 100) + "%"));

         /*
         textFont(f);
         textAlign(LEFT);
         fill(0);


         textSize(16);
         text("Best phrase:",20,30);
         textSize(32);
         text(answer, 20, 75);

         textSize(12);
         text("total generations: " + population.getGenerations(), 20, 140);
         text("average fitness: " + nf(population.getAverageFitness(), 0, 2), 20, 155);
         text("total populationation: " + popmax, 20, 170);
         text("mutation rate: " + int(mutationRate * 100) + "%", 20, 185);

         textSize(10);
         text("All phrases:\n" + population.allPhrases(), 450, 10);
         */
     }


    @FXML
    void initialize() throws Exception{
        target = "To be or not to be.";
        popmax = 150;
        mutationRate = (float)0.01;

        // Create a populationation with a target phrase, mutation rate, and populationation max
        population = new Population(target, mutationRate, popmax);


    }



    public void btnClicked(ActionEvent actionEvent) throws Exception{
        one.start();
    }
}
