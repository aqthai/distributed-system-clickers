import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String type;
    public Boolean online;

    //	constructors
    public User() { }
    
    protected User(String aType, String aUsername, String aPassword, Boolean online) {
      type = aType;
      username = aUsername;
      password = aPassword;
      online = false;
    }

    public void print(){
      String status;
      if (online == true){
        status = "online";
      } else {
        status = "offline";
      }
      System.out.print(type + " a.k.a " + username + " is " + status);
	  }
}
