package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;


public class TicTacToeApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private final Image imageBack = new Image("file:src/main/resources/pencil.png");
    boolean endGame=false;
    int wonComputer=0;
    int wonUser=0;

    GridPane grid = new GridPane();
    Button button = new Button();
    MenuBar menuBar = new MenuBar();
    Label resultOfGame= new Label();
    Label score= new Label();
    VBox vBox = new VBox(menuBar, grid, button, resultOfGame, score);

    @Override
    public void start(Stage primaryStage) throws Exception {

        Menu menu1 = new Menu("Exit the game");
        MenuItem menuItem= new MenuItem("Exit");
        menuItem.setOnAction(event-> exitGame());
        menu1.getItems().add(menuItem);
        menuBar.getMenus().add(menu1);

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        button.setText("New Game");
        button.setOnAction(event -> {
            newGame();
        });

        grid.setAlignment(Pos.CENTER);
        grid.setBackground(background);

        Scene scene = new Scene(vBox, 600, 800, Color.BLACK);
        makeBoard();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void exitGame(){
        System.exit(0);
    }
    private void newGame(){
        grid.getChildren().removeAll(grid.getChildren());
        endGame= false;
        resultOfGame.setText("");
        makeBoard();

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
                            if (tile.isEmpty()&&!endGame) {
                                tile.drawX();
                                String resultX = checkWin("X");
                                String result0 = checkWin("0");
                                if (resultX == null && result0 == null) {
                                    computerMovement();
                                }
                            }
                        endOfTheGame();
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

        Map<Integer, Integer> rows = new HashMap<>();
        Map<Integer, Integer> columns = new HashMap<>();
        int row = 0;
        int column = 0;

        for (Tile tile : tileListX) {
            row = GridPane.getRowIndex(tile);
            column = GridPane.getColumnIndex(tile);
            if (rows.containsKey(row)) {
                rows.put(row, rows.get(row) + 1);
            } else {
                rows.put(row, 1);
            }
            if (columns.containsKey(column)) {
                columns.put(column, columns.get(column) + 1);
            } else {
                columns.put(column, 1);
            }
        }
        Optional<Integer> optionalRow = rows.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .findAny();


        Optional<Integer> optionalColumn = columns.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .findAny();

        if (optionalRow.isPresent()) {
            int rowOfWin = optionalRow.get();
            changeWonRowColour(rowOfWin, sign);
            return sign;
        }
        if (optionalColumn.isPresent()) {
            int columnOfWin = optionalColumn.get();
            changeWonColumnColour(columnOfWin, sign);
            return sign;
        }
        boolean firstField = false, secondField = false, thirdField = false;

        for (int z = 0; z < columnList.size(); z++) {
            if (columnList.get(z) == 0 && rowList.get(z) == 0)
                firstField = true;
            if (columnList.get(z) == 1 && rowList.get(z) == 1)
                secondField = true;
            if (columnList.get(z) == 2 && rowList.get(z) == 2)
                thirdField = true;
        }
        boolean fourthField = false, fifthField = false;
        for (int m = 0; m < columnList.size(); m++) {
            if (columnList.get(m) == 0 && rowList.get(m) == 2)
                fourthField = true;
            if (columnList.get(m) == 2 && rowList.get(m) == 0)
                fifthField = true;
        }
        if (firstField && secondField && thirdField) {
            changeCrossColour(sign);
            return sign;
        }
        if (secondField && fourthField && fifthField){
            changeOppositeCrossColour(sign);
            return sign;
        }

        return null;
    }

    public void changeWonRowColour(int row, String sign){
        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile->GridPane.getRowIndex(tile)==row)
                .forEach(tile->tile.setStyle("-fx-background-color:red"));
    }
    public void changeWonColumnColour(int column, String sign){
        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile->GridPane.getColumnIndex(tile)==column)
                .forEach(tile->tile.setStyle("-fx-background-color:red"));
    }
    public void changeCrossColour(String sign) {
        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile -> GridPane.getRowIndex(tile) == GridPane.getColumnIndex(tile))
                .forEach(tile -> tile.setStyle("-fx-background-color: red"));
    }
    public void changeOppositeCrossColour(String sign) {
        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile -> GridPane.getRowIndex(tile) == 1 && GridPane.getColumnIndex(tile)== 1)
                .forEach(tile -> tile.setStyle("-fx-background-color: red"));

        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile -> GridPane.getRowIndex(tile) ==2&& GridPane.getColumnIndex(tile)==0)
                .forEach(tile -> tile.setStyle("-fx-background-color: red"));

        grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(tile -> !(tile.isEmpty()))
                .filter(tile -> tile.getText().equals(sign))
                .filter(tile -> GridPane.getRowIndex(tile) == 0 && GridPane.getColumnIndex(tile)==2)
                .forEach(tile -> tile.setStyle("-fx-background-color: red"));
    }

    public void showResult(String text){

        resultOfGame.setAlignment(Pos.TOP_LEFT);
        resultOfGame.setFont(Font.font(36));
        resultOfGame.setTextFill(Color.RED);
        score.setAlignment(Pos.TOP_LEFT);
        score.setFont(Font.font(30));
        score.setTextFill(Color.BLUE);

        if("X".equals(text)) {
            resultOfGame.setText("You won!");
            score.setText("You: "+wonUser+ "   Computer: "+ wonComputer);
        }
        if("0".equals(text)) {
           resultOfGame.setText("Computer won");
            score.setText("You: "+wonUser+ "   Computer: "+ wonComputer);
        }
        if (text==null)
            resultOfGame.setText("The end the game");
    }
    public void endOfTheGame(){
        List<Tile> tileList = grid.getChildren().stream()
                .filter(node -> node instanceof Tile)
                .map(node -> (Tile) node)
                .filter(Tile::isEmpty)
                .collect(Collectors.toList());

        if ("X".equals(checkWin("X"))){
            wonUser++;
            showResult("X");
            endGame=true;

        }
        if ("0".equals((checkWin("0")))){
            wonComputer++;
            showResult("0");
            endGame= true;
        }

        if (endGame){
            Label label= new Label("Play again or exit game");
            label.setAlignment(Pos.CENTER);
            Button buttonNewGame= new Button("NEW GAME");
            Button buttonContinueGame= new Button("Continue Game");
            Button buttonExit= new Button("EXIT");

            HBox hBox= new HBox( 20,buttonNewGame,buttonContinueGame, buttonExit);
            hBox.setAlignment(Pos.BASELINE_CENTER);

            VBox vBox1= new VBox(label, hBox);

            Scene scene1= new Scene(vBox1,350,100);
            Stage stage= new Stage();
            buttonContinueGame.setOnAction(event->{
                stage.close();
                newGame();
            });
            buttonNewGame.setOnAction(e->{
                stage.close();
                wonComputer=0;
                wonUser=0;
                score.setText("");
                newGame();
            });
            buttonExit.setOnAction(event -> exitGame());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("End the game");
            stage.setScene(scene1);

            stage.showAndWait();

        }

        if (tileList.size()==0){
            showResult(null);
        }
    }
}

