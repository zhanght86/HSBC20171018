/*
* <p>ClassName: CertStatBL </p>
* <p>Description: 单证统计查询的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2002-04-10
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.Hashtable;
import java.sql.*;
import java.text.*;

public class CertStatBL  {
private static Logger logger = Logger.getLogger(CertStatBL.class);

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

    public static String STAT_STOCK = "1";                      // 库存
    public static String STAT_SENDED = "2";                     // 已发放
    public static String STAT_UNCANCEL = "3";                   // 未核销
    public static String STAT_GROSS = "4";                      // 入库总量
    public static String STAT_ABANDON = "5";                    // 正常作废
    public static String STAT_MISS = "6";                       // 遗失被盗
    public static String STAT_HANDIN = "7";                     // 缴销－正常回收

    public static String PRT_STATE = "0"; // 查询打印状态表中的数据
    public static String PRT_TRACK = "1"; // 查询打印轨迹表中的数据

    public CertStatBL() {
    }

    public static void main(String[] args) {

        CertStatBL test = new CertStatBL();
        LZCardSchema t = new LZCardSchema();
        LZCardSet setT = new LZCardSet();
        VData v = new VData();
        t.setCertifyCode("1101");
        t.setSendOutCom("A86");
        t.setReceiveCom("A8611");
        t.setInvaliDate("2004-11-28");
        t.setMakeDate("2005-1-10");
        t.setMakeTime("15:20");
        t.setModifyDate("2005-1-10");
        t.setModifyTime("15:20");
//    t.setOperateFlag("1");
        t.setState("9");
        setT.add(t);
        t = new LZCardSchema();
        t.setSendOutCom("0");           //分组条件
        t.setReceiveCom("0");
        t.setMakeDate("2006-3-10");
        t.setHandleDate("2006-3-10");
        t.setInvaliDate("");
        setT.add(t);

        GlobalInput tG = new GlobalInput();
        tG.ComCode = "8611";
        tG.Operator = "001";

        v.add(tG);
        v.add(setT);
        v.add( new Hashtable() );
        try{
            test.submitData(v,"QUERY||MAIN");
            }catch(Exception e){}

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
                return submitQueryMain();
            else if( m_strOperate.equals("QUERY||PRINT") )
                return submitQueryPrint();
            else if( m_strOperate.equals("REPORT||QUERY") )
                return submitReportQuery();
            else if( m_strOperate.equals("REPORT||PRINT") )
                return submitReportPrint();
            else if( m_strOperate.equals("REPORT||BLANCE"))
            	return submitReportBlance();
            else
            {
            	logger.debug("**********");
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

        cError.moduleName = "CertStatBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private String verifyOperate(String szOperate)
    {
        String szReturn = "";
        String szOperates[] = {"QUERY||MAIN", "QUERY||PRINT", "REPORT||QUERY", "REPORT||PRINT","REPORT||BLANCE"};

        for(int nIndex = 0; nIndex < szOperates.length; nIndex ++)
        {
            if( szOperate.equals(szOperates[nIndex]) )
            {
                szReturn = szOperate;
            }
        }

        return szReturn;
    }

    /**
     * 解析传入的条件，生成查询需要的Where条件。
     * @param aLZCardSet
     * @return
     * @throws Exception
     */
	private VData parseParams(LZCardSet aLZCardSet) throws Exception
    {
        LZCardSchema tLZCardSchema = null;

        String strSQL = "";

        tLZCardSchema = aLZCardSet.get(1);

        String strState = tLZCardSchema.getState();
        String strSendOutCom = tLZCardSchema.getSendOutCom();
        String strReceiveCom = tLZCardSchema.getReceiveCom();

        String strWhere = getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                        + getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"))
                        + getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"))
                        + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getStartNo(),"StartNo"), 0)
                        + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getEndNo(),"EndNo"), 0)
                        + getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"), sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1);
        
        if( strState.equals( CertStatBL.STAT_STOCK ) )
        {
            // 库存：只查出处于发放状态的单证
            strSQL = "SELECT * FROM LZCard WHERE StateFlag = '0' "
                   + strWhere
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if( strState.equals( CertStatBL.STAT_SENDED ) )
        {
            // 已发放：指单证从本级机构发放到下级机构的所有记录
            strSQL = "SELECT * FROM LZCardTrack WHERE OperateFlag = '0'"
                   + strWhere
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                   ;
        }
        else if( strState.equals( CertStatBL.STAT_UNCANCEL ) )
        {
            // 未核销：指已发放但未曾使用的单证数量
            strSQL = "SELECT * FROM LZCard WHERE 1=1" + strWhere
                   + " AND StateFlag = '0'"
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                   ;
        }
        else if( strState.equals( CertStatBL.STAT_GROSS ) )
        {
            // 入库总量：从轨迹表中查询出发放状态的单证
            if(strReceiveCom.equals("A86"))
            {
                strSQL = "select * from lzcardprint where state = '2' "
                       + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                       + getWherePart("StartNo", sfun(tLZCardSchema.getStartNo(),"StartNo"))
                       + getWherePart("EndNo", sfun(tLZCardSchema.getEndNo(),"EndNo"))
                       + getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"), sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1)
                       + " order by certifycode,startno,endno "
                ;
                SQLwithBindVariables sqlbv = new SQLwithBindVariables();
                sqlbv.sql(strSQL);
                sqlbv.put("StartNo", tLZCardSchema.getStartNo());
                sqlbv.put("EndNo", tLZCardSchema.getEndNo());
                sqlbv.put("CertifyCode", tLZCardSchema.getCertifyCode());
                sqlbv.put("Handler", tLZCardSchema.getHandler());
                sqlbv.put("Operator", tLZCardSchema.getOperator());
                sqlbv.put("date1", aLZCardSet.get(1).getMakeDate());
                sqlbv.put("date2", aLZCardSet.get(2).getMakeDate());
                sqlbv.put("SendOutCom", strSendOutCom);
                sqlbv.put("ReceiveCom", strReceiveCom);
                logger.debug("SQL : " + strSQL);
                VData tVData = new VData();
                tVData.add(0, sqlbv);

                return tVData;
            }
            else
            {
                //定义：上级发放过来的计入，回退到上级的刨去
                strSQL = "SELECT * FROM LZCardTrack a WHERE OperateFlag = '0'"
                       + strWhere
                       + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                       + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                       + " and not exists(select 1 from lzcardtrack "
                       + " where startno >= a.startno and endno <= a.endno "
                       + " and certifycode = a.certifycode and operateflag = '3'"
                       + " and sendoutcom = a.receivecom and receivecom = a.sendoutcom) ";
            }
        }
        else if (strState.equals(CertStatBL.STAT_ABANDON))
        {
            //统计正常作废的单证
            strSQL = "select * from lzcard where stateflag = '2' "
                   + strWhere
                   + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if (strState.equals(CertStatBL.STAT_MISS))
        {
            //统计遗失被盗的单证
            strSQL = "select * from lzcard where stateflag = '3' "
                   + strWhere
                   + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if (strState.equals(CertStatBL.STAT_HANDIN))
        {
            //统计缴销（正常回收）的单证
            strSQL = "select * from lzcard where stateflag = '1' "
                   + strWhere
                   + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }

        strSQL += " ORDER BY CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo";

        logger.debug("SQL : " + strSQL);
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(strSQL);
        sqlbv1.put("StartNo", tLZCardSchema.getStartNo());
        sqlbv1.put("EndNo", tLZCardSchema.getEndNo());
        sqlbv1.put("CertifyCode", tLZCardSchema.getCertifyCode());
        sqlbv1.put("Handler", tLZCardSchema.getHandler());
        sqlbv1.put("Operator", tLZCardSchema.getOperator());
        sqlbv1.put("date1", aLZCardSet.get(1).getMakeDate());
        sqlbv1.put("date2", aLZCardSet.get(2).getMakeDate());
        sqlbv1.put("SendOutCom", strSendOutCom);
        sqlbv1.put("ReceiveCom", strReceiveCom);
        VData tVData = new VData();
        tVData.add(0, sqlbv1);

        return tVData;
    }

    private String getWherePart(String strColName, String strColValue)
    {
        if( strColValue == null || strColValue.equals("") ) {
            return "";
        }
        return " AND " + strColName + " = '" + strColValue + "'";
    }
    

    private String getWherePart(String strCol1, String strCol2, String strCol3, int nFlag)
    {
        if( nFlag == 0 )
        {
            if( strCol3 == null || strCol3.equals("") )
                return "";

            return " AND " + strCol1 + " <= '" + strCol3
                    + "' AND " + strCol2 + " >= '" + strCol3 + "'";
        }
        else
        {
            String str = "";
            if( strCol2 != null && !strCol2.equals("") )
                str += " AND " + strCol1 + " >= '" + strCol2 + "'";
            if( strCol3 != null && !strCol3.equals("") )
                str += " AND " + strCol1 + " <= '" + strCol3 + "'";
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
     * 查询函数
     * @throws Exception
     */
    private boolean submitQueryMain() throws Exception
    {
        VData tVData = parseParams(m_LZCardSet);  // 解析查询条件
        tVData.add(1,m_LZCardSet);                // 在对结果筛选中也被使用

        mResult = genQueryResult(m_LZCardSet.get(1).getState(), // 取得要统计的标志
                                 tVData);  // 利用临时表对查询结果进行筛选
        return true;
    }

    /**
     * 查询打印所对应的参数解析函数
     * @param aLZCardSet
     * @return
     * @throws Exception
     */
    private VData parseParamsPrint(LZCardSet aLZCardSet) throws Exception
    {
        LZCardSchema tLZCardSchema = null;

        String strSQL = "";

        tLZCardSchema = aLZCardSet.get(1);
        String strState = tLZCardSchema.getState();
        String strWhere = getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                        + getWherePart("SendOutCom", sfun(tLZCardSchema.getSendOutCom(),"SendOutCom"))
                        + getWherePart("ReceiveCom", sfun(tLZCardSchema.getReceiveCom(),"ReceiveCom"))
                        + getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"))
                        + getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"))
                        + getWherePart("TakeBackNo", sfun(tLZCardSchema.getTakeBackNo(),"TakeBackNo"));

        if( strState.equals( CertStatBL.PRT_STATE ) )
        {
            strSQL = "SELECT * FROM LZCard WHERE 1=1";
        }
        else if( strState.equals( CertStatBL.PRT_TRACK ) )
        {
            strSQL = "SELECT * FROM LZCardTrack WHERE 1=1";
        }

        strSQL += strWhere;
        strSQL += " ORDER BY CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo";
        logger.debug("SQL : " + strSQL);
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(strSQL);
        sqlbv2.put("CertifyCode", tLZCardSchema.getCertifyCode());
        sqlbv2.put("Handler", tLZCardSchema.getHandler());
        sqlbv2.put("Operator", tLZCardSchema.getOperator());
        sqlbv2.put("SendOutCom", tLZCardSchema.getSendOutCom());
        sqlbv2.put("ReceiveCom", tLZCardSchema.getReceiveCom());
        sqlbv2.put("TakeBackNo", tLZCardSchema.getTakeBackNo());
        VData tVData = new VData();
        tVData.add(0, sqlbv2);

        return tVData;
    }

    /**
     * 查询打印所对应的功能函数
     * @return
     * @throws Exception
     */
    private boolean submitQueryPrint() throws Exception
    {
        mResult.clear();

        VData vData = parseParamsPrint(m_LZCardSet);

        String strSQL = (String)vData.getObjectByObjectName("String", 0);
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(strSQL);
        LZCardDB tLZCardDB = new LZCardDB();
        LZCardSet tLZCardSet = tLZCardDB.executeQuery(sqlbva);

        if( tLZCardDB.mErrors.needDealError() )
        {
            mErrors.copyAllErrors( tLZCardDB.mErrors );
            return false;
        }

        boolean bFlag = false; // 表示是从机构到业务员的单证发放操作

        // 外部的参数，指示是否需要使用不同的模板。
        if( getParams("NoUseAgentTemplate").equals("") )
        {
            if( tLZCardSet.size() > 0 )
            {
                LZCardSchema tLZCardSchema = tLZCardSet.get(1);

                if( tLZCardSchema.getReceiveCom().charAt(0) == 'D'
                    && tLZCardSchema.getOperateFlag().equals("0") )
                {
                    bFlag = true;
                }
            }
        }

        XmlExport xe = new XmlExport();

        if( bFlag )
            printAgent(xe, tLZCardSet);
        else
            printOther(xe, tLZCardSet);

        mResult.add( xe );
        return true;
    }

    // 一些辅助函数，用来将编码转换成名字
    private String getCertifyName(String strCertifyCode) throws Exception
    {
        LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
        tLMCertifyDesDB.setCertifyCode( strCertifyCode );

        if (!tLMCertifyDesDB.getInfo()) {
            mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
            throw new Exception("在取得LMCertifyDes的数据时发生错误");
        }

        return tLMCertifyDesDB.getCertifyName();
    }

    private String getComName(String strComCode) throws Exception
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

    private String getAgentName(String strAgentCode) throws Exception
    {
        LAAgentDB tLAAgentDB = new LAAgentDB();
        tLAAgentDB.setAgentCode(strAgentCode);
        if (!tLAAgentDB.getInfo()) {
            mErrors.copyAllErrors(tLAAgentDB.mErrors);
            throw new Exception("在取得LAAgent的数据时发生错误");
        }
        return tLAAgentDB.getName();
    }

    private String transComCode(String strComCode) throws Exception
    {
        if( strComCode.trim().equals("") ) {
            return strComCode;
        }

        if( strComCode.charAt(0) == 'A' )
            return getComName( strComCode.substring(1) );
        else if( strComCode.charAt(0) == 'B' )
            throw new Exception("目前还不支持");
        else if( strComCode.charAt(0) == 'D' )
            return getAgentName( strComCode.substring(1) );
        else
            return strComCode;
    }

    /**
     * Kevin 2003-05-08
     * 针对于发放单证到业务员的情况的打印逻辑
     * @param xe
     * @param aLZCardSet
     * @throws Exception
     */
    private void printAgent(XmlExport xe, LZCardSet aLZCardSet) throws Exception
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
            String tDate = yLZCardSchema.getMakeDate();                         // 领取时间
            String tNameReceiveCom = transComCode(yLZCardSchema.getReceiveCom());
            String tCodeReceiveCom = "";

            if(yLZCardSchema.getReceiveCom().length()>=1)
            {
                tCodeReceiveCom = yLZCardSchema.getReceiveCom().substring(1);   // 获取工号
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
            values[5] = "Fee";                  // 单价
            values[6] = "FeeCount";             // 单证费用
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
    private void printOther(XmlExport xe, LZCardSet aLZCardSet) throws Exception
    {
        xe.createDocument("CertifyListEx.vts", "");

        int nLen = 6;  // 列表数据的列数
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

            lt.add( values );
        }

        values = new String[nLen];

        values[0] = "SendOutCom";
        values[1] = "ReceiveCom";
        values[2] = "CertifyCode";
        values[3] = "StartNo";
        values[4] = "EndNo";
        values[5] = "SumCount";

        xe.addListTable(lt, values);

        String CurrentDate = PubFun.getCurrentDate();

        TextTag texttag=new TextTag();
        texttag.add("ManageCom", getComName(m_GlobalInput.ManageCom))  ;
        texttag.add("Operator",m_GlobalInput.Operator);
        texttag.add("time",CurrentDate);
        xe.addTextTag(texttag);
    }

    /**
     * Kevin 2003-06-18
     * 针对于单证统计报表打印
     * @param xe
     * @param aLZCardSet
     * @throws Exception
     */
    private void printReport(XmlExport xe, LZCardSet aLZCardSet) throws Exception
    {
        xe.createDocument("CertifyReport.vts", "");

        // get latest print price of each kind of certify
        Hashtable hashPrice = new Hashtable();

        String strSQL = "SELECT CertifyCode, CertifyPrice FROM LZCardPrint A "
                      + "WHERE PrtNo = "
                      + "(SELECT MAX(PrtNo) FROM LZCardPrint B WHERE B.CertifyCode = A.CertifyCode) "
                      + "ORDER BY CertifyCode";
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(strSQL);
        ExeSQL es = new ExeSQL();
        SSRS ssrs = es.execSQL(sqlbv3);
        if( ssrs.mErrors.needDealError() ) {
            mErrors.copyAllErrors(ssrs.mErrors);
            throw new Exception("取单证印刷费用失败！");
        }

        String strCertifyCode = "";

        for(int nIndex = 0; nIndex < ssrs.getMaxRow(); nIndex ++)
        {
            strCertifyCode = ssrs.GetText(nIndex + 1, 1);
            hashPrice.put(strCertifyCode, ssrs.GetText(nIndex + 1, 2));
        }

        // put data to xmlexport
        int nLen = 7;                      // 列表数据的列数
        String[] values = new String[nLen];
        double dMoney = 0.0;               // print cost
        double dSumMoney = 0.0;            // sum print cost
        double dSumCount = 0.0;            // sum count

        ListTable lt = new ListTable();
        lt.setName("Info");

        for(int nIndex = 0; nIndex < aLZCardSet.size(); nIndex ++)
        {
            values = new String[nLen];

            LZCardSchema tLZCardSchema = aLZCardSet.get(nIndex + 1);
            strCertifyCode = tLZCardSchema.getCertifyCode();

            values[0] = strCertifyCode;
            values[1] = getCertifyName( tLZCardSchema.getCertifyCode() );
            values[2] = transComCode( tLZCardSchema.getSendOutCom() );
            values[3] = transComCode( tLZCardSchema.getReceiveCom() );
            values[4] = String.valueOf( tLZCardSchema.getSumCount() );
            values[5] = (String)hashPrice.get(strCertifyCode);

            dSumCount += tLZCardSchema.getSumCount();
            dMoney = tLZCardSchema.getSumCount() * Double.parseDouble(values[5]);
            dSumMoney += dMoney;

            values[6] = new DecimalFormat("0.00").format(dMoney);

            lt.add( values );
        }

        values = new String[nLen];

        values[0] = "CertifyCode";
        values[1] = "CertifyName";
        values[2] = "SendOutCom";
        values[3] = "ReceiveCom";
        values[4] = "SumCount";
        values[5] = "Price";
        values[6] = "PrintMoney";

        xe.addListTable(lt, values);

        TextTag tag = new TextTag();
        tag.add("SumMoney", new DecimalFormat(".00").format(dSumMoney));
        tag.add("SumCount", new DecimalFormat("0").format(dSumCount));
        xe.addTextTag(tag);
    }

    /**
     * 2003-05-09 Kevin
     * 利用临时表对第一次得到的结果进行筛选，从而得到实际的查询结果
     * @param strState 统计状态（库存、已发放、已回收、发放未回收、发放总量）
     * @param aQueryData
     * @return
     */
    private VData genQueryResult(String strState, VData aQueryData) throws Exception
    {
        VData tResult = new VData();
        String strTemp = "";
        String strSQL = (String)aQueryData.get(0);  // 取得详细信息
        LZCardSet tLZCardSet = (LZCardSet)aQueryData.get(1);
        String ReceiveCom = tLZCardSet.get(1).getReceiveCom() ;
        
        logger.debug("genQueryResult strSQL:"+strSQL);

        if( strState.equals( CertStatBL.STAT_STOCK ) || strState.equals( CertStatBL.STAT_GROSS )
            || strState.equals( CertStatBL.STAT_ABANDON ) || strState.equals( CertStatBL.STAT_MISS )
            || strState.equals( CertStatBL.STAT_HANDIN )|| strState.equals( CertStatBL.STAT_UNCANCEL ))
        {
            // 库存和入库总量：不需要使用临时表，直接使用解析得到的SQL语句
            // 原来返回查询后的结果集，现在返回查询前的SQL语句。
            // 在processData函数中也将做同样的改动。
            tResult.add( strSQL );

        }
        else if( strState.equals( CertStatBL.STAT_SENDED ))
        {

            processData(strState, strSQL, tResult);
            // 得到查询语句
            strSQL = (String)tResult.get(0);
        }

        // 格式化要输出的列
        // 将原来SQL语句中的“SELECT *”改成所要的列。
        if(ReceiveCom.equals("A86") && strState.equals( CertStatBL.STAT_GROSS) )
        {
            strTemp = "select certifycode,'00',concat('A',managecom),startno,endno,sumcount,operatorget "
                    + strSQL.substring( strSQL.indexOf('*') + 1 );
        }
        else
        {
            strTemp = "SELECT CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo, SumCount, Operator"
                    + strSQL.substring( strSQL.indexOf('*') + 1 );
        }

        tResult.set(0, strTemp);

        // 取总数：将原来SQL语句中的“SELECT *”改成“SELECT NVL(SUM(SumCount), 0)”
        strTemp = "SELECT (case when SUM(SumCount) is null then 0 else SUM(SumCount) end)"
                + strSQL.substring( strSQL.indexOf('*') + 1 );
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(strTemp);
        ExeSQL es = new ExeSQL();
        SSRS ssrs = es.execSQL(sqlbv4);

        if( es.mErrors.needDealError() ) {
            throw new Exception( es.mErrors.getFirstError() );
        }

        tResult.add( ssrs.GetText(1, 1) );

        return tResult;
    }


    /**
     * 2003-05-09 Kevin
     * 利用临时表对第一次得到的结果进行筛选，从而得到实际的查询结果
     * @param strState
     * @param strSQL
     * @param aResult
     * @throws Exception
     */
    private void processData(String strState, String strSrcSQL, VData aResult) throws Exception
    {
        String strOper = m_GlobalInput.Operator;

        //限制查询得到的结果条数，过多的话会对系统内存造成压力
        String strSQL = "select count(startno) from lzcardtrack "
                      + strSrcSQL.substring( strSrcSQL.indexOf("WHERE") );
        logger.debug("查询记录的条数 : " + strSQL);
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(strSQL);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv5);
        if (tssrs.getMaxRow() > 0)
        {
            int count = Integer.parseInt(tssrs.GetText(1,1));
            if(count > 500)
                throw new Exception("查询的结果条数过多，请输入更多的查询条件或缩短查询时间！");
        }

        // 将数据存放到临时表中
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql("DELETE FROM LZCardTemp WHERE QueryOper = '" + "?strOper?" + "'");
        sqlbv6.put("strOper", strOper);
        new ExeSQL().execUpdateSQL(sqlbv6);
        strSQL = "INSERT INTO LZCardTemp SELECT * FROM LZCardTrack, (SELECT '" + "?strOper?" + "' FROM DUAL) "
               + strSrcSQL.substring( strSrcSQL.indexOf("WHERE") );
        logger.debug(strSQL);
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        sqlbv7.sql(strSQL);
        sqlbv7.put("strOper", strOper);
        new ExeSQL().execUpdateSQL(sqlbv7);

        String[] strFlagDel = null;  // 要删除数据的标志
        String[] strFlagLeft = null;  // 要保留数据的标志

        if( strState.equals( CertStatBL.STAT_SENDED ) )
        {
            strFlagDel = new String[1];
            strFlagDel[0] = "2";

            strFlagLeft = new String[1];
            strFlagLeft[0] = "0";

        }

        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        LZCardTempDB tLZCardTempDB = null;
        LZCardTempSet tLZCardTempSet = null;
        LZCardTempSchema tOldLZCardTempSchema = null;
        LZCardTempSchema tNewLZCardTempSchema = new LZCardTempSchema();
        int nNoLen = 0;

        if( conn == null ) {
            throw new Exception("连接数据库失败！");
        }

        try
        {
            stmt = conn.createStatement();

            for(int nIndex = 0; nIndex < strFlagLeft.length; nIndex ++)
            {
                strSQL = "SELECT ROWID FROM LZCardTemp WHERE QueryOper = '"
                           + strOper + "' AND OperateFlag = '"
                           + strFlagDel[nIndex] + "' ORDER BY ModifyDate DESC, ModifyTime DESC";
//                SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
//                sqlbv2.sql(strSQL);
//                sqlbv2.put("QueryOper", strOper);
//                sqlbv2.put("OperateFlag", strFlagDel[nIndex]);
                rs = stmt.executeQuery( strSQL );

                while( rs.next() )
                {
                    strSQL = "SELECT * FROM LZCardTemp WHERE ROWID = '" + "?ROWID?" + "'";
                    logger.debug(strSQL);
                    SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
                    sqlbv8.sql(strSQL);
                    sqlbv8.put("ROWID", rs.getString("ROWID"));
                    tOldLZCardTempSchema = new LZCardTempDB().executeQuery(sqlbv8).get(1);

                    strSQL = "SELECT * FROM LZCardTemp WHERE ROWID IN ("  +
                             "SELECT ROWID FROM LZCARDTEMP WHERE EndNo >= '" + "?StartNo?" +
                             "' AND StartNo <= '" + "?StartNo?" +
                             "' AND EndNo >= '" + "?EndNo?" +
                             "' AND StartNo <= '" + "?EndNo?" +
                             "' AND ReceiveCom = '" + "?ReceiveCom?" +
                             "' AND CertifyCode = '" + "?CertifyCode?" +
                             "' AND SendOutCom = '" + "?SendOutCom?" +
                             "'  AND QueryOper = '" + "?QueryOper?" +
                             "') AND OperateFlag = '" + "?OperateFlag?" +
                             "' AND ModifyDate < '" + "?ModifyDate?" + "'";
                    strSQL = strSQL +
                             " union SELECT * FROM LZCardTemp WHERE ROWID IN ("  +
                             "SELECT ROWID FROM LZCARDTEMP WHERE EndNo >= '" + "?StartNo?" +
                             "' AND StartNo <= '" + "?StartNo?" +
                             "' AND EndNo >= '" + "?EndNo?" +
                             "' AND StartNo <= '" + "?EndNo?" +
                             "' AND ReceiveCom = '" + "?ReceiveCom?" +
                             "' AND CertifyCode = '" + "?CertifyCode?" +
                             "' AND SendOutCom = '" + "?SendOutCom?" +
                             "'  AND QueryOper = '" + "?QueryOper?" +
                             "') AND OperateFlag = '" + "?OperateFlag?" +
                             "' AND ModifyDate < '" + "?ModifyDate?" + 
                             "' AND ModifyTime <= '" + "?ModifyTime?"+ "' ";
                    logger.debug(strSQL);

                    // 删除需要删除的数据
                    tLZCardTempDB = new LZCardTempDB();
                    tLZCardTempDB.setSchema( tOldLZCardTempSchema );
                    tLZCardTempDB.delete();

                    // 查询出要拆分的数据
                    SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
                    sqlbv9.sql(strSQL);
                    sqlbv9.put("StartNo", tOldLZCardTempSchema.getStartNo());
                    sqlbv9.put("EndNo", tOldLZCardTempSchema.getEndNo());
                    sqlbv9.put("ReceiveCom", tOldLZCardTempSchema.getSendOutCom());
                    sqlbv9.put("CertifyCode", tOldLZCardTempSchema.getCertifyCode());
                    sqlbv9.put("SendOutCom", tOldLZCardTempSchema.getReceiveCom());
                    sqlbv9.put("QueryOper", strOper);
                    sqlbv9.put("OperateFlag", strFlagLeft[nIndex]);
                    sqlbv9.put("ModifyDate", tOldLZCardTempSchema.getModifyDate());
                    sqlbv9.put("ModifyTime", tOldLZCardTempSchema.getModifyTime());
                    tLZCardTempSet = new LZCardTempDB().executeQuery(sqlbv9);

                    if( tLZCardTempSet.size() < 1 ) {
                        continue;
                    }

                    nNoLen = CertifyFunc.getCertifyNoLen( tOldLZCardTempSchema.getCertifyCode() );

                    tNewLZCardTempSchema.setSchema( tLZCardTempSet.get(1) );

                    // 删除旧的单证
                    tLZCardTempDB = new LZCardTempDB();
                    tLZCardTempDB.setSchema( tNewLZCardTempSchema );
                    tLZCardTempDB.delete();

                    // 拆分第一部分单证
                    tNewLZCardTempSchema.setEndNo(
                            CertifyFunc.bigIntegerPlus(
                            tOldLZCardTempSchema.getStartNo(), "-1",  nNoLen));

                    tNewLZCardTempSchema.setSumCount(
                            (int)CertifyFunc.bigIntegerDiff(
                            tNewLZCardTempSchema.getEndNo(),
                            tNewLZCardTempSchema.getStartNo()) + 1);

                    if( tNewLZCardTempSchema.getSumCount() > 0 ) {
                        tLZCardTempDB = new LZCardTempDB();
                        tLZCardTempDB.setSchema( tNewLZCardTempSchema );
                        tLZCardTempDB.insert();
                    }

                    // 拆分第二部分单证
                    tNewLZCardTempSchema.setSchema( tLZCardTempSet.get(1) );

                    tNewLZCardTempSchema.setStartNo(
                            CertifyFunc.bigIntegerPlus(
                            tOldLZCardTempSchema.getEndNo(), "1",  nNoLen));

                    tNewLZCardTempSchema.setSumCount(
                            (int)CertifyFunc.bigIntegerDiff(
                            tNewLZCardTempSchema.getEndNo(),
                            tNewLZCardTempSchema.getStartNo()) + 1);

                    if( tNewLZCardTempSchema.getSumCount() > 0 ) {
                        tLZCardTempDB = new LZCardTempDB();
                        tLZCardTempDB.setSchema( tNewLZCardTempSchema );
                        tLZCardTempDB.insert();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            if( stmt != null ) {
                stmt.close();
                stmt = null;
            }

            if( rs != null ) {
                rs.close();
                rs = null;
            }

            if( conn != null ) {
                conn.close();
                conn = null;
            }
        }

        strSQL = "SELECT * FROM LZCardTemp WHERE QueryOper = '" + "?QueryOper?" + "'";
        strSQL += " ORDER BY CertifyCode, SendOutCom, ReceiveCom, StartNo, EndNo";
        SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
        sqlbv10.sql(strSQL);
        sqlbv10.put("QueryOper", strOper);
        // aResult.add(new LZCardDB().executeQuery(strSQL));
        // 原来返回查询后的结果集，现在返回查询前的SQL语句。
        aResult.add(sqlbv10);
    }

    private String getParams(String strKey)
    {
        if( m_hashParams == null ) {
            return "";
        }

        String str = (String)m_hashParams.get(strKey);

        if( str == null )
            return "";
        else
            return str;
    }



    /**
     * 解析传入的条件，生成查询需要的Where条件。
     * @param aLZCardSet
     * @return
     * @throws Exception
     */
    //领取结算的解析sql函数
    private VData parseParamsBlanceReport(LZCardSet aLZCardSet) throws Exception
    {
        LZCardSchema tLZCardSchema = null;

        // 设置分组条件
        tLZCardSchema = aLZCardSet.get(2);
        if( tLZCardSchema.getSendOutCom() == null || tLZCardSchema.getSendOutCom().equals("")
            || tLZCardSchema.getReceiveCom() == null || tLZCardSchema.getReceiveCom().equals("") )
        {
            throw new Exception("缺少必要的参数");
        }

        // 设置其它条件
        tLZCardSchema = aLZCardSet.get(1);
        String strSendOutCom = tLZCardSchema.getSendOutCom();
        String strReceiveCom = tLZCardSchema.getReceiveCom();

        String sql = "select certifycode,sum(sumamount) from ("
                   + "select certifycode,sum(sumcount) sumamount from lzcardtrack "
                   + "where 1 = 1 "
                   + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                   + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                   + getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"))
                   + getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"))
                   + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getStartNo(),"StartNo"), 0)
                   + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getEndNo(),"EndNo"), 0)
                   + getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"), sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1)
                   + " group by certifycode "
                   + " union all "
                   + "select certifycode,-sum(sumcount) sumamount from lzcardtrack "
                   + "where 1 = 1 "
                   + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                   + getWherePart("SendOutCom", sfun(strReceiveCom,"ReceiveCom"))
                   + getWherePart("ReceiveCom", sfun(strSendOutCom,"SendOutCom"))
                   + getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"))
                   + getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"))
                   + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getStartNo(),"StartNo"), 0)
                   + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getEndNo(),"EndNo"), 0)
                   + getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"), sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1)
                   + " group by certifycode "
                   + ") group by certifycode order by certifycode ";
        logger.debug("SQL : " + sql);
        SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
        sqlbv11.sql(sql);
        sqlbv11.put("StartNo", tLZCardSchema.getStartNo());
        sqlbv11.put("EndNo", tLZCardSchema.getEndNo());
        sqlbv11.put("CertifyCode", tLZCardSchema.getCertifyCode());
        sqlbv11.put("Handler", tLZCardSchema.getHandler());
        sqlbv11.put("Operator", tLZCardSchema.getOperator());
        sqlbv11.put("date1", aLZCardSet.get(1).getMakeDate());
        sqlbv11.put("date2", aLZCardSet.get(2).getMakeDate());
        sqlbv11.put("SendOutCom", strSendOutCom);
        sqlbv11.put("ReceiveCom", strReceiveCom);
        VData tVData = new VData();
        tVData.add(0, sqlbv11);

        return tVData;
    }

    // 下面是单证统计报表的相关功能模块

    /**
     * 解析传入的条件，生成查询需要的Where条件。
     * @param aLZCardSet
     * @return
     * @throws Exception
     */
    private VData parseParamsReport(LZCardSet aLZCardSet) throws Exception
    {
        LZCardSchema tLZCardSchema = null;

        // 设置分组条件
        tLZCardSchema = aLZCardSet.get(2);

        String strSQL = "SELECT CertifyCode";
        String strGroupBy = " GROUP BY CertifyCode";

        if( tLZCardSchema.getSendOutCom() == null || tLZCardSchema.getSendOutCom().equals("")
            || tLZCardSchema.getReceiveCom() == null || tLZCardSchema.getReceiveCom().equals("") )
        {
            throw new Exception("缺少必要的参数");
        }

        if( tLZCardSchema.getSendOutCom().equals("0") )
        {
            strSQL += ", ' '";
        }
        else if( tLZCardSchema.getSendOutCom().equals("1") )
        {
            strSQL += ", SendOutCom";
            strGroupBy += ", SendOutCom";
        }

        if( tLZCardSchema.getReceiveCom().equals("0") )
        {
            strSQL += ", ' '";
        }
        else if( tLZCardSchema.getReceiveCom().equals("1") )
        {
            strSQL += ", ReceiveCom";
            strGroupBy += ", ReceiveCom";
        }

        strSQL += ", SUM(SumCount) FROM";

        // 设置其它条件
        tLZCardSchema = aLZCardSet.get(1);

        String strState = tLZCardSchema.getState();
        String strSendOutCom = tLZCardSchema.getSendOutCom();
        String strReceiveCom = tLZCardSchema.getReceiveCom();

        String strWhere = getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                + getWherePart("Handler", sfun(tLZCardSchema.getHandler(),"Handler"))
                + getWherePart("Operator", sfun(tLZCardSchema.getOperator(),"Operator"))
                + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getStartNo(),"StartNo"), 0)
                + getWherePart("StartNo", "EndNo", sfun(tLZCardSchema.getEndNo(),"EndNo"), 0)
                + getWherePart("MakeDate", sfun(aLZCardSet.get(1).getMakeDate(),"date1"), sfun(aLZCardSet.get(2).getMakeDate(),"date2"), 1);
        if( strState.equals( CertStatBL.STAT_STOCK ) )
        {
            // 库存：只查出处于发放状态的单证
            strSQL += " LZCard WHERE StateFlag = '0' "
                    + strWhere
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if( strState.equals( CertStatBL.STAT_SENDED ) )
        {
            // 已发放（轨迹表）：处于发放状态的单证 - 处于反发放状态的单证
            strSQL += "( SELECT CertifyCode, SendOutCom, ReceiveCom, SumCount"
                    + " FROM LZCardTrack WHERE OperateFlag = '0'"
                    + strWhere
                    + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                    + ")";

        }
        else if( strState.equals( CertStatBL.STAT_UNCANCEL ) )
        {
            // 未核销：指已发放但未曾使用的单证数量
            strSQL += " LZCard WHERE StateFlag = '0' "
                    + strWhere
                    + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                      ;
        }
        else if( strState.equals( CertStatBL.STAT_GROSS ) )
        {
            // 入库总量：从轨迹表中查询出发放状态的单证
            strSQL += " LZCardTrack a WHERE OperateFlag = '0' "
                    + strWhere
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"))
                    + " and not exists(select 1 from lzcardtrack "
                    + " where startno >= a.startno and endno <= a.endno "
                    + " and certifycode = a.certifycode and operateflag = '3'"
                    + " and sendoutcom = a.receivecom and receivecom = a.sendoutcom) ";
        }
        else if (strState.equals(CertStatBL.STAT_ABANDON))
        {
            //统计正常作废的单证
            strSQL += "lzcard where stateflag = '2' "
                    + strWhere
                    + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                    + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if (strState.equals(CertStatBL.STAT_MISS))
        {
            //统计遗失被盗的单证
            strSQL += "lzcard where stateflag = '3' "
                    + strWhere
                    + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                    + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }
        else if (strState.equals(CertStatBL.STAT_HANDIN))
        {
            //统计缴销（正常回收）的单证
            strSQL += "lzcard where stateflag = '1' "
                    + strWhere
                    + getWherePart("CertifyCode", sfun(tLZCardSchema.getCertifyCode(),"CertifyCode"))
                    + getWherePart("SendOutCom", sfun(strSendOutCom,"SendOutCom"))
                    + getWherePart("ReceiveCom", sfun(strReceiveCom,"ReceiveCom"));
        }

        strSQL += strGroupBy;
        strSQL += " ORDER BY CertifyCode";

        logger.debug("SQL : " + strSQL);
        SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
        sqlbv12.sql(strSQL);
        sqlbv12.put("StartNo", tLZCardSchema.getStartNo());
        sqlbv12.put("EndNo", tLZCardSchema.getEndNo());
        sqlbv12.put("CertifyCode", tLZCardSchema.getCertifyCode());
        sqlbv12.put("Handler", tLZCardSchema.getHandler());
        sqlbv12.put("Operator", tLZCardSchema.getOperator());
        sqlbv12.put("date1", aLZCardSet.get(1).getMakeDate());
        sqlbv12.put("date2", aLZCardSet.get(2).getMakeDate());
        sqlbv12.put("SendOutCom", strSendOutCom);
        sqlbv12.put("ReceiveCom", strReceiveCom);
        VData tVData = new VData();
        tVData.add(0, sqlbv12);

        return tVData;
    }


    /**
     * 统计报表的查询功能
     * @return
     */
    private boolean submitReportBlance() throws Exception
    {
        logger.debug("*** In submitReportBlance() ***");
        VData tVData = parseParamsBlanceReport(m_LZCardSet);  // 解析查询条件

        String strSQL = (String)tVData.getObject(0);
        SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
        sqlbvb.sql(strSQL);
        ExeSQL es = new ExeSQL();
        SSRS rs = es.execSQL(sqlbvb);

        if( es.mErrors.needDealError() ) {
            mErrors.copyAllErrors(es.mErrors);
            return false;
        }

        String inSendOutCom = m_LZCardSet.get(1).getSendOutCom();
        String inReceiveCom = m_LZCardSet.get(1).getReceiveCom();
        LZCardSet tLZCardSet = new LZCardSet();
        for(int nIndex = 0; nIndex < rs.MaxRow; nIndex ++)
        {
            LZCardSchema tLZCardSchema = new LZCardSchema();

            tLZCardSchema.setCertifyCode(rs.GetText(nIndex + 1, 1));
            tLZCardSchema.setSendOutCom(inSendOutCom);
            tLZCardSchema.setReceiveCom(inReceiveCom);
            tLZCardSchema.setSumCount(rs.GetText(nIndex + 1, 2));

            tLZCardSet.add(tLZCardSchema);
        }

        mResult.clear();
        mResult.add(tLZCardSet);

        return true;
    }


    /**
     * 统计报表的查询功能
     * @return
     */
    private boolean submitReportQuery() throws Exception
    {
        VData tVData = parseParamsReport(m_LZCardSet);  // 解析查询条件

        String strSQL = (String)tVData.getObject(0);
        SQLwithBindVariables sqlbvc = new SQLwithBindVariables();
        sqlbvc.sql(strSQL);
        ExeSQL es = new ExeSQL();
        SSRS rs = es.execSQL(sqlbvc);

        if( es.mErrors.needDealError() ) {
            mErrors.copyAllErrors(es.mErrors);
            return false;
        }

        LZCardSet tLZCardSet = new LZCardSet();

        for(int nIndex = 0; nIndex < rs.MaxRow; nIndex ++)
        {
            LZCardSchema tLZCardSchema = new LZCardSchema();

            tLZCardSchema.setCertifyCode(rs.GetText(nIndex + 1, 1));
            tLZCardSchema.setSendOutCom(rs.GetText(nIndex + 1, 2));
            tLZCardSchema.setReceiveCom(rs.GetText(nIndex + 1, 3));
            tLZCardSchema.setSumCount(rs.GetText(nIndex + 1, 4));

            tLZCardSet.add(tLZCardSchema);
        }

        mResult.clear();
        mResult.add(tLZCardSet);

        return true;
    }

    /**
     * 统计报表的打印功能
     * @return
     */
    private boolean submitReportPrint() throws Exception
    {
        mResult.clear();

        XmlExport xe = new XmlExport();
        printReport(xe, m_LZCardSet);
        mResult.add( xe );
        return true;
    }
}

