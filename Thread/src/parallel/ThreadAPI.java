/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mosta
 */
public class ThreadAPI {
    public static String[] results = new String[]{"", "", "", "", ""};

    public ThreadAPI() {

    }

    public static String[] getData(String urls[]) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < urls.length; i++) {
            Runnable worker= new WorkerThread(urls[i], i);
            executor.execute(worker);//calling execute method of ExecutorService
            /*thread_object.start();*/
        }
        executor.shutdown();
        while (!executor.isTerminated()) { }
        System.out.println("Finished all threads");
        return results;


    }


public static class WorkerThread implements Runnable {
    private String API_to_print;
    private int index;

    WorkerThread(String to_print, int index) {
        this.API_to_print = to_print;
        this.index = index;
    }

    public void run() {
        Api api = new Api();
        try {
            results[index] = api.main(API_to_print);
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
}