package com.sinosoft.lis.ebusiness;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.util.*;

/**
 * <p>Title: Web电子商务系统</p>
 * <p>Description: 内网综合查询调用接口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft</p>
 * @author zy
 * @version 1.0
 */
public class TBInfoBL
{
private static Logger logger = Logger.getLogger(TBInfoBL.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();

    /** 传送数据的容器 */
    private VData mOutputData = new VData();

    /** 返回数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    public TBInfoBL()
    {
    }

    public Vector submitData(Vector inParam)
    {
        String strAct = (String) inParam.get(0);
        logger.debug("==> Begin to TBInfoBL"+strAct);
        if (strAct.equals("LOGIN||AGENT"))
        {
            if (!execLoginagent(inParam))
            {
                // @@ 错误处理
                mResult.add("代理人注册失败"); //注册：业务员注册判断信息出错
            }
            return mResult;
        }

        else if (strAct.equals("CHECK"))
        {
            if (!execcheck(inParam))
            {
                // @@ 错误处理
                mResult.add("代理人登陆校验信息失败"); //注册：业务员登陆信息出错
            }
            return mResult;
        }
        else if ("QUERY||AGENT".equals(strAct))
        {
            if (!queryAgent(inParam))
            {
                // @@ 错误处理
                mResult.add("代理人信息核实失败"); //注册：业务员登陆信息出错
            }
            return mResult;
        }
        else if ("QUERYPOLFORAGENT".equals(strAct))
        {
            if (!execQueryCus(inParam))
            {
                // @@ 错误处理
                mResult.add("老客户查询失败"); //注册：业务员登陆信息出错
            }
            return mResult;
        }

        return mResult;
    }

    /**
     * 报户注册
     * @param inParam
     * @return
     */
    private boolean execLogin(Vector inParam)
    {
        mResult.clear();
        mResult.add("SUCCESS");

        //注册时通过新客户注册请求，返回客户号
        String cFlag = (String) inParam.get(1);

        String CustomerNo = "";
        CustomerNo = PubFun1.CreateMaxNo(cFlag, "SN");
        logger.debug("CustomerNo:-------" + CustomerNo);

        //返回客户号
        mResult.add(CustomerNo);
        return true;
    }

    private boolean execLoginagent(Vector inParam)
    {
        mResult.clear();
        mResult.add("SUCCESS");

        //注册时通过新客户注册请求，返回客户号
        String AgentCode = (String) inParam.get(1);
        String name = (String) inParam.get(2);
        String sex = (String) inParam.get(3);
        String birthday = (String) inParam.get(4);
        String idno = (String) inParam.get(5);
        String  Managecom= (String) inParam.get(6);


        String SQL = "select branchtype,( Case branchtype When 'EC' Then '电子商务虚拟代理人' When '1' Then '个险代理人' When '2' Then '团险直销代理人' When '3' Then '银行代理' When '4' Then '续收员' When '6' Then '联办代理' When '7' Then '新中介' When '8' Then '西点工程' When '9' Then '中介续收督导' When '99' Then '收展' else '其他' End) "
        		   + "from laagent where managecom like concat('"+"?Managecom?"+"','%') and agentcode='"+ "?AgentCode?" + "' "
        		   + "and name='" + "?name?" + "' and sex='" + "?sex?" + "' and birthday='" + "?birthday?" + "' and idno='" + "?idno?" + "' "
        		   + "and agentstate<='02'";
//        logger.debug("CustomerNo:-------" + Flag);
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(SQL);
        sqlbva.put("Managecom", Managecom);
        sqlbva.put("AgentCode", AgentCode);
        sqlbva.put("name", name);
        sqlbva.put("sex", sex);
        sqlbva.put("birthday", birthday);
        sqlbva.put("idno", idno);
        
        String strReturn = execSql(sqlbva);
        String strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
            mResult.clear();
            mResult.add("FAIL");
        }
        mResult.add(strReturn);
        return true;
    }

    private boolean execcheck(Vector inParam)
    {
        mResult.clear();
        mResult.add("SUCCESS");

        //注册时通过新客户注册请求，返回客户号
//        logger.debug("here");
//        logger.debug((String) inParam.get(1));
        String AgentCode = (String) inParam.get(1);

        String Flag = "";
        String SQL = "select count(*) from laagent where agentcode='"+"?AgentCode?"+"' and agentstate<='02'";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(SQL);
        sqlbv2.put("AgentCode",AgentCode);
        ExeSQL tSQL = new ExeSQL();
        Flag = tSQL.getOneValue(sqlbv2);
//        logger.debug("CustomerNo:-------" + Flag);
        mResult.add(Flag);
        return true;
    }
    //查询业务员
  private boolean queryAgent(Vector inParam)
  {
      mResult.clear();
      mResult.add("SUCCESS");
      String strAgentcode = (String) inParam.get(1);
      String strName = (String) inParam.get(2);

      String tSQL = " select name, agentcode, sex, (select gradename from laagentgrade b ,latree c  where  a.agentcode=c.agentcode and c.agentgrade=b.gradecode), mobile,email,(select name from ldcom where comcode = a.managecom),"
    	  		  + "employdate,( Case branchtype When 'EC' Then '电子商务虚拟代理人' When '1' Then '个险代理人' When '2' Then '团险直销代理人' When '3' Then '银行代理' When '4' Then '续收员' When '6' Then '联办代理' When '7' Then '新中介' When '8' Then '西点工程' When '9' Then '中介续收督导' When '99' Then '收展' else '其他' End) "
    	  		  + " from laagent a  where a.outworkdate is null  and agentcode ='"+"?strAgentcode?"+"' and name ='"+"?strName?"+"'";

      SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
      sqlbvb.sql(tSQL);
      sqlbvb.put("strAgentcode", strAgentcode);
      sqlbvb.put("strName", strName);
      
      String strReturn = execSql(sqlbvb);
      String strR = strReturn.substring(0, 3);
      if (strR.equals("100"))
      {
          mResult.clear();
          mResult.add("FAIL");
      }
      mResult.add(strReturn);

      return true;
  }
  
