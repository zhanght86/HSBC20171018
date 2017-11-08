<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：YYYY-MM-DD
//创建人  ：XXX 
%>
<%//必须在以下部分编辑SQL
if(SQLID.equals("FinFeeInputSql_0"))
{
  SQL=" select FinFeeNo,PayDate,Operator,'' from LJFinFee";
}
if(SQLID.equals("FinFeeInputSql_1"))
{
  SQL="select enteraccdate from ljtempfee where tempfeeno='?arrResult?'";
}

if(SQLID.equals("CustomerYFInputSql_13"))
{
  SQL="select AgentCode,Name from LAAgent where AgentCode='?cAgentCode?'"; 
}
if(SQLID.equals("CustomerYFInputSql_14"))
{
  SQL="select agentgroup from LAAgent where agentcode='?AgentCode?'";
}
if(SQLID.equals("CustomerYFInputSql_15"))
{
  SQL="select agentcode from lccont where contno='?PolNo?'";
}
if(SQLID.equals("CustomerYFInputSql_16"))
{
  SQL="select agentcode from LCGrpCont where GrpContNo='?PolNo?'";
}

if(SQLID.equals("CustomerYFInputSql_20"))
{
  SQL="select a.AgentCode,a.managecom,a.agentgroup,(select name from laagent b where b.agentcode=a.agentcode),a.GetNoticeNo from ljspay a where a.otherno='?InputNo3?'";
}
if(SQLID.equals("CustomerYFInputSql_21"))
{
  SQL="select GetNoticeNo from LJSPay where OtherNo='?InputNo7?' and othernotype='10'"
  	 +" and PayDate >='?PayDate?' and StartPayDate<='?PayDate?'" ;
}
if(SQLID.equals("CustomerYFInputSql_22"))
{
  SQL="select otherno from LJSPay where GetNoticeNo='?InputNo8?' and othernotype='10'";
}
if(SQLID.equals("CustomerYFInputSql_23"))
{
  SQL="select managecom from LJSPay where GetNoticeNo='?InputNo8?'"
  	+" and othernotype='10' and PayDate >='?PayDate?' and StartPayDate<='?PayDate?'" ;
}
if(SQLID.equals("CustomerYFInputSql_24"))
{
  SQL="select a.managecom from LAAgent a,LATree b,LABranchGroup c where 1=1 "
     + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup"
     +" and (a.AgentState is null or a.AgentState < '03') "
     + "and a.agentcode ='?AgentCode?'";
}
if(SQLID.equals("CustomerYFInputSql_25"))
{
  SQL="select agentgroup from LAAgent where agentcode='?AgentCode?'";
}
if(SQLID.equals("CustomerYFInputSql_26"))
{
  SQL="select name from LAAgent where agentcode='?AgentCode?'";
}
if(SQLID.equals("CustomerYFInputSql_27"))
{
  SQL="select distinct ManageCom,OtherNo from ljspay where getnoticeno='?InputNo11?'";
}
if(SQLID.equals("CustomerYFInputSql_28"))
{
  SQL="select ManageCom,GetNoticeNo from ljspay where otherno='?InputNo12?'";
}
if(SQLID.equals("CustomerYFInputSql_29"))
{
  SQL="select * from lcgrpcont where grpcontno = '?InputNo5?'";
}
if(SQLID.equals("CustomerYFInputSql_30"))
{
  SQL="select managecom from LCCont where contno='?InputNo5?'";
}
if(SQLID.equals("CustomerYFInputSql_31"))
{
  SQL="select a.agentcode,(select b.Name from LAAgent b where b.agentcode=a.agentcode ),"
  + " (select c.agentgroup from LAAgent c where c.agentcode=a.agentcode ) from LCCont a where a.contno='?InputNo5?'";
}
if(SQLID.equals("CustomerYFInputSql_32"))
{
  SQL="select managecom from LCGrpCont where grpcontno='?InputNo5?'";
}
if(SQLID.equals("CustomerYFInputSql_33"))
{
  SQL="select agentcode from LCGrpCont where grpcontno='?InputNo5?'";
}

if(SQLID.equals("CustomerYFInputSql_34"))
{
  SQL="select ActuGetNo, OtherNo,SumGetMoney,getcurrname(currency),currency from LJAGet"
  	+" where 1=1 and ConfDate is null and EnterAccDate is null"
  	+" and (bankonthewayflag='0' or bankonthewayflag is null)  ";
}
if(SQLID.equals("CustomerYFInputSql_35"))
{
	SQL=" and actugetno='?ActuGetNo5?'";
}
if(SQLID.equals("CustomerYFInputSql_36"))
{
	SQL=" and otherno='?OtherNo5?'";	
}

%>
<%
	loggerDebug("CustomerYFInputSql","InputSQL===:"+SQL);
	request.setAttribute("EASYQUERYSQL",SQL);
%>
