/**
 * ClassListServant is the skeleton ClassList calls.  It is made of an instructor
 * and an ArrayList of StudentServant objects.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */

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
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < theList.size(); i++){
            names.add(theList.get(i).getName());
        }
        if (!names.contains(u.getName())){
            StudentServant s = new StudentServant(u);
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