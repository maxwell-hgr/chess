package org.maxwellhgr.chess_gamefx.chess.pieces;


import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(Color color) {
        super(color);
    }


    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];

        List<Position> positions = kingPositions(this.getPosition(), board);

        for (Position p : positions) {
            if (board.piece(p) != null) {
                ChessPiece chessPiece = (ChessPiece) board.piece(p);
                if (chessPiece.getColor() != this.getColor()) {
                    possibleMoves[p.getRow()][p.getColumn()] = true;
                }
                continue;
            }
            possibleMoves[p.getRow()][p.getColumn()] = true;
        }

        return possibleMoves;
    }
    ;

    public static List<Position> kingPositions(Position position, Board board) {
        Position[] positions = getPositions(position);

        List<Position> result = new ArrayList<>();
        for(Position p : positions) {
            if(board.isValidPosition(p)){
                result.add(p);
            }
        }
        return result;
    }

    private static Position[] getPositions(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        int upperRow = row + 1;
        int lowerRow = row - 1;

        int rightColumn = column + 1;
        int leftColumn = column - 1;

        return new Position[]{
                new Position(upperRow, leftColumn),
                new Position(upperRow, column),
                new Position(upperRow, rightColumn),
                new Position(row, leftColumn),
                new Position(row, rightColumn),
                new Position(lowerRow, leftColumn),
                new Position(lowerRow, column),
                new Position(lowerRow, rightColumn)
        };
    }

    @Override
    public String toString() {
        return "K";
    }

}
