import java.sql.*;
import java.util.ArrayList;

class Inventory{
    public ArrayList<Ingredient> itemlist = new ArrayList<Ingredient>();

    public static void main(String[] args) {
        Inventory new2 = new Inventory();
        ArrayList<Ingredient> loop = new2.getInventory();

//                loop array
        for (Ingredient g: loop) {
            System.out.println(g.getName());
            System.out.println(g.getType());
            System.out.println(g.getMin());
            System.out.println(g.getMax());
        }
    }


    Inventory(){
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
            query = "select * from Inventory";

//        create prepared statement
            stm = connection.createStatement();




//        Execute statement
            result = stm.executeQuery(query);


            while(result.next()){
                String name = result.getString("Name");
                Double min = result.getDouble("Quantity");
                Double max = result.getDouble("Max_Quantity");
                Integer type = 1;
                Ingredient item = new Ingredient(name,type,min,max);
                itemlist.add(item);
            }




        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public  ArrayList<Ingredient> getInventory(){
        return itemlist;

    }

}