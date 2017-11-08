<%
//程序名称：PubLockConfig.jsp
//程序功能：
//更新记录：  更新人    更新日期     更新原因/内容
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
  		 <TD class="titleImg">当前锁表信息</td>
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
    				<INPUT  VALUE="首  页" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> </td>
			    <td>  
			      <INPUT  VALUE="上一页" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="下一页" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="尾  页" TYPE=button class=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
   </Div>
	<div id="divUnLockReason" style="display: none">
        <table class="common">
            <tr class="common">
                <td class="title5">
                    解锁原因：
                </td>
            
                <td colspan="3">
                    <textarea class="common" rows="4" cols="146" name="UnLockReason" value=""></textarea>

                </td>
            </tr>

        </table>
    </div>

	<Div id = "divAllLockInfoButton" style= "display: ''">
	    <INPUT VALUE="任务刷新" class=cssButton TYPE=button name=delbutton onclick="refreshTask();">
	    <INPUT VALUE="解除锁定" class=cssButton TYPE=button name=addbutton onclick="unLockManual();">
		  
		  <INPUT VALUE="基本模块管理" class=cssButton TYPE=button name=delbutton onclick="lockBaseManage();">  
		  <INPUT VALUE="并发控制组管理" class=cssButton TYPE=button name=delbutton onclick="lockGroupManage();"><br><br>
	</Div>
	
	<Div  id= "divLockBase" style= "display: ''">
      <table  class= common>
  		<TR>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,awe);"></td>
  			<td class="titleImg">控制模块信息
  			</td>
  		</TR>
        </table>
        <Div  id= "awe" style= "display: ''" class="maxbox1">
        <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            控制模块编码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=BaseModuleCode id=BaseModuleCode >
          </TD>
           <TD  class= title5>
            控制模块名称
          </TD>
          <TD  class=input5>
            <Input class="wid" class= common name=BaseModuleName id=BaseModuleName >
          </TD>
  		</TR>
  		<TR  class= common>
         <TD  class= title5>
            控制模块描述
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
    	<INPUT VALUE="新增基本模块" class=cssButton TYPE=button name=delbutton onclick="addNewLockBase();">  
		  <INPUT VALUE="锁定信息管理" class=cssButton TYPE=button name=delbutton onclick="lockDataManage();">  
		  <INPUT VALUE="并发控制组管理" class=cssButton TYPE=button name=delbutton onclick="lockGroupManage();">  
    </Div>
    
    
<Div  id= "divLockGroup" style= "display: ''">
      <table  class= common>
  		<TR>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvb);"></td>
  			<td class="titleImg">并发控制组信息
  			</td>
  		</TR></table>
        <Div  id= "cvb" style= "display: ''" class="maxbox1">
        <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            并发控制组编码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common readonly name=LockGroupCode id=LockGroupCode >
          </TD>
           <TD  class= title5>
            并发控制组名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=LockGroupName id=LockGroupName >
          </TD>
  		</TR>
        <TR  class= common>
          <TD  class= title5>
            并发控制组描述
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
   <INPUT VALUE="锁定信息管理" class=cssButton TYPE=button name=delbutton onclick="lockDataManage();">  
	 <INPUT VALUE="基本模块管理" class=cssButton TYPE=button name=delbutton onclick="lockBaseManage();">  

	  <INPUT VALUE="增加控制组" class=cssButton TYPE=button name=addbutton onclick="appendTask();">
	  <INPUT VALUE="删除控制组" class=cssButton TYPE=button name=delbutton disabled=true onclick="deleteTask();">
    </Div>
    
   <Div  id= "divLockConfig" style= "display: ''">
      <table  class= common>
  		<TR>
  			<td class="titleImg">并发控制组配置明细
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
      <INPUT VALUE="保存配置" class=cssButton TYPE=button name=delbutton onclick="saveLockGroupConfig();">  
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
