//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.awt.Color;


public class ClassListClient{
   public static void main(String args[]){
		if(args.length > 0)  option = args[0];	// read or write
		if(args.length > 1)  shapeType = args[1];	// specify Circle, Line etc
 		System.out.println("option = " + option + "shape = " + shapeType);
		
 		if(System.getSecurityManager() == null){
        	System.setSecurityManager(new SecurityManager());
        } else System.out.println("Already has a security manager, so cant set RMI SM");
        ClassList aClassList = null;
        try{
            aClassList  = (ClassList) Naming.lookup("//127.0.0.1/ClassList");
 			System.out.println("Found server");
 			Vector sList = aClassList.allShapes();
 			System.out.println("Got vector");
			if(option.equals("Read")){
				for(int i=0; i<sList.size(); i++){
        			GraphicalObject g = ((Shape)sList.elementAt(i)).getAllState();
        			g.print();
        		}
        	} else {
                GraphicalObject g = new GraphicalObject(shapeType, new Rectangle(50,50,300,400),Color.red,
                  			Color.blue, false);
                System.out.println("Created graphical object");
      			aClassList.newShape(g);
      			System.out.println("Stored shape");
        	}
		}catch(RemoteException e) {System.out.println("allShapes: " + e.getMessage());
	    }catch(Exception e) {System.out.println("Lookup: " + e.getMessage());}
    }
}


	             
