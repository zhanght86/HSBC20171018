package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.sys.*;
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
 * <p>Description:单证收费操作类</p>
 * @author 张征
 * @version 1.0
 */
public class CertifyFeeOperateBL
{
private static Logger logger = Logger.getLogger(CertifyFeeOperateBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  private LZCardFeeOperSet mLZCardFeeOperSet = new LZCardFeeOperSet();
  private String mOperate;//操作变量
  private String mOperateType = "";//操作类型
  private String mSerialNo="";//单证操作的流水号，形成联合主键
  private String mCertifyCode = "";//单证编码
  private String mCertifyName="";//单证名称
  private int    mCount=0;//单证数量
  private String ManageCom="";//管理机构
  private String Operator="";//操作员
  private double mPayMoney=0.0;//缴费金额
  private String mPayDate="";//缴费日期
  private String mMakeDate="";//入机日期
  private String mMakeTime="";//入机时间
  
  public CertifyFeeOperateBL() {}

  /**
  * 传输数据的公共方法
  * @param: cInputData 输入的数据
  *         cOperate 数据操作
  * @return:
  */
  
  public boolean submitData(VData cInputData,String cOperate)
  {
	 
	logger.debug("开始执行CertifyFeeOperateBL.java"); 
	  
    //将操作数据拷贝到本类中
    mInputData = (VData)cInputData.clone() ;
    
    if (!getInputData(cInputData))
    {
      return false;
    }
    
    //将操作类型赋值给操作变量
    this.mOperate = mOperateType;
    
    // 数据操作业务处理
    if (cOperate.equals("INSERT"))
    {
      
      if (!dealData())
      {
        return false;
      }
    }

    return true;
  }

  public VData getResult()
  {
  	return mResult;
  }
  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  //添加数据：操作类型，管理机构，操作员
  private boolean dealData()
  {
    mInputData.clear();
    LZCardFeeOperSchema bLZCardFeeOperSchema = new LZCardFeeOperSchema();
    bLZCardFeeOperSchema=mLZCardFeeOperSet.get(1);
    mCertifyCode=bLZCardFeeOperSchema.getCertifyCode();
    logger.debug("CertfiyFeeOperateBl获得的单证编码是"+mCertifyCode);
    //获得流水号,入机时间，入机日期
    mMakeDate=PubFun.getCurrentDate();
    logger.debug("获得的入机日期是"+mMakeDate);
    mMakeTime=PubFun.getCurrentTime();
    logger.debug("获得的入机时间是"+mMakeTime);
	mSerialNo=PubFun1.CreateMaxNo("SerialNo",mCertifyCode);
	logger.debug("得到的流水号是"+mSerialNo);
	bLZCardFeeOperSchema.setSerialNo(mSerialNo);
    bLZCardFeeOperSchema.setMakeDate(mMakeDate);
    bLZCardFeeOperSchema.setManageCom(ManageCom);
    bLZCardFeeOperSchema.setOperator(Operator);
    bLZCardFeeOperSchema.setMakeTime(mMakeTime);
    //将取回的Schema放回到Set中
    bLZCardFeeOperSchema.setSchema(bLZCardFeeOperSchema);
    mInputData.add(mLZCardFeeOperSet);

    //数据提交
    logger.debug("开始把数据提交给CertifyFeeOperateBLS");
    CertifyFeeOperateBLS tCertifyFeeOperateBLS = new CertifyFeeOperateBLS();
    logger.debug("Start CertifyFeeOperateBLS Submit...");
    if (!tCertifyFeeOperateBLS.submitData(mInputData,mOperate))
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tCertifyFeeOperateBLS.mErrors);
      CError tError = new CError();
      tError.moduleName = "ReportBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }

  /****************************************************************************
   * 接收从CertifyFeeOperateSave.jsp中传递的数据
   */

  private boolean getInputData(VData cInputData)
  {
    mOperateType = (String)cInputData.get(0);//接收操作类型
	logger.debug("所要执行的操作类型是*********"+mOperateType);
	ManageCom=(String)cInputData.get(1);
	logger.debug("CertfiyFeeOperateBl获得的管理机构是"+ManageCom);
	Operator=(String)cInputData.get(2);
	logger.debug("CertfiyFeeOperateBl获得的操作员是"+Operator);
    mLZCardFeeOperSet = ((LZCardFeeOperSet)cInputData.getObjectByObjectName("LZCardFeeOperSet",0));
    
    //从LZCardFeeOperSchema的Get方法中获得页面输入的单证编码，单证名称，单证单证数量，缴费金额，缴费日期
    LZCardFeeOperSchema gLZCardFeeOperSchema = new LZCardFeeOperSchema();
    gLZCardFeeOperSchema=mLZCardFeeOperSet.get(1);
    
    mCertifyCode=gLZCardFeeOperSchema.getCertifyCode();
    logger.debug("CertfiyFeeOperateBl获得的单证编码是"+mCertifyCode);
    mCertifyName=gLZCardFeeOperSchema.getCertifyName();
    logger.debug("CertfiyFeeOperateBl获得的单证名称是"+mCertifyName);
    mCount=gLZCardFeeOperSchema.getCount();
    logger.debug("CertfiyFeeOperateBl获得的单证数量是"+mCount);
    mPayMoney=gLZCardFeeOperSchema.getPayMoney();
    logger.debug("CertfiyFeeOperateBl获得的交费金额是"+mPayMoney);
    mPayDate=gLZCardFeeOperSchema.getPayDate();
    logger.debug("CertfiyFeeOperateBl获得的交费日期是"+mPayDate);

    return true;
  }




  public static void main(String[] args)
  {
  }
}
