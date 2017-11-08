<%@include file="../common/jsp/UsrCheck.jsp" %>
<html> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：
//程序功能：
//创建日期：2006-07-19 11:10:36
//创建人  ：ck程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

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

//得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	
  loggerDebug("PolModifyInput","LoadFlag:" + tLoadFlag);

String polno=request.getParameter("PolNo");
loggerDebug("PolModifyInput","@@@polno:"+polno);
%>
<script language="javascript">
     var ContNo = "<%=request.getParameter("PolNo")%>";
     var prtNo = "<%=request.getParameter("prtNo")%>";
     var LoadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
</script>
<head >
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
 <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
	<%@include file="./PolModifyInit.jsp"%>
	<SCRIPT src="./PolModifyInput.js"></SCRIPT>
	<SCRIPT src="../app/ProposalAutoMove3.js"></SCRIPT>
	<script src="../app/TabAutoScroll.js"></script>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT language="javascript"> window.document.onkeydown = document_onkeydown;</SCRIPT>
  	
</head>

<body  onload="initForm()" >
  <form action="./PolModifySave.jsp" method=post name=fm id=fm target="fraSubmit">
  <div id=DivLCContButton style="display:''">
  <!-- 合同信息部分 -->
	<table id="table1">
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
			<td class="titleImg">合同信息
			</td>
		</tr>
	</table>
</div>

<div id="DivLCCont" class=maxbox STYLE="display:''">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">印刷号 </td>
			<td CLASS="input" COLSPAN="1">				
				<input name="PrtNo" id=PrtNo  class="common wid" readonly>
			</td>
			<td class="title">投保日期</td>
			<td class="input"><input class="common wid" dateFormat="short" name="PolApplyDate" id=PolApplyDate readonly ></td>
			<td CLASS="title">管理机构 </td>
			<td CLASS="input">
				<input NAME="ManageCom" id=ManageCom readonly  MAXLENGTH="10" CLASS="codeno" ><!-- ondblclick="showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|notnull"  --><input NAME="ManageComName" id=ManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</td>	
		</tr>
	</table>
	<table class="common">
		<tr class="common">
			<TD  class= title>代理机构</TD>
			<TD  class= input>
			  <Input name="AgentCom" id=AgentCom id=AgentCom class='codeno' readonly><input name=InputAgentComName id=InputAgentComName class='codename' readonly=true >
			</TD>
			<td class="title">代理人编码 </td>
			<td class="input" COLSPAN="1">
				<input NAME="AgentCode" id=AgentCode readonly  CLASS="codeno"><input name=AgentName id=AgentName class='codename' readonly=true  elementtype=nacessary >
			</td> 
			<td CLASS="title">所属机构 </td>
			<td CLASS="input" COLSPAN="1">
				<input NAME="AgentManageCom" id=AgentManageCom readonly  MAXLENGTH="10" CLASS="codeno" ><input NAME="AgentManageComName" id=AgentManageComName elementtype=nacessary CLASS="codename" readonly="readonly">
			</td>	
		</tr>
		<tr class="common">	
			<td CLASS="title">代理人组别 </td>
			<td CLASS="input" COLSPAN="1">				
				<input NAME="AgentGroup" id=AgentGroup readonly CLASS="common wid" value="">
			</td>			
			<td class="title" style="display:''">销售渠道</td>
			<td class="input" style="display:''">
				<input class="codeno" name="SaleChnl" id=SaleChnl readonly ><input class="codename" name="SaleChnlName" id=SaleChnlName readonly="readonly">
			</td>
			<td CLASS="title"> </td>
			<td CLASS="input"> </td>
		</tr>
		</table>
	<table class="common">
		<tr class="common">
			<td class="title" style="display:''">备注:</td>
		</tr>
		<tr>
	    <TD  class= input>
        	<textarea name="remark" id=remark cols="100" rows="2" witdh=25% class="common" readonly></textarea>
        </TD>
	  	</tr>
	</table>
<Div id=DivLCAppntIndButton STYLE="display:''">
	<!-- 投保人信息部分 -->
	<Table>
		<TR>
			<TD class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				投保人信息（客户号：<input class="common" name="AppntNo" value="">)<input type="hidden" name="AppntAddressNo" value="">
			</TD>
		</TR>
	</Table>
