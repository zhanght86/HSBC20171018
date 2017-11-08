<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2005-01-24 18:15:01
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">
 var UWPopedom = "<%=request.getParameter("UWPopedom")%>";
 var UWType = "<%=request.getParameter("UWType")%>";
 var tt="hh";
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LDIndUWUserInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDIndUWUserInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDIndUWUserSave.jsp" method=post name=fm id=fm target="fraSubmit">
   
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser1);">
    		</td>
    		 <td class= titleImg>
        		 核保师信息表基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDUWUser1" style= "display: ''" class="maxbox">
<table  class= common >
  <TR  class= common>
    <TD  class= title5>
      核保师编码
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=UserCode id=UserCode elementtype=nacessary  verify="核保师编码|notnull&len<=10">
      <!--Input class= 'common' name=UserCode elementtype=nacessary onchange=checkuseronly(this.value) verify="核保师编码|notnull&len<=10" ondblclick="return showCodeList('uwusercode',[this,UserName],[0,1]);" onkeyup="return showCodeListKey('uwusercode', [this,UserName],[0,1]);"edit by yaory-->
    </TD>
    <TD  class= title5>
      核保师类别
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' readonly  name=UWType  id=UWType  verify="核保师类别|notnull&len<=1" 
      value='1' onfocus="hh();"><input class=codename readonly=true value='个险' name=UWTypeName id=UWTypeName elementtype=nacessary>
      <!--Input class= 'common' name=UWType elementtype=nacessary verify="核保师类别|notnull&len<=1"-->
    </TD></TR>
    <TR  class= common>
    <TD  class= title5>
      核保权限
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UWPopedom id=UWPopedom verify="核保权限|notnull&len<=2" 
        ondblclick="return showCodeList('UWPopedom',[this,UWPopedomName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onclick="return showCodeList('UWPopedom',[this,UWPopedomName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onkeyup="return showCodeListKey('UWPopedom', [this,UWPopedomName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');"><input class=codename readonly=true name=UWPopedomName id=UWPopedomName elementtype=nacessary>
    </TD>
     <TD  class= title5>
      上级核保级别
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UpUwPopedom verify="上级核保级别|notnull&len<=2" ondblClick="showCodeListEx('upuwpopedom',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('upuwpopedom',[this,''],[0,1]);"-->
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name="UpUwPopedom" id="UpUwPopedom" verify="上级核保师|notnull&len<=10"  ondblclick="return showCodeList('UWPopedom',[this,UpUserCodeName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onclick="return showCodeList('UWPopedom',[this,UpUserCodeName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onkeyup="return showCodeListKey('UWPopedom', [this,UpUserCodeName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');"><input class=codename readonly=true name=UpUserCodeName id=UpUserCodeName elementtype=nacessary>
    </TD>
  </TR>  
  <TR  class= common>
    
    <TD  class= title5>
      中间核保动作权限
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' readonly name="UWProcessFlag" id="UWProcessFlag" 
        ondblclick="return showCodeList('yesno',[this,UWProcessFlagName],[0,1]);" onclick="return showCodeList('yesno',[this,UWProcessFlagName],[0,1]);" onkeyup="return showCodeListKey('yesno', [this,UWProcessFlagName],[0,1]);"><input class=codename readonly=true name=UWProcessFlagName id=UWProcessFlagName elementtype=nacessary>
    </TD>   
    <TD  class= title5>
      超权限操作
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' readonly name="SurpassGradeFlag" id="SurpassGradeFlag" 
        ondblclick="return showCodeList('UWPopedom',[this,SurpassGradeFlagName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onclick="return showCodeList('UWPopedom',[this,SurpassGradeFlagName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');" onkeyup="return showCodeListKey('UWPopedom', [this,SurpassGradeFlagName],[0,1],null,'1 and #1#=#1# and code <>#K#','1');"><input class=codename readonly=true name=SurpassGradeFlagName id=SurpassGradeFlagName elementtype=nacessary>
    </TD>    
  </TR>
  <TR  class= common>             
   <TD  class= title5>
      加费评点
    </TD>
    <TD  class= input5>
      <Input class="wid" class= 'common' name=AddPoint id=AddPoint verify="加费评点|notnull&NUM&len<=3" elementtype=nacessary>
    </TD>
    <TD  class= title5>
      员工单权限
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' readonly name="UWOperatorFlag" id="UWOperatorFlag" 
        ondblclick="return showCodeList('yesno',[this,UWOperatorFlagName],[0,1]);" onclick="return showCodeList('yesno',[this,UWOperatorFlagName],[0,1]);" onkeyup="return showCodeListKey('yesno', [this,UWOperatorFlagName],[0,1]);"><input class=codename readonly=true name=UWOperatorFlagName id=UWOperatorFlagName elementtype=nacessary>
    </TD>  </TR>
     <TR  class= common> 
    <TD  class= title5>
      备注
    </TD>
    <TD style="display:none"  class= input5>
      <Input class="wid" class= 'common' name=Remark id=Remark verify="备注|len<=120">
    </TD>
    <TD  style="display:none" class= title5>
      保全权限
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=edpopedom id=edpopedom  ondblclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);" onclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);"  onkeyup="return showCodeListKey('edpopedom', [this,edpopedomName],[0,1]);"><input class=codename readonly=true name=edpopedomName id=edpopedomName elementtype=nacessary>
    </TD>
</TR>
<!--</table>
    </Div>
 <Div style= "display: 'none'">-->
 <TR style="display:none"  class= common>
 	
    <TD  class= title5>
      理赔权限
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ClaimPopedom id=ClaimPopedom verify="理赔权限|len<=1" CodeData= "0|^1|有^0|无" ondblclick=" showCodeList('clPopedom',[this,claimpopedomName],[0,1]);" onclick=" showCodeList('clPopedom',[this,claimpopedomName],[0,1]);"  onkeyup="return showCodeListKey('clPopedom',[this,claimpopedomName],[0,1]);"><input class=codename readonly=true name=claimpopedomName id=claimpopedomName elementtype=nacessary>
    </TD>
    <TD  class= title5>
      特殊职业权限
    </TD>
    <TD  class= input5>
      <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=SpecJob id=SpecJob  ondblclick="return showCodeList('SpecJob',[this,SpecJobName],[0,1]);" onclick="return showCodeList('SpecJob',[this,SpecJobName],[0,1]);" onkeyup="return showCodeListKey('SpecJob',[this,SpecJobName],[0,1]);" verify="核保师编码|notnull&len<=10"><input class=codename readonly=true name=SpecJobName id=SpecJobName >
       <!--Input class= 'codeno' name=edpopedom  ondblclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);"  onkeyup="return showCodeListKey('edpopedom', [this,edpopedomName],[0,1]);"><input class=codename readonly=true name=edpopedomName elementtype=nacessary-->
    </TD>
<!--</DIV>
<Div  id= "divLDUWUser3" style= "display: 'none'">
<table  class= common align='center' >-->
<TR style="display:none"  class= common>
    
    <TD  class= title5>
      最低保额权限
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=LowestAmnt id=LowestAmnt   verify="最低保额权限|notnull&len<=1" 
      	 ondblClick="return showCodeList('LowestAmnt',[this,LowestAmntName],[0,1]);" onClick="return showCodeList('LowestAmnt',[this,LowestAmntName],[0,1]);" onkeyup="return showCodeListKey('LowestAmnt',[this,LowestAmntName],[0,1]);" ><input class=codename readonly=true name=LowestAmntName id=LowestAmntName >
      <!--Input class= 'common' name=UWType elementtype=nacessary verify="核保师类别|notnull&len<=1"-->
    </TD>
    <TD  class= title5>
      超龄人员权限
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=OverAge id=OverAge  verify="超龄人员权限|notnull&len<=2" 
         CodeData= "0|^1|是^0|否" ondblClick="return showCodeList('OverAge',[this,OverAgeName],[0,1]);" onClick="return showCodeList('OverAge',[this,OverAgeName],[0,1]);" onkeyup="return showCodeListKey('OverAge',[this,OverAgeName],[0,1]);" ><input class=codename readonly=true name=OverAgeName id=OverAgeName >
  
    </TD>
     
  </TR> 
  <TR style="display:none">
    <TD  class= title5>
      费率权限
    </TD>
    <TD  class= input5>
      <Input class="wid" class= 'common' name=Rate id=Rate verify="费率权限|value<=1" >
    </TD>
  </TR>
 <TR  class= common-->
      <td class=button >&nbsp;
        	
      </td>    
     
  		     
</table>
    </Div>
   <!-- <TR  class= common style='display:'>
     <td class=button  align=right>-->
          <input class=cssButton  name="setbutton" VALUE="设置保额或保费上限"  TYPE=button onclick="return setclick();">
     <!-- </td> -->  
   
 <!-- </TR>-->
    
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
				</td>
				<td class= titleImg>
					核保结论
				</td>
			</tr>
		</table>
		
		<div  id= "divLCImpart1" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanUWResultGrid" >
						</span>
					</td>
				</tr>
			</table>
		</div><br>
    <div id="div1" style="display: none" class="maxbox">
    	<table>
	    	
	    	<!--TD  class= title>
		      核保师组别
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=UWBranchCode verify="核保师组别|len<=12">
		    </TD>
	    	<TR  class= common>
		    <TD  class= title>
		      其他上级核保师
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=OtherUserCode verify="其他上级核保师|len<=10">
		    </TD>
		    <TD  class= title>
		      其他上级核保师级别
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=OtherUpUWPopedom verify="其他上级核保师级别|len<=2">
		    </TD>
		  </TR-->
    		<TR  class= common>
		    <TD  class= title5>
		      操作员代码
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=Operator id=Operator >
		    </TD>
		    <TD  class= title5>
		      管理机构
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ManageCom id=ManageCom >
		    </TD>
		  </TR>
		  <TR  class= common>
		    <TD  class= title5>
		      入机日期
		    </TD>
		    <TD  class= input5>
		     <Input class= 'cooldatePicker wid' dateFormat="Short" name=MakeDate id=MakeDate >
              <!--<Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
		    </TD>
		    <TD  class= title5>
		      入机时间
		    </TD>
		    <TD  class= input5>
		     <Input class= 'cooldatePicker wid' dateFormat="Short" name=MakeTime id=MakeTime >
               <!--<Input class="coolDatePicker" onClick="laydate({elem: '#MakeTime'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeTime id="MakeTime"><span class="icon"><a onClick="laydate({elem: '#MakeTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
		    </TD>
		  </TR>
		  <TR  class= common>
		    <TD  class= title5>
		      最后一次修改日期
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ModifyDate id=ModifyDate >
		    </TD>
		    <TD  class= title5>
		      最后一次修改时间
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ModifyTime id=ModifyTime >
		    </TD>
		  </TR>
		</table>
	</div>
	
	<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser2);">
				</td>
				<td class= titleImg>
					核保保额上限
				</td>
			</tr>
		</table>
		
		<div  id= "divLDUWUser2" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanUWMaxAmountGrid" >
						</span>
					</td>
				</tr>

       <input type=hidden id="UserName" name="UserName"> 				
			</table>
		</div>  
		
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
    <input type=hidden id="UserCode1" name="UserCode1"> 
    <input type=hidden id="UWType1" name="UWType1"> 
    <input type=hidden id="UwPopedom1" name="UwPopedom1"> 
    <br>
     <%@include file="../common/jsp/OperateButton.jsp"%>
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
