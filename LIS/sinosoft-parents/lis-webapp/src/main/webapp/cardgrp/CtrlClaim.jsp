<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
String InputType = request.getParameter("InputType");
if(InputType==null||InputType.equals("null"))
{
	InputType = "1";
}
//InputType = "2";

loggerDebug("CtrlClaim","##########"+InputType);
%>    
<head>
<script>
GrpContNo="<%=GrpContNo%>";
//alert(GrpContNo);
LoadFlag="<%=LoadFlag%>";

var prtNo ="<%=request.getParameter("prtNo")%>";
var polNo ="<%=request.getParameter("polNo")%>";
var scantype ="<%=request.getParameter("scantype")%>";
var MissionID ="<%=request.getParameter("MissionID")%>";
var ManageCom ="<%=request.getParameter("ManageCom")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";

</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CtrlClaim.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CtrlClaimInit.jsp"%>
<title>
     �������ο���
</title>
</head>
<body onload="initForm();">
	  <form method=post name=fm target="fraSubmit" action="CtrlClaimSave.jsp">
	    	<table>
		       	<tr>
				        <td class=common>
										<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				        </td>
				        <td class= titleImg>
					          ���Ʋ㼶��Ϣ
								</td>
						</tr>
				</table>
				<table  class= common align=center>
					<TR  class= common>
							<TD  class= title>
								���屣����
							</TD>
							<TD  class= input>
					        	<Input class=common name=GrpContNo value="" verify="���屣����|notnull">
					    	</TD>
					   		<TD  class= title>
                	  			ְҵ���
            				</TD>
            				<TD  class= input>
                				<Input class="codeno" name="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);" elementtype=nacessary><input class=codename name=OccupationTypeName readonly=true>
            				</TD>
            				<TD  class= title>
                	  		��������
            				</TD>
            				<TD  class= input >
                				<Input class="code" name="CtrlProp" readonly value='0' verify="��������|code:CtrlProp"  elementtype=nacessary><input class=codename name=CtrlPropName type=hidden readonly=true>
            				</TD>
 						</TR>
						<TR  class= title>
								<TD  class= title>
									  ���ϼƻ�����
							  </TD>
				        <TD  class= input>
					          <Input class=codeno name=ContPlanCode ondblclick="return showCodeList('lldutyctrlplan',[this,ContPlanName],[0,1],null,fm.GrpContNo.value,'grpcontno',1);" onkeyup="return showCodeListKey('lldutyctrlplan',[this,ContPlanName],[0,1],null,fm.GrpContNo.value,'GrpContNo');"><input class=codename name=ContPlanName readonly=true>
					          <!--<input type=hidden name=ContPlanFlag>-->
				        </TD>
				        <TD  class= title>
									  ���ֱ���
								</TD>
                	    <TD class=input>
                	  		<Input class="codeno" name="RiskCode"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName readonly=true>
				        </TD>
				        <TD  class= title>
				            �������α���
					      </TD>
				        <TD  class= input>
					          <Input class=codeno name=DutyCode ondblclick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);"><input class=codename name="DutyCodeName" readonly=true>
				        </TD>

				    </TR>
				    <TR>
				    	<TD  class= title>
				            �������α���
					    </TD>
				        <TD  class= input>
					          <Input class=codeno name=GetDutyCode ondblclick="return showCodeList('griskgetduty',[this,GetDutyCodeName],[0,1],null,fm.DutyCode.value,'a.DutyCode',1);" onkeyup="return showCodeListKey('griskgetduty',[this,GetDutyCodeName],[0,1],null,fm.DutyCode.value,'a.DutyCode',1);"><input class=codename name="GetDutyCodeName" readonly=true>
				        </TD>
				        <TD  class= title>
				            �������κ�
					    </TD>
				        <TD  class= input>
					          <Input class=readonly readonly name=CtrlBatchNo>
				        </TD>
				    </TR>	
        </table>
        <hr>
        <table>
			      <TR class=common>
				        <TD class=common>
					          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
				        </TD>
				        <TD class= titleImg>
				        	  �������ο�����Ϣ
				        </TD>
			      </TR>
		    </table>
				<table  class= common>
			      <TR  class= common>
				        <TD text-align: left colSpan=1>
					          <span id="spanCtrlClaimCodeGrid" ></span>
				        </TD>
			      </TR>
		    </table>
		    <table  class= common align=center>
		    	<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();">
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();">
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();">
        	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();">
 				</table>
		    <hr>
		    <table>
			      <TR class=common>
				        <TD class=common>
				          	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
			        	</TD>
			    	    <TD class= titleImg>
			    	    	  ����������Ϣ
					      </TD>
			      </TR>
		    </table>
		    <table  class= common align=center>
			      <TR  class= common>
				        <TD  class= common>
				        	  �۲���
				        </TD>
				        <TD  class= input>
					          <input class=common name=ObserveDate verify="�۲���|value>=0">
				        </TD>
				        <TD  class= common>	 
				        	  �����
				        </TD>
				        <TD  class= input>
					          <Input class=common name=Exempt verify="�����|value>=0">
				        </TD> 
				        <TD  class= common>
				        	  ��������
				        </TD>
				        <TD  class= input>
					          <Input class=common name=ExemptDate verify="��������|value>=0">
				        </TD> 
				     </TR>
				     <TR class=common>
				         <TD  class= common>
				        	  ���⸶�޶�
				        </TD>
				        <TD  class= input>
					          <Input class=common name=TotalLimit>
				        </TD> 
				        <TD  class= common>
				        	  �⸶����
				        </TD>
				        <TD  class= input>
					          <Input class=common name=ClaimRate verify="�⸶����|Decimal">
				        </TD> 
				    </TR>
				    <TR class=common style= "display: 'none'">
				         <TD  class= common>
				        	  ����
				        </TD>
				        <TD  class= input>
					          <Input class=common name=StartPay>
				        </TD> 
				        <TD  class= common>
				        	  �ⶥ��
				        </TD>
				        <TD  class= input>
					          <Input class=common name=EndPay>
				        </TD> 
				    </TR>
				    <TR>
				    	<input type=hidden name=mOperate>
						<input type=hidden name=levelflag>
				    </TR>
		    </table>
		     <table>
			      <TR class=common>
				        <TD class=common>
				          	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
			        	</TD>
			    	    <TD class= titleImg>
			    	    	  ��ע��Ϣ
					     </TD>
			      </TR>
		    </table>
		    <table  class= common align=center>
		     <tr class="common">
                <td class="common"><textarea rows="4" cols="100" name="Remark" value=""></textarea>
                </td>
            </tr>
		    </table>
	<hr>
    <Div  id= "divRiskPlanSave" style= "display: ''" align= left> 
        <INPUT VALUE="��  ѯ" class="cssButton"  TYPE=button onclick="QueryForm();"> 
        <%if(InputType.equals("1")){%>
		    <INPUT VALUE="��  ��" class="cssButton"  TYPE=button onclick="submitForm();">
		   <!-- <INPUT VALUE="��  ��" class="cssButton"  TYPE=button onclick="UptContClick();"> 	-->
		    <INPUT VALUE="ɾ  ��" class="cssButton"  TYPE=button onclick="DelContClick();">
		    <INPUT VALUE="��һ��" class="cssButton"  TYPE=button onclick="returnparent();">
	      <% }%>  	
		<!-- INPUT VALUE="��һ��" class="cssButton"  TYPE=button onclick="returnparent();"-->
	</Div>
	</form>
	      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    </body>
</html>
