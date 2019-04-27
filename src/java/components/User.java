/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class User {
    private
     String id;
     String name;
     String password;
     String gender;
     String email;
     boolean connected=false;
     
     ArrayList<File> uploadedFiles=new ArrayList<>();

    public ArrayList<File> getUploadedFiles() {
        return uploadedFiles;
    }
     
     
     public User(String id,String name,String password,String gender,String email) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalAccessException, IllegalAccessException, IllegalAccessException
     {
         this.id=id;
         this.name=name;
         this.password=password;
         this.gender=gender;
         this.email=email;
         connected=true;
        try {
            loadFiles();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }
    
    public void loadFiles() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
         String statement="select * from Files where id=?";
            String dbURL="jdbc:derby://localhost:1527/FileUploadDB;create=true;user=alex;password=Tim4797";
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection connection=DriverManager.getConnection(dbURL);
            PreparedStatement pa=connection.prepareStatement(statement);
            pa.setString(1, id);
            ResultSet rs=pa.executeQuery();
            while(rs.next())
            {
              File file=new File(rs.getInt(2),rs.getString(3),rs.getString(4));
              uploadedFiles.add(file);
            }
             
    }
    public void printFiles()
    {
        uploadedFiles.toString();
    }
     
    
}
