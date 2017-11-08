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
 * <p>Description: 预付审核回退逻辑处理类</p>
 * 删除之前保存的llbnf,ljsget,LLPrepayClaim,LLPrepayDetail,LLBalance,llclaimuwmain中的对应数据,并将llclaimdetail的PrepayFlag(预付标志)置0(没有预付),
 * PrepaySum置0,llclaim的beforepay置0;
 * @author: zhangzheng
 * @version 1.0
 */
public class LLClaimPrepayAuditRetrunBL implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimPrepayAuditRetrunBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */

    private MMap map = new MMap();
    private TransferData mTransferData = null;
    
    private String mClmNo="";//案件号
    private String mAuditConclusion="";//案件审核结论

    private GlobalInput mG = new GlobalInput();

    public LLClaimPrepayAuditRetrunBL()
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
                "----------LLClaimPrepayAuditRetrunBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();

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
        logger.debug("----------after PubSubmit----------");
        
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
        logger.debug("---LLClaimPrepayAuditRetrunBL start getInputData()");
        
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = new TransferData();
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mAuditConclusion = (String) mTransferData.getValueByName("AuditConclusion");

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
    	//校验赔案号
    	if(mClmNo==null||mClmNo.equals("")){
    		
    		CError.buildErr(this, "传入案件号为空!");
    		return false;
    	}
    	
		
		if(mAuditConclusion==null||mAuditConclusion.equals("")){
			
			CError.buildErr(this,"案件"+mClmNo+"的审核结论为空!");
			return false;
		}
		
		logger.debug("案件"+mClmNo+"审核结论:"+mAuditConclusion);
    	
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {

        logger.debug("------start dealData-----");

        //预付审核退回,清除预付信息
    	if (!eliminatePayInformation())
    		return false;	
  
    	logger.debug("------end dealData-----");
    	
        return true;

    }
    
    /**
	 * 2009-1-4 zhangzheng 
	 * 回退时删除除审核,审批结论外的所有预付信息
	 * @return
	 */
	private boolean eliminatePayInformation(){
		
		
        String ljsgetSql="delete from ljsget where exists(select 1 from ljsgetclaim a where feeoperationtype='B' and a.getnoticeno=ljsget.getnoticeno) and ljsget.otherno='"+"?clmno?"+"'";
        String bnfSql="delete from llbnf where bnfkind='B' and clmno='"+"?clmno?"+"'";
        
        String balanceSql="delete from llbalance where feeoperationtype='B' and clmno='"+"?clmno?"+"'";
        String ljsgetclaimSql="delete from ljsgetclaim where feeoperationtype='B' and otherno='"+"?clmno?"+"'";
        
        String prepayclaimSql="delete from LLPrepayClaim where clmno='"+"?clmno?"+"'";
        String prepaydetaliSql="delete from LLPrepayDetail where clmno='"+"?clmno?"+"'";
        
        String llclaimSql="update llclaim set beforepay='0' where clmno='"+"?clmno?"+"'";
        String llclaimdetailSql="update llclaimdetail set PrepayFlag='0',PrepaySum='0' where clmno='"+"?clmno?"+"'";
		
	    
		// 打包提交数据    
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(ljsgetSql);
        sqlbv.put("clmno", mClmNo);
        map.put(sqlbv, "DELETE");
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(bnfSql);
        sqlbv1.put("clmno", mClmNo);
        map.put(sqlbv1, "DELETE");
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(balanceSql);
        sqlbv2.put("clmno", mClmNo);
        map.put(sqlbv2, "DELETE");
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(ljsgetclaimSql);
        sqlbv3.put("clmno", mClmNo);
        map.put(sqlbv3, "DELETE");
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(prepayclaimSql);
        sqlbv4.put("clmno", mClmNo);
        map.put(sqlbv4, "DELETE");
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(prepaydetaliSql);
        sqlbv5.put("clmno", mClmNo);
        map.put(sqlbv5, "DELETE");
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql(llclaimSql);
        sqlbv6.put("clmno", mClmNo);
        map.put(sqlbv6, "UPDATE");
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        sqlbv7.sql(llclaimdetailSql);
        sqlbv7.put("clmno", mClmNo);
        map.put(sqlbv7, "UPDATE");
    
        //释放引用
        llclaimdetailSql=null;
        llclaimSql=null;
        prepaydetaliSql=null;
        prepayclaimSql=null;
        ljsgetclaimSql=null;
        balanceSql=null;
        bnfSql=null;
        ljsgetSql=null;
        
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
