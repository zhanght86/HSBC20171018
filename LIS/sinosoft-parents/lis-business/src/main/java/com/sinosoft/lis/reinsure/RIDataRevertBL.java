

package com.sinosoft.lis.reinsure;

/**
 * <p>ClassName: RIDataRevertBL.java </p>
 * <p>Description: 数据回滚 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011-07-30
 */

//包名
//package com.sinosoft.lis.config;

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.db.RICalParamDB;
import com.sinosoft.lis.db.RIPolRecordBakeDB;
import com.sinosoft.lis.db.RIRecordTraceDB;
import com.sinosoft.lis.db.RIWFLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RICalParamBSchema;
import com.sinosoft.lis.schema.RICalParamSchema;
import com.sinosoft.lis.schema.RIDataRevertLogSchema;
import com.sinosoft.lis.schema.RIPolRecordBakeBSchema;
import com.sinosoft.lis.schema.RIPolRecordBakeSchema;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceBSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RICalParamBSet;
import com.sinosoft.lis.vschema.RICalParamSet;
import com.sinosoft.lis.vschema.RIPolRecordBakeBSet;
import com.sinosoft.lis.vschema.RIPolRecordBakeSet;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.lis.vschema.RIRecordTraceBSet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class RIDataRevertBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private RIDataRevertLogSchema mRIDataRevertLogSchema = new RIDataRevertLogSchema();

	private String mAccumulateDefNO = new String();
	private String mInsuredNo = new String();
	private String mStartDate = new String();
	private String currentDate = PubFun.getCurrentDate();
	/** 全局变量 (张斌添加) */
	private RIPolRecordBakeSet mRIPolRecordBakeSet = new RIPolRecordBakeSet();
	
	private MMap map;
	private static final int ROWNUM = 5000;
	private String batNo;
	private String BakeSerialNo;
	private PubSubmit tPubSubmit = new PubSubmit();
	private boolean hasInsuredNo=false;
	public RIDataRevertBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = cInputData;
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		mInputData = null;
		System.out.println("End RIDataRevertBL Submit...");
		return true;

	}

	private boolean saveData() {
		if (!prepareOutputData()) {
			buildError("prepareOutputData", "准备向后台传递参数错误");
			return false;
		}
		if (!tPubSubmit.submitData(mInputData, null)) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDataRevertBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.mRIDataRevertLogSchema
				.setSchema((RIDataRevertLogSchema) mInputData
						.getObjectByObjectName("RIDataRevertLogSchema", 0));
		this.mAccumulateDefNO = mRIDataRevertLogSchema.getAccumulateDefNo();
		this.mInsuredNo = mRIDataRevertLogSchema.getInsuredNo();
		this.mStartDate = mRIDataRevertLogSchema.getStartDate();
		this.mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		hasInsuredNo = mInsuredNo != null && !"".equals(mInsuredNo);
		return true;
	}

	private boolean checkData() {
		return true;
	}
	
	private boolean dealData(){
		RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
		tRIAccumulateDefDB.setAccumulateDefNO(this.mAccumulateDefNO);
		if(!tRIAccumulateDefDB.getInfo()){
			// @@错误处理
			buildError("prepareData", "校验分出责任时出现错误。");
			return false;
		}
		RIAccumulateDefSchema tAccumulateDefNOSchema = tRIAccumulateDefDB.getSchema();
		
		if("".equals(tAccumulateDefNOSchema.getBFFlag())){
			// 事件驱动数据业务处理
			if (!dealEventData()) {
				return false;
			}
		}else{
			// 有效保单数据业务处理
			if (!dealValiData()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealEventData() {
		if ("REVERT".equals(mOperate)) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
					.append("select * from RIPolRecordBake a where a.accumulatedefno='");
			strBuffer.append(mAccumulateDefNO);
			strBuffer.append("' and a.getdate between '");
			strBuffer.append(mStartDate);
			strBuffer.append("' and '");
			strBuffer.append(currentDate);
			if (hasInsuredNo) {
				strBuffer.append("' and a.InsuredNo='");
				strBuffer.append(mInsuredNo);
			}
			strBuffer.append("' and rownum <=");
			strBuffer.append(ROWNUM);
			strBuffer.append(" order by a.Eventno");
			BakeSerialNo = PubFun1.CreateMaxNo("RIDATAREVERTLOG", 10);
			batNo = PubFun1.CreateMaxNo("RIWFLOG", 10);
			mRIDataRevertLogSchema.setSerialNo(BakeSerialNo);
			mRIDataRevertLogSchema.setEndDate(currentDate);
			RIPubFun.fillDefaultField(mRIDataRevertLogSchema, mGlobalInput);

			RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
			// 生成日志表
			tRIWFLogSchema.setBatchNo(batNo);
			tRIWFLogSchema.setTaskCode(mAccumulateDefNO);
			tRIWFLogSchema.setTaskType("01");
			tRIWFLogSchema.setNodeState("02");
			tRIWFLogSchema.setStartDate(mStartDate);
			tRIWFLogSchema.setEndDate(currentDate);
			RIPubFun.fillDefaultField(tRIWFLogSchema, mGlobalInput);

			map = new MMap();
			map.put(mRIDataRevertLogSchema, "INSERT");
			map.put(tRIWFLogSchema, "INSERT");
			if (!saveData()) {
				return false;
			}

			while (true) {
				Reflections tReflections = new Reflections();
				RIPolRecordBakeDB tRIPolRecordBakeDB = new RIPolRecordBakeDB();
				RIPolRecordBakeBSet tRIPolRecordBakeBSet = new RIPolRecordBakeBSet();
				RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
				RIPolRecordBakeSet tRIPolRecordBakeSet = tRIPolRecordBakeDB
						.executeQuery(strBuffer.toString());
				if (tRIPolRecordBakeSet.size() == 0) {
					break;
				}
				map = new MMap();
				for (int i = 1; i <= tRIPolRecordBakeSet.size(); i++) {
					
					RIPolRecordBakeBSchema mRIPolRecordBakeBSchema = new RIPolRecordBakeBSchema(); // new一个LCPolSchema
					RIPolRecordSchema mRIPolRecordSchema = new RIPolRecordSchema();
					RIPolRecordBakeSchema mRIPolRecordBakeSchema = tRIPolRecordBakeSet
							.get(i);
					tReflections.transFields(mRIPolRecordSchema,
							mRIPolRecordBakeSchema); // 将LBPolSchema的数据放进LCPolSchema中
					tReflections.transFields(mRIPolRecordBakeBSchema,
							mRIPolRecordBakeSchema); // 将LBPolSchema的数据放进LCPolSchema中
					mRIPolRecordSchema.setNodeState("02");
					mRIPolRecordSchema.setBatchNo(batNo);
					
					/**zb 2012-10-18 add*/
					mRIPolRecordSchema.setEventNo("R"+PubFun1.CreateMaxNo("RIEVENTNO", 19));
					 /*add end*/
					
					tRIPolRecordSet.add(mRIPolRecordSchema);
					mRIPolRecordBakeBSchema.setBakeSerialNo(BakeSerialNo);
					tRIPolRecordBakeBSet.add(mRIPolRecordBakeBSchema);
				}

				StringBuffer s = new StringBuffer();
				s.append("select * from RIRecordTrace b where b.eventno in (select a.eventno from (select eventno from RIPolRecordBake where accumulatedefno='");
				s.append(mAccumulateDefNO);
				s.append("' and getdate between '");
				s.append(mStartDate);
				s.append("' and '");
				s.append(currentDate);
				if (hasInsuredNo) {
					s.append("' and InsuredNo='");
					s.append(mInsuredNo);
				}
				s.append("' and rownum <=");
				s.append(ROWNUM);
				s.append("order by eventno) a) order by b.Eventno");

				RIRecordTraceBSet tRIRecordTraceBSet = new RIRecordTraceBSet();
				RIRecordTraceDB tRIRecordTraceDB = new RIRecordTraceDB();
				RIRecordTraceSet tRIRecordTraceSet = tRIRecordTraceDB
						.executeQuery(s.toString());

				for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
					RIRecordTraceBSchema mRIRecordTraceBSchema = new RIRecordTraceBSchema(); // new一个LCPolSchema
					RIRecordTraceSchema mRIRecordTraceSchema = tRIRecordTraceSet
							.get(i);
					tReflections.transFields(mRIRecordTraceBSchema,
							mRIRecordTraceSchema); // 将LBPolSchema的数据放进LCPolSchema中
					mRIRecordTraceBSchema.setBakeSerialNo(BakeSerialNo);
					tRIRecordTraceBSet.add(mRIRecordTraceBSchema);
				}

				s = new StringBuffer();
				s.append("select * from RICalParam b where b.eventno in (select a.eventno from (select eventno from RIPolRecordBake where accumulatedefno='");
				s.append(mAccumulateDefNO);
				s.append("' and getdate between '");
				s.append(mStartDate);
				s.append("' and '");
				s.append(currentDate);
				if (hasInsuredNo) {
					s.append("' and InsuredNo='");
					s.append(mInsuredNo);
				}
				s.append("' and rownum <=");
				s.append(ROWNUM);
				s.append("order by eventno) a) order by b.Eventno");

				RICalParamBSet tRICalParamBSet = new RICalParamBSet();
				RICalParamDB mRICalParamDB = new RICalParamDB();
				RICalParamSet tRICalParamSet = mRICalParamDB.executeQuery(s
						.toString());

				for (int i = 1; i <= tRICalParamSet.size(); i++) {
					RICalParamBSchema mRICalParamBSchema = new RICalParamBSchema(); // new一个LCPolSchema
					RICalParamSchema mRICalParamSchema = tRICalParamSet.get(i);
					tReflections.transFields(mRICalParamBSchema,
							mRICalParamSchema); // 将LBPolSchema的数据放进LCPolSchema中
					mRICalParamBSchema.setBakeSerialNo(BakeSerialNo);
					tRICalParamBSet.add(mRICalParamBSchema);
				}

				map.put(tRIPolRecordBakeBSet, "INSERT"); // 备份RIPolRecordBake表
				map.put(tRIRecordTraceBSet, "INSERT"); // 备份RIRecordTrace表
				map.put(tRICalParamBSet, "INSERT"); // 备份RICalParam表
				map.put(tRIPolRecordSet, "INSERT"); // 将RIPolRecordBake数据插入到RIPolRecord表中
				map.put(tRIPolRecordBakeSet, "DELETE"); // 删除RIRecordTrace表内条件数据
				map.put(tRIRecordTraceSet, "DELETE"); // 删除RICalParam表内条件数据
				map.put(tRICalParamSet, "DELETE"); // 删除RIPolRecordBake表内条件数据

				if (!saveData()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 张斌 增加
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealValiData() {
		
		if ("REVERT".equals(mOperate)) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
					.append("select * from Riwflog a where a.Taskcode = '");
			strBuffer.append(mAccumulateDefNO);
			strBuffer.append("' and a.Startdate >= '");
			strBuffer.append(mStartDate);
			strBuffer.append("' and a.endDate <= '");
			strBuffer.append(currentDate);			
			strBuffer.append("'");
			
			RIWFLogDB tRIWFLogDB = new RIWFLogDB();
			
			RIWFLogSet tRIWFLogSet = tRIWFLogDB.executeQuery(strBuffer.toString());
			if(tRIWFLogDB.mErrors.needDealError()){
				buildError("dealValiData", "有效保单数据执行批次查询失败。");
				return false;
			}
			if(tRIWFLogSet.size()==0){
				buildError("dealValiData", "有效保单数据执行批次为空。");
				return false;
			}
			
			for(int i=1 ;i<=tRIWFLogSet.size();i++){
				
				BakeSerialNo = PubFun1.CreateMaxNo("RIDATAREVERTLOG", 10);
				
				//保存回滚日志表
				if(!saveRevertLog(tRIWFLogSet.get(i))){
					return false;
				}
				
				//回滚任务日志表
				if(!updateValiRIWFlog(tRIWFLogSet.get(i))){
					return false;
				}
				
				//回滚数据
				if(!ervertValiData(tRIWFLogSet.get(i))){
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean saveRevertLog(RIWFLogSchema aRIWFLogSchema){
				
		mRIDataRevertLogSchema.setSerialNo(BakeSerialNo);
		mRIDataRevertLogSchema.setEndDate(currentDate);
		RIPubFun.fillDefaultField(mRIDataRevertLogSchema, mGlobalInput);

		map = new MMap();
		map.put(mRIDataRevertLogSchema, "INSERT");
		
		if (!saveData()) {
			return false;
		}		
		return true;
	}
	
	/**
	 * 张斌 增加
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean ervertValiData(RIWFLogSchema aRIWFLogSchema){
		
		while (true) {
			
			map = new MMap();
			
			//删除RIPolRecordBake
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
				.append("select * from RIPolRecordBake a where a.accumulatedefno='");
			strBuffer.append(mAccumulateDefNO);
			if (hasInsuredNo) {
				strBuffer.append("' and a.InsuredNo='");
				strBuffer.append(mInsuredNo);
			}
			strBuffer.append("' and a.BatchNo = '");
			strBuffer.append(aRIWFLogSchema.getBatchNo());		
			strBuffer.append("' and rownum <=");
			strBuffer.append(ROWNUM);
			strBuffer.append(" order by a.Eventno");
			System.out.println("sql: "+strBuffer.toString());
			
			RIPolRecordBakeDB tRIPolRecordBakeDB = new RIPolRecordBakeDB();
			
			mRIPolRecordBakeSet = tRIPolRecordBakeDB
					.executeQuery(strBuffer.toString());
			
			if(tRIPolRecordBakeDB.mErrors.needDealError()){
				buildError("ervertValiData", "有效保单数据查询失败。");
				return false;
			}
			if (mRIPolRecordBakeSet.size() == 0) {
				break;
			}
			map.put(mRIPolRecordBakeSet, "DELETE"); // 删除RIRecordTrace表内条件数据
			
			//保存RIPolRecordBakeB，保存RIPolRecord
			if(!revertRIPol(aRIWFLogSchema)){
				return false;
			}
			
			//删除RIRecordTrace，保存RIRecordTraceB
			if(!revertRIRecord(aRIWFLogSchema)){
				return false;
			}
			
			//删除RICalParam，保存RICalParamB			
			if(!ervertCalParam(aRIWFLogSchema)){
				return false;
			}

			if (!saveData()) {
				return false;
			}
		}
		
		return true;
	}
	/**
	 * 保存RIPolRecordBakeB，保存RIPolRecord
	 * @param aRIPolRecordBakeSet
	 * @return
	 */
	private boolean revertRIPol(RIWFLogSchema aRIWFLogSchema){
		
		Reflections tReflections = new Reflections();
		RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
		RIPolRecordBakeBSet tRIPolRecordBakeBSet = new RIPolRecordBakeBSet();
		
		for (int i = 1; i <= mRIPolRecordBakeSet.size(); i++) {
			RIPolRecordBakeBSchema mRIPolRecordBakeBSchema = new RIPolRecordBakeBSchema(); // new一个LCPolSchema
			RIPolRecordSchema mRIPolRecordSchema = new RIPolRecordSchema();
			
			
			RIPolRecordBakeSchema mRIPolRecordBakeSchema = mRIPolRecordBakeSet
					.get(i);
			tReflections.transFields(mRIPolRecordSchema,
					mRIPolRecordBakeSchema); // 将LBPolSchema的数据放进LCPolSchema中
			tReflections.transFields(mRIPolRecordBakeBSchema,
					mRIPolRecordBakeSchema); // 将LBPolSchema的数据放进LCPolSchema中
			mRIPolRecordSchema.setNodeState("02");
			mRIPolRecordSchema.setBatchNo(aRIWFLogSchema.getBatchNo());
			tRIPolRecordSet.add(mRIPolRecordSchema);
			
			mRIPolRecordBakeBSchema.setBakeSerialNo(BakeSerialNo);
			tRIPolRecordBakeBSet.add(mRIPolRecordBakeBSchema);
		}
		
		map.put(tRIPolRecordBakeBSet, "INSERT"); // 备份RIPolRecordBake表
		map.put(tRIPolRecordSet, "INSERT"); // 将RIPolRecordBake数据插入到RIPolRecord表中
		
		return true;
	}
	
	private boolean revertRIRecord(RIWFLogSchema aRIWFLogSchema){
		
		Reflections tReflections = new Reflections();
		
		StringBuffer s = new StringBuffer();
		s.append("select * from RIRecordTrace b where b.eventno in (select a.eventno from (select c.eventno from RIPolRecordBake c where c.accumulatedefno='");
		s.append(mAccumulateDefNO);
		if (hasInsuredNo) {
			s.append("' and c.InsuredNo='");
			s.append(mInsuredNo);
		}
		s.append("' and c.batchNo = '");
		s.append(aRIWFLogSchema.getBatchNo());
		s.append("' and rownum <=");
		s.append(ROWNUM);
		s.append("order by c.eventno) a) order by b.Eventno");

		RIRecordTraceBSet tRIRecordTraceBSet = new RIRecordTraceBSet();
		RIRecordTraceDB tRIRecordTraceDB = new RIRecordTraceDB();
		RIRecordTraceSet tRIRecordTraceSet = tRIRecordTraceDB
				.executeQuery(s.toString());

		for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
			RIRecordTraceBSchema mRIRecordTraceBSchema = new RIRecordTraceBSchema(); // new一个LCPolSchema
			RIRecordTraceSchema mRIRecordTraceSchema = tRIRecordTraceSet
					.get(i);
			tReflections.transFields(mRIRecordTraceBSchema,
					mRIRecordTraceSchema); // 将LBPolSchema的数据放进LCPolSchema中
			mRIRecordTraceBSchema.setBakeSerialNo(BakeSerialNo);
			tRIRecordTraceBSet.add(mRIRecordTraceBSchema);
		}
		
		map.put(tRIRecordTraceBSet, "INSERT"); // 备份RIRecordTrace表
		map.put(tRIRecordTraceSet, "DELETE"); // 删除RICalParam表内条件数据
		
		return true;
	}
	
	private boolean ervertCalParam(RIWFLogSchema aRIWFLogSchema){
		
		Reflections tReflections = new Reflections();
		
		StringBuffer s = new StringBuffer();
		s = new StringBuffer();
		s.append("select * from RICalParam b where b.eventno in (select a.eventno from (select c.eventno from RIPolRecordBake c where c.accumulatedefno='");
		s.append(mAccumulateDefNO);
		if (hasInsuredNo) {
			s.append("' and c.InsuredNo='");
			s.append(mInsuredNo);
		}
		s.append("' and c.batchNo = '");
		s.append(aRIWFLogSchema.getBatchNo());
		s.append("' and rownum <=");
		s.append(ROWNUM);
		s.append("order by c.eventno) a) order by b.Eventno");
		
		
		RICalParamBSet tRICalParamBSet = new RICalParamBSet();
		RICalParamDB mRICalParamDB = new RICalParamDB();
		RICalParamSet tRICalParamSet = mRICalParamDB.executeQuery(s
				.toString());

		for (int i = 1; i <= tRICalParamSet.size(); i++) {
			RICalParamBSchema mRICalParamBSchema = new RICalParamBSchema(); // new一个LCPolSchema
			RICalParamSchema mRICalParamSchema = tRICalParamSet.get(i);
			tReflections.transFields(mRICalParamBSchema,
					mRICalParamSchema); // 将LBPolSchema的数据放进LCPolSchema中
			mRICalParamBSchema.setBakeSerialNo(BakeSerialNo);
			tRICalParamBSet.add(mRICalParamBSchema);
		}
		
		map.put(tRICalParamBSet, "INSERT"); // 备份RICalParam表
		map.put(tRICalParamSet, "DELETE"); // 删除RIPolRecordBake表内条件数据
		
		return true;
	}
	
	private boolean updateValiRIWFlog(RIWFLogSchema aRIWFLogSchema){
		
		RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
		tRIWFLogSchema.setSchema(aRIWFLogSchema.getSchema());
		// 生成日志表
		tRIWFLogSchema.setNodeState("02");
		tRIWFLogSchema.setModifyDate(PubFun.getCurrentDate());
		tRIWFLogSchema.setModifyTime(PubFun.getCurrentTime());
		
		map = new MMap();
		map.put(tRIWFLogSchema, "UPDATE");
		
		if (!saveData()) {
			return false;
		}
		
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}

}

