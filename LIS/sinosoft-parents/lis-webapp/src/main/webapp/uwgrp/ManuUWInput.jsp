<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ManuUWInput.jsp.
//�����ܣ�����Լ�����˹��˱�
//�������ڣ�2005-12-29 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPrtNo = "";
	String tContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tNoType = "";
	String tReportFlag="";

	tPrtNo = request.getParameter("PrtNo");
	tContNo = request.getParameter("ContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
  tActivityID = request.getParameter("ActivityID");
  tNoType = request.getParameter("NoType");
	session.putValue("ContNo",tContNo);
	tReportFlag = request.getParameter("ReportFlag");
%>
<html>
<%
  //�����¸���
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var PrtNo = "<%=tPrtNo%>";
	var ActivityID = "<%=tActivityID%>";
	var NoType = "<%=tNoType%>";
	var uwgrade = "";
    var appgrade= "";
    var uwpopedomgrade = "";
    var codeSql = "code";
    var ReportFlag = "<%=tReportFlag%>";
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="ManuUW.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./ManuUWCho.jsp">
    <!-- ������ѯ���� -->
    <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>�������ѯ������</td>
	</tr>
    </table>
    <table  class= common align=center>
      	<tr  class= common>
          <td  class= title>Ͷ������</TD>
          <td  class= input> <Input class= common name=QProposalNo > </TD>
          <td  class= title>ӡˢ����</TD>
          <td  class= input> <Input class=common name=QPrtNo> </TD>
          <td  class= title> �������  </TD>
          <td  class= input>  <Input class="code" name=QManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
        </TR>
        <tr  class= common>
          <td  class= title> �˱�����  </TD>
          <td  class= input>  <Input class="code" name=UWGradeStatus value= "1" CodeData= "0|^1|��������^2|�¼�����" ondblClick="showCodeListEx('Grade',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Grade',[this,''],[0,1]);">  </TD>
          <td  class= title>  Ͷ�������� </TD>
          <td  class= input> <Input class=common name=QAppntName > </TD>
          <td  class= title>  �˱���  </TD>
          <td  class= input>   <Input class= common name=QOperator value= "">   </TD>
        </TR>
        <tr  class= common>
          <td  class= title>  ����״̬ </TD>
          <td  class= input>  <Input class="code" readonly name=State value= "" CodeData= "0|^1|δ�˹��˱�^2|�˱��ѻظ�^3|�˱�δ�ظ�" ondblClick="showCodeListEx('State',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('State',[this,''],[0,1]);"> </TD>

        </TR>
        <tr  class= common>
          <td  class= input>   <Input  type= "hidden" class= Common name = QComCode >  </TD>

       </TR>
    </table>
          <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
          <!--INPUT VALUE="���������ѯ" class=common TYPE=button onclick="withdrawQueryClick();"-->
          <INPUT type= "hidden" name= "Operator" value= "">
    </div>
    <!--��ͬ��Ϣ-->
	<DIV id=DivLCContButton STYLE="display:'none'">
	<table id="table1">
			<tr>
				<td>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">��ͬ��Ϣ
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" STYLE="display:'none'">
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>Ͷ��������
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="PrtNo" VALUE CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>�������
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom"  MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title" nowrap>��������
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" CLASS="readonly" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">ҵ��Ա����
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">��������
	    		</td>
				<td CLASS="input" COLSPAN="3">
					<input NAME="Remark" CLASS="readonly" readonly MAXLENGTH="255">
	    		</td>
			</tr>
			  <input NAME="ProposalContNo" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
				<input NAME="AgentGroup" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
				<input NAME="AgentCode1" type=hidden MAXLENGTH="10" CLASS="readonly" readonly>
				<input NAME="AgentCom" type=hidden CLASS="readonly" readonly>
				<input NAME="AgentType" type=hidden CLASS="readonly" readonly>
		</table>
	</div>
	<div id=DivLCSendTrance STYLE="display:'none'">
		<table class = "common">
				<tr CLASS="common">
			    <td CLASS="title">�ϱ���־
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendFlag" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">�˱�����
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWFlag" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">�˱����
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWIdea" CLASS="readonly" readonly>
	    		</td>
			</tr>
		</table>
	</div>
<hr>
<table>
  <tr>
    <td nowrap>
    <INPUT VALUE=" Ͷ������ϸ��ѯ " class=cssButton TYPE=button onclick="showPolDetail();">
    <INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=cssButton  TYPE=button onclick="ScanQuery();">
    <INPUT VALUE="�Զ��˱���ʾ��Ϣ" class=cssButton TYPE=button onclick="showNewUWSub();">
    <INPUT VALUE=" �������Ϣ��ѯ " class=cssButton TYPE=button onclick="QuestQuery()">
    <INPUT VALUE="���񽻷���Ϣ��ѯ" class=cssButton TYPE=button onclick="showTempFee();">
    </td>
  </tr>
  <tr>
    <td>
    <INPUT VALUE="Ͷ������������ѯ" class=cssButton TYPE=button onclick="QueryRecord()">
    <INPUT VALUE="    �˱���ѯ    " class=cssButton TYPE=button onclick="UWQuery()">
    </td>
  </tr>
</table>
<hr>
	<DIV id=DivLCAppntIndButton STYLE="display:'none'">
	<!-- Ͷ������Ϣ���� -->
	<table>
	<tr>
	<td>
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
	</td>
	<td class= titleImg>
	Ͷ������Ϣ
	</td>
	</tr>
	</table>
	</DIV>

	<DIV id=DivLCAppntInd STYLE="display:'none'">
	 <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly" readonly name="AppntName" value="">
	          </TD>
	          <td  class= title>
	            �Ա�
	          </TD>
	          <td  class= input>
	            <Input name=AppntSex CLASS="readonly" >
	          </TD>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="AppntBirthday">
	          </TD>
	        </TR>

	        <tr  class= common>
	            <Input CLASS="readonly" readonly type=hidden name="AppntNo" value="">
	            <Input CLASS="readonly" readonly  type=hidden name="AddressNo">
	          <td  class= title>
	            ְҵ
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="OccupationCode">
	          </TD>
	          <td  class= title>
	            ְҵ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="OccupationType">
	          </TD>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="NativePlace">
	          </TD>
	        </TR>
	        <tr>
	        	<td  class= title>
           	 VIP���
          	</TD>
          	<td  class= input>
            	<Input class="readonly" readonly name=VIPValue >

          	</TD>
	        <td  class="title" nowrap>
            	���������
          	</TD>
         	 <td  class= input>
            	<Input class="readonly" readonly name=BlacklistFlag >

          	</TD>
	        </TR>
	      </table>
	</DIV>
    <!-- ������ѯ������֣��б� -->
   <DIV id=DivLCContInfo STYLE="display:''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������ѯ���
    		</td>
    	</tr>
    </table>
    </Div>
         <Div  id= "divLCPol1" style= "display: ''" align = center>
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getNextPage();">
      <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="getLastPage();">
    </div>
<hr>
<table>
  <tr>
    <td nowrap>
    <!--INPUT VALUE=" Ͷ���˼���Ͷ����Ϣ " class=cssButton TYPE=button onclick="showApp(1);"-->
    <INPUT VALUE=" Ͷ���˽�����֪��ѯ " class=cssButton TYPE=button onclick="queryHealthImpart()">
    <INPUT VALUE=" Ͷ����������ϲ�ѯ " class=cssButton TYPE=button onclick="showBeforeHealthQ()">
    <INPUT VALUE=" Ͷ���˱����ۼ���Ϣ " class=cssButton TYPE=button onclick="amntAccumulate();">
    <!--<INPUT VALUE=" Ͷ����Ӱ�����ϲ�ѯ " class=cssButton TYPE=button onclick=""> -->
    </td>
  </tr>
  <tr>
    <td nowrap>
    <INPUT VALUE="Ͷ�����ѳб�������ѯ" class=cssButton TYPE=button onclick="queryProposal();">
    <INPUT VALUE="Ͷ����δ�б�������ѯ" class=cssButton TYPE=button onclick="queryNotProposal();">
    <INPUT VALUE=" Ͷ���˼�����ȫ��ѯ " class=cssButton TYPE=button onclick="queryEdor()">
    <INPUT VALUE=" Ͷ���˼��������ѯ " class=cssButton TYPE=button onclick="queryClaim();">
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
    <!--INPUT VALUE="��ͥ����Ͷ����Ϣ" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="�����˼���Ͷ����Ϣ" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="�����˱���¼" class=common TYPE=button onclick="showOldUWSub();"-->
  <hr></hr>
   <Div  id= "divMain" style= "display: 'none'">
    <!--������-->

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	    </tr>
        </table>
        <Div  id= "divLCPol2" style= "display: 'none'" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>

    </Div>
<hr>
<table class= common border=0 width=100% >

	  <div id=divUWButton1 style="display:''">
<table>
  <tr>
    <td nowrap>
          <input value=" ���֪ͨ¼�� " class=cssButton type=button onclick="showHealth();" width="200">
          <input value=" ��������¼�� " class=cssButton type=button onclick="showRReport();">

          <input value="  �б��ƻ����  " class=cssButton type=button onclick="showChangePlan();">
          <input value="�б��ƻ��������¼��" class=cssButton type=button onclick="showChangeResultView();">
          <input value="  �ٱ��ظ���Ϣ  " class=cssButton type=button onclick="showUpReportReply();" width="200">
    </td>
  </tr>
  <tr>
    <td nowrap>
          <input value=" ��ѯ����� " class="cssButton" type="button" onclick="queryHealthReportResult();">
          <input value=" ��ѯ������� " class="cssButton" type="button" onclick="queryRReportResult();">
          <input value="  ���˱�֪ͨ��  " class = cssButton type = button onclick="SendAllNotice()">
          <!--input value="   ��   ��   ��     " class=cssButton type=button onclick="showNotePad();" width="200"-->
         <!--edit by yaory-->
          <input value="     �� �� �� ��    " id="ReDistribute"  name="ReDistribute" class=cssButton type=button onclick="UWUpReport();" width="200">
    </td>
  </tr>

  <tr>
  	<td nowrap>
  		<input value=" �ͻ�Ʒ�ʹ��� " class=cssButton type=button onclick="CustomerQuality();" >
  		<input value="ҵ��ԱƷ�ʹ���" class=cssButton type=button onclick="AgentQuality();" >
  		<input value="�޸ı�����Ч����" class=cssButton type=button onclick="ChangeCvalidate();" >
  	 </td>
  </tr>
</table>
<hr>
          <span id= "divAddButton1" style= "display: ''">
          	<!--input value="������ϲ�ѯ" class=common type=button onclick="showHealthQ();" width="200"-->
          </span>
          <!--input value="������" class=cssButton type=button onclick="showHealthQ();" -->
          <!--INPUT VALUE="��������" class=cssButton TYPE=button onclick="RReportQuery();"-->
          <!--INPUT VALUE="��ȫ��ϸ��ѯ" class= common TYPE=button onclick="Prt();"-->
          <!--INPUT VALUE="��ȫ��Ŀ��ѯ" class= common TYPE=button onclick="ItemQuery();"-->
          <!--INPUT VALUE="�����¼��" class=cssButton TYPE=button onclick="QuestInput();"-->
          <!--input value="���������" class=cssButton type=button onclick="QuestBack();"-->
          <!--input value="�������ѯ" class=cssButton type=button onclick="QuestQuery();"-->
          <!--input value="��֪����" class=cssButton type=button onclick="ImpartToICD();"-->
          <!--INPUT  VALUE="���������ѯ" class=cssButton TYPE=button onclick="ClaimGetQuery();"-->

          <span  id= "divAddButton2" style= "display: ''">
          	<!--INPUT VALUE="��������ѯ" class=common TYPE=button onclick="RReportQuery();"-->
          </span>

          <span  id= "divAddButton3" style= "display: ''">
          	<!--input value="���������ѯ" class=common type=button onclick="BackPolQuery();"-->
          </span>
          <span  id= "divAddButton4" style= "display: ''">
          	<!--input value="�߰��ѯ" class=common type=button onclick="OutTimeQuery();"-->
          </span>

          <span  id= "divAddButton5" style= "display: ''">
          <!--input value="�������¼��" class=common type=button onclick="showHealth();" width="200"-->
          </span>


          <span  id= "divAddButton6" style= "display: ''">
          	<!--input value="��������˵��" class=common type=button onclick="showRReport();"-->
          </span>

        <br></br>

           <!--input value="���˱�֪ͨ��" class=cssButton type=button onclick="SendNotice();"-->
           <!--input value="�������֪ͨ��" class=cssButton type=button onclick="SendIssue();"-->
            <!--input value="���ܱ�֪ͨ��" class=cssButton type=button onclick="SendRanNotice();"-->
           <!--input value="������֪ͨ��" class=cssButton type=button onclick="SendDanNotice();"-->
           <!--input value="���ӷ�֪ͨ��" class = cssButton type= button onclick="SendAddNotice()"-->

          <!--tr> <hr> </hr>  </tr-->
          <span  id= "divAddButton7" style= "display: ''">

          <!--input value="�����ڽ���֪ͨ��" class=cssButton type=button onclick="SendFirstNotice();"-->
          </span>
          <!--<input value="���߰�֪ͨ��" type=button onclick="SendPressNotice();">-->
          <span  id= "divAddButton8" style= "display: ''">
          <!--input value="���˱�֪ͨ��" class=common type=button onclick="SendNotice();">-->
          </span>
          <!--input value="�����֪ͨ��" class=common type=button onclick="SendHealth();"-->
          <!--input value="�˱���������¼��" class=cssButton type=button onclick="showReport();" width="200"-->
           <span  id= "divAddButton9" style= "display: ''">

      </div>
    </table>
    	  <input type="hidden" name= "PrtNoHide" value= "">
    	  <input type="hidden" name= "PolNoHide" value= "">
    	  <input type="hidden" name= "MainPolNoHide" value= "">
    	  <input type="hidden" name= "NoHide" value= "">
    	  <input type="hidden" name= "TypeHide" value= "">
          <INPUT  type= "hidden" class= Common name= UWGrade value= "">
          <INPUT  type= "hidden" class= Common name= AppGrade value= "">
          <INPUT  type= "hidden" class= Common name= PolNo  value= "">
          <INPUT  type= "hidden" class= Common name= "ContNo"  value= "">
          <INPUT  type= "hidden" class= Common name= "YesOrNo"  value= "">
          <INPUT  type= "hidden"  class= Common name= "MissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubMissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "ActivityID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "NoType"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubConfirmMissionID"  value= "">
          <INPUT  type= "hidden" class= Common name= SubNoticeMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= UWPopedomCode value= "">

    <div id = "divUWResult" style = "display: ''">
        <!-- �˱����� -->
        <table class=common >
            <tr><td class= titleImg align= center>�˱����ۣ�</td></tr>
            <!--
            <tr class = common>
                <td class=title >�������</td>
                <td class =input><Input class= common name="SugIndUWFlag"  ></td>
            </tr>
            -->

            <tr>
                <td class= title>
                    <!--span id= "UWResult"> ��ȫ�˱����� <Input class="code" name=uwstate value= "1" CodeData= "0|^1|��������^2|�¼�����" ondblClick="showCodeListEx('uwstate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('uwstate',[this,''],[0,1]);">  </span-->
                    �˱�����
                </td>

                <td class=input>
                    <Input class=codeno name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly elementtype=nacessary>
                </td>

                <td class=title8>
                    �˱�����
                </td>

                <td class=input8>
                    <Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary>
                </td>

            </TR>
        </table>
    <table class=common>
		<tr>
		      	<td height="24"  class= title>
            		�˱����
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<td  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td>
      			<input name=uwPopedom type=hidden>

      			</td>
      		</tr>
	  </table>
</div>

        <!--****************************************************************
            �޸��ˣ����Σ��޸�ʱ��20050420���޸�ԭ�򣺳ʱ�
            ****************************************************************
        -->
<!--
        <div id = "divUWup" style = "display: ''">

          <table  class= common align=center>
              <TR>


                  <td class=title8>
                      �˱�ʦ����
                  </td>


                  <td class=input8>
                      <Input class="codeno" name=uwPopedom ondblclick="return showCodeList('uwPopedom',[this,uwPopedomname],[0,1]);" onkeyup="return showCodeListKey('uwPopedom',[this,uwPopedomname],[0,1]);"><Input class="codename" name=uwPopedomname readonly>
                  </td>
                  <td class=title></td>
                  <td class=input></td>
                  <td class=title>
                      �˱�ʦ
                  </td>

                  <td class=input>
                      <Input class="codeno" name=uwPer ondblclick="return showCodeList('uwper',[this,uwPername],[0,1]);" onkeyup="return showCodeListKey('uwper',[this,uwPername],[0,1]);"><Input class="codename" name=uwPername readonly>
                  </td>
              </TR>
          </table>
<!--
  </div>
-->

<div id = "divSugUWResult" style = "display: 'none'">
  <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>����˱����ۣ�</td>
	  </tr>
	</table>
  <table class=common>
    	<tr class = common>
				<td class=title nowrap >
					�������
				</td>
				<td class =input>
					<Input class=codeno ondblclick="return showCodeList('contuwstate',[this,SugIndUWFlagName],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,SugIndUWFlagName],[0,1]);" name="SugIndUWFlag"><input type="text" class="codename" name="SugIndUWFlagName" readonly="readonly">
				</td>
				</tr><tr class="common">
				<td class=title nowrap >
					����˱����
				</td>
				<td class =input colspan=3>
					<!--Input class= common name="SugIndUWIdea"  -->
          <textarea name="SugIndUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea>
        </td>
	  	</tr>
  	</table>
<!--
  	<table  class= common align=center>

    	  	<tr  class= common>
          		<td height="29"  class= title>
          		 	<span id= "UWResult">�˱�����<Input class="code" name=uwstate ondblclick="return showCodeList('uwstate',[this]);" onkeyup="return showCodeListKey('uwstate',[this]);">  </span>
	   			      <span id= "UWDelay" style = "display: 'none'">�ӳ�ʱ��<Input class="common" name=UWDelay value= ""> </span>
	   		        <span id= "UWPopedom" style = "display: 'none'">�ϱ��˱�<Input class="code" name=UWPopedomCode ondblclick="showCodeList('UWPopedomCode1', [this]);" onkeyup="return showCodeListKey('UWPopedomCode1', [this]);"> </span>
	   		 	    <span id= "Delay" style = "display: 'none'">�����˱�ʦ<Input class="common" name=UWOperater value= ""> </span>
	   		 	   </TD>
          </TR>
		<tr></tr>
          	<td height="24"  class= title>
            		�˱����
          	</TD>
		<tr></tr>
      		<td  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
	  </table>
-->

</div>
  	<div id = "divUWSave" style = "display: ''">
    		<INPUT VALUE="ȷ  ��" class=cssButton id="button1" style="display:" TYPE=button onclick="submitForm(0);">
    		<INPUT VALUE="�����¼�" class=cssButton id="button2" style="display:none" TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="cancelchk();">
        <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="InitClick();">
        <INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
    </div>
  	<div id = "divUWAgree" style = "display: 'none'">
    		<INPUT VALUE="ͬ  ��" class=cssButton TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="��ͬ��" class=cssButton TYPE=button onclick="submitForm(2);">
    		<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="InitClick();">
  	</div>

    <div id = "divChangeResult" style = "display: 'none'">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		�б��ƻ��������¼��:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="ChangeIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
    	 <INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="showChangeResult();">
    	 <INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="HideChangeResult();">
    </div>
  </Div>
  <table  class= common align=center>

	</table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
