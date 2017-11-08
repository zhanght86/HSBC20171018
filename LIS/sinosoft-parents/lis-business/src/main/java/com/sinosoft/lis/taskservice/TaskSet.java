/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskSchema;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 后台任务处理基本任务管理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class TaskSet {
private static Logger logger = Logger.getLogger(TaskSet.class);
	public CErrors mErrors = new CErrors();
	private String mOperate = "";
	private String mOperator = "";
	private LDTaskSchema mLDTaskSchema;
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	public TaskSet() {
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
		LDTaskSet tLDTaskSet = (LDTaskSet) aInputData.getObjectByObjectName(
				"LDTaskSet", 0);

		if ((tGI == null) || (tLDTaskSet == null) || (tLDTaskSet.size() <= 0)) {
			mErrors.addOneError(new CError("传入数据不完全！"));

			return false;
		}

		mLDTaskSchema = tLDTaskSet.get(1);
		mOperator = tGI.Operator;

		return true;
	}

	private boolean dealData() {
		if (mOperate.equals("INSERTTASK")) {
			if ((mLDTaskSchema.getTaskClass() == null)
					|| mLDTaskSchema.getTaskClass().equals("")
					|| (mLDTaskSchema.getTaskDescribe() == null)
					|| mLDTaskSchema.getTaskDescribe().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}

			int tCodeInt = 0;
			String tCodeString = "000000";
			LDTaskDB tLDTaskDB = new LDTaskDB();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select * from LDTask where TaskCode = (select MAX(TaskCode) from LDTask)");
			LDTaskSet tLDTaskSet = tLDTaskDB.executeQuery(sqlbv1); // 取最大序列号

			if ((tLDTaskSet != null) && (tLDTaskSet.size() > 0)) {
				tCodeString = tLDTaskSet.get(1).getTaskCode();
			}

			try {
				tCodeInt = Integer.parseInt(tCodeString);
				tCodeInt++;
				tCodeString = Integer.toString(tCodeInt);
				tCodeString = "000000".substring(1,
						6 - tCodeString.length() + 1)
						+ tCodeString; // 生成新任务编码
			} catch (Exception ex) {
				return false;
			}

			mLDTaskSchema.setTaskCode(tCodeString);
			mLDTaskSchema.setOperator(mOperator);
			mLDTaskSchema.setMakeDate(PubFun.getCurrentDate());
			mLDTaskSchema.setMakeTime(PubFun.getCurrentTime());
			mLDTaskSchema.setModifyDate(PubFun.getCurrentDate());
			mLDTaskSchema.setModifyTime(PubFun.getCurrentTime());
			mMap.put(mLDTaskSchema, "INSERT");
		} else if (mOperate.equals("DELETETASK")) {
			LDTaskDB tLDTaskDB = new LDTaskDB();

			if ((mLDTaskSchema.getTaskCode() == null)
					|| mLDTaskSchema.getTaskCode().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}

			mMap.put(mLDTaskSchema, "DELETE");
		}

		return true;
	}
}
