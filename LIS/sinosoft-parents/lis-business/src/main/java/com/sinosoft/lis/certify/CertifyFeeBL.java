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
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HST
 * @version 1.0
 */
public class CertifyFeeBL
{
private static Logger logger = Logger.getLogger(CertifyFeeBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  private LZCardFeeSet mLZCardFeeSet = new LZCardFeeSet();
  private String mOperate;//操作变量
  private String mOperateType = "";//操作类型
  private String mCertifyCode = "";//单证编码
  private String mCertifyName="";//单证名称
  private String ManageCom="";//管理机构
  private String Operator="";//操作员
  private String mUnit="";//单证单位
  private double mPrice=0.0;//单证定价
  private String DefineDate="";//单证定义日期
  public CertifyFeeBL() 
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
	 
	logger.debug("开始执行CertifyFeeBL.java"); 
	  
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
      if(!checkData())
      {
        return false;
      }
      
      if (!dealData())
      {
        return false;
      }

    //logger.debug("---dealData---");
    }
    if(cOperate.equals("QUERY"))
    {
      if(!queryData())
      {
        return false;
      }
    }
    
    if(cOperate.equals("RETURNDATA"))
    {
      if(!returnData())
        return false;
    }
    
    
    if(cOperate.equals("UPDATE"))
    { 
      if(!checkData())
      {
          return false;
      }
      
      
      if(!dealData())
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
    LZCardFeeSchema bLZCardFeeSchema = new LZCardFeeSchema();
    bLZCardFeeSchema=mLZCardFeeSet.get(1);
    mCertifyCode=bLZCardFeeSchema.getCertifyCode();
    logger.debug("CertfiyFeeBl获得的单证编码是"+mCertifyCode);
    //得到需要的当前日期和
    DefineDate=PubFun.getCurrentDate();
    logger.debug("获得的当前日期是"+DefineDate);
    bLZCardFeeSchema.setDefineDate(DefineDate);
    bLZCardFeeSchema.setOperator(Operator);
    bLZCardFeeSchema.setSchema(bLZCardFeeSchema);
    //将数据添加到VData中
    mInputData.add(mLZCardFeeSet);

    //数据提交
    CertifyFeeBLS tCertifyFeeBLS = new CertifyFeeBLS();
    logger.debug("Start CertifyFeeBLS Submit...");
    if (!tCertifyFeeBLS.submitData(mInputData,mOperate))
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tCertifyFeeBLS.mErrors);
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
   * 接收从CertifyFeeSave.jsp中传递的数据
   */

  private boolean getInputData(VData cInputData)
  {

    mOperateType = (String)cInputData.get(0);//接收操作类型
	logger.debug("所要执行的操作类型是*********"+mOperateType);
	Operator=(String)cInputData.get(1);
	logger.debug("CertfiyFeeBl获得的操作员是"+Operator);
	ManageCom=(String)cInputData.get(2);
	logger.debug("CertfiyFeeBl获得的管理机构是"+ManageCom);
    mLZCardFeeSet = ((LZCardFeeSet)cInputData.getObjectByObjectName("LZCardFeeSet",0));
    
    //从LZCardFeeSchema的Get方法中获得页面输入的单证编码，单证名称，单证定价，单证单位,登陆机构
    LZCardFeeSchema gLZCardFeeSchema = new LZCardFeeSchema();
    gLZCardFeeSchema=mLZCardFeeSet.get(1);
    mCertifyCode=gLZCardFeeSchema.getCertifyCode();
    logger.debug("CertfiyFeeBl获得的单证编码是"+mCertifyCode);
    mCertifyName=gLZCardFeeSchema.getCertifyName();
    logger.debug("CertfiyFeeBl获得的单证名称是"+mCertifyName);
    mUnit=gLZCardFeeSchema.getUnit();
    logger.debug("CertfiyFeeBl获得的单证单位是"+mUnit);
    mPrice=gLZCardFeeSchema.getPrice();
    logger.debug("CertfiyFeeBl获得的单证定价是"+mPrice);
    logger.debug("CertfiyFeeBl获得的登陆机构是"+gLZCardFeeSchema.getManageCom());
    
    return true;
  }

