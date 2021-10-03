import java.io.Serializable;
import java.util.Scanner;

public class User implements Serializable {
    public String username;
    public String password;
    public String type;
    public Boolean status;

    //	constructors
    public User() { }
    
    protected User(String aType, String aUsername, String aPassword, Boolean aStatus) {
      type = aType;
      username = aUsername;
      password = aPassword;
      status = false;
    }

    public void print(){
      System.out.print(type + " a.k.a " + username);
	  }
}
