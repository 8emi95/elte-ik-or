
package alapfeladat;

import java.io.*;
import java.net.*;
import java.util.*;

public class JatekSzerver {
    int port;
    int n;
    String filename;
    ServerSocket server;
    FileReader reader;
    ArrayList kerdesek = new ArrayList<String>();
    ArrayList valaszLehetosegek = new ArrayList<ArrayList>();
    ArrayList helyesValaszSorszama = new ArrayList<Integer>();
    ArrayList clients = new ArrayList<Socket>();
    long nyeremenykassza;
    int kerdesszam = 0;
    
    JatekSzerver(int port, int n, String filename){
        this.filename = filename;
        this.port = port;
        this.n = n;
        this.nyeremenykassza = 1024;
        
        try {
            server = new ServerSocket(this.port);
            System.out.println("startolt");
        } catch (IOException e) {}
        
        try{
            reader = new FileReader(this.filename);
        }catch(FileNotFoundException fe){System.err.println("File nem létezik");}
        BufferedReader file_br = new BufferedReader(reader);
        String line;
        ArrayList valasz;
        try{
            while (file_br.ready()) {
                line = file_br.readLine();//kérdés
                kerdesek.add(line);
                valasz = new ArrayList<String>();
                for (int i=0;i<4;i++){ //4 válasz lehetőség
                    line = file_br.readLine();
                    valasz.add(line);
                }
                valaszLehetosegek.add(valasz);
                line = file_br.readLine();//helyes válasz
                helyesValaszSorszama.add(Integer.parseInt(line));        
            }
            file_br.close();
            reader.close();
        }catch(IOException e){System.err.println("Hiba");}
        
        //System.out.println(kerdesek);
        //System.out.println(valaszLehetosegek.get(kerdesszam));
        //System.out.println(helyesValaszSorszama);
            
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
                pw.println("Játékosok száma: "+this.n);
            } catch (IOException e) {
                System.err.println("Inicializalasi problema egy kliensnel.");
            }
        }
        
        int ingamedarab = n;
        boolean run=true;
        
        while(run){
            for (int i =0; i<pw_list.size();i++){
                pw = (PrintWriter) pw_list.get(i);
                String kerdes = "kerdes: "+kerdesek.get(kerdesszam).toString()+"; ";
                ArrayList valaszok = (ArrayList)valaszLehetosegek.get(kerdesszam);
                String valasz= "";
                for(int k=0;k<4;k++){
                    valasz += (k+1) +": "+valaszok.get(k).toString()+" ";
                }                
                
                String message = kerdes + valasz;
                pw.println(message);
            }
            
            ArrayList valaszok = new ArrayList<Integer>();
            int helyesValaszokSzama = 0;
            for (int i =0; i<br_list.size();i++){
                br = (BufferedReader) br_list.get(i);
                valaszok.add(Integer.parseInt(br.readLine()));
                if ((int)valaszok.get(i) == (int)helyesValaszSorszama.get(kerdesszam)){
                    helyesValaszokSzama++;
                }
            }
            
            int x = ingamedarab;
            for (int i=x-1;i>=0;i--){
                pw = (PrintWriter) pw_list.get(i);
                if ((int)valaszok.get(i) == (int)helyesValaszSorszama.get(kerdesszam))
                {
                    long nyeremeny = this.nyeremenykassza/helyesValaszokSzama;
                    pw.println("Sikeres tipp, a nyeremenykassza "+nyeremeny+" Ft.");
                }
                else
                {
                    pw.println("vesztettel");
                    pw.close();
                    br = (BufferedReader) br_list.get(i);
                    br.close();
                    s = (Socket) clients.get(i);
                    s.close();
                    pw_list.remove(i);
                    br_list.remove(i);
                    clients.remove(i);
                    ingamedarab--;
                }
            }
            
            if ((kerdesek.size()-1) == kerdesszam){
                for (int i=0; i< ingamedarab;i++){
                    pw = (PrintWriter) pw_list.get(i);
                    long nyeremeny = this.nyeremenykassza/helyesValaszokSzama;
                    pw.println("Gyoztel, nyeremenyed "+nyeremeny+" Ft.");
                    pw.close();
                    br = (BufferedReader) br_list.get(i);
                    br.close();
                    s = (Socket) clients.get(i);
                    s.close();
                    pw_list.remove(i);
                    br_list.remove(i);
                    clients.remove(i);
                }
                run = false;
            }
            else{
                kerdesszam++;
            }
            
            this.nyeremenykassza = this.nyeremenykassza*2;
        }
        server.close();
    }
    
    public static void main(String[] args) throws IOException
    {
        JatekSzerver server = new JatekSzerver(Integer.parseInt("32123"), Integer.parseInt("3"), "kerdesek.txt");
        if (server != null) server.handleClients();
    }
    
}
