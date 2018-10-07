 
package amp.managed.storage.config;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.axis.utils.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amp.jpa.entities.WorkerConfiguration;
import amp.jpaentities.mo.WorkerConfigurationListMO;
import amp.jpaentities.mo.WorkerConfigurationMO;
import amp.managed.storage.base.StorageServiceBase;


@Path("ConfigurationService")
@Consumes({"application/json", "application/xhtml+xml", "application/xml", "multipart/form-data", "text/html", "image/jpeg", "text/plain", "text/xml", "image/gif" })
@Produces({"application/json", "application/xhtml+xml", "application/xml", "multipart/form-data", "image/jpeg", "text/plain", "text/xml", "image/gif" })
public class ConfigurationService extends StorageServiceBase
{
	private static final Logger cLogger = 
			LoggerFactory.getLogger(ConfigurationService.class);
	
	//---class variables----------------------
	
	
	//---getters/setters----------------------
	
	
	//---class methods------------------------
	/**
     * Default constructor. 
     */
    public ConfigurationService() 
    {
    	super();
		
		String methodName = "";
		
		// TODO Auto-generated method stub
		try
    	{
			
    	}    		
    	catch( Exception e)
    	{
    		cLogger.error(methodName + "::" + e.getStackTrace());
    		
    		this.setLcRes(false);
    	}
    }

