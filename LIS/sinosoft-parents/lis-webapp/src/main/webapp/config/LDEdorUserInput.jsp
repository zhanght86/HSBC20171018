<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  <SCRIPT src="LDEdorUserInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDEdorUserInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDEdorUserSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser1);">
    		</td>
    		 <td class= titleImg>
        		 �˱�ʦ��Ϣ�������Ϣ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDUWUser1" style= "display: ''" class="maxbox">
<table  class= common >
  <TR  class= common>
    <TD  class= title5>
      �˱�ʦ����
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=UserCode id=UserCode elementtype=nacessary  verify="�˱�ʦ����|notnull&len<=10" onblur=" trimcode();">
      <!--Input class= 'common' name=UserCode elementtype=nacessary onchange=checkuseronly(this.value) verify="�˱�ʦ����|notnull&len<=10" ondblclick="return showCodeList('uwusercode',[this,UserName],[0,1]);" onkeyup="return showCodeListKey('uwusercode', [this,UserName],[0,1]);"edit by yaory-->
    </TD>
    
    <TD  class= title5>
      �˱�Ȩ��
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UWPopedom id=UWPopedom  ondblclick="return showCodeList('bqgrppopedom',[this,UWPopedomName],[0,1]);" onclick="return showCodeList('bqgrppopedom',[this,UWPopedomName],[0,1]);" onkeyup="return showCodeListKey('bqgrppopedom', [this,UWPopedomName],[0,1]);"><input class=codename readonly=true name=UWPopedomName id=UWPopedomName elementtype=nacessary>
  
    </TD>
     
      
  </TR>  
  <TR  class= common>
    <TD  class= title5>
      �ϼ��˱�����
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UpUwPopedom verify="�ϼ��˱�����|notnull&len<=2" ondblClick="showCodeListEx('upuwpopedom',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('upuwpopedom',[this,''],[0,1]);"-->
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UpUwPopedom id=UpUwPopedom verify="�ϼ��˱�ʦ|notnull&len<=10"  ondblclick="return showCodeList('bqgrppopedom',[this,UpUserCodeName],[0,1]);" onclick="return showCodeList('bqgrppopedom',[this,UpUserCodeName],[0,1]);" onkeyup="return showCodeListKey('bqgrppopedom', [this,UpUserCodeName],[0,1]);"><input class=codename readonly=true name=UpUserCodeName id=UpUserCodeName elementtype=nacessary>
    </TD>
     <TD  class= title5></TD>
     <TD  class= input5></TD>
    </TR></table>
    <table style="display:none"  class= common >
  <TR  class= common>
    <TD  class= title5>
      ����Ȩ��
    </TD>
    <TD  class= input5>
      <!--Input class= 'codeno' name=ClaimPopedom ondblclick=" showCodeList('clPopedom',[this,claimpopedomName],[0,1]);"  onkeyup="return showCodeListKey('clPopedom',[this,claimpopedomName],[0,1]);"><input class=codename readonly=true name=claimpopedomName elementtype=nacessary-->
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' value="A" name=ClaimPopedom id=ClaimPopedom verify="�ϼ��˱�ʦ|notnull&len<=10"  ondblclick="return showCodeList('uwpopedom',[this,claimpopedomName],[0,1]);" onclick="return showCodeList('uwpopedom',[this,claimpopedomName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,claimpopedomName],[0,1]);"><input class=codename readonly=true name=claimpopedomName id=claimpopedomName elementtype=nacessary>
    </TD>     
    
   
    
    
    
  </TR>
  <!--TR  class= common>             
    <TD  class= title>
      ��ע
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Remark verify="�˱�ʦ���|len<=120">
    </TD>
    <TD  class= title>
      �ӷ�����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AddPoint verify="�ӷ�����|notnull&NUM&len<=3" elementtype=nacessary>
    </TD>
</TR-->
<!--</table>
    </Div>
