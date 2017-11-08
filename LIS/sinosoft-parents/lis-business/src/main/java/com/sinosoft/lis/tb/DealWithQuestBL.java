package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeModDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;




public class DealWithQuestBL {
private static Logger logger = Logger.getLogger(DealWithQuestBL.class);
	
	public DealWithQuestBL(){
		
	}
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mInputData = new VData();//接受前台传入的数据
	private TransferData mTransferData = new TransferData();//接收前台传入的数据
	private TransferData tTransferData = new TransferData();//存放返回前台的数据
	private LDCodeModSchema mLDCodeModSchema = new LDCodeModSchema();
	private MMap map = new MMap();
	private VData mResult = new VData();//存放返回前台的数据
	public CErrors mErrors = new CErrors();
	
	private String mOperate="";
	private String mCode="";//处理后最终的的问题件编码
	private String tCode="";//计算用的问题件编码
	private String theCurrentDate = PubFun.getCurrentDate();
//	private String theCurrentTime = PubFun.getCurrentTime();
	
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in DSGetBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData()) {
			CError.buildErr(this, "前台传入数据出错!");
			return false;
		}
		logger.debug("---getInputData---");

		if (!checkData()) {
			return false;
		}
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (!dealData()) {
			//CError.buildErr(this, "处理数据时出错!");
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			return false;
		}

		return true;
	}
	
	private boolean getInputData(){
		mTransferData=(TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		mLDCodeModSchema=(LDCodeModSchema) mInputData.getObjectByObjectName("LDCodeModSchema", 0);
		tCode = (String) mTransferData.getValueByName("TheCode");
		return true;
	}
	
	private boolean checkData(){
		return true;
	}
	
	private boolean dealData(){
		//tongmeng 2009-02-06 add
		//规则编码由界面传入
		if(mOperate.equals("INSERT")){
			/*
			if(tCode.length()!=1){
				CError.buildErr(this, "前台传入问题件代码有误");
				return false;
			}
			*/
			/*
			String ttCode="";
			ttCode=PubFun1.CreateMaxNo( "QuestNo",2);
			mCode=tCode+"1"+ttCode;
			logger.debug("生成的问题件编码为："+mCode);
			*/
			mCode = tCode;
			if(tCode.length()!=4){
				CError.buildErr(this, "前台传入问题件代码有误");
				return false;
			}
			if(!tCode.substring(0,1).equals(mLDCodeModSchema.getSendObj()))
			{
				CError.buildErr(this, "问题件编码第一位与发放对象不符!");
				return false;
			}
			
			mLDCodeModSchema.setCode(mCode);
			mLDCodeModSchema.setCodeName("承保问题件");
			mLDCodeModSchema.setModifyDate(theCurrentDate);
			map.put(mLDCodeModSchema, "INSERT");
			//mCode=
		}else {
			LDCodeModDB tLDCodeModDB = new LDCodeModDB();
			tLDCodeModDB.setCode(tCode);
			tLDCodeModDB.setCodeType("Question");
			if(!tLDCodeModDB.getInfo()){
				CError.buildErr(this, "前台传入问题件编码错误，未查到相应的问题件！");
				return false;
			}
			tLDCodeModDB.setCont(mLDCodeModSchema.getCont());
			tLDCodeModDB.setRecordQuest(mLDCodeModSchema.getRecordQuest());
			tLDCodeModDB.setOperator(mLDCodeModSchema.getOperator());
			tLDCodeModDB.setModifyDate(theCurrentDate);
			tLDCodeModDB.setSendObj(mLDCodeModSchema.getSendObj());
			if(mOperate.equals("UPDATE")){
				map.put(tLDCodeModDB.getSchema(), "UPDATE");
			}else if(mOperate.equals("DELETE")){
				map.put(tLDCodeModDB.getSchema(), "DELETE");
			}else{
				CError.buildErr(this, "前台传入操作类型失败");
				return false;
			}
			//存入轨迹表
			LBCodeModSchema tLBCodeModSchema = new LBCodeModSchema();
			String tSerielNo = PubFun1.CreateMaxNo("QuestSerielNo", 10);
			tLBCodeModSchema.setSerialNo(tSerielNo);
			tLBCodeModSchema.setCodeType(tLDCodeModDB.getCodeType());
			tLBCodeModSchema.setCode(tLDCodeModDB.getCode());
			tLBCodeModSchema.setCodeName(tLDCodeModDB.getCodeName());
			tLBCodeModSchema.setCont(tLDCodeModDB.getCont());
			tLBCodeModSchema.setOperator(tLDCodeModDB.getOperator());
			tLBCodeModSchema.setModifyDate(theCurrentDate);
			tLBCodeModSchema.setRecordQuest(tLDCodeModDB.getRecordQuest());
			tLBCodeModSchema.setSendObj(tLDCodeModDB.getSendObj());
			map.put(tLBCodeModSchema, "INSERT");
		}
		return true;
	}
	
	private boolean prepareOutputData(){
		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		tTransferData.setNameAndValue("Code", mCode);
		mResult.add(tTransferData);
//		mInputData
		return true;
	}
	
	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
}
