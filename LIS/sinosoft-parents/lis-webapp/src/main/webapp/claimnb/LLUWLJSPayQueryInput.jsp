<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	  GlobalInput tGI = new GlobalInput();
	  tGI = (GlobalInput)session.getValue("GI");
	  String tClmNo  = request.getParameter("ClmNo"); //�ⰸ��
	  String tContNo = request.getParameter("ContNo"); //��ͬ��
%>
<script>
	  var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	  var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
    <SCRIPT src="LLUWLJSPayQuery.js"></SCRIPT>
    <%@include file="LLUWLJSPayQueryInit.jsp"%>
  <title>�ӷ�Ӧ����Ϣ��ѯ </title>
</head>
<body  onload="initForm();" >
  
  <form  method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ���˼ӷ�Ӧ����Ϣ�б�
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLJSPayGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
    </div>

	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 ����Ӧ�ո��˽�����Ϣ�б�
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLJSPayPersonGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <table align="center">
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();"></td>
            </tr>
        </table>     	
    </div>
    <!--  
	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLX);">
    		</td>
    		<td class= titleImg>��Ϣ</td>
    	</tr>
    </table>    
  	<Div  id= "divLX" style= "display: ''" align = center>	
  		<table  class= common>
	        <TR class=common>
		        <TD class=title>��Ϣ </TD>
	            <TD class= input><Input class="readonly" readonly  name=tPay></TD>
	            <TD class=title> </TD>
	            <TD class= input></TD> 		         
		        <TD class=title> </TD>
		        <TD class= input></TD>                                               
	        </TR> 	
        </table>
  	</Div> -->
	
  		<!--<tr class=common>
  			<td class=common><INPUT class=cssButton name="AddFeePrint" VALUE="��ӡ����֪ͨ��" TYPE=button onclick="LLUWPCLMAddFeePrint()" ></td>
  			<td class=common><INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="top.close();"></td>
  	  </tr>-->
  	  <a href="javascript:void(0);" class="button" name="AddFeePrint" onClick="LLUWPCLMAddFeePrint();">��ӡ����֪ͨ��</a>
      <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
  	  
  	 <Input  type=hidden id="ClmNo" name="ClmNo" value="<%=tClmNo%>">
  	 <Input  type=hidden id="ContNo" name="ContNo" value="<%=tContNo%>">
  	 <Input  type=hidden id="NoticeNo" name="NoticeNo" >
  	 <Input  type=hidden id="DoutyCode" name="DoutyCode" >
  	 <Input  type=hidden id="PayPlayCode" name="PayPlayCode" >
  	 <Input  type=hidden id="PolNo" name="PolNo" >
  	 
     <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
