<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWCustomerQuality.jsp
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>体检医院品质管理维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="HospitalQualityManage.js"></SCRIPT>
  <%@include file="HospitalQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./HospitalQualityManageSave.jsp">
   <Div  id= "divAgentQuality" style= "display: ''"  class="maxbox">	 
    <table class= common>
 
    	<tr class= common>
        <TD  class= title5>
          体检医院代码
          </TD>
         <td class="input5" >
         <Input class="wid" class=code name=HospitalCode1 id=HospitalCode1 ondblclick="showHospital();" onclick="showHospital();" >
         </TD> 
        <TD  class= title5>
          体检医院名称
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=HospitalName1 id=HospitalName1 >
        </TD>
        </tr>
        <tr class= common>
    		<TD  class= title5>
      		所属管理机构
    		</TD>
    		<TD  class= input5>
      	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom1 id=ManageCom1  ondblclick="return showCodeList('ComCode',[this,ManageComName1],[0,1]);" onclick="return showCodeList('ComCode',[this,ManageComName1],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName1],[0,1]);"><input class=codename readonly=true name=ManageComName1 id=ManageComName1 elementtype=nacessary>
    		</TD>
    		<TD  class= title5>
          录入日期
        </TD>
        <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="有效开始日期|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
     	</tr>
     	<tr class= common>
    		<TD  class= title5>体检品质状态</TD>
        	<TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=FQualityFlag id=FQualityFlag ondblclick="return showCodeList('hospitalqaflag',[this,FQualityFlagName],[0,1]);" onclick="return showCodeList('hospitalqaflag',[this,FQualityFlagName],[0,1]);" onkeyup="return showCodeListKey('hospitalqaflag',[this,FQualityFlagName],[0,1]);"><input class=codename name=FQualityFlagName id=FQualityFlagName readonly=true>
            </TD>   
            <TD  class= title5></TD>
             	<TD  class= input5></TD>	
     	</tr>
     </Table></Div>
    <!--<INPUT CLASS=cssButton VALUE="查   询" TYPE=button onclick="easyQueryClick()"><br>-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a><br><br><br>
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanHospitalGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div><br>
    <div class="maxbox1">
   <table class= common>
   	 <tr class= common>
    	<TD  class= title5>品质状态</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="体检品质状态|notnull&code:hospitalqaflag" ondblclick="return showCodeList('hospitalqaflag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('hospitalqaflag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('hospitalqaflag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
        </TD> 
       <TD  class= title5>原因类别</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlagType id=QualityFlagType verify ="原因类别|notnull&code:hospitalqaflagsub" ondblclick="getClickHospital();" onclick="getClickHospital();" onkeyup="return getClickUpHospital();"><input class=codename name=QualityFlagTypeName id=QualityFlagTypeName readonly=true>
        </TD> 
    </tr>
  <!-- </Table> 
  <table width="20%" height="2%" class= common>-->
  	

    <TR  class= common> 
      <TD class= title5> 原因 </TD>
      <TD class=input colspan="3"><textarea name="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table></div>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
	<input type="hidden" name= "HospitalCode" value= ""  verify ="体检医院代码|notnull&code:pehospital">
	<input type="hidden" name= "HospitalName" value= "">
	<input type="hidden" name= "ManageCom" value= "">
	<input type="hidden" name= "ManageComName" value= "">

  <!--<INPUT CLASS=cssButton VALUE="确   认" TYPE=button onclick="InsertClick()">-->
  <a href="javascript:void(0);" class="button" onClick="InsertClick();">确    认</a>
  <!--INPUT CLASS=cssButton VALUE="修  改" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
