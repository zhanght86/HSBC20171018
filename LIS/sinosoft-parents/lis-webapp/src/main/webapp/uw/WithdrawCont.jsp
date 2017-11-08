<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：WithdrawCont.jsp
//程序功能：撤单
//创建日期：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="WithdrawCont.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>撤单</title>
  <%@include file="WithdrawContInit.jsp"%> 
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./WithdrawContChk.jsp">
  <!-- 撤单查询条件 -->
    <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class= titleImg>请输入查询条件：</td>
	</tr>
    </table>
    <Div  id= "divPayPlan1" style= "display:  " class="maxbox1">
    <table  class= common>
      	<tr CLASS="common">
					<td  class= title>印刷号</td>
			        <td  class= input><input class="wid" class= common name=PrtNo id=PrtNo ></td>
					<td  class= title>业务员代码</td>
					<td class="input">
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="AgentCode" id="AgentCode" VALUE="" CLASS="codeno" ondblclick="return queryAgent();" onclick="return queryAgent();" ><input name=AgentName id=AgentName class='codename' readonly=true  elementtype=nacessary >
					</td>
                    <td  class=title>销售渠道</td>
				<td  class=input>
					<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SaleChnl id=SaleChnl ondblclick="showCodeList('SaleChnl',[this,SaleChnlName1],[0,1]);" onclick="showCodeList('SaleChnl',[this,SaleChnlName1],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName1],[0,1]);"><input class=codename name=SaleChnlName1 id=SaleChnlName1 readonly=true>
				</td></tr>
                    <tr CLASS="common">
					
                <td  class= title>管理机构</td>
				<td  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true>
				</td> 
                  <td  class= title>投保人</td>
					<td  class= input><Input class="wid" class= common name=AppntName id=AppntName ></td>
					<td  class= title>被保人</td>
					<td  class= input><Input class="wid" class= common name=InsuredName id=InsuredName ></td> 		
		</tr>
         
        <!--
        <tr>
        	<td  class= title> 保单类型 </td>
          <td  class= input>
            <Input class="codeno" name=ContType ondblclick="return showCodeList('conttype',[this,conttypeName],[0,1]);" onkeyup="return showCodeListKey('conttype',[this,conttypeName],[0,1]);"><input class="codename" name=conttypeName readonly=true>
          </td>  
       </tr>   
       -->     
    </table></div>
          <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="withdrawQueryClick();">
    
    <!--待撤销合同信息-->
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divWithDContAll);">
    		</td>
    		<td class= titleImg>
    			 撤单工作池
    		</td>
      </tr>
  </table>
  <Div  id= "divWithDContAll" style= "display:  " >
      <table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1 >
  					<span id="spanWithDContAllGrid">
  					</span>
  			  	</td>
  			</tr>
      </table>
      <div align=center>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPageAll.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPageAll.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPageAll.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPageAll.lastPage();">
    </div>
  </Div>
  
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divWithDCont);">
			</td>
			<td class= titleImg>投保单信息</td>
		</tr>
	</table>
 <Div  id= "divWithDCont" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1 >
  				<span id="spanWihtDContGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	<div align=center>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </div></Div>
   <INPUT VALUE="操作履历查询" class=cssButton TYPE=button name="uwButton6" onclick="QueryRecord()"><br><br>   
   <div class="maxbox1">
   <table class = common border=0>
    <TR class=common >
       <TD  class= title5>
    	  撤单原因
    	 </TD>
        <TD  class= input5 ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=UWWithDReasonCode id=UWWithDReasonCode readonly ondblclick="showCodeList('UWWithDReason', [this,UWWithDReason],[0,1],null,'1 and (code <=#05# or code >=#14#)','1');" onclick="showCodeList('UWWithDReason', [this,UWWithDReason],[0,1],null,'1 and (code <=#05# or code >=#14#)','1');" onkeyup="return showCodeListKey('UWWithDReason', [this,UWWithDReason],[0,1],null,'1 and (code <=#05# or code >=#14#)','1');"><Input class="codename" name=UWWithDReason id=UWWithDReason readonly>
        </TD>
        <TD class= title5></TD>
      <td class="input5"></td>
       </TR>
    </table>    
  <table class = common>
    <TR  class= common> 
      <TD class= title5> 撤单说明(20字以内) </TD>
      <td class="input5"></td>
      <TD class= title5></TD>
      <td class="input5"></td>
    </TR>
    <TR  class= common>
      <TD style="padding-left:16px" colspan="4"><textarea name="Content" id="Content" cols="188" rows="4" class="common"></textarea></TD>
    </TR>
  </table></div>
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "PrtNoH" value= "">  
    <input type= "hidden" name= "ContNoH" value= "">   
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" name= "sure" class= cssButton  value=" 确  认 " onClick="submitForm()">
<br><br><br><br>
</body>
</html>
