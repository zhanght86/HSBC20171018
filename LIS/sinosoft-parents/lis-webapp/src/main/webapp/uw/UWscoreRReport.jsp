<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�UWscoreRReport.jsp
//�����ܣ��˱���������
//������  ��ln
//�������ڣ�2008-10-24
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%	
	String tContNo = "";
	tContNo =  request.getParameter("ContNo");
	String tPrtSeq =  request.getParameter("PrtSeq");
	String tType = ""; //type=2Ϊ��ѯ type=1Ϊ¼��
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
  <title>�˱���������</title>
  <%@include file="UWscoreRReportInit.jsp"%>
<script language="javascript">
  var tContNo ="<%=tContNo%>";
  var tPrtSeq ="<%=tPrtSeq%>";
  var tType ="<%=tType%>";
  
  	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>  
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWscoreRReportChk.jsp">
  <div class="maxbox1">
  <table  class=common border=0 align=center>
      <tr>
       <td class=title5>ӡˢ��</td>
       <TD  class=input5>
         <Input class= "readonly wid" readonly id="PrtNo" name=PrtNo >            
       </TD> 	    
       <TD class = title5> �������� </TD>
       <TD  class=input5><Input class="readonly wid" readonly id="ManageCom" name=ManageCom ></TD>      
     </tr>    
	      <tr>
	      <td class=title5>����Ա����</td>
	       <TD  class=input5>
	         <Input class= "readonly wid" readonly id="CustomerNo" name=CustomerNo >            
	       </TD>	    
	       <TD class = title5> ����Ա���� </TD>
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
    			 �۷���Ŀ
    		</td>
      </tr>
  </table>
  <Div  id= "divSubtract" class="maxbox" style= "display: ''" >
     <table  class=common border=0 align=center>
	      <tr>
	      <td class=title>�ϼƿ۷�</td>
	      <TD  class=input>
	         <Input class= common id="SScore" name=SScore ondblclick="calcuSScore();">            
	      </TD>	  
	      <TD  class= title TYPE=hidden></TD>
			<TD  class=input TYPE=hidden><Input class="common" TYPE=hidden></TD>  
			</tr>   
	  </table>
      <table  class= common>
       		<tr> 
       		    <td class= common > <input type=checkbox id="Subtraction1" name=Subtraction1 value="1">��5������������ɣ������涨ʱ���</td>
       		    <td class= common > <Input class="codeno" id="SScore1" name=SScore1 ><Input class="codename" id="unit1" name=unit1 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction2" name=Subtraction2 value="1">�������쳣�����δ���֣��ͻ��ڶ����ڳ��յ�</td>
				<td class= common > <Input class="codeno" id="SScore2" name=SScore2 ><Input class="codename" id="unit2" name=unit2 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction3" name=Subtraction3 value="1">�Թؼ���δ��ʵ��������������������</td>
       			<td class= common > <Input class="codeno" id="SScore3" name=SScore3 ><Input class="codename" id="unit3" name=unit3 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction4" name=Subtraction4 value="1">ϵͳ�ڻظ�����ʵ�ʵ����˲�һ�µ�</td>
       			<td class= common > <Input class="codeno" id="SScore4" name=SScore4 ><Input class="codename" id="unit4" name=unit4 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction5" name=Subtraction5 value="1">�����ռ�����򲻷���Ҫ���</td>
				<td class= common > <Input class="codeno" id="SScore5" name=SScore5 ><Input class="codename" id="unit5" name=unit5 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction6" name=Subtraction6 value="1">��������δ��ʵ���ʵ�����</td>
				<td class= common > <Input class="codeno" id="SScore6" name=SScore6 ><Input class="codename" id="unit6" name=unit6 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction7" name=Subtraction7 value="1">��������������ݲ�������</td>
				<td class= common > <Input class="codeno" id="SScore7" name=SScore7 ><Input class="codename" id="unit7" name=unit7 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Subtraction8" name=Subtraction8 value="1">�����۷���</td>
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
    			 �ӷ���Ŀ
    		</td>
      </tr>
  </table>
  <Div  id= "divAdd" class="maxbox" style= "display: ''" >
     <table  class=common border=0 align=center>
	      <tr>
	      <td class=title>�ϼƼӷ�</td>
	      <TD  class=input>
	         <Input class= common id="AScore" name=AScore ondblclick="calcuAScore();">            
	      </TD>	  
	      <TD  class= title TYPE=hidden></TD>
			<TD  class=input TYPE=hidden><Input class="common" TYPE=hidden></TD>     
		</tr>
	  </table>
      <table  class= common>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition1" name=Addition1 value="1">ͨ�����鷢���쳣������˱�ʦ�����ڡ��ܱ������</td>
       			<td class= common > <Input class="codeno" id="AScore1" name=AScore1 ><Input class="codename" id="unit9" name=unit9 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition2" name=Addition2 value="1">ͨ�����鷢���쳣������˱�ʦ���ӷѻ��޶���</td>
       			<td class= common > <Input class="codeno" id="AScore2" name=AScore2 ><Input class="codename" id="unit10" name=unit10 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition3" name=Addition3 value="1">��ʵҵ��Ա���󵼿ͻ���Ϊ</td>
       			<td class= common > <Input class="codeno" id="AScore3" name=AScore3 ><Input class="codename" id="unit11" name=unit11 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition4" name=Addition4 value="1">��ʵ�ͻ��и�֪��ʵ�����</td>
       			<td class= common > <Input class="codeno" id="AScore4" name=AScore4 ><Input class="codename" id="unit12" name=unit12 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition5" name=Addition5 value="1">�����вο���ֵ�����ϻ���</td>
       			<td class= common > <Input class="codeno" id="AScore5" name=AScore5 ><Input class="codename" id="unit13" name=unit13 readonly></td>
       		</tr>
       		<tr> 
       			<td class= common > <input type=checkbox id="Addition6" name=Addition6 value="1">�����ӷ���</td>
       			<td class= common > <Input class="codeno" id="AScore6" name=AScore6 ><Input class="codename" id="unit14" name=unit14 readonly></td>
       		</tr>
      </table>
  </Div> 
     
  <table  class=common border=0 align=center>
      <tr>
       <td class=title5>�÷�</td>
       <TD  class=input5>
         <Input class= "common wid" id="Score" name=Score ondblclick="calcuScore();">            
       </TD> 	    
       <TD class = title5> �������� </TD>
       <TD  class= input5><Input class="code wid" id="Conclusion" name=Conclusion readonly ><!--  ondblclick="showCodeList('RReportScoreCon', [this],[1]);" onkeyup="return showCodeListKey('RReportScoreCon', [this],[1]);"--></TD>      
     </tr>    
	      <tr>
	      <td class=title5>������</td>
	       <TD  class=input5>
	         <Input class= "readonly wid" readonly id="AssessOperator" name=AssessOperator >            
	       </TD>	    
	       <TD class = title5> �������� </TD>
	       <TD  class=input5><Input class= "common wid" readonly id="AssessDay" name=AssessDay ></TD>     
	     </tr>    
	  </table> 
  <p> 
    <!--��ȡ��Ϣ  У������Ƿ�ı�-->
    <input type= "hidden" id="ScoreH" name= "ScoreH">
    <input type= "hidden" id="AScoreH" name= "AScoreH">
    <input type= "hidden" id="SScoreH" name= "SScoreH">
    <input type= "hidden" id="AssessTimeH" name= "AssessTimeH">
    <input type= "hidden" id="ContNoH" name= "ContNoH">
  </p>
  <input type= "button" id="Sure" name="Sure" class= cssButton  value=" ��  �� " onClick="submitForm();">
  <input type= "button" id="Print" name="Print" class= cssButton  value=" ��  ӡ " onClick="printResult();">
  <input class= cssButton type= "button" value=" ��  �� " class= Common onClick="top.close();">
  
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/> 
</body>
</html>
