
package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import com.sinosoft.lis.db.LMRiskBonusDB;
import com.sinosoft.lis.db.LOBonusMainDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskBonusSchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: 红利提前(强制分红)计算处理</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author pst
 * @version 1.0
 * */

public class BonusPreCountBL {
private static Logger logger = Logger.getLogger(BonusPreCountBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 错误处理类 */
	private CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/**根据主调函数传入的动作，作相应处理 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**分红年度*/
	private String mFiscalYear;

	/**保单号*/
	private String mContNo;

	/**红利分配组号*/
	private String mGroupID;

	private List mBomList = new ArrayList();
	
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LOBonusPolSchema mLOBonusPolSchema = new LOBonusPolSchema();

	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();

	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	private Reflections tReflections = new Reflections();

	/*转换精确位数的对象   */
	private String FORMATMODOL = "0.00";//保费保额计算出来后的精确位数

	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);//数字转换对象
	
	private String mFiscalYearFlag="";  //待计算会计年度的红利分配方案是否出台

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 * @return
	 */
	public boolean submitData(VData cInputData, String tOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = tOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 数据校验，包括业务校验
		if (!checkData()) {
			return false;
		}

		// 根据业务逻辑对数据进行处理
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		mLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLOBonusPolSchema = (LOBonusPolSchema) mInputData
				.getObjectByObjectName("LOBonusPolSchema", 0);

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		TransferData mTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		mGroupID = (String) mTransferData.getValueByName("GroupID");
		if (mGroupID == null || mGroupID.equals("")) {
			buildError("getInputData", "获得分红组失败!");
			return false;
		}
		mFiscalYear = (String) mTransferData.getValueByName("FiscalYear");
		if (mFiscalYear == null || mFiscalYear.equals("")) {
			buildError("getInputData", "获得分红年度失败!");
			return false;
		}
		mFiscalYearFlag =(String)  mTransferData.getValueByName("FiscalYearFlag");
		if (mFiscalYearFlag == null || mFiscalYearFlag.equals("")) {
			buildError("getInputData", "获取待计算会计年度的红利分配方案是否出台标志失败!");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性目前只有主险
	 * 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @return
	 */
	private boolean dealData() {

		//红利系数计算
		if ("BONUS".equals(mOperate)) {
			LMRiskBonusDB tLMRiskBonusDB = new LMRiskBonusDB();

			tLMRiskBonusDB.setRiskCode(mLCPolSchema.getRiskCode());
			if (!tLMRiskBonusDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskBonusDB.mErrors);
				return false;
			}
			LMRiskBonusSchema tLMRiskBonusSchema =tLMRiskBonusDB.getSchema();
			//计算保单年度值--需要校验:即不满一年的需要加 1 --润年问题
			int PolYear = Integer.parseInt(mFiscalYear)
					- Integer.parseInt(mLCPolSchema.getCValiDate().substring(0,
							4)) + 1;
			//求出正常分红的保额，即剔除增额交清部分的保额,保费
			String strSQL = "select (case when SUM(Amnt) is not null then SUM(Amnt) else 0 end),(case when SUM(StandPrem) is not null then SUM(StandPrem) else 0 end) from lcduty where polno='?polno?' and char_length(trim(dutycode))=6 ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("polno", mLCPolSchema.getPolNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			String actuAmnt = tSSRS.GetText(1, 1);
			String actuPrem = tSSRS.GetText(1, 2);

			CalBase mCalBase = new CalBase();
			mCalBase.setAmnt(Double.parseDouble(actuAmnt));
			mCalBase.setPrem(Double.parseDouble(actuPrem));
			if(this.mFiscalYearFlag.equals("N"))
			{
				mCalBase.setBonusYear(Integer.parseInt(mFiscalYear)-1); //加入会计年度考虑
			}
			else
			{
				mCalBase.setBonusYear(Integer.parseInt(mFiscalYear));
			}
			mCalBase.setAppAge(mLCPolSchema.getInsuredAppAge());
			mCalBase.setSex(mLCPolSchema.getInsuredSex());
			mCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
			mCalBase.setPayEndYearFlag(mLCPolSchema.getPayEndYearFlag());
			mCalBase.setPayIntv(mLCPolSchema.getPayIntv());
			mCalBase.setGetYear(mLCPolSchema.getGetYear()); //起领日期
			mCalBase.setInsuYear(mLCPolSchema.getInsuYear());
			mCalBase.setInsuYearFlag(mLCPolSchema.getInsuYearFlag());

			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(tLMRiskBonusSchema.getBonusCoefCode());
			//增加基本要素
			mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
			mCalculator.addBasicFactor("Amnt", mCalBase.getAmnt());
            //会计年度,取前一年的
			LOBonusPolSchema hLOBonusPolSchema =new LOBonusPolSchema();
			hLOBonusPolSchema.setFiscalYear(mFiscalYear);
			if(this.mFiscalYearFlag.equals("N"))
			{
				mCalculator.addBasicFactor("BonusYear", String.valueOf(Integer.parseInt(mFiscalYear)-1));
				hLOBonusPolSchema.setFiscalYear(Integer.parseInt(mFiscalYear)-1);
			}
			else
			{
				mCalculator.addBasicFactor("BonusYear", String.valueOf(Integer.parseInt(mFiscalYear)));
				hLOBonusPolSchema.setFiscalYear(Integer.parseInt(mFiscalYear));
			}
			mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
			mCalculator.addBasicFactor("Sex", mCalBase.getSex());
			mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
			mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
					.getPayEndYearFlag());
			mCalculator.addBasicFactor("PolYear", String.valueOf(PolYear));//添加保单年度值
			mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
			mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear()); //起领日期
			mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear()); //保单年度
			mCalculator.addBasicFactor("InsuYearFlag", mCalBase
					.getInsuYearFlag());
			PrepareBOMBounsBL tPrepareBOMBounsBL=new PrepareBOMBounsBL();

