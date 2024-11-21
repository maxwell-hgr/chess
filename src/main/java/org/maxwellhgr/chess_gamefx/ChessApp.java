package org.maxwellhgr.chess_gamefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.maxwellhgr.chess_gamefx.gui.ChessBoard;

public class ChessApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        ChessBoard chessBoard = new ChessBoard();
        Scene scene = new Scene(chessBoard, 600, 600);

        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}