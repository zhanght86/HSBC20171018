package com.sinosoft.lis.list;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;


public class SinCardTempFeeUI {
private static Logger logger = Logger.getLogger(SinCardTempFeeUI.class);

	/**
	 * @param args
	 */
	public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
  
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;
    
	public boolean submitData(VData cInputData,String cOperate)
	{
		
		mInputData=(VData)cInputData.clone();
		mOperate=cOperate;
		SinCardTempFeeBL tSinCardTempFeeBL = new SinCardTempFeeBL();
		logger.debug(" 开始提交SinCardTempFeeBL处理了------");
		if(!tSinCardTempFeeBL.submitData(mInputData,mOperate))
		{
			mErrors.copyAllErrors(tSinCardTempFeeBL.mErrors);
			CError.buildErr(this, "业务逻辑处理失败");
			mResult.clear();
			return false;
		}
		else
		{
			mResult = tSinCardTempFeeBL.getResult();
		}
		
		
		return true;
	}
	
	public VData getResult()
	{
		return mResult;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

}
