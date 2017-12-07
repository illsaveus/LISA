public class Ingredient {
    private String name;
    private double amount;
    private double max;
    private String type;
    private String measuringUnit;

    String getName(){
        return this.name;
    }

    double getAmount(){
        return this.amount;
    }

    double getMax(){
        return this.max;
    }

    String getType(){
        return this.type;
    }

    String getUnit() { return this.measuringUnit; }


    Ingredient(){

    }


    Ingredient(String name, String type, double amount, double max ){
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.max = max;
        this.measuringUnit = "oz";

    }


}
