/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.nio.file.Path;
import java.sql.Date;

/**
 *
 * @author Alex
 */
public class File {

    Integer size;
    String type;
    String name;
    Date uploadDate;
    String category;
    Path filePath;
    String description;

    public File(Integer size,
            String type,
            String name
            /*Date uploadDate,
            String category,
            Path filePath,
            String description*/) 
    {
this.size=size;
this.type=type;
this.name=name;
this.uploadDate=uploadDate;
this.category=category;
this.filePath=filePath;
this.description=description;
    }

    public Integer getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getCategory() {
        return category;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }
    
}
