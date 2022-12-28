package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
                    
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
        
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");

            String query = "INSERT INTO user (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, fname);
            statement.setString(3, lname);
            statement.setString(4, password);
            
            statement.execute(); // because of DML, it should use .execute()

        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
            ex.printStackTrace();
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        request.setAttribute("message", "You are ready to use Food Tracker! Please log-in. :) ");
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }

}
