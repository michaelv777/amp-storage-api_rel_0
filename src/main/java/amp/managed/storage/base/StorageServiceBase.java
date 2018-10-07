/**
 * 
 */
package amp.managed.storage.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import amp.common.api.impl.ToolkitDataProvider;
import amp.common.api.impl.ToolkitReflection;
import amp.common.api.impl.ToolkitSpringConfig;

/**
 * @author MVEKSLER
 *
 */
public abstract class StorageServiceBase 
{
	private static final Logger cLogger = 
			LoggerFactory.getLogger(StorageServiceBase.class);
	
	protected String cFrameworkBeansConfig = "FrameworkBeans.xml";
	
	protected String cFrameworkBeansProps =  "FrameworkBeans.properties";
	
	//---class variables---------------------------------
	//@Autowired
	protected ToolkitDataProvider cToolkitDataProvider = null;
	
	private ToolkitReflection iReflection = null;
	
	

	protected Properties cSpringProps = null;
	
	private ApplicationContext applicationContext = null;
	
	protected boolean lcRes = true;
	
	//---getters/setters
	public String getcFrameworkBeansConfig() {
		return cFrameworkBeansConfig;
	}

	public void setcFrameworkBeansConfig(String cFrameworkBeansConfig) {
		this.cFrameworkBeansConfig = cFrameworkBeansConfig;
	}

	public String getcFrameworkBeansProps() {
		return cFrameworkBeansProps;
	}

	public void setcFrameworkBeansProps(String cFrameworkBeansProps) {
		this.cFrameworkBeansProps = cFrameworkBeansProps;
	}

	public ToolkitDataProvider getcToolkitDataProvider() {
		return cToolkitDataProvider;
	}

	public void setcToolkitDataProvider(ToolkitDataProvider cToolkitDataProvider) {
		this.cToolkitDataProvider = cToolkitDataProvider;
	}

	public Properties getcSpringProps() {
		return cSpringProps;
	}

	public void setcSpringProps(Properties cSpringProps) {
		this.cSpringProps = cSpringProps;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	 
	
	public boolean isLcRes() {
		return lcRes;
	}

	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}

	//---class methods---------------------------------
	/**
	 * 
	 */
	public StorageServiceBase() 
	{
		boolean cRes = true;

		try
    	{
			cRes = this.initClassVariables();
			
			if ( !cRes )
			{
				cLogger.error("MappingHandler() is false!");
				
				this.setLcRes(cRes);
			}
			
    	}    		
    	catch( Exception e)
    	{
    		cLogger.error("MappingHandler:" + e.getStackTrace());
    		
    		this.setLcRes(cRes = false);
    	}
	}
	/*-------------------------------------------------------------------*/
	protected boolean initClassVariables()
	{
		boolean cRes = true;
		
		String  methodName = "";
	
		try
    	{
    		this.iReflection = new ToolkitReflection();
    		
    		methodName = this.iReflection.getMethodName();
    		
    		//-----------------
    		if ( cRes )
    		{
    			cRes = this.configureSpringExt();
    		}
    		//-----------------
    		if ( cRes )
    		{
    			this.cToolkitDataProvider = (ToolkitDataProvider)
    						this.applicationContext.getBean("toolkitDataProvider");
    			
    			if ( null == this.cToolkitDataProvider )
	    		{
    				cRes = false;
    				
	    			cLogger.error(methodName + "::cToolkitDataProvider is NULL!");
	    		}
    			else
    			{
    				cRes = this.cToolkitDataProvider.isLcRes();
    				
    				cLogger.info(methodName + "::cToolkitDataProvider status is " + cRes);
    			}
    		}	
    		//-----------------
    		if ( cRes )
    		{
    			List<Class<? extends Object>> clazzes = this.cToolkitDataProvider.
    					gettDatabase().getPersistanceClasses();
    			
    			this.cToolkitDataProvider.
    					gettDatabase().getHibernateSession(clazzes);
				
    			this.setLcRes(cRes = this.cToolkitDataProvider.
    					gettDatabase().isLcRes());
    		}
    		//-----------------
    		
    		return cRes;	 
    	}
		catch(  NoSuchBeanDefinitionException nbd )
		{
			cLogger.error(methodName + "::" + nbd.getMessage());
    		
    		this.setLcRes(cRes = false);
    		return cRes;
		}
		catch(  BeansException be )
		{
			cLogger.error(methodName + "::" + be.getMessage());
    		
    		this.setLcRes(cRes = false);
    		return cRes;
		}
    	catch( Exception e)
    	{
    		cLogger.error(methodName + "::" + e.getMessage());
    		
    		this.setLcRes(cRes = false);
    		return cRes;
    	}
	}
	/*-----------------------------------------------------------------------------------*/
	protected boolean configureSpringExt() 
	{
		boolean cRes = true;
		
		try 
		{
			this.applicationContext = 
					new AnnotationConfigApplicationContext(ToolkitSpringConfig.class);
			
			return cRes;

		} 
		catch (Exception e) 
		{
			cLogger.error("problem with configuration files!" + e.getMessage());
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	/*-----------------------------------------------------------------------------------*/
	protected String hashString(String s) throws NoSuchAlgorithmException 
	{
	    byte[] hash = null;
	    
	    try 
	    {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        hash = md.digest(s.getBytes());
	        
	        StringBuilder sb = new StringBuilder();
		    
		    for (int i = 0; i < hash.length; ++i) 
		    {
		        String hex = Integer.toHexString(hash[i]);
		        if (hex.length() == 1) 
		        {
		            sb.append(0);
		            sb.append(hex.charAt(hex.length() - 1));
		        } 
		        else 
		        {
		            sb.append(hex.substring(hex.length() - 2));
		        }
		    }
		    
		    String hashStr = sb.toString() + "_" + RandomStringUtils.randomAlphanumeric(20).toUpperCase();
		    
		    return hashStr;
	    } 
	    catch (NoSuchAlgorithmException e) 
	    { 
	    	e.printStackTrace(); 
	    	
	    	String hashStr = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
	    	
	    	return hashStr;
	    }
	}
}
