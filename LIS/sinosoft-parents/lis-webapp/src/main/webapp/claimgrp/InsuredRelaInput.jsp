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
  	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  	if (LoadFlag == null)
  	    Loadflag=1
          var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  	if (ContType == "null")
  	    ContType=1
  	var Auditing = "<%=request.getParameter("Auditing")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
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
    <script src="../common/Calendar/Calendar.js"></SCRIPT>
  	<script src="../common/javascript/MulLine.js"></SCRIPT>
  	<script src="../common/javascript/VerifyInput.js"></SCRIPT>
    <script src="../common/javascript/EasyQuery.js"></SCRIPT>
  	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<script src="ProposalAutoMove.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

    <script src="InsuredRelaInput.js"></SCRIPT>
    <%@include file="InsuredRelaInit.jsp"%>
    <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>
    <script src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
    <script>window.document.onkeydown = document_onkeydown;</SCRIPT>
    <%}%>
  </head>
<body  onload="initForm();initElementtype();" >
<Form action="./GrpFillInsuredSave.jsp" method=post name=fm target="fraSubmit">
    <!-- 被保人信息部分 -->
    <DIV id=DivLCInsuredButton STYLE="display:''">
        <table>
            <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);"></td>
                <td class= titleImg><Input class= mulinetitle value="第一被保险人资料"  name=InsuredSequencename readonly >（客户号：<Input class= common name=InsuredNo ><INPUT id="butBack" VALUE="查  询" class=cssButton TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
                    <Div  id= "divSamePerson" style="display:'none'">
                        <font color=red>如投保人为被保险人本人，可免填本栏，请选择
                            <INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
                        </font>
                    </div>
                </td>
            </tr>
        </table>
    
    </DIV>
    <DIV id=DivRelation STYLE="display:''">
        <table  class= common>  
            <TR  class= common>  
    	        <TD  class= title>客户内部号码</TD>             
                <TD  class= input><input class="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" readonly="true"></TD>              
    	        <TD  class= title>与第一被保险人关系</TD>             
                <TD  class= input><Input class="codeno" name="RelationToMainInsured"  elementtype=nacessary verify="主被保险人关系|code:Relation" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" readonly="readonly"  ></TD>  
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>                     
            </TR> 
        </table> 
    </DIV>  
    <!-- <table  class= common> 
        <TR  class= common>   
            <TD CLASS=title>保单类型标记</td>
            <TD  class="input"><Input NAME=PolTypeFlag VALUE="0" CLASS=codeno ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="保单类型标记" ><input name="PolTypeFlagName" class="codename" readonly="readonly"></TD>
        </TR> 
     </table>  --> 
	<!-- <DIV id=DivMainInsured STYLE="display:''"> -->
        <!-- 告知信息部分（列表） -->
	<table>
            <tr>
                <!-- <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);"></td> -->
                <td class= titleImg>需要补充被保人的保单信息</td>
            </tr>
        </table>

        <div  id= "divCont1" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1><span id="spanContGrid" ></span></td>
                </tr>
            </table>
        </div>
        <table>
            <tr>
                <!-- <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);"></td> -->
                <td class= titleImg>主被保人信息</td>
            </tr>
        </table>

        <div  id= "divMainInsured1" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1><span id="spanMainInsuredGrid" ></span></td>
                </tr>
            </table>
        </div>
       <table>
            <tr>
                <!-- <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);"></td> -->
                <td class= titleImg>主被保人下连带被保人信息</td>
            </tr>
        </table>

        <div  id= "divInsuredRela" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1><span id="spanInsuredRelaGrid" ></span></td>
                </tr>
            </table>
        </div>
    <!-- </DIV> -->      
    <DIV id=DivLCInsured STYLE="display:''">
        <DIV id=DivLCBasicInfo STYLE="display:''">   
            <table  class= common>
                <TR  class= common>        
                    <TD  class= title>姓名</TD>
                    <TD  class="input"><Input class="common" name=Name elementtype=nacessary verify="被保险人姓名|notnull&len<=20" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();"></TD>
                    <TD  class="title">性别</TD>
                    <TD  class="input"><Input class="codeno" name=Sex ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly="readonly" elementtype=nacessary></TD>
                    <TD  class= title>证件类型</TD>
                    <TD  class= input><Input class="codeno" name="IDType" value="0"  ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
                <TR  class= common>
                    <TD  class= title>证件号码</TD>
                    <TD  class= input><Input class=common name="IDNo"  onkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();getAge();"><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <!-- <TD  class= title>职业代码</TD>
                    <TD  class= input><Input class="codeno" name="OccupationCode" verify="被保险人职业代码|NOTNUlL&CODE:OccupationCode"  verifyorder="1" onblur="throughwork();" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" ><input class=codename name=AppntOccupationCodeName  elementtype=nacessary onblur="throughwork();"></TD> -->
                	<TD  class= title>出生日期</TD>
                    <TD  class= input ><input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge();" name="Birthday" verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' ></TD>
                    <TD CLASS=title>被保人年龄</TD>
                    <TD CLASS=input ><Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="被保人年龄|num" ></TD>
				</TR>
				<TR  class= common>  
	    	        <TD  class= title>与主被保险人关系</TD>             
	                <TD  class= input><Input class="codeno" name="RelationToMainInsured2"   verify="主被保险人关系|code:Relation" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName2], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName2], [0,1]);"><Input class="codename" name="RelationToMainInsuredName2" readonly="readonly"  ><font size=1 color='#ff0000'><b>*</b></font></TD>  
    				<TD CLASS=title>职业代码 </TD>
    				<TD CLASS=input >
						<Input class="codeno" name="OccupationCode" verify="被保人职业代码|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName], [0,1]);" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName], [0,1]);"><input class=codename name=OccupationCodeName readonly="readonly" elementtype=nacessary>
          			</TD>    
					<TD CLASS=title>职业类别 </TD>
   				    <TD  class= input>
     					 <Input class= 'common' readonly name=OccupationType >
   					 </TD>
				            
            	</TR> 
            </Table>
        </Div>	
    
        <DIV id=DivGrpNoname STYLE="display:'none'">        
            <table  class="common">        
                <TR class="common">
                    <TD  class= title>职业类别</TD>
                    <TD  class= input id="OccupationTypeID"><Input class="code" name="OccupationTypeID"  verify="被保险人职业类别|code:OccupationType" readonly=true elementtype=nacessary></TD>  
                    <TD CLASS=title >被保人人数</TD>
                    <TD CLASS=input><Input NAME=InsuredPeoples VALUE="" readonly=true CLASS=common verify="被保人人数|num" ></TD>
                    <TD CLASS=title>工作证号</TD>
                    <TD CLASS=input><Input NAME=WorkNo VALUE="" CLASS=common ></TD> 
        	    </TR>
            </table>
        </Div>
        <!-- <DIV id=DivAddress STYLE="display:''">   
            <table  class= common>   	
                <TR  class= common>
                    <TD  class= title>出生日期</TD>
                    <TD  class= input id="BirthdayID"><input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge();" name="Birthday" verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' ></TD>
                    <TD CLASS=title>被保人年龄</TD>
                    <TD CLASS=input ><Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="被保人年龄|num" ></TD>
                </TR>  
                
                <TR class=common>
        			<TD class=title>入司时间</TD>
        			<TD class=input><Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short"></TD>
        			<TD class=title>工资</TD>
        			<TD class=input><Input name="Salary" class=common></TD>
        			<TD  class= title>处理机构</TD>
                    <TD  class= input><Input class=codeno name=ExecuteCom  ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>
                </TR>
            </TABLE>
        </Div> -->    
    </DIV>
    <TABLE class= common>
        <TR class= common>
            <TD  class= title>
                <DIV id="divContPlan" style="display:''" >
    	            <TABLE class= common>
    		            <TR class= common>
    			            <TD  class= title>保险计划</TD>
                            <TD  class= input><Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);"></TD>
    		            </TR>
    	            </TABLE>
                </DIV>
            </TD>
        </TR>
    </TABLE>
    <DIV id=DivClaim STYLE="display:'none'"> 
        <table  class= common> 
            <TR  class= common>
                <TD  class= title>联系地址</TD>
                <TD  class= input  colspan="3"><Input class= common3 name="PostalAddress"  verify="被保险人联系地址|len<=80" ></TD>
                <TD  class= title>邮政编码</TD>
                <TD  class= input><Input class= common name="ZipCode"  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" ></TD>
            </TR>  
            <TR  class= common>  
                <TD  class= title>地址代码</TD>                  
                <TD  class= input><Input class="codeno" name="InsuredAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress();"><input class=codename name=InsuredAddressNoName readonly=true></TD>
                <TD  class= title>理赔金帐户</TD>                  
                <TD  class= input><Input class="code" name="AccountNo"  CodeData="0|^1|缴费帐户^2|其它" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();"></TD>
                <TD  class= title>移动电话</TD>
                <TD  class= input><Input class= common name="Mobile" verify="被保险人移动电话|len<=15" ></TD>
                <TD  class= title>办公电话</TD>
                <TD  class= input><Input class= common name="GrpPhone"  ></TD>          
            </TR>         
            <TR  class= common>  
            	<TD class=title>住宅电话</TD>
            	<TD class=input><Input name="HomePhone" class=common></TD>			
                <TD  class= title>电子邮箱</TD>
                <TD  class= input><Input class= common name="EMail" verify="被保险人电子邮箱|len<=20&Email" ></TD>
            </TR> 
            <TR CLASS=common>
        	    <TD CLASS=title>开户银行</TD>
        	    <TD CLASS=input COLSPAN=1 ><Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" ></TD>
        	    <TD CLASS=title >户名</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|len<=20" ></TD>
                <TD CLASS=title width="109" >账号</TD>
        	    <TD CLASS=input COLSPAN=1  ><Input NAME=BankAccNo class="code" VALUE="" CLASS=common ondblclick="return showCodeList('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|len<=40" onfocus="getdetail();"></TD>
    	    </TR>   
        </Table>          
    </DIV>
    
    <DIV id=DivLCImpart STYLE="display:'none'">
        <!-- 告知信息部分（列表） -->
        <table>
            <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);"></td>
                <td class= titleImg>被保险人告知信息</td>
            </tr>
        </table>

        <div  id= "divLCImpart1" style= "display: 'none'">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1><span id="spanImpartGrid" ></span></td>
                </tr>
            </table>
        </div>
    </DIV>

    <Div  id= "divAnotherAddDelButton" style= "display: 'none'" align=right>
        <input type =button class=cssButton value="添加被保险人" onclick="addInsuredList();"> </TD>
    </DIV>

