/*
	Huffman Code implementation
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class HuffmanNode {
 
   int data;
   char c;

   HuffmanNode left;
   HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {
   
	public int compare(HuffmanNode x, HuffmanNode y) {
		return x.data - y.data;
	}
}

class HuffmanBuild {

	private HashMap<Character, Integer> hash_map;
	private HashMap<Character, String> new_hash_map; 
	private PriorityQueue<HuffmanNode> q;
	private String filename;

	public HuffmanBuild(String filename) {
		hash_map = new HashMap<Character, Integer>();
		new_hash_map = new HashMap<Character, String>();
		this.filename = filename;
	}

/*
	This function adds the frequency of the chat
*/

	private void makeFrequencyMap(Character c) {
		
		if (hash_map.containsKey(c)) {
         hash_map.put(c, hash_map.get(c) + 1);
      } else {
         hash_map.put(c, 1);
      }
	}

/*
	This function generates PriorityQueue
*/

	private void makePriorityQueue() {
		int size = hash_map.size();
		q = new PriorityQueue<HuffmanNode>(size, new MyComparator());

		for (Character alphabet : hash_map.keySet()) {
      		HuffmanNode hn = new HuffmanNode();
 
            hn.c = alphabet;
            hn.data = hash_map.get(alphabet);
 
            hn.left = null;
            hn.right = null;
 
            q.add(hn);
    	}

		generateHuffmanTree();
	}

/*
	This function generates the huffman tree
*/


	private void generateHuffmanTree() {

		HuffmanNode root = null;

		while (q.size() > 1) {
 
			HuffmanNode x = q.peek();
			q.poll();

			HuffmanNode y = q.peek();
			q.poll();

			HuffmanNode f = new HuffmanNode();

			f.data = x.data + y.data;
			f.c = '-';

			f.left = x;
			f.right = y;

			root = f;

			q.add(f);
      }

      generateCharCode(root, "");
      printMap();
      printCode();

	}

/*
	This function generates the huffman Code for each character in puts it to the hashMap
*/

	private void generateCharCode(HuffmanNode root, String s) {
 
		if (root.left == null && root.right == null) {
		   System.out.println(root.c + ":" + s);
		   new_hash_map.put(root.c, s);
		   return;
		}

		generateCharCode(root.left, s + "0");
		generateCharCode(root.right, s + "1");
   }

/*
	This function prints the Huffman code of the inputted file
*/

   private void printCode() {
   	File file;

		try {

			file = new File(filename);
     		BufferedReader br = new BufferedReader(new FileReader(file));

     		String line;
     		StringBuilder sb = new StringBuilder();

  			while ((line = br.readLine()) != null) {

  				for(int i=0; i<line.length(); i++) {
  					char ch = line.charAt(i);
  					sb.append(new_hash_map.get(ch));
  				}

				sb.append(new_hash_map.get('\n'));
  			}

  			String code = sb.toString();
  			System.out.println(" "+code);

		} catch(FileNotFoundException ex) {
    		ex.printStackTrace();
		} catch(IOException ex){
			ex.printStackTrace();
		}
   }

/*
	This function prints HashMap<Character, String> which has the final codes of each character of the file
*/

	public void printMap() {
		for (Character entry : new_hash_map.keySet()) {
      		System.out.print(entry + " ");
      		System.out.println(new_hash_map.get(entry));
    		}
	}

/*
	This function reads the input file and creates a HashMap<Character, Integer>
*/
	public void readFile() {
		File file;

		try {

			file = new File(filename);
     		BufferedReader br = new BufferedReader(new FileReader(file));

     		String line;

  			while ((line = br.readLine()) != null) {

  				for(int i=0; i<line.length(); i++) {
  					makeFrequencyMap(line.charAt(i));
  				}

				makeFrequencyMap('\n');
  			}

  			makePriorityQueue();

		} catch(FileNotFoundException ex) {
    		ex.printStackTrace();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
}

class Huffman {

	public static void main(String[] args) {
		HuffmanBuild hf = new HuffmanBuild(args[0]);
		hf.readFile();
	}
}
