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

  <SCRIPT src="LDHospitalDetailQueryInput.js"></SCRIPT> 
  <%@include file="LDHospitalDetailQueryInit.jsp"%>
  <title>���ҽԺ��Ϣ��ѯ</title>
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
  <!--<table class=common border=0 width=100%>
    <tr>
    		
			<td class=titleImg> �������ѯ������ </td>
		</tr>
	</table>-->
	
     
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);">
    		</td>
    		 <td class= titleImg>
        		 ���ҽԺ������Ϣ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "peer" style= "display: ''" class="maxbox1">
<table  class= common  >
  <TR  class= common>
    <TD  class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= 'code' name=HospitalCode id=HospitalCode " verify="���ҽԺ����|code:pehospital"
        ondblclick="return showCodeList('pehospital',[this,HospitalName],[0,1]);" onclick="return showCodeList('pehospital',[this,HospitalName],[0,1]);" onkeyup="return showCodeListKey('pehospital', [this,HospitalName],[0,1]);">
    </TD>
    <TD  class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalName id=HospitalName elementtype=nacessary  verify="���ҽԺ����|notnull">
    </TD>

  </TR>
  <TR  class= common>
    <TD  class= title5>
      �����������
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom id=ManageCom verify="�����������|notnull&len<=8"  ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName],[0,1]);"><input class=codename readonly=true name=ManageComName id=ManageComName elementtype=nacessary>
    </TD>
    <TD  class= title5>
      Э����ǩ������
    </TD>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#ValidityDate'});" verify="ǩ������|notnull&DATE" dateFormat="short" elementtype=nacessary name="ValidityDate" id="ValidityDate"><span class="icon"><a onClick="laydate({elem: '#ValidityDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>

  </TR>  
  <TR  class= common style='display:none'>
      <TD  type='hidden' class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type='hidden'  class= 'codeno' name=HospitalGrade " 
        ondblclick="return showCodeList('hospitalgrade',[this,HospitalGradeName],[0,1]);" onclick="return showCodeList('hospitalgrade',[this,HospitalGradeName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,HospitalGradeName],[0,1]);"><input class=codename readonly=true name=HospitalGradeName type='hidden' >
    </TD>
  </TR>
</table>
</Div>

        <!--<INPUT VALUE="��ѯ"   TYPE=button   class=cssButton onclick="easyQueryClick();">-->
		<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
        

  
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
  				<span id="spanLDHospitalGrid">
  				</span> 
		    </td>
			</tr>
		</table>
	</div>
  	
 
    
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
 <!--<INPUT VALUE="����" TYPE=button   class=cssButton onclick="returnParent();">-->
 <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
 
