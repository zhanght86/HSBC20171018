<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
  <head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <script language="javascript">
  	var GrpContNo = "<%=request.getParameter("GrpContNo")%>";
  	var ContNo = "<%=request.getParameter("ContNo")%>";
  	var vContNo = "<%=request.getParameter("ContNo")%>";
  	var prtNo = "<%=request.getParameter("PrtNo")%>";
  	//alert(prtNo);
    var display = "<%=request.getParameter("display")%>"; 
    
  	if (prtNo == null)
  	    prtNo="";
  	var checktype = "<%=request.getParameter("checktype")%>";
  	var scantype = "<%=request.getParameter("scantype")%>";
  	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
  	if (LoadFlag == null)
  	    Loadflag=1
          var ContType = "<%=request.getParameter("ContType")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
  	if (ContType == "null")
  	    ContType=1
  	var Auditing = "<%=request.getParameter("Auditing")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
  	if (Auditing == "null") 
  	    Auditing=0;
  	var MissionID = "<%=request.getParameter("MissionID")%>";
  	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
  	var AppntNo = "<%=request.getParameter("AppntNo")%>";
  	var AppntName = "<%=request.getParameter("AppntName")%>";
  	var BQFlag = "<%=request.getParameter("BQFlag")%>";
  	var EdorType = "<%=request.getParameter("EdorType")%>";
  	//alert("11111111====="+EdorType);
  	var EdorTypeCal = "<%=request.getParameter("EdorTypeCal")%>";
  	//alert(EdorTypeCal);
  	var EdorValiDate = "<%=request.getParameter("EdorValiDate")%>";
  	var NameType = "<%=request.getParameter("tNameFlag")%>";     
  	//alert(NameType); 
  	var param="";
  </script>
  	<script src="../common/javascript/Common.js" ></SCRIPT>
  	<script src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<script src="../common/javascript/MulLine.js"></SCRIPT>
  	<script src="../common/javascript/VerifyInput.js"></SCRIPT>
    <script src="../common/javascript/EasyQuery.js"></SCRIPT>
  	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<script src="ProposalAutoMove.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

    <script src="GrpFillInsured.js"></SCRIPT>
    <%@include file="GrpFillInsuredInit.jsp"%>
    <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>
    <script src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
    <script>window.document.onkeydown = document_onkeydown;</SCRIPT>
    <%}%>
  </head>
