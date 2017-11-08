package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

/**
 * <p>Title: Lis_New</p>
 *
 * <p>Description:应收保费凭证冲消 </p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: </p>
 *
 * @author Sinosoft
 * @version 1.0
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.util.*;
import java.text.*;

public class OtoFReverPremBL {
private static Logger logger = Logger.getLogger(OtoFReverPremBL.class);
  public OtoFReverPremBL() {
  }

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 数据操作字符串 */
  private String mOperate;
  private String mToday = "";
  private int mTime = 0;
  private String bDate = "";
  private String eDate = "";
  private String accountDate = ""; //界面传入的冲消日期
  private String tManageCom = ""; //提取机构

  private GlobalInput mGlobalInput = new GlobalInput();

//对表的定义
  OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
  LITranInfoSet mLITranInfoSet = new LITranInfoSet();
  LITranErrSet mLITranErrSet = new LITranErrSet();

  /**
    传输数据的公共方法
   */
  public boolean submitData(VData cInputData, String cOperate) {
    logger.debug("--- OtoFReceiPremBL begin ---");
    mInputData = (VData) cInputData.clone();
    this.mOperate = cOperate;

    try {

      if (!cOperate.equals("Reverse")) {
    	  CError.buildErr(this, "不支持的操作字符串");
        return false;
      }
      //避免getinputdata中的数据被冲掉
      // 得到外部传入的数据，将数据备份到本类中
      if (!getInputData(cInputData)) {
        return false;
      }

      FDate chgdate = new FDate();
      Date dbdate = chgdate.getDate(eDate);
      mToday = chgdate.getString(dbdate);


      if(!check())
      {
        CError.buildErr(this, "该机构该月没有待冲消的应收保费凭证");
        return false;
      }

      // 冲消的应收保费

      if (!getOffsetPrem()) {
        CError.buildErr(this, "冲消应收保费失败");
        return false;
      }
    }

    catch (Exception ex) {
      ex.printStackTrace();
      CError.buildErr(this, "发生异常");
      return false;
    }
    return true;
  }

  private boolean getOffsetPrem() {

    logger.debug("开始冲消应收保费");
    String dSql = "select * from ofinterface where transdate='" + "?transdate?" +"' and vouchertype='" + "?vouchertype?" + "' and segment2 like concat('" + "?like?" +"','%') "
                + "and (ReversedStatus is null or ReversedStatus='0') and voucherflag<>'NA' and voucherid<>'-1' order by BatchNo,MatchID,RecordID ";
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(dSql);
    sqlbv1.put("transdate", mToday);
    sqlbv1.put("vouchertype", mTime);
    sqlbv1.put("like", tManageCom);
    logger.debug("冲消应收保费dSql--------" + dSql);

    OFInterfaceDB dOFInterfaceDB = new OFInterfaceDB();
    OFInterfaceSet dOFInterfaceSet = dOFInterfaceDB.executeQuery(sqlbv1);
    OFInterfaceSet tOFInterfaceSet = new OFInterfaceSet();
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("Reason", bDate + "至" + eDate + "应收保费凭证冲销");
    tTransferData.setNameAndValue("AccountingDate", accountDate);

    String lastBatchNo = "";
    String lastMatchID = "";
    for (int i = 1; i <= dOFInterfaceSet.size(); i++) {
      OFInterfaceSchema dOFInterfaceSchema = new OFInterfaceSchema();
      dOFInterfaceSchema = dOFInterfaceSet.get(i);
      if (dOFInterfaceSchema.getBatchNo().equals(lastBatchNo) &&
          (String.valueOf(dOFInterfaceSchema.getMatchID()).equals(lastMatchID))) {
        continue;
      }
      else {
        OFInterfaceSchema tOFInterfaceSchema = new OFInterfaceSchema();
        tOFInterfaceSchema.setBatchNo(dOFInterfaceSchema.getBatchNo());
        tOFInterfaceSchema.setMatchID(dOFInterfaceSchema.getMatchID());
        tOFInterfaceSchema.setManageCom(dOFInterfaceSchema.getManageCom());
        tOFInterfaceSchema.setTransDate(dOFInterfaceSchema.getTransDate());
        tOFInterfaceSchema.setBussNo(dOFInterfaceSchema.getBussNo());
        tOFInterfaceSchema.setPolNo(dOFInterfaceSchema.getPolNo());
        tOFInterfaceSchema.setAccountingDate(dOFInterfaceSchema.getAccountingDate());
        tOFInterfaceSchema.setMatchID(dOFInterfaceSchema.getMatchID());
        tOFInterfaceSchema.setVoucherType(dOFInterfaceSchema.getVoucherType());
        tOFInterfaceSet.add(tOFInterfaceSchema);
        lastBatchNo = dOFInterfaceSchema.getBatchNo();
        lastMatchID = String.valueOf(dOFInterfaceSchema.getMatchID());
      }
    }

    VData dVData = new VData();
    dVData.add(mGlobalInput);
    dVData.add(tOFInterfaceSet);
    dVData.add(tTransferData);

    OtoFReverseUI tOtoFReverseUI = new OtoFReverseUI();

    if (!tOtoFReverseUI.submitData(dVData, "Reverse")) {
    	
    	CError.buildErr(this, "财务凭证冲销失败");
    	return false;
    }

    return true;
  }

  private boolean getInputData(VData cInputData) {
	  
    mGlobalInput.setSchema( (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
    TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
    bDate = (String) tTransferData.getValueByName("bDate"); //提取起始日期
    eDate = (String) tTransferData.getValueByName("eDate"); //提取终止日期
    accountDate = (String) tTransferData.getValueByName("accountDate"); //冲消日期
    Integer itemp = (Integer) tTransferData.getValueByName("itemp"); //凭证类别（应收保费15）
    mTime = itemp.intValue();
    tManageCom = (String) tTransferData.getValueByName("Managecom"); //管理机构

    if (bDate.equals("")) {
    	CError.buildErr(this, "没有起始日期!");
      	return false;
    }

    if (eDate.equals("")) {
    	CError.buildErr(this, "没有终止日期!");
    	return false;
    }

    if (mGlobalInput == null) {
    	CError.buildErr(this, "没有得到足够的信息!");
    	return false;
    }

    if (tManageCom == null) {
    	CError.buildErr(this, "没有提取机构的信息!");
    	return false;
    }

    return true;
  }

/*校验该月该机构下的应收保费是否已经冲消过*/
  private boolean check()
  {
    String sql = "select * from ofinterface where transdate='" + "?transdate1?" +"' and vouchertype='" + "?vouchertype1?" + "' and segment2 like concat('" + "?like1?" +"','%') "
              + "and (ReversedStatus is null or ReversedStatus='0') and voucherflag<>'NA' and voucherid<>'-1' order by BatchNo,MatchID,RecordID ";
    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    sqlbv2.sql(sql);
    sqlbv2.put("transdate1", mToday);
    sqlbv2.put("vouchertype1", mTime);
    sqlbv2.put("like1", tManageCom);
   logger.debug("冲消应收保费sql--------" + sql);
   OFInterfaceDB mOFInterfaceDB = new OFInterfaceDB();
   OFInterfaceSet mOFInterfaceSet = mOFInterfaceDB.executeQuery(sqlbv2);
   if(mOFInterfaceSet.size()<=0)
   {
	   CError.buildErr(this, "没有待冲消的凭证，请核实！");
	   return false;
   }

    return true;
  }

  public static void main(String[] args) {
    OtoFReverPremBL tOtoFReverPremBL = new OtoFReverPremBL();
    VData vData = new VData();
    GlobalInput tG = new GlobalInput();
    tG.Operator = "001";
    tG.ManageCom = "86";
    vData.addElement(tG);
    TransferData tTransferData = new TransferData();
    String bDate = "2008-05-26";
    String eDate = "2008-06-25";
    String accountDate = "2008-07-2";
    Integer itemp = new Integer(61);
    String Managecom = "8611";
    tTransferData.setNameAndValue("bDate", bDate);
    tTransferData.setNameAndValue("eDate", eDate);
    tTransferData.setNameAndValue("accountDate", accountDate);
    tTransferData.setNameAndValue("itemp", itemp);
    tTransferData.setNameAndValue("Managecom", Managecom);
    vData.addElement(tTransferData);

    tOtoFReverPremBL.submitData(vData, "Reverse");
  }

}
