 
package amp.managed.storage.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.axis.utils.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amp.jpa.entities.WorkerData;
import amp.jpaentities.mo.WorkerDataListMO;
import amp.jpaentities.mo.WorkerDataMO;
import amp.managed.storage.base.StorageServiceBase;


@Path("StatusService")
@Consumes({"application/json", "application/xhtml+xml", "application/xml", "multipart/form-data", "text/html", "image/jpeg", "text/plain", "text/xml", "image/gif" })
@Produces({"application/json", "application/xhtml+xml", "application/xml", "multipart/form-data", "image/jpeg", "text/plain", "text/xml", "image/gif" })
public class StatusService extends StorageServiceBase
{
	private static final Logger cLogger = 
			LoggerFactory.getLogger(StatusService.class);
	
	//---class variables----------------------
	
	
	//---getters/setters----------------------
	
	
	//---class methods------------------------
	/**
     * Default constructor. 
     */
    public StatusService() 
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
	//---
    @POST
  	@Path("/setItemOpStatus")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	public Response setItemOpStatus(
  			@QueryParam("sourceName")  String sourceName, 
  			@QueryParam("targetName")  String targetName,
  			@QueryParam("workerName")  String workerName,
  			@QueryParam("threadName")  String threadName,
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
    			
    			Query query1 = hbsSession.createSQLQuery("DELETE FROM WORKER_DATA WHERE " 
    	    			+ "WORKERDATA2SOURCE=(select sourceid from source where name=:sourceName) AND "
    	    			+ "WORKERDATA2TARGET=(select targetid from target where name=:targetName) AND "
    	    			+ "WORKERDATA2WORKER=(select workerid from worker where name=:workerName) AND "
    	    			+ "WORKERDATA2THREAD=(select threadid from thread where name=:threadName) AND "
    	    			+ "WORKERDATA2OPTYPE=(select operationtypeid from operationtype_m where name=:opTypeName) AND "
    	    			+ "ITEMID=:itemKey ");
    			
    			query1.setString("sourceName", sourceName);
    			query1.setString("targetName", targetName);
    			query1.setString("workerName", workerName);
    			query1.setString("threadName", threadName);
    			query1.setString("opTypeName", opTypeName);
    			query1.setString("itemKey",    itemKey);	
    	    			
    			query1.executeUpdate();	
    	    		
    			//---
    			Query query2 = hbsSession.createSQLQuery("INSERT INTO WORKER_DATA " + 
    			"(WORKERDATA2SOURCE, "
    			+ "WORKERDATA2TARGET, "
    			+ "WORKERDATA2WORKER, "
    			+ "WORKERDATA2THREAD, "
    			+ "WORKERDATA2OPTYPE, "
    			+ "WORKERDATA2STATUS, "
    			+ "ITEMID, "
    			+ "DESCRIPTION, "
    			+ "WORKERDATAHASH, "
    			+ "UPDATEDATE) " + 
    			"VALUES ("
    			+ " (select sourceid from source where name=:sourceName), "
    			+ " (select targetid from target where name=:targetName), "
    			+ " (select workerid from worker where name=:workerName), "
    			+ " (select threadid from thread where name=:threadName), "
    			+ " (select operationtypeid from operationtype_m where name=:opTypeName), "
    			+ " (select statusid from status_m where name=:status), "
    			+ " :itemKey, "
    			+ " :description, "
    			+ " :hash,"
    			+ " :updateDate" + ")");
    			
    			query2.setString("sourceName", sourceName);
    			query2.setString("targetName", targetName);
    			query2.setString("workerName", workerName);
    			query2.setString("threadName", threadName);
    			query2.setString("opTypeName", opTypeName);
    			query2.setString("status",     status);
    			query2.setString("itemKey",    itemKey);
    			query2.setTimestamp("updateDate", updateDate);
    			query2.setString("description", description);
    			query2.setString("hash", hashEncr);
    			
    			query2.executeUpdate();
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
      		e.printStackTrace();
      		
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
    //---
  	@POST
  	@Path("/setItemOpStatusMO")
  	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  	public Response setItemOpStatusMO(WorkerDataMO cWorkerDataMO)
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
    			
    			String sourceId = String.valueOf(cWorkerData.getSource().getSourceid());
      			String targetId = String.valueOf(cWorkerData.getTarget().getTargetid());
      			String workerId = String.valueOf(cWorkerData.getWorker().getWorkerid());
      			String threadId = String.valueOf(cWorkerData.getThread().getThreadid());
      			String opTypeName = String.valueOf(cWorkerData.getOperationtypeM().getOperationtypeid());
      			String itemKey    = cWorkerData.getItemid();
      			String status     = cWorkerData.getStatusM().getName();
    			
      			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
      			
      			cWorkerData.setUpdatedate(new Date(updateDate.getTime()));
      			
    			String hashClean = sourceId + "_" + targetId + "_" + 
				                   workerId + "_" + threadId + "_" + 
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
  	}
  	