  /**
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean queryData()
  {
  	logger.debug("开始在BL层中执行queryData()");
    LZCardFeeSet tLZCardFeeSet = new LZCardFeeSet();
	LZCardFeeDB tLZCardFeeDB = new LZCardFeeDB();
    tLZCardFeeDB.setSchema(mLZCardFeeSet.get(1));
    
    //根据数据库的表名作为参数建立SQLString对象，组成所需SQL
    SQLString sqlObj = new SQLString("LZCardFee");
    //通过Schema对象组成Where：select * from LZCardFee where
    sqlObj.setSQL(5, tLZCardFeeDB.getSchema());
    //获得拼出开头的sql语句
    String strSQL = sqlObj.getSQL();
    String strSqlAppend = " 1=1 ";

    if( tLZCardFeeDB.getCertifyName() != null && !tLZCardFeeDB.getCertifyName().trim().equals("") )
    {
      strSqlAppend = " CertifyName LIKE concat(concat('%','?CertifyName?'),'%')";
    }

    strSqlAppend += "  ORDER BY CertifyCode ";

    if( strSQL.toUpperCase().indexOf("WHERE") != -1 ) 
    {
      strSQL += " AND " + strSqlAppend;
    } 
    else 
    {
      strSQL += " WHERE " + strSqlAppend;
    }
    try
    { 
	    logger.debug("LZCardFeeBL执行的SQL是"+strSQL);
	    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	    sqlbv.sql(strSQL);
	    sqlbv.put("CertifyName", tLZCardFeeDB.getCertifyName());
	    tLZCardFeeSet.set(tLZCardFeeDB.executeQuery(sqlbv));
		logger.debug("在bl中的查询的个数是"+tLZCardFeeSet.size());
		logger.debug(tLZCardFeeDB.mErrors.needDealError());
	    if (tLZCardFeeDB.mErrors.needDealError())
	    {
	      // @@错误处理
	      this.mErrors.copyAllErrors(tLZCardFeeDB.mErrors);
	      CError tError = new CError();
	      tError.moduleName = "LZCardFeeBL";
	      tError.functionName = "queryData";
	      tError.errorMessage = "单证查询失败！！！";
	      this.mErrors.addOneError(tError);
	      tLZCardFeeSet.clear();
	      return false;
	    }
	    
	    if (tLZCardFeeSet.size() == 0)
	    {
	      // @@错误处理
	      CError tError = new CError();
	      tError.moduleName = "LZCardFeeBL";
	      tError.functionName = "queryData";
	      tError.errorMessage = "没有该单证记录！！！";
	      this.mErrors.addOneError(tError);
	      tLZCardFeeSet.clear();
	      mResult.add(tLZCardFeeSet);
	      return false;
	    }
    
    }
    catch(Exception ex)
    {
    	ex.printStackTrace();
    	return false;
    }

    mResult.clear();
    mResult.add(tLZCardFeeSet);
    return true;
  }

  
  /**
   * 校验传入的单证是否已经存在
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean checkData()
  {

	LZCardFeeSchema tLZCardFeeSchema = new LZCardFeeSchema();
    //获得第一个Schema，也就是当前的Schema
	tLZCardFeeSchema.setSchema(mLZCardFeeSet.get(1));
	
    if(mOperateType.equals("INSERT"))
    {
      //建立DB，根据单证编码进行查询，看是否该单证编码已存在.
      LZCardFeeDB tLZCardFeeDB = new LZCardFeeDB();
      //将查询条件：单证编码赋给相应的setCertifyCode()方法
      tLZCardFeeDB.setCertifyCode(tLZCardFeeSchema.getCertifyCode());
      tLZCardFeeDB.setManageCom(tLZCardFeeSchema.getManageCom());
      //建立Set对象，进行查询判断操作
      LZCardFeeSet tLZCardFeeSet = new LZCardFeeSet();
      tLZCardFeeSet.set(tLZCardFeeDB.query());
      if(tLZCardFeeSet.size()>0)
      {
        this.mErrors.copyAllErrors(tLZCardFeeSchema.mErrors);
        CError tError = new CError();
        tError.moduleName = "CertityFeeBL";
        tError.functionName = "INSERT";
        tError.errorMessage = "该号码已经存在，不能进行新增操作！！！";
        this.mErrors.addOneError(tError);
        return false;
      }
    }
    
    if(mOperateType.equals("UPDATE"))
    {
    	logger.debug("修改信息：开始进行机构的校验");
    	logger.debug("登陆机构是"+ManageCom);
    	logger.debug("单证所属机构是"+tLZCardFeeSchema.getManageCom());
    	if(!ManageCom.equals(tLZCardFeeSchema.getManageCom()))
    	{
    	     this.mErrors.copyAllErrors(tLZCardFeeSchema.mErrors);
    	     CError tError = new CError();
    	     tError.moduleName = "CertityFeeBL";
    	     tError.functionName = "UPDATE";
    	     tError.errorMessage = "不能修改其他机构的单证的收费信息！！！";
    	     this.mErrors.addOneError(tError);
    	     return false;
    	}
    }
    
    return true;
  }

  //将查询到的数据返回到初始的界面上
  private boolean returnData()
  {
    LZCardFeeSchema tLZCardFeeSchema = new LZCardFeeSchema();
    tLZCardFeeSchema.setSchema(mLZCardFeeSet.get(1));
    LZCardFeeDB tLZCardFeeDB = new LZCardFeeDB();
    tLZCardFeeDB.setCertifyCode(tLZCardFeeSchema.getCertifyCode());
    tLZCardFeeDB.setManageCom(tLZCardFeeSchema.getManageCom());
    
    LZCardFeeSet yLZCardFeeSet = new LZCardFeeSet();

    yLZCardFeeSet.set(tLZCardFeeDB.query());
    mResult.clear();
    mResult.add(yLZCardFeeSet);
    return true;
  }


  public static void main(String[] args)
  {
    
  }
}
