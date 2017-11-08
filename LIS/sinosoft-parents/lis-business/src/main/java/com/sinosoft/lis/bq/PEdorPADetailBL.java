package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LPPerInvestPlanSchema;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.db.*;

public class PEdorPADetailBL {
private static Logger logger = Logger.getLogger(PEdorPADetailBL.class);
        /** 错误处理类，每个需要错误处理的类中都放置该类 */
         public CErrors mErrors = new CErrors();
         private VData mResult = new VData();
         /** 往后面传输数据的容器 */
         private VData mInputData;
         /** 全局数据 */
         private GlobalInput mGlobalInput = new GlobalInput();

         /** 数据操作字符串 */
         private String mOperate;
         private TransferData mTransferData = new TransferData();
         /** 业务处理相关变量 */
         private LPPerInvestPlanSchema mLPPerInvestPlanSchema = new
                 LPPerInvestPlanSchema();
         private LPPerInvestPlanSet mLPPerInvestPlanSet = new
                                                    LPPerInvestPlanSet();
        // private String mPlanCode = "";
         /** 时间信息*/
         String mCurrentDate = PubFun.getCurrentDate(); //当前值
         String mCurrentTime = PubFun.getCurrentTime();
         String OAFlag = "";
         private MMap map = new MMap();

    public PEdorPADetailBL() {
    }
    public boolean submitData(VData cInputData, String operate)
{
    mInputData = cInputData;
    // 数据操作字符串拷贝到本类中
    this.mOperate = operate;
    if (!getInputData())
    {
        this.buildError("submitData", "无法获取输入信息");
        return false;
    }
    if(!mOperate.equals("DELETE")){
    	
    	if (!checkData())
    	{
    		this.buildError("", "提交的数据有误，请核对后再操作");
    		return false;
    	}
    }
    if (!dealData())
    {
        this.buildError("submitData", "处理数据时失败");
        return false;
    }
    if (!prepareData())
    {
        return false;
    }
    logger.debug("--------end prepareOutputData");
    PubSubmit tSubmit = new PubSubmit();

 /*   if (!tSubmit.submitData(mResult, mOperate))
    { //数据提交
        // @@错误处理
        this.mErrors.copyAllErrors(tSubmit.mErrors);
        CError tError = new CError();
        tError.moduleName = "PMAscriptionRuleBL";
        tError.functionName = "submitData";
        tError.errorMessage = "数据提交失败!";
        this.mErrors.addOneError(tError);
        return false;
    }*/

    return true;
}

/**
 * prepareData
 *
 * @return boolean
 */
private boolean prepareData()
{
    mResult.clear();
    mResult.add(map);
    return true;
}

private boolean getInputData()
{
    mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
            "GlobalInput", 0));
   // mPlanCode = (String) mInputData.get(2);
 ///   logger.debug("mPlanFlag====" + mPlanCode);
    mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData",0);
    //要从VData　类的对象中取出全局变量
    mLPPerInvestPlanSet.set((LPPerInvestPlanSet) mInputData.
                         getObjectByObjectName(
                                 "LPPerInvestPlanSet", 0));
    OAFlag =(String)mTransferData.getValueByName("OAFlag");
    if (mGlobalInput == null || mLPPerInvestPlanSet == null)
    {
        buildError("getInputData", "传过来的数据不全");
        return false;
    }

    return true;
}