<body  onload="initForm();initElementtype();" >
<Form action="./GrpFillInsuredSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ��������Ϣ���� -->
    <DIV id=DivLCInsuredButton STYLE="display: ">
        <table>
            <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);"></td>
                <td class= titleImg><Input class= mulinetitle value="��һ������������"  name=InsuredSequencename id=InsuredSequencename readonly >���ͻ��ţ�<Input class= common name=InsuredNo id=InsuredNo ><INPUT id="butBack" VALUE="��  ѯ" class=cssButton TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
                    <Div  id= "divSamePerson" style="display:none">
                        <font color=red>��Ͷ����Ϊ�������˱��ˣ������������ѡ��
                            <INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
                        </font>
                    </div>
                </td>
            </tr>
        </table>
    
    </DIV>
	<div class=maxbox>
    <DIV id=DivRelation  STYLE="display: ">
        <table  class= common>  
            <TR  class= common>  
    	        <TD  class= title>�ͻ��ڲ�����</TD>             
                <TD  class= input><input class="codeno" name="SequenceNoCode" id=SequenceNoCode  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������"
				style="background:url(../common/images/select--bg_03.png) no-repeat right center"				
				ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);">
				<Input class="codename" name="SequenceNoName" id=SequenceNoName readonly="true"></TD>              
    	        <TD  class= title>���һ�������˹�ϵ</TD>             
                <TD  class= input><Input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured  elementtype=nacessary verify="���������˹�ϵ|code:Relation" 
				style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);">
				<Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName readonly="readonly"  ></TD>  
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>                     
            </TR> 
        </table> 
    </DIV>  
    <table  class= common>
        <TR  class= common>   
            <TD CLASS=title>�������ͱ��</td>
            <TD  class="input"><Input NAME=PolTypeFlag VALUE="0" CLASS=codeno id=PolTypeFlag
			style="background:url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="�������ͱ��" >
			<input name="PolTypeFlagName" id=PolTypeFlagName class="codename" readonly="readonly"></TD>
			<td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td> 
			<td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td> 
        </TR> 
     </table>         
    <DIV id=DivLCInsured STYLE="display: ">
        <DIV id=DivLCBasicInfo STYLE="display: ">   
            <table  class= common>
                <TR  class= common>        
                    <TD  class= title>����</TD>
                    <TD  class="input"><Input class="common wid" name=Name id=Name elementtype=nacessary verify="������������|notnull&len<=20" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();"></TD>
                    <TD  class="title">�Ա�</TD>
                    <TD  class="input"><Input class="codeno" name=Sex id=Sex verify="���������Ա�|notnull&code:Sex" 
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);">
					<input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary></TD>
                    <TD  class= title>֤������</TD>
                    <TD  class= input><Input class="codeno" name="IDType" id=IDType value="0" 
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"					
					ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" >
					<input class=codename name=IDTypeName id=IDTypeName readonly=true></TD>
                </TR>
                <TR  class= common>
                    <TD  class= title>֤������</TD>
                    <TD  class= input><Input class="common wid" name="IDNo" id=IDNo  aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();getAge();"></TD>
                    <TD  class= title>ְҵ����</TD>
                    <TD  class= input><Input class="codeno" name="OccupationCode" id=OccupationCode verify="��������ְҵ����|NOTNUlL&CODE:OccupationCode"  verifyorder="1" 
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					onblur="throughwork();" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" >
					<input class=codename name=AppntOccupationCodeName id=AppntOccupationCodeName  elementtype=nacessary onblur="throughwork();"></TD>
                </TR>
            </Table>
        </Div>	
    
        <DIV id=DivGrpNoname STYLE="display: ">        
            <table  class="common">        
                <TR class="common">
                    <TD  class= title>ְҵ���</TD>
                    <TD  class= input id="OccupationTypeID"><Input class="code" name="OccupationType" id=OccupationType verify="��������ְҵ���|code:OccupationType" readonly=true elementtype=nacessary></TD>  
                    <TD CLASS=title >����������</TD>
                    <TD CLASS=input><Input NAME=InsuredPeoples id=InsuredPeoples VALUE="" readonly=true CLASS="common wid" verify="����������|num" ></TD>
                    <TD CLASS=title>����֤��</TD>
                    <TD CLASS=input><Input NAME=WorkNo id=WorkNo VALUE="" CLASS="common wid" ></TD> 
        	    </TR>  
            </table>
        </Div>
        <DIV id=DivAddress STYLE="display: ">   
            <table  class= common>   	
                <TR  class= common>
                    <TD  class= title>��������</TD>
                    <TD  class= input id="BirthdayID">
					<input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge();" name="Birthday" verify="�������˳�������|NOTNULL&DATE" verifyorder='2' onClick="laydate({elem: '#Birthday'});" id="Birthday">
					<span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
                    <TD CLASS=title>����������</TD>
                    <TD CLASS=input ><Input NAME=InsuredAppAge id=InsuredAppAge VALUE="" readonly=true CLASS="common wid" verify="����������|num" ></TD>
                </TR>  
                
                <TR class=common>
        			<TD class=title>��˾ʱ��</TD>
        			<TD class=input><Input name="JoinCompanyDate" id=JoinCompanyDate class="coolDatePicker" dateFormat="short"onClick="laydate({elem: '#JoinCompanyDate'});" >
					<span class="icon"><a onClick="laydate({elem: '#JoinCompanyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        			<TD class=title>����</TD>
        			<TD class=input><Input name="Salary" class="common wid"></TD>
        			<TD  class= title>�������</TD>
                    <TD  class= input><Input class=codeno name=ExecuteCom  id=ExecuteCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);">
					<input class=codename name=ManageComName id=ManageComName readonly=true></TD>
                </TR>
            </TABLE>
        </Div>    
    </DIV>
    <DIV id="divContPlan" style="display: " >
    	<TABLE class= common>
    		<TR class= common>
    			<TD  class= title>���ռƻ�</TD>
                <TD  class= input><Input class="code" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('ContPlanCode',[this],[0], ,  ,  , true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0], ,  ,  , true);"></TD>
				<td class="title">&nbsp;</td>
				<td class="input">&nbsp;</td> 
				<td class="title">&nbsp;</td>
				<td class="input">&nbsp;</td>
    		</TR>
    	</TABLE>
    </DIV>
    <DIV id=DivClaim STYLE="display:none"> 
        <table  class= common> 
            <TR  class= common>
                <TD  class= title>��ϵ��ַ</TD>
                <TD  class= input  colspan="3"><Input class= common3 name="PostalAddress" id=PostalAddress  verify="����������ϵ��ַ|len<=80" ></TD>
                <TD  class= title>��������</TD>
                <TD  class= input><Input class="wid common" name="ZipCode" id=ZipCode  MAXLENGTH="6" verify="����������������|zipcode" ></TD>
            </TR>  
            <TR  class= common>  
                <TD  class= title>��ַ����</TD>                  
                <TD  class= input><Input class="codeno" name="InsuredAddressNo" id=InsuredAddressNo  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , true);" onfocus="getdetailaddress();"><input class=codename name=InsuredAddressNoName readonly=true></TD>
                <TD  class= title>������ʻ�</TD>                  
                <TD  class= input><Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|�ɷ��ʻ�^2|����" 
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();"></TD>
                <TD  class= title>�ƶ��绰</TD>
                <TD  class= input><Input class="wid common" name="Mobile" id=Mobile verify="���������ƶ��绰|len<=15" ></TD>
                <TD  class= title>�칫�绰</TD>
                <TD  class= input><Input class="wid common" name="GrpPhone" id=GrpPhone ></TD>          
            </TR>         
            <TR  class= common>  
            	<TD class=title>סլ�绰</TD>
            	<TD class=input><Input name="HomePhone" class=common></TD>			
                <TD  class= title>��������</TD>
                <TD  class= input><Input class="wid common" name="EMail" id=EMail verify="�������˵�������|len<=20&Email" ></TD>
            </TR> 
            <TR CLASS=common>
        	    <TD CLASS=title>��������</TD>
        	    <TD CLASS=input COLSPAN=1 ><Input NAME=BankCode id=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" ></TD>
        	    <TD CLASS=title >����</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=AccName id=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="����|len<=20" ></TD>
                <TD CLASS=title width="109" >�˺�</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=BankAccNo class="code" VALUE=""  id=BankAccNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|len<=40" onfocus="getdetail();"></TD>
    	    </TR>   
        </Table>          
    </DIV>
    </div>
    <DIV id=DivLCImpart STYLE="display: ">
        <!-- ��֪��Ϣ���֣��б� -->
        <table>
            <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);"></td>
                <td class= titleImg>�������˸�֪��Ϣ</td>
            </tr>
        </table>

        <div  id= "divLCImpart1" style= "display:  ">
            <table  class= common>
                <tr  class= common>
                    <td style="text-align: left" colSpan=1><span id="spanImpartGrid" ></span></td>
                </tr>
            </table>
        </div>
    </DIV>
    
    <!-- ������������Ϣ���� -->