<hr>

    <DIV id = "divaddPerButton" style = "display:'none'" style="float: left">
        <INPUT class=cssButton id="riskbutton9" VALUE="上一步" TYPE=button onclick="returnparent();">
    </DIV>
    
    <INPUT type=hidden id="fmAction" name="fmAction">
    <INPUT type=hidden id="ContType" name="ContType">
    <INPUT type=hidden id="GrpContNo" name="GrpContNo">
    <INPUT type=hidden name=FamilyType>
    <INPUT type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
    <INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- 工作流任务编码 -->
    <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
    <INPUT  type= "hidden" class= Common name= ProposalGrpContNo value= "">
    <INPUT  type= "hidden" class= Common name= AppntNo value= "">
    <INPUT  type= "hidden" class= Common name= AppntName value= "">
    <INPUT  type= "hidden" class= Common name= SelPolNo value= "">
    <INPUT  type= "hidden" class= Common name= PolNo value= "">
    <INPUT  type= "hidden" class= Common name= SaleChnl value= "">
    <INPUT  type= "hidden" class= Common name= ManageCom value= "">
    <INPUT  type= "hidden" class= Common name= AgentCode value= "">
    <INPUT  type= "hidden" class= Common name= AgentGroup value= "">
    <INPUT  type= "hidden" class= Common name= GrpName value= "">
    <INPUT  type= "hidden" class= Common name= MainInsuredNo value= "">
    <INPUT  type= "hidden" class= Common name= CValiDate value= "">
    <INPUT  type= "hidden" class= Common name= RiskSortValue value= ""><!-- 是否校验连带被保险人标志 -->
    <INPUT type= "hidden" class="common" name=ContNo >
	<INPUT type= "hidden" class="common" name=codeSql >
    <INPUT type=hidden id=CardNo name=CardNo value="" >
    <Input type= "hidden" class="common" name=PrtNo value="<%=request.getParameter("PrtNo")%>">
    <input type=hidden id="" name="pagename" value="">
    <Input type=hidden class="common" name=ProposalContNo >
    <input type=hidden id="BPNo" name="BPNo" value="<%=request.getParameter("GrpContNo")%>">
    <!--无名单补单时用到的原无名单合同号-->
    <INPUT  type= "hidden" class= Common name= OldContNo value= "">
    <input type=hidden name=BQFlag>
    <input type=hidden name=EdorType>
    <input type=hidden name=EdorTypeCal>
    <input type=hidden name=EdorValiDate>
</Form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