private boolean checkData()
{
    double a = 0;

    logger.debug("mLPPerInvestPlanSet.get(1).getInputMode()==="+mLPPerInvestPlanSet.get(1).getInputMode());
    if (mLPPerInvestPlanSet.get(1).getInputMode().equals("1")) //按比例投资
    {
        for (int i = 1; i <= mLPPerInvestPlanSet.size(); i++)
        {
            if(mLPPerInvestPlanSet.get(i).getInvestMaxRate()<0||mLPPerInvestPlanSet.get(i).getInvestMaxRate()>1){
                CError.buildErr(this, "投资比例上限应大等于0,小等于1,请重新录入!");
                return false;
            }
            if(mLPPerInvestPlanSet.get(i).getInvestMinRate()<0||mLPPerInvestPlanSet.get(i).getInvestMinRate()>1){
               CError.buildErr(this, "投资比例下限应大等于0,小等于1,请重新录入!");
               return false;
           }

            if (mLPPerInvestPlanSet.get(i).getInvestRate() < 0 ||
                mLPPerInvestPlanSet.get(i).getInvestRate() > 1)
            {
                CError.buildErr(this, "投资比例应大于等于0,小于等于1,请重新录入!");
                return false;
            }
            else if(mLPPerInvestPlanSet.get(i).getInvestRate()*100%5>0.00001||mLPPerInvestPlanSet.get(i).getInvestRate()*100%5<-0.00001)
            {
            	CError.buildErr(this, "投资比例必须是百分之五的整数倍,请重新录入!");
                return false;
            }
            a += mLPPerInvestPlanSet.get(i).getInvestRate();

        }
        if (Arith.round(a, 12) != 1)
        {
            CError.buildErr(this, "同一缴费帐户下的所有投资帐户的投资比例之和不为1,请重新录入");
            return false;
        }
    }
    return true;
}

