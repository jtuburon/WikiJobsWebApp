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
    private String text;
    private int fromId;
    private int toId;
    
   
    public Edge() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    
}
