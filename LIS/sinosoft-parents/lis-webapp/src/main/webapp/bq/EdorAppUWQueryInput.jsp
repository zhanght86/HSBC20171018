<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�EdorAppUWQueryInput.jsp
//�����ܣ���ȫ�˱���ѯ
//�������ڣ�2005-7-21 19:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%
	String tEdorAcceptNo = "";
	tEdorAcceptNo = request.getParameter("EdorAcceptNo"); 

%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var EdorAcceptNo = "<%=tEdorAcceptNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="EdorAppUWQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorAppUWQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  
<DIV id=divEdorMainInfo STYLE="display:''"> 

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMain);">
    		</td>
    		<td class= titleImg>
    			 ��ȫ���������˱���Ϣ
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divEdorMain"   align = center >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanEdorMainGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
	       <!-- <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
            <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
            <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
            <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();">  
<br>         					-->
    </Div>
<div id = "divContUWIdiea"	style = "display:'none'">
        <table>
    	    <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContUWIdieaInfo);">
    		</td>
    		<td class= titleImg>
    			 ��ȫ���������˱�ԭ��
    		</td>
    	    </tr>
        </table>
        <div id = "divContUWIdieaInfo"	style = "display:''">	
		<table  class= common>
				<tr class=common>
					<TD  colspan="6" style="padding-left:16px" ><textarea name="ContUWIdea" id="ContUWIdea" readonly cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
				</tr>
		</table>
        </div>
</div>
<!--������Ϣ-->  
<div id = "divPolInfo"	style = "display:'none'">
<br>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPPol);">
    		</td>
    		<td class= titleImg>
    			 ��ȫ�������ֺ˱���Ϣ
    		</td>
    	    </tr>
        </table>	
        
        <Div  id= "DivLPPol">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
	       <!-- <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
            <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
            <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
            <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage1.lastPage();"> -->
        </Div>	
</div>
<div id = "divPolUWIdiea"	style = "display:'none'">
        <table>
    	    <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolUWIdieaInfo);">
    		</td>
    		<td class= titleImg>
    			 ��ȫ�������ֺ˱�ԭ��
    		</td>
    	    </tr>
        </table>
        <div id = "divPolUWIdieaInfo"	style = "display:''">	
		<table  class= common>
				<tr class=common>
					<TD  colspan="6" style="padding-left:16px" ><textarea name="PolUWIdea" id="PolUWIdea"  readonly cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
				</tr>
		</table>
        </div>
</div>
<br>
<INPUT VALUE="�� ��" class=cssButton TYPE=button onclick="returnParent();">

	<input type=hidden id="EdorNo" 	      name= "EdorNo">


  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>