package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

/**
 * <p>
 * Title:工作流节点任务:新契约人工核保确认
 * </p>
 * <p>
 * Description:人工核保确认工作流AfterEnd服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWConfirmAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(UWConfirmAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private Reflections mReflections = new Reflections();

	private LWMissionSet mLWMissionSet = new LWMissionSet();

	/** 工作流任务节点备份表 */
	private LBMissionSet mLBMissionSet = new LBMissionSet();

	//tongmeng 2009-02-06 add
	//核保7状态强制回收各种已发放但未处理完毕的核保函件
	//问题件
	LCIssuePolSet mTakeBackLCIssuePolSet = new LCIssuePolSet();
	//体检件
	LCPENoticeSet mTakeBackLCPENoticeSet = new LCPENoticeSet();
	//生调件
	LCRReportSet mTakeBackLCRReportSet = new LCRReportSet();
	
	String mUWUpReport = "";
	String mUWFlag="";
	public UWConfirmAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;
		
		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWConfirmAfterEndService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mUWUpReport = (String) mTransferData.getValueByName("UWUpReport");
		this.mUWFlag=(String) mTransferData.getValueByName("UWFlag");
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 保全核保工作流起始节点状态改变
		if (prepareMission() == false)
			return false;

		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean prepareMission() {
		// 修改保全人工核保的起始节点状态为未回复
		// 核保完毕不再关心发放的问题件、体检生调通知书是否已回收 ,核保完毕时根据录入的通知书而生成相应的打印节点modify by lzf 2013-04-07 
	
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		String tStr = "";
//		// 准备要删除的核保相关工作流任务节点
//		tStr = "Select * from LWMission where MissionID = '"
//				+ mMissionID
//				+ "'"
//				+ "and ActivityID in('0000001100','0000001111','0000001112','0000001113','0000001106','0000001107','0000001108','0000001114','0000001115','0000001116','0000001017','0000001018','0000001019','0000001020','0000001021','0000001002','0000001105'"
//				+",'0000001120','0000001121','0000001301','0000001134')";
//		mLWMissionSet = tLWMissionDB.executeQuery(tStr);
//		if (mLWMissionSet == null || mLWMissionSet.size() < 0) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLWMissionSet.mErrors);
//			CError tError = new CError();
//			tError.moduleName = "UWConfirmAfterEndService";
//			tError.functionName = "prepareMission";
//			tError.errorMessage = "查询工作流续保人工核保的加费和特约任务节点失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//
//		for (int i = 1; i <= mLWMissionSet.size(); i++) {
//			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
//
//			tLWMissionSchema = mLWMissionSet.get(i);
//			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
//			tLBMissionSchema.setSerialNo(tSerielNo);
//			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
//			tLBMissionSchema.setLastOperator(mOperater);
//			tLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//			tLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//			mLBMissionSet.add(tLBMissionSchema);
//		}

		
		//tongmeng 2009-02-26 add
		//如果是7状态,强制回收所有已经下发的通知书
//		String tSQL_UWState = "select nvl(missionprop18,'1'),missionprop1 from lwmission where missionid='"+mMissionID+"' "
//		                    + " and activityid = '0000001100' ";
		String tSQL_UWState = "select uwstate from lccuwmaster where contno='"+"?contno?"+"'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL_UWState);
		sqlbv.put("contno", mContNo);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		if(tSSRS.getMaxRow()>0)
		{
			String tStatus = "";
			//String tContNo = "";
			tStatus = tSSRS.GetText(1,1);
			//tContNo = tSSRS.GetText(1,2);
			if(tStatus.equals("7")&&mUWUpReport.equals("0") && !"5".equals(this.mUWFlag))
			{
				// 1-判断是否有已发放但回复的问题件信息
				
				String tSQL_IssuePol = "select * from lcissuepol where  contno='"+"?contno?"+"' "
				                     + " and state is not null and state<>'2' ";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSQL_IssuePol);
				sqlbv1.put("contno", mContNo);
				LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
				LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
				tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
				for(int i=1;i<=tLCIssuePolSet.size();i++)
				{
					LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
					tLCIssuePolSchema.setSchema(tLCIssuePolSet.get(i));
					tLCIssuePolSchema.setState("2");
					tLCIssuePolSchema.setFieldName("AUTO");
					if(tLCIssuePolSchema.getReplyResult()==null || tLCIssuePolSchema.getReplyResult().equals(""))
					{
						tLCIssuePolSchema.setReplyMan("AUTO");
						tLCIssuePolSchema.setReplyResult("强制下核保结论,系统自动回收");
						tLCIssuePolSchema.setReplyDate(tCurrentDate);
						tLCIssuePolSchema.setReplyTime(tCurrentTime);
					}					
					tLCIssuePolSchema.setModifyDate(tCurrentDate);
					tLCIssuePolSchema.setModifyTime(tCurrentTime);				
					
					this.mTakeBackLCIssuePolSet.add(tLCIssuePolSchema);
				}
				
				// 2-判断是否有未处理完毕的体检件信息
				String tSQL_PENotice = " select * from LCPENotice where contno='"+ "?contno?"+ "' "
									 + " and printflag is not null and printflag<>'2' ";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSQL_PENotice);
				sqlbv2.put("contno", mContNo);
				LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
				LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
				tLCPENoticeSet = tLCPENoticeDB.executeQuery(sqlbv2);
				for (int i = 1; i <= tLCPENoticeSet.size(); i++) {
					LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
					tLCPENoticeSchema.setSchema(tLCPENoticeSet.get(i));
					tLCPENoticeSchema.setPrintFlag("2");
					if(tLCPENoticeSchema.getPEResult()==null || tLCPENoticeSchema.getPEResult().equals(""))
					{
						tLCPENoticeSchema.setREPEDate(tCurrentDate);						
					}					
					tLCPENoticeSchema.setGrpPrtSeq("AUTO");
					tLCPENoticeSchema.setModifyDate(tCurrentDate);
					tLCPENoticeSchema.setModifyTime(tCurrentTime);
					
					this.mTakeBackLCPENoticeSet.add(tLCPENoticeSchema);

				}
				// 3-生调件信息
				String tSQL_Report = "select * from lcrreport where contno='"+ "?contno?" + "' "
								   + " and replyflag is not null and replyflag<>'2' ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSQL_Report);
				sqlbv3.put("contno", mContNo);
				LCRReportSet tLCRReportSet = new LCRReportSet();
				LCRReportDB tLCRReportDB = new LCRReportDB();
				tLCRReportSet = tLCRReportDB.executeQuery(sqlbv3);
				for (int i = 1; i <= tLCRReportSet.size(); i++) {
					LCRReportSchema tLCRReportSchema = new LCRReportSchema();
					tLCRReportSchema.setSchema(tLCRReportSet.get(i));
					tLCRReportSchema.setReplyFlag("2");
					tLCRReportSchema.setGrpPrtSeq("AUTO");
					if(tLCRReportSchema.getReplyContente()==null || tLCRReportSchema.getReplyContente().equals(""))
					{
						tLCRReportSchema.setReplyOperator("AUTO");
						tLCRReportSchema.setReplyDate(tCurrentDate);
						tLCRReportSchema.setReplyTime(tCurrentTime);
						tLCRReportSchema.setReplyContente("强制下核保结论,系统自动回收");
					}		

					this.mTakeBackLCRReportSet.add(tLCRReportSchema);
				}
			}
		
		//
		
		//2009-11-04  增加一段逻辑  如果有未回复的，相应的未回复节点也需要删除，以防核保订正回来后造成问题
//			LWMissionDB queryLWMissionDB = new LWMissionDB();
//			String querySql = "select * from lwmission where missionprop1='"+tContNo+"' "
//						+ " and activityid in ('0000001120')";
		}
		return true;
	}

	/**
	 * 准备提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加相关工作流同步执行完毕的任务节点表数据
//		if (mLWMissionSet != null && mLWMissionSet.size() > 0) {
//			map.put(mLWMissionSet, "DELETE");
//		}
//
//		// 添加相关工作流同步执行完毕的任务节点备份表数据
//		if (mLBMissionSet != null && mLBMissionSet.size() > 0) {
//			map.put(mLBMissionSet, "INSERT");
//		}
		//tongmeng 2009-02-06 add
		//7状态强制回收通知书
		if(this.mTakeBackLCIssuePolSet.size()>0)
		{
			map.put(mTakeBackLCIssuePolSet, "UPDATE");
		}
		if(this.mTakeBackLCPENoticeSet.size()>0)
		{
			map.put(mTakeBackLCPENoticeSet, "UPDATE");
		}
		if(this.mTakeBackLCRReportSet.size()>0)
		{
			map.put(mTakeBackLCRReportSet, "UPDATE");
		}
		
		mResult.add(map);
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
