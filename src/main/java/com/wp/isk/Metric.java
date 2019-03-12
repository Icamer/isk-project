package com.wp.isk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author w.podosek
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    Long metric = 0L;
    Long routerId;
}
