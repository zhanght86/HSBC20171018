<html> 
<% 
//程序名称：
//程序功能：个人保全
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>    

<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./GEdorTypeCT.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="GEdorTypeCTInit.jsp"%>
  
  
</head>
<body  onload="initForm();" >
  <form action="./GEdorTypeCTSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
	<TABLE class=common>
    <TR  class= common> 
      <TD  class= title > 批单号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorNo id=EdorNo>
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
     
      <TD class = title > 团体保单号 </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
      </TD>   
    </TR>
    <TR  class= common>
    	 <TD class =title>申请日期</TD>
    	  <TD class = input>    		
    		<input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  </TD>
    	 <TD class =title>生效日期</TD>
    	  <TD class = input>
    		<input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  </TD>
    		<TD class = title ></TD>
      <TD class = input ></TD>
      </TR>
	</TABLE> 
	</div>
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    团体下合同险种信息
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''" align=center>
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanGrpPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
			
    </DIV>
   


   <Div id= "divEdorquery" style="display: ''" align="center">

       	     <Input  class= cssButton type=Button value="解除合同" onclick="edorTypeCTSave()">
       	     <Input type=Button value="费用明细" class= cssButton onclick="MoneyDetail()">
     		 <Input  class= cssButton type=Button value="返  回" onclick="returnParent()">
     		 <!--Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();"-->  
	</Div>
<DIV id="divCTInfo" style="display: 'none'">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeePolDetail);">
                </td>
                <td class= titleImg> 险种退费明细 </td>
            </tr>
	</table>
        
	<Div  id= "divCTFeePolDetail" style= "display: ''" align=center>
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCTFeePolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
	</Div>
	
	 <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeInfo);">
                </td>
                <td class= titleImg> 退费列表 </td>
            </tr>
	</table>
<Div  id= "divCTFeeInfo" style= "display: ''" >	
	<DIV id="DivLCPol" style="display : ''" align="center">
				<table  class= common>
					<tr  class= common>
				  	<td text-align: left colSpan=1>
					  	<span id="spanLCPolGrid" ></span> 
	  				</td>
	  			</tr>
				  
				</table>	
						<INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage1.firstPage();"> 
				    	<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage1.previousPage();"> 					
				    	<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage1.nextPage();"> 
				    	<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage1.lastPage();">
				  	
 	</DIV>
        

      <table  class= common>       
        <TR class = common>
            <TD  class= title > 退费合计金额 </TD>
			<TD  class= title ><Input class= "readonly wid" readonly name=GetMoney  id=GetMoney>元</TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
       </TR>
     </table>
	</DIV>
</DIV>	
        
	<Div  id= "divCTFeeDetail" style= "display: none">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCTFeeDetailGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
	</Div>
	
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="ContType" name="ContType">
	<input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	<input type=hidden id="EdorTypeCal" name="EdorTypeCal">
	<input type=hidden id="EdorState" name="EdorState">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
	
</script>
</html>
