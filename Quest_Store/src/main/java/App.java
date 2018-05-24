import com.codecool.DAO.MentorDAO;
import com.codecool.Model.Mentor;
import com.codecool.Model.User;


public class App {
    public static void main( String[] args ) {
        System.out.println("STARTING APP: \n");
        User mentor = new Mentor();
        mentor.setRoleId(1);
        mentor.setFirstName("MILOSZ");
        mentor.setLastName("Romanowski");
        mentor.setLogin("bartp");
        mentor.setPassword("tarara");
        mentor.setEmail("wfwrfw@wesd.com");
        MentorDAO mDao = new MentorDAO();
    }
}

