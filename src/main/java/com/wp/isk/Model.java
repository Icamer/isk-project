/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wp.isk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author w.podosek
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Model {

    public Map<SingleEntry, Metric> entryMetric = new HashMap<>();
    RoutingFullInfo routingData;
    Boolean isChanged;
    int size = 0;

}
