<html> 
<% 
//程序名称：
//程序功能：保全查询
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%> 
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); 
    
    //保全受理号
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");         
%>   
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

  <SCRIPT src="./GBqDetailQuery.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  
<%@include file="GBqDetailQueryInit.jsp"%>
<script language="javascript">
	//var intPageWidth=screen.availWidth;
	//var intPageHeight=screen.availHeight;
	//window.resizeTo(intPageWidth,intPageHeight);
	//window.screenleft=0;
	//window.screentop=0; 
	//window.focus();
</script>
</head>

<body  onload="initForm();" >
  <form action="./BqDetailQuery.jsp" method=post name=fm id=fm target="fraSubmit">     
  	<input type=hidden id="ContType2" name="ContType2">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> 保全受理信息 </td>
        </tr>
    </table>             
    <Div  id= "divEdorAppInfo" style= "display: ''" class=maxbox>
	        <TABLE class=common>
	            <tr class=common>
	                <td class=title> 保全受理号 </td>
	                <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo_Read id=EdorAcceptNo_Read></td>
	                <td class=title>  </td>
	                <td class= input> </td>
	                <td class=title>  </td>
	                <td class= input> </td>
	            </tr>         
	            <tr class=common>
	                <td class=title>  团体保单号 </td>
	                <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo_Read id=OtherNo_Read></td>
	                <TD class=title> 受理日期 </TD>
	                <td class= input><Input type="input" class="coolDatePicker" readonly name=EdorAppDate_Read onClick="laydate({elem: '#EdorAppDate_Read'});" id="EdorAppDate_Read"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate_Read'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</td>              
	                <td class=title>  批改状态 </td>
	                <td class= input><Input type="input" class="readonly wid" readonly name=PEdorStateName_Read id=PEdorStateName_Read></td>
	            </tr>
	            <tr class=common>
	                <td class=title> 申请人姓名 </td>
	                <td class= input><Input type="input" class="readonly  wid" readonly name=EdorAppName_Read id=EdorAppName_Read></td>            
	                <td class=title> 申请方式 </td>
	                <td class= input><Input type="input" class="readonly wid" readonly name=AppType_Read id=AppType_Read></td>
	            </tr>
	        </TABLE>  
   </Div>    
   
   <Div  id= "divCustomer" style= "display: ''">   
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerInfo);">
                </td>
                <td class= titleImg>
                    团体保单详细信息
                </td>
            </tr>
        </table>
        <Div  id= "divCustomerInfo" style= "display: ''" class=maxbox1>
          <table  class= common>
            <TR  class= common>
              <TD  class= title> 团体保单号 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=ContNoApp id=ContNoApp ></TD>
              <TD  class= title> 投保单位 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=GrpName id=GrpName ></TD>               
              <TD  class= title> 生效日期 </TD>
              <TD  class= input><Input class= "coolDatePicker" readonly name=CValiDate onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

			  </TD>           
            </TR>        
            <TR  class= common>
              <TD  class= title> 投保人数 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=AppntName id=AppntName ></TD>
              <TD  class= title> 总保费 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Prem id=Prem ></TD>
              <TD  class= title> 总保额 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Amnt id=Amnt ></TD>
            </TR>
          </table>      
        </Div>      
    </Div>                          

    <Div  id= "divCustomer" style= "display: 'none'">   
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerInfo);">
                </td>
                <td class= titleImg>
                    客户详细信息
                </td>
            </tr>
        </table>
        <Div  id= "divCustomerInfo" style= "display: ''" class=maxbox>
          <table  class= common>
            <TR  class= common>
              <TD  class= title> 客户号码 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=CustomerNo id=CustomerNo ></TD>
              <TD  class= title> 客户姓名 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Name id=Name ></TD>
              <TD  class= title> 客户性别 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=Sex id=Sex ></TD>
            </TR>        
            <TR  class= common>
              <TD  class= title> 客户出生日期 </TD>
              <TD  class= input><Input class= "coolDatePicker" readonly name=Birthday onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

			  </TD>
              <TD  class= title> 证件类型 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=IDType id=IDType></TD>              
              <TD  class= title> 证件号码 </TD>
              <TD  class= input><Input class= "readonly wid" readonly name=IDNo id=IDNo ></TD>
            </TR>
            <TR  class= common>          
              <TD  class= title> 单位名称 </TD>          
              <TD  class= input><Input class= "readonly wid" readonly name=GrpName1 id=GrpName1 ></TD>  
              <TD  class= title> </TD>                 
              <TD  class= input> </TD>  
              <TD  class= title> </TD>                 
              <TD  class= input> </TD>  
            </TR>
          </table>      
        </Div>      
    </Div>                   
    <Div  id= "divEdorItemInfo" style= "display: ''"> 
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> 保全项目信息 </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''"  >
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanEdorItemGrid" ></span> 
                    </td>
                </tr>
            </table> 
            <br>
    				<INPUT class=cssButton VALUE="保全明细查询" TYPE=button onclick="QueryEdorClick();">
    				<!--INPUT class=cssButton VALUE="保全影像查询" TYPE=button onclick="QueryPhoto();">
    				<INPUT class=cssButton VALUE="保全批单查询" TYPE=button onclick="QueryEdorRecipt();"-->
            <INPUT class=cssButton VALUE="  返  回  "   TYPE=button onclick="returnParent();">
        </div> 
    </DIV>    
  <input type=hidden id="fmtransact"      name="fmtransact">
	<input type=hidden id="ContType"        name="ContType" VALUE=2>	
	<input type=hidden id="EdorAcceptNo"    name="EdorAcceptNo">
	<input type=hidden id="OtherNo"         name="OtherNo">
	<input type=hidden id="OtherNoType"     name="OtherNoType">
	<input type=hidden id="AppType"         name="AppType">
	<input type=hidden id="BankCode"        name="BankCode">	                                             
	<input type=hidden id="addrFlag"        name="addrFlag"> 
	<input type=hidden id="EdorNo"          name="EdorNo">
	<input type=hidden id="EdorType"        name="EdorType">
	<input type=hidden id="AddressNo"       name="AddressNo">
	<input type=hidden id="LoadFlag"        name="LoadFlag">     
  <input type=hidden id="currentDay"      name="currentDay">
  <input type=hidden id="dayAfterCurrent" name="dayAfterCurrent">   
  <input type=hidden id="PEdorState"      name="PEdorState">
  <input type=hidden id="EdorItemState"   name="EdorItemState">
  <input type=hidden id="GrpContNo"       name="GrpContNo">
  <input type=hidden id="DisplayType"     name="DisplayType">  
  <input type=hidden id="ContNo"          name="ContNo">
  <input type=hidden id="PolNo"           name="PolNo">
  <input type=hidden id="DelFlag"         name="DelFlag">
  <input type=hidden id="MakeDate"        name="MakeDate">
  <input type=hidden id="MakeTime"        name="MakeTime">     
  <input type=hidden id="MissionID"       name="MissionID">
  <input type=hidden id="SubMissionID"    name="SubMissionID">               
  <input type=hidden id="ContNoBak"       name="ContNoBak">
  <Input type=hidden id="CustomerNoBak"   name="CustomerNoBak" >
  <input type=hidden id="EdorValiDate"    name="EdorValiDate">
  <input type=hidden id="EdorItemAppDate" name="EdorItemAppDate">
  <input type=hidden id="EdorAppDate"     name="EdorAppDate">
  <input type=hidden id="InsuredNo"       name="InsuredNo">
  <input type=hidden id="ButtonFlag"      name="ButtonFlag" value=1 >   
  <Input type=hidden id="EdorTypeCal"     name="EdorTypeCal" >   
  <Input type=hidden id="EdorTypeName"     name="EdorTypeName" >   
  <br/><br/><br/><br/>
  </form>    
</body>
</html>
