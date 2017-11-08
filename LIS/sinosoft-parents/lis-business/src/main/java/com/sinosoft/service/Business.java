/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.service;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:Remote接口</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author lk
 * @version 1.0
 * @date: 2008-05-20
 */
public interface Business extends EJBObject
{
	public IMessage request(IMessage message) throws RemoteException;
	//public IMessage response() throws RemoteException;
}
