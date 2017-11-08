/*
 * <p>ClassName: ComBL </p>
 * <p>Description: LDComBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCalendarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCalendarSchema;
import com.sinosoft.lis.schema.LDCalendarTraceSchema;
import com.sinosoft.lis.vschema.LDCalendarSet;
import com.sinosoft.lis.vschema.LDCalendarTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class CalendarBL {
private static Logger logger = Logger.getLogger(CalendarBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput =new GlobalInput() ;
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	/** 业务处理相关变量 */
	private LDCalendarSet mLDCalendarSet = new LDCalendarSet();
	private LDCalendarSet newLDCalendarSet = new LDCalendarSet();
	private LDCalendarTraceSet mLDCalendarTraceSet = new LDCalendarTraceSet();
	// private LDComSet mLDComSet=new LDComSet();
	public CalendarBL() {
	}

	public static void main(String[] args) {
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
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		
		PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);

            CError tError = new CError();
            tError.moduleName = "ContBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";

            this.mErrors.addOneError(tError);
            return false;
        }

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		for(int i = 1;i<=mLDCalendarSet.size();i++){
			String tDay = mLDCalendarSet.get(i).getCommonDate();
			String tNewFlag = mLDCalendarSet.get(i).getWorkDateFlag();
			LDCalendarDB tLDCalendarDB = new LDCalendarDB();
			tLDCalendarDB.setCommonDate(tDay);
			if(!tLDCalendarDB.getInfo()){
				CError.buildErr(this, "查找日历表日期："+tDay+"失败!");
				return false;
			}
			LDCalendarSchema tLDCalendarSchema = tLDCalendarDB.getSchema();
			String tOldFlag = tLDCalendarSchema.getWorkDateFlag();
			
			tLDCalendarSchema.setWorkDateFlag(tNewFlag);
			newLDCalendarSet.add(tLDCalendarSchema);
			
			LDCalendarTraceSchema tLDCalendarTraceSchema = new LDCalendarTraceSchema();
			String tSQL = "select (case when max(orderno) is not null then max(orderno) else '0' end)+1 from ldcalendartrace where commondate = '"+"?tDay?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("tDay", tDay);
			String tOrderNo = new ExeSQL().getOneValue(sqlbv);
			tLDCalendarTraceSchema.setCommonDate(tDay);
			tLDCalendarTraceSchema.setOrderNo(tOrderNo);
			tLDCalendarTraceSchema.setLastFlag(tOldFlag);
			tLDCalendarTraceSchema.setThisFlag(tNewFlag);
			tLDCalendarTraceSchema.setOperator(mGlobalInput.Operator);
			tLDCalendarTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tLDCalendarTraceSchema.setModifyTime(PubFun.getCurrentTime());
			mLDCalendarTraceSet.add(tLDCalendarTraceSchema);
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mLDCalendarSet =(LDCalendarSet) mInputData.getObjectByObjectName("LDCalendarSet", 0);
		mGlobalInput =(GlobalInput)mInputData.getObjectByObjectName("GlobalInput",0);
		return true;
	}



	private boolean prepareOutputData() {

		map.put(newLDCalendarSet, "DELETE&INSERT");
		map.put(mLDCalendarTraceSet, "DELETE&INSERT");
	  	mResult.clear();
	  	mResult.add(map);
		return true;
	}

}
