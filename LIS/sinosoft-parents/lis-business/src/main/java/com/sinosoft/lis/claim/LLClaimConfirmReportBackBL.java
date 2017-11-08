package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

//import com.sinosoft.workflowengine.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: 审批上报服务类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007-11-28
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangzheng
 * @version 1.0
 */

public class LLClaimConfirmReportBackBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimConfirmReportBackBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mClmNo = "";
	private String approvalLevel = "";// 案件确定的审批级别
	private String approvalOperator = "";// 确定的审批人员
	private String lastApprovalOperator = "";// 上报的分公司的审批人员
	private LLClaimSchema mLLClaimSchema=null;//赔案信息
	private LWMissionSchema tLWMissionSchema = new LWMissionSchema();// 工作流轨迹
	private LLClaimUWMainSchema tLLClaimUWMainSchema=null;//案件审批信息
	private LLClaimUWMainSchema mLLClaimUWMainSchema=null;//界面传入的审批信息
	private Reflections ref = null;
	private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema=null;//案件审核审批轨迹
	private Reflections mReflections = new Reflections();
	private LLClaimPubFunBL tLLClaimPubFunBL= null;
	
	public LLClaimConfirmReportBackBL() {
		// TODO Auto-generated constructor stub
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验传入数据
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "上报失败,"+tPubSubmit.mErrors.getLastError());
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
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		logger.debug("LLClaimConfirmReportBackBL获得的ClmNo=" + mClmNo);
		mInputData = cInputData;
		mOperate = cOperate;
		
		mLLClaimUWMainSchema = (LLClaimUWMainSchema)cInputData.getObjectByObjectName(
				"LLClaimUWMainSchema", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		logger.debug("LLClaimConfirmReportBackBL获得的MIssionID="
				+ mMissionID);
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		logger.debug("LLClaimConfirmReportBackBL获得的SubMIssionID="
				+ mSubMissionID);
		
		if (mSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中SubMissionID失败!");
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		logger.debug("LLClaimConfirmReportBackBL获得的ActivityID="
				+ mActivityID);
		
		if (mActivityID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中ActivityID失败!");
			return false;
		}
		
		tLLClaimPubFunBL = new LLClaimPubFunBL();
		mLLClaimSchema = tLLClaimPubFunBL.getLLClaim(mClmNo);
		
		if (mLLClaimSchema == null) {
			// @@错误处理
			CError.buildErr(this, "查询不到赔案信息,上报失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");

		// ----------------------------------------------------------------------BEG
		// 功能：重新确定审批人
		// 处理：根据审批级别从总公司中找到符合案件审批级别的操作员,给业务人员确定;
		// ----------------------------------------------------------------------

		if (mOperate.equals("UPREPORT")) {
			
			// 重新确定审批人
			if (!checkPopedomUser())
				return false;
			
			// 保存审批结论
			if (!dealClaimUW())
				return false;
		}

		// ----------------------------------------------------------------------END
		return true;
	}
	
	/**
	 * 保存审批结论
	 */
	private boolean dealClaimUW() {
		
		LLClaimUWMainDB tLLClaimUWMainDB=new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		
        try
        {
    		if(tLLClaimUWMainDB.getInfo())
    		{
    			tLLClaimUWMainSchema =new LLClaimUWMainSchema ();
    			tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());
    			
    			
    			//更新审批结论
    		    tLLClaimUWMainSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());//审批结论
    		    tLLClaimUWMainSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());//审批意见
    		    tLLClaimUWMainSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());//审批不通过原因
    		    tLLClaimUWMainSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());//审批不通过意见	
    		    
    		    tLLClaimUWMainSchema.setExamPer(mGlobalInput.Operator);//审批人
                tLLClaimUWMainSchema.setExamDate(PubFun.getCurrentDate());//审批日期
                tLLClaimUWMainSchema.setExamCom(mGlobalInput.ManageCom);//审批机构
                
                tLLClaimUWMainSchema.setOperator(mGlobalInput.Operator);
                tLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());
    			
    			
    			//更新审批结论
                String	strSQL =" select * from LLClaimUWMDetail where  clmno ='"+"?clmno?"+"'"
    				   +" and ExamConclusion is null order by (clmuwno/1) desc";
    			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    			sqlbv.sql(strSQL);
    			sqlbv.put("clmno", mLLClaimUWMainSchema.getClmNo());
        	    LLClaimUWMDetailDB tLLClaimUWMDetailDB=new LLClaimUWMDetailDB();
        		LLClaimUWMDetailSet tLLClaimUWMDetailSet=new LLClaimUWMDetailSet();
        			
        		tLLClaimUWMDetailSet=tLLClaimUWMDetailDB.executeQuery(sqlbv);
        			
        		if(tLLClaimUWMDetailSet.size()==0)
        		{
        		           CError.buildErr(this, "查询不到案件"+mLLClaimUWMainSchema.getClmNo()+"的审核结论轨迹");
        		           return false;
        		}
        		else
        		{
        			//保留本次轨迹
        			LLClaimUWMDetailSet mLLClaimUWMDetailSet=new LLClaimUWMDetailSet();
        				
        			for(int i=1;i<=tLLClaimUWMDetailSet.size();i++){
        					
        				tLLClaimUWMDetailSchema=tLLClaimUWMDetailSet.get(i);
        				
            			tLLClaimUWMDetailSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());
            			tLLClaimUWMDetailSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());
            			tLLClaimUWMDetailSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());
            			tLLClaimUWMDetailSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());
            			
            		    tLLClaimUWMDetailSchema.setExamDate(PubFun.getCurrentDate());
            		    tLLClaimUWMDetailSchema.setExamPer(mGlobalInput.Operator);
               	        tLLClaimUWMDetailSchema.setExamCom(mGlobalInput.ManageCom);
               	        
            	        tLLClaimUWMDetailSchema.setOperator(mGlobalInput.Operator);
            	        tLLClaimUWMDetailSchema.setModifyDate(PubFun.getCurrentDate());
            	        tLLClaimUWMDetailSchema.setModifyTime(PubFun.getCurrentTime());
     
            	            
            	        mLLClaimUWMDetailSet.add(tLLClaimUWMDetailSchema);
            	        tLLClaimUWMDetailSchema=null;
        			}
        	
                    map.put(mLLClaimUWMDetailSet, "DELETE&INSERT");
        		}
        		
        		//当审批结论不为通过时,审核结论主表不保存审批结论,同时插入一条新的轨迹
        		if(!mLLClaimUWMainSchema.getExamConclusion().equals("0"))
        		{
         		    tLLClaimUWMainSchema.setExamConclusion("");//审批结论
        		    tLLClaimUWMainSchema.setExamIdea("");//审批意见
        		    tLLClaimUWMainSchema.setExamNoPassReason("");//审批不通过原因
        		    tLLClaimUWMainSchema.setExamNoPassDesc("");//审批不通过意见	
        		    
        		    tLLClaimUWMainSchema.setExamPer("");//审批人
                    tLLClaimUWMainSchema.setExamDate("");//审批日期
                    tLLClaimUWMainSchema.setExamCom("");//审批机构
                    
        			tLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
        			this.mReflections.transFields(tLLClaimUWMDetailSchema,
        					tLLClaimUWMainSchema);
        			
        			//查询LLClaimUWMDetail核赔次数
        			String strSQL2 = "";
        			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
        			       + " ClmNO='" + "?ClmNO?" + "'";
        			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        			sqlbv1.sql(strSQL);
        			sqlbv1.put("ClmNO", tLLClaimUWMainSchema.getClmNo());
        			ExeSQL exesql = new ExeSQL();
        			String tMaxNo = exesql.getOneValue(sqlbv1);
        			if (tMaxNo.length() == 0) {
        					tMaxNo = "1";
        			} 
        			else {
        					int tInt = Integer.parseInt(tMaxNo);
        					tInt = tInt + 1;
        					tMaxNo = String.valueOf(tInt);
        			}
        			
        			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);
        			
        			map.put(tLLClaimUWMDetailSchema, "INSERT");
        		}
        		
     			// 打包提交数据
    			map.put(tLLClaimUWMainSchema, "UPDATE");
    			
    
    			
    		}
    		else
    		{
    			CError.buildErr(this, "查询不到案件"+mClmNo+"的审核信息");
    			return false;
    		}
    		
    		tLLClaimUWMainDB=null;
    		tLLClaimUWMainSchema=null;
    		mLLClaimUWMainSchema=null;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "上报出现异常!");
            return false;
        }

		
		return true;
	
	}
	
	
	/**
	 * 重新确定审批人
	 */
	private boolean checkPopedomUser() {
		
		// 分公司权限不够，审批该案件必须从总公司找出符合级别的审批人
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);

		tLWMissionDB.getInfo();
		if (tLWMissionDB.mErrors.needDealError() == true) {
			// @@错误处理
			CError.buildErr(this, "案件上报重新查询案件的核赔级别失败,"+tLWMissionDB.mErrors.getLastError());
			return false;
		}

		tLWMissionSchema = tLWMissionDB.getSchema();