			mBomList=tPrepareBOMBounsBL.dealData(mLCPolSchema, hLOBonusPolSchema);
			mCalculator.setBOMList(mBomList);
			String tStr = "";
			double mValue;
			tStr = mCalculator.calculate();
			if (tStr.trim().equals("")) {
				mValue = 0;
			} else {
				mValue = Double.parseDouble(tStr);
			}

			if (mValue > 0) {
				//格式化，取两位精度-校验
				String strCalPrem = mDecimalFormat.format(mValue);//转换计算后的保费(规定的精度)
				mValue = Double.parseDouble(strCalPrem);
				//计算保单年度值--:不满一年的会加 1 --润年问题（因为计算的保单范围是上一年之前的，所以不能用今天的日期，要用当年日期的边界值）
				int tPolYear = (Integer.parseInt(mFiscalYear))
						- Integer.parseInt(mLCPolSchema.getCValiDate()
								.substring(0, 4)) + 1;
				//计算实际红利应该分配日期--校验
				FDate tD = new FDate();
				Date baseDate = tD.getDate(mLCPolSchema.getCValiDate());
				Date SGetDate = PubFun.calDate(baseDate, tPolYear, "Y", null);
				LOBonusPolSchema tLOBonusPolSchema = new LOBonusPolSchema();
				tReflections.transFields(tLOBonusPolSchema, mLCPolSchema);
				tLOBonusPolSchema.setFiscalYear(mFiscalYear);
				tLOBonusPolSchema.setBonusCoef(mValue);
				tLOBonusPolSchema.setBonusFlag("0"); //红利领取标记
				tLOBonusPolSchema.setSGetDate(SGetDate);//红利应该分配日期
				tLOBonusPolSchema.setOperator(mGlobalInput.Operator);
				tLOBonusPolSchema.setMakeDate(tCurMakeDate);
				tLOBonusPolSchema.setMakeTime(tCurMakeTime);
				tLOBonusPolSchema.setModifyDate(tCurMakeDate);
				tLOBonusPolSchema.setModifyTime(tCurMakeTime);
				tLOBonusPolSchema.setGroupID(mGroupID);
				String  tFlag=getValiFlag(mLCPolSchema,tD.getString(SGetDate) );
				tLOBonusPolSchema.setValiFlag(tFlag);
				mMap.put(tLOBonusPolSchema, "DELETE&INSERT");
				mResult.clear();
				mResult.add(mMap);
			}
		}
		//红利金额计算
		if ("BONUSM".equals(mOperate)) {
			//1-根据组别查询红利分配主表LOBonusMain，得到红利结算时刻的红利系数和BonusCoefSum
			LOBonusMainDB tLOBonusMainDB = new LOBonusMainDB();
			tLOBonusMainDB.setGroupID(mGroupID);
			if(this.mFiscalYearFlag.equals("N"))
			{
				tLOBonusMainDB.setFiscalYear(String.valueOf(Integer.parseInt(mFiscalYear)-1));
			}
			else
			{
				tLOBonusMainDB.setFiscalYear(String.valueOf(Integer.parseInt(mFiscalYear)));
			}
			if (tLOBonusMainDB.getInfo() == false) {
				mErrors.copyAllErrors(tLOBonusMainDB.mErrors);
				CError tCError = new CError();
				tCError.functionName = "dealData";
				tCError.moduleName = "BonusPreCountBL()";
				tCError.errorMessage = "查询红利分配主表失败!";
				mErrors.addOneError(tCError);
				return false;
			}
			//10844175.450000
			double sumBonusQuotiety = tLOBonusMainDB.getBonusCoefSum();//该组的红利分配系数和
			//24547560.00
			double distributeValue = tLOBonusMainDB.getDistributeValue();//该组的可分配盈余
			//0.70
			double distributeRate = tLOBonusMainDB.getDistributeRate();//该组的红利分配比例

			double tBonusCoef = mLOBonusPolSchema.getBonusCoef();
			//红利金额计算公式
			double tBonusMoney = tBonusCoef / sumBonusQuotiety
					* distributeValue * distributeRate;
			//格式化，取两位精度-校验
			String strBonusMoney = mDecimalFormat.format(tBonusMoney);//转换计算后的保费(规定的精度)
			tBonusMoney = Double.parseDouble(strBonusMoney);

//			double tInterest = calInterest(mLOBonusPolSchema.getPolNo(),
//					tBonusMoney, mLOBonusPolSchema.getSGetDate());

			String strSQL = "update LOBonusPol set BonusMoney = ?tBonusMoney?,BonusInterest = 0,BonusMakeDate = '?tCurMakeDate?',ModifyDate = '?tCurMakeDate?',ModifyTime='?tCurMakeTime?' "
					+ "where PolNo = '?PolNo?' and FiscalYear = ?FiscalYear? and GroupID = ?mGroupID?";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("tBonusMoney", tBonusMoney);
			sqlbv1.put("tCurMakeDate", tCurMakeDate);
			sqlbv1.put("tCurMakeTime", tCurMakeTime);
			sqlbv1.put("PolNo", mLOBonusPolSchema.getPolNo());
			sqlbv1.put("FiscalYear", mLOBonusPolSchema.getFiscalYear());
			sqlbv1.put("mGroupID", mGroupID);
			mMap.put(sqlbv1, "UPDATE");
			mResult.clear();
			mResult.add(mMap);
		}

		return true;
	}

	/**
	 * 预计算指定会计年度分红产生的利息
	 * @param cPolNo ： 保单号
	 * @param cBonusMoney ： 红利金额
	 * @param cSGetDate ： 应领日期
	 * @return
	 */
	private double calInterest(String cPolNo, double cBonusMoney,
			String cSGetDate) {
		//如果没有到应分配日，则不计算利息
		if (PubFun.calInterval(cSGetDate, tCurMakeDate, "D") <= 0)
			return 0;

		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select bonusgetmode from lcpol " + "where polno = '?cPolNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("cPolNo", cPolNo);
		SSRS tssrs = tExeSQL.execSQL(sqlbv2);
		if (tExeSQL.mErrors.needDealError() == true || tssrs == null
				|| tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询保单'" + cPolNo + "'的信息失败！");
			return 0;
		}
        //只针对现金领取的保单随红利进行批量计算
		if (!tssrs.GetText(1, 1).equals("2"))
			return 0;

		//只有现金和累计生息进行利息的计算
		double tRate = 0.025;

		//校验结息点（正常情况下结息点是在上一个会计年度末－小于应领日期）
		sql = "select baladate from lcinsureacc " + "where polno = '?cPolNo?' and insuaccbala > 0";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("cPolNo", cPolNo);
		tssrs = tExeSQL.execSQL(sqlbv3);
		if (tExeSQL.mErrors.needDealError() == false && tssrs != null
				&& tssrs.getMaxRow() > 0) {
			for (int i = 1; i <= tssrs.getMaxRow(); i++) {
				String tBalaDate = tssrs.GetText(i, 1);
				if (PubFun.calInterval(cSGetDate, tBalaDate, "D") > 0) {
					cSGetDate = tBalaDate;
				}
			}
		}

		//计算利息
		AccountManage tAccountManage = new AccountManage();
		double tInterest = tAccountManage.getInterest(cBonusMoney, cSGetDate,
				tCurMakeDate, tRate, "Y", "C", "D");

		return tInterest;
	}

	/**获取保单在计算红利时点是否有效，失效的情况，保单有效 返回 "1", 失效返回 "0"*/
	private String getValiFlag(LCPolSchema tLCPolSchema,String tSGetDate)
	{
	 	ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select '0' from LCContState where PolNo='?PolNo?' and StateType='Available' and State='1' and ((STARTDATE <= '?tSGetDate?' and EndDate >= '?tSGetDate?') or (STARTDATE <= '?tSGetDate?' and EndDate is null))";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv4.put("tSGetDate", tSGetDate);
		String  tFlag= tExeSQL.getOneValue(sqlbv4);
		if (!"".equals(tFlag)) {
			return "0";			
		}else
		{
		  return "1";
		}
	}
	
	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "InsuAccBala";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
