import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application{

    Stage window;
    Scene homeScene;

    //default size of app window
    int window_h = 900;
    int window_w = 700;



    public static void main(String[] args) {
        launch(args);
    }

    //this method returns an arrayList with all the items in the users inventory
    //this is a temporary method and should be deleted after the proper methods are included
    /*
    public ArrayList<Ingredient> getInventory(){
        ArrayList<Ingredient> itemList = new ArrayList<Ingredient>();


        Ingredient item1 = new Ingredient("Sausage", "First", 2.0, 8.0);
        itemList.add(item1);

        Ingredient item2 = new Ingredient("Beef", "First", 4.0, 8.0);
        itemList.add(item2);

        Ingredient item3 = new Ingredient("Pasta", "Second", 4.0, 4.0);
        itemList.add(item3);

        Ingredient item4 = new Ingredient("Cheese", "Second", 1.0, 4.0);
        itemList.add(item4);

        Ingredient item5 = new Ingredient("Green Beans", "Second", 2.0, 8.0);
        itemList.add(item5);

        Ingredient item6 = new Ingredient("Bread", "Second", 4.0, 8.0);
        itemList.add(item6);

        Ingredient item7 = new Ingredient("Pasta Sauce", "Third", 4.0, 4.0);
        itemList.add(item7);

        Ingredient item8 = new Ingredient("Lettuce", "Third", 1.0, 4.0);
        itemList.add(item8);

        return itemList;
    }
    */

    /*
    //Temporary method that will return a list of recipes to display in RecipesPage
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Ingredient> ingredients = getInventory();
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        Recipe b1 = new Recipe("The classic american heart attack", "breakfast1.jpg", "breakfast", ingredients);
        Recipe b2 = new Recipe("Porridge gloop", "breakfast2.jpg", "breakfast", ingredients);
        Recipe b3 = new Recipe("Eggs with everything that's bad for you", "breakfast3.jpg", "breakfast", ingredients);
        Recipe b4 = new Recipe("Pancakes and Sysrp", "breakfast4.jpg", "breakfast", ingredients);

        Recipe l1 = new Recipe("Some bread with peanut and berry paste", "lunch1.jpg", "lunch", ingredients);
        Recipe l2 = new Recipe("Honestly don't know what this is but hey a donut", "lunch2.jpg", "lunch", ingredients);
        Recipe l3 = new Recipe("It's just a sandwich", "lunch3.jpg", "lunch", ingredients);
        Recipe l4 = new Recipe("Bread with guac and whatever's in the fridge", "lunch4.jpg", "lunch", ingredients);

        Recipe r1 = new Recipe("Spaghetti Bolonese with A Spicy Meat Ball", "dinner1.jpg", "dinner", ingredients);
        Recipe r2 = new Recipe("Some delicious steak my mom made that one time", "dinner2.jpg", "dinner", ingredients);
        Recipe r3 = new Recipe("Beef and peas with some yellow stuff", "dinner3.jpg", "dinner", ingredients);
        Recipe r4 = new Recipe("A big disgusting burger your dad will love", "dinner4.jpg", "dinner", ingredients);

        recipeList.add(b1);
        recipeList.add(b2);
        recipeList.add(b3);
        recipeList.add(b4);

        recipeList.add(l1);
        recipeList.add(l2);
        recipeList.add(l3);
        recipeList.add(l4);

        recipeList.add(r1);
        recipeList.add(r2);
        recipeList.add(r3);
        recipeList.add(r4);

        return recipeList;
    }
    */

    @Override
    public void start (Stage primaryStage) {
        window = primaryStage;
        window.setTitle("LISA Cooks");

        //grab inventory and put it in an arrayList I can use
        //ArrayList<Ingredient> inventoryList = getInventory();


        //inventoryList = inventory.getInventory();
        Inventory inventory = new Inventory();





        //****DRAW UI****//
        //use borderPane as main layout
        ScrollPane scrollingWindow = new ScrollPane();
        BorderPane mainApp = new BorderPane();


        //create a LisaUI object - this is the entire app UI - send it the size of the entire app and the main layout
        LisaUI ui = new LisaUI(inventory, mainApp, window_h, window_w);



        mainApp.setTop(ui.getMenuBar()); // draw top menu bar

        ui.getRecipesPage("1");

        mainApp.setCenter(ui.getInventoryPage()); // draw inventory display, pass it the inventory arrayList

        scrollingWindow.setContent(mainApp);
        //********END UI***********//




        //Create and init the home scenew
        homeScene = new Scene(scrollingWindow, window_w, window_h);
        //homeScene.getStylesheets().add("LisaUI.css");
        scrollingWindow.getStylesheets().add("LisaUI.css");
        window.setScene(homeScene);
        window.show();



    }


}
