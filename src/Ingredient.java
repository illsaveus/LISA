import java.util.ArrayList;
import java.sql.*;
import java.lang.*;

class Ingredient{

    private String name;
    private Integer type;
    private double min;
    private double max;
    private String measuringType;

    Ingredient(){}


    //        constructor
    Ingredient(String name, Integer type, double min, double max){
        this.name = name;
        this.type = type;
        this.min = min;
        this.max = max;
        this.measuringType = "Oz";
    }



//        getters

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    double getAmount(){
        return this.min;
    }

    public String getMeasuringType(){
        return measuringType;
    }

    public void setAmount(double newAmount) {
        Double namount = newAmount;
        this.min = namount;
    }

}







