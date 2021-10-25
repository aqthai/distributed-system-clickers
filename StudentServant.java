//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    String theQuestion;
    String theAnswer;
     
    public StudentServant(User u)throws RemoteException{
    	theUser = u;
        theQuestion = "";
        theAnswer = "";
    }

    public String getAnswer() throws RemoteException{
        return theAnswer;
    }

    public void sendAnswer(String answer) throws RemoteException{
        this.theAnswer = answer;
    }
    
	public User getState() throws RemoteException {
        return theUser;
	}

	public String getName() throws RemoteException {
        return theUser.username;
	}

	public String getStatus() throws RemoteException {
        return this.theUser.getStatus();
	}

	public String getQuestion() throws RemoteException {
        return theQuestion;
	}

	public void setStatusOn() throws RemoteException {
        this.theUser.setStatusOn();
        System.out.println(this.getName() + " has logged in");
	}

    public void setStatusOff() throws RemoteException {
        this.theUser.setStatusOff();
        System.out.println(this.getName() + " has logged out");
    }

    public void setQuestion(String question) throws RemoteException {
        this.theQuestion = question;
    }

	public String getPass() throws RemoteException {
        return this.theUser.password;
	}
    
}