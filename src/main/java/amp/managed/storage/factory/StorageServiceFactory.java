/**
 * 
 */
package amp.managed.storage.factory;

import amp.managed.storage.base.StorageServiceBase;

/**
 * @author MVEKSLER
 *
 */
public class StorageServiceFactory 
{

	/**
	 * 
	 */
	public StorageServiceFactory() 
	{
		// TODO Auto-generated constructor stub
	}

	public static StorageServiceBase getStorageService(Class<?> clazz)
	{
		try
		{
			if ( null == clazz )
			{
				return null;
			}
			else
			{
				return (StorageServiceBase)clazz.newInstance();
			}
		}
		catch( InstantiationException ie )
		{
			return null;
		}
		catch( IllegalAccessException ile )
		{
			return null;
		}
		catch( Exception e )
		{
			return null;
		}
	}
}
