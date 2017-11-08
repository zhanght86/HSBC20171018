package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.sinosoft.lis.bl.LJTempFeeBL;
import com.sinosoft.lis.bl.LJTempFeeClassBL;
import com.sinosoft.lis.bq.BqContHangUpBL;
import com.sinosoft.lis.certify.PubCertifyTakeBack;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJAGetOtherDB;
import com.sinosoft.lis.db.LJAGetTempFeeDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCustomerAccTraceSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerAccSet;
import com.sinosoft.lis.vschema.LCCustomerAccTraceSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CodeJudge;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author GHT
 * @version 1.0
 */

public class TempFeeBL
{
private static Logger logger = Logger.getLogger(TempFeeBL.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private String[] existTempFeeNo;
	private VData mInputData;
	private VData mLastFinData = new VData();
	private GlobalInput tGI = new GlobalInput();
	private VData FinFeeVData = new VData(); // 存放财务付费数据
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String serNo = ""; // 流水号
	private String tLimit = "";
	private String tNo = ""; // 生成的暂交费号
	private String mOperate;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mContNo;
	private String mQueryType;
	private String mCustomerNo;
	private String mAccType;
	private String mCustomerType;
	private String mPayMode = "";
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSet mLJTempFeeSetNew = new LJTempFeeSet();
	private LJSPaySet mLJSPaySetNew = new LJSPaySet(); // 续收eft操作需要更新应收表
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSet mLJTempFeeClassSetNew = new LJTempFeeClassSet();
	private LJTempFeeClassSet mLJTempFeeClassSetDel = new LJTempFeeClassSet();
	private LJFIGetSet mLJFIGetSet = new LJFIGetSet();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private String mLJSPayFlag = "0"; // 是否需要插应收表标志 0-不插 1-插
	private boolean mYCFlag; // 客户预存标记
	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
	private LJAGetTempFeeSchema mLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
	private String tFlag = "";
	private String FeeUpdate="0";
	private String SubmitFlag="";
	private String CERTIFY_XQTempFee = ""; //单证类型编码(为3209或531001)
    private String mCardNo = ""; //续期交费可能记录的暂交费收据号
    private PubConcurrencyLock mLock = new PubConcurrencyLock();

	// 业务处理相关变量
	public TempFeeBL()
	{
		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		// 1st record:
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("11111111111158");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setPayDate("2007-11-08");
		tLJTempFeeSchema.setPayMoney(11000);
		tLJTempFeeSchema.setOperator("001");
		// tLJTempFeeSchema.setEnterAccDate("2005-8-3");
		tLJTempFeeSchema.setRiskCode("00903000");
		tLJTempFeeSchema.setAgentCode("8611000506");
		tLJTempFeeSchema.setAgentGroup("000000000487");
		tLJTempFeeSchema.setManageCom("86110000");
		tLJTempFeeSchema.setPolicyCom("232323");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setOtherNo("20071017203200");
		tLJTempFeeSchema.setOtherNoType("7");
		// tLJTempFeeSchema.setPayEndYear("1000");
		// tLJTempFeeSchema.setPayIntv("0");
		tLJTempFeeSet.add(tLJTempFeeSchema);
		//
		tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo("11111111111158");
		tLJTempFeeClassSchema.setPayMode("1");
		tLJTempFeeClassSchema.setChequeNo("12345");
		tLJTempFeeClassSchema.setPayDate("2007-11-08");
		tLJTempFeeClassSchema.setPayMoney(11000);
		// tLJTempFeeClassSchema.setEnterAccDate("2007-8-29");
		tLJTempFeeClassSchema.setManageCom("86110000");
		tLJTempFeeClassSchema.setPolicyCom("232323");
		tLJTempFeeClassSchema.setBankAccNo("");
		tLJTempFeeClassSchema.setBankCode("1010");
		tLJTempFeeClassSchema.setAccName("");
		tLJTempFeeClassSchema.setOperator("001");
		tLJTempFeeClassSchema.setManageCom("86");
		tLJTempFeeClassSchema.setOtherNo("20071031094500");
		tLJTempFeeClassSchema.setOtherNoType("7");
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		// SchemaSet tSchemaSet = new SchemaSet();
		// tSchemaSet.add(tLJTempFeeClassSet);
		// Object tob = tSchemaSet.getClass();
		// tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tLJTempFeeClassSchema.setTempFeeNo("3100000011187");
		// tLJTempFeeClassSchema.setPayMode("3");
		// tLJTempFeeClassSchema.setChequeNo("2323232323");
		// tLJTempFeeClassSchema.setPayDate("2005-11-11");
		// tLJTempFeeClassSchema.setPayMoney(1000);
		// tLJTempFeeClassSchema.setEnterAccDate("2007-8-29");
		// tLJTempFeeClassSchema.setManageCom("86110000");
		// tLJTempFeeClassSchema.setBankAccNo("77777777777777");
		// tLJTempFeeClassSchema.setBankCode("01");
		// tLJTempFeeClassSchema.setAccName("月亮");
		// tLJTempFeeClassSchema.setOperator("001");
		// tLJTempFeeClassSchema.setManageCom("86");
		// tLJTempFeeClassSchema.setOtherNo("HB020227011001356");
		// tLJTempFeeClassSchema.setOtherNoType("2");
		// tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		// logger.debug(tob.toString());

		// tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tLJTempFeeClassSchema.setTempFeeNo("12222222222222222223");
		// tLJTempFeeClassSchema.setPayMode("1");
		// tLJTempFeeClassSchema.setPayDate("2005-08-22");
		// tLJTempFeeClassSchema.setPayMoney(200);
		// tLJTempFeeClassSchema.setOtherNo("230110000003552");
		// tLJTempFeeClassSchema.setOtherNoType("2");
		// tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		// tLJTempFeeClassSchema.setManageCom("86110000");
		// tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		/*
		 * //2nd record: tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222224");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222224");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(10);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema); //3rd record:
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111501");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(20);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		 */
		// 5th record:
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setTempFeeNo("12222222222222222226");
		// tLJTempFeeSchema.setTempFeeType("3");
		// tLJTempFeeSchema.setOtherNo("130000000000026");
		// tLJTempFeeSchema.setOtherNoType("1");
		// tLJTempFeeSchema.setPayDate("2003-3-5");
		// tLJTempFeeSchema.setPayMoney(10);
		// tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		// tLJTempFeeSchema.setRiskCode("111501");
		// / tLJTempFeeSchema.setAgentCode("00000228");
		// tLJTempFeeSchema.setRiskCode("000000");
		// tLJTempFeeSchema.setAgentGroup("010101");
		// tLJTempFeeSchema.setManageCom("86110000");
		// tLJTempFeeSchema.setOperator("001");
		// tLJTempFeeSet.add(tLJTempFeeSchema);
		// tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tLJTempFeeClassSchema.setTempFeeNo("12222222222222222226");
		// tLJTempFeeClassSchema.setPayMode("2");
		// tLJTempFeeClassSchema.setPayDate("2005-7-5");
		// tLJTempFeeClassSchema.setPayMoney(50);
		// tLJTempFeeClassSchema.setEnterAccDate("2005-7-5");
		// tLJTempFeeClassSchema.setManageCom("86110000");
		// tLJTempFeeClassSchema.setOperator("001");
		// tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		/*
		 * //6th record: tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222227");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222227");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(10);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema); //7th record:
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111501");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(20);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		 */

		// 单证回收
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CertifyFlag", "1");

		VData tVData = new VData();
		tVData.addElement(tLJTempFeeSet);
		tVData.addElement(tLJTempFeeClassSet);
		tVData.addElement(tTransferData);
		tVData.addElement(tGI);

		TempFeeBL tTempFeeBL = new TempFeeBL();

		if(!tTempFeeBL.submitData(tVData, "INSERT"))
		{
			logger.debug(tTempFeeBL.mErrors.getErrContent());
		}

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate)
	{
		
		try
	   {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate==" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if(!getInputData(cInputData))
		{
			return false;
		}
		logger.debug("After getinputdata");

		if(!checkData())
		{
			return false;
		}
		logger.debug("After checkData");

        if("0".equals(FeeUpdate))//如果不是暂收费修改功能则进行加锁
        {
			// 增加并发控制，zy
			String[] tOperatedNo = new String[2];
			tOperatedNo[0] = mLJTempFeeSet.get(1).getTempFeeNo();//由于界面只允许对一个收据号和一个合同号进行收费操作，所以获取第一条记录即可
			if("8".equals(mLJTempFeeSet.get(1).getTempFeeType()))
			tOperatedNo[1] = "O"+mLJTempFeeSet.get(1).getOtherNo();
			else
			tOperatedNo[1] = mLJTempFeeSet.get(1).getOtherNo();	

			//进行并发组的控制
			if(!mLock.lock(tOperatedNo, "LF0002", tGI.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
		
			}
        }
		// 进行业务处理
		if(!dealData())
		{
			return false;
		}
		logger.debug("After dealData！");
		
		

		/***********************************/
		// 添加客户账户处理
		VData nInputData = new VData();		
		nInputData.add(mLJTempFeeSetNew);
		nInputData.add(mLJTempFeeClassSetNew);
		/* 单次 EFT 操作 */
		if(mLJSPaySetNew.size() > 0)
		{
			nInputData.add(mLJSPaySetNew);		
		}		
		nInputData.add(tGI);
		FICustomerMain tFICustomerMain = new FICustomerMain();
		// 调用客户账户收费接口，传入财务标志FI
		if (tFICustomerMain.submitData(nInputData, "FI"))
		{
			// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
			map.add(tFICustomerMain.getMMap());
		}
		else
		{
			mErrors.copyAllErrors(tFICustomerMain.mErrors);
			return false;
		}
		
		/***********************************/
		
		
		
		
		// 准备往后台的数据
		if(!prepareOutputData())
		{
			return false;
		}
		logger.debug("After prepareOutputData");
		
		
		
		
		
		
		if(!"CaseBack".equals(SubmitFlag)){
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		}
		
        //单证回收 2008-12-23 暂时屏蔽掉
		//2009-04-20 放开单证回收
		// 2009-04-28 屏蔽单证回收 
        if (this.mOperate.equals("INSERT"))
        {
            PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
//            GlobalInput cGI = tGI;
            //zy 直接对GlobalInput对象赋值，则修改该对象的值同样会修改原对象的值
            GlobalInput cGI = new GlobalInput();
            cGI.Operator= tGI.Operator;       	
        	cGI.ManageCom=tGI.ManageCom;
        	cGI.AgentCom = tGI.AgentCom;
            //zy 2009-11-13 因为财务外包传递的管理机构是86，所以直接截取有问题,需要进行判断，注意程序的严谨性
            if(tGI.ComCode.trim().length()>=6)
            {
            	cGI.ComCode=tGI.ComCode.substring(0,6);
            }
            else
            {
            	cGI.ComCode=tGI.ComCode;
            }
            if (tPubCertifyTakeBack.CheckNewType(tPubCertifyTakeBack.CERTIFY_CheckNo1)) //如果需要单证回收
            {
                String operator = tGI.Operator;
                LJTempFeeSchema tLJTempFee = new LJTempFeeSchema();

                for (int i = 1; i <= mLJTempFeeSetNew.size(); i++)
                {
                    tLJTempFee = mLJTempFeeSetNew.get(i);

                    //回收定额单
                    if (tLJTempFee.getTempFeeType().equals("1") && "3".equals(tLJTempFee.getTempFeeNoType()))
                    {
                        logger.debug("开始回收定额单");

                        String CertifyCode = tLJTempFee.getState();

                        if (CertifyCode == null)
                        {
                            continue;
                        }
//                        if (!tPubCertifyTakeBack.CertifyTakeBack_A(tLJTempFee.getOtherNo(),
//                                                                       tLJTempFee.getOtherNo(),
//                                                                       CertifyCode,
//                                                                       tGI))
                        //zy 2009-11-09 调整单证自动缴销规则，只校验到三级机构
                        if (!tPubCertifyTakeBack.CertifyTakeBack_A(tLJTempFee.getOtherNo(),tLJTempFee.getOtherNo(),CertifyCode,cGI))
                        {
                            logger.debug("单证回收错误（定额单" + i + "）:" +
                                               tLJTempFee.getOtherNo());
                            logger.debug("错误原因：" +
                                               tPubCertifyTakeBack.mErrors.getFirstError()
                                                                          .toString());
                        }
                    }

                    //回收普通单证
                    else
                    {
                        if (tLJTempFee.getTempFeeType().equals("1") && "2".equals(tLJTempFee.getTempFeeNoType())) //类型为5的不用回收单证，跳出
                        {
                            continue;
                        }
                        else
                        {
                            String tCardNo = tLJTempFee.getTempFeeNo();
                            String tCardType = tPubCertifyTakeBack.CERTIFY_TempFee;
                            logger.debug("开始回收普通单证");
    
                            //对首期暂收费之一的邮保通单证的进行单独处理
                            logger.debug("TempFeeNo的3到6位是"+tLJTempFee.getTempFeeNo().substring(2,6));
                            if(tLJTempFee.getTempFeeType().equals("1")&&tLJTempFee.getTempFeeNo().substring(2,6).equals(tPubCertifyTakeBack.CERTIFY_YBTempFee))
                            {
                            	logger.debug("邮保通单证不在此处回收");
                            }
                            else
                            {
                                //续期交费时候增加对暂收费收据号码的自动处理
                                if(tLJTempFee.getTempFeeType().equals("2"))
                                {
                                    tCardNo = mCardNo;
                                    if(!("".equals(CERTIFY_XQTempFee)))
                                    {
                                    	tCardType = CERTIFY_XQTempFee;
                                    }
                                }
                                if(tLJTempFee.getTempFeeType().equals("3"))
                                {
                                    if(!("".equals(CERTIFY_XQTempFee)))
                                    {
                                    	tCardType = CERTIFY_XQTempFee;
                                    }
                                }
                                if(!("".equals(tCardNo)))
                                 {
//	                                if (!tPubCertifyTakeBack.CertifyTakeBack_A(tCardNo,tCardNo,tCardType,tGI))
                                	if (!tPubCertifyTakeBack.CertifyTakeBack_A(tCardNo,tCardNo,tCardType,cGI))
	                                {
	                                    logger.debug("单证回收错误（暂交费" + i + "）:" +tLJTempFee.getTempFeeNo());
	                                    logger.debug("错误原因：" +tPubCertifyTakeBack.mErrors.getFirstError().toString());
	                                }
                                }
                                else
                                {
                                	logger.debug("回收的单证号为空");
                                }
                            }
                        }
                    }
                }
            }
        }
	  }
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData()
	{
		int i, iMax;
		boolean tReturn = false;
		// 产生流水号
		LJTempFeeBL tempLJTempFeeBL = new LJTempFeeBL();
		tempLJTempFeeBL.setSchema(mLJTempFeeSet.get(1).getSchema());
		tLimit = PubFun.getNoLimit(tempLJTempFeeBL.getManageCom());
		if(this.mOperate.equals("INSERT"))
		{
			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		}
		else
		{
			serNo = tempLJTempFeeBL.getSerialNo();
		}

		// 添加纪录
		if(this.mOperate.equals("INSERT") || this.mOperate.equals("UPDATE"))
		{
			// 1-处理暂交费表，记录集，循环处理
			LJTempFeeBL tLJTempFeeBL;
			iMax = mLJTempFeeSet.size();
			String saveNo[][] = new String[iMax][2];
			String PolNo = "";
			int num = 0;
			existTempFeeNo = new String[iMax]; // 保存重复的数据库中已经存在的暂交费号码
			// 定额单判断生成暂交费号

			int NoIndex = 0; // 保存号码的数目
			/** @todo ----处理暂交费表LJTempFee---- */

			for(i = 1; i <= iMax; i++)
			{
				tLJTempFeeBL = new LJTempFeeBL();
				tLJTempFeeBL.setSchema(mLJTempFeeSet.get(i).getSchema());

                //查询该纪录是否已经存在,如果已存在，将号码保存在数组中
                if (!queryTempFee(tLJTempFeeBL.getSchema()) &&this.mOperate.equals("INSERT"))
                {
                    existTempFeeNo[NoIndex] = tLJTempFeeBL.getTempFeeNo();
                    NoIndex = NoIndex + 1;
                    continue;
                }
				// Modify by Minim，判断到帐日期，不为空则设置操作日期为当天
				if(tLJTempFeeBL.getEnterAccDate() != null && !tLJTempFeeBL.getEnterAccDate().equals(""))
				{
					tLJTempFeeBL.setConfMakeDate(PubFun.getCurrentDate());
					tLJTempFeeBL.setConfMakeTime(PubFun.getCurrentTime());
				}
				//对健康委托产品的特殊处理
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(tLJTempFeeBL.getRiskCode());
				if(tLMRiskAppDB.getInfo())
				{
					if("1".equals(tLMRiskAppDB.getSchema().getHealthType()))
						tLJTempFeeBL.setRiskType("1")	;
				}

				mCardNo = tLJTempFeeBL.getSerialNo();
				
				/** @todo 新契约 */
				// 首期交费时，管理机构和交费机构相同
				if(tLJTempFeeBL.getTempFeeType().equals("1"))
				{
//					tLJTempFeeBL.setPolicyCom(tLJTempFeeBL.getPolicyCom());
					if(tLJTempFeeBL.getOtherNo().equals("") || tLJTempFeeBL.getOtherNo() == null)
					{
						CError.buildErr(this, "未录入投保单印刷号");
						return false;
					}
										
	                if ("3".equals(tLJTempFeeBL.getTempFeeNoType()) &&this.mOperate.equals("INSERT")) //如果是定额单交费，要产生暂交费号
	                {
	                    //          sameCardFlag = false;
	                    tLJTempFeeBL.setState(tLJTempFeeBL.getOtherNo()); //将保存在其它号码处的单证编码重新放置在状态处
	                    tLJTempFeeBL.setOtherNo(tLJTempFeeBL.getTempFeeNo());
	                    tLJTempFeeBL.setOtherNoType("4"); //其它号码类型置印刷号
	                }
				}
				/** @todo 续期催收 */
				// 处理续收业务
				if(tLJTempFeeBL.getTempFeeType().equals("2"))
				{
					LJSPayDB tLJSPayDB = new LJSPayDB();
					tLJSPayDB.setGetNoticeNo(tLJTempFeeBL.getTempFeeNo());
					tLJSPayDB.setCurrency(tLJTempFeeBL.getCurrency());
					if(!tLJSPayDB.getInfo())
					{
						CError.buildErr(this, "缺少应收纪录");
						return false;
					}

					if(tLJSPayDB.getBankOnTheWayFlag() != null && tLJSPayDB.getBankOnTheWayFlag().equals("1"))
					{
						CError.buildErr(this, "银行在途不能交费");
						return false;
					}

					tLJTempFeeBL.setPolicyCom(tLJSPayDB.getManageCom());
				}
				/** @todo 保全 */
				if(tLJTempFeeBL.getTempFeeType().equals("4"))
				{
					LJSPayDB tLJSPayDB = new LJSPayDB();
					tLJSPayDB.setGetNoticeNo(tLJTempFeeBL.getTempFeeNo());
					tLJSPayDB.setCurrency(tLJTempFeeBL.getCurrency());
					if(!tLJSPayDB.getInfo())
					{
						CError.buildErr(this, "缺少应收纪录");
						return false;
					}

					if(tLJSPayDB.getBankOnTheWayFlag() != null && tLJSPayDB.getBankOnTheWayFlag().equals("1"))
					{
						CError.buildErr(this, "银行在途不能交费");
						return false;
					}

					tLJTempFeeBL.setPolicyCom(tLJSPayDB.getManageCom());
					tLJTempFeeBL.setOtherNoType("10");
				}
				/** @todo 理赔 */				
				if(tLJTempFeeBL.getTempFeeType().equals("6") && !"CaseBack".equals(SubmitFlag))
				{					
					LJSPayDB tLJSPayDB = new LJSPayDB();
					tLJSPayDB.setGetNoticeNo(tLJTempFeeBL.getTempFeeNo());
					tLJSPayDB.setCurrency(tLJTempFeeBL.getCurrency());
					if(!tLJSPayDB.getInfo())
					{
						CError tError = new CError();
						tError.moduleName = "DifSetBL";
						tError.functionName = "checkData";
						tError.errorMessage = "缺少应收纪录";
						this.mErrors.addOneError(tError);
						return false;
					}
					tLJTempFeeBL.setPolicyCom(tLJSPayDB.getManageCom());
					tLJTempFeeBL.setOtherNoType("2");//理赔赔案号
				}
				// 处理保费预存业务
				/** @todo 保费预存 */
//				if(tLJTempFeeBL.getTempFeeType().equals("5"))
//				{
//					mYCFlag = true;
//					tLJTempFeeBL.setPolicyCom(tLJTempFeeBL.getManageCom());
//					tLimit = PubFun.getNoLimit(tLJTempFeeBL.getManageCom()); // 产生通知书号即暂交费号
//					tNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
//					tLJTempFeeBL.setTempFeeNo(tNo);
//					if(tLJTempFeeBL.getEnterAccDate() != null)
//					{
//						logger.debug("--------------开始客户预存业务处理---------------");
//						logger.debug("本次备注信息:::::::::" + tLJTempFeeBL.getRemark());
//						// tLJTempFeeBL.setEnterAccDate(CurrentDate);
//						String tName = tLJTempFeeBL.getAPPntName();
//						String tIDType = "";
//						String tIDNo = "";
//						String nNo = PubFun1.CreateMaxNo("GETNO", tLimit);
//						mLJAGetSchema.setActuGetNo(nNo);
//						mLJAGetSchema.setAgentCode(tLJTempFeeBL.getAgentCode());
//						mLJAGetSchema.setAgentCom(tLJTempFeeBL.getAgentCom());
//						mLJAGetSchema.setAgentGroup(tLJTempFeeBL.getAgentGroup());
//						mLJAGetSchema.setDrawer(tName);
//						mLJAGetSchema.setDrawerID(tIDNo);
//						mLJAGetSchema.setOtherNo(tLJTempFeeBL.getOtherNo());
//						mLJAGetSchema.setGetNoticeNo(tNo);
//						mLJAGetSchema.setOtherNoType("YC");
//						mLJAGetSchema.setPayMode("1");
//						mLJAGetSchema.setShouldDate(CurrentDate);
//						mLJAGetSchema.setStartGetDate(CurrentDate);
//						mLJAGetSchema.setSumGetMoney(tLJTempFeeBL.getPayMoney());
//						String tSo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
//						mLJAGetSchema.setSerialNo(tSo);
//						mLJAGetSchema.setMakeDate(CurrentDate);
//						mLJAGetSchema.setMakeTime(CurrentTime);
//						mLJAGetSchema.setManageCom(tLJTempFeeBL.getManageCom());
//						mLJAGetSchema.setModifyDate(CurrentDate);
//						mLJAGetSchema.setModifyTime(CurrentTime);
//						mLJAGetSchema.setOperator(tGI.Operator);
//
//						mLJAGetTempFeeSchema.setMakeDate(CurrentDate);
//						mLJAGetTempFeeSchema.setMakeTime(CurrentTime);
//						mLJAGetTempFeeSchema.setManageCom(tLJTempFeeBL.getManageCom());
//						mLJAGetTempFeeSchema.setModifyDate(CurrentDate);
//						mLJAGetTempFeeSchema.setModifyTime(CurrentTime);
//						mLJAGetTempFeeSchema.setOperator(tGI.Operator);
//						mLJAGetTempFeeSchema.setActuGetNo(nNo);
//						mLJAGetTempFeeSchema.setTempFeeNo(tLJTempFeeBL.getTempFeeNo());
//						mLJAGetTempFeeSchema.setRiskCode("000000");
//						mLJAGetTempFeeSchema.setTempFeeType("5");
//						mLJAGetTempFeeSchema.setFeeOperationType("YC");
//						mLJAGetTempFeeSchema.setFeeFinaType("YC");
//						mLJAGetTempFeeSchema.setPayMode("Y");
//						mLJAGetTempFeeSchema.setGetMoney(tLJTempFeeBL.getPayMoney());
//						mLJAGetTempFeeSchema.setGetDate(CurrentDate);
//						mLJAGetTempFeeSchema.setAgentCom(tLJTempFeeBL.getAgentCom());
//						mLJAGetTempFeeSchema.setAgentCode(tLJTempFeeBL.getAgentCode());
//						mLJAGetTempFeeSchema.setAgentGroup(tLJTempFeeBL.getAgentGroup());
//						mLJAGetTempFeeSchema.setAPPntName(tName);
//						mLJAGetTempFeeSchema.setSerialNo(tSo);
//						mLJAGetTempFeeSchema.setGetReasonCode("YC");
//
//						map.put(mLJAGetTempFeeSchema, "INSERT");
//						map.put(mLJAGetSchema, "INSERT");
//						tLJTempFeeBL.setConfDate(CurrentDate);
//						tLJTempFeeBL.setConfFlag("1");
//					}
//				}
				//预收保费
				if("3".equals(tLJTempFeeBL.getTempFeeType()))
				{
					//差主险的判断
					String ySql="";
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					ySql="select conttype,payintv from lcpol where contno='?contno?' and appflag='1' and mainpolno=polno "
							   +"union all select conttype,payintv from lcpol where grpcontno='?contno?' and appflag='1'  and rownum=1 ";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						ySql="select conttype,payintv from lcpol where contno='?contno?' and appflag='1' and mainpolno=polno "
								   +"union all select conttype,payintv from lcpol where grpcontno='?contno?' and appflag='1' limit 0,1 ";
					}
					ExeSQL tExeSQL =new ExeSQL();
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(ySql);
					sqlbv.put("contno", tLJTempFeeBL.getOtherNo());
					SSRS tSSRS = new SSRS();
					tSSRS =tExeSQL.execSQL(sqlbv);
					for(int y=1;y<=tSSRS.MaxRow;y++)
					{
						String mSql="select riskperiod from lmriskapp where riskcode='?riskcode?'";
						SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
						sqlbv1.sql(mSql);
						sqlbv1.put("riskcode", tLJTempFeeBL.getRiskCode());
						SSRS mSsrs = tExeSQL.execSQL(sqlbv1);
						if(Integer.parseInt(tSSRS.GetText(y,2))<=0)
						{
							CError.buildErr(this, "该保单为非期交保单，请核实！");
							return false;
						}
						if(!"L".equals(mSsrs.GetText(1, 1)))
						{
							CError.buildErr(this, "该保单为非长险险种的保单，请核实!");
							return false;
						}
						MMap yMap = new MMap();
						if("1".equals(tSSRS.GetText(y, 1)))
						{
						  DifSetBL tDifSetBL = new DifSetBL();
						  VData tVData = new VData();
						  tVData.addElement(tLJTempFeeBL);
						  if(!tDifSetBL.submitData(tVData, "YSTemp"))
						  {
							  mErrors.copyAllErrors(tDifSetBL.mErrors);
							  CError.buildErr(this, "更新"+tLJTempFeeBL.getOtherNo()+"保单余额失败！");
							  return false;
						  }
						  else
						  {
							  yMap = new MMap();
						      yMap =tDifSetBL.mp;
						      map.add(yMap);
						  }
						}
						else if("2".equals(tSSRS.GetText(y, 1)))
						{
						  GrpDifSetBL tGrpDifSetBL = new GrpDifSetBL();
						  VData tVData = new VData();
						  tVData.addElement(tLJTempFeeBL);
						  if(!tGrpDifSetBL.submitData(tVData, "YSTemp"))
						  {
							  mErrors.copyAllErrors(tGrpDifSetBL.mErrors);
							  CError.buildErr(this, "更新"+tLJTempFeeBL.getOtherNo()+"保单余额失败！");
							  return false;
						  }
						  else
						  {
							  yMap = new MMap();
							  yMap =tGrpDifSetBL.mp;
							  map.add(yMap);
						  }
						}
						else
						{
							CError.buildErr(this, "该合同既不是个单也不是团单，请核实！");
							return false;
						}
					}
					
					//如果是预收，则立即核销
					tLJTempFeeBL.setEnterAccDate(PubFun.getCurrentDate());
                    tLJTempFeeBL.setConfFlag("1");
                    tLJTempFeeBL.setConfDate(PubFun.getCurrentDate());
					
				}
				// 续期非催收
				/** @todo 团体不定期 */
				if(tLJTempFeeBL.getTempFeeType().equals("8"))
				{
					LCGrpContDB tLCGrpContDB = new LCGrpContDB();
					tLCGrpContDB.setGrpContNo(tLJTempFeeBL.getOtherNo());
					if(!tLCGrpContDB.getInfo())
					{
					    LCPolDB tLCPolDB = new LCPolDB();
					    LCPolSet tLCPolSet = new LCPolSet();
					    tLCPolDB.setContNo(tLJTempFeeBL.getOtherNo());
					    tLCPolDB.setRiskCode(tLJTempFeeBL.getRiskCode());
					    tLCPolSet = tLCPolDB.query();
					    					   
						if(tLCPolSet.size()<=0||tLCPolSet==null)
						{
							CError.buildErr(this, "合同号为"+tLJTempFeeBL.getOtherNo()+"对应的保单信息缺失，请核实！");
							return false;
						}
						for(int m=1;m<=tLCPolSet.size();m++)
						{
							if(!("-1".equals(String.valueOf(tLCPolSet.get(m).getPayIntv()))))
							{
								CError.buildErr(this, "合同号为"+tLJTempFeeBL.getOtherNo()+",险种编码为"+tLJTempFeeBL.getRiskCode()+"的保单交费间隔不是不定期交费，请核实！");
								return false;
							}
						}
						mQueryType = "2";
					}
					else
					{
						LCGrpPolSet tGrpPolSet = new LCGrpPolSet();
						LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
						tLCGrpPolDB.setGrpContNo(tLJTempFeeBL.getOtherNo());
						tLCGrpPolDB.setRiskCode(tLJTempFeeBL.getRiskCode());
						tGrpPolSet = tLCGrpPolDB.query();
						if(tGrpPolSet.size()<=0||tGrpPolSet==null)
						{
							CError.buildErr(this, "合同号为"+tLJTempFeeBL.getOtherNo()+"对应的集体保单信息缺失，请核实！");
							return false;
						}
						for(int m=1;m<=tGrpPolSet.size();m++)
						{
							if(!("-1".equals(String.valueOf(tGrpPolSet.get(m).getPayIntv()))))
							{
								CError.buildErr(this, "合同号为"+tLJTempFeeBL.getOtherNo()+"的交费间隔不是不定期交费，请核实！");
								return false;
							}
						}
						mQueryType = "1";
					}
					tLimit = PubFun.getNoLimit(tLJTempFeeBL.getManageCom()); // 产生通知书号即暂交费号
					tNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
					tLJTempFeeBL.setTempFeeNo(tNo);
                    saveNo[num][0] =tLJTempFeeBL.getOtherNo(); //第一位存放保单号
                    saveNo[num][1] = tNo; //第二位存放新生成的暂交费号
                    num++; //后面循环用到;为了与对应的暂交费分类纪录的关联（保单号相同则赋予新生成的暂交费号）
				}
				
				// 判断该保单号码类型，填充OtherNoType字段
				PolNo = tLJTempFeeBL.getOtherNo();

				tLJTempFeeBL.setSerialNo(serNo);

				if(this.mOperate.equals("INSERT"))
				{
					tLJTempFeeBL.setMakeDate(CurrentDate); // 入机日期
					tLJTempFeeBL.setMakeTime(CurrentTime); // 入机时间
				}
				tLJTempFeeBL.setModifyDate(CurrentDate); // 最后一次修改日期
				tLJTempFeeBL.setModifyTime(CurrentTime); // 最后一次修改时间
				//如果是预收则即时核销
				if(!("3".equals(tLJTempFeeBL.getTempFeeType())))	
				tLJTempFeeBL.setConfFlag("0"); // 核销标志置0
				//处理其他号码类型
				if("1".equals(tLJTempFeeBL.getTempFeeType()))
                  tLJTempFeeBL.setOtherNoType("4");//投保单印刷号
                //当是续期收费时，判断该号码的类型
				else if ("2".equals(tLJTempFeeBL.getTempFeeType())  ||"3".equals(tLJTempFeeBL.getTempFeeType()))
                {
					ExeSQL pExeSQL = new ExeSQL();
					String pSql="select 1 from lccont where contno='?contno?' and grpcontno='00000000000000000000'";
					String gSql="select 1 from lcgrpcont where grpcontno='?grpcontno?'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(pSql);
					sqlbv2.put("contno", tLJTempFeeBL.getOtherNo().trim());
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(gSql);
					sqlbv3.put("grpcontno", tLJTempFeeBL.getOtherNo().trim());
					if(pExeSQL.execSQL(sqlbv2).MaxRow>0)
					{
						tLJTempFeeBL.setOtherNoType("0");//个人合同号
					}
					else if(pExeSQL.execSQL(sqlbv3).MaxRow>0)
					{
						tLJTempFeeBL.setOtherNoType("1");//集体合同号
					}
					else
					{
						CError.buildErr(this,"合同号为"+tLJTempFeeBL.getOtherNo()+"对应的合同信息不存在，请核实！");
						return false;
					}
                }
				else if ("4".equals(tLJTempFeeBL.getTempFeeType()))
					tLJTempFeeBL.setOtherNoType("10");//保全受理号
				else if("8".equals(tLJTempFeeBL.getTempFeeType()))
				{
					if("1".equals(mQueryType))
					{
						tLJTempFeeBL.setOtherNoType("1");//集体合同号
					}
					else if ("2".equals(mQueryType))
					{
						tLJTempFeeBL.setOtherNoType("0");//个人合同号
					}
					else
					{
						CError.buildErr(this,"合同号为"+tLJTempFeeBL.getOtherNo()+"对应的保单类型不存在，请核实！");
						return false;
					}
				}
				else
				{
					String aSql = "select 1 from ldperson where customerno='?customerno?'";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(aSql);
					sqlbv4.put("customerno", tLJTempFeeBL.getOtherNo());
					ExeSQL tExeSQL = new ExeSQL();
					if(tExeSQL.execSQL(sqlbv4).getMaxRow()>0)
						tLJTempFeeBL.setOtherNoType("5");//客户号
				}

				mLJTempFeeSetNew.add(tLJTempFeeBL);
				tReturn = true;
				

			}
			

			// 2-处理暂交费分类表，记录集，循环处理
			LJTempFeeClassBL tLJTempFeeClassBL;
			LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
			iMax = mLJTempFeeClassSet.size();
			boolean existTempFeeClassFlag = false;
			/** @todo ----处理暂交费表分类表LJTempFeeClass----* */
			for(i = 1; i <= iMax; i++)
			{
				existTempFeeClassFlag = false; // 初始化为假
				tLJTempFeeClassBL = new LJTempFeeClassBL();
				tLJTempFeeClassBL.setSchema(mLJTempFeeClassSet.get(i).getSchema());
				int m = i;
				if(m > mLJTempFeeSet.size())
				{
					m = mLJTempFeeSet.size();
				}
				// 判断暂交费分类纪录是否也是重复的纪录，从数组中查找
				if(NoIndex > 0 && this.mOperate.equals("INSERT"))
				{ // 如果暂交费纪录有重复
					for(int n = 0; n < NoIndex; n++)
					{

						if(tLJTempFeeClassBL.getTempFeeNo().equals(existTempFeeNo[n]))
						{
							existTempFeeClassFlag = true; // 找到重复的暂交费分类纪录
						}
					}
					if(existTempFeeClassFlag == true)
					{
						continue;
					}
				}
				if("".equals(tLJTempFeeClassBL.getChequeNo()) || tLJTempFeeClassBL.getChequeNo() == null)
				{
					tLJTempFeeClassBL.setChequeNo("000000");
				}
				tLJTempFeeClassBL.setSerialNo(serNo);
				//zy 2009-11-03 如果交费方式为现金，且现金交费方式为空，则默认现金交费方式为客户自交现金P11
				if("1".equals(tLJTempFeeClassBL.getPayMode())
				&&(tLJTempFeeClassBL.getTempFeeNoType()==null || "".equals(tLJTempFeeClassBL.getTempFeeNoType()	)))
				{
					tLJTempFeeClassBL.setTempFeeNoType("P11");
				}
				if(this.mOperate.equals("INSERT"))
				{
					tLJTempFeeClassBL.setMakeDate(CurrentDate); // 入机时间
					tLJTempFeeClassBL.setMakeTime(CurrentTime); // 入机时间
				}

				tLJTempFeeClassBL.setModifyDate(CurrentDate);
				tLJTempFeeClassBL.setModifyTime(CurrentTime);
				tLJTempFeeClassBL.setConfFlag("0"); // 核销标志置0
				
				
		
				
				
                //如果是预收，则立即核销
                for (int y = 1; y <= mLJTempFeeSet.size(); y++)
                {
                	//add at 2010-11-26
                	if (tLJTempFeeClassBL.getTempFeeNo().equals(mLJTempFeeSet.get(y).getTempFeeNo()) )
        			{
                		tLJTempFeeClassBL.setOtherNo(mLJTempFeeSet.get(y).getOtherNo());
                		tLJTempFeeClassBL.setOtherNoType(mLJTempFeeSet.get(y).getOtherNoType());
        		
        			}
                    if (tLJTempFeeClassBL.getTempFeeNo().equals(mLJTempFeeSet.get(y).getTempFeeNo()) 
                    		&& mLJTempFeeSet.get(y).getTempFeeType().equals("3"))
                    {
                        tLJTempFeeClassBL.setConfFlag("1");
                        tLJTempFeeClassBL.setConfDate(PubFun.getCurrentDate());
                    }
                }

				// 如果号码类型不是交费收据号31，那么循环找到数组中对应的保单号，
				// (因为对于要产生暂交费号码的类型，其暂交费分类纪录的暂交费号存放的是保单号或印刷号)
				// 所以和前面保存的保单号或者印刷号对比，如果匹配，将新生成的暂交费号赋予暂交费分类纪录的暂交费号
				if(!(tLJTempFeeClassBL.getTempFeeNo().length() == 20 && 
						CodeJudge.judgeCodeType(tLJTempFeeClassBL.getTempFeeNo(), "31"))
						&& this.mOperate.equals("INSERT"))
				{
					for(int n = 0; n < num; n++)
					{ // 循环处理：如果是相同的保单号
						if(tLJTempFeeClassBL.getTempFeeNo().equals(saveNo[n][0]))
						{
							tLJTempFeeClassBL.setTempFeeNo(saveNo[n][1]);
							break;
						}
					}
				}

				if(tLJTempFeeClassBL.getPayMode().equals("3")||tLJTempFeeClassBL.getPayMode().equals("4"))
				{
					tLJTempFeeClassBL.setEnterAccDate("");
					tLJTempFeeClassBL.setConfMakeDate("");

					// 处理主表的到帐日期
					for(int j = 0; j < mLJTempFeeSetNew.size(); j++)
					{
						if(mLJTempFeeSetNew.get(j + 1).getSchema().getTempFeeNo().equals(tLJTempFeeClassBL.getTempFeeNo()))
						{
							mLJTempFeeSetNew.get(j + 1).setEnterAccDate("");
							mLJTempFeeSetNew.get(j + 1).setConfMakeDate("");
						}
					}
				}
				else
				{
					// tLJTempFeeClassBL.setConfMakeDate(tLJTempFeeClassBL.getEnterAccDate());
					// //后续添加：将到帐日期赋给财务确认操作日期
					// Modify by Minim，判断到帐日期，不为空则设置操作日期为当天
					if(tLJTempFeeClassBL.getEnterAccDate() != null && !tLJTempFeeClassBL.getEnterAccDate().equals(""))
					{
						tLJTempFeeClassBL.setConfMakeDate(PubFun.getCurrentDate());
						tLJTempFeeClassBL.setConfMakeTime(PubFun.getCurrentTime());
					}
				}

//				if(this.mOperate.equals("UPDATE"))
//				{
//					// 查询上次暂收是否为内部转账，如果是，取给付通知书号
//					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
//					tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeClassBL.getTempFeeNo());
//					tLJTempFeeClassDB.setPayMode("5");
//					if(tLJTempFeeClassDB.getInfo())
//					{
//						String tChequeNo = tLJTempFeeClassDB.getChequeNo();
//						VData ttData = new VData();
//						LJFIGetDB tLJFIGetDB = new LJFIGetDB();
//						tLJFIGetDB.setActuGetNo(tChequeNo);
//						tLJFIGetDB.setPayMode("5");
//						LJFIGetSet tLJFIGetSet = new LJFIGetSet();
//						tLJFIGetSet = tLJFIGetDB.query();
//						LJAGetDB tLJAGetDB = new LJAGetDB();
//						tLJAGetDB.setActuGetNo(tChequeNo);
//						if(tLJAGetDB.getInfo())
//						{
//							LJAGetSchema tLJAGetSchema = new LJAGetSchema();
//							tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
//							tLJAGetSchema.setConfDate("");
//							tLJAGetSchema.setEnterAccDate("");
//							String tFlag = tLJAGetSchema.getOtherNoType();
//							SchemaSet tSchemaSet = new SchemaSet();
//							if(tFlag.equals("0") || tFlag.equals("1") || tFlag.equals("2"))
//							{
//								LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
//								LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
//								tLJAGetDrawDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJAGetDrawSet = tLJAGetDrawDB.query();
//								for(int j = 1; j <= tLJAGetDrawSet.size(); j++)
//								{
//									LJAGetDrawSchema tLJAGetDrawSchema = tLJAGetDrawSet.get(j);
//									tLJAGetDrawSchema.setConfDate("");
//									tLJAGetDrawSchema.setEnterAccDate("");
//									tLJAGetDrawSet.set(j, tLJAGetDrawSchema);
//								}
//								tSchemaSet = tLJAGetDrawSet;
//							}
//
//							if(tFlag.equals("10"))
//							{
//								LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
//								LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
//								tLJAGetEndorseDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJAGetEndorseSet = tLJAGetEndorseDB.query();
//								for(int j = 1; j <= tLJAGetEndorseSet.size(); j++)
//								{
//									LJAGetEndorseSchema tLJAGetEndorseSchema = tLJAGetEndorseSet.get(j);
//									tLJAGetEndorseSchema.setGetConfirmDate("");
//									tLJAGetEndorseSchema.setEnterAccDate("");
//									tLJAGetEndorseSet.set(j, tLJAGetEndorseSchema);
//								}
//								tSchemaSet = tLJAGetEndorseSet;
//							}
//
//							if(tFlag.equals("4"))
//							{
//								LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
//								LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
//								tLJAGetTempFeeDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJAGetTempFeeSet = tLJAGetTempFeeDB.query();
//								for(int j = 1; j <= tLJAGetTempFeeSet.size(); j++)
//								{
//									LJAGetTempFeeSchema tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(j);
//									tLJAGetTempFeeSchema.setConfDate("");
//									tLJAGetTempFeeSchema.setEnterAccDate("");
//									tLJAGetTempFeeSet.set(j, tLJAGetTempFeeSchema);
//								}
//								tSchemaSet = tLJAGetTempFeeSet;
//
//							}
//
//							if(tFlag.equals("5"))
//							{
//								LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
//								LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
//								tLJAGetClaimDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJAGetClaimSet = tLJAGetClaimDB.query();
//								for(int j = 1; j <= tLJAGetClaimSet.size(); j++)
//								{
//									LJAGetClaimSchema tLJAGetClaimSchema = tLJAGetClaimSet.get(j);
//									tLJAGetClaimSchema.setConfDate("");
//									tLJAGetClaimSchema.setEnterAccDate("");
//									tLJAGetClaimSet.set(j, tLJAGetClaimSchema);
//								}
//								tSchemaSet = tLJAGetClaimSet;
//							}
//
//							if(tFlag.equals("6"))
//							{
//								LJAGetOtherSet tLJAGetOtherSet = new LJAGetOtherSet();
//								LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
//								tLJAGetOtherDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJAGetOtherSet = tLJAGetOtherDB.query();
//								for(int j = 1; j <= tLJAGetOtherSet.size(); j++)
//								{
//									LJAGetOtherSchema tLJAGetOtherSchema = tLJAGetOtherSet.get(j);
//									tLJAGetOtherSchema.setConfDate("");
//									tLJAGetOtherSchema.setEnterAccDate("");
//									tLJAGetOtherSet.set(j, tLJAGetOtherSchema);
//								}
//								tSchemaSet = tLJAGetOtherSet;
//							}
//
//							if(tFlag.equals("7"))
//							{
//								LJABonusGetSet tLJABonusGetSet = new LJABonusGetSet();
//								LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
//								tLJABonusGetDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
//								tLJABonusGetSet = tLJABonusGetDB.query();
//								for(int j = 1; j <= tLJABonusGetSet.size(); j++)
//								{
//									LJABonusGetSchema tLJABonusGetSchema = tLJABonusGetSet.get(j);
//									tLJABonusGetSchema.setConfDate("");
//									tLJABonusGetSchema.setEnterAccDate("");
//									tLJABonusGetSet.set(j, tLJABonusGetSchema);
//								}
//								tSchemaSet = tLJABonusGetSet;
//							}
//
//							mLJAGetSet.add(tLJAGetSchema);
//							ttData.add(tLJFIGetSet);
//							ttData.add(tLJAGetSchema);
//							ttData.add(tFlag);
//							ttData.add(tSchemaSet);
//							mLastFinData.add(ttData);
//						}
//					}
//
//				}

				if(tLJTempFeeClassBL.getPayMode().equals("5") && !"CaseBack".equals(SubmitFlag))
				{ // 如果暂交费分类纪录的交费方式是5-内部转账
					mPayMode = "5";

					LJAGetDB tLJAGetDB = new LJAGetDB();
					tLJAGetDB.setActuGetNo(tLJTempFeeClassBL.getChequeNo());
					tLJAGetDB.setCurrency(tLJTempFeeClassBL.getCurrency());
					if(!tLJAGetDB.getInfo())
					{
						CError.buildErr(this, "未找到对应的实付相关信息!");
						return false;
					}
					LJAGetSchema tLJAGetSchema = tLJAGetDB.getSchema();
					if(tLJAGetSchema.getActuGetNo() == null)
					{
						CError.buildErr(this, "未找到对应的实付相关信息!");
						return false;
					}
					if(tLJAGetSchema.getConfDate()!=null)
					{
						CError.buildErr(this, "实付号为"+tLJTempFeeClassBL.getChequeNo()+"的实付信息已经核销！");
						return false;
					}


					tLJFIGetSchema = new LJFIGetSchema();
					tLJFIGetSchema.setActuGetNo(tLJTempFeeClassBL.getChequeNo()); // 将票据号赋予实付号
					tLJFIGetSchema.setPayMode("5"); // 交费方式
					tLJFIGetSchema.setCurrency(tLJTempFeeClassBL.getCurrency()); // 币种
					tLJFIGetSchema.setGetMoney(tLJTempFeeClassBL.getPayMoney()); // 交费金额
					tLJFIGetSchema.setEnterAccDate(tLJTempFeeClassBL.getEnterAccDate()); // 到帐日期
					tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
					tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
					tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
					tLJFIGetSchema.setConfDate(CurrentDate); // 确认日期为当天
					tLJFIGetSchema.setSerialNo(tLJTempFeeClassBL.getSerialNo());
					tLJFIGetSchema.setOperator(tGI.Operator);
					tLJFIGetSchema.setMakeDate(CurrentDate);
					tLJFIGetSchema.setMakeTime(CurrentTime);
					tLJFIGetSchema.setModifyDate(CurrentDate);
					tLJFIGetSchema.setModifyTime(CurrentTime);

			        if (!tLJAGetSchema.getCurrency().equals(tLJFIGetSchema.getCurrency()) || tLJAGetSchema.getSumGetMoney() != tLJFIGetSchema.getGetMoney())
			        {
			            CError.buildErr(this, "作内部转账的退费金额与暂收金额不符");
			            return false;
			        }
					mLJFIGetSet.add(tLJFIGetSchema);

					// 从应付表中取应收金额，更新实付表

					tLJAGetSchema.setEnterAccDate(tLJTempFeeClassBL.getEnterAccDate());
					tLJAGetSchema.setConfDate(CurrentDate);
					tLJAGetSchema.setModifyDate(CurrentDate);
					tLJAGetSchema.setModifyTime(CurrentTime);
					tLJAGetSchema.setPayMode(tLJFIGetSchema.getPayMode());
					tLJAGetSchema.setSerialNo(tLJFIGetSchema.getSerialNo());
					VData tVData = new VData();
					tVData.add(tLJFIGetSchema);
					tVData.add(tLJAGetSchema);
					tVData.add(tGI);
					OperFinFeeGetBL tOperFinFeeGetBL = new OperFinFeeGetBL();
					if(!tOperFinFeeGetBL.submitData(tVData, "VERIFY"))
					{
						this.mErrors.copyAllErrors(tOperFinFeeGetBL.mErrors);
						return false;
					}
					MMap tmap = new MMap();
					tmap = tOperFinFeeGetBL.getMap();
					map.add(tmap);
				}
				mLJTempFeeClassSetNew.add(tLJTempFeeClassBL);
				tReturn = true;
			}
		}
		return tReturn;
	}


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData)
	{
		// 暂交费表
		mLJTempFeeSet.set((LJTempFeeSet)mInputData.getObjectByObjectName("LJTempFeeSet", 0));
		// 暂交费分类表
		mLJTempFeeClassSet.set((LJTempFeeClassSet)mInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		//zy 2009-11-03 代码审核，请注意代码的质量，不需要在获取前台数据时处理该段逻辑，尤其在dealdata方法中本身存在对mLJTempFeeClassSet循环处理
//		//财务外包数据中如果有现金收费的记录，对现金收费方式进行赋值(现金收费方式LJTempfeeClass.TempfeeNoType:P11-客户自交 P12-人工收取)
//		for(int i=1;i<=mLJTempFeeClassSet.size();i++){
//			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
//			tLJTempFeeClassSchema = mLJTempFeeClassSet.get(i);
//			if("1".equals(tLJTempFeeClassSchema.getPayMode())
//					&&(tLJTempFeeClassSchema.getTempFeeNoType()==null || "".equals(tLJTempFeeClassSchema.getTempFeeNoType()	))){
//				tLJTempFeeClassSchema.setTempFeeNoType("P11");
//			}
//		}
//		 公用变量tGI
		tGI = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
		if(mInputData.getObjectByObjectName("TransferData", 0) != null)
		{
			mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
			tFlag = (String)mTransferData.getValueByName("Flag");//判断是界面录入还是录入外包
			if(mTransferData.getValueByName("FeeUpdate")!=null)
			{
				FeeUpdate =(String) mTransferData.getValueByName("FeeUpdate");//暂收费修改功能的标志
			}
			if(mTransferData.getValueByName("SubmitFlag")!=null)
			{
				SubmitFlag =(String) mTransferData.getValueByName("SubmitFlag");//是否提交标识
			}
			if(mTransferData.getValueByName("CERTIFY_XQTempFee")!=null)
			{
				CERTIFY_XQTempFee = (String) mTransferData.getValueByName("CERTIFY_XQTempFee");
			}
		}
		if(mLJTempFeeSet == null || mLJTempFeeClassSet == null || tGI == null)
		{
			// @@错误处理
			CError.buildErr(this, "没有得到足够的数据，请您确认!");
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据 gaohuiting
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData()
	{
		mResult.clear();

		map.put(mLJTempFeeSetNew, "INSERT");
		map.put(mLJTempFeeClassSetNew, "INSERT");
		map.put(mLJTempFeeClassSetDel, "DELETE");
		/* 单次 EFT 操作 */
		if(mLJSPaySetNew.size() > 0)
		{
			map.put(mLJSPaySetNew, "UPDATE");
		}
//		/** 客户帐户需要修改的表 */
//		if(mCAFlag)
//		{
//			map.put(mLJAGetSet, "UPDATE");
//			map.put(mLJFIGetSet, "INSERT");
//			map.put(mLCContSet, "UPDATE");
//			map.put(mLCCustomerAccTraceSet, "INSERT");
//		}
//		else
//		{
//			if(mLCCustomerAccTraceSet.size() > 0)
//			{
//				map.put(mLCCustomerAccTraceSet, "INSERT");
//			}
//
//			if(mLJSPayFlag.equals("1"))
//			{
//				map.put(mLJSPaySet, "INSERT");
//			}
//			int a = 0;
//			int b = 0;
//			int c = 0;
//			// for (int m = 1; m <= mLJTempFeeClassSetNew.size(); m++)
//			// {
//			// LJTempFeeClassSchema tLJTempFeeClassSchema = new
//			// LJTempFeeClassSchema();
//			// tLJTempFeeClassSchema = mLJTempFeeClassSetNew.get(m);
//			// String mPayMode = tLJTempFeeClassSchema.getPayMode();
//			// if (mPayMode.equals("5"))
//			// {
//			// a = a + 1;
//			// }
//			// }
//			for(int j = 1; j <= mLJTempFeeSetNew.size(); j++)
//			{
//				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
//				tLJTempFeeSchema = mLJTempFeeSetNew.get(j);
//				String uTempFeeType = tLJTempFeeSchema.getTempFeeType();
//				if(uTempFeeType.equals("3") || uTempFeeType.equals("7"))
//				{
//					b = b + 1;
//				}
//			}
//			for(int g = 1; g <= mLJTempFeeSetNew.size(); g++)
//			{
//				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
//				tLJTempFeeSchema = mLJTempFeeSetNew.get(g);
//				String uTempFeeType = tLJTempFeeSchema.getTempFeeType();
//
//				if(uTempFeeType.equals("5"))
//				{
//					c = c + 1;
//				}
//			}
//
//			// if (a >= 1)
//			// {
//			// map.put(mLJFIGetSet, "INSERT");
//			// map.put(mLJAGetSet, "INSERT");
//			// }
//			if(b >= 1)
//			{
//				if(mQueryType.equals("2"))
//				{
//					map.put(mLCContSet, "UPDATE");
//				}
//				if(mQueryType.equals("1"))
//				{
//					map.put(mLCGrpContSet, "UPDATE");
//				}
//			}
//			if(c >= 1)
//			{
//				// String pOperate = "";
//				// pOperate =
//				// (TransferData)tInsuAccBalaApp.getResult().getObjectByObjectName("LCCustomerAccSchema",0);
//				map.put(mLCCustomerAccSet, fi);
//				map.put(mLCCustomerAccTraceSet, fi);
//			}
//		}
		mResult.add(map);
		return true;
	}

	/*
	 * 查询暂交费表 返回布尔值
	 */
	private boolean queryTempFee(LJTempFeeSchema pLJTempFeeSchema)
	{
		String TempFeeNo = pLJTempFeeSchema.getTempFeeNo();
		if(TempFeeNo == null)
		{
			CError.buildErr(this, "暂交费号不能为空!");
			return false;
		}
		// 查询凡是同一个收据号的纪录
		String sqlStr = "select * from LJTempFee where TempFeeNo='?TempFeeNo?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlStr);
		sqlbv5.put("TempFeeNo", TempFeeNo);
		
		logger.debug("查询暂交费表:" + sqlStr);
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv5);
		if(tLJTempFeeDB.mErrors.needDealError() == true)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			CError.buildErr(this, "暂交费表查询失败!");
			tLJTempFeeSet.clear();
			return false;
		}
		if(tLJTempFeeSet.size() > 0)
		{

			// @@错误处理
			CError.buildErr(this, "暂交费号为：" + TempFeeNo + " 的纪录已经存在！");
			return false;
		}
		return true;
	}


	public VData getResult()
	{
		mResult.clear();
		return mResult;
	}

	/**
	 * 检验
	 * 
	 * @return
	 */
	public boolean checkData()
	{
		
		String currentTempFeeNo = "";
		String tMaxEnterAccDate = "";

		for(int k = 1; k <= mLJTempFeeClassSet.size(); k++)
		{
			// 校验是否数据收费机构
			if(mLJTempFeeClassSet.get(k).getManageCom() != null && mLJTempFeeClassSet.get(k).getManageCom().equals(""))
			{
				CError.buildErr(this, "无收费机构！");
				return false;
			}
			//zy 2010-02-04 增加管理机构位数的校验
			// 校验是否数据收费机构
			//zy 2010-03-24 排除电子商务的收费
			if(!("0001".equals(mLJTempFeeClassSet.get(k).getOperator()) && "86".equals(mLJTempFeeClassSet.get(k).getManageCom())))
			{
				if(mLJTempFeeClassSet.get(k).getManageCom().length()<8)
				{
					CError.buildErr(this, "请以八位管理机构进行财务收费！");
					return false;
				}
			}
			// 校验是否输入管理机构
			if(mLJTempFeeClassSet.get(k).getPolicyCom() != null && mLJTempFeeClassSet.get(k).getPolicyCom().equals(""))
			{
				CError.buildErr(this, "无管理机构！");
				return false;
			}
			// 如果是现金交费-那么存入当天系统时间,如果是支票，则置空
			//如果为现金和现金支票，则到帐日期为当天
			if(mLJTempFeeClassSet.get(k).getPayMode().equals("1") || "2".equals(mLJTempFeeClassSet.get(k).getPayMode()) ||  "6".equals(mLJTempFeeClassSet.get(k).getPayMode()))
			{
				mLJTempFeeClassSet.get(k).setEnterAccDate(PubFun.getCurrentDate());
			}
			//如果为转账支票和银行转账则到账日期为空
			else if(mLJTempFeeClassSet.get(k).getPayMode().equals("3") || mLJTempFeeClassSet.get(k).getPayMode().equals("4"))
			{
				mLJTempFeeClassSet.get(k).setEnterAccDate("");
			}

			//不同的交费方式录入的明细的校验
			if(mLJTempFeeClassSet.get(k).getPayMode().equals("6")
					&& (mLJTempFeeClassSet.get(k).getBankCode() == null || mLJTempFeeClassSet.get(k).getBankCode()
							.length() == 0))
			{
				CError.buildErr(this, "请录入托收银行代码!");
				return false;
			}
			else if("2".equals(mLJTempFeeClassSet.get(k).getPayMode())
					|| "3".equals(mLJTempFeeClassSet.get(k).getPayMode()))
			{
				if(mLJTempFeeClassSet.get(k).getBankCode() == null
						|| mLJTempFeeClassSet.get(k).getBankCode().length() == 0
						|| mLJTempFeeClassSet.get(k).getChequeNo() == null
						|| mLJTempFeeClassSet.get(k).getChequeNo().length() == 0)
				{
					CError.buildErr(this, "请录入支票号和银行编码");
					return false;
				}
			}
			else if("4".equals(mLJTempFeeClassSet.get(k).getPayMode()))
			{
				if(mLJTempFeeClassSet.get(k).getBankCode() == null
						|| mLJTempFeeClassSet.get(k).getBankCode().length() == 0)
				{
					CError.buildErr(this, "请录入银行编码");
					return false;
				}
				if(mLJTempFeeClassSet.get(k).getBankAccNo() == null
						|| mLJTempFeeClassSet.get(k).getBankAccNo().length() == 0)
				{
					CError.buildErr(this, "请录入银行账号");
					return false;
				}
				if(mLJTempFeeClassSet.get(k).getAccName() == null
						|| mLJTempFeeClassSet.get(k).getAccName().length() == 0)
				{
					CError.buildErr(this, "请录入银行户名");
					return false;
				}
			}
		}
 
		// 到帐日期处理
		for(int n = 1; n <= mLJTempFeeClassSet.size(); n++)
		{
			tMaxEnterAccDate = "1900-1-1";
			currentTempFeeNo = mLJTempFeeClassSet.get(n).getTempFeeNo();

			// 把分类表中同一暂交费的纪录查出，如果有一项到帐日期为空，则tMaxEnterAccDate置空。否则取最大的到帐日期
			for(int m = 1; m <= mLJTempFeeClassSet.size(); m++)
			{
				// 当交费类型与方式数目不等的时候无法处理
				if(!mLJTempFeeSet.get(1).getTempFeeType().equals("3"))
				{
					if(mLJTempFeeClassSet.get(m).getTempFeeNo().equals(currentTempFeeNo))
					{
						if(mLJTempFeeClassSet.get(m).getEnterAccDate() == null
								|| mLJTempFeeClassSet.get(m).getEnterAccDate().equals(""))
						{
							tMaxEnterAccDate = "";
							break;
						}
						else
						{
							// 起始日期，终止日期 ,比较单位 (格式："YYYY-MM-DD")
							int t = PubFun.calInterval(tMaxEnterAccDate, mLJTempFeeClassSet.get(m).getEnterAccDate(),"D");
							if(t > 0)
							{
								tMaxEnterAccDate = mLJTempFeeClassSet.get(m).getEnterAccDate();
							}
						}
					}
				}
			}
			
			for(int s = 1; s <= mLJTempFeeSet.size(); s++)
			{
				// 把主表中同一暂交费的纪录查出，置到帐日期为tMaxEnterAccDate.
				if(mLJTempFeeSet.get(s).getTempFeeNo().equals(currentTempFeeNo))
				{
					mLJTempFeeSet.get(s).setEnterAccDate(tMaxEnterAccDate);
				}
				
				//zy 2010-02-04 增加管理机构位数的校验
				// 校验是否数据收费机构
				if(!("0001".equals(mLJTempFeeSet.get(s).getOperator()) && "86".equals(mLJTempFeeSet.get(s).getManageCom())))
				{
					if(mLJTempFeeSet.get(s).getManageCom().length()<8)
					{
						CError.buildErr(this, "请以八位管理机构进行财务收费！");
						return false;
					}
				}
				
				/** @todo 查询代理人组别* */
				String tAgentCode = mLJTempFeeSet.get(s).getAgentCode();
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(tAgentCode);
				if(!tLAAgentDB.getInfo())
				{
					CError.buildErr(this, "没有找到代理人编码："+ tAgentCode +"对应的代理人信息");
					return false;
				}
				else
					mLJTempFeeSet.get(s).setAgentGroup(tLAAgentDB.getBranchCode());
			}
		}
		
        //预收校验处理 :无未结案的报案或立案信息，无未保全确认的保全申请
        for (int m = 1; m <= mLJTempFeeSet.size(); m++)
        {
        	// 2008-12-23 暂时屏蔽掉对单证的校验
            if (mLJTempFeeSet.get(m).getTempFeeType().equals("3"))
            {
            	ExeSQL tExeSQL;
                //校验该预交费收据号在保单的管理机构下且单证类型为3209
//                LZCardSet mLZCardSet = new LZCardSet();
//                LZCardDB mLZCardDB = new LZCardDB();
//                String cSql = "select * from lzcard where certifycode='3209' and receivecom like 'D%' and  "
//                	        + "sendoutcom = 'A" + mLJTempFeeSet.get(m).getManageCom() +"' and stateflag='0' "
//                	        + "and OperateFlag='0' and startno<='" +mLJTempFeeSet.get(m).getTempFeeNo().trim() + "' "
//                	        + "and endno >='" + mLJTempFeeSet.get(m).getTempFeeNo().trim() + "'";
//                mLZCardSet = mLZCardDB.executeQuery(cSql);
//
//                if (mLZCardSet.size() == 0)
//                {
//                    // @@错误处理
//                    CError.buildErr(this, "该预收收据没有下放到代理人手中！");
//                    return false;
//                }
                
              //有垫交要先还垫再做预收操作
                String bSql = "select 1 from lccontstate where contno='?contno?' "
                            + "and statetype='PayPrem' and state='1' and startdate<='?CurrentDate?'  "
                            + "and (enddate >='?CurrentDate?' or enddate is null)";
                SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
                sqlbv6.sql(bSql);
                sqlbv6.put("contno", mLJTempFeeSet.get(m).getOtherNo());
                sqlbv6.put("CurrentDate", CurrentDate);
               
                tExeSQL = new ExeSQL();
                SSRS bSsrs = tExeSQL.execSQL(sqlbv6);
                if(bSsrs.MaxRow>0)
                {
                	CError.buildErr(this, "该合同号下的保单有垫交，请先还垫再做预收操作!");
                	return false;
                }
                
                //如果有未交暂收的应收续期保费，则将该未交数额与本次续期所交数额进行比较
                LJSPaySet tLJSPaySet = new LJSPaySet();
                LJSPayDB tLJSPayDB = new LJSPayDB();
                String xSql = "select * from ljspay where otherno='?otherno?' and othernotype='2' ";
                SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
                sqlbv7.sql(xSql);
                sqlbv7.put("otherno", mLJTempFeeSet.get(m).getOtherNo());
                tLJSPaySet = tLJSPayDB.executeQuery(sqlbv7);

                if (tLJSPaySet.size() > 0)
                {
                    double payMoney = 0.00;

                    for (int k = 1; k <= tLJSPaySet.size(); k++)
                    {
                        payMoney += tLJSPaySet.get(k).getSumDuePayMoney();

                        LJTempFeeSet tempLJTempFeeSet = new LJTempFeeSet();
                        LJTempFeeDB tempLJTempFeeDB = new LJTempFeeDB();
                        String xSql1 = "select * from ljtempfee where tempfeeno='?tempfeeno?' "
                                     + "and tempfeetype='2'";
                        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
                        sqlbv8.sql(xSql1);
                        sqlbv8.put("tempfeeno", tLJSPaySet.get(k).getGetNoticeNo());
                        tempLJTempFeeSet = tempLJTempFeeDB.executeQuery(sqlbv8);
                        if (tempLJTempFeeSet.size() > 0)
                        {
                            for (int z = 1; z <= tempLJTempFeeSet.size();z++)
                            {
                                payMoney -= tempLJTempFeeSet.get(z).getPayMoney();
                            }
                        }
                    }

                    logger.debug("payMoney:" + payMoney);
                    if (payMoney > 0)
                    {
                        CError.buildErr(this, "该合同号的保单未交满续期应收款项，不允许做预收操作!");
                        return false;
                    }
                }
                tExeSQL = new ExeSQL();
                String lSql = "select 1 from llclaimpolicy where contno='?contno?' and clmstate not in ('40','50','60','70')";
                SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
                sqlbv9.sql(lSql);
                sqlbv9.put("contno", mLJTempFeeSet.get(m).getOtherNo());
                if (tExeSQL.execSQL(sqlbv9).MaxRow > 0)
                {
                    CError.buildErr(this, "该合同处于理赔未结案状态，不允许做预收操作!");
                    return false;
                }

        		ContHangUpBL tContHangUpBL = new ContHangUpBL(tGI);
        		if (!tContHangUpBL.checkHangUpState(mLJTempFeeSet.get(m).getOtherNo(), "2")) // 2-保全
        		{
        			mErrors.copyAllErrors(tContHangUpBL.mErrors);
        			 CError.buildErr(this, "该合同正处于保全挂起状态，不允许做预收操作!");
        			return false;
        		} 
        		
        		if (!tContHangUpBL.checkGrpHangUpState(mLJTempFeeSet.get(m).getOtherNo(), "2")) // 2-保全
        		{
        			mErrors.copyAllErrors(tContHangUpBL.mErrors);
        			CError.buildErr(this, "该合同正处于保全挂起状态，不允许做预收操作!");
        			return false;
        		}

                //插入时判断数据库中是否有该预收费收据号，如果有则返回
                if (this.mOperate.equals("INSERT"))
                {
                    LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
                    LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
                    tLJTempFeeDB.setTempFeeNo(mLJTempFeeSet.get(m).getTempFeeNo());
                    tLJTempFeeSet = tLJTempFeeDB.query();

                    if (tLJTempFeeSet.size() > 0)
                    {
                        CError.buildErr(this, "该预收费收据号在暂收费表中已存在，请重新输入预收费收据号!");
                        return false;
                    }

                    LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
                    LJTempFeeClassDB mLJTempFeeClassDB = new LJTempFeeClassDB();
                    mLJTempFeeClassDB.setTempFeeNo(mLJTempFeeSet.get(m)
                                                                .getTempFeeNo());
                    mLJTempFeeClassSet = mLJTempFeeClassDB.query();

                    if (mLJTempFeeClassSet.size() > 0)
                    {
                        CError.buildErr(this, "该预收费收据号在暂收费分类表中已存在，请重新输入预收费收据号!");
                        return false;
                    }
                }
            }
        }
		return true;
	}

	// 根据号码类型、实付号查找转账明晰
	public LCCustomerAccTraceSet prepareCustomerAcc(String tActuGetNo, String tNoType)
	{
		LCCustomerAccTraceSet tLCCustomerAccTraceSet = new LCCustomerAccTraceSet();
		LJAGetDB tempLJAGetDB = new LJAGetDB();
		tempLJAGetDB.setActuGetNo(tActuGetNo);
		tempLJAGetDB.getInfo();
		// 实付关联号为getnoticeno,也可在这里调整为ActuGetNo
		String tAccHappenNo = tempLJAGetDB.getGetNoticeNo();
		double tMoneyofljaget = tempLJAGetDB.getSumGetMoney();
		double sumMoney = 0;
		if("5".equals(tNoType))
		{
			LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
			tLJAGetClaimDB.setActuGetNo(tActuGetNo);
			LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.query();
			if(tLJAGetClaimSet.size() == 0)
			{
				CError tError = new CError();
				tError.moduleName = "TempFeeBL";
				tError.functionName = "prepareCustomerAcc";
				tError.errorMessage = "查询LJAGetClaim失败!";
				this.mErrors.addOneError(tError);
				return null;
			}
			else
			{
				LCCustomerAccTraceSchema tLCCustomerAccTraceSchema;
				for(int i = 1; i <= tLJAGetClaimSet.size(); i++)
				{
					LJAGetClaimSchema tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
					sumMoney = Arith.add(sumMoney, tLJAGetClaimSchema.getPay());
					tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
					tLCCustomerAccTraceSchema.setCustomerNo(mCustomerNo);
					tLCCustomerAccTraceSchema.setAccHappenNo(tAccHappenNo);
					tLCCustomerAccTraceSchema.setAccNo(1);
					tLCCustomerAccTraceSchema.setInsuAccNo(mContNo);
					tLCCustomerAccTraceSchema.setAccType(mAccType);
					tLCCustomerAccTraceSchema.setCustomerType(mCustomerType);
					tLCCustomerAccTraceSchema.setOtherNo(tLJAGetClaimSchema.getOtherNo());
					tLCCustomerAccTraceSchema.setOtherNoType(tLJAGetClaimSchema.getOtherNoType());
					tLCCustomerAccTraceSchema.setMoney(tLJAGetClaimSchema.getPay());
					tLCCustomerAccTraceSchema.setOperationType(tLJAGetClaimSchema.getFeeOperationType());
					tLCCustomerAccTraceSchema.setOperFlag("2"); // 转出
					tLCCustomerAccTraceSchema.setMoneyType(tLJAGetClaimSchema.getFeeFinaType());
					tLCCustomerAccTraceSchema.setOperator(tGI.Operator);
					tLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
					tLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
					tLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
					tLCCustomerAccTraceSchema.setModifyTime(CurrentTime);
					tLCCustomerAccTraceSet.add(tLCCustomerAccTraceSchema);

				}
			}
		}
		else if("10".equals(tNoType))
		{
			LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
			tLJAGetEndorseDB.setActuGetNo(tActuGetNo);
			LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.query();
			if(tLJAGetEndorseSet.size() == 0)
			{
				CError tError = new CError();
				tError.moduleName = "TempFeeBL";
				tError.functionName = "prepareCustomerAcc";
				tError.errorMessage = "查询LJAGetEndorse失败!";
				this.mErrors.addOneError(tError);
				return null;
			}
			else
			{
				LCCustomerAccTraceSchema tLCCustomerAccTraceSchema;
				for(int i = 1; i <= tLJAGetEndorseSet.size(); i++)
				{
					LJAGetEndorseSchema tLJAGetEndorseSchema = tLJAGetEndorseSet.get(i);
					sumMoney = Arith.add(sumMoney, tLJAGetEndorseSchema.getGetMoney());
					tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
					tLCCustomerAccTraceSchema.setCustomerNo(mCustomerNo);
					tLCCustomerAccTraceSchema.setAccHappenNo(tAccHappenNo);
					tLCCustomerAccTraceSchema.setAccNo(1);
					tLCCustomerAccTraceSchema.setInsuAccNo(mContNo);
					tLCCustomerAccTraceSchema.setAccType(mAccType);
					tLCCustomerAccTraceSchema.setCustomerType(mCustomerType);
					tLCCustomerAccTraceSchema.setOtherNo(tLJAGetEndorseSchema.getOtherNo());
					tLCCustomerAccTraceSchema.setOtherNoType(tLJAGetEndorseSchema.getOtherNoType());
					tLCCustomerAccTraceSchema.setMoney(tLJAGetEndorseSchema.getGetMoney());
					tLCCustomerAccTraceSchema.setOperationType(tLJAGetEndorseSchema.getFeeOperationType());
					tLCCustomerAccTraceSchema.setOperFlag("2"); // 转出
					tLCCustomerAccTraceSchema.setMoneyType(tLJAGetEndorseSchema.getFeeFinaType());
					tLCCustomerAccTraceSchema.setOperator(tGI.Operator);
					tLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
					tLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
					tLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
					tLCCustomerAccTraceSchema.setModifyTime(CurrentTime);
					tLCCustomerAccTraceSet.add(tLCCustomerAccTraceSchema);

				}
			}

		}

		else if("2".equals(tNoType))
		{
			LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
			tLJAGetDrawDB.setActuGetNo(tActuGetNo);
			LJAGetDrawSet tLJAGetDrawSet = tLJAGetDrawDB.query();
			if(tLJAGetDrawSet.size() == 0)
			{
				CError tError = new CError();
				tError.moduleName = "TempFeeBL";
				tError.functionName = "prepareCustomerAcc";
				tError.errorMessage = "查询LJAGetDraw失败!";
				this.mErrors.addOneError(tError);
				return null;
			}
			else
			{
				LCCustomerAccTraceSchema tLCCustomerAccTraceSchema;
				for(int i = 1; i <= tLJAGetDrawSet.size(); i++)
				{
					LJAGetDrawSchema tLJAGetDrawSchema = tLJAGetDrawSet.get(i);
					sumMoney = Arith.add(sumMoney, tLJAGetDrawSchema.getGetMoney());
					tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
					tLCCustomerAccTraceSchema.setCustomerNo(mCustomerNo);
					tLCCustomerAccTraceSchema.setAccHappenNo(tAccHappenNo);
					tLCCustomerAccTraceSchema.setAccNo(1);
					tLCCustomerAccTraceSchema.setInsuAccNo(mContNo);
					tLCCustomerAccTraceSchema.setAccType(mAccType);
					tLCCustomerAccTraceSchema.setCustomerType(mCustomerType);
					tLCCustomerAccTraceSchema.setOtherNo(tLJAGetDrawSchema.getContNo());
					tLCCustomerAccTraceSchema.setOtherNoType("2");
					tLCCustomerAccTraceSchema.setMoney(tLJAGetDrawSchema.getGetMoney());
					tLCCustomerAccTraceSchema.setOperationType(tLJAGetDrawSchema.getFeeOperationType());
					tLCCustomerAccTraceSchema.setOperFlag("2"); // 转出
					tLCCustomerAccTraceSchema.setMoneyType(tLJAGetDrawSchema.getFeeFinaType());
					tLCCustomerAccTraceSchema.setOperator(tGI.Operator);
					tLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
					tLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
					tLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
					tLCCustomerAccTraceSchema.setModifyTime(CurrentTime);
					tLCCustomerAccTraceSet.add(tLCCustomerAccTraceSchema);
				}
			}
		}

		else if("4".equals(tNoType))
		{
			LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
			tLJAGetTempFeeDB.setActuGetNo(tActuGetNo);
			LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB.query();
			if(tLJAGetTempFeeSet.size() == 0)
			{
				CError tError = new CError();
				tError.moduleName = "TempFeeBL";
				tError.functionName = "prepareCustomerAcc";
				tError.errorMessage = "查询LJAGetTempFee失败!";
				this.mErrors.addOneError(tError);
				return null;
			}
			else
			{
				LCCustomerAccTraceSchema tLCCustomerAccTraceSchema;
				for(int i = 1; i <= tLJAGetTempFeeSet.size(); i++)
				{
					LJAGetTempFeeSchema tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i);
					sumMoney = Arith.add(sumMoney, tLJAGetTempFeeSchema.getGetMoney());
					tLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
					tLCCustomerAccTraceSchema.setCustomerNo(mCustomerNo);
					tLCCustomerAccTraceSchema.setAccHappenNo(tAccHappenNo);
					tLCCustomerAccTraceSchema.setAccNo(1);
					tLCCustomerAccTraceSchema.setInsuAccNo(mContNo);
					tLCCustomerAccTraceSchema.setAccType(mAccType);
					tLCCustomerAccTraceSchema.setCustomerType(mCustomerType);
					tLCCustomerAccTraceSchema.setOtherNo(tLJAGetTempFeeSchema.getTempFeeNo());
					tLCCustomerAccTraceSchema.setOtherNoType("4");
					tLCCustomerAccTraceSchema.setMoney(tLJAGetTempFeeSchema.getGetMoney());
					tLCCustomerAccTraceSchema.setOperationType(tLJAGetTempFeeSchema.getFeeOperationType());
					tLCCustomerAccTraceSchema.setOperFlag("2"); // 转出
					tLCCustomerAccTraceSchema.setMoneyType(tLJAGetTempFeeSchema.getFeeFinaType());
					tLCCustomerAccTraceSchema.setOperator(tGI.Operator);
					tLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
					tLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
					tLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
					tLCCustomerAccTraceSchema.setModifyTime(CurrentTime);
					tLCCustomerAccTraceSet.add(tLCCustomerAccTraceSchema);
				}
			}
		}
		else
		{
			CError tError = new CError();
			tError.moduleName = "TempFeeBL";
			tError.functionName = "prepareCustomerAcc";
			tError.errorMessage = "数据有误：不支持的othernotype";
			this.mErrors.addOneError(tError);
			return null;

		}
		logger.debug("LJAGet中的金额：" + tMoneyofljaget + "   子表中的总金额：" + sumMoney);
		tMoneyofljaget = Math.abs(tMoneyofljaget);
		sumMoney = Math.abs(sumMoney);
		if(Math.abs(Arith.sub(tMoneyofljaget, sumMoney)) > 0)
		{
			CError tError = new CError();
			tError.moduleName = "TempFeeBL";
			tError.functionName = "prepareCustomerAcc";
			tError.errorMessage = "数据有误：实付总金额与各实付明细项金额之和不相等";
			this.mErrors.addOneError(tError);
			return null;

		}
		return tLCCustomerAccTraceSet;
	}

	private void jbInit() throws Exception
	{
	}

}
