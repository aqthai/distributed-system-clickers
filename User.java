import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String type;
    public String status;

    //	constructors
    public User() { }
    
    protected User(String aType, String aUsername, String aPassword, String aStatus) {
      type = aType;
      username = aUsername;
      password = aPassword;
      status = aStatus;
    }

    public void print(){
      System.out.println("(" + status + ") " + type + " " + username);
	  }

    public String getStatus(){
      return this.status;
    }

    public void setStatusOff() {
      this.status = "offline";
    }

    public void setStatusOn() {
      this.status = "online";
    }
}
