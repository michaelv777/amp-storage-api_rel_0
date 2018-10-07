/**
 * 
 */
package amp.managed.ws.rest.test;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import amp.jpa.entities.WorkerData;
import amp.jpaentities.mo.WorkerConfigurationListMO;
import amp.jpaentities.mo.WorkerConfigurationMO;
import amp.jpaentities.mo.WorkerDataListMO;
import amp.jpaentities.mo.WorkerDataMO;
import amp.managed.storage.base.StorageServiceBase;
import amp.managed.storage.config.ConfigurationService;

/**	
 * @author MVEKSLER
 *
 */
public class ConfigurationServiceJUnit 
{
	private static URI getConfigBaseURI() {
        return UriBuilder.fromUri("http://localhost:7001/amp-storage-api/ConfigurationService").build();
	}
	
	private static URI getStatusBaseURI() {
        return UriBuilder.fromUri("http://localhost:7001/amp-storage-api/StatusService").build();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
			
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Ignore
	@Test
	public void testStorageBaseLogic() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        StorageServiceBase cService = new ConfigurationService();
    		
	        System.out.println(cMethodName + "::Status=" + cService.isLcRes());
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testSayPlainTextHello() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();

	        Client client = Client.create();

			WebResource webResource = client
			   .resource(getConfigBaseURI().toString() + "/sayPlainTextHello");

			ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN_TYPE)
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
	//---------------
	@Ignore
	@Test
	public void testSetItemOpStatus() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String sourceName  = "1774237332905314";
	        String targetName  = "1774237332905314"; 
	        String workerName  = "FacebookWorkerBean"; 
	        String opTypeName  = "ProcessPost"; 
		    String itemKey     = "1774237332905314";
		    String description = "1774237332905314"; 
		    String status      = "Normal";
    		
	        Client client = Client.create();

    		WebResource webResource = client.resource(getStatusBaseURI().toString());
    		
    		ClientResponse response = webResource.path("/setItemOpStatus").
    											  queryParam("sourceName", String.valueOf(sourceName)).
    											  queryParam("targetName", String.valueOf(targetName)).
    											  queryParam("workerName", String.valueOf(workerName)).
    											  queryParam("opTypeName", String.valueOf(opTypeName)).
    											  queryParam("itemKey", itemKey).
    											  queryParam("description", description). 
    											  queryParam("status", status).
    											  accept(MediaType.APPLICATION_XML).
					  							  type(MediaType.APPLICATION_JSON).
												  post(ClientResponse.class);
    		
    		Assert.assertEquals(200, response.getStatus());

			String cStatus = response.getEntity(String.class);
    		
			System.out.println(cMethodName + "::" + cStatus);
    		
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	//---------------
	@Ignore
	@Test
	public void testSetItemOpStatusMO() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        long sourceId  = 3;
	        long targetId  = 3; 
	        long statusId  = 1;
	        long workerId  = 1;
	        
	        String sourceName  = "1774237332905314";
	        String targetName  = "1774237332905314"; 
	        @SuppressWarnings("unused")
			String workerName  = "FacebookWorkerBean"; 
	        String opTypeName  = "ReadPosts"; 
		    String itemKey     = "1774237332905314";
		    String description = "1774237332905314"; 
		    String statusName  = "Normal";
    		
		    WorkerDataMO cWorkerDataMO = new WorkerDataMO();
		    cWorkerDataMO.getcWorkerData().getSource().setSourceid(sourceId);
		    cWorkerDataMO.getcWorkerData().getSource().setName(sourceName);
		    
		    cWorkerDataMO.getcWorkerData().getTarget().setTargetid(targetId);
		    cWorkerDataMO.getcWorkerData().getTarget().setName(targetName);

		    cWorkerDataMO.getcWorkerData().getWorker().setWorkerid(workerId);
		    
		    cWorkerDataMO.getcWorkerData().getOperationtypeM().setOperationtypeid(5);
		    cWorkerDataMO.getcWorkerData().getOperationtypeM().setName(opTypeName);
		    
		    cWorkerDataMO.getcWorkerData().setItemid(itemKey);
		    cWorkerDataMO.getcWorkerData().setDescription(description);
		    
		    cWorkerDataMO.getcWorkerData().getStatusM().setStatusid(statusId);
		    cWorkerDataMO.getcWorkerData().getStatusM().setName(statusName);
		    
	        Client client = Client.create();

    		WebResource webResource = client.resource(getStatusBaseURI().toString());
    		
    		ClientResponse response = webResource.path("/setItemOpStatusMO").
    										      accept(MediaType.APPLICATION_XML).
					  							  post(ClientResponse.class, cWorkerDataMO);
    		
