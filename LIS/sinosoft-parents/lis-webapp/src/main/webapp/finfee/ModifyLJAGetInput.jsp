<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：ModifyLJAGetInput.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="ModifyLJAGetInput.js"></SCRIPT> 
  <%@include file="ModifyLJAGetInit.jsp"%>
  
  <title>变更付款方式</title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./ModifyLJAGetSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <!-- 保单信息部分 -->
   
  <table class= common border=0 width=100%>
    <tr>
            <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<div class="maxbox1" id="maxbox">
    <table  class= common align=center>
    	<TR  class= common>
        <TD  class= title5>
          实付通知书号
        </TD>
        <TD  class= input5>
          <Input NAME=ActuGetNo id="ActuGetNo" class="common wid">
        </TD>
        <TD  class= title5>
          其它号码
        </TD>
        <TD  class= input5>
          <Input NAME=OtherNo id="OtherNo" class="common wid">
        </TD>
      </TR>
      <TR>
        <TD  class= title5>
            管理机构
        </TD>  
        <TD  class= input5>
            <Input class="common wid" name=FManageCom id="FManageCom"  value="<%=tGlobalInput.ComCode%>" readonly >
        </TD>
      </TR>
    </table>
    <INPUT VALUE="查   询" TYPE=button class=cssButton name=butQuery onClick="easyQueryClick();">
    </div>   
    <!-- 付款信息（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 付款信息
    		</td>
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
  	
  	
    <Div id= "divPage" align=center style= "display: none ">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
    </Div>
    </div>
    
    
    <table class= common border=0 width=100%>
    <tr>
            <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox2);">
    		</td>
			<td class= titleImg align= center>录入要修改的信息：</td>
		</tr>
	  </table> 
	 <div class="maxbox" id="maxbox2">    		  								
    <table  class= common align=center>
      <TR CLASS=common>
        <TD  class= title5>
          实付通知书号
        </TD>
        <TD  class= input5>
          <Input NAME=ActuGetNo2 id="ActuGetNo2" class="common wid" verify="实付通知书号|notnull">
        </TD>
        <TD  class= title5>
          付款方式
        </TD>
        <TD  class= input5>
          <Input NAME=PayMode id="PayMode" CLASS="codeno" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|现金^2|现金支票^3|转账支票^4|银行转账" onMouseDown="return showCodeListEx('PayMode', [this,PayModeName],[0,1]);" onDblClick="return showCodeListEx('PayMode', [this,PayModeName],[0,1]);" onKeyUp="return showCodeListKeyEx('PayMode', [this,PayModeName],[0,1]);" verify="银行代码|notnull&code:PayMode"><Input class=codeName name=PayModeName readonly>
        </TD>
        
      </TR>
      
    	<!--TR CLASS=common>
        <TD class=title5>
          银行代码 
        </TD>
        <TD CLASS=input COLSPAN=1>
           <Input class=codeno name=BankCode ondblclick="return showCodeList('bank',[this,BankName],[0,1]);"  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1]);"><input class=codename name=BankName readonly=true> 
        </TD>
        <TD CLASS=title>
          银行账号 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccNo VALUE="" CLASS=coolConfirmBox>
        </TD>
        <TD CLASS=title>
          账户名称 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccName VALUE="" CLASS=common>
        </TD>
      </TR-->
    </table>
    <!--zy 2009-04-09 16:45 优化付费方式变更-->
  <div id=divPayMode1 style="display:none" >
    <Table  class= common  align=center>
    		<tr class= common>
    		</tr>
    </table>
  </div>
  <div id=divPayMode2 style="display:none" >
  	<Table  class= common  align=center>
  			<tr class= common>
  					<td class=title5>
  							票据号码
  					</td>
  					<td class=input5>
  						<Input class="coolConfirmBox wid" name=ChequeNo2 id="ChequeNo2" >
  					</td>
  					<td class=title5>
  							开户银行
  					</td>
  					<td class=input5>
  						<Input class=codeno name=BankCode2 id="BankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" verify="开户银行|code:FINAbank" onDblClick="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');"><input class=codename name=BankCode2Name readonly=true>
  					</td>
  			</tr>  			
  			<!--tr class= common>
  					<td class=title5>
  							银行账号
  					</td>
  					<td class=input5>
  						<Input class=common name=BankAccNo2 >
  					</td>
  					<td class=title5>
  							户名
  					</td>
  					<td class=input5>
  						<Input class=common name=AccName2>
  					</td>
  			</tr-->    		   			  			
  	</table>
  </div>
  <div id=divPayMode3 style="display:none" >
  	<Table  class= common  align=center>
  			<tr class= common>
  					<td class=title5>
  							票据号码
  					</td>
  					<td class=input5>
  						<Input class="coolConfirmBox wid" name=ChequeNo3 id="ChequeNo3" >
  					</td>
  					<td class=title5>
  							开户银行
  					</td>
  					<td class=input5>
							<Input name=BankCode3 class='codeno' id="BankCode3" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');"><input name=BankCode3Name class='codename' readonly=true elementtype=nacessary>
  					</td>
  			</tr>   			
  			<!--tr class= common>
  				 <td class=title5>
  							银行账号
  					</td>
  					<td class=input5>
  						<Input class=common name=BankAccNo3 >
  					</td>
  					<td class=title5>
  							户  名
  					</td>
  					<td class=input5>
  						<Input class=common name=AccName3>
  					</td>
  			</tr-->    		  			  			
  	</table>
  </div>

  <div id=divPayMode4 style="display:none" >
  	<Table  class= common  align=center>
  			<tr class= common>
				    <td class=title5>
  							开户银行
  					</td>
  					<td class=input5>
							<Input class=codeno name=BankCode4 id="BankCode4" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('bank',[this,BankCode4Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('bank',[this,BankCode4Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('bank',[this,BankCode4Name],[0,1],null,mSql,'1');"><input class=codename name=BankCode4Name readonly=true>
  					</td>
  			</tr>
  			<tr class= common>
  					<td class=title5>
  							银行账号
  					</td>
  					<td class=input5>
  						<Input class="coolConfirmBox wid" name=BankAccNo4 id="BankAccNo4" onBlur="checkBank(BankCode4,BankAccNo4);">
  					</td>
  					<td class=title5>
  							户  名
  					</td>
  					<td class=input5>
  						<Input class="common wid" name=AccName4 id="AccName4" >
  					</td>
  			</tr>
  			<tr class= common>
		      <td class=title5>
		       	证件类型
		     	</td>
		     	<td class=input5>
						<Input class=codeno name=IDType id="IDType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onDblClick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onKeyUp="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true>							        			
		     	</td>
		  		<td class=title5>
		     		证件号码
		   		</td>
		  		<td class=input5>
						<Input class="common wid" name=IDNo id="IDNo" >							        			
		   		</td>
      	</tr> 			
  	</table>
    </div>
  </div>
    <br>
    <table align=left>
      <tr>
        <td>         
    <INPUT VALUE="保存数据" class= cssButton TYPE=button onClick="submitForm()" name=butSave>
        </td>
        <td>
    <INPUT VALUE="读取保单银行信息" class= cssButton TYPE=button onClick="readBankInfo()">
        </td>
      </tr>
    </table>
    <INPUT VALUE="" TYPE=hidden name=serialNo>
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
<script>
var bCom=comCode.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}
var mSql="1 and comcode like #"+bCom+"%#" ;	
</script>
