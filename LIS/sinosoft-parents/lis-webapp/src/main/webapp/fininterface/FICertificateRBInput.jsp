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
      //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
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
	<td class=titleImg>��������¼��</td>
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
        <center><INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
           <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
           <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
           <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="getLastPage();">  </center> 	          
           					
     </Div>

  <table>  
    <tr class=common>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificatp);"></IMG></td>
	<td class=titleImg>ƾ֤���������ϸ��Ϣ��</td>
    </tr>  
  </table>  

    	<Div  id= "divFICertificatp" style= "display: ''" class="maxbox"> 
     <table class= common>     
	<TR  class= common> 
	      <TD  class= title5>������</TD>
	      <TD  class= input5><Input class="wid" class= common  name=Applicant id=Applicant readonly=true> </TD>
	      <TD  class= title5>��������</TD>
	      <TD  class= input5><!--<Input class= 'coolDatePicker'   name=AppDate  format='short' readonly=true>-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>     
	</TR>	

	<TR  class= common>
	      <TD class= title5>ҵ���������</TD>
	      <TD class= input5>
		  <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID ondblClick="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" onMouseDown="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" onkeyup="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]);" readonly=true><input class=codename name=DetailIndexName id=DetailIndexName readonly=true>
	      </TD>			
	      <TD  class= title5>ҵ�����</TD> 
	      <TD  class= input5><Input class="wid" class=common  name=BusinessNo readonly=true></TD>						  												
	</TR>
	
	<TR  class= common>
	      <TD  class= title5>ƾ֤���ͱ��</TD> 
	      <TD  class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CertificateId id=CertificateId  ondblclick="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onMouseDown="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onkeyup="return showCodeList('CertificateId',[this,CertificateIdName],[0,1]);"  readonly=true ><input class=codename name=CertificateIdName id=CertificateIdName readonly=true ></TD>		      	
	      <TD  class= title5>���ԭ������</TD> 
	      <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno  name=ReasonType id=ReasonType  ondblclick="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onMouseDown="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onkeyup="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);"  readonly=true><input class=codename name=ReasonTypeName id=ReasonTypeName readonly=true ></TD>			
	</TR>
						  														
	<TR  class= common>
	      <TD  class= title5>ϸ������</TD>
	      <TD  class= common>
	          <textarea class= common  name=DetailReMark verifyorder="1" cols="39%" rows="4" witdh=50% readonly=true></textarea>
	      </TD>
              <TD  class= title5>����������</TD>
	      <TD  class= input5><Input class="wid" class= common  name=AppNo readonly=true> </TD>	      			
	</TR>	     
     </table>  
     
			
	</div>
  	 
  	 
    <!-- <INPUT VALUE="���봦��" class = cssButton TYPE=button onclick="AppDeal();"> 
     <INPUT VALUE="���볷��" class = cssButton TYPE=button onclick="AppDelete();"> 
     <INPUT VALUE="����ȷ��" class = cssButton TYPE=button onclick="ReConfirm();">-->
     <a href="javascript:void(0);" class="button" onClick="AppDeal();">���봦��</a>
     <a href="javascript:void(0);" class="button" onClick="AppDelete();">���볷��</a>
     <a href="javascript:void(0);" class="button" onClick="ReConfirm();">����ȷ��</a> <br>
     
  </form>
        <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