private boolean dealData()
{
    logger.debug("mPMRetireRuleSet.size()=====" +
                       mLPPerInvestPlanSet.size());
    //新增处理
    if (mOperate.equals("INSERT"))
    {
        if(!getOtherDate()){
            return false;
        }
        for (int i = 1; i <= mLPPerInvestPlanSet.size(); i++)
        {

            if (prepareNewData(i) == false)
            {
                return false;
            }
        }
    }
    //删除处理
    if (mOperate.equals("DELETE"))
    {
        //准备需要删除的数据
        if (prepareOldData() == false)
        {
            return false;
        }
    }
    if (mOperate.equals("UPDATE"))
    {

        LPPerInvestPlanSchema tempLPPerInvestPlanSchema = new
                LPPerInvestPlanSchema();
        tempLPPerInvestPlanSchema = mLPPerInvestPlanSet.get(1);
        LPPerInvestPlanDB tLPPerInvestPlanDB = new LPPerInvestPlanDB();
        tLPPerInvestPlanDB.setPolNo(tempLPPerInvestPlanSchema.getPolNo());
        tLPPerInvestPlanDB.setInsuAccNo(tempLPPerInvestPlanSchema.getInsuAccNo());
        tLPPerInvestPlanDB.setPayPlanCode(tempLPPerInvestPlanSchema.getPayPlanCode());
        LPPerInvestPlanSet tLPPerInvestPlanSet = new LPPerInvestPlanSet();
        LPPerInvestPlanSet tempLPPerInvestPlanSet = new LPPerInvestPlanSet();
        tLPPerInvestPlanSet = tLPPerInvestPlanDB.query();
        //当没有查询到结果时，用CError　类在前台报错！
        if (tLPPerInvestPlanSet == null || tLPPerInvestPlanSet.size() <= 0)
        {
            mErrors.copyAllErrors(tLPPerInvestPlanDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "TkBdDealBL";
            tError.functionName = "checkData";
            tError.errorMessage = "没有查询到保险单号编码:" +
                                  mLPPerInvestPlanSet.get(1).getPolNo()+
                                  " 账户号编码:" +
                                  mLPPerInvestPlanSet.get(1).getInsuAccNo()+
                                  " 的数据,不能做数据修改!!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(!getOtherDate()){
            return false;
        }
        String oldMakeDate = tLPPerInvestPlanSet.get(1).getMakeDate();
        String oldMakeTime = tLPPerInvestPlanSet.get(1).getMakeTime();
        String oldOperator = tLPPerInvestPlanSet.get(1).getOperator();

        for (int i = 1; i <= mLPPerInvestPlanSet.size(); i++)
        {
            LPPerInvestPlanSchema tLPPerInvestPlanSchema = new
                    LPPerInvestPlanSchema();
            tLPPerInvestPlanSchema = mLPPerInvestPlanSet.get(i);
            tLPPerInvestPlanSchema.setModifyOperator(mGlobalInput.
                    Operator);
            tLPPerInvestPlanSchema.setOperator(oldOperator);
            tLPPerInvestPlanSchema.setMakeDate(oldMakeDate); //当前值
            tLPPerInvestPlanSchema.setMakeTime(oldMakeTime);
            tLPPerInvestPlanSchema.setModifyDate(mCurrentDate); //当前值
            tLPPerInvestPlanSchema.setModifyTime(mCurrentTime);
            
            String tSql = "select (case when a.currency is not null then a.currency else b.currency end) from LCPerInvestPlan a,lcpol b where a.polno = b.polno and a.polno = '?polno?' and insuaccno = '?insuaccno?' and payplancode = '?payplancode?'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(tSql);
            sqlbv.put("polno", tLPPerInvestPlanSchema.getPolNo());
            sqlbv.put("insuaccno", tLPPerInvestPlanSchema.getInsuAccNo());
            sqlbv.put("payplancode", tLPPerInvestPlanSchema.getPayPlanCode());
            String tCurrency = new ExeSQL().getOneValue(sqlbv);
            tLPPerInvestPlanSchema.setCurrency(tCurrency);
            logger.debug("SQLSUPER..>..><<<<"+OAFlag);
            /////////////////////////增加同单不同缴费项下帐户只录一次比例\\\\\\\\\\\\\\\\\\\\\
            if(OAFlag.equals("ALL"))
            {
            	LCPerInvestPlanSet lzLCPerInvestPlanSet = new LCPerInvestPlanSet();
            	LCPerInvestPlanDB lzLCPerInvestPlanDB = new LCPerInvestPlanDB();
            	String strSQL = "select * from LCPerInvestPlan where "
            		+" contno = '?contno?' "
            		+" and insuaccno = '?insuaccno?' "
            		+" and payplancode != '?payplancode?'";
            	logger.debug("SQLSUPER..>..>"+strSQL);
            	SQLwithBindVariables sbv=new SQLwithBindVariables();
            	sbv.sql(strSQL);
            	sbv.put("contno", tLPPerInvestPlanSchema.getContNo());
            	sbv.put("insuaccno", tLPPerInvestPlanSchema.getInsuAccNo());
            	sbv.put("payplancode", tLPPerInvestPlanSchema.getPayPlanCode());
            	lzLCPerInvestPlanSet = lzLCPerInvestPlanDB.executeQuery(sbv);
            	LPPerInvestPlanSet lzLPPerInvestPlanSet = new LPPerInvestPlanSet();
            	LPPerInvestPlanSchema lzLPPerInvestPlanSchemaaddall = new LPPerInvestPlanSchema();
            	Reflections tReflections = new Reflections();
            	
            	for(int j =1;i<=lzLCPerInvestPlanSet.size();i++)
            	{
            		tReflections.transFields(lzLPPerInvestPlanSchemaaddall, tLPPerInvestPlanSchema);
            		lzLPPerInvestPlanSchemaaddall.setPayPlanCode(lzLCPerInvestPlanSet.get(j).getPayPlanCode());
            		lzLPPerInvestPlanSet.add(lzLPPerInvestPlanSchemaaddall);
            	}
            	map.put(lzLPPerInvestPlanSet, "DELETE&INSERT");
            }
            
            
            
            
            
            tempLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);

        }

        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql("delete from LPPerInvestPlan where PolNo='?PolNo?' and InsuAccNo='?InsuAccNo?' and PayPlanCode='?PayPlanCode?' and EdorNo = '?EdorNo?'");
        sbv1.put("PolNo", mLPPerInvestPlanSet.get(1).getPolNo());
        sbv1.put("InsuAccNo", mLPPerInvestPlanSet.get(1).getInsuAccNo());
        sbv1.put("PayPlanCode", mLPPerInvestPlanSet.get(1).getPayPlanCode());
        sbv1.put("EdorNo", mLPPerInvestPlanSet.get(1).getEdorNo());
        map.put(sbv1, "DELETE");

        map.put(tempLPPerInvestPlanSet, "DELETE&INSERT");
       /* map.put("update LPPerInvestPlan set PolNo='" +
                mLPPerInvestPlanSet.get(1).getPolNo()+
                "',InsuAccNo='" +
                mLPPerInvestPlanSet.get(1).getInsuAccNo() +
                "' where PayPlanCode='" +
                mLPPerInvestPlanSet.get(1).getPayPlanCode() +
                "'", "UPDATE"); //执行更新语句*/

        return true;
    }

    return true;
}

