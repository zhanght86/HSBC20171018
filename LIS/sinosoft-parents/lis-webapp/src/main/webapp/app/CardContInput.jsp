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
	
	
  loggerDebug("CardContInput","LoadFlag:" + tLoadFlag);
%>
<script language="javascript">
	
	//�ӷ�������ȡ������:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	//alert("prtNo:"+prtNo);
	
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	//alert("ActivityID:"+ActivityID);
	var NoType = "<%=request.getParameter("NoType")%>";
	//alert("NoType:"+NoType);
	
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
    var ComCode = "<%=tGI.ComCode%>";
    var ContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ComCode:"+ComCode);
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";

	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	var BankFlag = "<%=request.getParameter("BankFlag")%>";

	if (ScanFlag == "null") ScanFlag = "0";
	//��ȫ���ûᴫ���ֹ���
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//�������ģ����ô���
	var LoadFlag ="<%=tLoadFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����

	//alert("prtNo==="+prtNo);
	//hanlin 20050416
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
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";


	//end hanlin

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
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="ProposalAutoMove2.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
}
%>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
             ���뱾ҳContInit.jspҳ��                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<%@include file="CardContInit.jsp"%>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    ���뱾ҳContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="CardContInput.js"></script>
 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
</head>


<body  onload="initForm();initForm2();initElementtype();queryinfo();" >
  <form action="./CardContSave.jsp" method=post name=fm id="fm" target="fraSubmit">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      �����ͬ��Ϣ¼��ؼ�         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="CardContPage.jsp"/>
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
            <td class= titleImg>
                ����ҵ��Ա��Ϣ
            </td>
        </tr>
    </table>
   
    <div id="divMultiAgent1" style="display:none">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMultiAgentGrid">
                    </span>
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
                     ҵ��Ա��֪            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display:none">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divAgentImpart1);">
            </td>
            <td class= titleImg>
                ҵ��Ա��֪
            </td>
        </tr>
    </table>
    <div id="divAgentImpart1" style="display:none">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanAgentImpartGrid'>
            </span>
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
<jsp:include page="CardComplexAppnt.jsp"/>
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
              <td class= titleImg>
                  Ͷ���˸�֪��Ϣ
              </td>
          </tr>
      </table>
   <div id=DivIncome0 style="display:none">
   	<table class=common>
    <tr  class= common> 
      <td  class= common>����������200���������ڣ�</TD>
    </tr>
    <tr  class= common>
      <td  class= common>
        <textarea name="Remark" id=Remark verify="��������|len<800" verifyorder="1" cols="110" rows="2" class="common" >
        </textarea>
      </td>
    </tr>
  </table>
     <table  class="common">  
       <tr class="common">
         <td class="title">��ÿ��̶�����</td>
          <td>
            <input class=common name=Income0 id=Income0 verify="Ͷ��������|NUM&LEN<=8" verifyorder='1' >��Ԫ
          </td>
         
  
        
         <td  class="title">
            ��Ҫ������Դ��
         </td>             
         <td class="input">
           <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
           <input class="codeno" name="IncomeWay0" id="IncomeWay0" verify="��Ҫ������Դ|LEN<4"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0"  readonly="true">
         </td>              
         
         
       </tr> 
    </table> 
   </div>  
      <div id="divLCImpart1" style="display:none">
          <table class="common">
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanImpartGrid">
                      </span>
                  </td>
              </tr>
          </table>
      </div>
    </div>


	

    
    <div  id= "divButton" style= "display:none">
<%--jsp:include page="../common/jsp/InputButton.jsp"/--%>
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
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
  

    <!--����������Ϣ��Ͷ���˽���ϲ� hanlin 20050416-->
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>

    <!--input type=hidden id="fmAction" name="fmAction"-->

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
    
    
    
	    
	    
    <div  id= "divTempFeeInput" style= "display:none">
    	<table class=common>
    	  <tr>
    		  <td style="text-align: left" colSpan=1>
	  				<span id="spanInsuredGrid" >
	  				</span>
	  			</td>
    		</tr>
    		<tr CLASS=common>
    <TD CLASS=title>
     �ɷ�����
    </TD>
    <TD class=input>
        <Input name="PayEndYear" id="PayEndYear">
		<input class=codename name=PayEndYearName id="PayEndYearName" readonly=true elementtype=nacessary>
    </TD>
  </TR>
  <TR CLASS=common>

	  <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear id="InsuYear" VALUE="" class="common wid" >
    </TD>


    <TD CLASS=title>
      ���ѷ�ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv id="PayIntv" VALUE="" class="common wid" >
    </TD>

 </TR>
  <TR CLASS=common>
    

    <TD CLASS=title>
      �����ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id="InsuYearFlag" value="A" class="common wid">
    </TD>

   <TR CLASS=common>
    <TD CLASS=title>
      �����ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYearFlag id="PayEndYearFlag" value="Y" class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetDutyKind id="GetDutyKind">
    </TD>
    <TD CLASS=title>
      ��ȡ��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=getIntv id="getIntv">
    </TD>
    <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetYear id="GetYear">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �����ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetYearFlag id="GetYearFlag">
    </TD>
    <TD CLASS=title>
      �������ڼ�������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=GetStartType id="GetStartType">
    </TD>
    <TD CLASS=title>
      �Զ��潻��־
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=AutoPayFlag id="AutoPayFlag">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=InterestDifFlag id="InterestDifFlag">
    </TD>
    <TD CLASS=title>
      ������־
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=SubFlag id="SubFlag">
    </TD>
    <TD CLASS=title>
      ������ȡ��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=BonusGetMode id="BonusGetMode">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������ȡ��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=LiveGetMode id="LiveGetMode">
    </TD>
    <TD CLASS=title>
      �Ƿ��Զ�����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=RnewFlag id="RnewFlag">
    </TD>
	<td class="title"></td>
	<td class="input"></td>
  </TR>
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
<jsp:include page="CardComplexInsured.jsp"/>
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

    <div id="DivLCImpartInsured" STYLE="display:none">
    <!-- ��֪��Ϣ���֣��б� -->
      <table>
        <tr>
          <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpartInsured1);">
          </td>
          <td class= titleImg>
            �������˸�֪��Ϣ
          </td>
        </tr>
      </table>
   <div id=DivIncome style="display:none">
     <table  class="common">  
       <tr class="common">
         <td class="title">��ÿ��̶�����</td>
          <td>
            <input class=common name=Income id="Income" verify="������������|NUM&LEN<=8" verifyorder='2' >��Ԫ
          </td>
         
  
        <!--td class="title">��Ԫ</td-->
    
         <td  class="title">
            ��Ҫ������Դ��
         </td>             
         <td class="input">
           <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
           <input class="codeno" name="IncomeWay" id="IncomeWay" verify="��������Ҫ������Դ|LEN<4"  verifyorder="2" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" readonly="true">
         </td>              
         
         
       </tr> 
   </table> 
