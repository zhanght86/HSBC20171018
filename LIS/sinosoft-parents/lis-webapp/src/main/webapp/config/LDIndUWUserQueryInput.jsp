<%
//�������ƣ�LDUWUserInput.jsp
//�����ܣ���������
//�������ڣ�2005-01-24 18:15:01
//������  ��ctrHTML
//������  ��  
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <SCRIPT src="LDIndUWUserQueryInput.js"></SCRIPT> 
  <%@include file="LDIndUWUserQueryInit.jsp"%>
  
  <title></title>
</head>
<%
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput)session.getValue("GI");
%>
<script>
  var comCode = "<%=tG.ComCode%>";
  var manageCom = "<%=tG.ManageCom%>";
  var operator = "<%=tG.Operator%>";
</script>
<body onload="initForm();">
  <form method=post name=fm target=fraSubmit>
  
  <!-- ���밴ť���� -->
  <%@include file="../common/jsp/InputButton.jsp"%>
    
  <!-- ��ѯ�������� -->
  <!-- ��ѯ����Title -->
  <table class=common border=0 width=100%>
    <tr>
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUserGrid1);"></td>
			<td class=titleImg align=center> �������ѯ������ </td>
		</tr>
	</table>
	
  <Div  id= "divLDUWUserGrid1" style= "display: ''" class="maxbox">    
<table  class= common >
  <TR  class= common>
     <TD  class= title>
      �˱�ʦ����
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=UserCode id=UserCode  verify="�˱�ʦ����|notnull&len<=10" >
    </TD>
    <TD  class= title>
      �˱�ʦ���
    </TD>
    <TD  class= input>
      <Input  class= 'codeno' readonly  name=UWType verify="�˱�ʦ���|notnull&len<=1" 
      value='1' ><input class=codename readonly=true value='����' name=UWTypeName id=UWTypeName elementtype=nacessary>
      <!--Input class= 'common' name=UWType elementtype=nacessary verify="�˱�ʦ���|notnull&len<=1"-->
    </TD>
    <TD  class= title>
      �˱�Ȩ��
    </TD>
    <TD  class= input>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UWPopedom id=UWPopedom verify="�˱�Ȩ��|notnull&len<=2" 
        ondblclick="return showCodeList('uwpopedom',[this,UWPopedomName],[0,1]);"  onclick="return showCodeList('uwpopedom',[this,UWPopedomName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,UWPopedomName],[0,1]);"><input class=codename readonly=true name=UWPopedomName id=UWPopedomName >
  
    </TD>
     
  </TR>  
  <TR  class= common>
    <TD  class= title>
      �ӷ�����
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AddPoint id=AddPoint verify="�ӷ�����|len<=2">
    </TD>
     <TD  class= title>
      �ϼ��˱�����
    </TD>
    <TD  class= input>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UpUWPopedom id=UpUWPopedom verify="�ϼ��˱�ʦ|notnull&len<=10"  ondblclick="return showCodeList('uwpopedom',[this,UpUserCodeName],[0,1]);" onclick="return showCodeList('uwpopedom',[this,UpUserCodeName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,UpUserCodeName],[0,1]);"><input class=codename readonly=true name=UpUserCodeName id=UpUserCodeName >
    </TD>
    <TD style="display:none"  class= title>
	      �˱�ʦ����
	    </TD>
	    <TD style="display:none"  class= input>
	      <Input class= 'common wid' name=UserDescription id=UserDescription >
	    </TD>
  </TR>
    
   
    
    <!--TD  class= title>
      ��ϯ�˱���־
    </TD>
    <TD  class= input>
      <Input class= 'code' name=PopUWFlag verify="�˱�ʦ���|len<=1" CodeData= "0|^1|����ϯ�˱�ʦ^0|������ϯ�˱�ʦ" ondblClick="showCodeListEx('PopUWFlag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('PopUWFlag',[this,''],[0,1]);">
    </TD>
    <TD  class= title>
      ��Ч��ʼ����
    </TD>
    <TD  class= input>
      <Input class= 'cooldatePicker' dateFormat="Short" name=ValidStartDate >
    </TD>
  </TR>  
  <TR  class= common>
    <TD  class= title>
      ��Ч��������
    </TD>
    <TD  class= input>
      <Input class= 'cooldatePicker' dateFormat="Short" name=ValidEndDate >
    </TD>
    <TD  class= title>
      �Ƿ���ͣ
    </TD>
    <TD  class= input>
      
       <Input class= 'code' name=IsPendFlag verify="�Ƿ���ͣ|len<=1" CodeData= "0|^1|��^0|��" ondblClick="showCodeListEx('IsPendFlag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('IsPendFlag',[this,''],[0,1]);">
   
    </TD>
    <TD  class= title>
      ��ͣԭ��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PendReason verify="�˱�ʦ���|len<=120">
    </TD>
  </TR>
  <TR  class= common-->
    

 
  
