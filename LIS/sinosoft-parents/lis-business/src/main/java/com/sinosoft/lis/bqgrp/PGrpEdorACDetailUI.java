package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:被保险人资料变更功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PGrpEdorACDetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorACDetailUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PGrpEdorACDetailUI() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;
    PGrpEdorACDetailBL tPGrpEdorACDetailBL = new PGrpEdorACDetailBL();
    logger.debug("---UI BEGIN---"+mOperate);
    if (tPGrpEdorACDetailBL.submitData(cInputData,mOperate) == false)
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tPGrpEdorACDetailBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PGrpEdorACDetailUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据查询失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
      mResult = tPGrpEdorACDetailBL.getResult();
    //支持EJB改造
    mResult.clear();
    mResult.add("success");
    
    return true;
  }

  public VData getResult()
  {
    return mResult;
  }

  public static void main(String[] args)
  {
    LPGrpPolSchema tLPGrpPolSchema   = new LPGrpPolSchema();
    LPGrpEdorItemDB tLPGrpEdorItemDB   = new LPGrpEdorItemDB();
    PGrpEdorACDetailUI tPGrpEdorACDetailUI   = new PGrpEdorACDetailUI();
    LPGrpAppntSchema tLPGrpAppntSchema=new LPGrpAppntSchema();
  LPGrpSchema tLPGrpSchema=new LPGrpSchema();
  LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();


    CErrors tError = null;
    //后面要执行的动作：添加，修改

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result="";

    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    transact = "INSERT||EDORAC";
    logger.debug("------transact:"+transact);
    GlobalInput tG = new GlobalInput();
    logger.debug("------------------begin ui");
    tG.ManageCom="86110000";
    tG.Operator="001";
    tLPGrpEdorItemDB.setEdorAcceptNo("86110000000014");
    tLPGrpEdorItemDB.setEdorNo("410110000000014");
    tLPGrpEdorItemDB.setEdorType("AC");
    tLPGrpEdorItemDB.setGrpContNo("240110000000130");
    tLPGrpEdorItemDB.getInfo();
    //个人保单批改信息
    //团单投保人信息  LCGrpAppnt
        tLPGrpAppntSchema.setEdorNo("410110000000014");
        tLPGrpAppntSchema.setEdorType("AC");
        tLPGrpAppntSchema.setGrpContNo("240110000000130");     //集体投保单号码
            tLPGrpAppntSchema.setCustomerNo("0000005030");            //客户号码
            tLPGrpAppntSchema.setPrtNo("001227001");                 //印刷号码
            tLPGrpAppntSchema.setName("asdfasd");
            tLPGrpAppntSchema.setPostalAddress("");
            tLPGrpAppntSchema.setZipCode("");
            tLPGrpAppntSchema.setAddressNo("");
            tLPGrpAppntSchema.setPhone("");
            //团体客户信息  LPGrp
            tLPGrpSchema.setEdorNo("410110000000014");
        tLPGrpSchema.setEdorType("AC");
            tLPGrpSchema.setCustomerNo("0000005030");            //客户号码
            tLPGrpSchema.setGrpName("asdfasd");             //单位名称
            tLPGrpSchema.setGrpNature("01");         //单位性质
            tLPGrpSchema.setBusinessType("");   //行业类别
            tLPGrpSchema.setPeoples("");             //总人数
            tLPGrpSchema.setRgtMoney("");           //注册资本
            tLPGrpSchema.setAsset("");                 //资产总额
            tLPGrpSchema.setNetProfitRate(""); //净资产收益率
            tLPGrpSchema.setMainBussiness(""); //主营业务
            tLPGrpSchema.setCorporation("");     //法人
            tLPGrpSchema.setComAera("");             //机构分布区域
            tLPGrpSchema.setPhone("asdfasdf");             //总机
            tLPGrpSchema.setFax("sdf");             //传真
            tLPGrpSchema.setFoundDate("asdfdsf");             //成立时间
            //团体客户地址  LCGrpAddress
            tLPGrpAddressSchema.setEdorNo("410110000000014");
        tLPGrpAddressSchema.setEdorType("AC");
            tLPGrpAddressSchema.setCustomerNo("0000005030");            //客户号码
            tLPGrpAddressSchema.setAddressNo("");      //地址号码
            tLPGrpAddressSchema.setGrpAddress("");       //单位地址
            logger.debug("*******************"+("GrpAddress"));
            tLPGrpAddressSchema.setGrpZipCode("123123");       //单位邮编
            //保险联系人一
            tLPGrpAddressSchema.setLinkMan1("asdfasd");
            tLPGrpAddressSchema.setDepartment1("");
            tLPGrpAddressSchema.setHeadShip1("");
            tLPGrpAddressSchema.setPhone1("");
            tLPGrpAddressSchema.setE_Mail1("");
            tLPGrpAddressSchema.setFax1("");
            //保险联系人二
            tLPGrpAddressSchema.setLinkMan2("");
            tLPGrpAddressSchema.setDepartment2("");
            tLPGrpAddressSchema.setHeadShip2("");
            tLPGrpAddressSchema.setPhone2("");
            tLPGrpAddressSchema.setE_Mail2("");
            tLPGrpAddressSchema.setFax2("");



    // 准备传输数据 VData
    VData tVData = new VData();
    //保存个人保单信息(保全)
    tVData.addElement(tG);
    tVData.addElement(tLPGrpEdorItemDB.getSchema());
    tVData.addElement(tLPGrpAppntSchema);
    tVData.addElement(tLPGrpSchema);
    tVData.addElement(tLPGrpAddressSchema);
    tPGrpEdorACDetailUI.submitData(tVData,"INSERT||EDORAC");
    logger.debug("-------test...");
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
