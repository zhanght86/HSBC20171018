package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCPolOriginalSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolOriginalSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title: BPODealInputDataBL
 * </p>
 * <p>
 * Description: 录入保存保单原始信息处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class SaveOriginalAfterEndServiceBLF implements AfterEndService {
private static Logger logger = Logger.getLogger(SaveOriginalAfterEndServiceBLF.class);
	/** 数据处理类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	
	private String mContNo = "";
	private LCPolSet tLCPolSet = new LCPolSet();
	private LCPolOriginalSet tLCPolOriginalSet = new LCPolOriginalSet();

	public SaveOriginalAfterEndServiceBLF() {
	}

	/**
	 * 处理界面提交数据的公共方法
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate))
			return false;

		if (!checkData())
			return false;

		if (!dealData())
			return false;
		
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;
		
		if (!prepareOutputData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) { 
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// 获得当前工作任务的任务ContNo
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null || mContNo.equals("")) {
			CError.buildErr(this, "ContNo数据传输失败!") ;
			return false;
		}
		return true;
	}

	/**
	 * @param checkData
	 * @return
	 */
	private boolean checkData() {
		try {
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setContNo(mContNo);
				tLCPolSet = tLCPolDB.query();
				if(tLCPolSet == null || tLCPolSet.size()<1)
				{
					CError.buildErr(this, "LCPol查询失败！");
					return false;
				}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean dealData() {
		Reflections mReflections = new Reflections();		
		for(int i=1;i<=tLCPolSet.size();i++)
		{
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolOriginalSchema tLCPolOriginalSchema = new LCPolOriginalSchema();
			tLCPolSchema = tLCPolSet.get(i);
			mReflections.transFields(tLCPolOriginalSchema,
					tLCPolSchema);
			tLCPolOriginalSet.add(tLCPolOriginalSchema);
			
		}
		return true;
	}
	
	/** 为下一结点准备数据 */
	private boolean prepareTransferData() {		
		return true;
	}
	
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(tLCPolOriginalSet, "INSERT");

		mResult.add(map);
		return true;
	}
	
	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
	
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	
}
