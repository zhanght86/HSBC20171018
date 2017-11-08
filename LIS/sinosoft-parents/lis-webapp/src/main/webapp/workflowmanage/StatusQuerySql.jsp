
<%@page import="org.apache.log4j.*" %>
<%Logger log = Logger.getRootLogger();%>	 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：
//创建人  ：
%>
<%
//必须在以下部分编辑SQL
if(SQLID.equals("StatusQuerySql1"))
{     
  String tStartDate = (String)request.getAttribute("StartDate".toUpperCase()); 
  String tEndDate = (String)request.getAttribute("EndDate".toUpperCase());
	SQL  = " Select distinct missionid,mainmissionid,processid,(select codename from ldcode where codetype='busitype' and code=missionprop2),"
	       + " missionprop1,missionprop3 "
	       + " From LWMission  where 1=1 "
         + getWherePart(request,"MissionProp1","NO")
         + getWherePart(request,"MissionProp2","BusiType")
         + getWherePart(request,"MissionProp3","ContNo")
         + getWherePart(request,"MissionProp4","ComCode");
         
  if(tStartDate!=null&&!tStartDate.equals(""))
  {
     SQL=SQL+" and InDate>='"+tStartDate+"'";
  }
  if(tEndDate!=null&&!tEndDate.equals(""))
  {
     SQL=SQL+" and InDate<='"+tEndDate+"'";
  }  
  SQL=SQL+" union ";
	SQL  =SQL+ " Select distinct missionid,mainmissionid,processid,(select codename from ldcode where codetype='busitype' and code=missionprop2),"
	       + " missionprop1,missionprop3 "
	       + " From LBMission  where 1=1 "
         + getWherePart(request,"MissionProp1","NO")
         + getWherePart(request,"MissionProp2","BusiType")
         + getWherePart(request,"MissionProp3","ContNo")
         + getWherePart(request,"MissionProp4","ComCode");
         
  if(tStartDate!=null&&!tStartDate.equals(""))
  {
     SQL=SQL+" and InDate>='"+tStartDate+"'";
  }
  if(tEndDate!=null&&!tEndDate.equals(""))
  {
     SQL=SQL+" and InDate<='"+tEndDate+"'";
  }   
  
}

%>
<%
log.debug("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
