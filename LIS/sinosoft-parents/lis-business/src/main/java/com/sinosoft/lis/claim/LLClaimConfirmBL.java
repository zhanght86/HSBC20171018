/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimUWMDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimConfirmBL {
private static Logger logger = Logger.getLogger(LLClaimConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();

	private LLClaimUWMainSchema tLLClaimUWMainSchema=null;//案件审批信息
	private LLClaimUWMainSchema mLLClaimUWMainSchema=null;//界面传入的审批信息
	private Reflections ref = null;
	private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema=null;//案件审核审批轨迹
	private Reflections mReflections = new Reflections();

	private GlobalInput mGlobalInput = new GlobalInput();

	public LLClaimConfirmBL() {
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
		logger.debug("----------LLClaimConfirmBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---start getInputData()");
		// 获取页面信息
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mLLClaimUWMainSchema = (LLClaimUWMainSchema) mInputData
				.getObjectByObjectName("LLClaimUWMainSchema", 0);

		if (mLLClaimUWMainSchema == null) {
			// @@错误处理
			CError.buildErr(this, "接受的案件审批信息全部为空!");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----");

		// 添加纪录
		if (cOperate.equals("INSERT")) {
			
			// 保存审批结论
			if (!dealClaimUW())
				return false;

		}
		
		logger.debug("------end dealData-----");
		
		return true;
	}
	
	
	/**
	 * 保存审批结论
	 */
	private boolean dealClaimUW() {
		
		LLClaimUWMainDB tLLClaimUWMainDB=new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
		
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
    				   +" and (ExamConclusion is null or ExamConclusion = '') order by (clmuwno/1) desc";
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
        		
        		//当审批结论不为通过时,再插入一条轨迹
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
        			strSQL2 = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
        			       + " ClmNO='" + "?clmno?" + "'";
        			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        			sqlbv1.sql(strSQL2);
        			sqlbv1.put("clmno", mLLClaimUWMainSchema.getClmNo());
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
    			CError.buildErr(this, "查询不到案件"+mLLClaimUWMainSchema.getClmNo()+"的审核信息");
    			return false;
    		}

    		
    		tLLClaimUWMainDB=null;
    		tLLClaimUWMainSchema=null;
    		mLLClaimUWMainSchema=null;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "保存审批结论出现异常!");
            return false;
        }

		
		return true;
	
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

}
