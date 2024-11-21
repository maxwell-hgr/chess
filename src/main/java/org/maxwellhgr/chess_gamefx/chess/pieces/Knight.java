package org.maxwellhgr.chess_gamefx.chess.pieces;


import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];

        List<Position> positions = knightPositions(this.getPosition(), board);



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

    public List<Position> knightPositions(Position position, Board board) {
        Position[] positions = getPositions(position);

        List<Position> result = new ArrayList<>();
        for(Position p : positions) {
            if(board.isValidPosition(p)){
                result.add(p);
            }
        }
        return result;
    }

    ;

    public static Position[] getPositions(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        return new Position[]{
                new Position(row - 1, column - 2),
                new Position(row - 2, column - 1),
                new Position(row - 2, column + 1),
                new Position(row - 1, column + 2),
                new Position(row + 1, column - 2),
                new Position(row + 2, column - 1),
                new Position(row + 2, column + 1),
                new Position(row + 1, column + 2)
        };
    }

    @Override
    public String toString() {
        return "N";
    }
}
