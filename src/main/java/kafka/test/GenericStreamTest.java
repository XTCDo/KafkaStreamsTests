package kafka.test;

import kafka.generic.streams.GenericStream;
import planets.Planet;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class GenericStreamTest {
    public static void  main(String[] args){

        // testing static invokes
        try {
            GenericStream.staticinvoke( GenericStreamTest.class.getMethod("reverse", String.class),"hello, world");

        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        Planet planet = new Planet("zorpflorp-17","Hogenbeek","fuchsia",0f,-20f,(float) Math.PI);
        try{
            System.out.println("planet color:\t"+planet.getColor());
            // testing if null return does anything, I suspect not
            GenericStream.invoke(planet,planet.getClass().getMethod("setColor", String.class),"magenta");

            // testing object call with no paramaters passed
            String color = Objects.requireNonNull(GenericStream.invoke(planet, planet.getClass().getMethod("getColor"))).toString();
            System.out.println("planet color:\t"+color);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void reverse(String input){
        System.out.println(new StringBuilder(input).reverse().toString());
    }




}
