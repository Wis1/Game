package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;


public class TicTacToeApp extends Application {

    private Image imageback = new Image("file:src/main/resources/pencil.png");
    private boolean turnX=true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setBackground(background);

        Scene scene = new Scene(grid, 600, 600, Color.WHITE);
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                Title title= new Title();
                title.setTranslateX(j*200);
                title.setTranslateY(i*200);
                grid.getChildren().add(title);
            }
        }



        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public class Title extends StackPane{
        private Text text= new Text();
        Rectangle border= new Rectangle(200,200);
        public Title(){


            border.setFill(null);
            border.setStroke(Color.BLACK);
            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event-> {
                        if (!turnX)
                            return;
                        drawX();
                        turnX = false;
                        computerMovement();
                    });


        }

        private void drawX(){
            text.setText("X");
        }
        private void draw0(){
            text.setText("0");
        }
        private void computerMovement(){
            if(!turnX) {

                setTranslateX(0);
                setTranslateY(200);
                draw0();
            }
            turnX=true;
        }
    }
}
