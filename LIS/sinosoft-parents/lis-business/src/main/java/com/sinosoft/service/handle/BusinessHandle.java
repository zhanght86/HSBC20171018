package com.sinosoft.service.handle;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.service.Service;
import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 处理业务逻辑方法
 * 解包,调用实际业务处理 打包,返回处理结果
 *  
 * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class BusinessHandle implements Handle {
private static Logger logger = Logger.getLogger(BusinessHandle.class);
	private Service service;
	public BusinessHandle(Service service)
	{
		this.service = service;
	}
	public void init(TransferData transfer) {
	}
	
	public void invoke(IMessage message) {
		/**获取前台出入参数*/
		VData tData = message.getVData();
		String tOperate = message.getOperate();
		BusinessService tBusinessService;
		try {
			/**实例化对应业务夐理类*/
			tBusinessService = (BusinessService) Class.forName(service.getClassName()).newInstance();
			/**提交业务处理*/
			if(!tBusinessService.submitData(tData, tOperate))
			{
				/**打包返回错误*/
				message.setSuccess(false);
				message.setCErrors(tBusinessService.getErrors());
				message.setResult(tBusinessService.getResult());
			}
			else
			{
				/**打包返回处理结果*/
				message.setSuccess(true);
				message.setCErrors(tBusinessService.getErrors());
				message.setResult(tBusinessService.getResult());
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new ServiceException("实例化业务对应类失败!异常信息:"+ex.toString());
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new ServiceException("实例化业务对应类失败!异常信息:"+ex.toString());
		} catch (InstantiationException ex) {
			ex.printStackTrace();
			throw new ServiceException("实例化业务对应类失败!异常信息:"+ex.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new ServiceException("业务处理失败!异常信息"+ex.toString());
		}
	}

}