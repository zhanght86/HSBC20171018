<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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

//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//Ĭ�������Ϊ���˱���ֱ��¼��
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	
  loggerDebug("BankContInput","LoadFlag:" + tLoadFlag);
%>
<script language="javascript">
	
	//�ӷ�������ȡ������:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	//alert("prtNo:"+prtNo);
	
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	//alert("ActivityIDxxxxxxxx"+ActivityID);  
	var NoType = "<%=request.getParameter("NoType")%>"; 
	//alert("NoType:"+NoType);
	
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	//alert("ContNo:"+ContNo);
	var specScanFlag = "";    //��¼ͨ�����Ᵽ����ɨ���¼�룬����ǣ�����ֵΪspecScanFlag = "bposcan";
	var scantype = "<%=request.getParameter("scantype")%>";
	//alert("old scantype: "+scantype);
	if(scantype == "bposcan")
	{
	  specScanFlag = "bposcan";
	  scantype = "scan";
	}
	//alert("new scantype: "+scantype);
	var type = "<%=request.getParameter("type")%>";
	//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";

	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";

	if (ScanFlag == "null") ScanFlag = "0";
	//��ȫ���ûᴫ���ֹ���
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//alert("BQRiskCode="+BQRiskCode);
	//�������ģ����ô���
	var LoadFlag ="<%=tLoadFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	//alert("LoadFlag"+LoadFlag);
	var ContType = 1;
	
	//Q��Auditing �������˹��˱�ʱ����ѯ������ϸʱ��Ϊ��־ Auditing=1
	var Auditing = "<%=request.getParameter("Auditing")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����

	/******************
   *  ����Լ��Auditing=null	
	 *
	 *****************/
	
	//alert("hl Auditing=="+Auditing); 
	
	if(Auditing=="null")
	{
	  Auditing=0;
	}   
	    
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	//alert("AppntNo="+AppntNo);
	var AppntName = "<%=request.getParameter("AppntName")%>";
	//alert("AppntName="+AppntName); 
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";

</script>

 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
  

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 �ڱ�ҳ����������JSҳ��              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/jquery-1.7.2..js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
  <script src="../common/javascript/jquery.easyui.min.js"></script>

  <script src="../common/javascript/Signature.js"></script>
  <script src="ProposalAutoMoveNew.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
   <SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
    <script src="../common/javascript/MultiCom.js"></script>
  <%
  loggerDebug("BankContInput","***scantype123: "+request.getParameter("scantype"));
if(request.getParameter("scantype")!=null&&((request.getParameter("scantype")).equals("scan") || (request.getParameter("scantype")).equals("bposcan"))){
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
}
%>  
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
             ���뱾ҳBankContInputInit.jspҳ��                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
 
<%@include file="BankContInputInit.jsp"%>  
 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    ���뱾ҳBankContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="BankContInput.js"></script> 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
</head>


<body  onload="initForm();initForm2();initElementtype();" >
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
   
		<div id="divMultiAgent1" style="display:''">
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
    <div id="divAgentImpart1" style="display:''">
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

<hr class="line">
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
		<div id="DivIncome0" style="display:''">
			 <table  class="common">  
			   <tr class="common" style="display:none">
			     	<td class="title">��ÿ��̶�����</td>
			      	<td>
			        	<input class="common wid"  name="Income0" id="Income0"  verify="Ͷ��������|NUM&LEN<=8" verifyorder='1' >  ��Ԫ
			      	</td>   
			     	<td  class="title">��Ҫ������Դ��</td>             
			     	<td class="input">
			       	<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
			       		<input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="IncomeWay0" id="IncomeWay0" verify="��Ҫ������Դ|LEN<4"  verifyorder="1" onClick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onDblClick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onKeyUp="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0" id="IncomeWayName0"  readonly="true">
			     	</td>              
			   </tr> 
			</table> 
		</div>  
		<div id="divLCImpart1" style="display:''">
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
      <td  class= title>����������200���������ڣ�</TD>
    </tr>
    <tr  class= common>
      <td  class= input>
        <textarea name="Remark1" id="Remark1" verify="��������|len<800" verifyorder="1" cols="110" rows="4" class="common" >
        </textarea>
      </td>
    </tr>
  </table>

    
    <div  id= "divButton" style= "display:none">
