<%
/***************************************************************
 * <p>ProName��LLClaimPreinvestInput.jsp</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = request.getParameter("ManageCom");
	String tOperator = tGI.Operator;
	String mCurrentDate = PubFun.getCurrentDate();
  String tGrpRgtNo = request.getParameter("GrpRgtNo");  //�����ⰸ��
  String tRgtNo = request.getParameter("RgtNo"); 
  String tCustomerNo = request.getParameter("custNo"); //�����˱���
  String tSurveyType = request.getParameter("surveyType"); //��������
  String tInitPhase = request.getParameter("initPhase"); //��������״̬
  String tAppntNo =request.getParameter("AppntNo"); //Ͷ���˱���
  String tMode =request.getParameter("Mode"); //Ͷ���˱���
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
	var tCurrentDate = "<%=mCurrentDate%>"; 	//ϵͳʱ��
	var tGrpRgtNo ="<%=tGrpRgtNo%>";
	var tRgtNo ="<%=tRgtNo%>";
	var tCustomerNo ="<%=tCustomerNo%>";
	var tSurveyType ="<%=tSurveyType%>";
	var tInitPhase ="<%=tInitPhase%>";
	var tAppntNo ="<%=tAppntNo%>";
	var tMode = "<%=tMode%>";
</script>
<html>
<head>
	<title>�������</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimPreinvestInput.js"></script>
	<%@include file="./LLClaimPreinvestInit.jsp"%>
</head>

<body onload="initForm(); initElementtype();">
<form name=fm method=post action="./LLClaimPreinvestSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInvestList);">
			</td>
			<td class=titleImgtop>�ѷ��������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInvestList" style="display: ''">
		<input class=cssButton name=queryInqInfo value="������ϸ��ѯ" type=button onclick="showInvest();">
		<input class=cssButton name=inqTuenBack value="�����ջ�" type=button onclick="returnInvest();">
		<font color="#FF0000">***���δ����ĵ��飬���Խ��С������ջء�</font>					
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanInvestListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divOnInvest);">
			</td>
			<td class=titleImg>�����������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divOnInvest" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOnInvestGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	</div>
  <!--������Ϣ-->
		<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInvesstInfo);">
			</td>
			<td class=titleImg>���������Ϣ</td>
		</tr>
		</table>
  
  <div id="divInvesstInfo" class=maxbox style="display: ''">
	  <input class=cssButton name=saveButton value="��������" type=button onclick="saveSurvey();">
		<input class=cssButton name=recoverButton value="�޸ĵ���" type=button onclick="modifySurvey();">
		<input class=cssButton name=deleteButton value="ɾ������" type=button onclick="deleteSurvey();">
		<input class=cssButton name=doCloseButton value="�رյ���" type=button onclick="closeSurvey();">
		<input class=cssButton name=doButton value="�������" type=button onclick="startSurvey();">
		<input class=cssButton name=goBackButton value="��  ��" type=button onclick="returnBack();">
		<input class=cssButton name=turnback value="��  ��" type=button onclick="goBack();">
		<input class=cssButton name=attachAdd value="��������" type=button onclick="addAttach();">
	  <table class=common>
	  <tr class=common>
			<td class=title>����������</td>
			<td class=input><Input class="wid readonly" readonly name=CustomerName id=CustomerName></td>
			<td class=title>���鷢������</td>
		  <td class=input><input class="coolDatePicker" dateFormat="short"  verify="������������|notnull&DATE verifyorder="1""   name=ApplyDate elementtype=nacessary onClick="laydate({elem: '#ApplyDate'});" id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
			 
<!-- 			<td class=Input ><Input class=coolDatePicker dateFormat="short" name=ApplyDate  verify="������������|NOTNULL" elementtype=nacessary  maxlength="10" onClick="laydate({elem: '#ApplyDate'});" id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
 -->			<td class=title>��������</td>
			<td class=input><Input class=codeno  name=SurveyType  readonly ><input class=codename name="SurveyTypeName" readonly></TD>      
		</tr>    
	  <tr class=common>
	    <td class=title> ����ԭ�� </td>
	    <td class=input><Input class=codeno  name=InqReason verify="����ԭ��|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1],null,null,null,1);"><input class=codename name="InqReasonName" readonly=true elementtype=nacessary></TD>       
	    <td class=title> ����ر�ԭ�� </td>
	    <td class=input><Input class=codeno  name=InqCloseReason ondblclick="return showCodeList('inqcloseresason',[this,InqCloseReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('inqcloseresason',[this,InqCloseReasonName],[0,1],null,null,null,1);"><input class=codename name="InqCloseReasonName" readonly=true ></TD>              
	    <td class=title></td>
	    <td class=input></TD>
	  </tr>
	  <tr class=common>
	    <td class=title> ����Ŀ�� <font color=red>*</font></td>
	    <td class=input colspan="5"><textarea name=InqPurpose id=InqPurpose cols=60 rows=5 verify="����Ŀ��|NOTNULL&LEN<=1000" maxlength="1000" class=common></textarea></TD>       
	   </tr>
	  <tr class=common>
	    <td class=title> ����ƻ� <font color=red>*</font> </td>
	    <td class=input colspan="5"><textarea name=InqPlan cols=60 rows=5 verify="����ƻ�|NOTNULL" maxlength="1000" class=common></textarea></TD>       
	  </tr>
	   <tr class=common>
		    <td class=title> ��ע��Ϣ </td>
		    <td class=input colspan="5"><textarea name=Remark cols=60 rows=3 maxlength="1000" class=common></textarea></TD>       
		  </tr> 
	  </table>	  	
  </div> 
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
	<Input type=hidden  name=CustomerNo>
	<Input type=hidden  name=InitPhase>
	<Input type=hidden  name=ManageCom>
	<Input type=hidden  name=LocFlag><!--���ر�ʶ-->
	<Input type=hidden  name=InqNo><!--�������-->
  <Input type=hidden  name=BatNO><!--���κ�-->
  <Input type=hidden	name=RgtNo>
  <Input type=hidden	name=GrpRgtNo>
  <Input type=hidden	name=InqFlowState>
  <Input type=hidden	name=InitiationType>
  <input type=hidden  name=AppntNo>
</form>
  <!-- ���鸽��form-->
	<form action="LLSurveyAttachmentSave.jsp" style="margin-bottom:0px;margin-top: 0px" method=post name=fm2 id=fm2 target="fraSubmit" ENCtype="multipart/form-data">
	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSurveyAttachmentGrid);">
				</td>
				<td class=titleImg>���鸽����Ϣ�б�</td>
			</tr>
		</table>
		<div id="divSurveyAttachmentGrid" style="display: ''" align=left>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSurveyAttachmentGrid" ></span> 
					</td>
				</tr>
			</table>
		</div>
		<div  id="divSurveyAttachmentPage" style="display: none">
			<center>
				<Input class=cssButton value="��  ҳ" type=button onclick="turnPage4.firstPage();">
				<Input class=cssButton value="��һҳ" type=button onclick="turnPage4.previousPage();">
				<Input class=cssButton value="��һҳ" type=button onclick="turnPage4.nextPage();">
				<Input class=cssButton value="β  ҳ" type=button onclick="turnPage4.lastPage();">
			</center>	
		</div>
  		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFJ);">
				</td>
				<td class=titleImg>���鸽����Ϣ</td>
			</tr>
		</table>
		<div id=divFJ>
			<Input value="��  ��" class="cssButton" type=button name=saveAttache onclick="saveAttachmentClick();">
			<Input value="��  ��" class="cssButton" type=button name=updateAttache onclick="updateLLinqupload();">
			<Input value="ɾ  ��" class="cssButton" type=button name=deleteAttache onclick="deleteLLinqupload();">
			<td style="font:10pt"><font color=red>&nbsp&nbsp&nbsp&nbsp* ����"����"������Ȼ�����"���鸽������"</font></td>
		</div>
		<div>
			<table class=common>
				<tr class=common>
					<td class=title> ���鸽������ </td>
					<td class=Input><Input class=common name=AffixName maxlength="10" elementtype=nacessary></td>
					<td class=title> ԭ����ʶ </td>
					<td class=Input><Input class=codeno name=OriginalLogo ondblclick="return showCodeList('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);"><Input class=codename name=OriginalLogoName elementtype=nacessary readonly=true></td>        
					<td class=title> �ϴ����� </td>
					<td class=Input><Input class=common name=AffixNumber maxlength="10" elementtype=nacessary value=1></td>
				</tr>
			</table>
		</div>
		<div>
			<td class=title> ���鸽������ &nbsp&nbsp&nbsp</td><td class=Input><Input type=hidden id="ImportPath" name=ImportPath> </td>
			<Input type="file" name=FileName style= "height:20px ;width:260px " onkeydown="this.blur()" >
			<Input value="�ϴ��ļ�" class="cssButton" type=button id=uploadfile name=uploadfile onclick="uploadFileClick('0');">
			<Input value="�滻�ļ�" class="cssButton" type=button id=repalecefile  name=repalecefile onclick="replaceFileClick();">
			<Input value="�鿴�ļ�" class="cssButton" type=button name=viewfile onclick="viewAttachmentClick();">
			<Input value="ɾ���ļ�" class="cssButton" type=button id=deletefile name=deletefile onclick="deleteAttachmentClick();">
		</div>
		<Input type=hidden name=AffixPatch>
		<Input type=hidden name=AttachNo><!--�������-->
		<Input type=hidden name=InitDept2><!--���������ҵ�����-->
		<Input type=hidden name=insuredimport value="1">
		<Input type=hidden name=ImportFile>
		<Input type=hidden name=OldFileName>
		<Input type=hidden name=fmtransact><!--�������롮insert,delete����-->   
		<Input type=hidden name=RgtNo>
		<Input type=hidden name=InqNo><!--ҵ���-->
	</form>
	<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