    //---examples----------------------------
//    /**
//     * Retrieves representation of an instance of AmazonService2
//     * @return an instance of String
//     */
//	@GET
//	@Produces("text/plain")
//	public String resourceMethodGET() { 
//		// TODO Auto-generated method stub
//		return "Hello From resourceMethodGET";
//	}
//
//	/**
//     * PUT method for updating or creating an instance of AmazonService2
//     * @content content representation for the resource
//     * @return an HTTP response with content of the updated or created resource.
//     */
//	@PUT
//	@Consumes("text/plain")
//	public void resourceMethodPUT(String content) { 
//		// TODO Auto-generated method stub
//		return;
//	}
//	
//	// This method is called if TEXT_PLAIN is request
//	@GET
//	@Path("/sayPlainTextHello")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String sayPlainTextHello() 
//	{
//		return "Hello Jersey";
//	}
//	
//	// This method is called if XML is request
//	@GET
//	@Path("/sayXMLHello")
//	@Produces(MediaType.TEXT_XML)
//	public String sayXMLHello() 
//	{
//	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
//	}
//	
//	// This method is called if HTML is request
//	@GET
//	@Path("/sayHtmlHello")
//	@Produces(MediaType.TEXT_HTML)
//	public String sayHtmlHello() 
//	{
//	    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
//	        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
//	}
	/*//--------------------------------------------------------------------------
    @POST
  	@Path("/addWorkerDataOA")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	public Response addWorkerDataOA(
  			@QueryParam("sourceName")  String sourceName, 
  			@QueryParam("targetName")  String targetName,
  			@QueryParam("workerName")  String workerName,
  			@QueryParam("opTypeName")  String opTypeName,
  			@QueryParam("itemKey")     String itemKey,
  			@QueryParam("description") String description, 
  			@QueryParam("status")      String status)
  	{
      	boolean cRes = true;
      	
      	String cMethodName = "";
      	
      	Session hbsSession = null;
		
		Transaction tx = null;
		
      	try
      	{
      		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
  	        StackTraceElement ste = stacktrace[1];
  	        cMethodName = ste.getMethodName();
		  
  	        if ( StringUtils.isEmpty(itemKey) || StringUtils.isEmpty(description) || StringUtils.isEmpty(status))
	        {
	        	cRes = false;
	        	
	        	cLogger.error(cMethodName + "::Error:params is null. Check parameters for null value!");
	        }
		    
	        //--------
    		if ( cRes )
    		{
	    		if ( null == this.cToolkitDataProvider )
	    		{
	    			cLogger.error(cMethodName + "::cToolkitDataProvider is NULL. Reinitialize.");
	    			
	    			cRes = this.initClassVariables();
	    		}
    		} 
	        //--------
    		if ( cRes )
    		{
    			hbsSession = cToolkitDataProvider.gettDatabase().getHbsSessions().openSession();
    			
    			tx = hbsSession.beginTransaction();
    			
    			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
    			
    			String hashClean = sourceName + "_" + targetName + "_" + 
    			                   workerName + "_" + opTypeName + "_" +
    			                   opTypeName + "_" + itemKey    + "_" +
    			                   status 	  + "_" + updateDate.toString();
    			
    			String hashEncr  = this.hashString(hashClean);
    			
    			Query query = hbsSession.createSQLQuery("INSERT INTO WORKER_DATA " + 
    			"(WORKERDATA2SOURCE, "
    			+ "WORKERDATA2TARGET, "
    			+ "WORKERDATA2WORKER, "
    			+ "WORKERDATA2OPTYPE, "
    			+ "ITEMID, "
    			+ "DESCRIPTION, "
    			+ "STATUS, "
    			+ "WORKERDATAHASH, "
    			+ "UPDATEDATE) " + 
    			"VALUES ("
    			+ " (select NVL(sourceid, 0) from source where name=:sourceName), "
    			+ " (select NVL(targetid, 0) from target where name=:targetName), "
    			+ " (select NVL(workerid, 0) from worker where name=:workerName), "
    			+ " (select NVL(operationtypeid, 0) from operationtype_m where name=:opTypeName), "
    			+ " :itemKey, "
    			+ " :description, "
    			+ " :status, "
    			+ " :hash,"
    			+ " :updateDate" + ")");
    			
    			query.setString("sourceName", sourceName);
    			query.setString("targetName", targetName);
    			query.setString("workerName", workerName);
    			query.setString("opTypeName", opTypeName);
    			query.setString("itemKey", itemKey);
    			query.setTimestamp("updateDate", updateDate);
    			query.setString("description", description);
    			query.setString("status", status);
    			query.setString("hash", hashEncr);
    			
    			query.executeUpdate();
    		}
	        
  	        cLogger.info("------------------");
  	        
  	        if ( tx != null )
  	        {
				tx.commit();
  	        }
  	      
  	        return Response.ok(String.valueOf(cRes)).build();
      	}
      	catch( Exception e)
      	{
      		cLogger.error(cMethodName + "::Exception:" + e.getMessage());
      		
      		tx.rollback();
      		
      		String cError = String.valueOf(cRes = false) + ":" + e.getMessage();
      		
      		return Response.ok(cError).build();
      	}
      	finally
		{
			if ( hbsSession != null )
    		{
    			hbsSession.close();
    		}
		}
  	}
    
  	@POST
  	@Path("/addWorkerDataMO")
  	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	public Response addWorkerDataMO(WorkerDataMO cWorkerDataMO)
  	{
      	boolean cRes = true;
      	
      	String cMethodName = "";
      	
      	Session hbsSession = null;
		
		Transaction tx = null;
		
      	try
      	{
      		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
  	        StackTraceElement ste = stacktrace[1];
  	        cMethodName = ste.getMethodName();
		  
		    if ( null == cWorkerDataMO )
	        {
	        	cRes = false;
	        	
	        	cLogger.error(cMethodName + "::Error:params is null. Check MultivaluedMap<String, String> params parameter!");
	        }
		    
	        //--------
    		if ( cRes )
    		{
	    		if ( null == this.cToolkitDataProvider )
	    		{
	    			cLogger.error(cMethodName + "::cToolkitDataProvider is NULL. Reinitialize.");
	    			
	    			cRes = this.initClassVariables();
	    		}
    		} 
	        //--------
    		if ( cRes )
    		{
    			hbsSession = cToolkitDataProvider.gettDatabase().getHbsSessions().openSession();
    			
    			tx = hbsSession.beginTransaction();
    			
    			WorkerData cWorkerData = cWorkerDataMO.getcWorkerData();
    			
    			String sourceName = String.valueOf(cWorkerData.getSource().getSourceid());
      			String targetName = String.valueOf(cWorkerData.getTarget().getTargetid());
      			String workerName = String.valueOf(cWorkerData.getWorkerdata2worker().longValue());
      			String opTypeName = String.valueOf(cWorkerData.getOperationtypeM().getOperationtypeid());
      			String itemKey    = cWorkerData.getItemid();
      			String status     = cWorkerData.getStatus();
    			
      			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
      			cWorkerData.setUpdatedate(new Date(updateDate.getTime()));
      			
    			String hashClean = sourceName + "_" + targetName + "_" + 
				                   workerName + "_" + opTypeName + "_" +
				                   opTypeName + "_" + itemKey    + "_" +
				                   status 	  + "_" + updateDate.toString();
		
    			String hashEncr  = this.hashString(hashClean);
 			
    			cWorkerData.setWorkerdatahash(hashEncr);
    			
    			hbsSession.save(cWorkerData);
    		}
	        
  	        cLogger.info("------------------");
  	        
  	        if ( tx != null )
			{
				tx.commit();
			}
  	      
  	        return Response.ok(String.valueOf(cRes)).build();
      	}
      	catch( Exception e)
      	{
      		cLogger.error(cMethodName + "::Exception:" + e.getMessage());
      		
      		tx.rollback();
      		
      		String cError = String.valueOf(cRes = false) + ":" + e.getMessage();
      		
      		return Response.ok(cError).build();
      	}
      	finally
		{
			if ( hbsSession != null )
    		{
    			hbsSession.close();
    		}
		}
  	}*/
  	
