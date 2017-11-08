package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.get.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.text.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单查询业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class LFGetPayQueryBL
{
private static Logger logger = Logger.getLogger(LFGetPayQueryBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /**全局变量*/
   GlobalInput mGlobalInput = new GlobalInput();
  /** 业务处理相关变量 */
  /** 保单 */
  private LCPolSchema mLCPolSchema = new LCPolSchema();
  private LJSGetSet  mLJSGetSet = new LJSGetSet();
  private LCPolSet mLCPolSet=new LCPolSet();
  private LJSGetSchema mLJSGetSchema=new LJSGetSchema();
  private LJSGetDrawSet mLJSGetDrawSet=new LJSGetDrawSet();
  public LFGetPayQueryBL() {}

  public static void main(String[] args)
  {
    VData tVData = new VData();
    GlobalInput tG = new GlobalInput();
    LJSGetSchema tLJSGetSchema = new LJSGetSchema();
    tG.Operator="tjj";
    tG.ManageCom="001";
    tVData.addElement(tG);
    tVData.addElement(tLJSGetSchema);
    LFGetPayQueryBL tLFGetPayQueryBL = new LFGetPayQueryBL();
    tLFGetPayQueryBL.submitData(tVData,"QUERY||MAIN");
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
      return false;
    logger.debug("---getInputData-gyj--");

    //进行业务处理
    if (cOperate.equals("QUERY||MAIN"))
    {
      if (!queryData())
        return false;
      logger.debug("---queryData---");
    }

    return true;
  }

  public VData getResult()
  {
    return mResult;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    // 保单查询条件
    mLJSGetSchema.setSchema((LJSGetSchema)cInputData.getObjectByObjectName("LJSGetSchema",0));
    mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);

    if(mLCPolSchema == null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LFGetPayQueryBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "请输入查询条件!";
      this.mErrors.addOneError(tError) ;
      return false;
    }
    return true;
  }

  /**
   * 查询符合条件的保单
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean queryData()
  {

    LJSGetDB tLJSGetDB=new LJSGetDB();
    tLJSGetDB.setSchema(this.mLJSGetSchema);
    tLJSGetDB.setOtherNoType("2");
    mLJSGetSet=tLJSGetDB.query();
    logger.debug("========55=======");
    if (mLJSGetSet.size() == 0)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LFGetPayQuertBL";
      tError.functionName = "queryData";
      tError.errorMessage = "未找到相关数据!";
      mErrors.addOneError(tError);
      mLJSGetSet.clear();
      return false;
    }

    LCPolDB tLCPolDB=new LCPolDB();
    this.mLCPolSchema.setPolNo(this.mLJSGetSet.get(1).getOtherNo());
    tLCPolDB.setSchema(mLCPolSchema);
    mLCPolSet= tLCPolDB.query();
    if (mLCPolSet.size() == 0)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LFGetPayQuertBL";
      tError.functionName = "queryData";
      tError.errorMessage = "未找到相关数据!";
      this.mErrors.addOneError(tError);
      mLCPolSet.clear();
      return false;
    }
    LJSGetDrawSchema tLJSGetDrawSchema=new LJSGetDrawSchema();

    String aSql;

    aSql = "select * from LJSGetDraw where ManageCom like concat('?ManageCom?','%') and getdate <= now()";
    SQLwithBindVariables sqlbv=new SQLwithBindVariables();

    if (mLJSGetSchema.getGetNoticeNo()!=null&&!mLJSGetSchema.getGetNoticeNo().trim().equals("")){
      aSql = aSql + " and GetNoticeNo ='?GetNoticeNo?'";
      sqlbv.put("GetNoticeNo", mLJSGetSchema.getGetNoticeNo());
    }
    if (mLJSGetSchema.getOtherNo()!=null&&!mLJSGetSchema.getOtherNo().trim().equals("")){
      aSql = aSql + " and PolNo ='?PolNo?' order by GetNoticeNo";
      sqlbv.put("PolNo", mLJSGetSchema.getOtherNo());
    }
    sqlbv.sql(aSql);
    sqlbv.put("ManageCom", mGlobalInput.ManageCom);
    LJSGetDrawDB tLJSGetDrawDB=new LJSGetDrawDB();
    logger.debug("===aSql:"+aSql);
    mLJSGetDrawSet=tLJSGetDrawDB.executeQuery(sqlbv);

    if (mLJSGetDrawSet.size() == 0)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LFGetPayQuertBL";
      tError.functionName = "queryData";
      tError.errorMessage = "未找到相关数据!";
      this.mErrors.addOneError(tError);
      mLJSGetDrawSet.clear();
      return false;
    }
    double aSumGetMoney =0;
    if (mLJSGetDrawSet.size()>0)
    {
      for (int i=1;i<=mLJSGetDrawSet.size();i++)
      {
         aSumGetMoney = aSumGetMoney + mLJSGetDrawSet.get(i).getGetMoney();
      }
    }
    TransferData aTransferData = new TransferData();
    aTransferData.setNameAndValue("GetDate",PubFun.getCurrentDate());
    aTransferData.setNameAndValue("SumGetMoney",new DecimalFormat("0.00").format(aSumGetMoney));

    mResult.clear();
    mResult.add(this.mLCPolSet);
    mResult.add(this.mLJSGetSet);
    mResult.add(this.mLJSGetDrawSet);
    mResult.add(aTransferData);
    return true;
  }
  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData()
  {
    return true;
  }
}
