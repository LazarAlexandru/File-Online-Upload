/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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
     ArrayList<String> categoryList=new ArrayList<>();

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
    
    public void insertFileDB(Long size,String type,String name,String driveId,String category,Date date) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalAccessException, IllegalAccessException, SQLException, SQLException, SQLException
    {
             String statement="insert into files(id,size,type,name,drive_id,category,date) values(?,?,?,?,?,?,?)";
            String dbURL="jdbc:derby://localhost:1527/FileUploadDB;create=true;user=alex;password=Tim4797";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            Connection connection=DriverManager.getConnection(dbURL);
            PreparedStatement pa=connection.prepareStatement(statement);
            
            pa.setString(1,this.id);
            pa.setLong(2,size);
            pa.setString(3,type);
            pa.setString(4,name);
            pa.setString(5, driveId);
            pa.setString(6, category);
            pa.setDate(7, date);
           
            pa.executeUpdate();
       
            
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
            uploadedFiles.clear();
            while(rs.next())
            {
              File file=new File(rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7));
              uploadedFiles.add(file);
            }
             
    }
    
     public void loadCategoryList() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
         String statement="select distinct category from Files where id=?";
            String dbURL="jdbc:derby://localhost:1527/FileUploadDB;create=true;user=alex;password=Tim4797";
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            Connection connection=DriverManager.getConnection(dbURL);
            PreparedStatement pa=connection.prepareStatement(statement);
            pa.setString(1, id);
            ResultSet rs=pa.executeQuery();
            categoryList.clear();
            while(rs.next())
            {
              categoryList.add(rs.getString(1));
            }
             
    }

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }
    
    public void printFiles()
    {
        uploadedFiles.toString();
    }
     
    
}
