/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

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
 * @author: zl
 * @version 1.0
 */
public class LLClaimAuditBL
{
private static Logger logger = Logger.getLogger(LLClaimAuditBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private String mModifyRgtState = "";//案件标识修改

    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private TransferData mTransferData = new TransferData();

    private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
    private LLClaimUWMainSchema tLLClaimUWMainSchema = null;//数据库中已经存在的审核信息
    private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema = null;//案件核赔履历表
    
	private Reflections ref = null;
    
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

    private GlobalInput mG = new GlobalInput();

    public LLClaimAuditBL()
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
                "----------LLClaimAuditBL begin submitData----------");
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
        logger.debug("---LLClaimAuditBL start getInputData()");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mLLClaimUWMainSchema = (LLClaimUWMainSchema) mInputData.
                               getObjectByObjectName(
                                       "LLClaimUWMainSchema", 0);
        mTransferData     = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        mModifyRgtState   = (String) mTransferData.getValueByName("ModifyRgtState"); //案件类型

        if (mLLClaimUWMainSchema == null)
        {
            // @@错误处理
            CError.buildErr(this, "接受的信息全部为空!");
            return false;
        }
        if(mModifyRgtState.equals(""))
        {
            // @@错误处理
            CError.buildErr(this, "接受的案件标识信息为空!");
            return false;
         }

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
    	
    	logger.debug("----------begin checkInputData----------");
		
	     LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
	     LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
	     
		 try
		 {

		     tLLClaimDetailDB.setClmNo(mLLClaimUWMainSchema.getClmNo());		    	
		     tLLClaimDetailSet=tLLClaimDetailDB.query();
		     
//			 if(!mLLClaimPubFunBL.getCheckAmnt(tLLClaimDetailSet)){			 
//				 // @@错误处理
//				 CError.buildErr(this, "在校验输入的数据时出错,"+mLLClaimPubFunBL.mErrors.getLastError());
//				 tLLClaimDetailSet=null;
//				 tLLClaimDetailDB=null;
//				 return false;
//			 }
		 }
		 catch (Exception ex)
		 {
			 // @@错误处理
			 CError.buildErr(this, "在校验输入的数据时出错!");
			 return false;
		 }
		 
		 
		 tLLClaimDetailSet=null;
		 tLLClaimDetailDB=null;
		 

