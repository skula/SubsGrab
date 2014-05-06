package services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Episode;
import models.Subtitle;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import utils.Property;
import utils.XmlUtils;

public class BetaserieService {
	private static final String DOMAIN = Property.get("BS_DOMAIN");
	private static final String KEY = Property.get("BS_KEY");

	public static Map<Integer, Subtitle> getSubtitles(Episode ep) {
		Map<Integer, Subtitle> res = new HashMap<Integer, Subtitle>();
		String url = DOMAIN + "subtitles/show/" + ep.getShow()
				+ ".xml?language=VF&season=" + ep.getSeason() + "&episode="
				+ ep.getNumber() + "&key=" + KEY;

		try {
			Document document = XmlUtils.getDocumentFromURL(url);
			Element root = document.getRootElement();
			XPath xpa = XPath.newInstance("./subtitles/subtitle");
			List subtitles = xpa.selectNodes(root);
			Iterator iter = subtitles.iterator();
			int cpt = 1;
			while (iter.hasNext()) {
				Element subtitleNode = (Element) iter.next();
				res.put(cpt, new Subtitle(subtitleNode.getChildText("file"),
						subtitleNode.getChildText("url")));
				cpt++;
			}
		} catch (Exception e) {
			return null;
		}

		return res;
	}
}