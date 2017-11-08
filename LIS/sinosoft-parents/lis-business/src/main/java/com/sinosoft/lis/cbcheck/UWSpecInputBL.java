package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 改变生效日期
 * </p>
 * <p>
 * Description: 在人工核保时改变生效日期
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class UWSpecInputBL {
private static Logger logger = Logger.getLogger(UWSpecInputBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	// 合同表
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSchema mmLCContSchema = new LCContSchema();
	private LCCSpecSchema mLCCSpecSchema = new LCCSpecSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	// 险种表
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	double SumPrem = 0; // 合计保费

	public UWSpecInputBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		mOperate = cOperate;
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!") ;
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		// map.put(mmLCPolSet, "UPDATE");

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if(this.mOperate!=null&&this.mOperate.equals("DELETE&&YGSPEC"))
		{
			String tContNo = (String)this.mTransferData.getValueByName("ContNo");
			String tSQL = "select count(*) from lccspec a where contno  ='"+"?contno?"+"' and speccode = 'yg001'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("contno", tContNo);
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(sqlbv);
			if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)==0)
			{
				CError.buildErr(this,"该合同下没有员工特约,不需要删除!");
				return false;
			}
		}
//		else if(this.mOperate.equals("INSERT"))
//		{
//			
//		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCContSchema = (LCContSchema) cInputData.getObjectByObjectName(
				"LCContSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCCUWMasterSchema = (LCCUWMasterSchema) cInputData.getObjectByObjectName(
				"LCCUWMasterSchema", 0);
		mLCCSpecSchema = (LCCSpecSchema) cInputData.getObjectByObjectName(
				"LCCSpecSchema", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据失败!") ;
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!") ;
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!") ;
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		//生效日回溯特约录入
		LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		logger.debug("mOperate: "+mOperate);
		if(mOperate.equals("INSERT")){
			//插入新的合同生效日特约
			logger.debug("流水号为（应该为空）："+mLCCSpecSchema.getSerialNo());
			tLCCSpecSchema.setSchema(mLCCSpecSchema);
			tLCCSpecSchema.setSerialNo(PubFun1.CreateMaxNo("SpecCode",
					PubFun.getNoLimit(mGlobalInput.ComCode)));
			tLCCSpecSchema.setMakeDate(tNowDate);
			tLCCSpecSchema.setMakeTime(tNowTime);
			tLCCSpecSchema.setModifyDate(tNowDate);
			tLCCSpecSchema.setModifyTime(tNowTime);
			//tongmeng 2009-05-09 modify
			//如果员工特约有值,就不再新增了...
			String tContNo = tLCCSpecSchema.getContNo();
			//yg001
			if(tLCCSpecSchema.getSpecCode()!=null&&tLCCSpecSchema.getSpecCode().equals("yg001"))
			{
			String tSQL = "select count(*) from lccspec a where contno  ='"+"?contno?"+"' and speccode = 'yg001'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("contno", tContNo);
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(sqlbv1);
			if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
			{
				return true;
			}
			}
			map.put(tLCCSpecSchema, "INSERT");
			if(!prepareContUW())
				return false;
		}else if(mOperate.equals("UPDATE"))
		{
			logger.debug("流水号为（应该有值）："+mLCCSpecSchema.getSerialNo());
			tLCCSpecDB.setContNo(mLCCSpecSchema.getContNo());
			tLCCSpecDB.setProposalContNo(mLCCSpecSchema.getProposalContNo());
			tLCCSpecDB.setSerialNo(mLCCSpecSchema.getSerialNo());
			tLCCSpecDB.getInfo();
			tLCCSpecDB.setModifyDate(tNowDate);
			tLCCSpecDB.setModifyTime(tNowTime);
			tLCCSpecDB.setSpecContent(mLCCSpecSchema.getSpecContent());
			tLCCSpecDB.setOperator(mLCCSpecSchema.getOperator());
			tLCCSpecSchema = tLCCSpecDB.getSchema();
			map.put(tLCCSpecSchema, "UPDATE");
			if(!prepareContUW())
				return false;
		}
		else if(this.mOperate.equals("DELETE&&YGSPEC"))
		{
			String tContNo = (String)this.mTransferData.getValueByName("ContNo");
			String tSQL = "delete from lccspec where contno  ='"+"?contno?"+"' and speccode = 'yg001'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("contno", tContNo);
			map.put(sqlbv2, "DELETE");
		}
		
		return true;
	}
	
	/**
	 * preparePrt 准备核保主表数据
	 * 
	 * @return boolean
	 */
	private boolean prepareContUW() {

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		tLCCUWMasterDB.setContNo(mLCCSpecSchema.getContNo());
		if (!tLCCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "LCCUWMaster表取数失败!");
			return false;
		}
		tLCCUWMasterSchema.setSchema(tLCCUWMasterDB);
		tLCCUWMasterSchema.setSpecFlag("1");
		tLCCUWMasterSchema.setSpecReason(mLCCUWMasterSchema.getSpecReason());
		tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		tLCCUWMasterSchema.setOperator(mOperator);
		tLCCUWMasterSchema.setManageCom(mManageCom);

		// 每次进行核保相关操作时，向核保轨迹表插一条数据
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(tLCCUWMasterSchema.getContNo());
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "LCCUWSub表取数失败!");
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
			tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
			tLCCUWSubSchema.setOperator(mOperator); // 操作员
			tLCCUWSubSchema.setManageCom(mManageCom);
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			CError.buildErr(this, "LCCUWSub表取数失败!");
			return false;
		}
		
		map.put(tLCCUWMasterSchema, "UPDATE");
		map.put(tLCCUWSubSchema, "INSERT");
		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
