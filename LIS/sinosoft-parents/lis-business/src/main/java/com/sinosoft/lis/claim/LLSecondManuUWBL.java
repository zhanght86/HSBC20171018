package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LLCUWBatchDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLCUWSubDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LLCUWBatchSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLCUWSubSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LLCUWSubSet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔人工核保
 * </p>
 * <p>
 * Description: 在人工核保时为总单下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class LLSecondManuUWBL {
private static Logger logger = Logger.getLogger(LLSecondManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mG = new GlobalInput();
	private LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();
	// 保单表
	LCContSchema mLCContSchema = new LCContSchema();
	private MMap map = new MMap();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	/** 相关的变量 */
	private String mUWFlag;

	/** 业务类操作 */
	public LLSecondManuUWBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 主函数------主要用于 设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		LLCUWMasterSchema ttLLCUWMasterSchema = new LLCUWMasterSchema();
		ttLLCUWMasterSchema.setCaseNo("90000013979");
		ttLLCUWMasterSchema.setBatNo("550000000010339");
		ttLLCUWMasterSchema.setContNo("NJD10422651000614");
		ttLLCUWMasterSchema.setPassFlag("9");
		ttLLCUWMasterSchema.setUWIdea("标准承保------2005-09-11");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(ttLLCUWMasterSchema);

		LLSecondManuUWBL tLLSecondManuUWBL = new LLSecondManuUWBL();
		if (tLLSecondManuUWBL.submitData(tVData, "") == false) {
			logger.debug("---------发生错误---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
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

		// 提交数据

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象，获得全局公共数据
		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mLLCUWMasterSchema = (LLCUWMasterSchema) cInputData
				.getObjectByObjectName("LLCUWMasterSchema", 0);
		mInputData = cInputData;
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受公共登陆数据信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLLCUWMasterSchema == null) {
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传入核保数据信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 输入核保代码的正确性
		mUWFlag = mLLCUWMasterSchema.getPassFlag();
		if (!mUWFlag.equals("1") && !mUWFlag.equals("2")
				&& !mUWFlag.equals("4") && !mUWFlag.equals("9")&& !mUWFlag.equals("3")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWConfirmBL";
			tError.functionName = "CheckData";
			tError.errorMessage = "请输入正确的核保结论代码!";
			this.mErrors.addOneError(tError);
			return false;

		}

		// 所有校验暂时不做---以后补上(体检通知书、问卷通知书、索要资料通知书)的回销检测
		// 校验是否有未打印的体检通知书----暂时不做

		// (CaseNo--分案号、BatNo--批次号、ContNo--合同号、PassFlag---结论、UWIdea---核保意见)由前台传入

		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLLCUWMasterSchema.getContNo());// 合同号
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());// 合同表---用于提取数据

		// 检验险种结论是否完成，根据合同号，从个人险种表---LCPol 取出该合同下所有 保单险种号码---PolNo
		// 然后以“分案号 and 保单险种号码”查询 个人理陪险种核保最近结果表--- LLUWMaster 是否存在记录
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLLCUWMasterSchema.getContNo());// 合同号
		tLCPolDB.setAppFlag("1");// ,附加险只显示最近一期的记录<过滤条件>
		tLCPolSet.set(tLCPolDB.query());
		logger.debug("-----------该合同下保单险种号码数目---" + tLCPolSet.size());

		String tstate = "complete";// 险种结论全部下完标志----自定义
		String struncomplete = "该合同下未下核保结论的保单险种号码:";
		LLUWMasterSet tLLUWMasterSet = new LLUWMasterSet();
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();

			tLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
			tLLUWMasterDB.setBatNo(mLLCUWMasterSchema.getBatNo());
			tLLUWMasterDB.setPolNo(tLCPolSet.get(i).getPolNo());
			tLLUWMasterDB.setPassFlag("5");
			tLLUWMasterSet.set(tLLUWMasterDB.query());

			if (tLLUWMasterSet.size() > 0) {
				struncomplete = struncomplete + " / "
						+ tLCPolSet.get(i).getPolNo();
				tstate = "uncomplete";
			}
		}

		if (tstate.equals("uncomplete")) {
			logger.debug("************该合同下还未下核保结论 的险种************");
			logger.debug(struncomplete);
			CError tError = new CError();
			tError.moduleName = "LLSecondManuUWBL";
			tError.functionName = "dealtData";
			tError.errorMessage = struncomplete;
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * 根据个人下险种的核保结论，判断总单的核保结论
		 * 个单：1-拒保,2－延期,3－加费承保,4－特约承保,9－正常承保,,b-不自动续保（针对附加险的）
		 * 总单：1-拒保，2－延期，4-非标准体，9-标准体
		 * 
		 * 
		 * 主险 附加险 核保结论 标准承保 标准承保 标准体 拒保 * 拒保 延期 * 延期
		 * 
		 */

		int aUWFlag1 = 0; // 拒保
		int aUWFlag2 = 0; // 延期
		int aUWFlag3 = 0; // 加费
		int aUWFlag4 = 0; // 特约
		int aUWFlag9 = 0; // 正常承保
		int aUWFlagc = 0; // 维持原条件

		int bUWFlag1 = 0; // 拒保
		int bUWFlag2 = 0; // 延期
		int bUWFlag3 = 0; // 加费
		int bUWFlag4 = 0; // 特约
		int bUWFlag9 = 0; // 正常承保
		int bUWFlagc = 0; // 维持原条件
		
		int aCount = 0;
		int bCount = 0;

		// 准备险种保单的核保标志
		LLUWMasterSet aLLUWMasterSet = new LLUWMasterSet();
		LLUWMasterDB aLLUWMasterDB = new LLUWMasterDB();

		aLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
		aLLUWMasterDB.setBatNo(mLLCUWMasterSchema.getBatNo());
		aLLUWMasterDB.setContNo(mLLCUWMasterSchema.getContNo());
		// aLLUWMasterSet.set(aLLUWMasterDB.query());Modify by zhaorx 2006-10-12

		for (int i = 1; i <= tLCPolSet.size(); i++) {
			aLLUWMasterDB.setPolNo(tLCPolSet.get(i).getPolNo());
			if(!aLLUWMasterDB.getInfo()){
				CError.buildErr(this, "获得lluwmaster数据失败！");
				return false;
			}
			LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
			tLLUWMasterSchema = aLLUWMasterDB.getSchema();
			// 主险核保结论
			if (tLCPolSet.get(i).getMainPolNo().equals(
					tLCPolSet.get(i).getPolNo())) {
				aCount++;
				if (tLLUWMasterSchema.getPassFlag().equals("1")) {
					aUWFlag1++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("2")) {
					aUWFlag2++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("3")) {
					aUWFlag3++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("4")) {
					aUWFlag4++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("9")) {
					aUWFlag9++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("c")) {
					aUWFlagc++;
				}

			} else {
				bCount++;
				if (tLLUWMasterSchema.getPassFlag().equals("1")) {
					bUWFlag1++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("2")) {
					bUWFlag2++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("3")) {
					bUWFlag3++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("4")) {
					bUWFlag4++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("9")) {
					bUWFlag9++;
				} else if (tLLUWMasterSchema.getPassFlag().equals("c")) {
					bUWFlagc++;
				}

			}

		}
		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		logger.debug("aUWFlag1==" + aUWFlag1);
		logger.debug("aUWFlag2==" + aUWFlag2);
		logger.debug("aUWFlag3==" + aUWFlag3);
		logger.debug("aUWFlag4==" + aUWFlag4);
		logger.debug("aUWFlag9==" + aUWFlag9);
		logger.debug("aUWFlagc==" + aUWFlagc);

		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		logger.debug("bUWFlag1==" + bUWFlag1);
		logger.debug("bUWFlag2==" + bUWFlag2);
		logger.debug("bUWFlag3==" + bUWFlag3);
		logger.debug("bUWFlag4==" + bUWFlag4);
		logger.debug("bUWFlag9==" + bUWFlag9);
		logger.debug("bUWFlagc==" + bUWFlagc);

		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		/**
		 * 进行总单结论判断
		 */

		if (aUWFlag3 > 0  || bUWFlag3 > 0 || aUWFlag4 > 0 || bUWFlag4 > 0) {
			// 如果没有主险拒保延期
			if ((aUWFlag1 == 0) && (aUWFlag2 == 0)) {
				if (!mUWFlag.equals("4")) {
					CError tError = new CError();
					tError.moduleName = "UWRReportAfterInitService";
					tError.functionName = "checkData";
					tError.errorMessage = "险种保单下有非“标准承保”结论，总单只可以下“非标准体”核保结论！";
					this.mErrors.addOneError(tError);
					return false;

				}
			}
		}
		if (aUWFlag9 == aCount && bUWFlag9 == bCount) {
			if (!mUWFlag.equals("9")) {
				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "checkData";
				tError.errorMessage = "险种保单下没有“非标准承保”结论，总单只可以下“标准体”核保结论！";
				this.mErrors.addOneError(tError);
				return false;

			}

		}

		if (aUWFlag1 > 0) {
			if (mUWFlag == null || !(mUWFlag.equals("1"))) {

				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "checkData";
				tError.errorMessage = "主险“拒保”，总单必须下“拒保”核保结论！";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		if (aUWFlag2 > 0) {
			if (mUWFlag == null || !(mUWFlag.equals("2"))) {

				CError tError = new CError();
				tError.moduleName = "UWRReportAfterInitService";
				tError.functionName = "checkData";
				tError.errorMessage = "主险“延期”，总单必须下“延期”核保结论！";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		int tUWNo = 0;// 核保顺序号----计算得出
		// 个人合同理赔核保核保轨迹表（LLCUWSub）
		LLCUWSubSet tLLCUWSubSet = new LLCUWSubSet();
		LLCUWSubDB tLLCUWSubDB = new LLCUWSubDB();
		tLLCUWSubDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
		tLLCUWSubDB.setBatNo(mLLCUWMasterSchema.getBatNo());
		tLLCUWSubDB.setContNo(mLLCUWMasterSchema.getContNo());
		tLLCUWSubSet.set(tLLCUWSubDB.query());
		if (tLLCUWSubSet.size() == 0) {
			tUWNo = 1; /* 核保顺序 */
		} else {
			tUWNo = tLLCUWSubSet.size() + 1;
		}
		logger.debug("-----------核保顺序---" + tUWNo);

		// 准备个人理陪险种核保最近结果表----[LLCUWMaster]
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
		tLLCUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
		tLLCUWMasterDB.setContNo(mLLCUWMasterSchema.getContNo());
		tLLCUWMasterDB.getInfo();
		tLLCUWMasterSchema.setSchema(mLLCUWMasterSchema);
		tLLCUWMasterSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		tLLCUWMasterSchema.setProposalContNo(mLCContSchema.getProposalContNo());
		tLLCUWMasterSchema.setUWNo(tUWNo);// 核保顺序号----计算得出
		tLLCUWMasterSchema.setInsuredNo(mLCContSchema.getInsuredNo());
		tLLCUWMasterSchema.setInsuredName(mLCContSchema.getInsuredName());
		tLLCUWMasterSchema.setAppntNo(mLCContSchema.getAppntNo());
		tLLCUWMasterSchema.setAppntName(mLCContSchema.getAppntName());
		tLLCUWMasterSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLLCUWMasterSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLLCUWMasterSchema.setChangePolFlag(tLLCUWMasterDB.getChangePolFlag());
		tLLCUWMasterSchema.setChangePolReason(tLLCUWMasterDB.getChangePolReason());
		tLLCUWMasterSchema.setOperator(mG.Operator);
		tLLCUWMasterSchema.setMakeDate(mCurrentDate);
		tLLCUWMasterSchema.setMakeTime(mCurrentTime);
		tLLCUWMasterSchema.setModifyDate(mCurrentDate);
		tLLCUWMasterSchema.setModifyTime(mCurrentTime);
		map.put(tLLCUWMasterSchema, "DELETE&INSERT");

		// 个人合同理赔核保核保轨迹表（LLCUWSub）-----数据结构与（LLCUWMaster）相同，由LLCUWMaster表拷贝得出
		LLCUWSubSchema tLLCUWSubSchema = new LLCUWSubSchema();
		Reflections tRef = new Reflections(); // [声明复制表的数据的对象]
		// [LLCUWMaster(个人合同核保最近结果表)---->LLCUWSub(个人合同核保核保轨迹表)]
		tRef.transFields(tLLCUWSubSchema, tLLCUWMasterSchema);
		map.put(tLLCUWSubSchema, "INSERT");

		// 修改 个人理赔合同批次表（LLCUWBatch）
		LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
		LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
		tLLCUWBatchDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
		tLLCUWBatchDB.setBatNo(mLLCUWMasterSchema.getBatNo());
		tLLCUWBatchDB.setContNo(mLLCUWMasterSchema.getContNo());
		if(!tLLCUWBatchDB.getInfo()){
			CError.buildErr(this, "查询个人理赔合同批次表失败！");
			return false;
		}
		tLLCUWBatchSchema.setSchema(tLLCUWBatchDB.getSchema());
		// tLLCUWBatchSchema.setState("1");//状态 1---完成
		tLLCUWBatchSchema.setPassFlag(mLLCUWMasterSchema.getPassFlag());// 核保结论
		tLLCUWBatchSchema.setUWIdea(mLLCUWMasterSchema.getUWIdea());// 核保意见
		tLLCUWBatchSchema.setAutoUWFlag("2");// 是否自动核保:1 ---自动核保;2 ---人工核保
		// tLLCUWBatchSchema.setOperator(mG.Operator);
		tLLCUWBatchSchema.setModifyDate(mCurrentDate);
		tLLCUWBatchSchema.setModifyTime(mCurrentTime);
		map.put(tLLCUWBatchSchema, "DELETE&INSERT");

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
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	private void jbInit() throws Exception {
	}

}
