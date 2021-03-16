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
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 *
 * @author mosta
 */
public class SeqAPI {
    
    // public static String[] results = new String[]{"", "", "", "", ""};
    public static ArrayList<String> results = new ArrayList<>();
    
    public SeqAPI() {
    }
    
    public static ArrayList<String> getData(ArrayList<String> urls) {
        Api api = new Api();
        Flowable.fromIterable(urls)
                .parallel()
                .concatMap(url -> Flowable.just(url)
                        .subscribeOn(Schedulers.newThread())
                        .map(w -> api.main(w))
                )
                .sequential()
                .subscribe(w-> results.add(w));
        return results;
    }
}