  	//---
    @SuppressWarnings("unchecked")
    @GET
	@Path("/getWorkerConfig")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getWorkerConfig(@QueryParam("cWorkerName") String cWorkerName) 
	{
		String cMethodName = "";
		
		String sqlQuery = "";
		
		Session hbsSession = null;
		
		Transaction tx = null;
		
		ArrayList<WorkerConfiguration> cWorkerConfigs = 
				new ArrayList<WorkerConfiguration>();
		
		WorkerConfigurationListMO cSourceConfigurationList = 
				new WorkerConfigurationListMO();
				
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	    
	        if ( null == cWorkerName )
    		{
    			cLogger.error(cMethodName + "::(null == cWorkerName)");
    			
    			cRes = false;
    		}
	       
    		//------
    		if ( cRes )
    		{
	    		if ( null == this.cToolkitDataProvider )
	    		{
	    			cLogger.error(cMethodName + "::cToolkitDataProvider is NULL. Reinitialize.");
	    			
	    			cRes = this.initClassVariables();
	    		}
    		}
    		//------
    		if ( cRes )
    		{
    			sqlQuery = this.cToolkitDataProvider.gettSQL().getSqlQueryByFunctionName(cMethodName);
    			
    			if ( null == sqlQuery || StringUtils.isEmpty(sqlQuery))
        		{
        			cLogger.error(cMethodName + "::sqlQuery is NULL for the Method:" + cMethodName);
        			
        			cRes = false;
        		}
    		}
    		//------
    		if ( cRes )
    		{
    			hbsSession = cToolkitDataProvider.gettDatabase().getHbsSessions().openSession();
    			
    			SQLQuery cQuery = hbsSession.createSQLQuery(sqlQuery);
    			
    			cQuery.addEntity(WorkerConfiguration.class);
    			
    			cQuery.setString("workerName", cWorkerName);
    			
    			tx = hbsSession.beginTransaction();
    			
				cWorkerConfigs = (ArrayList<WorkerConfiguration>)cQuery.list();
				
				if ( null == cWorkerConfigs )
				{
					cLogger.error("M.V. Custom::" + cMethodName + "::cConfiguration  is NULL!");
					
					cRes = false;
				}
				else
				{
					for( WorkerConfiguration cWorkerConfig : cWorkerConfigs )
					{
						WorkerConfigurationMO cSourceConfigMO = new WorkerConfigurationMO();
						
						//---config key/value
						cSourceConfigMO.setSourceconfigid(cSourceConfigMO.getSourceconfigid());
						cSourceConfigMO.setConfigkey(cWorkerConfig.getConfigkey());
						cSourceConfigMO.setConfigvalue(cWorkerConfig.getConfigvalue());
						cSourceConfigMO.setUnit(cWorkerConfig.getUnit());
						
						//---source worker
						cSourceConfigMO.setSourceworkerid(cWorkerConfig.getWorker().getWorkerid());
						cSourceConfigMO.setWorker_name(cWorkerConfig.getWorker().getName());
						
						//---configtype
						cSourceConfigMO.setConfigurationtypeid(cWorkerConfig.getConfigurationtypeM().getConfigurationtypeid());
						cSourceConfigMO.setTarget(cWorkerConfig.getConfigurationtypeM().getTarget());
						
						//---source
						cSourceConfigMO.setSourceid(cWorkerConfig.getSource().getSourceid());
						cSourceConfigMO.setSource_name(cWorkerConfig.getSource().getName());
						cSourceConfigMO.setCompany(cWorkerConfig.getSource().getCompany());
						
						//---source type
						cSourceConfigMO.setSourcetypeid(cWorkerConfig.getSource().getSourcetypeM().getSourcetypeid());
						cSourceConfigMO.setSourcetype_name(cWorkerConfig.getSource().getSourcetypeM().getName());
						
						cSourceConfigurationList.cSourceConfiguration.add(cSourceConfigMO);
					}
				}
    		}
    		
    		if ( tx != null )
			{
				tx.commit();
			}
    		
    		return Response.ok(cSourceConfigurationList).build();
		}
		catch( Exception e)
		{
			cLogger.error("M.V. Custom::" + cMethodName + "::" + e.getMessage());
			
			tx.rollback();
			
			e.printStackTrace();
			
			return Response.serverError().build();
		}
		finally
		{
			if ( hbsSession != null )
    		{
    			hbsSession.close();
    		}
		}
	}
}