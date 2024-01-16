public class Ship {
    private String name;
    private Position pos;
    private int hp;
    private int id;

    public static final int MAX_HP = 3;
    public static final int ACT_NOTHING = 0;
    public static final int ACT_NAMITAKA = 1;
    public static final int ACT_HIT1 = 2;
    public static final int ACT_HIT2 = 3;

    public Ship(String name,Position pos,int id){
        hp = MAX_HP;
        this.name = name;
        this.pos = pos.copy();
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Position getPos() {
        return pos;
    }
    public int getId() {
        return id;
    }

    /**
     * 船が生きているかどうかの判定
     * @return
     */
    public boolean isAlive(){
        return hp > 0;
    }

    /**
     * 船を指定した位置に移動する
     * @param newPos
     */
    public void move(Position newPos){
        pos = newPos.copy();
    }

    /**
     * 爆弾が命中したときの処理
     * @param bomb
     */
    public void hit(Bomb bomb){
        hp = hp - bomb.getPow();
        if( hp < 0 ){
            hp = 0;
        }
    }

    /**
     * 爆弾に対するアクション
     * @param bomb
     * @return
     */
    public int action(Bomb bomb){
        if( !isAlive() ){
            return ACT_NOTHING;
        }
        int result = ACT_NOTHING;
        if( pos.equals(bomb.getPos())){
            //命中
            hit( bomb );
            if( isAlive() ){
                result = ACT_HIT1;
            }else{
                result = ACT_HIT2;
            }
        }else if( pos.isThereAllSide(bomb.getPos())){
            //波高し
            result = ACT_NAMITAKA;
        }
        return result;
    }
}
