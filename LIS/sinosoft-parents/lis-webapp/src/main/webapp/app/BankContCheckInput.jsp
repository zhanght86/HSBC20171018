<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2006-02-14 11:51:42
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");

	}
	catch( Exception e )
	{
		tPNo = "";
	}

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//Ĭ�������Ϊ���˱���ֱ��¼��
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "5";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "5";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script language="javascript">
	
	//�ӷ�������ȡ������:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var NoType = "<%=request.getParameter("NoType")%>"; 
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	//��ȫ���ûᴫ���ֹ���
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//�������ģ����ô���
	var LoadFlag ="<%=tLoadFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	var ContType = 1;
	//Q��Auditing �������˹��˱�ʱ����ѯ������ϸʱ��Ϊ��־ Auditing=1
	var Auditing = "<%=request.getParameter("Auditing")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if(Auditing=="null") { Auditing=0; }    
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";

</script>

 
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
   <script src="../common/javascript/VerifyInput.js"></script>
  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
  <script src="../common/javascript/jquery.easyui.min.js"></script>

  <script src="../common/javascript/Signature.js"></script>
  <script src="ProposalAutoMoveNew.js"></script>
  <script src="TabAutoScroll.js"></script>
  <script src="BankContCheck.js"></script> 
  <%@include file="BankContCheckInit.jsp"%>  
  <script src="../common/javascript/MultiCom.js"></script>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
}
%>
  
</head>


<body  onload="initForm();initElementtype();window.document.ondblclick=AutoTab;" >
  <form action="./BankContSave.jsp" method=post name=fm id="fm" target="fraSubmit"> 

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      �����ͬ��Ϣ¼��ؼ�         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="BankContPage.jsp"/>  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   ��ҵ��ԱMultiLine            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:none">
		<table>
			<tr>
			    <td class=common>
			        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
			    </td>
			    <td class= titleImg>����ҵ��Ա��Ϣ</td>
			</tr>
		</table>
   
		<div id="divMultiAgent1" style="display: ">
			<table class="common">
				<tr class="common">
					<td style="text-align:left" colSpan="1"><span id="spanMultiAgentGrid"></span></td>
				</tr>
			</table>
		</div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     ҵ��Ա��֪            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display:none">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divAgentImpart1);">
            </td>
            <td class= titleImg>ҵ��Ա��֪</td>
        </tr>
    </table>
    <div id="divAgentImpart1" style="display: ">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanAgentImpartGrid'></span>
          </td>
        </tr>
      </table>
    </div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  
 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               ����Ͷ����¼��ؼ�����
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="BankComplexAppnt.jsp"/>
<!-- 
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <!-- ��֪��Ϣ���֣��б� -->
    <div id="DivLCImpart" style="display:none">
		<table>
		  <tr>
		      <td class=common>
		          <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCImpart1);">
		      </td>
		      <td class= titleImg>Ͷ���˸�֪��Ϣ</td>
		  </tr>
		</table>
		<div id=DivIncome0 style="display: ">
			 <table  class="common">  
			   <tr class="common" style="display:none">
			     	<td class="title">��ÿ��̶�����</td>
			      	<td>
			        	<input class=common name=Income0  verify="Ͷ��������|NUM&LEN<=8" verifyorder='1' >  ��Ԫ
			      	</td>   
			     	<td  class="title">��Ҫ������Դ��</td>             
			     	<td class="input">
			       		<input class="codeno" name="IncomeWay0" verify="��Ҫ������Դ|LEN<4"  verifyorder="1" ondblclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0"  readonly="true">
			     	</td>              
			   </tr> 
			</table> 
		</div>  
		<div id="divLCImpart1" style="display: ">
		  <table class="common">
		      <tr class="common">
		          <td style="text-align:left" colSpan="1">
		              <span id="spanImpartGrid"></span>
		          </td>
		      </tr>
		  </table>
		</div>
    </div>
 
	<table class=common style="display:none">
	    <tr  class= common> 
	      <td  class= common>����������200���������ڣ�</TD>
	    </tr>
	    <tr  class= common>
	      <td  class= common>
	        <textarea name="Remark1" verify="��������|len<800" verifyorder="1" cols="110" rows="2" class="common" >
	        </textarea>
	      </td>
	    </tr>
    </table>

    
    <div  id= "divButton" style= "display:none">
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
       			</td> 
       		</tr>
       	</table>
      </span>
    </div>

  </div>
  <hr class=line> 

    <!--����������Ϣ��Ͷ���˽���ϲ� hanlin 20050416-->
    <div  id= "divFamilyType" style="display:none">
      <table class=common>
        <tr class=common>
          <TD  class= input>
            <!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
            <input type="hidden" name=FamilyType value="1">
          </TD>
        </tr>
      </table>
    </div>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               �������б�MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
    <div  id= "divTempFeeInput" style= "display: "> 
    	<table class=common>
    	  <tr>
    		  <td style="text-align: left" colSpan=1>
	  				<span id="spanInsuredGrid" > 
	  				</span>
	  			</td>
    		</tr>
    	</Table>
    </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               ���뱻����¼��ؼ�
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <jsp:include page="BankComplexInsured.jsp"/>

