package utils;

public class Sleep {
    
    public static void sleep(long timeoutMillis){
        try{
            Thread.sleep(timeoutMillis);
        }catch(InterruptedException e){
            System.out.printf("[warn]%s",e.getMessage());
        }
    }
}
