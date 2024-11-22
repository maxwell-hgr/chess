package org.maxwellhgr.chess_gamefx.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessMatch;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.ChessPosition;

import static org.maxwellhgr.chess_gamefx.gui.util.Alerts.showAlert;

public class ChessBoard extends GridPane {

    private ChessPosition selectedPosition;
    private final ChessMatch match;

    public ChessBoard() {
        match = new ChessMatch();
        drawBoard();
        drawPieces();
        setOnMouseClicked(this::handleClick);
    }

    private void drawBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle square = new Rectangle(75, 75);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.BEIGE);
                } else {
                    square.setFill(Color.BROWN);
                }
                this.add(square, col, row);
            }
        }
    }

    private void drawPieces() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = (ChessPiece) match.getBoard().piece(new Position(row, col));
                if (piece != null) {
                    ImageView pieceImage = new ImageView(getImageForPiece(piece));
                    pieceImage.setFitWidth(75);
                    pieceImage.setFitHeight(75);
                    this.add(pieceImage, col, row);
                }
            }
        }
    }

    private Image getImageForPiece(ChessPiece piece) {
        String color = piece.getColor().toString().toLowerCase();
        String type = piece.getClass().getSimpleName().toLowerCase();
        return new Image("file:src/main/resources/" + color + "_" + type + ".png");
    }

    private void handleClick(MouseEvent event) {
        int col = (int) (event.getX() / 75);
        int row = (int) (event.getY() / 75);

        ChessPosition clickedPosition = new ChessPosition((char) ('a' + col), 8 - row);

        if (selectedPosition == null) {
            ChessPiece piece = match.selectPiece(clickedPosition);
            if (piece != null) {
                selectedPosition = clickedPosition;
                highlightMoves(piece);
            }
        } else {
            match.moveToTarget(clickedPosition, (ChessPiece) match.getBoard().piece(selectedPosition.toPosition()));
            selectedPosition = null;
            refreshBoard();

            if(match.getCurrentPlayer().isInCheck()){
                showAlert("Check!", match.getCurrentPlayer().getColor() + " king is in check!");
            }
        }
    }

    private void resetGame() {
        match.reset();
        refreshBoard();
    }

    private void highlightMoves(ChessPiece piece) {
        boolean[][] possibleMoves = piece.possibleMoves(match.getBoard());
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (possibleMoves[row][col]) {
                    Rectangle highlight = new Rectangle(75, 75, Color.GREENYELLOW);
                    highlight.setOpacity(0.5);
                    this.add(highlight, col, row);
                }
            }
        }
    }

    private void refreshBoard() {
        this.getChildren().clear();
        drawBoard();
        drawPieces();
    }
}
