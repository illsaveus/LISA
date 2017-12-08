import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class Recipe {
    String ID;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String image;
    private String mealType;
    private static String instructions;
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

    String getImage () { return this.image; }

    void addIngredients(ArrayList<Ingredient> i){
        this.ingredients = i;
    }


    String getMealType () { return this.mealType;}

    //String getInstructions() { return this.instructions; }
    public static String getInstructions(){
        String output = "";
        try{
            Scanner scanner = new Scanner(new File(instructions + ".txt"));

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



    public ArrayList<Recipe> getRecipes(Connection conn, String mealType) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String ingIDSQLList = "(";
        String sqlQuery = "select recipe_id, name, mealtype from recipes where mealtype = ?";

        PreparedStatement getRcps = null;

        try {
            getRcps = conn.prepareStatement(sqlQuery);
            getRcps.setString(1, mealType);

            ResultSet rs = getRcps.executeQuery();

            while (rs.next()) {
                Recipe rcp = new Recipe();
                String ingQuery = "select name, type, qty\n" +
                        "  from ingredients inner join ingredients_needed\n" +
                        "  where recipe_id = ?;";

                int rcpID = rs.getInt("recipe_id");
                PreparedStatement getIngs = conn.prepareStatement(ingQuery);
                getIngs.setInt(1, rcpID);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return recipes;
    }
}

