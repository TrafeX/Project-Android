package nl.trafex.android.project;

import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.widget.ArrayAdapter;


public class RssReader
{
	public ArrayAdapter<String> getFeed(Context context, URI rssurl) {
		//HashMap<String, String >articles = new HashMap<String, String>();
		ArrayAdapter<String> array = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
		try {
			 DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			 Document doc = builder.parse(rssurl.toURL().openStream());
			
			 //mStateMap.put("item", new Integer(STATE_IN_ITEM));
			 NodeList nodes = doc.getElementsByTagName("item");
			 for (int i = 0; i < nodes.getLength(); i++)
			 {
				Element element = (Element) nodes.item(i);
				
				NodeList title = element.getElementsByTagName("title");
				Element line = (Element) title.item(0);
			
				String feedTitle = getCharacterDataFromElement(line);
				// String url = getCharacterDataFromElement(element.getElementsByTagName("link").item(0));
				String description = getCharacterDataFromElement(element.getElementsByTagName("description").item(0));
			   
				array.add(feedTitle);
				
			 }
           }
           catch (Exception e) {
              e.printStackTrace();
           }
           return array;
	}
	
	public static String getCharacterDataFromElement(Node e) {
	   Node child = e.getFirstChild();
	   if(child == null)
	           return "?";
	   if (child instanceof CharacterData) {
	     CharacterData cd = (CharacterData) child;
	       return cd.getData();
	     }
	   return "?";
	} 
}
