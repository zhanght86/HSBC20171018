    <%
//程序名称：TempFeeInput.jsp
//程序功能：财务收费的输入
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="ContInsuInput.js"></SCRIPT>
<%@include file="ContInsuInit.jsp"%>
  
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
</head>
<body  onload="initForm();" >
<form action="./ProposalSave.jsp" method=post name=fm id="fm" target="fraSubmit">

<DIV id=DivRiskHidden class=maxbox1 STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      *保单号 
    </TD>
    <TD CLASS=input>
      <Input class="readonly wid" readonly NAME=ContNo id="ContNo">
    </TD>
    <TD CLASS=title>
      管理机构 
    </TD>
    <TD CLASS=input>
      <Input class="readonly wid" readonly NAME=AgentCom id="AgentCom">
    </TD>
    <TD CLASS=title>
      投保人
    </TD>
    <TD CLASS=input>
      <Input class="readonly wid" readonly NAME=Handler id="Handler">
    </TD>
  </TR>

</TABLE>
</DIV>
<TABLE class=common>

  <TR class=common>
    <td class=title>
  	*客户号 
    </TD>
    <TD CLASS=input >
      <Input NAME=CustomerNo id="CustomerNo"  CLASS="common wid" MAXLENGTH=20 >
    </TD>
    <td class=title></TD>
    <TD CLASS=input ></TD>
	<td class=title></TD>
    <TD CLASS=input ></TD> 
   </tr>
    
</table>
 <table>
 <tr>
 <td>
     <input type =button class=cssButton value="查 询" onclick=""> 
     </td>
     
      </tr>
      </table>
      <table>
    	<tr>
        	<td class=commond>
		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
    		</td>
    		<td class= titleImg>被保人信息</td>
    	</tr>
    </table>
<DIV id=DivLCInsured class=maxbox STYLE="display: ">

    <table class=common>	
  <TR CLASS=common>
    <TD CLASS=title>
      *姓名 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name id="Name" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="姓名|notnull" >
    </TD>
    <TD CLASS=title>
      *性别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id="Sex" VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="被保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      *出生日期 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday id="Birthday" VALUE="" CLASS="common wid" verify="被保人出生日期|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      *证件类型 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType id="IDType" VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="被保人证件类型|code:IDType" >
    </TD>
    <TD CLASS=title>
      *证件号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo id="IDNo" VALUE="" CLASS="common wid" MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      国籍
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=NativePlace id="NativePlace" VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="被保人国籍|code:NativePlace" >
    </TD>
  </TR>

  
  <TR CLASS=common>
  <TD CLASS=title>
      *地址编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="code" NAME=AddressNo id="AddressNo" VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      户口所在地 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RgtAddress id="RgtAddress" VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      住址 
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress id="HomeAddress" VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      邮政编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode id="HomeZipCode" VALUE="" CLASS="common wid" MAXLENGTH=6 verify="被保人邮政编码|zipcode" >
    </TD>
    <TD CLASS=title>
      联系电话（1）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone id="Phone" VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      联系电话（2）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 id="Phone2" VALUE="" CLASS="common wid" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      工作单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName id="GrpName" VALUE="" CLASS="common wid" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      职业（工种） 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=WorkType id="WorkType" VALUE="" CLASS="common wid" MAXLENGTH=10 >
    </TD>
    <TD CLASS=title>
      兼职（工种） 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PluralityType id="PluralityType" VALUE="" CLASS="common wid" MAXLENGTH=10 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      *职业代码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationCode id="OccupationCode" VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="被保人职业代码|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      职业类别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationType id="OccupationType" VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=10 verify="被保人职业类别|notnull&code:OccupationType" >
    </TD>
    <TD CLASS=title>
      *家庭保单号 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=FamilyContNo id="FamilyContNo" VALUE="" CLASS="common wid" MAXLENGTH=10  >
    </TD>
  </TR>
  <!-- 告知信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 告知信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td sytle=text-align:left colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
<tr>
<td>
      <input type =button class=cssButton value="保存被保人信息" onclick=""> 
      </td>
      <tr>
</TABLE>
</DIV>

<table>
    <TR class=common> 
    <TD class=common>
      <input type =button class=cssButton value="上 一 步" onclick="returnparent();">      
     </TD>
         
     <TD   class=common>
      <input type =button class=cssButton value="录入被保人险种信息" onclick="InputPolicy();">      
     </TD>
     
     <TD   class=common>
      <input type =button class=cssButton value="查询被保人险种信息" onclick="queryInsuRisk();">      
     </TD>
     
    </TR> 
    </table> 
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




