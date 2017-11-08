<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealth.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="java.util.*"%>
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  String tPrtSeq = "";
  String tCustomerNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tPrtSeq = request.getParameter("PrtSeq");
  tCustomerNo = request.getParameter("CustomerNo");
  tFlag = request.getParameter("Flag");
 %>                            



<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./UWManuHealthQ.js"></SCRIPT>





  <LINK href="../common/css/Project.css" rel=stylesheet type='text/css'>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type='text/css'>
  <%@include file="UWManuHealthQInit.jsp"%>
  <title> 承保体检资料录入 </title>

</head>
<script language="JavaScript">
var tFlag = "<%=tFlag%>";


function initPage()
{

       initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');
       
}

function operatorQuery()
{
		alert("in!");
		var prtSeq = fm.PrtSeq.value;
		var CustomerNo = fm.CustomerNo.value;
		var arrReturn;
		var strSQL = "select Operator,MakeDate,ModifyTime from LCPENotice where PrtSeq = '"+prtSeq+"' and CustomerNo='"+CustomerNo+"'";
		alert(strSQL);
//		try{
//			arrReturn =easyExecSql(strSQL);
//		}
//		catch(ex)
//		{
//			alert( "查询发送人失败！");		
//		}
//		
//		fm.all.('Operator').value = arrReturn[0];
//		fm.all.('MakeDate').value = arrReturn[1];
		return;	
}
</script>

<body  onload="initPage();" >
  <form method=post name=fm target="fraSubmit" action= "./UWManuHealthChk.jsp">
    <!-- 非列表 -->
    
    
    <div id = "divMainHealth" style = "display : 'none'">
       <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainUWSpec1);"></td>
    		<td class= titleImg>	 体检履历</td>
    	</tr>
    </table>
    
    <Div  id= "divMainUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanMainHealthGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
      </div>
    </div>
    <div id ="divOperation" style = "display : 'none'">
    <table class=common>
       <tr  class= common>
          <td class=title>发送人</td><td class=common><Input  class= Common name= 'Operator' readonly></td>
          <td class=title>发送时间</td><td class=common><Input  class= Common name= 'MakeDate' readonly></td>
       </tr>
        <tr  class= common>
          <td class=title>回复时间</td><td class=common><Input  class= Common name= 'ReplyDate' readonly></td>
       </tr>
    </table>
    </div>
    <table class=common>
        <TR  class= title>


          <Input type= "hidden" class= Common name= 'ContNo' value= "">
          <Input type= "hidden" class= Common name= 'PrtNo' value= "">
          <INPUT  type= "hidden" class= Common name= 'MissionID' value= "">
          <INPUT  type= "hidden" class= Common name= 'SubMissionID' value= "">
          <INPUT  type= "hidden" class= Common name= 'PrtSeq' value= "">
          <INPUT  type= "hidden" class= Common name= 'CustomerNo' value= "">
          
          <TD  class= title>  体检医院  </TD>
          <TD  class= input> <Input class="common" name='PEAddress' > </TD>
          <TD  class= title>  体检医师  </TD>
          <TD  class= input> <Input class="common" name='PEDoctor' > </TD>
        </TR>
        <TR  class= title>
          <TD  class= title>  体检时间  </TD>
          <!--<TD  class= input> <Input class="common" name='PEDate' > </TD> -->
          <TD  class= input>
            <Input class="coolDatePicker"  dateFormat="short" name='PEDate' verify="体检时间|date" >
          </TD>
          <TD  class= title>  复查时间  </TD>
          <!--<TD  class= input> <Input class="common" name='REPEDate' > </TD>-->
          <TD  class= input>
            <Input class="coolDatePicker"  dateFormat="short" name='REPEDate' verify="复查时间|date" >
          </TD>
        </TR>
        <TR  class= title>
          <TD  class= title>  阳性标记 </TD>
          <TD nowrap class= input>
            <Input class="codeno" name=MasculineFlag verify="阳性标记|NOTNUlL&CODE:yesno"  verifyorder="1" ondblclick="return showCodeList('yesno',[this,MasculineName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,MasculineName],[0,1]);"><input class=codename name=MasculineName readonly=true elementtype=nacessary>
          </TD>
        	
        </TR>
    </table>

     

    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	 体检项目</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanHealthGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
    <!--	
      <INPUT CLASS=cssButton VALUE="首  页" class= cssButton type=hidden onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="上一页" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="下一页" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="尾  页" class= cssButton TYPE=button onclick="turnPage.lastPage();">
    -->  
      <Div  id = "divPage" align=center style = "display: 'none' ">
        <INPUT VALUE="首页" class= cssButton TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 
        <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="尾页" class= cssButton TYPE=button onclick="turnPage.lastPage();">
    </Div>

    </div>

    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   疾病结果</td>
    	</tr>
    </table>

    <Div  id= "divUWDis" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
      </div>

    	<table class=common>
         <TR  class= common>
           <TD  class= common> 其他体检信息 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Note" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
       <div id = "divHealthButton" style = "display:;">
	<input value="体检结果保存" class=cssButton type=button onclick="saveDisDesb();" >
</div>
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
