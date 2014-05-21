package au.edu.jcu.it.appframework.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class ServerCommunication extends
	AsyncTask<List<NameValuePair>, Integer, String> {
	HttpClient client;
	HttpPost post;
	String responseText = null;

	public ServerCommunication() {
		client = new DefaultHttpClient();
		post = new HttpPost("http://137.219.176.193:3001/jcutility-server");
	}
	
	@Override
	protected String doInBackground(List<NameValuePair>... params) {
		try {
			post.setEntity(new UrlEncodedFormEntity(params[0]));
			HttpResponse response = client.execute(post);
			responseText = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}
}
