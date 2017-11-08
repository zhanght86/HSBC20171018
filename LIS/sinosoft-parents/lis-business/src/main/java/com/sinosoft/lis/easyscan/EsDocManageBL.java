package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.DelPrtBL;
import com.sinosoft.lis.db.ES_DOC_DEFDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.schema.LCDelTraceSchema;
import com.sinosoft.lis.vschema.ES_DOC_DEFSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan单证索引管理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class EsDocManageBL {
private static Logger logger = Logger.getLogger(EsDocManageBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private ES_DOC_MAINSchema mES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	private ES_DOC_PAGESSet mES_DOC_PAGESSet = new ES_DOC_PAGESSet();
	private MMap mmap = new MMap();
	private String mDocID = "";
	private String mPrtNoB = "";
	private String mPrtNoA = "";
	private String mReason = "";
	private String mReasonCode = "";
	private String mSerialNo = "";//通知书流水号
	String mRtnCode = "0";
	String mRtnDesc = "";

	public EsDocManageBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		if(!getInputData(cInputData))
			return false;
		
		if(!dealData(cOperate))
			return false;
		
		
		if(!prepareDate())
			return false;
		
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError.buildErr(this,"数据提交失败!");
			return false;
		}
		//去掉用BLS提交的老方法  改用PubSubmit提交
//		EsDocManageBLS tEsDocManageBLS = new EsDocManageBLS();
//		tEsDocManageBLS.submitData(mInputData, cOperate);
//
//		// 如果有需要处理的错误，则返回
//		if (tEsDocManageBLS.mErrors.needDealError()) {
//			mErrors.copyAllErrors(tEsDocManageBLS.mErrors);
//			tReturn = false;
//		}

//		mResult.clear();
//		mResult = tEsDocManageBLS.getResult();
		mResult = mInputData;

		mInputData = null;

		return tReturn;
	}

	public VData getResult() {
		return mResult;
	}
	
	/**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                              0));
        mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData",
                0));
        mES_DOC_MAINSchema = ((ES_DOC_MAINSchema) cInputData.getObjectByObjectName("ES_DOC_MAINSchema",
                0));
        mES_DOC_PAGESSet = ((ES_DOC_PAGESSet) cInputData.getObjectByObjectName("ES_DOC_PAGESSet",
                0));
        if (mGlobalInput == null)
        {
            CError.buildErr(this, "请重新登录!");

            return false;
        }
        if (mTransferData == null)
        {
            CError.buildErr(this, "TransferData传输数据失败!");

            return false;
        }
        mDocID = ((String) mTransferData.getValueByName("DocID")).trim();
        mPrtNoB = ((String) mTransferData.getValueByName("PrtNoB")).trim();
        mPrtNoA = ((String) mTransferData.getValueByName("PrtNoA")).trim();
        mReason = ((String) mTransferData.getValueByName("Reason"));
        mReasonCode = ((String) mTransferData.getValueByName("ReasonCode"));
        mSerialNo = ((String) mTransferData.getValueByName("serialno"));

        return true;
    }

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String tOperate) 
	{
		if(tOperate.equals("UPDATE||MAIN"))
		{
			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
	        ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
	        if(mPrtNoB==null || "".equals(mPrtNoB.trim())){
	        	CError.buildErr(this, "单证号码为空！");
	        	return false;
	        }
	        tES_DOC_MAINDB.setDocCode(mPrtNoB);
	        tES_DOC_MAINSet = tES_DOC_MAINDB.query();
	        if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size()<1)
	        {
	        	CError.buildErr(this, "查询单证主表失败！");
	        	return false;
	        }
			for(int i=1;i<=tES_DOC_MAINSet.size();i++)
			{
				tES_DOC_MAINSet.get(i).setDocCode(mPrtNoA);
			}
			mInputData.add(tES_DOC_MAINSet);
			mmap.put(tES_DOC_MAINSet, "UPDATE");
		}
		else if(tOperate.equals("DELETE||MAIN"))
		{
			//2010-4-23 如果投保单处于待复核抽检、待异常件处理 需要删除保单信息
			String tCheckSQL = "select 1 from lwmission where missionprop1='"
				+ "?mPrtNoB?" +"' and activityid in ('0000001090','0000001091')";
			String delContFlag = "";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tCheckSQL);
			sqlbv1.put("mPrtNoB", mPrtNoB);
			ExeSQL tExeSQL = new ExeSQL();
			delContFlag = tExeSQL.getOneValue(sqlbv1);
			if("1".equals(delContFlag)){
				if(!delCont()){
					CError.buildErr(this, "删除保单信息失败！");
					return false;
				}
			}
			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
	        ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
	        String sql = "select * from es_doc_main where docid in (select docid from es_doc_relation where bussno='"+"?mPrtNoB?"+"' and bussnotype='11')";
	        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	        sqlbv2.sql(sql);
	        sqlbv2.put("mPrtNoB", mPrtNoB);
	        tES_DOC_MAINSet = tES_DOC_MAINDB.executeQuery(sqlbv2);
	        if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size()<1)
	        {
	        	CError.buildErr(this, "查询该印刷号下单证主表失败！");
	        	return false;
	        }
	        ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
	        ES_DOC_RELATIONSet tES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();
	        sql = "select * from es_doc_relation where docid in (select docid from es_doc_relation where bussno='"+"?mPrtNoB?"+"' and bussnotype='11')";
	        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	        sqlbv3.sql(sql);
	        sqlbv3.put("mPrtNoB", mPrtNoB);
	        tES_DOC_RELATIONSet = tES_DOC_RELATIONDB.executeQuery(sqlbv3);
	        if(tES_DOC_RELATIONSet == null || tES_DOC_RELATIONSet.size()<1)
	        {
	        	CError.buildErr(this, "查询该印刷号下es_doc_relation表失败！");
	        	return false;
	        }
	        
	        ES_DOC_PAGESSet ttES_DOC_PAGESSet = new ES_DOC_PAGESSet();
	        ES_DOC_PAGESDB ttES_DOC_PAGESDB = new ES_DOC_PAGESDB();
			sql = "select * from es_doc_pages where docid in (select docid from es_doc_relation where doccode='"+"?mPrtNoB?"+"' and bussnotype='11')";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("mPrtNoB", mPrtNoB);
			ttES_DOC_PAGESSet = ttES_DOC_PAGESDB.executeQuery(sqlbv4);
			if(ttES_DOC_PAGESSet == null || ttES_DOC_PAGESSet.size()<1)
	        {
	        	CError.buildErr(this, "该印刷号下单证页信息查询失败！");
	        	return false;
	        }
			
			mInputData.remove(1);
			mInputData.add(ttES_DOC_PAGESSet);
			mInputData.add(tES_DOC_MAINSet);
			mInputData.add(tES_DOC_RELATIONSet);
			
			LCDelTraceSchema tLCDelTraceSchema = new LCDelTraceSchema();
			tLCDelTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
	                PubFun.getNoLimit(mGlobalInput.ManageCom)));
			tLCDelTraceSchema.setPrtNo(mPrtNoB);
			tLCDelTraceSchema.setPolNo("all");//单证号码
			tLCDelTraceSchema.setOldOperator(tES_DOC_MAINSet.get(1).getScanOperator());//扫描员
			tLCDelTraceSchema.setOperator(mGlobalInput.Operator);
			tLCDelTraceSchema.setManageCom(mGlobalInput.ManageCom);
			tLCDelTraceSchema.setRiskCode(mReasonCode);//单证删除原因编码
			tLCDelTraceSchema.setRemark("单证删除--原因是："+ this.mReason);
			tLCDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLCDelTraceSchema.setMakeTime(PubFun.getCurrentTime());
			if(mReason==null || mReason.equals(""))
		    	   tLCDelTraceSchema.setRemark("None");
			mInputData.add(tLCDelTraceSchema);
			mmap.put(ttES_DOC_PAGESSet, "DELETE");
			mmap.put(tES_DOC_MAINSet, "DELETE");
			mmap.put(tES_DOC_RELATIONSet, "DELETE");
			mmap.put(tLCDelTraceSchema, "INSERT");
		}
		else if(tOperate.equals("DELETE||DOCID") || tOperate.equals("UPDATE||DOCID"))
		{
			String tSubtype = mES_DOC_MAINSchema.getSubType();
			if(tSubtype==null||"".equals(tSubtype)){
				CError.buildErr(this, "前台传入的单证类型为空！");
				return false;
			}
			if(tSubtype.substring(0,2).equals("UN")&& tOperate.equals("UPDATE||DOCID")){
				if(mSerialNo==null||"".equals(mSerialNo)){
					CError.buildErr(this, "单证类型为问题件回复类，单证号码不能为空！");
					return false;
				}
			}
			ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
			ES_DOC_RELATIONSet tES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();
			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
	        ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
	        ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
	        ES_DOC_PAGESSet tES_DOC_PAGESSET = new ES_DOC_PAGESSet();
	        tES_DOC_PAGESDB.setDocID(mES_DOC_MAINSchema.getDocID());
	        tES_DOC_PAGESSET = tES_DOC_PAGESDB.query();
	        tES_DOC_MAINDB.setDocID(mES_DOC_MAINSchema.getDocID());
	        if(mES_DOC_MAINSchema.getDocID()<=0){
	        	CError.buildErr(this, "内部号码为空！");
	        	return false;
	        }
	        tES_DOC_MAINSet = tES_DOC_MAINDB.query();
	        if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size()!=1)
	        {
	        	CError.buildErr(this, "查询单证主表失败！");
	        	return false;
	        }
	        //获取ES_DOC_RELATION表中的信息，如果修改单证类型，必须同时修改ES_DOC_RELATION表中的数据
	        tES_DOC_RELATIONDB.setDocID(mES_DOC_MAINSchema.getDocID());
	        tES_DOC_RELATIONSet = tES_DOC_RELATIONDB.query();
	        if(tES_DOC_RELATIONSet ==null ||tES_DOC_RELATIONSet.size()==0){
	        	CError.buildErr(this, "获取ES_DOC_RELATION表数据失败！");
	        	return false;
	        }
	        ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	        tES_DOC_MAINSchema.setSchema(tES_DOC_MAINSet.get(1));
	        if(tOperate.equals("UPDATE||DOCID"))
	        {
	        	//增加校验，只有两次的单证号码长度一样才能进行修改
	        	String tOldSubType = tES_DOC_RELATIONSet.get(1).getSubType();
	        	String tNewSubType = mES_DOC_MAINSchema.getSubType();
	        	//如果是修改单证类型 则不允许修改UA开头的类型
	        	if(!tOldSubType.equals(tNewSubType)){
		        	if(tOldSubType.substring(0,2).equals("UA")){
		        		CError.buildErr(this, "不允许修改投保单扫描类");
		        		return false;
		        	}
	        	}
	        	String tOldLengSql = "select distinct codelen from es_doc_def where subtype='"+"?tOldSubType?"+"'";
	        	String tNewLengSql = "select distinct codelen from es_doc_def where subtype='"+"?tNewSubType?"+"'";
	        	String tOldLeng = "";//原号码长度
	        	String tNewLeng = "";//新号码长度
	        	SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
	        	sqlbv5.sql(tOldLengSql);
	        	sqlbv5.put("tOldSubType", tOldSubType);
				
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tNewLengSql);
				sqlbv6.put("tNewSubType", tNewSubType);
	        	ExeSQL tExeSQL = new ExeSQL();
	        	/*2010-4-16 注掉此处校验 并令问题件回复类与申请扫描资料类两类单证可互相修改
	        	     如果扫描上传后发现扫描件不清楚，而此时 工作流已经流转，无法进行补扫，所以
	        	     在打开此处限制，并修改逻辑，令问题件回复类与申请扫描资料类可互相修改，这
	        	     样可以用UR类的扫进来后删除掉原来的通知书，再将新扫进来的类型修改为通知书
	        	     类型
	        	*/
	        	tOldLeng = tExeSQL.getOneValue(sqlbv5);
	        	tNewLeng = tExeSQL.getOneValue(sqlbv6);
	        	/* 如果旧长度不等于新长度并且新的号码长度为10位则先删除es_doc_relation中的
	        	 * 记录，重新插入两条记录，一条为印刷号，一条为通知书流水号
	        	 * 如果旧长度不等于新长度并且新的号码长度为14为则先删除es_doc_relation中的
	        	 * 记录，重新插入一条记录印刷号的记录
	        	 */
	        	String tDelSql = "delete from es_doc_relation where docid = '"
	        		+ "?docid?"+"' and subtype = '"
	        		+ "?tOldSubType?" +"'";
	        	SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
	        	sqlbv7.sql(tDelSql);
	        	sqlbv7.put("docid", mES_DOC_MAINSchema.getDocID());
	        	sqlbv7.put("tOldSubType", tOldSubType);
	        	ES_DOC_RELATIONSet tES_DOC_RELATIONSetInsert = new ES_DOC_RELATIONSet();
	        	LCDelTraceSchema tLCDelTraceSchema = new LCDelTraceSchema();
	        	if((!tOldLeng.equals(tNewLeng))&&("10".equals(tNewLeng))||"20".equals(tNewLeng)){
	        		ES_DOC_RELATIONSchema tES_DOC_RelationSchema1 = new ES_DOC_RELATIONSchema();
	        		ES_DOC_RELATIONSchema tES_DOC_RelationSchema2 = new ES_DOC_RELATIONSchema();
	        		ES_DOC_DEFDB tES_DOC_DEFDB = new ES_DOC_DEFDB();
	        		ES_DOC_DEFSet tES_DOC_DEFSet = new ES_DOC_DEFSet();
	        		tES_DOC_DEFDB.setSubType(tOldSubType);
	        		tES_DOC_DEFSet = tES_DOC_DEFDB.query();
	        		if(tES_DOC_DEFSet.size()!=1){
	        			CError.buildErr(this, "查询单证类型信息失败!");
	        			return false;
	        		}
	        		tES_DOC_RelationSchema1.setBussNo(mPrtNoA);
	        		tES_DOC_RelationSchema1.setBussNoType("11");
	        		tES_DOC_RelationSchema1.setBussType(tES_DOC_DEFSet.get(1).getBussType());
	        		tES_DOC_RelationSchema1.setDocCode(mPrtNoA);
	        		tES_DOC_RelationSchema1.setDocID(mES_DOC_MAINSchema.getDocID());
	        		tES_DOC_RelationSchema1.setRelaFlag("0");
	        		tES_DOC_RelationSchema1.setSubType(tOldSubType);
	        		tES_DOC_DEFSet.clear();
	        		tES_DOC_DEFDB.setSubType(tNewSubType);
	        		tES_DOC_DEFSet = tES_DOC_DEFDB.query();
	        		if(tES_DOC_DEFSet.size()!=1){
	        			CError.buildErr(this, "查询单证类型信息失败!");
	        			return false;
	        		}
	        		tES_DOC_RelationSchema2.setBussNo(mSerialNo);
	        		tES_DOC_RelationSchema2.setBussNoType(mSerialNo.substring(0,2));
	        		tES_DOC_RelationSchema2.setBussType(tES_DOC_DEFSet.get(1).getBussType());
	        		tES_DOC_RelationSchema2.setDocCode(mSerialNo);
	        		tES_DOC_RelationSchema2.setDocID(mES_DOC_MAINSchema.getDocID());
	        		tES_DOC_RelationSchema2.setRelaFlag("0");
	        		tES_DOC_RelationSchema2.setSubType(tNewSubType);
	        		tES_DOC_RELATIONSetInsert.add(tES_DOC_RelationSchema1);
	        		tES_DOC_RELATIONSetInsert.add(tES_DOC_RelationSchema2);
					tLCDelTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
			                PubFun.getNoLimit(mGlobalInput.ManageCom)));
					tLCDelTraceSchema.setPrtNo(mPrtNoA);
					tLCDelTraceSchema.setPolNo(String.valueOf(mES_DOC_MAINSchema.getDocID()));//单证号码
					tLCDelTraceSchema.setOldOperator(tES_DOC_MAINSchema.getScanOperator());//扫描员
					tLCDelTraceSchema.setOperator(mGlobalInput.Operator);
					tLCDelTraceSchema.setManageCom(mGlobalInput.ManageCom);
					tLCDelTraceSchema.setRiskCode(mReasonCode);//单证删除原因编码
					tLCDelTraceSchema.setRemark("修改单证类型，原类型:"+tOldSubType+" 新单证类型:"+tNewSubType);
					tLCDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
					tLCDelTraceSchema.setMakeTime(PubFun.getCurrentTime());
					mmap.put(sqlbv7, "DELETE");
	        	} else if((!tOldLeng.equals(tNewLeng))&&"14".equals(tNewLeng)){
	        		ES_DOC_RELATIONSchema tES_DOC_RelationSchema1 = new ES_DOC_RELATIONSchema();
	        		ES_DOC_DEFDB tES_DOC_DEFDB = new ES_DOC_DEFDB();
	        		ES_DOC_DEFSet tES_DOC_DEFSet = new ES_DOC_DEFSet();
	        		tES_DOC_DEFDB.setSubType(tOldSubType);
	        		tES_DOC_DEFSet = tES_DOC_DEFDB.query();
	        		if(tES_DOC_DEFSet.size()!=1){
	        			CError.buildErr(this, "查询单证类型信息失败!");
	        			return false;
	        		}
	        		tES_DOC_RelationSchema1.setBussNo(mPrtNoA);
	        		tES_DOC_RelationSchema1.setBussNoType("11");
	        		tES_DOC_RelationSchema1.setBussType(tES_DOC_DEFSet.get(1).getBussType());
	        		tES_DOC_RelationSchema1.setDocCode(mPrtNoA);
	        		tES_DOC_RelationSchema1.setDocID(mES_DOC_MAINSchema.getDocID());
	        		tES_DOC_RelationSchema1.setRelaFlag("0");
	        		tES_DOC_RelationSchema1.setSubType(tOldSubType);
	        		tES_DOC_DEFSet.clear();
	        		tES_DOC_DEFDB.setSubType(tNewSubType);
	        		tES_DOC_DEFSet = tES_DOC_DEFDB.query();
	        		if(tES_DOC_DEFSet.size()!=1){
	        			CError.buildErr(this, "查询单证类型信息失败!");
	        			return false;
	        		}
	        		tLCDelTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
			                PubFun.getNoLimit(mGlobalInput.ManageCom)));
					tLCDelTraceSchema.setPrtNo(mPrtNoA);
					tLCDelTraceSchema.setPolNo(String.valueOf(mES_DOC_MAINSchema.getDocID()));//单证号码
					tLCDelTraceSchema.setOldOperator(tES_DOC_MAINSchema.getScanOperator());//扫描员
					tLCDelTraceSchema.setOperator(mGlobalInput.Operator);
					tLCDelTraceSchema.setManageCom(mGlobalInput.ManageCom);
					tLCDelTraceSchema.setRiskCode(mReasonCode);//单证删除原因编码
					tLCDelTraceSchema.setRemark("修改单证类型，原类型:"+tOldSubType+" 新单证类型:"+tNewSubType);
					tLCDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
					tLCDelTraceSchema.setMakeTime(PubFun.getCurrentTime());
					tES_DOC_RELATIONSetInsert.add(tES_DOC_RelationSchema1);
	        		mmap.put(tDelSql, "DELETE");
	        	}
