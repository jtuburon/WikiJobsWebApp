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
public class Node {
    public final static String COUNTRY_KIND_NODE="country";
    public final static String PERSON_KIND_NODE="person";
    
    private int id;
    private String kind;
    private String label;
    private String pageURL;

    public Node() {
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

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    
}
