import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class Recipe {
    String ID;
     String name;
     ArrayList<Ingredient> ingredients;
     ArrayList<Recipe> recipes;
     String image;
     String mealType;
      String instructions;
    String prep_time;
    String cook_time;
    String ready_time;



    Recipe() {
        this.name = "";
        this.ingredients = null;
        this.image = "";
        this.mealType = "";
        this.instructions = "Instructions go here";
    }

    Recipe(String name, String nothing, String mealType){
        this.name = name;
        this.mealType = mealType;
    }

    Recipe(String name, String image, String mealType, ArrayList<Ingredient>ingredients){
        this.name = name;
        this.image = "/imgs/" + image;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.instructions = "Bring the water to a boil and then reduce the heat to simmer.\n\n" +
                "Gently stir in the oats. If the water starts to bubble over, reduce the heat even further.\n\n" +
                "Cook for about 4 minutes, stirring occasionally.\n\n" +
                "Stir in the dash of salt, and scrape the oatmeal into a bowl.\n\n" +
                "Float the milk across the top and add your favorite add-ins and toppings. ";
    }

    Recipe(String ID, String name, String image, String meal_type, String instructions, String prep_time, String cook_time, String ready_time){
        this.ID = ID;
        this.name = name;
        this.image = image;
        this.mealType = meal_type;
        this.instructions = instructions;
        this.cook_time = prep_time;
        this.prep_time = cook_time;
        this.ready_time = ready_time;
    }

    public void setIngredients(ArrayList ingredients){
        this.ingredients = ingredients;

    }

    public String getID(){
        return ID;
    }

    String getName() { return this.name; }

    ArrayList<Ingredient> getIngredients () { return this.ingredients; }

    String getImage () { return "/recipes/imgs/" + this.image + ".jpg"; }

    void addIngredients(ArrayList<Ingredient> i){
        this.ingredients = i;
    }


    String getMealType () { return this.mealType;}

    //String getInstructions() { return this.instructions; }
    public String getInstructions(){
        String output = "";

        try{
            System.out.println(instructions);
            Scanner scanner = new Scanner(new File(instructions));

            while(scanner.hasNext()){
                //System.out.println(scanner.nextLine() + "\n");
                output = output + scanner.nextLine() + "\n";
            }
        }catch(Exception e){
            System.out.println("Warning: No file found.");
        }

        return output;
    }

    public String getPrepTime(){
        return prep_time;
    }
    public String getCookTime(){
        return cook_time;
    }
    public String getReadyTime(){
        return ready_time;
    }







}

