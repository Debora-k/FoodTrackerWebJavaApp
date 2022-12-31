package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author debor
 */
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/forgotPassword.jsp").forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "Please check your email. Email will be sent very soon!");
        String email = request.getParameter("email");
        String resetPasswordId = UUID.randomUUID().toString();
        
                      
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL JDBC driver
            
            String url = "jdbc:mysql://localhost:3306/fooddb";
            Connection con = DriverManager.getConnection(url,
                    "root", "password");

            String query = "UPDATE user SET reset_password_id = ? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, resetPasswordId);
            statement.setString(2, email);
            
            statement.execute(); // because of DML, it should use .execute()

        } catch (ClassNotFoundException ex) {
            // did not find JDBC driver
            ex.printStackTrace();
        } catch (SQLException ex) {
            // SQL exception
            ex.printStackTrace();
        }
        
        try {
            String username = "foodtracker@gmail.com";
            String password = "";

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.port", 465);
            props.put("mail.smtps.auth", "true");
            Session session = Session.getDefaultInstance(props);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            
            // SUBJECT
            message.setSubject("Reset Password Link");
            
            // CONTENT
            String content = "Click on this link to reset your password: http://localhost:8080/FoodTracker/changePassword?id=" + resetPasswordId;
            message.setText(content);
            
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }



}