private boolean prepareNewData(int tIndex)
{
    //准备计算要素信息数据

    mLPPerInvestPlanSet.get(tIndex).setModifyOperator(mGlobalInput.
            Operator);
    mLPPerInvestPlanSet.get(tIndex).setOperator(mGlobalInput.
                                             Operator);
    mLPPerInvestPlanSet.get(tIndex).setMakeDate(mCurrentDate); //当前值
    mLPPerInvestPlanSet.get(tIndex).setMakeTime(mCurrentTime);
    mLPPerInvestPlanSet.get(tIndex).setModifyDate(mCurrentDate); //当前值
    mLPPerInvestPlanSet.get(tIndex).setModifyTime(mCurrentTime);
    LPPerInvestPlanSchema lzLPPerInvestPlanSchame = new LPPerInvestPlanSchema();
    lzLPPerInvestPlanSchame = mLPPerInvestPlanSet.get(tIndex);
    String strpayplancode = lzLPPerInvestPlanSchame.getPayPlanCode();
    
    String tSql = "select (case when a.currency is not null then a.currency else b.currency end) from LCPerInvestPlan a,lcpol b where a.polno = b.polno and a.polno = '?polno?' and insuaccno = '?insuaccno?' and payplancode = '?payplancode?'";
    SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    sqlbv.sql(tSql);
    sqlbv.put("polno", lzLPPerInvestPlanSchame.getPolNo());
    sqlbv.put("insuaccno", lzLPPerInvestPlanSchame.getInsuAccNo());
    sqlbv.put("payplancode", lzLPPerInvestPlanSchame.getPayPlanCode());
    String tCurrency = new ExeSQL().getOneValue(sqlbv);
    mLPPerInvestPlanSet.get(tIndex).setCurrency(tCurrency);
    logger.debug("亲哥啊。。。。"+strpayplancode);
    map.put(lzLPPerInvestPlanSchame, "DELETE&INSERT");
    /////////////////////////增加同单不同缴费项下帐户只录一次比例\\\\\\\\\\\\\\\\\\\\\
    if(OAFlag.equals("ALL"))
    {
    	LCPerInvestPlanSet lzLCPerInvestPlanSet = new LCPerInvestPlanSet();
    	LCPerInvestPlanDB lzLCPerInvestPlanDB = new LCPerInvestPlanDB();
    	String strSQL = "select * from LCPerInvestPlan where "
    		+" contno = '?contno?' "
    		+" and insuaccno = '?insuaccno?' "
    		+" and payplancode != '?payplancode?'";
    	logger.debug("SQLLLLLLL"+strSQL);
    	SQLwithBindVariables sbv=new SQLwithBindVariables();
    	sbv.sql(strSQL);
    	sbv.put("contno", lzLPPerInvestPlanSchame.getContNo());
    	sbv.put("insuaccno", lzLPPerInvestPlanSchame.getInsuAccNo());
    	sbv.put("payplancode", lzLPPerInvestPlanSchame.getPayPlanCode());
    	lzLCPerInvestPlanSet = lzLCPerInvestPlanDB.executeQuery(sbv);
    	LPPerInvestPlanSet lzLPPerInvestPlanSet = new LPPerInvestPlanSet();
    	LPPerInvestPlanSchema lzLPPerInvestPlanSchemaaddall = new LPPerInvestPlanSchema();
    	Reflections tReflections = new Reflections();
    	
    	for(int i =1;i<=lzLCPerInvestPlanSet.size();i++)
    	{
    		tReflections.transFields(lzLPPerInvestPlanSchemaaddall, lzLPPerInvestPlanSchame);
    		lzLPPerInvestPlanSchemaaddall.setPayPlanCode(lzLCPerInvestPlanSet.get(i).getPayPlanCode());
    		lzLPPerInvestPlanSet.add(lzLPPerInvestPlanSchemaaddall);
    	}
    	map.put(lzLPPerInvestPlanSet, "DELETE&INSERT");
    }
    
    return true;
}

