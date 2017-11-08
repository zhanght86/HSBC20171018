<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("PriceInfoInput","werererererrrerwer");
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
  <SCRIPT src="PriceInfoInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PriceInfoInputInit.jsp"%>

</head>
<body  onload="initForm();" onclick="myCheckFiled();" >
  <form action="PriceInfoIputSave.jsp" method=post name=fm id="fm" target="fraSubmit">	
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    		</TD>
    		<TD class= titleImg>
    			 请输入查询条件
    		</TD>
    	</TR>
  </Table>
   <Div  id= "divQuery" style= "display: ''" >
      <div class="maxbox1" >
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
          投资帐户号码
          </TD>
          <TD  class= input5>
            <input class=codeno name=QureyInsuAccNo  id=QureyInsuAccNo 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');" 
            onDblClick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');"
             onKeyUp="return showCodeListKey('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');"><input class=codename name=QueryName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title5>
          价格起期
          </TD>
          <TD  class= input5>
               <Input class="coolDatePicker" dateFormat="short"   name=QueryStartDate id="QueryStartDate" onClick="laydate({elem:'#QueryStartDate'});"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>         
          </TD>
       </TR> 
       <TR  class= common>
       	<td class="title5">记录状态</td>
         <td class="input5"><Input class=codeno name=QueryState id=QueryState readonly CodeData='0|^1|录入^0|生效' value=5 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
          onclick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);"
           onDblClick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);" 
           onKeyUp="return showCodeListKeyEx('QueryState',[this,QueryStateName],[0,1]);"><input class=codename name=QueryStateName readonly=true elementtype=nacessary><font></font></td>
        </TR> 
 </Table>
 <!--<Table>
  	<TR>   
       <td width="10%">&nbsp;&nbsp;</td>        
       	<TD> <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> </TD>           
       </TR>
    </Table>-->
    <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
    </div>
    </div>
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
 <Div  id= "divCollectivityGrid" style= "display: ''" >
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      <!--<INPUT VALUE="首  页" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="上一页" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	-->	
 </Div>		
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrice);">
    		</TD>
    		<TD class= titleImg>
    			 请输入计价信息
    		</TD>
    	</TR>
  </Table>
    <div  id= "divPrice" style= "display: ''">
     <div class="maxbox1" >
      <table  class=common>
          <tr class=common>
          <td class="title5">投资帐户号码</td>
         <td class="input5"><input class=codeno name=InsuAccNo 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
         onclick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" 
         onDblClick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" 
         onKeyUp="return showCodeListKey('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" ><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary ><font size=1 color='#ff0000'><b>*</b></font></td>
       <td class="title5">资产评估日期</td>
          <td class="input5"><input class="multiDatePicker laydate-icon" dateFormat="short"  name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  <font size=1 color='#ff0000'><b>*</b></font></td>
          
       </tr>

      <tr class=common >

          <td class="title5">账户总资产(单位：元)</td>
          <td class="input5"><Input class="common wid" name=InsuTotalMoney ><font size=1 color='#ff0000'><b>*</b></font></td>
          <td class="title5">负债(单位：元)</td>
          <td class="input5"><Input class="common wid" name=Liabilities ><font size=1 color='#ff0000'><b>*</b></font></td>
      </tr>
      <tr class=common >
      <td class="title5">公司投资单位数</td>
      <td class="input5"><Input class="common wid" name=CompanyUnitCount readonly></td>
      <td class="title5">本次变动单位数</td>
      <td class="input5"><Input class="common wid" name=ComChgUnitCount ><font size=1 color='#ff0000'><b>*</b></font></td>
			</tr>	
        <tr class=common >
        	<td class="title5">客户投资单位数</td>
      		<td class="input5"><Input class="common wid" name=CustomersUnitCount readonly></td>
					<td class="title5">收缩扩张标志</td>
         <td class="input5"><Input class=codeno name=SKFlag readonly CodeData='0|^1|收缩期^0|扩张期'
         style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
         onclick="return showCodeListEx('SKFlag',[this,SKFlagName],[0,1]);" 
         onDblClick="return showCodeListEx('SKFlag',[this,SKFlagName],[0,1]);" 
         onKeyUp="return showCodeListKeyEx('SKFlag',[this,SKFlagName],[0,1]);" onBlur="QueryCompanyUnitCount();" ><input class=codename name=SKFlagName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>

       </tr>
       <td class="title5">记录状态</td>
          <td class="input5"><Input class="common wid" name=StateName readonly></td>
        <tr class=common >        	

       </tr>        
      </table> 
  <!-- <table>
      <tr>
        <td >&nbsp;&nbsp;</td>
        <td><input type="button" class=cssButton value="&nbsp;增加&nbsp;" onClick="submitFrom()"></td>
        <td><input type="button" class=cssButton value="&nbsp;修改&nbsp;" onClick="updateClick()"></td>
        <td><input type="button" class=cssButton value="&nbsp;删除&nbsp;" onClick="deleteClick()"></td>
        <td><input type="button" class=cssButton value="&nbsp;重置&nbsp;" onClick="resetForm()"></td>
      </tr>
 </table>-->
 <br>
 <a href="javascript:void(0);" class="button"onClick="submitFrom()">增   加</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">修   改</a>
 <a href="javascript:void(0);" class="button"onClick="deleteClick()">删  除</a>
<a href="javascript:void(0);" class="button" onClick="resetForm()">重   置</a>

        <input type=hidden name=Transact >
        <input type=hidden name=State >
        </div>
        </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
