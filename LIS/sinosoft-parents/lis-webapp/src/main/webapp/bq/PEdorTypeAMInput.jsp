<html> 
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: 客户联系方式变更
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeAM.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeAMInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeAMSubmit.jsp" method=post name=fm id=fm target="fraSubmit">  
  <div class="maxbox1"> 	
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title >保全申请号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title >批改类型</TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>    
      <TD class = title >保单号</TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
      <TR class=common>
    	<TD class =title>柜面受理日期</TD>
    	<TD class = input>    		
    		<!--<input class = "multiDatePicker" readonly name=EdorItemAppDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EdorItemAppDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EdorItemAppDate id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            
    	</TD>
    <!--	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<input class = "readonly" readonly name=EdorValiDate>
    	</TD>-->
    	<TD class =title></TD>
    	<TD class = input></TD>
    	<TD class =title></TD>
    	<TD class = input></TD>
    </TR>   
    </TR>
  </TABLE>  
</div>
<table>
			<td class="common">
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
			</td>
			<td class= titleImg>
			  客户的信息
			</td>
</table>


<DIV id=DivLCInsured STYLE="display:''" class="maxbox1">
    <table  class= common>
        <TR  class= common>
          <TD  class= title>
            通讯地址
          </TD>
          <TD  class= input>
            <Input class= "common3 wid" name="PostalAddress" id="PostalAddress" readonly >
          </TD>
          <TD  class= title>
            通讯地址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  name="ZipCode" id="ZipCode"  readonly  >
          </TD>
 <TD  class= title>
            住址
          </TD>
          <TD  class= input >
            <Input class= "common3 wid" name="HomeAddress" id="HomeAddress" readonly >
          </TD>
        </TR>
        <TR  class= common>
         
          <TD  class= title>
            住址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  name="HomeZipCode"  id="HomeZipCode" readonly >
          </TD>
<TD  class= title>
            首选回访电话
          </TD>
          <TD  class= input>
            <Input class="wid" class= common  name="Mobile" id="Mobile" readonly >
          </TD>
          <TD  class= title>
            其他联系电话
          </TD>
          <TD  class= input>
            <Input class="wid" class= common  name="GrpPhone" id="GrpPhone"  readonly  >
          </TD>
        </TR>
        <tr class=common>
          
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= common>
            <Input class="wid" class= common  name="EMail" id="EMail" readonly >
          </TD>
          <TD  class= title  >
            工作单位
          </TD>
          <TD  class=input >
            <Input class="wid" class=common3   name="GrpName" id="GrpName" readonly  >
          </TD>
          <TD  class= title>
          </TD>
          <TD  class= input>
        
          </TD>
        </tr>
               
	</table>
</DIV>

<table>
			<td class="common">
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPInsured);">
			</td>
			<td class= titleImg>
			  客户更改后的信息
			</td>
</table>


<DIV id=DivLPInsured STYLE="display:''" class="maxbox1">
    <table  class= common>
          <TR  class= common>
          <TD  class= title>
            通讯地址
          </TD>
          <TD  class= input>
            <Input class= "common3 wid" name="PostalAddress_New" id="PostalAddress_New" verify="客户通讯地址|notnull&len<=80" elementtype=nacessary  >
          </TD>
          <TD  class= title>
            通讯地址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  name="ZipCode_New" id="ZipCode_New" verify="客户通讯地址邮政编码|notnull&zipcode&len=6" elementtype=nacessary >
          </TD>
<TD  class= title>
            住址
          </TD>
          <TD  class= input>
            <Input class= "common3 wid" name="HomeAddress_New" id="HomeAddress_New" verify="客户住址|notnull&len<=80" elementtype=nacessary   >
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            住址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  name="HomeZipCode_New" verify="客户住址邮政编码|notnull&zipcode&len=6" elementtype=nacessary   >
          </TD>
 <TD  class= title>
            首选回访电话
          </TD>
          <TD  class= input>
            <Input class="wid" class= common  name="Mobile_New" id="Mobile_New" verify="首选回访电话|notnull&TEL&len<=15" elementtype=nacessary >
          </TD>
          <TD  class= title>
            其他联系电话
          </TD>
          <TD  class= input>
            <Input class="wid" class= common  name="GrpPhone_New" id="GrpPhone_New" verify="其他联系电话|TEL&len<=18" elementtype=nacessary  >
          </TD>
        </TR>
        <tr class=common>
         
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class="wid" class= common  name="EMail_New" id="EMail_New" verify="客户电子邮箱|EMAIL&LEN<=40"  >
          </TD>
          <TD  class= title  >
            工作单位
          </TD>
          <TD  class=input >
            <Input class="wid" class=common3  name="GrpName_New" id="GrpName_New" verify="客户工作单位|len<=80">
          </TD>
          <TD  class= title>
          </TD>
          <TD  class= input>
       
          </TD>
        </tr>
        
	</table>
</DIV>
<br>
<Div id= "divEdorquery" style="display: ''">
	<Input class= cssButton type=Button value=" 保  存 " onclick="edorTypeAMSave()">
	<Input class= cssButton type=Button value=" 返  回 " onclick="returnParent()">
	<input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">

</Div>
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <!--客户号-->
	 <input type=hidden id="CustomerNo" name="CustomerNo">	 
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <input type=hidden id="AddressNo" name="AddressNo">
	 	 <input type=hidden id="EdorValiDate" name="EdorValiDate">
	 	 <!--投保人还是被保人,0,投保人，1，被保人，2 两者兼之-->
	 <input type=hidden id="AppntIsInsuredFlag" name="AppntIsInsuredFlag">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


