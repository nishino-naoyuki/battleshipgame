import java.util.Random;

public class Map {
    private Ship[] ships;
    private int mapSize;
    private int shipNum;

    public int getMapSize() {
        return mapSize;
    }

    public int getShipNum() {
        return shipNum;
    }

    private static final int DEFAULT_MAP_SIZ = 5;
    private static final int DEFAULT_SHIP_NUM = 3;
    public Map(){
        this(DEFAULT_MAP_SIZ,DEFAULT_SHIP_NUM);
    }

    public Map(int mapSize,int shipNum){
        this.shipNum = shipNum;
        this.mapSize = mapSize;
        ships = new Ship[shipNum];
    }

    /**
     * マップの初期化
     */
    public void initialize(){
        Random rand = new Random();

        for( int i=0; i < shipNum; i++ ){
            Position pos = null;
            do{
                pos = new Position(rand.nextInt(mapSize)+1, rand.nextInt(mapSize)+1);
            }while( isDuplicatePosition(pos) );
    
            ships[i] = new Ship("第"+(i+1)+"号丸",pos,i);
        }
    }

    /**
     * 指定した位置に既に船が居るかをチェックする
     * @param pos
     * @return
     */
    public boolean isDuplicatePosition(Position pos){
        boolean isDuplicate = false;
        for( Ship ship : ships){
            if( ship != null && 
                ship.isAlive() &&
                ship.getPos().equals(pos)){
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    /**
     * 生き残っている船が何艘居るかを返す
     * @return
     */
    public int getAliveShips(){
        int num = 0;
        for( Ship ship : ships){
            if( ship != null && ship.isAlive()){
                num++;
            }
        }
        return num;
    }

    /**
     * 船番号を指定してShipオブジェクトを取得する
     * @param id
     * @return
     */
    public Ship getShipById(int id){
        Ship ship = null;
        if( id < shipNum ){
            ship = ships[id];
        }
        return ship;
    }

    /**
     * 船を移動する
     * @param id
     */
    public void moveShip(int id){
        Ship ship = null;
        if( id < 0 || id >= shipNum ){
            return;
        }
        ship = ships[id];
        Random rand = new Random();
        Position pos = null;
        do{
            pos = new Position(rand.nextInt(mapSize)+1, rand.nextInt(mapSize)+1);
        }while( isDuplicatePosition(pos) );

        ship.move(pos);
    }

    /**
     * DEGUG用：船の位置を表示する
     */
    public void disp(){
        for(int i = 0; i < mapSize+1; i++){
            for(int j=0; j < mapSize+1; j++){
                if( i == 0 && j== 0){
                    System.out.printf("   ",j);
                }else if( i == 0){
                    System.out.printf("[%d]",j);
                }else if(j==0){
                    System.out.printf("[%d]",i);
                }else{
                    int shipId = -1;
                    for(Ship ship : ships){
                        if( ship != null &&
                            ship.isAlive() &&
                            ship.getPos().equals(new Position(j, i))
                        ){
                            shipId = ship.getId();
                            break;
                        }
                    }
                    System.out.print( (shipId != -1 ?"("+(shipId+1)+")":"^^^") );
                }
            }
            System.out.println();
        }
    }
}
