package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLClaimUWMDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LLCUWBatchSchema;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LLCUWBatchSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class QueryDefaultOperator implements BusinessService{
private static Logger logger = Logger.getLogger(QueryDefaultOperator.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	/** 往工作流引擎中传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
	private Reflections mReflections = new Reflections();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mRptNo;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public QueryDefaultOperator() {
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

		// 校验数据的合法性
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

		PubSubmit tPubSubmit = new PubSubmit();
		if(!tPubSubmit.submitData(mResult)){
			CError.buildErr(this, "数据提交失败！");
			return false;
		}
		
		logger.debug("Start  Submit...");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mG = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mLLClaimUWMainSchema = (LLClaimUWMainSchema) cInputData.getObjectByObjectName(
				"LLClaimUWMainSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mRptNo = (String) mTransferData.getValueByName("RptNo");
		// mInputData = cInputData;
		if (mG == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mG.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mG.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 	确定第二岗的审批人，修改工作流的defaultOperator 置LLClaimUWMainSchema的DSClaimFlag为1
	 */
	private boolean dealData() {
		//修改工作流defaultoperator
		String tMissionSql = "select * from lwmission where missionprop1='"
				+mRptNo+"' and activityid='0000009055'";
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		tLWMissionSet = tLWMissionDB.executeQuery(tMissionSql);
		String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
		//备份到B表
		mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
		tLBMissionSchema.setSerialNo(tSerielNo);
		tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
		tLBMissionSchema.setLastOperator(mOperator);
		// mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
		// mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
		tLBMissionSchema.setOutDate(PubFun.getCurrentDate());
		tLBMissionSchema.setOutTime(PubFun.getCurrentTime());
		if(tLWMissionSet.size()!=1){
			CError.buildErr(this, "查询工作流节点9055失败！");
			return false;
		}
		tLWMissionSchema = tLWMissionSet.get(1);
		String tOperatorSql = "select usercode from llgrpclaimuser where jobcategory='TB' and usercode<>'"
			+mOperator+"'";
		ExeSQL tExeSQL = new ExeSQL();
		String tDefaultOperator = tExeSQL.getOneValue(tOperatorSql);
		if(tDefaultOperator==null||"".equals(tDefaultOperator)){
			CError.buildErr(this,"确认第二审批人失败！");
			return false;
		}
		tLWMissionSchema.setDefaultOperator(tDefaultOperator);
		tLWMissionSchema.setModifyDate(mCurrentDate);
		tLWMissionSchema.setModifyTime(mCurrentTime);
		
		//审批结论 并将DSClaimFlag置为1
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
        tLLClaimUWMainDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
        if(tLLClaimUWMainDB.getInfo()){
            tLLClaimUWMainSchema = tLLClaimUWMainDB.getSchema();
        }
//        tLLClaimUWMainSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());
//        tLLClaimUWMainSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());
//        tLLClaimUWMainSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());
//        tLLClaimUWMainSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());
//        tLLClaimUWMainSchema.setExamDate(mCurrentDate);
//        tLLClaimUWMainSchema.setExamPer(mG.Operator);
        tLLClaimUWMainSchema.setDSClaimFlag("1");//将DSClaimFlag置为1，表示一岗审批完毕
//        tLLClaimUWMainSchema.setOperator(mG.Operator);
//        tLLClaimUWMainSchema.setModifyDate(mCurrentDate);
//        tLLClaimUWMainSchema.setModifyTime(mCurrentTime);
//        tLLClaimUWMainSchema.setMngCom(mG.ManageCom);

        //复制最近的一条轨迹 并修改其审批结论
        LLClaimUWMDetailSchema tLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
        LLClaimUWMDetailDB tLLClaimUWMDetailDB = new LLClaimUWMDetailDB();
        LLClaimUWMDetailSet tLLClaimUWMDetailSet = new LLClaimUWMDetailSet();
        String strSQL2 = "";
		strSQL2 = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
				+ " ClmNO='"
				+ tLLClaimUWMainSchema.getClmNo()
				+ "'";

		ExeSQL exesql = new ExeSQL();
		String tMaxNo = exesql.getOneValue(strSQL2);
		if (tMaxNo.length() == 0) {
			tMaxNo = "1";
		} else {
			int tInt = Integer.parseInt(tMaxNo);
			tInt = tInt + 1;
			tMaxNo = String.valueOf(tInt);
		}
        String tClaimDetailSql = "select * from LLClaimUWMDetail where clmno='"+mLLClaimUWMainSchema.getClmNo()+"' "
        			+" and clmuwno = (select Max(ClmUWNo/1) from LLClaimUWMDetail where clmno='"+mLLClaimUWMainSchema.getClmNo()+"')"
        			+"  order by clmuwno/1 desc";
        tLLClaimUWMDetailSet = tLLClaimUWMDetailDB.executeQuery(tClaimDetailSql);
        if(tLLClaimUWMDetailSet.size() ==0){
        	
        }else{
        	tLLClaimUWMDetailSchema = tLLClaimUWMDetailSet.get(1);
        	tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);
        	tLLClaimUWMDetailSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());
        	tLLClaimUWMDetailSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());
        	tLLClaimUWMDetailSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());
        	tLLClaimUWMDetailSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());
        	tLLClaimUWMDetailSchema.setExamDate(mCurrentDate);
        	tLLClaimUWMDetailSchema.setExamPer(mG.Operator);
//          tLLClaimUWMainSchema.setDSClaimFlag("1");//将DSClaimFlag置为1，表示一岗审批完毕
        	tLLClaimUWMDetailSchema.setOperator(mG.Operator);
        	tLLClaimUWMDetailSchema.setModifyDate(mCurrentDate);
        	tLLClaimUWMDetailSchema.setModifyTime(mCurrentTime);
        	tLLClaimUWMDetailSchema.setMngCom(mG.ManageCom);
        }
        
        mMap.put(tLLClaimUWMDetailSchema, "INSERT");
        mMap.put(tLLClaimUWMainSchema, "DELETE&INSERT");
		mMap.put(tLWMissionSet, "UPDATE");
		return true;
	}



	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mG);
		mResult.add(mMap);// 业务数据包
		mResult.add(mTransferData);// 工作流数据
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
