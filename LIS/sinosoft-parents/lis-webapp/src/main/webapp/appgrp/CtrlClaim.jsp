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
var LPFlag = "<%=request.getParameter("LPFlag")%>";

</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CtrlClaim.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CtrlClaimInit.jsp"%>
<title>
     理赔责任控制
</title>
</head>
<body onload="initForm();">
	  <form method=post name=fm id=fm target="fraSubmit" action="CtrlClaimSave.jsp">
	    	<table>
		       	<tr>
				        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				        <td class= titleImg>
					          控制层级信息
								</td>
						</tr>
				</table><Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
				<table  class= common align=center>
					<TR  class= common>
							<TD  class= title>
								团体保单号
							</TD>
							<TD  class= input>
					        	<Input class="wid" class=common name=GrpContNo id=GrpContNo value="" verify="团体保单号|notnull">
					    	</TD>
					   		<TD  class= title>
                	  			职业类别
            				</TD>
            				<TD  class= input>
                				<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="OccupationType" id="OccupationType"  verify="被保险人职业类别|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onclick="return showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onkeyup="return showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);" elementtype=nacessary><input class=codename name=OccupationTypeName id=OccupationTypeName readonly=true>
            				</TD>
            				<TD  class= title>
                	  		控制属性
            				</TD>
            				<TD  class= input >
                				<Input class="code" name="CtrlProp" id="CtrlProp" readonly value='0' verify="控制属性|code:CtrlProp"  elementtype=nacessary><input class=codename name=CtrlPropName id=CtrlPropName type=hidden readonly=true>
            				</TD>
 						</TR>
						<TR  class= title>
								<TD  class= title>
									  保障计划编码
							  </TD>
				        <TD  class= input>
					          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ContPlanCode id=ContPlanCode ondblclick="return showCodeList('lldutyctrlplan',[this,ContPlanName],[0,1],null,fm.GrpContNo.value,'grpcontno',1);" onclick="return showCodeList('lldutyctrlplan',[this,ContPlanName],[0,1],null,fm.GrpContNo.value,'grpcontno',1);" onkeyup="return showCodeListKey('lldutyctrlplan',[this,ContPlanName],[0,1],null,fm.GrpContNo.value,'GrpContNo');"><input class=codename name=ContPlanName id=ContPlanName readonly=true>
					          <!--<input type=hidden name=ContPlanFlag>-->
				        </TD>
				        <TD  class= title>
									  险种编码
								</TD>
                	    <TD class=input>
                	  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="RiskCode" id="RiskCode"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName id=AddressNoName readonly=true>
				        </TD>
				        <TD  class= title>
				            保险责任编码
					      </TD>
				        <TD  class= input>
					          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DutyCode id=DutyCode ondblclick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);" onclick="return showCodeList('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);" onkeyup="return showCodeListKey('griskduty',[this,DutyCodeName],[0,1],null,fm.RiskCode.value,'a.RiskCode',1);"><input class=codename name="DutyCodeName" id="DutyCodeName" readonly=true>
				        </TD>

				    </TR>
				    <TR>
				    	<TD  class= title>
				            给付责任编码
					    </TD>
				        <TD  class= input>
					          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GetDutyCode id=GetDutyCode ondblclick="return showCodeList('griskgetduty',[this,GetDutyCodeName],[0,1],null,fm.DutyCode.value,'a.DutyCode',1);" onclick="return showCodeList('griskgetduty',[this,GetDutyCodeName],[0,1],null,fm.DutyCode.value,'a.DutyCode',1);" onkeyup="return showCodeListKey('griskgetduty',[this,GetDutyCodeName],[0,1],null,fm.DutyCode.value,'a.DutyCode',1);"><input class=codename name="GetDutyCodeName" id="GetDutyCodeName" readonly=true>
				        </TD>
				        <TD  class= title>
				            控制批次号
					    </TD>
				        <TD  class= input>
					          <Input class="wid" class=readonly readonly name=CtrlBatchNo id=CtrlBatchNo>
				        </TD>
				    </TR>	
        </table></Div>
        
        <table>
			      <TR class=common>
				        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vvb);"></td>
				        <TD class= titleImg>
				        	  理赔责任控制信息
				        </TD>
			      </TR>
		    </table><Div  id= "vvb" style= "display: ''">
				<table  class= common>
			      <TR  class= common>
				        <TD text-align: left colSpan=1>
					          <span id="spanCtrlClaimCodeGrid" ></span>
				        </TD>
			      </TR>
		    </table>
		    <center>
		    	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
        	<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
        	<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
        	<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
 				</Div>
		    
		    <table>
			      <TR class=common>
				        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,zxc);"></td>
			    	    <TD class= titleImg>
			    	    	  控制属性信息
					      </TD>
			      </TR>
		    </table><Div  id= "zxc" style= "display: ''" class="maxbox1">
		    <table  class= common align=center>
			      <TR  class= common>
				        <TD  class= title>
				        	  观察期
				        </TD>
				        <TD  class= input>
					          <input class="wid" class=common name=ObserveDate id=ObserveDate verify="观察期|value>=0">
				        </TD>
				       
				        <TD  class= title>
				        	  免赔天数
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=ExemptDate id=ExemptDate verify="免赔天数|value>=0">
				        </TD> 
				        <TD  class= title>
				        	  总赔付限额
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=TotalLimit id=TotalLimit>
				        </TD> 
				     </TR>
				     <TR class=common>
				         
				        <TD  class= title>
				        	  赔付比例
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=ClaimRate id=ClaimRate verify="赔付比例|Decimal">
				        </TD> 
				        <TD  class= title>	 
				        	  免赔额
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=Exempt id=Exempt verify="免赔额|value>=0">
				        </TD> 
                          <TD  class= title>
				        	  起付线
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=StartPay id=StartPay>
				        </TD> 
				    </TR>
				    <TR class=common style= "display: 'none'">
				       
				        <TD  class= title>
				        	  封顶线
				        </TD>
				        <TD  class= input>
					          <Input class="wid" class=common name=EndPay id=EndPay>
				        </TD> 
				    </TR>
				    <TR>
				    	<input type=hidden name=mOperate>
						<input type=hidden name=levelflag>
				    </TR>
		    </table></Div>
		     <table>
			      <TR class=common>
				       <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvv);"></td>
			    	    <TD class= titleImg>
			    	    	  备注信息
					     </TD>
			      </TR>
		    </table><Div  id= "cvv" style= "display: ''">
		    <table  class= common align=center>
		     <tr class="common">
                <td colspan="6"><textarea class="common" rows="4" cols="229" name="Remark" id="Remark" value=""></textarea>
                </td>
            </tr>
		    </table></Div>
	
    <Div  id= "divRiskPlanSave" style= "display: ''" align= left> 
        <INPUT VALUE="查  询" class="cssButton"  TYPE=button onclick="QueryForm();"> 
        <%if(InputType.equals("1")){%>
		    <INPUT VALUE="添  加" class="cssButton"  TYPE=button onclick="submitForm();">
		   <!-- <INPUT VALUE="修  改" class="cssButton"  TYPE=button onclick="UptContClick();"> 	-->
		    <INPUT VALUE="删  除" class="cssButton"  TYPE=button onclick="DelContClick();">
		    <INPUT VALUE="上一步" class="cssButton"  TYPE=button onclick="returnparent();">
	      <% }%>  	
		<!-- INPUT VALUE="上一步" class="cssButton"  TYPE=button onclick="returnparent();"-->
	</Div>
	</form>
	      <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
    </body>
</html>
