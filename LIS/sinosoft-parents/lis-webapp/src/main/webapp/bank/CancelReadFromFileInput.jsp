<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：ReadFromFileInput.jsp
//程序功能：
//创建日期：2009-9-2 11:10:36
//创建人  ：杨林海
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="CancelReadFromFileInput.js"></SCRIPT>
  <%@include file="CancelReadFromFileInit.jsp"%>
  
  <title>返回文件撤销 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  String tTempBankFlag = request.getParameter("BankFlag");
  String tYBTBankCode = request.getParameter("YBTBankCode");
  loggerDebug("CancelReadFromFileInput","tTempBankFlag: "+tTempBankFlag);
  loggerDebug("CancelReadFromFileInput","tYBTBankCode: "+tYBTBankCode);
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
  dealType = "<%=request.getParameter("DealType")%>";
  var tBankFlag = "0";  //0 正常银行接口,1 银保通批量代付
  var tNewBankFlag = "<%=tTempBankFlag%>";
  var tYBTBankCode = "<%=tYBTBankCode%>";
  if( tNewBankFlag != null && tNewBankFlag != "null" && tNewBankFlag != "" )
  {
    tBankFlag = tNewBankFlag;
  }
  
  //alert("BankFlag: "+tBankFlag);
  
  //银行编码下拉菜单过滤条件
  var tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and not exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code1 = a.BankCode and OtherSign=#1#)";
  //easyQueryClick查询时过滤条件
  var tBankDataSQL = " and not exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code1 = BankCode and OtherSign='1') ";
  
  if(tBankFlag == "1")   //如果是银保通批量代付
  {
    tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code=#"+tYBTBankCode+"# and Code1 = a.BankCode and OtherSign=#1#)";
    tBankDataSQL = " and exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code='"+tYBTBankCode+"' and Code1 = BankCode and OtherSign='1') ";
  }
</script>

<body  onload="initForm();" >
  <form action="./CancelReadFromFileSave.jsp"  method=post name=fm id=fm target="fraTitle" >
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <table class= common border=0 width=100%>
      <tr>
       <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
  			<td class= titleImg align= center>请输入查询条件：</td>
  		</tr>
  	</table>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >	
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            银行编码
          </TD>
          <TD  class= input5>
			 <Input class=codeno name=BankCode readonly 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');" 
             ondblclick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');" 
              onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');"
               verify="银行代码|notnull&code:bank" readonly=true><input class=codename name=BankName readonly=true>
          </TD>
          <TD  class= title5>
            批次号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=SerialNo >
          </TD>

        </TR>
        <TR  class= common>
          <TD  class= title5>
            回盘时间
          </TD>
          <TD  class= input5>
            <Input class="common wid" dateFormat="short" name=ReturnDate >
          </TD>
					<TD  class= title5>
            回盘文件名称（模糊查询）
          </TD>
          <TD  class= input5>
			 			<Input class="common wid" name=InFile>
          </TD>
        </TR>
    </table>
    </div>
    </div>
<!--<INPUT VALUE="查  询" TYPE=button class=cssButton onClick="easyQueryClick();">-->
 <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>

<br><br>  
    <!-- 生成送银行文件 fraSubmit-->
    
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divBank1);">
            </TD>
			<td class= titleImg align= center>撤销文件信息</td>
  		</tr>
  	</table>   
        
   
  	<Div  id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
   <!-- <Div id= "divPage" align=center style= "display: none ">
    <INPUT CLASS="cssButton90" VALUE="首页" TYPE=button onClick="turnPage.firstPage();"> 
    <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
    <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
    <INPUT CLASS="cssButton93" VALUE="尾页" TYPE=button onClick="turnPage.lastPage();">
    </Div>-->
    
    <br>
     
     <table class= common border=0 width=100%>
      <tr>
       <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo1);">
            </TD>

  			<td class= titleImg align= center>请录入回滚原因</td>
  		</tr>
        	</table>
      <div id="divInvAssBuildInfo1">
       <div class="maxbox1" >	      
       <table>     
  		<tr>
  			<td><textarea rows="4" cols="100" name=backReason class="common" ></textarea></td>
  	  </tr>
  	</table>
   </div>
   </div> 
    


   <!-- <INPUT VALUE="撤销确认" class= cssButton TYPE=button onClick="submitForm()">   --> 
    <a href="javascript:void(0);" class="button"onClick="submitForm()">撤销确认</a>  										
  <br> <br> <br> <br>
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
