package com.sinosoft.lis.claim;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLCUWBatchDB;
import com.sinosoft.lis.db.LLCUWMasterDB;
import com.sinosoft.lis.db.LLCaseBackDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLExemptDB;
import com.sinosoft.lis.db.LLGrpRegisterDB;
import com.sinosoft.lis.db.LLParaPupilAmntDB;
import com.sinosoft.lis.db.LLParaRepDB;
import com.sinosoft.lis.db.LLPrepayClaimDB;
import com.sinosoft.lis.db.LLPrepayDetailDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LLToClaimDutyDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.db.LLUWPENoticeDB;
import com.sinosoft.lis.db.LLUWPremMasterDB;
import com.sinosoft.lis.db.LLUWSpecMasterDB;
import com.sinosoft.lis.db.LMCheckFieldDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMDutyGetClmCalDB;
import com.sinosoft.lis.db.LMDutyGetClmDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMDutyGetRelaDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskDutyDB;
import com.sinosoft.lis.db.LMRiskPayDB;
import com.sinosoft.lis.db.LMRiskSortDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLCUWBatchSchema;
import com.sinosoft.lis.schema.LLCUWMasterSchema;
import com.sinosoft.lis.schema.LLCaseBackSchema;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.lis.schema.LLGrpRegisterSchema;
import com.sinosoft.lis.schema.LLParaRepSchema;
import com.sinosoft.lis.schema.LLPrepayClaimSchema;
import com.sinosoft.lis.schema.LLPrepayDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLToClaimDutySchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWPENoticeSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.schema.LMDutyGetClmCalSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskDutySchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LLAccidentSet;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCUWBatchSet;
import com.sinosoft.lis.vschema.LLCUWMasterSet;
import com.sinosoft.lis.vschema.LLCaseBackSet;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.vschema.LLClaimUWMainSet;
import com.sinosoft.lis.vschema.LLExemptSet;
import com.sinosoft.lis.vschema.LLGrpRegisterSet;
import com.sinosoft.lis.vschema.LLParaPupilAmntSet;
import com.sinosoft.lis.vschema.LLParaRepSet;
import com.sinosoft.lis.vschema.LLPrepayClaimSet;
import com.sinosoft.lis.vschema.LLPrepayDetailSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.lis.vschema.LLToClaimDutySet;
import com.sinosoft.lis.vschema.LLUWMasterSet;
import com.sinosoft.lis.vschema.LLUWPENoticeSet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LLUWSpecMasterSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LMDutyGetClmCalSet;
import com.sinosoft.lis.vschema.LMDutyGetClmSet;
import com.sinosoft.lis.vschema.LMDutyGetRelaSet;
import com.sinosoft.lis.vschema.LMDutyGetSet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.lis.vschema.LMRiskPaySet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.lis.vschema.LMRiskSortSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔系统：通用方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLClaimPubFunBL {
private static Logger logger = Logger.getLogger(LLClaimPubFunBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();

	/** 全局数据 */

	public LLClaimPubFunBL() {
	}
	
	
	/**
	 * 2009-5-07 zhangzheng
	 * 日期比较
	 * @param StateDate
	 * @param EndDate
	 * @return
	 */
    public static boolean checkDate(String StateDate, String EndDate)
    {
        String flag = "";
        FDate tFDate = new FDate();
        Date tStateDate = tFDate.getDate(StateDate);
        Date tEndDate = tFDate.getDate(EndDate);

        if (tStateDate.after(tEndDate))
        {
            flag = "0";
            logger.debug("开始日期晚于结束日期！！！！");

            return false;
        }
        else
        {
            flag = "1";
            logger.debug("开始日期早于结束日期！！！");

            return true;
        }
    }
    
    public static String Display_Year(String date)
    {
        String EndDate = "";

        if (!((date == null) || date.equals("")))
        {
            String t = "";
            String t1 = date.substring(0, 4);
            String t2;

            if (date.substring(5, 6).equals("0"))
            {
                t2 = date.substring(6, 7);
            }
            else
            {
                t2 = date.substring(5, 7);
            }

            String t3;

            if (date.substring(8, 9).equals("0"))
            {
                t3 = date.substring(9, 10);
            }
            else
            {
                t3 = date.substring(8, 10);
            }

            t = t1 + "年" + t2 + "月" + t3 + "日";
            logger.debug("转换后的日期是" + t);
            EndDate = t;
        }

        return EndDate;
    }
	
	/**
	 * 
	 * @param tClmNo 案件号,tUserCode,操作员
	 * @return true,有权限,false 无权限;
	 */
	public boolean getCheckPopedom(String tClmNo,String tUserCode,String tCasePayType) {
		
		 LLPrepayClaimSchema tLLPrepayClaimSchema=getLLPrepayClaim(tClmNo);
		 
		 String sql=" select distinct 1 from LLClaimPopedom b,llclaimuser a where a.JobCategory=b.JobCategory "
			       +" and a.usercode='"+  "?usercode?"+"' and b.casecategory='"+"?casecategory?"+"'"
		           +" and b.basemin<='"+"?prepaysum?"+"' and b.basemax>='"+"?prepaysum?"+"'";
		 
		 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tCasePayType
				          +",理赔金:"+tLLPrepayClaimSchema.getPrepaySum()+"的权限sql:"+sql);
		 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		 sqlbv.sql(sql);
		 sqlbv.put("usercode", tUserCode);
		 sqlbv.put("casecategory", tCasePayType);
		 sqlbv.put("prepaysum", tLLPrepayClaimSchema.getPrepaySum());
		 ExeSQL tExeSQL=new ExeSQL();
		 String tFlag=tExeSQL.getOneValue(sqlbv);
		// 理赔处理案件权限放开，以后再改回来
		// if(tFlag.equals("")){
		// return false;
		// }
		 
		 tExeSQL=null;
		 tFlag=null;
		 
		 return true;
	}
	
	
	/**
	 * 
	 * @param tClmNo 案件号,tUserCode,操作员
	 * @return true,有权限,false 无权限;
	 */
	public boolean getCheckPopedom(String tClmNo,String tUserCode) {
		
		 LLClaimSchema tLLClaimSchema=getLLClaim(tClmNo);
		 
		 LLClaimUWMainSchema tLLClaimUWMainSchema=getLLClaimUWMain(tClmNo);
		 
		 logger.debug("案件:"+tClmNo+"的审核结论:"+tLLClaimUWMainSchema.getAuditConclusion());
		 
		 /**
		  * 2009-05-08 zhangzheng
		  * 如果审核结论是拒付,由于拒付金额是存放在declinepay字段不是存放的realpay,所以权限校验金额字段取declinepay
		  */
		 
		 String sql="";
		 
		 if(tLLClaimUWMainSchema.getAuditConclusion().trim().equals("1"))
		 {
			 
			/* sql=" select distinct 1 from LLgrpClaimPopedom b,llgrpclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  tUserCode+"' and b.casecategory='4'"
		           +" and b.basemin<='"+tLLClaimSchema.getDeclinePay()+"' and b.basemax>='"+tLLClaimSchema.getDeclinePay()+"'";
			 
			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getDeclinePay()
			          +"理赔金:"+tLLClaimSchema.getDeclinePay()+"的权限sql:"+sql);*/
			 //zy 2010-02-21 个险的权限校验应查询个险的相关权限表信息
			 sql=" select distinct 1 from LLClaimPopedom b,llclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  "?usercode?"+"' and b.casecategory='4'"
		           +" and b.basemin<='"+"?declinepay?"+"' and b.basemax>='"+"?declinepay?"+"'";

			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getDeclinePay()
			          +"理赔金:"+tLLClaimSchema.getDeclinePay()+"的权限sql:"+sql);
	
		 }
		 else
		 {
			 
			/* sql=" select distinct 1 from LLgrpClaimPopedom b,llgrpclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  tUserCode+"' and b.casecategory='"+tLLClaimSchema.getCasePayType()+"'"
		           +" and b.basemin<='"+tLLClaimSchema.getRealPay()+"' and b.basemax>='"+tLLClaimSchema.getRealPay()+"'";
			 
			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getCasePayType()
			          +"理赔金:"+tLLClaimSchema.getRealPay()+"的权限sql:"+sql);*/
			 sql=" select distinct 1 from LLClaimPopedom b,llclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  "?usercode?"+"' and b.casecategory='"+"?casecategory?"+"'"
		           +" and b.basemin<='"+"?realpay?"+"' and b.basemax>='"+"?realpay?"+"'";
			 
			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getCasePayType()
			          +"理赔金:"+tLLClaimSchema.getRealPay()+"的权限sql:"+sql);
	
		 } 
		 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		 sqlbv1.sql(sql);
		 sqlbv1.put("usercode", tUserCode);
		 sqlbv1.put("casecategory", tLLClaimSchema.getCasePayType());
		 sqlbv1.put("realpay", tLLClaimSchema.getRealPay());
		 sqlbv1.put("declinepay", tLLClaimSchema.getDeclinePay());
		 
		 ExeSQL tExeSQL=new ExeSQL();
		 String tFlag=tExeSQL.getOneValue(sqlbv1);
		// 理赔处理案件权限放开，以后再改回来
		// if(tFlag.equals("")){
		// return false;
		// }
		 
		 tExeSQL=null;
		 tFlag=null;
		 
		 return true;
	}
	
	/**
	 * 判断案件给付类型
	 * 一般案件：系统显示为“0-一般给付件”。除短期出险件外的案件。
     * 短期出险件：系统显示为“1-短期出险件”。涉及赔付的险种保险期间在一年（不含）以上的，且出险日期在保单生效或复效日期两年以内的，出险原因为疾病，
     * 理赔类型为身故或重大疾病，系统判断为短期出险件
     * 3 责任免除件(拒付案件)
	 * @param tClmNo,赔案号
	 * @return casePayType: 0-一般给付件,2-短期给付件,3-责任免除件
	 */
	public String  getCheckCasePayType(String tClmNo) {
		
		String casePayType="";//案件给付类型
		
		//案件类型过滤
        String sql = " select * from LMCheckField where " +
        " riskcode = '000000'" + " and pageLocation = 'CLAIM'" +
        " and FieldName = 'CheckCasePayType'" + " ";
        
        logger.debug("过滤案件类型sql:"+sql);
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(sql);
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
		LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv3);
		
		for (int i = 1; i <= tLMCheckFieldSet.size(); i++)
		{
			Calculator tCalculator = new Calculator();
			
			tCalculator.setCalCode(tLMCheckFieldSet.get(i).getCalCode());
			tCalculator.addBasicFactor("clmno", tClmNo);            
			tCalculator.addBasicFactor("AccidentDate", String.valueOf(getInsDate(tClmNo)));// 出险日期
			 //保单险种号
			TransferData tTransferData = new TransferData();
			 tTransferData.setNameAndValue("clmno",String.valueOf(tClmNo));
			 tTransferData.setNameAndValue("AccidentDate",String.valueOf(getInsDate(tClmNo)));
			mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
			tCalculator.setBOMList(mBomList);
			casePayType = tCalculator.calculate();
			
			if (!casePayType.trim().equals(""))
			{
				break;
			}

		}
		
		//都不符合时即默认为0-一般给付件
		if ((casePayType == null) || casePayType.trim().equals(""))
		{
			casePayType="0";
		}
		
	    logger.debug("案件:"+tClmNo+"的案件類型確定為:"+casePayType);
		
		return casePayType;
	}
	
	/**
	 * 校验理算结果是否超过风险保额的限制
	 * 
	 * @param pRiskCode
	 * @return
	 */
	public boolean getCheckAmnt(LLClaimDetailSet mLLClaimDetailSet) {
		
		boolean flag=true;

		 try
		 {
		     //非空字段检验
			 if (mLLClaimDetailSet== null||mLLClaimDetailSet.size()==0)
			 {
			   // @@错误处理
			   CError.buildErr(this, "传入理算信息为空!");
			   return false;
			 }
			 
			 
			 //建立引用
		     LCGetSet tLCGetSet = null;
		     LCGetDB tLCGetDB = null;
	         LCDutyDB tLCDutyDB = null;
	         LCDutySet tLCDutySet = null;
	         LCPolDB tLCPolDB=null;
	         LCPolSchema tLCPolSchema=null;
			 
			 LLClaimPolicySet tLLClaimPolicySet=new LLClaimPolicySet();
			 LLClaimPolicyDB  tLLClaimPolicyDB=new  LLClaimPolicyDB();
			 tLLClaimPolicyDB.setClmNo(mLLClaimDetailSet.get(1).getClmNo());
			 tLLClaimPolicySet=tLLClaimPolicyDB.query();

		     
			 boolean riskCheckFlag=true;
			 
			 //检查保单赔付金额是否超过保单的保额
		     for (int i = 1; i <= tLLClaimPolicySet.size(); i++)
		     {
		    	 riskCheckFlag=true;

		         //查询险种下的责任定义
		         LMRiskDutyDB tLMRiskDutyDB = new LMRiskDutyDB();
				 LMRiskDutySet tLMRiskDutySet= new LMRiskDutySet();
				 
		         tLMRiskDutyDB.setRiskCode(tLLClaimPolicySet.get(i).getRiskCode());
		         tLMRiskDutySet = tLMRiskDutyDB.query();
		         
		         if(tLMRiskDutySet==null||tLMRiskDutySet.size()==0)
		         {
		  		       // @@错误处理
					   CError.buildErr(this, "查询不到险种"+tLLClaimPolicySet.get(i).getRiskCode()+"的责任定义信息!");
					   return false;
		         }

		         
		         //循环判断是否需要进行风险保额的校验
		         for (int j = 1; j <= tLMRiskDutySet.size(); j++)
		         {
		             LMDutyGetRelaSet tLMDutyGetRelaSet = new LMDutyGetRelaSet();
		             LMDutyGetRelaDB tLMDutyGetRelaDB = new LMDutyGetRelaDB();
		             tLMDutyGetRelaDB.setDutyCode(tLMRiskDutySet.get(j).getDutyCode());
		             tLMDutyGetRelaSet = tLMDutyGetRelaDB.query();
		             
			         if(tLMDutyGetRelaSet==null||tLMDutyGetRelaSet.size()==0)
			         {
			  		       // @@错误处理
						   CError.buildErr(this, "查询不到险种"+tLMRiskDutySet.get(j).getRiskCode()+"下的险种,责任关联信息!");
						   return false;
			         }
			         
			         
			         for (int k = 1; k <= tLMDutyGetRelaSet.size(); k++)
			         {
			                LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
			                LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
			                tLMDutyGetClmDB.setGetDutyCode(tLMDutyGetRelaSet.get(k).getGetDutyCode());
			                tLMDutyGetClmDB.setGetDutyKind(tLLClaimPolicySet.get(i).getGetDutyKind());
			                tLMDutyGetClmSet = tLMDutyGetClmDB.query();
			                
			                if(tLMDutyGetRelaSet==null||tLMDutyGetRelaSet.size()==0)
					        {
					  		       // @@错误处理
								   CError.buildErr(this, "查询不到责任"+tLMRiskDutySet.get(j).getDutyCode()+"下的给付关联信息!");
								   return false;
					        }
			                
			                for (int m = 1; m <= tLMDutyGetClmSet.size(); m++)
		                    {

			                	//如果为空,也表示不校验
			                	if(tLMDutyGetClmSet.get(m).getDeadToPValueFlag()==null||
			                			tLMDutyGetClmSet.get(m).getDeadToPValueFlag().equals("")){
			                		
			                		tLMDutyGetClmSet.get(m).setDeadToPValueFlag("N");
			                	}
			                	
			                	logger.debug("给付责任编码:"+tLMDutyGetClmSet.get(m).getGetDutyCode()+",校验风险保额标志:"+
			                			tLMDutyGetClmSet.get(m).getDeadToPValueFlag());
			                	
		                        //帐户型的不存在保额的概念,并且只有身故责任的险种只赔付一次,也无需担心赔出风险保额的概念
			                	if (tLMDutyGetClmSet.get(m).getDeadToPValueFlag().equals("N"))
		                        {
		                        	riskCheckFlag = false;
		                            break;
		                        }
		                    }

		                    if (riskCheckFlag==false)
		                    {
		                        break;
		                    }
			         }
		         
		         }
		         
		         //校验风险保额
		         if (riskCheckFlag==true)
		         {
		        	 
		             //查询该险种下责任信息
			         tLCDutyDB=new LCDutyDB();
			         tLCDutySet=new LCDutySet();
			         
			         tLCDutyDB.setPolNo(tLLClaimPolicySet.get(i).getPolNo());
			         tLCDutySet = tLCDutyDB.query();
			         
			         if(tLCDutySet==null||tLCDutySet.size()==0)
			         {
			  		       // @@错误处理
						   CError.buildErr(this, "查询不到保单"+tLLClaimPolicySet.get(i).getPolNo()+"的责任信息!");
						   return false;
			         }
			         
	

			          //查询保单所有的给付记录（包含本次赔付）
			         LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
			         LLClaimDetailSet tLLClaimDetailSet =new LLClaimDetailSet();
			         String tSql = " select * from llclaimdetail where polno='" +"?polno?" + "'"
			                     + " and clmno in(select clmno from llclaim where ClmState in('50','60'))";
			         
			         logger.debug("查询保单所有的给付记录（包含本次赔付）LLClaimDetail:"+tSql);
			         SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			         sqlbv4.sql(tSql);
			         sqlbv4.put("polno", mLLClaimDetailSet.get(i).getPolNo());
			         tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv4);
			         
			         
			         //查询保单所有的预付记录（包含本次赔付）
			         LLPrepayDetailDB tLLPrepayDetailDB = new LLPrepayDetailDB();
			         LLPrepayDetailSet tLLPrepayDetailSet =new LLPrepayDetailSet();
			         
			         String tSql2 = " select * from LLPrepayDetail c where polno='" +"?polno?" + "'"
			                      + " and exists(select 1 from ljagetclaim where othernotype='5'  and otherno=c.clmno and feeoperationtype='B')";
			         
			         logger.debug("查询保单所有的给付记录（包含本次赔付）LLPrepayDetail:"+tSql);
			         SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			         sqlbv5.sql(tSql2);
			         sqlbv5.put("polno", mLLClaimDetailSet.get(i).getPolNo());
			         tLLPrepayDetailSet = tLLPrepayDetailDB.executeQuery(sqlbv5);
			         
			         
			          //去掉责任编码不是6位的正常责任
		              for (int k = 1; k <= tLCDutySet.size(); k++)
		              {
		                  LCDutySchema tLCDutySchema = tLCDutySet.get(k);
		                  if (tLCDutySchema.getDutyCode().length() != 6)
		                  {
		                      tLCDutySet.remove(tLCDutySchema);
		                      k--;
		                  }
		              }
		                
		              for (int j = 1; j <= tLCDutySet.size(); j++)
		              {
		                  LCDutySchema tLCDutySchema = new LCDutySchema();
		                  tLCDutySchema = tLCDutySet.get(j);

		                  double tSumDuty = 0;
		                  
		                  LLClaimDetailSchema tLLClaimDetailSchema=null;

		                  //累计给付责任的赔付金额
		                  for (int k = 1; k <= tLLClaimDetailSet.size(); k++)
		                  {
		                	    tLLClaimDetailSchema = new LLClaimDetailSchema();
		                        tLLClaimDetailSchema = tLLClaimDetailSet.get(k);

		                        if (tLLClaimDetailSchema.getDutyCode().substring(0, 6).equals(tLCDutySchema.getDutyCode().substring(0,6)) &&
		                                tLLClaimDetailSchema.getPolNo().equals(tLCDutySchema.getPolNo()))
		                        {
		                            tSumDuty = tSumDuty +
		                                       tLLClaimDetailSchema.getRealPay();
		                        }
		                        
		                        tLLClaimDetailSchema=null;
		                 }
		                  
		                  LLPrepayDetailSchema tLLPrepayDetailSchema=null;
		 
		                  //累计预付的赔付金额
		                  for (int k = 1; k <= tLLPrepayDetailSet.size(); k++)
		                  {
		                	    tLLPrepayDetailSchema = new LLPrepayDetailSchema();
		                	    
		                	    tLLPrepayDetailSchema = tLLPrepayDetailSet.get(k);
	
		                        if (tLLPrepayDetailSchema.getDutyCode().substring(0, 6).equals(tLCDutySchema.getDutyCode().substring(0,6)) &&
		                        		tLLPrepayDetailSchema.getPolNo().equals(tLCDutySchema.getPolNo()))
		                        {
		                            tSumDuty = tSumDuty +
		                              tLLPrepayDetailSchema.getPrepaySum();
		                        }
		                        
		                        tLLPrepayDetailSchema=null;
		                 }

		                 //累计本次赔付金额
		                 double tThisSumDuty = 0;

		                 for (int k = 1; k <= mLLClaimDetailSet.size(); k++)
		                 {
		                	  tLLClaimDetailSchema = new LLClaimDetailSchema();
		                      tLLClaimDetailSchema = mLLClaimDetailSet.get(k);

		                      if (tLLClaimDetailSchema.getDutyCode().substring(0, 6).equals(tLCDutySchema.getDutyCode().substring(0,6)) &&
		                                tLLClaimDetailSchema.getPolNo().equals(tLCDutySchema.getPolNo()))
		                      {
		                            tThisSumDuty = tThisSumDuty +tLLClaimDetailSchema.getRealPay();
		                      }
		                      
		                      tLLClaimDetailSchema=null;
		                 }

		                 
		                 logger.debug(tLCDutySchema.getDutyCode() + "=" +tSumDuty + tThisSumDuty);

		                 String ttSql ="select sum(riskamnt) from lcduty where polno='" +
		                        "?polno?" + "' and dutycode like concat('" +
		                        "?dutycode?" + "','%')";
		                 
		                 logger.debug("查询险种"+tLCDutySchema.getPolNo()+"责任:"+tLCDutySchema.getDutyCode()+"下的riskamnt的语句:"+ttSql);
		                 SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		                 sqlbv6.sql(ttSql);
		                 sqlbv6.put("polno", tLCDutySchema.getPolNo());
		                 sqlbv6.put("dutycode", tLCDutySchema.getDutyCode());
		                 ExeSQL ttExeSQL = new ExeSQL();
		                 SSRS tSSRS = ttExeSQL.execSQL(sqlbv6);
		                 
		                 double tDutyAmnt = Double.parseDouble(tSSRS.GetText(1, 1));

		                 logger.debug(tLCDutySchema.getDutyCode() + "=" +tDutyAmnt);

		                 if (((tSumDuty + tThisSumDuty) - tDutyAmnt) > 0.01)
		                 {
		                        CError.buildErr(this, "历次赔付超过责任保额，保单：" +tLLClaimPolicySet.get(i).getPolNo() +"，责任编码：" +
                                                       tLCDutySchema.getDutyCode() +"，已赔付金额:" + tThisSumDuty);
		                        return false;
		                    }
		                }

		                //检查保单是否超过保单保额
		                tLCPolDB = new LCPolDB();
		                tLCPolDB.setPolNo(tLLClaimPolicySet.get(i).getPolNo());

		                if (!tLCPolDB.getInfo())
		                {
		                    CError.buildErr(this, "查询保单"+tLLClaimPolicySet.get(i).getPolNo()+"的信息失败!");
		                    return false;
		                }

		                tLCPolSchema = new LCPolSchema();
		                tLCPolSchema.setSchema(tLCPolDB.getSchema());

		                double tSumPay = 0;
		                double tSGetSum = 0;
		                
				        //查询该险种下的给付责任
				        tLCGetSet = new LCGetSet();
				        tLCGetDB = new LCGetDB();
				         
				        tLCGetDB.setPolNo(tLLClaimPolicySet.get(i).getPolNo());
				        tLCGetSet=tLCGetDB.query();
				         
				        if(tLCGetSet==null||tLCGetSet.size()==0)
				        {
				  		       // @@错误处理
							   CError.buildErr(this, "查询不到保单"+tLLClaimPolicySet.get(i).getPolNo()+"的给付责任信息!");
							   return false;
				        }

		                for (int j = 1; j <= tLCGetSet.size(); j++)
		                {
		                    if (tLCGetSet.get(j).getLiveGetType().equals("1"))
		                    {
		                        tSumPay = tSumPay + tLCGetSet.get(j).getSumMoney();
		                    }
		                }

		                tSumPay = tSumPay + tLLClaimPolicySet.get(i).getRealPay();
		                logger.debug("tSumPay=" + tSumPay);
		                logger.debug("RiskAmnt=" + tLCPolSchema.getRiskAmnt());

		                if ((tSumPay - tLCPolSchema.getRiskAmnt()) > 0.01)
		                {
		                    CError.buildErr(this, "历次赔付超过保单保额，保单：" +
		                    		tLLClaimPolicySet.get(i).getPolNo());
		                    return false;
		                }
		                
		                tLCDutyDB=null;
		                tLCDutySet=null;
		                tLCPolDB=null;
		                tLCPolSchema=null;
		            }
		         
				    //释放强引用
		            tLCGetSet=null;
		            tLCGetDB=null;
		     }
		 }
		 catch (Exception ex)
		 {
			 // @@错误处理
			 CError.buildErr(this, "在校验输入的数据时出错!");
			 return false;
		 }
		 
		 
		return flag;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－产品定义信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 得到险种信息
	 * 
	 * @param pRiskCode
	 * @return
	 */
	public LMRiskSchema getLMRisk(String pRiskCode) {
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();

		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(pRiskCode);
		LMRiskSet tLMRiskSet = tLMRiskDB.query();

		if (tLMRiskDB.mErrors.needDealError()) {
			mErrors.addOneError("险种编码[" + pRiskCode + "]的定义信息没有取到!!!"
					+ tLMRiskDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMRiskSet.size() == 1) {
			tLMRiskSchema = tLMRiskSet.get(1);
		} else {
			mErrors.addOneError("险种编码[" + pRiskCode + "]的定义信息为空没有取到!!!");
			return null;
		}
		return tLMRiskSchema;
	}

	/**
	 * 得到险种承保信息
	 * 
	 * @param pRiskCode
	 * @return
	 */
	public LMRiskAppSchema getLMRiskApp(String pRiskCode) {
		LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(pRiskCode);
		LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();

		if (tLMRiskAppDB.mErrors.needDealError()) {
			mErrors.addOneError("险种编码[" + pRiskCode + "]的承保信息没有取到!!!"
					+ tLMRiskAppDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMRiskAppSet.size() == 1) {
			tLMRiskAppSchema = tLMRiskAppSet.get(1);
		} else {
			mErrors.addOneError("险种编码[" + pRiskCode + "]的承保信息为空没有取到!!!");
			return null;
		}
		return tLMRiskAppSchema;
	}

	/**
	 * 险种与责任的关联信息
	 * 
	 * @return
	 */
	public LMRiskDutySchema getLMRiskDuty(String pRiskCode, String pDutyCode) {
		LMRiskDutySchema tLMRiskDutySchema = new LMRiskDutySchema();

		LMRiskDutyDB tLMRiskDutyDB = new LMRiskDutyDB();
		tLMRiskDutyDB.setRiskCode(pRiskCode);
		tLMRiskDutyDB.setDutyCode(pDutyCode);
		LMRiskDutySet tLMRiskDutySet = tLMRiskDutyDB.query();

		if (tLMRiskDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("险种编码[" + pRiskCode + "],责任编码[" + pDutyCode
					+ "]的险种与责任关联信息没有取到!!!"
					+ tLMRiskDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMRiskDutySet.size() == 1) {
			tLMRiskDutySchema = tLMRiskDutySet.get(1);
		} else {
			mErrors.addOneError("险种编码[" + pRiskCode + "],责任编码[" + pDutyCode
					+ "]的险种与责任关联信息为空没有取到!!!");
			return null;
		}
		return tLMRiskDutySchema;
	}

	/**
	 * 险种与责任的关联信息
	 * 
	 * @return
	 */
	public LMRiskDutySet getLMRiskDutySet(String pRiskCode, String pDutyCode) {
		LMRiskDutySchema tLMRiskDutySchema = new LMRiskDutySchema();

		LMRiskDutyDB tLMRiskDutyDB = new LMRiskDutyDB();
		if (pRiskCode != null || pRiskCode.length() != 0) {
			tLMRiskDutyDB.setRiskCode(pRiskCode);
		}

		if (pDutyCode != null || pDutyCode.length() != 0) {
			tLMRiskDutyDB.setDutyCode(pDutyCode);
		}

		LMRiskDutySet tLMRiskDutySet = tLMRiskDutyDB.query();

		if (tLMRiskDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("险种编码[" + pRiskCode + "],责任编码[" + pDutyCode
					+ "]的险种与责任关联信息没有取到!!!"
					+ tLMRiskDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMRiskDutySet.size() == 0) {
			mErrors.addOneError("险种编码[" + pRiskCode + "],责任编码[" + pDutyCode
					+ "]的险种与责任关联信息为空没有取到!!!");
			return null;
		}
		return tLMRiskDutySet;
	}

	/**
	 * 责任信息
	 * 
	 * @return
	 */
	public LMDutySchema getLMDuty(String pDutyCode) {

		LMDutySchema tLMDutySchema = new LMDutySchema();

		// 2006-05-16 周磊 对加保保项处理,2009-02-26 增加对增额缴清保项的处理 (dutycode的长度等于10)
		if (pDutyCode.length() == 8||pDutyCode.length() == 10) {
			pDutyCode = pDutyCode.substring(0, 6);
		}
		
		
		LMDutyDB tLMDutyDB = new LMDutyDB();
		tLMDutyDB.setDutyCode(pDutyCode);
		LMDutySet tLMDutySet = tLMDutyDB.query();

		if (tLMDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("责任编码[" + pDutyCode + "]的责任信息没有取到!!!"
					+ tLMDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMDutySet.size() == 1) {
			tLMDutySchema = tLMDutySet.get(1);
		} else {
			mErrors.addOneError("责任编码[" + pDutyCode + "]的责任信息为空没有取到!!!");
			return null;
		}
		return tLMDutySchema;
	}

	/**
	 * 根据类型找出年金的责任信息
	 * 
	 * @param tRiskCode
	 * @param tType
	 * @return
	 */
	public LMDutyGetSet getLMDutyGetForPension(String tRiskCode, String tType) {
		String tSql = "select LMDutyGet.* from LMDutyGet,LMDutyGetRela,LMRiskDuty where 1=1"
				+ " and LMDutyGet.GetDutyCode = LMDutyGetRela.GetDutyCode "
				+ " and LMRiskDuty.DutyCode = LMDutyGetRela.DutyCode "
				+ " and LMRiskDuty.RiskCode = '"
				+ "?RiskCode?"
				+ "'"
				+ " and LMRiskDuty.ChoFlag = 'L'" // 代表年金责任
				+ " and LMDutyGet.Type = '" + "?Type?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("RiskCode", tRiskCode);
		sqlbv7.put("Type", tType);
		LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
		LMDutyGetSet tLMDutyGetSet = tLMDutyGetDB.executeQuery(sqlbv7);

		if (tLMDutyGetDB.mErrors.needDealError()) {
			mErrors.addOneError("险种[" + tRiskCode + "]的责任信息没有取到!!!");
			return null;
		}

		if (tLMDutyGetSet.size() == 0) {
			return null;
		}

		return tLMDutyGetSet;
	}
	
	
	
	/**
	 * 责任给付赔付
	 * 
	 * @param pRiskCode
	 * @param pGetDutyCode
	 * @return
	 */
	public LMDutyGetClmSet getLMDutyGetClmSet(String pRiskCode) {

		LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
		
		String sql=" select * from lmdutygetclm where getdutycode in"
			   +"(select getdutycode from lmdutygetrela where dutycode in(select dutycode from lmriskduty where riskcode in('"+"?riskcode?"+"')))";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("riskcode", pRiskCode);
		LMDutyGetClmSet tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(sqlbv8);

		if (tLMDutyGetClmDB.mErrors.needDealError()) {
			mErrors.addOneError(pRiskCode + "]的产品定义信息没有取到!!!"
					+ tLMDutyGetClmDB.mErrors.getError(0).errorMessage);
			return null;
		}

		return tLMDutyGetClmSet;
	}

	/**
	 * 责任给付赔付
	 * 
	 * @param pGetDutyKind
	 * @param pGetDutyCode
	 * @return
	 */
	public LMDutyGetClmSchema getLMDutyGetClm(String pGetDutyKind,
			String pGetDutyCode) {

		LMDutyGetClmSchema tLMDutyGetClmSchema = new LMDutyGetClmSchema();
		LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
		tLMDutyGetClmDB.setGetDutyKind(pGetDutyKind);
		tLMDutyGetClmDB.setGetDutyCode(pGetDutyCode);

		LMDutyGetClmSet tLMDutyGetClmSet = tLMDutyGetClmDB.query();

		if (tLMDutyGetClmDB.mErrors.needDealError()) {
			mErrors.addOneError("理赔类型[" + pGetDutyKind + "],给付责任["
					+ pGetDutyCode + "]的产品定义信息没有取到!!!"
					+ tLMDutyGetClmDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMDutyGetClmSet.size() == 1) {
			tLMDutyGetClmSchema = tLMDutyGetClmSet.get(1);
		} else {
			mErrors.addOneError("理赔类型[" + pGetDutyKind + "],给付责任["
					+ pGetDutyCode + "]的产品定义信息为空!!!");
			return null;
		}

		return tLMDutyGetClmSchema;
	}

	/**
	 * 责任给付赔付扩充计算公式
	 * 
	 * @param pGetDutyKind
	 * @param pGetDutyCode
	 * @return
	 */
	public LMDutyGetClmCalSchema getLMDutyGetClmCal(String pGetDutyKind,
			String pGetDutyCode) {

		LMDutyGetClmCalSchema tLMDutyGetClmCalSchema = new LMDutyGetClmCalSchema();
		LMDutyGetClmCalDB tLMDutyGetClmCalDB = new LMDutyGetClmCalDB();
		tLMDutyGetClmCalDB.setGetDutyKind(pGetDutyKind);
		tLMDutyGetClmCalDB.setGetDutyCode(pGetDutyCode);

		LMDutyGetClmCalSet tLMDutyGetClmCalSet = tLMDutyGetClmCalDB.query();

		if (tLMDutyGetClmCalDB.mErrors.needDealError()) {
			mErrors.addOneError("理赔类型[" + pGetDutyKind + "],给付责任["
					+ pGetDutyCode + "]的责任给付赔付扩充计算公式信息没有取到!!!"
					+ tLMDutyGetClmCalDB.mErrors.getError(0).errorMessage);
		}

		if (tLMDutyGetClmCalSet.size() == 1) {
			tLMDutyGetClmCalSchema = tLMDutyGetClmCalSet.get(1);
		}

		return tLMDutyGetClmCalSchema;
	}

	/**
	 * 对LMRiskSort进行判断 7.理算金额是否大于有效保额,用于理算第五步及审批通过时的保额校验
	 * 8.理赔医疗类附加险单独理算[225],用于理算 
	 * 9. 责任下多个给付责任共享险种保额,理算时给付责任的相互冲减
	 * 10 责任下多个给付责任分类共享某个保额,理算时给付责任的相互冲减
	 * 20.主险终止附加险必须终止,用于合同处理 11.长期附加险应退现金价值[277,280,332],用于合同处理退现金价值计算
	 * 12.附加险等同于主险[293,294]，用于匹配时计算主险的终了红利 13.匹配时需要取基本保额当作有效保额的险种[107],用于匹配
	 * 14.审批通过时冲减LCGet上的SumMoney已领金额
	 * 
	 * @return 暂时不用 一年内初始保额,一年后有效保额[601、611、616、617、623、610、701]
	 */
	public boolean getLMRiskSort(String pRiskCode, String pRiskType) {
		LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(pRiskCode);
		tLMRiskSortDB.setRiskSortType(pRiskType);
		LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();

		// 没有查到定义的数据
		if (tLMRiskSortSet.size() >0) {
			return true;
		}

		return false;
	}

	/**
	 * 得到给付责任的名称
	 * 
	 * @return
	 */
	public String getGetDutyName(LCGetSchema tLCGetSchema) {
		String tReturn = "";
		String tSql = "select GetDutyName from LMDutyGetRela where 1=1 "
				+ " and GetDutyCode = '" + "?GetDutyCode?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("GetDutyCode", tLCGetSchema.getGetDutyCode());
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv9));
		if (tExeSQL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保项名称时出错!";
			this.mErrors.addOneError(tError);
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getGetDutyName()--查询保项名称时出错!"
					+ tSql);
			logger.debug("------------------------------------------------------");
		}
		return tReturn;
	}

	/**
	 * 得到宽限期的天数
	 * 
	 * @param pRiskCode
	 *            险种编号
	 * @return
	 */
	public int getGracePeriod(String pRiskCode) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		tLMRiskPayDB.setRiskCode(pRiskCode);
		// tLMRiskPayDB.setRiskVer(pRiskVer);
		LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();

		int tGracePeriod = 0;
		if (tLMRiskPaySet != null && tLMRiskPaySet.size() == 1) {
			tGracePeriod = tLMRiskPaySet.get(1).getGracePeriod();
		}
		return tGracePeriod;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－产品定义信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－承保处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 目的:得到合同信息
	 * 
	 */
	public LCContSchema getLCCont(String pContNo) {

		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(pContNo);
		LCContSet tLCContSet = tLCContDB.query();

		if (tLCContDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的合同信息没有取到!!!"
					+ tLCContDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCContSet.size() == 1) {
			tLCContSchema = tLCContSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "]的合同信息为空没有取到!!!");
			return null;
		}

		return tLCContSchema;
	}

	/**
	 * 目的:得到投保人信息
	 * 
	 */
	public LCAppntSchema getLCAppnt(String pContNo, String pCustNo) {

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(pContNo);
		tLCAppntDB.setAppntNo(pCustNo);
		LCAppntSet tLCAppntSet = tLCAppntDB.query();

		if (tLCAppntDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的投保人信息没有取到!!!"
					+ tLCAppntDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCAppntSet.size() == 1) {
			tLCAppntSchema = tLCAppntSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的投保人信息为空没有取到!!!");
			return null;
		}

		return tLCAppntSchema;
	}

	/**
	 * 目的:得到被保人信息
	 * 
	 */
	public LCInsuredSchema getLCInsured(String pContNo, String pCustNo) {

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(pContNo);
		tLCInsuredDB.setInsuredNo(pCustNo);
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();

		if (tLCInsuredDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的被保人信息没有取到!!!"
					+ tLCInsuredDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCInsuredSet.size() == 1) {
			tLCInsuredSchema = tLCInsuredSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的被保人信息为空没有取到!!!");
			return null;
		}

		return tLCInsuredSchema;
	}

	/**
	 * 目的:得到被保人信息
	 * 
	 */
	public LCInsuredSet getLCInsured(String pContNo) {

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(pContNo);
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();

		if (tLCInsuredDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的被保人信息没有取到!!!"
					+ tLCInsuredDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCInsuredSet.size() == 0) {
			mErrors.addOneError("合同号[" + pContNo + "]的被保人信息为空没有取到!!!");
			return null;
		}

		return tLCInsuredSet;
	}

	/**
	 * 目的:得到保单险种信息
	 * 
	 */
	public LCPolSchema getLCPol(String pPolNo) {

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(pPolNo);
		LCPolSet tLCPolSet = tLCPolDB.query();

		if (tLCPolDB.mErrors.needDealError()) {
			mErrors.addOneError("险种号[" + pPolNo + "]的险种信息没有取到!!!"
					+ tLCPolDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPolSet.size() == 1) {
			tLCPolSchema = tLCPolSet.get(1);
		} else {
			mErrors.addOneError("险种号[" + pPolNo + "]的险种信息为空没有取到!!!");
			return null;
		}

		return tLCPolSchema;
	}

	/**
	 * 目的:得到保单险种信息
	 * 
	 */
	public LCPolSet getLCPolSet(String pContNo) {

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(pContNo);
		LCPolSet tLCPolSet = tLCPolDB.query();

		if (tLCPolDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的险种信息没有取到!!!"
					+ tLCPolDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPolSet.size() == 0) {
			mErrors.addOneError("合同号[" + pContNo + "]的险种信息为空没有取到!!!");
			return null;
		}

		return tLCPolSet;
	}

	/**
	 * 目的:通过合同得到保单主险信息
	 * 
	 */
	public LCPolSchema getLCPolForMRisk(String pContNo) {

		String tSql = "select * from LCPol where 1=1 " + " and ContNo='"
				+ "?ContNo?" + "'" + " and PolNo = MainPolNo";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(tSql);
		sqlbv10.put("ContNo", pContNo);
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv10);

		if (tLCPolDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的主险信息没有取到!!!"
					+ tLCPolDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPolSet.size() == 1) {
			tLCPolSchema = tLCPolSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "]的主险信息为空没有取到");
			return null;
		}

		return tLCPolSchema;
	}

	/**
	 * 目的:得到保单险种的责任信息
	 * 
	 */
	public LCDutySet getLCDuty(String pPolNo) {

		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(pPolNo);
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("保单险种[" + pPolNo + "]的责任信息没有取到!!!"
					+ tLCDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCDutySet.size() == 0) {
			mErrors.addOneError("保单险种[" + pPolNo + "]的责任信息为空没有取到!!!");
			return null;
		}

		return tLCDutySet;
	}

	/**
	 * 目的:得到保单险种的责任信息
	 * 
	 */
	public LCDutySchema getLCDuty(String pPolNo, String pDutyCode) {

		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(pPolNo);
		tLCDutyDB.setDutyCode(pDutyCode);
		LCDutySet tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("保单号[" + pPolNo + "]，责任编码[" + pDutyCode
					+ "]的责任信息没有取到!!!"
					+ tLCDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCDutySet.size() == 1) {
			tLCDutySchema = tLCDutySet.get(1);
		} else {
			mErrors.addOneError("保单号[" + pPolNo + "]，责任编码[" + pDutyCode
					+ "]的责任信息为空没有取到!!!");
			return null;
		}

		return tLCDutySchema;
	}

	/**
	 * 保费项表
	 * 
	 * @return
	 */
	public LCPremSet getLCPremForCont(String pContNo) {
		String tSql = "select * from LCPrem where 1=1 " + " and ContNo = '"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("ContNo", pContNo);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv11);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCPremForCont[" + tLCPremSet.size() + "]:"
				+ tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCPremDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的保费项信息没有取到!!!"
					+ tLCPremDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPremSet.size() == 0) {
			mErrors.addOneError("合同号[" + pContNo + "]的保费项信息为空没有取到!!!");
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 保费项表 LLBalRecedeFeeBL.java
	 * 
	 * @return
	 */
	public LCPremSet getLCPremForPol(String pPolNo) {
		String tSql = "select * from LCPrem where 1=1 " + " and PolNo = '"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("PolNo", pPolNo);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv12);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCPremForPol[" + tLCPremSet.size() + "]:"
				+ tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCPremDB.mErrors.needDealError()) {
			mErrors.addOneError("险种号[" + pPolNo + "]的保费项信息没有取到!!!"
					+ tLCPremDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPremSet.size() == 0) {
			mErrors.addOneError("险种号[" + pPolNo + "]的保费项信息为空没有取到!!!");
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 保费项表,不包括加费信息 LLClaimCalAutoBL.java
	 * 
	 * @return
	 */
	public LCPremSchema getLCPremSql(String pPolNo, String pDutyCode,
			String pPayPlanType) {
		String tSql = "select * from LCPrem where 1=1 " + " and PolNo = '"
				+ "?PolNo?" + "'" + " and DutyCode = '" + "?DutyCode?" + "'"
				+ " and PayPlanType in ('" + "?PayPlanType?" + "')"
				+ " and char_Length(Trim(PayPlanCode))=6 ";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tSql);
		sqlbv13.put("PolNo", pPolNo);
		sqlbv13.put("DutyCode", pDutyCode);
		sqlbv13.put("PayPlanType", pPayPlanType);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv13);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCPremSql[" + tLCPremSet.size() + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCPremDB.mErrors.needDealError()) {
			mErrors.addOneError("险种号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],交费计划类型[" + pPayPlanType + "]的保费项信息没有取到!!!"
					+ tLCPremDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPremSet.size() != 1) {
			mErrors.addOneError("险种号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],交费计划类型[" + pPayPlanType + "]的保费项信息为空没有取到!!!");
			return null;
		}

		return tLCPremSet.get(1);
	}

	/**
	 * 保费项表
	 * 
	 * @return
	 */
	public LCPremSet getLCPremSetSql(String pContNo, String pPolNo,
			String pDutyCode, String pPayPlanType) {
		String tSql = "select * from LCPrem where 1=1 ";

		if (pContNo != null || pContNo.length() != 0) {
			tSql = tSql + " and ContNo = '" + "?ContNo?" + "'";
		}

		if (pPolNo != null || pPolNo.length() != 0) {
			tSql = tSql + " and PolNo = '" + "?PolNo?" + "'";
		}

		if (pDutyCode != null || pDutyCode.length() != 0) {
			tSql = tSql + " and DutyCode = '" + "?DutyCode?" + "'";
		}

		if (pPayPlanType != null || pPayPlanType.length() != 0) {
			tSql = tSql + " and PayPlanType in ('" + "?PayPlanType?" + "')";
		}
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(tSql);
		sqlbv14.put("ContNo", pContNo);
		sqlbv14.put("PolNo", pPolNo);
		sqlbv14.put("DutyCode", pDutyCode);
		sqlbv14.put("PayPlanType", pPayPlanType);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv14);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCPremSetSql[" + tLCPremSet.size() + "]:"
				+ tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCPremDB.mErrors.needDealError()) {
			mErrors.addOneError("险种号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],交费计划类型[" + pPayPlanType + "]的保费项信息没有取到!!!"
					+ tLCPremDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPremSet.size() == 0) {
			mErrors.addOneError("险种号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],交费计划类型[" + pPayPlanType + "]的保费项信息为空没有取到!!!");
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 责任项表
	 * 
	 * @return
	 */
	public LCGetSet getLCGetForCont(String pContNo) {
		String tSql = "select * from LCGet where 1=1 " + " and ContNo = '"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(tSql);
		sqlbv15.put("ContNo", pContNo);
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv15);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCGetForCont[" + tLCGetSet.size() + "]:"
				+ tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "]的责任项信息没有取到!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() == 0) {
			mErrors.addOneError("合同号[" + pContNo + "]的责任项信息为空没有取到!!!");
			return null;
		}

		return tLCGetSet;
	}

	/**
	 * 责任项表
	 * 
	 * @return
	 */
	public LCGetSet getLCGetForPol(String pPolNo) {
		String tSql = "select * from LCGet where 1=1 " + " and PolNo = '"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("PolNo", pPolNo);
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv16);

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--getLCGetForPol[" + tLCGetSet.size() + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("险种号[" + pPolNo + "]的责任项信息没有取到!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() == 0) {
			mErrors.addOneError("险种号[" + pPolNo + "]的责任项信息为空没有取到!!!");
			return null;
		}

		return tLCGetSet;
	}

	/**
	 * 目的:得到保单险种的领取项信息
	 * 
	 */
	public LCGetSchema getLCGet(String pPolNo, String pDutyCode,
			String pGetDutyCode) {

		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(pPolNo);
		tLCGetDB.setDutyCode(pDutyCode);
		tLCGetDB.setGetDutyCode(pGetDutyCode);
		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("保单号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],给付责任类型[" + pGetDutyCode + "]的领取项信息没有取到!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() == 1) {
			tLCGetSchema = tLCGetSet.get(1);
		} else {
			mErrors.addOneError("保单号[" + pPolNo + "],责任编码[" + pDutyCode
					+ "],给付责任类型[" + pGetDutyCode + "]的领取项信息为空没有取到!!!");
			return null;
		}

		return tLCGetSchema;
	}

	/**
	 * 
	 * @param pContNo:合同号
	 * @param pPolNo:险种号
	 * @param pStateType:状态类型，Available:
	 *            0-有效 1-失效 （险种状态） Terminate: 0-未终止 1-终止 （险种状态）
	 * @param pState:0有效，1无效
	 * @return
	 */

	public LCContStateSet getLCContState(String pContNo, String pPolNo,
			String pStateType, String pState) {

		LCContStateSchema tLCContStateSchema = new LCContStateSchema();
		LCContStateDB tLCContStateDB = new LCContStateDB();
		tLCContStateDB.setContNo(pContNo);
		tLCContStateDB.setPolNo(pPolNo);
		tLCContStateDB.setStateType(pStateType);
		tLCContStateDB.setState(pState);
		LCContStateSet tLCContStateSet = tLCContStateDB.query();

		if (tLCContStateDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "],保单号[" + pPolNo
					+ "],状态类型[" + pStateType + "],状态[" + pState
					+ "]的合同状态信息没有取到!!!"
					+ tLCContStateDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCContStateSet.size() == 1) {
			tLCContStateSchema = tLCContStateSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "],保单号[" + pPolNo
					+ "],状态类型[" + pStateType + "],状态[" + pState + "]的合同状态信息有["
					+ tLCContStateSet.size() + "]条记录!!!");
			return null;
		}

		return tLCContStateSet;
	}

	/**
	 * 目的:计算两个日期之间的缴费期数 pBDate:开始时间 pEDate:终止时间 pPayIntv:缴费间隔 pStandByDate:出险时间
	 */
	public double getLCPremPeriodTimes(String pBDate, String pEDate,
			String pPayIntv, String pStandByDate) {
		double tDPeriodTimes = 0; // 返回的最后缴费期数
		int tIPayIntv = 0; // 缴费间隔
		String tEDate = ""; // 计算后的缴费截止日期

		logger.debug("-------------------计算两个日期之间的缴费期数------开始-------------------\n");

		logger.debug("-----开始时间:[" + pBDate + "],结束时间:[" + pEDate
				+ "],缴费间隔:[" + pPayIntv + "],出险时间:[" + pStandByDate + "]\n");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 0 －－ 趸缴 1 －－ 月缴 3 －－ 季缴 6 －－
		 * 半年缴 12 －－ 年缴 36 －－ 三年缴 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 如果 出险时间 < 缴费终止日
		 * 2004-01-01 < 2005-10-01 则 计算终止日期 = 出险时间 否则 计算终止日期 = 缴费终止日 -
		 * 缴费间隔月数[将缴费终止日期往前推一期]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tDays = PubFun.calInterval(pStandByDate, pEDate, "D");
		if (tDays > 0) {
			tEDate = pStandByDate;
		} else {
			tIPayIntv = Integer.parseInt(pPayIntv);
			tEDate = PubFun.calDate(pEDate, -tIPayIntv, "M", pEDate);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算开始时间，结束时间间隔的月数
		 * 对于月缴[1]的数据，需要将月数进行加一处理 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tIMonth = PubFun.calInterval(pBDate, tEDate, "M");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 对于月缴[1]的数据，需要将月数进行加一处理 2005-01-02 到 2006-01-01 PubFun计算为 11 个月
		 * 可实际业务中应为12个月 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (pPayIntv.equals("1")) {
			tIMonth = tIMonth + 1;
		}

		logger.debug("-----经过的整月数:[" + tIMonth + "],开始时间:[" + pBDate
				+ "],结束时间:[" + tEDate + "]");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 计算2个日期的天数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tISMonth = getDateUnit(pBDate, "M");
		int tIEMonth = getDateUnit(tEDate, "M");

		int tISDay = getDateUnit(pBDate, "D");
		int tIEDay = getDateUnit(tEDate, "D");

		if (!pPayIntv.equals("1") && tISMonth <= tIEMonth && tISDay <= tIEDay) {
			tIMonth = tIMonth + 1;
			logger.debug("-----计算后经过的月数:[" + tIMonth + "]");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 最后确定的缴费月数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double tDMonth = Double.parseDouble(String.valueOf(tIMonth));
		logger.debug("-----最后确定的缴费月数:[" + tIMonth + "]");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
		 * 当缴费方式为趸交,或者两个日期为同月时,返回的期数为1
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (pPayIntv.equals("0") || pPayIntv.equals("-1") || tIMonth == 0) {
			tDPeriodTimes = 1;
		} else {
			tDPeriodTimes = Arith.div(tDMonth, Double.parseDouble(pPayIntv));
			double tMod = tDMonth % Double.parseDouble(pPayIntv);

			if (tMod != 0) {
				tDPeriodTimes = tDPeriodTimes + 1;
				tDPeriodTimes = (int) tDPeriodTimes;
			}

			logger.debug("-----初步确定的缴费期数:[" + tDPeriodTimes + "]");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 开始日期 + 计算的期数 * 期
			 * 与 终止日期相比较 如果大于等于终止日期，需要期数再加一，否则不动
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSSumMonth = String.valueOf((int) Arith.round(tDPeriodTimes
					* Double.parseDouble(pPayIntv), 0));// 总月数
			int tISumMonth = Integer.parseInt(tSSumMonth);
			String tStandaDate = PubFun
					.calDate(pBDate, tISumMonth, "M", pBDate);
			logger.debug("-----开始日期 + 期数 * 期后的日期:[" + tStandaDate + "]");
			int tCalDay = PubFun.calInterval(tStandaDate, tEDate, "D");
			if (tCalDay >= 0) {
				tDPeriodTimes = tDPeriodTimes + 1;
				tDPeriodTimes = (int) tDPeriodTimes;
			}

		}

		logger.debug("-----最后确定的缴费期数:[" + tDPeriodTimes + "]");

		logger.debug("\n-------------------计算两个日期之间的缴费期数------结束-------------------");
		return tDPeriodTimes;
	}

	/**
	 * 被保险人投保年龄
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	public int getInsuranceAge(LCPolSchema pLCPolSchema) {
		int tIReturn = 0;
		String tInsuredBirthday = pLCPolSchema.getInsuredBirthday();
		String tCValiDate = pLCPolSchema.getCValiDate();
		tIReturn = PubFun.calInterval(tInsuredBirthday, tCValiDate, "Y");
		return tIReturn;
	}

	/**
	 * 目的：得到险种的类型，M主险，S附加险
	 * 
	 * @param pRiskCode
	 * @return
	 */
	public String getRiskType(String pRiskCode) {
		String tSReturn = "";
		LMRiskAppSchema tLMRiskAppSchema = getLMRiskApp(pRiskCode);
		if (tLMRiskAppSchema == null) {
			return "";
		}
		tSReturn = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());

		return tSReturn;
	}

	/**
	 * [废弃不用]判断当期保费是否已交 1-应交已交 0-应交未交
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return String
	 */
	public String getPayFeeFlag(LCPolSchema pLCPolSchema, String sCalDate) {
		// 暂时只是判断交至日期与退保点的关系
		// 疑问：是否要去判断应收实收和暂交费？ 如果有暂交费没有核销算不算已交 zhangtao 2005-07-11
		// 确认：暂交费未核销的不算已交
		String sPayNextFlag = "";
		if (pLCPolSchema.getPaytoDate() == null
				|| pLCPolSchema.getPaytoDate().equals("")) {
			// CError.buildErr(this,
			// "合同号:["+pLCPolSchema.getContNo()+"],保险险种号:["+pLCPolSchema.getPolNo()+"],交费对应日为空,没有交费!");
			return null;
		}

		if (pLCPolSchema.getPayIntv() == 0) // 趸交
		{
			sPayNextFlag = "1"; // 1-应交已交
			return sPayNextFlag;
		}

		int intval = PubFun.calInterval(sCalDate, pLCPolSchema.getPaytoDate(),
				"D");

		if (intval > 0) {
			sPayNextFlag = "1"; // 1-应交已交
		} else {
			sPayNextFlag = "0"; // 0-应交未交
		}

		return sPayNextFlag;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－承保处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－保全处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 保全处理的批单号--通用查找方式--最早的日期 如果没有,将返回值置为空 [暂时不用]
	 * 
	 * @return
	 */
	public LPEdorItemSchema getLPEdorItemMin(String pContNo, String pPolNo,
			String pDate, String pPosType, String pDealMode) {

		String tReturn = "";
		String tSql = "";

		ExeSQL tExeSQL = new ExeSQL();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据保单的合同号、被保人编号
		 * 在LPEdorItem个险保全项目表中找出最后的批改生效日期 事故日期 <= 保全生效日期 2005-05-01 <=
		 * 2005-07-01 AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select min(EdorValiDate) from LPEdorItem where 1=1 "
				+ " and ContNo ='" + "?ContNo?" + "'" + " and (PolNo ='" + "?PolNo?"
				+ "' or  PolNo = ('000000'))" // 承保时的保单号
				+ " and EdorState='0'";

		if (StrTool.cTrim(pDate).length() > 0) {
			tSql = tSql + " and to_date('" + "?pDate?"
					+ "','YYYY-MM-DD') >= EdorValiDate";
		}

		if (StrTool.cTrim(pPosType).length() > 0) {
			tSql = tSql + " and EdorType in ('" + "?pPosType?" + "')";
		}
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql(tSql);
		sqlbv17.put("ContNo", pContNo);
		sqlbv17.put("PolNo", pPolNo);
		sqlbv17.put("pDate", pDate);
		sqlbv17.put("pPosType", pPosType);
		String tEdorValiDate = tExeSQL.getOneValue(sqlbv17);
		if (tExeSQL.mErrors.needDealError()) {
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimCalMatchBL.getLPEdorItemMin()--获取保全处理的最早批改生效日期发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");
			mErrors.addOneError("获取保全处理的批改生效日期发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPEdorItemMin()--获取保全处理的最早批改生效日期["
						+ tEdorValiDate + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tEdorValiDate == null || tEdorValiDate.length() == 0) {
			return null;
		} else {
			tEdorValiDate = tEdorValiDate.substring(0, 10);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据最早的核保完成生效时间
		 * 在LPEdorItem个险保全项目表中找出批单号 如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LPEdorItem where 1=1 " + " and ContNo ='"
				+ "?ContNo?" + "'" + " and (PolNo ='"
				+ "?PolNo?"
				+ "' or  PolNo in ('000000'))" // 承保时的保单号
				+ " and EdorValiDate = to_date('" + "?EdorValiDate?"
				+ "','yyyy-mm-dd')" + " and EdorState='0'";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(tSql);
		sqlbv18.put("ContNo", pContNo);
		sqlbv18.put("PolNo", pPolNo);
		sqlbv18.put("EdorValiDate", tEdorValiDate);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv18);

		if (tLPEdorItemDB.mErrors.needDealError()) {
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPEdorItemMin()--获取保全处理的最早批单号发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");
			mErrors.addOneError("获取保全处理的批单号发生错误!"
					+ tLPEdorItemDB.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimCalMatchBL.getLPEdorItemMin()--获取保全处理的批单号["
						+ tLPEdorItemSet.size() + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
			return null;
		}

		return tLPEdorItemSet.get(1);
	}

	/**
	 * 找事故日期之前的经过保全处理的批单号 如果没有,将返回值置为空, 目前主要是在理算中找到复效的时间
	 * 
	 * @return
	 */
	public LPEdorItemSchema getLPEdorItemBefore(String pContNo, String pPolNo,
			String pDate, String pPosType) {

		pPosType = StrTool.cTrim(pPosType);
		String tReturn = "";
		String tSql = "";

		ExeSQL tExeSQL = new ExeSQL();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据保单的合同号、被保人编号
		 * 在LPEdorItem个险保全项目表中找出最后的批改生效日期 在事故之后发生了保全变更 事故日期 <= 保全生效日期 2005-05-01 <=
		 * 2005-07-01 AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select max(EdorValiDate) from LPEdorItem where 1=1 "
				+ " and ContNo ='" + "?ContNo?" + "'" + " and (PolNo ='"
				+ "?PolNo?"
				+ "' or  PolNo = ('000000'))" // 承保时的保单号
				+ " and to_date('" + "?pDate?" + "','YYYY-MM-DD') >= EdorValiDate"
				+ " and EdorState='0'";

		if (pPosType.length() > 0) {
			tSql = tSql + " and EdorType in ('" + pPosType + "')";
		}
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tSql);
		sqlbv19.put("ContNo", pContNo);
		sqlbv19.put("PolNo", pPolNo);
		sqlbv19.put("pDate", pDate);
		String tEdorValiDate = tExeSQL.getOneValue(sqlbv19);
		if (tExeSQL.mErrors.needDealError()) {

			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimCalMatchBL.getLPEdorItemBefore()--获取保全处理的批改生效日期发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");

			mErrors.addOneError("获取保全处理的批改生效日期发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPEdorItemBefore()--获取保全处理的批改生效日期["
						+ tEdorValiDate + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tEdorValiDate == null || tEdorValiDate.length() == 0) {
			return null;
		} else {
			tEdorValiDate = tEdorValiDate.substring(0, 10);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据最早的核保完成生效时间
		 * 在LPEdorItem个险保全项目表中找出批单号 如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LPEdorItem where 1=1 " + " and ContNo ='"
				+ "?ContNo?" + "'" + " and (PolNo ='"
				+ "?PolNo?"
				+ "' or  PolNo = ('000000'))" // 承保时的保单号
				+ " and EdorValiDate = to_date('" + "?EdorValiDate?"
				+ "','yyyy-mm-dd')" + " and EdorState='0'";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(tSql);
		sqlbv20.put("ContNo", pContNo);
		sqlbv20.put("PolNo", pPolNo);
		sqlbv20.put("EdorValiDate", tEdorValiDate);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv20);

		if (tLPEdorItemDB.mErrors.needDealError()) {
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPEdorItemBefore()--获取保全处理的批单号发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");

			mErrors.addOneError("获取保全处理的批单号发生错误!"
					+ tLPEdorItemDB.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimCalMatchBL.getLPEdorItemBefore()--获取保全处理的批单号["
						+ tLPEdorItemSet.size() + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
			return null;
		}

		return tLPEdorItemSet.get(1);
	}

	/**
	 * 找事故日期之后的经过保全处理的批单号 如果没有,将返回值置为空
	 * 
	 * @return
	 */
	public LPEdorItemSchema getLPEdorItemAfter(String pContNo, String pPolNo,
			String pDate, String pPosType) {

		pPosType = StrTool.cTrim(pPosType);
		String tReturn = "";
		String tSql = "";

		ExeSQL tExeSQL = new ExeSQL();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据保单的合同号、被保人编号
		 * 在LPEdorItem个险保全项目表中找出最后的批改生效日期 在事故之后发生了保全变更 事故日期 <= 保全生效日期 2005-05-01 <=
		 * 2005-07-01 AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select min(EdorValiDate) from LPEdorItem where 1=1 "
				+ " and ContNo ='" + "?ContNo?" + "'" + " and (PolNo ='"
				+ "?PolNo?"
				+ "' or  PolNo = ('000000'))" // 承保时的保单号
				+ " and to_date('" + "?pDate?" + "','YYYY-MM-DD') <= EdorValiDate"
				+ " and EdorState='0'";

		if (pPosType.length() > 0) {
			tSql = tSql + " and EdorType in ('" + "?pPosType?" + "')";
		}
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(tSql);
		sqlbv21.put("ContNo", pContNo);
		sqlbv21.put("PolNo", pPolNo);
		sqlbv21.put("pDate", pDate);
		sqlbv21.put("pPosType", pPosType);
		String tEdorValiDate = tExeSQL.getOneValue(sqlbv21);
		if (tExeSQL.mErrors.needDealError()) {
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批改生效日期发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");

			mErrors.addOneError("获取保全处理的批改生效日期发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批改生效日期["
						+ tEdorValiDate + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tEdorValiDate == null || tEdorValiDate.length() == 0) {
			return null;
		} else {
			tEdorValiDate = tEdorValiDate.substring(0, 10);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据最早的核保完成生效时间
		 * 在LPEdorItem个险保全项目表中找出批单号 如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LPEdorItem where 1=1 " + " and ContNo ='"
				+ "?ContNo?" + "'" + " and (PolNo ='"
				+ "?PolNo?"
				+ "' or  PolNo in ('000000'))" // 承保时的保单号
				+ " and EdorValiDate = to_date('" + "?EdorValiDate?"
				+ "','yyyy-mm-dd')" + " and EdorState='0'";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(tSql);
		sqlbv22.put("ContNo", pContNo);
		sqlbv22.put("PolNo", pPolNo);
		sqlbv22.put("EdorValiDate", tEdorValiDate);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv22);

		if (tLPEdorItemDB.mErrors.needDealError()) {
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批单号发生错误!"
							+ tSql);
			logger.debug("-----------------------------------------------------------------------------------");
			mErrors.addOneError("获取保全处理的批单号发生错误!"
					+ tLPEdorItemDB.mErrors.getError(0).errorMessage);
			return null;
		}

		logger.debug("-----------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批单号["
						+ tLPEdorItemSet.size() + "]:" + tSql);
		logger.debug("-----------------------------------------------------------------------------------");

		if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
			return null;
		}

		return tLPEdorItemSet.get(1);
	}

	/**
	 * 得到保全处理Dutyt数据
	 * 
	 * @return
	 */
	public LPDutySchema getLPDuty(String pPosEdorNo, String pNBPolNo,
			LCGetSchema pLCGetSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据批单号，承保保单号，责任编码
		 * 在LPDuty保全保单险种责任表找出基本保额Amnt 如果数据空，则说明没有发生保全变更项目，取C表的基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		tSql = "select * from LPDuty where 1=1 " + " and EdorNo ='"
				+ "?EdorNo?" + "'" + " and PolNo  ='" + "?PolNo?" + "'"
				+ " and DutyCode ='" + "?DutyCode?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(tSql);
		sqlbv23.put("EdorNo", pPosEdorNo);
		sqlbv23.put("PolNo", pNBPolNo);
		sqlbv23.put("DutyCode", pLCGetSchema.getDutyCode());
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySet tLPDutySet = tLPDutyDB.executeQuery(sqlbv23);

		if (tLPDutyDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPubFunBL";
			tError.functionName = "getLPGet";
			tError.errorMessage = "查询保全处理的领取项发生错误!";
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPDuty()--查询保全处理的领取项发生错误!");
			logger.debug("------------------------------------------------------");
			this.mErrors.addOneError(tError);
			return null;
		}

		logger.debug("------------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPDuty()--获取保全处理的领取项信息:["
				+ tLPDutySet.size() + "]" + tSql);
		logger.debug("------------------------------------------------------------------------------------");

		if (tLPDutySet.size() == 1) {
			return tLPDutySet.get(1);
		} else {
			return null;
		}

	}

	/**
	 * 得到保全处理LPGet数据
	 * 
	 * @return
	 */
	public LPGetSchema getLPGet(String pPosEdorNo, String pNBPolNo,
			LCGetSchema pLCGetSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据批单号，承保保单号，责任编码
		 * 在LPDuty保全保单险种责任表找出基本保额Amnt 如果数据空，则说明没有发生保全变更项目，取C表的基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		tSql = "select * from LPGet where 1=1 " + " and EdorNo ='" + "?EdorNo?"
				+ "'" + " and PolNo  ='" + "?PolNo?" + "'" + " and DutyCode ='"
				+ "?DutyCode?" + "'" + " and GetDutyCode ='"
				+ "?GetDutyCode?" + "'";
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(tSql);
		sqlbv24.put("EdorNo", pPosEdorNo);
		sqlbv24.put("PolNo", pNBPolNo);
		sqlbv24.put("DutyCode", pLCGetSchema.getDutyCode());
		sqlbv24.put("GetDutyCode", pLCGetSchema.getGetDutyCode());
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = tLPGetDB.executeQuery(sqlbv24);

		if (tLPGetDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPubFunBL";
			tError.functionName = "getLPGet";
			tError.errorMessage = "查询保全处理的领取项发生错误!";
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getLPGet()--查询保全处理的领取项发生错误!");
			logger.debug("------------------------------------------------------");
			this.mErrors.addOneError(tError);
			return null;
		}

		logger.debug("------------------------------------------------------------------------------------");
		logger.debug("--LLClaimPubFunBL.getLPGet()--获取保全处理的领取项信息:["
				+ tLPGetSet.size() + "]" + tSql);
		logger.debug("------------------------------------------------------------------------------------");

		if (tLPGetSet.size() == 1) {
			return tLPGetSet.get(1);
		} else {
			return null;
		}

	}

	/**
	 * 得到累计年度红利
	 * 
	 * @return
	 */
	public double getYearBonus(String pPolNo, String pRiskCode, String pDate) {
		logger.debug("-----------------------------------计算年度红利-----开始------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 得到年度红利信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		tEdorCalZT.setCalDateType("LP");
		t_Sum_Return = Arith
				.round(tEdorCalZT.getSumAmntBonus(pPolNo, pDate), 2);

		if (t_Sum_Return == -1) {
			this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
		}

		logger.debug("-----------------------------------------------------------------");
		logger.debug("--计算年度红利:[" + t_Sum_Return + "]");
		logger.debug("-----------------------------------------------------------------");
		logger.debug("-----------------------------------计算年度红利-----结束------------------------------------");

		return t_Sum_Return;
	}

	/**
	 * 得到终了红利
	 * 
	 * @return
	 */
	public double getFinalBonus(String pPolNo, String pRiskCode, String pDate) {
		logger.debug("-----------------------------------计算终了红利-----开始------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 D02：计算终了红利 有效保额 =
		 * 基本保额 + 累计红利保额 有效保额 = 基本保额 + 累计红利保额 + 终了红利 基本保额 =
		 * LCDutySchema.getAmnt() 年度红利 = LOEngBonusPol，BonusAmnt 累计红利 =
		 * LOEngBonusPol，SUM(BonusAmnt)
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1
		 * 如果RiskSort=12，附加险等同于主险[293,294]， 用基本保额计算终了红利，其他情况用的是有效保额计算终了红利
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (this.getLMRiskSort(pRiskCode, "12")) {
			tEdorCalZT.setFinaBonusFlag(false);
		}

		tEdorCalZT.setCalDateType("LP");
		t_Sum_Return = Arith.round(tEdorCalZT.getFinalBonus(pPolNo, pDate), 2);

		if (t_Sum_Return == -1) {
			this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
		}

		logger.debug("-----------------------------------------------------------------");
		logger.debug("--计算终了红利:[" + t_Sum_Return + "]");
		logger.debug("-----------------------------------------------------------------");
		logger.debug("-----------------------------------计算终了红利-----结束------------------------------------");
		return t_Sum_Return;
	}

	/**
	 * 计算主险的现金价值,附加险的未满期保费
	 * 
	 * @return
	 */
	public double getCashValue(String pPolNo, String pRiskCode, String pDate) {
		logger.debug("-----------------------------------计算主险的现金价值,附加险的未满期保费-----开始------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 计算主险的现金价值,附加险的未满期保费
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算附加险的未满期保费,需要进行此设置
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tRiskType = getRiskType(pRiskCode);
		if (tRiskType.equals("S")) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorType("CT");// 退保标志
			tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
		}

		tEdorCalZT.setCalDateType("LP");
		t_Sum_Return = Arith.round(tEdorCalZT.getCashValue(pPolNo, pDate), 2);

		if (t_Sum_Return == -1) {
			this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
		}

		logger.debug("-----------------------------------------------------------------");
		logger.debug("--计算主险的现金价值,附加险的未满期保费:[" + t_Sum_Return + "]");
		logger.debug("-----------------------------------------------------------------");
		logger.debug("-----------------------------------计算主险的现金价值,附加险的未满期保费-----结束------------------------------------");
		return t_Sum_Return;
	}

	/**
	 * 判断险种是否是分红险 Y:是 N:不是
	 * 
	 * @return
	 */
	public String getBonus(LCPolSchema pLCPolSchema) {
		String tReturn = "";

		String sql = " select BonusFlag from lmriskapp where 1=1"
				+ " and riskcode = '" + "?riskcode?" + "'";
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(sql);
		sqlbv25.put("riskcode", pLCPolSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
		String sBonusFlag = tExeSQL.getOneValue(sqlbv25);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "险种[" + pLCPolSchema.getRiskCode()
					+ "],查询分红险标志失败!");
			return null;
		}

		if (sBonusFlag == null || sBonusFlag.equals("")
				|| sBonusFlag.equals("0")) {
			// 不是分红险
			sBonusFlag = "N";
		} else {
			sBonusFlag = "Y";
		}

		tReturn = sBonusFlag;
		return tReturn;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－保全处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－理赔处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 目的：找出该赔案对应的事件信息
	 * 
	 * @return
	 */
	public LLAccidentSchema getLLAccident(String pClmNo) {

		String tSql = "select * from LLAccident where AccNo in "
				+ " (select CaseRelaNo from LLCaseRela where caseno = '"
				+ "?clmno?" + "')";
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(tSql);
		sqlbv26.put("clmno", pClmNo);
		LLAccidentDB tLLAccidentDB = new LLAccidentDB();
		LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(sqlbv26);

		if (tLLAccidentDB.mErrors.needDealError() || tLLAccidentSet.size() != 1) {
			mErrors.addOneError("赔案号：[" + pClmNo + "]无事故信息，不能匹配理算!!!");
			return null;
		}

		return tLLAccidentSet.get(1);
	}

	/**
	 * 目的:得到团体立案信息
	 * 
	 */
	public LLGrpRegisterSchema getLLGrpRegister(String pClmNo) {

		LLGrpRegisterSchema tLLGrpRegisterSchema = new LLGrpRegisterSchema();

		LLGrpRegisterDB tLLGrpRegisterDB = new LLGrpRegisterDB();
		tLLGrpRegisterDB.setRgtNo(pClmNo);
		LLGrpRegisterSet tLLGrpRegisterSet = tLLGrpRegisterDB.query();

		if (tLLGrpRegisterDB.mErrors.needDealError()) {
			mErrors.addOneError("团体赔案号[" + pClmNo + "]的立案信息没有取到!!!");
			return null;
		}

		if (tLLGrpRegisterSet.size() == 1) {
			tLLGrpRegisterSchema = tLLGrpRegisterSet.get(1);
		} else {
			mErrors.addOneError("团体赔案号[" + pClmNo + "]的立案信息为空没有取到!!!");
			return null;
		}

		return tLLGrpRegisterSchema;
	}

	/**
	 * 目的:得到个人立案信息
	 * 
	 */
	public LLRegisterSchema getLLRegister(String pClmNo) {

		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(pClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();

		if (tLLRegisterDB.mErrors.needDealError()) {
			mErrors.addOneError("个人赔案号[" + pClmNo + "]的立案信息没有取到!!!");
			return null;
		}

		if (tLLRegisterSet.size() == 1) {
			tLLRegisterSchema = tLLRegisterSet.get(1);
		} else {
			mErrors.addOneError("个人赔案号[" + pClmNo + "]的立案信息为空没有取到!!!");
			return null;
		}

		return tLLRegisterSchema;
	}

	/**
	 * 目的:得到立案分案信息
	 * 
	 */
	public LLCaseSchema getLLCase(String pClmNo, String pCustNo) {

		LLCaseSchema tLLCaseSchema = new LLCaseSchema();

		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(pClmNo);
		tLLCaseDB.setCustomerNo(pCustNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();

		if (tLLCaseDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],客户号[" + pCustNo
					+ "]的立案分案信息没有取到!!!"
					+ tLLCaseDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCaseSet.size() == 1) {
			tLLCaseSchema = tLLCaseSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],客户号[" + pCustNo
					+ "]的立案分案信息为空没有取到!!!");
			return null;
		}

		return tLLCaseSchema;
	}

	/**
	 * 目的:得到立案分案信息
	 * 
	 */
	public LLCaseSet getLLCaseSet(String pClmNo) {

		LLCaseSchema tLLCaseSchema = new LLCaseSchema();

		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(pClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();

		if (tLLCaseDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的立案分案信息没有取到!!!"
					+ tLLCaseDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCaseSet.size() == 0) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的立案分案信息为空没有取到!!!");
			return null;
		}

		return tLLCaseSet;
	}

	/**
	 * 目的:得到立案的理赔类型
	 * 
	 * @param pClmNo
	 * @return
	 */
	private LLAppClaimReasonSet getLLAppClaimReason(String pClmNo) {
		// LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
		// tLLAppClaimReasonDB.setCaseNo(this.mClmNo); //赔案号
		// tLLAppClaimReasonDB.setCustomerNo(mLLCaseSchema.getCustomerNo()); //客户号
		//
		// mLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
		// if(mLLAppClaimReasonSet == null || mLLAppClaimReasonSet.size() == 0 )
		// {
		// this.mErrors.addOneError("没有找到到立案的理赔类型信息，不能进行责任匹配!");
		// return false;
		// }
		return new LLAppClaimReasonSet();
	}

	/**
	 * 目的:待理算责任信息
	 * 
	 */
	public LLToClaimDutySchema getLLToClaimDuty(String pClmNo, String pPolNo,
			String pDutyCode, String pGetDutyKind, String pGetDutyCode) {

		LLToClaimDutySchema tLLToClaimDutySchema = new LLToClaimDutySchema();

		LLToClaimDutyDB tLLToClaimDutyDB = new LLToClaimDutyDB();
		tLLToClaimDutyDB.setCaseNo(pClmNo);
		tLLToClaimDutyDB.setPolNo(pPolNo);
		tLLToClaimDutyDB.setDutyCode(pDutyCode);
		tLLToClaimDutyDB.setGetDutyKind(pGetDutyKind);
		tLLToClaimDutyDB.setGetDutyCode(pGetDutyCode);

		LLToClaimDutySet tLLToClaimDutySet = tLLToClaimDutyDB.query();

		if (tLLToClaimDutyDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],保单号[" + pPolNo + "],理赔类型["
					+ pGetDutyKind + "],给付责任[" + pGetDutyCode
					+ "]的待理算责任信息没有取到!!!"
					+ tLLToClaimDutyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLToClaimDutySet.size() == 1) {
			tLLToClaimDutySchema = tLLToClaimDutySet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],保单号[" + pPolNo + "],理赔类型["
					+ pGetDutyKind + "],给付责任[" + pGetDutyCode
					+ "]的待理算责任信息为空没有取到!!!");
			return null;
		}

		return tLLToClaimDutySchema;
	}
	
	
	/**
	 * 目的:得到赔案理算信息
	 * 
	 */
	public LLPrepayClaimSchema getLLPrepayClaim(String pClmNo) {

		LLPrepayClaimSchema tLLPrepayClaimSchema = new LLPrepayClaimSchema();

		LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
		tLLPrepayClaimDB.setCaseNo(pClmNo);
		LLPrepayClaimSet tLLPrepayClaimSet = tLLPrepayClaimDB.query();

		if (tLLPrepayClaimDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息没有取到!!!"
					+ tLLPrepayClaimDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLPrepayClaimSet.size() == 1) {
			tLLPrepayClaimSchema = tLLPrepayClaimSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息为空没有取到!!!");
			return null;
		}

		return tLLPrepayClaimSchema;
	}

	/**
	 * 目的:得到赔案理算信息
	 * 
	 */
	public LLClaimSchema getLLClaim(String pClmNo) {

		LLClaimSchema tLLClaimSchema = new LLClaimSchema();

		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setCaseNo(pClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();

		if (tLLClaimDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息没有取到!!!"
					+ tLLClaimDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLClaimSet.size() == 1) {
			tLLClaimSchema = tLLClaimSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息为空没有取到!!!");
			return null;
		}

		return tLLClaimSchema;
	}

	/**
	 * 目的:得到赔案核赔轨迹信息
	 * 
	 */
	public LLClaimUWMainSchema getLLClaimUWMain(String pClmNo) {

		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();

		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(pClmNo);

		LLClaimUWMainSet tLLClaimUWMainSet = tLLClaimUWMainDB.query();

		if (tLLClaimUWMainDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的核赔轨迹信息没有取到!!!"
					+ tLLClaimUWMainDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLClaimUWMainSet.size() == 1) {
			tLLClaimUWMainSchema = tLLClaimUWMainSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "]的核赔轨迹信息为空没有取到!!!");
			return null;
		}

		return tLLClaimUWMainSchema;
	}

	/**
	 * 目的:得到赔案回退信息
	 * 
	 */
	public LLCaseBackSchema getLLCaseBack(String pBackNo) {

		LLCaseBackSchema tLLCaseBackSchema = new LLCaseBackSchema();

		LLCaseBackDB tLLCaseBackDB = new LLCaseBackDB();
		tLLCaseBackDB.setBackNo(pBackNo);
		LLCaseBackSet tLLCaseBackSet = tLLCaseBackDB.query();

		if (tLLCaseBackDB.mErrors.needDealError()) {
			mErrors.addOneError("回退号[" + pBackNo + "]的赔案回退信息没有取到!!!"
					+ tLLCaseBackDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCaseBackSet.size() == 1) {
			tLLCaseBackSchema = tLLCaseBackSet.get(1);
		} else {
			mErrors.addOneError("回退号[" + pBackNo + "]的赔案回退为空没有取到!!!");
			return null;
		}

		return tLLCaseBackSchema;
	}

	/**
	 * 目的:得到医疗费用明细信息
	 * 
	 */
	public LLCaseReceiptSchema getLLCaseReceipt(String pClmNo,
			String pFeeDetailNo) {

		LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

		LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
		tLLCaseReceiptDB.setClmNo(pClmNo);
		tLLCaseReceiptDB.setFeeDetailNo(pFeeDetailNo);

		LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB.query();

		if (tLLCaseReceiptDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],账单费用明细号[" + pFeeDetailNo
					+ "]的医疗费用明细信息没有取到!!!"
					+ tLLCaseReceiptDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCaseReceiptSet.size() == 1) {
			tLLCaseReceiptSchema = tLLCaseReceiptSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],账单费用明细号[" + pFeeDetailNo
					+ "]的医疗费用明细信息为空没有取到!!!");
			return null;
		}

		return tLLCaseReceiptSchema;
	}

	/**
	 * 目的:得到理赔豁免信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLExemptSet getLLExempt(String pClmNo, String pPolNo) {

		LLExemptSchema tLLExemptSchema = new LLExemptSchema();

		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setClmNo(pClmNo);
		tLLExemptDB.setPolNo(pPolNo);

		LLExemptSet tLLExemptSet = tLLExemptDB.query();

		if (tLLExemptDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的豁免信息没有取到!!!"
					+ tLLExemptDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLExemptSet.size() == 0) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的豁免信息为空没有取到!!!");
			return null;
		}

		return tLLExemptSet;
	}

	/**
	 * 目的:得到个人理赔合同批次表
	 * 
	 */
	public LLCUWBatchSchema getLLCUWBatch(String pClmNo, String pBatNo,
			String pContNo) {
		LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();

		LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
		tLLCUWBatchDB.setCaseNo(pClmNo);
		tLLCUWBatchDB.setBatNo(pBatNo);
		tLLCUWBatchDB.setContNo(pContNo);

		LLCUWBatchSet tLLCUWBatchSet = tLLCUWBatchDB.query();

		if (tLLCUWBatchDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],批次号[" + pBatNo + "],合同号["
					+ pContNo + "]的合同核保批次结果信息没有取到!!!"
					+ tLLCUWBatchDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCUWBatchSet.size() == 1) {
			tLLCUWBatchSchema = tLLCUWBatchSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],批次号[" + pBatNo + "],合同号["
					+ pContNo + "]的合同核保批次结果信息为[" + tLLCUWBatchSet.size()
					+ "],数据不唯一!!!");
			return null;
		}

		return tLLCUWBatchSchema;

	}

	/**
	 * 目的:得到个人合同理陪核保信息
	 * 
	 */
	public LLCUWMasterSchema getLLCUWMaster(String pClmNo,String pBatNo, String pContNo) {

		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();

		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();

		if (pClmNo != null) {
			tLLCUWMasterDB.setCaseNo(pClmNo);
		}
		
		if (pContNo != null) {
			tLLCUWMasterDB.setContNo(pContNo);
		}
		
		if (pBatNo != null) {
			tLLCUWMasterDB.setBatNo(pBatNo);
		}

		LLCUWMasterSet tLLCUWMasterSet = tLLCUWMasterDB.query();

		if (tLLCUWMasterDB.mErrors.needDealError()) {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息没有取到!!!"
					+ tLLCUWMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCUWMasterSet.size() == 1) {
			tLLCUWMasterSchema = tLLCUWMasterSet.get(1);
		} else {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息为空没有取到!!!");
			return null;
		}

		return tLLCUWMasterSchema;
	}
	
	
	/**
	 * 目的:得到个人合同理陪核保最近结果表信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLCUWMasterSet getLLCUWMasterSet(String pClmNo,String pBatNo,
			String pContNo) {
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();

		LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();

		if (pClmNo != null) {
			tLLCUWMasterDB.setCaseNo(pClmNo);
		}
		
		if (pContNo != null) {
			tLLCUWMasterDB.setContNo(pContNo);
		}
		
		if (pBatNo != null) {
			tLLCUWMasterDB.setBatNo(pBatNo);
		}


		LLCUWMasterSet tLLCUWMasterSet = tLLCUWMasterDB.query();

		if (tLLCUWMasterDB.mErrors.needDealError()) {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息没有取到!!!"
					+ tLLCUWMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLCUWMasterSet.size() == 0) {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息为空没有取到!!!"
					+ tLLCUWMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}

		return tLLCUWMasterSet;

	}
	
	
	
	/**
	 * 目的:得到个人理陪险种核保最近结果表信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLUWMasterSet getLLUWMasterSet(String pClmNo,String pBatNo,
			String pContNo, String pPolNo) {
		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();

		LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
		
		tLLUWMasterDB.setCaseNo(pClmNo);
		if (pContNo != null) {
			tLLUWMasterDB.setContNo(pContNo);
		}

		if (pPolNo != null) {
			tLLUWMasterDB.setPolNo(pPolNo);
		}
		
		if (pBatNo != null) {
			tLLUWMasterDB.setBatNo(pBatNo);
		}

		LLUWMasterSet tLLUWMasterSet = tLLUWMasterDB.query();

		if (tLLUWMasterDB.mErrors.needDealError()) {
			CError.buildErr(this,"赔案号[" + pClmNo + "],合同号[" + pContNo + "],险种号["
					+ pPolNo + "],批次号[" + pBatNo +  "]的核保加费信息没有取到!!!"
					+ tLLUWMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLUWMasterSet.size() == 0) {
			CError.buildErr(this,"赔案号[" + pClmNo + "],合同号[" + pContNo + "],险种号["
					+ pPolNo + "],批次号[" + pBatNo +  "]的核保加费信息为空没有取到!!!");
			return null;
		}

		return tLLUWMasterSet;

	}

	/**
	 * 目的:得到个人理陪险种核保结果信息
	 * 
	 */
	public LLUWMasterSchema getLLUWMaster(String pClmNo,String pBatNo,String pContNo,String pPolNo) {

		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();

		LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();

		
		if (pClmNo != null) {
			tLLUWMasterDB.setCaseNo(pClmNo);
		}

		if (pBatNo != null) {
			tLLUWMasterDB.setPolNo(pBatNo);
		}
		
		if (pContNo != null) {
			tLLUWMasterDB.setBatNo(pContNo);
		}
		
		if (pPolNo != null) {
			tLLUWMasterDB.setBatNo(pPolNo);
		}

		LLUWMasterSet tLLUWMasterSet = tLLUWMasterDB.query();

		if (tLLUWMasterDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],险种号[" + pPolNo
					+ "]的合同核保结果信息没有取到!!!"
					+ tLLUWMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLUWMasterSet.size() == 1) {
			tLLUWMasterSchema = tLLUWMasterSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],险种号[" + pPolNo
					+ "]的合同核保结果信息为空没有取到!!!");
			return null;
		}

		return tLLUWMasterSchema;
	}


	/**
	 * 目的:得到理赔核保加费表信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLUWPremMasterSet getLLUWPremMasterSet(String pClmNo,String pBatNo,
			String pContNo, String pPolNo) {
		
		LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();

		LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();

		if (pClmNo != null) {
			tLLUWPremMasterDB.setClmNo(pClmNo);
		}
		
		if (pContNo != null) {
			tLLUWPremMasterDB.setContNo(pContNo);
		}

		if (pPolNo != null) {
			tLLUWPremMasterDB.setPolNo(pPolNo);
		}
		
		if (pBatNo != null) {
			tLLUWPremMasterDB.setBatNo(pBatNo);
		}

		LLUWPremMasterSet tLLUWPremMasterSet = tLLUWPremMasterDB.query();

		if (tLLUWPremMasterDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],合同号[" + pContNo + "],险种号["
					+ pPolNo + "],批次号[" + pBatNo +  "]的核保加费信息没有取到!!!"
					+ tLLUWPremMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}


		return tLLUWPremMasterSet;

	}
	
	
	/**
	 * 目的:得到理赔核保特约表信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLUWSpecMasterSet getLLUWSpecMasterSet(String pClmNo,String pBatNo,
			String pContNo) {
		LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();

		LLUWSpecMasterDB tLLUWSpecMasterDB = new LLUWSpecMasterDB();
		

		if (pClmNo != null) {
			tLLUWSpecMasterDB.setClmNo(pClmNo);
		}
		
		if (pContNo != null) {
			tLLUWSpecMasterDB.setContNo(pContNo);
		}
		
		if (pBatNo != null) {
			tLLUWSpecMasterDB.setBatNo(pBatNo);
		}

		LLUWSpecMasterSet tLLUWSpecMasterSet = tLLUWSpecMasterDB.query();

		if (tLLUWSpecMasterDB.mErrors.needDealError()) {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息没有取到!!!"
					+ tLLUWSpecMasterDB.mErrors.getError(0).errorMessage);
			return null;
		}


		return tLLUWSpecMasterSet;

	}
	
	
	/**
	 * 目的:得到理赔核保体检表信息
	 * 
	 * @param pClmNo
	 * @param pPolNo
	 * @return
	 */
	public LLUWPENoticeSet getLLUWPENoticeSet(String pClmNo,String pBatNo,
			String pContNo) {
		LLUWPENoticeSchema tLLUWPENoticeSchema = new LLUWPENoticeSchema();

		LLUWPENoticeDB tLLUWPENoticeDB = new LLUWPENoticeDB();
		
		if (pClmNo != null) {
			tLLUWPENoticeDB.setClmNo(pClmNo);
		}
		
		if (pContNo != null) {
			tLLUWPENoticeDB.setContNo(pContNo);
		}
		
		if (pBatNo != null) {
			tLLUWPENoticeDB.setBatNo(pBatNo);
		}

		LLUWPENoticeSet tLLUWPENoticeSet = tLLUWPENoticeDB.query();

		if (tLLUWPENoticeDB.mErrors.needDealError()) {
			CError.buildErr(this, "赔案号[" + pClmNo + "],合同号[" + pContNo +  "],批次号[" + pBatNo +  "]的核保特约信息没有取到!!!"
					+ tLLUWPENoticeDB.mErrors.getError(0).errorMessage);
			return null;
		}


		return tLLUWPENoticeSet;

	}
	

	/**
	 * 获取报表参数定义信息
	 * 
	 * @param pRepID
	 * @return
	 */
	public LLParaRepSchema getLLParaRep(String pRepID) {
		LLParaRepSchema tLLParaRepSchema = new LLParaRepSchema();

		LLParaRepDB tLLParaRepDB = new LLParaRepDB();
		tLLParaRepDB.setRepID(pRepID);

		LLParaRepSet tLLParaRepSet = tLLParaRepDB.query();

		if (tLLParaRepDB.mErrors.needDealError()) {
			mErrors.addOneError("报表编号[" + pRepID + "]的参数定义信息没有取到!!!"
					+ tLLParaRepDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLParaRepSet.size() == 1) {
			tLLParaRepSchema = tLLParaRepSet.get(1);
		} else {
			mErrors.addOneError("报表编号[" + pRepID + "]的参数定义信息没有取到");
			return null;
		}

		return tLLParaRepSchema;

	}

	/**
	 * 执行指定的SQL语句，返回结果值，仅一个值
	 * 
	 * @param tSql
	 * @return
	 */
	private String getExeSQL(SQLwithBindVariables tSql) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		ExeSQL tExeSQL = new ExeSQL();
		String tSReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--执行指定的SQL[" + tSReturn + "]:[" + tSql + "]");
		logger.debug("----------------------------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("执行指定的SQL时发生错误!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return "-1";
		}
		return tSReturn;
	}

	/**
	 * 目的:计算该保单是否发生过理赔
	 * 
	 * @param pNBPolNo
	 * @param pLCPolSchema
	 * @return
	 */
	public boolean getPolNoAddPay(String pClmNo, String pNBPolNo,
			LCPolSchema pLCPolSchema) {

		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());

		String tSql = "select a.* from LLClaimDetail a,LLClaim d where 1 = 1"
				+ " and a.ClmNo = d.ClmNo " + " and a.GiveType = '0'"
				+ " and ( d.ClmState in ('50','60') or d.ClmNo in ('" + "?clmno?"
				+ "') ) "// 只计算状态为结案,完成,以及本次赔案的费用
				+ " and a.PolNo = '" + pLCPolSchema.getPolNo() + "'";
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql(tSql);
		sqlbv27.put("clmno", pClmNo);
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB
				.executeQuery(sqlbv27);

		logger.debug("------------------------------------------------------------------");
		logger.debug("--得到险种号[" + tPolNo + "]的理赔累计给付的记录数["
				+ tLLClaimDetailSet.size() + "]:" + tSql);
		logger.debug("------------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError()) {
			this.mErrors.addOneError("得到险种号[" + tPolNo + "]的理赔累计支付发生错误!!!");
			return false;
		}

		if (tLLClaimDetailSet.size() > 1) {
			return true;
		}

		return false;
	}

	/**
	 * 计算不包括本次赔案的，已经支付给客户的未满期保费[D0501]
	 * 
	 * @return
	 */
	public double getPolClmBalPay(LCPolSchema pLCPolSchema, String pClmNo,
			String pSubFeeType) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double tDReturn = 0;
		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());
		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 计算累计的支付值
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select (case when sum(a.pay) is null then 0 else sum(a.pay) end) from LLBalance a,LLClaim b where 1=1"
				+ " and a.ClmNo = b.ClmNo "
				+ " and b.ClmState in ('50','60') "
				+ " and a.ClmNo not in ('"
				+ "?ClmNo?"
				+ "')"
				+ " and a.PolNo in ('"
				+ "?PolNo?"
				+ "')"
				+ " and a.SubFeeOperationType in ('" + "?subfeetype?" + "')";
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		sqlbv28.sql(tSql);
		sqlbv28.put("ClmNo", pClmNo);
		sqlbv28.put("PolNo", tPolNo);
		sqlbv28.put("subfeetype", pSubFeeType);
		ExeSQL tExeSQL = new ExeSQL();
		String tSReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv28));

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--查询已经支付的未满期保费[" + tSReturn + "]:[" + tSql + "]");
		logger.debug("----------------------------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("查询已经支付的未满期保费时发生错误!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return -1;
		}

		if (tSReturn != null && tSReturn.length() > 0 && !tSReturn.equals("")) {
			tDReturn = Arith.round(Double.parseDouble(tSReturn), 2);
		}

		return tDReturn;
	}

	/**
	 * 获取事故日期
	 * 
	 * @return
	 */
	public String getAccDate(String pClmNo) {
		String tReturn = "";
		String tSql = "";
		tSql = "select a.accDate from LLAccident a,LLCaseRela b where 1=1 "
				+ " and a.AccNo = b.CaseRelaNo " + " and b.CaseNo in ('"
				+ "?clmno?" + "')";
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		sqlbv29.sql(tSql); //wyc
		sqlbv29.put("clmno", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv29));

		logger.debug("------------------------------------------------------------------");
		logger.debug("--得到赔案号[" + pClmNo + "]的事故日期[" + tReturn + "]:"
				+ tSql);
		logger.debug("------------------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到赔案号[" + pClmNo + "]的事故日期发生错误!");
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}

		return tReturn;
	}

	/**
	 * 获取出险日期
	 * 规则：若存在“其他出险日期”，则使用“其他出险日期”，若不存在“其他出险日期”，则使用“医疗出险日期”
	 * @return
	 */
	public String getInsDate(String pClmNo) {
		
		String tReturn = "";
		String tSql = "";
		tSql = "select AccDate,MedAccDate from LLCase where CaseNo='" + "?clmno?" + "'";
		
		logger.debug("--得到赔案号[" + pClmNo + "]的出险日期的sql:"+ tSql);
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql(tSql); //wyc
		sqlbv30.put("clmno", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS=new SSRS();
		tSSRS=tExeSQL.execSQL(sqlbv30);
		
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "得到赔案号[" + pClmNo + "]的出险时间发生错误!"+this.mErrors.getLastError());
		}
		else{
			
			if(tSSRS.GetText(1,1)==null||tSSRS.GetText(1,1).equals("")){
				tReturn=tSSRS.GetText(1,2);
			}
			else{
				tReturn=tSSRS.GetText(1,1);
			}
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}
		
		logger.debug("--得到赔案号[" + pClmNo + "]的出险日期[" + tReturn + "]!");


		return tReturn;
	}

	/**
	 * 获取立案日期
	 * 
	 * @return
	 */
	public String getRgtDate(String pClmNo) {
		String tReturn = "";
		String tSql = "";
		tSql = "select max(RgtDate) from LLRegister where RgtNo='" + "?clmno?"
				+ "'";
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql(tSql); //wyc
		sqlbv31.put("clmno", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv31));

		logger.debug("------------------------------------------------------------------");
		logger.debug("--得到赔案号[" + pClmNo + "]的立案日期[" + tReturn + "]:"
				+ tSql);
		logger.debug("------------------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到赔案号[" + pClmNo + "]的立案日期发生错误!");
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}

		return tReturn;
	}

	/**
	 * 获取审批通过日期
	 * 
	 * @return
	 */
	public String getExamDate(String pClmNo) {

		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(pClmNo);
		LLClaimUWMainSet tLLClaimUWMainSet = tLLClaimUWMainDB.query();

		if (tLLClaimUWMainDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "],案件核赔信息没有取到!!!");
			return null;
		}

		if (tLLClaimUWMainSet.size() == 1) {
			tLLClaimUWMainSchema = tLLClaimUWMainSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "],案件核赔信息没有取到!!!");
			return null;
		}

		String tReturn = tLLClaimUWMainSchema.getExamDate().substring(0, 10);

		logger.debug("------------------------------------------------------------------");
		logger.debug("--得到赔案号[" + pClmNo + "]的审批通过日期:[" + tReturn + "]");
		logger.debug("------------------------------------------------------------------");

		return tReturn;
	}

	/**
	 * 获取结案日期
	 * 
	 * @return
	 */
	public String getEndCaseDate(String pClmNo) {
		String tReturn = "";
		String tSql = "";
		tSql = "select EndCaseDate from LLClaim where ClmNo='" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		sqlbv32.sql(tSql); //wyc
		sqlbv32.put("clmno", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv32));

		logger.debug("------------------------------------------------------------------");
		logger.debug("--得到赔案号[" + pClmNo + "]的结案时间[" + tReturn + "]:"
				+ tSql);
		logger.debug("------------------------------------------------------------------");

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到赔案号[" + pClmNo + "]的结案时间发生错误!");
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}

		return tReturn;
	}

	/**
	 * 设置结算信息
	 * 
	 * @param pFeeOperationType:业务类型
	 * @param pSubFeeOperationType:子业务类型
	 * @param pFeeFinaType:财务类型
	 * @param pCalValue:计算金额
	 * @param pLCPolSchema:
	 * @return
	 */
	public LLBalanceSchema setLLBalance(String pClmNo, String pOperator,
			String pFeeType, String pSubFeeType, String pFeeFinaType,
			double pCalValue, LCPolSchema pLCPolSchema) {

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(pClmNo);
		tLLBalanceSchema.setFeeOperationType(pFeeType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(pSubFeeType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(pFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 财务类型

		tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(pLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode("0"); // 责任编码
		tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

		tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setSaleChnl(pLCPolSchema.getSaleChnl()); // 销售渠道
		tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(PubFun.getCurrentDate()); // 给付日期
		tLLBalanceSchema.setPay(pCalValue); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(pLCPolSchema.getManageCom()); // 管理机构
		tLLBalanceSchema.setAgentCom(pLCPolSchema.getAgentCom()); // 代理机构
		tLLBalanceSchema.setAgentType(pLCPolSchema.getAgentType()); // 代理机构内部分类

		tLLBalanceSchema.setOperator(pOperator); // 操作员
		tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate()); // 入机日期
		tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime()); // 入机时间
		tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate()); // 入机日期
		tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime()); // 入机时间

		tLLBalanceSchema.setOriPay(pCalValue); // 原始金额

		return tLLBalanceSchema;

	}

	/**
	 * 退指定日期以后的实收保费 用于保单结算，合同处理
	 * 
	 * @return
	 */
	public LLBalanceSchema getLJAPayPerson(TransferData pTransferData) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取变量定义
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCPolSchema tLCPolSchema = (LCPolSchema) pTransferData
				.getValueByName("LCPolSchema");
		if (tLCPolSchema == null) {
			mErrors.addOneError("退指定日期之前之后的实收保费时传入的LCPolSchema信息为空,操作终止!!!");
			return null;
		}

		String tClmNo = StrTool.cTrim((String) pTransferData
				.getValueByName("ClmNo")); // 赔案号
		String tDate = StrTool.cTrim((String) pTransferData
				.getValueByName("Date")); // 赔案号
		String tDealType = StrTool.cTrim((String) pTransferData
				.getValueByName("DealType")); // 处理方式,1-指定日期之后
		String tDealDate = StrTool.cTrim((String) pTransferData
				.getValueByName("DealDate")); // 处理时间,1-包括计算的时点
		String tOperator = StrTool.cTrim((String) pTransferData
				.getValueByName("Operator")); // 操作人员

		String tFeeType = StrTool.cTrim((String) pTransferData
				.getValueByName("FeeType")); // 业务类型
		String tSubFeeType = StrTool.cTrim((String) pTransferData
				.getValueByName("SubFeeType")); // 子业务类型
		String tFinaType = StrTool.cTrim((String) pTransferData
				.getValueByName("FinaType")); // 财务类型

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * 
		 * -----之前[2]4.1---<---计算时点5.1---<---之后[1]6.1-----
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select (case when sum(LJAPayPerson.SumActuPayMoney) is null then 0 else sum(LJAPayPerson.SumActuPayMoney) end) from LJAPayPerson where 1=1 "
				+ " and PolNo = '" + "?PolNo?" + "'";
		//为了保证续保件能够退干净，进行修改 modify by weikai
