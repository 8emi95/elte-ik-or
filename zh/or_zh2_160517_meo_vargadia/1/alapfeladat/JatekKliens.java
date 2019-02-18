
package alapfeladat;

import java.io.*;
import java.net.*;
import java.util.*;

public class JatekKliens {
    Socket s;
    int port; 
    String message = null;
    ArrayList valaszok = null;
    BufferedReader br;
    PrintWriter pw;
    Scanner sc;
    int szamol = 0;    
    
    public JatekKliens(int port){
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
    public JatekKliens setAnswers(int[] valaszok){
        if (valaszok.length != 0){
            this.valaszok = new ArrayList<Integer>();
            for (int i=0;i<valaszok.length;i++){
                this.valaszok.add(valaszok[i]);
            }
        }
        return this;
    }
    
    private boolean proc(){
        boolean state=false;
        
        try{
            message=br.readLine();
        }catch(IOException ex){System.err.println("Olvasasi hiba");}
        
        if(message.substring(0,6).matches("kerdes"))
        {
            System.out.println(message);
            int tipp;
            if (valaszok == null){
                System.out.println("Válaszod: ");
                tipp = Integer.parseInt(sc.nextLine());
            }
            else
            {
                tipp = (Integer) this.valaszok.get(szamol);
                if(szamol == this.valaszok.size()-1){
                    szamol = 0;
                }else{
                    szamol++;
                }
            }
            pw.println(tipp);
        }
        else if("vesztettel".equals(message))
        {
            System.out.println("Vesztettél.");
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }
        else if(message.substring(0,7).matches("Gyoztel"))
        {
            System.out.println(message);
            try{
                s.close();
            }catch(IOException ex){}
            state=true;
        }
        else
        {
            System.out.println(message);
        }

        return state;
    }
    
    
    public static void main(String[] args){
        int[] valaszok = null;
        if (args.length>1){
            valaszok = new int[args.length-1];
            for (int i=1; i<args.length;i++){
                valaszok[i]=( Integer.parseInt(args[i]) );
            }
        }
        new JatekKliens(Integer.parseInt("32123")).setAnswers(valaszok);
    }
    
}
