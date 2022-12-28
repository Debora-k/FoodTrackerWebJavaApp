package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email != null) {
            // user is already logged in
            response.sendRedirect("journal");
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get input from POST request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");
            
            // SELECT * FROM user WHERE email = 'deb@gmail.com' AND password = 'pw'
//            String query1 = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
//            Statement statement1 = con.createStatement();
//            ResultSet rs1 = statement1.executeQuery(query1);
            
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            
            ResultSet rs = statement.executeQuery(); // run the SQL
            boolean foundRow = rs.next(); // fetch/get the next result

            if (foundRow) {
                // email and password correct!
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                response.sendRedirect("journal");
                return;
            }
            
            
        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
        } catch (SQLException ex) {
            // SQL exception
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
