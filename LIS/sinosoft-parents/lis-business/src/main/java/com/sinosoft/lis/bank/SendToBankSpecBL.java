package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LYSendToBankSpecBDB;
import com.sinosoft.lis.db.LYSendToBankSpecDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LYSendToBankSpecBSchema;
import com.sinosoft.lis.schema.LYSendToBankSpecSchema;
import com.sinosoft.lis.vschema.LYSendToBankSpecBSet;
import com.sinosoft.lis.vschema.LYSendToBankSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 查询批次号程序
 * </p>
 * <p>
 * Description: 根据合同号先从lysendtobankspec表查询数据 如有往lysendtobankspecb里挪动已存在的数据 然后再往lysendtobankspec表里插数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003-04-02
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wuxw
 * @version 1.0
 * @function show BillNo that was selected from LYBankLog Business Logic Layer
 */
public class SendToBankSpecBL {
private static Logger logger = Logger.getLogger(SendToBankSpecBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private String mContNo;
	private String mTypicalPayType;
	private String mOperate;
	private String mNoType;
	
	private GlobalInput mG = new GlobalInput();
	//把schema和对应的操作（save，update，delete）等都放入map中最后放到mInputData中往后台传数据
	private MMap map = new MMap();
	
	private LYSendToBankSpecSchema lYSendToBankSpecSchema = new LYSendToBankSpecSchema();
//	private LYSendToBankSpecSchema lYSendToBankSpecInsertSchema = new LYSendToBankSpecSchema();
	private LYSendToBankSpecSet lYSendToBankSpecSet = new LYSendToBankSpecSet();
//	private LYSendToBankSpecBSchema lYSendToBankSpecBSchema = new LYSendToBankSpecBSchema();
	private LYSendToBankSpecBSet lYSendToBankSpecBSet = new LYSendToBankSpecBSet();
//	private LYSendToBankSpecDB lYSendToBankSpecDB = new LYSendToBankSpecDB();
	private LYSendToBankSpecBDB lYSendToBankSpecBDB = new LYSendToBankSpecBDB();
	private String serialNo = "";
//	private String t_ComCode;
//	private String strTFFlag = "";
//	private String strXQFlag = "";
//	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();

	public SendToBankSpecBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return: true or false
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		//logger.debug("mInputData:"+mInputData);
		this.mOperate = cOperate;
		
		if (!getInputData(cInputData))
			return false;
		if (!dealData())
			return false;
		if(!prepareOutputData()){
			return false;
		}
		logger.debug("开始插入数据");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}

