/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.service;
import org.apache.log4j.Logger;

import javax.naming.*;
import java.util.Properties;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:该类为web server请求服务的统一接口</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author lk
 * @version 1.0
 * @date: 2008-05-20
 */
public class BusinessDelegate
{
private static Logger logger = Logger.getLogger(BusinessDelegate.class);

	private int i = 0;
	/**
	 * 业务数据，处理结果等
	 */
	private IMessage mMessage = null;
	/**
	 * 默认参数文件
	 */
	private static ResourceBundle config = ResourceBundle.getBundle("config");
	/**
	 * EJB容器提供者
	 */
	private String server = config.getString("server");
	/**
	 * EJB容器提供者url
	 */
	private String url = config.getString("url");
	/**
	 * EJB容器提供者port
	 */
	private String port = config.getString("port");
	/**
	 * EJBName
	 */
	private String EJBName = config.getString("EJBName");
	/**认证用户*/
	private String authUser = config.getString("user");

	/**认证密码*/
	private String authPwd = config.getString("pwd");
	/**
	 * 上下文
	 */
	private static InitialContext initialContext = null;
	/**
	 * 上下文
	 */
	private static BusinessHome businessHome = null;

	/**
	 * 日志
	 */
	public static String classinfo = "com.sinosoft.service.BusinessDelegate";
	/**
	 * 日志
	 */
	public static Log log = LogFactory.getLog(classinfo);
	/**
	 * 初始化错误信息
	 */
	private String info = null;
	/**
	 * 是否初始化成功标志
	 */
	private static boolean initflag = false;
	/**
	 * 是否分层部署
	 */
	private static boolean mode = false;

	/**
	 * 构造函数
	 */
	private BusinessDelegate()
	{
		if(mode)
		{
			init();
		}
	}

	/**
	 * 初始化
	 */
	private void init()
	{
		try
		{
			getContext();
			getHome();
			initflag = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			i++;
			initflag = false;
			info = e.toString();
			log.error("初始化失败:" + info);
		}
	}

	/**
	 * 获取实例
	 */
	public static BusinessDelegate getBusinessDelegate()
	{
		return new BusinessDelegate();
	}

	/**
	 * web请求服务接口
	 * @param VData aVData,String aOperate,String aBusiName
	 */
	public boolean submitData(VData aVData, String aOperate, String aBusiName)
	{

		boolean aSuccess = false;
		mMessage = new Message();
		mMessage.setVData(aVData);
		mMessage.setOperate(aOperate);
		mMessage.setBusiName(aBusiName);
		if (mode)
		{
			if (initflag)
			{
				aSuccess = doPost(mMessage);
			}
			else
			{
				aSuccess = false;
				mMessage.setSuccess(false);
				if (mMessage.getCErrors() != null)
				{
					mMessage.getCErrors().addOneError(info);
				}
				else
				{
					CErrors aCErrors = new CErrors();
					aCErrors.addOneError(info);
					mMessage.setCErrors(aCErrors);
				}
			}
		}
		else
		{
			aSuccess = doLocal(mMessage);
		}
		if (aSuccess)
		{
			return mMessage.getSuccess();
		}
		else
		{
			return false;
		}
	}

	/**
	 * web请求服务返回错误
	 * @return CErrors
	 */
	public CErrors getCErrors()
	{
		return mMessage.getCErrors();
	}

	/**
	 * web请求服务返回结果
	 * @return CErrors
	 */
	public VData getResult()
	{
		return mMessage.getResult();
	}

	/**
	 * 请求服务
	 * @param Message aMessage
	 */
	private boolean doPost(IMessage aMessage)
	{
		try
		{
			Business mBusiness = businessHome.create();
			mMessage = mBusiness.request(mMessage);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// 重新初始化
			initflag = false;
			log.info(e.toString());
			if (mMessage.getCErrors() != null)
			{
				mMessage.getCErrors().addOneError(e.toString());
			}
			else
			{
				CErrors aCErrors = new CErrors();
				aCErrors.addOneError(e.toString());
				mMessage.setCErrors(aCErrors);
			}
			return false;
		}
	}

	/**
	 * 请求服务
	 * @param Message aMessage
	 */
	private boolean doLocal(IMessage aMessage)
	{
		try
		{
			LocalService.getLocalService().doService(mMessage);
			return true;
		}
		catch (Exception e)
		{
			log.info(e.toString());
			if (mMessage.getCErrors() != null)
			{
				mMessage.getCErrors().addOneError(e.toString());
			}
			else
			{
				CErrors aCErrors = new CErrors();
				aCErrors.addOneError(e.toString());
				mMessage.setCErrors(aCErrors);
			}
			return false;
		}
	}

	/**
	 * 定位远程服务类
	 * @return BusinessHome
	 */
	private void getHome() throws NamingException
	{
		//businessHome = (BusinessHome) initialContext.lookup(EJBName);
    	Object obj = initialContext.lookup(EJBName);
    	businessHome = (BusinessHome) PortableRemoteObject.narrow(obj,BusinessHome.class);

	}

	/**
	 * 初始化上下文  可能要池化
	 * @return InitialContext
	 */
	private void getContext() throws NamingException
	{
		if ("weblogic".equals(server))
		{
			Hashtable props = new Hashtable();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			props.put(Context.PROVIDER_URL, "t3://" + url + ":" + port);
			if (authUser != null && !authUser.equals(""))
			{
				props.put(Context.SECURITY_PRINCIPAL, authUser);
				props.put(Context.SECURITY_CREDENTIALS, authPwd);
			}

			initialContext = new InitialContext(props);
		}
		else if ("jboss".equals(server))
		{
			Hashtable props = new Hashtable();
			props.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			props.put(InitialContext.PROVIDER_URL, "jnp://" + url + ":" + port);
			if (authUser != null && !authUser.equals(""))
			{
				props.put(Context.SECURITY_PRINCIPAL, authUser);
				props.put(Context.SECURITY_CREDENTIALS, authPwd);
			}
			initialContext = new InitialContext(props);
		}
//		else if ("websphere".equals(server))
//		{
//
//			try
//			{
//				Hashtable props = new Hashtable();
//				props.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
//				props.put(Context.PROVIDER_URL, "iiop://" + url + ":" + port);
//				logger.debug("iiop://" + url + ":" + port);
//				if (authUser != null && !authUser.equals(""))
//				{
//					props.put(Context.SECURITY_PRINCIPAL, authUser);
//					props.put(Context.SECURITY_CREDENTIALS, authPwd);
//				}
//				initialContext = new InitialContext(props);
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//
//		}
		else
		{
			log.error("初始化失败:" + "不支持此种类型的上下文");
		}
	}
}
