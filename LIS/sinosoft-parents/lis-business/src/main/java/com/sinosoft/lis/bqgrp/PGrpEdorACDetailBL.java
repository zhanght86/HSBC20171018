package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.sql.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 承保暂交费业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PGrpEdorACDetailBL
{
private static Logger logger = Logger.getLogger(PGrpEdorACDetailBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  /**  */
//  private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
//  private LPAppntGrpSchema  mLPAppntGrpSchema = new LPAppntGrpSchema();
//  private LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
//  private LPGrpPolSet aLPGrpPolSet = new LPGrpPolSet();
//  private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();

  private LPGrpEdorItemSchema mLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
  private LPGrpContSchema mLPGrpContSchema=new LPGrpContSchema();
  private LPGrpAppntSchema mLPGrpAppntSchema=new LPGrpAppntSchema();
  private LPGrpSchema mLPGrpSchema=new LPGrpSchema();
  private LPGrpAddressSchema mLPGrpAddressSchema = new LPGrpAddressSchema();
  private LCGrpAddressSchema mLCGrpAddressSchema=new LCGrpAddressSchema();
  private TransferData mTransferData = new TransferData();
  String addrFlag="";
  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  MMap map=new MMap();
  Reflections tRef = new Reflections();
  private String tEdorAcceptNo="";
  private String tGetFlag="";
  private String tBankCode="";
  private String tBankAccNo="";
  boolean newaddrFlag=false;
  public PGrpEdorACDetailBL() {}

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */

  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    mInputData = (VData)cInputData.clone() ;
    this.mOperate=cOperate;

    //得到外部传入的数据,将数据备份到本类中
    getInputData(cInputData)  ;


    //数据查询业务处理(queryData())
    if (mOperate.equals("QUERY||MAIN")||mOperate.equals("QUERY||DETAIL"))
    {
      if(!queryData())
        return false;
      else
        return true;
    }

//数据校验操作（checkdata)
    if (!checkData())
      return false;
    //数据准备操作（preparedata())
    if(!prepareData() )
      return false;

    logger.debug("---oper"+cOperate);

    // 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
    if (mOperate.equals("INSERT||EDORAC"))
    {
      if (!insertData())
        return false;
      logger.debug("---insertData---");
    }
    if(mOperate.equals("UPDATE||EDORAC"))
    {
      if(!updateData())
        return false;
      logger.debug("---updateData---");
    }
    PubSubmit tPubSubmit = new PubSubmit();
        //tPubSubmit.submitData(mInputData, cOperate);
        if (tPubSubmit.submitData(mInputData, cOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            return false;
        }

        return true;
  }

  public VData getResult()
  {
    return mResult;
  }
  /**数据查询业务处理(queryData())
   *
   */
  private boolean queryData()
  {
//    if (mOperate.equals("QUERY||MAIN"))
//    {
//      LPGrpPolBL tLPGrpPolBL = new LPGrpPolBL();
//      LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
//      tLPGrpPolBL.setSchema(mLPGrpPolSchema);
//
//      tLPGrpPolBL.queryLPGrpPol(mLPGrpEdorMainSchema);
//
//      String tReturn;
//      tReturn = tLPGrpPolBL.getSchema().encode();
//      tReturn = "0|"+ 1 +"^"+tReturn;
//
//      mInputData.clear();
//      mInputData.add(tLPGrpPolSet);
//
//      mResult.clear();
//      mResult.add(tReturn);
//    }
//    else if (mOperate.equals("QUERY||DETAIL"))
//    {
//      LPAppntGrpSchema tLPAppntGrpSchema = new LPAppntGrpSchema();
//      LPAppntGrpDB tLPAppntGrpDB = new LPAppntGrpDB();
//      tLPAppntGrpDB.setSchema(mLPAppntGrpSchema);
//      if (!tLPAppntGrpDB.getInfo())
//      {
//        LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
//        tLCAppntGrpDB.setPolNo(mLPAppntGrpSchema.getPolNo());
//        tLCAppntGrpDB.setGrpNo(mLPAppntGrpSchema.getGrpNo());
//        if (!tLCAppntGrpDB.getInfo())
//        {
//          // @@错误处理
//          CError tError = new CError();
//          tError.moduleName = "PGrpEdorACDetailBL";
//          tError.functionName = "QueryData";
//          tError.errorMessage = "明细查询失败!";
//          this.mErrors.addOneError(tError);
//          return false;
//        }
//        Reflections tRef = new Reflections();
//        tRef.transFields(tLPAppntGrpSchema,tLCAppntGrpDB.getSchema());
//      }
//      else
//        tLPAppntGrpSchema = tLPAppntGrpDB.getSchema();
//
//      String tReturn = tLPAppntGrpSchema.encode();
//      mInputData.clear();
//      mInputData.add(tLPAppntGrpSchema);
//
//      mResult.clear();
//      mResult.add(tReturn);
//    }
//    else
//    {
//    }
    return true;
  }


  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean insertData()
  {
      map.put(mLPGrpContSchema, "DELETE&INSERT");
      map.put(mLPGrpAppntSchema, "DELETE&INSERT");
      map.put(mLPGrpSchema, "DELETE&INSERT");
      map.put("update LPGrpEdorItem set EdorState='1' where EdorAcceptNo='"+tEdorAcceptNo+"'","UPDATE");
      if ("MOD".equals(addrFlag)||"NEW".equals(addrFlag))
          map.put(mLPGrpAddressSchema, "DELETE&INSERT");
          logger.debug(mLPGrpAddressSchema.encode());

      mInputData.clear();
      mInputData.add(map);

      mResult.clear();
      mResult.add(mLPGrpAddressSchema);


      return true;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private void getInputData(VData cInputData)
  {
      addrFlag=(String)cInputData.get(0);
      mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData.
                             getObjectByObjectName("LPGrpEdorItemSchema", 0);
      mLPGrpAppntSchema = (LPGrpAppntSchema) cInputData.getObjectByObjectName(
              "LPGrpAppntSchema", 0);
      mLPGrpSchema = (LPGrpSchema) cInputData.getObjectByObjectName(
              "LPGrpSchema", 0);

      mLPGrpAddressSchema = (LPGrpAddressSchema) cInputData.
                            getObjectByObjectName("LPGrpAddressSchema", 0);

      mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
              "GlobalInput", 0);
      mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
      tGetFlag=String.valueOf(mTransferData.getValueByName("GetFlag"));
      tBankCode=String.valueOf(mTransferData.getValueByName("BankCode"));
      tBankAccNo=String.valueOf(mTransferData.getValueByName("BankAccNo"));
  }

  /**
   * 更新集体保单保全信息
   * @return
   */
  private boolean updateData()
  {
//    LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
//    tLPGrpPolDB.setSchema(mLPGrpPolSchema);
//
//    Connection conn = DBConnPool.getConnection();
//
//    try {
//      conn.setAutoCommit(false);
//      //数据提交
//      LPGrpPolDBSet tLPGrpPolDBSet = new LPGrpPolDBSet(conn);//yangzhao
//      tLPGrpPolDBSet.add(mLPGrpPolSet);//yangzhao
//
//      //数据提交
//      if (!tLPGrpPolDBSet.update())  //yangzhao/11-27 DB->DBSet
//      {
//        // @@错误处理
//        this.mErrors.copyAllErrors(tLPGrpPolDBSet.mErrors);
//        CError tError = new CError();
//        tError.moduleName = "tLPAppntIndDB";
//        tError.functionName = "insertData";
//        tError.errorMessage = "数据提交失败!";
//        this.mErrors .addOneError(tError) ;
//
//        conn.rollback();
//        conn.close();
//        return false;
//      }
//
//      conn.commit();
//    }
//    catch (Exception ex) {
//      try {
//        conn.rollback() ;
//        conn.close();
//      } catch(Exception e) {}
//    }

    return true;
  }

  /**
   * 校验传入的数据的合法性
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean checkData()
  {

    boolean flag = true;
    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
    if (!tLPGrpEdorItemDB.getInfo())
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PAppntGrpBL";
      tError.functionName = "Preparedata";
      tError.errorMessage = "无保全项目数据!";
      logger.debug("------"+tError);
      this.mErrors.addOneError(tError);
      return false;
    }
    logger.debug("***EdorState***"+tLPGrpEdorItemDB.getEdorState().trim());
    if (tLPGrpEdorItemDB.getEdorState().trim().equals("2"))
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PAppntGrpBL";
      tError.functionName = "Preparedata";
      tError.errorMessage = "该保全项目已经申请确认不能修改!";
      this.mErrors.addOneError(tError);
      return false;
    }
    //如果地址号不为空，需要校验该地址号是否为本次保全申请产生，如果不是本次保全申请产生，则不让修改
    tEdorAcceptNo=tLPGrpEdorItemDB.getEdorAcceptNo();
    return flag;

  }

  /**
   * 准备需要保存的数据
   */
  private boolean prepareData()
  {

    //准备团体投保人信息
    LPGrpAppntBL tLPGrpAppntBL = new LPGrpAppntBL();
    if (!tLPGrpAppntBL.queryLPGrpAppnt(mLPGrpEdorItemSchema))
    {
        CError.buildErr(this,"查询投保人信息失败！");
        return false;

    }
    tLPGrpAppntBL.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    tLPGrpAppntBL.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpAppntBL.setEdorType(mLPGrpEdorItemSchema.getEdorType());

    tLPGrpAppntBL.setEdorNo(mLPGrpAppntSchema.getEdorNo());
    tLPGrpAppntBL.setEdorType(mLPGrpAppntSchema.getEdorType());
//    tLPGrpAppntBL.setGrpContNo(mLPGrpAppntSchema.getGrpContNo()); //集体投保单号码
    tLPGrpAppntBL.setCustomerNo(mLPGrpAppntSchema.getCustomerNo()); //客户号码
//    tLPGrpAppntBL.setPrtNo(mLPGrpAppntSchema.getPrtNo()); //印刷号码
    tLPGrpAppntBL.setName(mLPGrpAppntSchema.getName());
    tLPGrpAppntBL.setPostalAddress(mLPGrpAppntSchema.getPostalAddress());
    tLPGrpAppntBL.setZipCode(mLPGrpAppntSchema.getZipCode());
    tLPGrpAppntBL.setAddressNo(mLPGrpAppntSchema.getAddressNo());
    tLPGrpAppntBL.setPhone(mLPGrpAppntSchema.getPhone());


//    LPGrpContBL tLPGrpContBL = new LPGrpContBL();
//    if (!tLPGrpContBL.queryLPGrpCont(mLPGrpEdorItemSchema))
//    {
//        CError.buildErr(this,"查询投保人信息失败！");
//        return false;
//    }
    LCGrpContDB tLCGrpContDB=new LCGrpContDB();
    tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    LCGrpContSchema tLCGrpContSchema=tLCGrpContDB.query().get(1);
    LPGrpContSchema tLPGrpContSchema=new LPGrpContSchema();
    tRef.transFields(tLPGrpContSchema,tLCGrpContSchema);
    tLPGrpContSchema.setEdorNo(mLPGrpSchema.getEdorNo());
    tLPGrpContSchema.setEdorType(mLPGrpSchema.getEdorType());
    tLPGrpContSchema.setAppntNo(mLPGrpSchema.getCustomerNo()); //客户号码
    tLPGrpContSchema.setGrpName(mLPGrpSchema.getGrpName()); //单位名称
    tLPGrpContSchema.setGrpNature(mLPGrpSchema.getGrpNature()); //单位性质
    tLPGrpContSchema.setBusinessType(mLPGrpSchema.getBusinessType()); //行业类别
    tLPGrpContSchema.setPeoples(mLPGrpSchema.getPeoples()); //总人数
    tLPGrpContSchema.setRgtMoney(mLPGrpSchema.getRgtMoney()); //注册资本
    tLPGrpContSchema.setAsset(mLPGrpSchema.getAsset()); //资产总额
    tLPGrpContSchema.setNetProfitRate(mLPGrpSchema.getNetProfitRate()); //净资产收益率
    tLPGrpContSchema.setMainBussiness(mLPGrpSchema.getMainBussiness()); //主营业务
    tLPGrpContSchema.setCorporation(mLPGrpSchema.getCorporation()); //法人
    tLPGrpContSchema.setComAera(mLPGrpSchema.getComAera()); //机构分布区域
    tLPGrpContSchema.setPhone(mLPGrpSchema.getPhone()); //总机
    tLPGrpContSchema.setFax(mLPGrpSchema.getFax()); //传真
    tLPGrpContSchema.setFoundDate(mLPGrpSchema.getFoundDate()); //成立时间
    tLPGrpContSchema.setGetFlag(tGetFlag);
    tLPGrpContSchema.setBankCode(tBankCode);
    tLPGrpContSchema.setBankAccNo(tBankAccNo);
    if (!StrTool.compareString(mLPGrpAddressSchema.getAddressNo(),""))
    {
	tLPGrpContSchema.setAddressNo(mLPGrpAddressSchema.getAddressNo());
    }


    //准备团体客户信息
    LPGrpBL tLPGrpBL = new LPGrpBL();
    if (!tLPGrpBL.queryLPGrp(mLPGrpSchema))
    {
        CError.buildErr(this,"查询投团体客户信息！");
        return false;

    }

    tLPGrpBL.setEdorNo(mLPGrpSchema.getEdorNo());
    tLPGrpBL.setEdorType(mLPGrpSchema.getEdorType());
    tLPGrpBL.setCustomerNo(mLPGrpSchema.getCustomerNo()); //客户号码
    tLPGrpBL.setGrpName(mLPGrpSchema.getGrpName()); //单位名称
    tLPGrpBL.setGrpNature(mLPGrpSchema.getGrpNature()); //单位性质
    tLPGrpBL.setBusinessType(mLPGrpSchema.getBusinessType()); //行业类别
    tLPGrpBL.setPeoples(mLPGrpSchema.getPeoples()); //总人数
    tLPGrpBL.setRgtMoney(mLPGrpSchema.getRgtMoney()); //注册资本
    tLPGrpBL.setAsset(mLPGrpSchema.getAsset()); //资产总额
    tLPGrpBL.setNetProfitRate(mLPGrpSchema.getNetProfitRate()); //净资产收益率
    tLPGrpBL.setMainBussiness(mLPGrpSchema.getMainBussiness()); //主营业务
    tLPGrpBL.setCorporation(mLPGrpSchema.getCorporation()); //法人
    tLPGrpBL.setComAera(mLPGrpSchema.getComAera()); //机构分布区域
    tLPGrpBL.setPhone(mLPGrpSchema.getPhone()); //总机
    tLPGrpBL.setFax(mLPGrpSchema.getFax()); //传真
    tLPGrpBL.setFoundDate(mLPGrpSchema.getFoundDate()); //成立时间
     //团体客户地址  LPGrpAddress
     LPGrpAddressBL tLPGrpAddressBL = new LPGrpAddressBL();
     //如果地址号不为空
     if (!StrTool.compareString(mLPGrpAddressSchema.getAddressNo(),""))
     {
         if (!tLPGrpAddressBL.queryLPGrp(mLPGrpAddressSchema))
         {
             CError.buildErr(this, "查询投团体客户信息！");
             return false;
         }
     }
     else if ("MOD".equals(addrFlag))
     {
         newaddrFlag=true;
         SSRS tSSRS = new SSRS();

         String sql =
                 " select max(AddressNo) from LPGrpAddress where CustomerNo='"
                 + mLPGrpAddressSchema.getCustomerNo() + "' and edorno='"
                 + mLPGrpAddressSchema.getEdorNo() + "'";
         ExeSQL tExeSQL = new ExeSQL();
         tSSRS = tExeSQL.execSQL(sql);

         if (!tExeSQL.mErrors.needDealError() &&StrTool.compareString(tSSRS.GetText(1, 1),""))
         {
             sql = " select max(AddressNo) from LPGrpAddress a,LPGrpEdorItem b where a.CustomerNo='"
                   + mLPGrpAddressSchema.getCustomerNo() +
                   "' and a.edorno=b.edorno" +
                   " and b.EdorState='2'";
             tSSRS = tExeSQL.execSQL(sql);
             if (!tExeSQL.mErrors.needDealError() &&StrTool.compareString(tSSRS.GetText(1, 1),""))
             {
                 sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from LCGrpAddress where CustomerNo='"
                       + mLPGrpAddressSchema.getCustomerNo() +
                       "'";
                 tSSRS = tExeSQL.execSQL(sql);
                 if (!tExeSQL.mErrors.needDealError() && StrTool.compareString(tSSRS.GetText(1, 1),""))
                 {
                     CError.buildErr(this, "查询客户地址失败");
                     return false;
                 }
                 else if (tExeSQL.mErrors.needDealError())
                 {
                     CError.buildErr(this, "查询客户地址失败");
                     return false;
                 }

             }
             else if (tExeSQL.mErrors.needDealError())
             {
                 CError.buildErr(this, "查询客户地址失败");
                 return false;
             }

         }
         else if (tExeSQL.mErrors.needDealError())
         {
             CError.buildErr(this, "查询客户地址失败");
             return false;
         }

         Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
         int ttNo = firstinteger.intValue() + 1;
         Integer integer = new Integer(ttNo);
         String AddressNo = integer.toString();
         logger.debug("得到的地址码是：" + AddressNo);
         if (!AddressNo.equals(""))
         {
             tLPGrpAddressBL.setAddressNo(AddressNo);
             tLPGrpAppntBL.setAddressNo(AddressNo);
             tLPGrpContSchema.setAddressNo(AddressNo);
         }
         else
         {
             mErrors.addOneError(new CError("客户地址号码生成失败"));
             return false;
         }

     }
     if ("MOD".equals(addrFlag)||"NEW".equals(addrFlag))
     {
         tLPGrpAddressBL.setEdorNo(mLPGrpAddressSchema.getEdorNo());
         tLPGrpAddressBL.setEdorType(mLPGrpAddressSchema.getEdorType());
         tLPGrpAddressBL.setCustomerNo(mLPGrpAddressSchema.getCustomerNo()); //客户号码
//     tLPGrpAddressBL.setAddressNo(mLPGrpAddressSchema.getAddressNo()); //地址号码
         tLPGrpAddressBL.setGrpAddress(mLPGrpAddressSchema.getGrpAddress()); //单位地址
         tLPGrpAddressBL.setGrpZipCode(mLPGrpAddressSchema.getGrpZipCode()); //单位邮编
         //保险联系人一
         tLPGrpAddressBL.setLinkMan1(mLPGrpAddressSchema.getLinkMan1());
         tLPGrpAddressBL.setDepartment1(mLPGrpAddressSchema.getDepartment1());
         tLPGrpAddressBL.setHeadShip1(mLPGrpAddressSchema.getHeadShip1());
         tLPGrpAddressBL.setPhone1(mLPGrpAddressSchema.getPhone1());
         tLPGrpAddressBL.setE_Mail1(mLPGrpAddressSchema.getE_Mail1());
         tLPGrpAddressBL.setFax1(mLPGrpAddressSchema.getFax1());
         //保险联系人二
         tLPGrpAddressBL.setLinkMan2(mLPGrpAddressSchema.getLinkMan2());
         tLPGrpAddressBL.setDepartment2(mLPGrpAddressSchema.getDepartment2());
         tLPGrpAddressBL.setHeadShip2(mLPGrpAddressSchema.getHeadShip2());
         tLPGrpAddressBL.setPhone2(mLPGrpAddressSchema.getPhone2());
         tLPGrpAddressBL.setE_Mail2(mLPGrpAddressSchema.getE_Mail2());
         tLPGrpAddressBL.setFax2(mLPGrpAddressSchema.getFax2());
     }
     tLPGrpAppntBL.setDefaultFields();
     tLPGrpAppntBL.setOperator(mGlobalInput.Operator);
     //tLPGrpContBL.setDefaultFields();
     tLPGrpContSchema.setOperator(mGlobalInput.Operator);
     tLPGrpBL.setDefaultFields();
     tLPGrpBL.setOperator(mGlobalInput.Operator);
     tLPGrpAddressBL.setDefaultFields();
     tLPGrpAddressBL.setOperator(mGlobalInput.Operator);
     mLPGrpContSchema.setSchema(tLPGrpContSchema);
     mLPGrpAppntSchema.setSchema(tLPGrpAppntBL);
     mLPGrpSchema.setSchema(tLPGrpBL);
     mLPGrpAddressSchema.setSchema(tLPGrpAddressBL);


    return true;

  }


}
