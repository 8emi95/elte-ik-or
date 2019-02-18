
package alapfeladat;

import java.io.*;
import java.net.*;
import java.util.*;

public class TippKliens {
    Socket s;
    int port; 
    String message = null;
    BufferedReader br;
    PrintWriter pw;
    Scanner sc;
    int szamol = 0;
    
    ArrayList valaszok = new ArrayList<Integer>();
    
    public TippKliens(int port){
        this.port = port;
        try{
           s = new Socket("localhost", this.port);
           System.out.println("A kliens letrejott, es csatlakozott a szerverhez.");
           
           pw = new PrintWriter(s.getOutputStream(), true);
           br = new BufferedReader(new InputStreamReader(s.getInputStream()));
           sc = new Scanner(System.in);
            
        }catch (IOException ex){System.err.println("Init hiba");}
        
        boolean run = true;
        while (run){
           run = !proc();
        }
    }
    
    public TippKliens(int port,int[] valaszok){
        this.port = port;
        
        for (int i=0;i<valaszok.length;i++){
            this.valaszok.add(valaszok[i]);
        }
        
        try{
           s = new Socket("localhost", this.port);
           System.out.println("A kliens letrejott, es csatlakozott a szerverhez.");
           
           pw = new PrintWriter(s.getOutputStream(), true);
           br = new BufferedReader(new InputStreamReader(s.getInputStream()));
           
            
        }catch (IOException ex){System.err.println("Init hiba");}
        
        boolean run = true;
        while (run){
           run = !proc2();
        }
        
    }
    
    private boolean proc()
    {
        boolean state=false;
        
        try{
            message=br.readLine();
        }catch(IOException ex){System.err.println("Olvasasi hiba");}
        
        if (message.substring(0,5).matches("Jatek")){
        //folytatás
            System.out.println("Folytatás: "+message);
            try{
                message=br.readLine(); //köv kérdés
                System.out.println("A kérdés: "+message);
            }catch(IOException ex){System.err.println("Olvasasi hiba");}
            
            System.out.println("Tippelj: ");
            int tipp = Integer.parseInt(sc.nextLine());
            pw.println(tipp);
        }
        else if("gyoztel".equals(message))
        {
            System.out.println("Nyertél!");
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }
        else if("vesztettel".equals(message))
        {
            System.out.println("Vesztettél.");
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }else{
            //első üzi: kérdés
            System.out.println("A kérdés: "+message);
            System.out.println("Tippelj: ");
            int tipp = Integer.parseInt(sc.nextLine());
            pw.println(tipp);
        }
        
        return state;
    }
    
    private boolean proc2()
    {
        boolean state=false;
        
        try{
            message=br.readLine();
        }catch(IOException ex){System.err.println("Olvasasi hiba");}
        
        if (message.substring(0,5).matches("Jatek")){
        //folytatás
            System.out.println("Folytatás: "+message);
            try{
                message=br.readLine(); //köv kérdés
                System.out.println("A kérdés: "+message);
            }catch(IOException ex){System.err.println("Olvasasi hiba");}
            
            int tipp = (Integer) this.valaszok.get(szamol);
            pw.println(tipp);
        }
        else if("gyoztel".equals(message))
        {
            System.out.println("Nyertél!");
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }
        else if("vesztettel".equals(message))
        {
            System.out.println("Vesztettél.");
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }else{
            //első üzi: kérdés
            System.out.println("A kérdés: "+message);
            int tipp = (Integer) this.valaszok.get(szamol);
            pw.println(tipp);
        }
        
        if(szamol == this.valaszok.size()-1){
            szamol = 0;
        }else{
            szamol++;
        }
        
        return state;
    }

    public static void main(String[] args){
        if (args.length>1){
            int[] valaszok = new int[args.length-1]; 
            
            for (int i=1; i<args.length;i++){
                valaszok[i]=( Integer.parseInt(args[i]) );
            }
            //new TippKliens(Integer.parseInt(args[0]), valaszok);
            new TippKliens(Integer.parseInt("32123"), valaszok);
        }else{
            //new TippKliens(Integer.parseInt(args[0]));
            //new TippKliens(Integer.parseInt("32123"));
            
            //int valaszok[] = {15,2,150,2668,75416};
            new TippKliens(Integer.parseInt("32123"));
        }
        
    }   
}
