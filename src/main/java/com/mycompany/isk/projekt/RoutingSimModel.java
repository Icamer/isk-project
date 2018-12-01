/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isk.projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

public class RoutingSimModel {

    RoutingData routingData;
    Boolean isChanged;

    public void simulate() {
        isChanged = false;
        for (Router router : routingData.getRouters()) {
            List<RoutingEntry> newEntries = new ArrayList<>();
            for (Link link : routingData.getLinks()) {
                if (!link.getIsWorking()) {
                    continue; //co wtedy
                }
                Router secondRouter = getSecondRouterRoutingTable(link);
                List<RoutingEntry> secondRoutingTable = secondRouter.getRoutingTable();
                newEntries.addAll(getNewEntries(router, secondRouter, secondRoutingTable));
            }
            router.getRoutingTable().addAll(newEntries);
            System.out.println("a");
        }
        routingData.setIteration(routingData.getIteration() + 1);
    }

    private List<RoutingEntry> getNewEntries(Router router, Router secondRouter, List<RoutingEntry> secondRoutingTable) {
        List<RoutingEntry> newEntries = new ArrayList<>();
        router.getRoutingTable().stream().filter(e -> e.getIteration().equals(routingData.getIteration())).forEach(e -> newEntries.add(process(e, router.getId(), getSameDestination(e, secondRoutingTable), secondRouter.getId())));
        return newEntries;
    }

    private Router getSecondRouterRoutingTable(Link link) {
        return routingData
                .getRouters()
                .stream()
                .filter(s -> s.getId().equals(link.getIdServerTwo()))
                .findAny()
                .get();
    }

    private RoutingEntry getSameDestination(RoutingEntry e, List<RoutingEntry> secondRoutingTable) {
        Optional<RoutingEntry> findAny = secondRoutingTable
                .stream()
                .filter(se -> se.getNetworkDestination()
                .equals(e.getNetworkDestination())
                && se.getIteration().equals(e.getIteration())
                && se.getIteration().equals(routingData.getIteration())).findAny();
        return findAny.get();
    }

    private RoutingEntry process(RoutingEntry firstEntry, Long firstServerId, RoutingEntry secondEntry, Long secondServerId) {
        RoutingEntry newEntry = firstEntry
                .toBuilder()
                .build();
        newEntry.setIteration(newEntry.getIteration() + 1);

        Optional<Link> entrysLink = routingData.getLinks().stream().filter(link -> link.getIsWorking() && link.getLinkId().equals(firstEntry.getNetworkDestination())).findAny();

        if (!newEntry.getMetric().equals(1L)
                && (entrysLink.get().getIdServerOne().equals(firstServerId)
                || entrysLink.get().getIdServerTwo().equals(firstServerId))) {
            isChanged = true;
            newEntry.setMetric(1L);
        } else if (firstEntry.getMetric() > secondEntry.getMetric() + 1) {
            isChanged = true;
            newEntry.setMetric(secondEntry.getMetric() + 1);
            newEntry.setInterfaceId(secondServerId);
        }
        return newEntry;
    }

}
