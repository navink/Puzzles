package nov2014;

import java.util.LinkedList;
import java.util.Queue;

public class TrieST<Value> {	
	private static final int R = 256;
	private Node root = new Node();
	
	private static class Node {		
		Object value;
		Node[] next = new Node[R];		
	}
	
	public void put(String key, Value value) {
		root = put(root, key, value, 0);
	}
	
	private Node put(Node node, String key, Value value, int d) {
		if (node == null) {
			node = new Node();
		}
		
		if (d == key.length()) {
			node.value = value;
			return node;
		}
		
		char c = key.charAt(d);
		
		node.next[c] = put(node.next[c], key, value, d+1);
		
		return node;
	}
	
	public Value get(String key) {
		Node node = get(root, key, 0);
		
		if (node == null) {
			return null;
		}
		else {
			return (Value) node.value;
		}
	}
	
	private Node get(Node node, String key, int d) {
		if (node == null) {
			return null;
		}
			
		if (d == key.length()) {
			return node;
		}
		
		char c = key.charAt(d);
		
		return get(node.next[c], key, d+1);
	}
	
	public Iterable<String> keys() {
		Queue<String> queue = new LinkedList<String>();
		
		collect(root, "", queue);
		
		return queue;
	}
	
	private void collect(Node node, String prefix, Queue<String> queue) {
		if (node == null)
			return;
		
		if (node.value != null)
			queue.add(prefix);
		
		for (char c=0; c<R; c++) {			
			collect(node.next[c], prefix+c, queue);
		}
	}
	
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> queue = new LinkedList<String>();
		Node node = get(root, prefix, 0);
		
		collect(node, prefix, queue);
		
		return queue;
	}
	
	public boolean isPrefix(String prefix) {
		Node node = get(root, prefix, 0);
		
		if (node == null)
			return false;
		else
			return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
