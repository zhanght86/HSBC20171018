<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLUWSpecInput.jsp
//�����ܣ�������Լ�б�
//�������ڣ�2005-11-04 
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
   String tQueryFlag = request.getParameter("QueryFlag");

%>
 <script language="JavaScript">
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>                          

<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLUWSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> ��Լ�б� </title>
  <%@include file="LLUWSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tClmNo%>','<%=tProposalNo%>','<%=tBatNo%>');" >

  <form method=post name=fm id=fm target="fraSubmit" >
    <!-- �����˱���¼���֣��б� -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
    		</td>
    		   <td class= titleImg>������Ϣ</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLLUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>
   
   <Div  id= "divLCPol2" style= "display: ''">  	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		  <td class= titleImg>��Լ��Ϣ</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanLLUWSpecContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

    </table>
  </Div>
  
        <table class = common>
    	<TR  class= common>
          <TD  class= title>��Լԭ�� </TD>
          <tr></tr>          
          <TD colspan="6" style="padding-left:16px"> <textarea name="SpecReason" id="SpecReason" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
   	
	 <table class = common>
	  <TR  class= common>
       <TD  class= title>�ر�Լ�� </TD>
       <tr></tr>
       <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
     	
     <table class=common  style="display:none">
     	<tr class=common>
     		<td class=common>
		      <INPUT type= "button" id="button1"  name= "sure"  value="ȷ  ��" class= cssButton  onclick="saveClick()">			
		      <INPUT type= "button" id="button3"  name="delete" value="ɾ  ��" class= cssButton  onclick="deleteClick();">      
		      <INPUT type= "button" id="button2"  name= "back"  value="��  ��" class= cssButton  onclick="top.close();">					
		    </td>
		</tr>
     </table>
     <br>
     <a href="javascript:void(0);" id="button1"  name="sure" class="button" onClick="saveClick();">ȷ    ��</a>
     <a href="javascript:void(0);" id="button3"  name="delete" class="button" onClick="deleteClick();">ɾ    ��</a>
     <a href="javascript:void(0);" id="button2"  name="back" class="button" onClick="top.close();">��    ��</a>
      <INPUT type= "hidden" name= "ClmNo" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
      <INPUT type= "hidden" name= "PolNo" value= "">
      <INPUT type= "hidden" name= "ProposalNo" value= "">
      <INPUT type= "hidden" name= "Flag" value="">
      <input type= "hidden" name= "UWIdea" value="">
      <input type= "hidden" name= "PrtNo" value="">
      <input type= "hidden" name= "BatNo" value="">
      <INPUT type= "hidden" name= "MissionID" value= "">
      <INPUT type= "hidden" name= "SubMissionID" value="">
      <input type= "hidden" name= hideOperate value=''> 
      <input type= "hidden" name= SerialNo value=''> 
      <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