private boolean prepareOldData()
{
    LPPerInvestPlanSchema tempLPPerInvestPlanSchema = new
            LPPerInvestPlanSchema();
    tempLPPerInvestPlanSchema = mLPPerInvestPlanSet.get(1);
    LPPerInvestPlanDB tLPPerInvestPlanDB = new LPPerInvestPlanDB();
    tLPPerInvestPlanDB.setPolNo(tempLPPerInvestPlanSchema.getPolNo());
    tLPPerInvestPlanDB.setInsuAccNo(tempLPPerInvestPlanSchema.getInsuAccNo());
    tLPPerInvestPlanDB.setPayPlanCode(tempLPPerInvestPlanSchema.getPayPlanCode());
    LPPerInvestPlanSet tLPPerInvestPlanSet = tLPPerInvestPlanDB.query();
    //当没有查询到结果时，用CError　类在前台报错！
    if (tLPPerInvestPlanSet == null ||
        tLPPerInvestPlanSet.size() <= 0)
    {
        mErrors.copyAllErrors(tLPPerInvestPlanDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "TkBdDealBL";
        tError.functionName = "checkData";
        tError.errorMessage = "没有找到相应的数据,无法删除!";
        this.mErrors.addOneError(tError);
        return false;
    }

    
    SQLwithBindVariables sbv1=new SQLwithBindVariables();
    sbv1.sql("delete from LPPerInvestPlan where PolNo='?PolNo?' and PayPlanCode='?PayPlanCode?' and EdorNo = '?EdorNo?'");
    sbv1.put("PolNo", tempLPPerInvestPlanSchema.getPolNo());
    sbv1.put("PayPlanCode", tempLPPerInvestPlanSchema.getPayPlanCode());
    sbv1.put("EdorNo", mLPPerInvestPlanSet.get(1).getEdorNo());
    map.put(sbv1,"DELETE");

         /*   "' and InsuAccNo='" +
            tempLPPerInvestPlanSchema.getInsuAccNo() +
            "' and PayPlanCode='" + tempLPPerInvestPlanSchema.getPayPlanCode() +
            "'", "DELETE");*/
    return true;
}
 private boolean getOtherDate()
 {
        String GrpPolNo = "";
        String GrpContNo = "";
        String InsuredNo = "";
        String  RiskCode="";
        String  ContNo = "";

    // LPPerInvestPlanDB tLPPerInvestPlanDB=new LPPerInvestPlanDB();
        LCPolDB tLCPolDB=new LCPolDB();
        tLCPolDB.setPolNo(mLPPerInvestPlanSet.get(1).getPolNo());
        if(tLCPolDB.getInfo())
        {
                GrpPolNo=tLCPolDB.getGrpPolNo();
                GrpContNo=tLCPolDB.getGrpContNo();
                InsuredNo=tLCPolDB.getInsuredNo();
                 RiskCode=tLCPolDB.getRiskCode();
                 ContNo=tLCPolDB.getContNo();
        }else{
              return false;
        }
        for(int i=1;i<=mLPPerInvestPlanSet.size();i++)
        {
            mLPPerInvestPlanSet.get(i).setGrpContNo(GrpContNo);
            mLPPerInvestPlanSet.get(i).setGrpPolNo(GrpPolNo);
            mLPPerInvestPlanSet.get(i).setInsuredNo(InsuredNo);
            mLPPerInvestPlanSet.get(i).setRiskCode(RiskCode);
            mLPPerInvestPlanSet.get(i).setContNo(ContNo);
        }
          return true;
 }
  private boolean findIfIn(int index)
  {

      return true;
  }
private void buildError(String szFunc, String szErrMsg)
{
    CError cError = new CError();
    cError.moduleName = "PMAscriptionRuleBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
}
public VData getResult()
    {
        return mResult;
    }
}
