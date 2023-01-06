package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.FoodRecord;
import models.Nutrient;

/**
 *
 * @author debor
 */
public class NutritionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String date = request.getParameter("date");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            String query = "SELECT food_id, SUM(servings) FROM record"
                    + " WHERE owner = ? AND date = ? GROUP BY food_id";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, date);
            ResultSet rs = statement.executeQuery(); // run the SQL
            
            ArrayList<Nutrient> nutrients = new ArrayList<>();
            ArrayList<FoodRecord> foodRecords = new ArrayList<>();
            
            while (rs.next()) {
                int foodId = rs.getInt("food_id");
                int servings = rs.getInt("SUM(servings)");
                
                String query2 = "SELECT food_description FROM food WHERE food_id = ?";
                PreparedStatement statement2 = con.prepareStatement(query2);
                statement2.setInt(1, foodId);
                ResultSet rs2 = statement2.executeQuery();
                
                FoodRecord foodRecord = new FoodRecord();
                rs2.next();
                String foodDescription = rs2.getString("food_description");
                
                foodRecord.setFoodDescription(foodDescription);
                foodRecord.setServings(servings);
                foodRecords.add(foodRecord);

                
                String query3 = "SELECT nutrient_id, (nutrient_value * ?) AS value FROM nutrient_amount WHERE food_id = ? AND nutrient_id IN (203,204,205,208,269)";
                PreparedStatement statement3 = con.prepareStatement(query3);
                statement3.setInt(1, servings);
                statement3.setInt(2, foodId);
                ResultSet rs3 = statement3.executeQuery();
                
                while (rs3.next()) {
                    int nutrientId = rs3.getInt("nutrient_id");
                    double nutrientValue = rs3.getDouble("value");
                    
                    String query4 = "SELECT nutrient_name FROM nutrient WHERE nutrient_id = ?";
                    PreparedStatement statement4 = con.prepareStatement(query4);
                    statement4.setInt(1, nutrientId);
                    ResultSet rs4 = statement4.executeQuery();
                    
                    rs4.next();
                    String nutrientName = rs4.getString("nutrient_name");
                    
                    boolean addNutrient = true;
                    for(int i = 0; i < nutrients.size(); i++) {
                        Nutrient current = nutrients.get(i);
                        if(nutrientName.equals(current.getName())) {
                            current.setValue(nutrientValue += current.getValue());
                            addNutrient = false;
                            break;
                        }   
                    }
                    if (addNutrient == true) {
                        Nutrient nutrient = new Nutrient();
                        nutrient.setName(nutrientName);
                        nutrient.setValue(nutrientValue);
                        nutrients.add(nutrient);
                    }
                }
            }
            request.setAttribute("foodRecords", foodRecords);
            request.setAttribute("nutrients", nutrients);
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
        } catch (SQLException ex) {
            // SQL exception
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/nutrition.jsp").forward(request, response);

    }


}
