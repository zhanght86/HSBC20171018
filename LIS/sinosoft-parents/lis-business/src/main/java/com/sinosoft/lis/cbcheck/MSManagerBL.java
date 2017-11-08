package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOMSErrorLogDB;
import com.sinosoft.lis.db.LOMSManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOMSErrorLogSchema;
import com.sinosoft.lis.schema.LOMSManagerSchema;
import com.sinosoft.lis.vschema.LOMSErrorLogSet;
import com.sinosoft.lis.vschema.LOMSManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>
 * Title: Web业务系统 短信管理表 维护
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author hanbin
 */
public class MSManagerBL {
private static Logger logger = Logger.getLogger(MSManagerBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LOMSManagerSchema mLOMSManagerSchema = new LOMSManagerSchema();
	private LOMSErrorLogSchema mLOMSErrorLogSchema = new LOMSErrorLogSchema();
	private MMap map = new MMap();
	private String mOperate;
	private String mMSType;
//	private String mMSSeq;
	private String flag = "0";//同一合同下是否有未发送的短信 0-没有  1-有 


	public   MSManagerBL(){
	}
	
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("------begin---MSManagerBL------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---MSManagerBL  end getInputData---");
		// 校验数据是否打印
		if (!checkData()) {
			return false;
		}
		logger.debug("---MSManagerBL end checkData---");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
			}  
		if(!prepareOutputData()){
			return false;
		}
		logger.debug("MSManagerBL end prepareOutputData ---!");
		
