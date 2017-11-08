package com.sinosoft.service;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: 业务逻辑接口,所有的业务处理类都要实现这个接口</p>
 *
 * <p>Description: ejb转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public interface BusinessService {
	/**提交业务处理*/
    public boolean submitData(VData vData,String Operater);
    /**获取处理结果*/
    public VData getResult();
    /**获取错误信息*/
    public CErrors getErrors();
}
