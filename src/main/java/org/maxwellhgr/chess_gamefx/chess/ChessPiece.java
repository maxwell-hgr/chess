package org.maxwellhgr.chess_gamefx.chess;

import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Piece;
import org.maxwellhgr.chess_gamefx.boardgame.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece extends Piece {

    private final Color color;

    public ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean[][] possibleMoves(Board board);

    public static List<Position> rookAndBishopPositions(int[][] directions, Board board, Position position) {
        List<Position> positions = new ArrayList<>();
        for(int[] direction : directions) {
            int rowIncrement = direction[0];
            int colIncrement = direction[1];

            int row = position.getRow() + rowIncrement;
            int column = position.getColumn() + colIncrement;


            Position targetPosition = new Position(row, column);
            while(board.isValidPosition(targetPosition)) {
                ChessPiece chessPiece = (ChessPiece) board.piece(targetPosition);
                if(chessPiece != null) {
                    positions.add(targetPosition);
                    break;
                }
                positions.add(targetPosition);

                row += rowIncrement;
                column += colIncrement;
                targetPosition = new Position(row, column);

            }
        }
        return positions;
    }
}
