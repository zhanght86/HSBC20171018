<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�EdorUWQueryInput.jsp
//�����ܣ���ȫ�˱���ѯ
//�������ڣ�2005-7-12 19:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%
	String tContNo = "";
	String tEdorNo = "";
	tContNo = request.getParameter("ContNo"); 
	tEdorNo = request.getParameter("EdorNo"); 

%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var EdorNo = "<%=tEdorNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
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
  <SCRIPT src="EdorUWQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorUWQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  	
    <!--��ͬ��Ϣ-->
	<DIV id=DivLPContButton >
	<table id="table1">
			<tr>
				<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLPCont);">
				</td>
				<td class="titleImg">��ͬ��Ϣ
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLPCont"  class=maxbox1>
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id=ContNo VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id=ManageCom  MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title" nowrap>�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" id=SaleChnl CLASS="readonly wid" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">ҵ��Ա���� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="AgentCode" id=AgentCode MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				
				<td CLASS="title">�������� 
	    		</td>
				<td CLASS="input" COLSPAN="3">
					<input NAME="Remark" id=Remark CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>
				<td></td>
				<td></td>
			</tr>
		</table>
</div>
		
		 </DIV>
          <INPUT class=cssButton VALUE="��ͬ�˱��켣��ѯ" TYPE=button onclick="QueryContTrace();">     
      
<DIV id=DivLPContInfo STYLE="display:''"> 
<hr>
<!--��������Ϣ-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLPInsured  " >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>
  
<!--������Ϣ-->  
<div id = "DivPolInfo"	style = "display:'none'">
  	<hr>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPPol);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ:
    		</td>
    	    </tr>
        </table>	
        
        <Div  id= "DivLPPol" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>	
</div>
<hr>
<INPUT VALUE=" �� �� " class=cssButton TYPE=button onclick="returnParent();"> 	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
