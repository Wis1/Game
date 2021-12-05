package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class TicTacToeApp extends Application {

    private Image imageback = new Image("file:src/main/resources/pencil.png");
    private boolean turnX=true;
    private int counter=0;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Button button= new Button();
        button.setText("New Game");

        GridPane grid = new GridPane();
        grid.setBackground(background);
        grid.getChildren().add(button);

        Scene scene = new Scene(grid, 600, 800, Color.BLACK);
        for(int i=1; i<4;i++){
            for(int j=0; j<3; j++){
                Tile tile= new Tile();
                tile.setTranslateX(j*200);
                tile.setTranslateY(i*200);
                grid.getChildren().add(tile);

            }
        }
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnMouseClicked(event->{
            primaryStage.close();
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }


    public class Tile extends StackPane{
        private Text text= new Text();

        public Tile(){

            Rectangle border= new Rectangle(200,200);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);


            setOnMouseClicked(event-> {
                        if (!turnX)
                            return;
                        else {
                            drawX();
                            turnX = false;
                            counter++;
                            computerMovement();

                        }
                    });
            getChildren().addAll(border,text);
        }

        private void drawX(){
            text.setText("X");
        }
        private void draw0(){
            text.setText("0");
        }
        private void computerMovement(){
            if(!turnX) {
                System.out.println("Now computer move.");
                turnX=true;
                if(counter==5)
                    System.exit(0);
            }

        }
    }
}
