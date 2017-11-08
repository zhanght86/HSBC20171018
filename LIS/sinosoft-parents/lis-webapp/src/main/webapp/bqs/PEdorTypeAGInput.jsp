<html>
<%
//程序名称：
//程序功能：
//创建日期：
//创建人  ：Nicholas
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <SCRIPT src="./PEdor.js"></SCRIPT>

  <SCRIPT src="./PEdorTypeAG.js"></SCRIPT>

  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@include file="PEdorTypeAGInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
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
      	<input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>
    		<Input class= "readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<Input class= "readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
  </TABLE>
  </div>
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
   </tr>
   </table>

    <Div  id= "divPol" class="maxbox1" style= "display:  ">
      	<table  class= common>
      		<tr class common>
      			<td class = title>投保人姓名</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=AppntName id=AppntName>
      			</td>
      			<td class = title>证件类型</td>
      			<td class = input>
      				<Input class=codeno  "readonly" readonly name=AppntIDType id=AppntIDType >
					<input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true>
      			</td>
		       <TD  class= title > 证件号码</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo >
               </TD>
					</tr>
       		<tr  class= common>
		       <TD  class= title > 被保人姓名</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=InsuredName id=InsuredName >
               </TD>
           <TD  class= title > 证件类型</TD>
		       <TD  class= input >
               <Input class=codeno  "readonly" readonly name=InsuredIDType id=InsuredIDType >
			   <input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly=true>
               </TD>

		       <TD  class= title > 证件号码</TD>
		       <TD  class= input >
               <input class="readonly wid" readonly name=InsuredIDNo id=InsuredIDNo >
               </TD>
         </tr>

    	</table>
    </Div>

<table>
	   	<tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
	      </td>
	      <td class= titleImg>
	        年金、期满金给付信息
	      </td>
	   	</tr>
</table>
	<Div  id= "divAGInfo" class=maxbox1 style= "display:  ">
	  <table  class= common>
	  	<TR class = common >
	      <TD  class= title5>
	          应领金额
	      </TD>
	      <TD  class= input5 >
				 <Input class= "readonly wid"  readonly name=MoneyAdd id=MoneyAdd >
	  	  </TD>
	    	<TD  class= title5 >
	      续期领取形式
	      </TD>
	      <TD  class= input5 width="">
	        	<input class="codeno" name=GoonGetMethod id=GoonGetMethod readonly>
				<input class="codename" name=GoonGetMethodName id=GoonGetMethodName readonly>
				</TD>
                    <td class="title5">&nbsp;</td>
                    <td class="input5">&nbsp;</td>
	    </TR>
	  </table>

	<Div  id= "BankInfo" style= "display: none">
	  <table  class= common>
	  	<TR class = common >
	      <TD  class= title width="">
	      开户银行
	      </TD>
	      <TD  class= input width="">
	      	   	<Input class="codeno" name=GetBankCode id=GetBankCode readonly>
				<input class=codename name=BankName id=BankName readonly>
	      </TD>
	      <TD  class= title width="">
	      银行帐户</font>
	      </TD>
	      <TD  class= input width="">
					<Input class="common wid" name=GetBankAccNo id=GetBankAccNo type="text" readonly>
  			</TD>
	  	   <TD  class= title width="">
	      户   名
	      </TD>
	      <TD  class= input width="">
				 <Input class= "common wid"  name="GetAccName" id=GetAccName readonly>
	  	  </TD>
	  	</TR>
	  </table>
	</Div>

</Div>

<table>
	   	<tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetProject);">
	      </td>
	      <td class= titleImg>
	        领取项目
	      </td>
	   	</tr>
</table>

   <Div id="divGetProject" style="display: ">
   	<table  class= common>
        	<tr  class= common>
          		<td style="text-align: left" colSpan=1>
        			<span id="spanPolGrid" >
        			</span>
        	  	</td>
        	</tr>
        </table>
   	     <table align='center'>
    </table>
   </Div>
<br>
<Div id= "divEdorquery" style="display:  ">
    <!--Input  class= cssButton type=Button hidden value="保  存" onclick="edorTypeAGSave()"-->
    <Input  class= cssButton type=Button value="返  回" onclick="returnParent()">
    <!--Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();"-->
</Div>


	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <input type=hidden id="PolNo" name="PolNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</html>
