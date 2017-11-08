package com.sinosoft.lis.maintain;

import com.sinosoft.lis.db.LDUnifyCodeDB;
import com.sinosoft.lis.db.LDUnifyCodeTypeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LDUnifyCodeSchema;
import com.sinosoft.lis.schema.LDUnifyCodeTraceSchema;
import com.sinosoft.lis.schema.LDUnifyCodeTypeSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LDUnifyCodeSet;
import com.sinosoft.lis.vschema.LDUnifyCodeTraceSet;
import com.sinosoft.lis.vschema.LDUnifyCodeTypeSet;
import com.sinosoft.utility.BusConst;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>Title：系统编码管理</p>
 * 
 * <p>Description：系统编码管理</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author 刘锦祥
 * @version 6.5
 */
public class LDUnifyCodeTypeBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 提交数据的容器 */
	private MMap mMMap = new MMap();
	/** 操作变量 */
	private String mOperate;
	
	/** 全局变量 */
	private GlobalInput mGlobalInput;
	private String mManagecom;
	private String mOperator;
	
	private ExeSQL mExeSQL = new ExeSQL();
	private StringBuffer mStrBuff = new StringBuffer();
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	/** 系统统一编码类型表 */
	private LDUnifyCodeTypeSchema mLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
	/** 获取数据库中系统统一编码类型表 */
	private LDUnifyCodeTypeSchema moldLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
	/** 系统统一编码表 */
	private LDUnifyCodeSchema mLDUnifyCodeSchema = new LDUnifyCodeSchema();
	/** 获取数据库中系统统一编码表 */
	private LDUnifyCodeSchema moldLDUnifyCodeSchema = new LDUnifyCodeSchema();
	/** 系统统一编码轨迹表 */
	private LDUnifyCodeTraceSchema mLDUnifyCodeTraceSchema = new LDUnifyCodeTraceSchema();
	
	public boolean submitData(VData cInputData, String cOperate) {
		
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		
		if (!checkData()) {
			return false;
		}
		
		if (!dealData()) {
			return false;
		}
		
		if (!saveData()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象

	 * @param cInputData 传入数据对象
	 * @param cOperate 操作类型
	 * @return 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		
		mInputData = (VData)cInputData.clone();
		mOperate = cOperate;
		
		mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
		mManagecom = mGlobalInput.ManageCom;
		mOperator = mGlobalInput.Operator;
		
		mLDUnifyCodeTypeSchema = (LDUnifyCodeTypeSchema)mInputData.getObjectByObjectName("LDUnifyCodeTypeSchema", 0);
		mLDUnifyCodeSchema = (LDUnifyCodeSchema)mInputData.getObjectByObjectName("LDUnifyCodeSchema", 0);
		return true;
	}
	
	/**
	 * 数据校验
	 * @return 如果校验失败，则返回false,否则返回true
	 */
	private boolean checkData() {
		//根据系统编码，编码类型编码查询编码类型信息
		if("UPDATE&TYPE".equals(mOperate)||"DELETE&TYPE".equals(mOperate)||"SUBMIT&TYPE".equals(mOperate)) {
			 moldLDUnifyCodeTypeSchema = getOldLDUnifyCodeTypeSchema();
			if(moldLDUnifyCodeTypeSchema==null) {
				buildError("checkData", "查询编码类型信息出错！");
				return false;
			}
		}
		
		//根据系统编码，编码类型,编码查询编码信息
		if("UPDATE".equals(mOperate)||"DELETE".equals(mOperate)||"SUBMIT".equals(mOperate)) {
			
			moldLDUnifyCodeSchema = getOldLDUnifyCodeSchema();
			if(moldLDUnifyCodeSchema==null) {
				buildError("checkData", "查询编码信息出错！");
				return false;
			}
		}else if("INSERT".equals(mOperate)) {
			String selectSql = "select state,maintainflag from ldunifycodetype where syscode='?syscode?' and codetype = '?codetype?' ";
			
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(selectSql);
			sqlbv.put("syscode", mLDUnifyCodeSchema.getSysCode());
			sqlbv.put("codetype", mLDUnifyCodeSchema.getCodeType());
			SSRS tSSRS = mExeSQL.execSQL(sqlbv);
			if(tSSRS.getMaxRow()<=0) {
				buildError("checkData", "不存在对应的编码类型,请到编码类型中维护该编码类型！");
				return false;
			}else {
				String tState = tSSRS.GetText(1, 1);
				String tMaintainflag = tSSRS.GetText(1, 2);
				if(tState==null||"".equals(tState)||tMaintainflag==null||"".equals(tMaintainflag)) {
					buildError("checkData", "查询编码类型错误！");
					return false;
				}else{
					if(BusConst.STATE_VALID.equals(tState)&&BusConst.MAINTAINFLAG_FLASE.equals(tMaintainflag)) {
						buildError("checkData", "选择的编码类型，已生效并且不能维护，不能新增编码信息！");
						return false;
					}
				}
			}
		}
		
		if("SUBMIT".equals(mOperate)) {
			String selectSql = "select state,maintainflag from ldunifycodetype where syscode='?syscode?' and codetype = '?codetype?' ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(selectSql);
			sqlbv1.put("syscode", mLDUnifyCodeSchema.getSysCode());
			sqlbv1.put("codetype", mLDUnifyCodeSchema.getCodeType());
			SSRS tSSRS = mExeSQL.execSQL(sqlbv1);
			if(tSSRS.getMaxRow()<=0) {
				buildError("checkData", "不存在对应的编码类型,请到编码类型中维护该编码类型！");
				return false;
			}else {
				String tState = tSSRS.GetText(1, 1);
				String tMaintainflag = tSSRS.GetText(1, 2);
				if(BusConst.STATE_NoVALID.equals(tState)) {
					buildError("checkData", "选择的编码类型未生效，不能单独提交编码，请提交编码类型！");
					return false;
				}else if(BusConst.MAINTAINFLAG_FLASE.equals(tMaintainflag))  {
					buildError("checkData", "选择的编码类型，已生效并且不能维护，不能新增编码信息！");
					return false;
				}
			}
		}
		
		if(mOperate.endsWith("TYPE")) {
			if(BusConst.STATE_VALID.equals(mLDUnifyCodeTypeSchema.getState())) {
				buildError("checkData", "编码类型已经生效，不能执行该操作！");
				return false;
			}
		}else {
			if(BusConst.STATE_VALID.equals(mLDUnifyCodeSchema.getState())) {
				buildError("checkData", "编码类型已经生效，不能执行该操作！");
				return false;
			}
			
		}

		
		return true;
	}
	
	/**
	 * 业务处理
	 * @return 如果处理失败，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		if ("INSERT&TYPE".equals(mOperate)) {//新增编码类型表
			
			mLDUnifyCodeTypeSchema.setState(BusConst.STATE_NoVALID);
			mLDUnifyCodeTypeSchema.setManageCom(mManagecom);
			mLDUnifyCodeTypeSchema.setOperator(mOperator);
			mLDUnifyCodeTypeSchema.setMakeDate(mCurrentDate);
			mLDUnifyCodeTypeSchema.setMakeTime(mCurrentTime);
			mLDUnifyCodeTypeSchema.setModifyDate(mCurrentDate);
			mLDUnifyCodeTypeSchema.setModifyTime(mCurrentTime);
			
			mMMap.put(mLDUnifyCodeTypeSchema, "INSERT");
		}else if("UPDATE&TYPE".equals(mOperate)){//修改编码类型表
			
			mLDUnifyCodeTypeSchema.setState(moldLDUnifyCodeTypeSchema.getState());
			mLDUnifyCodeTypeSchema.setMakeDate(moldLDUnifyCodeTypeSchema.getMakeDate());
			mLDUnifyCodeTypeSchema.setMakeTime(moldLDUnifyCodeTypeSchema.getMakeTime());
			mLDUnifyCodeTypeSchema.setManageCom(mManagecom);
			mLDUnifyCodeTypeSchema.setOperator(mOperator);
			mLDUnifyCodeTypeSchema.setModifyDate(mCurrentDate);
			mLDUnifyCodeTypeSchema.setModifyTime(mCurrentTime);
			
			mMMap.put(mLDUnifyCodeTypeSchema, "UPDATE");
		}else if("DELETE&TYPE".equals(mOperate)){//删除编码类型表
			String delteSql = "delete from ldunifycodetype where syscode='?syscode?' and codetype='?codetype?'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(delteSql);
			sqlbv2.put("syscode", mLDUnifyCodeTypeSchema.getSysCode());
			sqlbv2.put("codetype", mLDUnifyCodeTypeSchema.getCodeType());
			mMMap.put(sqlbv2, "DELETE");
			
			//删除ldunifycode时写入轨迹表中
			productCodeTrace(mLDUnifyCodeSchema.getSysCode(),mLDUnifyCodeSchema.getCodeType(),null);
			//删除编码信息表
			StringBuffer deleteSql = new StringBuffer();
			deleteSql.append("delete from ldunifycode where ");
			deleteSql.append("syscode = '?syscode?' ");
			deleteSql.append("and codetype = '?codetype?' ");
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(deleteSql.toString());
			sqlbv3.put("syscode", mLDUnifyCodeTypeSchema.getSysCode());
			sqlbv3.put("codetype", mLDUnifyCodeTypeSchema.getCodeType());
			mMMap.put(sqlbv3, "DELETE");
			
			
		}else if("SUBMIT&TYPE".equals(mOperate)){//提交编码类型表
			
			//提交该编码类型下所有的编码
			if(!submitAllCode(moldLDUnifyCodeTypeSchema.getSysCode(),moldLDUnifyCodeTypeSchema.getCodeType(),null)) {
				return false;
			}
			
			//更新编码类型状态为已生效
			moldLDUnifyCodeTypeSchema.setState(BusConst.STATE_VALID);
			moldLDUnifyCodeTypeSchema.setOperator(mOperator);
			moldLDUnifyCodeTypeSchema.setModifyDate(mCurrentDate);
			moldLDUnifyCodeTypeSchema.setModifyTime(mCurrentTime);
			mMMap.put(moldLDUnifyCodeTypeSchema, "UPDATE");
			
			//更新编码状态未已生效
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("update ldunifycode set state ='?state?', ");
			updateSql.append("modifydate ='?mCurrentDate?' ,modifytime='?mCurrentTime?',");
			updateSql.append("operator ='?mOperator?' ");
			updateSql.append("where syscode = '?syscode?' ");
			updateSql.append("and codetype = '?codetype?' ");
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(updateSql.toString());
			sqlbv4.put("state", BusConst.STATE_VALID);
			sqlbv4.put("mCurrentDate", mCurrentDate);
			sqlbv4.put("mCurrentTime", mCurrentTime);
			sqlbv4.put("mOperator", mOperator);
			sqlbv4.put("syscode", mLDUnifyCodeTypeSchema.getSysCode());
			sqlbv4.put("codetype", mLDUnifyCodeTypeSchema.getCodeType());
			mMMap.put(sqlbv4, "UPDATE");
		
		}else if("INSERT".equals(mOperate)) {//新增编码表
			mLDUnifyCodeSchema.setState(BusConst.STATE_NoVALID);
			mLDUnifyCodeSchema.setManageCom(mManagecom);
			mLDUnifyCodeSchema.setOperator(mOperator);
			mLDUnifyCodeSchema.setMakeDate(mCurrentDate);
			mLDUnifyCodeSchema.setMakeTime(mCurrentTime);
			mLDUnifyCodeSchema.setModifyDate(mCurrentDate);
			mLDUnifyCodeSchema.setModifyTime(mCurrentTime);
			
			mMMap.put(mLDUnifyCodeSchema, "INSERT");
			
		}else if("UPDATE".equals(mOperate)){//修改编码表
			
			mLDUnifyCodeSchema.setState(moldLDUnifyCodeSchema.getState());
			mLDUnifyCodeSchema.setMakeDate(moldLDUnifyCodeSchema.getMakeDate());
			mLDUnifyCodeSchema.setMakeTime(moldLDUnifyCodeSchema.getMakeTime());
			mLDUnifyCodeSchema.setManageCom(mManagecom);
			mLDUnifyCodeSchema.setOperator(mOperator);
			mLDUnifyCodeSchema.setModifyDate(mCurrentDate);
			mLDUnifyCodeSchema.setModifyTime(mCurrentTime);
			
			mMMap.put(mLDUnifyCodeSchema, "UPDATE");
		}else if("DELETE".equals(mOperate)){//删除编码表
			
			//删除ldunifycode时写入轨迹表中
			productCodeTrace(mLDUnifyCodeSchema.getSysCode(),mLDUnifyCodeSchema.getCodeType(),mLDUnifyCodeSchema.getCode());
			
			//删除编码信息表
			StringBuffer deleteSql = new StringBuffer();
			deleteSql.append("delete from ldunifycode where ");
			deleteSql.append("syscode = '?syscode?' ");
			deleteSql.append("and codetype = '?codetype?' ");
			deleteSql.append("and code = '?code?'");
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(deleteSql.toString());
			sqlbv5.put("syscode", mLDUnifyCodeSchema.getSysCode());
			sqlbv5.put("codetype", mLDUnifyCodeSchema.getCodeType());
			sqlbv5.put("code", mLDUnifyCodeSchema.getCode());
			mMMap.put(sqlbv5, "DELETE");
		}else if("SUBMIT".equals(mOperate)){//提交编码表
			
			//提交编码数据
			if(!submitAllCode(mLDUnifyCodeSchema.getSysCode(),mLDUnifyCodeSchema.getCodeType(),mLDUnifyCodeSchema.getCode())) {
				buildError("dealData", "数据源链接未创建或数据源链接失败！");
				return false;
			}
			//修改编码信息表
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("update ldunifycode set state ='?state?', ");
			updateSql.append("modifydate ='?mCurrentDate?' ,modifytime='?mCurrentTime?',");
			updateSql.append("operator ='?mOperator?' ");
			updateSql.append("where syscode = '?syscode?' ");
			updateSql.append("and codetype = '?codetype?' ");
			updateSql.append("and code = '?code?'");
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(updateSql.toString());
			sqlbv6.put("state", BusConst.STATE_VALID);
			sqlbv6.put("mCurrentDate", mCurrentDate);
			sqlbv6.put("mCurrentTime", mCurrentTime);
			sqlbv6.put("mOperator", mOperator);
			sqlbv6.put("syscode", mLDUnifyCodeSchema.getSysCode());
			sqlbv6.put("codetype", mLDUnifyCodeSchema.getCodeType());
			sqlbv6.put("code", mLDUnifyCodeSchema.getCode());
			mMMap.put(sqlbv6, "UPDATE");
		
		}else {
			buildError("dealData", "没有对应的操作类型！");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 提交数据
	 * @return 如果提交数据失败，则返回false,否则返回true
	 */
	private boolean saveData() {
		
		mInputData.clear();
		mInputData.add(mMMap);
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, null)) {
			buildError("saveData", "提交数据失败！");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 根据提交编码类型下的编码
	 * @param tSysCode 系统编码
	 * @param tCodeType编码类型
	 * @param tCode编码
	 * @return
	 */
	private boolean submitAllCode(String tSysCode,String tCodeType,String tCode){
		boolean tResult = true;
		
		//查询出编码类型中所有的编码
		LDUnifyCodeSet tLDUnifyCodeSet = new LDUnifyCodeSet();
		LDUnifyCodeDB tLDUnifyCodeDB = new LDUnifyCodeDB();
		tLDUnifyCodeDB.setSysCode(tSysCode);
		tLDUnifyCodeDB.setCodeType(tCodeType);
		if(tCode!=null) {
			tLDUnifyCodeDB.setCode(tCode);
		}
		tLDUnifyCodeSet = tLDUnifyCodeDB.query();
		if(tLDUnifyCodeSet.size()<=0) {
			buildError("SubmitAllCode", "编码类型下没有编码信息，请维护！");
			return false;
		}else {
			//将所有未提交编码插入到ldcode中
			LDCodeSet tLDCodeSet = new LDCodeSet();
			for(int index=1;index<=tLDUnifyCodeSet.size();index++) {
				LDUnifyCodeSchema tLDUnifyCodeSchema= tLDUnifyCodeSet.get(index);
				if(BusConst.STATE_NoVALID.equals(tLDUnifyCodeSchema.getState())) {
					LDCodeSchema tLDCodeSchema = new LDCodeSchema();
					tLDCodeSchema.setCodeType(tLDUnifyCodeSchema.getCodeType());
					tLDCodeSchema.setCode(tLDUnifyCodeSchema.getCode());
					tLDCodeSchema.setCodeName(tLDUnifyCodeSchema.getCodeName());
					tLDCodeSchema.setCodeAlias(tLDUnifyCodeSchema.getCodeAlias());
					tLDCodeSchema.setComCode(tLDUnifyCodeSchema.getComCode());
					tLDCodeSchema.setOtherSign(tLDUnifyCodeSchema.getOtherSign());
					tLDCodeSet.add(tLDCodeSchema);
				}
			}
			//获得系统编码
			if(SysConst.SYSNAME.equals(tSysCode)) {
				mMMap.put(tLDCodeSet, "INSERT");
			}else {
				MMap tMMap   = new MMap();
				tMMap.put(tLDCodeSet, "INSERT");
				VData tVData = new VData();
				tVData.add(tMMap);
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(tVData, null)) {
					buildError("saveData", "提交数据失败！");
					return false;
				}
			}
			
			
		}
		return true;
		
	}
	
	/**
	 * 生成ldunifycode轨迹表
	 * @return
	 */
	private Boolean productCodeTrace(String tSysCode,String tCodeType,String tCode) {
		//查询出编码类型中所有的编码
		LDUnifyCodeSet tLDUnifyCodeSet = new LDUnifyCodeSet();
		LDUnifyCodeDB tLDUnifyCodeDB = new LDUnifyCodeDB();
		tLDUnifyCodeDB.setSysCode(tSysCode);
		tLDUnifyCodeDB.setCodeType(tCodeType);
		if(tCode!=null) {
			tLDUnifyCodeDB.setCode(tCode);
		}
		tLDUnifyCodeSet = tLDUnifyCodeDB.query();
		if(tLDUnifyCodeSet==null||tLDUnifyCodeSet.size()==0) {
			return false;
		}
		LDUnifyCodeTraceSet tLDUnifyCodeTraceSet = new LDUnifyCodeTraceSet();
		for(int index=1;index<=tLDUnifyCodeSet.size();index++) {
			LDUnifyCodeSchema tLDUnifyCodeSchema = tLDUnifyCodeSet.get(index);
			//生成序列号
			String tCodeTraceNo = PubFun1.CreateMaxNo("CodeTraceNo", 10);
			LDUnifyCodeTraceSchema tLDUnifyCodeTraceSchema = new LDUnifyCodeTraceSchema();
			tLDUnifyCodeTraceSchema.setCodeTraceNo(tCodeTraceNo);
			tLDUnifyCodeTraceSchema.setSysCode(tLDUnifyCodeSchema.getSysCode());
			tLDUnifyCodeTraceSchema.setCodeType(tLDUnifyCodeSchema.getCodeType());
			tLDUnifyCodeTraceSchema.setCode(tLDUnifyCodeSchema.getCode());
			tLDUnifyCodeTraceSchema.setCodeName(tLDUnifyCodeSchema.getCodeName());
			tLDUnifyCodeTraceSchema.setCodeAlias(tLDUnifyCodeSchema.getCodeAlias());
			tLDUnifyCodeTraceSchema.setComCode(tLDUnifyCodeSchema.getComCode());
			tLDUnifyCodeTraceSchema.setOtherSign(tLDUnifyCodeSchema.getOtherSign());
			tLDUnifyCodeTraceSchema.setManageCom(tLDUnifyCodeSchema.getManageCom());
			tLDUnifyCodeTraceSchema.setState(BusConst.STATE_NoVALID);
			tLDUnifyCodeTraceSchema.setOperator(mOperator);
			tLDUnifyCodeTraceSchema.setMakeDate(mCurrentDate);
			tLDUnifyCodeTraceSchema.setMakeTime(mCurrentTime);
			tLDUnifyCodeTraceSchema.setModifyDate(mCurrentDate);
			tLDUnifyCodeTraceSchema.setModifyTime(mCurrentTime);
			tLDUnifyCodeTraceSet.add(tLDUnifyCodeTraceSchema);
		}
	
		mMMap.put(tLDUnifyCodeTraceSet, "INSERT");
		
		return true;
	}
	/**
	 * 根据条件查出编码类型表信息
	 * @return
	 */
	private LDUnifyCodeTypeSchema getOldLDUnifyCodeTypeSchema() {
		LDUnifyCodeTypeSet tLDUnifyCodeTypeSet = new LDUnifyCodeTypeSet();
		LDUnifyCodeTypeDB tLDUnifyCodeTypeDB = new LDUnifyCodeTypeDB();
		tLDUnifyCodeTypeDB.setSysCode(mLDUnifyCodeTypeSchema.getSysCode());
		tLDUnifyCodeTypeDB.setCodeType(mLDUnifyCodeTypeSchema.getCodeType());
		LDUnifyCodeTypeSchema toldLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
		tLDUnifyCodeTypeSet = tLDUnifyCodeTypeDB.query();
		if(tLDUnifyCodeTypeSet.size()==1) {
			toldLDUnifyCodeTypeSchema = tLDUnifyCodeTypeSet.get(1);
		}else {
			return null;
		}
		return toldLDUnifyCodeTypeSchema;
	}
	
	/**
	 * 根据条件查出编码表信息
	 * @return
	 */
	private LDUnifyCodeSchema getOldLDUnifyCodeSchema() {
		LDUnifyCodeSet tLDUnifyCodeSet = new LDUnifyCodeSet();
		LDUnifyCodeDB tLDUnifyCodeDB = new LDUnifyCodeDB();
		tLDUnifyCodeDB.setSysCode(mLDUnifyCodeSchema.getSysCode());
		tLDUnifyCodeDB.setCodeType(mLDUnifyCodeSchema.getCodeType());
		tLDUnifyCodeDB.setCode(mLDUnifyCodeSchema.getCode());
		LDUnifyCodeSchema toldLDUnifyCodeSchema = new LDUnifyCodeSchema();
		tLDUnifyCodeSet = tLDUnifyCodeDB.query();
		if(tLDUnifyCodeSet.size()==1) {
			toldLDUnifyCodeSchema = tLDUnifyCodeSet.get(1);
		}else {
			return null;
		}
		return toldLDUnifyCodeSchema;
	}
	
	/**
	 * 错误构造方法

	 * @param tFunctionName
	 * @param tErrorMessage
	 */
	private void buildError(String tFunctionName, String tErrorMessage) {
		
		CError tCError = new CError();
		tCError.moduleName = "LDUnifyCodeTypeBL";
		tCError.functionName = tFunctionName;
		tCError.errorMessage = tErrorMessage;
		mErrors.addOneError(tCError);
	}
	
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
