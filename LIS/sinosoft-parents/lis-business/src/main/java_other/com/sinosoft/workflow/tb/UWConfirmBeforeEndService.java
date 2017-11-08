package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.UWSendAllPrintBL;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.BeforeEndService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 人工核保工作流 - 发送拒保、延期通知书类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author tm
 * @version 1.0
 */

public class UWConfirmBeforeEndService implements BeforeEndService {
private static Logger logger = Logger.getLogger(UWConfirmBeforeEndService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap mMap = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	/** 核保结论标志 */
	private String mUWFlag = "";
	/** 核保流向 */
	private String mUWIdea = "";
	/** 合同号 */
	private String mContNo = "";
    //ln 2008-10-18 add 下延期拒保核保结论时的建议，需要打到核保通知书上
	private String mSugContInput = "";

	public UWConfirmBeforeEndService() {
	}

	public static void main(String[] args) {
		UWConfirmBeforeEndService UWConfirmBeforeEndService1 = new UWConfirmBeforeEndService();
	}

	public boolean submitData(VData cInputData, String cOperate) {
		/**
		 * @todo Implement this com.sinosoft.workflowengine.BeforeInitService
		 *       method
		 */
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 进行业务验证
		if (!checkData()) {
			return false;
		}
		//tongmeng 2009-06-26 modify
		//上报和返回下级不能生成通知书记录.
		String mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		logger.debug("mUWUpReport:"+mUWUpReport);
		if(mUWUpReport!=null&&mUWUpReport.equals("0"))
		{
			// 进行业务处理
			if (!dealData()) {
				return false;
			}
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}
		}

		return true;
	}

	public VData getResult() {
		/**
		 * @todo Implement this com.sinosoft.workflowengine.BeforeInitService
		 *       method
		 */
		return mResult;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.add(mMap);

		mResult.add(map);

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// 获得核保结论。
		this.mUWFlag = mTransferData.getValueByName("UWFlag") == null ? ""
				: (String) mTransferData.getValueByName("UWFlag");
		logger.debug("UWFlag:" + this.mUWFlag);
		if (this.mUWFlag.equals("")) {
			logger.debug("获得核保结论标志出错！");
			return false;
		}
		this.mUWIdea = mTransferData.getValueByName("UWIdea") == null ? ""
				: (String) mTransferData.getValueByName("UWIdea");
		logger.debug("UWIdea:" + this.mUWIdea);
		// if(this.mUWIdea.equals(""))
		// {
		// logger.debug("获得核保结论流向出错！");
		// return false;
		// }
		this.mContNo = mTransferData.getValueByName("ContNo") == null ? ""
				: (String) mTransferData.getValueByName("ContNo");
		if (this.mContNo.equals("")) {
			logger.debug("获得合同号出错！");
			return false;
		}
		//ln 2008-10-17 add
		this.mSugContInput = mTransferData.getValueByName("SugContInput") == null ? ""
				: (String) mTransferData.getValueByName("SugContInput");
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 准备发送的通知书数据.
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);// 合同号类型
		tLOPRTManagerSchema.setOtherNo(this.mContNo);// 合同号
		tLOPRTManagerSchema.setStandbyFlag7("TBJB");// 投保拒保类型
		String tGetNoticeNo = this.mTransferData.getValueByName("GetNoticeNo") == null ? "NO"
				: (String) this.mTransferData.getValueByName("GetNoticeNo");
		logger.debug("@@@@@发送单证时或者给付通知书号：" + tGetNoticeNo);
		tLOPRTManagerSchema.setStandbyFlag6(tGetNoticeNo);
		// if(tGetNoticeNo.equals("NO"))
		// {
		// logger.debug("获得给付通知书号为空");
		// }
		if (this.mUWFlag.equals("1")) {
			// 拒保
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DECLINE);// 拒保通知书
			//ln 2008-10-18 add
			if(!mSugContInput.equals(""))
			     tLOPRTManagerSchema.setRemark(mSugContInput);// 拒保建议			
			// tLOPRTManagerSchema.setCode("00");//拒保通知书
		} else if (this.mUWFlag.equals("2")) {
			// 延期
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_DEFER);// 延期通知书
			// tLOPRTManagerSchema.setCode("06");//延期通知书
			String tDelayDay = this.mTransferData.getValueByName("ValiDate") == null ? "NO"
					: (String) this.mTransferData.getValueByName("ValiDate");
			tLOPRTManagerSchema.setStandbyFlag5(tDelayDay);
			//ln 2008-10-18 add
			if(!mSugContInput.equals(""))
			     tLOPRTManagerSchema.setRemark(mSugContInput);// 延期建议	
		} else if (this.mUWFlag.equals("a")) {
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_WITHDRAW);
			// tLOPRTManagerSchema.setCode("09");
		} else {
			return true;
		}
		logger.debug("tLOPRTManagerSchema:"
				+ tLOPRTManagerSchema.getCode());
		mInputData.add(tLOPRTManagerSchema);
		// 加入业务逻辑处理类
		UWSendAllPrintBL tUWSendAllPrintBL = new UWSendAllPrintBL();
		if (!tUWSendAllPrintBL.submitData(mInputData, "")) {
			this.mErrors.copyAllErrors(tUWSendAllPrintBL.getErrors());
			return false;
		} else {
			mMap = (MMap) tUWSendAllPrintBL.getResult().getObjectByObjectName(
					"MMap", 0);
			mTransferData = null;
			mTransferData = (TransferData) tUWSendAllPrintBL.getResult()
					.getObjectByObjectName("TransferData", 0);
		}
		return true;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
