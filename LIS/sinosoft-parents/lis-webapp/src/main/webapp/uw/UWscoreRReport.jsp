<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：UWscoreRReport.jsp
//程序功能：核保生调评分
//创建人  ：ln
//创建日期：2008-10-24
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%	
	String tContNo = "";
	tContNo =  request.getParameter("ContNo");
	String tPrtSeq =  request.getParameter("PrtSeq");
	String tType = ""; //type=2为查询 type=1为录入
	tType =  request.getParameter("Type");
	
	GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWscoreRReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>核保生调评分</title>
  <%@include file="UWscoreRReportInit.jsp"%>
<script language="javascript">
  var tContNo ="<%=tContNo%>";
  var tPrtSeq ="<%=tPrtSeq%>";
  var tType ="<%=tType%>";
  
  	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>  
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWscoreRReportChk.jsp">
  <div class="maxbox1">
  <table  class=common border=0 align=center>
      <tr>
       <td class=title5>印刷号</td>
       <TD  class=input5>
         <Input class= "readonly wid" readonly id="PrtNo" name=PrtNo >            
       </TD> 	    
       <TD class = title5> 所属机构 </TD>
       <TD  class=input5><Input class="readonly wid" readonly id="ManageCom" name=ManageCom ></TD>      
     </tr>    
	      <tr>
	      <td class=title5>生调员工号</td>
	       <TD  class=input5>
	         <Input class= "readonly wid" readonly id="CustomerNo" name=CustomerNo >            
	       </TD>	    
	       <TD class = title5> 生调员姓名 </TD>
	       <TD  class=input5><Input class="readonly wid" readonly id="Name" name=Name ></TD>     
	     </tr>    
	  </table>
	  </div>   
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSubtract);">
    		</td>
    		<td class= titleImg>
    			 扣分项目
    		</td>
      </tr>
  </table>
  <Div  id= "divSubtract" class="maxbox" style= "display: ''" >
     <table  class=common border=0 align=center>
	      <tr>
	      <td class=title>合计扣分</td>
	      <TD  class=input>
	         <Input class= common id="SScore" name=SScore ondblclick="calcuSScore();">            
	      </TD>	  
	      <TD  class= title TYPE=hidden></TD>
			<TD  class=input TYPE=hidden><Input class="common" TYPE=hidden></TD>  
			</tr>   
	  </table>
      <table  class= common>
       		<tr> 
       		    <td class= common > <input type=checkbox id="Subtraction1" name=Subtraction1 value="1">在5个工作日内完成，超过规定时间的</td>
       		    <td class= common > <Input class="codeno" id="SScore1" name=SScore1 ><Input class="codename" id="unit1" name=unit1 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction2" name=Subtraction2 value="1">有明显异常情况而未发现，客户在短期内出险的</td>
				<td class= common > <Input class="codeno" id="SScore2" name=SScore2 ><Input class="codename" id="unit2" name=unit2 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction3" name=Subtraction3 value="1">对关键项未核实导致需做二次生存调查的</td>
       			<td class= common > <Input class="codeno" id="SScore3" name=SScore3 ><Input class="codename" id="unit3" name=unit3 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction4" name=Subtraction4 value="1">系统内回复人与实际调查人不一致的</td>
       			<td class= common > <Input class="codeno" id="SScore4" name=SScore4 ><Input class="codename" id="unit4" name=unit4 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction5" name=Subtraction5 value="1">资料收集不齐或不符合要求的</td>
				<td class= common > <Input class="codeno" id="SScore5" name=SScore5 ><Input class="codename" id="unit5" name=unit5 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction6" name=Subtraction6 value="1">调查事项未核实或核实不清的</td>
				<td class= common > <Input class="codeno" id="SScore6" name=SScore6 ><Input class="codename" id="unit6" name=unit6 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction7" name=Subtraction7 value="1">生调报告基本内容不完整的</td>
				<td class= common > <Input class="codeno" id="SScore7" name=SScore7 ><Input class="codename" id="unit7" name=unit7 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction8" name=Subtraction8 value="1">其他扣分项</td>
				<td class= common > <Input class="codeno" id="SScore8" name=SScore8 ><Input class="codename" id="unit8" name=unit8 readonly></td>
       		</tr>
      </table>
  </Div>   
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAdd);">
    		</td>
    		<td class= titleImg>
    			 加分项目
    		</td>
      </tr>
  </table>
  <Div  id= "divAdd" class="maxbox" style= "display: ''" >
     <table  class=common border=0 align=center>
	      <tr>
	      <td class=title>合计加分</td>
	      <TD  class=input>
	         <Input class= common id="AScore" name=AScore ondblclick="calcuAScore();">            
	      </TD>	  
	      <TD  class= title TYPE=hidden></TD>
			<TD  class=input TYPE=hidden><Input class="common" TYPE=hidden></TD>     
		</tr>
	  </table>
      <table  class= common>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition1" name=Addition1 value="1">通过调查发现异常情况，核保师做延期、拒保处理的</td>
       			<td class= common > <Input class="codeno" id="AScore1" name=AScore1 ><Input class="codename" id="unit9" name=unit9 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition2" name=Addition2 value="1">通过调查发现异常情况，核保师做加费或限额处理的</td>
       			<td class= common > <Input class="codeno" id="AScore2" name=AScore2 ><Input class="codename" id="unit10" name=unit10 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition3" name=Addition3 value="1">查实业务员有误导客户行为</td>
       			<td class= common > <Input class="codeno" id="AScore3" name=AScore3 ><Input class="codename" id="unit11" name=unit11 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition4" name=Addition4 value="1">查实客户有告知不实的情况</td>
       			<td class= common > <Input class="codeno" id="AScore4" name=AScore4 ><Input class="codename" id="unit12" name=unit12 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition5" name=Addition5 value="1">其他有参考价值的资料或发现</td>
       			<td class= common > <Input class="codeno" id="AScore5" name=AScore5 ><Input class="codename" id="unit13" name=unit13 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition6" name=Addition6 value="1">其他加分项</td>
       			<td class= common > <Input class="codeno" id="AScore6" name=AScore6 ><Input class="codename" id="unit14" name=unit14 readonly></td>
       		</tr>
      </table>
  </Div> 
     
  <table  class=common border=0 align=center>
      <tr>
       <td class=title5>得分</td>
       <TD  class=input5>
         <Input class= "common wid" id="Score" name=Score ondblclick="calcuScore();">            
       </TD> 	    
       <TD class = title5> 评估结论 </TD>
       <TD  class= input5><Input class="code wid" id="Conclusion" name=Conclusion readonly ><!--  ondblclick="showCodeList('RReportScoreCon', [this],[1]);" onkeyup="return showCodeListKey('RReportScoreCon', [this],[1]);"--></TD>      
     </tr>    
	      <tr>
	      <td class=title5>评估人</td>
	       <TD  class=input5>
	         <Input class= "readonly wid" readonly id="AssessOperator" name=AssessOperator >            
	       </TD>	    
	       <TD class = title5> 评估日期 </TD>
	       <TD  class=input5><Input class= "common wid" readonly id="AssessDay" name=AssessDay ></TD>     
	     </tr>    
	  </table> 
  <p> 
    <!--读取信息  校验分数是否改变-->
    <input type= "hidden" id="ScoreH" name= "ScoreH">
    <input type= "hidden" id="AScoreH" name= "AScoreH">
    <input type= "hidden" id="SScoreH" name= "SScoreH">
    <input type= "hidden" id="AssessTimeH" name= "AssessTimeH">
    <input type= "hidden" id="ContNoH" name= "ContNoH">
  </p>
  <input type= "button" id="Sure" name="Sure" class= cssButton  value=" 保  存 " onClick="submitForm();">
  <input type= "button" id="Print" name="Print" class= cssButton  value=" 打  印 " onClick="printResult();">
  <input class= cssButton type= "button" value=" 返  回 " class= Common onClick="top.close();">
  
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/> 
</body>
</html>
