package kafka.test;

import kafka.generic.streams.GenericStream;

import java.lang.reflect.InvocationTargetException;

public class GenericStreamTest {
    public static void  main(String[] args){
        try {
            GenericStream.invoke(GenericStreamTest.class.getMethod("reverse", String.class), new String[]{"hello world"});
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void reverse(String input){
        System.out.println(new StringBuilder(input).reverse().toString());
    }
}
