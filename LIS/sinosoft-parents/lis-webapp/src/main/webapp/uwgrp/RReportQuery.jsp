<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�RReportQuery.jsp
//�����ܣ�������鱨���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  
  tPrtSeq = request.getParameter("PrtSeq");
  tFlag = request.getParameter("Flag");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");  
  
%>            
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var customers = "";                   //������ѡ
  var Flag = "<%=tFlag%>";
 
</script>
<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="RReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RReportQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtSeq%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm target="fraSubmit">
  	
  	<div id = "divMainHealth" style = "display : 'none'">
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
    <div id ="divOperation" style = "display : 'none'">
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=common><Input  class= Common name= 'Operator' readonly></td>
          <td class=title>����ʱ��</td><td class=common><Input  class= Common name= 'MakeDate' readonly></td>
       </tr>
       <tr  class= common>
          <td class=title>�ظ�ʱ��</td><td class=common><Input  class= Common name= 'ReplyDate' readonly></td>
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
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    		<td class= titleImg>	 ������Ŀ</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanRReportGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	<table class = common>
    		<tr class = common>
    			<td class=title>��������</td><td class=common><Input  class= Common name= 'RReportFee' ></td>
    		</tr>
      </table>
   
    <Div  id= "divUWDis" style= "display: 'none'">
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
           <TD  class= common> ����������Ŀ </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Contente" cols="120" rows="3" class="common" >
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
             <textarea name="ReplyContente" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNo" value= "">
    <input type= "hidden" name= "Type" value="">
    <input type= "hidden" name= "PrtSeq" value="">
    <input type= "hidden" name= "MissionID" value="">
    <input type= "hidden" name= "SubMissionID" value="">
    
  </p>
   <div id = "divRReportButton" style = "display:;">
  	<input value="�����������" class=cssButton type=button onclick="saveRReport();" > 
  </Div>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
