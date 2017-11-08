<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>暂交费信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./TempFeeQuery.js"></script> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="TempFeeQueryInit.jsp"%>


</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id="fm" action="./TempFeeQueryResult.jsp" target=fraSubmit method=post>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" onMouseDown= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 请输入查询条件：
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox1" id="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
      	  <TD  class= title>
            管理机构
          </TD>          
          <TD  class= input>
				<Input class=codeno name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		  </TD>        		
          <TD  class= title>
          暂交费状态
          </TD>
          <TD  class= input>
            <Input class= code name=TempFeeStatus id="TempFeeStatus" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" verify="号码类型|NOTNULL" CodeData="0|^0|未到帐^1|未核销^2|已核销^3|已退费^4|新保未核销^5|续保未核销^6|全部" onMouseDown="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" ondblClick="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" onKeyUp="showCodeListKeyEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" >
          </TD> 
          <TD  class= title>
            险种
          </TD>
          <TD  class= input>
            <Input class= code name=RiskCode id="RiskCode"  style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onMouseDown="return showCodeListKey('RiskCode',[this]);"  onDblClick="return showCodeList('RiskCode',[this]);" onKeyUp="return showCodeListKey('RiskCode',[this]);">
          </TD>         
       </TR>                     
      	<TR  class= common>
          <TD  class= title>
          开始日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>       	
          <TD  class= title>
          结束日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EndDate id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>
          <TD  class= title>
          操作员
          </TD>
          <TD  class= input>
            <Input class="common wid" name=Operator id="Operator" >
          </TD>   
       </TR>
       <!--TR  class= common>      	
          <TD  class= title>
          最小金额:
          </TD>
          <TD  class= input>
            <Input class=common name=MinMoney >
          </TD> 
          <TD  class= title>
          最大金额:
          </TD>
          <TD class= input>
            <Input class=common name=MaxMoney >
          </TD>               	
         <TD  class= title>
          业务员:
          </TD>
          <TD  class= input>
            <Input class=common name=AgentCode >
          </TD>       	
       </TR-->
       
       <TR  class= common>
          <TD  class= title>
          暂收据号:
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id="TempFeeNo" >
          </TD>  
          <TD class = title>
          投保单印刷号/保单号
          </TD>
          <TD class=input>
            <Input class="common wid" name=PrtNo id="PrtNo">
          </TD>
          <TD  class= title>
          业务员
          </TD>
          <TD  class= input>
            <Input class="common wid" name=AgentCode id="AgentCode" >
          </TD> 
        </TR>
      </table>
      </div>
      <div id=divChequeNo style='display:none'>
      <table class=common align=center>
        <TR class=common>
          <TD class=title>
            支票号码:
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeNo id="ChequeNo">
          </TD>
          
        </TR>
      </table>
      </div>
            <INPUT VALUE="查  询" Class=cssButton TYPE=button onMouseDown="submitForm();" name="QUERYName">  
            <!-- <INPUT CLASS=cssButton VALUE="打  印" TYPE=button onMouseDown="easyprint();" name="PrintName"> -->
   </div>
   <BR><BR>
     <!--INPUT VALUE="返回" Class=cssButton TYPE=button onMouseDown="returnParent();"-->   
    
   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" onMouseDown= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 暂交费信息
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divTempFee1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanTempQueryGrid" ></span> 
  	</TD>
      </TR>
    </Table>
      
	<center>			
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onMouseDown="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onMouseDown="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onMouseDown="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onMouseDown="getLastPage();"> 					
    </center>
 <input type=hidden id="fmAction" name="fmAction">
 <br><br><br><br>
 </Div>					
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>

