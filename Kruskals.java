import java.util.*;
import java.io.*;

public class Kruskals {

	//lists to keep track of all nodes and paths in the set/graph
	ArrayList<String> l1 = new ArrayList<>();
	ArrayList<Edge> l2 = new ArrayList<>();
	
	//Edge class
	public static class Edge implements Comparable<Edge>{
		
		String firstCity;
		String secondCity;
		int distance;
		
		//constructor
		Edge(String firstCity, String secondCity, int distance){
			
			this.firstCity = firstCity;
			this.secondCity = secondCity;
			this.distance = distance;
			
		}
		//method to compare two edges based on their distances
		@Override
		public int compareTo(Edge e) {
			
			if(distance < e.distance) {
				
				return 0;
				
			} else if(distance > e.distance) {
				
				return 1;
				
			} else {
				
				return -1;
			}
		}
	}
	//method to read input file and create a graph with the data
	public void getData() {
		
		//variables
		int totalVertex = 0;
		BufferedReader read = null;
		String x;
		
		try {
			
			//open the file to read the data
			read = new BufferedReader(new FileReader("/Users/jungpark/eclipse-workspace/project 5/src/assn9_data.csv"));
			
			//read each line of the csv file
			while((x = read.readLine()) != null) {
				
				//split the line to get the data
				String[] fileLine = x.split(",");
				
				//add the nodes to the list
				l1.add(fileLine[0]);
				String f = fileLine[0];
				
				//create an edge and add to other list
				Edge ed = new Edge(f, fileLine[1], Integer.parseInt(fileLine[2]));
				l2.add(ed);
				
				//create an edge for all cities in the file
				for(int i=3; i<fileLine.length; i+=2) {
					
					//add edge to the list
					Edge edg = new Edge(f, fileLine[i], Integer.parseInt(fileLine[i+1]));
					l2.add(edg);

				}
				//the total number of nodes
				totalVertex = totalVertex + 1;
			}
			//file reading is finished
			read.close();
			
			
		} catch (FileNotFoundException e) {
			
			System.out.println("The file could not be opened.");
			
			e.printStackTrace();
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
	}
	//method that uses Kruskal's algorithm to find the minimum spanning tree for the cities 
	public void calculateKruskal() {
		
		//using the priority queue in java
		PriorityQueue<Edge> p = new PriorityQueue<>();
		int edge = 0;
		int distance = 0;
		DisjSets disjSet = new DisjSets(l1.size());
		
		//get the edges from the list 
		int length = l2.size();
		for(int j=0; j<length; j++) {
			
			//add to queue
			p.add(l2.get(j));
		}
		
		//the number of paths is less than the number of nodes - 1
		int vertices = l1.size();
		while(edge < vertices-1) {
			
			//get the edge from the queue
			Edge ed = p.poll();
			
			//if the starting city and second city are not in the same set
			int start = disjSet.find(l1.indexOf(ed.firstCity));
			int end = disjSet.find(l1.indexOf(ed.secondCity));
			
			if(!(start == end)) {
				
				//combine, so that they are in the same set
				disjSet.union(start, end);
				
				//also, calculate the distance between the cities
				distance += ed.distance;
				
				//output the minimum spanning tree to user
				System.out.print("First City: " + ed.firstCity + " -> Second City: " + ed.secondCity + ", Distance between cities is: " + ed.distance + "\n");
				
				edge++;
			}
		}
		//display the total distance for all cities
		System.out.println("The total distance for all the cities is: " + distance);
	}
	//main method
	public static void main(String[] args) {
		
		//create object 
		Kruskals krusk = new Kruskals();
		
		//display the minimum spanning tree to user for the data
		krusk.getData();
		krusk.calculateKruskal();
		
	}

}