    		Assert.assertEquals(200, response.getStatus());

			String cStatus = response.getEntity(String.class);
    		
			System.out.println(cMethodName + "::" + cStatus);
    		
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
		
	@Ignore
	@Test
	public void testGetItemOpStatusFacebook() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String sourceName  = "1774237332905314";
	        String targetName  = "1774237332905314"; 
	        String workerName  = "FacebookWorkerBean";
	        String threadName  = "FacebookWorkerBeanThread1";
	        //String opTypeName  = "PostLink"; 
	        String opTypeName  = "ProcessPost";
		    String itemKey     = "1774237332905314_1830718860590494";
    		
	        Client client = Client.create();

    		WebResource webResource = client.resource(getStatusBaseURI().toString());
    		
    		ClientResponse response = webResource.path("/getItemOpStatus").
    											  queryParam("sourceName", sourceName).
    											  queryParam("targetName",  targetName).
    											  queryParam("workerName",  workerName).
    											  queryParam("threadName",  threadName).
    											  queryParam("opTypeName",  opTypeName).
    											  queryParam("itemKey",     itemKey).
												  get(ClientResponse.class);
    		
    		Assert.assertEquals(200, response.getStatus());

			WorkerDataListMO cWorkerDataListMO =  response.getEntity(WorkerDataListMO.class);
    		
    		for ( WorkerDataMO cWorkerDataMO : cWorkerDataListMO.cWorkerData)
    		{
    			WorkerData cWorkerData = cWorkerDataMO.cWorkerData;
    			
    			System.out.println(cMethodName + "::" + 
    					cWorkerData.getItemid() + ":" +
    					cWorkerData.getOperationtypeM().getName() + ":" +
    					cWorkerData.getUpdatedate().toString());
    		}
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetItemOpStatusYoutube() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String sourceName  = "Amazon";
	        String targetName  = "Youtube Video"; 
	        String workerName  = "YoutubeWorkerBean";
	        String threadName  = "YoutubeWorkerBeanThread1";
	        //String opTypeName  = "PostLink"; 
	        String opTypeName  = "ProcessPost";
		    String itemKey     = "ZpJ7r0JtWn8";
    		
	        Client client = Client.create();

    		WebResource webResource = client.resource(getStatusBaseURI().toString());
    		
    		ClientResponse response = webResource.path("/getItemOpStatus").
    											  queryParam("sourceName", sourceName).
    											  queryParam("targetName",  targetName).
    											  queryParam("workerName",  workerName).
    											  queryParam("threadName",  threadName).
    											  queryParam("opTypeName",  opTypeName).
    											  queryParam("itemKey",     itemKey).
												  get(ClientResponse.class);
    		
    		Assert.assertEquals(200, response.getStatus());

			WorkerDataListMO cWorkerDataListMO =  response.getEntity(WorkerDataListMO.class);
    		
    		for ( WorkerDataMO cWorkerDataMO : cWorkerDataListMO.cWorkerData)
    		{
    			WorkerData cWorkerData = cWorkerDataMO.cWorkerData;
    			
    			System.out.println(cMethodName + "::" + 
    					cWorkerData.getItemid() + ":" +
    					cWorkerData.getOperationtypeM().getName() + ":" +
    					cWorkerData.getUpdatedate().toString());
    		}
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testGetWorkerConfig() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String cWorkerName = "FacebookWorkerBean";
    		
	        Client client = Client.create();

    		WebResource webResource = client.resource(getConfigBaseURI().toString());
    		
    		ClientResponse response = webResource.path("/getWorkerConfig").
    											  queryParam("cWorkerName", cWorkerName).
												  get(ClientResponse.class);
    		
    		Assert.assertEquals(200, response.getStatus());

			WorkerConfigurationListMO cSourceConfigurations =  response.getEntity(WorkerConfigurationListMO.class);
    		
    		for ( WorkerConfigurationMO cSourceConfig : cSourceConfigurations.cSourceConfiguration)
    		{
    			System.out.println(cMethodName + "::" + cSourceConfig.getConfigkey() + ":" + 
    													cSourceConfig.getConfigvalue());
    		}
		}
		catch( Exception e)
		{
			System.out.println(cMethodName + "::Exception:" + e.getMessage());
			
			e.printStackTrace();
		}
	}
	//---------------
	@Ignore
	@Test
	public void testGetWorkerConfigSA() 
	{
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        String cWorkerName = "FacebookWorkerBean";
    		
	        ConfigurationService cConfigService = 
	        		new ConfigurationService();

	        
	        javax.ws.rs.core.Response response = cConfigService.getWorkerConfig(cWorkerName);
    		
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
}
