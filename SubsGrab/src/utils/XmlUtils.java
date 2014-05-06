package utils;

import java.net.URL;
import java.net.URLConnection;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public class XmlUtils {
	private XmlUtils() {
	}

	public static Document getDocumentFromURL(String urlString)
			throws Exception {
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			SAXBuilder sxb = new SAXBuilder();
			return sxb.build(conn.getInputStream());
		} catch (Exception e) {
			throw new Exception("Cannot reach url: " + urlString + ".");
		}
	}

}