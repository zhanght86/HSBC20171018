<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWQuery.jsp
//�����ܣ��˱���ѯ
//�������ڣ�2005-6-23 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%
	String tProposalContNo = "";
	String tContType = "";
	tProposalContNo = request.getParameter("ProposalContNo"); 
	tContType = request.getParameter("ContType"); 
	session.putValue("ProposalContNo",tProposalContNo);
%>
<html>
<%
  //�����¸���
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ProposalContNo = "<%=tProposalContNo%>";          //���˵��Ĳ�ѯ����.
	var ContType = "<%=tContType%>";          //��������:"1"������"2"���嵥
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
  <SCRIPT src="UWQuery1.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWQueryInit1.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
  	
    <!--��ͬ��Ϣ-->
	<DIV id=DivLCContButton >
	<table id="table1">
			<tr>
				<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">��ͬ��Ϣ
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" class="maxbox1" >
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>Ͷ�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ContNo" id="ContNo" VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom" id="ManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
				<td CLASS="title" nowrap>�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" id="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">ҵ��Ա���� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly wid" id="readonly" readonly>
	    		</td>
				<td CLASS="title">�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
					<input NAME="Remark" id="Remark" CLASS="readonly wid" readonly MAXLENGTH="255">
	    		</td>	    		
			</tr>
		</table>
</div>
		
		 </DIV>
          <INPUT class=cssButton VALUE="�˱��켣��ѯ" TYPE=button onClick="QueryContTrace();">     
      
     <DIV id=DivLCContInfo STYLE="display:''"> 
	
	

<!--��������Ϣ-->   
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ:
    		</td>
    	    </tr>
        </table>	
         <Div  id= "divLCPol2" >
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

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ:
    		</td>
    	    </tr>
        </table>	
        
        <Div  id= "DivLCPol" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>	
  	 
          
 </form>
 <br><br><br><br><br>
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
        
</body>
</html>
