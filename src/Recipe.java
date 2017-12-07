import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String image;
    private String mealType;
    private String instructions;


    Recipe() {
        this.name = "";
        this.ingredients = null;
        this.image = "";
        this.mealType = "";
        this.instructions = "Instructions go here";
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

    String getName() { return this.name; }

    ArrayList<Ingredient> getIngredients () { return this.ingredients; }

    String getImage () { return this.image; }


    String getMealType () { return this.mealType;}

    String getInstructions() { return this.instructions; }


}

