package com.microwise.uma.common.util.cleanDB;

/**
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午5:07
 *
 * @author Basten Gao
 */
public class ColumnMetaData {
    //列名
    private String name;
    //列值的类型
    private String type;

    public ColumnMetaData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

 
    public String toString() {
        return "ColumnMetaData{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

