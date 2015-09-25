/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.taller02.wikijobswebapp;

import flexjson.JSONSerializer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import uniandes.taller02.wikijobswebapp.models.Edge;
import uniandes.taller02.wikijobswebapp.models.Graph;
import uniandes.taller02.wikijobswebapp.models.Node;

/**
 * REST Web Service
 *
 * @author teo
 */
@Path("WikiJob")
public class WikiJobResource {

    @Context
    private UriInfo context;
    
    private JSONSerializer serializer;

    /**
     * Creates a new instance of WikiJobResource
     */
    public WikiJobResource() {
        serializer= new JSONSerializer();
    }

    /**
     * Retrieves representation of an instance of com.mycompany.wikijobswebapp.WikiJobResource
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("application/json")
    @Path("run")
    public String runJob(@FormParam("countryName") String countryName, @FormParam("fromDate") String fromDate, @FormParam("toDate") String toDate) {
        try {    
            String commandBase= System.getProperty("HADOOP_JOB_SHELL_PATH");
            //"/home/teo/run_remote_hadoop.sh"
            String command= commandBase + " "+ countryName+" "+ fromDate +" "+toDate ;
            System.out.println("Command: " + command);
            ArrayList<Node> nodes= new ArrayList<>();
            ArrayList<Edge> edges= new ArrayList<>();
            Graph g = new Graph();
            g.setTitle("Grafo de personas");  
            
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            String line;
            int node_id=0;
            int relationship_id=0;
            
         
            
            Node country= new Node();
            country.setLabel(countryName);
            country.setKind(Node.COUNTRY_KIND_NODE);
            country.setId(node_id);
            node_id++;
            nodes.add(country);
            
            while ((line = bri.readLine()) != null) {
                if(line.contains("\t")){
                    // This line contains mapred results!!!
                    Node node= new Node();
                    String line_vals[]= line.split("\t");
                    node.setId(node_id);
                    node.setLabel(line_vals[0]);                    
                    node.setKind(Node.PERSON_KIND_NODE);
                    node.setPageURL("https://en.wikipedia.org/wiki/" + line_vals[0].replaceAll("\\s+", "_"));
                    
                    nodes.add(node);
                    node_id++;
                    
                    String edge_part= line_vals[1];
                    System.out.println("Edge: " + edge_part);
                    String relationships[] = edge_part.split(Edge.RELATIONSHIPS_SEPARATOR);
                    HashMap<String, String> relsMap= new HashMap<>();
                    
                    for (int i = 0; i < relationships.length; i++) {
                        String relationship = relationships[i];
                        String rel_vals[]= relationship.split(Edge.RELATIONSHIP_SEP);
                        String rel_key= rel_vals[0];
                        String rel_val= rel_vals[1];
                        relsMap.put(rel_key, rel_val);
                    }
                    if(relsMap.containsKey(Edge.BORN_IN_RELATIONSHIP)){
                        Edge edge= new Edge();
                        edge.setId(relationship_id);
                        edge.setFrom(node.getId());
                        edge.setTo(country.getId());
                        edge.setLabel(Edge.BORN_IN_RELATIONSHIP);
                        if (relsMap.containsKey(Edge.BORN_ON_RELATIONSHIP)){
                            edge.setLabel(Edge.BORN_ON_RELATIONSHIP+ Edge.RELATIONSHIP_SEP + relsMap.get(Edge.BORN_ON_RELATIONSHIP));
                        }
                        relationship_id++;
                        edges.add(edge);
                    }
                    
                    if(relsMap.containsKey(Edge.DIED_IN_RELATIONSHIP)){
                        Edge edge= new Edge();
                        edge.setId(relationship_id);
                        edge.setFrom(node.getId());
                        edge.setTo(country.getId());
                        edge.setLabel(Edge.DIED_IN_RELATIONSHIP);
                        if (relsMap.containsKey(Edge.DIED_ON_RELATIONSHIP)){
                            edge.setLabel(Edge.DIED_ON_RELATIONSHIP+ Edge.RELATIONSHIP_SEP + relsMap.get(Edge.DIED_ON_RELATIONSHIP));
                        }
                        relationship_id++;
                        edges.add(edge);
                    }
                }
            }
            bri.close();
            
            
            while ((line = bre.readLine()) != null) {
                System.out.println(line);
            }
            bre.close();
            p.waitFor();

                      
            g.setNodes(nodes);
            g.setEdges(edges);
            
            serializer.include("nodes");
            serializer.include("edges");
            serializer.exclude("class");
            serializer.exclude("nodes.class");
            serializer.exclude("edges.class");
            
            return serializer.serialize(g);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    /**
     * PUT method for updating or creating an instance of WikiJobResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
