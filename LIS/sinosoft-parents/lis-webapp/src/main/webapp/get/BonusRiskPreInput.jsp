<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
   
    String CurrentDate = PubFun.getCurrentDate();
    loggerDebug("BonusRiskPreInput",CurrentDate);      
%>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="BonusRiskPre.js"></SCRIPT> 
  <%@include file="BonusRiskPreInit.jsp"%> 
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./BonusRiskPreSave.jsp" method=post name=fm id=fm target="fraSubmit">

  <Div class="maxbox1">      
    <table  class= common>
      <TR class= common>
         <TD  class= title5>
            �������������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalYear id=BonusCalYear verify="�������������|notnull&INT&len==4" onchange="clearAllShow()"><font color=red>*</font>
             </TD> 
          <TD  class= title5>
            ������������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalRisk id=BonusCalRisk >
          </TD>   
      </TR>     
    </Table>  
</Div>
<INPUT VALUE="��  ѯ" CLASS=cssButton TYPE=button onclick="queryData('0');">  
<INPUT VALUE="��  ��" CLASS=cssButton TYPE=button onclick="addData();">
<!--<a href="javascript:void(0);" class="button" onClick="queryData('0');">��    ѯ</a>
<a href="javascript:void(0);" class="button" onClick="addData();">��    ��</a>-->

<table>
   <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick= "showPage(this,divNoAgentGrid);">
      </td>
      <td class= titleImg>
        �ֺ�������Ϣ
      </td>
   </tr>
</table>
<Div  id= "divNoAgentGrid" style= "display: ''" >
      	<table width="100%">
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanBonusRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" TYPE=button CLASS=cssButton90 onclick="turnPage1.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton91 onclick="turnPage1.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton92 onclick="turnPage1.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button CLASS=cssButton93 onclick="turnPage1.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>

<table>
   <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick= "showPage(this,divAgentGrid);">
      </td>
      <td class= titleImg>
        ������ֺ�������Ϣ
      </td>
   </tr>
</table>   


       
<Div  id= "divAgentGrid" style= "display: ''" >
      	<table  width="100%" >
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanNoBonusRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" TYPE=button CLASS=cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button CLASS=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button CLASS=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>
<INPUT VALUE="��  ѯ" name = "query" CLASS=cssButton TYPE=button onclick="queryData('1');">
<!--<a href="javascript:void(0);" name = "query" class="button" onClick="queryData('1');">��    ѯ</a>
<a href="javascript:void(0);" name = "compute" class="button" onClick="delData();">ɾ    ��</a><br><br>-->
<INPUT VALUE="ɾ  ��" name = "compute" CLASS=cssButton TYPE=button onclick="delData();"> 

<table class= common style= "display: none">
<TR class= common >
         <TD class= title>
             <input Type="Checkbox"  name= checkbox1 value="1"  onClick="setDefaultClass()">�����㷨
         </TD> 
</TR>
  <!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ע:��������㷨��Ҫ�ú���������������� ?BonusCalYear? ��ע--></table>
<Div  id= "divOtherGrid" style= "display: none">
<table  class= common>
      <TR class= common>
         <TD  class= title5>
            Ĭ��ȡ���㷨
          </TD></TR>
         <TR class= common> 
          <TD class=input colspan="4" style="padding-left:16px">
           <textarea class=common readonly cols=164 rows=4 name="WorkDetail" value="">   
           </textarea>  
          </TD>                  
      </TR> 
      <TR class= common>
         <TD  class= title5>
            ����ȡ���㷨
          </TD>    </TR>
             <TR class= common>            
          <TD class=input colspan="4" style="padding-left:16px">
           <textarea class=common cols=164 rows=4 name="OtherCondition" >   
           </textarea>  
          </TD>   
      </TR>
 </table>
 <Div id= divCmdButton style="display: ''">
      
  </Div>     
  <INPUT VALUE="�����㷨���ݱ���" name = "query" CLASS=cssButton  TYPE=button onclick="saveOtherData();"> 
 <!-- <a href="javascript:void(0);" name = "query" class="button" onClick="saveOtherData();">�����㷨���ݱ���</a> -->
</Div>  
  <!-- ȷ�϶Ի��� ��ʼ��Ϊ0 ��ȷ�Ͽ��ȷ���޸�Ϊ1 ������Ϻ����³�ʼ��Ϊ0 -->
  <INPUT VALUE="0" TYPE=hidden name=myconfirm >
  
    <!-- ��ǰ���� -->
  <INPUT VALUE=<%=CurrentDate%> TYPE=hidden name=CurrentDay>
  <!-- ��ǰ�·� -->
  <input type=hidden id="hideaction" name="hideaction">  
  <input type=hidden id="hideDelManageCom" name="hideDelManageCom"> 
  <input type=hidden id="hideDelIndexCalNo" name="hideDelIndexCalNo">   
   
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
