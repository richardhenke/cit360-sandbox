/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.richard;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

@ManagedBean
@SessionScoped
public class Data implements Serializable {
    
    private Members m;
    private HibernateUtil helper;
    private Session session; 
    private String name;
    
    public String getName() {
        session = helper.getSessionFactory().openSession();
        session.beginTransaction();
        // get an object with the id of '1' in the Members class in DB
        m = (Members) session.get(Members.class, 8);
        this.name = m.getName();
        return name;
    }
    
    public void addMember() {
        
        m = new Members("Mr. Potato Head");
        session = helper.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(m);
        session.getTransaction().commit();
        session.close();

    }
    
    
}
