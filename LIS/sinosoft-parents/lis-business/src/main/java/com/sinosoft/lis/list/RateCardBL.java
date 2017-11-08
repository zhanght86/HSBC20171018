package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>自助卡单专用费率表维护</p>
 * @author zz
 * @version 2008-06-20
 */
public class RateCardBL
{
private static Logger logger = Logger.getLogger(RateCardBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  private RateCardSchema mRateCardSchema = new RateCardSchema();//全局Schema.存储的是界面传入的数据
  private RateCardSet tRateCardSet = new RateCardSet();//全局Schema.存储的是修改记录是需要删除记录的Set
  private TransferData tTransferData = new TransferData();
  private String hiddenRiskCode="";//隐藏的RiskCode,用于在修改操作时查询数据数据库中的老记录
  private String hiddenPrem="";//隐藏的Prem,用于在修改操作时查询数据数据库中的老记录
  private String mOperate;//操作类型标志
  private MMap map=new MMap();//提交数据库的Map

  public RateCardBL() 
  {
  	
  }

  /**
  * 传输数据的公共方法
  * @param: cInputData 输入的数据
  *         cOperate 数据操作
  * @return:
  */
  
  public boolean submitData(VData cInputData,String cOperate)
  {
	 
	logger.debug("Begin RateCardBL.java"); 
    
    //将操作类型赋值给操作变量
    this.mOperate = cOperate;
	  
    //将操作数据拷贝到本类中
    mInputData = (VData)cInputData.clone() ;
    
    if (!getInputData(cInputData))
    {
      return false;
    }
    
    // 数据操作业务处理
    if (mOperate.equals("INSERT")||mOperate.equals("UPDATE"))
    {
      if(!checkData())
      {
        return false;
      }
      
      if (!dealData())
      {
        return false;
      }
      
	  if (!prepareOutputData()) 
	  {
			return false;
	  }
      
	  logger.debug(" PubSubmit------");
	  PubSubmit tPubSubmit = new PubSubmit(); 
	  if (!tPubSubmit.submitData(mInputData))
	  {
			return false;
	  }

    }

    
    //查询返回
    if(cOperate.equals("RETURNDATA"))
    {
      if(!returnData())
        return false;
    }

    return true;
  }

  public VData getResult()
  {
  	return mResult;
  }
  
  
	/**
	 * 准备提交数据库的数据
	**/		
	private boolean prepareOutputData() 
	{
		mRateCardSchema.setMakeDate(PubFun.getCurrentDate());
		mRateCardSchema.setMakeTime(PubFun.getCurrentTime());
		mRateCardSchema.setModifyDate(PubFun.getCurrentDate());
		mRateCardSchema.setModifyTime(PubFun.getCurrentTime());
		
		mInputData = new VData();
		if(mOperate.equals("INSERT"))
		{
			map.put(mRateCardSchema,"DELETE&INSERT");         
		}
		else
		{	
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("update RateCard set RiskCode='?RiskCode?'," 
					+" Prem='?Prem?',Mult='?Mult?',"
					+" InsuYear='?InsuYear?',InsuYearFlag='?InsuYearFlag?',"
					+" ProductPlan='?ProductPlan?',ManagerCom='?ManagerCom?',"
					+" Operator='?Operator?',"
					+" MakeDate='?MakeDate?',"
					+" MakeTime='?MakeTime?',"
					+" ModifyDate='?ModifyDate?',"
					+" ModifyTime='?ModifyTime?'"
					+" where riskcode='?hiddenRiskCode?' and prem='?hiddenPrem?'");
			sqlbv.put("RiskCode", mRateCardSchema.getRiskCode());
			sqlbv.put("Prem", mRateCardSchema.getPrem());
			sqlbv.put("Mult", mRateCardSchema.getMult());
			sqlbv.put("InsuYear", mRateCardSchema.getInsuYear());
			sqlbv.put("InsuYearFlag", mRateCardSchema.getInsuYearFlag());
			sqlbv.put("ProductPlan", mRateCardSchema.getProductPlan());
			sqlbv.put("ManagerCom", mRateCardSchema.getManagerCom());
			sqlbv.put("Operator", mRateCardSchema.getOperator());
			sqlbv.put("MakeDate", mRateCardSchema.getMakeDate());
			sqlbv.put("MakeTime", mRateCardSchema.getMakeTime());
			sqlbv.put("ModifyDate", mRateCardSchema.getModifyDate());
			sqlbv.put("ModifyTime", mRateCardSchema.getModifyTime());
			sqlbv.put("hiddenRiskCode", hiddenRiskCode);
			sqlbv.put("hiddenPrem", hiddenPrem);
			map.put(sqlbv,"UPDATE"); 
		}
		                
		mInputData.add(map);
		
		return true;
	}
  
  
  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  //添加数据：操作类型，管理机构，操作员
  private boolean dealData()
  {
	  return true;
  }

  /****************************************************************************
   * 接收从CertifyFeeSave.jsp中传递的数据
   */

  private boolean getInputData(VData cInputData)
  {

	  mRateCardSchema = (RateCardSchema)cInputData.get(0);//接收传入的卡单费率表的数据
	  
	  if (mOperate.equals("INSERT")||mOperate.equals("UPDATE"))
	  {
		  tTransferData = (TransferData)cInputData.get(1);	  
		  hiddenRiskCode=(String)tTransferData.getValueByName("HiddenRiskCode");
		  hiddenPrem=(String)tTransferData.getValueByName("HiddenPrem");
	  }

    
    return true;
  }

  
  /**
   * 校验传入的单证是否已经存在
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean checkData()
  {

	  //建立DB，根据险种编码和保费进行查询，看该种产品形态是否已经存在.
	  logger.debug("要操作的险种是"+hiddenRiskCode);
	  logger.debug("要操作的保费是"+hiddenPrem);
	  RateCardDB tRateCardDB = new RateCardDB();
	  tRateCardDB.setRiskCode(hiddenRiskCode);
	  tRateCardDB.setPrem(hiddenPrem);
	  logger.debug("hiddenPrem="+hiddenPrem);
	     
	  //建立Set对象，进行查询判断操作
	  tRateCardSet.set(tRateCardDB.query());
	      
	  if(mOperate.equals("INSERT"))
	  {
		  if(tRateCardSet.size()>0)
		  {
		      this.mErrors.copyAllErrors(tRateCardDB.mErrors);
		      CError tError = new CError();
		      tError.moduleName = "RateCardBL";
		      tError.functionName = "INSERT";
		      tError.errorMessage = "数据库中已经存在相应记录";
		      this.mErrors.addOneError(tError);
		      return false;
		  }
	  }
    return true;
  }

  //将查询到的数据返回到初始的界面上
  private boolean returnData()
  {
	  RateCardDB tRateCardDB = new RateCardDB();
	  tRateCardDB.setRiskCode(mRateCardSchema.getRiskCode());
	  tRateCardDB.setPrem(mRateCardSchema.getPrem());
	     
	  //建立Set对象，进行查询判断操作
	  RateCardSet tRateCardSet = new RateCardSet();
	  tRateCardSet.set(tRateCardDB.query());

	  mResult.clear();
	  mResult.add(tRateCardSet);
	  return true;
  }


  public static void main(String[] args)
  {
    
  }
}
