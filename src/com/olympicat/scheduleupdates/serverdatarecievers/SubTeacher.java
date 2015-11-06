package com.olympicat.scheduleupdates.serverdatarecievers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Avishay
 */
public class SubTeacher implements Serializable {
    
    public static final long serialVersionUID = 2l;
    
    private String subName;
    private String location;

    public SubTeacher(String subName, String location) {
        this.subName = subName;
        this.location = location;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
    
}
