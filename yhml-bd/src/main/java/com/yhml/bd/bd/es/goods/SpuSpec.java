package com.yhml.bd.bd.es.goods;

/**
 * Created by lvhantai on 2018/5/7.
 */
public class SpuSpec {
    private String specName;
    private String specValue;

    public SpuSpec() {
    }

    public SpuSpec(String specName, String specValue) {
        this.specName = specName;
        this.specValue = specValue;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    @Override
    public String toString() {
        return "SpuSpec{" +
                "specName='" + specName + '\'' +
                ", specValue='" + specValue + '\'' +
                '}';
    }
}
