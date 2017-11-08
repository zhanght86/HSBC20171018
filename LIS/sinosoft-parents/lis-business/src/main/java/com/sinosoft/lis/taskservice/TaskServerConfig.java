/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LDTaskGroupDetailSet;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskServerParamSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 批处理引擎参数配置
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>

 */
public class TaskServerConfig {
private static Logger logger = Logger.getLogger(TaskServerConfig.class);
	public CErrors mErrors = new CErrors();
	private String mOperate = "";
	private String mOperator = "";
	private LDTaskParamSet mLDTaskParamSet = new LDTaskParamSet();
	private LDTaskServerParamSet mLDTaskServerParamSet = new LDTaskServerParamSet();
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	public TaskServerConfig() {
	}

	public boolean submitData(VData aInputData, String aOperate) {
		mOperate = aOperate;

		if (!getInputData(aInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		mResult.add(mMap);

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, "")) {
			mErrors.addOneError(new CError("任务数据提交失败！"));

			return false;
		}

		return true;
	}

	private boolean getInputData(VData aInputData) {
		GlobalInput tGI = (GlobalInput) aInputData.getObjectByObjectName(
				"GlobalInput", 0);
		
		if(this.mOperate.toUpperCase().equals("SERVERCONFIG"))
		{
			this.mLDTaskParamSet = (LDTaskParamSet) aInputData.getObjectByObjectName(
					"LDTaskParamSet", 0);
		}
		else
		{
			this.mLDTaskServerParamSet = (LDTaskServerParamSet) aInputData.getObjectByObjectName(
					"LDTaskServerParamSet", 0);
		}
		
		
		if ((tGI == null) ) {
			mErrors.addOneError(new CError("传入数据不完全！"));

			return false;
		}


		mOperator = tGI.Operator;

		
		return true;
	}

	private boolean dealData() {
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		if (mOperate.toUpperCase().equals("SERVERCONFIG")) {
			
			if (this.mLDTaskParamSet.size()<=0) {
				mErrors.addOneError(new CError("数据不完全！"));
				return false;
			}
			
			for(int i=1;i<=this.mLDTaskParamSet.size();i++)
			{
				mLDTaskParamSet.get(i).setOperator(this.mOperator);
				mLDTaskParamSet.get(i).setMakeDate(tCurrentDate);
				mLDTaskParamSet.get(i).setMakeTime(tCurrentTime);
				mLDTaskParamSet.get(i).setModifyDate(tCurrentDate);
				mLDTaskParamSet.get(i).setModifyTime(tCurrentTime);
			}

			mMap.put(mLDTaskParamSet, "DELETE&INSERT");
		}
		else
		{
			if (this.mLDTaskServerParamSet.size()<=0) {
				mErrors.addOneError(new CError("数据不完全！"));
				return false;
			}
			mMap.put(mLDTaskServerParamSet, "DELETE&INSERT");
		}

		return true;
	}
}
