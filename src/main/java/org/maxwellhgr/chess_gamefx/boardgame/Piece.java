package org.maxwellhgr.chess_gamefx.boardgame;


public class Piece {

    private Position position;

    public Piece() {
        this.position = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
