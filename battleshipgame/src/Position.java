public class Position {
    private int x;
    private int y;

    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    /**
     * オフセットを加味してPositionインスタンスを作成し返す
     * 
    */
    public Position offset(int ox,int oy){
        return new Position(x+ox, y+oy);
    }
    /*
     * インスタンスを新たに作成し複製する
     */
    public Position copy(){
        return new Position(x , y);
    }
    /**
     * 指定された位置が同じかをチェックする
     * @param pos
     * @return
     */
    public boolean equals(Position pos){
        return ( x == pos.getX() && y == pos.getY() );
    }

    /**
     * 指定された位置の十字方向にあるかどうか
     * @param pos
     * @return
     */
    public boolean isThereAllSide(Position pos){
        boolean isThere = false;
        if( 
            this.equals(pos.offset(-1, 0)) ||
            this.equals(pos.offset( 1, 0)) ||
            this.equals(pos.offset( 0,-1)) ||
            this.equals(pos.offset( 0, 1)) 
        ){
            isThere = true;
        }
        return isThere;
    }
}
