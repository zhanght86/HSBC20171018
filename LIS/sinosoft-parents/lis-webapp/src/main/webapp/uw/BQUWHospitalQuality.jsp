<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.cbcheck.UWHospitalQualityRecordUI"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//�������ƣ�UWCustomerQuality.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//��ʼ����Ϣ
	UWHospitalQualityRecordUI tUWHospitalQualityRecordUI = new UWHospitalQualityRecordUI();
	tUWHospitalQualityRecordUI.submitData(new VData(),"INIT");
	VData mInitData = new VData();
	mInitData = tUWHospitalQualityRecordUI.getResult();
	//SSRS tSSRS_Score= (SSRS)mInitData.get(0);
	//SSRS tSSRS_Manage = (SSRS)mInitData.get(1);
	//SSRS tSSRS_Inner = (SSRS)mInitData.get(2);
	SSRS tSSRS_Quality =(SSRS)mInitData.get(0);
	
%>

<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var EdorNo = "<%=request.getParameter("EdorNo")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var QueryFlag = "<%=request.getParameter("QueryFlag")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>���ҽԺƷ�ʹ��� </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="BQUWHospitalQuality.js"></SCRIPT>
  <%@include file="BQUWHospitalQualityInit.jsp"%>
  
</head>

<body  onload="initForm(EdorNo,ContNo);" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./UWHospitalQualitySave.jsp">
   <br> 
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           ��ѡ�����ҽԺ
         </td>                
          <TD  class= input> <Input class=code readonly name=HospitalCode id="HospitalCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="showCodeListEx('HospitalCode',[this],[0,1],null,null,null,1);" ondblClick="showCodeListEx('HospitalCode',[this],[0,1],null,null,null,1);" 
          onkeyup="showCodeListKeyEx('HospitalCode',[this],[0,1],null,null,null,1);"></TD>
        <td class=title></td>
         <td class= input></td>
         <td class=title></td>
         <td class= input></td>
      </TR>
    
    </table>
    
    <table class= common>
    	<hr class="line">
  <TR  class= common>
    <TD  class= title>
      ���ҽԺ����
    </TD>
    <TD  class= input>
        <Input class= 'common wid' name=HospitalCode1 id="HospitalCode1" verify="���ҽԺ����|code:pehospital" readonly >
    </TD>
    <TD  class= title>
      ���ҽԺ����
    </TD>
    <TD  class= input>
      <input class="common wid" name=HospitalName id="HospitalName" elementtype=nacessary  verify="���ҽԺ����|notnull" readonly>
    </TD>
    <TD  class= title>
      �����������
    </TD>
    <TD  class= input>
       <Input class= 'codeno' name=ManageCom id="ManageCom" verify="�����������|notnull&len<=8"  readonly ><input class=codename readonly=true name=ManageComName id="ManageComName" >
    </TD>
	</TR>
    </table>
 <div id="HealthType">
 	<table class= common align=center>
    	<%
    	//ֱ��д��������
    	 for(int i=1;i<=tSSRS_Quality.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Quality.GetText(i,1);
                	String tSubName = tSSRS_Quality.GetText(i,2);
                	String tother = tSSRS_Quality.GetText(i,3);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	/*
                	if(tother.equals("21"))
                	{
                		%>
                		
                		<td></td>
                		<td></td>
                		<%
                	} 
                	*/
                	//else
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Score' name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//ÿ4�������д���.
                	if(i%4==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Quality.getMaxRow())
                	{
                		%>
                		</tr>
                		<%

                	}
                }
    	%>
  </table>
</div>
<!--  table class=common border=0 width=100%>
	<tr><td class=titleImg align=center>���ҽԺ�����</td>
	</tr>
</table -->
<div id="HealthType">

 	<table class= common align=center>
    	<%
    	//ֱ��д��������
    	/*
    	 for(int i=1;i<=tSSRS_Manage.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Manage.GetText(i,1);
                	String tSubName = tSSRS_Manage.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Manage' name='<%=tSubCode%>' id='<%=tSubCode%>'  value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//ÿ4�������д���.
                	if(i%4==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Manage.getMaxRow())
                	{
                		%>
                		<td></td>
                		</tr>
                		<%

                	}
                }
    	*/
    	%>
  </table>
</div>
<!--  table class=common border=0 width=100%>
	<tr><td class=titleImg align=center>�������</td>
	</tr>
</table-->
<div id="HealthType">

 	<table class= common align=center>
    	<%
    	//ֱ��д��������
    	/*
    	 for(int i=1;i<=tSSRS_Inner.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Inner.GetText(i,1);
                	String tSubName = tSSRS_Inner.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Inner' name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//ÿ4�������д���.
                	if(i%4==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Inner.getMaxRow())
                	{
                		%>
                		<td></td>
                		</tr>
                		<%

                	}
                }
    	*/
    	%>
  </table>
</div>
      <table class= common style="display:none">
      	<hr class="line">
    	  <tr>
    	    <TD  class= title>
          ���������
        </TD>
        <TD  class= input>
            <Input class="codeno" name=QualityFlag id="QualityFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onDblClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onKeyUp="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id="QualityFlagName" readonly=true>
          </TD> 
         <td></td>
         <td></td>
         <td></td>
         <td></td>  
    	  </tr>	  
    	
    </table>


  
  <table width="80%" height="20%" class= common>
    <TR  class= common> 
      <TD width="100%" height="15%"  class= title> ��ע��Ϣ </TD>
    </TR>
    <TR  class= common>
      <TD height="85%"  class= title><textarea name="Remark" id="Remark" cols="226" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
  <input class=input type=hidden id="CheckedItem1" name="CheckedItem1" >
  <input class=input type=hidden id="CheckedItem2" name="CheckedItem2" >
  <input class=input type=hidden id="CheckedItem3" name="CheckedItem3" >


  <INPUT CLASS=cssButton VALUE="ȷ  ��" name=sure TYPE=button onClick="UpdateClick()">
  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="returnParent()">
  
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
