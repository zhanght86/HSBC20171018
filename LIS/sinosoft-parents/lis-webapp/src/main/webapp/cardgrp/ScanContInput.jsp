<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���	
	String tContNo = "";
	String tFlag = request.getParameter("type");
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ScanContInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ScanContInit.jsp"%>
  <title>ɨ��¼��</title>
</head>
<body  onload="initForm();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>�������ѯ������</td>
	</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <table  class= common >   
      	<TR  class= common>
          <TD  class= title8>
            �������
          </TD>
          <TD  class= input8>
            <Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </TD>      	
          <TD  class= title8>
            Ͷ������
          </TD>
          <TD  class= input8>
            <Input class= common name=PrtNo >
          </TD>
          <TD  class= title8>
            ɨ��ʱ��
          </TD>
          <TD  class= input8>
            <Input class="coolDatePicker" name=InputDate >
          </TD>                  
        </TR>      
    </table>
    </div> 
          <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> <!--INPUT VALUE="ɨ���Ԥ��" class = cssButton TYPE=button onclick="easyQueryClick();"> <INPUT VALUE="ɨ�������" class = cssButton TYPE=button onclick="easyQueryClick();"--> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������
    		</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">       
          <INPUT  type= "hidden" class= Common name= PrtNo1  value= "">       
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
  <br>
   <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">

   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 ɨ�����Ϣ
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
         <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();"> 					
  	</div>
 	
  	<p>
     
      <!--INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->
  	</p>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
