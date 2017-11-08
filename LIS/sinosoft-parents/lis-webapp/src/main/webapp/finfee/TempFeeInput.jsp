<%
//程序名称：TempFeeInput.jsp
//程序功能：财务收费的输入
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
<SCRIPT src="TempFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="TempFeeInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./TempFeeQuery.jsp" method=post name=fm id="fm" target="fraSubmit">
<!--框架一：公有信息 --> 
     <script>
	   var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
     </script> 
     <div align="right" style="margin-top:5px">
     <input type =button class=cssButton value="查询暂交费" onClick="queryTempfee();">
     </div> 
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
          <td class= titleImg>公共信息
           
          </td>
          <td class=common></td>
            	  
    	</tr>
    </Table>
  <Div  id= "Frame1" class="maxbox1" style= "display:  ">          
     <Table class= common border=0>
       <TR  class= common> 
          <TD  class= title>
            业务员
          </TD>
         <td class="input" width="25%">
         <Input class=code name=AgentCode id="AgentCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="queryAgent()" onDblClick="queryAgent()" onBlur="GetManageCom();">
         </TD> 
            
          <TD class=title>
          	业务员姓名
          </TD>          
          <TD  class= input>
            <Input class="readonly wid"  tabindex=-1 name=AgentName id="AgentName" readonly=true >
          </TD>          
         <TD class= title> 
            交费日期
          </TD>
          <TD class= input >
            <Input class="readonly wid" verify="交费日期|DATE&NOTNULL" name=PayDate id="PayDate" onClick="laydate({elem:'#PayDate'});"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>                                        
       </TR>         
       <TR  class= common>
          <TD  class= title>
             收费机构
          </TD>          
          <TD  class= input>
							<Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true >				 
				  </TD>             
          <TD  class= title>
             管理机构
          </TD>          
          <TD  class= input>
							<Input class="readonly wid" name=PolicyCom id="PolicyCom" readonly=true >				 
				  </TD>         
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly tabindex=-1 name=AgentGroup id="AgentGroup" >
          </TD>
          
      </TR>           	                 	 
     </TABLE>
 </Div>
 
<!--框架一  -->     

