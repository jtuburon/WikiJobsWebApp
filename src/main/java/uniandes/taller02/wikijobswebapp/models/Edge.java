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
    
    public final static String MARRIED_WITH_RELATIONSHIP= "married_with";
    public final static String SON_OR_DAUGHTER_RELATIONSHIP= "son_or_daughter";
    
    private int id;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
