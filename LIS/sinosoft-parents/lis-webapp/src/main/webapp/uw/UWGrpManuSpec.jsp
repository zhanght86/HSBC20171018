<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ��˹��˱������б�
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
  <SCRIPT src="UWGrpManuSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �����б� </title>
  <%@include file="UWGrpManuSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPolNo%>');" >

  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWGrpManuSpecChk.jsp">
    <!-- �����˱���¼���֣��б� -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			Ͷ������Ϣ
    		</td>
    	    </tr>
        </table>	
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWSpecGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>
    
    
   
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanUWResultSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</Div>

    </table>
        <table class = common>
    	<TR  class= common>
          <TD  class= title>
            ��Լԭ��
          </TD>
          <tr></tr>          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>


    	
    	<table class = common>
    	<TR  class= common>
          <TD  class= title>
            �ر�Լ��
          </TD>
          <tr></tr>
          
      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp; <textarea id="Remark" name="Remark" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
    	
     <table>
      
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
       <INPUT type= "hidden" id="PolNo" name= "PolNo" value= "">
      <INPUT type= "hidden" id="Flag" name= "Flag" value="">
      <input type= "hidden" id="UWIdea" name= "UWIdea" value="">
      <input type= "hidden" id="PrtNo" name= "PrtNo" value="">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
      <INPUT type= "hidden" id="operate" name= "operate" value="">
      <INPUT type= "hidden" id="proposalno" name= "proposalno" value="">
      <INPUT type= "hidden" id="serialno" name= "serialno" value="">
      
      <INPUT type= "button" id="button1" name= "sure" value="��  ��" class= cssButton  onclick="submitForm()">			
      <INPUT type= "button" id="button3" name= "sure" value="��  ��" class= cssButton  onclick="submitForm()">			
      
      <INPUT type= "button" id="button2" name= "back" value="��  ��" class= cssButton  onclick="top.close();">					
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
