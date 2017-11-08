package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.UWManuAddDelBL;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.db.LLUWPremMasterDB;
import com.sinosoft.lis.db.LLUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLUWManuAddDelBL {
private static Logger logger = Logger.getLogger(LLUWManuAddDelBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mPolNo;
	private String mDutyCode;
	private String mPayPlanCode;
	private String mBatNo;
	private String mClmNo;
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	private LLUWPremMasterSet mLLUWPremMasterSet = new LLUWPremMasterSet();
	private MMap mMMap = new MMap();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LLUWMasterSet mLLUWMasterSet = new LLUWMasterSet();

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		// 提交数据

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		// mMMap;
		VData tVData = new VData();
		tVData.add(mMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this,"数据提交失败");
			return false;
		}
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
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		logger.debug("::::::::::::::mPolNo::::::::::::" + mPolNo);
		if (mPolNo == null || mPolNo.equals("null") || mPolNo.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输保单号失败!");
			return false;
		}

		mDutyCode = (String) mTransferData.getValueByName("DutyCode");
		logger.debug("::::::::::::::mDutyCode::::::::::::" + mDutyCode);
		if (mDutyCode == null || mDutyCode.equals("null")
				|| mDutyCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输责任编码失败!");
			return false;
		}

		mPayPlanCode = (String) mTransferData.getValueByName("PayPlanCode");
		if (mPayPlanCode == null || mPayPlanCode.equals("null")
				|| mPayPlanCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输缴费编码失败!");
			return false;
		}
		mBatNo = (String) mTransferData.getValueByName("BatNo");
		if (mBatNo == null || mBatNo.equals("")
				|| mPayPlanCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输BatNo失败!");
			return false;
		}
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if (mClmNo == null || mClmNo.equals("")
				|| mPayPlanCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输ClmNo失败!");
			return false;
		}
		logger.debug("::::::::::::::mPayPlanCode::::::::::::"
				+ mPayPlanCode);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();
		tLLUWPremMasterDB.setPolNo(mPolNo);
		tLLUWPremMasterDB.setDutyCode(mDutyCode);
		tLLUWPremMasterDB.setPayPlanCode(mPayPlanCode);
		tLLUWPremMasterDB.setClmNo(mClmNo);
		tLLUWPremMasterDB.setBatNo(mBatNo);
		mLLUWPremMasterSet = tLLUWPremMasterDB.query();

		if (mLLUWPremMasterSet.size() == 0) {
			CError.buildErr(this,"无法找到该笔数据!");
			return false;
		}
		else if(mLLUWPremMasterSet.size()!=1)
		{
			CError.buildErr(this,"查询加费记录有误!");
			return false;
		}
		mMMap.put(mLLUWPremMasterSet, "DELETE");

		// 得到lcprem 中的prem值，减去lopol中的加费
//		String tSql = "select prem " + " from lcprem " + "  where polno = '"
//				+ mPolNo + "' " + "  and dutycode = '" + mDutyCode + "' "
//				+ " and payplancode = '" + mPayPlanCode + "'";
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tSSRS = new SSRS();
//		tSSRS = tExeSQL.execSQL(tSql);
//		// tSSRS.GetText(1,1)
//		double tPremMoney = Double.parseDouble(tSSRS.GetText(1, 1));
//		LCPolDB tLCPolDB = new LCPolDB();
//		tLCPolDB.setPolNo(mPolNo);
//
//		mLCPolSet = tLCPolDB.query();
//
//		if (mLCPolSet.size() == 0) {
//			CError.buildErr(this,"查询保单数据失败!");
//			return false;
//		}
//		for (int i = 1; i <= mLCPolSet.size(); i++) {
//			double tLCPolPrem = mLCPolSet.get(i).getPrem() - tPremMoney;
//			mLCPolSet.get(i).setPrem(tLCPolPrem);
//		}
//		mMMap.put(mLCPolSet, "UPDATE");
//		
		//准备核保主表数据
		LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
//		tLLUWMasterDB.setProposalNo(mLCPolSet.get(1).getProposalNo());
		tLLUWMasterDB.setCaseNo(mClmNo);
		tLLUWMasterDB.setBatNo(mBatNo);
		mLLUWMasterSet = tLLUWMasterDB.query();

//		for (int i = 1; i <= mLCUWMasterSet.size(); i++) {
//			mLCUWMasterSet.get(i).setAddPremReason("");
//		}
//		mMMap.put(mLCUWMasterSet, "UPDATE");
		if(mLLUWMasterSet==null||mLLUWMasterSet.size()<1)
		{
			CError.buildErr(this,"查询核保主表失败!");
			return false;
		}
		LLUWMasterSchema tLLUWMasterSchema= new LLUWMasterSchema();
		tLLUWMasterSchema = mLLUWMasterSet.get(1);
		
		LCPremDB uLCPremDB = new LCPremDB();
		String tSql = "select * from lluwpremmaster where clmno='"+"?clmno?"+"' and payplancode like '000000%%' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("clmno", tLLUWMasterSchema.getCaseNo());
		LCPremSet tLCPremSet = uLCPremDB.executeQuery(sqlbv);
		if(tLCPremSet.size()>1)
			tLLUWMasterSchema.setAddPremFlag("1");
		else
			tLLUWMasterSchema.setAddPremFlag("0");
		tLLUWMasterSchema.setAddPremReason("");
		tLLUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLLUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLLUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLLUWMasterSchema, "UPDATE");
		
		//准备核保子表数据
		LLUWSubDB tLLUWSubDB = new LLUWSubDB();
		tSql = "select * from lluwsub where caseno='"+"?caseno?"
				+"' and batno ='"+"?batno?"+"' order by uwno desc";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("caseno", tLLUWMasterSchema.getCaseNo());
		sqlbv1.put("batno", tLLUWMasterSchema.getBatNo());
		LLUWSubSet tLLUWSubSet = tLLUWSubDB.executeQuery(sqlbv1);
		if(tLLUWSubSet==null)
		{
			CError.buildErr(this,"查询险种核保子表失败!");
			return false;
		}
		LLUWSubSchema tLLUWSubSchema= new LLUWSubSchema();	
		tLLUWSubSchema = tLLUWSubSet.get(1);
		tLLUWSubSchema.setAddPremFlag(tLLUWMasterSchema.getAddPremFlag());
		tLLUWSubSchema.setAddPremReason(tLLUWMasterSchema.getAddPremReason());
		tLLUWSubSchema.setUWNo(tLLUWSubSet.get(1).getUWNo()+1);
		tLLUWSubSchema.setOperator(mGlobalInput.Operator);
		tLLUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLLUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLLUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLLUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLLUWSubSchema, "INSERT");

