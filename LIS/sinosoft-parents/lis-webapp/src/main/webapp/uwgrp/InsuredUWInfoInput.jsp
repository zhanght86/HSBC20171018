<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�InsuredUWInfoInput.jsp
//�����ܣ������˺˱���Ϣ����
//�������ڣ�2005-01-06 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var InsuredNo = "<%=request.getParameter("InsuredNo")%>";
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var SendFlag = "<%=request.getParameter("SendFlag")%>";
	
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="InsuredUWInfo.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="InsuredUWInfoInit.jsp"%>
  <title>��������Ϣ </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" action= "./InsuredUWInfoChk.jsp">
<DIV id=DivLCInsured STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>��������Ϣ��</td>
	     </tr>
    </table>
    <table  class= common>
            <TR  class= common>
          <TD  class= title>
            �ͻ�����
          </TD>
          <TD  class= input>
            <Input class= common name=InsuredNo elementtype=nacessary verify="������������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Name elementtype=nacessary verify="������������|notnull&len<=20" >
          </TD>

          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex elementtype=nacessary verify="���������Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
            <TD CLASS=title>
                ����������
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="����������|num" >
            </TD>  
            <TD CLASS=title>
                ����
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=NativePlace VALUE="" readonly=true CLASS=common >
            </TD>                    
           
           
            
              
	        <TD  class= title id=MainInsured style="display:">�����������˹�ϵ</TD>
            <TD  class= input id=MainInsuredInput style="display:">    
           <Input class="code" name="RelationToMainInsured"  verify="���������˹�ϵ|code:Relation" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>
	      
	      
	       
       </TR>
	    <TR class= common>
            <TD  class= title>
                ְҵ����
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationCode"  elementtype=nacessary verify="��������ְҵ����|code:OccupationCode&notnull" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);" onfocus="getdetailwork();">
            </TD>
            <TD  class= title>
                ְҵ���
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
            </TD>
            
             <TD  class= title id=MainAppnt   style="display:">��Ͷ���˹�ϵ</TD>
            <TD  class= input id=MainAppntInput   style="display:">
           <Input class="code" name="RelationToAppnt"  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>    
    
	    </TR>
	   
		</Table>
</DIV>
<TABLE class= common>
    <TR class= common>
        <TD  class= title>
            <DIV id="divContPlan" style="display:'none'" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            ���ռƻ�
                    </TD>
                    <TD  class= input>
                        <Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);">
                    </TD>

                    </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
            <DIV id="divExecuteCom" style="display:'none'" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            �������
                        </TD>
                        <TD  class= input>
                            <Input class="code" name="ExecuteCom" ondblclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],'', '', '', true);">
                        </TD>
		            </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
        </TD>
    </TR>
</TABLE>
<DIV id="divLCInsuredPerson" style="display:'none'">
  <TABLE class= common>
        <TR  class= common>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name="Marriage"  ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);"></TD>
           <!-- <Input class="code" name="Marriage" verify="�������˻���״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);"></TD>-->
          <TD  class= title>
            �뱻���˹�ϵ</TD>
          <TD  class= input>
            <Input class="code" name="TheRelationToAppnt"  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>
           <!-- <Input class="code" name="RelationToAppnt" verify="�뱻���˹�ϵ|code:Relation" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>-->
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="TheNativePlace"  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          <!--<input class="code" name="NativePlace" verify="�������˹���|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">-->
          </TD>
        </TR>

        <TR class= common>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class= common name="RgtAddress" >
            <!--<Input class= common name="RgtAddress" verify="�������˻������ڵ�|len<=80" >-->
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality"  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
         <!-- <input class="code" name="Nationality" verify="������������|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">-->
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree"  ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
            <!--<Input class="code" name="Degree" verify="��������ѧ��|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpName"  >
            <!--<Input class= common3 name="GrpName" verify="�������˹�����λ|len<=60" >-->
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone"  >
            <!--<Input class= common name="GrpPhone" verify="�������˵�λ�绰|len<=18" >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpAddress"  >
            <!--<Input class= common3 name="GrpAddress" verify="�������˵�λ��ַ|len<=80" >-->
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class= common name="GrpZipCode"  >
            <!-- <Input class= common name="GrpZipCode" verify="�������˵�λ��������|zipcode" >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="WorkType"  >
            <!--<Input class= common name="WorkType" verify="��������ְҵ�����֣�|len<=10" >-->
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="PluralityType"  >
            <!--<Input class= common name="PluralityType" verify="�������˼�ְ�����֣�|len<=10" >-->
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            <!--<Input class="code" name="SmokeFlag" verify="���������Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">-->
          </TD>
       </TR>
       <Input name=PolNo type=hidden>
       <Input name=ContNo type=hidden>
       <Input name=MissionID type=hidden>
       <Input name=SubMissionID type=hidden>
       <Input name=flag type=hidden>
       <Input name=PrtNo type=hidden>
       <Input name=Stature type=hidden>
       <Input name=Avoirdupois type=hidden>
       <Input name=BMI type=hidden>
      </table>
