<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>    
<%
//程序名称：LCGrpPolBillInput.jsp
//程序功能：
//创建日期：2003-3-24
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script><head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LCGrpPolBillInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LCGrpPolBillInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr> 
    		<td class="common">
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
    		</td>
    		 <td class= titleImg>
        		输入查询的条件
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLCPol" style= "display: ''">
    <div class="maxbox1" >
      <table  class= common>
        <TR  class= common>
         <TD  class= title5>
            管理机构
          </TD>          
           <TD class= input5><Input type="text" class="codeno" name=ManageCom  id=ManageCom
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);"
 
           onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" > </TD>
          <TD  class= title5 width="25%">
            起始日期
          </TD>
          <TD  class= input5 width="25%">
             <input class="coolDatePicker" dateFormat="short" verify="起始时间|NOTNULL" id=StartDay  name=StartDay onClick="laydate
({elem:'#StartDay'});" > <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          </TR>
        <TR  class= common>
          <TD  class= title5 width="25%">
            结束日期
          </TD>
          <TD  class= input5 width="25%">
            <input class="coolDatePicker" dateFormat="short" id=EndDay  name=EndDay  onClick="laydate({elem:'#EndDay '});" verify="结束日期|NOTNULL"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
       
	     		<TD  class= title5>
            销售渠道 
          </TD>
          <TD class= input5><Input type="text" class="codeno" name=SaleChnl  id=SaleChnl verify="销售渠道|code:SaleChnl"
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
           onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
            onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class="codename" name="SaleChnlName" ></TD>          	
           </TR>
        <TR  class= common>
          <TD  class= title5 width="25%">
            起始时间
          </TD>
          <TD  class= input5 width="25%">
            <Input class="common wid" name=StartTime verify="起始时间|NOTNULL">
          </TD>
          <TD  class= title5 width="25%">
            结束时间
          </TD>
          <TD  class= input5 width="25%">
            <Input class="common wid" name=EndTime verify="结束时间|NOTNULL">
          </TD>  
        </TR>
        <TR  class= common>
 
          <TD  class= title5>
            起始保单号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=StartContNo >
          </TD>
          <TD  class= title5>
            终止保单号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=EndContNo >
          </TD>	
            </TR>
        <TR  class= common>     	
          <TD  class= title5> 机构级别 </TD>
          <TD  class= input5>  <Input type="text" class="codeno" name=ManageGrade id=ManageGrade
           value=<%=tGI.ManageCom.length()%> CodeData="0|^4|二级机构|^6|三级机构^8|四级机构"
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);"  
            ondblClick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);" 
            onKeyUp="showCodeListKeyEx('MangeComGrade',[this,MangeComGradeName],[0,1]);"><input class="codename" name="MangeComGradeName" ></TD>
         </TR>     
        <TR  class= common>
    </TR>          

   <!--     <tr>
        <TD  class= title>
            展业机构编码 
          </TD>
          <TD  class= input>
            <Input class=common name=AgentGroup >
            <input name="btnQueryCom" class="common" type="button" value="组别查询" onclick="queryCom()" style="width:100; ">
          </TD> 
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode  ondblclick="return queryAgent(comcode);" onkeyup="return queryAgent(comcode);">
          </TD>
        </tr>
        <TR  class= common>
          <TD  class= title>
            起始保单号
          </TD>
          <TD  class= input>
            <Input class= common name=StartPolNo >
          </TD>
          <TD  class= title>
            终止保单号
          </TD>
          <TD  class= input>
            <Input class= common name=EndPolNo >
          </TD>	     		  
        </TR>
        <TR>
          <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD>  
          <TD  class= title>
            印刷号
          </TD>
          <TD  class= input>
            <Input class= common name=PrtNo >
          </TD>  
        </TR>
        <TR>
          <TD  class= title>
            投保人姓名
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName >
          </TD>  
          <TD  class= title>
            被保人姓名
          </TD>
          <TD  class= input>
            <Input class= common name=InsuredName >
          </TD>  
        </TR>     -->
     </table> 
</div></Div>
<br>
  	<input class="cssButton" type=button value="保单交接清单打印" onClick="fnBillPrint()">
    <input class="cssButton" type=button  name="BillRePrint" value="补打保单交接清单" onClick="fnBillPrintLR()">
        
    </Div>
      
        <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
