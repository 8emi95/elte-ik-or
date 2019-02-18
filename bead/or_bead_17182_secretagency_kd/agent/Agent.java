/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author lokos
 */
public class Agent{
    
    private int id;
    private ArrayList<String> nameList = new ArrayList<>();
    private String secretcode;
    private AgencyHQ ahq;
    
    private ArrayList<String> toldSecrets = new ArrayList<>();
    private ArrayList<String> secretList = new ArrayList<>();


    public Agent(int id, AgencyHQ ahq) {
        this.id = id;
        this.ahq = ahq;
    }
    
    public void addName(String name){
        nameList.add(name);
    }
    
    public void setCode(String code){
        this.secretcode = code;
        secretList.add(code);
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public String getCode() {
        return secretcode;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(" ");
        for(String s : nameList){
            sb.append(s);
            sb.append(" ");
        }
        sb.append(": ");
        sb.append(secretcode);
        return sb.toString();
    }
    
    public String randomName(){
        Random rand = new Random();
        System.out.println(nameList.size());
        int i = rand.nextInt(nameList.size());
        return nameList.get(i);
    }
    
    public String randomSecret(){
        if (secretList.isEmpty()){
            Random r = new Random();
            int i = r.nextInt(toldSecrets.size());
            return toldSecrets.get(i);
        }else{
            Random r = new Random();
            int i = r.nextInt(secretList.size());
            return secretList.get(i);
        }
    }
    
    public void startIntell() {
        
        Thread t1 = new Thread(() -> {
            while (!secretList.isEmpty()){
                Random r = new Random();
                int PORT = randVal(20090, 20100);
                try {
                    ServerSocket ss = new ServerSocket(PORT);
                    Socket s        = ss.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    
                    client(out, in, s);
                    
                    s.setSoTimeout(randVal(ahq.getLowerLimit(), ahq.getUpperLimit()));
                }catch(IOException e){
                }
            }
            ahq.activeAgents--;
        });
        t1.start();
    }
    
    public void connect(){
        new Thread(() -> {
            while (!secretList.isEmpty()) {
                int PORT = randVal(20090, 20100);
                try {
                    Socket s = new Socket("localhost", PORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    
                    server(out, in, s);
                } catch (Exception e) {
                }

            }

        }).start();
    }
    
    public int randVal(int min, int max){
        Random r = new Random();
        int i = r.nextInt(max - min) + min;
        return i;
    }
    
    public void server(BufferedWriter out, BufferedReader in, Socket s) throws IOException{
        String name = in.readLine();
                    
        int i;

        if (ahq.isInAgentList(name)) {
            i = ahq.agencyNum;
            out.write(ahq.agencyNum);
            out.newLine();
            out.flush();
        } else if (ahq.isInEnemyList(name)) {
            if (ahq.agencyNum == 1) {
                i = 2;
            } else {
                i = 1;
            }
            out.write(i);
            out.newLine();
            out.flush();
        } else {
            if (ahq.agencyNum == 1) {
                i = 2;
            } else {
                i = 1;
            }
            ahq.addEnemy(name);
            out.write(randVal(1, 2));
            out.newLine();
            out.flush();
        }

        if (in.readLine().equals("OK")) {
            if (i == ahq.agencyNum) {
                out.write("OK");
                out.newLine();
                out.flush();
                String secret = ahq.randomSecret();
                if (secret == null) {
                    out.write(secretcode);
                    out.newLine();
                    out.flush();
                } else {
                    out.write(secret);
                    out.newLine();
                    out.flush();
                }
                secret = in.readLine();
                ahq.addSecret(secret);
                secretList.add(secret);
            } else {
                out.write("???");
                out.newLine();
                out.flush();
                if (ahq.searchInEnemyID(name) == -1) {
                    int r = randVal(1, ahq.enemyNum);
                    out.write(r);
                    out.newLine();
                    out.flush();
                } else {
                    out.write(ahq.searchInEnemyID(name));
                    out.newLine();
                    out.flush();
                }
                String secret = in.readLine();
                ahq.addSecret(secret);
                secretList.add(secret);
            }

        } else {
            s.close();
        }
    }
    
    public void client(BufferedWriter out, BufferedReader in, Socket s) throws IOException{
        
        out.write(randomName());
        out.newLine();
        out.flush();

        int i = in.read();
        System.out.println(i);
        if (i != ahq.getAgencyNum()) {
            s.close();
        } else {
            out.write("OK");
            out.newLine();
            out.flush();

            String a = in.readLine();
            String b = in.readLine();
            if (b.equals("OK")) {
                String secret = in.readLine();
                ahq.addSecret(secret);
                secretList.add(secret);

                secret = ahq.randomSecret();
                if (secret == null) {
                    out.write(secretcode);
                    out.newLine();
                    out.flush();
                } else {
                    out.write(secret);
                    out.newLine();
                    out.flush();
                }
            } else {
                int id = in.read();
                if (id != this.id) {
                    s.close();
                } else {
                    String rSecret = randomSecret();

                    out.write(secretcode);
                    out.newLine();
                    out.flush();

                    secretList.remove(rSecret);
                    toldSecrets.add(rSecret);
                }
            }
        }
    }
}
