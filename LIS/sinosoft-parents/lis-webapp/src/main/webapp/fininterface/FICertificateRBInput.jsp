 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>

<%
     GlobalInput tGI = new GlobalInput();
     tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
      //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
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
  <SCRIPT src="FICertificateRBInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FICertificateRBInit.jsp"%>
  
</head>
<body  onload="initForm();initElementtype();" >

  <form  method=post name=fm id=fm target="fraSubmit">          
  <table>   		  		
    <tr class=common>
	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificateRBApp);"></IMG></td>
	<td class=titleImg>红冲申请记录：</td>
    </tr>
  </table>

    <Div  id= "divFICertificateRBApp" style= "display: ''">

      	<table  class= common>
       	   <tr  class= common>
      	  	<td text-align: left colSpan=1 >
  			<span id="spanRBResultGrid" ></span> 
  		</td>
  	   </tr>
    	</table>
        <center><INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
           <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
           <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
           <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="getLastPage();">  </center> 	          
           					
     </Div>

  <table>  
    <tr class=common>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificatp);"></IMG></td>
	<td class=titleImg>凭证红冲申请明细信息：</td>
    </tr>  
  </table>  

    	<Div  id= "divFICertificatp" style= "display: ''" class="maxbox"> 
     <table class= common>     
	<TR  class= common> 
	      <TD  class= title5>申请人</TD>
	      <TD  class= input5><Input class="wid" class= common  name=Applicant id=Applicant readonly=true> </TD>
	      <TD  class= title5>申请日期</TD>
	      <TD  class= input5><!--<Input class= 'coolDatePicker'   name=AppDate  format='short' readonly=true>-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>     
	</TR>	

	<TR  class= common>
	      <TD class= title5>业务号码类型</TD>
	      <TD class= input5>
		  <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID ondblClick="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" onMouseDown="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" onkeyup="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" readonly=true><input class=codename name=DetailIndexName id=DetailIndexName readonly=true>
	      </TD>			
	      <TD  class= title5>业务号码</TD> 
	      <TD  class= input5><Input class="wid" class=common  name=BusinessNo readonly=true></TD>						  												
	</TR>
	
	<TR  class= common>
	      <TD  class= title5>凭证类型编号</TD> 
	      <TD  class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CertificateId id=CertificateId  ondblclick="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onMouseDown="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onkeyup="return showCodeList('CertificateId',[this,CertificateIdName],[0,1]);"  readonly=true ><input class=codename name=CertificateIdName id=CertificateIdName readonly=true ></TD>		      	
	      <TD  class= title5>红冲原因类型</TD> 
	      <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno  name=ReasonType id=ReasonType  ondblclick="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onMouseDown="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onkeyup="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);"  readonly=true><input class=codename name=ReasonTypeName id=ReasonTypeName readonly=true ></TD>			
	</TR>
						  														
	<TR  class= common>
	      <TD  class= title5>细节描述</TD>
	      <TD  class= common>
	          <textarea class= common  name=DetailReMark verifyorder="1" cols="39%" rows="4" witdh=50% readonly=true></textarea>
	      </TD>
              <TD  class= title5>红冲申请号码</TD>
	      <TD  class= input5><Input class="wid" class= common  name=AppNo readonly=true> </TD>	      			
	</TR>	     
     </table>  
     
			
	</div>
  	 
  	 
    <!-- <INPUT VALUE="申请处理" class = cssButton TYPE=button onclick="AppDeal();"> 
     <INPUT VALUE="申请撤消" class = cssButton TYPE=button onclick="AppDelete();"> 
     <INPUT VALUE="申请确认" class = cssButton TYPE=button onclick="ReConfirm();">-->
     <a href="javascript:void(0);" class="button" onClick="AppDeal();">申请处理</a>
     <a href="javascript:void(0);" class="button" onClick="AppDelete();">申请撤消</a>
     <a href="javascript:void(0);" class="button" onClick="ReConfirm();">申请确认</a> <br>
     
  </form>
        <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
