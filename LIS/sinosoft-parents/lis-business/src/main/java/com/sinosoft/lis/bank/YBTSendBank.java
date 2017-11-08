package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 
 * <p>Title: YBTSendBank</p>
 * <p>Description:银保通批量代付代扣发盘处理接口
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: SinoSoft</p>
 * @author Fuqx
 * @version 1.0
 */
public interface YBTSendBank
{
	public CErrors mErrors = new CErrors();
	public boolean submitData(VData cInputData, String cOperate) ;
	public VData getResult() ;
}