<!--框架二：选择暂交费类型并输入 --> 
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame2);">    	 
          <td class= titleImg>
           录入暂交费
          </td>    	 
    	</tr>
    </Table>
 
    <Div  id= "Frame2" class="maxbox1" style= "display:  ">
     <table class= common> 
       <TR  class= common>
          <TD  class= title>
            暂交费类型
          </TD>          
          <TD  class= input>
            <Input class=codeno name=TempFeeType id="TempFeeType" style="background: url(../common/images/select--bg_03.png) no-repeat center right;" onMouseDown="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onDblClick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onKeyUp="return showCodeListKey('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class=codename name=TempFeeTypeName readonly=true>
          </TD>        
          <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
      </TR> 
     </table>
  </div>  <!-- maxbox  -->  
    <!-- 新单交费 -->  
    <Div id="TempFeeType1" style="display:none">
    <table class= common> 
      <TR  class= common>        
          <TD  class= title>
            单证类型
          </TD>
          <TD  class= input>
            <Input class= codeno name=CertifyFlag id="CertifyFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('certifyflag',[this,CertifyFlagName],[0,1]);" onDblClick="return showCodeList('certifyflag',[this,CertifyFlagName],[0,1]);" onKeyUp="return showCodeListKey('certifyflag',[this,CertifyFlagName],[0,1]);"><input class=codename name=CertifyFlagName readonly=true>
          </TD> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>                            
      </TR>                   
    </table>  
          <Div id="divCertifyFlag1" style="display:none">
      	<table class= common>
		      <TR  class= common> 
				      <TD  class= title>
		            投保单印刷号
		          </TD>          
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNo1 id="InputNo1" >
		          </TD>
		      	  <TD  class= title>
		            暂收费收据号
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNo2 id="InputNo2" >
		          </TD> 
                  <TD class= title></TD>
                  <TD class= input></TD>
			  	</TR>
	      </table>
	    </Div>
		  <Div id="divCertifyFlag2" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
			  		  <TD  class= title>
		            投保单印刷号
		          </TD>          
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoB1 id="InputNoB1" >
		          </TD>
		      	  <TD  class= title>
		            银行划款协议书号
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoB id="InputNoB" >
		          </TD> 
                   <TD class= title></TD>
                   <TD class= input></TD>
			  	</TR>
			  </table>
		  </Div>
		  <Div id="divCertifyFlag3" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
		          <TD  class= title>
		            单证编码 
		          </TD>          
		          <TD  class= input>
		            <Input class=codeno name=CardCode id="CardCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="showCodeList('CardCode',[this,CardCodeName],[0,1]);" onDblClick="showCodeList('CardCode',[this,CardCodeName],[0,1]);" onKeyUp="return showCodeListKey('CardCode',[this,CardCodeName],[0,1]);" ><Input class="codename" name=CardCodeName readonly=true>
		          </TD>        
		          <TD  class= title>
		            单证号码
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoC id="InputNoC" >
		          </TD> 
                  <TD class= title></TD>
                  <TD class= input></TD>
		  	</TR>
		  </table>
		  </Div>
    </Div>

    <!-- 续期催收交费 --> 
    <Div id="TempFeeType2" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title >
            保单合同号码
          </TD>        
           <td class="input">
          <Input class="common wid" name=InputNo3 id=""InputNo3 onBlur="getAgentCode();">
          </td>
           <td class=title>
             交费通知书号码
          </td>
          <td class=input>
            <Input class="common wid" name=InputNo4 >
            <Input class=cssButton type=button value="查  询" onClick="queryLJSPay();">
          </TD>  
          <TD class= title></TD>
          <TD class= input></TD>
      </TR>          
      
      <TR>
          <td class=title>
             续期暂收费收据号
          </td>          
          <td class=input>
          	<Input class="common wid"  name=XQCardNo id="XQCardNo" >
          </td> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>
      
      </TR>
    
    </table>  
    </Div>
    
        <!-- 预收保费 --> 
    <Div id="TempFeeType3" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            保单合同号码
          </TD>          
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo13 id="InputNo13" >
          </TD>        
          <TD  class= title>
            预交费收据号
          </TD>
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo14 id="InputNo14" >
          </TD> 
          <td class="title"></td>
          <td class="input"></td>         
      </TR>
    </table>  
    </Div>       
    <!-- 保全交费 --> 
    <Div id="TempFeeType4" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            保全受理号
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo7 id="InputNo7" >
          </TD>        
          <TD  class= title>
            交费收据号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=InputNo8 id="InputNo8" >
          </TD>  
          <td class="title"></td>
          <td class="input">
          <Input class=cssButton type=button value="查  询" onClick="queryLJSPayEdor();"></td>         
      </TR>
    </table>  
    </Div>
    </Div>
    <Div id="TempFeeType5" style="display:none">
     </Div>
    
    <!-- 理赔收费，分为理赔加费和理赔回滚 -->  
    <Div id="TempFeeType6" style="display:none">
    <table class= common> 
      <TR  class= common>        
          <TD  class= title>
            单证类型
          </TD>
          <TD  class= input>
            <Input class= codeno name=ClaimFeeType id="ClaimFeeType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|理赔加费|^2|理赔回滚"  onMouseDown="return showCodeListEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);" onDblClick="return showCodeListEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);" onKeyUp="return showCodeListKeyEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);"><input class=codename name=ClaimFeeTypeName readonly=true>
          </TD> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>                            
      </TR>                   
    </table>  
      <Div id="divClaimFeeType1" style="display:none">
      	<table class= common>
		      <TR  class= common> 
	          <TD  class= title>
	            交费收据号
	          </TD>          
	          <TD  class= input>
	            <Input class="common wid" name=InputNo11 id="InputNo11" onBlur="getLJSPayPolicyCom();" >
	          </TD>        
	          <TD  class= title>
	            赔案号 
	          </TD>
	          <TD  class= input>
	            <Input class="common wid" name=InputNo12 id="InputNo12" onBlur="getLJSPayPolicyCom();">
                <Input class=cssButton type=button value="查  询" onClick="queryLJSPayClaim();">
	          </TD>
              <TD class= title></TD>
          <TD class= input></TD>  
			  	</TR>
	      </table>
	    </Div>
		  <Div id="divClaimFeeType2" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
	          <TD  class= title>
	            交费收据号
	          </TD>          
	          <TD  class= input>
	            <Input class="common wid" name=InputNoH11 id="InputNoH11" onBlur="getLJSPayPolicyCom();" >
	          </TD>        
	          <TD  class= title>
	            赔案号 
	          </TD>
	          <TD  class= input>
	            <Input class="common wid" name=InputNoH12 onBlur="getLJSPayPolicyCom();">
                <Input class=cssButton type=button value="查  询" onClick="queryLJSPayClaim();">
	          </TD> 
              <TD class= title></TD>
          <TD class= input></TD> 
			  	</TR>
			  </table>
		  </Div>
    </Div>
    <Div id="TempFeeType7" style="display:none">
    </Div>
        <!-- 续期非催收交费 --> 
    <Div id="TempFeeType8" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            保单合同号码
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo5 id="InputNo5" onBlur="getPolicyCom();">
          </TD>        
          <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>         
      </TR>
    </table>  
    </Div>   
    <!--卡单结算缴费-->
    <Div id="TempFeeType9" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            结算号
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo99 id="InputNo99" >
          </TD>        
          <TD  class= title>
            单证类型
          </TD>
          <TD  class= input>
            <Input class= codeno name=CertifyFlag9 id="CertifyFlag9" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|暂收收据^2|结算单" onMouseDown="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" onDblClick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('CertifyFlag', [this，ContTypeName],[0,1])"><input class=codename name=ContTypeName readonly=true>
          </TD>          
          <TD  class= title>
            单证号码
          </TD>
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo19 id="InputNo19" >
          </TD>        
      </TR>
    </table>  
    </Div> 
       <Input type=button class= cssButton value="确  认" onClick="confirm1();" name=butConfirm>
      <!--保存暂交费号码 type=hidden--> 
       <input  class= "common wid"  name="TempFeeNo" type=hidden readonly >
    <br><br>           
  <Div  id= "divTempFeeInput" style= "display:  ">
  <!--录入的暂交费表部分 -->
    <Table  class= common>
      	<tr>
    	 		<td style="text-align: left" colSpan=1>
	 					<span id="spanTempGrid" >
	 					</span> 
					</td>
       </tr>
    </Table>
  <!--录入的暂交费分类表部分 -->
    <Table  class= common>
      	<tr>
  				  	 <td style="text-align: left" colSpan=1>
					 <span id="spanTempClassGrid" >
					 </span> 
					</td>
       </tr>
    </table>
  <div id= "divTempFeeClassInput" style="display:none">
	<table  class= common>
      <tr class= common>
       		<td class= title>
       			交费方式
       			</td>
       		<td class= input>
					<Input class=codeno name=PayMode id="PayMode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);" onDblClick="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);" onKeyUp="return showCodeListKey('chargepaymode',[this,PayModeName],[0,1]);"><input class=codename name=PayModeName readonly=true>			    			
       		</td>
       		<td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
       </tr>
    </Table> 
   </div>  
  <div id= "divTempFeeClassInput3" style="display:'none;'">
		<table  class= common align=left>
       <tr class= common>
       		<td>
                <input type =button class=cssButton value="增  加" onClick="addMul();">  
       			<input type =button class=cssButton value="修  改" onClick="ModMul();"></td>
       		<td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
       </tr>
    </Table> 
  </div>

  <br></br>
  
    <div id=divPayMode1 style="display:none" >
    	<Table  class= common>
    		<tr class= common>
	    		<td class = title>
	    		现金交费方式
	    	</td>
	    		<td class="input">	
	    			<Input class= codeno name=CashType id="CashType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  CodeData="0|^1|客户自交现金|^2|人工收取现金" onMouseDown="return showCodeListEx('CashType',[this,CashTypeName],[0,1]);" onDblClick="return showCodeListEx('CashType',[this,CashTypeName],[0,1]);" onKeyUp="return showCodeListKeyEx('CashType',[this,CashTypeName],[0,1]);"><input class=codename name=CashTypeName readonly=true>
	    		</td>
                <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
    		</tr>
    		<tr class= common>
    			<td class=title>币种</td>
    			<td class=input><Input class=codeno name=Currency1 id="Currency1" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('currency',[this,CurrencyName1],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName1],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName1],[0,1]);"><input class=codename name=CurrencyName1 readonly=true></td>
    			<td class=title>
    					交费金额
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee1 id="PayFee1">
    			</td> 
                <td class=title></td>
          <td class=input></td>   			
    		</tr>
    	</table>
    </div>
    <div id=divPayMode2 style="display:none" >
    	<Table  class= common>    			
    			<tr class= common>
    					<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency2 id="Currency2" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName2],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName2],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName2],[0,1]);"><input class=codename name=CurrencyName2 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							交费金额
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee2 id="PayFee2">
    					</td>
    					<td class=title>
    							票据号码
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo2 id="ChequeNo2" >
    					</td>
    					<td class=title>
    							支票日期 
    					</td>
    					<td class=input>
    						<Input class="multiDatePicker" name=ChequeDate2 id="ChequeDate2" onClick="laydate({elem:'#ChequeDate2'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							开户银行
    					</td>
    					<td class=input>
    						<Input class=codeno name=BankCode2 id="BankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="开户银行|code:FINAbank" onMouseDown="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');"><input class=codename name=BankCode2Name readonly=true>
    					</td>
    					<td class=title>
    							银行账号
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo2 id="BankAccNo2" onBlur="checkBank(BankCode2,BankAccNo2);">
    					</td>
    					<td class=title>
    							户名
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName2 id="AccName2">
    					</td>
    			</tr>    		   			  			
    	</table>
    </div>
    <div id=divPayMode3 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency3 id="Currency3" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName3],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName3],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName3],[0,1]);"><input class=codename name=CurrencyName3 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							交费金额
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee3 id="PayFee3">
    					</td>
    					<td class=title>
    							票据号码
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo3 id="ChequeNo3" >
    					</td>
    					<td class=title>
    							支票日期
    					</td>
    					<td class=input>
    						<Input class="multiDatePicker" name=ChequeDate3 id="ChequeDate3" onClick="laydate({elem:'#ChequeDate3'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							开户银行
    					</td>
    					<td class=input>
								<Input name=BankCode3 id="BankCode3" class='codeno' style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');" onBlur="checkBank(BankCode3,BankAccNo3);"><input name=BankCode3Name class='codename' readonly=true elementtype=nacessary>
    					</td>
    				 <td class=title>
    							银行账号
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo3 id="BankAccNo3" onBlur="checkBank(BankCode3,BankAccNo3);">
    					</td>
    					<td class=title>
    							户  名
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName3 id="AccName3">
    					</td>
    			</tr>    		

    			  			
    	</table>
    </div>

    <div id=divPayMode4 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency4 id="Currency4" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName4],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName4],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName4],[0,1]);"><input class=codename name=CurrencyName4 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							交费金额
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee4 id="PayFee4">
    					</td>
					    <td class=title>
    							开户银行
    					</td>
    					<td class=input>
								<Input class=codeno name=BankCode4 id="BankCode4" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" ><input class=codename name=BankCode4Name readonly=true>
    					</td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							银行账号
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=BankAccNo4 id="BankAccNo4" onBlur="checkBank(BankCode4,BankAccNo4);">
    					</td>
    					<td class=title>
    							户  名
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName4 id="AccName4" >
    					</td>
    			</tr>
    			<tr class= common>
			      <td class=title>
			       	证件类型
			     	</td>
			     	<td class=input>
							<Input class=codeno name=IDType id="IDType" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onDblClick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onKeyUp="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true>							        			
			     	</td>
			  		<td class=title>
			     		证件号码
			   		</td>
			  		<td class=input>
							<Input class="common wid" name=IDNo id="IDNo" >							        			
			   		</td>
                    <td class=title></td>
    			<td class=input></td>
        	</tr> 			
    	</table>
    <div id=divPayMode5 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>
    						 实付通知书号
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo5 id="ChequeNo5" >   						 
    					</td>
    					<td class=title>
    					   保全受理号/赔案号/投保单印刷号	
    					</td>
    					<td class=input>
    					  <Input class="coolConfirmBox wid" name=OtherNo5 id="OtherNo5" > 
    					</td>
    					<td class=title>
    					<a href="javascript:void(0)" style="width:60" class=button onClick="queryLJAGet();">查    询</a>
                        <!--<input class=cssButton type="button" value="查  询" style="width:60" onClick="queryLJAGet()"></td>-->
    			</tr>
    			<tr class= common>
    					<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency5 id="Currency5" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName5],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName5],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName5],[0,1]);"><input class=codename name=CurrencyName5 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							给付金额
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee5 id="PayFee5" readonly=true>
    					</td>
    					<td class=title>
    							领取人
    					</td>
    					<td class=input>
    						<Input class="common wid" name=Drawer5 id="Drawer5" readonly=true>
    					</td>
    			</tr>    		
          <Div  id= "divFinFee1" style= "display:  " align=center>
             <Table  class= common>
               <TR  class= common>
                <TD text-align: left colSpan=1>
                  <span id="spanQueryLJAGetGrid" ></span> 
  	            </TD>
              </TR>
             </Table>					
            <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
            <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
            <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
            <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
          </Div>	    			  			
    	</table>
    <div id=divPayMode6 style="display:none" >
     	<Table  class= common>
    		<tr class= common>
    			<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency6 id="Currency6" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName6],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName6],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName6],[0,1]);"><input class=codename name=CurrencyName6 readonly=true></td>
    			<td class=title>
    					交费金额
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee6 id="PayFee6">
    			</td>
    			<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    		</tr>
    			<tr class= common>
    					<td class=title>
    							开户银行
    					</td>
    					<td class=input>
    						<Input name=BankCode6 id="BankCode6" class='codeno' style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');"  ondblclick="return showCodeList('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');" onBlur="checkBank(BankCode6,BankAccNo6);"><input name=BankCode6Name class='codename' readonly=true elementtype=nacessary>
    					</td>
    					<td class=title>
    							银行账号
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo6 id="BankAccNo6" onBlur="checkBank(BankCode6,BankAccNo6);">
    					</td>
    					<td class=title>
    							户  名
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName6 id="AccName6" >
    					</td>
    			</tr> 
    	</table>
    </div>
    <div id=divPayMode0 style="display:none" >
    	<Table  class= common>
    		<tr class= common>
    			<td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency0 id="Currency0" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName0],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName0],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName0],[0,1]);"><input class=codename name=CurrencyName0 readonly=true></td>
    			<td class=title>
    					交费金额
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee0 id="PayFee0">
    			</td>
    			<td class=title></td>
    			<td class=input></td>
    		</tr>
    	</table>
    </div>
   </div>
   </div>
    <table class=common>
    <TR class=common>    
     <TD class=common align=right>
     <!-- <a href="javascript:void(0)" name=butAdd  class=button onClick="addRecord();">添加一笔</a> -->
      <input id="butAdd" type =button class=cssButton value="添加一笔" onClick="addRecord();" name=butAdd>      
     </TD>
    </TR> 
    </table>
    <br>
    <hr class="line">
    <Table class=common>
      <TR>
        <TD  class= title>
          暂交费共缴纳
        <input style="width:40px" class=common  name=TempFeeCount id="TempFeeCount" readonly tabindex=-1 >
          笔
       </TD>
       <TD  class= title>
          暂交费总计
        <!-- <input width='10px' class=common  name=SumTempFee readonly tabindex=-1 > -->
       </TD>
       </TR>    
   </Table>
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanSumTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table> 
  </Div>   
