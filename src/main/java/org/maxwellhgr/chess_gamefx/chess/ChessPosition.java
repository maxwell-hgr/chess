package org.maxwellhgr.chess_gamefx.chess;


import org.maxwellhgr.chess_gamefx.boardgame.Position;

public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        this.column = column;
        this.row = row;
    }
    public char getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
