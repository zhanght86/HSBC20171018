<%
//�������ƣ�TaskService.jsp
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>
<html> 
<script>
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/javascript/jquery.js"></script>
 <script src="../common/javascript/jquery.form.js"></script>
 <script src="../common/javascript/jquery.easyui.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <%@include file="TaskPlanDefInit.jsp"%>
  <SCRIPT src="TaskPlanDef.js"></SCRIPT>
  <script language=javascript>
  	var specialcode="<DIV id=searchspe style='Z-INDEX: 100; right: 10px;POSITION: absolute; TOP: 5px; float:right'>"
  	               + "<table cellSpacing=0 cellPadding=0 width=450 style='border-left:1px solid #2E6287;border-top:1px ;border-right:1px solid #2E6287;font-size:12px; border-bottom-width:1px'>"
  	               + " <INPUT VALUE=\"���Ӽƻ�\" class=cssButton TYPE=button name=addbutton onclick=\"appendOne();\">"
		  	           + " <INPUT VALUE=\"ɾ���ƻ�\" class=cssButton TYPE=button name=delbutton onclick=\"deleteOne();\">"
		  	           + " <INPUT VALUE=\"����/�����ƻ�\" class=cssButton TYPE=button name=addbutton onclick=\"activateOne();\">"
		               + " <INPUT VALUE=\"ֹͣ�ƻ�\" class=cssButton TYPE=button name=delbutton onclick=\"deactivateOne();\">"
		               + " <INPUT VALUE=\"�ƻ�ˢ��\" class=cssButton TYPE=button name=delbutton onclick=\"refreshTask();\">"
  	               + "</table></DIV>";
  	
  	
  	document.write(specialcode);
  	lastScrollY=0;
  	function heartBeat0(){
  		diffY=document.body.scrollTop;
  		   percent=.1*(diffY-lastScrollY);
  		if(percent>0)
  		    percent=Math.ceil(percent);
  		else 
  			  percent=Math.floor(percent);
  			  
  		//percent = percent  + 1;
  		document.all.searchspe.style.pixelTop+=percent;
  		
  		lastScrollY=lastScrollY+percent;
  	}
  	window.setInterval("heartBeat0()",1);
   
   ��$(document).ready(function() {
      initForm();
       }); 
   </script> 
   <style>
   #divSimpleMode td.mulinetitle{
	   background:#eaeaea;
   }
   #divSimpleMode input.mulinetitle{
	   background:#eaeaea;
	   color:#000;
	   border:none;
   }
   #divSimpleMode td.muline{
	   border-right:none;
   }
   #divSimpleMode table.muline{
	   BORDER-right: #f3f3f3 3pt solid;
   }
   
   </style>
   
</head>

<body  onload="" >
  <form action="./TaskPlanDefChk.jsp" method=post name=fm id=fm target="fraSubmit">
