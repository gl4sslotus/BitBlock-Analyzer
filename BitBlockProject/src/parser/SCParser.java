package parser;

import java.util.*;

import bba.model.Input;
import javafx.scene.paint.Color;

/**
 * class that parses the Source Code. Builds a TokenizedPixel list by taking in an input file, 
 * scans it into lines, tokenizes it, and adds it to the list token by token.
 * Contains only one static method.
 * 
 * @author Triston Scallan
 * @see #parse(Input, ColorPalette)
 */
public class SCParser {
	/**
	 * Parses the input through tokenizing and colorizing
	 * @param input input to parse
	 * @param palette the palette to use as the codex
	 * @return list of our pixels
	 */
	public static List<TokenizedPixel> parse(Input input, ColorPalette palette) { 
		int isComment = 0;
		
		//the arrayList of tokens of a single expression
		List<String> tokenList = new ArrayList<String>();
		//the arrayList of TokenizedPixel type to construct a BitBlock
		List<TokenizedPixel> pixelList = new ArrayList<>();
		
		
		for (String line : input.getSourceCodeLines()) {
			
			//check if this line is between /* and */, i.e. a comment
			if (isComment == 1) {
				if (!line.contains("*/")) {
					pixelList.addAll(LiteralsToPixels(line, palette));
					continue;
				}
			}
				
			//tokenize the line
			ArrayList<String> tempList = new ArrayList<String>();
			String[] splitLine = line.split(" "); //raw array of tokens
			for (String token : splitLine) {
				tempList.add(token);
			}
			
			ArrayList<String> emptyList = new ArrayList<String>();
			tokenList = recursiveTokenize(tempList, emptyList, 0);
			
			int parseCode = 0; //1 = quote pairs. 2 = remainder line comment. 3 = multiline comment.
			for (String token : tokenList) {
				
				if (token.contains("//") || parseCode == 2) { //parse rest of line as literals
					pixelList.addAll(LiteralsToPixels(token, palette));
					parseCode = 2; 
				} else if (token.contains("/*")) { //parse tokens as literals until "*/"
					pixelList.addAll(LiteralsToPixels(token, palette));
					parseCode = 3;
					isComment = 1;
				} else if (token.contains("*/")) { //parse tokens up to this point
					pixelList.addAll(LiteralsToPixels(token, palette));
					isComment = 0;
					parseCode = 0;
				} else if (token.contains("\"")) { //case: quote pairs
					pixelList.addAll(LiteralsToPixels(token, palette));
					parseCode = (parseCode == 0) ? 1 : 0; //toggle between 0 and 1.
				} else if ( parseCode == 1 || parseCode == 3) {
					pixelList.addAll(LiteralsToPixels(token, palette));
				} else if (palette.containsToken(token)) { //case: token is on the map
					pixelList.add(new TokenizedPixel(token, palette.getColor(token)));
				} else { //case: token isn't on the map
					//attempt to add tuple to token-color table. If 'already exists' or 'can't', then use literals.
					//otherwise add the pixel based on it's new token-color association. (this step allows crypto)
					if (! palette.setColor(token)) 
						pixelList.addAll(LiteralsToPixels(token, palette));
					else
						pixelList.add(new TokenizedPixel(token, palette.getColor(token)));
				}
			}	
		}
		return pixelList;
	}
	
	/**
	 * Takes in a line of strings from tempList and recursively builds a list of more concise tokens.
	 * @param tempList the list it tokenizes from.
	 * @param newList the list it tokenizes to.
	 * @param done a value that tells the function when it's finished
	 * @return an arraylist of strings, the tokenized version of tempList
	 */
	private static ArrayList<String> recursiveTokenize(ArrayList<String> tempList, ArrayList<String> newList, int done) { //cant figure out what's wrong
		if (done == 1) {
			return tempList;
		}
		//newList = tempList;
		int pass = 1;
		
		for (int i = 0; i < tempList.size(); i++) {
			String token = tempList.get(i);
			
			//CASE: token has `Class.methods`
			if (token.matches(".+\\..+") && !token.contains(".txt")) {
				pass = 0;
				
				//add the split tokens to the end of the new list
				String[] tempSplit = token.split("(?<=\\.)|(?=\\.)");
				for (String temp : tempSplit) { 
					newList.add(temp);
				} 
			} else if (token.matches(".+;.*")) { //CASE: ";"
				pass = 0;
				String[] tempSplit = token.split("(?<=;)|(?=;)");
				for (String temp : tempSplit) { 
					newList.add(temp);
				}
				
			} else if (token.matches(".*\\(.+") || token.matches(".+\\(.*")) { //CASE: ( or ), and { or } later
				pass = 0;
				String[] tempSplit = token.split("(?<=\\()|(?=\\()");
				for (String temp : tempSplit) { 
					newList.add(temp);
				}
			} else if (token.matches(".*\\).+") || token.matches(".+\\).*")) { //CASE: ( or ), and { or } later
				pass = 0;
				String[] tempSplit = token.split("(?<=\\))|(?=\\))");
				for (String temp : tempSplit) { 
					newList.add(temp);
				}
			} else { //CASE: token doesn't need to be split up further.
				token.trim();
				token.replaceAll("\\s", "");
				if (!token.isEmpty()) {
					newList.add(token);
				}
			}
		}
		//if pass is still 1, then it will end recursion and return the final tokenized version.
		ArrayList<String> emptyList = new ArrayList<String>();
		return recursiveTokenize(newList, emptyList, pass);
	}
	
	/**
	 * Takes a whole token and breaks it into a series of chars and adds it to a pixel list.
	 * @param token a single word string representing a lingual piece of source code
	 */
	private static List<TokenizedPixel> LiteralsToPixels(String token, ColorPalette palette) {
		String[] tempSplit = token.split("(?<!^)");
		List<TokenizedPixel> pixelList = new ArrayList<>();
		for (String temp: tempSplit) {
			TokenizedPixel pixel = new TokenizedPixel(temp, palette.getColor(temp));
			if (palette.getColor(temp) == null) {
				pixel.setColor(Color.BLACK);
			}
			pixelList.add(pixel);
		}
		return pixelList;
	}
}
