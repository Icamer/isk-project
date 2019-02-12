/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.isk.projekt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Marci
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

public class RoutingEntry {

//    String netmask;
//    String gateway;
    Long networkDestination;
    Long metric;

    Long iteration;
    Long interfaceId;
    Long hash;
}
