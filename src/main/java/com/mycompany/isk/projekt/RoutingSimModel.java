/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isk.projekt;

import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Marci
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RoutingSimModel {

    RoutingData routingData;
    Boolean isChanged;

    public Map<RoutingEntry, Metric> entryMetric = new HashMap<>();
//    public Map<RoutingEntry, Long> entryMetric = new HashMap<>();


    public void doSim(long iteration, long breakIteration, long brokenLinkId) {
        for (int i = 0; i < iteration; i++) {
            entryMetric = new HashMap<>();
            if (i == breakIteration) {
                Link brokenLink = routingData.getLinks().stream().filter(x -> x.getLinkId().equals(brokenLinkId)).findAny().get();
                brokenLink.setIsWorking(false);
//                routingData.getRouters().stream().filter(x->x.getId().equals(brokenLink.getIdServerOne()) || x.getId().equals(brokenLink.getIdServerTwo())).forEach();
            }
            for (Router router : routingData.getRouters()) {
                for (RoutingEntry re : router.getRoutingTable()) {
                    if (setMetricToThis(router, re)) continue;

                    List<Link> linksOfRouter = getLinksForward(router);
                    if (setMetricToNext(re, linksOfRouter)) continue;

                    List<Link> linksOfRouter2 = getLinksBackwards(router);
                    if (setMetricToPrevious(re, linksOfRouter2)) continue;

                    setMetricToOtherNext(re, linksOfRouter);
                    setMetricToOtherPrevious(re, linksOfRouter2);


                }
            }
            entryMetric.forEach((routingEntry, metric) -> {
                routingEntry.setMetric(metric.getMetric());
                routingEntry.setThrough(metric.getRouterId());
            });
        }
    }

    private List<Link> getLinksForward(Router router) {
        return routingData.getLinks().stream()
                .filter(link -> link.getIdServerOne().equals(router.getId()))
                .filter(Link::getIsWorking).collect(Collectors.toList());
    }

    private List<Link> getLinksBackwards(Router router) {
        return routingData.getLinks().stream()
                .filter(link -> link.getIdServerTwo().equals(router.getId()))
                .filter(Link::getIsWorking).collect(Collectors.toList());
    }

    private boolean setMetricToOtherNext(RoutingEntry re, List<Link> linksOfRouter) {
        for (Link link : linksOfRouter) {
            Optional<Router> collect = routingData.getRouters().stream().filter(r -> r.getId().equals(link.getIdServerTwo())).findAny();//jeden
            if (setMetricToOtherCommon(re, collect)) return true;
        }
        return false;
    }

    private boolean setMetricToOtherPrevious(RoutingEntry re, List<Link> linksOfRouter) {
        for (Link link : linksOfRouter) {
            Optional<Router> collect = routingData.getRouters().stream().filter(r -> r.getId().equals(link.getIdServerOne())).findAny();//jeden
            if (setMetricToOtherCommon(re, collect)) return true;
        }
        return false;
    }

    private boolean setMetricToOtherCommon(RoutingEntry re, Optional<Router> collect) {
        if (collect.isPresent()) {
            Router router2 = collect.get();
            Optional<RoutingEntry> any = router2.getRoutingTable().stream().filter(x -> x.getNetworkDestination().equals(re.getNetworkDestination())).findAny();
            if (any.isPresent()) {
                RoutingEntry routingEntry = any.get();
                if (entryMetric.get(re) != null) {
                    Long metricValueInEntryMetric = entryMetric.get(re).getMetric();
                    Long metricValueToGet = routingEntry.getMetric();
                    if (metricValueToGet < metricValueInEntryMetric) {
                        Metric metric = new Metric();
                        metric.setMetric(re.getMetric() > routingEntry.getMetric() ? routingEntry.getMetric() + 1 : re.getMetric());
//                        metric.setRouterId(re.getThisRouterId() < routingEntry.getThisRouterId() ? re.getThisRouterId() + 1 : re.getThisRouterId() - 1);
                        Long id = 0L;
                        boolean check = re.getMetric() > routingEntry.getMetric();
                        if (check) {
                            id = re.getThisRouterId() < routingEntry.getThisRouterId() ? re.getThisRouterId() + 1 : re.getThisRouterId() - 1;
                        } else {
                            id = re.getThrough();
                        }
                        metric.setRouterId(id);
                        entryMetric.put(re, metric);
                    }
                } else {
                    Metric metric = new Metric();
                    metric.setMetric(re.getMetric() > routingEntry.getMetric() ? routingEntry.getMetric() + 1 : re.getMetric());
                    Long id = 0L;
                    boolean check = re.getMetric() > routingEntry.getMetric();
                    if (check) {
                        id = re.getThisRouterId() < routingEntry.getThisRouterId() ? re.getThisRouterId() + 1 : re.getThisRouterId() - 1;
                    } else {
                        id = re.getThrough();
                    }
                    metric.setRouterId(id);
                    entryMetric.put(re, metric);
                }
                return true;
            }
        }
        return false;
    }


    private boolean setMetricToPrevious(RoutingEntry re, List<Link> linksOfRouter) {
        for (Link link : linksOfRouter) {
            if (re.getNetworkDestination().equals(link.getIdServerOne())) {
                entryMetric.put(re, new Metric(1L, link.getIdServerOne()));
                return true;
            }
        }
        return false;
    }

    private boolean setMetricToNext(RoutingEntry re, List<Link> linksOfRouter) {
        for (Link link : linksOfRouter) {
            if (re.getNetworkDestination().equals(link.getIdServerTwo())) {
                entryMetric.put(re, new Metric(1L, link.getIdServerTwo()));
                return true;
            }
        }
        return false;
    }

    private boolean setMetricToThis(Router router, RoutingEntry re) {
        if (re.getNetworkDestination().equals(router.getId())) {
            entryMetric.put(re, new Metric(0L, router.getId()));
            return true;
        }
        return false;
    }


    public void simulate() {
        isChanged = false;
        for (Router router : routingData.getRouters()) {
            Set<RoutingEntry> newEntries = new HashSet<>();
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