</Div>
<Div id=DivLCAppntInd class=maxbox STYLE="display:''">
	<Table  class= common> 
		<TR  class= common>        
			<TD  class= title>姓名</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id=AppntName elementtype=nacessary verify="投保人姓名|NOTNUlL&LEN<=20"  verifyorder="1" oncopy="return false;" oncut="return false">
			</TD>
			<TD  class= title>性别</TD>
			<TD nowrap class= input>
				<Input class="codeno" name=ApntSex id=ApntSex readonly><input class=codename name=ApntSexName id=ApntSexName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" name="ApntBirthday" id=ApntBirthday  readonly >
			</TD>
		</TR>    
		<TR  class= common>			
			<TD  class= title>系被保人的</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntRelationToInsured" id=AppntRelationToInsured readonly><Input class=codename name=RelationToInsuredName id=RelationToInsuredName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id=AppntIDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="证件类型|NOTNUlL&IDTYPE" ondblclick="return showCodeList('idtype',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDNo" id=AppntIDNo elementtype=nacessary verify="投保人证件号码|LEN<=20"  verifyorder="1" oncopy="return false" oncut="return false" >
			</TD>
		</TR>
		<TR  class= common>			
			<TD  class= title>国籍</TD>
			<TD  class="input">
				<input class="codeno" name="ApntNativePlace" id=ApntNativePlace readonly><input class=codename name=ApntNativePlaceName id=ApntNativePlaceName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>户籍所在地</TD>
			<TD  class=input >
				<Input class="common wid" name=ApntRgtAddress id=ApntRgtAddress readonly >
			</TD>
			<TD  class= title>婚姻状况</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="ApntMarriage" id=ApntMarriage readonly><input class=codename name=ApntMarriageName id=ApntMarriageName readonly=true elementtype=nacessary>
			</TD>  
		</TR> 		
		<TR  class= common>			
			<TD  class= title>通讯地址</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id=AppntPostalAddress verify="投保人通讯地址|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>通讯地址邮政编码</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntZipCode" id=AppntZipCode verify="投保人通讯地址邮政编码|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>住址</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntHomeAddress" id=AppntHomeAddress verify="投保人住址|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>住址邮政编码</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntHomeZipCode" id=AppntHomeZipCode verify="投保人住址邮政编码|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>	
		<TR  class= common>        
			<TD  class= title>首选回访电话</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntPhone" id=AppntPhone verify="投保人首选回访电话|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>         
			<TD  class= title>其他联系电话</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntPhone2" id=AppntPhone2 verify="投保人其他联系电话|LEN<=15&PHONE"  verifyorder="1" >
			</TD>       
			<TD  class= title>电子邮箱</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntEMail" id=AppntEMail MAXLENGTH=40 verify="投保人电子邮箱|EMAIL&LEN<=40"  verifyorder="1" >
			</TD>  
		</TR>
		<TR  class= common>
			<TD  class= title>工作单位</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpName" id=AppntGrpName verify="投保人工作单位|LEN<=60"  verifyorder="1" elementtype=nacessary >
			</TD>			
			<TD  class= title>
		        职业
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="ApntWorkType" id=ApntWorkType  value="" readonly>
		      </TD>
		  	<TD  class= title>
		        兼职
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="ApntPluralityType" id=ApntPluralityType  value="" readonly>
		      </TD>
	    </TR>
	    <TR  class= common>
			<TD  class= title>职业编码</TD>
			<TD class= input>
				<Input class="codeno" name="ApntOccupationCode" id=ApntOccupationCode readonly><input class=codename name=ApntOccupationCodeName id=ApntOccupationCodeName readonly=true elementtype=nacessary>
			</TD>   
			<TD class=title>职业类别</TD>
			<TD class= input>
				<Input class="codeno" readonly name="ApntOccupationType" id=ApntOccupationType readonly><input class=codename name=ApntOccupationTypeName id=ApntOccupationTypeName readonly=true>
			</TD> 	
		</TR> 	
	</Table>  
  <hr>
	<div id="DivLCAccount" style="display:''">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>缴费信息</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" class=maxbox style="display:''">
        	<Table class="common">  
        	    <TR class="common">
	          		<TD class="title">首期交费形式</TD>
		            <TD>
		            	<input class="codeno" name="NewPayMode" id=NewPayMode readonly><input class="codename" name="PayModeName" id=PayModeName readonly="readonly" elementtype=nacessary>
		            </TD>
	          	</TR>  
				<TR class='common'>
					<TD class='title'>首期转帐开户行 </TD>
					<TD class='input'>
						<input  NAME=NewBankCode id=NewBankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName id=AppntBankCodeName readonly=true>
					</TD>
					<TD CLASS=title >首期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewAccName id=NewAccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" > 首期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewBankAccNo id=NewBankAccNo class="common" VALUE="" CLASS=common verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');"  MAXLENGTH=25 verify="首期账号|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName id=AppntBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR class="common">
				    <TD class="title">续期交费形式</TD>
					<TD>
						<input class="codeno" name="PayLocation" id=PayLocation verify="续期交费方式|NOTNUlL&CODE:paylocation"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('paylocation',[this,PayLocationName],[0,1]);" onkeyup="return showCodeListKey('paylocation',[this,PayLocationName],[0,1]);"><input class="codename" name="PayLocationName" id=PayLocationName readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class="title" >首、续期账号一致</TD>
					<TD class='title'>
						<input type="checkbox" name='theSameAccount' value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >续期转帐开户行 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=BankCode id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,ReNBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,ReNBankCodeName],[0,1]);" ><input class=codename name=ReNBankCodeName id=ReNBankCodeName readonly=true>
					</TD>
					<TD CLASS=title >续期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=AccName id=AccName VALUE="" CLASS="common wid" MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >续期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=BankAccNo id=BankAccNo class="common wid" VALUE=""  verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="续期账号|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=ReNBankAccNoName id=ReNBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >自动垫交标志 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="AutoPayFlag" id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
            				   CodeData="0|^0|不垫交^1|垫交"
            				   ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">	   
					</TD>
					<TD CLASS=title >自动续保标志 </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="RnewFlag"  id=RnewFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            				   CodeData="0|^-2|非续保^-1|自动续保^0|人工续保"
            				   ondblClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_3',[this],[0,1]);">
					</TD>
					<TD CLASS=title width="109" >保单递送方式</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="GetPolMode" id=GetPolMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            				   CodeData="0|^0|返回银行领取^1|邮寄或专递"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1]);">
					</TD>
				</TR>
				<tr class=common>
					<TD  class= title>溢交保费处理方式</TD>
				    <TD  class= input>
				    	<Input class= 'code' name=OutPayFlag id=OutPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
											   CodeData="0|^1|退费^2|抵交续期保费"
		            				   		   ondblClick="showCodeListEx('ModeSelect_4',[this],[0,1]);" readonly>
				    </TD>
				     <TD CLASS=title>
				      交费方式
				    </TD>
				    <TD CLASS=input COLSPAN=1>
				      <Input NAME=PayIntv id=PayIntv VALUE="" CLASS=code CodeData="0|^0|趸交|^1|按月交|^3|按季交|^6|半年交|^12|按年交" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('PayIntv',[this],[0]);" readonly>
				    </TD>
				    <TD CLASS=title>
				      生存金领取方式
				    </TD>
				    <TD CLASS=input COLSPAN=1>
				      <Input NAME=LiveGetMode id=LiveGetMode VALUE="" CLASS=codeno readonly><Input NAME=LiveGetModeName id=LiveGetModeName VALUE="" CLASS=codename readonly>
				    </TD>
				</tr>	
				<tr class=common>
					<TD  class= title>红利领取方式</TD>
				    <TD  class= input>
				    	<Input NAME=BonusGetMode id=BonusGetMode VALUE="" CLASS=codeno readonly><Input NAME=BonusGetModeName id=BonusGetModeName VALUE="" CLASS=codename readonly>
				    </TD>				     
				</tr>		
	        </Table>  
      	</div> 
    </div>
