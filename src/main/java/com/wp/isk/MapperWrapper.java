/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wp.isk;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * @author w.podosek
 */
public class MapperWrapper {
    public RoutingFullInfo[] getData(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File("src/main/resources/" + fileName), RoutingFullInfo[].class);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }


}
