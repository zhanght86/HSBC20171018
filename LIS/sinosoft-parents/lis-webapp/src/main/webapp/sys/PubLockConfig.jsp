<%
//�������ƣ�PubLockConfig.jsp
//�����ܣ�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
%>
<html> 
<script>
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="PubLockConfigInit.jsp"%>
  <SCRIPT src="PubLockConfig.js"></SCRIPT>
  
</head>

<body  onload="initForm()" >
  <form action="./PubLockConfigChk.jsp" method=post name=fm id=fm target="fraSubmit">
     
    <!--<Div  id= "divAllLockInfo" style= "display: ''">-->
      <table  class= common >
  		<TR>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllLockInfo);"></td>
  		 <TD class="titleImg">��ǰ������Ϣ</td>
  	    </TR>
      </table>
      <Div  id= "divAllLockInfo" style= "display: ''">
      <table  class= common>
	   	<TR>
    	  <TD style=" text-align: left" colSpan=1>
			<span id="spanAllLockInfoGrid" > </span> 
		  </TD>
      </TR>
      </table>
      <table align="center">
    		<tr>
    			<td>
    				<INPUT  VALUE="��  ҳ" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button class=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
   </Div>
	<div id="divUnLockReason" style="display: none">
        <table class="common">
            <tr class="common">
                <td class="title5">
                    ����ԭ��
                </td>
            
                <td colspan="3">
                    <textarea class="common" rows="4" cols="146" name="UnLockReason" value=""></textarea>

                </td>
            </tr>

        </table>
    </div>

	<Div id = "divAllLockInfoButton" style= "display: ''">
	    <INPUT VALUE="����ˢ��" class=cssButton TYPE=button name=delbutton onclick="refreshTask();">
	    <INPUT VALUE="�������" class=cssButton TYPE=button name=addbutton onclick="unLockManual();">
		  
		  <INPUT VALUE="����ģ�����" class=cssButton TYPE=button name=delbutton onclick="lockBaseManage();">  
		  <INPUT VALUE="�������������" class=cssButton TYPE=button name=delbutton onclick="lockGroupManage();"><br><br>
	</Div>
	
	<Div  id= "divLockBase" style= "display: ''">
      <table  class= common>
  		<TR>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,awe);"></td>
  			<td class="titleImg">����ģ����Ϣ
  			</td>
  		</TR>
        </table>
        <Div  id= "awe" style= "display: ''" class="maxbox1">
        <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ����ģ�����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=BaseModuleCode id=BaseModuleCode >
          </TD>
           <TD  class= title5>
            ����ģ������
          </TD>
          <TD  class=input5>
            <Input class="wid" class= common name=BaseModuleName id=BaseModuleName >
          </TD>
  		</TR>
  		<TR  class= common>
         <TD  class= title5>
            ����ģ������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ModuleDescribe id=ModuleDescribe >
          </TD>
  		</TR>
       
      </table></Div>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD style=" text-align: left" colSpan=1>
			<span id="spanLockBaseGrid" > </span> 
		  </TD>
        </TR>
       
      </table>
    </Div>
       
    <Div id = "divLockBaseButton">
    	<INPUT VALUE="��������ģ��" class=cssButton TYPE=button name=delbutton onclick="addNewLockBase();">  
		  <INPUT VALUE="������Ϣ����" class=cssButton TYPE=button name=delbutton onclick="lockDataManage();">  
		  <INPUT VALUE="�������������" class=cssButton TYPE=button name=delbutton onclick="lockGroupManage();">  
    </Div>
    
    
<Div  id= "divLockGroup" style= "display: ''">
      <table  class= common>
  		<TR>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvb);"></td>
  			<td class="titleImg">������������Ϣ
  			</td>
  		</TR></table>
        <Div  id= "cvb" style= "display: ''" class="maxbox1">
        <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            �������������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common readonly name=LockGroupCode id=LockGroupCode >
          </TD>
           <TD  class= title5>
            ��������������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=LockGroupName id=LockGroupName >
          </TD>
  		</TR>
        <TR  class= common>
          <TD  class= title5>
            ��������������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=LockGroupDescribe id=LockGroupDescribe >
          </TD>
        </TR>
         <TR  class= common>
         
        </TR>
      </table></Div>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD style=" text-align: left" colSpan=1>
			<span id="spanLockGroupGrid" > </span> 
		  </TD>
        </TR>
      </table>
     
    </Div>
       
    <Div id = "divLockGroupButton">
   <INPUT VALUE="������Ϣ����" class=cssButton TYPE=button name=delbutton onclick="lockDataManage();">  
	 <INPUT VALUE="����ģ�����" class=cssButton TYPE=button name=delbutton onclick="lockBaseManage();">  

	  <INPUT VALUE="���ӿ�����" class=cssButton TYPE=button name=addbutton onclick="appendTask();">
	  <INPUT VALUE="ɾ��������" class=cssButton TYPE=button name=delbutton disabled=true onclick="deleteTask();">
    </Div>
    
   <Div  id= "divLockConfig" style= "display: ''">
      <table  class= common>
  		<TR>
  			<td class="titleImg">����������������ϸ
  			</td>
  		</TR>
      </table>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD style=" text-align: left" colSpan=1>
			<span id="spanLockGroupConfigGrid" > </span> 
		  </TD>
        </TR>
      </table>
      <INPUT VALUE="��������" class=cssButton TYPE=button name=delbutton onclick="saveLockGroupConfig();">  
    </Div>
     
    
    
  <INPUT  class=common type=hidden name="hiddenLockData" value="">
  <INPUT  class=common type=hidden name="hiddenLockGroup" value="">
  <INPUT  class=common type=hidden name="hiddenSelectGrid" value="">  
  <INPUT  class=common type=hidden name="hiddenAction" value="">  
  <INPUT  class=common type=hidden name="hiddenLockGroupConfig" value="">  
  </form>
  
  <form action="./queryAllLockInfo.jsp" method=post name=fm1 target="fraSubmit">
	</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>
</body>
</html>
