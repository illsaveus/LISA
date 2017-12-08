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

    public void setAmount(double newAmount) {
        Double namount = newAmount;
        this.amount = namount;
    }

    public void setMax(double newMax) {
        Double nmax = newMax;
        this.max = nmax;
    }

    public Ingredient() {

    }

    Ingredient(String name, String type, double amount, double max ){
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.max = max;
        this.measuringUnit = "oz";

    }

    Ingredient(String name, String type, String measuring_type, double amount, double max){
        this.name = name;
        this.type = type;
        this.measuringUnit = measuring_type;
        this.amount = amount;
        this.max = max;

    }

    @Override
    public String toString(){
        return	"Name: " + name + "\n" + "Type: " + type + "\n" + "Measuring_Type: " + measuringUnit + "\n";
    }


}
