package opencagedata;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

public class OpenCageClient {
	
	public static void main(String args[]) throws URISyntaxException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpGet request = new HttpGet("https://api.opencagedata.com/geocode/v1/json?q=51.794052+-0.077203&key=bb7732b23d2b46d68eb8c3aa85233366&ApplicationType=json");
		URIBuilder builder = null;
		try {
			builder = new URIBuilder("https://api.opencagedata.com/geocode/v1/json");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		builder.setParameter("key", "bb7732b23d2b46d68eb8c3aa85233366").setParameter("ApplicationType", "json").setParameter("q", "51.794052+-0.077203");

		HttpGet request = new HttpGet(builder.build());
        try {
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
               
            }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
