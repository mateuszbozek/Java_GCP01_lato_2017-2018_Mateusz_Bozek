package bozek.mateusz.crawler.logic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import bozek.mateusz.common.domain.MessageEvent;
import bozek.mateusz.common.domain.Student;
import bozek.mateusz.common.domain.User;
import bozek.mateusz.common.features.RMICrawlerProxy;
import bozek.mateusz.crawler.exception.CrolwerException;
import bozek.mateusz.crawler.main.Crawler;
import bozek.mateusz.crawler.type_enum.OrderMode;

public class RMICrawlerProxyImplementation extends UnicastRemoteObject implements RMICrawlerProxy
{
	private static final long serialVersionUID = -2101656626166332642L;
	
	private boolean logged = false;
	private MessageEvent event = null;
	
	public RMICrawlerProxyImplementation() throws RemoteException
	{
		super();
	}

	@Override
	public boolean loginUser( String login, String password ) throws RemoteException
	{
		//TODO: wymagana implementacja sessji dla roznych uzytkownikow
		
		if(this.logged)
			return false;
		
		if("admin".equals( login ) && "admin".equals( password ))
		{
			this.logged = true;
			
			return true;
		}
		
		if("mateusz".equals(login) && "test123".equals(password)){
			this.logged = true;
		}
		
		return false;
	}
	
	@Override
	public void logoutUser() throws RemoteException
	{
		
		this.logged = false;
	}
	
	@Override
	public void infoStudents() throws IOException
	{
		//TODO: wymagana implementacja sessji dla roznych uzytkownikow
		
		if(this.event == null)
			return;
		Crawler crawler = new Crawler();
		getAllStudents().forEach(s ->{
			
			try {
				this.event.messageSended(s.toString() );
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	@Override
	public List<bozek.mateusz.common.domain.Student> getAllStudents() throws IOException {
		Crawler crawler = new Crawler();
		return crawler.getAllStudents();
	}

	@Override
	public List<String> getLogs() throws Exception {
		Crawler crawler = new Crawler();
		return crawler.run();
	}
}
