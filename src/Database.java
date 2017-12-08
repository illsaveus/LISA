
import java.sql.*;
import java.util.*;
import java.util.ArrayList;

public class Database{

    public static String network = "192.185.5.33:3306";
    public static String username = "eendy89_user";
    public static String network_name = "eendy89_cs56";
    public static String password = "lisalisa";
    public Connection myConn;

    /**	getInventory will be associated with the Inventory table in the database.
     *
     *
     */
    public ArrayList getInventory(){
        Ingredient ing;
        ArrayList<Ingredient> inventory = new ArrayList<Ingredient>();

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Ingredient`");

            while (myResult.next()){
                ing = new Ingredient(myResult.getString("Name"),
                        myResult.getString("Type"),
                        myResult.getString("Measuring_type"),
                        myResult.getDouble("qty"),
                        myResult.getDouble("max_qty"));
                System.out.println(ing.toString());
                inventory.add(ing);
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
        //	Return ArrayList with Ingredient objects
        return inventory;
    }



	/*
	public void getInventory(){
		Ingredient ing;

		try{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
			Statement myStmt = myConn.createStatement();
			ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Ingredient`");

			while (myResult.next()){
				ing = new Ingredient(myResult.getString("Name"), myResult.getString("Type"), myResult.getString("Measuring_type"), myResult.getString("Image"));
				System.out.println(ing.toString());
			}

		}catch(Exception exc){
			exc.printStackTrace();
		}
		//	Return ArrayList with Ingredient objects
	}
	*/


    /**	getRecipes method returns all recipes in the database
     *
     *
     */
    public ArrayList getRecipes(){

        String index;
        String name;
        String image;
        String meal_type;
        String instructions;
        String prep_time;
        String cook_time;
        String ready_time;

        Recipe r;
        ArrayList<Recipe> recipe = new ArrayList<Recipe>();

        try{
            myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name +"`.`Recipe`");

            while (myResult.next()){
                r = new Recipe(myResult.getString("Index"), myResult.getString("Name"), myResult.getString("Image"), myResult.getString("Meal_Type"), myResult.getString("Instructions"), myResult.getString("Prep_Time"), myResult.getString("Cook_Time"), myResult.getString("Ready_Time"));
                recipe.add(r);
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }

        return recipe;
    }



    /**	The following methods display the TABLES in the database
     *
     */
    public void getIngredientTable(){

        String name;
        String type;
        String image;
        String measuring_type;

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Ingredient`");

            System.out.println("NAME, TYPE, IMAGE, MEASURING_TYPE");

            while (myResult.next()){

                name = myResult.getString("Name");
                type = myResult.getString("Type");
                image = myResult.getString("Image");
                measuring_type = myResult.getString("Measuring_Type");

                System.out.println(name + ", " + type + ", " + image + ", " + measuring_type);

            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void getIngredientTypeTable(){

        String index;
        String type;

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Ingredient_Type`");

            System.out.println("Index\t" + "Type\t\t\t");

            while (myResult.next()){

                index = myResult.getString("Index");
                type = myResult.getString("Type");

                System.out.println(index + "\t" + type);

            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    public void getInventoryTable(){
    }
    public void getMealTypeTable(){
    }
    public void getMeasuringTypeTable(){
    }
    public static void getNeededIngredientsTable(){
        String recipe;
        String ingredient;
        String quantity;

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Needed_Ingredients`");

            System.out.println("Recipe\t" + "Ingredient\t" + "Quantity");

            while (myResult.next()){

                recipe = myResult.getString("Recipe");
                ingredient = myResult.getString("Ingredient");
                quantity = myResult.getString("Quantity");

                System.out.println(recipe + "\t" + ingredient + "\t" + quantity + "\t");

            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }


    /**	The getNeededIngredients method is designed to get ingredients for a recipe
     *
     */
    public static ArrayList getNeededIngredients(String index){

        Ingredient ingredient;
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
            Statement myStmt = myConn.createStatement();
            ResultSet myResult = myStmt.executeQuery("select * from `" + network_name + "`.`Needed_Ingredients`");

            while (myResult.getString("Recipe") == index){

                String ing_name = myResult.getString("Ingredient");

                String name;
                String type;
                String image;
                String measuring_type;

                try{
                    Connection conn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
                    Statement stmt = myConn.createStatement();
                    ResultSet result = myStmt.executeQuery("select * from `" + network_name + "`.`Ingredient`");

                    name = result.getString("Name");
                    type = result.getString("Type");
                    image = result.getString("Image");
                    measuring_type = result.getString("Measuring_type");


                }catch(Exception e){
                    System.out.println("ERROR: No entry.");
                }
            }


        }catch(Exception exc){
            exc.printStackTrace();
        }

        return ingredients;
    }


    /**	The method to change data in the database
     *
     */
    public void writeToDatabase(){
    }

}