</Div>
   <hr class=line>
   <!--被保人信息-->
	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured);">
    		</td>
    		<td class= titleImg>
    			 被保人信息（客户号：<input class="common" name="InsuredNo" value="">)<input type="hidden" name="AddressNo" value="">    		
    		</td>
    	</tr>
  </table>
  <Div  id= "divSamePerson" class=maxbox style="display:''" >
						<font color="red">如投保人为被保险人本人，请选择<input type="checkbox" name="SamePersonFlag" onclick="isSamePerson();"></font>
					</Div>
	<div  id= "divInsured" style= "display:''">
	    <Table  class="common">  
			<TR class="common">			             
				<TD  class="title">与第一被保险人关系</TD>             
				<TD  class="input">
						<input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured readonly><Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName  elementtype=nacessary readonly="readonly"  >
				</TD>  
				<TD  class= title>与投保人关系</TD>
				<TD  class= input>
					<Input class="codeno" name="RelationToAppnt" id=RelationToAppnt readonly><Input class="codename" name="RelationToAppntName" id=RelationToAppntName readonly="readonly" elementtype=nacessary>
				</TD>
				<!-- 
				<TD  class="title">客户内部号码</TD>             
				<TD class="input">
						<Input class="codeno" name="SequenceNoCode"  readonly><Input class="codename" name="SequenceNoName" elementtype=nacessary readonly="readonly"  >
				</TD>   
				 -->  
				 <TD  class="title">&nbsp;</TD>             
				<TD class="input">
						<Input class="codeno" type="hidden" name="SequenceNoCode" id=SequenceNoCode  readonly><Input class="codename" name="SequenceNoName" id=SequenceNoName elementtype=nacessary readonly="readonly" type="hidden" >
				</TD>  
			</TR> 
		</Table> 
		<Table  class= common> 
		<TR  class= common>        
			<TD  class= title>姓名</TD>
			<TD  class=input >
				<Input class="common wid" name=Name id=Name elementtype=nacessary verify="被保人姓名|NOTNUlL&LEN<=20"  verifyorder="1" oncopy="return false;" oncut="return false">
			</TD>
			<TD  class= title>性别</TD>
			<TD  class= input>
				<Input class="codeno" name=Sex id=Sex readonly><input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" readonly name="Birthday" id=SexName >                                                                                                                                                                                          
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="IDType" id=IDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  verify="证件类型|NOTNUlL&IDTYPE"  ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class="common wid" name="IDNo" id=IDNo elementtype=nacessary verify="被保人证件号码|LEN<=20"  verifyorder="1" oncopy="return false" oncut="return false">
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>国籍</TD>
			<TD  class= input>
				<input class="codeno" name="NativePlace" id=NativePlace  readonly><input class=codename name=NativePlaceName id=NativePlaceName readonly=true>
			</TD>
			<TD  class= title>户籍所在地</TD>
			<TD  class=input >
				<Input class="common wid" name=RgtAddress  readonly>
			</TD>
			<TD  class= title>婚姻状况</TD>
			<TD  class= input>
				<Input class="codeno" name=Marriage id=Marriage readonly><input class=codename name=MarriageName id=MarriageName readonly=true elementtype=nacessary>
			</TD>  
		</TR>		
		<TR  class= common>
			<TD  class= title>住址</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="HomeAddress" id=HomeAddress verify="住址|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>住址邮政编码</TD>
			<TD  class= input>
				<Input class='common wid' name="HomeZipCode" id=HomeZipCode verify="被保人住址邮政编码|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>	
		<TR  class= common>        
			<TD  class= title>联系电话（1）</TD>
			<TD  class= input>
				<Input class="common wid" name="Phone" id=Phone verify="被保人首选回访电话|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>         
			<TD  class= title>联系电话（2）</TD>
			<TD  class= input>
				<Input class="common wid" name="Phone2" id=Phone2 verify="被保人其他联系电话|LEN<=15&PHONE"  verifyorder="1" >
			</TD>       
			<TD  class= title>电子邮箱</TD>
			<TD  class= input>
				<Input class="common wid" name="EMail" id=EMail MAXLENGTH=40 verify="被保人电子邮箱|EMAIL&LEN<=40"  verifyorder="1" >
			</TD>  
		</TR>
		<TR  class= common>
			<TD  class= title>工作单位</TD>
			<TD  class= input>
				<Input class="common wid" name="GrpName" id=GrpName  verify="被保人工作单位|LEN<=60"  verifyorder="1">
			</TD>
			<TD  class= title>
				        职业
				      </TD>
				      <TD  class= input>
				        <Input class="common wid" name="WorkType" id=WorkType readonly >
				      </TD>
				  	<TD  class= title>
				        兼职
				      </TD>
				      <TD  class= input>
				        <Input class="common wid" name="PluralityType" id=PluralityType readonly >
				      </TD>
		</TR>
		<TR class=common>
			<TD  class= title>职业编码</TD>
			<TD  class=input>
				<Input class="codeno" name="OccupationCode" id=OccupationCode readonly><input class=codename name=OccupationCodeName id=OccupationCodeName readonly=true>
						<!--<Input type="hidden" name="OccupationType"> -->
			</TD>
			<TD  class= title>职业类别</TD>
			<TD  class= input>
				<Input class="codeno" name="OccupationType" id=OccupationType readonly><input class=codename name=OccupationTypeName id=OccupationTypeName readonly=true>
			</TD>
		</TR>				
	</Table>
	</div>  
  <hr class=line>
   <!-- 险种信息部分（列表） -->
     <div id="divLCRisk" style="display:''"> 
	    <table>
	      <tr>
	        <td class=common>
			  	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCRisk1);">
	      	</td>
	        <td class= titleImg>险种信息</td>	        
	      </tr>
	    </table>
		<div id= "divLCRisk1" style= "display: ''" >
	      <table class= common>
	        <tr class= common>
	      	  <td style="text-align: left" colSpan="1">
			  		<span id="spanPolGrid"></span>
			  </td>
			</tr>
		  </table>
		</div>
	 </div>   
   <!-- 受益人信息部分（列表） -->
     <div id="divLCBnf" style="display:''"> 
	    <table>
	      <tr>
	        <td class=common>
			  	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
	      	</td>
	        <td class= titleImg>受益人信息</td>	        
	      </tr>
	    </table>
		<div id= "divLCBnf1" style= "display: ''" >
	      <table class= common>
	        <tr class= common>
	      	  <td style="text-align: left" colSpan="1">
			  		<span id="spanLCBnfGrid"></span>
			  </td>
			</tr>
		  </table>
		</div>
	 </div>   
  <!-- 客户告知信息部分（列表） -->
  <Div id="DivImpart" style="display:''">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divImpart);">
            </td>
            <td class= titleImg>
                客户告知
            </td>
        </tr>
    </table>
    <div id="divImpart" style="display:''">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanImpartGrid'>
            </span>
          </td>
        </tr>
      </table>
    </div>
  </Div>
  <!--回单日期修改 -->
  <Div  id= "divGetPolDate" style= "display: ''">
   <hr class=line>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomGetPolDate);">
    		</td>
    		<td class= titleImg>
    			 保单回执客户签收日期
    		</td>
    	</tr>
    </table>
     <Div  id= "divCustomGetPolDate" class=maxbox1 style= "display: ''">
        <table  class= common>
          <TR  class= common> 
          <!--          
            <TD  class= title>
              保单回执客户签收日期
            </TD>
            <TD  class= input>              
              <input class="coolDatePicker" dateFormat="short" readonly name="CustomGetPolDate" verify="保单回执客户签收日期|date" >
            </TD>
           -->
           <TD  class= title>
              保单回执客户签收日期
            </TD>
            <TD  class= input>              
              <input class="common wid" readonly name="CustomGetPolDate" id=CustomGetPolDate >
            </TD>
			<td CLASS="title"> </td>
			<td CLASS="input"> </td>
			<td CLASS="title"> </td>
			<td CLASS="input"> </td>
          </TR>         
        </table>
      </Div>  
   </Div>  	

<br>
 	
  	 <table>
		<tr>
	    	<td class=common>
	    		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divModifyReason);">
	    	</td>
	    	<td class= titleImg>
	        	保单修改原因录入
	       	</td>   		 
	    </tr>
	  </table>
	  <Div  id= "divModifyReason" class=maxbox1 style= "display: ''">
	   <table>
		<tr>
			<td class=input>
				<textarea class=common name=Reason id=Reason rows="4" cols="50"></textarea>
			</td>
		</tr>
	    </table>		    
      </Div>
	  <input type="hidden" name="ContNo" value="">
	  <input type="hidden" name="SelPolNo" value="">	
	  <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
      <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
      <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">    
      <input type="hidden" id="Operator" name="Operator">
	    <Div id= "divLCImpart2" style= "display: ''" align= left>	
	    <INPUT VALUE="保单修改确认"  class = cssButton  TYPE=button onclick="PolModify();">	
	</Div>
			
  </form>
  <br /><br /><br /><br />
   <form action="./queryAllContInfo.jsp?ContNo=<%=polno%>" method=post name=fmquery target="fraSubmit">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
