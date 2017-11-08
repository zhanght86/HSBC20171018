<%--@page language="java" --%>
<%-- @page contentType="text/html;charset=GBK" --%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-07-19 11:48:25
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
 GlobalInput tGI1 = new GlobalInput();
 tGI1=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
 String ManageCom = tGI1.ManageCom;
%>
<script>
  var manageCom = "<%=tGI1.ManageCom%>"; //记录管理机构
  var comcode = "<%=tGI1.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LFGetPayInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="LFGetPayInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form action="./LFGetPaySave.jsp" method=post name="fm" id=fm target="fraSubmit">
    <!--%@include file="../common/jsp/OperateButton.jsp"%-->
    <!--%@include file="../common/jsp/InputButton.jsp"%-->
  <table class= common border=0 width=100%>
    <tr>
      <td class= titleImg align= center> 请输入生存领取查询条件：</td>
    </tr>
  </table> 
   <div class=maxbox1>   
  <table  class= common align=center>
    <TR  class= common>
      <TD  class= title5>团体保单号</TD>
      <TD  class= input5><Input class="common wid" name=GrpContNo id=GrpContNo > </TD>
      <TD  class= title5></TD>
	  <TD  class= input5></TD>
    </TR>
  </table>  
  <INPUT class="cssButton" VALUE=" 查  询 " TYPE=button onclick="easyQueryClick();">  
 </div>
  <Div  id= "divLCGet" style= "display: none">
     <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
        </td>
        <td class= titleImg>
          团体保下领取信息
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td style="text-align: left" colSpan=1>
          <span id="spanLJSGetGrid" > </span> 
        </td>
      </tr>	
    </table>
    
    
    <br>
  <Div id= "divLCGetButton" style= "display: ''" align="center">
    	
        <INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage2.firstPage();"> 
				<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage2.previousPage();"> 					
				<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage2.nextPage();"> 
				<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage2.lastPage();">  			
<br>
<br>
<table  class= common align=center>
    <TR  class= common>
      <TD  class= title5>申请人姓名</TD>
      <TD  class= input5><Input class="common wid" name=LFAppName id=LFAppName > </TD>
      <TD  class= title5></TD>
	  <TD  class= input5></TD>
    </TR>
  </table>  
  </div> 
  <Input class= cssButton type=Button value="确认领取" onclick="confirmGet()">	
  <br>
</div>  
<div id="divGetDrawInfo" style= "display:none">
	     <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
        </td>
        <td class= titleImg>
          个人领取记录明细
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td style="text-align: left" colSpan=1>
          <span id="spanSubPayGrid" > </span> 
        </td>
      </tr>	
    </table>
      <Div id= "divLCGetDrawButton" style= "display: ''" align="center">
    	
				<INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage1.firstPage();"> 
				<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage1.previousPage();"> 					
				<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage1.nextPage();"> 
				<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage1.lastPage();">  			

  </div> 
</div>
 <br>
 <DIV id = "divGetInfo" style="display:none">	
 	<table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
        </td>
        <td class= titleImg>
          领取方式信息
        </td>
      </tr>
    </table>
	<div class=maxbox1>
 	<TABLE class=common>
      <tr class=common>
         <td class=title> 领取方式 </td>
         <TD  class= input><input class="codeno" name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('edorgetpayform',[this,PayModeName],[0,1],null,null,null,null,'207');" onkeyup="showCodeListKey('edorgetpayform', [this,PayModeName],[0,1],null,null,null,null,'207');"><input class="codename" name=PayModeName id=PayModeName readonly></TD>
         <td class=title>领取人</td>
         <td class= input><Input type="input" class="common wid" name=Drawer></td>
         <td class=title>领取人身份证号</td>
         <td class= input><Input type="input" class="common wid" name=DrawerIDNo></td>
       </tr>
      <tr class=common>
         <td class="title">开户银行</td>
         <td class="input"><Input type="text" class="codeno" name="BankCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('bank',[this,BankName],[0,1],null,null,null,0,317)" onkeyup="showCodeListKey('bank',[this,BankName],[0,1],null,null,null,0,317)"><input type="text" class="codename" name="BankName" id=BankName readonly></td>
         <td class="title">银行账户</td>
         <td class="input"><input type="text" class="coolConfirmBox wid" name="BankAccNo" id=BankAccNo></td>
         <td class="title">账户名</td>
         <td class="input"><Input type="text" class="common wid" name="AccName" id=AccName></td>
      </tr>
   </table>
  <br> 
  </div>
   	
   <Input class= cssButton type=Button value="修改记录" onclick="updateSubAcc()">
   <Input class= cssButton type=Button value="批量导入" onclick="showPage(this,divDiskApp)">	
   
</DIV>

        <Div  id= "divGetNotice" style= "display: none">
            <br>
            <INPUT VALUE=" 打印领取清单 " class=cssButton TYPE=button onclick="GetNotice();">
        </Div>
    <input type=hidden id="CommitFlag" name="CommitFlag">
    <input type=hidden id="ContNo" name="ContNo">
    <input type=hidden id="PrtSeq" name="PrtSeq">
    <input type=hidden id="fmtransact" name="fmtransact">
     <input type=hidden id="GrpContNoBak" name="GrpContNoBak">
  </form>
  
  <form action="./LFGetPayLoad.jsp" method=post name=fm2 id=fm2 target="fraSubmit" enctype="multipart/form-data"> 
  	 <div id="divDiskApp" style="display: none" align="center">
       <table>
         <TR class= common>
     	 	 <TD class=common>
						<Input type="file" name=FileName>
       		 	<Input class= cssButton type=Button value=" 导  入 " onclick="diskInput();">
     	 	 </TD>
     	  </TR>
     </table>
     <br>
      <center>
      		<a href="./SubAcc.xls">下载分帐户给付导入模板</a><br> 
      </center>
  </div>
</form>
<br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
