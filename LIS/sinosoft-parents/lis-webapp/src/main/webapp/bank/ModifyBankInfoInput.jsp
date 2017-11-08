<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：ModifyBankInfoInput.jsp
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

  <SCRIPT src="ModifyBankInfoInput.js"></SCRIPT> 
  <%@include file="ModifyBankInfoInit.jsp"%>
  
  <title>修改账户信息 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./ModifyBankInfoSave.jsp" method=post name=fm id="fm" target="fraTitle">
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
            暂收收据号/银行划款协议书号
         </TD>
         <TD  class= input5>
           <Input NAME=TempfeeNo class="common wid">
        </TD>
        <TD  class= title5>
            管理机构
        </TD>  
        <TD  class= input5>
            <Input class="common wid" name=FManageCom  value="<%=tGlobalInput.ComCode%>" readonly >
        </TD> 
       </TR>
     </table> 
     
    	  <!--TD  class= title>
            印刷号
         </TD>
         <TD  class= input>
           <Input NAME=PrtNo class=common>
         </TD--> 
         <!--取消保全收费的特殊处理，保全的变更在保全的转账问题件相关功能中完成-->
          <!--TD  class= title>
            收费类型
          </TD>
          <TD  class= input>
          	<Input class="codeno" name=FeeType verify="收费类型|notnull" CodeData="0|^1|保全收费^2|其他收费" ondblclick="return showCodeListEx('FeeType',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('FeeType',[this,FeeTypeName],[0,1]);" ><input class=codename name=FeeTypeName readonly=true>
          </TD>
        </TR>
    </table>    
      <Div id='divFeeType1' style='display:none'>
       <table class=common align=center>            
       <TR class=common >                                  
         <TD  class= title>
            交费通知书号
         </TD>
          <TD  class= input>
      			<Input class=common name=GetNoticeNo >
          </TD>          
        </TR> 
        </table> 
      </Div>        

     <Div id='divFeeType2' style='display:none'>
       <table class=common align=center>
       <TR class=common>                                  
         <TD  class= title>
            印刷号
         </TD>
         <TD  class= input>
           <Input NAME=PrtNo class=common>
         </TD>        
        </TR>
        </table> 
      </Div-->           
      <INPUT VALUE="查  询" TYPE=Button class=cssButton name=butQuery onClick="easyQueryClick();">
      </div>
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox2);">
</td>
			<td class= titleImg align= center> 
				请确认暂交费中录入的数据：
			</td>
  		</tr>
  	</table>   
    <div class="maxbox" id="maxbox2">   
    <!-- 暂交费信息（列表） -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 暂交费信息
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
  	</div>
  	
    <Div id= "divPage" align=center style= "display:none ">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
    </Div>
   </div>
    <br><br>
   
    <table class= common border=0 width=100%>
    <tr>
            <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox3);">
</td>
			<td class= titleImg align= center>录入要修改的银行信息，必须全部录入：</td>
		</tr>
	  </table> 
	 <div class="maxbox1" id="maxbox3">	
    <table  class= common align=center>
      <TR CLASS=common>
        <!--调整为银行划款协议书号-->
        <TD  class= title>
          收据号
        </TD>
        <TD  class= input>
          <Input readonly  NAME=TempfeeNo1 class="common wid">
        </TD>
        <!--TD  class= title>
          交费通知书号
        </TD>
        <TD  class= input>
          <Input readonly class=readonly NAME=GetNoticeNo2 class=common>
        </TD>
        
        <TD  class= title>
          交费方式
        </TD>
        <TD  class= input>
          <Input class="codeno" name=PayMode CodeData="0|^1|现金^4|银行转账" ondblclick="return showCodeListEx('PayMode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKeyEx('PayMode',[this,PayModeName],[0,1]);" ><input class=codename name=PayModeName readonly=true>
        </TD-->
      </TR>
      
    	<TR CLASS=common>
        <TD CLASS=title>
          银行代码 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input class=codeno name=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('bank',[this,BankName],[0,1],null,mSql,'1');" onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,mSql,'1');" verify="银行代码|notnull&code:bank"><input class=codename name=BankName readonly=true>
        </TD>
        <TD CLASS=title>
          账户名称 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccName VALUE="" CLASS="common wid" MAXLENGTH=20 >
        </TD>
        <TD CLASS=title>
          银行账号 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccNo VALUE="" CLASS="coolConfirmBox wid" MAXLENGTH=40 onBlur="checkBank(BankCode,AccNo);">
        </TD>
      </TR>
    </table>
    
    <br>
    <INPUT VALUE="保存数据" class= cssButton TYPE=Button name=butSave onClick="submitForm()">
    <INPUT VALUE="" TYPE=hidden name=serialNo>
  </div>
  <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
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

