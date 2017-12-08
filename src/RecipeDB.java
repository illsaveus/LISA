import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.util.Scanner;


public class RecipeDB {
    ArrayList<Recipe> recipes = new ArrayList<Recipe>() ;

    RecipeDB(Integer mealType) {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            //            initialize vars

            String query = null;
            PreparedStatement statement = null;
            ResultSet result = null;
            Statement stm;
            Boolean action = false;

            //        Connect
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.185.5.33:3306/eendy89_cs56", "eendy89_user", "lisalisa");

            //        Set query
            query = "SELECT * from Recipe where Meal_Type=? ";

            //        create prepared statement
            statement = connection.prepareStatement(query);
            statement.setInt(1, mealType);


            //        Execute statement
            result = statement.executeQuery();


            while (result.next()) {
                ArrayList<Ingredient> tmp = new ArrayList<>();
                Recipe item = new Recipe();
                item.name = result.getString("Name");
                item.image = result.getString("Image");
                String temp = result.getString("Instructions");
                item.instructions = "src/recipes/instructions/" + temp + ".txt";

                System.out.println(item.instructions);

                String query2 = "select * from Needed_Ingredients where Recipe =?";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setInt(1, result.getInt("Index"));
                ResultSet result2 = stmt2.executeQuery();


                while (result2.next()) {
                    Ingredient addIngredient = new Ingredient(result2.getString("Ingredient"), 0, result2.getInt("Quantity"), 0.00);
                    tmp.add(addIngredient);



                }
                item.ingredients = tmp;

                recipes.add(item);

            }

            for (Recipe g: recipes) {
                System.out.println(g.getName());
                System.out.println(g.getImage());
                System.out.println(g.getCookTime());
                System.out.println(g.getInstructions());
            }





        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public ArrayList<Recipe> returnRecipes() {
        return recipes;
    }
}