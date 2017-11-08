package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.GlobalInput;
/**
 * <p>Title: </p>
 *
 * <p>Description:投连后续处理接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public abstract class DealInsuAccAfter
{
private static Logger logger = Logger.getLogger(DealInsuAccAfter.class);

    public CErrors mErrors = new CErrors();
    public LOPolAfterDealSet _LOPolAfterDealSet=new LOPolAfterDealSet();

    /*对需要后续处理的集合进行处理*/
    public abstract boolean dealAfter(GlobalInput _GlobalInput,LOPolAfterDealSchema _LOPolAfterDealSchema);
    /*处理结果集*/
    public abstract VData getResult();
    /*错误处理*/
    public abstract CErrors getErrors();
}
