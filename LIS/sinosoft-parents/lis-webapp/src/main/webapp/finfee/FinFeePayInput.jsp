<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
   loggerDebug("FinFeePayInput","管理机构-----"+tG.ComCode);
%>   

<script>
  var comCode = "<%=tG.ComCode%>";
</script>
<html> 
<%
//程序名称：FinFeePayInput.jsp
//程序功能：财务付费表的输入
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>


<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>    
  <SCRIPT src="FinFeePayInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FinFeePayInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./FinFeePayQuery.jsp" method=post name=fm id="fm" target="fraSubmit">

   <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 请输入查询条件：
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox1" id="maxbox">
  <Table class= common>                             
    <TR>  
      <TD  class= title5>
         实付号码
      </TD>  
      <TD  class= input5>
        <Input class="common wid" name=ActuGetNo id="ActuGetNo" >
      </TD>
      <TD  class= title5></TD>
      <TD  class= input5>    
      </TD>
      <!--TD  class= title5>
        保全受理号/赔案号/投保单印刷号
      </TD>           
      <TD  class= input5>
        <Input class=common name=OtherNo>
      </TD-->              
    </TR> 
         
        <!--TR>
        	<TD class=title nowrap=false >
          	<!--<input type="hidden" name="PrtNo" value="PrtNo">-->
          	<!--input type="checkbox" name="chkPrtNo" onclick="inputCertify(this)">
          	异地付费
          </TD>
        </TR-->
          
          <!--<td><input type="button" name="btnPrtNo" class="cssButton" value="查  询" onclick="queryPrtNo()" style="display:none"></td>--> 
        
         <!--TR>
          <TD  class= title5>
            付费方式
          </TD>
          <TD  class= input5>
            <Input class="code" name=PayMode1  ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>            
          <TD  class= title5>
            代理人编码
          </TD>
          <TD  class= input5>
            <Input class="common" name=AgentCode >
          </TD>          
         </TR>
           <td class= title5>
            管理机构
           </td>  
				   <TD class=input>
				 	    <Input class=codeno name=ManageCom ondblclick="return showCodeList('managecom1',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('managecom1',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
				   </TD>
           <td class= title5>
            入机日期
           </td>  
          <TD  class= input5>
            <Input class="coolDatePicker" name=MakeDate dateFormat="short" name=StartDate>
            <!--Input class="common" name=MakeDate >
          </TD>
         <TR>
        <TR>
        </TR>
          <TD  class= title5>
            付费类型
          </TD>          
          <TD  class= input5>
            <Input class="codeno" name=OtherNoType CodeData="0|^1|生存领取^2|保全收费^3|暂交费退费^4|理赔退费^5|其他业务^6|分洪费用" ondblclick="return showCodeListEx('Type',[this,OtherNoTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('Type',[this,OtherNoTypeName],[0,1]);" onchange="showBankAccNo();"><input class=codename name=OtherNoTypeName readonly=true></TD>           
          </TD>        
        </TR--> 
                                  
  </Table>
  <Input class=cssButton type=button value="查  询" name=aquery id="aquery" onClick="queryLJAGet();">
  </div>
     <!--Div id="PolicyComInPut" style="display:none">
     		<Table class= common >
	        <TR>
	        	<TD  class= title5>
	          	管理机构
	          </TD>  
	          <TD  class= input5-->
	            <!--<Input class=common name=ManageCom-->
	            <!--Input class=common name=ManageCom readonly=true>
	          </TD> 
	          <TD  class= title5>
	          	付费机构
	          </TD>  
	          <TD  class= input5>
	            <Input class=common name=PolicyCom readonly=true>
	          </TD>
	          
	        </TR>
	   		</Table>
     </Div-->
  
          <!--TD  class= input5>
            <Input class=cssButton type=button value="查询" onclick="easyQueryClick();">
          </TD-->
  <Table>
    <TR>
     	<TD class=common>
	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
    	</TD>
   		<TD class= titleImg>
    		 实付总表信息
  		</TD>
   	</TR>
  </Table>    	  
  <Div  id= "divFinFee1" style= "display: ''" align=center>
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1>
         <span id="spanQueryLJAGetGrid" ></span> 
  	    </TD>
      </TR>
   </Table>					
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
 </Div>	
    <TD  class= input5>
      <input type=hidden name=ManageCom > 
    </TD>
</Form> 

