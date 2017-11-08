package com.sinosoft.ibrms;

import java.util.logging.Logger;

import com.sinosoft.ibrms.bom.BOMConTr;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLClaimUserDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPSpotCheckTrackDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LYSendToBankBDB;
import com.sinosoft.lis.db.PD_IssueDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPSpotCheckTrackSchema;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.schema.LWProcessTransSchema;
import com.sinosoft.lis.schema.LYSendToBankBSchema;
import com.sinosoft.lis.schema.PD_IssueSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LLClaimUserSet;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPSpotCheckTrackSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LYSendToBankBSet;
import com.sinosoft.lis.vschema.PD_IssueSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BOMsqls {
	/**
	 * 根据条件生成BOMConTr
	 * 
	 * @return 查询结果
	 * @author 赵佳伟
	 */
	//private static Logger logger = Logger.getLogger(ExeSQL.class);
	/**
	 * mflag = true: 传入Connection mflag = false: 不传入Connection
	 */
	private boolean mflag = false;
	private FDate fDate = new FDate();
	public CErrors mErrors = new CErrors(); // 错误信息

	// @Constructor
	public BOMsqls() {
	}

	/**
	 * 获取唯BOM对象
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public BOMConTr getBom(LWProcessTransSchema tLWProcessTransSchema,
			VData tInputData, LWMissionDB tLWMissionDB, String[] SQLParameters) {
		BOMConTr ConTr = new BOMConTr();
		//BOMSearchSql SQL = new BOMSearchSql();
		String tMissionID = tLWMissionDB.getMissionID();
		// 获取传入数据
		GlobalInput tGlobalInput = (GlobalInput) tInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) tInputData
				.getObjectByObjectName("TransferData", 0);
		String tBusiType = (String) tTransferData.getValueByName("BusiType");
		String tBusiInDate = (String) tTransferData
				.getValueByName("BusiInDate");
		String tBusiInTime = (String) tTransferData
				.getValueByName("BusiInTime");
		/** 是否需要抽检 */
		String NeedCheckFlag = (String) tTransferData
				.getValueByName("NeedCheckFlag");
		/** 合同号 */
		String ContNo = (String) tTransferData.getValueByName("ContNo");
		if (ContNo == null) {
			ContNo = tLWMissionDB.getMissionProp1();
		}
		/** 印刷号 */
		String PrtNo = (String) tTransferData.getValueByName("PrtNo");
		if (PrtNo == null) {
			PrtNo = tLWMissionDB.getMissionProp2();
		}
		/** 活动ID **/
		String Activityid = tLWProcessTransSchema.getTransitionStart();
		/** 是否存在体检录入的标志，0为不存在不需要发放 否则为 */
		String SendPENoticeFlag = (String) tTransferData
				.getValueByName("SendPENoticeFlag");
		/** 发送生调通知书标志 */
		String SendReportNoticeFlag = (String) tTransferData
				.getValueByName("SendReportNoticeFlag");
		/** 发送打印核保通知书标志 */
		String UWSendFlag = (String) tTransferData.getValueByName("UWSendFlag");
		/** 预付标记 */
		String BudgetFlag = (String) tTransferData.getValueByName("BudgetFlag");
		/** 不通过需要审核 0 1 */
		String AuditFlag = (String) tTransferData.getValueByName("AuditFlag");
		/** 发送问题件修改岗标志 */
		String ApproveModifyFlag = (String) tTransferData
				.getValueByName("ApproveModifyFlag");
		/** 产品上市停售规则标记，如果不符合规则则置为1*/
		String ProductSaleFlag = (String) tTransferData
				.getValueByName("ProductSaleFlag");
		/** 发送机构问题件标志 */
		String QuesOrgFlag = (String) tTransferData
				.getValueByName("QuesOrgFlag");
		/** 打印管理表 **/
		String QueModFlag = (String) tTransferData.getValueByName("QueModFlag");
		String QueModFlag_UnPrint = (String) tTransferData
				.getValueByName("QueModFlag_UnPrint");
		String QueModFlag_Agent = (String) tTransferData
				.getValueByName("QueModFlag_Agent");
		/** 初审标志位$ **/
		boolean FirstTrialFlaog = Boolean.valueOf((String) tTransferData
				.getValueByName("FirstTrialFlaog"));
		String ReplyPENoticeFlag = (String) tTransferData
				.getValueByName("ReplyPENoticeFlag");
		/** 上报标志 */
		String UWUpReport = (String) tTransferData.getValueByName("UWUpReport");
		/** 判断问题件修改完毕是否该扭转到下一结点标记*/
		String OtherNoticeFlag = (String) tTransferData
				.getValueByName("OtherNoticeFlag");
		/** 是否有问题件 0-没有 1-有 */
		String mIssueFlag = (String) tTransferData.getValueByName("mIssueFlag");
		/** 审计标志*/
		String AuditingFlag = (String) tTransferData
				.getValueByName("AuditingFlag");
		/** 结束标志 */
		String FinishFlag = (String) tTransferData.getValueByName("FinishFlag");
		/** 发送业务员通知书标志*/
		String SendOperFlag = (String) tTransferData
				.getValueByName("SendOperFlag");
		/** 客户合并*/
		String customerUnion = (String) tTransferData
				.getValueByName("customerUnion");
		/** 是否有外部问题件 0-没有 1-有*/
		String OutIssueFlag = (String) tTransferData
				.getValueByName("OutIssueFlag");
		/** 生成标志*/
		String CreateFlag = (String) tTransferData.getValueByName("CreateFlag");
		/** 自动初次核保结果*/
		String PreUWFlag = (String) tTransferData.getValueByName("PreUWFlag");
		
		/**
		 * 将数据组成BOMConTr
		 */
		ConTr.setActivityid(Activityid);
		ConTr.setMissionid(tMissionID);
		ConTr.setNeedCheckFlag(NeedCheckFlag);
		ConTr.setSendPENoticeFlag(SendPENoticeFlag);
		ConTr.setSendReportNoticeFlag(SendReportNoticeFlag);
		ConTr.setUWSendFlag(UWSendFlag);
		ConTr.setBudgetFlag(BudgetFlag);
		ConTr.setAuditFlag(AuditFlag);
		ConTr.setApproveModifyFlag(ApproveModifyFlag);
		ConTr.setProductSaleFlag(ProductSaleFlag);
		ConTr.setQuesOrgFlag(QuesOrgFlag);
		ConTr.setQueModFlag(QueModFlag);
		ConTr.setQueModFlag_Agent(QueModFlag_Agent);
		ConTr.setQueModFlag_UnPrint(QueModFlag_UnPrint);
		ConTr.setFirstTrialFlaog(FirstTrialFlaog);
		ConTr.setReplyPENoticeFlag(ReplyPENoticeFlag);
		ConTr.setUWUpReport(UWUpReport);
		ConTr.setOtherNoticeFlag(OtherNoticeFlag);
		ConTr.setMIssueFlag(mIssueFlag);
		ConTr.setAuditingFlag(AuditingFlag);
		ConTr.setFinishFlag(FinishFlag);
		ConTr.setSendOperFlag(SendOperFlag);
		ConTr.setCustomerUnion(customerUnion);
		ConTr.setOutIssueFlag(OutIssueFlag);
		ConTr.setCreateFlag(CreateFlag);
		ConTr.setPreUWFlag(PreUWFlag);
		/**
		 * 查询个人保单表 获取ApproveFlag UWFlag InputTime
		 **/
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo(ContNo);
		LCContDB tLCContDB = tLCContSchema.getDB();
		LCContSet tLCContSet = tLCContDB.query();
		LCContSchema tLCContSchema1 = tLCContSet.get(1);
		String ApproveFlag = tLCContSchema1.getAppFlag();
		String UWFlag = tLCContSchema1.getUWFlag();
		String InputTime = tLCContSchema1.getInputTime();
		ConTr.setApproveFlag(ApproveFlag);
		ConTr.setUWFlag(UWFlag);
		ConTr.setInputTime(InputTime);

		/**
		 * 查询产品定义问题件 获取ISSUESTATE backpost riskcode
		 **//*
		if(tLWMissionDB.getMissionProp9()!= null||!tLWMissionDB.getMissionProp9().equals("")){
		PD_IssueSchema tPD_IssueSchema = new PD_IssueSchema();
		tPD_IssueSchema.setSerialNO(tLWMissionDB.getMissionProp9());
		PD_IssueDB TPD_IssueDB = tPD_IssueSchema.getDB();
		PD_IssueSet tPD_IssueSet = TPD_IssueDB.query();
		PD_IssueSchema tPD_IssueSchema1 = tPD_IssueSet.get(1);
		String ISSUESTATE = tPD_IssueSchema1.getIssueState();
		String backpost = tPD_IssueSchema1.getBackPost();
		String riskcode = tPD_IssueSchema1.getRiskCode();
		ConTr.setISSUESTATE(ISSUESTATE);
		ConTr.setBackpost(backpost);
		ConTr.setRiskcode(riskcode);
		}*/
		// 根据tBusiType分类获取数据。
		if(tBusiType!=null){
		// 个险新契约业务
		if (tBusiType.equals("1001")) {
			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			ExeSQL exe = new ExeSQL();
			tLWFieldMapSchema.setActivityID(Activityid);
			/**
			 * 查询个人合同核保核保轨迹表 获取AutoUWFlag
			 **/
			LCCUWSubSchema tLccuwSubSchema = new LCCUWSubSchema();
			tLccuwSubSchema.setContNo(ContNo);
			LCCUWSubDB tLCCUWSubDB = tLccuwSubSchema.getDB();
			LCCUWSubSet tLCCUWSubSet = tLCCUWSubDB.query();
			LCCUWSubSchema tLccuwSubSchema1 = tLCCUWSubSet.get(1);
			String AutoUWFlag = tLccuwSubSchema1.getAutoUWFlag();
			ConTr.setAutoUWFlag(AutoUWFlag);
			/**
			 * 查询个人合同特约信息表 获取SpecType
			 **/
			if(tLWMissionDB.getMissionProp9()!= null&&!tLWMissionDB.getMissionProp9().equals("")&&tLWMissionDB.getMissionProp6()!= null&&!tLWMissionDB.getMissionProp6().equals("")){
			LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
			tLCCSpecSchema.setProposalContNo(tLWMissionDB.getMissionProp6());
			tLCCSpecSchema.setSerialNo(tLWMissionDB.getMissionProp9());
			LCCSpecDB TLCCSpecDB = tLCCSpecSchema.getDB();
			LCCSpecSet tLCCSpecSet = TLCCSpecDB.query();
			LCCSpecSchema tLCCSpecSchema1 = tLCCSpecSet.get(1);
			String SpecType = tLCCSpecSchema1.getSpecType();
			ConTr.setSpecType(SpecType);
			}
			return ConTr;
		}
		// 个险保全业务
		if (tBusiType.equals("1002")) {
			LWFieldMapSchema ttLWFieldMapSchema = new LWFieldMapSchema();
			ttLWFieldMapSchema.setActivityID(Activityid);
			ttLWFieldMapSchema.setFieldOrder(1);
			LWFieldMapDB tLWFieldMapDB = ttLWFieldMapSchema.getDB();
			LWFieldMapSet tLWFieldMapSet = tLWFieldMapDB.query();
			LWFieldMapSchema tLWFieldMapSchema1 = tLWFieldMapSet.get(1);
			String SourFieldName = tLWFieldMapSchema1.getSourFieldName();
			if (SourFieldName.equals("EdorNo")) {
				/**
				 * 查询个人保全核保最近结果表 获取CustomerIdea
				 **/
				LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();
				tLPCUWMasterSchema.setEdorNo(tLWMissionDB.getMissionProp1());
				LPCUWMasterDB tLPCUWMasterDB = tLPCUWMasterSchema.getDB();
				LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
				LPCUWMasterSchema tLPCUWMasterSchema1 = tLPCUWMasterSet.get(1);
				String CustomerIdea = tLPCUWMasterSchema1.getCustomerIdea();
				ConTr.setCustomerIdea(CustomerIdea);
			}
			if (SourFieldName.equals("EdorAcceptNo")) {
				/**
				 * 查询保全申请主表 获取OtherNoType
				 **/
				LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
				tLPEdorAppSchema
						.setEdorAcceptNo(tLWMissionDB.getMissionProp1());
				LPEdorAppDB tLPEdorAppDB = tLPEdorAppSchema.getDB();
				LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
				LPEdorAppSchema tLPEdorAppSchema1 = tLPEdorAppSet.get(1);
				String OtherNoType = tLPEdorAppSchema1.getOtherNoType();
				ConTr.setCustomerIdea(OtherNoType);
				/**
				 * 查询保全抽检轨迹表2获取CheckFlag
				 **/
				LPSpotCheckTrackSchema tLPSpotCheckTrackSchema = new LPSpotCheckTrackSchema();
				tLPSpotCheckTrackSchema.setEdorAcceptNo(tLWMissionDB
						.getMissionProp1());
				LPSpotCheckTrackDB tLPSpotCheckTrackDB = tLPSpotCheckTrackSchema
						.getDB();
				LPSpotCheckTrackSet tLPSpotCheckTrackSet = tLPSpotCheckTrackDB
						.query();
				LPSpotCheckTrackSchema tLPSpotCheckTrackSchema1 = tLPSpotCheckTrackSet
						.get(0);
				String CheckFlag = tLPSpotCheckTrackSchema1.getCheckFlag();
				ConTr.setCheckFlag(CheckFlag);
			}
			/** 分保标志 1-分保0不分保$ */
			String ReDisMark = (String) tTransferData
					.getValueByName("ReDisMark");
			ConTr.setReDisMark(ReDisMark);
			return ConTr;
		}
		// 个险理赔
		if (tBusiType.equals("1003")) {
			/**
			 * 查询理赔用户权限表获取SimpleFlag
			 **/
			LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
			tLLClaimUserSchema.setUserCode(tGlobalInput.Operator);
			LLClaimUserDB tLLClaimUserDB = tLLClaimUserSchema.getDB();
			LLClaimUserSet tLLClaimUserSet = tLLClaimUserDB.query();
			LLClaimUserSchema tLLClaimUserSchema1 = tLLClaimUserSet.get(1);
			String SimpleFlag = tLLClaimUserSchema1.getSimpleFlag();
			ConTr.setSimpleFlag(SimpleFlag);
			/**
			 * 查询发送银行盘记录表获取DealType
			 **/
			LYSendToBankBSchema tLYSendToBankBSchema = new LYSendToBankBSchema();
			tLYSendToBankBSchema.setSerNo(tLWMissionDB.getMissionProp9());
			LYSendToBankBDB tLYSendToBankBDB = tLYSendToBankBSchema.getDB();
			LYSendToBankBSet tLYSendToBankBSet = tLYSendToBankBDB.query();
			LYSendToBankBSchema tLYSendToBankBSchema1 = tLYSendToBankBSet
					.get(0);
			String DealType = tLYSendToBankBSchema1.getDataType();
			ConTr.setDealType(DealType);
			/** 简易案件权限 理赔 */
			String Reject = (String) tTransferData.getValueByName("Reject");
			ConTr.setReject(Reject);
			return ConTr;
		}
		}
		/***
		 * //个险续期业务 if(tBusiType.equals("1004")){ return ConTr; } //团险新契约业务
		 * if(tBusiType.equals("2001")){ return ConTr; } //团险保全业务
		 * if(tBusiType.equals("2002")){ return ConTr; } //团险理赔业务
		 * if(tBusiType.equals("2003")){ return ConTr; } //团险续期业务
		 * if(tBusiType.equals("2004")){ return ConTr; }
		 ***/
		return ConTr;
	}
  }
