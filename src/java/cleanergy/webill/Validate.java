package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Muhammad Wannous
 */
public class Validate extends HttpServlet {

    private static Connection sqlConnection = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //to access the session
            HttpSession session = request.getSession(true);
            //Get the parameters from the input request
            //We expect 3 parameters: userID, passWord, and userRole
            String userID = request.getParameter("userID");
            String secret = request.getParameter("passWord");
            String role = request.getParameter("userRole");
            //The password is saved in the db as MD5 string, but it is received as normal text. So we need to convert.
            byte[] md5SecretBytes = MessageDigest.getInstance("MD5").digest(secret.getBytes());
            String md5Secret;
            StringBuilder strBuilder = new StringBuilder();
            String tmpStr;
            for (int i = 0; i < md5SecretBytes.length; i++) {
                tmpStr = Integer.toHexString(0xFF & md5SecretBytes[i]);
                if (tmpStr.length() == 1) {
                    strBuilder.append('0');
                }
                strBuilder.append(tmpStr);
            }
            //The password in MD5 format at last.
            md5Secret = strBuilder.toString();
            if (!userID.isEmpty() 
                    && !secret.isEmpty() 
                    && !role.isEmpty() 
                    && userID.matches("[a-z]{1,}[.a-z0-9]*@[a-z]{1,}.[a-z0-9]{1,}[.a-z0-9]*[a-z0-9]{1,}")) {
                //The connection to the db was initiated in init(), so we can run a query.
                //We search for the specified user 
                String idSecretMatchQuery = "select * from " + 
                        (role.equalsIgnoreCase("Admin") ? "admins" : "consumers") + 
                        " where email='" + userID + "' and secret='" + md5Secret + "';";
                ResultSet matchingUser = sqlConnection.createStatement().executeQuery(idSecretMatchQuery);
                if (matchingUser.next()) {
                    session.setAttribute("userID", matchingUser.getString("i"));
                    session.setAttribute("userGivenName", matchingUser.getString("givenName"));
                    session.setAttribute("userSurName", matchingUser.getString("surName"));
                    response.sendRedirect((role.equalsIgnoreCase("Admin") ? "admin_home" : "consumer_home"));
                    out.println("-Welcome " + matchingUser.getString("givenName")
                            + " " + matchingUser.getString("surName"));
                }
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            System.err.println("Error in the Servlet");
            System.err.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init();
        if (sqlConnection == null) {
            System.out.println("Establishing new connection to the database...");
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/WeBill?"
                        + "user=root&password=password&useSSL=false");
                System.out.println("The connection was successfully established.");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                System.err.println("Error in establishing the connection to the database! Details follow.");
                System.err.println(ex.getMessage());
            }
        }
    }

}