<Form action="./FinFeePaySave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
     
     <Table class= common>                 
       <TR  class= title5>
       <TD class=common>
	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    	</TD>
          <TD class= titleImg>
            以下填写付费详细信息
          </TD>      
      </TR> 
      </Table>
      <div class="maxbox" id="maxbox">
      <table class="common"> 
      <TR  class= common> 
          <TD  class= title5>
            其他号码
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PolNo id="PolNo" verify="批单号码|notnull" >
          </TD>                       
          <TD  class= title5>
            付费方式
          </TD>          
          <TD  class= input5>
          	<!--zy 2009-04-09 20:55 进行付费方式的控制-->
            <!--Input class="codeno" name=PayMode verify="付费方式|notnull" CodeData="0|^1|现金^2|现金支票^3|转账支票^6|POS付款^7|网上银行" ondblclick="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKeyEx('Mode',[this,PayModeName],[0,1]);" onchange="showBankAccNo();"><input class=codename name=PayModeName readonly=true></TD-->
            <Input class="codeno" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " name=PayMode id="PayMode" verify="付费方式|notnull" CodeData="0|^1|现金^2|现金支票^3|转账支票^9|网银代付" onMouseDown="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onDblClick="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onKeyUp="return showCodeListKeyEx('Mode',[this,PayModeName],[0,1]);" onChange="showBankAccNo();" readonly ><input class=codename name=PayModeName readonly=true>                      
          </TD>           
      </TR>
