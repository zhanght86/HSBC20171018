<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ManuUWInput.jsp.
//�����ܣ�����Լ�����˹��˱�
//�������ڣ�2005-12-29 11:10:36
//������  ��HYQ
//���¼�¼��  ������  ln  ��������  2008-11-04   ����ԭ��/����
%> 
<%
	String tPrtNo = "";
	String tContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tNoType = "";
	
	tPrtNo = request.getParameter("PrtNo");
	tContNo = request.getParameter("ContNo"); 
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
  tActivityID = request.getParameter("ActivityID");
  tNoType = request.getParameter("NoType");
	session.putValue("ContNo",tContNo);
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
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="UWUpReportDealEach.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWUpReportDealEachInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./ManuUWCho.jsp">
    <!-- ������ѯ���� -->
    <div style="display:none" id="divSearch">
    <table class= common>
    	<tr class= common>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);"></td>
		<td class= titleImg>�������ѯ������</td>
	</tr>
    </table><Div  id= "asd" style= "display: ''" class="maxbox">
    <table  class= common>
      	<tr  class= common>
          <td  class= title>Ͷ������</TD>
          <td  class= input> <Input class="wid" class= common name=QProposalNo id=QProposalNo > </TD>
          <td  class= title>ӡˢ����</TD>
          <td  class= input> <Input class="wid" class=common name=QPrtNo id=QPrtNo> </TD>          
          <td  class= title> �������  </TD>
          <td  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=QManageCom id=QManageCom ondblclick="return showCodeList('station',[this]);" onclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
        </TR>
        <tr  class= common>
          <td  class= title> �˱�����  </TD>
          <td  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=UWGradeStatus id=UWGradeStatus value= "1" CodeData= "0|^1|��������^2|�¼�����" ondblClick="showCodeListEx('Grade',[this,''],[0,1]);" onClick="showCodeListEx('Grade',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Grade',[this,''],[0,1]);">  </TD>
          <td  class= title>  Ͷ�������� </TD>
          <td  class= input> <Input class="wid" class=common name=QAppntName id=QAppntName > </TD>  
          <td  class= title>  �˱���  </TD>
          <td  class= input>   <Input class="wid" class= common name=QOperator id=QOperator value= "">   </TD>     
        </TR>
        <tr  class= common>
          <td  class= title>  ����״̬ </TD>
          <td  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" readonly name=State id=State value= "" CodeData= "0|^1|δ�˹��˱�^2|�˱��ѻظ�^3|�˱�δ�ظ�" ondblClick="showCodeListEx('State',[this,''],[0,1]);" onClick="showCodeListEx('State',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('State',[this,''],[0,1]);"> </TD>

        </TR>
        <tr  class= common>
          <td  class= input>   <Input  type= "hidden" class= Common id="QComCode" name = QComCode >  </TD>

       </TR>
    </table></div>
          <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 
          <!--INPUT VALUE="���������ѯ" class=common TYPE=button onclick="withdrawQueryClick();"--> 
          <INPUT type= "hidden" id="Operator" name= "Operator" value= ""> 
    </div>
    <!--��ͬ��Ϣ-->
	<DIV id=DivLCContButton STYLE="display:''">
	<table id="table1">
			<tr>
				<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">��ͬ��Ϣ
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" STYLE="display:''" class="maxbox">
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>ӡˢ�� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="PrtNo" id="PrtNo" VALUE CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="ManageCom" id="ManageCom"  MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title" nowrap>�������� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="SaleChnl" id="SaleChnl" CLASS="readonly" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">ҵ��Ա���� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="AgentCode" id="AgentCode" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>			
			</tr>
			 <table class=common>
         <TR  class= common> 
           <TD  class= title> �ʱ�ԭ������ </TD>
         </TR>
         <TR  class= common>
           <TD  colspan="6" style="padding-left:16px">
             <textarea name="ReportRemark" id="ReportRemark" cols="226" rows="4" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
			  <input id="ProposalContNo" NAME="ProposalContNo" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
				<input id="AgentGroup" NAME="AgentGroup" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
				<input id="AgentCode1" NAME="AgentCode1" type=hidden MAXLENGTH="10" CLASS="readonly" readonly>
				<input id="AgentCom" NAME="AgentCom" type=hidden CLASS="readonly" readonly>
				<input id="AgentType" NAME="AgentType" type=hidden CLASS="readonly" readonly>
		</table>
	</div>
	<div id=DivLCSendTrance STYLE="display:none">
		<table class = "common">
				<tr CLASS="common">
			    <td CLASS="title">�ϱ���־ 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="SendFlag" id="SendFlag" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">�˱����� 
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="SendUWFlag" id="SendUWFlag" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">�˱����
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input class="wid" NAME="SendUWIdea" id="SendUWIdea" CLASS="readonly" readonly>
	    		</td>
			</tr>
		</table>
	</div>

<table>
  <tr>
    <td nowrap>
    <INPUT VALUE=" Ͷ������ϸ��ѯ " class=cssButton TYPE=button id="Button1" name="Button1" onclick="showPolDetail();">
    <INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button id="Button2" name="Button2" onclick="ScanQuery();">
    <INPUT VALUE="  Ӱ����������  " class=cssButton TYPE=button id="Button19" name="Button19" onclick="imageDownload();">
    <INPUT VALUE="Ͷ������������ѯ"  class=cssButton TYPE=button id="Button3" name="Button3" onclick="OperationQuery();">
    <INPUT VALUE="    �˱���ѯ    " class=cssButton TYPE=button id="Button4" name="Button4" onclick="UWQuery();">
      
    </td>
  </tr>
 
</table>

	<DIV id=DivLCAppntIndButton STYLE="display:none">
	<!-- Ͷ������Ϣ���� -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
	</td>
	<td class= titleImg>
	Ͷ������Ϣ
	</td>
	</tr>
	</table>
	</DIV>
	
	<DIV id=DivLCAppntInd STYLE="display:none" class="maxbox">
	 <table  class= common>
	        <tr  class= common>        
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	            <Input class="wid" CLASS="readonly" readonly name="AppntName" id="AppntName" value="">
	          </TD>
	          <td  class= title>
	            �Ա�
	          </TD>
	          <td  class= input>
	            <Input class="wid" name=AppntSex id=AppntSex CLASS="readonly" >
	          </TD>
	          <td  class= title>
	            ��������
	          </TD>
	          <td  class= input>
	            <input class="wid" CLASS="readonly" readonly name="AppntBirthday" id="AppntBirthday">
	          </TD>
	        </TR>
          <tr class="common">
	          <td  class= title>
	            ֤������
	          </TD>
	          <td  class= input>
	            <input class="wid" CLASS="readonly" readonly name="AppntIDType" id="AppntIDType">
	          </TD>
	          <td  class= title>
	            ֤������
	          </TD>
	          <td  class= input>
	            <input class="wid" CLASS="readonly" readonly name="AppntIDNo" id="AppntIDNo">
	          </TD>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="AppntNativePlace" id="AppntNativePlace">
	          </TD>
          </tr>
	        <tr  class= common>
	            <Input CLASS="readonly" readonly type=hidden id="AppntNo" name="AppntNo" value="">
	            <Input CLASS="readonly" readonly  type=hidden id="AddressNo" name="AddressNo">
	          <td  class= title>
	            ְҵ����
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="AppntOccupationCode" id="AppntOccupationCode">
	          </TD>	
	          <td  class= title>
	            ְҵ�ȼ�
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="AppntOccupationType" id="AppntOccupationType">
	          </TD>	            
	        	<td  class= title>
           	 VIP���
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name="VIPValue" id="VIPValue" >
            	
          	</TD>
	        </TR>
	        <tr>
	        <td  class="title" nowrap>
            	���������
          	</TD>
         	 <td  class= input>
            	<Input class="wid" class="readonly" readonly name="BlacklistFlag" id="BlacklistFlag" >
            
          	</TD>	        
	        	<td  class= title>
	            �ۼ����ձ���
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="AppntSumLifeAmnt" id="AppntSumLifeAmnt" >
	          </TD>
	        	<td  class= title>
           	 �ۼ��ش󼲲�����
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=AppntSumHealthAmnt id=AppntSumHealthAmnt >
          	</TD>
          	</TR>
	        <tr>
	         <td  class="title" nowrap>
            	�ۼ�ҽ���ձ���
           </TD>
         	 <td  class= input>
            	<Input class="wid" class="readonly" readonly name=AppntMedicalAmnt id=AppntMedicalAmnt >
           </TD>
	      	<td  class= title>
	            �ۼ������ձ���
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="AppntAccidentAmnt" id="AppntAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 �ۼƷ��ձ���
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=AppntSumAmnt id=AppntSumAmnt >
          	</TD>
          	</TR>
	        <tr>
          	<td  class= title>
           	 �ۼƱ���
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=SumPrem id=SumPrem >
          	</TD>
	        </tr>
	      </table>   
	</DIV>
    <!-- ������ѯ������֣��б� -->
   <DIV id=DivLCContInfo STYLE="display:none"> 
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
         <div  id= "divLCPol1" style= "display: none">
       		<table>
             <tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">  </center>    
    </div>

<table>
  <tr>
    <td nowrap>
   
    <INPUT VALUE=" Ͷ���˽�����֪��ѯ " class=cssButton TYPE=button id="Button5" name="Button5" onclick="queryHealthImpart();">
    <INPUT VALUE=" Ͷ���˼���Ͷ�����ϲ�ѯ " class=cssButton TYPE=button id="uwButton30" name="uwButton30" onclick="showApp(1)">
  <!-- <INPUT VALUE=" Ͷ����������ϲ�ѯ " class=cssButton TYPE=button name="Button6" onclick="showBeforeHealthQ();">
    <INPUT VALUE=" Ͷ���˱����ۼ���Ϣ " class=cssButton TYPE=button name="Button7" onclick="amntAccumulate();">
    <INPUT VALUE="Ͷ�����ѳб�������ѯ" class=cssButton TYPE=button name="Button8" onclick="queryProposal();">
    <INPUT VALUE="Ͷ����δ�б�������ѯ" class=cssButton TYPE=button name="Button9" onclick="queryNotProposal();">
    <INPUT VALUE=" Ͷ���˼�����ȫ��ѯ " class=cssButton TYPE=button name="Button10" onclick="queryEdor()">
    <INPUT VALUE=" Ͷ���˼��������ѯ " class=cssButton TYPE=button name="Button11" onclick="queryClaim();">
    -->
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
   
 
   <Div  id= "divMain" style= "display: none">
    <!--������-->
    <Div  id= "divLCPol2" style= "display: none" >
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
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         					
    </Div>

<DIV id=DivLCInsured STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
	        <td class= titleImg align= center>��������Ϣ��</td>
	     </tr>
    </table><Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
            <TR  class= common>
          <TD  class= title>
            �ͻ�����
          </TD>
          <TD  class= input>
            <Input class="wid" class="readonly" readonly name=InsuredNo id=InsuredNo elementtype=nacessary verify="������������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid" class="readonly" readonly name=Name id=Name elementtype=nacessary verify="������������|notnull&len<=20" >
          </TD>

          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="wid" readonly name=Sex id=Sex elementtype=nacessary verify="���������Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
            <TD CLASS=title>
                ��������
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input class="wid" class="readonly" readonly NAME=InsuredAppAge id=InsuredAppAge VALUE="" readonly=true >
            </TD> 
            <TD CLASS=title>
                ֤������
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input class="wid" class="readonly" readonly NAME=InsuredIDType id=InsuredIDType VALUE="" readonly=true >
            </TD> 
            <TD CLASS=title>
                ֤������
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input class="wid" class="readonly" readonly NAME=InsuredIDNo id=InsuredIDNo VALUE="" readonly=true >
            </TD> 
        </TR>
        <TR  class= common>
            <TD CLASS=title>
                ����
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input class="wid" NAME=NativePlace id=NativePlace readonly VALUE="" readonly=true class="readonly" >
            </TD>                    
	          <TD  class= title id=MainInsured style="display:">�����������˹�ϵ</TD>
            <TD  class=input>    
              <Input class="wid" class="readonly" readonly name="RelationToMainInsured" id="RelationToMainInsured"  >
            </TD>
            <TD  class="title" id=MainAppnt>��Ͷ���˹�ϵ</TD>
            <TD  class="input">
              <Input class="wid" class="readonly" readonly name="RelationToAppnt" id="RelationToAppnt">
            </TD>    
      </TR>
	    <TR class= common>
            <TD  class= title>
                ְҵ����
            </TD>
            <TD  class= input>
                <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="wid" readonly name="OccupationCode" id="OccupationCode"  elementtype=nacessary verify="��������ְҵ����|code:OccupationCode&notnull" ondblclick="return showCodeList('OccupationCode',[this]);" onclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);" onfocus="getdetailwork();">
            </TD>
            <TD  class= title>
                ְҵ���
            </TD>
            <TD  class= input>
                <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="wid" readonly name="OccupationType"  id="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
            </TD>
            <td  class= title>
	            �ۼ����ձ���
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="InsuredSumLifeAmnt" id="InsuredSumLifeAmnt" >
	          </TD>
	        </tr>
            <tr>
	        	<td  class= title>
           	 �ۼ��ش󼲲�����
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=InsuredSumHealthAmnt id=InsuredSumHealthAmnt >
          	</TD>          
	         <td  class="title" nowrap>
            	�ۼ�ҽ���ձ���
           </TD>
         	 <td  class= input>
            	<Input class="wid" class="readonly" readonly name=InsuredMedicalAmnt id=InsuredMedicalAmnt >
           </TD>	
	      	<td  class= title>
	            �ۼ������ձ���
	          </TD>
	          <td  class= input>
	          <input class="wid" CLASS="readonly" readonly name="InsuredAccidentAmnt" id="InsuredAccidentAmnt" >
	          </TD>
	       </tr>
           <tr>
	        	<td  class= title>
           	 �ۼƷ��ձ���
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=InsuredSumAmnt id=InsuredSumAmnt >
          	</TD>
	      	<td  class= title>
           	 �ۼƱ���
          	</TD>
          	<td  class= input>
            	<Input class="wid" class="readonly" readonly name=InsuredSumPrem id=InsuredSumPrem >
          	</TD>
	        </tr>
            
		</Table>