  private boolean execQueryCus(Vector inParam)
  {
      mResult.clear();
      mResult.add("SUCCESS");

      String strAgentCode = (String) inParam.get(1);

      if ((strAgentCode == null) || strAgentCode.equals(""))
      {
          mResult.clear();
          mResult.add("FAIL");
          mResult.add("代理人编码为空"); //查询：代理人号错误

          return false;
      }
      String strCount = (String) inParam.get(2);
      String strIndex = (String) inParam.get(3);

      int nCount = Integer.parseInt(strCount);
      int nIndex = Integer.parseInt(strIndex);
      int nBegin = (nCount * (nIndex - 1)) + 1;
      int nEnd = nCount * nIndex;
      if ((nBegin > nEnd) || (nBegin <= 0))
      {
          mResult.clear();
          mResult.add("FAIL");

          return false;
      }
      String cSQL ="select count(*) from  lccont where  appflag = '1' and agentcode ='"+"?strAgentCode?"+"'";
      SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
      sqlbv4.sql(cSQL);
      sqlbv4.put("strAgentCode",strAgentCode);
      ExeSQL tExeSQL = new ExeSQL();
      String cReturn = tExeSQL.getEncodedResult(sqlbv4, 1);
      String cstrR = cReturn.substring(0, 3);
      if (cstrR.equals("100"))
      {
          mResult.clear();
          mResult.add("FAIL");
      }
      mResult.add(cReturn);
      //查询出所有续期缴费信息
      String strSQL="";
      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	   strSQL ="select * from (select rownum r, a.contno,(select count(*) from ljspay where othernotype = '2' and otherno = a.contno),b.appntname,b.appntsex,b.appntbirthday,"
    			  +"c.homeaddress,c.postaladdress,c.phone,c. mobile,c.email, b.worktype from lccont a, lcappnt b,lcaddress c  "
    			  + "where a.contno=b.contno and a.appntno=b.appntno and b.appntno=c.customerno  "
    			  + "and b.addressno=c.addressno and appflag = '1' and agentcode ='"+"?strAgentCode?"+"' order by b.appntno) "
    			  +"where r>="+"?nBegin?"+ " and r<="+"?nEnd?";
      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	  strSQL ="select * from (select (@rownum := @rownum + 1) AS r, t1.contno,(select count(*) from ljspay where othernotype = '2' and otherno = t1.contno),t1.appntname,t1.appntsex,t1.appntbirthday,"
    			  +"t1.homeaddress,t1.postaladdress,t1.phone,t1. mobile,t1.email, t1.worktype from (select @rownum:= 0, a.contno,(select count(*) from ljspay where othernotype = '2' and otherno = a.contno),b.appntname,b.appntsex,b.appntbirthday,"
    			  +"c.homeaddress,c.postaladdress,c.phone,c. mobile,c.email, b.worktype from lccont a, lcappnt b,lcaddress c  "
    			  + "where a.contno=b.contno and a.appntno=b.appntno and b.appntno=c.customerno  "
    			  + "and b.addressno=c.addressno and appflag = '1' and agentcode ='"+"?strAgentCode?"+"' order by b.appntno) t1) t2 "
    			  +"where r>="+"?nBegin?"+ " and r<="+"?nEnd?";
      }
      SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
      sqlbv5.sql(strSQL);
      sqlbv5.put("nBegin",nBegin);
      sqlbv5.put("nEnd",nEnd);
      sqlbv5.put("strAgentCode",strAgentCode);
      String strReturn = tExeSQL.getEncodedResult(sqlbv5, 1);
      String strR = strReturn.substring(0, 3);
      if (strR.equals("100"))
      {
          mResult.clear();
          mResult.add("FAIL");
      }
      mResult.add(strReturn);

      return true;
  }
  /**
   * 执行查询
   * @param inParam
   * @return
   */
  public String execSql(SQLwithBindVariables strSQL)
  {
      logger.debug("Server ExecSql: " + strSQL);
      ExeSQL tExeSQL = new ExeSQL();
      String strReturn = tExeSQL.getEncodedResult(strSQL, 1);

      return strReturn;
  }


    public static void main(String[] args)
    {
        //        WebServiceServer webServiceServer1 = new WebServiceServer();
    }
}
