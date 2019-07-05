/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uploadownload;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Get;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.io.ByteStreams;
import components.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "Download", urlPatterns = {"/Download"})
public class Download extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
 private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "client102.json";
    
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Upload.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(9003).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("owner");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Download</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Download at " + request.getContextPath() + "</h1>");
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
        
         try {
             
             String driveId=request.getParameter("name");
             final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
             Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                     .setApplicationName(APPLICATION_NAME)
                     .build();
             
                     //service.files().export("1djdYUjW5ICGoYKNk1vk0qhX93k1IAlT8","application/pdf").executeAndDownloadTo(response.getOutputStream());
                     FileList result = service.files().list().execute();
    List<File> files = result.getFiles();
    if (files == null || files.size() == 0) {
        System.out.println("No files found.");
    } else {

        for (File file : files)if(file.getId().equals(driveId))
       {

            String fname = file.getName();
                         response.setHeader("Content-Disposition", "attachment; filename="+fname);

            String ex = fname.substring(fname.lastIndexOf(".") + 1);

            try {
                Files f = service.files();
                HttpResponse httpResponse = null;
                if (ex.equalsIgnoreCase("xlsx")) {
                    httpResponse = f
                            .export(file.getId(),
                                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            .executeMedia();
                    response.setContentType("application/pdf");

                } else if (ex.equalsIgnoreCase("docx")) {
                    httpResponse = f
                            .export(file.getId(),
                                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            .executeMedia();
                    response.setContentType("application/pdf");
                }
                else if (ex.equalsIgnoreCase("zip")) {
                    httpResponse = f
                            .export(file.getId(),
                                    "application/zip")
                            .executeMedia();
                    response.setContentType("application/zip");
                }
                
                else if (ex.equalsIgnoreCase("txt")) {
                    httpResponse = f
                            .export(file.getId(),
                                    "text/plain")
                            .executeMedia();
                    response.setContentType("text/plain");
                }
                else if (ex.equalsIgnoreCase("pptx")) {
                    httpResponse = f
                            .export(file.getId(),
                                    "application/vnd.openxmlformats-officedocument.presentationml.presentation")
                            .executeMedia();
                    response.setContentType("application/pdf");

                } else if (ex.equalsIgnoreCase("pdf")
                        || ex.equalsIgnoreCase("jpg")
                        || ex.equalsIgnoreCase("png")) {

                    Get get = f.get(file.getId());
                    httpResponse = get.executeMedia();
                    response.setContentType("image");

                }
                if (null != httpResponse) {
                    InputStream instream = httpResponse.getContent();
                    FileOutputStream output = new FileOutputStream(
                            "M:\\drive_download\\"+file.getName());
                    try {
                        int l;
                        byte[] tmp = new byte[2048];
                        while ((l = instream.read(tmp)) != -1) {
                            response.getOutputStream().write(tmp, 0, l);
                        }
                        
                       /* java.io.File my_file = new java.io.File("M:\\drive_download\\"+file.getName());

         // This should send the file to browser
         OutputStream out = response.getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.close();
                        */
            
                    } finally {
                        output.close();
                        instream.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
    
                   
              } catch (GeneralSecurityException ex) {
             Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
             
         }
         User user=(User)request.getSession().getAttribute("user");
         try {
            user.loadFiles();
         } catch (SQLException ex) {
             Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
             Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
             Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
         }
request.getSession().removeAttribute("files");
 request.getSession().setAttribute("files", user.getUploadedFiles());
            
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
