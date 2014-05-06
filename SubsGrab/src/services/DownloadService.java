package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import models.Episode;
import utils.Property;

public class DownloadService {

	private DownloadService() {
	}

	public static String getPath(Episode ep) {
		return Property.get("SHOWS_ROOT_DIR") + ep.getShow() + "/s"
				+ ep.getSeason() + "/";
	}

	public static boolean deleteFile(String path) throws Exception {
		try {
			File f = new File(path);
			f.delete();
		} catch (Exception e) {
			System.out.println("ERREUR: impossible de supprimer le fichier.");
			return false;
		}
		return true;
	}

	public static boolean getFile(String fileName, String dir, String urlString) {
		try {

			urlString = urlString.replaceAll("https://", "http://");
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			if (conn.getContentLength() == -1) {
				System.out.println("ERREUR: fichier introuvable.");
				return false;
			}

			String path = Property.get("SHOWS_DIR");
			InputStream in = conn.getInputStream();
			path +=dir+"/" + fileName;
			FileOutputStream writenFile = new FileOutputStream(path);
			byte[] buff = new byte[1024];
			int l = in.read(buff);
			while (l > 0) {
				writenFile.write(buff, 0, l);
				l = in.read(buff);
			}
			writenFile.flush();
			writenFile.close();
		} catch (Exception e) {
			System.out.println("ERREUR: le telechargement a echoue.");
			return false;
		}
		return true;
	}
}