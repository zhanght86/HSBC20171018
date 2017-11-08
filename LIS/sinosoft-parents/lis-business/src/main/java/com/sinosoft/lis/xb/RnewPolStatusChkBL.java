package com.sinosoft.lis.xb;
import org.apache.log4j.Logger;

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
public class RnewPolStatusChkBL {
private static Logger logger = Logger.getLogger(RnewPolStatusChkBL.class);
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
	private TransferData mTransferData =new TransferData();
	private String mRiskCode ="";
	private String mOperator=""; //最后操作员
	private String[][] temp_result ; //定义暂时的结果集
	private int j ;

	/** 计算公式表* */

	private LMUWSet mLMUWSet = new LMUWSet();
	private LMCalModeSet mmLMCalModeSet = new LMCalModeSet();
	private LMCalModeSet mLMCalModeSet = new LMCalModeSet();
	private CalBase mCalBase = new CalBase();

	public RnewPolStatusChkBL() {
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
		logger.debug("状态查询次数:"+n);
		//定义一个传出结果集
		temp_result = new String[n][2];
		logger.debug("mmLMCalModeSet.size====" + mmLMCalModeSet.size());
		if (n == 0) 
		{
		} 
		else 
		{
			mLMCalModeSet.clear();
		    j=0;
			for (int i = 1; i <= n; i++) 
			{
				// 取计算编码
				LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
				tLMCalModeSchema = mmLMCalModeSet.get(i);

				String[] xResult = new String[2];
				xResult=CheckPol(tLMCalModeSchema);
				if(xResult[0].equals("no"))
				{
					logger.debug("第" + i + "次查询无结果");
				} 
				else
				{
					temp_result[j]=xResult;
					j++;
				}
			}
			if (j == 0) 
			{
				buildError("outputXML", "该保单没有任何状态信息，原因可能是：该保单已经被拒保、延期或者已经撤保！");
				return false;
			}
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
		this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		mRiskCode =(String)mTransferData.getValueByName("RiskCode");
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
		tsql = "select * from LMCalMode where riskcode = 'RStatus' order by calcode";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
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
		mCalBase.setRiskCode(mRiskCode);
	}

	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private String[] CheckPol(LMCalModeSchema xLMCalModeSchema) {
		// 计算
		mCalCode=xLMCalModeSchema.getCalCode();
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
		mCalculator.addBasicFactor("RiskCode", mCalBase.getRiskCode());
		logger.debug("mCalBase.getPolNo()" + mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuredNo", mLCContSchema.getInsuredNo());
		logger.debug("mLCContSchema.getInsuredNo()"
				+ mLCContSchema.getInsuredNo());

		String tStr = "";
		tStr = mCalculator.calculate();
		String[] single_result = new String[2];
		if (tStr.trim().equals("")) 
		{
			single_result[0]="no";
		} 
		else 
		{
			single_result[0]=xLMCalModeSchema.getRemark();
		    single_result[1]=tStr.trim(); //从查询结果中找出最后操作员
		}
		return single_result;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {

		mResult.clear();
		String[][] tStrResult = new String[j][2];
		for(int k=0;k<j;k++)
		{
			tStrResult[k]=this.temp_result[k];
		}
	    TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Str", tStrResult);
		mResult.add(tTransferData);

		logger.debug("+======BL======+" + mResult.size());
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
