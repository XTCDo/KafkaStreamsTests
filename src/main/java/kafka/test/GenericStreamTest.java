package kafka.test;

import kafka.generic.streams.GenericStream;
import planets.Planet;

import java.lang.reflect.InvocationTargetException;

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
            //String newColor = "magenta";

            //System.out.println("changing color to:\t"+newColor);
            //GenericStream.invoke(planet,planet.getClass().getMethod("setColor", String.class),newColor);
            //System.out.println("color is now:\t"+planet.getColor());


            // testing object call with no paramaters passed an error occurs here
            System.out.println("method:\t"+planet.getClass().getMethod("getColor").toString());
            System.out.println("return type:\t"+planet.getClass().getMethod("getColor").getReturnType().toString());

            String color = (String) GenericStream.invoke(planet, planet.getClass().getMethod("getColor"));
            System.out.println("planet color:\t"+color);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reverse(String input){
        System.out.println(new StringBuilder(input).reverse().toString());
    }

}
