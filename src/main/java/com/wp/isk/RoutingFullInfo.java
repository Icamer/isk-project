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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author w.podosek
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutingFullInfo implements Serializable {

    List<Router> routers;
    List<Link> links;
    Long iteration;
    Boolean isBroken = false;
    Integer broken = 0;

    public void fillRoutingTables() {
        Long hash = 0L;
        for (Router router : routers) {
            List<SingleEntry> routingTable = new ArrayList<>();
            for (Router targetRouter : routers) {
                routingTable.add(SingleEntry.builder()
                        .hash(hash)
                        .thisRouterId(router.getId())
                        .networkDestination(targetRouter.getId())
                        .iteration(0L)
                        .metric(999999L)
                        .build());
                hash++;
            }
            router.setRoutingTable(routingTable);
        }
    }
}
