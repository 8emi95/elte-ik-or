/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lokos
 */
public class AgencyHQ {
    
    protected final int agentNum;
    protected final int enemyNum;
    protected final int agencyNum;
    
    public int activeAgents;
    
    private HashMap<Integer, ArrayList<String>> enemyID;
    
    protected int lowerLimit;
    protected int upperLimit;
    
    private ArrayList<String> secretList = new ArrayList<>(); 
    
    private ArrayList<Agent> agentList = new ArrayList<>();
    private ArrayList<String> enemyList = new ArrayList<>();
    
    public AgencyHQ(int agentNum, int enemyNum, int agencyNum) {
        this.agentNum = agentNum;
        this.enemyNum = enemyNum;
        this.agencyNum = agencyNum;
    }

    public int getActiveAgents() {
        return activeAgents;
    }

    public HashMap<Integer, ArrayList<String>> getEnemyID() {
        return enemyID;
    }

    public ArrayList<String> getSecretList() {
        return secretList;
    }
    
    public int getAgentNum() {
        return agentNum;
    }

    public int getEnemyNum() {
        return enemyNum;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public ArrayList<Agent> getAgentList() {
        return agentList;
    }

    public ArrayList<String> getEnemyList() {
        return enemyList;
    }

    public int getAgencyNum() {
        return agencyNum;
    }
    
    public void addAgent(Agent a){
        agentList.add(a);
    }
    
    public void addEnemyID(int i, String name){
        if (enemyID.containsKey(i)){
            enemyID.get(i).add(name);
        }else{
            ArrayList<String> temp = new ArrayList<>();
            temp.add(name);
            enemyID.put(i, temp);
        }
    }
    
    public int searchInEnemyID(String name){
        int i = -1;
        for (int j = 0; j < enemyID.size(); j++){
            for(String s : enemyID.get(j)){
                if (s.equals(name)){
                    i = j;
                }
            }
        }
        return i;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public void output(){
        for(Agent a : agentList){
            System.out.println(a.toString());
        }
    }
    
    public boolean isInAgentList(String name){
        for(Agent a : agentList){
            for (String n : a.getNameList()){
                if(name.equals(n)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isInEnemyList(String name){
        for(String n : enemyList){
            if(name.equals(n)){
                return true;
            }
        }
        return false;
    }
    
    public void addEnemy(String enemy){
        enemyList.add(enemy);
    }
    
    public void addSecret(String secret){
        secretList.add(secret);
    }
    
    public String randomSecret(){
        if (secretList.isEmpty()){
            return null;
        }else{
            Random r = new Random();
            int i = r.nextInt(secretList.size());
            return secretList.get(i);
        }
    }
}
