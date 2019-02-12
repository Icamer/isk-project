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
 * @author Marcin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Link {
    Long linkId;
    Long idServerOne;
    Long idServerTwo;
    Boolean isWorking;
}
