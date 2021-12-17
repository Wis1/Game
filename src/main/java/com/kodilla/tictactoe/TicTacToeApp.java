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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class TicTacToeApp extends Application {

    private final Image imageBack = new Image("file:src/main/resources/pencil.png");
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
                        if(!endOfTheGame()) {
                            if (tile.isEmpty()) {
                                tile.drawX();
                                checkWin("X");
                                String resultX = checkWin("X");
                                String result0 = checkWin("0");
                                if (resultX == null && result0 == null) {
                                    computerMovement();
                                    checkWin("0");
                                }
                            }else {
                                System.out.println("Full board");
                                showResult(null);
                            }
                        }
                    });
            }
        }
    }


    private String checkWin(String sign) {
         List<Tile> tileListX = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .collect(Collectors.toList());
        List<Integer> columnList = tileListX.stream()
                .map(GridPane::getColumnIndex)
                .collect(Collectors.toList());
        List<Integer> rowList = tileListX.stream()
                .map(GridPane::getRowIndex)
                .collect(Collectors.toList());

        Map<Integer,Integer> rows= new HashMap<>();
        Map<Integer,Integer> columns= new HashMap<>();
        for (Tile tile:tileListX){
            int row= GridPane.getRowIndex(tile);
            int column= GridPane.getColumnIndex(tile);
            if(rows.containsKey(row)){
                rows.put(row, rows.get(row)+1);
            }else{
                rows.put(row, 1);
            }
            if (columns.containsKey(column)){
                columns.put(column,columns.get(column)+1);
            }else{
                columns.put(column, 1);
            }
        }
        System.out.println(rows);
        System.out.println(columns);

        boolean rowsMatch = rows.values().stream()
                .anyMatch(value -> value == 3);

        boolean columnsMatch = columns.values().stream()
                .anyMatch((value -> value == 3));

        if (rowsMatch||columnsMatch){
//            Rectangle wonRectangle= new Rectangle(200,200);
//            wonRectangle.setFill(Color.GREEN);
//            wonRectangle.setStroke(Color.BLACK);
//
//            for(Map.Entry<Integer, Integer>entry:columns.entrySet()) {
//                grid.add(wonRectangle, entry.getKey(), entry.getValue());
//                grid.getChildren().addAll(wonRectangle);
//            }
            showResult(sign);
            return sign;
        }
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
            showResult(sign);
            return sign;
        }
        return null;
    }
    public void fillWonRectangle(){
        Rectangle wonRectangle= new Rectangle(200,200);
        wonRectangle.setFill(Color.GREEN);
        wonRectangle.setStroke(Color.BLACK);
    }
    public void showResult(String text){

        Label resultOfGame= new Label("You Won!");
        Label resultOfGame1= new Label("Computer Won");
        Label resultOfGame2= new Label("End of the game");
        resultOfGame.setAlignment(Pos.TOP_LEFT);
        resultOfGame.setFont(Font.font(36));
        resultOfGame.setTextFill(Color.RED);
        resultOfGame1.setAlignment(Pos.TOP_LEFT);
        resultOfGame1.setFont(Font.font(28));
        resultOfGame1.setTextFill(Color.RED);
        resultOfGame2.setAlignment(Pos.TOP_LEFT);
        resultOfGame2.setFont(Font.font(26));
        resultOfGame2.setTextFill(Color.RED);
        if("X".equals(text)) {
            grid.add(resultOfGame, 1, 8);
            grid.add(resultOfGame2, 1, 9);
        }
        if("0".equals(text)) {
            grid.add(resultOfGame1, 1, 8);
            grid.add(resultOfGame2, 1, 9);
        }
        if (text==null)
            grid.add(resultOfGame2,1,9);
    }
    public boolean endOfTheGame(){
        List<Tile> tileList = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(Tile::isEmpty)
                .collect(Collectors.toList());
        boolean checkFullBoard= checkWin("X") == null && tileList.size() == 0;
        return ("X").equals(checkWin("X")) || ("0").equals(checkWin("0")) || checkFullBoard;
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
