<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGSpec.jsp
//�����ܣ������˹��˱��¸����ӷѳб�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWGSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> ���嵥�¸����ӷѳб� </title>
  <%@include file="UWGSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tFlag%>','<%=tUWIdea%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWGSpecChk.jsp">
   <Div  id= "divUWSpec" style= "display: 'none'">
    <table>
    	<TR  class= common>
          <TD  class= title>
            �ر�Լ��
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="Remark" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>
    <table>
    	<TR  class= common>
          <TD  class= title>
            ��Լԭ��
          </TD>
          <tr></tr>          
      <TD  class= input> <textarea name="SpecReason" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>
   </Div>
   
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ������ѯ���
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      </div>
      <table>
    	<TR  class= common>
          <TD  class= title>
            �ӷ�ԭ��
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="Reason" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "Flag" value="">
      <input type= "hidden" name= "UWIdea" value="">
      <INPUT type= "button" name= "sure" value="ȷ��" onclick="submitForm()">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
