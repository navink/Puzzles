package nov2014;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BoggleSolver {
	TrieST<Integer> trie = new TrieST<Integer>();
	Set<Coord<Integer>> visited = new HashSet<Coord<Integer>>();
	BoggleBoard board;
	int M;
	int N;
	
	private class Coord<Integer> {
		int x;
		int y;
		
		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		for (String word : dictionary) {
			trie.put(word, 1);
		}
	}
	
	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		this.M = board.rows();
		this.N = board.cols();
		this.board = board;
		
		Queue<String> queue = new LinkedList<String>();
		
		for (int i=0; i<M; i++) {
			for (int j=0; j<N; j++) {
				Coord<Integer> coord = new Coord<Integer>(i, j);
				char c = board.getLetter(i, j);
				dfs(queue, coord, String.valueOf(c));
			}
		}
		
		return queue;
	}
	
	private Set<Coord<Integer>> getNeighbors(Coord<Integer> coord) {
		Set<Coord<Integer>> neighbors = new HashSet<Coord<Integer>>();
		int x = coord.x;
		int y = coord.y;
		
		for (int i=-1; i<=1; i++) {
			for (int j=-1; j<=1; j++) {
				int x1 = x+i;
				int y1 = y+j;
				
				if (x1 > -1 && y1 > -1 && x1 < N && y1 < N) {
					Coord<Integer> newCoord = new Coord<Integer>(x1, y1);
					
					if (!coordExists(visited, newCoord))
						neighbors.add(newCoord);
				}
			}
		}
		
		return neighbors;
	}
	
	private boolean coordExists(Set<Coord<Integer>> visited, Coord<Integer> coord) {
		return visited.contains(coord);
	}
	
	private boolean isValidPrefix(String prefix) {
		return (trie.isPrefix(prefix));
	}
	
	private void dfs(Queue<String> queue, Coord coord, String prefix) {
		visited.add(coord);
		
		Set<Coord<Integer>> neighbors = getNeighbors(coord);
		
		for (Coord neighbor : neighbors) {
			char c = board.getLetter(neighbor.x, neighbor.y);			
			prefix += String.valueOf(c);
			
			if (isValidPrefix(prefix)) {
				dfs(queue, neighbor, prefix);				
			}			
		}
	}
	
	// Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		int score = 0;
		
		switch (word.length()) {
			case 0-2:
				score = 0;
				break;
			case 3-4:
				score = 1;
				break;
			case 5:
				score = 2;
				break;
			case 6:
				score = 3;
				break;
			case 7:
				score = 5;
				break;
			default:
				score = 11;
		}
		
		return score;
	}
}
