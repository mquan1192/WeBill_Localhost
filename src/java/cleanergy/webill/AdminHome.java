/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Minh Quan
 */
public class AdminHome extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            String adminID = session.getAttribute("userID").toString();
            String adminGivenName = (String)session.getAttribute("userGivenName");
            String adminSurName = session.getAttribute("userSurName").toString();
            
            out.println("<html>\n" +
                        "    <head>\n" +
                        "        <title>WeBill: admin home</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <div style=\"border: 2px solid orangered; border-radius: 5px; background-color: lightyellow; width: 100%; float: left\">\n" +
                        "            <div style=\"float: left; width: 50%; background-color: transparent\">\n" +
                        "                <table>\n" +
                        "                    <tr>\n" +
                        "                        <td>\n" +
                        "                            <h1 style=\"color:green; margin-bottom: 0; margin-top: 0;\">Cleanergy</h1>\n" +
                        "                        </td>\n" +
                        "                        <td style=\"vertical-align: bottom; color: deeppink; white-space: nowrap;\">Clean Energy for Good Life.</td>\n" +
                        "                    </tr>\n" +
                        "                </table>\n" +
                        "            </div>\n" +
                        "            <div style=\"float: right; width: 50%; background-color: transparent\">\n" +
                        "                <p style=\"text-align: right; white-space: nowrap;\">Welcome "+ adminGivenName +" "+ adminSurName+" | <a href=\"edit_profile\">Profile</a> | <a href=\"logout\">Logout</a></p>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <hr style=\"color: transparent\">\n" +
                        "        <hr>\n" +
                        "        <div style=\"border: 2px solid orangered; border-radius: 5px; background-color: lightblue; width: 100%\">\n" +
                        "            <p></p>\n" +
                        "            <table style=\"width: 100%\">\n" +
                        "                <tr>\n" +
                        "                    <td style=\"text-align: center;\">\n" +
                        "                        <form method=\"post\" action=\"manage_maters\"  >\n" +
                        "                            <input type=\"submit\" value=\"Manage Meters\" style=\"background-color: lightgray; border: 5px double orangered; border-radius: 10px; font-size: 250%; white-space: normal;\">\n" +
                        "                        </form>\n" +
                        "                    </td>\n" +
                        "                    <td style=\"text-align: center;\">\n" +
                        "                        <form method=\"post\" action=\"manage_users\">\n" +
                        "                            <input type=\"submit\" value=\"Manage Consumers\"   style=\"background-color: lightgray; border: 5px double orangered; border-radius: 10px; font-size: 250%; white-space: normal;\">\n" +
                        "                        </form>\n" +
                        "                    </td>\n" +
                        "                    <td style=\"text-align: center\">\n" +
                        "                        <form method=\"post\" action=\"validate_reading\">\n" +
                        "                            <input type=\"submit\" value=\"Validate Readings\"  style=\"background-color: lightgray; border: 5px double orangered; border-radius: 10px; font-size: 250%; white-space: normal;\">\n" +
                        "                        </form>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "            <p></p>\n" +
                        "        </div>\n" +
                        "        <hr/>\n" +
                        "        <footer style=\"border: 2px solid orangered; border-radius: 5px; background-color: lightyellow; white-space: nowrap;\">Cleanergy IT Department</footer>\n" +
                        "    </body>\n" +
                        "</html>");
            
            
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

}
