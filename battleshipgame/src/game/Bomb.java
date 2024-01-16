package game;
public class Bomb {
    private Position pos;
    private int pow;

    public Bomb(Position pos){
        this.pos = pos.copy();
        pow = 1;
    }

    public Position getPos() {
        return pos;
    }

    public int getPow() {
        return pow;
    }

}