<tr class= common>
    					<td class=title5>币种</td>
    					<td class=input5><Input class=codeno name=Currency id="Currency" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('currency',[this,CurrencyName],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName],[0,1]);"><input class=codename name=CurrencyName readonly=true></td>
    					<td></td>
    			<td></td>
    			</tr>
      <TR  class= common>                    
          <TD  class= title5>
            给付金额
          </TD>           
          <TD  class= input5>
            <Input class="common wid" name=GetMoney id="GetMoney" verify="给付金额|notnull&num" readonly >
          </TD>          
          <TD  class= title5>
            财务到帐日期
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker"   name=EnterAccDate verify="财务到账日期|notnull&date" id="EnterAccDate" onClick="laydate({elem:'#EnterAccDate'});"><span class="icon"><a onClick="laydate({elem: '#EnterAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
       </TR>               
       <TR  class= common>
           <TD  class= title5>
            领取人
           </TD>  
          <TD  class= input5>
            <Input class="common wid" verify="领取人|notnull" name=SDrawer id="SDrawer" >
          </TD>                                   
          <TD  class= title5>
            领取人身份证号
          </TD>
          <TD  class= input5>
            <Input class="common wid" verify="领取人身份证号|notnull" name=SDrawerID id="SDrawerID" >
          </TD>
        </TR>                           
      <TR  class= common>
          <!--TD  class= title5>
            实际领取人
          </TD>  
          <TD  class= input5>
            <Input class= common  name=Drawer >
          </TD--> 
          <TD  class= title5>
            管理机构
          </TD>  
          <TD  class= input5>
            <Input class="common wid" name=FManageCom id="FManageCom"  value="<%=tG.ComCode%>" readonly >
          </TD>  
        </TR>
        <!--TR  class= common>
           <td class=title>
			       	实际领取人证件类型
			     	</td>
			     	<td class=input>
							<Input class=codeno name=DrawerIDType ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true>							        			
			     	</td>                            
          <TD  class= title5>
            实际领取人证件证号
          </TD>
          <TD  class= input5>
            <Input class= common  name=DrawerID >
          </TD>                         
        </TR-->      
                                                     	                  	 
     </TABLE> 
   <Div id='divBankAccNo' style="display:none">
      <table class=common>       
    		<tr class= common>
    					<td class=title5>
    						付款银行
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode style="background: url(../common/images/select--bg_03.png) no-repeat center right; " id="BankCode" onClick="return showCodeList('FINAbank',[this,BankName],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankName],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName],[0,1],null,mSql,'1');"><input class=codename name=BankName readonly=true> 
    					</td>
             <TD class= title5>
                银行户名
             </TD>
             <TD class= input5>
    				    <Input class="common wid" name=BankAccName id="BankAccName">
            
             </TD>
    		</tr>             
       <TR class=common>                                  
         <TD  class= title5>
            银行账号
         </TD>
          <TD  class= input5>
     				    <!--Input class=code name=BankAccNo ondblclick="return showCodeList('AccNo2',[this],null,null,[fmSave.BankCode.value],['bankcode']);" onkeyup="return showCodeListKey('AccNo2',[this],null,null,[fm.BankCode.value],['bankcode']);" readonly=true-->
     				    <Input class="common wid" name=BankAccNo id="BankAccNo" onBlur="confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font>
          </TD>          
          <TD class=title5>
          </TD>
          <TD class=input5>
          </TD>     
        </TR> 
        </table> 
      </Div>   
      <Div id='divChequeNo' style="display:none">
       <table class=common>
       
    		<tr class= common>
    					<td class=title5>
    						付款银行
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode2 id="BankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "
 onMouseDown="return showCodeList('FINAbank',[this,BankName2],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankName2],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName2],[0,1],null,mSql,'1');"><input class=codename name=BankName2 readonly=true> 
    					</td>
             <TD class= title5>
                票据号码
             </TD>
             <TD class= input5>
    				    <Input class="coolConfirmBox wid" name=ChequeNo id="ChequeNo">
            
             </TD>
    		</tr>        
        </table> 
      </Div>     
      <Div id='divNetBankAccNo' style="display:none">
       <table class=common>
       
    		<tr class= common>
    					<td class=title5>
    						付款银行
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode9 id="BankCode9" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"
 onDblClick="return showCodeList('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"><input class=codename name=BankName9 readonly=true> 
    					</td>
            </TR>
            <TR class=common>
            <TD class= title5>
                银行账号
             </TD>
             <TD class= input5>
    				    <Input class="coolConfirmBox wid" name=BankAccNo9 id="BankAccNo9" onBlur="checkBank(BankCode9,BankAccNo9);">           
             </TD>
             <TD class= title5>
                银行户名
             </TD>
             <TD class= input5>
    				    <Input class="common wid" name=BankAccName9 id="BankAccName9">           
             </TD>
    		</tr>        
        </table> 
      </Div>
      </div>  
     <!--Div id='divInSureBankAccNo' style="display:none">
       <table class=common>
       <TR class=common>                                  
         <TD  class= title5>
            客户银行
         </TD>
          <TD  class= input5>
    				<Input class=codeno name=InSureBankCode ondblclick="return showCodeList('HeadOffice',[this,InSureBankName],[0,1],null,'3','Branchtype',1);" onkeyup="return showCodeList('HeadOffice',[this,InSureBankName],[0,1],null,'3','Branchtype','1');"><input class=codename name=InSureBankName readonly=true> 
          	
          </TD>          
          <TD class=title>
            客户账号
          </TD>
          <TD class= input5>
    				    <Input class=common name=InSureAccNo onblur="confirmSecondInput(this,'onblur');">   
          </TD>       
        </TR> 
        <TR>
          <TD class=title>
            客户账户名
          </TD>
          <TD class= input5>
    				    <Input class=common name=InSureAccName>   
          </TD>            
        </TR>
        </table> 
      </Div> 

     <Div id='divPayByBank' style="display:none">
       <table class=common>
       <TR class=common>                                  
         <TD  class= title5>
            代付银行
         </TD>
          <TD  class= input5>
    				<Input class=codeno name=BankCode4 ondblclick="return showCodeList('bank',[this,BankName4],[0,1]);" onkeyup="return showCodeList('bank',[this,BankName4],[0,1]);"><input class=codename name=BankName4 readonly=true> 
          </TD>          
          <TD class=title>
            客户账号
          </TD>
          <TD class= input5>
    				 <Input class=common name=AccNo4 onblur="confirmSecondInput(this,'onblur');">   
          </TD>       
        </TR> 
        <TR>
          <TD class=title>
            客户账户名
          </TD>
          <TD class= input5>
    				    <Input class=common name=AccName4>   
          </TD>            
        </TR>
        </table> 
      </Div>
      
     <Div style="display:none">
       <TR  class= common>
          <TD  class= title5>
            代理人编码
          </TD>
          <TD  class= input5>
             <Input class="code" name=AgentCode >
          </TD>           
          <TD  class= title5>
            代理人组别
          </TD>
          <TD  class= input5>
            <Input class="readonly" readonly tabindex=-1 name=AgentGroup  >
          </TD>
      </TR>      
        <TR  class= common>          
          <TD  class= title5>
            操作员
          </TD> 
          <TD  class= input5>
            <Input class="readonly"  readonly tabindex=-1 name=Operator  verify="操作员|notnull">
          </TD>                             
          <TD  class= title5>
            操作时间
          </TD>
          <TD  class= input5>
            <Input class="readonly" readonly tabindex=-1 name=ModifyDate   verify="操作时间|notnull">
          </TD>
       </TR> 
     </Div-->
          <TD  class= input5>
            <input type=hidden name=ActuGetNo > 
          </TD>  
          <TD  class= input5>
           <Input class=cssButton type=button value="付费确定" name=btnSave id="btnSave" onClick="submitForm();">
          </TD>  
       <br><br><br><br>       
</Form>    

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

<Form action="./FinFeePayPrint.jsp" method=post name=fmprint id="fmprint" target="fraSubmit">
     <!--Table class= common>                 
       <TR  class= common>
          <TD  class= input5>
            <Input class=cssButton type=button value="打印凭证" onclick="Print();">
          </TD>
          <TD  class= input5>
            <Input class= common name=ActuGetNo1 type=hidden>
          </TD>
       
       </TR>
     </Table-->   
     
</Form>

</html>
<script>
var bCom=comCode.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}
var mSql="1 and comcode like #"+bCom+"%#" ;	
</script>
 
