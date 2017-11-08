package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


/**
 * <p>Title: 重要信息修改</p>
 * <p>Description: 重要信息修改 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft</p>
 * @author quyang
 * @version 1.0
 */

public class LLClaimImportModifyBL
{
private static Logger logger = Logger.getLogger(LLClaimImportModifyBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private VData mReturn = new VData();
    private String mOperate;                        /** 数据操作字符串 */

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private MMap mMMap = new MMap();

    private Reflections mReflections = new Reflections();

    private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema(); //事件信息
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();        //立案分案
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();  //立案信息
    private LLAffixSchema mLLAffixSchema = new LLAffixSchema();
    private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();   //理赔类型集合
    
    private String cancleMergeFlag="";//清除案件信息合并标志
    
    public LLClaimImportModifyBL(){}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }
        

        //数据校验
        if(!checkData())
        {
            return false;
        }


        //进行业务处理
        if (!dealData())
        {
            return false;
        }


        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;

        }

        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, ""))
        {
            // @@错误处理
            CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
            return false;
        }
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mInputData = (VData)cInputData.clone();

        //获取页面信息
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

        mLLAccidentSchema = (LLAccidentSchema) cInputData.getObjectByObjectName("LLAccidentSchema", 0);
        mLLCaseSchema = (LLCaseSchema) cInputData.getObjectByObjectName("LLCaseSchema", 0);
        mLLRegisterSchema = (LLRegisterSchema) cInputData.getObjectByObjectName("LLRegisterSchema", 0);
        mLLAppClaimReasonSet = (LLAppClaimReasonSet) cInputData.getObjectByObjectName("LLAppClaimReasonSet", 0);
        mLLAffixSchema = (LLAffixSchema) cInputData.getObjectByObjectName("LLAffixSchema", 0);

        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);

        return true;
    }


    /**
     * 数据校验
     * 
     * @return
     */
    private boolean checkData()
    {

        if (mLLCaseSchema == null )
        {
            // @@错误处理
            CError.buildErr(this, "传入的立案分案信息为空!");
            return false;
        }
        
        if (mLLAppClaimReasonSet == null )
        {
            // @@错误处理
            CError.buildErr(this, "传入的立案理赔类型信息为空!");
            return false;
        }

        if (mGlobalInput == null||  mLLAppClaimReasonSet == null)
        {
            // @@错误处理
            CError.buildErr(this, "前台传输全局公共数据失败!");
            return false;
        }

        cancleMergeFlag=(String)mTransferData.getValueByName("CancleMergeFlag");
        
        if (cancleMergeFlag == null ||cancleMergeFlag.equals(""))
        {
            // @@错误处理
            CError.buildErr(this, "传入的清除案件合并信息标志为空!");
            return false;
        }
        
        logger.debug("cancleMergeFlag:"+cancleMergeFlag);

        return true;
    }


    /**
     * 1 更新案件信息 2 出险日期和出险原因变动需要清楚案件合并信息
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
    	  //更新案件信息
	      if(!prepareClaimImportModify())
	      {
	         return false;
	      }
	      
	      //出险日期和出险原因变动需要清楚案件合并信息
		  if (cancleMergeFlag.equals("1")) {
				
				String newAccNo="8"+PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号;
				
				if (!UpdateAccNo(newAccNo,mLLAccidentSchema.getAccNo(),mLLCaseSchema.getCaseNo(),"CancelMerge")) {
					return false;
				}
				
				newAccNo=null;
		  }
      
          return true;
    }


    private boolean prepareClaimImportModify()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 声明备份信息表数据变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据赔案号，客户号查询原先立案分案的数据
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(mLLCaseSchema.getRgtNo());
        tLLCaseDB.setCustomerNo(mLLCaseSchema.getCustomerNo());
        if (tLLCaseDB.getInfo() == false)
        {
            CError.buildErr(this, "查询立案分案信息失败!");
            return false;
        }
        
        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        tLLCaseSchema.setSchema(tLLCaseDB.getSchema());

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 做映射，将立案分案原数据赋值到备份数据表
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LLBCaseSchema tLLBCaseSchema = new LLBCaseSchema();     //立案分案备份表
        mReflections.transFields(tLLBCaseSchema,tLLCaseSchema);
        String editNo = PubFun1.CreateMaxNo("EditNo","10"); //生成修改顺序号
        tLLBCaseSchema.setEditNo(editNo);
        tLLBCaseSchema.setOperator(mGlobalInput.Operator);
        tLLBCaseSchema.setMngCom(mGlobalInput.ManageCom);
        tLLBCaseSchema.setMakeDate(PubFun.getCurrentDate());
        tLLBCaseSchema.setMakeTime(PubFun.getCurrentTime());
        tLLBCaseSchema.setEditReason((String)mTransferData.getValueByName("EditReason"));//修改原因

        mMMap.put(tLLBCaseSchema,"INSERT");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 更新立案最新的数据
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tLLCaseSchema.setOperator(mGlobalInput.Operator);
        tLLCaseSchema.setModifyDate(PubFun.getCurrentDate());
        tLLCaseSchema.setModifyTime(PubFun.getCurrentTime());
        tLLCaseSchema.setEditFlag("1");//将修改标志改为已修改

        tLLCaseSchema.setHospitalCode(mLLCaseSchema.getHospitalCode());     //治疗医院
        tLLCaseSchema.setMedAccDate(mLLCaseSchema.getMedAccDate());
        tLLCaseSchema.setAccDate(mLLCaseSchema.getAccDate());               //出险时间
        tLLCaseSchema.setAccidentDetail(mLLCaseSchema.getAccidentDetail()); //出险细节

        tLLCaseSchema.setCureDesc(mLLCaseSchema.getCureDesc());     //治疗情况
        tLLCaseSchema.setAccResult1(mLLCaseSchema.getAccResult1()); //出险结果1
        tLLCaseSchema.setAccResult2(mLLCaseSchema.getAccResult2()); //出险结果2
        //事故者现状，存数据的含义：0-出险日期正常修改 1-出险日期非正常修改
        tLLCaseSchema.setCustState(mLLCaseSchema.getCustState());

        mMMap.put(tLLCaseSchema,"UPDATE");


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 更新事件表出险原因信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
        tLLCaseRelaDB.setCaseNo(mLLCaseSchema.getRgtNo());
        LLCaseRelaSet tLLCaseRelaSet = tLLCaseRelaDB.query();
        if (tLLCaseRelaSet == null || tLLCaseRelaSet.size()==0)
        {
            // @@错误处理
            CError.buildErr(this, "查询事件关联信息失败!");
            return false;
        }

        LLCaseRelaSchema tLLCaseRelaSchema = tLLCaseRelaSet.get(1);
        logger.debug("================="+tLLCaseRelaSchema.getCaseRelaNo());

        //
        LLAccidentDB tLLAccidentDB = new LLAccidentDB();
        tLLAccidentDB.setAccNo(tLLCaseRelaSchema.getCaseRelaNo());
        if (tLLAccidentDB.getInfo() == false)
        {
            // @@错误处理
            CError.buildErr(this, "查询事件信息失败!");
            return false;
        }
        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
        tLLAccidentSchema.setSchema(tLLAccidentDB.getSchema());
        tLLAccidentSchema.setAccType(mLLAccidentSchema.getAccType());
        tLLAccidentSchema.setAccDate(mLLAccidentSchema.getAccDate());//事故日期 Modify by zhaorx 2006-05-22
        mMMap.put(tLLAccidentSchema,"UPDATE");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 立案登记表的出险原因信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(mLLCaseSchema.getRgtNo());
        if (tLLRegisterDB.getInfo() == false)
        {
            // @@错误处理
            CError.buildErr(this, "查询立案信息失败!");
            return false;
        }
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
        tLLRegisterSchema.setAccidentReason(mLLAccidentSchema.getAccType());
        //赔付金领取方式:1-一次统一给付 2-分期支付 3-按年金方式领取
        tLLRegisterSchema.setGetMode((String)mTransferData.getValueByName("tPayGetMode"));
        tLLRegisterSchema.setAcceptedDate(mLLRegisterSchema.getAcceptedDate());
        tLLRegisterSchema.setApplyDate(mLLRegisterSchema.getApplyDate());
        tLLRegisterSchema.setAccidentDate(mLLRegisterSchema.getAccidentDate());
        tLLRegisterSchema.setRgtantMobile(mLLRegisterSchema.getRgtantMobile());
        tLLRegisterSchema.setRgtantAddress(mLLRegisterSchema.getRgtantAddress());
        tLLRegisterSchema.setPostCode(mLLRegisterSchema.getPostCode());
        mMMap.put(tLLRegisterSchema,"UPDATE");


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 做映射，将理赔类型原数据赋值到备份数据表
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
        tLLAppClaimReasonDB.setCaseNo(mLLCaseSchema.getRgtNo());
        tLLAppClaimReasonDB.setCustomerNo(mLLCaseSchema.getCustomerNo());
        LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
        if (tLLAppClaimReasonSet == null || tLLAppClaimReasonSet.size() == 0 )
        {
            // @@错误处理
            CError.buildErr(this, "查询立案理赔类型失败!");
            return false;
        }

        LLBAppClaimReasonSet tLLBAppClaimReasonSet = new LLBAppClaimReasonSet();
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = null;

        for (int i=1;i<=tLLAppClaimReasonSet.size();i++)
        {
            tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(i);
            LLBAppClaimReasonSchema tLLBAppClaimReasonSchema = new LLBAppClaimReasonSchema();

            mReflections.transFields(tLLBAppClaimReasonSchema,tLLAppClaimReasonSchema);
            tLLBAppClaimReasonSchema.setEditNo(editNo);
            tLLBAppClaimReasonSet.add(tLLBAppClaimReasonSchema);
        }
        mMMap.put(tLLBAppClaimReasonSet,"INSERT");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No8.0 删除现业务表中的数据，然后将新理赔类型更新到最新的业务表中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        String strSQL1 = "delete from LLAppClaimReason where CaseNo='" +"?CaseNo?"+ "'";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(strSQL1);
        sqlbv.put("CaseNo", mLLCaseSchema.getRgtNo());
        mMMap.put(sqlbv, "DELETE");
        for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++)
        {
            mLLAppClaimReasonSet.get(i).setOperator(mGlobalInput.Operator);
            mLLAppClaimReasonSet.get(i).setMngCom(mGlobalInput.ManageCom);
            mLLAppClaimReasonSet.get(i).setMakeDate(PubFun.getCurrentDate());
            mLLAppClaimReasonSet.get(i).setMakeTime(PubFun.getCurrentTime());
            mLLAppClaimReasonSet.get(i).setModifyDate(PubFun.getCurrentDate());
            mLLAppClaimReasonSet.get(i).setModifyTime(PubFun.getCurrentTime());
        }
        
        mMMap.put(mLLAppClaimReasonSet, "INSERT");

    	String strSQL2 = "update LLAffix set ReAffixDate= '" +  "?ReAffixDate?" + "' Where rgtno = '"
		+ "?rgtno?" + "'";
    	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    	sqlbv1.sql(strSQL2);
    	sqlbv1.put("ReAffixDate", mLLAffixSchema.getReAffixDate());
    	sqlbv1.put("rgtno", mLLAffixSchema.getRgtNo());
    	mMMap.put(sqlbv1, "UPDATE");
        
        return true;
      }
    
    /**
	 * 更新sql
	 * 第一个是参数是要覆盖的事件号,第二个参数是被覆盖的事件号,第三个参数是本次案件的赔案号,第四个参数是操作类型
	 * 
	 * @return boolean
	 */
	private boolean UpdateAccNo(String mergeAccNo,String oldAccNo,String oldClmNo,String pOperate) {
		try {
			
			logger.debug("sql1:"+"update llclaimpolicy set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql2:"+"update llclaimdetail set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql3:"+"update LLPrepayDetail set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql4:"+"update llcaserela set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql5:"+"update LLAccidentSub set accno='"+mergeAccNo+"' where accno='"+oldAccNo+"'");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("update llclaimpolicy set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
			sqlbv1.put("accno", mergeAccNo);
			sqlbv1.put("clmno", oldClmNo);
			mMMap.put(sqlbv1, "UPDATE");
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("update llclaimdetail set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
			sqlbv2.put("accno", mergeAccNo);
			sqlbv2.put("clmno", oldClmNo);
			mMMap.put(sqlbv2, "UPDATE");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("update LLPrepayDetail set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
			sqlbv3.put("accno", mergeAccNo);
			sqlbv3.put("clmno", oldClmNo);
			mMMap.put(sqlbv3, "UPDATE");
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("update llcaserela set caserelano='"+"?accno?"+"' where caseno='"+"?clmno?"+"'");
			sqlbv4.put("accno", mergeAccNo);
			sqlbv4.put("clmno", oldClmNo);
			mMMap.put(sqlbv4, "UPDATE");
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("update LLAccidentSub set accno='"+"?accno?"+"' where accno='"+"?oldAccNo?"+"'");
			sqlbv5.put("accno", mergeAccNo);
			sqlbv5.put("clmno", oldClmNo);
			sqlbv5.put("oldAccNo", oldAccNo);
			mMMap.put(sqlbv5, "UPDATE");
			
			if(pOperate.equals("CancelMerge"))
			{
				LLAccidentDB tLLAccidentDB = new LLAccidentDB();
				tLLAccidentDB.setAccNo(oldAccNo);
				
				if(tLLAccidentDB.getInfo())
				{
					LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
					tLLAccidentSchema.setSchema(tLLAccidentDB.getSchema());
					tLLAccidentSchema.setAccNo(mergeAccNo);
					mMMap.put(tLLAccidentSchema, "INSERT");
				}
				else
				{
					// @@错误处理
					CError.buildErr(this, "查询不到案件"+oldClmNo+"的事件信息!");
					return false;
				}

			}
			else
			{
				logger.debug("sql6:"+"delete from LLAccident where accno='"+oldAccNo+"'");
				mMMap.put("delete from LLAccident where accno='"+oldAccNo+"'", "INSERT");
			}
			
			
			mResult.add(mergeAccNo);
			mReturn.add(mergeAccNo);

		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.add(mMMap);
        return true;
    }

    /**
     * 返回结果
     * @return VData
     */
    public VData getResult()
    {
        return mReturn;
    }

    /**
     * 返回错误
     * @return VData
     */
    public CErrors getErrors()
    {
        return mErrors;
    }

    public static void main(String[] args)
    {


        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";


        String tAccNo = "80000001850";
        String tClmNo = "90000002660";
        String tAccDate = "2005-06-29";
        String tCusNo = "0000499030";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("ContType","1");   //总单类型,1-个人总投保单,2-集体总单
        tTransferData.setNameAndValue("ClmState","20");  //赔案状态，20立案，30审核

        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //立案事件表
        LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //立案分案
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();//立案理赔类型
        LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();//理赔类型集合

        tLLAccidentSchema.setAccNo(tAccNo);
        tLLAccidentSchema.setAccType("1"); //事故类型===出险原因

        tLLCaseSchema.setRgtNo(tClmNo);//申请登记号
        tLLCaseSchema.setCaseNo(tClmNo);//立案号(赔案号)
        tLLCaseSchema.setCustomerNo(tCusNo);//出险人客户号

        tLLCaseSchema.setHospitalCode("1001");//治疗医院
        tLLCaseSchema.setAccDate(tAccDate);//出险时间
        tLLCaseSchema.setAccidentDetail("V05");//出险细节


        tLLCaseSchema.setCureDesc("01");//治疗情况
        tLLCaseSchema.setAccResult1("AA01");//出险结果1
        tLLCaseSchema.setAccResult2("AA02");//出险结果2


        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.add(tLLAccidentSchema);
        tVData.add(tLLCaseSchema);
        tVData.add(tLLAppClaimReasonSet);
        tVData.add(tTransferData);


        LLClaimImportModifyBL tClaimCalBL = new LLClaimImportModifyBL();
        tClaimCalBL.submitData(tVData, "");

    }

}