<br>
<br>
     <Div id = "divTaskPlanButton" style= "display: ''" align='right'>
	      <!--INPUT VALUE="���Ӽƻ�" class=cssButton TYPE=button name=addbutton onclick="appendOne();">
		  <INPUT VALUE="ɾ���ƻ�" class=cssButton TYPE=button name=delbutton onclick="deleteOne();">
		  <INPUT VALUE="�����ƻ�" class=cssButton TYPE=button name=addbutton onclick="activateOne();">
		  <INPUT VALUE="ֹͣ�ƻ�" class=cssButton TYPE=button name=delbutton onclick="deactivateOne();">
		  <INPUT VALUE="�ƻ�ˢ��" class=cssButton TYPE=button name=delbutton onclick="refreshTask();"-->

	</Div>
    <Div  id= "divTaskPlan" style= "display: ''">
      <table  class= common>
  		<TR class=common>
  			<td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);" >
  		   <td class=titleImg>������Ϣ</td>
  		  </td>
  	    </TR>
  	   </table><Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
  	   <table  class= common>
        <TR  class= common>
          <TD  class= title>����ƻ�����</TD>
          <TD  class= input><Input class="wid" class=common readonly name=TaskPlanCode id=TaskPlanCode ></TD>
          <TD  class= title>��������/������б���</TD>
          <TD  class= common><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=TaskCode id=TaskCode  CodeData = "" ondblclick="showCodeList('TaskCode',[this, ''],[0,1],null,null,null,1,250);" onclick="showCodeList('TaskCode',[this, ''],[0,1],null,null,null,1,250);" onkeyup="showCodeListKey('TaskCode',[this,''],[0,1],null,null,null,1,250);"></TD>
          <TD  class= title>�Ƿ�����</TD>
          <TD  class= input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=RunFlag id=RunFlag  CodeData = "0|^0|ͣ��^1|����" ondblclick="return showCodeListEx('RunFlag',[this],[0],null,null,null,1,150);" onclick="return showCodeListEx('RunFlag',[this],[0],null,null,null,1,150);" onkeyup="return showCodeListEx('RunFlag',[this],[0],null,null,null,1,150);"></TD>   
        </TR>
        <TR  class= common>
        	<TD  class= title>��ʼʱ��(YYYY-MM-DD HH:MM:SS)</TD>
          <TD  class= input><!--<Input class="timeDatePicker"  dateFormat="short"  name=StartTime >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>��ֹʱ��(YYYY-MM-DD HH:MM:SS)</TD>
          <TD  class= input><!--<Input class="timeDatePicker"  dateFormat="short"  name=EndTime >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>ʧ�ܺ���</TD>
          <TD  class= input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=RunAfterFlag id=RunAfterFlag  CodeData = "0|^00|�޶���^01|����" ondblclick="return showCodeListEx('RunAfterFlag',[this],[0],null,null,null,1,150);" onclick="return showCodeListEx('RunAfterFlag',[this],[0],null,null,null,1,150);" onkeyup="return showCodeListEx('RunAfterFlag',[this],[0],null,null,null,1,150);"></TD>   

        </TR>
        
      </table></Div>
      
      <table  class= common>
  		<TR  class= common>
  		 <TD class="titleImg">
  		   ����ִ�мƻ�</td>
  	    </TR>
  	   </table>
  	  <div class=maxbox1>
     <%@include file="TaskPlanCrontabDef.jsp"%>
     </div>  
		   <table  class= common>
  		<TR>
  		 <TD class="titleImg">��Ϣ���Ѷ���</td>
  	    </TR>
  	   </table>
	   <div class=maxbox1>
  	 <%@include file="TaskPlanMsgAlertDef.jsp"%>
		</div>  
		  <table class= common>
  		<TR>
  			<td class="titleImg">���нڵ���Ϣ(��¼��Ĭ���ڱ��ڵ�����)
  			</td>
  		</TR>
  	 </table>
     <table  class= common>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanParamGrid" > </span> 
		  </TD>
        </TR>
      </table>
      <table  class= common>
  		<TR>
  			<td class="titleImg">����������Ϣ
  			</td>
  		</TR>
  	  </table>
      <table  class= common>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanMoreParamGrid" > </span> 
		  </TD>
        </TR>
      </table>
      <!-- -->
      <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
          <td class=titleImg>
            �ѱ������������Ϣ
          </td>
        </td>
      </tr>
    	</table>
    
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskPlanGrid" > </span> 
		  </TD>
       </TR>
      </table> 

      <!--<input CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPagePlan.firstPage();"> 
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPagePlan.previousPage();"> 					
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPagePlan.nextPage();"> 
      <input CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPagePlan.lastPage();">	-->    				

   </Div>

    <input type= "hidden" name= "fmAction" value="">

	
	
	
 <INPUT  class=common type=hidden name="RunString" value="">

  </form>
  
   <form action="" method=post name=fmtest target="fraSubmit">
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>
</body>
</html>
