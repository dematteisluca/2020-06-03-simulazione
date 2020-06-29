package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;



public class Model {
	
	PremierLeagueDAO dao;
	Graph<Player, DefaultWeightedEdge> graph;
	Map<Integer, Player> idMap;
	
	public Model () {
		
		dao = new PremierLeagueDAO();
		
		}
	
	public void creaGrafo(double x) {
		
		graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, Player>();
		Graphs.addAllVertices(graph, idMap.values());
		
		for(Adiacenza a: dao.getAdiacenze(idMap)) {
			if(graph.containsVertex(a.getP1()) && graph.containsVertex(a.getP2())) {
				if(a.getPeso() < 0) {
					//arco da p2 a p1
					Graphs.addEdgeWithVertices(graph, a.getP2(), a.getP1(), ((double) -1)*a.getPeso());
				} else if(a.getPeso() > 0){
					//arco da p1 a p2
					Graphs.addEdgeWithVertices(graph, a.getP1(), a.getP2(), a.getPeso());
				}
			}
		}
		
	}
	
	public int nVertici() {
		return graph.vertexSet().size();
	}
	
	public int nArchi() {
		return graph.edgeSet().size();
	}
	
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return graph;
	}


}
