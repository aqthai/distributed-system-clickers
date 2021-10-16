//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.util.Scanner;


public class ClassListClient{
   public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Login or Register?: ");
		String menu1 = scanner.nextLine();
		
 		if(System.getSecurityManager() == null){
        	System.setSecurityManager(new SecurityManager());
        } else System.out.println("Already has a security manager, so cant set RMI SM");
        ClassList aClassList = null;
        try{
            aClassList  = (ClassList) Naming.lookup("//127.0.0.1/ClassList");
 			System.out.println("Found server");
 			Vector sList = aClassList.allStudents();
 			System.out.println("Got vector");
			if(menu1.equals("Read")){
				for(int i=0; i<sList.size(); i++){
        			User u = ((Student)sList.elementAt(i)).getState();
        			u.print();
        		}
        	} else {
                GraphicalObject g = new GraphicalObject(shapeType, new Rectangle(50,50,300,400),Color.red,
                  			Color.blue, false);
                System.out.println("Created graphical object");
      			aClassList.newStudent(g);
      			System.out.println("Stored shape");
        	}
		}catch(RemoteException e) {System.out.println("allStudents: " + e.getMessage());
	    }catch(Exception e) {System.out.println("Lookup: " + e.getMessage());}
    }
}


	             
