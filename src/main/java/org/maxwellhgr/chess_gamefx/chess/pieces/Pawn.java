package org.maxwellhgr.chess_gamefx.chess.pieces;


import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends ChessPiece {

    private boolean firstMove = true;

    public Pawn(Color color) {
        super(color);
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];
        Color color = this.getColor();

        List<Position> pawnPositions = pawnPositions(this.getPosition(), board);
        if(color == Color.WHITE) {
            Position pos1 = pawnPositions.get(0);
            possibleMoves[pos1.getRow()][pos1.getColumn()] = true;
            if(firstMove) {
                Position pos2 = pawnPositions.get(1);
                possibleMoves[pos2.getRow()][pos2.getColumn()] = true;
            }
        }
        if(color == Color.BLACK) {
            Position pos1 = pawnPositions.get(2);
            possibleMoves[pos1.getRow()][pos1.getColumn()] = true;
            if(firstMove) {
                Position pos2 = pawnPositions.get(3);
                possibleMoves[pos2.getRow()][pos2.getColumn()] = true;
            }
        }

        return possibleMoves;
    };

    public static List<Position> pawnPositions(Position position, Board board) {
        Position[] positions = getPositions(position);

        List<Position> result = new ArrayList<>(Arrays.asList(positions));
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
                new Position(row + 1, column),
                new Position(row + 2, column),
                new Position(row - 1, column),
                new Position(row - 2, column)
        };
    }

    @Override
    public String toString() {
        return "P";
    }
}
