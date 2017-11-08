/**
 * <p>Title: 预付管理确认服务类 </p>
 * <p>Description: </p>
 * <p>Company: SinoSoft</p>
 * @author yuejw
 */

package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.AfterInitService;

public class LLClaimPrepayConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimPrepayConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
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
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mExamConclusion="";//审批结论
    private String mClmNo="";
    private LLClaimSchema mLLClaimSchema=null;//赔案信息	
	private LLClaimUWMainSchema tLLClaimUWMainSchema=null;//案件审批信息
	private LLClaimUWMainSchema mLLClaimUWMainSchema=null;//界面传入的审批信息
	private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema=null;//案件审核审批轨迹
	private Reflections mReflections = new Reflections();
	
	public LLClaimPrepayConfirmAfterInitService() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验传入数据
		if (!checkData()) {
			return false;
		}
		logger.debug("Start  dealData...");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("dealData successful!");
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
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
		mInputData = cInputData;
		mOperate = cOperate;

		mClmNo=(String)mTransferData.getValueByName("RptNo");
		mExamConclusion =(String)mTransferData.getValueByName("ExamConclusion");
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
			CError.buildErr(this, "前台传输全局公共数据Operater失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mMissionID == null || mSubMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");
		boolean tReturn = false;

		if(mExamConclusion.equals("2")){
			// 重新确定新的审批人
			LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
			String approvalOperator = tLLClaimPopedomSetBL.PubConfirmNewPopedom(mClmNo,mGlobalInput.Operator);
			
			if (approvalOperator == null || approvalOperator.equals("")) {
				
				// @@错误处理
				CError.buildErr(this, "查询新的审批人失败!");
				return false;
			}
			logger.debug("重新确定审批赔案号为" + mClmNo + "的案件的的总公司的审批人员是"
				+ approvalOperator);
			mTransferData.setNameAndValue("DefaultOperator", approvalOperator);
			  // 保存审批结论
			if (!dealClaimUW())
				tReturn= false;

			tReturn = true;
		}else{
			LClaimPrepayConfirmBL tLClaimPrepayConfirmBL = new LClaimPrepayConfirmBL();
			if (!tLClaimPrepayConfirmBL.submitData(mInputData, mOperate)) {
				// @@错误处理
				CError.buildErr(this, "数据提交失败,"+tLClaimPrepayConfirmBL.mErrors.getLastError());
				mResult.clear();
				mInputData = null;
				tReturn = false;
			}
			else 
			{
				mInputData = null;
				mInputData = tLClaimPrepayConfirmBL.getResult();
				logger.debug("-----start Service getData from BL");
				map = (MMap) mInputData.getObjectByObjectName("MMap", 0);

				//String mExamConclusion =(String)mTransferData.getValueByName("ExamConclusion");
				logger.debug("mExamConclusion:"+mExamConclusion);
				if(mExamConclusion.equals("1"))
				{
					ExeSQL tExeSQL=new ExeSQL();
					String sql="select AuditPopedom from llclaim where clmno='"+"?clmno?"+"'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(sql);
					sqlbv.put("clmno", mClmNo);
					String AuditPopedom=tExeSQL.getOneValue(sqlbv);
					logger.debug("案件"+mClmNo+"的审核权限是"+AuditPopedom);
					mTransferData.removeByName("Popedom");
					mTransferData.setNameAndValue("Popedom", AuditPopedom);
							
					tExeSQL=null;
					sql=null;
				}				
				tReturn=true;
			}
		}		
		return tReturn;
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
                SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL);
				sqlbv1.put("clmno", mLLClaimUWMainSchema.getClmNo());
        	    LLClaimUWMDetailDB tLLClaimUWMDetailDB=new LLClaimUWMDetailDB();
        			        			
        		LLClaimUWMDetailSet tLLClaimUWMDetailSet=new LLClaimUWMDetailSet();
        			
        		tLLClaimUWMDetailSet=tLLClaimUWMDetailDB.executeQuery(sqlbv1);
        			
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
        			       + " ClmNO='" + "?clmno?" + "'";
        			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    				sqlbv2.sql(strSQL);
    				sqlbv2.put("clmno", tLLClaimUWMainSchema.getClmNo());
        			ExeSQL exesql = new ExeSQL();
        			String tMaxNo = exesql.getOneValue(sqlbv2);
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
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
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
}
