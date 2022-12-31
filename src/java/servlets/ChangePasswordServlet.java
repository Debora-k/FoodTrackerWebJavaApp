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

/**
 *
 * @author debor
 */
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");     
        request.setAttribute("id", id);
        getServletContext().getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "Please log-in your account with new password!");
        String password = request.getParameter("password");
        String resetPasswordId = request.getParameter("id");
        
                      
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");

            String query = "UPDATE user SET password = ?, reset_password_id = NULL WHERE reset_password_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, password);
            statement.setString(2, resetPasswordId);
            
            statement.execute(); // because of DML, it should use .execute()

        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
            ex.printStackTrace();
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }



}
