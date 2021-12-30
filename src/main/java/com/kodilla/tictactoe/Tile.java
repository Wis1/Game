package com.kodilla.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
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
        text.setFill(Color.BLUE);
        text.setText("0");

    }
    public void changeColourText(){
        text.setFill(Color.RED);
    }

}