</DIV>
<DIV id=DivOldInfo STYLE="display:'none'">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>������Ϣ��</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanOldInfoGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>

<hr>
<table>
  <tr>
    <td>
    <!--INPUT VALUE="  �����˼���Ͷ����Ϣ  " class=cssButton TYPE=button onclick="showApp(1);"--> 
    <!--<INPUT VALUE="  ������Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button onclick=""> -->
    <INPUT VALUE="  �����˽�����֪��ѯ  " class=cssButton TYPE=button onclick="queryHealthImpart()">  
    <INPUT VALUE="  ������������ϲ�ѯ  " class=cssButton TYPE=button onclick="queryHealthReportResult();"> 
    <INPUT VALUE="�����˱����ۼ���ʾ��Ϣ" class=cssButton TYPE=button onclick="amntAccumulate();"> 
    </td>
  </tr>
  <tr>
    <td>
    <INPUT VALUE=" �������ѳб�������ѯ " class=cssButton TYPE=button onclick="queryProposal();"> 
    <INPUT VALUE="������δ�б�Ͷ������ѯ" class=cssButton TYPE=button onclick="queryNotProposal();"> 
    <INPUT VALUE="�����˼�����ȫ��Ϣ��ѯ" class=cssButton TYPE=button onclick="queryEdor()"> 
    <INPUT VALUE="�����˼���������Ϣ��ѯ" class=cssButton TYPE=button onclick="queryClaim()"> 
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
<hr>
<table>
<tr>
  <td>
    <input value="     �ӷѳб�¼��     " class=cssButton type=button name= "AddFee"  onclick="showAdd();">
    <input value="     ��Լ�б�¼��     " class=cssButton type=button onclick="showSpec();">
  </td>
</tr>
</table>

<hr></hr>
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

<div id = "divUWResult" style = "display: ''">
    	  <!-- �˱����� -->
    	  <table class= common border=0 width=100%>
    	  	<tr>
			<td class= titleImg align= center>���ֺ˱����ۣ�</td>
	  	</tr>
	  </table>
	   		 	   	
  	  <table  class="common">
    	  			
    	  	<TR >

          		<TD class= title>
          		 	<!--span id= "UWResult"> ��ȫ�˱����� <Input class="code" name=uwstate value= "1" CodeData= "0|^1|��������^2|�¼�����" ondblClick="showCodeListEx('uwstate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('uwstate',[this,''],[0,1]);">  </span-->
          		 	�˱�����
          		 </td>
          		 <td class=input>
          		 <Input class="codeno" name=uwstate ondblclick="return showCodeList('uwstate',[this,uwstatename],[0,1]);" onkeyup="return showCodeListKey('uwstate',[this,uwstatename],[0,1]);"><input type="text" class="codename" readonly="readonly" name="uwstatename" elementtype=nacessary>
	   		 	   </TD>
	   		 	  
	   		 	  
	   		 	   </div>
	   		 	   <td class=input>
	   		 	   </td>
	   		 	   <td class=input>
	   		 	   </td>
          	</TR>
          </table>
         <table class=common>
		
		<tr>
		      	<TD height="24"  class= title>
            		�˱����
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td></td>
      		</tr>
	  </table>
	  <div id =divUWButton1 style="display:''">
	  <p>
    		<INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="cancelchk();">
    		 <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="InitClick();">
  	</p>
  </div>
</div>

<DIV id=DivButton STYLE="display:''">
<!--table>
<tr>
	<td>
          <input value="������ϲ�ѯ" class=cssButton type=button onclick="showHealthQ();" >
          <INPUT VALUE="��������ѯ" class=cssButton TYPE=button onclick="RReportQuery();">
          <input value="�������¼��" class=cssButton type=button onclick="showHealth();" width="200">
          <input value="��������˵��" class=cssButton type=button onclick="showRReport();">

  </td>
</tr>
</table-->


</DIV>
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
