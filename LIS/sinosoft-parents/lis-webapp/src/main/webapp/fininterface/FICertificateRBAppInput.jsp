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
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
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
	      <td class=titleImg>ƾ֤���������Ϣ¼�룺</td>
	</tr>
      </table>
   <Div  id= "divFICertificateRBApp" style= "display: ''">
   <div class="maxbox">
      <table  class= common>
		
	<TR  class= common> 
	      <TD  class= title5>������</TD>
	      <TD  class= input5><Input class="wid" class= common  name=Applicant id=Applicant  verify="������|notnull" elementtype=nacessary > </TD>
	      <TD  class= title5>��������</TD>
	      <TD  class= input5><!--<Input class= 'coolDatePicker'   name=AppDate verify="��������|notnull&DATE" format='short'  elementtype=nacessary>-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="��������|notnull&DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>     
	</TR>	

	<TR  class= common>
	      <TD  class= title5>ƾ֤���ͱ��</TD> 
	      <TD  class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CertificateId id=CertificateId  ondblclick="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onMouseDown="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onkeyup="return showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" ><input class=codename name=CertificateIdName id=CertificateIdName readonly=true elementtype=nacessary></TD>		      	
	      <TD  class= title5>���ԭ������</TD> 
	      <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno  name=ReasonType id=ReasonType verify="���ԭ������|notnull" onMouseDown="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" ondblclick="showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onkeyup="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" ><input class=codename name=ReasonTypeName id=ReasonTypeName readonly=true elementtype=nacessary></TD>			
	</TR>

	<TR  class= common>
	      <TD class= title5>ҵ���������</TD>
	      <TD class= input5>
		  <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID verify="ҵ��������������|NOTNULL" ondblClick="showCodeList('Detailindexid',[this,DetailIndexName],[0,1],null,[fm.CertificateId.value],['CertificateId']);" onMouseDown="showCodeList('Detailindexid',[this,DetailIndexName],[0,1],null,[fm.CertificateId.value],['CertificateId']);" onkeyup="showCodeList('Detailindexid',[this,DetailIndexName],[0,1]),null,null,[fm.CertificateId.value],['CertificateId'];"><input class=codename name=DetailIndexName id=DetailIndexName readonly=true elementtype=nacessary>
	      </TD>			
	      <TD  class= title5>ҵ�����</TD> 
	      <TD  class= input5><Input class="wid" class=common  name=BusinessNo id=BusinessNo verify="ҵ�����|notnull" elementtype=nacessary></TD>						  												
	</TR>
							  														
	<TR  class= common>
	      <TD  class= title5>ϸ������</TD>
	      <TD  class= common5 colspan="3">
	          <textarea class= common  name=DetailReMark id=DetailReMark verifyorder="1" cols="144%" rows="4" witdh=50% verify="ϸ������|notnull" elementtype=nacessary></textarea>
	      </TD>			
	</TR>	
	
     </table>
     </div>
  </Div>
      <!--<INPUT VALUE="�� ��" class = cssButton TYPE=button onclick="ApplyInput();"> 
      <INPUT VALUE="�����ѯ" class = cssButton TYPE=button onclick="queryRBResultGrid();"> --><br>
      <a href="javascript:void(0);" class="button" onClick="ApplyInput();">��    ��</a>
      <a href="javascript:void(0);" class="button" onClick="queryRBResultGrid();">�����ѯ</a>
      <table>   		  		
        <tr class=common>
	      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertificateRBAppTrace);");"></IMG></td>
	      <td class=titleImg>������ƾ֤�����Ϣ��</td>
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
  	<INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ"  class =  cssButton93 TYPE=button onclick="getLastPage();">    </center></Div>
  	  	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
