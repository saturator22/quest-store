package com.codecool;

import com.codecool.DAO.DAO;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        User mentor = new Mentor();
        mentor.setRole_id(1);
        mentor.setFirst_name("Bartek");
        mentor.setLast_name("Petka");
        mentor.setLogin("bartp");
        mentor.setPassword("fwfwfw");
        mentor.setEmail("wfwrfw@wdfwe.com");

        DAO.addUserData(mentor);
    }
}
