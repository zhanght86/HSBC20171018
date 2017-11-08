<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("CheckPriceInfoInput","werererererrrerwer");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="CheckPriceInfoInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CheckPriceInfoInputInit.jsp"%>

</head>
<body  onload="initForm();initElementtype();" >
  <form action="CheckPriceInfoIputSave.jsp" method=post name=fm id="fm" target="fraSubmit">	
	
	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivQuery);">
    		</TD>
    		<TD class= titleImg>
    			 请输入查询条件：
    		</TD>
    	</TR>
  </Table> 
   <Div  id= "DivQuery" style= "display: ''" align=center>
   <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          
          <TD  class= title5>
          投资帐户号码
          </TD>
          <TD  class= input5>
            <!--<Input class= common name=QureyInsuAccNo >-->
         <input class=codeno name=QureyInsuAccNo  id=QureyInsuAccNo
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
         onclick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');" 
         onDblClick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');" 
         onKeyUp="return showCodeListKey('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'500');"><input class=codename name=QueryName readonly=true >
          </TD>
        
          <TD  class= title5>
          价格起期
          </TD>
          <TD  class= input5>
            <!--<Input class=common name=QueryStartDate "> -->
              <Input class="coolDatePicker" dateFormat="short"   name=QueryStartDate id="QueryStartDate"
               onClick="laydate({elem:'#QueryStartDate'});"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>         
          </TD>
          </TR>
         <TR  class= common>
       	<td class="title5">记录状态</td>
         <td class="input5"><Input class=codeno name=QueryState readonly CodeData='0|^1|录入^0|生效' value=6 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
         onclick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);" 
         onDblClick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);"
          onKeyUp="return showCodeListKeyEx('QueryState',[this,QueryStateName],[0,1]);"><input class=codename name=QueryStateName readonly=true ></font></td>
        </TR> 
 </Table> 
</div>
</div>


<!------------修改成与-种无关--鲁哲--luzhe--20070913------->
<Div  id= "DivQuery" style= "display: 'none'" align=center>
 <table  class= common align=center>
      	<TR  class= common>  	
   	<TD  class= title5>
          险种编码
          </TD>
          <TD  class= input5>
           <!--<Input class= common name=QureyRiskCode >-->
           <Input class=codeno name=QureyRiskCode
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return  showCodeList('riskind',[this,QueryRiskCodeName],[0,1],null,[3],['risktype3'],1,500);"  
            ondblclick="return  showCodeList('riskind',[this,QueryRiskCodeName],[0,1],null,[3],['risktype3'],1,500); " 
            onKeyUp="return showCodeListKey('riskind',[this,QueryRiskCodeName],[0,1],null,[3],['risktype'],1,500);"  ><input class=codename name=QueryRiskCodeName readonly=true elementtype=nacessary>
          </TD>
          <td class="title5">险种编码</td>
          <td class="input5"><Input class=codeno name=RiskCode
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
           onclick="return showCodeList('riskind',[this,RiskCodeName],[0,1],null,[3],['risktype3'],1,500);" 
            ondblclick="return showCodeList('riskind',[this,RiskCodeName],[0,1],null,[3],['risktype3'],1,500);" 
            onKeyUp="return showCodeListKey('riskind',[this,RiskCodeName],[0,1],null,[3],['risktype3'],1,500);"  ><input class=codename name=RiskCodeName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>
          
        </tr>
      </table>
   	
   	
