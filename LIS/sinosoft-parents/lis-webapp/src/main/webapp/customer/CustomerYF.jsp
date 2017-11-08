<%
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String ReadType = "readonly";
	String PayRead = "readonly=true";
	try 
	{		
		String Type = request.getParameter("type");
		if (Type!=null)
		{
		 if (Type.equals("1"))
		 ReadType = "coolDatePicker";
		 PayRead = "";
		}
		loggerDebug("CustomerYF","ReadType:     "+ReadType);
	}
	catch( Exception e )
	{ 
		ReadType = null;
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="CustomerYF.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CustomerYFInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<Form action="" method=post name=fm id="fm" target="fraSubmit">
<script>
	   var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script> 
<Div  id= "Frame1" style= "display: none">        
    <Table>
   	  <tr>
        <td class="common wid">
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
          <td class= titleImg>公共信息</td>
   	    </td>
   	  </tr>
    </Table>
    <div class="maxbox1">
        <Table class= common border=0 >
          <TR  class= common>
            <TD  class= title>收费机构</TD>          
            <TD  class= input><Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true ></TD>
            <TD  class= title>操作员号码</TD>
            <TD  class= input><Input class="readonly wid" name=Operator id="Operator" readonly=true></TD>
 		        <TD class= title>交费日期</TD>
            <TD class= input ><Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="交费日期|DATE" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
          </TR>
        </Table>
      
    </div>
</Div>
 <!-- <input type =button class=cssButton value="查&nbsp;&nbsp;询" onclick="queryClick();"> -->
  <Table>
    <tr>
      <td class="common wid"><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFeeInput);"></td>
    	<td class= titleImg>录入财务收费信息</td>
    </tr>
  </Table>
 <Div id= "divFinFeeInput" style= "display: ''">
  <div class="maxbox1">
 		 <Div id= "divTempFeeClassInput" style="display:none">
		  <table  class= common border=0>
			  <tr class= common>
				  <TD  class= title5>交费方式</TD>
 	     	  <TD  class= input5 width="25%"><Input class=codeno name=PayMode id="PayMode" value=5><input class=codename name=PayModeName id="PayModeName" value = 应付转入 readonly=true></TD>
 	     	  <TD  class= title5></TD>
 	     	  <TD  class= input5></TD>
			  </tr>
 	      <tr class= common>
 	     	  <TD  class= title5>币种选择</TD>
 	     	  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=Currency id="Currency" onclick="return showCodeList('currency',[this,CurrencyName],[0,1]);" ondblclick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onkeyup="return showCodeListKey('currency',[this,CurrencyName],[0,1]);" ><input class=codename name=CurrencyName id="CurrencyName" readonly=true></TD>
 	     	  <!--TD  class= title>暂收收据号码</TD>
 	     	  <TD  class= input><Input class="common wid" name=TempFeeNo1 onblur="confirmSecondInput(this,'onblur');" ></TD>
 	     	  <TD  class= title>交费人姓名</TD>
 	     	  <TD  class= input><input class="common wid" name=PayName ></TD-->
 	      </tr>
 	 	  </Table> 
 		 </Div>
 		
 		<!------------内部转账------------>
 		 <div id="divPayMode5" style="display:''" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title5>实付通知书号</td>
 					<td class=input5><Input class="common wid" name=ActuGetNo5 id="ActuGetNo5"></td>
 					<td class=title5>保全受理号/赔案号/投保单印刷号</td>
 					<td class=input5><Input class="common wid" name=OtherNo5 id="OtherNo5"></td>
 					<!-- <td class=title><input class=cssButton type="button" value="查  询" style="width:60" onclick="queryLJAGet();"></td>
 					<td class=input></td> -->
 				</tr>
 				<tr class= common>
 					<td class=title5>给付金额</td>
 					<td class=input5><Input class="readonly wid" name=PayFee5 id="PayFee5" readonly=true></td>
 					<td class=title5>领取人</td>
 					<td class=input5><Input class="readonly wid" name=Drawer5 id="Drawer5" readonly=true></td>
 					<!-- <td class=title><Input class="common wid" type=hidden name=EnterAccDate></td>
 					<td class=input><Input class="common wid" type=hidden name=DrawerID></td> -->
 				</tr>
 		  </table>
 		 </div>
  </div>
  <div>
  </div>
  <br>
  <input class=cssButton type="button" value="查  询" style="width:60" onclick="queryLJAGet();"> 

  <Div  id= "divFinFee1" style= "display: ''" >
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1><span id="spanQueryLJAGetGrid" ></span> </TD>
      </TR>
    </Table>
    <div align=center style= "display: none">
    <INPUT CLASS="cssButton90" VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
    <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
    <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
    <INPUT CLASS="cssButton93" VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
    </div>
  </Div>
