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

    protected User(String aType, String aUsername, String aPassword) {
      type = aType;
      username = aUsername;
      password = aPassword;
      status = "offline";
    }

    public void print(){
      System.out.println("(" + status + ") " + type + " " + username);
	  }

    public String getName(){
      return username;
    }

    public String getStatus(){
      return this.status;
    }

    public String getType(){
      return this.type;
    }

    public void setStatusOff() {
      this.status = "offline";
    }

    public void setStatusOn() {
      this.status = "online";
    }

    public void setTypeStudent(){
      this.type = "Student";
    }

    public void setTypeInstructor(){
      this.type = "Instructor";
    }

    public String toString() {
      return "User{" +
              "username='" + username + '\'' +
              ", password='" + password + '\'' +
              ", type='" + type + '\'' +
              '}';
  }
}