</Div>
<!------------修改成与-种无关--鲁哲--luzhe--20070913------->  
  <!--<Table>
  	<TR>           
       	<TD> <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> </TD>   
       	<td><input type="button" class=cssButton value="&nbsp;重置&nbsp;" onClick="resetForm()"></td>    
       </TR>
    </Table>-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
    <a href="javascript:void(0);" class="button"onClick="resetForm()">重  置</a>
  	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityGrid);">
    		</TD>
    		<TD class= titleImg>
    			 价格信息
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divCollectivityGrid" style= "display: ''" align=center>
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
     <!-- <INPUT VALUE="首  页" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="上一页" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 		-->
 </Div>	
    <table>
      <tr>
     <TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);">
    		</TD>
        <td class= titleImg>价格信息</td></tr>
    </table>

    <div  id= "divLDPerson" style= "display: ''">
      <div class="maxbox" >
      <table  class=common>
          <tr class=common>
          <td class="title">投资帐户号码</td>
         <td class="input"><input class=codeno name=InsuAccNo 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
         onclick="return showCodeList('insuacc',[this,InsuAccNoName],[0,1],null,null,null,1,'500');" onDblClick="return showCodeList('insuacc',[this,InsuAccNoName],[0,1],null,null,null,1,'500');" onKeyUp="return showCodeListKey('insuacc',[this,InsuAccNoName],[0,1],null,null,null,1,'500');"><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>
      <td class="title">记录状态</td>          
         <td class="input"><Input class="common wid"  name=StateName ></td>
          
        </tr>
				<tr class=common >
          <td class="title">账户总资产(单位：元)</td>
          <td class="input"><Input  class="common wid"  name=InsuTotalMoney ></font></td>
      
          <td class="title">负债(单位：元)</td>
          <td class="input"><Input class="common wid"    name=Liabilities ></td>
          </tr>
				<tr class=common >
          <td class="title">单位买入价格(单位：元)</td>
          <td class="input"><Input  class="common wid"    name=UnitPriceBuy ></font></td>
      
          <td class="title">单位卖出价格(单位：元)</td>
          <td class="input"><Input class="common wid"    name=UnitPriceSell ></td>
          </tr>
        <tr class=common >
          <td class="title">公司投资单位数</td>
          <td class="input"><Input  class="common wid"   name=CompanyUnitCount ></font></td>
      
          <td class="title">公司本次变动单位数</td>
          <td class="input"><Input class="common wid"   name=ComChgUnitCount ></td>
       </tr>

        <tr class=common >
 					<td class="title">客户投资单位数</td>
          <td class="input"><Input  class="common wid"  name=CustomersUnitCount ></td>
          <td class="title">资产管理费</td>
          <td class="input"><Input  class="common wid"  name=ManageFee ></td>

        </tr>
        
       <tr class="common"><td class="title">扩张收缩标志</td>
          <td class="input"><Input  class="common wid"  name=SKFlag ></td></tr>

      </table>
    </div>
      </div>
    
 
  <!-- <table>
      <tr>
               
        <td><input type="button" class=cssButton value="&nbsp;生效&nbsp;" onClick="updateClick0()"></td>
      </tr>
 </table> -->
 <a href="javascript:void(0);" class="button"onClick="updateClick0()">生  效</a>
   <table>
      <tr>
      <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo1);">
            </TD>
        <td class= titleImg>批处理</td></tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
   <table  class= common align=center>
      	<TR  class= common>
      		<TD  class= title5>计价日期</TD>
          <TD  class= input5>
               <Input class="coolDatePicker" dateFormat="short" id="DealDate" onClick="laydate({elem:'#DealDate'});"  name=DealDate elementtype=nacessary><span class="icon"><a onClick="laydate({elem: '#DealDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
      		<TD  class= title5>下个计价日</TD>
          <TD  class= input5>
              <Input class="coolDatePicker" dateFormat="short" id="NextPriceDate" onClick="laydate({elem:'#NextPriceDate'});"  name=NextPriceDate elementtype=nacessary><span class="icon"><a onClick="laydate({elem: '#NextPriceDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>       
      	</tr>
      
    </table>
     
  
    </div>   
       
    </div>
    <!-- <input type="button" class=cssButton value="&nbsp;批处理&nbsp;" onClick="BatchDeal()"> --> 
    <a href="javascript:void(0);" class="button"onClick="BatchDeal()">批处理</a>
    <br><br> <br><br>     	

<input type=hidden name=StartDate>
<input type=hidden name=EndDate>
<input type=hidden name=InvestFlag>
<input type=hidden name=InvestFlagName>
<input type=hidden name=SRateDate>
<input type=hidden name=ARateDate>
<input type=hidden name=RedeemRate>
<input type=hidden name=RedeemMoney>
<input type=hidden name=Transact >
<input type=hidden name=State >
<input type=hidden name=OldDealDate >
<input type=hidden name=OldNextPriceDate >
<input type=hidden name=DoBatch >
    
  </form>



  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <script>changecolor(); </script>
</body>

</html>
