package Claculater;

public class spillException extends Exception{
    public spillException(){
    }
    //构造器
    public spillException(String device){
        super (device);
    }
}