<hr class=line>
    <div id="DivLCImpartInsured" STYLE="display: ">
    <!-- ��֪��Ϣ���֣��б� -->
      <table>
        <tr>
          <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpartInsured1);">
          </td>
          <td class= titleImg>�������˸�֪��Ϣ</td>
          <td><Input type="button" class="cssButton" name="insuworkimpart" value="������ְҵ��֪��Ϣ"></td>
          <td><Input type="button" class="cssButton" name="insuhealthmpart" value="�����˽�����֪��Ϣ"></td>
        </tr>
      </table>
	  <div id=DivIncome style="display: ">
		 <table  class="common">  
		   <tr class="common" style="display:none">
		     <td class="title">��ÿ��̶�����</td> 
		      <td>
		        <input class=common name=Income verify="������������|NUM&LEN<=8" verifyorder='2' >  ��Ԫ
		      </td>
		     <td  class="title">��Ҫ������Դ��</td>             
		     <td class="input">
		       <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
		       <input class="codeno" name="IncomeWay" verify="��������Ҫ������Դ|LEN<4"  verifyorder="2" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" readonly="true">
		     </td>              
		   </tr> 	
		 </table> 
	  </div>     
      <div id="divLCImpartInsured1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            	<span id="spanImpartInsuredGrid" ></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
<hr class=line>    
     <!-- ������������Ϣ���� -->
    <div id=DivLCPol STYLE="display: ">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>��������������Ϣ</td>
          <td>
			<Input class="cssButton" name=RiskButton type="button" value="��������������Ϣ" onclick="goToPic(0); top.fraPic.scrollTo(83, 541);">
		  </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left" colSpan=1>
              <span id="spanPolGrid" ></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    
    <!-- ��������Ϣ���֣��б� -->
     <div id="divLCBnf" style="display: "> 
	    <table>
	      <tr>
	        <td class=common>
			  	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
	      	</td>
	        <td class= titleImg>��������Ϣ</td>
	        <td>
				<Input class="cssButton" name=BnfButton type="button" value="��������Ϣ" onclick="goToPic(0); top.fraPic.scrollTo(93, 792); ">
		  	</td>
	      </tr>
	    </table>
		<div id= "divLCBnf1" style= "display:  " >
	      <table class= common>
	        <tr class= common>
	      	  <td style="text-align: left" colSpan="1">
			  		<span id="spanLCBnfGrid"></span>
			  </td>
			</tr>
		  </table>
		</div>
	 </div>
	 
    <hr class=line>
    
    
 <!--
  ###############################################################################
                              �涯���ư�ť 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
	    <input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input class=cssButton id="queryintoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
      <input value="��  ��" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="35">
    </div>

<!--###############################################################################
                              ���˰�ť 
  ###############################################################################
-->
    <div id = "divApproveContButton" style = "display:none;float: left">
    	<table>
    	  <tr>
    	    <td>
    	    	
            <input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
            <input class=cssButton id="demo1" name="AppntChkButton" VALUE=Ͷ����У�� TYPE=hidden onclick='AppntChk();'>
            <input class=cssButton id="demo2" name="InsuredChkButton"  VALUE=������У�� TYPE=hidden onclick='InsuredChk();' disabled="disabled">
            <input class=cssButton id="riskbutton5" VALUE="ǿ�ƽ����˹��˱�" TYPE=button onclick="forceUW();">
            <input class=cssButton id="intoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
          </td>
        </tr>
        <tr>
          <td>
          	<input class=cssButton VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();">
          	<input class=cssButton VALUE="�������ѯ" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">	
    	    <input class=cssButton name="NotePadButton5" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
            <input class=cssButton id="Donextbutton4" VALUE=" ��  ��  ��  �� " TYPE=button onclick="inputConfirm();">
          </td>
        </tr>
      </table>
    </div>
    <div id = "divCustomerUnionButton" style = "display: ">
		<table>
			<tr><td>
				<!--input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();"-->
				<!--input class=cssButton id="intoriskbutton" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();"-->

				<input id="demo1" name="AppntChkButton"  class=cssButton VALUE="Ͷ����У��" TYPE=button onclick='GoToAppnt();'>
				<input id="demo2" name="InsuredChkButton" class=cssButton VALUE="������У��" TYPE=button  onclick='GoToInsured();'>
			</td></tr>
			<tr><td>
				<!--input class=cssButton VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();"-->	
			    <!--input class=cssButton VALUE="�������ѯ" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();"-->
				<input class=cssButton name="NotePadButtona" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
				<input class=cssButton id="Donextbutton4" VALUE=" �ͻ����ֹ��ϲ���� " TYPE=button onclick="UnionConfirm();">
			</td></tr>
		</table>
	</div>
	
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               ���ؿؼ�λ��                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
    	<input type="hidden" id="fmAction" name="fmAction">
    	<!-- ������������� -->
			<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
			<input type="hidden" name="MissionID" value= "">
      <input type="hidden" name="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" value= "">
      <input type="hidden" name="NoType" value= "">
      <input type="hidden" name="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" value="">
      <input type="hidden" name="BQFlag">  
      <input type='hidden' name="MakeDate">
      <input type='hidden' name="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
      
      <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
      <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
      <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">   
      <input type="hidden" id="Operator" name="Operator">
    </div>           
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
</form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
