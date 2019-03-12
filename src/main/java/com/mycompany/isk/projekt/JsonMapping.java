/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isk.projekt;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
/**
 *
 * @author Marci
 */
public class JsonMapping {

    private static final String SRC_MAIN_RESOURCES_INPUT_JSON = "src/main/resources/input.json";
    private static final String SRC_MAIN_RESOURCES_OUTPUT_JSON = "src/main/resources/output.json";

    ObjectMapper mapper = new ObjectMapper();

    public RoutingData getRoutingData() {
        try {
            return mapper.readValue(new File(SRC_MAIN_RESOURCES_INPUT_JSON), RoutingData.class);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

//    public RoutingData getRoutingData(String str) {
//        try {
//            return mapper.readValue(new File(str), RoutingData.class);
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//        return null;
//    }
    public RoutingData[] getRoutingData(String fileName) {
        try {
                return mapper.readValue(new File("src/main/resources/" + fileName), RoutingData[].class);

        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void saveRoutingData(RoutingData obj) {

        try {
            mapper.writeValue(new File(SRC_MAIN_RESOURCES_OUTPUT_JSON.replace("output","output" + new Date().getTime())), obj);
        } catch (IOException ex) {
            System.out.println("output ex: " + ex);
        }
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(obj);
        } catch (IOException ex) {
            System.out.println("input ex: " + ex);
        }
        System.out.println("str:" + jsonInString);
    }
}