//	        	if(!tOldLeng.equals(tNewLeng)){
//	        		CError.buildErr(this, "只能修改为同一单证号码长度的单证！");
//	        		return false;
//	        	}
	        	for(int i=1;i<=tES_DOC_RELATIONSet.size();i++){
	        		tES_DOC_RELATIONSet.get(i).setSubType(mES_DOC_MAINSchema.getSubType());
	        	}
	        	tES_DOC_MAINSchema.setSubType(mES_DOC_MAINSchema.getSubType());
	        	mInputData.remove(0);
		        mInputData.add(tES_DOC_MAINSchema);
		        mInputData.add(tES_DOC_RELATIONSet);
		        mInputData.add(mmap);
		        if(tES_DOC_RELATIONSetInsert!=null){
		        	mmap.put(tES_DOC_RELATIONSetInsert, "INSERT");
		        }
		        mmap.put(tES_DOC_MAINSchema, "UPDATE");
		        mmap.put(tES_DOC_RELATIONSet, "UPDATE");
	        }
	        else
	        {
	        	LCDelTraceSchema tLCDelTraceSchema = new LCDelTraceSchema();
				tLCDelTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
		                PubFun.getNoLimit(mGlobalInput.ManageCom)));
				tLCDelTraceSchema.setPrtNo(mPrtNoA);
				tLCDelTraceSchema.setPolNo(String.valueOf(mES_DOC_MAINSchema.getDocID()));//单证号码
				tLCDelTraceSchema.setOldOperator(tES_DOC_MAINSchema.getScanOperator());//扫描员
				tLCDelTraceSchema.setOperator(mGlobalInput.Operator);
				tLCDelTraceSchema.setManageCom(mGlobalInput.ManageCom);
				tLCDelTraceSchema.setRiskCode(mReasonCode);//单证删除原因编码
				tLCDelTraceSchema.setRemark("单证删除--原因是："+ this.mReason);
				tLCDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
				tLCDelTraceSchema.setMakeTime(PubFun.getCurrentTime());
				mInputData.remove(0);
				mInputData.add(tES_DOC_MAINSchema);
		       if(mReason==null || mReason.equals(""))
		    	   tLCDelTraceSchema.setRemark("None");
	        	mInputData.add(tLCDelTraceSchema);
	        	mmap.put(tLCDelTraceSchema, "INSERT");
	        	mmap.put(tES_DOC_PAGESSET, "DELETE");
	        	mmap.put(tES_DOC_MAINSchema, "DELETE");
	        }	        
		}
		else if(tOperate.equals("UPDATE||PAGES") || tOperate.equals("DELETE||PAGES"))
		{
			if(mES_DOC_PAGESSet == null || mES_DOC_PAGESSet.size()<1)
			{
				CError.buildErr(this, "单证页信息传输失败！");
	        	return false;
			}
			
	        if(tOperate.equals("DELETE||PAGES"))
	        {
	        	ES_DOC_MAINSet tES_DOC_MAINSetD = new ES_DOC_MAINSet();//需要删除的
	        	ES_DOC_MAINSet tES_DOC_MAINSetU = new ES_DOC_MAINSet();//需要修改页码的
	        	int tFlag1 = 0;
	        	int tDocID1 = 0;
	        	int tDocID2 = 0;
	        	int tDocID = 0;
	        	for(int i=1 ; i<=mES_DOC_PAGESSet.size(); i++)
		        {
	        		tFlag1 = tFlag1 + 1;
	        		tDocID = mES_DOC_PAGESSet.get(i).getDocID();
	        		tDocID2 = tDocID;
	        			if (tDocID1!=0 && tDocID1 - tDocID2 != 0)
	        			{
	        				//为下一个单证主表或者此次删除只有一个单证主表，对上一个单证主表进行修改
	    	        		ES_DOC_PAGESSet ttES_DOC_PAGESSet = new ES_DOC_PAGESSet();
	    			        ES_DOC_PAGESDB ttES_DOC_PAGESDB = new ES_DOC_PAGESDB();
	    					ttES_DOC_PAGESDB.setDocID(tDocID1);
	    					ttES_DOC_PAGESSet = ttES_DOC_PAGESDB.query();
	    					if(ttES_DOC_PAGESSet == null || ttES_DOC_PAGESSet.size()<1)
	    			        {
	    			        	CError.buildErr(this, "单证下页信息查询失败！");
	    			        	return false;
	    			        }
	    					if(ttES_DOC_PAGESSet.size() == (tFlag1-1))
	    					{
//	    						//删除单证
//	    						ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
//	    						ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
//	    				        tES_DOC_MAINDB.setDocID(tDocID1);
//	    				        if(!tES_DOC_MAINDB.getInfo())
//	    				        {
//	    				        	CError.buildErr(this, "单证主表信息查询失败！");
//	    				        	return false;
//	    				        }
//	    				        tES_DOC_MAINSchema = tES_DOC_MAINDB.getSchema(); 	        				        
//	    				        	
//	    						tES_DOC_MAINSetD.add(tES_DOC_MAINSchema);
	    						CError.buildErr(this, "如需删除该单证（单证内部号："+tDocID1+"）下所有单证页信息，请使用“删除单证”按钮！");
    				        	return false;
	    			        }
	    					else
	    					{
	    						//修改单证主表页码
	    						ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	    						ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
	    						tES_DOC_MAINDB.setDocID(tDocID1);
	    				        if(!tES_DOC_MAINDB.getInfo())
	    				        {
	    				        	CError.buildErr(this, "单证主表信息查询失败！");
	    				        	return false;
	    				        }
	    				        tES_DOC_MAINSchema = tES_DOC_MAINDB.getSchema();
	    			        	tES_DOC_MAINSchema.setNumPages(tES_DOC_MAINSchema.getNumPages()-(tFlag1-1));
	    			        	
	    			        	tES_DOC_MAINSetU.add(tES_DOC_MAINSchema);
	    		        		
	    					}
	    					
	    					tFlag1 = 1;//重新计算
	        			}
	        			
	        			if (i == mES_DOC_PAGESSet.size())
	        			{
	        				//对本次单证主表进行修改
	    	        		ES_DOC_PAGESSet ttES_DOC_PAGESSet = new ES_DOC_PAGESSet();
	    			        ES_DOC_PAGESDB ttES_DOC_PAGESDB = new ES_DOC_PAGESDB();
	    					ttES_DOC_PAGESDB.setDocID(tDocID2);
	    					ttES_DOC_PAGESSet = ttES_DOC_PAGESDB.query();
	    					if(ttES_DOC_PAGESSet == null || ttES_DOC_PAGESSet.size()<1)
	    			        {
	    			        	CError.buildErr(this, "单证下页信息查询失败！");
	    			        	return false;
	    			        }
	    					if(ttES_DOC_PAGESSet.size() == tFlag1)
	    					{
//	    						//删除单证
//	    						ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
//	    						ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
//	    				        tES_DOC_MAINDB.setDocID(tDocID2);
//	    				        if(!tES_DOC_MAINDB.getInfo())
//	    				        {
//	    				        	CError.buildErr(this, "单证主表信息查询失败！");
//	    				        	return false;
//	    				        }
//	    				        tES_DOC_MAINSchema = tES_DOC_MAINDB.getSchema(); 	        				        
//	    				        	
//	    						tES_DOC_MAINSetD.add(tES_DOC_MAINSchema);
	    						CError.buildErr(this, "如需删除该单证（单证内部号："+tDocID2+"）下所有单证页信息，请使用“删除单证”按钮！");
    				        	return false;
	    			        }
	    					else
	    					{
	    						//修改单证主表页码
	    						ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	    						ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
	    						tES_DOC_MAINDB.setDocID(tDocID2);
	    				        if(!tES_DOC_MAINDB.getInfo())
	    				        {
	    				        	CError.buildErr(this, "单证主表信息查询失败！");
	    				        	return false;
	    				        }
	    				        tES_DOC_MAINSchema = tES_DOC_MAINDB.getSchema();
	    			        	tES_DOC_MAINSchema.setNumPages(tES_DOC_MAINSchema.getNumPages()-tFlag1);
	    			        	
	    			        	tES_DOC_MAINSetU.add(tES_DOC_MAINSchema);
	    		        		
	    					}
	    					
	    					tFlag1 = 1;//重新计算
	        			}    		
	        				        	
		        	
	        		tDocID1 = tDocID;	        	
	        		
	        		
		        }
		        
		        mInputData.add(tES_DOC_MAINSetD);
		        mInputData.add(tES_DOC_MAINSetU);
		        mmap.put(tES_DOC_MAINSetU, "UPDATE");
		        mmap.put(mES_DOC_PAGESSet, "DELETE");
	        } else { 		        	
	        	//重新查询单证页信息
		        ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet(); 
		        for(int i=1 ; i<=mES_DOC_PAGESSet.size(); i++)
		        {
		        	ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
			        ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
					tES_DOC_PAGESDB.setPageID(mES_DOC_PAGESSet.get(i).getPageID());
					if(!tES_DOC_PAGESDB.getInfo())
			        {
			        	CError.buildErr(this, "单证页信息查询失败！");
			        	return false;
			        }
					tES_DOC_PAGESSchema = tES_DOC_PAGESDB.getSchema();
					tES_DOC_PAGESSchema.setPageCode(mES_DOC_PAGESSet.get(i).getPageCode());//更新单证页信息
					tES_DOC_PAGESSet.add(tES_DOC_PAGESSchema);
		        }		        
		        
		        mInputData.remove(1);
		        mInputData.add(tES_DOC_PAGESSet);
		        mmap.put(tES_DOC_PAGESSet, "UPDATE");
	        }        
	        
	        
		}
		
		return true;		 
	}

	private boolean prepareDate(){
		mInputData.clear();
		mInputData.add(mmap);
		return true;
	}
	
	/**
	 * 删除所有保单信息
	 * */
	private boolean delCont(){
		DelPrtBL tDelPrtBL = new DelPrtBL();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PrtNo", mPrtNoB);
		tTransferData.setNameAndValue("ESFlag", "1");
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		if(!tDelPrtBL.submitData(tVData, "")){
			CError.buildErr(this, "删除保单信息失败");
			return false;
		}
		VData tempVData = new VData();
		tempVData = tDelPrtBL.getResult();
		if ((tempVData != null) && (tempVData.size() > 0)) {
//			mInputData = tempVData;
			for(int i=0;i<tempVData.size();i++){
				MMap tMap = new MMap();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				mmap.add(tMap);
				break;
			}
		}
		return true;
	}
}
