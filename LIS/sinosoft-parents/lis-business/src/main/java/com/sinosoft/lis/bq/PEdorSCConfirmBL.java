/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个险SC项目保全确认处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @version 1.0
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class PEdorSCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorSCConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**  */

	private LCCSpecSchema mLCCSpecSchema = new LCCSpecSchema();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mEdorNo = null;

	private String mEdorType = null;

	private String mContNo = null;
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private ValidateEdorData2 mValidateEdorData = null;
	public PEdorSCConfirmBL() {
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 业务逻辑处理(实现C,P互换)
		if (!dealData()) {
			return false;
		}
		// 数据准备操作)
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		    mEdorNo = mLPEdorItemSchema.getEdorNo();
		    mEdorType = mLPEdorItemSchema.getEdorType();
		    mContNo = mLPEdorItemSchema.getContNo();
		    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");

		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorSCConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

//		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
//		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
//		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
//		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
//			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
//			mErrors.addOneError(new CError("查询保全项目信息失败！"));
//			return false;
//		}

//		mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		//CP 互换
	    //String[] chgTables = {"LCCont","LCPol","LCDuty","LCPrem","LCGet","LCAppnt","LCInsured"};
		String[] chgTables = {"LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured"};
	    mValidateEdorData.changeData(chgTables);
	    map.add(mValidateEdorData.getMap());
	    
		Reflections tReflections = new Reflections();


		LPCSpecDB tLPCSpecDB = new LPCSpecDB();
		LPCSpecSet tLPCSpecSet = new LPCSpecSet();
		LPCSpecSet rLPCSpecSet = new LPCSpecSet();
		LPCSpecSet dLPCSpecSet = new LPCSpecSet();
		
		LPCSpecSet pLPCSpecSet = new LPCSpecSet();
		LCCSpecSet qLCCSpecSet = new LCCSpecSet();
		// LCCSpecSchema aLCCSpecSchema = new LCCSpecSchema();

		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet rLCCSpecSet = new LCCSpecSet();
		LCCSpecSet cLCCSpecSet = new LCCSpecSet();
		
		String tSQL="select * from LPCSpec where  SpecType not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch') and contno='?contno?' and edorno='?edorno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
        tLPCSpecSet=tLPCSpecDB.executeQuery(sqlbv);
		int tSize=tLPCSpecSet.size();
		if (tLPCSpecSet == null || tLPCSpecSet.size() < 1) {
			mErrors.copyAllErrors(tLPCSpecDB.mErrors);
			mErrors.addOneError(new CError("查询保全特约信息失败！"));
			return false;
		} else {
			for(int k=1;k<=tSize;k++)
			{
		    LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
			LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
		    LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
			mLPCSpecSchema = tLPCSpecSet.get(k);
			tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
			//tLCCSpecDB.setPolNo(mLPEdorItemSchema.getPolNo());
			tLCCSpecDB.setSerialNo(mLPCSpecSchema.getSerialNo());
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.query();
			if (tLCCSpecSet.size() < 1) // 说明是首次增加特约
			{
				tReflections.transFields(tLCCSpecSchema, mLPCSpecSchema);
				tLCCSpecSchema.setMakeDate(mCurrentDate);
				tLCCSpecSchema.setMakeTime(mCurrentTime);
				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
				tLCCSpecSchema.setOperator(mGlobalInput.Operator);
				rLCCSpecSet.add(tLCCSpecSchema);
				dLPCSpecSet.add(mLPCSpecSchema);

			} else  if("del".equals(mLPCSpecSchema.getBackupType()))// 本次是删除特约
			{
				tLCCSpecSchema = tLCCSpecSet.get(1);
				tReflections.transFields(tLPCSpecSchema, tLCCSpecSchema);
				tLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPCSpecSchema.setModifyDate(mCurrentDate);
				tLPCSpecSchema.setModifyTime(mCurrentTime);
				tLPCSpecSchema.setOperator(mGlobalInput.Operator);
				pLPCSpecSet.add(tLPCSpecSchema);


				tReflections.transFields(tLCCSpecSchema, mLPCSpecSchema);
				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
				tLCCSpecSchema.setOperator(mGlobalInput.Operator);
				qLCCSpecSet.add(tLCCSpecSchema);

			}else     // 本次是修改特约
			{
				tLCCSpecSchema = tLCCSpecSet.get(1);
				tReflections.transFields(tLPCSpecSchema, tLCCSpecSchema);
				tLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPCSpecSchema.setModifyDate(mCurrentDate);
				tLPCSpecSchema.setModifyTime(mCurrentTime);
				tLPCSpecSchema.setOperator(mGlobalInput.Operator);
				rLPCSpecSet.add(tLPCSpecSchema);


				tReflections.transFields(tLCCSpecSchema, mLPCSpecSchema);
				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
				tLCCSpecSchema.setOperator(mGlobalInput.Operator);
				cLCCSpecSet.add(tLCCSpecSchema);
			}
				
			}
		}
		//处理核保过来的特约数据
		String tUWSQL="select * from LPCSpec where  SpecType   in ('hx','mn','nf','qt','sj','xi','xu','zx','ch') and contno='?contno?' and edorno='?edorno?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tUWSQL);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		LPCSpecSet qLPCSpecSet=tLPCSpecDB.executeQuery(sbv);
		if (qLPCSpecSet != null && qLPCSpecSet.size() > 0) {
			for(int k=1;k<=qLPCSpecSet.size();k++)
			{
		    LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
		    LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
			mLPCSpecSchema = qLPCSpecSet.get(k);
			tReflections.transFields(tLCCSpecSchema, mLPCSpecSchema);
			tLCCSpecSchema.setMakeDate(mCurrentDate);
			tLCCSpecSchema.setMakeTime(mCurrentTime);
			tLCCSpecSchema.setModifyDate(mCurrentDate);
			tLCCSpecSchema.setModifyTime(mCurrentTime);
			tLCCSpecSchema.setOperator(mGlobalInput.Operator);
			rLCCSpecSet.add(tLCCSpecSchema);
			dLPCSpecSet.add(mLPCSpecSchema);
			}
		} 
		if(cLCCSpecSet.size()>0)
		{
			map.put(cLCCSpecSet, "DELETE&INSERT");
		}
        if(rLPCSpecSet.size()>0)
        {
    		map.put(rLPCSpecSet, "DELETE&INSERT");
        }
        if(rLCCSpecSet.size()>0)
        {
    		map.put(rLCCSpecSet, "DELETE&INSERT");
        }
        if(dLPCSpecSet.size()>0)
        {
    		map.put(dLPCSpecSet, "DELETE");
        }
        if(pLPCSpecSet.size()>0)
        {
    		map.put(pLPCSpecSet, "DELETE&INSERT");
        }
        if(qLCCSpecSet.size()>0)
        {
    		map.put(qLCCSpecSet, "DELETE");
        }
        
        
     // 保费项目表 lcprem - lpprem
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setContNo(mLPEdorItemSchema.getContNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError.buildErr(this, "查询保费项目表失败!");
			return false;
		}
		
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tReflections.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			
			 LCPremDB tLCPremDB = new LCPremDB();
			 tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			 tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			 tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			 
			 boolean tExistsFlag = tLCPremDB.getInfo();
			if (tLCPremDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询保费项目表失败！"));
				return false;
			}
			if (tExistsFlag == true) {
				tReflections.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPPremSet.add(aLPPremSchema);
			}
			 
			 aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		map.put(aLPPremSet,"UPDATE");
		map.put(aLCPremSet, "DELETE&INSERT");
        
	    return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {

//		// 得到当前LPEdorMain保单信息主表的状态，并更新状态为申请确认。
//		mLPEdorMainSchema.setEdorState("0");
//		// 修改保全生效日期至保全确认次日零时 add by ck
//		mLPEdorMainSchema.setEdorValiDate(PubFun.getCurrentDate());
//		mLPEdorMainSchema.setConfDate(PubFun.getCurrentDate());
//		mLPEdorMainSchema.setConfOperator(mGlobalInput.Operator);
//
//		map.put(mLPEdorMainSchema, "UPDATE");
		mResult.clear();
		mResult.add(map);

		return true;
	}

	// 测试用
	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorSCConfirmBL tPEdorSCConfirmBL = new PEdorSCConfirmBL();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tGlobalInput.ManageCom = "001";
		tGlobalInput.Operator = "Admin";
		tLPEdorItemSchema.setEdorNo("86110020040410010703");
		tLPEdorItemSchema.setEdorAppDate("2002-10-30");
		tLPEdorItemSchema.setEdorValiDate("2002-10-31");
		tLPEdorItemSchema.setOperator("Admin");

		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tGlobalInput);

		tPEdorSCConfirmBL.submitData(tInputData, "INSERT||EDORCONFIRM");
	}
}