//		String tSql = "select nvl(sum(LJAPayPerson.SumActuPayMoney),0) from LJAPayPerson where 1=1 "
//			+ " and contno = '" + tLCPolSchema.getContNo() + "' and riskcode='"+tLCPolSchema.getRiskCode()+"'";

		if (tDealType.equals("1") && tDealDate.equals("1")) {
			tSql = tSql + " and LastPayToDate >= to_date('" + "?tdate?"
					+ "','yyyy-mm-dd') ";
		} else if (tDealType.equals("1") && tDealDate.equals("2")) {
			tSql = tSql + " and LastPayToDate >  to_date('" + "?tdate?"
					+ "','yyyy-mm-dd') ";
		}

		if (tDealType.equals("2") && tDealDate.equals("1")) {
			tSql = tSql + " and LastPayToDate <= to_date('" + "?tdate?"
					+ "','yyyy-mm-dd') ";
		} else if (tDealType.equals("2") && tDealDate.equals("2")) {
			tSql = tSql + " and LastPayToDate <  to_date('" + "?tdate?"
					+ "','yyyy-mm-dd') ";
		}
		//add by weikai 垫交保费排除
//		tSql = " and ininsuaccstate!=3 ";  comment by weikai   垫交发生在出险点之后，正常退给客户，
		//add by weikai 排除红利增额交清
		tSql=tSql+" and char_length(trim(dutycode))!=10  and paytype='ZC'";
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		sqlbv33.sql(tSql);
		sqlbv33.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv33.put("tdate", tDate);
		ExeSQL tExeSQL = new ExeSQL();
		String tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv33));

		if (tDealType.equals("1")) {
			logger.debug("------------------------------------------------------");
			logger.debug("--退指定日期之后的保费[" + tReturn + "]:" + tSql);
			logger.debug("------------------------------------------------------");
		} else if (tDealType.equals("2")) {
			logger.debug("------------------------------------------------------");
			logger.debug("--退指定日期之前的保费[" + tReturn + "]:" + tSql);
			logger.debug("------------------------------------------------------");
		}

		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("退指定日期以后的实收保费发生错误,操作终止!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return null;
		}

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		if (tReturn.equals("") || tReturn.length() == 0 || tReturn.equals("0")) {
			return null;
		} else {

			tLLBalanceSchema.setClmNo(tClmNo);
			tLLBalanceSchema.setFeeOperationType(tFeeType); // 业务类型
			tLLBalanceSchema.setSubFeeOperationType(tSubFeeType); // 子业务类型
			tLLBalanceSchema.setFeeFinaType(tFinaType); // 财务类型

			tLLBalanceSchema.setBatNo("0"); // 批次号
			tLLBalanceSchema.setOtherNo("0"); // 其它号码
			tLLBalanceSchema.setOtherNoType("0"); // 财务类型

			tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); // 集体合同号码
			tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); // 合同号码
			tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); // 集体保单号码
			tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); // 保单号码

			tLLBalanceSchema.setDutyCode("0"); // 责任编码
			tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
			tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

			tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); // 险类编码
			tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
			tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); // 险种版本

			tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); // 代理人编码
			tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); // 代理人组别

			tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
			tLLBalanceSchema.setPay(tReturn); // 赔付金额

			tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
			tLLBalanceSchema.setState("0"); // 状态,0有效
			tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

			tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom()); // 管理机构
			tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom()); // 代理机构
			tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType()); // 代理机构内部分类
			tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl()); // 销售渠道

			tLLBalanceSchema.setOperator(tOperator); // 操作员
			tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate()); // 入机日期
			tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime()); // 入机时间
			tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate()); // 入机日期
			tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime()); // 入机时间
			tLLBalanceSchema.setOriPay(tReturn); // 原始金额
			tLLBalanceSchema.setCurrency(tLCPolSchema.getCurrency());
			tLLBalanceSchema.setCustomerNo(tLCPolSchema.getInsuredNo());

			return tLLBalanceSchema;
		}

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－理赔处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－团体处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－团体处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－匹配理算算法－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 保单是否复效的标记 1复效 0无复效
	 * 
	 * @return
	 */
	public String getLRFlag(String ptPosEdorNoFront, String pPolNo) {

		String tReturn = "";
		if (ptPosEdorNoFront == null
				|| StrTool.cTrim(ptPosEdorNoFront).length() == 0) {
			tReturn = "0";
		} else {
			tReturn = "1";
			logger.debug("------------------------------------------------------");
			logger.debug("--LRFlag:保单复效标志,保全批单号:["
					+ StrTool.cTrim(ptPosEdorNoFront) + "]");
			logger.debug("------------------------------------------------------");

		}
		return tReturn;

	}

	/**
	 * 得到复效到出险时已保年期,取自出险时点,需要考虑保全
	 * 
	 * @return
	 * 
	 */
	public String getLastRevDate(String ptPosEdorNoFront, String pContNo,
			String pPolNo, LCPolSchema pLCPolSchema) {
		String tReturn = "";
		String tSql = "";

		logger.debug("--------------开始---------------复效时间-----------------------");

		if (ptPosEdorNoFront == null
				|| StrTool.cTrim(ptPosEdorNoFront).length() == 0) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 本险种:保项保单号 =
			 * LCPol表的保单号 计算主险[266,267] ,采用主险保单号
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (pPolNo.equals(pLCPolSchema.getPolNo())) {
				tReturn = pLCPolSchema.getCValiDate();
				if (tReturn == null) {
					tReturn = "2900-01-01";
				}
			} else {
				LCPolSchema tLCPolSchema = this.getLCPol(pPolNo);
				if (tLCPolSchema == null) {
					mErrors.copyAllErrors(this.mErrors);
				}
				tReturn = tLCPolSchema.getCValiDate();
				if (tReturn == null) {
					tReturn = "2900-01-01";
				}
			}
		} else {
			tSql = "select (case when EdorValiDate is null then to_date('2900-01-01','yyyy-mm-dd') else EdorValiDate end) from LPEdorItem where 1=1 "
					+ " and EdorNo ='"
					+ "?EdorNo?"
					+ "'"
					+ " and ContNo ='" + "?ContNo?" + "'";
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.sql(tSql);
			sqlbv34.put("EdorNo", ptPosEdorNoFront);
			sqlbv34.put("ContNo", pContNo);
			ExeSQL tExeSQL = new ExeSQL();
			tReturn = tExeSQL.getOneValue(sqlbv34);
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.addOneError("计算要素LastRevDate[复效到时间]发生错误!");
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				tReturn = tReturn.substring(0, 10);
			}
		}

		logger.debug("--LastRevDate:复效日期:[" + tReturn + "],保全批单号:["
				+ StrTool.cTrim(ptPosEdorNoFront) + "]," + tSql);
		logger.debug("--------------结束---------------复效时间-----------------------");

		return tReturn;
	}

	/**
	 * 续保标志,0未续保,1已续保
	 * 
	 * @return
	 */
	public String getAppFlag(String ptPosEdorNoFront, LCPolSchema pLCPolSchema) {
		String tReturn = "";
		String tSql = "";

		logger.debug("--------------开始---------------续保标志-----------------------");

		tSql = "select count(*) from lcpol where 1=1 "
				+ " and contno='"
				+ "?contno?"
				+ "'"
				+ " and appflag in ('1','4') "
				+ " and riskcode='"
				+ "?riskcode?"
				+ "'"
				+ " and insuredno='"
				+ "?insuredno?"
				+ "'"
				+ " and enddate = (select getstartdate from lcpol where polno ='"
				+ "?polno?" + "')"
				// +" and amnt = (select amnt from lcpol where polno ='" +
				// pLCPolSchema.getPolNo()+"')"
				// +" and amnt <= (select sum(standmoney) from lcget where polno = '" +
				// pLCPolSchema.getPolNo()+"' and length(trim(dutycode)) = 6)" //2006-05-16 周磊
				// 增加对附加险续保并加保的判断
				+ " and rnewflag != '-2'";
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		sqlbv35.sql(tSql);
		sqlbv35.put("contno", pLCPolSchema.getContNo());
		sqlbv35.put("riskcode", pLCPolSchema.getRiskCode());
		sqlbv35.put("insuredno", pLCPolSchema.getInsuredNo());
		sqlbv35.put("polno", pLCPolSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String tAppFlag = StrTool.cTrim(tExeSQL.getOneValue(sqlbv35));

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算续保标志发生错误!");
		}

		if (tAppFlag.equals("0")) {
			tReturn = "0";
		} else {
			tReturn = "1";
		}

		logger.debug("--AppFlag:续保标志:[" + tReturn + "]," + tSql);
		logger.debug("--------------结束---------------续保标志-----------------------");

		return tReturn;
	}

	/**
	 * 计算被保人0岁保单生效对应日
	 * 
	 * @return
	 */
	public String getInsuredvalideBirth(LCPolSchema pLCPolSchema) {
		FDate tInsuredBirthday = new FDate();
		FDate tCValidate = new FDate();
		tCValidate.getDate(pLCPolSchema.getCValiDate());
		Date tInsuredvalideBirth = null;
		tInsuredvalideBirth = PubFun.calDate(tInsuredBirthday
				.getDate(pLCPolSchema.getInsuredBirthday()), 0, "Y", tCValidate
				.getDate(pLCPolSchema.getCValiDate()));

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);

		return df.format(tInsuredvalideBirth);
	}

	/**
	 * 得到生存领取的起领日期,取自出险时点 pPosEdorNo:如果无值时一定要置为空
	 * 
	 * @return
	 */
	public String getGetStartDate(String pPosEdorNo, LCGetSchema pLCGetSchema) {
		String tReturn = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "";

		logger.debug("--------------开始---------------生存领取的起领日期-----------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断LPEdorItem是否有数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (pPosEdorNo == null || StrTool.cTrim(pPosEdorNo).length() == 0) {
			tSql = "select (case when GetStartDate is null then to_date('2900-01-01','yyyy-mm-dd') else GetStartDate end) from LCGet where 1=1 "
					+ " and PolNo ='"
					+ "?PolNo?"
					+ "'"
					+ " and DutyCode ='"
					+ "?DutyCode?"
					+ "'"
					+ " and GetDutyCode in "
					+ " (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '"
					+ "?DutyCode?"
					+ "'"
					+ " and a.GetDutyCode = b.GetDutyCode "
					+ " and a.GetDutyCode not in ('"
					+ "?GetDutyCode?"
					+ "')"
					+ " and b.GetType2 = '1')" // 养老金
					+ " and LiveGetType ='0'"; // 生存意外给付标志,为0生存领取
		} else {
			String tPosEdorNo = StrTool.cTrim(pPosEdorNo);
			String tNBPoNo = StrTool.cTrim(pLCGetSchema.getPolNo());
			LPGetSchema tLPGetSchema = getLPGet(tPosEdorNo, tNBPoNo,
					pLCGetSchema);

			if (tLPGetSchema != null) {
				tSql = "select (case when GetStartDate is null then to_date('2900-01-01','yyyy-mm-dd') else GetStartDate end) from LPGet where 1=1 "
						+ " and EdorNo ='"
						+ "?EdorNo?"
						+ "'"
						+ " and PolNo ='"
						+ "?PolNo?"
						+ "'"
						+ " and DutyCode ='"
						+ "?DutyCode?"
						+ "'"
						+ " and GetDutyCode in "
						+ " (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '"
						+ "?DutyCode?"
						+ "'"
						+ " and a.GetDutyCode not in ('"
						+ "?GetDutyCode?"
						+ "')"
						+ " and b.GetType2 = '1')" // 养老金
						+ " and LiveGetType ='0'"; // 生存意外给付标志,为0生存领取
			} else {
				tSql = "select (case when GetStartDate is null then to_date('2900-01-01','yyyy-mm-dd') else GetStartDate end) from LCGet where 1=1 "
						+ " and PolNo ='"
						+ "?PolNo?"
						+ "'"
						+ " and DutyCode ='"
						+ "?DutyCode?"
						+ "'"
						+ " and GetDutyCode in "
						+ " (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '"
						+ "?DutyCode?"
						+ "'"
						+ " and a.GetDutyCode = b.GetDutyCode "
						+ " and a.GetDutyCode not in ('"
						+ "?GetDutyCode?"
						+ "')"
						+ " and b.GetType2 = '1')" // 养老金
						+ " and LiveGetType ='0'"; // 生存意外给付标志,为0生存领取
			}

		}
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		sqlbv38.sql(tSql);
		sqlbv38.put("EdorNo", StrTool.cTrim(pPosEdorNo));
		sqlbv38.put("DutyCode", pLCGetSchema.getDutyCode());
		sqlbv38.put("GetDutyCode", pLCGetSchema.getGetDutyCode());
		sqlbv38.put("PolNo", pLCGetSchema.getPolNo());
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv38));
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "getOamnt";
			tError.errorMessage = "得到保全生存领取的起领日期发生错误!";
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimPubFunBL.getGetStartDate()--得到保全生存领取的起领日期发生错误!"
							+ tSql);
			logger.debug("------------------------------------------------------");
			this.mErrors.addOneError(tError);
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}

		logger.debug("--GetStartDate:[" + tReturn + "]--" + tSql);
		logger.debug("--------------结束---------------生存领取的起领日期-----------------------");

		return tReturn;

	}

	/**
	 * 取出未成年人保额
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	public double getLLParaPupilAmnt(LCPolSchema pLCPolSchema) {
		logger.debug("-----------------------------------计算未成年人限额-----开始------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tContNo = StrTool.cTrim(pLCPolSchema.getContNo()); // 合同号
		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo()); // 险种号
		String tTBDate = StrTool.cTrim(pLCPolSchema.getPolApplyDate()); // 投保日期
		String tManageCom = StrTool.cTrim(pLCPolSchema.getManageCom()); // 签单机构
		String pManageCom = ""; // 比较机构

		if (tManageCom.length() == 8) {
			pManageCom=tManageCom.substring(0, 6);
		} 
		else if (tManageCom.length() == 6) {
			pManageCom=tManageCom.substring(0, 6);
		} 
		else if (tManageCom.length() == 4) {
			pManageCom = tManageCom.substring(0, 4);
		} 
		else if (tManageCom.length() == 2) {
			return -1;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 先根据保单的签单机构,投保日期在参数表中查找是否有数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		//2008-12-11  鉴于参数表的机构列表和金额还不确定，这里在暂时只测试程序，具体逻辑等徐建军确定后再进行处理
		tSql = "select * from LLParaPupilAmnt where 1=1 "
				//+ " and ComCode in ('" + tSignCom4 + "','" + tSignCom6 + "')"
			+ " and ComCode = '" + "?ComCode?" + "'"
				+ " and StartDate <= to_date('" + "?tbdate?" + "','yyyy-mm-dd') "
				+ " and (EndDate  >  to_date('" + "?tbdate?"
				+ "','yyyy-mm-dd') or EndDate is null )";

		logger.debug("--查询未成年人限额sql:"+ tSql);
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		sqlbv39.sql(tSql);
		sqlbv39.put("ComCode", pManageCom);
		sqlbv39.put("tbdate", tTBDate);
		LLParaPupilAmntDB tLLParaPupilAmntDB = new LLParaPupilAmntDB();
		LLParaPupilAmntSet tLLParaPupilAmntSet = tLLParaPupilAmntDB
				.executeQuery(sqlbv39);


		if (tLLParaPupilAmntDB.mErrors.needDealError()) {
			mErrors.addOneError("查询未成年人限额失败!"
					+ tLLParaPupilAmntDB.mErrors.getError(0).errorMessage);
			return -1;
		}

		if (tLLParaPupilAmntSet.size() != 1) {
			return -1;
		} else {
			t_Sum_Return = Arith.round(tLLParaPupilAmntSet.get(1)
					.getBaseValue(), 2);

		}

		logger.debug("--返回值：[" + t_Sum_Return + "]");
		logger.debug("-----------------------------------计算未成年人限额-----结束------------------------------------");
		return t_Sum_Return;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－匹配理算算法－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－财务处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 财务暂交费未核销信息查询(原来直接用DB查询，后针对可能有退费情况进行修改)
	 */
	public LJTempFeeSet getLJTempFee(String pTempFeeNo) {

		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();

		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// tLJTempFeeDB.setTempFeeNo(pTempFeeNo);
		// LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
		String tSQL = " select * from ljtempfee where tempfeeno='" + "?tempfeeno?"
				+ "' " + " and confdate is null ";
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql(tSQL);
		sqlbv40.put("tempfeeno", pTempFeeNo);
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv40);

		if (tLJTempFeeDB.mErrors.needDealError()) {
			mErrors.addOneError("暂交费收据号码[" + pTempFeeNo + "]的暂交费信息没有取到!!!"
					+ tLJTempFeeDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLJTempFeeSet.size() == 0) {
			mErrors.addOneError("暂交费收据号码[" + pTempFeeNo + "]的暂交费信息没有取到!!!");
			return null;
		}
		

		return tLJTempFeeSet;

	}
	
	/**
	 * 财务暂交费分类未核销信息查询(原来直接用DB查询，后针对可能有退费情况进行修改)
	 */
	public LJTempFeeClassSet getLJTempFeeClass(String pTempFeeNo) {

		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		// tLJTempFeeDB.setTempFeeNo(pTempFeeNo);
		// LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
		String tSQL = " select * from ljtempfeeclass where tempfeeno='" + "?tempfeeno?"
				+ "' " + " and confdate is null ";
		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
		sqlbv41.sql(tSQL);
		sqlbv41.put("tempfeeno", pTempFeeNo);
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv41);

		if (tLJTempFeeClassDB.mErrors.needDealError()) {
			mErrors.addOneError("暂交费收据号码[" + pTempFeeNo + "]的暂交费分类信息没有取到!!!"
					+ tLJTempFeeClassDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLJTempFeeClassSet.size() == 0) {
			mErrors.addOneError("暂交费收据号码[" + pTempFeeNo + "]的暂交费分类信息没有取到!!!");
			return null;
		}

		return tLJTempFeeClassSet;

	}

	/**
	 * 目的:得到应收总表信息
	 * 
	 */
	public LJSPaySchema getLJSPay(String pGetNoticeNo) {

		LJSPaySchema tLJSPaySchema = new LJSPaySchema();

		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(pGetNoticeNo);
		LJSPaySet tLJSPaySet = tLJSPayDB.query();

		if (tLJSPayDB.mErrors.needDealError()) {
			mErrors.addOneError("通知书号[" + pGetNoticeNo + "]的应收汇总表信息没有取到!!!"
					+ tLJSPayDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLJSPaySet.size() == 1) {
			tLJSPaySchema = tLJSPaySet.get(1);
		} else {
			mErrors.addOneError("通知书号[" + pGetNoticeNo + "]的应收汇总表信息为空没有取到!!!");
			return null;
		}

		return tLJSPaySchema;
	}

	/**
	 * 目的:得到应收明细表信息
	 */
	public LJSPayPersonSet getLJSPayPersonSet(String pGetNoticeNo) {

		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();

		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setGetNoticeNo(pGetNoticeNo);
		LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();

		if (tLJSPayPersonDB.mErrors.needDealError()) {
			mErrors.addOneError("通知书号[" + pGetNoticeNo + "]的应收明细信息没有取到!!!"
					+ tLJSPayPersonDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLJSPayPersonSet.size() == 0) {
			mErrors.addOneError("通知书号[" + pGetNoticeNo + "]的应收明细信息没有取到!!!");
			return null;
		}

		return tLJSPayPersonSet;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－财务处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－其他处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	public LDSysVarSchema getLDSysVar(String pSysVar, String pSysVarType) {
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar(pSysVar);
		if (pSysVarType != null) {
			tLDSysVarDB.setSysVarType(pSysVarType);
		}

		LDSysVarSet tLDSysVarSet = tLDSysVarDB.query();

		if (tLDSysVarDB.mErrors.needDealError()) {
			mErrors.addOneError("变量类型[" + pSysVar + "]的合同核保批次结果信息没有取到!!!"
					+ tLDSysVarDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLDSysVarSet.size() == 1) {
			tLDSysVarSchema = tLDSysVarSet.get(1);
		} else {
			mErrors.addOneError("变量类型[" + pSysVar + "]的合同核保批次结果信息没有取到!!!");
			return null;
		}

		return tLDSysVarSchema;

	}

	private String getLDCode(String pCodeType, String pCode) {
		String tSReturn = "";
		pCodeType = StrTool.cTrim(pCodeType);
		pCode = StrTool.cTrim(pCode);

		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType(pCodeType);
		tLDCodeDB.setCode(pCode);

		return tSReturn;

	}

	/**
	 * 得到日期的部件
	 */
	public int getDateUnit(String pDate, String pUnit) {
		int tIReturn = 0;

		FDate tFDate = new FDate();
		Date tDate = tFDate.getDate(pDate);

		GregorianCalendar tCalendar = new GregorianCalendar();
		tCalendar.setTime(tDate);

		if (pUnit.equals("Y")) {
			tIReturn = tCalendar.get(Calendar.YEAR);
		}

		if (pUnit.equals("M")) {
			tIReturn = tCalendar.get(Calendar.MONTH) + 1;
		}

		if (pUnit.equals("D")) {
			tIReturn = tCalendar.get(Calendar.DAY_OF_YEAR);// 一年中的第N天
			tIReturn = tCalendar.get(Calendar.DAY_OF_MONTH);// 一月中的第N天

		}

		return tIReturn;
	}

	/**
	 * 得到理赔操作员姓名
	 * 
	 * @return
	 */
	public String getUserName(String pUserCode) {
		String tReturn = "";
		String tSql = "select a.UserName from LLClaimUser a where 1=1 "
				+ " and UserCode = '" + "?UserCode?" + "'";
		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
		sqlbv42.sql(tSql);
		sqlbv42.put("UserCode", pUserCode);
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv42));

		if (tExeSQL.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询操作员名称时出错!";
			this.mErrors.addOneError(tError);
		}

		return tReturn;
	}

	/**
	 * 得到本级机构的主任核赔人代码
	 * 
	 * @return tReturn
	 */
	public String getClaimUserMain(String tMngCom) {
		String tReturn = "";
		String tSql = "select UserCode from LLClaimUser a where 1=1 "
				 + " and comcode = '" + "?comcode?" + "'";
		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
		sqlbv43.sql(tSql);
		sqlbv43.put("comcode", tMngCom);
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = tExeSQL.getOneValue(sqlbv43);

		if (tReturn == null || StrTool.cTrim(tReturn).length() == 0) {
			logger.debug(tMngCom + "机构下无主任核赔人!");
		}
		return tReturn;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－其他处理信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 
	 * @param tClmNo
	 *            String
	 * @param tGetDutyKind
	 *            String
	 * @return String 345险种上线应急方案，应该将下面的方法写成FNC，以便以后维护，有时间再修改。
	 */
	public double getSumMoney26(String tClmNo, String tPolNo, String tDutyCode,
			String tGetDutyCode, String tGetDutyKind) {
		// 准备相关信息
		String tSQLF = " select * from llclaimdetail where clmno='" + "?clmno?"
				+ "' and polno='" + "?polno?" + "' " + " and dutycode='"
				+ "?dutycode?" + "' and getdutykind='" + "?dutykind?" + "' ";
		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
		sqlbv44.sql(tSQLF);
		sqlbv44.put("clmno", tClmNo);
		sqlbv44.put("polno", tPolNo);
		sqlbv44.put("dutycode", tDutyCode);
		sqlbv44.put("dutykind", tGetDutyKind);
		logger.debug(tSQLF);
		LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();

		tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv44);

		String tContNo = tLLClaimDetailSet.get(1).getContNo();
		String tRiskCode = tLLClaimDetailSet.get(1).getRiskCode();
		String tCustomerNo = tLLClaimDetailSet.get(1).getCustomerNo();

		LCPolSchema tLCPolSchema = getLCPol(tPolNo);
		String tNBPolNo = getNBPolNo(tLCPolSchema);// 承保时的保单号

		String tInsDate = getInsDate(tClmNo);// 出险时间

		// 判断是否发生过保全，使保额发生变化，目的在于求原保额（对于不冲减的险种类型26）
		double tDAvaAmnt = 0.00;
		LPEdorItemSchema tMRLPEdorItemSchema = getLPEdorItemAfter(tContNo,
				tNBPolNo, tInsDate, null); // 得到保全批改项目表

		String tMRiskPosEdorNo = "";
		if (tMRLPEdorItemSchema != null) {
			tMRiskPosEdorNo = StrTool.cTrim(tMRLPEdorItemSchema.getEdorNo());
			logger.debug("====tMRiskPosEdorNo====" + tMRiskPosEdorNo);
			String tSql = "";
			tSql = " select StandMoney-SumMoney from LPGet where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?" + "'"
					+ " and PolNo  ='" + tNBPolNo + "'" + " and DutyCode ='"
					+ "?DutyCode?" + "'" + " and GetDutyCode ='" + "?GetDutyCode?"
					+ "'";
			SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
			sqlbv45.sql(tSql);
			sqlbv45.put("PolNo", tNBPolNo);
			sqlbv45.put("EdorNo", tMRiskPosEdorNo);
			sqlbv45.put("DutyCode", tDutyCode);
			sqlbv45.put("GetDutyCode", tGetDutyCode);
			logger.debug(tSql);
			ExeSQL ttExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = ttExeSQL.execSQL(sqlbv45);
			if (tSSRS.getMaxRow() > 0) {
				String tB = tSSRS.GetText(1, 1);
				tDAvaAmnt = Double.parseDouble(tB);
			} else {
				String tSQLS = " select StandMoney from lcget "
						+ " where polno='" + "?polno?" + "' " + " and dutycode='"
						+ "?dutycode?" + "'" + " and getdutycode='" + "?getdutycode?"
						+ "'";
				logger.debug(tSQLS);
				SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
				sqlbv46.sql(tSQLS);
				sqlbv46.put("polno", tPolNo);
				sqlbv46.put("dutycode", tDutyCode);
				sqlbv46.put("getdutycode", tGetDutyCode);
				String tA = ttExeSQL.getOneValue(sqlbv46);
				tDAvaAmnt = Double.parseDouble(tA);
			}
		} else {
			String tSQLS = " select StandMoney from lcget " + " where polno='"
					+ "?polno?" + "' " + " and dutycode='" + "?dutycode?" + "' "
					+ " and getdutycode='" + "?getdutycode?" + "'";
			logger.debug(tSQLS);
			SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
			sqlbv47.sql(tSQLS);
			sqlbv47.put("polno", tPolNo);
			sqlbv47.put("dutycode", tDutyCode);
			sqlbv47.put("getdutycode", tGetDutyCode);
			ExeSQL ttExeSQL = new ExeSQL();
			String tA = ttExeSQL.getOneValue(sqlbv47);
			tDAvaAmnt = Double.parseDouble(tA);
			logger.debug("=======hanshu01====tDAvaAmnt====" + tDAvaAmnt);
		}

		// 连带被保险人人数(除主被保险人)
		String tSQLFi = " select count(*) from lcinsured where contno ='"
				+ "?contno?" + "' " + " and relationtomaininsured <>'00' ";
		SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
		sqlbv48.sql(tSQLFi);
		sqlbv48.put("contno", tContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tCouPoelpes = tExeSQL.getOneValue(sqlbv48);

		if (tGetDutyKind.equals("100")) {// 意外医疗
			tDAvaAmnt = tDAvaAmnt / (Double.parseDouble(tCouPoelpes) + 1);
			// 查询已赔付的金额
			String tSQLT = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llclaimdetail c where "
					+ " c.customerno ='"
					+ "?customerno?"
					+ "' and c.contno='"
					+ "?contno?"
					+ "' "
					+ " and c.dutycode='"
					+ "?dutycode?"
					+ "' and c.getdutycode='"
					+ "?getdutycode?"
					+ "' "
					+ " and c.givetype ='0' and c.getdutykind ='100' and exists "
					+ "(select 'X' from llregister r where c.clmno=r.rgtno and r.clmstate in ('50','60')) ";
			logger.debug(tSQLT);
			SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
			sqlbv49.sql(tSQLT);
			sqlbv49.put("customerno", tCustomerNo);
			sqlbv49.put("contno", tContNo);
			sqlbv49.put("dutycode", tDutyCode);
			sqlbv49.put("getdutycode", tGetDutyCode);
			String tSumRealPay = tExeSQL.getOneValue(sqlbv49);
			if (tDAvaAmnt >= Double.parseDouble(tSumRealPay)) {
				tDAvaAmnt = tDAvaAmnt - Double.parseDouble(tSumRealPay);
			}
		}
		if (tGetDutyKind.equals("101") || tGetDutyKind.equals("102")) {// 意外伤残 意外死亡
			String tRealPaySQL = " select (case when sum(c.realpay) is null then 0 else sum(c.realpay) end) from llclaimdetail c "
					+ " where c.customerno ='"
					+ "?customerno?"
					+ "' and c.contno='"
					+ "?contno?"
					+ "'"
					+ " and c.dutycode='"
					+ "?dutycode?"
					+ "' and c.getdutycode='"
					+ "?getdutycode?"
					+ "' "
					+ " and c.givetype ='0' and c.getdutykind in ('101','102') and c.clmno in "
					+ " (select r.rgtno from llregister r where r.rgtno =c.clmno and r.clmstate in ('50','60')) ";
			SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
			sqlbv50.sql(tRealPaySQL);
			sqlbv50.put("customerno", tCustomerNo);
			sqlbv50.put("contno", tContNo);
			sqlbv50.put("dutycode", tDutyCode);
			sqlbv50.put("getdutycode", tGetDutyCode);
			String tRealPay101 = tExeSQL.getOneValue(sqlbv50);
			logger.debug(tRealPaySQL);

			String tBValueSQL = " select basevalue from LLParaPupilAmnt "
					+ " where trim(comcode) =substr('"
					+ "?mngcom?" + "' ,1,4) "
					+ " and startdate <='" + "?cvalidate?" + "' "
					+ " and ((enddate is NULL) or enddate ='' or enddate>'"
					+ "?cvalidate?" + "') ";
			SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
			sqlbv51.sql(tBValueSQL);
			sqlbv51.put("mngcom", tLCPolSchema.getManageCom());
			sqlbv51.put("cvalidate", tLCPolSchema.getCValiDate());
			String tBValue = tExeSQL.getOneValue(sqlbv51);

			String trelaSQL = " select relationtomaininsured from lcinsured where contno ='"
					+ "?contno?" + "' " + " and insuredno ='" + "?insuredno?" + "' ";
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(trelaSQL);
			sqlbv52.put("contno", tContNo);
			sqlbv52.put("insuredno", tCustomerNo);
			String tRela = tExeSQL.getOneValue(sqlbv52);

			if (tRela.equals("00")) {
				String tIns00 = " select birthday from lcinsured where contno = '"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql(tIns00);
				sqlbv53.put("contno", tContNo);
				SSRS tSSRS00 = new SSRS();
				tSSRS00 = tExeSQL.execSQL(sqlbv53);
				double tMDAvaAmnt = 0.00;
				for (int i = 1; i <= tSSRS00.getMaxRow(); i++) {
					String tbirday = tSSRS00.GetText(i, 1);
					int tAge = PubFun.calInterval(tbirday, tLCPolSchema
							.getCValiDate(), "Y");
					if (tAge <= 18) {
						if (tDAvaAmnt / (2 * Double.parseDouble(tCouPoelpes)) > Double
								.parseDouble(tBValue)) {
							tMDAvaAmnt = tMDAvaAmnt + tDAvaAmnt
									/ (2 * Double.parseDouble(tCouPoelpes))
									- Double.parseDouble(tBValue);
						}
					}
				}
				tDAvaAmnt = tDAvaAmnt / 2 + tMDAvaAmnt;
			} else {
				String tIns = " select birthday from lcinsured where contno = '"
						+ "?contno?"
						+ "' "
						+ " and insuredno='"
						+ "?insuredno?"
						+ "'";
				SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
				sqlbv54.sql(tIns);
				sqlbv54.put("contno", tContNo);
				sqlbv54.put("insuredno", tCustomerNo);
				String tbirday = tExeSQL.getOneValue(sqlbv54);
				int tAge = PubFun.calInterval(tbirday, tLCPolSchema
						.getCValiDate(), "Y");
				// tDAvaAmnt=tDAvaAmnt/(2*Double.parseDouble(tCouPoelpes));
				logger.debug("=======hanshu02====tDAvaAmnt===="
						+ tDAvaAmnt);
				if (tAge <= 18) {
					if (tDAvaAmnt / (2 * Double.parseDouble(tCouPoelpes)) > Double
							.parseDouble(tBValue)) {
						tDAvaAmnt = Double.parseDouble(tBValue);
						logger.debug("=======hanshu03====tDAvaAmnt===="
								+ tDAvaAmnt);
					} else {
						tDAvaAmnt = tDAvaAmnt
								/ (2 * Double.parseDouble(tCouPoelpes));
						logger.debug("=======hanshu04====tDAvaAmnt===="
								+ tDAvaAmnt);
					}
				}
			}
			if (tDAvaAmnt >= Double.parseDouble(tRealPay101)) {
				tDAvaAmnt = tDAvaAmnt - Double.parseDouble(tRealPay101);
				logger.debug("=======hanshu05====tDAvaAmnt===="
						+ tDAvaAmnt);
			} else {
				tDAvaAmnt = 0.00;
			}
			if (tGetDutyCode.equals("345042") || tGetDutyCode.equals("345942")) {
				tDAvaAmnt = 0.00;
			}
		}
		logger.debug("=======hanshu06====tDAvaAmnt====" + tDAvaAmnt);
		return tDAvaAmnt;
	}

	/**
	 * 找出换号前的保单险种号,也就是承保时的保单号
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	public String getNBPolNo(LCPolSchema pLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 续期抽档时 原保单的 AppFlag =
		 * "1" 新保单的 AppFlag = "9" 抽档回销成功时 原保单的 AppFlag = "4" 新保单的 AppFlag = "1"
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tReturn = "";
		String tAppFlag = StrTool.cTrim(pLCPolSchema.getAppFlag());
		String tContNo = StrTool.cTrim(pLCPolSchema.getContNo());
		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());
		if (tAppFlag.equals("4")) {
			String tSql = " select * from LCPol where 1=1 "
					+ " and ContNo = '"
					+ "?ContNo?"
					+ "'"
					+ " and RiskCode='"
					+ "?RiskCode?"
					+ "'"
					+ " and PayToDate in (select max(PayToDate) from LCPol where 1=1 "
					+ " and ContNo = '" + "?ContNo?" + "'" + " and RiskCode='"
					+ "?RiskCode?" + "' )";
			SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
			sqlbv55.sql(tSql);
			sqlbv55.put("ContNo", tContNo);
			sqlbv55.put("RiskCode", tRiskCode);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv55);
			if (tLCPolDB.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalMatchBL";
				tError.functionName = "getYearBonus";
				tError.errorMessage = "得到承保时的保单险种号失败!";
				logger.debug("--------------------------------------------------------------------------------------------------");
				logger.debug("--LLClaimCalMatchBL.getNBPolNo()--得到承保时的保单险种号失败!"
								+ tSql);
				logger.debug("--------------------------------------------------------------------------------------------------");
				this.mErrors.addOneError(tError);
			}

			if (tLCPolSet.size() == 1) {
				tReturn = StrTool.cTrim(tLCPolSet.get(1).getPolNo());
			} else {
				tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
			}
		} else {
			tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
		}
		return StrTool.cTrim(tReturn);
	}
	
	
	/**
	 * 查询赔案设计的社保和第三方给付金额
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	public double[] getSocOtherFee(String tClmno,String destype) {
		
		double[] tSocOtherFee={0.0,0.0};
		
		// 社保赔付金额。
		double p_Sum_SocFranchise = 0;
		p_Sum_SocFranchise = Double.parseDouble(new DecimalFormat("0.00")
				.format(p_Sum_SocFranchise));
		
		//第三方给付金额
		double p_Sum_OtherFranchise = 0;
		p_Sum_OtherFranchise = Double.parseDouble(new DecimalFormat("0.00")
				.format(p_Sum_OtherFranchise));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		tSql = "select currency,FactorCode,(case when sum(FactorValue) is null then 0 else sum(FactorValue) end) "
			/*+ " nvl(sum(case FactorCode  when 'D001' then FactorValue end),0),"
			+ " nvl(sum(case FactorCode  when 'D002' then FactorValue end),0) "*/
			+ " from LLOtherFactor  where 1 = 1 " + " and Feeitemtype='D' and ClmNo ='"
			+ "?ClmNo?" + "'"
			+" group by currency,FactorCode";

	    logger.debug("------------------------------------------------------");
	    logger.debug("--计算社保给付,第三方给付:" + tSql);
	    logger.debug("------------------------------------------------------");
	    SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
	    sqlbv56.sql(tSql);
	    sqlbv56.put("ClmNo", tClmno);
	    tSSRS = tExeSQL.execSQL(sqlbv56);

	    if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算社保给付,第三方给付发生错误,"+tExeSQL.mErrors.getLastError());
			return tSocOtherFee;
		}
	    
	    LDExch tLDExch = new LDExch();
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			
			if(tSSRS.GetText(i,2).equals("D001"))
			{
				if(tSSRS.GetText(i,1).equals(destype))
					tSocOtherFee[0] = tSocOtherFee[0] +Double.parseDouble(tSSRS.GetText(i,3));
				else
					tSocOtherFee[0] = tSocOtherFee[0] +tLDExch.toOtherCur(tSSRS.GetText(i,1),destype,PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}				
			if(tSSRS.GetText(i,2).equals("D002"))
			{
				if(tSSRS.GetText(i,1).equals(destype))
					tSocOtherFee[1] = tSocOtherFee[1] + Double.parseDouble(tSSRS.GetText(i,3));
				else
					tSocOtherFee[1] = tSocOtherFee[1] +tLDExch.toOtherCur(tSSRS.GetText(i,1),destype,PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}				
		}
		
		logger.debug("案件:"+tClmno+"的社保给付总金额:"+tSocOtherFee[0]+",第三方给付金额:"+tSocOtherFee[1]);
		
		return tSocOtherFee;
	}
	
	
	
	/**
	 * 根据险种代码和给付责任取得结算信息的财务类型FeeFinaType
	 * @param tRiskCode:险种编码
	 * @param tGetDutyKind:理赔类型
	 * @return tFeeFinaType:财务类型
	 */
	public String getFeeFinaType(String tRiskCode,String tGetDutyKind) {
		
		 logger.debug("***************** Start getFeeFinaType");
		 
		 logger.debug("传入RiskCode:"+tRiskCode+",传入GetDutyKind:"+tGetDutyKind);
		 String tFeeFinaType=null;//财务类型
		 
		 if(tRiskCode==null||tRiskCode.equals("")){
			 CError.buildErr(this, "传入险种信息为空!");
			 return tFeeFinaType;
		 }
		 
		 if(tGetDutyKind==null||tGetDutyKind.equals("")){
			 CError.buildErr(this, "传入理赔类型为空!");
			 return tFeeFinaType;
		 }
		 
		 /**
		  * 判断顺序:1 健康委托产品; 2 短期险; 3 根据理赔类型判断
		  * 
		  */
		 LMRiskAppDB tLMRiskAppDB=new LMRiskAppDB();
		 tLMRiskAppDB.setRiskCode(tRiskCode);
		 
		 LMRiskAppSet tLMRiskAppSet=tLMRiskAppDB.query();
		 
		 logger.debug("tLMRiskAppSet.get(1).getHealthType():"+tLMRiskAppSet.get(1).getHealthType());
		 logger.debug("tLMRiskAppSet.get(1).getRiskType():"+tLMRiskAppSet.get(1).getRiskType());
		 
         if(tLMRiskAppSet.size()>0)
         {
        	 boolean CMFlag=false;
        	 
        	 //首先判断是不是健康委托产品
        	 if(!(tLMRiskAppSet.get(1).getRiskType()==null||tLMRiskAppSet.get(1).getRiskType().equals("")))
        	 {
        		 if(!(tLMRiskAppSet.get(1).getHealthType()==null|| tLMRiskAppSet.get(1).getHealthType().equals("")))
            	 {
                 	 if((tLMRiskAppSet.get(1).getRiskType().equals("H"))&&
                			 (tLMRiskAppSet.get(1).getHealthType().equals("1"))){
                		 
                    	 tFeeFinaType="CM";////健康委托产品特殊财务类型
                    	 CMFlag=true;
                	 } 
            	 }
        	 }
        	 
        	 //如果不是健康委托产品,则第二步判断是不是短期险赔款
        	 if(CMFlag==false)
        	 {
        		 if(tLMRiskAppSet.get(1).getRiskPeriod().equals("M")||
            			 tLMRiskAppSet.get(1).getRiskPeriod().equals("S")){
            		 
            		 tFeeFinaType="DQPK";//	短期险赔款
            	 }
            	 else{		 
            		 
            		 //前两步都无法确定财务类型的情况再根据理赔类型来确定财务类型
            		 if(tGetDutyKind.substring(1,3).equals("00")
            				 ||tGetDutyKind.substring(1,3).equals("04")){
            			 
            			 tFeeFinaType= "YLPK";//医疗给付赔款
            		 }
            		 
               		 else if(tGetDutyKind.substring(1,3).equals("02")){
            			 
            			 tFeeFinaType= "SWPK";//身故给付赔款
            		 }
            		 
            		 else if(tGetDutyKind.substring(1,3).equals("01")
            				 ||tGetDutyKind.substring(1,3).equals("03")){
            			 
            			 tFeeFinaType= "SCPK";//伤残/高残给付赔款
            		 }
            		 else if(tGetDutyKind.substring(1,3).equals("09")){
            			 
            			 tFeeFinaType= "TF";//退费
            		 }
            		 else{

            			 CError.buildErr(this, "传入理赔类型错误!");	
            			 tFeeFinaType=null;
            		 }
            		 
            	 }
        	 }
         }
         else{
        	 
			 CError.buildErr(this, "查询不到险种"+tRiskCode+"的险种信息!");
			 tFeeFinaType=null;
         }
		 
         logger.debug("RiskCode:"+tRiskCode+",GetDutyKind:"+tGetDutyKind+"确定的FeeFinaType:"+tFeeFinaType);
		 logger.debug("***************** End getFeeFinaType");
		 
		 tLMRiskAppDB=null;
		 tLMRiskAppSet=null;
		 return tFeeFinaType;		
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
		double tDReturn = 0;
		int tITempData = 0;
		String tSTempData = "";
		String tTempData = "";

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 测试2个日期之间经过的月数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tSDate = "2005-12-23";
		String tEDate = "2015-12-23";
		String tSBDate = "2009-12-22";

		// tITempData = PubFun.calInterval(tSDate,tSBDate,"M");//计算出时间间隔的月数
		// logger.debug(tITempData);

		// tDReturn =
		// tLLClaimPubFunBL.getSumMoney26("90000060438","110210000324558","345900","345940","101");
		int a = (int) tLLClaimPubFunBL.getLCPremPeriodTimes("2003-11-05",
				"2007-11-05", "12", "2007-11-05");
		logger.debug(a);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 计算未成年人保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// LCPolSchema tLCPolSchema = tLLClaimPubFunBL.getLCPol("HB010327011000314238");
		// tDReturn = Arith.round(tLLClaimPubFunBL.getLLParaPupilAmnt(tLCPolSchema),2);
		// if (tDReturn==-1)
		// {
		// logger.debug(tDReturn);
		// }
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		// tTempData
		// =String.valueOf(PubFun.getCurrentDate()).substring(5,7)+String.valueOf(PubFun.getCurrentDate()).substring(8,10);
		// tSDate = PubFun.calDate(tSDate,12,"M",tSDate);
		// logger.debug(tSDate);
		// int tIDateUnit = tLLClaimPubFunBL.getDateUnit("20061211","D");
		// logger.debug(tIDateUnit);
		// tDReturn =
		// tLLClaimPubFunBL.getLCPremPeriodTimes("2005-01-01","2005-12-11","0");
		// // tDReturn = Arith.div(11,3);
		// // tDReturn = 10 % 5 ;
		// // logger.debug(tDReturn);
		//
		//
		// LCGetSchema tLCGetSchema = new LCGetSchema();
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// String tReturn = tLLClaimPubFunBL.getGetStartDate(null,tLCGetSchema);
		//
		// tReturn = tLLClaimPubFunBL.getLRFlag(" ","");
		// String tSignCom="12345678";
		// tSignCom = tSignCom.substring(0,5);
		// logger.debug(tSignCom);
		// LCPolSchema pLCPolSchema = new LCPolSchema();
		// pLCPolSchema.setPolNo("HB020421411000027225");
		// tLLClaimPubFunBL.getPolNoAddPay("","a",pLCPolSchema);
		// tLLClaimPubFunBL.getLPEdorItemAfter("HB010226011000077","0000","2005-01-01","CT','BC','CM");
		// tLLClaimPubFunBL.getLPEdorItemAfter("HB010226011000077","0000","2005-01-01","CT");
	}
	//对于存在预授权标识的费用项  如果案件没有关联预授权则乘以80%
   	public static double getAuthRela(LLClaimDutyFeeSchema cLLClaimDutyFeeSchema,LLRegisterSchema cLLRegisterSchema){
   		
   		double tAuthRela=1.0;
   		
//   		if(cLLRegisterSchema.getPreAuthNo() == null){
//            String sql = "select PreAuthFlag from LDPlanFeeRela where FeeCode='"
//                + cLLClaimDutyFeeSchema.getDutyFeeCode()+"' and PlanCode=(select ContPlanCode from LCCont where ContNo='"
//                + cLLClaimDutyFeeSchema.getContNo() + "') and GetDutyCode='"
//                + cLLClaimDutyFeeSchema.getGetDutyCode() + "'";
//            String tPreAuthFlag = tExeSQL.getOneValue(sql);
//            if("Y".equals(tPreAuthFlag)){
//            	tAuthRela = 0.8;
//            }
//   		}
   		
   		return tAuthRela;
   	}
   	

}
