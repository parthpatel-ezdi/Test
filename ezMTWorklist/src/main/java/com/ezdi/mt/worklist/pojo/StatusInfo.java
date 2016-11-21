package com.ezdi.mt.worklist.pojo;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by EZDI\manimaran.s on 2/9/16.
 */
@Table("ez_status_master")
public class StatusInfo {

    @PrimaryKey("status_id")
    private Integer statusId;

    @Column("milestone_id")
    private Integer milestoneId;

    @Column("milestone_value")
    private String milestoneValue;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getMilestoneValue() {
        return milestoneValue;
    }

    public void setMilestoneValue(String milestoneValue) {
        this.milestoneValue = milestoneValue;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusInfo)) return false;

        StatusInfo that = (StatusInfo) o;

        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return statusId != null ? statusId.hashCode() : 0;
    }
}
