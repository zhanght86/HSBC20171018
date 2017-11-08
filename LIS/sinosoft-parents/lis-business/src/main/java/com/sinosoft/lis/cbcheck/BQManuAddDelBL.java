package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LPUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQManuAddDelBL {
private static Logger logger = Logger.getLogger(BQManuAddDelBL.class);

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
	private String mEdorNo;
	private String mEdorType;

	private LPPremSchema mLPPremSchema = new LPPremSchema();
	private LPPremSet mLPPremSet = new LPPremSet();
	private MMap mMMap = new MMap();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPUWMasterSet mLPUWMasterSet = new LPUWMasterSet();

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
		logger.debug("::::::::::::::mPayPlanCode::::::::::::"
				+ mPayPlanCode);
		if (mPayPlanCode == null || mPayPlanCode.equals("null")
				|| mPayPlanCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输缴费编码失败!");
			return false;
		}
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		logger.debug("::::::::::::::mPayPlanCode::::::::::::"
				+ mEdorNo);
		if (mEdorNo == null || mEdorNo.equals("null")
				|| mEdorNo.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输EdorNo失败!");
			return false;
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		logger.debug("::::::::::::::mPayPlanCode::::::::::::"
				+ mEdorType);
		if (mEdorType == null || mEdorType.equals("null")
				|| mEdorType.equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输EdorType失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setPolNo(mPolNo);
		tLPPremDB.setEdorNo(mEdorNo);
		tLPPremDB.setEdorType(mEdorType);
		tLPPremDB.setDutyCode(mDutyCode);
		tLPPremDB.setPayPlanCode(mPayPlanCode);
		mLPPremSet = tLPPremDB.query();

		if (mLPPremSet.size() == 0) {
			CError.buildErr(this,"无法找到该笔数据!");
			return false;
		}
		else if(mLPPremSet.size()!=1)
		{
			CError.buildErr(this,"查询加费记录有误!");
			return false;
		}
		mMMap.put(mLPPremSet, "DELETE");

		// 得到lpprem 中的prem值，减去lppol中的加费
		String tSql = "select prem " + " from lpprem " + "  where polno = '"
				+ "?polno?" + "' " + "  and dutycode = '" + "?dutycode?" + "' "
				+ " and payplancode = '" + "?payplancode?" + "' " 
				+" and edorno='"+"?edorno?"+"' and edortype='"+"?edortype?"+"'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("polno", mPolNo);
		sqlbv.put("dutycode", mDutyCode);
		sqlbv.put("payplancode", mPayPlanCode);
		sqlbv.put("edorno", mEdorNo);
		sqlbv.put("edortype", mEdorType);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		// tSSRS.GetText(1,1)
		double tPremMoney = Double.parseDouble(tSSRS.GetText(1, 1));
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setPolNo(mPolNo);
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		mLPPolSet = tLPPolDB.query();

		if (mLPPolSet.size() == 0) {
			CError.buildErr(this,"查询保单数据失败!");
			return false;
		}
		for (int i = 1; i <= mLPPolSet.size(); i++) {
			double tLPPolPrem = mLPPolSet.get(i).getPrem() - tPremMoney;
			mLPPolSet.get(i).setPrem(tLPPolPrem);
		}
		mMMap.put(mLPPolSet, "UPDATE");
		
		//修改pduty prem
		LPDutyDB tLPDutyDB = new LPDutyDB();
		tLPDutyDB.setPolNo(mPolNo);
		tLPDutyDB.setEdorNo(mEdorNo);
		tLPDutyDB.setEdorType(mEdorType);
		LPDutySet tLPDutySet = tLPDutyDB.query();

		if (tLPDutySet.size() == 0) {
			CError.buildErr(this,"查询lpduty数据失败!");
			return false;
		}
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			double tLPPrem = tLPDutySet.get(i).getPrem() - tPremMoney;
			tLPDutySet.get(i).setPrem(tLPPrem);
		}
		mMMap.put(tLPDutySet, "UPDATE");
		
		//准备核保主表数据
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setPolNo(mLPPolSet.get(1).getPolNo());
		tLPUWMasterDB.setEdorNo(mEdorNo);
		tLPUWMasterDB.setEdorType(mEdorType);
		mLPUWMasterSet = tLPUWMasterDB.query();

//		for (int i = 1; i <= mLCUWMasterSet.size(); i++) {
//			mLCUWMasterSet.get(i).setAddPremReason("");
//		}
//		mMMap.put(mLCUWMasterSet, "UPDATE");
		if(mLPUWMasterSet==null||mLPUWMasterSet.size()<1)
		{
			CError.buildErr(this,"查询核保主表失败!");
			return false;
		}
		LPUWMasterSchema tLPUWMasterSchema= new LPUWMasterSchema();
		tLPUWMasterSchema = mLPUWMasterSet.get(1);
		
		LPPremDB uLPPremDB = new LPPremDB();
		tSql = "select * from lpprem where EdorNo ='"+"?EdorNo?"+"' and EdorType='"+"?EdorType?"+"' and contno='"+"?contno?"+"' and payplancode like '000000%%' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("EdorNo", mEdorNo);
		sqlbv1.put("EdorType", mEdorType);
		sqlbv1.put("ProposalNo", tLPUWMasterSchema.getProposalNo());
		sqlbv1.put("contno", tLPUWMasterSchema.getContNo());
		LPPremSet tLPPremSet = uLPPremDB.executeQuery(sqlbv1);
		if(tLPPremSet.size()>1)
			tLPUWMasterSchema.setAddPremFlag("1");
		else
			tLPUWMasterSchema.setAddPremFlag("0");
		tLPUWMasterSchema.setAddPremReason("");
		tLPUWMasterSchema.setPassFlag("y");//区别下险种结论 进行加费标志
		tLPUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLPUWMasterSchema, "UPDATE");
		
		//准备核保子表数据
		LPUWSubDB tLPUWSubDB = new LPUWSubDB();
		tSql = "select * from lpuwsub where PolNo='"+"?PolNo?"
				+"' and edorno='"+"?edorno?"
				+"' and edortype='"+"?edortype?"
				+"' order by uwno desc";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("PolNo", tLPUWMasterSchema.getPolNo());
		sqlbv2.put("edorno", tLPUWMasterSchema.getEdorNo());
		sqlbv2.put("edortype", tLPUWMasterSchema.getEdorType());
		logger.debug("tSql:"+tSql);
		LPUWSubSet tLPUWSubSet = tLPUWSubDB.executeQuery(sqlbv2);
		if(tLPUWSubSet==null||tLPUWSubSet.size()==0)
		{
			CError.buildErr(this,"查询险种核保子表失败!");
			return false;
		}
		LPUWSubSchema tLPUWSubSchema= new LPUWSubSchema();	
		tLPUWSubSchema = tLPUWSubSet.get(1);
		tLPUWSubSchema.setAddPremFlag(tLPUWMasterSchema.getAddPremFlag());
		tLPUWSubSchema.setAddPremReason(tLPUWMasterSchema.getAddPremReason());
		tLPUWSubSchema.setPassFlag(tLPUWMasterSchema.getPassFlag());
		tLPUWSubSchema.setUWNo(tLPUWSubSet.get(1).getUWNo()+1);
		tLPUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLPUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLPUWSubSchema, "INSERT");
		
		String tSQL_LJSGetEndorsePrem = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
		                   + " and FeeOperationType ='"+"?mEdorType?"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
                           + " and dutycode='" + "?dutycode?" + "' "
           				   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype = '"+"?subfeeoperationtype?"+"' and FeeFinaType = 'BF'" 
		                   ; 
		logger.debug("tSQL_LJSGetEndorsePrem:"+tSQL_LJSGetEndorsePrem);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables(); 
		sqlbv3.sql(tSQL_LJSGetEndorsePrem);
		sqlbv3.put("mEdorNo", mEdorNo);
		sqlbv3.put("mEdorType", mEdorType);
		sqlbv3.put("polno", mPolNo);
		sqlbv3.put("dutycode", mDutyCode);
		sqlbv3.put("payplancode", mPayPlanCode);
		sqlbv3.put("subfeeoperationtype", BqCode.Pay_addPrem);
		mMMap.put(sqlbv3, "DELETE");
		
		String tSQL_LJSGetEndorsePremZK = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
				        + " and FeeOperationType ='"+"?mEdorType?"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
				        + " and dutycode='" + "?dutycode?" + "' "
						   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype in (select code from ldcode where codetype='discounttype') and FeeFinaType = 'ZK'" 
				        ; 
		logger.debug("tSQL_LJSGetEndorsePremZK:"+tSQL_LJSGetEndorsePremZK);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables(); 
		sqlbv4.sql(tSQL_LJSGetEndorsePremZK);
		sqlbv4.put("mEdorNo", mEdorNo);
		sqlbv4.put("mEdorType", mEdorType);
		sqlbv4.put("polno", mPolNo);
		sqlbv4.put("dutycode", mDutyCode);
		sqlbv4.put("payplancode", mPayPlanCode);
		mMMap.put(sqlbv4, "DELETE");
		
		String tSQL_LJSGetEndorseLX = "delete from LJSGetEndorse where GetNoticeNo = '"+"?mEdorNo?"+"' and EndorsementNo = '"+"?mEdorNo?"+"' "
					        + " and FeeOperationType ='"+"?mEdorType?"+"' and polno = '"+"?polno?"+"' and otherno= '"+"?mEdorNo?"+"' "
					        + " and dutycode='" + "?dutycode?" + "' "
							   + " and payplancode = '" + "?payplancode?" + "' and subfeeoperationtype = '"+"?subfeeoperationtype?"+"' and FeeFinaType = 'LX'" 
					        ; 
		logger.debug("tSQL_LJSGetEndorseLX:"+tSQL_LJSGetEndorseLX);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables(); 
		sqlbv5.sql(tSQL_LJSGetEndorseLX);
		sqlbv5.put("mEdorNo", mEdorNo);
		sqlbv5.put("mEdorType", mEdorType);
		sqlbv5.put("polno", mPolNo);
		sqlbv5.put("dutycode", mDutyCode);
		sqlbv5.put("payplancode", mPayPlanCode);
		sqlbv5.put("subfeeoperationtype", BqCode.Pay_addPremInterest);
		mMMap.put(sqlbv5, "DELETE");

		
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