<%--jsp:include page="../common/jsp/InputButton.jsp"/--%>
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="addbutton" id="addbutton" VALUE="��  ��"  TYPE=button onClick="return submitForm();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="updatebutton" id="updatebutton" VALUE="��  ��"  TYPE=button onClick="return updateClick();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="deletebutton" id="deletebutton" VALUE="ɾ  ��"  TYPE=button onClick="return deleteClick();">
       			</td> 
       		</tr>
       	</table>
      </span>
    </div>

  <hr class="line"> 
	<!------############# FamilyType:0-���ˣ�1-��ͥ�� #######################--------->
	<div  id= "divFamilyType" style="display:none">
		<table class=common>
			<tr class=common>
				<!---TD  class= title>���˺�ͬ����</TD-->
				<TD  class= input>
					<!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
					<input type="hidden" name=FamilyType id="FamilyType" value="1">
				</TD>
			</tr>
		</table>
	</div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               �������б�MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
	<div  id= "divTempFeeInput" style= "display:none"> 
		<Table class=common>
			<tr>
				<td text-align: left colSpan=1>
					<span id="spanInsuredGrid" > </span>
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
<!--  
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


    <!--table class=common>
      <TR  class= common>
        <TD  class= title> �ر�Լ������ע </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>
        <textarea name="GrpSpec" cols="120" rows="3" class="common" >
        </textarea></TD>
      </TR>
    </table-->
<hr class="line">
    <div id="DivLCImpartInsured" STYLE="display:''">
    <!-- ��֪��Ϣ���֣��б� -->
      <table>
        <tr>
          <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpartInsured1);">
          </td>
          <td class= titleImg>�������˸�֪��Ϣ</td>
          <TD><Input type="button" class="cssButton" name="insuhealthmpart" id="insuhealthmpart" value="�����˽�����֪��Ϣ"></TD>
          <TD><Input type="button" class="cssButton" name="insuworkimpart" id="insuworkimpart" value="������ְҵ��֪��Ϣ"></TD>
        </tr>
      </table>
		<div id="DivIncome" style="display:''">
		 <table  class="common">  
		   <tr class="common" style="display:none">
		     <td class="title">��ÿ��̶�����</td> 
		      <td class="input">
		        <input class="common wid"  name=Income id="Income" verify="������������|NUM&LEN<=8" verifyorder='2' >  ��Ԫ
		      </td>
		     <td  class="title">��Ҫ������Դ��</td>             
		     <td class="input">
		       <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
		       <input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name="IncomeWay" id="IncomeWay" verify="��������Ҫ������Դ|LEN<4"  verifyorder="2" onclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" id="IncomeWayName" readonly="true">
		     </td>              
		   </tr> 
		</table> 
		</div>     
      <div id="divLCImpartInsured1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
            <td text-align: left colSpan=1>
            <span id="spanImpartInsuredGrid" ></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <!-- ��֪��Ϣ���֣��б� -->
<!--
    <div id="DivLCImpartInsuredDetail" STYLE="display:''">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
          </td>
          <td class="titleImg">
            �������˸�֪��ϸ��Ϣ
          </td>
        </tr>
      </table>
      
      <div  id="divLCImpart2" style= "display: ''">
        <table  class= common>
          <tr  class=common>
            <td text-align: left colSpan=1>
              <span id="spanImpartDetailGrid" >
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
-->
    <div  id= "divAddDelButton" style= "display:none" align=right>
       <input type="button" class="cssButton" value="��ӱ�������" onclick="addRecord();"> 
      <input type="button" class="cssButton" value="ɾ����������" onclick="deleteRecord();"> 
      <input type="button" class="cssButton" value="�޸ı�������" onclick="modifyRecord();"> 
    </div>
