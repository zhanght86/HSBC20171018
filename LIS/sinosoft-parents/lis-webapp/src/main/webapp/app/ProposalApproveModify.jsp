<%
//程序名称：ProposalApproveModify.jsp
//程序功能：问题件修改
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
	var comcode = "<%=tGI.ComCode%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <script src="ProposalApproveModify.js"></SCRIPT>
  <%@include file="ProposalApproveModifyInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  
  <title>问题件修改保单查询 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- 保单信息部分 -->
    <!-- <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <td  class=title>
            印刷号
          </td>
          <td  class=input>
            <input class= common name=ContNo >
            <input class= common name=PrtNo type="hidden">
          </td>  
          <td  class=title>
            管理机构
          </td>
          <td  class=input>
            <input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </td>          
          <td  class=title>
            销售渠道
          </td>
          <td  class=input>
            <input class="codeno" name=SaleChnl ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true>
          </td>  
        </tr>      
        <tr>
          <TD  class= title>
            业务员编码
          </TD>
			    <td class="input">
			      <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" ondblclick="return queryAgent();" >
          </td>
          <td  class= title>
            回复日期
          </td>
          <td class= input>
            <input class="multiDatePicker" dateFormat="short" name=MakeDate >
          </td>
          <td  class=title>
           回复状态
          </td>
          <td  class=input>
            <input class="codeno" name=State value='1' CodeData ="0|^1|全部回复^2|未回复" ondblclick="return showCodeListEx('State',[this,StateName],[0,1]);" onkeyup="return showCodeListKeyEx('State',[this,StateName],[0,1]);"><input class=codename name=StateName value='全部回复' readonly=true>
          </td>  
        </tr>
	<tr class=common>
		<td class=title>问题件类型</td>
		<td><Input class="codeno" name=BackObj readonly CodeData=""
			; ondblclick="return showCodeList('backobj',[this,BackObjName],[0,1]);"
			onkeyup="return showCodeListKey('backobj',[this,BackObjName],[0,1]);"><Input
			class=codename name=BackObjName readonly=true></td>
		<TD class=title>优先机构</TD>
		<TD class=input><Input class="codeno" name=PreCom
			CodeData="0|^a|百团机构投保单^b|非百团机构投保单"
			ondblClick="showCodeListEx('PreCom',[this,PreComName],[0,1]);"
			onkeyup="showCodeListKeyEx('PreCom',[this,PreComName],[0,1]);"><input
			class=codename name=PreComName value='' readonly=true></TD>
	</tr>

	<!--
          <td  class= title>
            印刷号码
          </td>
          <td  class= input>
            <input class= common name=PrtNo >
          </td>
-->
<!--
          <td  class= title>
            投保人姓名
          </td>
          <td  class= input>
            <input class=common name=AppntName >
          </td>          \
-->
<!--
          <td  class= title>
            录单日期/复核日期
          </td>
          <td class= input>
            <input class="coolDatePicker" dateFormat="short" name=MakeDate >
          </td>

        </tr>
        <tr class="common">
          <td  class= title8>
            管理机构
          </td>
          <td  class= input8>
            <input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </td>          
         <td  class= title>
            业务员编码
          </td>
          <td  class= input>
            <input class="codeno" name=AgentCode ondblclick="showCodeList('AgentCode',[this, AgentName], [0, 1]);" onkeyup="showCodeListKey('AgentCode', [this, AgentName], [0, 1]);"><input class=codename name=AgentName readonly=true>
          </td>
          <td  class= title>
            营业部、营业组
          </td>
          <td  class= input>
            <input class="codeno" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" name=AgentGroupName readonly=true>
          </td>  
        </tr>
-->
    <!-- </table>
    </DIV>
          <input class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">     
      		<input class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divPolGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <input CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">	    				
  	</div>
  <br>
   	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
    		</td>
    		<td class= titleImg>
    			 待问题件修改投保单
    		</td>
    	</tr>  	
    </table>
    	<Div  id= "divSearch1" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <td  class=title>
            印刷号
          </td>
          <td  class=input>
            <input class= common name=ContNo1 >
            <input class= common name=PrtNo1 type="hidden">
          </td>  
          <td  class=title>
            管理机构
          </td>
          <td  class=input>
            <input class="codeno" name=ManageCom1 ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
          </td>          
          <td  class=title>
            销售渠道
          </td>
          <td  class=input>
            <input class="codeno" name=SaleChnl1 ondblclick="showCodeList('SaleChnl',[this,SaleChnlName1],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName1],[0,1]);"><input class=codename name=SaleChnlName1 readonly=true>
          </td>  
        </tr>      
        <tr>
          <td  class= title>
            回复日期
          </td>
          <td class= input>
            <input class="multiDatePicker" dateFormat="short" name=MakeDate1 >
          </td>
          <td  class=title>
           回复状态
          </td>
          <td  class=input>
            <input class="codeno" name=State1 value='1' CodeData ="0|^1|全部回复^2|未回复" ondblclick="return showCodeListEx('State',[this,StateName1],[0,1]);" onkeyup="return showCodeListKeyEx('State',[this,StateName1],[0,1]);"><input class=codename name=StateName1 value='全部回复' readonly=true>
          </td> 
          <td  class= title>
	            问题件类型  
	          </td>
	          <td>
	          <Input class= "codeno" name = BackObj1 readonly CodeData=""; ondblclick="return showCodeList('backobj',[this,BackObj1Name],[0,1]);" onkeyup="return showCodeListKey('backobj',[this,BackObj1Name],[0,1]);"><Input class = codename name=BackObj1Name readonly = true>
	          </td>  
        </tr>  
    </table>
    </DIV>
          <input class=cssButton VALUE="查  询" TYPE=button onclick="initQuerySelf();">  
  	<Div  id= "divSelfPolGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <input CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
      <input CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
      <input CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
      <input CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">	   				
  	</div>  	
  	  	
  	<br> -->
    	
    	<!--input VALUE="问题件查询" class= cssButton TYPE=button onclick="QuestQuery();"--> 
    	<!--input class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
    	<div id="IssuePool"></div> 
    
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
       <input type=hidden id="MissionID" name="MissionID" >
    	 <input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <input type=hidden id="ActivityID" name="ActivityID" >
    	 <input type=hidden id="AgentCode" name="AgentCode" value = "" >	
    	 <INPUT type=hidden id="AgentName" name= AgentName value= "">
    	 <input type=hidden id="ManageCom" name="ManageCom" >	
    	 <input type=hidden id="AgentGroup" name=AgentGroup>		
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
