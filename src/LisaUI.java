import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class LisaUI {
    private int h;
    private int w;

    private VBox menuBar;
    private VBox inventoryPage;
    private VBox recipesPage;
    private VBox mealTypePage;
    private VBox instructPg;
    public ButtonBase inventoryBtn;
    public Button recipesBtn;
    private BorderPane window;
    private ArrayList<Recipe> recipes;
    private ArrayList<Ingredient> inventory;
    private Inventory kInventory;

    private Connection conn;
    public static String network = "192.185.5.33:3306";
    public static String username = "eendy89_user";
    public static String network_name = "eendy89_cs56";
    public static String password = "lisalisa";

    //default height and width of app
    LisaUI(){
        this.h = 700;
        this.w = 300;
    }

    //takes in the height and width you want the app to be
    LisaUI(Inventory inventory, BorderPane win, int height, int width){

        this.h = height;
        this.w = width;
        this.window = win;


        this.kInventory = inventory;
        this.conn = kInventory.connectToServer(network, username, password);
        this.inventory = kInventory.getInventory(conn);



        //Create buttons
        inventoryBtn = new Button();
        recipesBtn = new Button();
        inventoryBtn.setOnAction(e -> inventoryBtnClicked() );
        recipesBtn.setOnAction(e -> recipesBtnClicked() );
    }

    void inventoryBtnClicked (){

        window.setCenter(inventoryPage);
        inventoryBtn.setId("inventory-open");
        recipesBtn.setId("recipes-closed");
    }

    void recipesBtnClicked (){

        window.setCenter(getMealTypePage());

        inventoryBtn.setId("inventory-closed");
        recipesBtn.setId("recipes-open");
    }

    //this method will add letter-spacing to any string and return the outpu
    String beautifyText (String text) {
        String output = "";
        for(int i = 0; i < text.length(); i++){

            output += text.charAt(i);
            output += " ";
        }

        return output;
    }

    //This method returns the inventoryPage layout UI with all the inventory objects added to it
    public VBox getInventoryPage(){

        ArrayList<Ingredient> items = inventory;

        int length = items.size(); //get size of arrayList

        inventoryPage = new VBox(); //create a layout for the inventory UI

        //loop through the arrayList to add objects to the layout
        for(int i = 0; i < length; i++){
            HBox singleIngredient = new HBox();

            //Ingredient Name

            Label itemName = new Label(beautifyText(items.get(i).getName().toUpperCase()));
            itemName.getStyleClass().add("header-item");

            //Ingredient Amount
            String amountInStock = Double.toString(items.get(i).getAmount()) + " " + items.get(i).getUnit();
            Label itemAmount = new Label(amountInStock);
            itemAmount.getStyleClass().add("header-item");


            Region filler1 = new Region(); // create a filler to align amounts to right side
            singleIngredient.setHgrow(filler1, Priority.ALWAYS);

            singleIngredient.getChildren().addAll(itemName, filler1, itemAmount);
            singleIngredient.setPadding(new Insets(00,20,10,20));

            //draw progress bar
            StackPane progressBar = new StackPane();
            Rectangle progressBG = new Rectangle(w-70, 40, Color.RED);
            progressBG.setArcHeight(40);
            progressBG.setArcWidth(40);
            StackPane.setAlignment(progressBG, Pos.CENTER_LEFT);
            progressBG.getStyleClass().add("progress-bar-bg");

            Rectangle itemProgress = new Rectangle(getProgress(items.get(i), w-80), 32, Color.BLUE);
            itemProgress.setArcHeight(30);
            itemProgress.setArcWidth(30);
            StackPane.setAlignment(itemProgress, Pos.CENTER_LEFT);
            itemProgress.getStyleClass().add("progress-bar");
            StackPane.setMargin(itemProgress, new Insets(0,0,1,5));

            progressBar.setPadding(new Insets(0,0,20,0));

            //add items to the page
            progressBar.getChildren().addAll(progressBG, itemProgress);
            inventoryPage.getChildren().add(singleIngredient);
            inventoryPage.getChildren().add(progressBar);

            inventoryPage.setSpacing(0.0);
        }

        inventoryPage.setPadding(new Insets (20, 20, 20, 20));

        return inventoryPage;
    }



    //this second getProgress method grabs the window size and resizes the progress bars, however nodes needs to be initialized first, so this is used afterwards
    double getProgress(Ingredient item){
        double width = window.getScaleY();
        System.out.println(width);

        return  (item.getAmount() / item.getMax()) * width;
    }

    double getProgress(Ingredient item, int width){

        return  (item.getAmount() / item.getMax()) * width;
    }

    //This method returns the recipePage layout UI with all the inventory objects added to it
    //It takes an ArrayList of recipes and a String of mealType to filter results with
    public VBox getRecipesPage(String userChoice){

        recipes = kInventory.getRecipes(conn, "1");
        int length = recipes.size(); //get size of arrayList

        recipesPage = new VBox(); //create a layout for the inventory UI

        //loop through the arrayList to add objects to the layout
        for(int i = 0; i < length; i++){
            HBox recipeRow = new HBox();
            System.out.println("creating a new recipe row. i is now " +  i);

            for(int rowCount = 0; rowCount < 2; ) {
                if(i >= length )
                    break;
                String pulledType = recipes.get(i).getMealType();
                System.out.println("grabbing item : " +  pulledType + " i : " + i + " rowCount : " + rowCount);

                if(pulledType == userChoice) {

                    //stack the name and photo on top of each other in this object
                    StackPane singleRecipe = new StackPane();
                    Region nameBG = new Region();
                    nameBG.getStyleClass().add("recipe-name-bg");


                    //item name needs an hBox to center text using padding, in this object
                    HBox itemNamePane = new HBox();
                    Region namePadding1 = new Region();
                    Region namePadding2 = new Region();
                    itemNamePane.setHgrow(namePadding1, Priority.ALWAYS);

                    itemNamePane.setHgrow(namePadding2, Priority.ALWAYS);
                    Label itemName = new Label(recipes.get(i).getName().toUpperCase());
                    //make sure text stays within bounds
                    itemName.setMaxWidth((w/2)-50); //set width to a portion of window size
                    itemName.setWrapText(true);


                    System.out.println("creating a recipe of : " +  itemName.getText());

                    itemName.getStyleClass().add("recipe-name");

                    //load button with recipe image
                    Button image = new Button();
                    image.getStyleClass().add("recipe-button");
                    String style = "-fx-background-image: url('" + recipes.get(i).getImage() + "');";
                    image.setStyle(style);
                    itemNamePane.setOnMouseEntered(e -> {

                        itemName.setId("recipeItem-active");
                        nameBG.setId("recipe-name-bg-active");
                    });
                    itemNamePane.setOnMouseExited(e -> {
                        itemName.setId("recipeItem-inactive");
                        nameBG.setId("recipe-name-bg-inactive");
                    });
                    Recipe thisRecipeButton = recipes.get(i);
                    itemNamePane.setOnMouseClicked(e -> getRInstructPg(thisRecipeButton));


                    image.setMinSize(500, 500);



                    //.setOnAction(e -> getRInstructPg(thisRecipeButton));
                    //add inventory item to the page
                    //recipesPage.getChildren().add(itemName);
                    itemNamePane.getChildren().addAll(namePadding1, itemName, namePadding2);
                    singleRecipe.getChildren().addAll(image, nameBG, itemNamePane);

                    recipeRow.getChildren().add(singleRecipe);



                    rowCount++;

                    if(rowCount > 1){
                        recipesPage.getChildren().add(recipeRow);

                        System.out.println("Made a row and added it");

                    } else {
                        i++;

                    }

                    //recipesPage.getChildren().add(singleRecipe);
                    //recipesPage.getChildren().add(image);
                } else {
                    System.out.println(pulledType + " is not a matche of " +  userChoice);
                    i++;
                }
            }

        }


        recipesPage.setPadding(new Insets (20, 35, 20, 35));


        return recipesPage;
    }



    //this method returns the layout for the top menu bar
    public VBox getMenuBar(){
        //Create a top title bar
        //GridPane titleBarLayout = new GridPane();
        menuBar = new VBox();

        HBox logoRow = new HBox();

        //filler regions to center logo
        Region filler1 = new Region();
        Region filler2 = new Region();
        logoRow.setHgrow(filler1, Priority.ALWAYS);
        logoRow.setHgrow(filler2, Priority.ALWAYS);

        //Logo Image Display
        Image header_logo = new Image("/ui/logo-header.jpg");
        ImageView headerImage = new ImageView();
        headerImage.setImage(header_logo);
        headerImage.getStyleClass().add("header-logo");

        logoRow.getChildren().addAll(filler1, headerImage, filler2);

        menuBar.getChildren().add(logoRow);
        //GridPane.setConstraints(headerImage, 1, 0); //middle top

        //titleBarLayout.add(headerImage, 1,0, 4, 1 );

        //add buttons to top menu bar
        HBox menuButtons = new HBox();
        Region filler3 = new Region();
        Region filler4 = new Region();
        menuButtons.setHgrow(filler3, Priority.ALWAYS);
        menuButtons.setHgrow(filler4, Priority.ALWAYS);

        //titleBarLayout.add(inventoryBtn, 1, 1);
        //GridPane.setConstraints(inventoryBtn, 1, 1); //left bottom
        inventoryBtn.getStyleClass().add("inventory-btn");
        inventoryBtn.setId("inventory-open");


        //titleBarLayout.add(recipesBtn, 3, 1);
        //GridPane.setConstraints(recipesBtn, 3, 1); // right bottom
        recipesBtn.getStyleClass().add("recipes-btn");
        recipesBtn.setId("recipes-closed");


        menuButtons.getChildren().addAll(filler3, inventoryBtn, recipesBtn, filler4);
        menuButtons.setPadding(new Insets (20, 0, 0, 0));

        menuBar.getChildren().add(menuButtons);


        //titleBarLayout.getChildren().addAll(headerImage, inventoryBtn, recipesBtn);

        //return titleBarLayout; //return the gridPane layout objs

        menuBar.setPadding(new Insets (20, 0, 10, 0));
        return menuBar;

    }


    //Display Meal Type Page

    public VBox getMealTypePage() {
        //add image to button
        mealTypePage = new VBox();
        Image bImage = new Image(getClass().getResourceAsStream("imgs/breakfast1.jpg"));
        Image lImage = new Image(getClass().getResourceAsStream("/imgs/mealType-lunch.jpg"));
        Image dImage = new Image(getClass().getResourceAsStream("imgs/dinner1.jpg"));

        //START BREAKFAST BUTTON
            StackPane breakfastPane = new StackPane();
            Region breakfastBG = new Region();
            breakfastBG.getStyleClass().add("mealType-bg");
            String bg = "-fx-background-image: url('/imgs/mealType-breakfast.jpg');";
            breakfastBG.setStyle(bg);

            Region breakfastBGcolor = new Region();
            breakfastBGcolor.getStyleClass().add("mealType-bgcolor");

            HBox breakfastBox = new HBox();
            Region breakfastPadding1 = new Region();
            Region breakfastPadding2 = new Region();
            breakfastBox.setHgrow(breakfastPadding1, Priority.ALWAYS);
            breakfastBox.setHgrow(breakfastPadding2, Priority.ALWAYS);

            Label breakfastLable = new Label("B R E A K F A S T");
            breakfastLable.getStyleClass().add("mealType-label");
            breakfastLable.setMaxWidth(w-50);
            breakfastLable.setWrapText(true);

            breakfastBox.setOnMouseEntered(e -> {
                breakfastBGcolor.setId("mealType-bgcolor-active");
            });

            breakfastBox.setOnMouseExited(e -> {
                breakfastBGcolor.setId("mealType-bgcolor-inactive");
            });

            breakfastBox.setOnMouseClicked(e -> window.setCenter(getRecipesPage("breakfast")));

            breakfastBox.getChildren().addAll(breakfastPadding1, breakfastLable, breakfastPadding2);
            breakfastPane.getChildren().addAll(breakfastBG, breakfastBGcolor, breakfastBox);

        //END BREAKFAST BUTTON

        //START LUNCH BUTTON
            StackPane lunchPane = new StackPane();
            Region lunchBG = new Region();
            lunchBG.getStyleClass().add("mealType-bg");
            bg = "-fx-background-image: url('/imgs/mealType-lunch.jpg');";
            lunchBG.setStyle(bg);

            Region lunchBGcolor = new Region();
            lunchBGcolor.getStyleClass().add("mealType-bgcolor");

            HBox lunchBox = new HBox();
            Region lunchPadding1 = new Region();
            Region lunchPadding2 = new Region();
            lunchBox.setHgrow(lunchPadding1, Priority.ALWAYS);
            lunchBox.setHgrow(lunchPadding2, Priority.ALWAYS);

            Label lunchLable = new Label("L U N C H");
            lunchLable.getStyleClass().add("mealType-label");
            lunchLable.setMaxWidth(w-50);
            lunchLable.setWrapText(true);

            lunchBox.setOnMouseEntered(e -> {
                lunchBGcolor.setId("mealType-bgcolor-active");
            });

            lunchBox.setOnMouseExited(e -> {
                lunchBGcolor.setId("mealType-bgcolor-inactive");
            });

        lunchBox.setOnMouseClicked(e -> window.setCenter(getRecipesPage("lunch")));

            lunchBox.getChildren().addAll(lunchPadding1, lunchLable, lunchPadding2);
            lunchPane.getChildren().addAll(lunchBG, lunchBGcolor, lunchBox);

        //END LUNCH BUTTON

        //START DINNER BUTTON
            StackPane dinnerPane = new StackPane();
            Region dinnerBG = new Region();
            dinnerBG.getStyleClass().add("mealType-bg");
            bg = "-fx-background-image: url('/imgs/mealType-dinner.jpg');";
            dinnerBG.setStyle(bg);

            Region dinnerBGcolor = new Region();
            dinnerBGcolor.getStyleClass().add("mealType-bgcolor");

            HBox dinnerBox = new HBox();
            Region dinnerPadding1 = new Region();
            Region dinnerPadding2 = new Region();
            dinnerBox.setHgrow(dinnerPadding1, Priority.ALWAYS);
            dinnerBox.setHgrow(dinnerPadding2, Priority.ALWAYS);

            Label dinnerLable = new Label("D I N N E R");
            dinnerLable.getStyleClass().add("mealType-label");
            dinnerLable.setMaxWidth(w-50);
            dinnerLable.setWrapText(true);

            dinnerBox.setOnMouseEntered(e -> {
                dinnerBGcolor.setId("mealType-bgcolor-active");
            });

            dinnerBox.setOnMouseExited(e -> {
                dinnerBGcolor.setId("mealType-bgcolor-inactive");
            });

            dinnerBox.setOnMouseClicked(e -> window.setCenter(getRecipesPage("lunch")));

            dinnerBox.getChildren().addAll(dinnerPadding1, dinnerLable, dinnerPadding2);
            dinnerPane.getChildren().addAll(dinnerBG, dinnerBGcolor, dinnerBox);

        //END dinner BUTTON

        Button bButton = new Button("Breakfast");
        bButton.setGraphic(new ImageView(bImage));

        Button lButton = new Button("Lunch");
        lButton.setGraphic(new ImageView(lImage));

        Button dButton = new Button("Dinner");
        dButton.setGraphic(new ImageView(dImage));

        bButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("bButton clicked");
                window.setCenter(getRecipesPage("breakfast"));
            }
        });

        lButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("lButton clicked");
                window.setCenter(getRecipesPage("lunch"));


            }
        });

        dButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("dButton clicked");
                window.setCenter(getRecipesPage("dinner"));

            }
        });

        mealTypePage.getChildren().addAll(breakfastPane, lunchPane, dinnerPane);

        mealTypePage.setPadding(new Insets (20, 35, 20, 35));
        return mealTypePage;
    }

    public void getRInstructPg(Recipe recipeChoice) {
        instructPg = new VBox();
        StackPane header = new StackPane();
        HBox txtHolder = new HBox();
        //Recipe recipeTest = recipeList.get(0);
        String rName = beautifyText(recipeChoice.getName().toUpperCase());

        String instructionsHeaderImg = "-fx-background-image: url('" + recipeChoice.getImage() + "');";
        //Image rImage = new Image(recipeChoice.getImage());

        Region rImage = new Region();
        rImage.getStyleClass().add("instructions-header-img");
        rImage.setStyle(instructionsHeaderImg);


        Region imageOverlay = new Region();
        imageOverlay.getStyleClass().add("instructions-header-overlay");

        HBox recipeNamePane = new HBox();
        Region namePadding1 = new Region();
        Region namePadding2 = new Region();
        recipeNamePane.setHgrow(namePadding1, Priority.ALWAYS);
        recipeNamePane.setHgrow(namePadding2, Priority.ALWAYS);

        Label titleLabel = new Label(rName);
        titleLabel.getStyleClass().add("instructions-title");
        txtHolder.getChildren().addAll(titleLabel);
        titleLabel.setMaxWidth(600); //set width to a portion of window size
        titleLabel.setWrapText(true);

        recipeNamePane.getChildren().addAll(namePadding1, titleLabel, namePadding2);
        header.getChildren().addAll(rImage, imageOverlay, recipeNamePane);

        //INGREDIENTS LIST
            VBox ingredientArea = new VBox();


            Label ingrLabel = new Label("INGREDIENTS");
            ingrLabel.getStyleClass().add("Instructions-header");
            ingredientArea.getChildren().add(ingrLabel);


            ArrayList<Ingredient> items = recipeChoice.getIngredients();
            for(int i = 0; i < items.size(); i++){
                HBox singleIngredient = new HBox();

                Label itemName = new Label(items.get(i).getName());
                itemName.getStyleClass().add("ingredient-items");

                String amountInStock = Double.toString(items.get(i).getAmount()) + " " + items.get(i).getUnit();
                Label itemAmount = new Label(amountInStock);
                itemAmount.getStyleClass().add("ingredient-items");

                Region filler1 = new Region();
                singleIngredient.setHgrow(filler1, Priority.ALWAYS);

                singleIngredient.getChildren().addAll(itemName, filler1, itemAmount);
                singleIngredient.setPadding(new Insets(00, 20, 10, 20));

                ingredientArea.getChildren().add(singleIngredient);
                ingredientArea.setSpacing(0.0);

            }

        //


        VBox instructArea = new VBox();
        Label instructLabel = new Label("INSTRUCTIONS");
        instructLabel.getStyleClass().add("Instructions-header");
        HBox instructPara = new HBox();
        //add recipe instructions to instructPara
        Label instructText = new Label(recipeChoice.getInstructions());
        instructText.setMaxWidth(600); //set width to a portion of window size
        instructText.setWrapText(true);
        instructArea.getChildren().addAll(instructLabel, instructPara, instructText);


        HBox bottomPart = new HBox();
        Region filler1 = new Region();
        Region filler2 = new Region();

        bottomPart.setHgrow(filler1, Priority.ALWAYS);
        bottomPart.setHgrow(filler2, Priority.ALWAYS);
        Button finished = new Button("finished");

        finished.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                System.out.println("finished with the recipe");
                inventoryBtnClicked(); //change to inventory page
            }
        });

        finished.setId("record-sales");
        bottomPart.getChildren().addAll(filler1, finished, filler2);

        instructPg.getChildren().addAll(header, ingredientArea, instructArea, bottomPart);

        instructPg.setPadding(new Insets (20, 35, 20, 35));

        window.setCenter(instructPg);
    }
}
