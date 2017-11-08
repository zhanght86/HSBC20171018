package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class DSImpartUI {
private static Logger logger = Logger.getLogger(DSImpartUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	public boolean submitData( VData cInputData, String cOperate )
	{
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		DSImpartBL tDSImpartBL = new DSImpartBL();

		logger.debug("---UI BEGIN---");
		if( tDSImpartBL.submitData( cInputData, mOperate ) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tDSImpartBL.mErrors );
			return false;
		}
		else
		{
			mResult = tDSImpartBL.getResult();
		}
                logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult()
	{
		return mResult;
	}
}
