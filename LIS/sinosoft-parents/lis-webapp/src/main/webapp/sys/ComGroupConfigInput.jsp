<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
   
    String CurrentDate = PubFun.getCurrentDate();
    loggerDebug("ComGroupConfigInput",CurrentDate);   
    //
   
%>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="ComGroupConfig.js"></SCRIPT> 
  <%@include file="ComGroupConfigInit.jsp"%> 

</head>
<body  onload="initForm();" >

<form action="./ComGroupConfigSave.jsp" method=post name=fm id=fm target="fraSubmit">
<table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBonusRisk);"></td>
    <td class=titleImg>��������Ϣ����</td>
    </td>
    </tr>
    </table>
  <Div  id= "divBonusRisk" style= "display: ''" class="maxbox1">      
    <table  class= common>
      <TR class= common>
         <TD  class= title5>
            ���������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=ComGroupCode id=ComGroupCode verify="���������|notnull&len<=10"><font color=red>*</font>
            (ʮλ�ַ�)
          </TD> 
          <TD  class= title5>
            ����������
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=ComGroupName id=ComGroupName verify="���������|notnull"><font color=red>*</font>
          </TD>   
      </TR> 
    </table>
    <table  class= common>
      <TR class= common>
    	 <TD  class= title5>
            ����������:
          </TD>                
    	</TR>
    	<TR>
    		 <TD style=" padding-left:16px" colspan="4">
           <textarea class=common  cols=169 rows=4 name="GroupInfo" value="">   
           </textarea>  
          </TD>  
      </TR>
     </table>   </Div>  
 	
      <!--<Div id= divCmdButton style="display: ''"> -->          
      
        <!--  <INPUT VALUE="��ѯ��������Ϣ" class= cssButton TYPE=button onclick="queryComGroupConfig();">  
	  
          <INPUT VALUE="��������Ϣ����" class= cssButton TYPE=button onclick="addData();">  -->
          <a href="javascript:void(0);" class="button" onClick="queryComGroupConfig();">��ѯ��������Ϣ</a>
          <a href="javascript:void(0);" class="button" onClick="addData();">��������Ϣ����</a>
	 
     <!-- </div>-->

<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNoAgentGrid);"></td>
    <td class=titleImg>�ѱ����������Ϣ</td>
    </td>
    </tr>
    </table>

<Div  id= "divNoAgentGrid" style= "display: ''">
      	<table  >
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanComGroupGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<!--<table>
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" TYPE=button class= cssButton onclick="turnPage1.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton onclick="turnPage1.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton onclick="turnPage1.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button class= cssButton onclick="turnPage1.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>		-->					
</div>
<table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);"></td>
    <td class=titleImg>������ӳ����Ϣ</td>
    </td>
    </tr>
</table>
  <!--<Div id= divCmdButton style="display: ''">-->
  	 <!--INPUT VALUE="������ӳ����Ϣ��ѯ" name = "compute" class= common TYPE=button onclick="queryComGroupMap();"-->
       
  <!--</Div>  -->     
<Div  id= "divAgentGrid" style= "display: ''">
      	<table style=" width:100%"  >
       		<tr  >
      	  		<td colSpan=1>
  					<span id="spanComGroupMapGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" TYPE=button class= cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button class= cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
  	</div>
    <!--<INPUT VALUE="������ӳ����Ϣ����" name = "compute" class= cssButton TYPE=button onclick="addDataSub();">-->
    <a href="javascript:void(0);" name = "compute" class="button" onClick="addDataSub();">������ӳ����Ϣ����</a>
<table>
<TR class= common>
         <TD class= title>
             <input Type="Checkbox"  name= checkbox1 value="1"  onClick="setDefaultClass()">�����㷨
         </TD> 
</TR>
</table>  
<Div  id= "divOtherGrid" style= "display: 'none'">
<table  class= common>
      <TR class= common>
         <TD  class= title>
            Ĭ��ȡ���㷨
          </TD>
          <TD class=input>
           <textarea class=readonly readonly cols=100 rows=6 name="WorkDetail" value="">   
           </textarea>  
          </TD>                  
      </TR> 
      <TR class= common>
      </TR>
      <TR class= common>
         <TD  class= title>
            ����ȡ���㷨
          </TD>                  
          <TD class=input>
           <textarea class=input cols=166 rows=4 name="OtherCondition" >   
           </textarea>  
          </TD>   
      </TR>
 <Div id= divCmdButton style="display: ''">
 		<INPUT VALUE="�����㷨���ݲ�ѯ" name = "query" class= common TYPE=button onclick="queryOtherData();">  
     <INPUT VALUE="�����㷨���ݱ���" name = "query" class= common TYPE=button onclick="saveOtherData();">  
  </Div>
       
</Div>  
  <!-- ȷ�϶Ի��� ��ʼ��Ϊ0 ��ȷ�Ͽ��ȷ���޸�Ϊ1 ������Ϻ����³�ʼ��Ϊ0 -->
  <INPUT VALUE="0" TYPE=hidden name=myconfirm >
  
    <!-- ��ǰ���� -->
  <INPUT VALUE=<%=CurrentDate%> TYPE=hidden name=CurrentDay>
  <!-- ��ǰ�·� -->
  <input type=hidden id="hideaction" name="hideaction">  
  <input type=hidden id="BatchNo" name="BatchNo">  
  <input type=hidden id="hideDelManageCom" name="hideDelManageCom"> 
  <input type=hidden id="hideDelIndexCalNo" name="hideDelIndexCalNo">   
   
</form><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
