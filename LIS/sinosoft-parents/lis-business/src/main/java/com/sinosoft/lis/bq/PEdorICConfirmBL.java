package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOEngBonusPolDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEngBonusPolDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPInsuredRelatedDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOEngBonusPolSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEngBonusPolSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredRelatedSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vbl.LPInsuredBLSet;
import com.sinosoft.lis.vbl.LPPolBLSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredRelatedSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 被保人重要资料变更保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorICConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorICConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPContSet CM_LPContSet = new LPContSet();
	private LPInsuredSet CM_Delete_LPInsuredSet = new LPInsuredSet();
	private LPInsuredSet CM_LPInsuredSet = new LPInsuredSet();
	
	private LPInsuredSet IC_INSERT_LPInsuredSet =new LPInsuredSet();
	private LCInsuredSet IC_INSERT_LCInsuredSet =new LCInsuredSet();
	private LPAppntSet CM_LPAppntSet = new LPAppntSet();
	private LPPolSet CM_LPPolSet = new LPPolSet();

	private LCPolBL mLCPolBL = new LCPolBL();
	private LCPolBLSet mLCPolBLSet = new LCPolBLSet();
	private LPPolBL mLPPolBL = new LPPolBL();
	private LPPolBLSet mLPPolBLSet = new LPPolBLSet();
	private LPInsuredBLSet mLPInsuredBLSet = new LPInsuredBLSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;
	private String mCMEdorNo = null;
	/** 字段映射类 */
	private Reflections tRef = new Reflections();
	
	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();

	private String strCurrentTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorICConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
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

		// 数据操作
		if (!dealData()) {
			return false;
		}

		// 输出数据准备
		if (!prepareOutputData()) {
			return false;
		}

		this.setOperate("CONFIRM||IC");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据查询业务处理(queryData())
	 * 
	 */

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mCMEdorNo = mLPEdorItemSchema.getStandbyFlag1();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		return true;
	}


	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		
		//采用新的方式进行 CP 互换
	    //String[] chgTables = {"LCPol","LCDuty","LCPrem","LCGet","LCCont","LCAppnt","LCInsured"};
		//lpinsured表中主键insuredno可能被修改为新客户号，不适用于此新的CP互换方法，需要单独处理
		String[] chgTables = {"LCPol","LCDuty","LCGet","LCCont","LCAppnt"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());	
	    
	    //单独处理LCInsured
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setEdorNo(mEdorNo);
		tLPInsuredSchema.setEdorType("IC");
		tLPInsuredSchema.setContNo(mContNo);

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setSchema(tLPInsuredSchema);
		tLPInsuredSet = tLPInsuredDB.query();
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			aLPInsuredSchema = tLPInsuredSet.get(j);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLPInsuredSchema = new LPInsuredSchema();

			// 转换成保单个人信息。
			tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
			tLCInsuredSchema.setModifyDate(strCurrentDate);
			tLCInsuredSchema.setModifyTime(strCurrentTime);

			LCInsuredDB aLCInsuredDB = new LCInsuredDB();
			aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
			if (aLCInsuredDB.query().size()==0) {
				mErrors.copyAllErrors(aLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCInsuredSchema = aLCInsuredDB.query().get(1);
			tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
			tLPInsuredSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
			tLPInsuredSchema
					.setEdorType(aLPInsuredSchema.getEdorType());
			
			IC_INSERT_LPInsuredSet.add(tLPInsuredSchema);
			IC_INSERT_LCInsuredSet.add(tLCInsuredSchema);
		}
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("delete from  lpinsured  where contno='?mContNo?' and edortype='IC' and edorno='?mEdorNo?' ");
		sbv1.put("mContNo", mContNo);
		sbv1.put("mEdorNo", mEdorNo);
		mMap.put(sbv1, "DELETE");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("delete from  lCinsured  where contno='?mContNo?'  ");
		sbv2.put("mContNo", mContNo);
		mMap.put(sbv2, "DELETE");
		mMap.put(IC_INSERT_LPInsuredSet, "INSERT");
		mMap.put(IC_INSERT_LCInsuredSet, "INSERT");
		
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("update lpconttempinfo  set state='1',ICEdorNo='?ICEdorNo?',ICEdorAcceptNo='?ICEdorAcceptNo?',modifydate='?strCurrentDate?',modifytime='?strCurrentTime?' where ContNo = '?ContNo?' and edorno='?edorno?'");
		sbv3.put("ICEdorNo", mLPEdorItemSchema.getEdorNo());
		sbv3.put("ICEdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		sbv3.put("strCurrentDate", strCurrentDate);
		sbv3.put("strCurrentTime", strCurrentTime);
		sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv3.put("edorno", mLPEdorItemSchema.getStandbyFlag1());
		mMap.put(sbv3, "UPDATE");

		String delLJSGet = " delete from LJSGet where GetNoticeNo in "
			+ " ( select GetNoticeNo from LJSGetDraw where PolNo in "
			+ "   ( select PolNo from LPPol where EdorNo = '?EdorNo?'))";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(delLJSGet);
		sbv4.put("EdorNo", mLPEdorItemSchema.getEdorNo());
	   String delLJSGetDraw = " delete from LJSGetDraw where PolNo in "
			+ " ( select PolNo from LPPol where EdorNo = '?EdorNo?')";
	   SQLwithBindVariables sbv5=new SQLwithBindVariables();
	   sbv5.sql(delLJSGetDraw);
	   sbv5.put("EdorNo", mLPEdorItemSchema.getEdorNo());
	    mMap.put(sbv4, "DELETE");
	    mMap.put(sbv5, "DELETE");
	    
	    Reflections tReflections = new Reflections();
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
		mMap.put(aLPPremSet,"UPDATE");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		//增加处理CM保全中的相关p表信息
		if(!prepareCMData())
		{
			CError.buildErr(this, "保全确认时处理CM项目中备份表信息出错!");
			return false;
		}
		
		return true;
	}

	private boolean prepareCMData()
	{

		LPContSchema xLPContSchema = new LPContSchema();
		LCContSchema xLCContSchema = new LCContSchema();
		
		LPContDB xLPContDB = new LPContDB();
		xLPContDB.setEdorNo(mCMEdorNo);
		xLPContDB.setEdorType("CM");
		xLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if(!xLPContDB.getInfo())
		{
			mErrors.copyAllErrors(xLPContDB.mErrors);
			mErrors.addOneError(new CError("查询CM保全LPCONT信息失败！"));
			return false;
		}
		xLPContSchema = xLPContDB.getSchema();
		
		LCContDB aLCContDB = new LCContDB();
		aLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!aLCContDB.getInfo()) {
			mErrors.copyAllErrors(aLCContDB.mErrors);
			mErrors.addOneError(new CError("查询保单信息失败！"));
			return false;
		}
		
		// 转换成保单个人信息。
		tRef.transFields(xLPContSchema, aLCContDB.getSchema());
		xLPContSchema.setModifyDate(strCurrentDate);
		xLPContSchema.setModifyTime(strCurrentTime);
		
		CM_LPContSet.add(xLPContSchema);

		mMap.put(CM_LPContSet, "DELETE&INSERT");

		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mCMEdorNo);
		tLPPolDB.setEdorType("CM");
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();
		String tAppntNo = tLPPolSet.get(1).getAppntNo();
		String tInsuredNo = tLPPolSet.get(1).getInsuredNo();
		

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPPolSet.get(i).getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mCMEdorNo);
			tLPPolSchema.setEdorType("CM");
			CM_LPPolSet.add(tLPPolSchema);
		}
		mMap.put(CM_LPPolSet, "DELETE&INSERT");
		

		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setEdorNo(mCMEdorNo);
		tLPInsuredSchema.setEdorType("CM");
		tLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setSchema(tLPInsuredSchema);
		tLPInsuredSet = tLPInsuredDB.query();
		//lpinsured表主键包含insuredno，而此字段有可能已经是合并后的新客户号，所以此处删除和插入要分开。而lpappnt主键不包含appntno，因此可以用delete&insert
		CM_Delete_LPInsuredSet = tLPInsuredSet;
		mMap.put(CM_Delete_LPInsuredSet, "DELETE");
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			aLPInsuredSchema = tLPInsuredSet.get(j);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLPInsuredSchema = new LPInsuredSchema();

			LCInsuredDB aLCInsuredDB = new LCInsuredDB();
			aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
			aLCInsuredDB.setInsuredNo(tInsuredNo);
			if (!aLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(aLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCInsuredSchema = aLCInsuredDB.getSchema();
			tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
			tLPInsuredSchema.setEdorNo(mCMEdorNo);
			tLPInsuredSchema.setEdorType("CM");
			
			CM_LPInsuredSet.add(tLPInsuredSchema);
		}
		mMap.put(CM_LPInsuredSet, "INSERT");

		// 得到当前客户资料
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LPAppntSet tLPAppntSet = new LPAppntSet();

		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setEdorNo(mCMEdorNo);
		tLPAppntSchema.setEdorType("CM");
		tLPAppntSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setSchema(tLPAppntSchema);
		tLPAppntSet = tLPAppntDB.query();
		for (int j = 1; j <= tLPAppntSet.size(); j++) {
			aLPAppntSchema = tLPAppntSet.get(j);
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			tLPAppntSchema = new LPAppntSchema();

			LCAppntDB aLCAppntDB = new LCAppntDB();
			aLCAppntDB.setContNo(aLPAppntSchema.getContNo());
			aLCAppntDB.setAppntNo(tAppntNo);
			if (!aLCAppntDB.getInfo()) {
				mErrors.copyAllErrors(aLCAppntDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCAppntSchema = aLCAppntDB.getSchema();
			tRef.transFields(tLPAppntSchema, aLCAppntSchema);
			tLPAppntSchema.setEdorNo(aLPAppntSchema.getEdorNo());
			tLPAppntSchema.setEdorType(aLPAppntSchema.getEdorType());
			
			CM_LPAppntSet.add(tLPAppntSchema);
		}
		mMap.put(CM_LPAppntSet, "DELETE&INSERT");
		
		return true;
    
	}
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