<Div  id= "divLDUWUser3" style= "display: 'none'">
<table  class= common align='center' >-->
<TR  class= common>
	
    <TD  class= title5>
      ����ְҵȨ��
    </TD>
    <TD  class= input5>
      <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=SpecJob id=SpecJob  ondblclick="return showCodeList('SpecJob',[this,SpecJobName],[0,1]);" onclick="return showCodeList('SpecJob',[this,SpecJobName],[0,1]);" onkeyup="return showCodeListKey('SpecJob',[this,SpecJobName],[0,1]);" verify="�˱�ʦ����|notnull&len<=10"><input class=codename readonly=true name=SpecJobName id=SpecJobName >
       <!--Input class= 'codeno' name=edpopedom  ondblclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);"  onkeyup="return showCodeListKey('edpopedom', [this,edpopedomName],[0,1]);"><input class=codename readonly=true name=edpopedomName elementtype=nacessary-->
    </TD>
    <TD  class= title5>
      ��ͱ���Ȩ��
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=LowestAmnt id=LowestAmnt   verify="��ͱ���Ȩ��|notnull&len<=1" 
      	 ondblClick="return showCodeList('LowestAmnt',[this,LowestAmntName],[0,1]);" onClick="return showCodeList('LowestAmnt',[this,LowestAmntName],[0,1]);" onkeyup="return showCodeListKey('LowestAmnt',[this,LowestAmntName],[0,1]);" ><input class=codename readonly=true name=LowestAmntName id=LowestAmntName >
      <!--Input class= 'common' name=UWType elementtype=nacessary verify="�˱�ʦ���|notnull&len<=1"-->
    </TD></TR>
    <TR  class= common>
    <TD  class= title5>
      ������ԱȨ��
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=OverAge id=OverAge  verify="������ԱȨ��|notnull&len<=2" 
         CodeData= "0|^1|��^0|��" ondblClick="return showCodeList('OverAge',[this,OverAgeName],[0,1]);" onClick="return showCodeList('OverAge',[this,OverAgeName],[0,1]);" onkeyup="return showCodeListKey('OverAge',[this,OverAgeName],[0,1]);" ><input class=codename readonly=true name=OverAgeName id=OverAgeName >
  
    </TD>
     <TD  class= title5>
      ����Ȩ��
    </TD>
    <TD  class= input5>
      <Input class= 'common wid' name=Rate id=Rate verify="����Ȩ��|value<=1" >
    </TD>
  </TR> 
<TR  class= common>
		    <TD  class= title5>
		      ����Ա����
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=Operator id=Operator >
		    </TD>
		    <TD  class= title5>
		      �������
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ManageCom id=ManageCom >
		    </TD>
		  </TR>
		  <TR  class= common>
		    <TD  class= title5>
		      �������
		    </TD>
		    <TD  class= input5>
		      <!--<Input class= 'cooldatePicker' dateFormat="Short" name=MakeDate >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		    </TD>
		    <TD  class= title5>
		      ���ʱ��
		    </TD>
		    <TD  class= input5>
		      <!--<Input class= 'cooldatePicker' dateFormat="Short" name=MakeTime >-->
               <Input class="coolDatePicker" onClick="laydate({elem: '#MakeTime'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeTime id="MakeTime"><span class="icon"><a onClick="laydate({elem: '#MakeTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		    </TD>
		  </TR>
		  <TR  class= common>
		    <TD  class= title5>
		      ���һ���޸�����
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ModifyDate id=ModifyDate >
		    </TD>
		    <TD  class= title5>
		      ���һ���޸�ʱ��
		    </TD>
		    <TD  class= input5>
		      <Input class= 'common wid' name=ModifyTime id=ModifyTime >
		    </TD>
		  </TR>
</table>
    </Div>
    
		
		
		<div  id= "divLCImpart1" style= "display: 'none'">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanUWResultGrid" >
						</span>
					</td>
				</tr>
			</table>
		</div>
    <div id="div1" style="display: 'none'">
    	<table>
	    	
	    	<!--TD  class= title>
		      �˱�ʦ���
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=UWBranchCode verify="�˱�ʦ���|len<=12">
		    </TD>
	    	<TR  class= common>
		    <TD  class= title>
		      �����ϼ��˱�ʦ
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=OtherUserCode verify="�����ϼ��˱�ʦ|len<=10">
		    </TD>
		    <TD  class= title>
		      �����ϼ��˱�ʦ����
		    </TD>
		    <TD  class= input>
		      <Input class= 'common' name=OtherUpUWPopedom verify="�����ϼ��˱�ʦ����|len<=2">
		    </TD>
		  </TR-->
    		
		</table>
	</div>
	
	<!--table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser2);">
				</td>
				<td class= titleImg>
					�˱���������
				</td>
			</tr>
		</table-->
		
		<div  id= "divLDUWUser2" style= "display: 'none'">
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
    <input type=hidden id="UWPopedom1" name="UWPopedom1"> 
    <input type=hidden id="MaxGrade" name="MaxGrade"> 
    <br>
    <%@include file="../common/jsp/OperateButton.jsp"%>
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