<hr class="line">    
    <!-- ������������Ϣ���� -->
    <div id="DivLCPol" STYLE="display:''">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>
            ��������������Ϣ
          </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left" colSpan=1>
              <span id="spanPolGrid" >
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <hr class="line">
    
    
    
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!--end hanlin-->
    
    <!-- ����Ͷ�ʼƻ� -->
     <Div  id= "divInvestPlanRate" style= "display: none" >
      <table>
        <tr class=common>	
       <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanInvestPlanRate);">
          </td>
    	 	<td class= titleImg>
    			 ����Ͷ�ʼƻ�
    		</td>
    	  </tr>
    	 </table> 
        <table class=common> 
    	   <TR  class= common>
       	<td class="title">�����ʻ���Ч������</td> 
       <td class="input"><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=UintLinkValiFlag id="UintLinkValiFlag"  CodeData='0|^2|ǩ������Ч^4|����ԥ�ں���Ч' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName id="UintLinkValiFlagName" readonly=true ></td>
       <td></td>
        </TR> 
        </table>
    		  <table  class= common>
        	 <tr  class= common>
    	  		<td text-align: left colSpan=1>
					  <span id="spanInvestPlanRate" >
					  </span>
				    </td>
			     </tr>
			      <tr  class= common>
			      	<td>
			      	</td>
			     </tr>
		    </table>
  	</Div>
    <br>

<!--
  ###############################################################################
                              ¼����ť 
  ###############################################################################
-->
	<div  id= "divInputContButton" style="display:'';float: left">
		<input class=cssButton VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();">	
		<input class=cssButton id="riskbutton4" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
		<input class=cssButton VALUE="�������ѯ" TYPE=button onclick="QuestQuery();">
		<!--INPUT class=cssButton id="riskbutton1" VALUE="��һ��" TYPE=button onclick="returnparent();"-->
		<input class=cssButton id="riskbutton3" VALUE="������Ϣ¼��" TYPE=button onclick="intoRiskInfo();">
		<input class=cssButton id="riskbutton33" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">
		<input class=cssButton name="NotePadButton1" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
		<input class=cssButton id="riskbutton2" VALUE="¼�����"  TYPE=button onclick="inputConfirm(1);">
	</div>
<!--
  ###############################################################################
                              ���˰�ť 
  ###############################################################################
-->
    <div id = "divApproveContButton" style = "display:none;float: left">
    	<table>
    	  <tr>
    	    <td>
    	    <input class=cssButton VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();">	
            <input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
            <input class=cssButton id="demo1" name="AppntChkButton" VALUE=Ͷ����У�� TYPE=hidden onclick='AppntChk();'>
            <input class=cssButton id="demo2" name="InsuredChkButton"  VALUE=������У�� TYPE=hidden onclick='InsuredChk();' disabled="disabled">
            <input class=cssButton id="intoriskbutton" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
          </td>
        </tr>
        <tr>
          <td>
          	<input class=cssButton VALUE="�������ѯ" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">	
            <!--INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();"-->
    	    <!--INPUT class=cssButton id="Donextbutton6" VALUE="��һ��" TYPE=button onclick="intoInsured();"-->
    	    <input class=cssButton name="NotePadButton5" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
    	    <input class=cssButton id="riskbutton5" VALUE="ǿ�ƽ����˹��˱�" TYPE=button onclick="forceUW();">
            <input class=cssButton id="Donextbutton4" VALUE=" ������� " TYPE=button onclick="inputConfirm(2);">
          	<!--<input class="cssButton" id="exitButton" value="  ����  " type="button" onclick="exitAuditing();">-->
          </td>
        </tr>
      </table>
    </div>
<!--
  ###############################################################################
                              �˱��޸�Ͷ������ť 
  ###############################################################################
-->

    <div id = "divchangplan" style="display:none;float:left">
      <!--input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <input class=cssButton VALUE=Ͷ����У�� TYPE=button onclick='AppntChk();'>
      <input class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();"-->
      <input class=cssButton id="intoriskbutton3" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
      <!--input class=cssButton id="riskbutton34" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();"-->
    </div>
    
<!--
  ###############################################################################
                              ���ո����޸İ�ť 
  ###############################################################################