<DIV id=DivLCPol STYLE="display: ">
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

<div  id= "divLCPol1" style= "display:  ">
<table  class= common>
<tr  class= common>
<td style="text-align: left" colSpan=1>
<span id="spanPolGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>

    <Div  id= "divAnotherAddDelButton" style= "display: none" align=right>
        <input type =button class=cssButton value="��ӱ�������" onclick="addInsuredList();"> </TD>
    </DIV>

<hr class=line>

    <DIV id = "divaddPerButton" style = "display:none" style="float: left">
        <INPUT class=cssButton id="riskbutton9" VALUE="��һ��" TYPE=button onclick="returnparent();">
    </DIV>
    
    <INPUT type=hidden id="fmAction" name="fmAction">
    <INPUT type=hidden id="ContType" name="ContType">
    <INPUT type=hidden id="GrpContNo" name="GrpContNo">
    <INPUT type=hidden name=FamilyType>
    <INPUT type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
    <INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- ������������� -->
    <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
    <INPUT  type= "hidden" class= Common name= ProposalGrpContNo value= "">
    <INPUT  type= "hidden" class= Common name= AppntNo value= "">
    <INPUT  type= "hidden" class= Common name= AppntName value= "">
    <INPUT  type= "hidden" class= Common name= SelPolNo value= "">
    <INPUT  type= "hidden" class= Common name= SaleChnl value= "">
    <INPUT  type= "hidden" class= Common name= ManageCom value= "">
    <INPUT  type= "hidden" class= Common name= AgentCode value= "">
    <INPUT  type= "hidden" class= Common name= AgentGroup value= "">
    <INPUT  type= "hidden" class= Common name= GrpName value= "">
    <INPUT  type= "hidden" class= Common name= CValiDate value= "">
    <INPUT type= "hidden" class="common" name=ContNo >
    <INPUT type=hidden id=CardNo name=CardNo value="" >
    <Input type= "hidden" class="common" name=PrtNo value="<%=request.getParameter("PrtNo")%>">
    <input type=hidden id="" name="pagename" value="">
    <Input type=hidden class="common" name=ProposalContNo >
    <input type=hidden id="BPNo" name="BPNo" value="<%=request.getParameter("GrpContNo")%>">
    <!--����������ʱ�õ���ԭ��������ͬ��-->
    <INPUT  type= "hidden" class= Common name=OldContNo id=OldContNo value= "">
    <input type=hidden name=BQFlag id=BQFlag>
    <input type=hidden name=EdorType id=EdorType>
    <input type=hidden name=EdorTypeCal id=EdorTypeCal>
    <input type=hidden name=EdorValiDate id=EdorValiDate> 
</Form>
<br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
