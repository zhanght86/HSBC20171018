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
<title>客户品质管理 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="GrpCustomerQualityManage.js"></SCRIPT>
  <%@include file="GrpCustomerQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./GrpCustomerQualityManageSave.jsp">
    <div class="maxbox">
    <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          客户号码
          </TD>
         <td class="input5">
         <Input class="wid" class=code name=CustomerNo id=CustomerNo ondblclick="showAppnt();" onclick="showAppnt();" >
         </TD> 
        <TD  class= title5>
          客户名称
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=Name id=Name >
        </TD>
        </tr>
        <tr class= common>
        <TD  class= title5>
          单位性质
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=GrpNature id=GrpNature ondblclick="return showCodeList('GrpNature',[this]);"  onclick="return showCodeList('GrpNature',[this]);" onkeyup="return showCodeListKey('GrpNature',[this]);">            
          </TD>
          <TD style="display:none"  class= title5>
          单位性质
        </TD>
        <TD style="display:none"  class= input5>
          <!--<Input class="wid" class="coolDatePicker" dateFormat="short" name=Birthday id=Birthday  >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="有效开始日期|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title5>客户品质状态</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="客户品质状态|notnull" ondblclick="return showCodeList('GrpCQualityFlag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('GrpCQualityFlag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('GrpCQualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
          </TD> 
    	</tr>
   <!-- </Table>
    <div id="notPerson" style="display:'none'">
    <Table class=common>	-->
    	<tr style="display:none" class= common>
    	<TD  class= title5>
          性别
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=Sex id=Sex readonly=true>
        </TD>
        <TD  class= title5>
          证件类型
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=IDType id=IDType readonly=true>
        </TD>
        </tr>
        <tr class= common>
        <TD style="display:none"  class= title5>
          证件号码
        </TD>
        <TD style="display:none"  class= input5>
          <Input class="wid" class=common name=IDNumber id=IDNumber readonly=true>
        </TD>
        
    	</tr>
   
    	</table></div>
    	<!--<INPUT CLASS=cssButton VALUE="查   询" TYPE=button onclick="easyQueryClick()">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a><br><br><br>
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanCustomerGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div>
    <div id="noDetailInfo" style="display:none">
   <table>
    <tr class=common>
    <td class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,lpo);"></td>
    <td class=titleImg>黑名单明细</td>
    </td>
    </tr>
    </table> 
  
    <Div  id= "lpo" style= "display: ''">	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanCustomerQualityGrid" >
	 					</span> 
					</td>
           </tr>
       </Table><center>
            <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
            <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
            <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
            <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></center>
    </Div>  
    </div>
    <br>
    <div class="maxbox1">
      <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          客户姓名
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=NameSel id=NameSel readonly=true>
        </TD>
        <TD  class= title5>
          客户号
          </TD>
         <td class="input5">
            <Input class="wid" class=common name=CustomerNoSel id=CustomerNoSel readonly=true>
         </TD> 
         <!--  
        <TD class=title>
         管理机构
        </TD>
		<TD class=input>
		    <Input class=codeno name=ManageComSel verify="管理机构|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
		</TD>
		-->
    	</tr>
    	  <tr>
    	    <TD  class= title5>
          客户品质更改状态
        </TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CQualityFlag id=CQualityFlag verify ="品质状态维护|notnull" ondblclick="return showCodeList('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);" onclick="return showCodeList('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);" onkeyup="return showCodeListKey('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);"><input class=codename name=GrpCQualityFlagName id=GrpCQualityFlagName readonly=true>
          </TD> 
    	<!--TD  class= title>
          原因类别
        </TD>
        <TD  class= input>
            <Input class="codeno" name=CReasonType ondblclick="return showCodeList('CReasonType',[this,CReasonTypeName],[0,1]);" onkeyup="return showCodeListKey('CReasonType',[this,CReasonTypeName],[0,1]);"><input class=codename name=CReasonTypeName readonly=true>
          </TD--> 
        <TD  class= title5></TD>
        <TD  class= input5></TD>
    	</tr>	  
    	<!--</table>
    
  <table width="20%" height="2%" class= common>-->
    <TR  class= common> 
      <TD class= title5> 原因 </TD>
    
      <TD class="input" colspan="3"><textarea name="Reason" id="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  </div>
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


  <!--<INPUT CLASS=cssButton VALUE="确   认" TYPE=button onclick="InsertClick()">-->
  <a href="javascript:void(0);" class="button" onClick="InsertClick();">确    认</a>
  <!--INPUT CLASS=cssButton VALUE="修  改" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