		mInputData = null;
		return true;
	}

	private boolean dealData() {
		try{
		//从lysendtobankspec表中查询数据
			String sql = "select * from lysendtobankspec where contno = '"+"?contno?"+"'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("contno", mContNo);
			 LYSendToBankSpecDB lYSendToBankSpecDB = new LYSendToBankSpecDB();
			lYSendToBankSpecSet = lYSendToBankSpecDB.executeQuery(sqlbv2);
			 serialNo = PubFun1.CreateMaxNo("1", 20);
			//在新增银行转账特约表里有数据的话先往历史表是查数据 再从特约表里删除数据 再往特约表里插新数据
			if(lYSendToBankSpecSet.size() > 0 ){
					lYSendToBankSpecSchema = lYSendToBankSpecSet.get(1)	;
					LYSendToBankSpecBSchema lYSendToBankSpecBSchema = new LYSendToBankSpecBSchema();
					//往历史表插数据
					lYSendToBankSpecBSchema.setSerialNo(serialNo);
					lYSendToBankSpecBSchema.setContNo(lYSendToBankSpecSchema.getContNo());
					lYSendToBankSpecBSchema.setComCode(lYSendToBankSpecSchema.getComCode());
					lYSendToBankSpecBSchema.setNoType(lYSendToBankSpecSchema.getNoType());
					lYSendToBankSpecBSchema.setMakeDate(lYSendToBankSpecSchema.getMakeDate());
					lYSendToBankSpecBSchema.setMakeTime(lYSendToBankSpecSchema.getMakeTime());
					lYSendToBankSpecBSchema.setModifyDate(lYSendToBankSpecSchema.getModifyDate());
					lYSendToBankSpecBSchema.setModifyTime(lYSendToBankSpecSchema.getModifyTime());
					lYSendToBankSpecBSchema.setOperator(lYSendToBankSpecSchema.getOperator());
					
					 map.put(lYSendToBankSpecBSchema, "INSERT");
					
					//lYSendToBankSpecBSet.add(lYSendToBankSpecBSchema);
				//从特约表删除数据
					map.put(lYSendToBankSpecSchema, "DELETE");	
			}
			//在特约表里插入新的数据
			LYSendToBankSpecSchema lYSendToBankSpecInsertSchema = new LYSendToBankSpecSchema();
			lYSendToBankSpecInsertSchema.setContNo(mContNo);
			lYSendToBankSpecInsertSchema.setComCode(mTypicalPayType);
			lYSendToBankSpecInsertSchema.setNoType(mNoType);
			lYSendToBankSpecInsertSchema.setMakeDate(PubFun.getCurrentDate());
			lYSendToBankSpecInsertSchema.setMakeTime(PubFun.getCurrentTime());
			lYSendToBankSpecInsertSchema.setModifyDate(PubFun.getCurrentDate());
			lYSendToBankSpecInsertSchema.setModifyTime(PubFun.getCurrentTime());
			logger.debug(mG.Operator);
			lYSendToBankSpecInsertSchema.setOperator(mG.Operator);
			
		    map.put(lYSendToBankSpecInsertSchema, "INSERT");
		}catch(Exception e){
			CError tError = new CError();
			tError.moduleName = "SendToBankSpecBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! 数据准备不充分";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/***************************************************************************
	 * @function :receive parameter and checkdate
	 * @receive :receive parameter from jsp
	 * @checkdate:judge whether startDate before EndDate
	 * @return true or false
	 **************************************************************************/
	private boolean getInputData(VData cInputData) {
		try{
			if (mOperate.equals("SAVE")) {
				TransferData data = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
				mContNo = (String) data.getValueByName("contNo");
				mTypicalPayType = (String)data.getValueByName("typicalPayType");
				mNoType = (String)data.getValueByName("noType");
				mG =(GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
				
				if(mContNo == null ||"".equals(mContNo)){
//					CError tError =new CError();
//			          tError.moduleName="SendToBankBL";
//			          tError.functionName="getInputData";
//			          tError.errorMessage="没有得到足够的信息！";
//			          this.mErrors .addOneError(tError) ;
					CError.buildErr(this, "保单号为空");
			          return false;
				}
				if(mG == null){
//					CError tError =new CError();
//			          tError.moduleName="SendToBankBL";
//			          tError.functionName="getInputData";
//			          tError.errorMessage="没有得到足够的信息！";
//			          this.mErrors .addOneError(tError) ;
					CError.buildErr(this, "登录信息有误");
			          return false;
				}
				logger.debug("mNoType:"+mNoType);
				if(mNoType == null || "".equals(mNoType)){
//					CError tError =new CError();
//			          tError.moduleName="SendToBankBL";
//			          tError.functionName="getInputData";
//			          tError.errorMessage="没有得到足够的信息！";
//			          this.mErrors .addOneError(tError) ;
					CError.buildErr(this, "业务类型有误");
			          return false;
				}
				
			}else{
				return false;
			}
		}catch(Exception e){
			CError tError = new CError();
		      tError.moduleName = "SendToBankSpecBL";
		      tError.functionName = "getInputData";
		      tError.errorMessage = "接收数据失败!!";
		      this.mErrors .addOneError(tError) ;
		      return false;
		}
		return true;
	}

	/**
	 * @function select BillNo from LYBankLog
	 * @execute different SQL bansd on different OperateFlag return true or
	 *          false
	 */
	
	public boolean estSerialno(String tNotype) {
		int tFirstC = 0;
		int tSecondC = 0;
		String tSql = "select count(*) from lysendtobankspec where contno = "
				+ "?mContNo?" ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mContNo", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		tFirstC = Integer.parseInt(tSSRS.GetText(1, 1));
		logger.debug("=======开始判断是否重复点击==================================");
		try {

			Thread.currentThread().sleep(8 * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		tSSRS = tExeSQL.execSQL(sqlbv);
		tSecondC = Integer.parseInt(tSSRS.GetText(1, 1));
		if (tFirstC != tSecondC) {
			return false;
		}
		return true;
	}
	
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
	}

	public static void main(String[] args) {
		
	}
}
