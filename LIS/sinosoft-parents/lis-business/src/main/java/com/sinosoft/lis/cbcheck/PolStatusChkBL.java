package com.sinosoft.lis.cbcheck;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人单状态查询部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class PolStatusChkBL {
private static Logger logger = Logger.getLogger(PolStatusChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private String mCalCode; // 计算编码
	private float mValue;

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 计算公式表* */

	private LMUWSet mLMUWSet = new LMUWSet();
	private LMCalModeSet mmLMCalModeSet = new LMCalModeSet();
	private LMCalModeSet mLMCalModeSet = new LMCalModeSet();
	private CalBase mCalBase = new CalBase();

	public PolStatusChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---PolStatusChkBL getInputData---");

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = mLCContSet.get(1);
		mLCContSchema = mLCContSet.get(1);

		// 校验数据

		// 校验主附险

		logger.debug("---PolStatusChkBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCContSchema)) {
			return false;
		}
		logger.debug("准备返回的数据");
		logger.debug("---PolStatusChkBL dealData---");
		// 准备返回的数据
		logger.debug("准备返回的数据");
		prepareOutputData();
		logger.debug("完成相应的功能，传出值 ");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
		// 准备算法
		if (CheckKinds(tLCContSchema) == false) {
			return false;
		}

		// 准备数据
		CheckPolInit(tLCContSchema); // 冗余代码

		// 取保单信息
		int n = mmLMCalModeSet.size();
		logger.debug("mmLMCalModeSet.size====" + mmLMCalModeSet.size());
		if (n == 0) {
		} else {
			int j = 0;
			mLMCalModeSet.clear();
			
			Calculator mCalculator = new Calculator();
			//mCalculator.setCalCode(mCalCode);
			// 增加基本要素
			mCalculator.addBasicFactor("Get", mCalBase.getGet());
			mCalculator.addBasicFactor("Mult", mCalBase.getMult());
			mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
			mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
			mCalculator.addBasicFactor("Sex", mCalBase.getSex());
			mCalculator.addBasicFactor("Job", mCalBase.getJob());
			mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
			mCalculator.addBasicFactor("GetStartDate", "");
			mCalculator.addBasicFactor("Years", mCalBase.getYears());
			mCalculator.addBasicFactor("Grp", "");
			mCalculator.addBasicFactor("GetFlag", "");
			mCalculator.addBasicFactor("ValiDate", "");
			mCalculator.addBasicFactor("Count", mCalBase.getCount());
			mCalculator.addBasicFactor("FirstPayDate", "");
			mCalculator.addBasicFactor("ContNo", mCalBase.getPolNo());
			
			
			List tCalList = new ArrayList();
			
			for (int i = 1; i <= n; i++) {
				// 取计算编码
				LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
				tLMCalModeSchema = mmLMCalModeSet.get(i);
				mCalCode = tLMCalModeSchema.getCalCode();
				
				//调整成batch
				tCalList.add(mCalCode);
				
				
//				if (CheckPol() == 0) {
//					logger.debug("第" + i + "次CheckPol== 0");
//				} else {
//					j++;
//					mLMCalModeSet.add(tLMCalModeSchema);
//				}
			}
			mCalculator.setBatchCalCodeList(tCalList);
			//List tResult = mCalculator.batchComputing();
			List tResult = mCalculator.batchComputingSeq();
			if(tResult!=null)
			{
				for(int i=0;i<tResult.size();i++)
				{
					String[] t = (String[])tResult.get(i);
					logger.debug("calcode:"+t[0]+":Result:"+t[1]);
//					SQLTaskResult tt = (SQLTaskResult)((ArrayList)mCalculator.getCalResult().get(t[0])).get(0);
//					logger.debug(tt.getMessageByLanguage("zh")+":"+tt.getMessageByLanguage("en"));
					
					if(t[1]==null||t[1].equals("")||t[1].equals("0"))
					{
						logger.debug(t[0] + "算法 CheckPol== 0");
					}
					else
					{
						logger.debug(t[0] + "算法 CheckPol!= 0");
						for(int m=1;m<=mmLMCalModeSet.size();m++)
						{
							LMCalModeSchema tLMCalModeSchema = mmLMCalModeSet.get(m);
							if(tLMCalModeSchema.getCalCode().equals(t[0]))
							{
								mLMCalModeSet.add(tLMCalModeSchema);
								break;
							}
						}
					}
				}
			}
		}

		if (dealOnePol() == false) { // 冗余代码
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;

		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));
		int n = mLCContSet.size();
		logger.debug("dddddddddd=" + n);
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		return true;
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */

	private boolean CheckKinds(LCContSchema tLCContSchema) {
		String tsql = "";
		mmLMCalModeSet.clear();
		// 查询算法编码
		tsql = "select * from LMCalMode where riskcode = 'Status' order by calcode";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		LMCalModeDB tLMCalModeDB = new LMCalModeDB();
		// tLMUWDB.setSchema(tLMUWSchema);

		// LMUWDBSet tLMUWDBSet = new LMUWDBSet();
		mmLMCalModeSet = tLMCalModeDB.executeQuery(sqlbv);
		if (tLMCalModeDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMCalModeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds";

			this.mErrors.addOneError(tError);
			mLMUWSet.clear();
			return false;
		}
		return true;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCContSchema tLCContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCContSchema.getPrem());
		mCalBase.setGet(tLCContSchema.getAmnt());
		mCalBase.setMult(tLCContSchema.getMult());

		mCalBase.setPolNo(tLCContSchema.getContNo());
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private float CheckPol() {
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		logger.debug("CalCode=:" + mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("ContNo", mCalBase.getPolNo());
		logger.debug("mCalBase.getPolNo()" + mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuredNo", mLCContSchema.getInsuredNo());
		logger.debug("mLCContSchema.getInsuredNo()"
				+ mLCContSchema.getInsuredNo());

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Float.parseFloat(tStr);
		}

		logger.debug(mValue);
		return mValue;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {

		mResult.clear();
		// 定义二维数组
		String[][] tStrResult = new String[mLMCalModeSet.size()][2];
		if (mLMCalModeSet.size() == 0) {
			buildError("outputXML", "该保单没有任何状态信息，原因可能是：该保单已经被拒保、延期或者已经撤保！");
			return;
		}
		// 循环给操作人赋值
		for (int i = 0; i < mLMCalModeSet.size(); i++) {
			// 得到状态
			tStrResult[i][0] = mLMCalModeSet.get(i + 1).getRemark();
			if (Integer.parseInt(mLMCalModeSet.get(i + 1).getCalCode()
					.substring(1)) >= 74
//					||Integer.parseInt(mLMCalModeSet.get(i + 1).getCalCode()
//							.substring(1)) <= 59
							) {
				tStrResult[i][1] = "";
			} else
			// 查询出操作人
			{
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				String tSql = " select calsql from lmcalmode "
						+ " where riskcode ='Status'" + " and remark = '"
						+ "?remark?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql);
				sqlbv1.put("remark", mLMCalModeSet.get(i + 1).getRemark());
				tSSRS = tExeSQL.execSQL(sqlbv1);
				//BEGIN
				String sql = tSSRS.GetText(1, 1);
				String tFunctionId = sql.substring(sql.indexOf("functionid =")+14,sql.lastIndexOf("')"));
				//END
				tSql = " select missionid " + " from lwmission "
						+ " where missionprop1 = '"
						+ "?missionprop1?" + "' "
						+ " union select missionid " + " from lbmission "
						+ " where missionprop1 = '"
						+ "?missionprop1?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(sql);
				sqlbv2.put("missionprop1", mLCContSet.get(1).getContNo());
				tSSRS = tExeSQL.execSQL(sqlbv2);
				if (tSSRS.getMaxRow() <= 0) {
					tStrResult[i][1] = "";
				} else {
					String tMissID = tSSRS.GetText(1, 1);
					if (tFunctionId.equals("10010021")) {
						tSql = " select a from ("
								+ " select (case (case when defaultoperator is null then missionprop14 else defaultoperator end) when '0000000000' then '公共池' else (case when defaultoperator is null then missionprop14 else defaultoperator end) end) a,'9' b "
								+ " from lwmission " + " where missionid = '"
								+ "?missionid?" + "' " + " and activityid in (select activityid from lwactivity where functionid = '"
								+ "?functionid?" + "') "
								+ " union select defaultoperator a,'0' b "
								+ " from lbmission " + " where missionid ='"
								+ "?missionid?" + "'" + " and activityid in (select activityid from lwactivity where functionid ='"
								+ "?functionid?" + "') " + " ) g order by b desc ";
					} else {
						tSql = " select defaultoperator   from lwmission "
								+ " where missionid = '" + "?missionid?" + "' "
								+ " and activityid in (select activityid from lwactivity where functionid =  '" + "?functionid?" + "') "
								+ " union select defaultoperator "
								+ " from lbmission " + " where missionid ='"
								+ "?missionid?" + "'" + " and activityid  in( select activityid from lwactivity where functionid = '"
								+ "?functionid?" + "') ";
					}
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tSql);
					sqlbv3.put("missionid", tMissID);
					sqlbv3.put("functionid", tFunctionId);
					tSSRS = tExeSQL.execSQL(sqlbv3);
					tStrResult[i][1] = tSSRS.GetText(1, 1);
				}
			}
			// tStrResult.length
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Str", tStrResult);
			mResult.add(tTransferData);
		}

		logger.debug("+======BL======+" + mResult.size());
		// mResult.add(tStrResult);
		// String [][] Strrtr = (String [][])mResult.get(1);
		int tStringSize = mLMCalModeSet.size();
		logger.debug("完成编译!");

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}
}
