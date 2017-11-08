/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.service;
import org.apache.log4j.Logger;


import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:Home接口</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author lk
 * @version 1.0
 * @date: 2008-05-20
 */
public interface BusinessHome extends EJBHome 
{
	public Business create() throws RemoteException,CreateException;
}
