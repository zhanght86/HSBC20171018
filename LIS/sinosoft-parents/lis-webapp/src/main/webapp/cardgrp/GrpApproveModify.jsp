<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String tContNo = "";
	try
	{
		tContNo = request.getParameter( "ContNo" );
		//Ĭ�������Ϊ����Ͷ����
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "";
	}
	
	catch( Exception e1 )
	{
		tContNo = "";
			loggerDebug("GrpApproveModify","---contno:"+tContNo);

	}
	String tGrpPolNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("GrpApproveModify","---contno:"+tContNo);
%>
<script>
	
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpApproveModify.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpApproveModifyInit.jsp"%>
  <title>����Ͷ������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    
    <!-- <table  class= common align=center>
    </table>	 -->				
  <table class= common border=0 width=100%>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
      </td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''">
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>�������κ�</TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNo  id="GrpProposalNo">
          </TD>
          <TD class=title5>��������</TD>
				  <TD class=input5>
					  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="��������|code:comcode&notnull" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				  </TD>
            <Input type = "hidden" class= "common wid" name=PrtNo id="PrtNo">
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <a href="javascript:void(0)" class=button id="riskbutton" onclick="ApplyUW();">��  ��</a>
  <!-- <INPUT VALUE="��  ѯ" class=cssButton  TYPE=button onclick="easyQueryClick();">  
  <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();"> -->

    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 ����������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div align=center>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">					
      </div>	
  	</div>  	
  	
  <br>
    <div class="maxbox1">
      <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
            �������κ�
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNoSelf id="GrpProposalNoSelf">
          </TD>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageComSelf id="ManageComSelf" onclick="return showCodeList('station',[this,ManageComSelfName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComSelfName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name=ManageComSelfName id="ManageComSelfName" readonly=true>
          </TD>
        </TR>
    </table> 
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">��  ѯ</a> 
    <!-- <INPUT VALUE="��  ѯ" class=cssButton  TYPE=button onclick="easyQueryClickSelf();">   -->
 	
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div align=center>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">				
      </div>		
  	</div>
  	  	
  	<P>
  	<a href="javascript:void(0)" class=button onclick="showNotePad();">���±��鿴</a>
  	<!-- <INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">    -->
  	</P>
  	
          <INPUT  type= "hidden" class= Common name= MissionID  id="MissionID" value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id="SubMissionID"  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID id="ActivityID"  value= "">     	
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>