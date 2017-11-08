package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOCustomerImpartSchema;
import com.sinosoft.lis.tb.DSContBL;
import com.sinosoft.lis.tb.DSContUI;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLMakeUWMasterUI {
private static Logger logger = Logger.getLogger(LLMakeUWMasterUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public LLMakeUWMasterUI() {}

	// @Main
	public static void main(String[] args)
	{}

	// @Method
	/**
	* 数据提交方法
	* @param: cInputData 传入的数据
	*		  cOperate 数据操作字符串
	* @return: boolean
	**/
	public boolean submitData( VData cInputData, String cOperate )
	{
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		LLMakeUWMasterBL tLLMakeUWMasterBL = new LLMakeUWMasterBL();

		logger.debug("---UI BEGIN---");
		if( tLLMakeUWMasterBL.submitData( cInputData, mOperate ) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tLLMakeUWMasterBL.mErrors );
			return false;
		}
		else
		{
			mResult = tLLMakeUWMasterBL.getResult();
		}
                logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult()
	{
		return mResult;
	}

}
