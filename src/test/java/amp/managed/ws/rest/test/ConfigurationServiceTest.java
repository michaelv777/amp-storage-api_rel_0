/**
 * 
 */
package amp.managed.ws.rest.test;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import amp.managed.storage.config.ConfigurationService;



/**
 * @author MVEKSLER
 *
 */
public class ConfigurationServiceTest 
{
	private static URI getConfigBaseURI() {
        return UriBuilder.fromUri("http://localhost:7001/amp-storage-api/ConfigurationService").build();
	}
	
	@SuppressWarnings("unused")
	private static URI getDataBaseURI() {
        return UriBuilder.fromUri("http://localhost:7001/amp-storage-api/DataService").build();
	}
	
	public void testSayPlainTextHello() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();

	        Client client = Client.create();

			WebResource webResource = client.resource(getConfigBaseURI().toString() + "/sayPlainTextHello");

			ClientResponse response = webResource.accept("text/plain")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) 
			{
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);
	        
		}
		catch( Exception e )
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
		}
	}
	
	public void testGetWorkerConfigSA() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String cWorketName = "FacebookWorkerBean";
    		
	        ConfigurationService cConfigService = 
	        		new ConfigurationService();
	        
	        
	        Response response = cConfigService.getWorkerConfig(cWorketName);
    		
	        if ( response != null )
	        {
	        	Object responseObject = response.getEntity();
	        	
	        	System.out.println(cMethodName + "::" + responseObject.toString());
	        }
	        
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
		}
	}
	
	public static void main(String [ ] args)
	{
		try
		{
			new ConfigurationServiceTest().testGetWorkerConfigSA();
		}
		catch( Exception e )
		{
			
		}
	}
}
