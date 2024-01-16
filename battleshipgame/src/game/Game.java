package game;
import java.util.Scanner;

import utils.Sleep;
import utils.Sound;

public class Game {
    private int turn;
    private Map map;
    private Scanner sc;
    
    public void run(){
        sc = new Scanner(System.in);
        map = new Map();

        //マップの初期化
        map.initialize();
        turn = 1;

        //タイトルの表示
        dispTitle();
        //メインループ
        while( map.getAliveShips() > 0 ){
            //ターン数表示
            System.out.printf("*****[ターン：%d]******\n",turn);
            //////debug/////
            map.disp();
            //////debug/////

            //戦艦ゲームメイン処理
            execBattle();

            turn++;
            
            Sleep.sleep(1500);
        }
        dispGameEnd();
    }

    public void dispTitle(){
        System.out.println("********************");
        System.out.println("      戦艦ゲーム      ");
        System.out.println("********************");
    }
    public void dispGameEnd(){
        System.out.println("********************");
        System.out.println("  戦艦ゲームクリア！");
        System.out.println("********************");
    }

    /**
     * 爆弾の位置を入力する
     * @return
     */
    private Bomb inputBombPos(){
        int bx = 0;
        int by = 0;
        while(true){
            System.out.printf("爆弾のX座標を入力してください\n");
            String xVal = sc.next();
            bx = isCorrectPos(xVal);
            if( bx != -1){
                break;
            }else{
                System.out.printf("1から%dの数値を入力してください\n",map.getMapSize());
            }
        }
        
        while(true){
            System.out.printf("爆弾のY座標を入力してください\n");
            String yVal = sc.next();
            by = isCorrectPos(yVal);
            if( by != -1){
                break;
            }else{
                System.out.printf("1から%dの数値を入力してください\n",map.getMapSize());
            }
        }
        
        return new Bomb( new Position(bx, by) );
    }

    /**
     * 入力された位置をチェックする
     * @param value
     * @return
     */
    private int isCorrectPos(String value){
        int numval = -1;
        try{
            numval = Integer.parseInt(value);
            if( numval <= 0 || numval > map.getMapSize()){
                numval = -1;
            }
        }catch(NumberFormatException e){
            numval = -1;
        }
        return numval;
    }

    /**
     * 戦艦ゲームメイン処理
     */
    private void execBattle(){

        //船の状態を表示
        displayShipState();

        //爆弾の位置を取得
        Bomb bomb = inputBombPos();
        //爆発音を鳴らす
        Sound.sound("explosion.wav");
        Sleep.sleep(1000);

        for( int i=0; i < map.getShipNum(); i++ ){
            Ship ship = map.getShipById(i);
            //爆弾に対するリアクションを取得
            int result = ship.action(bomb);
            //結果を表示
            dispMessageForAction(ship,result);
            //再移動
            if( result == Ship.ACT_HIT1){
                map.moveShip(i);
            }
        }
    }

    /**
     * リアクションに対するメッセージを表示する
     * @param ship
     * @param result
     */
    private void dispMessageForAction(Ship ship,int result){
        switch( result ){
            case Ship.ACT_NOTHING :
                System.out.println(ship.getName()+":何もなし");
                break;
            case Ship.ACT_HIT1 :
                System.out.println(ship.getName()+":命中したけどまだ生きている");
                Sound.sound("damage.wav");
                break;
            case Ship.ACT_HIT2 :
                System.out.println(ship.getName()+":撃沈！");
                Sound.sound("die.wav");
                break;
            case Ship.ACT_NAMITAKA :
                System.out.println(ship.getName()+":波高し");
                break;
            default:
            System.out.println(ship.getName()+":システムエラー");
        }

    }

    /**
     * 船の状態を表示
     */
    public void displayShipState(){
        
        for( int i=0; i < map.getShipNum(); i++ ){
            Ship ship = map.getShipById(i);
            System.out.println(ship.getName()+":"+(ship.isAlive()?"生きている":"撃沈済み"));
        }
    }
}
