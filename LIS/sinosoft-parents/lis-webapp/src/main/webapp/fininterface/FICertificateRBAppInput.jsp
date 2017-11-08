<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>

<html>

<%
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var date = "<%=PubFun.getCurrentDate()%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FICertificateRBAppInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FICertificateRBAppInit.jsp"%>
  
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./FICertificateRBAppSave.jsp" method=post name=fm id=fm target="fraSubmit">
          
      <table>   		  		
        <tr class=common>
	      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificateRBApp);"></IMG></td>
	      <td class=titleImg>凭证红冲申请信息录入：</td>
	</tr>
      </table>
   <Div  id= "divFICertificateRBApp" style= "display: ''">
   <div class="maxbox">
      <table  class= common>
		
	<TR  class= common> 
	      <TD  class= title5>申请人</TD>
	      <TD  class= input5><Input class="wid" class= common  name=Applicant id=Applicant  verify="申请人|notnull" elementtype=nacessary > </TD>
	      <TD  class= title5>申请日期</TD>
	      <TD  class= input5><!--<Input class= 'coolDatePicker'   name=AppDate verify="申请日期|notnull&DATE" format='short'  elementtype=nacessary>-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="申请日期|notnull&DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>     
	</TR>	

	<TR  class= common>
	      <TD  class= title5>凭证类型编号</TD> 
	      <TD  class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CertificateId id=CertificateId  ondblclick="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onMouseDown="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onkeyup="return showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" ><input class=codename name=CertificateIdName id=CertificateIdName readonly=true elementtype=nacessary></TD>		      	
	      <TD  class= title5>红冲原因类型</TD> 
	      <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno  name=ReasonType id=ReasonType verify="红冲原因类型|notnull" onMouseDown="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" ondblclick="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onkeyup="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" ><input class=codename name=ReasonTypeName id=ReasonTypeName readonly=true elementtype=nacessary></TD>			
	</TR>

	<TR  class= common>
	      <TD class= title5>业务号码类型</TD>
	      <TD class= input5>
		  <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID verify="业务索引代码类型|NOTNULL" ondblClick="showCodeList('Detailindexid',[this,DetailIndexName],[0,1],null,[fm.CertificateId.value],['CertificateId']);" onMouseDown="showCodeList('Detailindexid',[this,DetailIndexName],[0,1],null,[fm.CertificateId.value],['CertificateId']);" onkeyup="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]),null,null,[fm.CertificateId.value],['CertificateId'];"><input class=codename name=DetailIndexName id=DetailIndexName readonly=true elementtype=nacessary>
	      </TD>			
	      <TD  class= title5>业务号码</TD> 
	      <TD  class= input5><Input class="wid" class=common  name=BusinessNo id=BusinessNo verify="业务号码|notnull" elementtype=nacessary></TD>						  												
	</TR>
							  														
	<TR  class= common>
	      <TD  class= title5>细节描述</TD>
	      <TD  class= common5 colspan="3">
	          <textarea class= common  name=DetailReMark id=DetailReMark verifyorder="1" cols="144%" rows="4" witdh=50% verify="细节描述|notnull" elementtype=nacessary></textarea>
	      </TD>			
	</TR>	
	
     </table>
     </div>
  </Div>
      <!--<INPUT VALUE="申 请" class = cssButton TYPE=button onclick="ApplyInput();"> 
      <INPUT VALUE="申请查询" class = cssButton TYPE=button onclick="queryRBResultGrid();"> --><br>
      <a href="javascript:void(0);" class="button" onClick="ApplyInput();">申    请</a>
      <a href="javascript:void(0);" class="button" onClick="queryRBResultGrid();">申请查询</a>
      <table>   		  		
        <tr class=common>
	      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificateRBAppTrace);");"></IMG></td>
	      <td class=titleImg>已申请凭证红冲信息：</td>
	</tr>
      </table>

   <Div  id= "divFICertificateRBAppTrace" style= "display: ''" align=center>
     <table  class= common>
       	<tr  class= common>
      	      <td text-align: left colSpan=1>
  		    <span id="spanRBResultGrid" >
  		    </span> 
  	      </td>
  	</tr>
      </table>
      
  
  <center>
  	<INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页"  class =  cssButton93 TYPE=button onclick="getLastPage();">    </center></Div>
  	  	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
