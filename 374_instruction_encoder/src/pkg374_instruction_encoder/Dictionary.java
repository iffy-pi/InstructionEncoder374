/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author omnic
 */
public class Dictionary {
	//dictionary functions include:
	/**
	 * set value
	 * get value
	 * tostring
	 * get keys
	 * get values
	 * 
	 */
	
	private JSONObject dictionary;
//	private static LinkedHashMap stdMap;
//	private static JSONArray stdArray;
//	private static Integer stdInt;
//	private static Double stdDouble;
//	private static Float stdFloat;
//	private static String stdString;
//	private static Long stdLong;
//	private static Short stdShort;
//	private static Character stdChar;
//	private static Boolean stdBool;
	
	//adding standard values can be done with dictionary.put
	//adding a dictionary into the dictionary is done with a linkedHashMap
	//adding an array into the dictionary is done with JSONArray
	
	public Dictionary() {
		dictionary = new JSONObject();

	}
	
	public Dictionary(String dixText) {
		try {
			dictionary = (JSONObject) new JSONParser().parse(dixText);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Dictionary(LinkedHashMap m) {
		//get everything from the linked hashmap and add it to the dictionary
		this();
		dictionary.putAll(m);
	}
	
	public Dictionary(Dictionary dicIn) {
		this();
		dictionary = dicIn.dictionary;
	}
	
	public Dictionary(JSONObject actualDix) {
		this();
		dictionary = actualDix;
	}


	public void setValue(String keyIn, Object valueIn) {
		//this covers text, numbers and dictionaries themselves
		//assuming JSON has support for adding dictionaries directly to the setInfod
		dictionary.put(keyIn, valueIn);
	}
	
	public <T extends Object> void setValue(String keyIn, T[] arrayIn) {
		//this covers a passed in array of information that must be conveted to a JSON array
		JSONArray localArray = new JSONArray();
		for(Object curObject : arrayIn) {
			localArray.add((T)curObject);
		}
		
		//then add the local array to dictionary.
		dictionary.put(keyIn, localArray);
	}
	
	public void setArray(String keyIn, ArrayList listIn) {
		JSONArray localArray = new JSONArray();
		
		//convert array list to json array
		Iterator itr = listIn.iterator();
		while(itr.hasNext()) {
			localArray.add(itr.next());
		}
		
		//add json array to dictionary
		dictionary.put(keyIn, localArray);
	}
	
	public void setDictionary(String keyIn, Dictionary dixIn) {
		//assuming support for json object as it should
		
		dictionary.put(keyIn, dixIn.dictionary);
	}
	
	public Object getValue(String keyIn) {
		return dictionary.get(keyIn);
	}
	
	public ArrayList getArray(String keyIn){
		//this returns the form of an array list for a local array
		JSONArray localArray = (JSONArray)getValue(keyIn);
		Iterator itr = localArray.iterator();
		//iterator for json array
		
		ArrayList localList = new ArrayList();
		//local array list
		while(itr.hasNext()) {
			localList.add(itr.next());
			//adds everything from json array into local list
		}
		return localList;
	}
	
	public Dictionary getDictionary(String keyIn) {
		//dictionaries are stored in the JSON as JSON objects
		if(dictionary.get(keyIn) != null){
		return new Dictionary((JSONObject) dictionary.get(keyIn));
                }
                else return null;
	}
	
	public Set getKeys() {
		return dictionary.keySet();
	}
	
	public Iterator getKeyIterator() {
		return getKeys().iterator();
	}
	
	public void removeValue(String keyIn) {
		//new dictionary to hold information
		Dictionary newDix = new Dictionary();
		
		//get the current keys and iterate through them
		Set oldKeys = getKeys();
		Iterator oldKeyItr = oldKeys.iterator();
		while(oldKeyItr.hasNext()) {
			//we want to filter out keyIn, therefore
			// if current key is not equal to key in then add the key to the new dictionary.
			String curkey = (String) oldKeyItr.next();
			if(!(curkey.equals(keyIn))) {
				newDix.setValue(curkey, this.getValue(curkey));
			}
		}
		
		//now that is done assign new dictionary to calling object
		dictionary = newDix.dictionary;
		
	}
        
    /**
     *
     * @param otherObject
     * @return
     */
    @Override
        public boolean equals(Object otherObject){
            if(otherObject == null) return false;
            else if(otherObject.getClass()!= getClass()) return false;
            else{
                Dictionary otherDix = (Dictionary)otherObject;
                return (dictionary.equals(otherDix.dictionary));
            }
        }
	
	
	public String toString() {
		return dictionary.toJSONString();
	}
        
        public Object clone(){
            return new Dictionary((JSONObject)dictionary.clone());
        }
	
//	public static void main(String[] args) {
//		Dictionary testDix = new Dictionary();
//		testDix.setValue("1st", "Apple");
//		testDix.setValue("2nd", "Oranges");
//		
//		Dictionary testDix2 = new Dictionary();
//		testDix2.setValue("Ours", "Wow");
//		testDix2.setDictionary("Test",testDix);
//		
//		//System.out.println(testDix2.toString());
//		
//		Dictionary testDix3 = testDix2.getDictionary("Test");
//		System.out.println(testDix3.toString());
//		
//	}
	
}
