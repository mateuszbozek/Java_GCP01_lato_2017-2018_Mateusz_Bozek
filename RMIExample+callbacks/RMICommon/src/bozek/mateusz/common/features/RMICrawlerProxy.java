package bozek.mateusz.common.features;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import bozek.mateusz.common.domain.*;

public interface RMICrawlerProxy extends Remote
{

	 boolean loginUser(String login, String password) throws RemoteException;
	 
	 void logoutUser() throws RemoteException;
	
	 List<String> getLogs() throws Exception;
	 
	 List<bozek.mateusz.common.domain.Student> getAllStudents() throws RemoteException, IOException;
	 
	 void infoStudents() throws  IOException;
}
