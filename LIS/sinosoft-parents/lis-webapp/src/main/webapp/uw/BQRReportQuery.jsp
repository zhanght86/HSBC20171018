<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<%
//�������ƣ�RReportQuery.jsp
//�����ܣ�������鱨���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-24   ����ԭ��/���ݣ������º˱�Ҫ������޸�
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";

  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<%
  String tFlag = "";
  String tPrtSeq = "";
  String tMissionID = "";
  String tSubMissionID = ""; 
 

  tContNo = request.getParameter("ContNo");
  String tEdorNo = request.getParameter("EdorNo");
  
  tPrtSeq = request.getParameter("PrtSeq");
  tFlag = request.getParameter("Flag");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");  
  String tQueryFlag = request.getParameter("QueryFlag"); 
  
%>            
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var customers = "";                   //������ѡ
  var Flag = "<%=tFlag%>";
  var QueryFlag = "<%=tQueryFlag%>";
  
 	var tContNo = "<%=tContNo%>";
    var tEdorNo = "<%=tEdorNo%>";
</script>
<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BQRReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQRReportQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tEdorNo%>','<%=tPrtSeq%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post id="fm" name=fm target="fraSubmit">
  	
  	<div id = "divMainHealth" style = "display : none">
  	 <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 ������鱨�����ݣ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    </div>
  </div>
    <div id ="divOperation" class="maxbox1" style = "display : none">
    <table class=common>
       <tr  class= common>
          <td class=title5>���������</td>
		  <td class=input5><Input  class=wid id="Operator" name= 'Operator' readonly></td>
          <td class=title5>����ʱ��</td>
		  <td class=input5><Input  class=wid id="MakeDate" name= 'MakeDate' readonly></td>
       </tr>
       <tr  class= common>
          <td class=title>�ظ�ʱ��</td>
		  <td class=input5><Input  class=wid id="ReplyDate" name= 'ReplyDate' readonly></td>
		  <td class=title5></td>
		  <td class=input5></td>
       </tr>
    </table>
    </div>
   
      
  <!--table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> �������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> �ظ����� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table-->
		<Div id= "divUWSpec2" style= "display: none">
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg>	 ������Ŀ</td>                            
    	</tr>	
    </table>
    <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanRReportGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
  </div>
    <Div  id= "divUWSpec1" class="maxbox1" style= "display: ''">

    	<table class = common>
    		<tr class = common>
    			<td class=title>��������</td>
    			<td class="input"><Input  class= "code wid" id="RReportFee" name= 'RReportFee' ></td>
    			<td class=title></td>
         		<td class= input></td>
         		<td class=title></td>
         		<td class= input></td>
    		</tr>
      </table>
   </Div>
    <Div  id= "divUWDis" style= "display: none">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanRReportResultGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
        <table class=common>
         <TR  class= common> 
           <TD  class= common> ����˵�� </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Contente" id=Contente cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
       <table class=common>
         <TR  class= common> 
           <TD  class= common> �������� </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="ReplyContente" id=ReplyContente cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value= "">
    <input type= "hidden" id="Type" name= "Type" value="">
    <input type= "hidden" id="PrtSeq" name= "PrtSeq" value="">
    <input type= "hidden" id="MissionID" name= "MissionID" value="">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
    
  </p>
   <div id = "divRReportButton" style = "display:none">
  	<input value="�����������" class=cssButton type=button onclick="saveRReport();" >   	
  </Div>
  <div id = "divRReportButton" style = "display:none">
  	<input value="�������ֱ�" class=cssButton type=button id="Assess" name=Assess onclick="soreRReport();" >   	
  </Div>
  <input class= cssButton type= "button" value=" ��  �� "  onClick="top.close();">
  <br ><br ><br ><br ><br >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
