
package serialization;

import alapfeladat.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TippSzerver {
    int port;
    int n;
    String filename;
    ServerSocket server;
    FileReader reader;
    ArrayList kerdes = new ArrayList<String>();
    ArrayList valasz = new ArrayList<Integer>();
    ArrayList clients = new ArrayList<Socket>();
    int kerdesszam = 0;
    
    Map eddigiek = new HashMap<String,Integer>();
    
    ObjectInputStream ois; 
    
    TippSzerver(int port, int n, String filename){
        this.filename = filename;
        this.port = port;
        this.n = n;
        
        try {
            ois = new ObjectInputStream(new FileInputStream("kerdesek.dat"));
            if (ois.available() != 0){
                eddigiek = (HashMap) ois.readObject();
            }
            ois.close();
        } catch (IOException|ClassNotFoundException ex) {}
        
        
        try {
            server = new ServerSocket(this.port);
            System.out.println("startolt");
        } catch (IOException e) { }
        try{
            reader = new FileReader(this.filename);
        }catch(FileNotFoundException fe){System.err.println("File nem létezik");}
        
        BufferedReader file_br = new BufferedReader(reader);

        String line;
        
        try{
            while (file_br.ready()) {
                line = file_br.readLine();
                kerdes.add(line);
                line = file_br.readLine();
                valasz.add(Integer.parseInt(line));
            }
            file_br.close();
            reader.close();
        }catch(IOException e){System.err.println("Hiba");}

    }

    public void handleClients(){
        try {
            for (int i=0;i<this.n;i++){
                Socket client = server.accept();
                System.out.println((i+1) +" kliens csatizott");
                clients.add(client);
            }
            Clienthandler(clients);
        } catch (IOException e) {
                System.err.println("Hiba a kliensek fogadasakor.");
        }
    }
    
    public void Clienthandler(ArrayList clients) throws IOException{
        ArrayList pw_list = new ArrayList<PrintWriter>();
        ArrayList br_list = new ArrayList<BufferedReader>();
        PrintWriter pw;
        BufferedReader br;
        Socket s;
        
        for(int i=0;i<n;i++){
            try {
                s =(Socket)clients.get(i);
                pw = new PrintWriter(s.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw_list.add(pw);
                br_list.add(br);

            } catch (IOException e) {
                System.err.println("Inicializalasi problema egy kliensnel.");
            } 
        }
        
        //String message=null;
        boolean run=true;
        int ingamedarab = n;
            
        while(run){
            int akt_valasz = (int) valasz.get(kerdesszam); //helyes válasz a listábol
            int[] akt_valaszok = new int[clients.size()];//a játékosok válaszai
            //kérdés kiküldése
            for (int i =0; i<pw_list.size();i++){
                pw = (PrintWriter) pw_list.get(i);
                pw.println(kerdes.get(kerdesszam));
            }
            
            //válaszok begyűjtése
            ArrayList calculated = new ArrayList<Integer>(); //absz értékes válaszok
            for (int i =0; i<br_list.size();i++){
                br = (BufferedReader) br_list.get(i);
                try{
                    akt_valaszok[i] = Integer.parseInt(br.readLine());
                    calculated.add(Math.abs(akt_valasz-akt_valaszok[i]));
                    System.out.println(i+1 + " válasza: " + akt_valaszok[i]+", eltérés: "+Math.abs(akt_valasz-akt_valaszok[i]));
                }catch (IOException e){}
            }
            
            //Uj kerdes vagy Ismert kerdes
            
            //a tipp, ami a legközelebb volt a helyes megoldáshoz
            /*int min = 0;
            for(int i=0;i<ingamedarab;i++){
                if((int)calculated.get(i) < (int)calculated.get(min)){
                    min = i;
                }
            }
            eddigiek.put(kerdes.get(min), akt_valaszok[min]);*/
            

            //választok ellenőrzése
            ArrayList maxes = new ArrayList<Integer>(); //kiesők indexe
            
            int max = 0; //max érték

            for(int i=0;i<ingamedarab;i++){
                if((int)calculated.get(i) > max){
                    max = (int)calculated.get(i);
                }
            }
            for(int i=0;i<ingamedarab;i++){
                if ((int)calculated.get(i) == max){
                    maxes.add(i);
                }
            }            

            if(maxes.size()+1 == ingamedarab) //1 nyertes, többi kiesett
            {
                for(int i=0;i<ingamedarab;i++)
                {
                    if(maxes.contains(i)){
                        pw = (PrintWriter) pw_list.get(i);
                        pw.println("vesztettel");
                        br = (BufferedReader) br_list.get(i);
                        pw.close();
                        br.close();
                        s = (Socket) clients.get(i);
                        s.close();
                    }
                    else
                    {
                        pw = (PrintWriter) pw_list.get(i);
                        pw.println("gyoztel");
                        br = (BufferedReader) br_list.get(i);
                        pw.close();
                        br.close();
                        s = (Socket) clients.get(i);
                        s.close();
                    }
                }
                
                run = false;
            }
            else if(maxes.size() < ingamedarab) //vannak kiesők
            {
                System.out.println("helyes válasz: "+ akt_valasz);
                for(int i=0;i<ingamedarab;i++){
                    if(maxes.contains(i)){
                        pw = (PrintWriter) pw_list.get(i);
                        pw.println("vesztettel");
                        br = (BufferedReader) br_list.get(i);
                        pw.close();
                        br.close();
                        s = (Socket) clients.get(i);
                        s.close();
                    }
                    else
                    {
                        pw = (PrintWriter) pw_list.get(i);
                        if (akt_valaszok[i] > akt_valasz){
                            pw.println("Jatekban maradtal, a tipped nagyobb.");
                        }
                        else if(akt_valaszok[i] < akt_valasz){
                            pw.println("Jatekban maradtal, a tipped kisebb.");
                        }
                        else{
                            pw.println("Jatekban maradtal, a tipped helyes.");
                        }
                    }
                }
                int x = ingamedarab;
                for(int i=x-1;i>=0;i--){
                    if(maxes.contains(i)){
                        pw_list.remove(i);
                        br_list.remove(i);
                        clients.remove(i);
                        ingamedarab--;
                    }
                }
            }
            else //mindenkinek egyforma az eltérés
            {
                System.out.println("helyes válasz: "+ akt_valasz);
                int x = ingamedarab;
                for(int i=0;i<x;i++)
                {
                    pw = (PrintWriter) pw_list.get(i);
                    if (akt_valaszok[i] > akt_valasz){
                        pw.println("Jatekban maradtal, a tipped nagyobb.");
                    }
                    else if(akt_valaszok[i] < akt_valasz){
                        pw.println("Jatekban maradtal, a tipped kisebb.");
                    }
                    else{
                        pw.println("Jatekban maradtal, a tipped helyes.");
                    }
                }
            }

            if ((kerdes.size()-1) == kerdesszam){
                kerdesszam = 0;
            }
            else{
                kerdesszam++;
            }
        }    
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kerdesek.dat"));
        oos.writeObject(eddigiek);
	oos.flush();
        oos.close();
        
        server.close();
    }


    public static void main(String[] args) throws IOException
    {
        //TippSzerver tippszerver = new TippSzerver(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        TippSzerver tippszerver = new TippSzerver(Integer.parseInt("32123"), Integer.parseInt("4"), "kerdesek.txt");
        if (tippszerver != null) tippszerver.handleClients(); 
    }
}

