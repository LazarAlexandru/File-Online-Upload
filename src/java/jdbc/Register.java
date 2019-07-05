/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 *
 * @author Alex
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})

public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param args
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
       try {
            String name=request.getParameter("name");
            String password=request.getParameter("password");
            String gender=request.getParameter("gender");
            String email=request.getParameter("email");
            String statement="insert into utilizator(id,name,password,gender,email) values(?,?,?,?,?)";
            String dbURL="jdbc:derby://localhost:1527/FileUploadDB;create=true;user=alex;password=Tim4797";
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection connection=DriverManager.getConnection(dbURL);
            PreparedStatement pa=connection.prepareStatement(statement);
            Random random=new Random();
            
            pa.setInt(1,random.nextInt((99999 - 10000) + 1) + 10000);
            pa.setString(2,name);
            pa.setString(3,password);
            pa.setString(4, gender);
            pa.setString(5, email);
           pa.executeUpdate();
          
            
            PrintWriter out=response.getWriter();
             out.write("<html>");
             out.write("<head>");
	out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
	out.write("<title>Register</title>");
out.write("</head>");
out.write("<body>");
	out.write("<ul class=\"navbar\">");
	out.write("<li class=\"navbar\"><a href=\"index.jsp\" class=\"scale_transition home navbar\">Home</a></li>");
		out.write("<li class=\"navbar\"><a href=\"login.jsp\" class=\"scale_transition login navbar\">Login</a></li>");
	out.write("</ul>");

	out.write("<div align=\"center\" class=\"registerform\">");
		out.write("<p>Register</p>");
		out.write("<form >");
			out.write("<table>");
				out.write("<tr>");
					out.write("<td>");
						out.write("Name :");
					out.write("</td>");

					out.write("<td>");
						out.write("<input class=\"input\" type=\"text\" placeholder=\"Name\" name=\"name\" required=\"required\">");
					out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
					out.write("<td>");
						out.write("Password :");
					out.write("</td>");
					out.write("<td>");
						out.write("<input type=\"Password\" class=\"input\" placeholder=\"Password\" name=\"password\" required=\"required\">");
					out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
					out.write("<td>");
						out.write("Gender :");
					out.write("</td>");
					out.write("<td>");
						out.write("<select> name=\"gender\" required=\"required\"");
						out.write("<option>Male");
						out.write("<option>Female");
					out.write("</select>");
					out.write("</td>");
				out.write("</tr>");
				out.write("<tr>");
					out.write("<td>");
						out.write("Email :");
					out.write("</td>");
					out.write("<td>");
						out.write("<input class=\"input\" type=\"mail\" placeholder=\"Email\" name=\"email\" required=\"required\">");
					out.write("</td>");
				out.write("</tr>");
			out.write("</table>");
			out.write("<tr>");
				out.write("<input type=\"Submit\" value=\"Submit\" name=\"\" class=\"submit\">");
			out.write("</tr>");
		out.write("</form>");
                out.write("<p>You have registered succesfully!</p>");
	out.write("</div>");
out.write("</body>");
out.write("</html>");
            
       } catch (SQLException ex) {
           Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
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
