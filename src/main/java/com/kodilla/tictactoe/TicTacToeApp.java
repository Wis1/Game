package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class TicTacToeApp extends Application {

    private final Image imageBack = new Image("file:src/main/resources/pencil.png");
    private int result=0;
    public static void main(String[] args) {
        launch(args);
    }

    GridPane grid = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Button button = new Button();
        button.setText("New Game");
        grid.setAlignment(Pos.CENTER);

        grid.setBackground(background);
        grid.add(button, 2, 8);
        Scene scene = new Scene(grid, 600, 700, Color.BLACK);
        makeBoard();
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(event -> {
            grid.getChildren().removeAll(grid.getChildren());
            grid.getChildren().add(button);
            makeBoard();
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private void computerMovement() {
        List<Tile> tiles = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(Tile::isEmpty)
                .collect(Collectors.toList());
        if(!tiles.isEmpty()){
            Random generateTile = new Random();
            int i = generateTile.nextInt(tiles.size());
            tiles.get(i).draw0();
        }
    }

    private void makeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                grid.add(tile, i, j);
                tile.setOnMouseClicked(e -> {
                    System.out.println("mouse clicked");
                    if (tile.isEmpty()&&!checkWin()) {
                        tile.drawX();
                        if (!checkWin()){
                            computerMovement();
                            checkWin();
                        }
                        else {
                            showResult();
                            System.out.println("A draw or someone won");
                        }
                    }
                });
            }
        }
    }

    private boolean checkWin() {
        List<Tile> tileListX = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals("X"))
                .collect(Collectors.toList());
        List<Integer> columnList = tileListX.stream()
                .map(GridPane::getColumnIndex)
                .collect(Collectors.toList());
        List<Integer> rowList = tileListX.stream()
                .map(GridPane::getRowIndex)
                .collect(Collectors.toList());

        List<Tile> tileList0 = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals("0"))
                .collect(Collectors.toList());
        List<Integer> columnList0 = tileList0.stream()
                .map(GridPane::getColumnIndex)
                .collect(Collectors.toList());
        List<Integer> rowList0 = tileList0.stream()
                .map(GridPane::getRowIndex)
                .collect(Collectors.toList());

        boolean firstField=false, secondField=false, thirdField=false;

        for(int z=0; z<columnList.size();z++){
            if (columnList.get(z)==0&&rowList.get(z)==0)
                firstField =true;
            if (columnList.get(z)==1&&rowList.get(z)==1)
                secondField=true;
            if (columnList.get(z)==2&&rowList.get(z)==2)
                thirdField=true;
        }
        boolean fourthField= false, fifthField= false;
        for(int m=0; m<columnList.size();m++){
            if (columnList.get(m)==0&&rowList.get(m)==2)
                fourthField =true;
            if (columnList.get(m)==2&&rowList.get(m)==0)
                fifthField=true;
        }
        if((firstField&&secondField&&thirdField)||(secondField&&fourthField&&fifthField)) {
            result = 1;
            return true;
        }
        boolean firstField0=false, secondField0=false, thirdField0=false;
        for(int e=0; e<columnList0.size();e++){
            if (columnList0.get(e)==0&&rowList0.get(e)==0)
                firstField0 =true;
            if (columnList0.get(e)==1&&rowList0.get(e)==1)
                secondField0=true;
            if (columnList0.get(e)==2&&rowList0.get(e)==2)
                thirdField0=true;
        }

        boolean fourthField0=false,fifthField0=false;
        for(int f=0; f<columnList0.size();f++){
            if (columnList0.get(f)==0&&rowList0.get(f)==2)
                fourthField0 =true;
            if (columnList0.get(f)==2&&rowList0.get(f)==0)
                fifthField0=true;
        }
        if((firstField0&&secondField0&&thirdField0)||(fourthField0&&fifthField0&&secondField0)) {
            result = 2;
            showResult();
            return true;
        }

        int number0 = 0,number1=1, number2 = 2;
        int counter0 = 0, counter1=0, counter2=0;
        int counterRow0=0, counterRow1=0, counterRow2=0;

        for (int i = 0; i < columnList.size(); i++) {
                if (columnList.get(i) == number0)
                    counter0++;
                if (columnList.get(i)==number1)
                    counter1++;
                if (columnList.get(i)==number2)
                    counter2++;
                if (rowList.get(i)==number0)
                    counterRow0++;
                if (rowList.get(i)==number1)
                    counterRow1++;
                if (rowList.get(i)==number2)
                    counterRow2++;
        }
        int counterColumn0 = 0, counterColumn1=0, counterColumn2=0;
        int counterRow00=0, counterRow01=0, counterRow02=0;

        for(int k=0;k<columnList0.size();k++){
                if (columnList0.get(k) == number0)
                    counterColumn0++;
                if (columnList0.get(k)==number1)
                    counterColumn1++;
                if (columnList0.get(k)==number2)
                    counterColumn2++;
                if (rowList0.get(k)==number0)
                    counterRow00++;
                if (rowList0.get(k)==number1)
                    counterRow01++;
                if (rowList0.get(k)==number2)
                    counterRow02++;
        }
        if (counter0==3||counter1==3||counter2==3||counterRow0==3||counterRow1==3||counterRow2==3){
            result=1;
            System.out.println("You won");
            return true;
        } if (counterColumn0==3||counterColumn1==3||counterColumn2==3||counterRow00==3||counterRow01==3||counterRow02==3){
            System.out.println("Computer won");
            result=2;
            showResult();
            return true;
        }
        else {
            if (tileListX.size()==5) {
                result = 0;
                showResult();
            }
            return false;
        }
    }
    public void showResult(){
        Label resultOfGame= new Label("You Won!");
        Label resultOfGame1= new Label("Computer Won");
        Label resultOfGame2= new Label("Tie");
        resultOfGame.setAlignment(Pos.TOP_LEFT);
        resultOfGame.setFont(Font.font(36));
        resultOfGame.setTextFill(Color.RED);
        resultOfGame1.setAlignment(Pos.TOP_LEFT);
        resultOfGame1.setFont(Font.font(28));
        resultOfGame1.setTextFill(Color.RED);
        resultOfGame2.setAlignment(Pos.TOP_LEFT);
        resultOfGame2.setFont(Font.font(36));
        resultOfGame2.setTextFill(Color.RED);
        if(result==1)
            grid.add(resultOfGame,1,8);
        if(result==2)
            grid.add(resultOfGame1,1,8);
        if (result==0)
            grid.add(resultOfGame2,1,8);
    }
}


class Tile extends StackPane{
        public Text text= new Text();

        public Tile(){

            Rectangle border= new Rectangle(200,200);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border,text);
        }

        public boolean isEmpty() {
            return text.getText()==null||text.getText().equals("");
        }
        public String getText(){
            return text.getText();
        }

        public void drawX(){
            text.setText("X");
        }
        public void draw0(){
            text.setText("0");
        }
    }
