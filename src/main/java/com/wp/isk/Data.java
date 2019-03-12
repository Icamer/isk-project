package com.wp.isk;

/**
 * @author w.podosek
 */
public class Data {
    Long networkDestination;
    Long metric;
    Long through;

    public Data(Long networkDestination, Long metric, Long through) {
        this.networkDestination = networkDestination;
        this.metric = metric;
        this.through = through;
    }

    public Long getNetworkDestination() {
        return networkDestination;
    }

    public void setNetworkDestination(Long networkDestination) {
        this.networkDestination = networkDestination;
    }

    public Long getMetric() {
        return metric;
    }

    public void setMetric(Long metric) {
        this.metric = metric;
    }

    public Long getThrough() {
        return through;
    }

    public void setThrough(Long through) {
        this.through = through;
    }
}
