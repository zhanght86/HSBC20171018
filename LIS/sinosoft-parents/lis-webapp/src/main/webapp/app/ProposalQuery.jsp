<%
//程序名称：ProposalQuery.jsp
//程序功能：复核不通过修改
//创建日期：2002-11-23 17:06:57
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="ProposalQuery.js"></SCRIPT>
  <%@include file="ProposalQueryInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <title>保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：（注：输入投保单号码或者暂收收据号/划款协议书号可独立查询。如无法提供请输入至少两条其他条件）</td>
		  </tr>
	  </table>
    <div class="maxbox">
    <Div  id= "divFCDay" style= "display:  "> 
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> 印刷号  </TD>
          <TD  class= input5> <Input class= "common wid" name=ContNo id="ContNo"> </TD>
          <TD  class= title5> 管理机构 </TD>
          <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="管理机构|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName"> </TD> 
        </TR>
        <TR  class= common>         
          <TD  class= title5> 投保日期 </TD>
          <TD  class= input5> 
            <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>          
          <TD  class= title5>  投保人客户号  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntNo" id="AppntNo"> </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  投保人姓名  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntName" id="AppntName"> </TD>
          <TD  class= title5>  投保人证件号码  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntIDNo" id="AppntIDNo"> </TD>       
        </TR>
        <tr class="common">
          <TD  class= title5>  被保人客户号  </TD>
          <TD  class= input5> <Input class= "common wid"  name="InsuredNo" id="InsuredNo"> </TD>
          <TD  class= title5>  被保人姓名</TD>
          <TD  class= input5> <Input class= "common wid"  name=InsuredName id="InsuredName"> <input type="hidden" name="InsuredSex" id="InsuredSex"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  被保人证件号码  </TD>
          <TD  class= input5> <Input class= "common wid"  name="InsuredIDNo" id="InsuredIDNo"> </TD>
          <TD  class= title5>  核保结论</TD>
          <td class=input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno CodeData="0|^1|拒保^2|延期^4|次标准体^9|标准体^a|撤保^z|核保订正" name=uwState id="uwState" onclick="return showCodeListEx('acontuwstate',[this,uwStatename],[0,1]);" ondblclick="return showCodeListEx('acontuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKeyEx('acontuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id="uwStatename" readonly ></td>
        </tr>
        <TR  class= common>
          <TD  class= title5> 业务员编码  </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode id="AgentCode" onclick="return showCodeList('AgentCode',[this, 'AgentGroup'], [0, 2]);" ondblclick="return showCodeList('AgentCode',[this,'AgentGroup'], [0, 2]);" onkeyup="return showCodeListKey('AgentCode', [this, 'AgentGroup'], [0, 2]);">  </TD>
          <Input class= "common wid"  type= "hidden" name=AgentGroup id="AgentGroup">
          <Input type= "hidden" class= "common wid"  name=PrtNo id="PrtNo">
        </TR>

        <TR  class= common style= "display: none">
          <TD  class= title5> 核保等级 </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=uwgrade id="uwgrade" onclick="return showCodeList('lduwuser',[this],[0]);" ondblclick="return showCodeList('lduwuser',[this],[0]);" onkeyup="return showCodeListKey('lduwuser',[this],[0]);"> </TD>          
        </Tr>
      <TR  class= common >
          <TD  class= title5>暂收收据号/划款协议书号</TD>
          <TD  class= input5> <Input class= "common wid"  name=tempfeeno id="tempfeeno"> 
        </TR>      
    </table>
  </Div>
  </div>
  <div>
    <INPUT class=cssButton VALUE="     查询投保单     " TYPE=button onclick="easyQueryClick();">
  </div>
  <br>
      
  <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<center>
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 					
  	</div>
    <br>
    <div>
        <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button name="Button1" onclick="queryProposal();">
        <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button name="Button2" onclick="queryNotProposal();">
		<INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button name="Button3" onclick="queryEdor()">
		<INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button name="Button4" onclick="queryClaim();">
	</div>
	<br>
	<div>
		<INPUT class=cssButton VALUE="     投保单明细     " TYPE=button name="Button5" onclick="queryDetailClick();"> 	
		<INPUT class=cssButton VALUE="   投保单影像查询   " TYPE=button name="Button6" onclick="ImageQuery();">
		<INPUT class=cssButton VALUE="     被保人查询     " TYPE=button name="Button7" onclick="InsuredQuery();">
		<INPUT class=cssButton VALUE="     保费项查询     " TYPE=button name="Button8" onclick="PremQuery();">
	</div>
	<br>
	<div>
		<INPUT class=cssButton VALUE="    操作履历查询    " TYPE=button name="Button9" onclick="OperationQuery();">
		<INPUT class=cssButton VALUE="     暂交费查询     " TYPE=button name="Button10" onclick="showTempFee();">        
		<INPUT class=cssButton VALUE="      状态查询      " TYPE=button name="Button11" onclick="statequery();">
	</div>
	<br>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