  	//---
    @SuppressWarnings("unchecked")
	@GET
	@Path("/getItemOpStatus")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getItemOpStatus(@QueryParam("sourceName")  String sourceName, 
						  			@QueryParam("targetName")  String targetName,
						  			@QueryParam("workerName")  String workerName,
						  			@QueryParam("threadName")  String threadName,
						  			@QueryParam("opTypeName")  String opTypeName,
						  			@QueryParam("itemKey")     String itemKey) 
	{
		String cMethodName = "";
		
		String sqlQuery = "";
		
		Session hbsSession = null;
		
		Transaction tx = null;
		
		ArrayList<WorkerData> cWorkerDataList = 
				new ArrayList<WorkerData>();
		
		WorkerDataListMO cWorkerDataListMO = 
				new WorkerDataListMO();
				
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	    
	        if ( StringUtils.isEmpty(workerName) || StringUtils.isEmpty(threadName) || 
	        	 StringUtils.isEmpty(sourceName) || StringUtils.isEmpty(targetName) || 
	        	 StringUtils.isEmpty(opTypeName) || StringUtils.isEmpty(itemKey))
    		{
    			cLogger.error(cMethodName + "::Null Parameters. Check Parameters!");
    			
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
    			
    			cQuery.addEntity(WorkerData.class);
    			
    			cQuery.setString("sourceName", sourceName);
    			cQuery.setString("targetName", targetName);
    			cQuery.setString("workerName", workerName);
    			cQuery.setString("threadName", threadName);
    			cQuery.setString("opTypeName", opTypeName);
    			cQuery.setString("itemKey", itemKey);
    			
    			tx = hbsSession.beginTransaction();
    			
				cWorkerDataList = (ArrayList<WorkerData>)cQuery.list();
				
				if ( null == cWorkerDataList )
				{
					cLogger.error("M.V. Custom::" + cMethodName + "::cConfiguration  is NULL!");
					
					cRes = false;
				}
				else
				{
					for( WorkerData cWorkerData : cWorkerDataList )
					{
						WorkerDataMO cWorkerDataMO = new WorkerDataMO();
						
						cWorkerDataMO.getcWorkerData().getSource().setSourceid(
								cWorkerData.getSource().getSourceid());
						
						cWorkerDataMO.getcWorkerData().getTarget().setTargetid(
								cWorkerData.getTarget().getTargetid());
						
						cWorkerDataMO.getcWorkerData().getOperationtypeM().setOperationtypeid(
								cWorkerData.getOperationtypeM().getOperationtypeid());
						
						cWorkerDataMO.getcWorkerData().getOperationtypeM().setName(
								cWorkerData.getOperationtypeM().getName());
						
						cWorkerDataMO.getcWorkerData().getWorker().setWorkerid(
								cWorkerData.getWorker().getWorkerid());
								
						cWorkerDataMO.getcWorkerData().getThread().setThreadid(
								cWorkerData.getThread().getThreadid());
						
						cWorkerDataMO.getcWorkerData().getStatusM().setName(
								cWorkerData.getStatusM().getName());
						
						cWorkerDataMO.getcWorkerData().setWorkerdatahash(
								cWorkerData.getWorkerdatahash());
						
						cWorkerDataMO.getcWorkerData().setItemid(
								cWorkerData.getItemid());
						
						cWorkerDataMO.getcWorkerData().setUpdatedate(
								cWorkerData.getUpdatedate());
						
						cWorkerDataMO.getcWorkerData().setDescription(
								cWorkerData.getDescription());
						
						cWorkerDataListMO.cWorkerData.add(cWorkerDataMO);
					}
				}
    		}
    		
    		if ( tx != null )
			{
				tx.commit();
			}
    		
    		return Response.ok(cWorkerDataListMO).build();
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