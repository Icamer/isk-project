/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isk.projekt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Marci
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutingData implements Serializable{

    List<Router> routers;
    List<Link> links;
    Long iteration;

    public void fillRoutingTables() {
        Long hash = 0L;
        for (Router router : routers) {
            List<RoutingEntry> routingTable = new ArrayList<>();
            for (Router targetRouter : routers) {
                routingTable.add(RoutingEntry.builder()
                        .hash(hash)
                        .networkDestination(targetRouter.getId())//todo:zmiana
                        .iteration(0L)
                        .metric(100000L)
                        .build());
                hash++;
            }
            router.setRoutingTable(routingTable);
        }
    }
}
