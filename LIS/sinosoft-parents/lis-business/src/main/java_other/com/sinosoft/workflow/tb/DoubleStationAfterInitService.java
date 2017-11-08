package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.easyscan.RelationConfig;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class DoubleStationAfterInitService implements AfterInitService{
private static Logger logger = Logger.getLogger(DoubleStationAfterInitService.class);
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private VData tInputData = new VData();

    private MMap map = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
	
	private ToJudgeWetherSimeBL mToJudgeWetherSimeBL = new ToJudgeWetherSimeBL();

	/** 数据操作字符串 */
	private String mOperater;

	private String mAgentCode;
	
	private String mPrtNo;
	
	private String mInputDate;

	private String mManageCom;

	private String mOperate;

	private String mMissionID;

	private String mSubMissionID;
	
	private String mInputTime;
	
	private String SameFlag="3";
	
	private String mSubType="";
	private String mScanDate="";
	private String mScanTime="";
	
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public DoubleStationAfterInitService() {
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

		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mOperater = (String) mTransferData.getValueByName("Operator");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mInputTime = (String) mTransferData.getValueByName("InputTime");
		logger.debug("InputTime : "+mInputTime);
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		mInputDate = (String) mTransferData.getValueByName("MakeDate");
		mSubType = (String) mTransferData.getValueByName("SubType");
//		if(mSubType==null||mSubType.equals(""))
//		{
//			mSubType = "TB1001";
//		}
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		//查询扫描日期扫描时间
		if("1".equals(mInputTime)){
			RelationConfig nRelationConfig = RelationConfig.getInstance();
			String subtype = nRelationConfig.getrelation(mSubType);
			if(subtype==null||subtype.equals(""))
			{
				subtype = "UA1001";
			}
			String tScanSql="select makedate,maketime from es_doc_main where doccode='"+"?doccode?"+"' and subtype='"+"?subtype?"+"'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tScanSql);
			sqlbv1.put("doccode", mPrtNo);
			sqlbv1.put("subtype", subtype);

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if(tSSRS.getMaxRow()>0)
			{
				mScanDate = tSSRS.GetText(1, 1);
				mScanTime = tSSRS.GetText(1, 2);
			}
			else
			{
				mScanDate = "";
				mScanTime = "";
			}
		}
		if("2".equals(mInputTime)){
			String tScanSql="select MissionProp7,MissionProp8 from lwmission where activityid='0000001401' " +
					"and missionid='"+"?missionid?"+"' and submissionid='"+"?submissionid?"+"'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tScanSql);
			sqlbv2.put("missionid", mMissionID);
			sqlbv2.put("submissionid", mSubMissionID);

			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			if(tSSRS.getMaxRow()>0)
			{
				mScanDate = tSSRS.GetText(1, 1);
				mScanTime = tSSRS.GetText(1, 2);
			}
			else
			{
				mScanDate = "";
				mScanTime = "";
			}
		}
		if(mInputTime.equals("2")){/**如果mInputTime为2表示二岗录入完毕，须进行数据一致性校验*/
			if(!mToJudgeWetherSimeBL.submitData(mPrtNo,mOperater)){
				 // @@错误处理
	            CError.buildErr(this,"双岗录入校验失败!");
	            return false;
			}
			tInputData = mToJudgeWetherSimeBL.getResult();
			TransferData TransferData1 = new TransferData();
			TransferData1 = (TransferData) tInputData.getObjectByObjectName("TransferData", 0);
			SameFlag = (String) TransferData1.getValueByName("SameFlag");
			logger.debug("SimeFlag : "+SameFlag);
			//如果两次录入一致，直接生成XML文件，待导入
			if(SameFlag.equals("1")&&mInputTime.equals("2")){
				VData cInputData = new VData();
				TransferData1.setNameAndValue("ContNo", mPrtNo);
				TransferData1.setNameAndValue("Operator", mOperater);
				TransferData1.setNameAndValue("ManageCom", mManageCom);
				TransferData1.setNameAndValue("InputTime", mInputTime);
				cInputData.add(TransferData1);
				DoubleStationForXMLAfterEndService tDSAfterEndService = new DoubleStationForXMLAfterEndService();
				if(!tDSAfterEndService.submitData(cInputData,"")){
					 // @@错误处理
		            CError.buildErr(this,"生成接口文件时出错!");
		            return false;
				}		
				tInputData = tDSAfterEndService.getResult();
			}
		}else{/**除二岗录入完毕外一岗三岗不需要进行数据一致性校验*/
			
			return true;
		}
		return true;
	}

	private boolean prepareTransferData() {
			mTransferData.setNameAndValue("ScanCom", mManageCom);
			mTransferData.setNameAndValue("PrtNo", mPrtNo);
			mTransferData.setNameAndValue("InputDate", mInputDate);
			mTransferData.setNameAndValue("ScanOperator", mOperater);
			mTransferData.setNameAndValue("ScanDate", mScanDate);
			mTransferData.setNameAndValue("ScanTime", mScanTime);
			//mTransferData.setNameAndValue("SubType", mSubType);
			mTransferData.setNameAndValue("InputDate", theCurrentDate);
			//mTransferData.setNameAndValue("SameFlag", SameFlag);
			mTransferData.removeByName("Operator");
		return true;
	}

	private boolean prepareOutputData() {
			//mTransferData.removeByName(name)
			mTransferData.setNameAndValue("SameFlag", SameFlag);
			tInputData.add(mTransferData);
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return tInputData;
	}

	public TransferData getReturnTransferData() {
		// TODO Auto-generated method stub
		return mTransferData;
	}
}
