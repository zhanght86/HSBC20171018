<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpec.jsp
//程序功能：保全人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
   GlobalInput tGlobalInput = new GlobalInput();
   tGlobalInput=(GlobalInput)session.getValue("GI");
   String tOperator = tGlobalInput.Operator;
   loggerDebug("UWManuAdd"," tOperator:"+ tOperator);
   String tQueryFlag = request.getParameter("QueryFlag");
   if(tQueryFlag==null||tQueryFlag.equals("")){
     tQueryFlag="0";
   }

 %>
 <script language="JavaScript" type="">
	var tOperator = "<%=tOperator%>";
	var tQueryFlag = "<%=tQueryFlag%>";
 </script>
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" type="" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js" type=""></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js" type=""></SCRIPT>
  <SCRIPT src="UWManuAdd.js" type=""></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 加费承保 </title>
  <%@include file="UWManuAddInit.jsp"%>

</head>
<body  onload="initForm('', '<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>');" >


  <form method=post id="fm" name=fm target="fraSubmit" action="" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);" alt="">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
      </table>
        <Div  id= "divLCPol2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span>
  			  	</td>
  			  </tr>
    	  </table>
    	<tr class=common align=center>
        	
    	</tr>
    	<center>
    	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="getFirstPage();">
        	<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="getPreviousPage();">
        	<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="getNextPage();">
        	<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="getLastPage();">
        	</center>
        </Div>

        <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       加费信息
    	  </td>
    	</tr>
        </table>
      	<Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span>
  			    	</td>
  			  </tr>
      	</table>
      	</div>


     <table class="common" style= "display: none" >
    	<TR  class= common>
          <TD  class= title>
            加费原因
          </TD>
          <tr></tr>

      <TD  class= input> <textarea name="AddReason" id=AddReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
      <INPUT type="hidden" id="PolNo2" name="PolNo2" value= ""> <!--保全加费所针对的保单-->
      <INPUT type= "hidden" id="PolNo" name="PolNo" value= ""> <!--保全项目所针对的保单,由前一页面传来-->
      <INPUT type= "hidden" id="ContNo" name="ContNo" value= "">
      <INPUT type= "hidden" id="MissionID" name="MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name="SubMissionID" value= "">
      <INPUT type= "hidden" id="InsuredNo" name="InsuredNo" value="">

      <INPUT type= "hidden" id="RiskCode" name="RiskCode" value= "">
      <INPUT type = "hidden" id="DutyCode" name = "DutyCode" value = ""><!---->
      <INPUT type= "hidden" id="AddFeeObject" name="AddFeeObject" value= ""><!--加费对象-->
      <INPUT type= "hidden" id="AddFeeType" name="AddFeeType" value=""><!--加费类型-->
      <INPUT type= "hidden" id="SuppRiskScore" name="SuppRiskScore" value= "">
      <INPUT type= "hidden" id="SecondScore" name="SecondScore" value= "">



      <INPUT type= "button" id="button1"  name="sure" value="确  认" class= cssButton onclick="submitForm()">
      <INPUT type= "button" id="button2"  name="back" value="返  回" class= cssButton onclick="top.close();">
      <INPUT type= "button" id="button3"  name="delete" value="删  除" class= cssButton onclick="deleteData()">
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
