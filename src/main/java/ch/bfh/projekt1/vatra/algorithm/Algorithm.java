package ch.bfh.projekt1.vatra.algorithm;

import org.json.simple.JSONObject;

/**
 * Created by dave on 02.12.15.
 */
public interface Algorithm {


    /**
     * Das JSON Objekt wird mitgegeben und ein Wert zwischen 1-10 muss zurückgegeben werden
     *
     * @param jsonObject
     * @return
     */
    int check(JSONObject jsonObject);
}
