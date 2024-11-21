package org.maxwellhgr.chess_gamefx.chess.pieces;


import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];

        List<Position> positions = queenPositions(this.getPosition(), board);

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


    public static List<Position> queenPositions(Position position, Board board) {
        List<Position> bishopPositions = Bishop.bishopPositions(position, board);
        List<Position> rookPositions = Rook.rookPositions(position, board);

        List<Position> queenPositions = new ArrayList<>();
        queenPositions.addAll(bishopPositions);
        queenPositions.addAll(rookPositions);

        return queenPositions;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
