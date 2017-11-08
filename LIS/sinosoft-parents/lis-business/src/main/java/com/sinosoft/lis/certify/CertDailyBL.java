/*
* <p>ClassName: CertDailyBL </p>
* <p>Description: 单证日结 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2003-08-11
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import java.sql.*;
import java.text.*;

public class CertDailyBL  {
private static Logger logger = Logger.getLogger(CertDailyBL.class);

  public  CErrors mErrors = new CErrors();
  private VData mResult = new VData();

  /** 全局数据 */
  private GlobalInput m_GlobalInput = new GlobalInput() ;

  /** 数据操作字符串 */
  private String m_strOperate;

  /** 业务处理相关变量 */
  private LZCardSchema m_LZCardSchema = new LZCardSchema();
  private Hashtable m_hashParams = null;

  public CertDailyBL() {
  }

  public static void main(String[] args) {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
    m_strOperate = verifyOperate(cOperate);
    if( m_strOperate.equals("") ) {
      buildError("submitData", "不支持的操作字符串");
      return false;
    }

    if (!getInputData(cInputData))
      return false;

    if (!dealData())
      return false;

//    VData tVData = new VData();
//    if( !prepareOutputData(tVData) )
//      return false;
//
    return true;
  }

  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    try {
      if( m_strOperate.equals("QUERY||MAIN") ) {
        return submitQueryMain();
      } else {
        buildError("dealData", "不支持的操作字符串");
        return false;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    try {
      this.m_GlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
      m_LZCardSchema = (LZCardSchema)cInputData.getObjectByObjectName("LZCardSchema", 0);
      m_hashParams = (Hashtable)cInputData.getObjectByObjectName("Hashtable", 0);

      if( m_LZCardSchema == null ) {
        buildError("getInputData", "没有传入所需要的查询条件");
        return false;
      }

    } catch(Exception ex) {
      ex.printStackTrace();
      buildError("getInputData", ex.getMessage());
      return false;
    }
    return true;
  }

  private boolean prepareOutputData(VData aVData)
  {
    return true;
  }

  public VData getResult()
  {
    return this.mResult;
  }

  /*
  * add by kevin, 2002-10-14
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "CertDailyBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
    String szReturn = "";
    String szOperates[] = {"QUERY||MAIN"};

    for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
      if( szOperate.equals(szOperates[nIndex]) ) {
        szReturn = szOperate;
      }
    }

    return szReturn;
  }

  /**
   * 初始化。对于所有的单证，将四个日结统计的数据都设为0。
   * @return
   */
  private boolean initResult(Hashtable hashResult) {
    if( hashResult == null ) {
      return false;
    }

    hashResult.clear();

    String strSQL = "SELECT * FROM LMCertifyDes"
                  + " WHERE CertifyClass='P' OR CertifyClass='D'"
                  + " ORDER BY CertifyCode";
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    sqlbv.sql(strSQL);
    LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
    LMCertifyDesSet tLMCertifyDesSet = tLMCertifyDesDB.executeQuery(sqlbv);

    String[] strArr = null;

    for(int nIndex = 0; nIndex < tLMCertifyDesSet.size(); nIndex ++) {
      strArr = new String[6];
      strArr[0] = tLMCertifyDesSet.get(nIndex + 1).getCertifyCode();
      strArr[1] = tLMCertifyDesSet.get(nIndex + 1).getCertifyName();
      strArr[2] = tLMCertifyDesSet.get(nIndex + 1).getUnit();
      strArr[3] = "0";  // 本日入库
      strArr[4] = "0";  // 本日发放
      strArr[5] = "0";  // 本日库存

      hashResult.put(tLMCertifyDesSet.get(nIndex + 1).getCertifyCode(),
                     strArr);
    }

    return true;
  }

  /**
   * 调用CertStatBL功能
   * @param hashResult
   * @param aLZCardSet
   * @return
   */
  private boolean callStatBL(Hashtable hashResult, LZCardSet aLZCardSet) {
    CertStatBL tCertStatBL = new CertStatBL();

    VData tVData = new VData();

    tVData.add(m_GlobalInput);
    tVData.add(aLZCardSet);
    tVData.add(new Hashtable());

    if( !tCertStatBL.submitData(tVData, "REPORT||QUERY") ) {
      buildError("callStatBL", tCertStatBL.mErrors.getFirstError());
      return false;
    }

    tVData = tCertStatBL.getResult();

    // 处理得到的数据
    int nPos = 0;
    LZCardSet tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);

    if( aLZCardSet.get(1).getState().equals( CertStatBL.STAT_GROSS ) ) {
      nPos = 3;
    } else if( aLZCardSet.get(1).getState().equals( CertStatBL.STAT_SENDED ) ) {
      nPos = 4;
    } else if( aLZCardSet.get(1).getState().equals( CertStatBL.STAT_STOCK ) ) {
      nPos = 5;
    }

    for(int nIndex = 0; nIndex < tLZCardSet.size(); nIndex ++) {
      LZCardSchema tLZCardSchema = tLZCardSet.get(nIndex + 1);
      String[] strArr = (String[])hashResult.get(tLZCardSchema.getCertifyCode());

      if( strArr == null ) {
        continue;
      }

      strArr[nPos] = String.valueOf(tLZCardSchema.getSumCount());
    }

    return true;
  }

  private boolean submitQueryMain() {
    Hashtable hashResult = new Hashtable();

    if( !initResult(hashResult) ) {
      buildError("submitQueryMain", "初始化查询结果集失败。");
      return false;
    }

    CertStatBL tCertStatBL = null;

    // 构造调用CertStat所需要的参数
    LZCardSchema tLZCardSchema1 = new LZCardSchema();
    LZCardSchema tLZCardSchema2 = new LZCardSchema();

    LZCardSet tLZCardSet = new LZCardSet();

    tLZCardSet.add(tLZCardSchema1);
    tLZCardSet.add(tLZCardSchema2);

    // 日期处理
    FDate fdate = new FDate();

    String strDate1 = m_LZCardSchema.getMakeDate();
    String strDate2 =
        strDate1 == null ?
        null : fdate.getString(PubFun.calDate(fdate.getDate(strDate1), 1, "D", null));

    // 查询A日库存
    // A日库存 = 当前库存 + A+1日至本日发放 - A+1日至本日入库

    // 查询当前库存
    tLZCardSchema1.setSendOutCom("");
    tLZCardSchema1.setReceiveCom("A" + m_LZCardSchema.getSendOutCom());
    tLZCardSchema1.setMakeDate("");
    tLZCardSchema1.setState(CertStatBL.STAT_STOCK);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
    tLZCardSchema2.setMakeDate("");

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询本日库存失败");
      return false;
    }

    // 查询A+1日至本日发放
    tLZCardSchema1.setSendOutCom("A" + m_LZCardSchema.getSendOutCom());
    tLZCardSchema1.setReceiveCom("");
    tLZCardSchema1.setMakeDate(strDate2);
    tLZCardSchema1.setState(CertStatBL.STAT_SENDED);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
    tLZCardSchema2.setMakeDate("");

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询A+1日至本日发放失败");
      return false;
    }

    // 查询A+1日至本日入库
    tLZCardSchema1.setSendOutCom("");
    tLZCardSchema1.setReceiveCom("A" + m_LZCardSchema.getSendOutCom());
    tLZCardSchema1.setMakeDate(strDate2);
    tLZCardSchema1.setState(CertStatBL.STAT_GROSS);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
    tLZCardSchema2.setMakeDate("");

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询A+1日至本日入库失败");
      return false;
    }

    for(Enumeration enum1 = hashResult.elements(); enum1.hasMoreElements();) {
      String[] strArr = (String[])enum1.nextElement();

      int nStock = Integer.parseInt(strArr[5]);  // A日库存
      int nSended = Integer.parseInt(strArr[4]);  // A日至本日发放
      int nGross = Integer.parseInt(strArr[3]);  // A日至本日入库

      strArr[5] = String.valueOf(nStock + nSended - nGross);
      strArr[4] = "0";
      strArr[3] = "0";
    }

    /* * * * * * * * * * * * * *
     * 查询A日库存完毕
     * * * * * * * * * * * * * */

    // 查询A日入库
    tLZCardSchema1.setSendOutCom("");
    tLZCardSchema1.setReceiveCom("A" + m_LZCardSchema.getSendOutCom());
    tLZCardSchema1.setMakeDate(strDate1);
    tLZCardSchema1.setState(CertStatBL.STAT_GROSS);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
    tLZCardSchema2.setMakeDate(strDate1);

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询本日入库失败");
      return false;
    }

    // 查询A日发放
    tLZCardSchema1.setSendOutCom("A" + m_LZCardSchema.getSendOutCom());
    tLZCardSchema1.setReceiveCom("");
    tLZCardSchema1.setMakeDate(strDate1);
    tLZCardSchema1.setState(CertStatBL.STAT_SENDED);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
    tLZCardSchema2.setMakeDate(strDate1);

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询本日发放失败");
      return false;
    }

    // 生成打印所需的数据
    XmlExport xmlExport = new XmlExport();
    xmlExport.createDocument("CertDaily.vts", "");

    TextTag textTag = new TextTag();

    try {
      textTag.add("SendOutCom", getComName(m_LZCardSchema.getSendOutCom()));
    } catch (Exception ex) {
      buildError("submitQueryMain", ex.getMessage());
      return false;
    }

    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
    textTag.add("MakeDate", df.format(fdate.getDate(m_LZCardSchema.getMakeDate())));

    xmlExport.addTextTag(textTag);

    // 按照单证编码排序
    String strSQL = "SELECT * FROM LMCertifyDes"
                  + " WHERE CertifyClass='P' OR CertifyClass='D'"
                  + " ORDER BY CertifyCode";
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(strSQL);
    LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
    LMCertifyDesSet tLMCertifyDesSet = tLMCertifyDesDB.executeQuery(sqlbv1);

    ListTable listTable = new ListTable();
    String[] strArr = null;

    for(int nIndex = 0; nIndex < tLMCertifyDesSet.size(); nIndex ++) {
      strArr = (String[])hashResult.get(tLMCertifyDesSet.get(nIndex + 1).getCertifyCode());
      listTable.add(strArr);
    }

    strArr = new String[6];
    strArr[0] = "单证编码";
    strArr[1] = "单证名称";
    strArr[2] = "单证单位";
    strArr[3] = "本日入库";
    strArr[4] = "本日发放";
    strArr[5] = "本日库存";

    listTable.setName("CertInfo");

    xmlExport.addListTable(listTable, strArr);

    mResult.clear();
    mResult.add(xmlExport);

    return true;
  }

  public String getComName(String strComCode)
      throws Exception
  {
    LDCodeDB tLDCodeDB = new LDCodeDB();

    tLDCodeDB.setCode(strComCode);
    tLDCodeDB.setCodeType("station");

    if (!tLDCodeDB.getInfo()) {
      mErrors.copyAllErrors(tLDCodeDB.mErrors);
      throw new Exception("在取得LDCode的数据时发生错误");
    }
    return tLDCodeDB.getCodeName();
  }
}