</Form>
<!--框架二 -->

<!--框架三：保存提交的数据 -->
<Form action="./TempFeeSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFeeSave);">    	 
          <td class= titleImg>
           存储数据
          </td>    	 
    	</tr>
    </Table>          
  <Div  id= "divTempFeeSave" style= "display: ">
  <!--暂交费表部分 -->  
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    
    <Table>
    <tr>
    	<input class = common name=CertifyFlagHidden  type=hidden>  
    	<input class = common name=ClaimFeeTypeHidden  type=hidden>
    </tr>
    </Table>
    
  <!--暂交费分类表部分 -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempClassToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <br><br>
    <table align=left>
    <TR class=input>     
     <TD class=common>
     <a href="javascript:void(0)"  class=button onClick="submitForm();">全部提交</a>
     <a href="javascript:void(0)"  class=button onClick="clearFormData();">撤销全部数据</a>
      <!--<input type =button class=cssButton value="全部提交" onClick="submitForm();">
      <input type =button class=cssButton value="撤销全部数据" onClick="clearFormData();">-->
      <!--input type =button class=cssButton value="打印票据" onclick="printInvoice();"-->
     </TD>
    </TR>
    </table>
  </Div>
  <input type=hidden name=NewQueryType>
  <input type=hidden name=CERTIFY_XQTempFee>
  
</Form> 
<!--框架三 -->   
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<!--隐藏-存放查询数据 -->
<Form action="./TempFeeTypeQuery.jsp" method=post name=fmTypeQuery id="fmTypeQuery" target="fraSubmit">
  <input type=hidden name=QueryNo>
  <input type=hidden name=QueryType>
 
</Form>
<br><br><br><br><br>
</body>
</html>
<script>
var bCom=manageCom.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}

var mSql="1 and comcode like #"+bCom+"%#" ;

</script>
