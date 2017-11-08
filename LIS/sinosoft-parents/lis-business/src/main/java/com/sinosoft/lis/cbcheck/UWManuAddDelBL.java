package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 删除加费数据
 * </p>
 * <p>
 * Description: 删除加费承保的数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author guanwei
 * @version 1.0
 */

public class UWManuAddDelBL {
private static Logger logger = Logger.getLogger(UWManuAddDelBL.class);

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
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	private LCPremSet mLCPremSet = new LCPremSet();
	private MMap mMMap = new MMap();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();

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

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(mPolNo);
		tLCPremDB.setDutyCode(mDutyCode);
		tLCPremDB.setPayPlanCode(mPayPlanCode);
		mLCPremSet = tLCPremDB.query();

		if (mLCPremSet.size() == 0) {
			CError.buildErr(this,"无法找到该笔数据!");
			return false;
		}
		else if(mLCPremSet.size()!=1)
		{
			CError.buildErr(this,"查询加费记录有误!");
			return false;
		}
		mMMap.put(mLCPremSet, "DELETE");

		// 得到lcprem 中的prem值，减去lopol中的加费
		String tSql = "select prem " + " from lcprem " + "  where polno = '"
				+ "?polno?" + "' " + "  and dutycode = '" + "?dutycode?" + "' "
				+ " and payplancode = '" + "?payplancode?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("polno", mPolNo);
		sqlbv.put("dutycode", mDutyCode);
		sqlbv.put("payplancode", mPayPlanCode);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		// tSSRS.GetText(1,1)
		double tPremMoney = Double.parseDouble(tSSRS.GetText(1, 1));
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);

		mLCPolSet = tLCPolDB.query();

		if (mLCPolSet.size() == 0) {
			CError.buildErr(this,"查询保单数据失败!");
			return false;
		}
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			double tLCPolPrem = mLCPolSet.get(i).getPrem() - tPremMoney;
			mLCPolSet.get(i).setPrem(tLCPolPrem);
		}
		mMMap.put(mLCPolSet, "UPDATE");
		
		//准备核保主表数据
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mLCPolSet.get(1).getProposalNo());
		mLCUWMasterSet = tLCUWMasterDB.query();