</div>     
      <div id="divLCImpartInsured1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            <span id="spanImpartInsuredGrid" >
            </span>
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
   
    <!-- ������������Ϣ���� -->
    <div id=DivLCPol STYLE="display:none">
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
    
      <div  id= "divLCPol1" style= "display: none">
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
    
    </div>
    <br>

    <div  id= "HiddenValue" style="display:none;float: right">
    	<input type="hidden" id="fmAction" name="fmAction">
			<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
			<input type="hidden" name="MissionID" id="MissionID" value= "">
      <input type="hidden" name="SubMissionID" id="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" id="ActivityID" value= "">
      <input type="hidden" name="NoType" id="NoType" value= "">
      <input type="hidden" name="PrtNo2" id="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" id="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" id="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" id="SelPolNo" value="">
      <input type="hidden" name="BQFlag" id="MakeDate">  
      <input type='hidden' name="MakeDate" id="MakeDate">
      <input type='hidden' name="MakeTime" id="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
      <input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
    </div>          

        <!-- ��������Ϣ���֣��б� -->
      <table>
      	<tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
      		</td>
          <td class= titleImg>
      		  ��������Ϣ
      		</td>
      	</tr>
      </table>
	    <div id= "divLCBnf1" style= "display: ''" >
      	<table class= common>
          <tr class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanBnfGrid" >
		  			  </span>
		  		  </td>
		  	  </tr>
		    </table>
	    </div>
   
   
			<div  id= "divLCPol1" style= "display: ''">
        <table  class=common>
          <tr  class=common>
          	<TD  class= title>���ֱ���</TD>
            <TD  class= input>
              <Input class="codeno" name=RiskCode id="RiskCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('CardRiskCode',[this,RiskCodeName],[0,1],null,null,null,null,'400');" onkeyup="showCodeListKey('CardRiskCode',[this,RiskCodeName],[0,1],null,null,null,null,'400');">
			  <input class="codename" name="RiskCodeName" id="RiskCodeName" elementtype=nacessary readonly="readonly">
            </TD>
            <TD  class= title>����</TD>
            <TD  class= input colspan=3 >
              <Input class='common wid'  name="Mult" id="Mult" verify="����|len<=5"  elementtype=nacessary verifyorder="1" >
            </TD> 		
          </tr>
           <TR CLASS=common>
                <TD CLASS=title>����</TD>
			    <TD CLASS=input COLSPAN=1>
			      <Input NAME=Amnt id="Amnt" VALUE="" MAXLENGTH=12 CLASS="common wid" readonly>
			    </TD>	
				<TD CLASS=title>����</TD>
			    <TD CLASS=input COLSPAN=1>
			      <Input NAME=Prem id="Prem" VALUE="" MAXLENGTH=12 CLASS="common wid" readonly>
			    </TD> 
				<td class=title></td>
				<td class=input></td>
  			</TR>
        </table>
      </div>
      
			<div id= "DivPolOtherGrid" style= "display: none" >
				<table class= common>
				<tr class= common>
				<td style="text-align: left" colSpan="1"><span id="spanPolOtherGrid" ></span>
				</td>
				</tr>
				</table>
			</div>
			<br><hr class=line>
      
      <Div  id= "HiddenValue" style= "float: right" style="float: right">
    	
    	
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="��  �� "  TYPE=button onclick="submitForm();"-->
      <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">      			
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="��  �� "  TYPE=button onclick="submitForm1();" disabled=true-->
      <INPUT class=cssButton id="Donextbutton9" VALUE="ǩ  ��"  TYPE=button onclick="submitForm2();">  
      
      <INPUT class=cssButton id="Donextbutton10" VALUE="ɾ  ��"  TYPE=button onclick="deleteClick2();">  	 	
    
    </div>    
<!--
  ###############################################################################
                              �涯���ư�ť 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
	    <input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input value="��  ��" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="11">
    </div>

  
  
</form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<br /><br /><br /><br />
</body>

<script language="javascript">
	if(PrtNo!="null")
	{
	  fm.ProposalContNo.value=PrtNo;
	}else{
	fm.ProposalContNo.value="";
	}
</script>
</html>
