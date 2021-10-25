import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String type;

    //	constructors
    public User() {
    }
    protected User( String aUsername, String aPassword, String aType) {
        username = aUsername;
        password = aPassword;
        type = aType;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}



//    public void print(){
//      System.out.println(type + " a.k.a " + username);
//	  }
//}

// name, 1234, student