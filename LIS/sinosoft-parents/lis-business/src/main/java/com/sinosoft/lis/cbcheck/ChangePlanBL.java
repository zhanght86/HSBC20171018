package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 扫描件申请
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ChangePlanBL {
private static Logger logger = Logger.getLogger(ChangePlanBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 保单 */
	private LCPolSchema inLCPolSchema = new LCPolSchema();
	private LCPolSchema outLCPolSchema = new LCPolSchema();

	//tongmeng 2008-12-02 add
	//增加对险种核保轨迹的记录
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	
	public ChangePlanBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareOutputData---");

		logger.debug("Start ChangePlan BLS Submit...");
		if (mOperate.equals("INSERT||CHANGEPLAN"))
		{
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(this.mInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				return false;
			}
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
					"LCPolSchema", 0);
			this.mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this,"接收数据失败!!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		int i;

		try {
			LCPolDB tLCPolDB = new LCPolDB();

			// 查询出投保单信息
			tLCPolDB.setPolNo(inLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				return false;
			}

			// 查询投保单，获取两个状态
			if (mOperate.equals("QUERY||CHANGEPLAN")) {
				mResult.add(tLCPolDB.getApproveFlag());
				mResult.add(tLCPolDB.getUWFlag());
				mResult.add(tLCPolDB.getApproveCode());
				mResult.add(tLCPolDB.getApproveDate());

				// 初始化两个状态，使投保单能够更新
				tLCPolDB.setApproveFlag("0");
				tLCPolDB.setUWFlag("0");
			}
			// 更新两个状态
			else if (mOperate.equals("INSERT||CHANGEPLAN")) {
				tLCPolDB.setApproveFlag(inLCPolSchema.getApproveFlag());
				tLCPolDB.setUWFlag(inLCPolSchema.getUWFlag());
				tLCPolDB.setApproveCode(inLCPolSchema.getApproveCode());
				tLCPolDB.setApproveDate(inLCPolSchema.getApproveDate());
			}

			outLCPolSchema = tLCPolDB.getSchema();
			
			//tongmeng 2008-12-02 增加对核保相关数据表的维护/
			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setProposalNo(tLCPolDB.getProposalNo());
			if(!tLCUWMasterDB.getInfo())
			{
				CError.buildErr(this,"查询险种核保主表失败!");
				return false;
			}
			tLCUWMasterSchema.setSchema(tLCUWMasterDB.getSchema());
			tLCUWMasterSchema.setContNo(tLCPolDB.getContNo());
			tLCUWMasterSchema.setGrpContNo(tLCPolDB.getGrpContNo());
			tLCUWMasterSchema.setPolNo(tLCPolDB.getPolNo());
			tLCUWMasterSchema.setProposalContNo(tLCPolDB.getContNo());
			tLCUWMasterSchema.setProposalNo(tLCPolDB.getProposalNo());
		//	tLCUWMasterSchema.setUWNo(tLCUWMasterSchema.getUWNo()+1);
			tLCUWMasterSchema.setInsuredNo(tLCPolDB.getInsuredNo());
			tLCUWMasterSchema.setInsuredName(tLCPolDB.getInsuredName());
			tLCUWMasterSchema.setAppntNo(tLCPolDB.getAppntNo());
			tLCUWMasterSchema.setAppntName(tLCPolDB.getAppntName());
			tLCUWMasterSchema.setAgentCode(tLCPolDB.getAgentCode());
			tLCUWMasterSchema.setAgentGroup(tLCPolDB.getAgentGroup());
			

			tLCUWMasterSchema.setChangePolFlag("1");
			tLCUWMasterSchema.setOperator(this.mGlobalInput.Operator); // 操作员
			tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			
			this.mLCUWMasterSet.add(tLCUWMasterSchema);
			
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			//tLCUWSubDB.setProposalNo(tLCPolDB.getProposalNo());
			String sql = "select * from lcuwsub where ProposalNo='"+"?ProposalNo?"+"' order by uwno desc";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("ProposalNo", tLCPolDB.getProposalNo());
			logger.debug("11111111111111111"+sql);
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			//tLCUWSubSet = tLCUWSubDB.query();
			tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv);
			if(tLCUWSubSet==null||tLCUWSubSet.size()==0)
			{
				CError.buildErr(this,"查询险种核保子表失败!");
				return false;
			}
			//准备核保子表数据
			tLCUWSubSchema.setContNo(tLCPolDB.getContNo());
			tLCUWSubSchema.setPolNo(tLCPolDB.getPolNo());
			tLCUWSubSchema.setUWNo(tLCUWSubSet.get(1).getUWNo()+1);
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
			tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
					.getUpReportContent());
			tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
			tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
			tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
			tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
			tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
			tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema.getChangePolFlag());
			tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
					.getChangePolReason());
			tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema.getAddPremReason());
			tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
			tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
			tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
			tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
			tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
			tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			this.mLCUWSubSet.add(tLCUWSubSchema);
			
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this,"数据处理错误");
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			
			
		//	mInputData.add(outLCPolSchema);
			MMap map = new MMap();

			map.put(outLCPolSchema, "UPDATE");
			map.put(this.mLCUWMasterSet, "UPDATE");
			map.put(this.mLCUWSubSet, "INSERT");
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"在准备往后层处理所需要的数据时出错");
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ChangePlanBL ChangePlanBL1 = new ChangePlanBL();
	}
}