//		for (int i = 1; i <= mLCUWMasterSet.size(); i++) {
//			mLCUWMasterSet.get(i).setAddPremReason("");
//		}
//		mMMap.put(mLCUWMasterSet, "UPDATE");
		if(mLCUWMasterSet==null||mLCUWMasterSet.size()<1)
		{
			CError.buildErr(this,"查询核保主表失败!");
			return false;
		}
		LCUWMasterSchema tLCUWMasterSchema= new LCUWMasterSchema();
		tLCUWMasterSchema = mLCUWMasterSet.get(1);
		
		LCPremDB uLCPremDB = new LCPremDB();
		tSql = "select * from lcprem where contno='"+"?ProposalNo?"+"' and payplancode like '000000%%' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("ProposalNo", tLCUWMasterSchema.getProposalNo());
		LCPremSet tLCPremSet = uLCPremDB.executeQuery(sqlbv1);
		/*if(tLCPremSet.size()>1)
			tLCUWMasterSchema.setAddPremFlag("1");
		else
			tLCUWMasterSchema.setAddPremFlag("0");*/
		tLCUWMasterSchema.setAddPremFlag("1");//进行过加费操作
		
		tLCUWMasterSchema.setAddPremReason("");
		tLCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLCUWMasterSchema, "UPDATE");
		
		//准备核保子表数据
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		tSql = "select * from lcuwsub where ProposalNo='"+"?ProposalNo?"+"' order by uwno desc";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("ProposalNo", tLCUWMasterSchema.getProposalNo());
		LCUWSubSet tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv2);
		if(tLCUWSubSet==null)
		{
			CError.buildErr(this,"查询险种核保子表失败!");
			return false;
		}
		LCUWSubSchema tLCUWSubSchema= new LCUWSubSchema();	
		tLCUWSubSchema = tLCUWSubSet.get(1);
		tLCUWSubSchema.setAddPremFlag(tLCUWMasterSchema.getAddPremFlag());
		tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema.getAddPremReason());
		tLCUWSubSchema.setUWNo(tLCUWSubSet.get(1).getUWNo()+1);
		tLCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		
		mMMap.put(tLCUWSubSchema, "INSERT");

		String tSQL_Duty = "update lcduty a set prem = (select (case when sum(prem) is null then 0 else sum(prem) end) from lcprem where polno=a.polno) "
            			 + " where polno='"+"?polno?"+"' "; 
		logger.debug("tSQL_Duty:"+tSQL_Duty);
		String tSQL_LCPol = "update lcpol a set prem = (select (case when sum(prem) is null then 0 else sum(prem) end) from lcduty where polno=a.polno) "
						 // + " ,sumprem = (select nvl(sum(prem),0) from lcprem where polno=a.polno) "
                          + " where polno = '"+"?polno?"+"' ";
		logger.debug("tSQL_LCPol:"+tSQL_LCPol);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL_Duty);
		sqlbv3.put("polno", mPolNo);
		mMMap.put(sqlbv3, "UPDATE");
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL_LCPol);
		sqlbv4.put("polno", mPolNo);
		mMMap.put(sqlbv4, "UPDATE");
		String tSQL_Cont = "update lccont a set prem=(select (case when sum(prem) is null then 0 else sum(prem) end) from lcpol where contno=a.contno) "
            //+ " ,sumprem = (select nvl(sum(prem),0) from lcpol where contno=a.contno) " 
			 + " where contno=(select contno from lcpol where polno='"+"?polno?"+"' ) ";
		logger.debug("tSQL_Cont:"+tSQL_Cont);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL_Cont);
		sqlbv5.put("polno", mPolNo);
		mMMap.put(sqlbv5, "UPDATE");
		
		//应收
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("delete from ljspay where OtherNo='" + "?contno?" + "' and OtherNotype='2' "
				+" and not exists(select 1 from ljspayperson where contno='" + "?contno?" + "' and (polno<>'" + "?polno?" + "' or dutycode<>'" + "?dutycode?" + "' or payplancode<>'"+"?payplancode?"+"' ) and currency=ljspay.currency)");
		sqlbv6.put("contno", mLCPolSet.get(1).getContNo());
		sqlbv6.put("polno", mLCPolSet.get(1).getContNo());
		sqlbv6.put("dutycode", mDutyCode);
		sqlbv6.put("payplancode", mPayPlanCode);
		mMMap.put(sqlbv6, "DELETE");		
		String sql = "select currency,sum(sumduepaymoney) from ljspayperson where contno='" + "?contno?" + "' and (polno<>'" + "?polno?" + "' or dutycode<>'" + "?dutycode?" + "' or payplancode<>'"+"?payplancode?"+"' ) group by currency";//修改应收总表
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("contno", mLCPolSet.get(1).getContNo());
		sqlbv7.put("polno", mLCPolSet.get(1).getContNo());
		sqlbv7.put("dutycode", mDutyCode);
		sqlbv7.put("payplancode", mPayPlanCode);
		ExeSQL yExeSQL = new ExeSQL();
		SSRS ySSRS = yExeSQL.execSQL(sqlbv7);
		if(ySSRS!=null && ySSRS.getMaxRow()>0)
		{
			for(int i=1;i<=ySSRS.getMaxRow();i++)
			{
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql("update ljspay set sumduepaymoney='"+"?sumduepaymoney?"+"' where OtherNo='" + "?OtherNo?" + "' and OtherNotype='2' and currency='"+"?currency?"+"'");
				sqlbv8.put("sumduepaymoney", ySSRS.GetText(i, 2));
				sqlbv8.put("OtherNo", mLCPolSet.get(1).getContNo());
				sqlbv8.put("currency", ySSRS.GetText(i, 1));
				mMMap.put(sqlbv8, "UPDATE");
			}			
		}
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("delete from ljspayperson where PolNo='" + "?PolNo?" + "' and dutycode = '" + "?dutycode?" + "' and payplancode='"+"?payplancode?"+"'");
		sqlbv9.put("PolNo", mPolNo);
		sqlbv9.put("dutycode", mDutyCode);
		sqlbv9.put("payplancode", mPayPlanCode);
		mMMap.put(sqlbv9, "DELETE");

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
