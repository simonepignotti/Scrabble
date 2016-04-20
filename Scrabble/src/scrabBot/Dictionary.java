package scrabBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class Dictionary {
	private Path dictionaryFile;
	private HashSet<String> scrabbleDictionary;
	
	public Dictionary(){
		scrabbleDictionary = new HashSet<String>();
		dictionaryFile = Paths.get("dictionary/SOWPODS.txt");
	}
	
	public boolean loadDictionary(){
		try (InputStream in = Files.newInputStream(dictionaryFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
		{
			String word = null;
			while ((word = reader.readLine()) != null)
				scrabbleDictionary.add(word);
		}
		catch (IOException e) {
			System.err.println(e);
		    return false;
		}
		return true;
	}
	
	public boolean dictionaryCheck(String word){
		boolean wordInDictionary;
		wordInDictionary = scrabbleDictionary.contains(word.toLowerCase()); 
		return wordInDictionary;
	}
}