		logger.debug("----------end checkInputData----------");
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {

        logger.debug("------start dealData-----");
        boolean tReturn = false;

        if (cOperate.equals("INSERT"))
        {
            LLClaimUWMainDB tLLClaimUWMainDB=new LLClaimUWMainDB();
    		tLLClaimUWMainSchema =new LLClaimUWMainSchema ();
    		tLLClaimUWMainDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
        	
    		//查询到记录表示不是第一次审核,需要同时记录轨迹
        	if(tLLClaimUWMainDB.getInfo())
    		{

    			tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());
    						
    			//更新审核结论
    			tLLClaimUWMainSchema.setCheckType(mLLClaimUWMainSchema.getCheckType());//审核类型 0--正常赔付 ,1-二次赔付
    		    tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());//审核结论
    		    tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());//审批意见
                tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());//特殊备注
                tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());//审核不通过原因
                tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());//审核不通过依据
                tLLClaimUWMainSchema.setAuditPer(mG.Operator);
                tLLClaimUWMainSchema.setAuditDate(PubFun.getCurrentDate());

                tLLClaimUWMainSchema.setOperator(mG.Operator);
                tLLClaimUWMainSchema.setMngCom(mG.ManageCom);
                tLLClaimUWMainSchema.setMakeDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setMakeTime(PubFun.getCurrentTime());
                tLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());
                
        		// 打包提交数据
                SQLwithBindVariables sqlbv = new SQLwithBindVariables();
                sqlbv.sql("delete from LLClaimUWMain where clmno='"+"?clmno?"+"'");
                sqlbv.put("clmno", mLLClaimUWMainSchema.getClmNo());
        	    map.put(sqlbv, "DELETE");
        		map.put(tLLClaimUWMainSchema, "INSERT");
    		}
    		else
    		{
    			//第一次审核
    			tLLClaimUWMainSchema.setSchema(mLLClaimUWMainSchema);
    			
    		    tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());//审核结论
    		    tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());//审批意见
                tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());//特殊备注
                tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());//审核不通过原因
                tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());//审核不通过依据
    		    
                tLLClaimUWMainSchema.setAuditPer(mG.Operator);
                tLLClaimUWMainSchema.setAuditDate(PubFun.getCurrentDate());
                
                tLLClaimUWMainSchema.setOperator(mG.Operator);
                tLLClaimUWMainSchema.setMngCom(mG.ManageCom);
                tLLClaimUWMainSchema.setMakeDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setMakeTime(PubFun.getCurrentTime());
                tLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
                tLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());
                
         		map.put(tLLClaimUWMainSchema, "INSERT");
    		}
        	
        	
    		
    		
    		//同步轨迹
    		tLLClaimUWMDetailSchema =new LLClaimUWMDetailSchema();
	            
	        //查询LLClaimUWMDetail核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
			       + " ClmNO='" + "?ClmNO?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("ClmNO", mLLClaimUWMainSchema.getClmNo());
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
			tMaxNo=null;
			exesql=null;
      	

            //赔案表
            LLClaimDB tLLClaimDB = new LLClaimDB();
            tLLClaimDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
            tLLClaimDB.getInfo();
            mLLClaimSchema.setSchema(tLLClaimDB.getSchema());
            if (mLLClaimSchema.getMakeDate() == null)
            {
                // @@错误处理
                CError.buildErr(this, "查询赔案表出错,赔案表无此立案信息!");
                return false;
            }
            mLLClaimSchema.setGiveType(mLLClaimUWMainSchema.
                                       getAuditConclusion());
            mLLClaimSchema.setGiveTypeDesc(mLLClaimUWMainSchema.
                                           getAuditIdea());
            mLLClaimSchema.setCasePayType(mLLClaimPubFunBL.getCheckCasePayType(mLLClaimUWMainSchema.getClmNo()));
            
            if(!"0".equals(mLLClaimPubFunBL.getCheckCasePayType(mLLClaimUWMainSchema.getClmNo()))){
            	String tinqSQL="select count(1) from LLInqApply where clmno='"+"?clmno?"+"' and InqState='1' ";
            	SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            	sqlbv2.sql(tinqSQL);
            	sqlbv2.put("clmno", mLLClaimUWMainSchema.getClmNo());
            	ExeSQL tExeSQL = new ExeSQL();
            	String tResult = tExeSQL.getOneValue(sqlbv2);
            	if("0".equals(tResult)){
	            	CError.buildErr(this, "该案件为强制调查案件，请先进行调查处理！");
	            	return false;
            	}
            }
            
			//99代表案件超过某种理赔类型的最大给付金,需要发起强制调查,但案件类型依然是一般给付件
			if(mLLClaimSchema.getCasePayType().equals("99"))
			{
				mLLClaimSchema.setCasePayType("0");
			}
			
            //案件标识修改Modify by zhaorx 2006-04-17
            String tSQLF = "";
            LLRegisterDB tLLRegisterDB = new LLRegisterDB();
            tLLRegisterDB.setRgtNo(mLLClaimUWMainSchema.getClmNo());
            tLLRegisterDB.getInfo();
            mLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
            String tRgtState = mLLRegisterSchema.getRgtState();//案件类型
            if(tRgtState != mModifyRgtState)
            {//案件标识与原案件案件标识不同时进行修改
                tSQLF = " update llregister set rgtstate='"+"?rgtstate?"+"' where rgtno='"+"?rgtno?"+"' ";
                SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
                sqlbv3.sql(tSQLF);
                sqlbv3.put("rgtstate", mModifyRgtState);
                sqlbv3.put("rgtno", mLLClaimUWMainSchema.getClmNo());
                map.put(sqlbv3,"UPDATE");
                logger.debug("llregister表中案件类型由原来的"+tRgtState+"修改为："+mModifyRgtState);
            }
            
            map.put(mLLClaimSchema, "DELETE&INSERT");
            
            
			// No.2.2  同步案件类型
			String sql1 = " update LLRegister set casePayType='"+"?casePayType?"+"' where"
						+ " RgtNo = '" + "?RgtNo?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql1);
			sqlbv4.put("casePayType", mLLClaimSchema.getCasePayType());
			sqlbv4.put("RgtNo", mLLClaimSchema.getClmNo());
			map.put(sqlbv4, "UPDATE");
	
			String sql2 = " update llclaimpolicy set casePayType='"+"?casePayType?"+"' where"
		    + " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sql2);
			sqlbv5.put("casePayType", mLLClaimSchema.getCasePayType());
			sqlbv5.put("clmno", mLLClaimSchema.getClmNo());
			map.put(sqlbv5, "UPDATE");

			// sl add 2015-06-11: if AuditConclusion=1/2/3,delete trace of claim calculator
			if ("1".equals(mLLClaimUWMainSchema.getAuditConclusion())
					||"2".equals(mLLClaimUWMainSchema.getAuditConclusion())
					||"3".equals(mLLClaimUWMainSchema.getAuditConclusion())){
				String sql3 = " delete from LCalculatorTrace where clmno = '" + "?clmno?" + "'";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(sql3);
				sqlbv6.put("clmno", mLLClaimSchema.getClmNo());
				map.put(sqlbv6, "DELETE");
			}
			// sl add 2015-06-11: end
			
            tReturn = true;
        }
        return tReturn;

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
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
            return false;
        }
        return true;
    }

}
