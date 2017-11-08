<html>
<%
//程序名称：红利领取方式变更
//程序功能：个人保全
//创建日期：2008-08-18 15:43:22
//创建人  ：WangWei
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeBM.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeBMInit.jsp"%>

  <title>红利领取方式变更 </title>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeBMSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
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
    	<TD class =title>柜面受理日期</TD>
    	<TD class = input>
    		<input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    </TR>
  </TABLE>
  </div>
   <!--险种的详细信息-->
   <table>
   <tr>
      <td class=common>
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
        			<span id="spanCustomerGrid" >
        			</span>
        	  	</td>
        	</tr>
        </table>
    </Div>
    
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainPol);">
      </td>
      <td class= titleImg>
        险种基本信息
      </td>
   </tr>
   </table>

    <Div  id= "divMainPol" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanMainPolGrid" >
        			</span>
        	  	</td>
        	</tr>
        </table>
    </Div>

	  <table>
	   <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
	      </td>
	      <td class= titleImg>
	        可做变更的险种
	      </td>
	   </tr>
	  </table>
	   
	  <Div  id= "divPol2" style= "display: ''">
	  	<table  class= common>
	    	<tr  class= common>
	      		<td text-align: left colSpan=1>
	    			<span id="spanPolGrid" >
	    			</span>
	    	  	</td>
	    	</tr>
	    </table>
	  </Div>
    
    
  <Div id= "divEdorquery" style="display: ''">
    <TABLE>
        <TR>
	       <TD>
            <Input class= cssButton type=Button value="保  存" onclick="edorTypeBMSave()">
            <Input class= cssButton type=Button value="返  回" onclick="returnParent()">
	          <input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">
	       </TD>
	      </TR>
     </TABLE>
  </Div>

	 <input type=hidden id="CalMode" name = "CalMode">
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 1 name="ContType">
	 <input type=hidden id="InsuredNo" name= "InsuredNo">
	 <input type=hidden id="PolNo" name= "PolNo">
   <input type=hidden id="EdorNo" name= "EdorNo">

  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
