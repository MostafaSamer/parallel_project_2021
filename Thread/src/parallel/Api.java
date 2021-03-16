/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 *
 * @author mosta
 */
public class Api {
    
     public static String main(String urlAPI) throws MalformedURLException, IOException{
         URL conn = new URL(urlAPI);
         URLConnection yc = conn.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String result = "";
         String inputLine;
         while ((inputLine = in.readLine()) != null)
             result +=inputLine;
         in.close();
         return result;
    }
}
