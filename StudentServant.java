//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    String theQuestion;
    String theAnswer;
    Boolean online;
     
    public StudentServant(User u)throws RemoteException{
    	theUser = u;
        theQuestion = "";
        theAnswer = "";
        online = false;
    }

    public String getAnswer() throws RemoteException{
        return theAnswer;
    }

    public void sendAnswer(String answer) throws RemoteException{
        this.theAnswer = answer;
        System.out.println(this.theUser.username + " says " + answer);
    }
    
	public User getState() throws RemoteException {
        return theUser;
	}

	public String getName() throws RemoteException {
        return theUser.username;
	}

	public Boolean getStatus() throws RemoteException {
        return online;
	}

	public String getQuestion() throws RemoteException {
        return theQuestion;
	}

	public void setStatusOn() throws RemoteException {
        this.online = true;
        System.out.println(this.getName() + " has logged in");
	}

    public void setStatusOff() throws RemoteException {
        this.online = false;
        System.out.println(this.getName() + " has logged out");
    }

    public void setQuestion(String question) throws RemoteException {
        this.theQuestion = question;
    }

	public String getPass() throws RemoteException {
        return theUser.password;
	}
	
    public void refresh() throws RemoteException{
        
    }
    
}