		logger.debug("MSManagerBL end  ---!");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			mLCContSchema = (LCContSchema) cInputData.getObjectByObjectName(
					"LCContSchema", 0);
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			
			mOperate = mGlobalInput.Operator;
			mMSType = (String)mTransferData.getValueByName("MSType");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 对出入数据的校验
	 * @param args
	 */
	private boolean checkData(){
		if(mGlobalInput == null || "".equals(mGlobalInput.Operator)){
			CError .buildErr(this,"获取操作员信息失败！");
			return false;
		}
		if(mLCContSchema == null || "".equals(mLCContSchema.getContNo())){
			CError .buildErr(this,"合同信息查询出错！");
			return false;
		}
		if(mMSType == null || "".equals(mMSType)){
			CError .buildErr(this,"您未传入要发送的短信类新！");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 业务处理
	 * @param args
	 */
	private boolean dealData(){
		LCContDB tLCContDB = new LCContDB(); 
		tLCContDB.setContNo(mLCContSchema.getContNo());
		if(!tLCContDB.getInfo()){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "MSManagerBL";
			tError.functionName = "dealData";
			tError.errorMessage = "合同信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCContSchema tLCContSchema = tLCContDB.getSchema();
		
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(tLCContSchema.getContNo());
		if(!tLCAppntDB.getInfo()){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "MSManagerBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCAppntSchema tLCAppntSchema = tLCAppntDB.getSchema();
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		if(!tLAAgentDB.getInfo()){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "MSManagerBL";
			tError.functionName = "dealData";
			tError.errorMessage = "代理人查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LAAgentSchema tLAAgentSchema = tLAAgentDB.getSchema();
		String tAgentMobile = tLAAgentSchema.getMobile();
		if(tAgentMobile == null || "".equals(tAgentMobile) || "null".equals(tAgentMobile)){
			LOMSErrorLogDB  tLOMSErrorLogDB = new LOMSErrorLogDB();
			tLOMSErrorLogDB.setPrtNo(tLCContSchema.getPrtNo());
			tLOMSErrorLogDB.setMSType(mMSType);
			LOMSErrorLogSet tLOMSErrorLogSet = tLOMSErrorLogDB.query();
			if(tLOMSErrorLogSet != null && tLOMSErrorLogSet.size() > 0){
				//当存在这个投保单的错误信息 则累计错误次数
				LOMSErrorLogSchema tLOMSErrorLogSchema = tLOMSErrorLogSet.get(1);
				tLOMSErrorLogSchema.setStandbyFlag1(String.valueOf(Integer.parseInt(tLOMSErrorLogSchema.getStandbyFlag1()) + 1));//借用字段--累计错误次数
				tLOMSErrorLogSchema.setModifyDate(PubFun.getCurrentDate());
				tLOMSErrorLogSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(tLOMSErrorLogSet.get(1), "UPDATE");
			}else{//不存在 则插入一条错误数据
				String mMSErrorSeq = PubFun1.CreateMaxNo("MSErrorSeq", 20);
				mLOMSErrorLogSchema.setMSSeq(mMSErrorSeq);
				mLOMSErrorLogSchema.setPrtNo(tLCContSchema.getPrtNo());
				mLOMSErrorLogSchema.setMSType(mMSType);
				mLOMSErrorLogSchema.setState("0"); //0 - 未发送、1 - 已发送
				mLOMSErrorLogSchema.setAppntNo(tLCAppntSchema.getAppntNo());
				mLOMSErrorLogSchema.setAppntName(tLCAppntSchema.getAppntName());
				mLOMSErrorLogSchema.setManageCom(tLCContSchema.getManageCom());
				mLOMSErrorLogSchema.setAgentCode(tLAAgentSchema.getAgentCode());
				mLOMSErrorLogSchema.setAgentName(tLAAgentSchema.getName());
				mLOMSErrorLogSchema.setAgentMobile(tLAAgentSchema.getMobile());
				mLOMSErrorLogSchema.setMSContent(mMSType);
				mLOMSErrorLogSchema.setErrorLog("代理人手机信息不存在！");
				mLOMSErrorLogSchema.setOperator(mGlobalInput.Operator);
				mLOMSErrorLogSchema.setMakeDate(PubFun.getCurrentDate());
				mLOMSErrorLogSchema.setMakeTime(PubFun.getCurrentTime());
				mLOMSErrorLogSchema.setModifyDate(PubFun.getCurrentDate());
				mLOMSErrorLogSchema.setModifyTime(PubFun.getCurrentTime());
				mLOMSErrorLogSchema.setStandbyFlag1("1");//错误次数
				map.put(mLOMSErrorLogSchema, "INSERT");
			}
			
		}else{
			LOMSManagerDB tLOMSManagerDB = new LOMSManagerDB();
			tLOMSManagerDB.setPrtNo(tLCContSchema.getPrtNo());
			tLOMSManagerDB.setState("0");
			LOMSManagerSet  tLOMSManagerSet  = tLOMSManagerDB.query();
			if(tLOMSManagerSet != null && tLOMSManagerSet.size() > 0){
				flag = "1"; // 合同存在未发短信 则累计短信内容
				mLOMSManagerSchema = tLOMSManagerSet.get(1);
				mLOMSManagerSchema.setMSContent(mLOMSManagerSchema.getMSContent()+ "&"+mMSType);//累计
				mLOMSManagerSchema.setModifyDate(PubFun.getCurrentDate());
				mLOMSManagerSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(mLOMSManagerSchema, "UPDATAE");
				
			}else{
				//填充mLOMSManagerSchema
				String mMSSeq = PubFun1.CreateMaxNo("MSSeq", 20);
				mLOMSManagerSchema.setMSSeq(mMSSeq);
				mLOMSManagerSchema.setPrtNo(tLCContSchema.getPrtNo());
				mLOMSManagerSchema.setMSType(mMSType);
				mLOMSManagerSchema.setState("0"); //0 - 未发送、1 - 已发送
				mLOMSManagerSchema.setAppntNo(tLCAppntSchema.getAppntNo());
				mLOMSManagerSchema.setAppntName(tLCAppntSchema.getAppntName());
				mLOMSManagerSchema.setManageCom(tLCContSchema.getManageCom());
				mLOMSManagerSchema.setAgentCode(tLAAgentSchema.getAgentCode());
				mLOMSManagerSchema.setAgentName(tLAAgentSchema.getName());
				mLOMSManagerSchema.setAgentMobile(tLAAgentSchema.getMobile());
				mLOMSManagerSchema.setMSContent(mMSType);
				mLOMSManagerSchema.setOperator(mGlobalInput.Operator);
				mLOMSManagerSchema.setMakeDate(PubFun.getCurrentDate());
				mLOMSManagerSchema.setMakeTime(PubFun.getCurrentTime());
				mLOMSManagerSchema.setModifyDate(PubFun.getCurrentDate());
				mLOMSManagerSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(mLOMSManagerSchema, "INSERT");
			}
		}
		return true;
	}
	/**
	 * 准备要传出的数据
	 * @return
	 */
	private boolean prepareOutputData(){
		
		mResult.clear();
		mResult.add(map);
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
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
