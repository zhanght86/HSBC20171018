package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCGrpSpecDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPCGrpSpecDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPCGrpSpecSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCCGrpSpecSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LPCGrpSpecSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 特别约定内容变更业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0 2.0
 */
public class PGrpEdorSCDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorSCDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**
	 * 保全项目表
	 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPCGrpSpecSchema mLPCGrpSpecSchema = new LPCGrpSpecSchema();
	private LPCGrpSpecSet mLPCGrpSpecSet = new LPCGrpSpecSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PGrpEdorSCDetailBL() {
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
		logger.debug("==> PGrpEdorSCDetailBL : Start Submit");
		mOperate=cOperate;
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		// 数据查询业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (tPubSubmit.submitData(mResult, cOperate) == false) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean dealData() {

		// 定义LPCSpec表操作变量
		LPCGrpSpecSchema tLPCGrpSpecSchema = new LPCGrpSpecSchema();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContSet = tLCGrpContDB.query();
		if (tLCGrpContSet.size() < 1) {
			CError.buildErr(this, "保单数据查询失败!");
			return false;
		} else {
			tLCGrpContSchema = tLCGrpContSet.get(1);
		}
		Reflections tReflections = new Reflections();		
		if (mOperate.equals("insert")){
			// 说明是首次增加特约
			String strNoLimit = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
			String aSerialNo = PubFun1.CreateMaxNo("SN", strNoLimit); // 生成打印流水号
			String aPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tReflections.transFields(tLPCGrpSpecSchema, mLPCGrpSpecSchema);
			// tLPCSpecSchema.setPrtSeq(sPrtSeq);
			tLPCGrpSpecSchema.setSerialNo(aSerialNo);
			tLPCGrpSpecSchema.setGrpContNo(tLCGrpContSchema.getGrpContNo());
			tLPCGrpSpecSchema.setGrpContNo(tLCGrpContSchema.getGrpContNo());
			tLPCGrpSpecSchema.setPrtFlag("0");
			tLPCGrpSpecSchema.setPrtSeq(aPrtSeq);
			tLPCGrpSpecSchema.setProposalGrpContNo(tLCGrpContSchema.getProposalGrpContNo());
			tLPCGrpSpecSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPCGrpSpecSchema.setMakeDate(mCurrentDate);
			tLPCGrpSpecSchema.setMakeTime(mCurrentTime);
			tLPCGrpSpecSchema.setModifyDate(mCurrentDate);
			tLPCGrpSpecSchema.setModifyTime(mCurrentTime);
			tLPCGrpSpecSchema.setOperator(mGlobalInput.Operator);
//			mMap.put("delete  from LPCSpec where edorno='"
//			+ mLPEdorItemSchema.getEdorNo() + "' and ContNo='"
//			+ mLPEdorItemSchema.getContNo() + "' and SerialNo='"+mLPCSpecSchema.getSerialNo()+"'", "DELETE");

			mLPCGrpSpecSet.add(tLPCGrpSpecSchema);
		} else if (mOperate.equals("modify")){ 
			// 本次是修改特约
			LPCGrpSpecDB tLPCGrpSpecDB = new LPCGrpSpecDB();
			LPCGrpSpecSet tLPCGrpSpecSet = new LPCGrpSpecSet();
			tLPCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
			tLPCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
			tLPCGrpSpecDB.setEdorNo(mLPCGrpSpecSchema.getEdorNo());
			tLPCGrpSpecDB.setEdorType(mLPCGrpSpecSchema.getEdorType());
			tLPCGrpSpecSet=tLPCGrpSpecDB.query();
			if(tLPCGrpSpecSet.size()>0){
				LPCGrpSpecSchema rLPCGrpSpecSchema = new LPCGrpSpecSchema();
				rLPCGrpSpecSchema=tLPCGrpSpecSet.get(1);
				tReflections.transFields(tLPCGrpSpecSchema, rLPCGrpSpecSchema);
			}else{
				//本次是直接修改
				LCCGrpSpecDB tLCCGrpSpecDB = new LCCGrpSpecDB();
				LCCGrpSpecSet tLCCGrpSpecSet = new LCCGrpSpecSet();
				tLCCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
				tLCCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
				tLCCGrpSpecSet=tLCCGrpSpecDB.query();
				if(tLCCGrpSpecSet.size()<1 || tLCCGrpSpecSet==null){
					CError.buildErr(this, "保单特约数据查询失败!");
					return false;
				}else{
					LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
					tLCCGrpSpecSchema=tLCCGrpSpecSet.get(1);
					tReflections.transFields(tLPCGrpSpecSchema, tLCCGrpSpecSchema);
				}
			}

			tLPCGrpSpecSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPCGrpSpecSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPCGrpSpecSchema.setSpecContent(mLPCGrpSpecSchema.getSpecContent());
			tLPCGrpSpecSchema.setModifyDate(mCurrentDate);
			tLPCGrpSpecSchema.setModifyTime(mCurrentTime);
			mLPCGrpSpecSet.add(tLPCGrpSpecSchema);
		}else if (mOperate.equals("delete")){
			//本次是直接删除
			LPCGrpSpecDB tLPCGrpSpecDB = new LPCGrpSpecDB();
			LPCGrpSpecSet tLPCGrpSpecSet = new LPCGrpSpecSet();
			tLPCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
			tLPCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
			tLPCGrpSpecDB.setEdorNo(mLPCGrpSpecSchema.getEdorNo());
			tLPCGrpSpecDB.setEdorType(mLPCGrpSpecSchema.getEdorType());
			tLPCGrpSpecSet=tLPCGrpSpecDB.query();
			if(tLPCGrpSpecSet.size()>0){
				LPCGrpSpecSchema rLPCGrpSpecSchema = new LPCGrpSpecSchema();
				rLPCGrpSpecSchema=tLPCGrpSpecSet.get(1);
				tReflections.transFields(tLPCGrpSpecSchema, rLPCGrpSpecSchema);
			}else{
				LCCGrpSpecDB tLCCGrpSpecDB = new LCCGrpSpecDB();
				LCCGrpSpecSet tLCCGrpSpecSet = new LCCGrpSpecSet();
				tLCCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
				tLCCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
				tLCCGrpSpecSet=tLCCGrpSpecDB.query();
				if(tLCCGrpSpecSet.size()<1 || tLCCGrpSpecSet==null){
					CError.buildErr(this, "保单特约数据查询失败!");
					return false;
				}else{

					LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
					tLCCGrpSpecSchema=tLCCGrpSpecSet.get(1);
					tReflections.transFields(tLPCGrpSpecSchema, tLCCGrpSpecSchema);
				}
			}
			tLPCGrpSpecSchema.setSpecType("del");
			tLPCGrpSpecSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPCGrpSpecSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			//tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());
			tLPCGrpSpecSchema.setModifyDate(mCurrentDate);
			tLPCGrpSpecSchema.setModifyTime(mCurrentTime);
			mLPCGrpSpecSet.add(tLPCGrpSpecSchema);
		}else if (mOperate.equals("cancelDel")){
			//撤消删除
			LPCGrpSpecDB tLPCGrpSpecDB = new LPCGrpSpecDB();
			LPCGrpSpecSet tLPCGrpSpecSet = new LPCGrpSpecSet();
			tLPCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
			tLPCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
			tLPCGrpSpecDB.setEdorNo(mLPCGrpSpecSchema.getEdorNo());
			tLPCGrpSpecDB.setEdorType(mLPCGrpSpecSchema.getEdorType());
			tLPCGrpSpecSet=tLPCGrpSpecDB.query();
			if(tLPCGrpSpecSet.size()>0){
				LPCGrpSpecSchema rLPCGrpSpecSchema = new LPCGrpSpecSchema();
				rLPCGrpSpecSchema=tLPCGrpSpecSet.get(1);
				tReflections.transFields(tLPCGrpSpecSchema, rLPCGrpSpecSchema);
				
				LCCGrpSpecDB tLCCGrpSpecDB = new LCCGrpSpecDB();
				LCCGrpSpecSet tLCCGrpSpecSet = new LCCGrpSpecSet();
				tLCCGrpSpecDB.setSerialNo(mLPCGrpSpecSchema.getSerialNo());
				tLCCGrpSpecDB.setGrpContNo(mLPCGrpSpecSchema.getGrpContNo());
				tLCCGrpSpecSet=tLCCGrpSpecDB.query();
				LCCGrpSpecSchema tLCCGrpSpecSchema = null;
				if(tLCCGrpSpecSet.size()<1 || tLCCGrpSpecSet==null){
					tLPCGrpSpecSchema.setSpecType(" ");
				}else{

					
					tLCCGrpSpecSchema=tLCCGrpSpecSet.get(1);
					tLPCGrpSpecSchema.setSpecType(tLCCGrpSpecSchema.getSpecType());
				}
				tLPCGrpSpecSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPCGrpSpecSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				//tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());
				tLPCGrpSpecSchema.setModifyDate(mCurrentDate);
				tLPCGrpSpecSchema.setModifyTime(mCurrentTime);
				mLPCGrpSpecSet.add(tLPCGrpSpecSchema);
			}
		}

		mLPGrpEdorItemSchema.setEdorState("1");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		if(mLPCGrpSpecSet.size()>0){
			mMap.put(tLPCGrpSpecSchema, "DELETE&INSERT");
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPCGrpSpecSchema = (LPCGrpSpecSchema) cInputData.getObjectByObjectName(
				"LPCGrpSpecSchema", 0);
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		 LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		 tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		
		 if (!tLPGrpEdorItemDB.getInfo()) {
		 // @@错误处理
		 CError.buildErr(this, "无保全申请数据!");
		
		 return false;
		 }
		 mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		 if(mLPCGrpSpecSchema==null)
		 {
		 CError.buildErr(this, "无申请数据!");
		
		 return false;
		 }
		

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		logger.debug("==> PEdorSCDetailBL : Start Prepare Data");

		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		mResult.clear();

		mResult.add(mMap);
		return true;
	}

}
