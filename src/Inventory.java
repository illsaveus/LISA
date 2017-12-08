
import java.sql.*;

import java.util.ArrayList;

public class Inventory {

    public static Connection connectToServer(String username, String password) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?" +
                    "user=" + username + "&password=" + password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public static Connection connectToServer(String network, String username, String password) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + network, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public static ArrayList<Recipe> getRecipes(Connection conn, String mealType) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String sqlQuery = "select Recipe.index, Recipe.name, Recipe.meal_type from Recipe where meal_type = ?;";
        int mType = Integer.parseInt(mealType);

        PreparedStatement getRcps = null;

        try {
            getRcps = conn.prepareStatement(sqlQuery);
            getRcps.setInt(1, mType);

            ResultSet rs = getRcps.executeQuery();

            while (rs.next()) {

                String ingQuery = "select ingredient.name, ingredient_type.type, inventory.quantity, inventory.max_quantity\n" +
                        "  from ((ingredient inner join ingredient_type on ingredient.type = ingredient_type.index)\n" +
                        "    inner join inventory on ingredient.name = inventory.name)\n" +
                        "    inner join needed_ingredients on ingredient.name = needed_ingredients.ingredient\n" +
                        "    where Recipe = ?;";

                int rcpID = rs.getInt("recipe_id");
                PreparedStatement getIngs = conn.prepareStatement(ingQuery);
                getIngs.setInt(1, rcpID);
                ResultSet rsI = getIngs.executeQuery();
                ArrayList<Ingredient> ings = new ArrayList<>();

                while (rsI.next()) {
                    Ingredient ing = new Ingredient(rs.getString("ingredient.name"),
                            rs.getString("ingredient_type.type"),
                            rs.getDouble("inventory.quantity"),
                            rs.getDouble("inventory.max_quantity"));
                    ings.add(ing);
                }

                String rcpName = rs.getString("Recipe.name");
                Recipe rcp = new Recipe(rcpName, "", mealType);
                rcp.addIngredients(ings);

                recipes.add(rcp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return recipes;
    }

    void updateInventory(String recipeID, Connection conn) {
        int rID = Integer.parseInt(recipeID);
        String sqlQuery = "update fridge";
    }

    public static ArrayList<Ingredient> getInventory(Connection conn) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        String sqlQuery = "select ingredient.name, ingredient_type.type, inventory.quantity, inventory.max_quantity\n" +
                "  from (ingredient inner join ingredient_type on ingredient.type = ingredient_type.index)\n" +
                "    inner join inventory on ingredient.name = inventory.name;";

        PreparedStatement getIngs = null;

        try {
            getIngs = conn.prepareStatement(sqlQuery);
            ResultSet rs = getIngs.executeQuery();

            while (rs.next()) {
                Ingredient ing = new Ingredient(rs.getString("ingredient.name"),
                        rs.getString("ingredient_type.type"),
                        rs.getDouble("inventory.quantity"),
                        rs.getDouble("inventory.max_quantity"));

                ingredients.add(ing);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }
}
