package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
public class PEdorPAConfirmBL implements EdorConfirm{
private static Logger logger = Logger.getLogger(PEdorPAConfirmBL.class);
        /** 错误处理类，每个需要错误处理的类中都放置该类 */
       public CErrors mErrors = new CErrors();
       /** 往后面传输数据的容器 */
       private VData mInputData;
       /** 往界面传输数据的容器 */
       private VData mResult = new VData();
       /** 数据操作字符串 */
       private String mOperate;
       /** 业务对象 */
       private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
       private LPPerInvestPlanSet mLPPerInvestPlanSet=new LPPerInvestPlanSet();
       private LCPerInvestPlanSet mLCPerInvestPlanSet=new LCPerInvestPlanSet();
       /** 全局数据 */
       private GlobalInput mGlobalInput = new GlobalInput();
       private MMap map = new MMap();
       //系统当前时间
       private String mCurrentDate = PubFun.getCurrentDate();
       private String mCurrentTime = PubFun.getCurrentTime();


    public PEdorPAConfirmBL() {
    }
    public boolean submitData(VData cInputData, String cOperate)
  {
      //将操作数据拷贝到本类中
      mInputData = (VData) cInputData.clone();
      mOperate = cOperate;

      //得到外部传入的数据,将数据备份到本类中
      if (!getInputData())
      {
          return false;
      }
      logger.debug("after getInputData....");

      //数据操作业务处理
      if (!dealData())
      {
          return false;
      }
      logger.debug("after dealData...");

      //准备提交后台的数据
      if (!prepareOutputData())
      {
          return false;
      }
      logger.debug("after prepareOutputData....");

      return true;
  }

  public VData getResult()
  {
      return mResult;
  }

  /**
   * 从输入数据中得到所有对象
   */
  private boolean getInputData()
  {
      try
      {

          mLPEdorItemSchema =
                  (LPEdorItemSchema)
                  mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
          //全局变量
          mGlobalInput =
                  (GlobalInput)
                  mInputData.getObjectByObjectName("GlobalInput", 0);
      }
      catch (Exception e)
      {
          e.printStackTrace();
          CError.buildErr(this, "接收前台数据失败!");
          return false;
      }
      if (mGlobalInput == null || mLPEdorItemSchema == null)
      {
          CError.buildErr(this, "传入数据有误!");
          return false;
      }

      return true;
  }

