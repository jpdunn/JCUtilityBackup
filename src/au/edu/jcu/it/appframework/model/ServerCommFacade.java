package au.edu.jcu.it.appframework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ServerCommFacade {

	public String response = null;

	public ServerCommFacade() {
	}

	public String getSubjects() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_SUBJECTS"));
		String response = null;
		try {
			response = new ServerCommunication().execute(pairs).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	public String getClasses() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_CLASSES"));
		String response = null;
		try {
			response = new ServerCommunication().execute(pairs).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}


}
