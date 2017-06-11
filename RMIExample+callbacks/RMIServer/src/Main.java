import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import javax.naming.NamingException;

import bozek.mateusz.crawler.logic.RMICrawlerProxyImplementation;

public class Main
{
	public static void main( String[] args ) throws NamingException, RemoteException, InterruptedException, AlreadyBoundException
	{
		int port = 5060;
		String name = "rmi://" + port + "/crawler";
		Registry registry = LocateRegistry.createRegistry( port );
		
		try
		{
			RMICrawlerProxyImplementation crawler = new RMICrawlerProxyImplementation(); 
			registry.bind( name, crawler ); 
			System.out.println("Press 0 to exit");
			Scanner scanner = new Scanner( System.in );
			String choice = scanner.nextLine();
			
			while (	true )
			{
				if (choice.equals(0))
				{
					if ( "exit".equals( choice) )
						break;
				}
				choice = scanner.nextLine();
			}
			
			scanner.close();
		}
		finally
		{
			UnicastRemoteObject.unexportObject( registry, true ); 
			System.out.println( "Server break." ); 
		}
	}
}