//		approvalLevel = tLWMissionSchema.getMissionProp10();
//		logger.debug("赔案号为" + mClmNo + "的案件的核赔级别是" + approvalLevel);
//		lastApprovalOperator = tLWMissionSchema.getDefaultOperator();
//		logger.debug("赔案号为" + mClmNo + "的案件的分公司的上报人员是"
//				+ lastApprovalOperator);
//
//		// 重新确定新的审批人
		LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
//
//		String approvalOperator = tLLClaimPopedomSetBL.ConfirmPopedom(
//				approvalLevel, lastApprovalOperator);

		
		String approvalOperator = tLLClaimPopedomSetBL.PubConfirmNewPopedom(mClmNo,mGlobalInput.Operator);
		
		if (approvalOperator == null || approvalOperator.equals("")) {
			
			// @@错误处理
			CError.buildErr(this, "查询新的审批人失败!");
			return false;
		}
		logger.debug("重新确定审批赔案号为" + mClmNo + "的案件的的总公司的审批人员是"
			+ approvalOperator);

		tLWMissionSchema.setDefaultOperator(approvalOperator);
		tLWMissionSchema.setLastOperator(lastApprovalOperator);

		// 打包提交数据
		map.put(tLWMissionSchema, "UPDATE");
		
		return true;
	
	}
	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {

		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 准备数据，测试权限部分
		LLClaimConfirmReportBackBL mLLClaimConfirmReportBackBL = new LLClaimConfirmReportBackBL();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("RptNo", "90000000003");
		tTransferData.setNameAndValue("MngCom", "86");
		tTransferData.setNameAndValue("MissionID", "10000000000000000023");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000005055");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "zz";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGlobalInput);

		// 调用submit方法,开始测试
		mLLClaimConfirmReportBackBL.submitData(tVData, "REPORT");
	}

}
