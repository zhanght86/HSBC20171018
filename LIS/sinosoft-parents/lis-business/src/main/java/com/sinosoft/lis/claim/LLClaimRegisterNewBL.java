/**
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAccidentSubSchema;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.schema.LLAppClaimReasonSchema;
import com.sinosoft.lis.schema.LLCaseRelaSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLReportReasonSchema;
import com.sinosoft.lis.schema.LLReportRelaSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLReportAffixSet;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.lis.vschema.LLSubReportSet;
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
 * Description: 理赔立案业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterNewBL {
private static Logger logger = Logger.getLogger(LLClaimRegisterNewBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	
	// 事件相关
	private LLAccidentSchema mLLAccidentSchema = null;
	private LLAccidentSubSchema mLLAccidentSubSchema = null;

	// 立案相关
	private LLRegisterSchema mLLRegisterSchema = null;
	private LLCaseSchema mLLCaseSchema = null;
	private LLCaseSet mLLCaseSet = null;
	private LLAppClaimReasonSet mLLAppClaimReasonSet = null;
	private LLAppClaimReasonSchema mLLAppClaimReasonSchema = null;
	private LLAffixSet mLLAffixSet = null;
	private LLAffixSchema mLLAffixSchema=null;

    //报案相关
	private LLReportSchema mLLReportSchema = null;
	private LLSubReportSchema mLLSubReportSchema = null;
	private LLSubReportSet mLLSubReportSet = null;
	private LLReportRelaSchema mLLReportRelaSchema = null;
	private LLCaseRelaSchema mLLCaseRelaSchema = null;
	private LLReportReasonSchema mLLReportReasonSchema = null;
	private LLReportReasonSet mLLReportReasonSet = null;
	private LLReportAffixSet mLLReportAffixSet = null;
	


	// 流水号类型,详细见SysMaxNo
	private String mRptNo = "";// 报案号
	private String mAccNo = "";// 事件号
	private String mRgtNo = "";// 立案号
	
	private String mIsAccExist="";//事件信息是否存在标志
	private boolean mIsRegisterExist;//立案信息是否存在标志
	private boolean mIsReportExist;//报案信息是否存在标志


	public LLClaimRegisterNewBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimRegisterBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		
		logger.debug("----------LLClaimRegisterBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		
		logger.debug("----------LLClaimRegisterBL after checkInputData----------");
		
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterBL after prepareOutputData----------");

		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		
		logger.debug("---LLClaimRegisterBL start getInputData()...");
		
		//初始化		
		mLLAccidentSchema=new LLAccidentSchema();
		mLLRegisterSchema=new LLRegisterSchema();
		mLLCaseSchema=new LLCaseSchema();
		mLLAppClaimReasonSet=new LLAppClaimReasonSet();
		
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mLLAccidentSchema = (LLAccidentSchema) mInputData
				.getObjectByObjectName("LLAccidentSchema", 0);
		mLLRegisterSchema = (LLRegisterSchema) mInputData
				.getObjectByObjectName("LLRegisterSchema", 0);
		mLLCaseSchema = (LLCaseSchema) mInputData.getObjectByObjectName(
				"LLCaseSchema", 0);
		mLLAppClaimReasonSet = (LLAppClaimReasonSet) mInputData
				.getObjectByObjectName("LLAppClaimReasonSet", 0);
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		logger.debug("----------begin checkInputData----------");
		
		if (mLLRegisterSchema == null && mLLCaseSchema == null&& mLLAccidentSchema == null&& mLLAppClaimReasonSet == null) {
			// @@错误处理
			CError.buildErr(this, "传入信息为空");
			return false;
		}
		
		logger.debug("----------end checkInputData----------");

		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("---LLClaimRegisterNewBL start dealData()...");
		// ----------------------------------------------------------------------Beg
		// 功能：判断事件、报案、立案信息是否存在
		// 处理：1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。
		// 2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
		// ----------------------------------------------------------------------
		IsExist();
		// ----------------------------------------------------------------------End
		
		// ----------------------------------------------------------------------
		// 功能：添加纪录
		// 处理：1 报案不存在则新建所有
		// 2 报案存在。则判断立案，立案不存在则同步所有报案信息
		// 立案存在则只更新立案分案信息
		// ----------------------------------------------------------------------
		
			// 报案不存在
			if (!mIsReportExist) {
				logger.debug("---报案不存在:");

				// 处理报案相关表，处理完返回的数据打包到map
//				if (!addReport()) {
//					// @@错误处理
//					CError.buildErr(this, "准备报案数据失败!");
//					return false;
//				}

				// 重新生成立案号
				createRgtno();
							
				mLLRegisterSchema.setRgtNo(mRgtNo); // 立案号
				mLLRegisterSchema.setRgtObj("3"); // 号码类型  1-个险报案号 2-团险报案号 3-个险立案号
				mLLRegisterSchema.setRgtObjNo(mRgtNo); // 立案号
				mLLRegisterSchema.setClmState("20");//理赔状态
				mLLRegisterSchema.setRgtDate(PubFun.getCurrentDate());
				mLLRegisterSchema.setRgtSources("0"); //立案来源 1--有扫描立案 0--无扫描立案
				mLLRegisterSchema.setOperator(mG.Operator);
				mLLRegisterSchema.setMngCom(mG.ManageCom);
				mLLRegisterSchema.setMakeDate(PubFun.getCurrentDate());
				mLLRegisterSchema.setMakeTime(PubFun.getCurrentTime());
				mLLRegisterSchema.setModifyDate(mLLRegisterSchema.getMakeDate());
				mLLRegisterSchema.setModifyTime(mLLRegisterSchema.getMakeTime());

				// 立案分案表
				mLLCaseSet =new LLCaseSet();
				mLLCaseSchema.setCaseNo(mLLRegisterSchema.getRgtNo()); // 赔案号
				mLLCaseSchema.setRgtNo(mLLRegisterSchema.getRgtNo()); // 赔案号
				mLLCaseSchema.setRgtType("11"); // 案件类型，普通案件
				mLLCaseSchema.setRgtState("20"); // 案件状态：20-立案
				mLLCaseSchema.setAffixConclusion("0");// 单证齐全标志
				mLLCaseSchema.setEditFlag("0");// 重要信息修改标志
				mLLCaseSchema.setRgtDate(mLLRegisterSchema.getRgtDate());
				mLLCaseSchema.setOperator(mG.Operator);
				mLLCaseSchema.setMngCom(mG.ManageCom);
				mLLCaseSchema.setMakeDate(mLLRegisterSchema.getMakeDate());
				mLLCaseSchema.setMakeTime(mLLRegisterSchema.getMakeTime());
				mLLCaseSchema.setModifyDate(mLLRegisterSchema.getModifyDate());
				mLLCaseSchema.setModifyTime(mLLRegisterSchema.getModifyTime());
				mLLCaseSet.add(mLLCaseSchema);

				// 理赔类型表(多条添加)
				for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
					mLLAppClaimReasonSet.get(i).setCaseNo(mLLRegisterSchema.getRgtNo());
					mLLAppClaimReasonSet.get(i).setRgtNo(mLLRegisterSchema.getRgtNo());
					mLLAppClaimReasonSet.get(i).setOperator(mG.Operator);
					mLLAppClaimReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLAppClaimReasonSet.get(i).setMakeDate(mLLRegisterSchema.getMakeDate());
					mLLAppClaimReasonSet.get(i).setMakeTime(mLLRegisterSchema.getMakeTime());
					mLLAppClaimReasonSet.get(i).setModifyDate(mLLRegisterSchema.getModifyDate());
					mLLAppClaimReasonSet.get(i).setModifyTime(mLLRegisterSchema.getModifyTime());
				}
				
				//生成事件号
				String AccNo = PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号
				AccNo = "8" + AccNo;
				logger.debug("-----生成事件号= " + AccNo);
				// 事件表
				mLLAccidentSchema.setAccNo(AccNo); // 事件号
				mLLAccidentSchema.setOperator(mG.Operator);
				mLLAccidentSchema.setMngCom(mG.ManageCom);
				mLLAccidentSchema.setMakeDate(PubFun.getCurrentDate());
				mLLAccidentSchema.setMakeTime(PubFun.getCurrentTime());
				mLLAccidentSchema.setModifyDate(PubFun.getCurrentDate());
				mLLAccidentSchema.setModifyTime(PubFun.getCurrentTime());
				
				
				// 分事件表
				mLLAccidentSubSchema =new LLAccidentSubSchema();
				mLLAccidentSubSchema.setAccNo(AccNo); // 事件号
				mLLAccidentSubSchema.setCustomerNo(mLLCaseSchema.getCustomerNo()); // 出险人编码

				mLLCaseRelaSchema = new LLCaseRelaSchema();
				mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSchema.getAccNo()); // 事件号
				mLLCaseRelaSchema.setCaseNo(mLLRegisterSchema.getRgtNo()); //  立案号
				mLLCaseRelaSchema.setSubRptNo(mLLRegisterSchema.getRgtNo()); //  立案号
				
				//立案信息
				mResult.add(mLLRegisterSchema);//立案信息
				mResult.add(mLLCaseSet);//立案分案信息
				mResult.add(mLLAppClaimReasonSet);//报案理赔类型
				mResult.add(mLLAffixSet);//报案附件表
				
				map.put(mLLRegisterSchema, "INSERT");
				map.put(mLLCaseSet, "INSERT");
				map.put(mLLAppClaimReasonSet, "INSERT");
				map.put(mLLAffixSet, "INSERT");
				map.put(mLLAccidentSchema, "INSERT");
				map.put(mLLAccidentSubSchema, "INSERT");
				map.put(mLLCaseRelaSchema, "INSERT");
			}			
		
		// 更新mTransferData中的值
		if (!perpareMissionProp()) {
			// @@错误处理
			CError.buildErr(this, "为工作流准备数据失败!");
			return false;
		}

		return true;
	}

	/**
	 * 判断事件、报案、立案信息是否存在
	 * 
	 * @todo 1 事件号为空，则报案、立案都不存在；事件号不为空，则判断报案号。 2 报案号为空，则立案不存在；报案号不为空，则查询立案是否存在。
	 */
	private void IsExist() {
		// 事件是否存在
		if (mLLAccidentSchema.getAccNo().equals("")
				|| mLLAccidentSchema.getAccNo() == null) {
			mIsAccExist = "false";
			mIsReportExist = false;
			mIsRegisterExist = false;
		} else {
			// 事件存在
			mIsAccExist = "true";
			mAccNo = mLLAccidentSchema.getAccNo();
			logger.debug("-----已有事件号= " + mAccNo);
			if (mLLRegisterSchema.getRgtNo().equals("")
					|| mLLRegisterSchema.getRgtNo() == null) {
				// 报案不存在
				mIsReportExist = false;
				mIsRegisterExist = false;
			} else {
				// 报案存在
		//      mIsReportExist = true;
				mIsReportExist = false;
				mRptNo = mLLRegisterSchema.getRgtNo();
				logger.debug("-----已有赔案号= " + mRptNo);
				// 查询判断立案是否存在
				String strSql = "select RgtNo from LLRegister where "
						+ "RgtNo = '" + "?RgtNo?" + "'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(strSql);
				sqlbv.put("RgtNo", mLLRegisterSchema.getRgtNo());
				ExeSQL exesql = new ExeSQL();
				String tResult = exesql.getOneValue(sqlbv);
				if (tResult.length() > 0) {
					mIsRegisterExist = true;
				} else {
					mIsRegisterExist = false;
				}
			}
		}
	}

	/**
	 * 创建新的立案号(赔案号)
	 */
	private void createRgtno() {
		
		logger.debug("开始创建立案号(赔案号)");
		String strlimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strlimit);
		mRgtNo = PubFun1.CreateMaxNo("RGTNO", strlimit);
		logger.debug("-----生成立案号= " + mRgtNo);
	}

	/**
	 * 处理报案信息
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean addReport() {
		
		logger.debug("---LLClaimRegisterBL Start addReport:" + mRptNo);
		
		//准备数据
		LLReportBL tLLReportBL = new LLReportBL();
		LLAccidentSchema tLLAccidentSchema=new LLAccidentSchema();
		VData tVData = new VData();
		
		// 获取报案页面信息
		tLLAccidentSchema.setAccNo(mAccNo); // 事件号(新事件时为空)
		tLLAccidentSchema.setAccDate(mLLRegisterSchema.getAccidentDate()); // 意外事故发生日期
		tLLAccidentSchema.setAccType(mLLRegisterSchema.getAccidentReason()); // 事故类型===出险原因
		tLLAccidentSchema.setAccPlace((mLLRegisterSchema.getAccidentSite()));// 出险地点
		tLLAccidentSchema.setAccDesc(mLLCaseSchema.getAccdentDesc());// 事故描述
		
		mLLReportSchema = new LLReportSchema();
		mLLReportSchema.setRptNo(mRptNo); // 报案号=赔案号
		mLLReportSchema.setRptorName(mLLRegisterSchema.getRgtantName()); // 报案人姓名
		mLLReportSchema.setRptorPhone(mLLRegisterSchema.getRgtantPhone()); // 报案人电话
		mLLReportSchema.setRptorAddress(mLLRegisterSchema.getRgtantAddress()); // 报案人通讯地址
		mLLReportSchema.setRelation(mLLRegisterSchema.getRelation()); // 报案人与事故人关系
		mLLReportSchema.setRptMode("05"); // 报案方式(立案没有，故置为其他方式)
		mLLReportSchema.setRgtObj("1");//号码类型:1个险报案号,2团险报案号
		mLLReportSchema.setRgtObjNo(mLLReportSchema.getRptNo());//备份报案号
		mLLReportSchema.setRemark(mLLRegisterSchema.getRemark());//备注信息
		
		mLLReportSchema.setAccidentSite(mLLRegisterSchema.getAccidentSite()); // 出险地点
		mLLReportSchema.setAccidentDate(mLLRegisterSchema.getAccidentDate()); // 意外事故发生日期
		mLLReportSchema.setAccidentReason(mLLRegisterSchema.getAccidentReason()); // 出险原因
		mLLReportSchema.setRgtFlag("30");// 立案标志(来自立案)
		mLLReportSchema.setRgtClass(mLLRegisterSchema.getRgtClass()); // 团单个单
		mLLReportSchema.setRgtState(mLLRegisterSchema.getRgtState());//案件类型:11-普通案件
		mLLReportSchema.setPeoples2(mLLRegisterSchema.getPeoples2());//出险人数
		
		mLLSubReportSchema=new LLSubReportSchema();//报案分案信息
		mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=报案号=赔案号
		mLLSubReportSchema.setCustomerNo(mLLCaseSchema.getCustomerNo()); // 出险人编码
		mLLSubReportSchema.setCustomerName(mLLCaseSchema.getCustomerName());//出险人姓名
		mLLSubReportSchema.setSex(mLLCaseSchema.getCustomerSex());//出险人性别
		
		mLLSubReportSchema.setMedAccDate(mLLCaseSchema.getMedAccDate());//医疗出险日期
		mLLSubReportSchema.setAccDate(mLLCaseSchema.getAccDate()); //其他出险日期
		mLLSubReportSchema.setHospitalCode(mLLCaseSchema.getHospitalCode()); // 治疗医院
		mLLSubReportSchema.setHospitalName(mLLCaseSchema.getHospitalName());//治疗医院名称
		mLLSubReportSchema.setAccidentDetail(mLLCaseSchema.getAccidentDetail()); // 出险细节
		
		// mLLSubReportSchema.setDieFlag(mLLCaseSchema.getDieFlag()); //死亡标志
		mLLSubReportSchema.setCureDesc(mLLCaseSchema.getCureDesc()); // 治疗情况
		mLLSubReportSchema.setSeqNo(mLLCaseSchema.getSeqNo());//序号
		
		mLLSubReportSchema.setRemark("直接立案自动补充报案数据"); // 备注
		mLLSubReportSchema.setAccDesc(mLLCaseSchema.getAccdentDesc());
		mLLSubReportSchema.setAccResult1(mLLCaseSchema.getAccResult1());// ICD10主代码
		mLLSubReportSchema.setAccResult2(mLLCaseSchema.getAccResult2());// ICD10子代码

		mLLReportReasonSet =new LLReportReasonSet();
		for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
			
			mLLReportReasonSchema = new LLReportReasonSchema();
			
			mLLReportReasonSchema.setRpNo(mLLReportSchema.getRptNo()); // 报案号=赔案号
			mLLReportReasonSchema.setCustomerNo(mLLAppClaimReasonSet.get(i).getCustomerNo()); // 出险人编码
			mLLReportReasonSchema.setReasonCode(mLLAppClaimReasonSet.get(i).getReasonCode()); // 理赔类型
			
			mLLReportReasonSet.add(mLLReportReasonSchema);
			mLLReportReasonSchema=null;
		}


	    

		tVData.add(mG);
	    tVData.add(mTransferData);
		tVData.add(mLLAccidentSchema);
		tVData.add(mLLReportSchema);
		tVData.add(mLLSubReportSchema);
		tVData.add(mLLReportReasonSet);
		
		

		if (!tLLReportBL.submitData(tVData, "INSERT")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLReportBL.mErrors);
			mResult.clear();
			tVData = null;
			return false;
		} 
		else {
			
			// 取得处理完的返回数据
			tVData.clear();
			tVData = tLLReportBL.getResult();
			
			//事件信息
			mLLAccidentSchema.setSchema((LLAccidentSchema)tVData.getObjectByObjectName("LLAccidentSchema", 0));
			
			//分事件
			mLLAccidentSubSchema =new LLAccidentSubSchema();
			mLLAccidentSubSchema.setSchema((LLAccidentSubSchema)tVData.getObjectByObjectName("LLAccidentSubSchema", 0));
			
			//报案信息
			mLLReportSchema.setSchema((LLReportSchema)tVData.getObjectByObjectName("LLReportSchema", 0));
			mRptNo = mLLReportSchema.getRptNo();
			logger.debug("返回生成的报案号:" + mRptNo);
			
			//报案分案信息
			mLLSubReportSet =new LLSubReportSet();
			mLLSubReportSchema.setSchema((LLSubReportSchema)tVData.getObjectByObjectName("LLSubReportSchema", 0));
			mLLSubReportSet.add(mLLSubReportSchema);

			//报案分案信息关联表
			mLLReportRelaSchema = new LLReportRelaSchema();
			mLLReportRelaSchema.setSchema((LLReportRelaSchema)tVData.getObjectByObjectName("LLReportRelaSchema", 0));
			
			//分案事件关联
			mLLCaseRelaSchema = new LLCaseRelaSchema();
			mLLCaseRelaSchema.setSchema((LLCaseRelaSchema)tVData.getObjectByObjectName("LLCaseRelaSchema", 0));
			
			logger.debug("mLLReportReasonSet3"+mLLReportReasonSet);
			
			//报案理赔类型		
			LLReportReasonSet tLLReportReasonSet=new LLReportReasonSet();
			tLLReportReasonSet=(LLReportReasonSet)tVData.getObjectByObjectName("LLReportReasonSet", 0);
			
			//报案信息
			mResult.add(mLLAccidentSchema);//事件信息
			mResult.add(mLLAccidentSubSchema);//分事件
			mResult.add(mLLReportSchema);//报案表
			mResult.add(mLLSubReportSet);//报案分案表
			mResult.add(mLLReportRelaSchema);//报案事件关联
			mResult.add(mLLCaseRelaSchema);//分案事件关联
			mResult.add(tLLReportReasonSet);//报案理赔类型
			mResult.add(mLLReportAffixSet);//报案附件
			
			
			tLLReportReasonSet=null;

		}
		
		//释放强引用
		tVData=null;
		tLLReportBL=null;
		tLLAccidentSchema=null;
		mLLReportSchema=null;
		mLLSubReportSchema=null;
		mLLSubReportSet=null;
		mLLReportReasonSet=null;
		
		logger.debug("---LLClaimRegisterBL End addReport");
		
		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String strNolimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit); // 生成印刷流水号
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mRptNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式

		// 打印状态：0都可用，1在线已打，2批打已打，3批打不打,4批打未开放
		tLOPRTManagerSchema.setStateFlag("3"); // 打印状态

		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate()); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime()); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(PubFun.getCurrentDate()); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(mLLCaseSchema.getCustomerNo()); // 客户号码
		// tLOPRTManagerSchema.setStandbyFlag5();

		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 更新mTransferData中的值，为工作流准备数据
	 * 
	 * @return boolean
	 */
	private boolean perpareMissionProp() {
		mTransferData.removeByName("RptNo");
		mTransferData.removeByName("RgtNo");
		mTransferData.setNameAndValue("RptNo", mRptNo);
		mTransferData.setNameAndValue("RgtNo", mRgtNo);
		mTransferData.setNameAndValue("IsAccExist", mIsAccExist);
		mTransferData.setNameAndValue("DefaultOperator", this.mG.Operator);//默认操作员
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			// 后台传送
			// 前台传
			mResult.add(mG);
			mResult.add(map);
			mResult.add(mTransferData);
			
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

}
