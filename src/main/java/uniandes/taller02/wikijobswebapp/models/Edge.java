/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.taller02.wikijobswebapp.models;

import java.util.ArrayList;

/**
 *
 * @author teo
 */
public class Edge {
    public final static String RELATIONSHIPS_SEPARATOR= ":::";
    public final static String RELATIONSHIP_SEP= ":";
    
    public final static String BORN_IN_RELATIONSHIP= "born_in";
    public final static String BORN_ON_RELATIONSHIP= "born_on";
    
    public final static String DIED_IN_RELATIONSHIP= "died_in";
    public final static String DIED_ON_RELATIONSHIP= "died_on";
    
    
    private int id;
    private String label;
    private int from;
    private int to;
    
   
    public Edge() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

   

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

   

    
}