-->

    <div id = "divApproveModifyContButton" style = "display:none;float:right">
        <input class=cssButton id="intoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
        <input class=cssButton id="riskbutton24" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">
     	<input class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
    	<input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
				<!--����������ظ���ť,��ϵͳ�Զ��ظ�-->       
        <input class=cssButton VALUE=������ظ� TYPE=button onclick="QuestQuery();">
        <input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
        <input id="demo5" name="AppntChkButton3" style = "display:''" class=cssButton VALUE=Ͷ����У�� TYPE=button onclick='AppntChk();'>
        <input id="demo6" name="InsuredChkButton3" style = "display:''" class=cssButton VALUE=������У�� TYPE=button onclick='InsuredChk();' disabled="disabled">
    	  <input class=cssButton id="Donextbutton7" VALUE="������޸����" TYPE=button onclick="inputConfirm(3);">
        <input class="cssButton" id="exitButton2" value="����" type="button" onclick="exitAuditing2();">
    	  <!--input class=cssButton id="Donextbutton8" VALUE="��  ��"  TYPE=button onclick="submitForm();"-->
    	  <!--INPUT class=cssButton id="Donextbutton9" VALUE="��һ��" TYPE=button onclick="intoInsured();"-->

    </div>
<!--
  ###############################################################################
                              ����������޸İ�ť 
  ###############################################################################
-->

    <div id = "divProblemModifyContButton" style = "display:none;float:left">
      <table>
        <tr>
          <td>
            <input class=cssButton id="intoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
            <input class=cssButton id="riskbutton24" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">
            <input class=cssButton id="demo3" name="AppntChkButton2" VALUE=Ͷ����У�� TYPE=hidden onclick='AppntChk();'>
            <input class=cssButton id="demo4" name="InsuredChkButton2" VALUE=������У�� TYPE=hidden onclick='InsuredChk();'>
      	    <input class=cssButton name="NotePadButton3" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
          </td>
        </tr>
        <tr>
          <td>
          	<input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
            <input class=cssButton VALUE="   ������ظ�   " TYPE=button onclick="QuestQuery();">
            <input class=cssButton VALUE=" �����Ӱ���ѯ " TYPE=button onclick="QuestPicQuery();">
			<input class="cssButton" id="exitButton10" value="Ͷ����ǿ�ƹ���" type="button" onclick="customerForceUnion(0);">
			<input class="cssButton" id="exitButton11" value="������ǿ�ƹ���" type="button" onclick="customerForceUnion(1);">
    	      <input class=cssButton id="Donextbutton7" VALUE=" ������޸���� " TYPE=button onclick="inputConfirm(3);">
            <input class="cssButton" id="exitButton2" value="     ����     " type="hidden" onclick="exitAuditing2();">
          </td>
        </tr>
      </table>

    </div>
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
      <input type=hidden name="autoMoveFlag">
      <input type=hidden name="autoMoveValue">
      <input type=hidden name="pagename" value="35">
    </div>
<!--
  ###############################################################################
                              ��ѯͶ������ť 
  ###############################################################################
-->

    <div id = "divInputQuery" style="display:none;float:left">
      <input class=cssButton id="queryintoriskbutton" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
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
			<input type="hidden" name="MissionID" id="MissionID" value= "">
      <input type="hidden" id="SubMissionID" name="SubMissionID" value= "">
      
      <input type="hidden" id="ActivityID" name="ActivityID" value= "">
      <input type="hidden" id="NoType" name="NoType" value= "">
      <input type="hidden" id="PrtNo2" name="PrtNo2" value= "">
      
      <input type="hidden" id="ContNo" name="ContNo" value="" >
      <input type="hidden" id="ProposalGrpContNo" name="ProposalGrpContNo" value="">
      <input type="hidden" id="SelPolNo" name="SelPolNo" value="">
      <input type="hidden" id="BQFlag" name="BQFlag">  
      <input type='hidden' id="MakeDate" name="MakeDate">
      <input type='hidden' id="MakeTime" name="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
      <!--input type='hidden' name="LoadFlag"><!--����-->  -->
    </div>           
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
</form>
<br>
<br>
<br>
<br>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
