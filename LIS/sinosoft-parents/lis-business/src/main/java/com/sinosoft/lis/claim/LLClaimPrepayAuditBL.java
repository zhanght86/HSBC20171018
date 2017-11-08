/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 审核结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zhangzheng
 * @version 1.0
 */
public class LLClaimPrepayAuditBL implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimPrepayAuditBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private LLClaimUWMainSchema mLLClaimUWMainSchema = null;//界面传入的案件审核信息
	private LLClaimUWMainSchema tLLClaimUWMainSchema=null;//案件审批信息
    private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema = null;//案件核赔履历表

    private GlobalInput mG = new GlobalInput();
    private String mClmNo="";//案件号
	private Reflections ref = null;
    
    public LLClaimPrepayAuditBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLClaimPrepayAuditBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug("----------after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------after prepareOutputData----------");

        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("---LLClaimPrepayAuditBL start getInputData()");
        
        mLLClaimUWMainSchema =new LLClaimUWMainSchema();
        
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mLLClaimUWMainSchema = (LLClaimUWMainSchema) mInputData.
                               getObjectByObjectName(
                                       "LLClaimUWMainSchema", 0);

        if (mLLClaimUWMainSchema == null)
        {
            // @@错误处理
            CError.buildErr(this, "接受的信息全部为空!");
            return false;
        }
        
        mClmNo=mLLClaimUWMainSchema.getClmNo();
        
        logger.debug("案件:"+mClmNo+",的预付审核结论:"+mLLClaimUWMainSchema.getAuditConclusion());

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {

    	if(mLLClaimUWMainSchema.getAuditConclusion()==null||mLLClaimUWMainSchema.getAuditConclusion().equals(""))
    	{
            // @@错误处理
            CError.buildErr(this, "传入的审核结论为空!");
            return false;
    	}
    	
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {

        logger.debug("------start dealData-----");
        
        LLClaimUWMainDB tLLClaimUWMainDB=new LLClaimUWMainDB();
		tLLClaimUWMainSchema =new LLClaimUWMainSchema ();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		
        try
        {
        	if(tLLClaimUWMainDB.getInfo())
    		{

    			tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());
    						
    			//更新审核结论
    		    tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());//审核结论
    		    tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());//审批意见
                tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());//特殊备注
                tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());//审核不通过原因
                tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());//审核不通过依据
    		    mLLClaimUWMainSchema.setAuditPer(mG.Operator);
    		    mLLClaimUWMainSchema.setAuditDate(PubFun.getCurrentDate());

    		    mLLClaimUWMainSchema.setOperator(mG.Operator);
    		    mLLClaimUWMainSchema.setMngCom(mG.ManageCom);
    		    mLLClaimUWMainSchema.setMakeDate(PubFun.getCurrentDate());
    		    mLLClaimUWMainSchema.setMakeTime(PubFun.getCurrentTime());
    		    mLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
    		    mLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());
                
        		// 打包提交数据
    		    SQLwithBindVariables sqlbv4 = new SQLwithBindVariables() ;
    		    sqlbv4.sql("delete from LLClaimUWMain where clmno='"+"?clmno?"+"'");
    		    sqlbv4.put("clmno", mClmNo);
    		    map.put(sqlbv4, "DELETE");
        		map.put(mLLClaimUWMainSchema, "INSERT");     		
                           
    		}
    		else
    		{
    			tLLClaimUWMainSchema.setSchema(mLLClaimUWMainSchema);
    			
    		    tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());//审核结论
    		    tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());//审批意见
                tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());//特殊备注
                tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());//审核不通过原因
                tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());//审核不通过依据
    			tLLClaimUWMainSchema.setCheckType(mLLClaimUWMainSchema.getCheckType());//审核类型 0--审核结论1--预付审核结论
    		    tLLClaimUWMainSchema.setAuditLevel(mLLClaimUWMainSchema.getAuditLevel());//案件审核级别
    		    
                tLLClaimUWMainSchema.setAuditPer(mG.Operator);
                tLLClaimUWMainSchema.setAuditDate(PubFun.getCurrentDate());
                
                tLLClaimUWMainSchema.setOperator(mG.Operator);
                tLLClaimUWMainSchema.setMngCom(mG.ManageCom);
                tLLClaimUWMainSchema.setMakeDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setMakeTime(PubFun.getCurrentTime());
                tLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());
                
        		// 打包提交数据
    			map.put(tLLClaimUWMainSchema, "INSERT");  			

    		}
        	
        	
			
    		 //同步轨迹
        	tLLClaimUWMDetailSchema =new LLClaimUWMDetailSchema();
	            
	        //查询LLClaimUWMDetail核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
			       + " ClmNO='" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("ClmNO", mLLClaimUWMainSchema.getClmNo());
			ExeSQL exesql = new ExeSQL();
			String tMaxNo = exesql.getOneValue(sqlbv);
			if (tMaxNo.length() == 0) {
					tMaxNo = "1";
			} 
			else {
					int tInt = Integer.parseInt(tMaxNo);
					tInt = tInt + 1;
					tMaxNo = String.valueOf(tInt);
			}
				
			ref = new Reflections();
			ref.transFields(tLLClaimUWMDetailSchema, tLLClaimUWMainSchema);
			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);	
			
			tLLClaimUWMDetailSchema.setExamConclusion("");//审批结论
            tLLClaimUWMDetailSchema.setExamIdea("");//审批意见
            tLLClaimUWMDetailSchema.setExamNoPassDesc("");//审批不通过原因
            tLLClaimUWMDetailSchema.setExamNoPassReason("");//审批不通过依据
            
            tLLClaimUWMDetailSchema.setExamDate("");
            tLLClaimUWMDetailSchema.setExamPer("");
			    
			// 打包提交数据
			map.put(tLLClaimUWMDetailSchema, "INSERT");
				
			tLLClaimUWMDetailSchema=null;
			ref=null;
    		tLLClaimUWMainDB=null;
    		tLLClaimUWMainSchema=null;
    		mLLClaimUWMainSchema=null;
    		
    		// No.2.2 更改赔案状态为结案,置结案日期,结案标志,案件给付类型
			String sql1 = " update LLRegister set casePayType='3' where"
					+ " RgtNo = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("clmno", mClmNo);
			map.put(sqlbv1, "UPDATE");
			
			String sql2 = " update llclaim set casePayType='3' where"
					+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("clmno", mClmNo);
			map.put(sqlbv2, "UPDATE");
			
			String sql3 = " update llclaimpolicy set casePayType='3' where"
				    + " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql3);
			sqlbv3.put("clmno", mClmNo);
			map.put(sqlbv3, "UPDATE");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "保存预付审核结论时出现日常!");
            return false;
        }
		
  
		logger.debug("------end dealData-----");
        return true;

    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
            //mResult.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
            return false;
        }
        return true;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
