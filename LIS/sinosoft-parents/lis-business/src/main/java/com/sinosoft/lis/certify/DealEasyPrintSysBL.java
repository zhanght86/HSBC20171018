package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMCertifySubDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManager2Schema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DealEasyPrintSysBL {
private static Logger logger = Logger.getLogger(DealEasyPrintSysBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mPolNo;
	/** 业务处理相关变量 */
	private String CertifyCode = "";
	private TransferData mTransferData = new TransferData();
	private MMap mMap = new MMap();
	private String mEasyPrintNo;
	public DealEasyPrintSysBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CodeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败CodeBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if(!prepareOutputData()){
			
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this,"数据提交失败!");
			return false;
		}
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		if(mTransferData==null){
			CError.buildErr(this, "数据传输失败！");
			return false;
		}
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		if(mPolNo==null||"".equals(mPolNo)){
			CError.buildErr(this, "数据传输失败！");
			return false;
		}
		mEasyPrintNo = (String) mTransferData.getValueByName("EasyPrintNo");
		if(mEasyPrintNo==null||"".equals(mEasyPrintNo)){
			CError.buildErr(this, "请输入待回收的单张号码！");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if(!checkData()){
			CError.buildErr(this, "打印失败，请确认输入的单证号码是否正确！");
			return false;
		}
		if(!dealCont()){
			CError.buildErr(this, "更改合同状态为已打印失败！");
			return false;
		}
		if(!sysRecycle()){
			CError.buildErr(this, "单证回收失败！");
			return false;
		}
		return true;
	}
	
	/**
	 * 校验单证是否已回收
	 * */
	private boolean checkData(){
		LZCardDB tLZCardDB = new LZCardDB();
		LZCardSet tLZCardSet = new LZCardSet();
		String tSql = "select * from lzcard where certifycode in ('123003','123004','123005','123006')"
//						+ " and managecom='"+mGlobalInput.ManageCom+"'"
						+ " and startno='"+"?startno?"+"'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("startno", mEasyPrintNo);
		tLZCardSet = tLZCardDB.executeQuery(sqlbv);
		if(tLZCardSet.size()<1){
			CError.buildErr(this, "打印失败！请确认输入的单证号码是否正确");
			return false;
		}else{
			CertifyCode = tLZCardSet.get(1).getCertifyCode();
		}
		return true;
	}
	
	/**
	 * 修改合同状态为已打印
	 * */
	private boolean dealCont(){
	    String strSql="update lccont set PrintCount=1 where contno in (select contno from lcpol where polno='"+"?polno?"+"' )";
	    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	    sqlbv1.put("polno", mPolNo);
	    mMap.put(sqlbv1, "UPDATE");
		return true;
	}
	
	/**
	 * 回收单证
	 * */
	private boolean sysRecycle(){
		PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
		if (!tPubCertifyTakeBack.CertifyTakeBack_A(mEasyPrintNo,mEasyPrintNo,CertifyCode,mGlobalInput)){
			CError.buildErr(this, "单证回收失败！");
			return false;
		}
		return true;
	}

	private boolean prepareOutputData(){
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86110000";
		tGI.ManageCom = "86110000";
		tGI.Operator = "001";
		LOPRTManager2Schema tLOPRTManagerSchema = new LOPRTManager2Schema();
		tLOPRTManagerSchema.setCode("13");
		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(tGI);
		AutoSysCertSendOutBL tAutoSysCertSendOutBL = new AutoSysCertSendOutBL();
		tAutoSysCertSendOutBL.submitData(tVData, "ASCSO2");
	}
}
