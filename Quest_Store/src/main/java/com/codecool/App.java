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
        mentor.setRoleId(1);
        mentor.setFirstName("MILOSZ");
        mentor.setLastName("Romanowski");
        mentor.setLogin("bartp");
        mentor.setPassword("tarara");
        mentor.setEmail("wfwrfw@wesd.com");

        DAO.addUserData(mentor);
    }
}
