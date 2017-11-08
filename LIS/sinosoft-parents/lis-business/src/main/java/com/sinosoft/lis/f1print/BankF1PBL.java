package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class BankF1PBL extends NoticeF1PBO {
private static Logger logger = Logger.getLogger(BankF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap map = new MMap();

	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String isRePrint = "";// 0-第一次打印通知书 1-重打通知书

	public BankF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			if (cOperate.equals("CONFIRM")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData();
			} else if (cOperate.equals("PRINT")) {
				if (!saveData(cInputData)) {
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}




	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));

		if (mLCContSchema != null && (mLCContSchema.getContNo() == null || "".equals(mLCContSchema.getContNo()))) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}else{
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLCContSchema.getContNo());
			if(!tLCContDB.getInfo()){
				mErrors.copyAllErrors(tLCContDB.mErrors);
				this.buildError("LCContDB.getInfo()", "保单表查询出错！");
				return false;
			}
			mLCContSchema = tLCContDB.getSchema();
			isRePrint = "0";
		}
		if(mOperate.equals("PRINT")){
			mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
					.getObjectByObjectName("LOPRTManagerSchema", 0));
			if(mLOPRTManagerSchema != null && (mLOPRTManagerSchema.getPrtSeq() == null || "".equals(mLOPRTManagerSchema.getPrtSeq()))){
//				buildError("getInputData", "没有得到足够的信息！");
//				return false;
			}else{
				LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
				tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
				if(!tLOPRTManagerDB.getInfo()){
					mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					this.buildError("LOPRTManagerDB.getInfo()", "打印管理表查询出错！");
					return false;
				}
				mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
				isRePrint = "1";
			}
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "BankF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setOtherNoType("05");
		tLOPRTManagerDB.setOtherNo(mLCContSchema.getPrtNo());
		tLOPRTManagerDB.setCode("21");
		tLOPRTManagerDB.setPatchFlag("0");
		
		LOPRTManagerSet tLOPRTManagerSet =  tLOPRTManagerDB.query();
		if(tLOPRTManagerSet != null &&tLOPRTManagerSet.size() > 1){
			buildError("getPrintData","转账不成功通知书只能打印一次！");
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(this.mLCContSchema.getAgentCode());
		if(!tLAAgentDB.getInfo()){
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("合同代理人信息查询失败！");
		}
		//账户信息取财务录入内容
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSql = " select a.bankcode, a.accname, a.bankaccno from ljtempfeeclass a,ljtempfee b ,lccont c where c.prtno = '"
				+ "?prtno?"
				+ "' and c.prtno = b.otherno and a.tempfeeno = b.tempfeeno and b.othernotype = '4' ";
		sqlbv.sql(tSql);
		sqlbv.put("prtno", mLCContSchema.getPrtNo());
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS == null && tSSRS.MaxRow == 0){
			this.buildError("getPrintData", "银行账户信息查询失败:"+tSql);
		}
		
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(tSSRS.GetText(1, 1));
		if(!tLDBankDB.getInfo()){
			mErrors.copyAllErrors(tLDBankDB.mErrors);
			throw new Exception("银行信息查询失败！");
		}
		String tPrintName = "转账不成功通知书";
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		String thead =PrintTool.getHead(mLCContSchema.getAppntName(), mLCContSchema.getAppntSex(), uLanguage);

		//开始准备数据
		TextTag texttag = new TextTag();
		texttag.add("AppntName", thead);
		texttag.add("PrtNo", mLCContSchema.getPrtNo());
		texttag.add("AccName", tSSRS.GetText(1, 2));
		texttag.add("BankName", tLDBankDB.getBankName());
		texttag.add("BankAccNo", tSSRS.GetText(1, 3));
		texttag.add("Prem", mLCContSchema.getPrem());
		
		texttag.add("AgentName", tLAAgentDB.getName());
		texttag.add("AgentCode", tLAAgentDB.getAgentCode());
		texttag.add("ManageCom", BqNameFun.getComName(mLCContSchema.getManageCom()));
		texttag.add("Prem", mLCContSchema.getPrem());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("Today", df.format(new Date()));
		xmlExport.addTextTag(texttag);

		mResult.clear();
		mResult.addElement(xmlExport);
	}

	//暂时没用
	private boolean saveData(VData mInputData) {
		
		if(isRePrint == "1"){
			mLOPRTManagerSchema.setStateFlag("1");
			mLOPRTManagerSchema.setDoneDate(CurrentDate);
			mLOPRTManagerSchema.setDoneTime(CurrentTime);
			mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
			
			map.put(mLOPRTManagerSchema, "UPDATE");
		}else{
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String tSql = " select enteraccdate from ljtempfee where TempFeeType = '1' and OtherNoType = '4' and OtherNo = '"+"?OtherNo?"+"'";
			sqlbv.sql(tSql);
			sqlbv.put("OtherNo", mLCContSchema.getPrtNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if(tSSRS != null && !"".equals(tSSRS.GetText(1, 1))){
				this.buildError("saveData", "印刷号：【" +mLCContSchema.getPrtNo()+"】保费已经到帐！" );
				return false;
			}
			
			// 保存【保险费转账不成功通知书】打印 数据
			String cLimit = PrintManagerBL.CODE_BANK_SUCC.replaceAll("[^0-9]", "");
			logger.debug("---cLimit---" + cLimit);
			String mPrtSeqUW = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
			//logger.debug("---tLimit---" + tLimit);

			// 准备打印管理表数据
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeqUW);
			mLOPRTManagerSchema.setOtherNo(mLCContSchema.getPrtNo());
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_PRT); // 保单印刷号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BANK_SUCC); // 保险费转账不成功通知书
			mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mLOPRTManagerSchema.setExeCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mLOPRTManagerSchema.setStateFlag("1");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate()) ;
			mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
//			mLOPRTManagerSchema.setStandbyFlag1(mLCContSchema.getAppntNo()); // 投保人编码
//			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeqUW);
//			mLOPRTManagerSchema.setForMakeDate(tDate);
			
			map.put(mLOPRTManagerSchema, "INSERT");
		}
		
		
		VData tVData = new VData();
		tVData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if(!tPubSubmit.submitData(tVData,"")){
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		
		return true;
	}

	
	
	public static void main(String[] args) {
		
	}
}
