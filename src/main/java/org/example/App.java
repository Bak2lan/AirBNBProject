package org.example;

import org.example.config.HibernateConfig;
import org.example.menu.MenuAirBNB;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        System.out.println(HibernateConfig.getEntityManagerFactory());

        MenuAirBNB menuAirBNB= new MenuAirBNB();
        menuAirBNB.menu();
    }

}
