import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClassListServant extends UnicastRemoteObject implements ClassList {
    public Instructor teacher;
    public ArrayList<StudentServant> theList;

    public ClassListServant(User u) throws RemoteException{
        teacher = new InstructorServant(u);
        theList = new ArrayList<StudentServant>();
    }

    public void newStudent(User u) throws RemoteException {
        StudentServant s = new StudentServant(u);
        if (this.getStudent(u.username) != null){
            theList.add(s);
            System.out.println(s.getName() + " has registered");
        }
    }

    public StudentServant getStudent(String name) throws RemoteException {
        for (StudentServant s : theList){
            if (s.getState().username.equals(name)){
                return s;
            }
        }
        return null;
    }

    public Instructor getInstructor() throws RemoteException {
        return teacher;
    }

    public void setInstructor(User u) throws RemoteException {
        this.teacher = new InstructorServant(u);
    }

    public ArrayList<StudentServant> allStudents() throws RemoteException{
        return theList;
    }
}