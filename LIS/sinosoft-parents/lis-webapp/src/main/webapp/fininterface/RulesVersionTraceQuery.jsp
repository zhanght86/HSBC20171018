<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
//�������ƣ�RulesVersionTraceQuery.jsp
//�����ܣ��������汾ά���켣��ѯҳ��
//�������ڣ�2008-08-21
//������  ��FanXin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
  var VersionNo = <%=request.getParameter("VersionNo")%>;
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./RulesVersionTraceQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./RulesVersionTraceQueryInit.jsp"%>

<title>�汾��Ϣ��ѯ</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion1);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFIRulesVersion1" style= "display: ''" class="maxbox1">
  <table  class= common>
		<tr class= common>
         <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	 <input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true >
				 </TD>
         <TD  class= title5>
           ά�����
         </TD>
         <TD class=input5>
				 	 <Input class="wid" class=common name=Maintenanceno id=Maintenanceno >
				 </TD>          
		</tr>
		<tr class= common>
         <TD class= title5>
					  ά��״̬
				 </TD>
				 <TD class=input5>
				 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name= MaintenanceState id= MaintenanceState verify="ά��״̬|notnull" CodeData="0|^01|���^02|ά��^03|����"  ondblclick="return showCodeListEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);"  onclick="return showCodeListEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);" onkeyup="return showCodeListKeyEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);"  ><input class=codename name=MaintenanceStateName id=MaintenanceStateName readonly=true></TD>
				 </TD>
         <TD  class= title5>
           ά������
         </TD>
         <TD class=input5>
				 	 <Input class="wid" class=common name=MaintenanceReMark id=MaintenanceReMark >
				 </TD>          
		</tr>

      </table></div>	
          <!--<INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
     
          
          
   <table>    	
    <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion2);">
    		</td>
    		<td class= titleImg>
    			 �汾ά���켣��ѯ���
    		</td>
    	</tr>
   </table>

    
  	<Div  id= "divFIRulesVersion2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanRulesVersionTraceGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
     <!-- <INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton">-->
  	</div>  
<!--<INPUT VALUE="��  ��" TYPE=button onclick="returnParent();" class="cssButton">-->
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

