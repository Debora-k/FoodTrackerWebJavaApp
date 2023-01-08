
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.FoodRecord;

/**
 *
 * @author debor
 */
public class JournalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        //security
        if(email == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        } 
        
        ArrayList<String> foods = new ArrayList<>();
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
            String query = "SELECT food_description FROM food WHERE food_description LIKE '%" + search + "%'";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery(); // run the SQL
            while (rs.next()) {
                String food = rs.getString("food_description");
                foods.add(food);
            }
            
            request.setAttribute("foods", foods);
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
            ex.printStackTrace();
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            String query = "SELECT date, food_id, servings FROM record WHERE owner = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);        
            ResultSet rs = statement.executeQuery(); // run the SQL
            
            
            
            ArrayList<FoodRecord> foodRecords = new ArrayList<>();

            while (rs.next()) {
                Date date = rs.getDate(1);
                int food_id = rs.getInt(2);
                int servings = rs.getInt(3);
                String query2 = "SELECT food_description FROM food WHERE food_id = " + food_id;
                PreparedStatement statement2 = con.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                
                rs2.next();
                String food_description = rs2.getString("food_description");
               
                
                String query3 = "SELECT nutrient_value FROM nutrient_amount WHERE food_id = " + food_id + " AND nutrient_id = 208";
                PreparedStatement statement3 = con.prepareStatement(query3);
                ResultSet rs3 = statement3.executeQuery();
                
                rs3.next();
                double calories = rs3.getDouble("nutrient_value");
                
                FoodRecord foodRecord = new FoodRecord();
                foodRecord.setDate(date);
                foodRecord.setFoodDescription(food_description);
                foodRecord.setServings(servings);
                foodRecord.setCalories(calories);
                
                foodRecords.add(foodRecord);
                
            }
            String typeOfSort = request.getParameter("sort");
            if(typeOfSort == null) {
                typeOfSort = "Date";
            }
            request.setAttribute("sort", typeOfSort);
            if(typeOfSort.equals("Date")) {
                foodRecords.sort(new DateComparator());
            } else if(typeOfSort.equals("Food")) {
                foodRecords.sort(new FoodComparator());
            } else if(typeOfSort.equals("Servings")) {
                foodRecords.sort(new ServingsComparator());
            } else if(typeOfSort.equals("Calories")) {
                foodRecords.sort(new CaloriesComparator());
            }
            request.setAttribute("foodRecords", foodRecords);
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
            ex.printStackTrace();
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/journal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        HttpSession session = request.getSession();
        String owner = (String) session.getAttribute("email");
        //security
        if(owner == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        } 
        
        try {
            String date = request.getParameter("date");
            String food_description = request.getParameter("food");
            int servings = Integer.parseInt(request.getParameter("servings"));
        
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            String query = "SELECT food_id FROM food WHERE food_description = '" + food_description + "'";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery(); // run the SQL
            rs.next();
            int food_id = rs.getInt("food_id");
            
            String query2 = "INSERT INTO record (owner, food_id, date, servings) VALUES (?, ?, ?, ?)";
            PreparedStatement statement2 = con.prepareStatement(query2);
            statement2.setString(1, owner);
            statement2.setInt(2, food_id);
            statement2.setString(3, date);
            statement2.setInt(4, servings);
            
            statement2.execute(); // because of DML, it should use .execute()

        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
        } catch (SQLException ex) {
            // SQL exception
        }
        
        ArrayList<String> foods = new ArrayList<>();
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            String query = "SELECT food_description FROM food";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery(); // run the SQL
            while (rs.next()) {
                String food = rs.getString("food_description");
                foods.add(food);
            }
            
            request.setAttribute("foods", foods);
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
        } catch (SQLException ex) {
            // SQL exception
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            String query = "SELECT date, food_id, servings FROM record WHERE owner = ?";
            PreparedStatement statement = con.prepareStatement(query);
            String email = (String) session.getAttribute("email");
            statement.setString(1, email);        
            ResultSet rs = statement.executeQuery(); // run the SQL
            

            ArrayList<FoodRecord> foodRecords = new ArrayList<>();

            while (rs.next()) {
                Date date = rs.getDate(1);
                int food_id = rs.getInt(2);
                int servings = rs.getInt(3);
                String query2 = "SELECT food_description FROM food WHERE food_id = " + food_id;
                PreparedStatement statement2 = con.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                
                rs2.next();
                String food_description = rs2.getString("food_description");
               
                
                String query3 = "SELECT nutrient_value FROM nutrient_amount WHERE food_id = " + food_id + " AND nutrient_id = 208";
                PreparedStatement statement3 = con.prepareStatement(query3);
                ResultSet rs3 = statement3.executeQuery();
                
                rs3.next();
                double calories = rs3.getDouble("nutrient_value");
                
                FoodRecord foodRecord = new FoodRecord();
                foodRecord.setDate(date);
                foodRecord.setFoodDescription(food_description);
                foodRecord.setServings(servings);
                foodRecord.setCalories(calories);
                
                foodRecords.add(foodRecord);
                
            }
            
            request.setAttribute("foodRecords", foodRecords);
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/journal.jsp").forward(request, response);
    }

}
