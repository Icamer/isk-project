package com.mycompany.isk.projekt;

import java.util.List;

public class DataTable1 {
    private Long routerId;
    private String linkIds;

    public DataTable1(Long routerId, String linkIds) {
        this.routerId = routerId;
        this.linkIds = linkIds;
    }

    public Long getRouterId() {
        return routerId;
    }

    public void setRouterId(Long routerId) {
        this.routerId = routerId;
    }

    public String getLinkIds() {
        return linkIds;
    }

    public void setLinkIds(String linkIds) {
        this.linkIds = linkIds;
    }
}
