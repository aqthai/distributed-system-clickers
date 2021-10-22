import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String type;

    //	constructors
    public User() { }
    
    protected User(String aType, String aUsername, String aPassword) {
      type = aType;
      username = aUsername;
      password = aPassword;
    }

    public void print(){
      System.out.println(type + " a.k.a " + username + " exists ");
	  }
}
