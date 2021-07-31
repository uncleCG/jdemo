package com.demo.jackson;

public class DemoBean {

    private Integer watercount;
    private Integer electriccount;
    private Integer clodcount;
    private Integer hotcount;
    private Integer waterpercent;
    private Integer electricpercent;
    private Integer clodpercent;
    private Integer hotpercent;
    private Integer carbonemission;

    public Integer getWatercount() {
        return watercount;
    }

    public void setWatercount(Integer watercount) {
        this.watercount = watercount;
    }

    public Integer getElectriccount() {
        return electriccount;
    }

    public void setElectriccount(Integer electriccount) {
        this.electriccount = electriccount;
    }

    public Integer getClodcount() {
        return clodcount;
    }

    public void setClodcount(Integer clodcount) {
        this.clodcount = clodcount;
    }

    public Integer getHotcount() {
        return hotcount;
    }

    public void setHotcount(Integer hotcount) {
        this.hotcount = hotcount;
    }

    public Integer getWaterpercent() {
        return waterpercent;
    }

    public void setWaterpercent(Integer waterpercent) {
        this.waterpercent = waterpercent;
    }

    public Integer getElectricpercent() {
        return electricpercent;
    }

    public void setElectricpercent(Integer electricpercent) {
        this.electricpercent = electricpercent;
    }

    public Integer getClodpercent() {
        return clodpercent;
    }

    public void setClodpercent(Integer clodpercent) {
        this.clodpercent = clodpercent;
    }

    public Integer getHotpercent() {
        return hotpercent;
    }

    public void setHotpercent(Integer hotpercent) {
        this.hotpercent = hotpercent;
    }

    public Integer getCarbonemission() {
        return carbonemission;
    }

    public void setCarbonemission(Integer carbonemission) {
        this.carbonemission = carbonemission;
    }

    @Override
    public String toString() {
        return "RespEnergyUsedStatistics{" +
                "watercount=" + watercount +
                ", electriccount=" + electriccount +
                ", clodcount=" + clodcount +
                ", hotcount=" + hotcount +
                ", waterpercent=" + waterpercent +
                ", electricpercent=" + electricpercent +
                ", clodpercent=" + clodpercent +
                ", hotpercent=" + hotpercent +
                ", carbonemission=" + carbonemission +
                '}';
    }
}
