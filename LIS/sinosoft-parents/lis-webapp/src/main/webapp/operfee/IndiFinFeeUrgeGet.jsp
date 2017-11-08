<html>
<%
	//程序名称：IndiFinFeeUrgeGet.jsp
	//程序功能：个人即时交费和批量交费（即个人的续期催收交费核销）
	//创建日期：2002-10-3 
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="IndiFinFeeUrgeGet.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="IndiFinFeeUrgeGetInit.jsp"%>
</head>

<body>
<Form name=fm id="fm" action="./IndiFinVerifyUrgeGet.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin1);"></td>
		<td class=titleImg>不定期个人即时核销</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin1" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>暂交费收据号码</TD>
		<TD class=input5><Input class="common wid" name=TempFeeNo id="TempFeeNo"><a href="javascript:void(0)" class=button onclick="fmSubmit();">核  销</a> <!-- <Input
			class=cssButton type=Button value="核  销" onclick="fmSubmit()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class="line">

<Form name=fm2 id="fm2" action="./IndiFinVerifyUrgeGetByPolNo.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin2);"></td>
		<td class=titleImg>个人即时核销</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin2" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>保单号码</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"><a href="javascript:void(0)" class=button onclick="fmSubmit2();">核  销</a>  <!-- <Input
			class=cssButton type=Button value="核销" onclick="fmSubmit2()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class='line'>

<Form name=fm3 id="fm3" action="./GrpFinFeeUrgeGetSave.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin3);"></td>
		<td class=titleImg>团体即时核销</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin3" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>团单号码</TD>
		<TD class=input5><Input class="common wid" name=GrpContNo id="GrpContNo"><a href="javascript:void(0)" class=button onclick="fmSubmit3();">核  销</a> <!-- <Input
			class=cssButton type=Button value="核销" onclick="fmSubmit3()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class="line">

<Form name=fmMult id="fmMult" action=./MultFinVerifyUrgeGet.jsp target=fraSubmit
	method=post>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divMultFinFeeUrge)">
		</td>
		<td class=titleImg>个人批量核销(需录入暂交费)</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divMultFinFeeUrge" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD class=title style="width:9%">暂交费交费日期范围(当天及之前)</TD>
		<TD class=input><a href="javascript:void(0)" class=button onclick="fmMultsubmit();">核  销</a><!-- <INPUT class=cssButton VALUE="核销" TYPE=button
			name=magan onclick="fmMultsubmit();"> --></TD>
		<!-- <TD class=title5></TD>
		<TD class=input5></TD> -->
	</TR>
</Table>
</Div>
</div>
</Form>

<Form name=fmMultAuto id="fmMultAuto" action=./MultFinVerifyUrgeGetAuto.jsp
	target=fraSubmit method=post><!-- 显示或隐藏信息 >      	
    <table class= common>
      <tr>
      <td>
        <IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divMultFinFeeUrge);">
      </td>
      <td class= titleImg>个人批理核销(余额抵交续期保费,无需录入暂交费)</td>
     </tr>
    </table>
    <Div  id= "divMultFinFeeUrge" style= "display: ''">
      <Table  class= common>
        <TR  class= common>
          <TD  class= title>
            应收表缴费日期范围(当天至2个月后)
          </TD>
        <TD class= input>
          <INPUT class= cssButton VALUE="核销" TYPE=button onclick= "fmMultsubmitAuto();" >           
        </TD>          
        </TR> 
        <!-- 
        <TR class= common>
         <TD  class= title>
            起始日期 
          </TD>
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="StartDate" >           
          </TD>
        </TR> 
        <TR class= common>
         <TD  class= title>
          终止日期 
          </TD>        
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="EndDate" >                                 
          </TD>
        </TR> 
        
      </Table>    
    </Div>  --></Form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
