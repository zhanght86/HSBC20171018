<html>
<%
//�������� :CostDataAcquisitionDefQuery.jsp
//������ :ƾ֤�������ݲɼ�����
//������ :���
//�������� :2008-08-18
//
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
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "CostDataAcquisitionDefQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CostDataAcquisitionDefQueryInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCostDataAcquisitionDefQuery);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </tr>
  </table>
  <Div  id= "divCostDataAcquisitionDefQuery" style= "display: ''" class="maxbox">
  	<table class= common border=0 width=100%>
  <table class=common>
		<tr class= common>
				 <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true >
				</TD>
				 <TD class= title5>
					  ���ݲɼ����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=AcquisitionID id=AcquisitionID >
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
					  ���ݲɼ�����
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= AcquisitionType id= AcquisitionType verify="���ݲɼ�����|NOTNULL"  ondblClick="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onMouseDown="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onkeyup="showCodeListKey('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);"><input class=codename name=AcquisitionTypeName id=AcquisitionTypeName readonly=true >
				</TD>
				  <TD class= title5>
					  ���û��������ͱ���
				 </TD>
				 <td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CostOrDataID id=CostOrDataID  ondblclick="return showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,'1');" onMouseDown="return showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,'1');" onkeyup=" return showCodeListKey('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,'1');"><input class=codename name=CostOrDataName id=CostOrDataName readonly=true>  </TD>
	 </tr>
	 <tr class= common>
	 	<TD class= title5>
	 		�ɼ����ݿ�����
	 	</TD>
	 	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DataSourceType id= DataSourceType verify="�ɼ����ݿ�����|NOTNULL"  CodeData="0|^02|�������Դ" ondblClick="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);"><input class=codename name=DataSourceTypeName id=DataSourceTypeName readonly=true >
    </TD>
    	<TD class= title5>
				����
			</TD>
			<TD class=input5>
				 <Input class="wid" class=common name=Remark id=Remark >
			</TD>
   </tr>
	 <tr class= common>
	 			 <TD class= title5>
					  ���ݲɼ���ʽ
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DistillMode id= DistillMode verify="���ݲɼ���ʽ|NOTNULL"  CodeData="0|^1|ͨ��Sql^2|ͨ����" ondblClick="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);"><input class=codename name=DistillModeName id=DistillModeName readonly=true >
				</TD>

	</tr>
</table></Div>
	           <!--<input class="cssButton" type=button value="��  ѯ" onclick="QueryForm()">-->
   				 <a href="javascript:void(0);" class="button" onClick="QueryForm();">��    ѯ</a>	
 	
     
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divCostDataAcquisitionDefQueryGrid);">
    		</td>
    		<td class= titleImg>
    			 ƾ֤�������ݲɼ���Ϣ��ѯ���
    		</td>
    	</tr>
    </table>
		<Div  id= "divCostDataAcquisitionDefQueryGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanCostDataAcquisitionDefQueryGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        
         <!--<div align="left"><input class="cssButton" type=button value="��  ��" onclick="ReturnData()"></div>-->
         <center>
      <INPUT VALUE="��  ҳ" TYPE=Button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=Button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=Button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=Button onclick="turnPage.lastPage();" class="cssButton93"></center>
   </Div>
   <a href="javascript:void(0);" class="button" onClick="ReturnData();">��    ��</a>
   <input type=hidden id="OperateType" name="OperateType">
</table>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
