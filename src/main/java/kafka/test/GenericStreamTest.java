package kafka.test;

import kafka.generic.streams.GenericStream;

import java.lang.reflect.InvocationTargetException;

public class GenericStreamTest {
    public static void  main(String[] args){
        try {
            GenericStream.invoke(GenericStreamTest.class.getMethod("reverse", String.class),"hello, world");

        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void reverse(String input){
        System.out.println(new StringBuilder(input).reverse().toString());
    }

}