</table>
  <Div style="display:none">
      <TD  class= title>
      ��ȫȨ��
    </TD>
    <TD  class= input>
       <Input class= 'codeno' name=edpopedom  ondblclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);"  onkeyup="return showCodeListKey('edpopedom', [this,edpopedomName],[0,1]);"><input class=codename readonly=true name=edpopedomName >
    </TD>
    <TD  class= title>
      ����Ȩ��
    </TD>
    <TD  class= input>
      <Input class= 'codeno' name=ClaimPopedom verify="����Ȩ��|len<=1" CodeData= "0|^1|��^0|��" ondblclick=" showCodeList('clPopedom',[this,claimpopedomName],[0,1]);"  onkeyup="return showCodeListKey('clPopedom',[this,claimpopedomName],[0,1]);"><input class=codename readonly=true name=claimpopedomName >
    </TD>
 </div>	
  <div id="div1" style="display: none">
  	<table class="common">

	    
  	 	<TR  class= common>
        
	  	 <TD  class= title>
	      �Ƿ���ͣ
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=IsPendFlag id=IsPendFlag >
	    </TD>	
	    <TD  class= title>
	      ��ͣԭ��
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=PendReason id=PendReason >
	    </TD>
	    <TD  class= title>
	      ��ע
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=Remark id=Remark  >
	    </TD>
	  </TR>
  		<TR  class= common>
	    <TD  class= title>
	      ����Ա����
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=Operator id=Operator >
	    </TD>
	    <TD  class= title>
	      �������
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ManageCom id=ManageCom >
	    </TD>
         <TD  class= title>
	      �������
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=MakeDate id=MakeDate >
	    </TD>
	  </TR>
	  <TR  class= common>
	   
	    <TD  class= title>
	      ���ʱ��
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=MakeTime id=MakeTime >
	    </TD>
         <TD  class= title>
	      ���һ���޸�����
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ModifyDate id=ModifyDate >
	    </TD>
	    <TD  class= title>
	      ���һ���޸�ʱ��
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ModifyTime id=ModifyTime >
	    </TD>
	  </TR> 
	 </table>
	</div></Div>
    
  <!--<table class=common border=0 width=100%>
    <TR class=common>  
      <TD  class=title>
        
          		
      </TD>
    </TR>
  </table>  --><br>
  <INPUT VALUE="��ѯ"   TYPE=button   class=cssButton onclick="easyQueryClick();">
  
  <!-- ��ѯ������� -->      
  <table>
  	<tr>
      <td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser1);">
  		</td>
  		<td class=titleImg>
  			 ��Ϣ
  		</td>
  	</tr>
  </table>
  <!-- ��Ϣ���б� -->
	<Div id="divLDUWUser1" style="display:''">
    <table class=common>
    	<tr class=common>
	  		<td text-align:left colSpan=1>
  				<span id="spanLDUWUserGrid">
  				</span> 
		    </td>
			</tr>
		</table>
	</div>
  	
 <!-- <Div id= "divPage" align=center style= "display: '' ">
    <INPUT CLASS=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
    <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
    <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
    <INPUT CLASS=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
  </Div>--><br>
   <INPUT VALUE="����" TYPE=button   class=cssButton onclick="returnParent();"> 			
<!-- ¼���ύ��Ϣ����     
  <table class=common border=0 width=100%>
    <tr>
  		<td class=titleImg align= center>¼����Ϣ��</td>
  	</tr>
  </table> 
     		  								
  <table  class=common align=center>
    <TR CLASS=common>
      <TD  class=title>
        ֪ͨ������
      </TD>
      <TD  class=input>
        <Input NAME=GetNoticeNo class=common verify="֪ͨ������|notnull">
      </TD>
      <TD  class=title>
        <INPUT VALUE="�� ӡ" class=common TYPE=button onclick="PPrint()">
      </TD>
      
    </TR>
  </table>
  
  <br>           
-->    
 
  <INPUT VALUE="" TYPE=hidden name=serialNo>
  <input type=hidden id="fmtransact" name="fmtransact">
 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
 