</Div>
<div>
</div>
<br>
 	  <input type =button class=cssButton value="确  定" 	onclick="addMul();"> 
 		<!--<input type =button class=cssButton	value="修  改" 	onclick="ModMul();">-->
 		 <!--录入的暂交费表部分 -->
 		<Table  class= common>
 		  <tr>
 		   	<td text-align: left colSpan=1><span id="spanFinFeeGrid" ></span></td>
 		  </tr>
 		</Table>

 <table>
   <tr>
      <td class="common wid"><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame2);"></td>
    	<td class= titleImg>录入业务费用信息</td>
   </tr>
 </table>

 <Div  id= "Frame2" style= "display: ''">
 <div class="maxbox1">
    <table class= common> 
    	<TR  class= common>
    		<TD  class= title5>交费类型</TD>
   		  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=TempFeeType id="TempFeeType" onclick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" ondblclick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onkeyup="return showCodeListKey('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class=codename name=TempFeeTypeName id="TempFeeTypeName" readonly=true></TD>
   		  <TD  class= title5>管理机构</TD>
    	  <TD  class= input5><Input class="readonly wid" name=PolicyCom id="PolicyCom" readonly=true></TD>
      </TR>
      <TR  class= common>
   		  <TD  class= title5>业务币种</TD>
	  	  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=OpeCurrency id="OpeCurrency" onclick="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" ondblclick="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" onkeyup="return showCodeListKey('currency',[this,OpeCurrencyName],[0,1]);"><input class=codename name=OpeCurrencyName id="OpeCurrencyName" readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></TD>
    	</TR>
    </table>
    
    <Div id="AgentCode" style="display:">
   	<table class= common> 
   		<TR  class= common>
   			<TD  class= title5>代理人号码</TD>
   			<td class="input5" width="25%">
   				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=AgentCode id="AgentCode" onblur="GetManageCom();" onclick="queryAgent();"  ondblclick="queryAgent();" elementtype=nacessary ><font size=1 color='#ff0000'><b>*</b></font><!--ondblclick="queryAgent();" -->
   			</TD>
   			<TD class=title5>代理人姓名</TD>
   			<TD  class= input5><Input class="readonly wid"  tabindex=-1 name=AgentName id="AgentName" readonly=true ></TD>
      </TR>
      <TR  class= common>
   			<TD  class= title5>代理人组别</TD>
	 			<TD  class= input5><Input class="readonly wid" readonly tabindex=-1 name=AgentGroup id="AgentGroup"></TD>
   		</TR>
   	</table>
  	</Div>
   	<Div id="AgentCode1" style="display:none">
   		<table class= common> 
   		  <TR  class= common>
   				<TD  class= title5>代理人号码</TD>
   				<td class="input5" width="25%">
   					<Input class="readonly wid" name=AgentCode1 id="AgentCode1">
   				</TD>
   				<TD class=title5>代理人姓名</TD>
   				<TD  class= input5><Input class="readonly wid"  tabindex=-1 name=AgentName1 id="AgentName1" readonly=true></TD>
        </TR>
      <TR  class= common>
   				<TD  class= title5>代理人组别</TD>
	 		    <TD  class= input5><Input class="readonly wid" readonly tabindex=-1 name=AgentGroup1 id="AgentGroup1"></TD>
   			</TR>
   		</table>
  	</Div>
   <!-- 新单交费 -->  
   <Div id="TempFeeType1" style="display:none">
   	<table class= common>
   		<TR  class= common>
	      <TD  class= title5>
	        投保单号
	      </TD>          
	      <TD  class= input5>
	        <Input class="common wid" name=InputNo1 id="InputNo1" onblur="confirmSecondInput(this,'onblur');" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
	      </TD>
	      <TD  class= title5>
	        投保人
	      </TD>
	      <TD  class= input5>
	        <Input class="common wid" name=InputNob id="InputNob">
	      </TD>
      </TR>
      <TR  class= common>
	      <TD  class= title5>费用金额</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee1 id="InputNob" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
        </TD>
	     </TR>
	   </table> 
   </Div> 
   
   <!-- 续期催收交费 --> 
   <Div id="TempFeeType2" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title5>费用金额</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee2 id="OpeFee2">
        </TD>
	      <TD  class= title5 >
	        保单号码
	      </TD>        
	       <td class="input5" width="25%">
	        <Input class="common wid" name=InputNo3 id="InputNo3" onblur="getAgentCode();" >
	      </TD>  
      </TR>
      <TR  class= common>
	      <TD class=title5>
	      	<!--<Input class=cssButton type=button value="查  询" onclick="queryLJSPay();">-->
	      </TD>
	      <TD  class= input5>
	      	<Input type=hidden name=GetNoticeNo id="GetNoticeNo">
	      </TD> 
	     </TR> 
	     <!--
	     	<TR>
	     	 <td class=title>
	     	    客户姓名
	     	 </td>
	     	 <td class=input>
	     	   <Input class="common wid" name=AppntNo readonly=true>
	     	 </td>
	     	 <td class=title>
	     	    交费期数
	     	 </td>          
	     	 <td class=input>
	     	 	<Input class="common wid" name=PayCount readonly=true>
	     	 </td> 
	     		<TD class=title></TD>
	     		<TD  class= input></TD>
	     	</TR>
	   		-->
	   </table>  
   </Div>
   
   <!-- 续期预收交费 --> 
   <Div id="TempFeeType3" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title5>费用金额</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee3 id="OpeFee3">
        </TD>
	      <TD  class= title5>
	        保单号码
	      </TD>          
	      <TD  class= input5>
	        <Input class="common wid" name=InputNo5 id="InputNo5" onblur="getPolicyCom();" >
	      </TD>        
	     </TR>
	   </table>  
   </Div>
	
   <!-- 保全交费 --> 
   <Div id="TempFeeType4" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title5>费用金额</TD>
      <TD  class= input5>
	    	<Input class="common wid" name=OpeFee4 id="OpeFee4">
      </TD>
      <TD  class= title5>
        保全受理号
      </TD>          
      <TD  class= input5>
        <Input class="common wid" name=InputNo7 id="InputNo7" onblur="getEdorCode();" >
      </TD>  
    </TR>
      <TR  class= common>      
      <TD  class= title5>
        交费收据号码
      </TD>
      <TD  class= input5>
        <Input class="common wid" name=InputNo8 id="InputNo8" onblur="getEdorCode();">
        <!--<Input class=cssButton type=button value="查  询" onclick="queryLJSPayEdor();">-->
      </TD>
     </TR>
   </table>  
   </Div>
   
   <!-- 理赔回退 -->  
   <Div id="TempFeeType5" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	  	<TD  class= title5>费用金额</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee5 id="OpeFee5"> 
        </TD>
   	    <TD  class= title5>交费收据号</TD> 
   	    <TD  class= input5><Input class="common wid" name=InputNo11 id="InputNo11" onblur="getLJSPayPolicyCom();" ></TD>
      </TR>
      <TR  class= common> 
   	    <TD  class= title5>赔案号</TD> 
   	    <TD  class= input5><Input class="common wid" name=InputNo12 id="InputNo12" onblur="getLJSPayPolicyCom();" ></TD>
   	  </TR>
   	</table>  
   </Div>
   
   <!-- 不定期交费 -->
   <Div id="TempFeeType6" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title5>费用金额</TD>
      <TD  class= input5>
	    	<Input class="common wid" name=OpeFee6> 
      </TD>
      <TD class= title5>保单号码</TD>
      <TD class= input5><Input class="common wid" name=InputNo22 id="InputNo22" onblur="getUrgePolicyCom();" ></TD>
     </TR>
   </table>  
   </Div>   
   
   <!-- 首期银行代扣交费 -->  
   <Div id="TempFeeType8" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	      <TD  class= title5>
   	        客户号码
   	      </TD>          
   	      <TD  class= input5>
   	        <Input class="common wid" name=InputNo9 id="InputNo9">
   	      </TD>        
   	      <td class=title5>
   	       <!-- 备注	-->
   	      </td>
   	      <td class=input5>
   	        <!--Input class="common wid" name=ReMark9 -->	
   	      </td>
   	  </TR>
   	</table>  
   </Div>
   
   <!-- 保全受理交费 --> 
   <Div id="TempFeeType7" style="display:none">
   	 <table class= common> 
   	   <TR  class= common>
   	     <TD class= title5>保全受理号</TD>
   	     <TD class= input5><Input class="common wid" name=InputNo13 id="InputNo13"></TD>
   	     <TD class= title5>交费收据号码</TD>
   	     <TD class= input5><Input class="common wid" name=InputNo14 id="InputNo14"></TD> 
   	   </TR>
   	 </table>  
   </Div>    
   
   <!--卡单结算缴费-->
   <Div id="TempFeeType9" style="display:none">
   <table class= common> 
     <TR  class= common>
       <TD  class= title5>结算号</TD>          
       <TD  class= input5><Input class="common wid" name=InputNo99 id="InputNo99"></TD>        
       <TD  class= title5>单证类型</TD>
       <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class= codeno name="CertifyFlag9" id="CertifyFlag9" CodeData="0|^1|暂收收据^2|结算单" onclick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" ondblclick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" onkeyup="return showCodeListKeyEx('CertifyFlag', [this，ContTypeName],[0,1])"><input class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
      </TR>
      <TR  class= common>
       <TD  class= title5>单证号码</TD>
       <TD  class= input5><Input class="common wid" name=InputNo19 id="InputNo19" onblur="confirmSecondInput(this,'onblur');"></TD>
     </TR>
   </table>  
   </Div> 
   <div>
   </div>
  <br>
  <Input name="addButton" type=button class= cssButton value="添  加" onclick="confirm1();"> 
 <!--保存暂交费号码 type=hidden--> 
  <input  class= common  name="TempFeeNo" id="TempFeeNo" type=hidden readonly >
  
  <Div  id= "divTempFeeInput" style= "display: ''">
  <!--录入的暂交费表部分 -->
  
    <Table  class= common>
      	<tr>
  				  	 <td text-align: left colSpan=1>
					 <span id="spanTempToGrid" >
					 </span> 
					</td>
       </tr>
    </Table>
 	</Div>
 </div>	
</Div>
 <table class= common> 
   <TR  class= common>
     <TD class= title5>业务金额合计</TD>
     <TD class= input5><Input class="common wid" name=OperateSum id="OperateSum" readonly=true ></TD>
     <td class=title5>金额差额</td>
     <td class=input5><Input class="common wid" name=OperateSub id="OperateSub" readonly=true ></td>
   </TR>
 </table>  
 </Div>
 
 <br></br>
 <input type=hidden name='OperateType' id="OperateType">
  	 <input type =button class=cssButton value="全部提交"  name="signbutton" onclick="submitForm();">
  	<input type =button class=cssButton value="撤销全部数据" onclick="clearFormData();"> 
 	 <!-- <input type =button class=cssButton value="打印票据" onclick="printInvoice();">-->
	<br></br>
</form>
<br>
<br>
<br>
<br>
<!--=========================================================================-->

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<!--隐藏-存放查询数据 -->
<Form action="./TempFeeTypeQuery.jsp" method=post name=fmTypeQuery id="fmTypeQuery" target="fraSubmit">
  <input type=hidden name=QueryNo id="QueryNo">
  <input type=hidden name=QueryType id="QueryType">
</Form>

</body>
</html>
