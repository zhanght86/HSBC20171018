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
	
	
  loggerDebug("ContInput","LoadFlag:" + tLoadFlag);
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
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var operator = "<%=tGI.Operator%>";
	//alert("ContNo:"+ContNo);
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

  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
   <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 �ڱ�ҳ����������JSҳ��              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <script src="../common/laydate/laydate.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
  <script src="ProposalAutoMoveNew.js"></script>

  <script src="../common/javascript/VerifyInput.js"></script>
  <script src="../common/javascript/MultiCom.js"></script>

  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>

	<script src="../common/javascript/Signature.js"></script>
  <%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){
%>


  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  

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

<%-- @include file="..\common\jsp\CodeQueryApplet.jsp" --%>
<%@include file="ContInit.jsp"%>
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
  <script src="ContInput.js"></script>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		-->
</head>

  
<body onLoad="initForm();initForm2();initElementtype();" >
  <form action="./ContSave.jsp" method=post name=fm id="fm" target="fraSubmit">

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      �����ͬ��Ϣ¼��ؼ�         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="ContPage.jsp"/>


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
   
    <div id="divMultiAgent1" style="display: ">
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
                     ҵ��Ա��֪            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display: ">
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
    <div id="divAgentImpart1" style="display: ">
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

<jsp:include page="ComplexAppnt.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

    <!-- ��֪��Ϣ���֣��б� -->
    <div id="DivLCImpart" style="display: ">
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
   
   <div id="DivIncome0" style="display:none">
    <div class="maxbox">
     <table  class="common">  
       <tr class="common">
         <td  class="title">��ÿ��̶�����</td>
          <td class=" input ">
            <input class="common wid" name=Income0 id="Income0" > ��Ԫ
          </td>
         
  
        <!--td class="title">��Ԫ</td-->
    
         <td  class="title">
            ��Ҫ������Դ
         </td>             
         <td class="input">           
           <input class="common wid" name="IncomeWay0" id="IncomeWay0" onDblClick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onKeyUp="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="common" name="IncomeWayName0"  readonly="true">
         </td>              
         <td class="title"></td>
         <td class="input"></td>
         
       </tr>
       <tr class = "common">
       	 <td class="title">Ͷ�������</td>
          <td class=" input ">
            <input class="common wid" name=AppntStature id="AppntStature"  > ����
          </td>          
          <td class="title">Ͷ��������</td>
          <td class=" input " >
            <input class="common wid" name=AppntAvoirdupois id="AppntAvoirdupois"  > ����
          </td>
       </tr>	
       <tr>
         <td>
           <input class="cssButton" name=appntfinaimpart id="appntfinaimpart" type="button" value="Ͷ���˲����֪" onClick="">

         </td>
         <td>
                    <input class="cssButton" name=appnthealimpart id="appnthealimpart" type="button" value="Ͷ���˽�����֪" onClick="">
         </td>
       </tr>
    </table> 
   </div> 
   </div> 
      <div id="divLCImpart1" style="display: ">
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

	<table class=common style="display:none">
		<tr  class= common> 
			<td  class= title>����������200���������ڣ�</TD>
		</tr>
		<tr  class= common>
			<td  class= input>
				<textarea name="Remark1" verify="��������|len<800" verifyorder="1" cols="88" rows="4" class="common"  >
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
       				<!-- <input class=cssButton name="addbutton" id="addbutton" VALUE="��  ��"  TYPE=button onClick="return submitForm();"> -->
              <a href="javascript:void(0)" class=button name="addbutton" id="addbutton" onClick="return submitForm();">��  ��</a>
       			</td>
       			<td class=button width="10%" align=right>
       				<!-- <input class=cssButton name="updatebutton" id="updatebutton" VALUE="��  ��"  TYPE=button onClick="return updateClick();"> -->
              <a href="javascript:void(0)" class=button name="updatebutton" id="updatebutton" onClick="return updateClick();">��  ��</a>
       			</td>
       			<td class=button width="10%" align=right>
       				<!-- <input class=cssButton name="deletebutton" id="deletebutton" VALUE="ɾ  ��"  TYPE=button onClick="return deleteClick();"> -->
              <a href="javascript:void(0)" class=button name="deletebutton" id="deletebutton" onClick="return deleteClick();">ɾ  ��</a>
       			</td>
       		</tr>
       	</table>
      </span>
    </div>


  	<!------############# FamilyType:0-���ˣ�1-��ͥ�� #######################--------->
	<div  id= "divFamilyType" style="display:none">
		<table class=common>
			<tr class=common>
				<!---TD  class= title>���˺�ͬ����</TD-->
					<!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
					<input type="hidden" name=FamilyType id="FamilyType" value="1">
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               �������б�MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
	<div  id= "divTempFeeInput" style= "display: ">
		<Table class=common>
			<tr>
				<td style="text-align: left" colSpan=1>
					<span id="spanInsuredGrid" ></span>
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
<jsp:include page="ComplexInsured2.jsp"/>
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
    <div id="DivLCImpartInsured" STYLE="display: ">
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
    </div>

   <div id="DivIncome" style="display:none">
    <div class="maxbox">
     <table  class="common">  
       <tr class="common">
         <td  class="title">��ÿ��̶�����</td>
          <td class=" input">
            <input class="common wid" name=Income id="Income" >&nbsp;&nbsp;��Ԫ
          </td>
       
         <td  class="title">
            ��Ҫ������Դ��
         </td>             
         <td class="input">           
           <input ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" class="codeno" name="IncomeWay" id="IncomeWay" onclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" id="IncomeWayName" readonly="true">
         </td>   
           
        </tr>
          
         <tr class = "common">
       	 <td class="title">���������</td>
          <td  class=" input">
            <input class="common wid"  name=Stature id="Stature" elementtype=nacessary>&nbsp;&nbsp;����
          </td>          
          <td class="title">����������</td>
          <td  class=" input">
            <input class="common wid" name=Avoirdupois id="Avoirdupois"  elementtype=nacessary>&nbsp;&nbsp;����
          </td>
       </tr>	       
       <tr>
         <td>
           <input class="cssButton" name=insufinaimpart id="insufinaimpart" type="button" value="�����˲����֪" >

         </td>
         <td>
          <input class="cssButton" name=insuhealimpart id="insuhealimpart" type="button" value="�����˽�����֪" >
         </td>
       </tr>
      </table> 
    </div>
    </div>     
      <div id="divLCImpartInsured1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            <span id="spanImpartInsuredGrid" >
            </span>
            </td>
          </tr>
        </table>
      </div>
    
    <!-- ��֪��Ϣ���֣��б� -->
<!--
    <div id="DivLCImpartInsuredDetail" STYLE="display: ">
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
      
      <div  id="divLCImpart2" style= "display:  ">
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
    <div id="DivLCPol" STYLE="display:block">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>
            ��������������Ϣ:
          </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display:  ">
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
    
    
    
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!--end hanlin-->
    
    <br>
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
        <table class="common" width="100%"> 
    	   <TR  class="common" >
       	<td class="title">�����ʻ���Ч������</td> 
       <td class="input"><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=UintLinkValiFlag id="UintLinkValiFlag"  CodeData='0|^2|ǩ������Ч^4|����ԥ�ں���Ч' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName id="UintLinkValiFlagName" readonly=true ></td>
       <td class="title"></td>
       <td class="input"></td>
       <td class="title"></td>
       <td class="input"></td>
        </TR> 
        </table>
    		  <table  class= common>
    			
        	 <tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
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
<!--
  ###############################################################################
                              ¼����ť 
  ###############################################################################
-->
	<div  id= "divInputContButton" style="display:none;float: left">
    
	<input class=cssButton VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();">
	<input id="sqButton" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="1" codetype="PrtNo">
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
			<input id="sqButton2" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
			<input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
			<input id="demo1" name="AppntChkButton" style = "display: " class=cssButton VALUE=Ͷ����У�� TYPE=button onclick='AppntChk();'>
			<input id="demo2" name="InsuredChkButton" style = "display: " class=cssButton VALUE=������У�� TYPE=button onclick='InsuredChk();' disabled="disabled">
			<input class=cssButton id="intoriskbutton" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
		</td>
  </tr>
		<tr>
            <td>
			    <input class=cssButton VALUE="�������ѯ" name="ApproveQuestQuery" id="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">
				<!--INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();"-->
				<!--INPUT class=cssButton id="Donextbutton6" VALUE="��һ��" TYPE=button onclick="intoInsured();"-->
				<input class=cssButton name="NotePadButton5" id="NotePadButton5" VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
				<input class=cssButton id="riskbutton5" VALUE="ǿ�ƽ����˹��˱�" TYPE=button onclick="forceUW();">
				<input class=cssButton id="Donextbutton4" VALUE=" ������� " TYPE=button onclick="inputConfirm(2);">
				<!--<input class="cssButton" id="exitButton" value="  ����  " type="button" onclick="exitAuditing();">-->
		</td></tr>
	</table>
</div>
<!--
  ###############################################################################
                              �˱��޸�Ͷ������ť 
  ###############################################################################
-->

    <div id = "divchangplan" style="display:none;float:left">
    		<input id="sqButton3" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
      <!--input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <input class=cssButton VALUE=Ͷ����У�� TYPE=button onclick='AppntChk();'>
      <input class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();"-->
      <input class=cssButton id="intoriskbutton3" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
      <!--input class=cssButton id="riskbutton34" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();"-->
    </div>
    
<!--
  ###############################################################################
                              �ŵ������޸İ�ť 
  ###############################################################################
-->

    <div id = "divApproveModifyContButton" style = "display:none;float:right">
        <input class=cssButton id="intoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
        	<input id="sqButton4" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
        <input class=cssButton id="riskbutton24" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">
    	<input class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
    	<input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
        <input class=cssButton VALUE=������ظ� TYPE=button onclick="QuestQuery();">
        <input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
        <input id="demo5" name="AppntChkButton3" style = "display: " class=cssButton VALUE=Ͷ����У�� TYPE=button onclick='AppntChk();'>
        <input id="demo6" name="InsuredChkButton3" style = "display: " class=cssButton VALUE=������У�� TYPE=button onclick='InsuredChk();' disabled="disabled">
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
			<input id="sqButton5" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
			<input class=cssButton id="riskbutton24" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();"> 
			<input class=cssButton id="demo3" name="AppntChkButton2" VALUE=Ͷ����У�� TYPE=hidden onclick='AppntChk();'>
			<input class=cssButton id="demo4" name="InsuredChkButton2" VALUE=������У�� TYPE=hidden onclick='InsuredChk();'>
			<input class=cssButton name="NotePadButton3" id="NotePadButton3" VALUE="���±��鿴" TYPE=button onclick="showNotePad();"> 
			</td>
		</tr>
		<tr>
			<td>
            <input class=cssButton id="Donextbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();"> 
			<input class=cssButton VALUE="   ������ظ�   " TYPE=button onclick="QuestQuery();"-->
			<input class=cssButton VALUE="   �������ѯ   " TYPE=button onclick="QuestQuery();">
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
	    <input type="button" name="autoMoveInput" id="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" id="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input class=cssButton id="intoriskbutton5" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
      <input value="��  ��" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="11">
    </div>
<!--
  ###############################################################################
                              ��ѯͶ������ť 
  ###############################################################################
-->

    <div id = "divInputQuery" style="display:none;float:left">
    	<input id="sqButton6" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="signatureQuery(this);" cancut="0" codetype="PrtNo">
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
      <input type="hidden" name="SubMissionID" id="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" id="ActivityID" value= "">
      <input type="hidden" name="NoType" id="NoType" value= "">
      <input type="hidden" name="PrtNo2" id="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" id="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" id="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" id="SelPolNo" value="">
      <input type="hidden" name="BQFlag" id="BQFlag">  
      <input type='hidden' name="MakeDate" id="MakeDate">
      <input type='hidden' name="MakeTime" id="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
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
<br>
<br>
<br>
<br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
