package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorAppConfirmBLF {
private static Logger logger = Logger.getLogger(PEdorAppConfirmBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 保全人工核保工作流数据 */
	VData tWorkFlowVData = new VData();
	TransferData tWorkFlowTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections ref = new Reflections();
	MMap map = new MMap();
	LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private String mPolNo = "";
	private String mRiskCode = "";
	String mPayPrintParams = "";

	/** 默认是核保通过 */
	String mUwflag = "9";

	public PEdorAppConfirmBLF() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作,查询出所有正在申请的数据
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		if (cOperate.equals("INSERT||EDORAPPCONFIRM")) {
			if (!dealData()) {
				return false;
			}
			// 保全自动核保，内含保全财务合计处理，将批改补退费表数据合计到应收总表
			// if (!autoUW())
			// {
			// return false;
			// }
			logger.debug("---End autoUW---");

			// 生成打印数据
			// if (!getPrintData())
			// {
			// return false;
			// }
			// logger.debug("---End getPrintData---");

			// 生成打印收费参数
			// if (!getPrintParams())
			// {
			// return false;
			// }
			logger.debug("---End getPrintData---");

			mResult.clear();
			// mResult.add(mUwflag);
			// mResult.add(mLPEdorMainSet.get(1).getEdorType());
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// /**
	// * 保全自动核保，内含保全财务合计处理，将批改补退费表数据合计到应收总表
	// * @return
	// */
	// private boolean autoUW()
	// {
	// try
	// {
	// //保全核保
	// logger.debug("Start 保全自动核保...");
	//
	// VData tVData = new VData();
	// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
	// tLPEdorMainSchema.setPolNo(mPolNo);
	// tVData.addElement(tLPEdorMainSchema);
	// tVData.addElement(mGlobalInput);
	//
	// PEdorAutoUWBL tPEdorAutoUWBL = new PEdorAutoUWBL();
	//
	// //内含保全财务合计处理，将批改补退费表数据合计到应收总表
	// if (!tPEdorAutoUWBL.submitData(tVData, "INSERT||AUTOUWENDORSE"))
	// {
	// this.mErrors.copyAllErrors(tPEdorAutoUWBL.mErrors);
	// return false;
	// }
	//
	// if ((tPEdorAutoUWBL.getResult() != null)
	// && (tPEdorAutoUWBL.getResult().size() > 0))
	// {
	// logger.debug("----flaguw1:"
	// + tPEdorAutoUWBL.getResult().get(0));
	//
	// if (!tPEdorAutoUWBL.getResult().get(0).equals("9"))
	// {
	// //保全自动核保不通过,转入人工核保工作流
	// LPEdorMainSet tLPEdorMainSet = (LPEdorMainSet) mInputData
	// .getObjectByObjectName("LPEdorMainSet",
	// 0);
	// logger.debug("tLPEdorMainSet.size()"
	// + tLPEdorMainSet.size());
	// if ((tLPEdorMainSet != null)
	// && (tLPEdorMainSet.size() == 1)) //目前只针对批单进行保全人工核保工作流处理
	// {
	// //准备工作流数据
	// LPEdorMainSchema tempLPEdorMainSchema = new LPEdorMainSchema();
	// tempLPEdorMainSchema = tLPEdorMainSet.get(1);
	// if (!prepareWorkFlowData(tempLPEdorMainSchema))
	// {
	// CError.buildErr(this,
	// "该保单保全自动核保不通过,在准备待转入人工核保工作流处理的数据时出错");
	// return false;
	// }
	//
	// //创建保全人工核保工作流起始任务
	// PEdorManuUWWorkFlowUI tPEdorManuUWWorkFlowUI = new
	// PEdorManuUWWorkFlowUI();
	// if (!tPEdorManuUWWorkFlowUI.submitData(tWorkFlowVData,
	// "9999999999")) //创建保全核保工作流节点0000000000
	// {
	// this.mErrors.copyAllErrors(tPEdorManuUWWorkFlowUI.mErrors);
	// return false;
	// }
	// }
	// }
	//
	// if (!tPEdorAutoUWBL.getResult().get(0).equals("9"))
	// {
	// mUwflag = "5";
	// }
	// else
	// {
	// mUwflag = "9";
	// }
	// }
	// }
	// catch (Exception e)
	// {
	// CError.buildErr(this, "保全自动核保失败");
	// return false;
	// }
	//
	// return true;
	// }

	/**
	 * 生成打印数据
	 * 
	 * @return
	 */
	private boolean getPrintData() {
		try {
			// 生成打印数据
			logger.debug("Start 生成打印数据 insert into Print Table");

			VData tVData = new VData();
			tVData.addElement(mGlobalInput);

			PrtEndorsementBL tPrtEndorsementBL = new PrtEndorsementBL(
					mLPEdorMainSchema.getEdorNo());
			if (!tPrtEndorsementBL.submitData(tVData, "")) {
				this.mErrors.copyAllErrors(tPrtEndorsementBL.mErrors);
				return false;
			}
			MMap tmap = new MMap();
			tVData = tPrtEndorsementBL.getResult();
			tmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			if (tmap != null) {
				map.add(tmap);
			}
		} catch (Exception e) {
			CError.buildErr(this, "生成打印数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 查询收费打印参数
	 * 
	 * @return
	 */
	private boolean getPrintParams() {

		// 判断是否有保全交费产生，如果有，取得相应的打印交费表需要的参数
		String prtOtherNo = mLPEdorMainSet.get(1).getEdorNo();
		String strSql = "select * from ljspay where otherno = '" + "?prtOtherNo?"
				+ "'";
		logger.debug("strSql :" + strSql);
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strSql);
        sqlbv.put("prtOtherNo", prtOtherNo);
		LJSPaySet tPaySet = new LJSPaySet();
		LJSPayDB tPayDB = new LJSPayDB();
		tPaySet = tPayDB.executeQuery(sqlbv);
		if ((tPaySet != null) && (tPaySet.size() == 1)) {
			mPayPrintParams = tPaySet.get(1).encode();
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorMainSchema = (LPEdorMainSchema) mInputData
					.getObjectByObjectName("LPEdorMainSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// mPolNo = mLPEdorMainSchema.getPolNo();
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		// 查询保全主表中，该保单处于申请状态的保全数据
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		tLPEdorMainDB.setContNo(mLPEdorMainSchema.getContNo());
		tLPEdorMainDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		if (!tLPEdorMainDB.getInfo()) {
			CError.buildErr(this, "查询批单号为" + mLPEdorMainSchema.getEdorNo()
					+ "的保全主表时失败！");
			return false;
		}
		mLPEdorMainSchema.setSchema(tLPEdorMainDB.getSchema());
		return true;
	}

	/**
	 * 业务处理
	 */
	private boolean dealData() {
		VData pInputData = new VData();

		pInputData.clear();
		pInputData.addElement(mGlobalInput);
		pInputData.addElement(mLPEdorMainSchema);
		// ======del for
		// compile===zhangtao=====2005-07-02=============BGN===============
		// EdorAppConfirmBL tEdorAppConfirmBL = new EdorAppConfirmBL();
		// tEdorAppConfirmBL.setGlobalInput(mGlobalInput);
		// tEdorAppConfirmBL.setLPEdorMainSchema(mLPEdorMainSchema);
		// tEdorAppConfirmBL.setOperate(mOperate);
		// MMap tMap = new MMap();
		// tMap = tEdorAppConfirmBL.getEdorAppConfirmData();
		// if (tEdorAppConfirmBL.mErrors.needDealError())
		// {
		// this.mErrors.copyAllErrors(tEdorAppConfirmBL.mErrors);
		// return false;
		// }
		// if (tMap != null)
		// {
		// map.add(tMap);
		// }
		// ======del for
		// compile===zhangtao=====2005-07-02=============END===============
		logger.debug("---End App Confirm---");
		return true;
	}

	/**
	 * 准备提交的数据
	 */

	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 为自动核保后,需进入人工核保的保全准备转入工作流的数据
	 */
	private boolean prepareWorkFlowData(LPEdorMainSchema tempLPEdorMainSchema) {
		// //保全自动核保不通过,转入人工核保工作流
		// //准备保全核保相关表
		// //导入lcpol表到lppol //查询待更新相关保单
		// LCPolDB tOldLCPolDB = new LCPolDB();
		// LCPolSet tOldLCPolSet = new LCPolSet();
		// LPPolSet tNewLPPolSet = new LPPolSet();
		// tOldLCPolDB.setPolNo(tempLPEdorMainSchema.getPolNo());
		// if (!tOldLCPolDB.getInfo())
		// {
		// CError tError =new CError();
		// tError.moduleName="PEdorNSConfirmBL";
		// tError.functionName="preparePrem";
		// tError.errorMessage="查询保单数据时出错! ";
		// this.mErrors.addOneError(tError);
		// }
		//
		// String tPrtNo = tOldLCPolDB.getPrtNo();
		// tOldLCPolDB = new LCPolDB();
		// String strSql = "select * from lcpol where prtno='" + tPrtNo + "'";
		// logger.debug("strSql:"+strSql) ;
		// tOldLCPolSet = tOldLCPolDB.executeQuery(strSql);
		// for(int i = 1 ;i<tOldLCPolSet.size() ;i++)
		// {
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// tLCPolSchema = tOldLCPolSet.get(i);
		// String tPolNo = tLCPolSchema.getPolNo();
		//
		// LPPolDB tLPPolDB = new LPPolDB();
		// tLPPolDB.setPolNo(tPolNo) ;
		// LPPolSet tLPPolSet = new LPPolSet();
		// tLPPolSet = tLPPolDB.query() ;
		// if(tLPPolSet == null )
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorAppConfirmBLF";
		// tError.functionName = "prepareWorkFlowData";
		// tError.errorMessage = "保单"+tPolNo+"信息查询失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		//
		// if(tLPPolSet.size() == 0 )
		// {
		// LPPolSchema tLPPolSchema = new LPPolSchema();
		// mReflections.transFields(tLPPolSchema,tLCPolSchema);
		// tNewLPPolSet.add(tLPPolSchema) ;
		// }
		//
		// }
		//
		// //导入lcduty表到lpduty
		// for(int i = 1 ;i<tOldLCPolSet.size() ;i++)
		// {
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// tLCPolSchema = tOldLCPolSet.get(i);
		// String tPolNo = tLCPolSchema.getPolNo();
		// LCDutySet
		//
		// }
		//
		// //导入lcprem表到lpprem
		//
		// //导入lcspec表到lpspec
		// 准备传输工作流数据 VData
		// 校验保单信息
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		String sql = "select * from LCPol " + " where polno='" + "?mPolNo?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mPolNo", mPolNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if ((tLCPolSet == null) || (tLCPolSet.size() != 1)) {
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLF";
			tError.functionName = "prepareWorkFlowData";
			tError.errorMessage = "保单" + mPolNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = tLCPolSet.get(1);

		LMRiskDB tLMRiskDB = new LMRiskDB();
		LMRiskSet tLMRiskSet = new LMRiskSet();
		tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
		tLMRiskSet = tLMRiskDB.query();
		if ((tLMRiskSet == null) || (tLMRiskSet.size() != 1)) {
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLF";
			tError.functionName = "prepareWorkFlowData";
			tError.errorMessage = "保单" + mPolNo + "险种信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = tLMRiskSet.get(1);

		tWorkFlowTransferData.setNameAndValue("EdorNo", tempLPEdorMainSchema
				.getEdorNo());
		tWorkFlowTransferData.setNameAndValue("PolNo", tLCPolSchema.getPolNo());
		tWorkFlowTransferData.setNameAndValue("RiskCode", tLMRiskSchema
				.getRiskCode());
		tWorkFlowTransferData.setNameAndValue("RiskName", tLMRiskSchema
				.getRiskName());
		tWorkFlowTransferData.setNameAndValue("InsuredNo", tLCPolSchema
				.getInsuredNo());
		tWorkFlowTransferData.setNameAndValue("InsuredName", tLCPolSchema
				.getInsuredName());
		tWorkFlowTransferData.setNameAndValue("AppntNo", tLCPolSchema
				.getAppntNo());
		tWorkFlowTransferData.setNameAndValue("AppntName", tLCPolSchema
				.getAppntName());
		tWorkFlowTransferData.setNameAndValue("ManageCom", tLCPolSchema
				.getManageCom());

		tWorkFlowVData.add(mGlobalInput);
		tWorkFlowVData.add(tWorkFlowTransferData);
		logger.debug("ok-prepareWorkFlowData");

		return true;
	}

	public String getPrtParams() {
		return mPayPrintParams;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorAppConfirmBLF aPEdorAppConfirmBLF = new PEdorAppConfirmBLF();
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorMainSchema.setEdorNo("410000000000028");
		tLPEdorMainSchema.setEdorAcceptNo("86000000000008");
		tLPEdorMainSchema.setContNo("230110000000184");
		tInputData.addElement(tLPEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		aPEdorAppConfirmBLF.submitData(tInputData, "INSERT||EDORAPPCONFIRM");
	}
}
