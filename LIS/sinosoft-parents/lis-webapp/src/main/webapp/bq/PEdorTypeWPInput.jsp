<html> 
<% 
//程序名称：
//程序功能：个人保全
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>

  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeWP.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeWPInit.jsp"%>
  
  <title>万能险追加保费</title> 
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeWPSubmit.jsp" method=post name=fm id=fm target="fraSubmit"> 
<div class=maxbox1>  
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > 保全受理号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
     
      <TD class = title > 保单号 </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>    		
    		<input class="readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<input class="readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    </TR>
  </TABLE> 
</div>
   <!--险种的详细信息-->
   <table>
   <tr>
      <td class= common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
   </tr>
   </table>
	 
    <Div  id= "divPol1" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
          			 <span id = "spanCustomerGrid">
          			 </span>   
        	  	</td>
        	</tr>
        </table>
    </Div>
    	
  <table>
   <tr>
      <td class= common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        险种基本信息
      </td>
   </tr>
   </table>
    	<Div  id= "divPol2" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
          			<span id = "spanPolGrid">
          			</span>
        	  	</td>
        	</tr>
        </table>
    </Div>
    	<!--加保后信息-->
<table>
   <tr>
      <td class= common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMRPol);">
      </td>
      <td class= titleImg>
        追加保费金额
      </td>
   </tr>
   </table>
   
  <Div  id= "divMRPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td class= common >
              	<Input class ='common' id = "AddPrem" name=AddPrem> 元
                </td>
            </tr>
        </table>
    
     <!--操作区域-->

   <Div  id= "DivEdorSaveButton" style= "display: ''">
           <Input  class= cssButton type=Button value=" 保  存 " onclick="edorTypeMRSave()">
           <br>
   </div>
  </Div>
  
  	<table style= "display: none">
       <tr >
           <td class="common">
               <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPolb);">
           </td>
           <td class= titleImg>
               追加记录
           </td>
       </tr>
  </table>
  
  <Div  id= "divLCGrpPolb" style= "display: none">
      <table  class= common>
           <tr  class= common>
               <td><span id="spanNoteGrid" ></span></td>
            </tr>
      </table>
      <Div id="DivEdorCancelButton" style="display: ''">
         <Input class=cssButton type=Button value=" 撤  销 " onclick="deleteRecord()">
      </Div>
   </DIV>
   <br>
   <br>
	  <TABLE>	
		  <TR>
	       <TD> 					     	 
	       	 <Input class= cssButton type=Button value="返  回" onclick="returnParent()">
	       	 <input class= cssButton TYPE=hidden VALUE="记事本查看" onclick="showNotePad()">
	     	 </TD>
	     </TR>
     </TABLE>
	 <input type=hidden id="CalMode" name = "CalMode">
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 1 name="ContType">	 
	 <input type=hidden id="InsuredNo" name= "InsuredNo">
	 <input type=hidden id="EdorNo" name= "EdorNo">
  </form>
</body>

</html>
