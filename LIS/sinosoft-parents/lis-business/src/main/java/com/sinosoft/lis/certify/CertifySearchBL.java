/*
* <p>ClassName: CertifySearchBL </p>
* <p>Description: 单证查询打印的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2002-06-16
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.Hashtable;
import java.sql.*;

public class CertifySearchBL  
{
private static Logger logger = Logger.getLogger(CertifySearchBL.class);


  public  CErrors mErrors = new CErrors();
  private VData mResult = new VData();

  /** 全局数据 */
  private GlobalInput m_GlobalInput = new GlobalInput() ;

  /** 数据操作字符串 */
  private String m_strOperate;

  /** 业务处理相关变量 */
  private LZCardSet m_LZCardSet = new LZCardSet();
  private String m_strWhere = "";
  private Hashtable m_hashParams = null;

  public static String PRT_STATE = "0"; // 查询打印状态表中的数据
  public static String PRT_TRACK = "1"; // 查询打印轨迹表中的数据

  private String countSql = "";
  SQLwithBindVariables sqlbvcount = new SQLwithBindVariables();
  
  private SQLwithBindVariables sqlbv = new SQLwithBindVariables();

  public CertifySearchBL() {
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
    if( m_strOperate.equals("") ) 
    {
      buildError("submitData", "不支持的操作字符串");
      return false;
    }

    if (!getInputData(cInputData))
      return false;

    if (!dealData())
      return false;

    VData tVData = new VData();
    if( !prepareOutputData(tVData) )
      return false;

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
      if( m_strOperate.equals("SEARCH||MAIN") ) 
      {
        return submitSearchMain();
      } 
      else if( m_strOperate.equals("SEARCH||PRINT") ) 
      {
        return submitSearchPrint();
      } 
      else 
      {
        buildError("dealData", "不支持的操作字符串");
        return false;
      }
    } 
    catch (Exception ex) 
    {
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
    try 
    {
      this.m_GlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
      m_LZCardSet = (LZCardSet)cInputData.getObjectByObjectName("LZCardSet", 0);
      m_hashParams = (Hashtable)cInputData.getObjectByObjectName("Hashtable", 0);

      if( m_LZCardSet == null ) 
      {
        buildError("getInputData", "没有传入所需要的查询条件");
        return false;
      }

      LZCardSchema tLZCardSchema = m_LZCardSet.get(1);

      if( tLZCardSchema.getState() == null || tLZCardSchema.getState().equals("") ) 
      {
        buildError("getInputData", "请输入统计条件");
        return false;
      }

    } 
    catch(Exception ex) 
    {
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

    cError.moduleName = "CertifySearchBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
    String szReturn = "";
    String szOperates[] = {"SEARCH||MAIN", "SEARCH||PRINT"};

    for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
      if( szOperate.equals(szOperates[nIndex]) ) {
        szReturn = szOperate;
      }
    }

    return szReturn;
  }

  private String getWherePart(String strColName, String strColValue)
  {
    if( strColValue == null || strColValue.equals("") ) 
    {
      return "";
    }
    return " AND " + strColName + " = '" + strColValue + "'";
  }

  private String getWherePart(String strCol1, String strCol2, String strCol3, int nFlag)
  {
    if( nFlag == 0 ) 
    {
      if( strCol3 == null || strCol3.equals("") ) 
      {
        return "";
      }

      return " AND "  + strCol1 + " >= '" + strCol3 + "'";
    } 
    else if(nFlag == 2 )
    {
    	if( strCol3 == null || strCol3.equals("") ) 
        {
          return "";
        }

        return " AND " + strCol2 + " <= '" + strCol3 + "'";
    }
    else 
    {
      String str = "";

      if( strCol2 == null || strCol2.equals("") ) 
      {
        str += "";
      } 
      else 
      {
        str += " AND " + strCol1 + " >= '" + strCol2 + "'";
      }

      if( strCol3 == null || strCol3.equals("") ) 
      {
        str += "";
      } 
      else 
      {
        str += " AND " + strCol1 + " <= '" + strCol3 + "'";
      }

      return str;
    }
  }
  
  /**
   * java变量绑自定义函数  两边自动添加?号
   * @param str1
   * @return
   */
  public String sfun(String str1, String str2){
  	if(str1 == null || str1.equals("")) return null;
  	return "?"+str2+"?";
  }

  /**
   * 查询打印所对应的参数解析函数
   * @param aLZCardSet
   * @return
   * @throws Exception
   */
  private VData parseParamsPrint(LZCardSet aLZCardSet)
      throws Exception
  {
    LZCardSchema tLZCardSchema = null;

    String strSQL = "";
    String strWhere = "";

    String strManageCom = "A" + m_GlobalInput.ComCode;

    tLZCardSchema = aLZCardSet.get(1);

    String strState = tLZCardSchema.getState();

    strWhere += getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"));
    strWhere += getWherePart("SendOutCom", sfun(tLZCardSchema.getSendOutCom(),"SendOutCom"));
    strWhere += getWherePart("ReceiveCom", sfun(tLZCardSchema.getReceiveCom(),"ReceiveCom"));
    strWhere += getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"));
    strWhere += getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"));
    strWhere += getWherePart("TakeBackNo", sfun(tLZCardSchema.getTakeBackNo(),"TakeBackNo"));
    strWhere += getWherePart("EndNo", "StartNo", sfun(tLZCardSchema.getEndNo(),"EndNo"), 0);
    strWhere += getWherePart("EndNo", "StartNo", sfun(tLZCardSchema.getStartNo(),"StartNo"), 2);
    strWhere += getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"),
                             sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1);

    if( strState.equals( CertifySearchBL.PRT_STATE ) ) {
      strSQL = "SELECT * FROM LZCard WHERE 1=1";
	  countSql = "SELECT count(*) FROM LZCard WHERE 1=1";
    } else if( strState.equals( CertifySearchBL.PRT_TRACK ) ) {
      strSQL = "SELECT * FROM LZCardTrack WHERE 1=1";
	  countSql = "SELECT count(*) FROM LZCardTrack WHERE 1=1";
    }

    strSQL += strWhere;
    strSQL += " ORDER BY CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo";

	countSql += strWhere;
	
	sqlbvcount.sql(countSql);
    sqlbvcount.put("CertifyCode", tLZCardSchema.getCertifyCode());
    sqlbvcount.put("SendOutCom", tLZCardSchema.getSendOutCom());
    sqlbvcount.put("ReceiveCom", tLZCardSchema.getReceiveCom());
    sqlbvcount.put("Handler", tLZCardSchema.getHandler());
    sqlbvcount.put("Operator", tLZCardSchema.getOperator());
    sqlbvcount.put("date1", aLZCardSet.get(1).getMakeDate());
    sqlbvcount.put("date2", aLZCardSet.get(2).getMakeDate());
    sqlbvcount.put("StartNo", tLZCardSchema.getStartNo());
    sqlbvcount.put("EndNo", tLZCardSchema.getEndNo());

    logger.debug("parseParamsPrint*********SQL : " + strSQL);
	logger.debug("parseParamsPrint*********Count Sql:"+countSql);

	 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	 sqlbv.sql(strSQL);
     sqlbv.put("CertifyCode", tLZCardSchema.getCertifyCode());
     sqlbv.put("SendOutCom", tLZCardSchema.getSendOutCom());
     sqlbv.put("ReceiveCom", tLZCardSchema.getReceiveCom());
     sqlbv.put("Handler", tLZCardSchema.getHandler());
     sqlbv.put("Operator", tLZCardSchema.getOperator());
     sqlbv.put("date1", aLZCardSet.get(1).getMakeDate());
     sqlbv.put("date2", aLZCardSet.get(2).getMakeDate());
     sqlbv.put("StartNo", tLZCardSchema.getStartNo());
     sqlbv.put("EndNo", tLZCardSchema.getEndNo());
	
    VData tVData = new VData();
    tVData.add(0, sqlbv);

    return tVData;
  }

  /**
   * 查询打印所对应的功能函数
   * @return
   * @throws Exception
   */
  private boolean submitSearchMain()
      throws Exception
  {
    mResult.clear();

    VData vData = parseParamsPrint(m_LZCardSet);
    SQLwithBindVariables sqlbva = new SQLwithBindVariables();
    if(vData.getObjectByObjectName("String", 0) instanceof SQLwithBindVariables){
    	sqlbva = (SQLwithBindVariables)vData.getObjectByObjectName("String", 0);
    }else{
    	String strSQL = (String)vData.getObjectByObjectName("String", 0);
    	sqlbva.sql(strSQL);
    }
    
//    logger.debug("*************查询状态的SQL语句是"+strSQL);
    
	ExeSQL exeSQL = new ExeSQL();
	String sCount = exeSQL.getOneValue(sqlbvcount);
	int iCount = Integer.parseInt(sCount);
	logger.debug("------------"+iCount);
	if(iCount>500)
	{
	  buildError("submitSearchMain", "查询结果数过多，请限定条件再查询！");
	  return false;
	}


    LZCardDB tLZCardDB = new LZCardDB();
    LZCardSet tLZCardSet = tLZCardDB.executeQuery(sqlbva);

    if( tLZCardDB.mErrors.needDealError() ) 
    {
      mErrors.copyAllErrors( tLZCardDB.mErrors );
      return false;
    }

    mResult.add( tLZCardSet );
    return true;
  }

  /**
   * 查询打印所对应的功能函数
   * @return
   * @throws Exception
   */
  private boolean submitSearchPrint()
      throws Exception
  {
    mResult.clear();

    XmlExport xe = new XmlExport();
    printOther(xe, m_LZCardSet);
    mResult.add( xe );
    return true;
  }

  // 一些辅助函数，用来将编码转换成名字
  private String getCertifyName(String strCertifyCode)
      throws Exception
  {
    LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();

    tLMCertifyDesDB.setCertifyCode( strCertifyCode );

    if (!tLMCertifyDesDB.getInfo()) {
      mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
      throw new Exception("在取得LMCertifyDes的数据时发生错误");
    }

    return tLMCertifyDesDB.getCertifyName();
  }

  private String getComName(String strComCode)
      throws Exception
  {
    if( strComCode.equals(CertifyFunc.INPUT_COM) ) {
      return CertifyFunc.INPUT_COM;
    }

    LDCodeDB tLDCodeDB = new LDCodeDB();

    tLDCodeDB.setCode(strComCode);
    tLDCodeDB.setCodeType("station");

    if (!tLDCodeDB.getInfo()) {
      mErrors.copyAllErrors(tLDCodeDB.mErrors);
      throw new Exception("在取得LDCode的数据时发生错误");
    }
    return tLDCodeDB.getCodeName();
  }

  private String getAgentName(String strAgentCode)
      throws Exception
  {
    LAAgentDB tLAAgentDB = new LAAgentDB();
    tLAAgentDB.setAgentCode(strAgentCode);
    if (!tLAAgentDB.getInfo()) {
      mErrors.copyAllErrors(tLAAgentDB.mErrors);
      throw new Exception("在取得LAAgent的数据时发生错误");
    }
    return tLAAgentDB.getName();
  }

  private String transComCode(String strComCode)
      throws Exception
  {
    if( strComCode.charAt(0) == 'A' ) {
      return getComName( strComCode.substring(1) );
    } else if( strComCode.charAt(0) == 'B' ) {
      throw new Exception("目前还不支持");
    } else if( strComCode.charAt(0) == 'D' ) {
      return getAgentName( strComCode.substring(1) );
    } else {
      return strComCode;
    }
  }

  /**
   * Kevin 2003-05-08
   * 针对于发放单证到业务员的情况的打印逻辑
   * @param xe
   * @param aLZCardSet
   * @throws Exception
   */
  private void printAgent(XmlExport xe, LZCardSet aLZCardSet)
      throws Exception
  {
    xe.createDocument("CertifyList.vts", "");

    int nLen = 7;  // 列表数据的列数
    String[] values = new String[nLen];

    ListTable lt = new ListTable();
    lt.setName("INFO");

    if(aLZCardSet.size()>=1)
    {
      LZCardSchema yLZCardSchema = new LZCardSchema();
      yLZCardSchema.setSchema(aLZCardSet.get(1));
      int tCountFee =0;
      String tDate = yLZCardSchema.getMakeDate(); // 领取时间
      String tNameReceiveCom = transComCode(yLZCardSchema.getReceiveCom());
      String tCodeReceiveCom = "";

      if(yLZCardSchema.getReceiveCom().length()>=1)
      {
        tCodeReceiveCom = yLZCardSchema.getReceiveCom().substring(1); // 获取工号
      }

      TextTag texttag = new TextTag();

      texttag.add("NameReceiveCom",tNameReceiveCom);
      texttag.add("CodeReceiveCom",tCodeReceiveCom);
      texttag.add("MakeDate",tDate);
      texttag.add("CountFee",tCountFee);
      xe.addTextTag(texttag);
      if(aLZCardSet.size()<=13)
      {
        xe.addDisplayControl("displaycompart");
        for(int nIndex = 0; nIndex < aLZCardSet.size(); nIndex++)
        {
          LZCardSchema tLZCardSchema = aLZCardSet.get(nIndex + 1);
          values = new String[nLen];
          values[0] = tLZCardSchema.getCertifyCode();
          values[1] = getCertifyName( tLZCardSchema.getCertifyCode() );
          values[2] = tLZCardSchema.getStartNo();
          values[3] = tLZCardSchema.getEndNo();
          values[4] = String.valueOf( tLZCardSchema.getSumCount() );
          values[5] = "";
          values[6] = "";
          lt.add( values );
        }
        for(int nIndex = aLZCardSet.size(); nIndex < 13; nIndex++)
        {
          values = new String[nLen];
          values[0] = "";
          values[1] = "";
          values[2] = "";
          values[3] = "";
          values[4] = "";
          values[5] = "";
          values[6] = "";
          lt.add( values );
        }
      }
      else
      {
        xe.addDisplayControl("displaydouble");
        for(int nIndex = 0; nIndex < aLZCardSet.size(); nIndex++)
        {
          LZCardSchema tLZCardSchema = aLZCardSet.get(nIndex + 1);
          values = new String[nLen];
          values[0] = tLZCardSchema.getCertifyCode();
          values[1] = getCertifyName( tLZCardSchema.getCertifyCode() );
          values[2] = tLZCardSchema.getStartNo();
          values[3] = tLZCardSchema.getEndNo();
          values[4] = String.valueOf( tLZCardSchema.getSumCount() );
          values[5] = "";
          values[6] = "";
          lt.add( values );
        }
      }
      values = new String[nLen];
      values[0] = "CertifyCode";
      values[1] = "CertifyName";
      values[2] = "StartNo";
      values[3] = "EndNo";
      values[4] = "SumCount";
      values[5] = "Fee";  // 单价
      values[6] = "FeeCount";  // 单证费用
      xe.addListTable(lt, values);
    }
  }

  /**
   * Kevin 2003-05-08
   * 针对于其它情况的单证清单打印
   * @param xe
   * @param aLZCardSet
   * @throws Exception
   */
  private void printOther(XmlExport xe, LZCardSet aLZCardSet)
      throws Exception
  {
    xe.createDocument("CertifyListEx.vts", "");

    int nLen = 10;  // 列表数据的列数
    String[] values = new String[nLen];

    ListTable lt = new ListTable();
    lt.setName("Info");

    for(int nIndex = 0; nIndex < aLZCardSet.size(); nIndex ++) 
    {
      LZCardSchema tLZCardSchema = aLZCardSet.get(nIndex + 1);

      values = new String[nLen];

      values[0] = transComCode( tLZCardSchema.getSendOutCom() );
      values[1] = transComCode( tLZCardSchema.getReceiveCom() );
      values[2] = getCertifyName( tLZCardSchema.getCertifyCode() );
      values[3] = tLZCardSchema.getStartNo();
      values[4] = tLZCardSchema.getEndNo();
      values[5] = String.valueOf( tLZCardSchema.getSumCount() );
      values[6] = tLZCardSchema.getStateFlag();
      values[7] = tLZCardSchema.getOperator();
      values[8] = tLZCardSchema.getMakeDate();
      values[9] = tLZCardSchema.getMakeTime();

      lt.add( values );
    }

    values = new String[nLen];

    values[0] = "SendOutCom";
    values[1] = "ReceiveCom";
    values[2] = "CertifyCode";
    values[3] = "StartNo";
    values[4] = "EndNo";
    values[5] = "SumCount";
    values[6] = "StateFlag";
    values[7] = "Operator";
    values[8] = "MakeDate";
    values[9] = "MakeTime";

    xe.addListTable(lt, values);
    String CurrentDate = PubFun.getCurrentDate();

    TextTag texttag=new TextTag();
    texttag.add("ManageCom", getComName(m_GlobalInput.ManageCom))  ;
    texttag.add("Operator",m_GlobalInput.Operator);
    texttag.add("time",CurrentDate);
    xe.addTextTag(texttag);
    //xe.outputDocumentToFile("d:/","out");
  }

  private String getParams(String strKey) {
    if( m_hashParams == null ) {
      return "";
    }

    String str = (String)m_hashParams.get(strKey);

    if( str == null ) {
      return "";
    } else {
      return str;
    }
  }
}
