package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:应交费用类（界面输入）（暂对个人） 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class TempFeeUI implements BusinessService {
private static Logger logger = Logger.getLogger(TempFeeUI.class);

	// 业务处理相关变量
	private String[] strResult;
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public TempFeeUI() {
	}

	public static void main(String[] args) {
		TempFeeUI TempFeeUI1 = new TempFeeUI();
		
		String[] x=new String[]{"2","3"};
		
		logger.debug(x.getClass().getName());
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		logger.debug("Start TempFee UI Submit...");


		logger.debug("End TempFee UI Submit...");
		TempFeeBL tTempFeeBL = new TempFeeBL();
		if(!tTempFeeBL.submitData(mInputData, cOperate))
		{
			this.mErrors.copyAllErrors(tTempFeeBL.mErrors);
			return false;
			
		}
		else
		{
			mInputData = null;
			mInputData = tTempFeeBL.getResult();
			mInputData.add(strResult);
		}
			
		return true;
	}

	public VData getResult() {
		return mInputData;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
