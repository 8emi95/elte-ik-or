
package threading;

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
    ArrayList clients;
    int kerdesszam = 0;
    
    TippSzerver(int port, int n, String filename){
        this.filename = filename;
        this.port = port;
        this.n = n;
        
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
        while (true){
            try {
                clients = new ArrayList<Socket>();
                for (int i=0;i<this.n;i++){
                    Socket client = server.accept();
                    System.out.println((i+1) +" kliens csatizott");
                    clients.add(client);
                }
                new Clienthandler(clients).start();
            } catch (IOException e) {
                    System.err.println("Hiba a kliensek fogadasakor.");
            }
        }  
    }
    
    
    class Clienthandler extends Thread
    {
        ArrayList pw_list = new ArrayList<PrintWriter>();
        ArrayList br_list = new ArrayList<BufferedReader>();
        PrintWriter pw;
        BufferedReader br;
        Socket s;
        int ingamedarab = n;
        int[] akt_valaszok;
        ArrayList clients;
        
        Clienthandler(ArrayList act_jatekosok) throws IOException{
            this.clients = act_jatekosok;
            for(int i=0;i<n;i++){
                s =(Socket)act_jatekosok.get(i);
                pw = new PrintWriter(s.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw_list.add(pw);
                br_list.add(br);
            }
 
        }
            
        @Override
            public void run(){
                boolean run = true;
                while (run){
                    int akt_valasz = (int) valasz.get(kerdesszam); //helyes válasz a listábol
                    String akt_kerdes = (String) kerdes.get(kerdesszam);
                    nextQuestion();
                    akt_valaszok = new int[clients.size()];//a játékosok válaszai

                    //kérdés kiküldése
                    for (int i =0; i<pw_list.size();i++){
                        pw = (PrintWriter) pw_list.get(i);
                        pw.println(akt_kerdes);
                    }

                    //válaszok begyűjtése
                    ArrayList calculated = new ArrayList<Integer>(); //absz értékes válaszok
                    for (int i =0; i<br_list.size();i++){
                        br = (BufferedReader) br_list.get(i);
                        try {
                            akt_valaszok[i] = Integer.parseInt(br.readLine());
                        } catch (IOException ex) {}
                        calculated.add(Math.abs(akt_valasz-akt_valaszok[i]));
                        System.out.println(i+1 + " válasza: " + akt_valaszok[i]+", eltérés: "+Math.abs(akt_valasz-akt_valaszok[i]));

                    }

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
                                s = (Socket) clients.get(i);
                                try {
                                    br.close();
                                    s.close();
                                } catch (IOException ex) {}
                            }
                            else
                            {
                                pw = (PrintWriter) pw_list.get(i);
                                pw.println("gyoztel");
                                br = (BufferedReader) br_list.get(i);
                                s = (Socket) clients.get(i);
                                pw.close();
                                try {
                                    br.close();
                                    s.close();
                                } catch (IOException ex) {}

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
                                s = (Socket) clients.get(i);
                                pw.close();
                                try {
                                    br.close();
                                    s.close();
                                } catch (IOException ex) {}
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
                }
            }

    }
    

    public synchronized void nextQuestion(){
        if ((this.kerdes.size()-1) == this.kerdesszam){
            this.kerdesszam = 0;
        }
        else{
            this.kerdesszam++;
        }
    }
        
    
    public static void main(String[] args) throws IOException
    {
        //TippSzerver tippszerver = new TippSzerver(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        TippSzerver tippszerver = new TippSzerver(Integer.parseInt("32123"), Integer.parseInt("3"), "kerdesek.txt");
        if (tippszerver != null) tippszerver.handleClients(); 
    }

}

