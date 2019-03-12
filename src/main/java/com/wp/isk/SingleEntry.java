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

/**
 * @author w.podosek
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

public class SingleEntry {

    //    String netmask;
//    String gateway;
    Long networkDestination;
    Long thisRouterId;
    Long metric;
    Long through;

    Long iteration;
    Long interfaceId;
    Long hash;
}
