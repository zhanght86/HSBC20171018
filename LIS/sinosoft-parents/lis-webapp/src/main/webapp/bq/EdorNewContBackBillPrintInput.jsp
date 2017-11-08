<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//程序名称：NBCList.jsp
//程序功能：
//创建日期：2003-05-13 15:39:06
//创建人  ：zy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="EdorNewContBackBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="EdorNewContBackBillPrintInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
  <table >
  <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
    <td class= titleImg>
    保全理赔数据提取
    </td> 
   </tr> 
 </table> 
  
    <Div  id= "divFCDay" style= "display: ''">
      <div class="maxbox" >
      <table  class= common>
        <TR  class= common>
          <TD  class= title5> 起始时间 </TD>
          <TD  class= input5> 
          <input class="coolDatePicker" dateFormat="short" id="StartDay"  name="StartDay" verify="起始时间|NOTNULL  onClick="laydate
({elem:'#StartDay'});" > <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= titl5e> 结束时间 </TD>
          <TD  class= input5> 
          <input class="coolDatePicker" dateFormat="short" verify="结束时间|NOTNULL" id="EndDay"  name="EndDay" onClick="laydate({elem:'#EndDay'});"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
        </TR>
        <tr>
          <td class="title5">销售渠道</td>
          <td class="input5"><input type="text" class="codeno" name="SaleChnl" id=SaleChnl  ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1])" onKeyUp="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1])"><input type="text" class="codename" name="SaleChnlName" id=SaleChnlName readonly>
          </td>
          <TD  class= title5>管理机构<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class= code name=ManageCom id=ManageCom onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')" onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
       </tr>        
        <tr>
           <TR  class= common>
           <td class="title5">产品代码</td>
          <td class="input5"><input type="text" class="codeno" name="RiskCode" id=RiskCode  ondblclick="showCodeList('RiskCode',[this,RiskCodeName],[0,1])" onKeyUp="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" id=RiskCodeName readonly>
          </td>             
        </tr>    
        </table>
    </Div>    </Div>
    <table>
    	<tr> 
    		<td class="common">
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator1);">
    		</td>
    		<td class= titleImg>
          新契约数量统计
       	</td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator1" style= "display: ''">  
      <div class="maxbox1" >  
    <table  class= common>
		<TR class= common>
		  	<TD class="title5">
		  		新契约数量：
		  	</TD>
		  	
        <TD class= input5> 
        	<Input class="readonly wid" readonly name=polamount  id=polamount>
        </TD>
        
        <TD  class="title5"> 
        </TD>	
         <TD  class= "input5"> 
        </TD>	
		</TR>
    </table>
    </Div></Div>

      <a href="javascript:void(0);" class="button"onClick="polcount()">新契约数量统计</a>

    <table>
    	<tr> 
    		<td class="common">
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator2);">
    		</td>
    		<td class= titleImg>
          新契约拜访客户明细表
       	</td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator2" style= "display: ''"> 
    <div class="maxbox1" >   
  
       <a href="javascript:void(0);" class="button"onClick="printList()">报  表  打  印</a>
      </Div>
      </Div>
    
       <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";	
</script>
