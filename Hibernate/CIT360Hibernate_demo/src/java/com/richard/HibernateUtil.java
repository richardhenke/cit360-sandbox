/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.richard;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author richard
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    static {
        try {
//            Configuration configuration = new Configuration().configure();
//            serviceRegistry = (ServiceRegistry) new ServiceRegistryBuilder().applySettings(configuration.getProperties());
            Configuration configuration=new Configuration()
                    .configure(); // configures settings from hibernate.cfg.xml
            
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            
            // If you miss the below line then it will complaing about a missing dialect setting
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            
            serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception.
            System.out.println("Well we have a problem: <><><><><><><><><><><><><><><><><><><> There ->: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
