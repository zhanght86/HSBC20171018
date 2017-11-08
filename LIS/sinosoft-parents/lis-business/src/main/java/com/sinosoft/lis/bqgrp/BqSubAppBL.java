package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPSubmitApplyDB;
import com.sinosoft.lis.db.LPSubmitApplyTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPSubmitApplySchema;
import com.sinosoft.lis.schema.LPSubmitApplyTraceSchema;
import com.sinosoft.lis.vschema.LPSubmitApplySet;
import com.sinosoft.lis.vschema.LPSubmitApplyTraceSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BqSubAppBL implements BusinessService {
private static Logger logger = Logger.getLogger(BqSubAppBL.class);

	
	 public CErrors mErrors = new CErrors();
	
    private VData mInputData = new VData();
    private MMap mMap = new MMap();
    private VData mResult = new VData();
    private String mOperate;
    private GlobalInput mGlobalInput = new GlobalInput(); 
    private TransferData mTransferData;
    private String mSubNo = "";
    private String mDispType = "";
    private String mNewDispPer = "";//新的承接人
    private String mNewDispDept = "";//新的承接机构
    private LPSubmitApplySet mLPSubmitApplySet = new LPSubmitApplySet();
    private LPSubmitApplyTraceSet mLPSubmitApplyTraceSet = new LPSubmitApplyTraceSet();
    
    private  LPSubmitApplyTraceSchema mLPSubmitApplyTraceSchema_in = new LPSubmitApplyTraceSchema();

    //   C表 to P表
    private Reflections mRef = new Reflections();
    
    private boolean getInputData(VData sugoInputData)
    {
    	mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0); 
    	mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
    	mLPSubmitApplyTraceSchema_in = (LPSubmitApplyTraceSchema) mInputData.getObjectByObjectName(
				"LPSubmitApplyTraceSchema", 0);
    	mSubNo = mLPSubmitApplyTraceSchema_in.getSubNo();
    	mDispType = mLPSubmitApplyTraceSchema_in.getDealType();
    	mNewDispPer = mLPSubmitApplyTraceSchema_in.getDispPer();
    	mNewDispDept = mLPSubmitApplyTraceSchema_in.getDispDept();
    	return true;
    }
    
    public VData getResult()
    {
        return mResult;
    }
    
    public boolean submitData(VData cInputData, String cOperate)
    {
        this.mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;
        if (!getInputData(cInputData))
        {
            return false;
        }
        
    	if (!dealData())
    	{
    		return false;
    	}
    	
    	if (!prepareData())
    	{
    		return false;
    	}
    	
    	PubSubmit tSubmit = new PubSubmit();
    	if (!tSubmit.submitData(mResult, ""))
    	{
    		this.mErrors.copyAllErrors(tSubmit.mErrors);
    		CError tError = new CError();
    		tError.moduleName = "GEdorCADetailBL";
    		tError.functionName = "submitData";
    		tError.errorMessage = "数据提交失败!";
    		this.mErrors.addOneError(tError);
    		return false;
    	}
    	mResult.clear();
    	return true;
    }
    
	private boolean prepareData() {
		mResult.clear();
		mMap.put(mLPSubmitApplySet, "DELETE&INSERT");
		mMap.put(mLPSubmitApplyTraceSet, "DELETE&INSERT");
		mResult.add(mMap);
		return true;
	}

	private boolean dealData() {


		LPSubmitApplyDB tLPSubmitApplyDB  = new LPSubmitApplyDB();
		tLPSubmitApplyDB.setSubNo(mSubNo);
		LPSubmitApplySchema tLPSubmitApplySchema = null;
		if(!tLPSubmitApplyDB.getInfo()){
			CError.buildErr(this, "查询呈报信息失败");
    		return false;
		}   
		tLPSubmitApplySchema = tLPSubmitApplyDB.getSchema();
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select max(serialno)+1 from lpsubmitapplytrace where subno = '"+mSubNo+"'";
		String tSerialNo = tExeSQL.getOneValue(tSQL);
		if (tSerialNo == null || tSerialNo.equals("")) {
			tSerialNo = "1";
		}
		//判断是上面发下来的,还是下面传给我的(根据机构来判断)
		//找出谁呈报给我的,我好还给他
		String  tSql = "select * from LPSubmitApplyTrace where subno='"+mSubNo+"' and rownum = 1 order by serialno desc";
		LPSubmitApplyTraceDB tLPSubmitApplyTraceDB = new LPSubmitApplyTraceDB();
		
		LPSubmitApplyTraceSchema recentSubRecord = tLPSubmitApplyTraceDB.executeQuery(tSql).get(1);//最近一次的呈报记录
		LPSubmitApplyTraceSchema tLPSubmitApplyTraceSchema = null;
		String lastSubPerson = recentSubRecord.getSubPer();//呈报给我的人
		String lastSubDept = recentSubRecord.getManageCom();//呈报给我的机构
		String tDispDept = recentSubRecord.getDispDept();//承接机构 原则上是本次的登陆机构
		String tDispPer = recentSubRecord.getDispPer();//承接人 原则上是Operator
		if("0".equals(mDispType))
		{
			//呈报给新的人,生成新的轨迹记录
			//继续承报的特殊处理
			tLPSubmitApplySchema.setDispPer(mNewDispPer);
			tLPSubmitApplySchema.setSubTimes(tLPSubmitApplySchema.getSubTimes()+1);
			tLPSubmitApplySchema.setDispDept(mNewDispDept);
			tLPSubmitApplySchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setDealType(mDispType);
			tLPSubmitApplySchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplySchema.setSubState("2");
			tLPSubmitApplySchema.setHasDealed("0");
			tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
			
			tLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
			tLPSubmitApplyTraceSchema.setSubNo(mSubNo);
			tLPSubmitApplyTraceSchema.setSerialNo(tSerialNo);
			tLPSubmitApplyTraceSchema.setGrpContNo(tLPSubmitApplySchema.getGrpContNo());
			tLPSubmitApplyTraceSchema.setEdorType(tLPSubmitApplySchema.getEdorType());
			tLPSubmitApplyTraceSchema.setDispDept(tLPSubmitApplySchema.getDispDept());
			tLPSubmitApplyTraceSchema.setDispPer(tLPSubmitApplySchema.getDispPer());
			tLPSubmitApplyTraceSchema.setSubPer(tDispPer);//本次的承接人变为呈报人
			tLPSubmitApplyTraceSchema.setManageCom(tDispDept);//本次的承接机构变为呈报机构
			tLPSubmitApplyTraceSchema.setAppText1(tLPSubmitApplySchema.getAppText1());
			tLPSubmitApplyTraceSchema.setAppText2(tLPSubmitApplySchema.getAppText2());
			tLPSubmitApplyTraceSchema.setSubTimes(tLPSubmitApplySchema.getSubTimes());
			tLPSubmitApplyTraceSchema.setSubState("2");
			tLPSubmitApplyTraceSchema.setSubDesc(tLPSubmitApplySchema.getSubDesc());
			tLPSubmitApplyTraceSchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setDealType(mDispType);
			tLPSubmitApplyTraceSchema.setHasDealed("0");
			tLPSubmitApplyTraceSchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplyTraceSchema.setOperator(mGlobalInput.Operator);
			tLPSubmitApplyTraceSchema.setSubDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeTime(PubFun.getCurrentTime());
			tLPSubmitApplyTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setModifyTime(PubFun.getCurrentTime());
		}else if ("1".equals(mDispType)){
			//呈报处理完成
			//找出下级呈报给自己的,返还给他
			tSQL = "select subper,managecom from LPSubmitApplyTrace where dispper = '"+tDispPer+"' and hasDealed='0' and subno = '"+mSubNo+"' and rownum = 1 order by SerialNo desc";
			SSRS tSSRS = tExeSQL.execSQL(tSQL);
			if(tSSRS.getMaxRow()<=0)
			{
				mErrors.addOneError(new CError("查询下级呈报失败！"));
				return false;
			}
			
			String beyondSubPer = tSSRS.GetText(1, 1);
			String beyondSubDept = tSSRS.GetText(1, 2);
			tLPSubmitApplySchema.setDispPer(beyondSubPer);
			tLPSubmitApplySchema.setDispDept(beyondSubDept);
			tLPSubmitApplySchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setDealType(mDispType);
			tLPSubmitApplySchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplySchema.setSubState("0");
			tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
			
			tLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
			tLPSubmitApplyTraceSchema.setSubNo(mSubNo);
			tLPSubmitApplyTraceSchema.setSerialNo(tSerialNo);
			tLPSubmitApplyTraceSchema.setGrpContNo(tLPSubmitApplySchema.getGrpContNo());
			tLPSubmitApplyTraceSchema.setEdorType(tLPSubmitApplySchema.getEdorType());
			tLPSubmitApplyTraceSchema.setDispDept(tLPSubmitApplySchema.getDispDept());
			tLPSubmitApplyTraceSchema.setDispPer(tLPSubmitApplySchema.getDispPer());
			tLPSubmitApplyTraceSchema.setSubPer(tDispPer);//本次的承接人变为呈报人
			tLPSubmitApplyTraceSchema.setManageCom(tDispDept);//本次的承接机构变为呈报机构
			tLPSubmitApplyTraceSchema.setAppText1(tLPSubmitApplySchema.getAppText1());
			tLPSubmitApplyTraceSchema.setAppText2(tLPSubmitApplySchema.getAppText2());
			tLPSubmitApplyTraceSchema.setSubTimes(tLPSubmitApplySchema.getSubTimes());
			tLPSubmitApplyTraceSchema.setSubState("0");
			tLPSubmitApplyTraceSchema.setSubDesc(tLPSubmitApplySchema.getSubDesc());
			tLPSubmitApplyTraceSchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setHasDealed("1");
			tLPSubmitApplyTraceSchema.setDealType(mDispType);
			tLPSubmitApplyTraceSchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplyTraceSchema.setOperator(mGlobalInput.Operator);
			tLPSubmitApplyTraceSchema.setSubDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeTime(PubFun.getCurrentTime());
			tLPSubmitApplyTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setModifyTime(PubFun.getCurrentTime());
		}else if ("2".equals(mDispType)){
			//不予处理
			tSQL = "select subper,managecom from LPSubmitApplyTrace where dispper = '"+tDispPer+"' and hasDealed='0' and subno = '"+mSubNo+"' and rownum = 1 order by SerialNo desc";
			SSRS tSSRS = tExeSQL.execSQL(tSQL);
			if(tSSRS.getMaxRow()<=0)
			{
				mErrors.addOneError(new CError("查询下级呈报失败！"));
				return false;
			}
			
			String beyondSubPer = tSSRS.GetText(1, 1);
			String beyondSubDept = tSSRS.GetText(1, 2);
			tLPSubmitApplySchema.setDispPer(beyondSubPer);
			tLPSubmitApplySchema.setDispDept(beyondSubDept);
			tLPSubmitApplySchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setDealType(mDispType);
			tLPSubmitApplySchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplySchema.setSubState("4");
			tLPSubmitApplySchema.setHasDealed("1");
			tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
			
			tLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
			tLPSubmitApplyTraceSchema.setSubNo(mSubNo);
			tLPSubmitApplyTraceSchema.setSerialNo(tSerialNo);
			tLPSubmitApplyTraceSchema.setGrpContNo(tLPSubmitApplySchema.getGrpContNo());
			tLPSubmitApplyTraceSchema.setEdorType(tLPSubmitApplySchema.getEdorType());
			tLPSubmitApplyTraceSchema.setDispDept(tLPSubmitApplySchema.getDispDept());
			tLPSubmitApplyTraceSchema.setDispPer(tLPSubmitApplySchema.getDispPer());
			tLPSubmitApplyTraceSchema.setSubPer(tDispPer);//本次的承接人变为承报人
			tLPSubmitApplyTraceSchema.setManageCom(tDispDept);//本次的承接机构变为成报机构
			tLPSubmitApplyTraceSchema.setAppText1(tLPSubmitApplySchema.getAppText1());
			tLPSubmitApplyTraceSchema.setAppText2(tLPSubmitApplySchema.getAppText2());
			tLPSubmitApplyTraceSchema.setSubTimes(tLPSubmitApplySchema.getSubTimes());
			tLPSubmitApplyTraceSchema.setSubState("4");
			tLPSubmitApplyTraceSchema.setSubDesc(tLPSubmitApplySchema.getSubDesc());
			tLPSubmitApplyTraceSchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setDealType(mDispType);
			tLPSubmitApplyTraceSchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplyTraceSchema.setOperator(mGlobalInput.Operator);
			tLPSubmitApplyTraceSchema.setHasDealed("1");
			tLPSubmitApplyTraceSchema.setSubDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setMakeTime(PubFun.getCurrentTime());
			tLPSubmitApplyTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplyTraceSchema.setModifyTime(PubFun.getCurrentTime());
		}else if ("C".equals(mDispType)){
			//确认关闭

			tLPSubmitApplySchema.setDealDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setDealType(mDispType);
			tLPSubmitApplySchema.setDealIdea(mLPSubmitApplyTraceSchema_in.getDealIdea());
			tLPSubmitApplySchema.setSubState("C");
			tLPSubmitApplySchema.setHasDealed("1");
			tLPSubmitApplySchema.setModifyDate(PubFun.getCurrentDate());
			tLPSubmitApplySchema.setModifyTime(PubFun.getCurrentTime());
			
		}
		mLPSubmitApplySet.add(tLPSubmitApplySchema);
		if(tLPSubmitApplyTraceSchema != null){
			mLPSubmitApplyTraceSet.add(tLPSubmitApplyTraceSchema);
		}
		return true;
	}

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			//

		}

		public CErrors getErrors() {
			// TODO Auto-generated method stub
			return this.mErrors;
		}

}