</DIV>
<DIV id=DivLCPol STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>������Ϣ��</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanRiskGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>

<table>
  <tr>
    <td>
    <!--INPUT VALUE="  �����˼���Ͷ����Ϣ  " class=cssButton TYPE=button onclick="showApp(1);"--> 
    <!--<INPUT VALUE="  ������Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button onclick=""> -->
    <INPUT VALUE="  �����˽�����֪��ѯ  " class=cssButton TYPE=button id="Button12" name="Button12" onclick="queryHealthImpart2();">
    <INPUT VALUE="  �����˼���Ͷ�����ϲ�ѯ  " class=cssButton TYPE=button id="indButton2" name="indButton2" onclick="showApp(2);">
  <!--   <INPUT VALUE="  ������������ϲ�ѯ  " class=cssButton TYPE=button name="Button13" onclick="queryHealthReportResult2();">
    <INPUT VALUE="�����˱����ۼ���ʾ��Ϣ" class=cssButton TYPE=button name="Button14" onclick="amntAccumulate2();">
    <INPUT VALUE=" �������ѳб�������ѯ " class=cssButton TYPE=button name="Button15" onclick="queryProposal2();">
    <INPUT VALUE="������δ�б�Ͷ������ѯ" class=cssButton TYPE=button name="Button16" onclick="queryNotProposal2();">
    <INPUT VALUE="�����˼�����ȫ��Ϣ��ѯ" class=cssButton TYPE=button name="Button17" onclick="queryEdor2()">
    <INPUT VALUE="�����˼���������Ϣ��ѯ" class=cssButton TYPE=button name="Button18" onclick="queryClaim2()">
   -->
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>

