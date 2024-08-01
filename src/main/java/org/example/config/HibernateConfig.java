package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.example.entity.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateConfig {
    public static EntityManagerFactory getEntityManagerFactory(){
        Properties properties= new Properties();
        properties.put(Environment.DRIVER,"org.postgresql.Driver");
        properties.put(Environment.URL,"jdbc:postgresql://localhost:5432/sql");
        properties.put(Environment.USER,"postgres");
        properties.put(Environment.PASS,"1234");
        properties.put(Environment.HBM2DDL_AUTO,"update");
        properties.put(Environment.DIALECT,"org.hibernate.dialect.PostgreSQLDialect");
//        properties.put(Environment.SHOW_SQL,"true");
//        properties.put(Environment.FORMAT_SQL,"true");
        Configuration configuration= new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Agency.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(House.class);
        configuration.addAnnotatedClass(Owner.class);
        configuration.addAnnotatedClass(Rent_Info.class);
        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);

    }


}
