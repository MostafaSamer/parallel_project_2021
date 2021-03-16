/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

/**
 *
 * @author mosta
 */
public class SeqAPI {
    
    public static String[] results = new String[]{"", "", "", "", ""};
    
    public SeqAPI() {
    }
    
    public static String[] getData(String urls[]) {
        Api api = new Api();
        try {
            for(int i=0; i<urls.length; i++ ) {
                results[i] = api.main(urls[i]);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
}
