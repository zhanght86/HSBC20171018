/*
* <p>ClassName: CertBalanceBL </p>
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

public class CertBalanceBL  {
private static Logger logger = Logger.getLogger(CertBalanceBL.class);

  public  CErrors mErrors = new CErrors();
  private VData mResult = new VData();

  /** 全局数据 */
  private GlobalInput m_GlobalInput = new GlobalInput() ;

  /** 数据操作字符串 */
  private String m_strOperate;

  /** 业务处理相关变量 */
  private LZCardSet m_LZCardSet = new LZCardSet();
  private Hashtable m_hashParams = null;

  public CertBalanceBL() {
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
    	logger.debug("**************8");
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
    try
    {
      if( m_strOperate.equals("QUERY||MAIN") )
      {
        return submitQueryMain();
      }
      else
      {
        buildError("dealData", "不支持的操作字符串");
        return false;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("dealData", ex.toString());
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
      m_LZCardSet = (LZCardSet)cInputData.getObjectByObjectName("LZCardSet", 0);
      m_hashParams = (Hashtable)cInputData.getObjectByObjectName("Hashtable", 0);

      if( m_LZCardSet == null ) {
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

    cError.moduleName = "CertBalanceBL";
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
      strArr[3] = "0";  // 单价
      strArr[4] = "0";  // 领取数量
      strArr[5] = "0";  // 印刷费用

      hashResult.put(tLMCertifyDesSet.get(nIndex + 1).getCertifyCode(),strArr);
    }

    // 取印刷费用
    // 取单证印刷表中最后一次的单证印刷费用
    strSQL = "SELECT CertifyCode, CertifyPrice FROM LZCardPrint A"
           + " WHERE PrtNo = (SELECT MAX(PrtNo) FROM LZCardPrint B"
           + "    WHERE B.CertifyCode = A.CertifyCode)"
           + " ORDER BY CertifyCode";
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(strSQL);
    ExeSQL es = new ExeSQL();
    SSRS ssrs = es.execSQL(sqlbv1);

    if( ssrs.mErrors.needDealError() ) {
      mErrors.copyAllErrors(ssrs.mErrors);
      return false;
    }

    for(int nIndex = 0; nIndex < ssrs.getMaxRow(); nIndex ++) {
      String strCertifyCode = ssrs.GetText(nIndex + 1, 1);
      strArr = (String[])hashResult.get(strCertifyCode);
      if( strArr == null ) {
        continue;
      }

      strArr[3] = ssrs.GetText(nIndex + 1, 2);
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

    if( !tCertStatBL.submitData(tVData, "REPORT||BLANCE") ) {
      buildError("callStatBL", tCertStatBL.mErrors.getFirstError());
      return false;
    }

    tVData = tCertStatBL.getResult();

    // 处理得到的数据
    LZCardSet tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);
    double dSumMoney = 0;  // 费用合计

    for(int nIndex = 0; nIndex < tLZCardSet.size(); nIndex ++) {
      LZCardSchema tLZCardSchema = tLZCardSet.get(nIndex + 1);
      String[] strArr = (String[])hashResult.get(tLZCardSchema.getCertifyCode());

      if( strArr == null ) {
        continue;
      }

      double dPrice = Double.parseDouble(strArr[3]);
      strArr[4] = String.valueOf(tLZCardSchema.getSumCount());

      double dMoney = dPrice * tLZCardSchema.getSumCount();
      strArr[5] = new DecimalFormat("0.00").format(dMoney);

      dSumMoney += dMoney;
    }

    hashResult.put("SumMoney", new DecimalFormat("0.00").format(dSumMoney));

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
    LZCardSet tLZCardSet = new LZCardSet();

    LZCardSchema tLZCardSchema1 = new LZCardSchema();
    LZCardSchema tLZCardSchema2 = new LZCardSchema();

    tLZCardSchema1.setSchema(m_LZCardSet.get(1));
    tLZCardSchema2.setSchema(m_LZCardSet.get(2));

    tLZCardSet.add(tLZCardSchema1);
    tLZCardSet.add(tLZCardSchema2);

    // 构造调用CertStatBL所必要的参数
    tLZCardSchema1.setSendOutCom("A" + tLZCardSchema1.getSendOutCom());
    tLZCardSchema1.setReceiveCom("A" + tLZCardSchema1.getReceiveCom());
//    tLZCardSchema1.setMakeDate("");
    tLZCardSchema1.setState(CertStatBL.STAT_SENDED);

    tLZCardSchema2.setSendOutCom("0");
    tLZCardSchema2.setReceiveCom("0");
//    tLZCardSchema2.setMakeDate("");

    if( !callStatBL(hashResult, tLZCardSet) ) {
      buildError("submitQueryMain", "查询单证结算信息失败");
      return false;
    }

    // 生成打印所需的数据
    XmlExport xmlExport = new XmlExport();
    xmlExport.createDocument("CertBalance.vts", "");
    String displayname = "";                                  //控制模板文件标签的显示
    String mDate[] = new String [2];                          //统计的起始和终止日期

    TextTag textTag = new TextTag();
    FDate fdate = new FDate();
    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");

    try {
      textTag.add("SendOutCom", getComName(m_LZCardSet.get(1).getSendOutCom()));
      textTag.add("ReceiveCom", getComName(m_LZCardSet.get(1).getReceiveCom()));

      mDate[0] = m_LZCardSet.get(1).getMakeDate();
      mDate[1] = m_LZCardSet.get(2).getMakeDate();

      textTag.add("MakeDateB", df.format(fdate.getDate(mDate[0])));
      textTag.add("MakeDateE", df.format(fdate.getDate(mDate[1])));
    } catch (Exception ex) {
      buildError("submitQueryMain", ex.getMessage());
      return false;
    }

    textTag.add("SumMoney", (String)hashResult.get("SumMoney"));
    xmlExport.addTextTag(textTag);

    // 按照单证编码排序
    String strSQL = "SELECT * FROM LMCertifyDes"
                  + " WHERE CertifyClass='P' OR CertifyClass='D'"
                  + " ORDER BY CertifyCode";
    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    sqlbv2.sql(strSQL);
    LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
    LMCertifyDesSet tLMCertifyDesSet = tLMCertifyDesDB.executeQuery(sqlbv2);

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
    strArr[3] = "单价";
    strArr[4] = "领取数量";
    strArr[5] = "印刷费用";

    listTable.setName("CertInfo");
    xmlExport.addListTable(listTable, strArr);

    if(m_GlobalInput.ComCode.equals("86"))
    {
        displayname = "displayGrant";
        xmlExport.addDisplayControl(displayname);
        listTable = new ListTable();                    //如果不重新实例化，就会连上面正常发放的信息也显示到里面了
        TextTag textGrantTag = new TextTag();

        try {
          textGrantTag.add("SendOutCom",getComName(m_LZCardSet.get(1).getSendOutCom()));
          textGrantTag.add("ReceiveCom",getComName(m_LZCardSet.get(1).getReceiveCom()));
          textGrantTag.add("MakeDateB", mDate[0]);
          textGrantTag.add("MakeDateE", mDate[1]);
        }catch (Exception ex) {
          buildError("submitQueryMain", ex.getMessage());
          return false;
        }

        strSQL = "select a.certifycode,b.certifyname,b.unit,a.certifyprice,a.sumcount "
               + "from lzcardprint a,lmcertifydes b "
               + "where a.certifycode = b.certifycode and b.managecom = '86' "
               + "and a.managecom = '" + "?managecom?" + "' "
               + ReportPubFun.getWherePart("GetMakeDate",sfun(mDate[0],"date1"),sfun(mDate[1],"date2"),1)
//               + " group by a.certifycode,b.certifyname,b.unit,a.certifyprice "
               + " order by a.certifycode "
               ;
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(strSQL);
        sqlbv3.put("managecom", m_LZCardSet.get(1).getReceiveCom());
        sqlbv3.put("date1", mDate[0]);
        sqlbv3.put("date2", mDate[1]);
        logger.debug("Query grant card : " + strSQL);
        ExeSQL tExesql = new ExeSQL();
        SSRS tssrs = tExesql.execSQL(sqlbv3);

        double price = 0.0;                                     //单证单价
        double count = 0.0;                                     //单证数量
        double SumMoney = 0.0;                                  //累计价格
        for(int i = 1; i <= tssrs.getMaxRow() ; i++)
        {
            strArr = new String [6];
            strArr[0] = tssrs.GetText(i,1);
            strArr[1] = tssrs.GetText(i,2);
            strArr[2] = tssrs.GetText(i,3);
            price = ReportPubFun.functionDouble(tssrs.GetText(i,4));
            strArr[3] = ReportPubFun.functionJD(price,"0.00");
            count = ReportPubFun.functionDouble(tssrs.GetText(i,5));
            strArr[4] = ReportPubFun.functionJD(count,"0");
            strArr[5] = ReportPubFun.functionJD(price * count , "0.00");

            listTable.add(strArr);
            SumMoney += price * count;
        }
        textGrantTag.add("SumMoneyGrant", ReportPubFun.functionJD(SumMoney,"0.00"));
        xmlExport.addTextTag(textGrantTag);

        strArr = new String[6];
        strArr[0] = "单证编码";
        strArr[1] = "单证名称";
        strArr[2] = "单证单位";
        strArr[3] = "单价";
        strArr[4] = "领取数量";
        strArr[5] = "印刷费用";

        listTable.setName("CertGrant");
        xmlExport.addListTable(listTable, strArr);
    }
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
  
  private String sfun(String str1, String str2){
	  if(str2 == null || str2.equals("")) return null;
	  return "?"+str2+"?";
  }
}