  /**
   * 根据前面准备的数据进行逻辑处理
   */
  private boolean dealData()
  {
	  Reflections tRef = new Reflections();
	  String SQL="select * from  LPPerInvestPlan where EdorNo='?EdorNo?' order by PolNo ";
	  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	  sqlbv.sql(SQL);
	  sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
	  LPPerInvestPlanDB tLPPerInvestPlanDB=new LPPerInvestPlanDB();
	  LPPerInvestPlanSet lzLPPerInvestPlanSet = new LPPerInvestPlanSet();
	  lzLPPerInvestPlanSet = tLPPerInvestPlanDB.executeQuery(sqlbv);
	  if(lzLPPerInvestPlanSet.size()>0)
	  {
		  for(int i = 1;i<=lzLPPerInvestPlanSet.size();i++)
		  {
			  LCPerInvestPlanDB lzLCPerInvestPlanDB = new LCPerInvestPlanDB();
			  lzLCPerInvestPlanDB.setPolNo(lzLPPerInvestPlanSet.get(i).getPolNo());
			  lzLCPerInvestPlanDB.setPayPlanCode(lzLPPerInvestPlanSet.get(i).getPayPlanCode());
			  lzLCPerInvestPlanDB.setInsuAccNo(lzLPPerInvestPlanSet.get(i).getInsuAccNo());
			  LCPerInvestPlanSchema lzLCPerInvestPlanSchema = new LCPerInvestPlanSchema();  
			  lzLCPerInvestPlanSchema.setSchema(lzLCPerInvestPlanDB.query().get(1));
			  double tempbili = lzLCPerInvestPlanSchema.getInvestRate();
			  lzLCPerInvestPlanSchema.setInvestRate(lzLPPerInvestPlanSet.get(i).getInvestRate());
			  lzLCPerInvestPlanSchema.setModifyDate(mCurrentDate);
			  lzLCPerInvestPlanSchema.setModifyTime(mCurrentTime);
			  mLCPerInvestPlanSet.add(lzLCPerInvestPlanSchema);
			  LPPerInvestPlanSchema lzLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
			  lzLPPerInvestPlanSchema.setSchema(lzLPPerInvestPlanSet.get(i));
			  lzLPPerInvestPlanSchema.setInvestRate(tempbili);
			  lzLPPerInvestPlanSchema.setModifyDate(mCurrentDate);
			  lzLPPerInvestPlanSchema.setModifyTime(mCurrentTime);
			  mLPPerInvestPlanSet.add(lzLPPerInvestPlanSchema);
		  }
		  map.put(mLCPerInvestPlanSet, "DELETE&INSERT");
		  map.put(mLPPerInvestPlanSet, "DELETE&INSERT");
		  mLPEdorItemSchema.setEdorValiDate(mCurrentDate);
		  map.put(mLPEdorItemSchema, "UPDATE");
		  
		  
//		处理后续处理接口表
	        LOPolAfterDealSchema mLOPolAfterDealSchema=new LOPolAfterDealSchema();
	        mLOPolAfterDealSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
	        mLOPolAfterDealSchema.setAccAlterType("3");
	        mLOPolAfterDealSchema.setBusyType(mLPEdorItemSchema.getEdorType());
	        mLOPolAfterDealSchema.setManageCom(mLPEdorItemSchema.getManageCom());
	        mLOPolAfterDealSchema.setState("0");
	        mLOPolAfterDealSchema.setContNo(mLPEdorItemSchema.getContNo());
	        mLOPolAfterDealSchema.setConfDate(mLPEdorItemSchema.getEdorValiDate());
	        
	        mLOPolAfterDealSchema.setMakeDate(mCurrentDate);
	        mLOPolAfterDealSchema.setMakeTime(mCurrentTime);
	        mLOPolAfterDealSchema.setModifyDate(mCurrentDate);
	        mLOPolAfterDealSchema.setModifyTime(mCurrentTime);
	        map.put(mLOPolAfterDealSchema, "INSERT");
		  
		  
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  ////////////////////////////鲁哲luzhe-20070910注释了重新写
//      Reflections tRef = new Reflections();
//      LPPerInvestPlanDB tLPPerInvestPlanDB=new LPPerInvestPlanDB();
//      LPPerInvestPlanSet tempLPPerInvestPlanSet=new LPPerInvestPlanSet();
//      String SQL="select * from  LPPerInvestPlan where EdorNo='"+mLPEdorItemSchema.getEdorNo()+"' order by PolNo ";
//      logger.debug(SQL);
//      tempLPPerInvestPlanSet=tLPPerInvestPlanDB.executeQuery(SQL);
//      for(int i=1;i<=tempLPPerInvestPlanSet.size();i++)
//      {
//           LCPerInvestPlanSchema tempLCPerInvestPlanSchema=new LCPerInvestPlanSchema();
//           tRef.transFields(tempLCPerInvestPlanSchema,tempLPPerInvestPlanSet.get(i));
//           mLCPerInvestPlanSet.add(tempLCPerInvestPlanSchema);
//      }
//
//        // tRef.transFields(mLCPerInvestPlanSet,tempLPPerInvestPlanSet);
//      LCPerInvestPlanDB ttLCPerInvestPlanDB=new LCPerInvestPlanDB();
//      LCPerInvestPlanSet ctempLCPerInvestPlanSet=new LCPerInvestPlanSet();
//      if(mLCPerInvestPlanSet.size()>0)
//      {
//         for(int j=1;j<=mLCPerInvestPlanSet.size();j++)
//         {
//             String tSQL = "select * from  LCPerInvestPlan where PolNo='" +
//                           mLCPerInvestPlanSet.get(1).getPolNo() + "'";
//             if(j>1){
//                 if( mLCPerInvestPlanSet.get(j).getPolNo().equals(mLCPerInvestPlanSet.get(j-1).getPolNo()))
//                 {
//                     continue;
//                 }else{
//                     tSQL = "select * from  LCPerInvestPlan where PolNo='" +
//                           mLCPerInvestPlanSet.get(j).getPolNo() + "'";
//                 }
//             }
//              LCPerInvestPlanSet tempLCPerInvestPlanSet=new LCPerInvestPlanSet();
//             tempLCPerInvestPlanSet = ttLCPerInvestPlanDB.executeQuery(tSQL);
//             for (int i = 1; i <= tempLCPerInvestPlanSet.size(); i++) {
//                 LPPerInvestPlanSchema tempLPPerInvestPlanSchema = new
//                         LPPerInvestPlanSchema();
//                 tRef.transFields(tempLPPerInvestPlanSchema,
//                                  tempLCPerInvestPlanSet.get(i));
//                 tempLPPerInvestPlanSchema.setEdorNo(mLPEdorItemSchema.
//                         getEdorNo());
//                 tempLPPerInvestPlanSchema.setEdorType(mLPEdorItemSchema.
//                         getEdorType());
//                 tempLPPerInvestPlanSchema.setModifyDate(mCurrentDate);
//                 tempLPPerInvestPlanSchema.setModifyTime(mCurrentTime);
//
//                 mLPPerInvestPlanSet.add(tempLPPerInvestPlanSchema);
//             }
//             ctempLCPerInvestPlanSet.add(tempLCPerInvestPlanSet);
//         }
//      }
//           map.put(ctempLCPerInvestPlanSet,"DELETE");
//            map.put(tempLPPerInvestPlanSet,"DELETE");
//           map.put(mLPPerInvestPlanSet, "DELETE&INSERT");
//           map.put(mLCPerInvestPlanSet, "DELETE&INSERT");
//


      return true;
  }

  /**
   * 准备提交后台的数据
   * @return: boolean
   */
  private boolean prepareOutputData()
  {
      try
      {
          mResult.clear();
          mResult.add(map);
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
          CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
          return false;
      }

      return true;
}

  public static void main(String[] args)
  {
    logger.debug("-------test...");
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

    tLPEdorItemSchema.setEdorAcceptNo("6120070705000017");
    tLPEdorItemSchema.setContNo("880000000269");
    tLPEdorItemSchema.setEdorNo("6020070705000029");
    tLPEdorItemSchema.setEdorType("PA");


    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.Operator = "001";
    tGlobalInput.ManageCom = "86";

    VData tVData = new VData();
    tVData.addElement(tLPEdorItemSchema);

    tVData.addElement(tGlobalInput);
    tVData.addElement("86110000");
    PEdorPAConfirmBL tPEdorPAConfirmBL = new PEdorPAConfirmBL();
    tPEdorPAConfirmBL.submitData(tVData, "");
  }

}
