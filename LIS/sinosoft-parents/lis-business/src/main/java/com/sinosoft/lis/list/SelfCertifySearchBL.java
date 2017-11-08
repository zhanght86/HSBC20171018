/*
* <p>ClassName: CertifySearchBL </p>
* <p>Description: 单证查询打印的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2002-06-16
 */
package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import com.sinosoft.lis.certify.CertifyFunc;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.Hashtable;
import java.sql.*;

/**
 * 卡单打印清单
 */
public class SelfCertifySearchBL  
{
private static Logger logger = Logger.getLogger(SelfCertifySearchBL.class);


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
  SQLwithBindVariables sqlbva=new SQLwithBindVariables();
  public SelfCertifySearchBL() {
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
	/**
	 * 如果参数parameter值为空直接返回null反则返回变量字符串paraStr
	 * @param parameter 参数值
	 * @param paraStr 变量字符串
	 * @return
	 */
	public static String getParameterStr(String parameter,String paraStr){
		if ((parameter == null) || parameter.equals("")) {
			return null;
		}else{
			return paraStr;
		}
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
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.put("CertifyCode", tLZCardSchema.getCertifyCode());
    sqlbv1.put("SendOutCom", tLZCardSchema.getSendOutCom());
   sqlbv1.put("ReceiveCom", tLZCardSchema.getReceiveCom());
   sqlbv1.put("Operator", tLZCardSchema.getOperator());
   sqlbv1.put("EndNo", tLZCardSchema.getEndNo());
   sqlbv1.put( "StartNo", tLZCardSchema.getStartNo());
   sqlbv1.put("MakeDate1", aLZCardSet.get(1).getMakeDate());
   sqlbv1.put("MakeDate2", aLZCardSet.get(2).getMakeDate());
    strWhere += getWherePart("CertifyCode", ReportPubFun.getParameterStr( tLZCardSchema.getCertifyCode(), "?CertifyCode?"));
    strWhere += getWherePart("SendOutCom", ReportPubFun.getParameterStr(tLZCardSchema.getSendOutCom(), "?SendOutCom?"));
    strWhere += getWherePart("ReceiveCom", ReportPubFun.getParameterStr(tLZCardSchema.getReceiveCom(), "?ReceiveCom?"));
    strWhere += getWherePart("Operator",ReportPubFun.getParameterStr( tLZCardSchema.getOperator(), "?Operator?"));
    strWhere += getWherePart("EndNo", "StartNo", ReportPubFun.getParameterStr( tLZCardSchema.getEndNo(), "?EndNo?"), 0);
    strWhere += getWherePart("EndNo", "StartNo", ReportPubFun.getParameterStr( tLZCardSchema.getStartNo(), "?StartNo?"), 2);
    strWhere += getWherePart("MakeDate", ReportPubFun.getParameterStr(aLZCardSet.get(1).getMakeDate(),"?MakeDate1?"),
    		ReportPubFun.getParameterStr(aLZCardSet.get(1).getMakeDate(),"?MakeDate2?"), 1);
    
    if( strState.equals( SelfCertifySearchBL.PRT_STATE ) ) {
      strSQL = "SELECT certifycode,(select riskcode from lmcardrisk where lmcardrisk.certifycode=lzcard.certifycode),sendoutcom,"
    	       +"receivecom,(select shortname from ldcom where trim(comcode) = trim(substr(receivecom,2, 8))),startno,endno,SumCount,Operator,modifydate FROM LZCard WHERE 1=1";
	  countSql = "SELECT count(*) FROM LZCard WHERE 1=1";
    } else if( strState.equals( SelfCertifySearchBL.PRT_TRACK ) ) {
      strSQL = "SELECT certifycode,(select riskcode from lmcardrisk where lmcardrisk.certifycode=lzcard.certifycode),sendoutcom,"
    	       +"receivecom,(select shortname from ldcom where trim(comcode) = trim(substr(receivecom,2, 8))),startno,endno,SumCount,Operator,modifydate FROM LZCardTrack WHERE 1=1";
	  countSql = "SELECT count(*) FROM LZCardTrack WHERE 1=1";
    }

    strSQL += strWhere;
    strSQL += " ORDER BY CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo";

	countSql += strWhere;
	
	sqlbva.sql(countSql);
    sqlbva.put("CertifyCode", tLZCardSchema.getCertifyCode());
    sqlbva.put("SendOutCom", tLZCardSchema.getSendOutCom());
    sqlbva.put("ReceiveCom", tLZCardSchema.getReceiveCom());
    sqlbva.put("Operator", tLZCardSchema.getOperator());
    sqlbva.put("EndNo", tLZCardSchema.getEndNo());
    sqlbva.put( "StartNo", tLZCardSchema.getStartNo());
    sqlbva.put("MakeDate1", aLZCardSet.get(1).getMakeDate());
    sqlbva.put("MakeDate2", aLZCardSet.get(2).getMakeDate());
	
    logger.debug("parseParamsPrint*********SQL : " + strSQL);
	logger.debug("parseParamsPrint*********Count Sql:"+countSql);

    VData tVData = new VData();
	sqlbv1.sql(strSQL);
    tVData.add(0, sqlbv1);

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

    
    SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
    if(vData.getObjectByObjectName("String", 0) instanceof SQLwithBindVariables){
    	sqlbvb = (SQLwithBindVariables)vData.getObjectByObjectName("String", 0);
    }else{
    	String strSQL = (String)vData.getObjectByObjectName("String", 0);
    	sqlbvb.sql(strSQL);
    }
//    logger.debug("*************查询状态的SQL语句是"+strSQL);
    
	ExeSQL exeSQL = new ExeSQL();
	String sCount = exeSQL.getOneValue(sqlbva);
	int iCount = Integer.parseInt(sCount);
	logger.debug("------------"+iCount);
	if(iCount>500)
	{
	  buildError("submitSearchMain", "查询结果数过多，请限定条件再查询！");
	  return false;
	}
	
	ExeSQL tExeSQL=new ExeSQL();
	SSRS tSSRS=tExeSQL.execSQL(sqlbvb);


    mResult.add(0,tSSRS );
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

    LDComDB tLDComDB = new LDComDB();

    tLDComDB.setComCode(strComCode);

    if (!tLDComDB.getInfo()) {
      mErrors.copyAllErrors(tLDComDB.mErrors);
      throw new Exception("在取得LDCode的数据时发生错误");
    }
    return tLDComDB.getShortName();
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
   * 
   * 自助卡单清单打印
   * @param xe
   * @param aLZCardSet
   * @throws Exception
   */
  private void printOther(XmlExport xe, LZCardSet aLZCardSet)
      throws Exception
  {
    xe.createDocument("SelfCertifyListEx.vts", "");

    int nLen = 12;  // 列表数据的列数
    String[] values = new String[nLen];

    ListTable lt = new ListTable();
    lt.setName("Info");
    
    int sumCount=0;//合计总量
    int sumMoney=0;//合计总金额
    
    ExeSQL tExeSQL=null;
    SSRS tSSRS=null;

    for(int nIndex = 0; nIndex < aLZCardSet.size(); nIndex ++) 
    {
      LZCardSchema tLZCardSchema = aLZCardSet.get(nIndex + 1);
      
      String TTsql="select riskcode from lmcardrisk where certifycode='?certifycode?'";
      logger.debug("TTsql:"+TTsql);
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(TTsql);
      sqlbv.put("certifycode", tLZCardSchema.getCertifyCode());
      tExeSQL=new ExeSQL();
      tSSRS=tExeSQL.execSQL(sqlbv);

      values = new String[nLen];

      values[0] = tLZCardSchema.getCertifyCode();
      values[1] = tSSRS.GetText(1,1);
      values[2] = tLZCardSchema.getSendOutCom();
      values[3] = tLZCardSchema.getReceiveCom();
      if(tLZCardSchema.getReceiveCom().substring(0,1).endsWith("D"))
      {
    	  values[4] = getAgentName( tLZCardSchema.getReceiveCom().substring(1));
      }
      else
      {
    	  values[4] = getComName( tLZCardSchema.getReceiveCom().substring(1));
      }  
      values[5] = tLZCardSchema.getStartNo();
      values[6] = tLZCardSchema.getEndNo();
      values[7] = String.valueOf( tLZCardSchema.getSumCount() );
      values[9] = tLZCardSchema.getModifyDate();
      values[10] = tLZCardSchema.getTakeBackNo();//借用该字段来存储交费类型
      
      sumCount=sumCount+tLZCardSchema.getSumCount();
      
      //查询每一张单证费用,计算本次发放记录的费用
      String sql="select a.prem,b.certifyname from lmcardrisk a,lmcertifydes b where a.certifycode=b.certifycode and a.certifycode='?certifycode?'";
      logger.debug("查询单证费用的sql"+sql);
      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
      sqlbv1.sql(sql);
      sqlbv1.put("certifycode", tLZCardSchema.getCertifyCode());
      
      tSSRS=tExeSQL.execSQL(sqlbv1);
      if(tSSRS.getMaxRow()>0)
      {
    	  values[8] = String.valueOf(Integer.parseInt(tSSRS.GetText(1,1))*tLZCardSchema.getSumCount());
    	  sumMoney=sumMoney+Integer.parseInt(values[8]);
    	  values[11]= tSSRS.GetText(1,2);
      }
      else
      {
          throw new Exception("查询定额单险种信息表出现异常!");
      }

      lt.add( values );
    }

    values = new String[nLen];

    values[0] = "CertifyCode";
    values[1] = "RiskCode";
    values[2] = "SendOutCom";
    values[3] = "ReceiveCom";
    values[4] = "ReceiveComName";
    values[5] = "StartNo";
    values[6] = "EndNo";
    values[7] = "SumCount";
    values[8] = "Fee";
    values[9] = "ModifyDate";
    values[10] = "FeeType";
    values[11] = "CertifyName";

    xe.addListTable(lt, values);
    String CurrentDate = PubFun.getCurrentDate();

    TextTag texttag=new TextTag();
    texttag.add("ManageCom", getComName(m_GlobalInput.ManageCom))  ;
    texttag.add("Operator",m_GlobalInput.Operator);
    texttag.add("time",CurrentDate);
    texttag.add("SumCount",sumCount);
    texttag.add("SumMoney",sumMoney);
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
