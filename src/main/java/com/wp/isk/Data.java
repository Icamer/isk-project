package com.wp.isk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author w.podosek
 */
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    Long networkDestination;
    Long metric;
    Long through;
}
