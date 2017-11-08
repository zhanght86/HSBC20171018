package com.sinosoft.service.handle;
import org.apache.log4j.Logger;

import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.service.SqlParse;
import com.sinosoft.utility.TransferData;
/**
 * 查询业务前置处理,把sql信息,转换成sql语句,然后提交后台处理
 *  <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class QueryHandle implements Handle {
private static Logger logger = Logger.getLogger(QueryHandle.class);

	public void invoke(IMessage message) {
		String mSQL = (String) message.getVData().getObject(0);
		//SQL类型 0-正常,1-绑定变量
		String mSQLType = "0";
		if (mSQL.indexOf(";") != -1)
		{
			SqlParse sqlParse = new SqlParse();
			mSQL = sqlParse.parseSQL(mSQL);
			
		}
		message.getVData().remove(0);
		logger.debug("mSQLType:"+mSQLType);
		//logger.debug("mSQL:"+mSQL);
		message.getVData().add(0, mSQL);
		message.getVData().add(4, mSQLType);
		
		
		
	}	
	public void init(TransferData transfer) {		
	}

}