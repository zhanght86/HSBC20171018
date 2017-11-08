package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqBill;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连批处理入口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 * @Cleaned QianLy 2009-07-16
 */
public class DealInsuAccPrint implements BusinessService
{
private static Logger logger = Logger.getLogger(DealInsuAccPrint.class);


    private GlobalInput mGlobalInput = new GlobalInput();
    public CErrors mErrors = new CErrors();
    public VData mResult = new VData();
    private TransferData mTransferData = new TransferData();
    public VData mInputData = new VData();





	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData data, String Operater) {
		mInputData = data;
		mGlobalInput = (GlobalInput) data.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData)data.getObjectByObjectName("TransferData", 0);
		String ClassBL = (String)mTransferData.getValueByName("ClassBL");
		if(ClassBL == null ||ClassBL.equals("")){
			CError.buildErr(this, "打印数据传输失败！");
			return false;
		}
		BqBill _BqBill = null;
		try {

			Class _Class = Class.forName(ClassBL);
			_BqBill = (BqBill)_Class.newInstance();
			if(!_BqBill.submitData(mInputData,"PRINT"))
			{
				CError.buildErr(this, _BqBill.getErrors().getFirstError());
				return false;  
			}
			mResult = _BqBill.getResult();
		}catch (Exception ex){
			CError.buildErr(this, _BqBill.getErrors().getFirstError());
			return false;  
		}
		
		
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