<DIV id=Divtrace STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>�ٱ��켣��</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanTraceGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>

<!--
          <span id= "divAddButton1" style= "display: ''">
          
          </span>
       
          
          <span  id= "divAddButton2" style= "display: ''">
       
          </span>         
          
          <span  id= "divAddButton3" style= "display: ''">
          
          </span>
          <span  id= "divAddButton4" style= "display: ''">
          	
          </span>

          <span  id= "divAddButton5" style= "display: ''">
               
          </span>
          
          
          <span  id= "divAddButton6" style= "display: ''">
          
          </span>
          <span  id= "divAddButton7" style= "display: ''">
          
       
          </span>
        
          <span  id= "divAddButton8" style= "display: ''">
         
          </span>
                            
           <span  id= "divAddButton9" style= "display: ''">
-->         
      </div>
    	  <input type="hidden" id="PrtNoHide" name= "PrtNoHide" value= "">
    	  <input type="hidden" id="PolNoHide" name= "PolNoHide" value= "">
    	  <input type="hidden" id="MainPolNoHide" name= "MainPolNoHide" value= "">
    	  <input type="hidden" id="NoHide" name= "NoHide" value= "">
    	  <input type="hidden" id="TypeHide" name= "TypeHide" value= "">
          <INPUT  type= "hidden" class= Common id="UWGrade" name= UWGrade value= "">
          <INPUT  type= "hidden" class= Common id="AppGrade" name= AppGrade value= "">
          <INPUT  type= "hidden" class= Common id="PolNo" name= PolNo  value= "">
          <INPUT  type= "hidden" class= Common id="ContNo" name= "ContNo"  value= "">
          <INPUT  type= "hidden" class= Common id="YesOrNo" name= "YesOrNo"  value= "">
          <INPUT  type= "hidden"  class= Common id="MissionID" name= "MissionID"  value= "">
          <INPUT  type= "hidden"  class= Common id="SubMissionID" name= "SubMissionID"  value= "">
          <INPUT  type= "hidden"  class= Common id="ActivityID" name= "ActivityID"  value= "">
          <INPUT  type= "hidden"  class= Common id="NoType" name= "NoType"  value= "">
          <INPUT  type= "hidden"  class= Common id="SubConfirmMissionID" name= "SubConfirmMissionID"  value= "">
          <INPUT  type= "hidden" class= Common id="SubNoticeMissionID" name= SubNoticeMissionID  value= "">
          <INPUT  type= "hidden" class= Common id="UWPopedomCode" name= UWPopedomCode value= "">
          
    <div id = "divUWResult" style = "display: ''">
        <!-- �˱����� -->
        <table class=common >
            <tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,poop);"></td>
            <td class= titleImg align= center>�˱����ۣ�</td></tr>
        </table>
	   		<Div  id= "poop" style= "display: ''" class="maxbox"> 	   	
        <table  class=common>
            <tr>
                <td class= title>
                    �ٱ�����
                </td>
                <td class=input>
                    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=ReinsuredResult id=ReinsuredResult ondblclick="return showCodeList('uqreportstate',[this,uwStatename],[0,1]);" onclick="return showCodeList('uqreportstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('uqreportstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id=uwStatename readonly elementtype=nacessary>                    
                </td>
                <td class= title></td>
               <td class=input></td>
                <td class= title></td>
                <td class=input></td>
            </TR>               
        </table>            
            
        <!--****************************************************************
            �޸��ˣ����Σ��޸�ʱ��20050420���޸�ԭ�򣺳ʱ�
            ****************************************************************
        -->
            
        <div id = "divUWup" style = "display: ''">
          <table  class= common align=center>
                  
          </table>
        </div>
        
    <table class=common>
         <TR  class= common> 
           <TD  class= title> ԭ������ </TD>
         </TR>
         <TR  class= common>
           <TD  colspan="6" style="padding-left:16px">
             <textarea name="ReinsuDesc" id="ReinsuDesc" cols="226" rows="4" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>         
    
       <table class=common>
         <TR  class= common> 
           <TD  class= title> �ٱ���ע </TD>
         </TR>
         <TR  class= common>
           <TD  colspan="6" style="padding-left:16px">
             <textarea name="ReinsuRemark" id="ReinsuRemark" cols="226" rows="4" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
</div>
     
  	<div id = "divUWSave" style = "display: ''">
    		<INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="submitForm(0);">
    	    <INPUT VALUE="���±��鿴" class=cssButton  TYPE=button onclick="showNotePad();"> 
    </div>        
  	<div id = "divUWAgree" style = "display: none">
    		<INPUT VALUE="ͬ  ��" class=cssButton TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="��ͬ��" class=cssButton TYPE=button onclick="submitForm(2);">
    		<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="InitClick();">  		
  	</div>

    <div id = "divChangeResult" style = "display: none">
      	  <table  class= common><tr class= common>
          	<td class= title>
            		�б��ƻ��������¼��:
          	</TD>
		</tr>   <tr class= common>       
      		<td  colspan="6" style="padding-left:16px"><textarea name="ChangeIdea" id="ChangeIdea" cols="226%" rows="4" witdh=100% class="common"></textarea></TD></tr> 
    	 </table>
    	 <INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="showChangeResult();">
    	 <INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="HideChangeResult();"></div>
    
          
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
