/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lokos
 */
public class AgentMain {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        
        Object lock = new Object();
        
        if(args.length == 2){
            AgencyHQ ahq1 = new AgencyHQ(Integer.parseInt(args[0]), Integer.parseInt(args[1]), 1);
            AgencyHQ ahq2 = new AgencyHQ(Integer.parseInt(args[1]), Integer.parseInt(args[0]), 2);
            
            start(ahq1, ahq2);
            
            for (int i = 0; i < ahq1.getAgentNum(); i++){
                fileRead(makeFileName(ahq1.getAgencyNum(),i+1), ahq1, i);
            }
            
            for (int i = 0; i < ahq2.getAgentNum(); i++){
                fileRead(makeFileName(ahq2.getAgencyNum(),i+1), ahq2, i);
            }
            
            //ahq1.output();
            //ahq2.output();
            /*
            Agent a = new Agent(5, ahq1);
            a.addName("gyula");
            a.setCode("a");
            Agent b = new Agent(3, ahq2);
            a.addName("bela");
            b.setCode("b");
            
            a.startIntell();
            b.connect();*/

            Thread agency1 = new Thread(() -> {
                while (ahq1.getActiveAgents() > 0 || ahq1.getSecretList().size() < (ahq1.getAgentNum() + ahq1.getEnemyNum()) ) {
                    for (Agent a : ahq1.getAgentList()){
                        a.startIntell();
                        a.connect();
                    }
                }
            });
            
            Thread agency2 = new Thread(() -> {
                while (ahq2.getActiveAgents() > 0 || ahq2.getSecretList().size() < (ahq2.getAgentNum() + ahq2.getEnemyNum())) {
                    for (Agent a : ahq2.getAgentList()){
                        a.startIntell();
                        a.connect();
                    }
                }
            });
            
            agency1.start();
            agency2.start();
            agency1.join();
            agency2.join();
            
        }else{
            System.out.println("Nincs eleg parancssori parameter!");
        }
    }

    public static void start(AgencyHQ a1, AgencyHQ a2) throws FileNotFoundException{
        try{
            Integer t1,t2;
            t1 = null;
            t2 = null;
            Scanner scIn = new Scanner(System.in);
            while(t1 == null && t2 == null){
                System.out.println("Kerek egy also es felso korlatot!");
                t1 = Integer.parseInt(scIn.nextLine());
                t2 = Integer.parseInt(scIn.nextLine());
                if (t1 > t2){
                    t1 = null;
                    t2 = null;
                    System.out.println("A felso korlat kisebb mint az also!");
                }
            }
            a1.setLowerLimit(t1);
            a1.setUpperLimit(t2);
            a2.setLowerLimit(t1);
            a2.setUpperLimit(t1);
            
        }catch(NumberFormatException e){
            System.out.println("Két egesz szamot kerek!");
        }
    }
    
    public static void fileRead(String name, AgencyHQ ahq, int i) throws FileNotFoundException{
        Scanner scFile = new Scanner(new File(name));
        String[] names = scFile.nextLine().split(" ");
        Agent agent = new Agent(i+1, ahq);
        for(String n : names){
            agent.addName(n);
        }
        agent.setCode(scFile.nextLine());
        
        ahq.addAgent(agent);
    }
    
    private static String makeFileName(int n, int m){
        return "src/agent/datas/agent" + n + "-" + m + ".txt";
    }
    
}
