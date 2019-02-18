package gardentest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
                                 
import garden.*;

public class GardenRmiTest {
    
    @Test(timeout=40000)
    public void testRmi1() throws Exception {
	playRmiStorageGame(10, 2, 1, "output1.txt", "output1_mo.txt", new String[][] {new String[] {"Gardener1_1", "6"}, new String[] {"Gardener2_1", "4"}}, 
		new String[][] {new String[] {"Customer1_1", "2", "5"}});                          
    }
      
    @Test(timeout=40000)
    public void testRmi2() throws Exception { 
          playRmiStorageGame(20, 2, 3, "output2.txt", "output2_mo.txt", new String[][] {new String[] {"Gardener1_2", "4"}, new String[] {"Gardener2_2", "6"}}, 
		new String[][] {new String[] {"Customer1_2", "2", "5"}, new String[] {"Customer2_2", "5", "4"}, new String[] {"Customer3_2", "3", "2"}});
        
    } 
	
     
    @Test(timeout=40000)
    public void testRmi3() throws Exception { 
          playRmiStorageGame(10, 3, 4, "output3.txt", "output3_mo.txt", new String[][] {new String[] {"Gardener1_3", "2"}, new String[] {"Gardener2_3", "7"}, 
		new String[] {"Gardener3_3", "4"}}, 
		new String[][] {new String[] {"Customer1_3", "2", "7"}, new String[] {"Customer2_3", "1", "5"}, new String[] {"Customer3_3", "4", "2"}, 
			new String[] {"Customer4_3", "5", "3"}});
	
    }
      
    @Test(timeout=40000)
    public void testRmi4() throws Exception { 
          playRmiStorageGame(5, 2, 4, "output4.txt", "output4_mo.txt", new String[][] {new String[] {"Gardener1_4", "5"}, new String[] {"Gardener2_4", "7"}}, 
		new String[][] {new String[] {"Customer1_4", "2", "7"}, new String[] {"Customer2_4", "1", "5"}, new String[] {"Customer3_4", "4", "2"}, 
			new String[] {"Customer4_4", "5", "3"}});
	
    }
    
    @Test(timeout=40000)
    public void testRmi5() throws Exception { 
          playRmiStorageGame(40, 5, 6, "output5.txt", "output5_mo.txt", new String[][] {new String[] {"Gardener1_5", "3"}, new String[] {"Gardener2_5", "6"}, 
		new String[] {"Gardener3_5", "5"}, new String[] {"Gardener4_5", "4"}, new String[] {"Gardener5_5", "4"}}, 
		new String[][] {new String[] {"Customer1_5", "2", "7"}, new String[] {"Customer2_5", "1", "5"}, new String[] {"Customer3_5", "4", "2"}, 
			new String[] {"Customer4_5", "4", "3"}, new String[] {"Customer5_5", "2", "10"}, new String[] {"Customer6_5", "5", "7"}});
	
    }   
       

    public void playRmiStorageGame(int plants, int serverPlayerCount, int realPlayerCount, String outputFilename, String sampleOutputFilename, 
		String[][] gardenerParams, String[][] customerParams)
            throws Exception {       

        dt.runMain(1,
                args -> GardenStore.main(args),
                new String[][] {new String[] {Integer.toString(plants), outputFilename}},
                "GardenStore");

        Thread.sleep(700);
	dt.runMain(serverPlayerCount,
                args -> Gardener.main(args),
                gardenerParams,
		"G"
                );

        Thread.sleep(400);
	dt.runMain(realPlayerCount,
                args -> Customer.main(args),
                customerParams,
		"C"
                );

         	
            

        dt.waitForMainsToFinish();

        dt.compareWithExpectedFile(outputFilename, sampleOutputFilename);
    }

    // ----------------------------------------
    // Technikai reszletek

    final static String DOMINO_DB_DIR = "gardenstore_db_dir";
    final static String DOMINO_RMI_NAME = "factory";
    static Registry reg;
    GardenTest dt;

    @BeforeClass
    public static void beforeClass() throws Exception {
	PlantFactory.main(new String[] {"input.txt"});
    }

    @Before
    public void before() throws Exception {
        deleteDirIfExists(DOMINO_DB_DIR);

        dt = new GardenTest();
        dt.before();

        String dbFilePath = Paths.get(DOMINO_DB_DIR, "gardenstore").toString();
        //forceUnbindService(DOMINO_RMI_NAME);             
    }

    private void forceUnbindService(String DOMINO_RMI_NAME) throws RemoteException, AccessException {
        try {
            reg.lookup(DOMINO_RMI_NAME);
            reg.unbind(DOMINO_RMI_NAME);
        } catch (Exception e) {
        }
    }

    private static void deleteDirIfExists(String dominoDbDir) {
        File file = new File(dominoDbDir);
        if (file.isDirectory()) {
            file.delete();
        }
    }
}
