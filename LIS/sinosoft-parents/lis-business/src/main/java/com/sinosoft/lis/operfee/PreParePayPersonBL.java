  package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.operfee.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HZM
 * @version 1.0
 */

public class PreParePayPersonBL  {
private static Logger logger = Logger.getLogger(PreParePayPersonBL.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors=new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 数据操作字符串 */
  private String mOperate;
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();

  //应收个人交费表
  private LJSPayPersonSet    mLJSPayPersonSet         = new LJSPayPersonSet();
  private LJSPayPersonSet    mLJSPayPersonInSertSet   = new LJSPayPersonSet();
  private LJSPayPersonSet    mLJSPayPersonUpdateSet   = new LJSPayPersonSet();

  //业务处理相关变量
  public PreParePayPersonBL() {
  }
  public static void main(String[] args) {

  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
    this.mOperate =cOperate;

    if (!getInputData(cInputData))
      return false;

    if (!dealData())
      return false;

    if (!prepareOutputData())
      return false;

    PreParePayPersonBLS tPreParePayPersonBLS=new PreParePayPersonBLS();
    tPreParePayPersonBLS.submitData(mInputData,cOperate);

    //如果有需要处理的错误，则返回
    if (tPreParePayPersonBLS.mErrors .needDealError())
    {
        this.mErrors .copyAllErrors(tPreParePayPersonBLS.mErrors ) ;
    }

    mInputData=null;
    return true;
  }

  //根据前面的输入数据，进行逻辑处理
  //如果在处理过程中出错，则返回false,否则返回true
  private boolean dealData()
  {
   boolean tReturn =false;
   if(this.mOperate.equals("INSERT"))
   { //查询应收个人表:没有该纪录，则添加一条；有则更新这条纪录
    LJSPayPersonBL tLJSPayPersonBL;
    LJSPayPersonSet tempLJSPayPersonSet = new LJSPayPersonSet();
    int iMax=mLJSPayPersonSet.size() ;
    String sqlStr="";
    String PolNo="";
    String DutyCode="";
    String PayPlanCode="";
    String PayAimClass="";
    for (int i=1;i<=iMax;i++)
    {
      tLJSPayPersonBL=new LJSPayPersonBL();
      tempLJSPayPersonSet = new LJSPayPersonSet();
      tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(i).getSchema());

      PolNo=tLJSPayPersonBL.getPolNo();
      DutyCode=tLJSPayPersonBL.getDutyCode();
      PayPlanCode=tLJSPayPersonBL.getPayPlanCode();
      PayAimClass=tLJSPayPersonBL.getPayAimClass();

      LCPremDB tLCPremDB=new LCPremDB();
      tLCPremDB.setPolNo(PolNo);
      tLCPremDB.setDutyCode(DutyCode);
      tLCPremDB.setPayPlanCode(PayPlanCode);
      if(tLCPremDB.getInfo()==false)
      {
    	  CError.buildErr(this, "没有找到对应的保费项，请您确认!");
          return false;
      }

      LCPolDB tLCPolDB=new LCPolDB();
      tLCPolDB.setPolNo(PolNo);
      if(tLCPolDB.getInfo()==false)
      {
    	  CError.buildErr(this, "没有找到对应的保单表，请您确认!");
          return false;
      }
      LCContDB tLCContDB = new LCContDB();
      tLCContDB.setContNo(tLCPolDB.getContNo());
      if(tLCContDB.getInfo()==false){
    	  CError.buildErr(this, "查询LCCont表失败");
    	  return false;
      }
      
      String tPayType="";
      LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
      tLCGrpPolDB.setGrpContNo(tLCContDB.getGrpContNo());
      LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
      if(tLCGrpPolSet.size()>0)
      {
      	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
      	tLMRiskAppDB.setRiskCode(tLCGrpPolSet.get(1).getRiskCode());
      	if(tLMRiskAppDB.getInfo())
      	{
      		if("1".equals(tLMRiskAppDB.getHealthType()))
      		{
      			tPayType="TM";
      		}
      		else
      		{
      			tPayType="ZC";
      		}
      	}
      	else
      	{
      		CError.buildErr(this, "查询LMRiskApp表失败");
      		return false;
      	}
      }
      else
      {
    	  CError.buildErr(this, "查询LCGrpPol表失败");
    	  return false;
      }

      tLJSPayPersonBL.setPayType(tPayType);
      tLJSPayPersonBL.setPayCount(tLCPremDB.getPayTimes()+1);
      tLJSPayPersonBL.setPayIntv(tLCPremDB.getPayIntv());
      tLJSPayPersonBL.setGrpContNo(tLCContDB.getGrpContNo());
      tLJSPayPersonBL.setContNo(tLCContDB.getContNo());
      tLJSPayPersonBL.setPayDate(CurrentDate);
      tLJSPayPersonBL.setLastPayToDate(tLCPremDB.getPaytoDate());
      tLJSPayPersonBL.setCurPayToDate(CurrentDate);
      tLJSPayPersonBL.setAppntNo(tLCPremDB.getAppntNo());
      tLJSPayPersonBL.setBankAccNo(tLCContDB.getBankAccNo());
      tLJSPayPersonBL.setBankCode(tLCContDB.getBankCode());
      tLJSPayPersonBL.setApproveCode(tLCPolDB.getApproveCode());
      tLJSPayPersonBL.setApproveDate(tLCPolDB.getApproveDate());
      tLJSPayPersonBL.setRiskCode(tLCPolDB.getRiskCode());
      tLJSPayPersonBL.setManageCom(tLCPolDB.getManageCom());
      tLJSPayPersonBL.setAgentCode(tLCPolDB.getAgentCode());
      tLJSPayPersonBL.setAgentGroup(tLCPolDB.getAgentGroup());
      tLJSPayPersonBL.setMakeDate(CurrentDate);
      tLJSPayPersonBL.setMakeTime(CurrentTime);
      tLJSPayPersonBL.setModifyDate(CurrentDate);
      tLJSPayPersonBL.setModifyTime(CurrentTime);

      LJSPayPersonDB tLJSPayPersonDB   = new LJSPayPersonDB();
      sqlStr = "select * from LJSPayPerson where PolNo='?PolNo?'";
      sqlStr = sqlStr+"and DutyCode='?DutyCode?'";
      sqlStr = sqlStr+"and PayPlanCode='?PayPlanCode?'";
      sqlStr = sqlStr+"and PayAimClass='?PayAimClass?'";
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(sqlStr);
      sqlbv.put("PolNo", PolNo);
      sqlbv.put("DutyCode", DutyCode);
      sqlbv.put("PayPlanCode", PayPlanCode);
      sqlbv.put("PayAimClass", PayAimClass);

      tempLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv);
      if(tempLJSPayPersonSet.size()==0)
      {
        mLJSPayPersonInSertSet.add(tLJSPayPersonBL);
      }
      else
      {
      	mLJSPayPersonUpdateSet.add(tLJSPayPersonBL);
      }
      tReturn=true;
     }
 }
  return tReturn;
}

 /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData mInputData)
  {
    // 应收个人纪录集合
    mLJSPayPersonSet.set((LJSPayPersonSet)mInputData.getObjectByObjectName("LJSPayPersonSet",0));
    if(mLJSPayPersonSet ==null)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="PreParePayPersonBL";
      tError.functionName="getInputData";
      tError.errorMessage="没有得到足够的数据，请您确认!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }

  //准备往后层输出所需要的数据
  //输出：如果准备数据时发生错误则返回false,否则返回true
  private boolean prepareOutputData()
  {
    mInputData=new VData();
    try
    {
    //注意：类型一致，但是序号不同.0,1
    mInputData.add(mLJSPayPersonInSertSet);  //添加应收个人交费表
    mInputData.add(mLJSPayPersonUpdateSet);  //更新应收个人交费表

logger.debug("prepareOutputData:");
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="PreParePayPersonBL";
      tError.functionName="prepareData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }
}

