package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskProgress extends Application {

    private Label lblProgress;

    private ProgressIndicator progInd;

    private Button btnStart;
    private Button btnCancel;


    @Override
    public void init(){

        // Instantiate components

        lblProgress = new Label("Progress");

        progInd = new ProgressIndicator();
        progInd.setMinWidth(75);
        progInd.setMinHeight(75);
        progInd.setProgress(0); // Start with a zero value of the progress indicator
        progInd.progressProperty().bind(myTask.progressProperty()); // this is very important - it binds the
        // progress bar to the task - see myTask below


        btnStart = new Button("Start Task");
        btnStart.setPrefWidth(100);
        btnStart.setOnAction(actionEvent -> startTask());

        btnCancel = new Button("Cancel Task");
        btnCancel.setPrefWidth(100);
        btnCancel.setOnAction(actionEvent -> cancelTask());

    }// end of init



    Task<Void> myTask = new Task<>() {


        @Override
        protected Void call() throws Exception {

            final int maximum = 200000000;

            for (int x = 0; x <= maximum; x++) {

                if (myTask.isCancelled()) {
                    break;
                }
                updateProgress(x, maximum);

            }

            return null;

        }

    };


    private void startTask(){

        Thread myThread = new Thread(myTask);

        myThread.setDaemon(false);

        myThread.start();


    }

    private void cancelTask(){

        myTask.cancel();

        //Unbind the progress indicator.

        progInd.progressProperty().unbind();

        // Reset the progress indicator to zero

        progInd.progressProperty().set(0);



    }




    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage = new Stage();
        primaryStage.setTitle("Task Progress");
        primaryStage.setHeight(350);
        primaryStage.setWidth(175);


        // Create a layout

        HBox hb1 = new HBox();
        hb1.getChildren().add(lblProgress);
        hb1.setAlignment(Pos.CENTER);

        HBox hb2 = new HBox();
        hb2.getChildren().add(progInd);
        hb2.setAlignment(Pos.CENTER);

        HBox hb3 = new HBox();
        hb3.getChildren().add(btnStart);
        hb3.setAlignment(Pos.CENTER);

        HBox hb4 = new HBox();
        hb4.getChildren().add(btnCancel);
        hb4.setAlignment(Pos.CENTER);

        VBox vb = new VBox();
        vb.getChildren().addAll(hb1,hb2,hb3,hb4);
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));

        // Create a scene

        Scene sc = new Scene(vb);

        // Set the scene

        primaryStage.setScene(sc);

        // Show the stage

        primaryStage.show();


    } // end of start


    public static void main (String args[]){
        launch(args);

    } // end of main


    public void exit(){

        Platform.exit();

    }



}