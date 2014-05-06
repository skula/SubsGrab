package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.Episode;
import models.Show;
import models.Subtitle;
import services.BetaserieService;
import services.DownloadService;

public class GrabService {
	private Map<Integer,Show> shows;
	
	public static void main(String arg[]){
		GrabService gbService = new GrabService();
		System.out.println("***** SERIES *****");
		gbService.printShows();
		System.out.println("******************");
		
		String line = null;
		String error = null;
		Scanner sc = new Scanner(System.in);
		
		int nShow = 0;
		int nSeason = 0;
		int nEpisode = 0;
		
		do{
			System.out.println("> Saisir le numero de la serie (entre 1 et " +gbService.getShowsCount() + "): ");
			line = sc.nextLine();
			if(line.equals("")){
				continue;
			}
			nShow = gbService.isValideNumber(line,1, gbService.getShowsCount());
			if(nShow==-1){
				continue;
			}else{
				break;
			}
			
		}while(true);
		
		do{
			System.out.println("> Saisir la saison : ");
			line = sc.nextLine();
			if(line.equals("")){
				continue;
			}
			nSeason = gbService.isValideNumber(line,1, Integer.MAX_VALUE);
			if(nSeason==-1){
				continue;
			}else{
				break;
			}
			
		}while(true);
		
		do{
			System.out.println("> Saisir l'episode : ");
			line = sc.nextLine();
			if(line.equals("")){
				continue;
			}
			nEpisode = gbService.isValideNumber(line,1, Integer.MAX_VALUE);
			if(nEpisode==-1){
				continue;
			}else{
				break;
			}
			
		}while(true);
		
		Episode ep = new Episode(gbService.getShows().get(nShow).getReference(), nSeason, nEpisode);
		
		Map<Integer, Subtitle> subs = BetaserieService.getSubtitles(ep);		
		System.out.println("***** SOUS-TITRES *****");
		for(Integer i : subs.keySet()){
			System.out.println(i + ") " + subs.get(i).getName());
		}
		System.out.println("**********************");
		
		boolean ok = true;
		int choice = 0;
		do{
			System.out.println("> Choix du sous-titres : ");
			line = sc.nextLine();	
			if(!line.matches("\\d")){
				System.out.println("ERREUR: saisir un nombre uniquement.");
				ok = false;
			}else{
				choice = Integer.valueOf(line);
				if(choice < 1 || choice > subs.size()){
					System.out.println("ERREUR: ce choix est impossible.");
					ok = false;
				}else{
					ok = true;
				}
			}
		}while(!ok);
		
		String url = subs.get(choice).getUrl();
		if(DownloadService.getFile(subs.get(choice).getName(), gbService.getShows().get(nShow).getDirectory(), url)){
			System.out.println("SUCCES: telechargement reussi.");
		}
	}
	
	public GrabService(){
		this.shows = new HashMap<Integer, Show>();
		
		try{
			InputStream ips=new FileInputStream("/home/slown/Scripts/SubsGrab/shows.txt"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line = null;
			int i = 1;
			while ((line=br.readLine())!=null){
				if(!line.startsWith("//")){
					String tmp[] = line.split(";");					
					shows.put(i++, new Show(tmp[0], tmp[1], tmp[2]));
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println("ERREUR: Fichier de series introuvable.");
		}
	}
	
	public int isValideNumber(String line, int min, int max){
		int choice =0;
		try{
			choice = Integer.parseInt(line);
		}catch(Exception e){
			System.out.println("ERREUR: saisir un nombre uniquement.");
			return -1;
		}
		
		if(choice<min || choice>max){
			System.out.println("ERREUR: saisir un nombre entre "+ min + " et " + max + ".");
			return -1;
		}else{
			return choice;
		}
	}

	public Episode tryValidate(String line){		
		String args[] = line.split(" ");
		for(String s : args){
			if(!s.matches("\\d")){
				System.out.println("ERREUR: le format de la commande est incorrecte.");
				return null;
			}
		}
		
		int nShow = Integer.valueOf(args[0]);
		if( nShow<1 || nShow>shows.size()){
			System.out.println("ERREUR: le numero de la serie est inconnu.");
			return null;
		}
		
		return new Episode(shows.get(1).getReference(), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
	}
	
	public void printShows(){
		/*StringBuffer sb = new StringBuffer();
		for (Integer i : shows.keySet()) {
			if(i%2==0){
				sb.sappend(System.getProperties("line.separator"));
				sb.append(i+") " + shows.get(i).getTitle());
			}else{
				sb.append("\t" + i +") " + shows.get(i).getTitle());
			}
			System.out.println(sb.toString());
		}*/
		for (Integer i : shows.keySet()) {
			System.out.println(i+") " + shows.get(i).getTitle());
		}
	}
	
	public int getShowsCount(){
		return shows.size();
	}

	public Map<Integer, Show> getShows() {
		return shows;
	}

	public void setShows(Map<Integer, Show> shows) {
		this.shows = shows;
	}
}