//		String tSQL_Duty = "update lcduty a set prem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
//            			 + " where polno='"+mPolNo+"' "; 
//		logger.debug("tSQL_Duty:"+tSQL_Duty);
//		String tSQL_LCPol = "update lcpol a set prem = (select nvl(sum(prem),0) from lcduty where polno=a.polno) "
//						 // + " ,sumprem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
//                          + " where polno = '"+mPolNo+"' ";
//		logger.debug("tSQL_LCPol:"+tSQL_LCPol);
//		mMMap.put(tSQL_Duty, "UPDATE");
//
//		mMMap.put(tSQL_LCPol, "UPDATE");
//		String tSQL_Cont = "update lccont a set prem=(select nvl(sum(prem),0) from lcpol where contno=a.contno) "
//            //+ " ,sumprem = (select nvl(sum(prem),0) from lcpol where contno=a.contno) " 
//			 + " where contno=(select contno from lcpol where polno='"+mPolNo+"' ) ";
//		logger.debug("tSQL_Cont:"+tSQL_Cont);
//
//		mMMap.put(tSQL_Cont, "UPDATE");

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 测试数据
	 * 
	 * @return VData
	 */
	public static void main(String[] arg) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		String tPolNo = "110110000029808";
		logger.debug("===tPolNo======" + tPolNo);
		String tDutyCode = "146000"; // 加费类型
		logger.debug("===tDutyCode======" + tDutyCode);
		String tPayPlanType = "02"; // 加费原因
		logger.debug("===tPayPlanType======" + tPayPlanType);

		// 将三个值变量的值装在TransferData中
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo", tPolNo);
		tTransferData.setNameAndValue("DutyCode", tDutyCode);
		tTransferData.setNameAndValue("PayPlanType", tPayPlanType);

		// 将TransferData 装在VData
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGlobalInput);

		UWManuAddDelBL tUWManuAddDelUI = new UWManuAddDelBL();
		if (!tUWManuAddDelUI.submitData(tVData, "")) {
			// VData rVData = tUWManuAddDelUI.mErrors;
			logger.debug("失败");

		} else {
			logger.debug("成功");

		}
	}
}
