<html>
<%
//�������� :CostDataAcquisitionDefInput.jsp
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
  String tAcquisitionType = "";
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>"; 
  var ttAcquisitionType = "<%=tAcquisitionType%>"
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "CostDataAcquisitionDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<jsp:include page="CostDataAcquisitionDefInputInit.jsp"  flush='true'/>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./CostDataAcquisitionDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  <Div id= "divCostDataAcquisitionDef" style= "display: ''">
  <table>
    	<tr>
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCostTypeDefQ);">
                </td>
    		 <td class= titleImg>
        		ƾ֤�������ݲɼ�
       		 </td>   		 
    	</tr>
    </table>
    
     <Div  id= "divCostTypeDefQ" style= "display: ''" class="maxbox1">
    <!--<td class=button width="10%" align=right>
				
	</td>-->
<table class= common border=0 width=100%>
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	<input class=wid name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	�汾״̬
		    	</TD>
		    	<TD class= input5>
		    	<input class=wid name=VersionState2 id=VersionState2 readonly=true>
		   		 </TD>
	</tr>
	</table>
    </table>
    </div>
	<!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return VersionStateQuery();">-->
    <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return VersionStateQuery();">�汾��Ϣ��ѯ</a><br><br>
    <div class="maxbox">
	
	
	<table class= common>
		<tr class= common>
				 <TD class= title5>
					  ���ݲɼ����
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=AcquisitionID id=AcquisitionID readonly=true>
				</TD>
				 	 
				<TD class= title5>
					  ���ݲɼ�����
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= AcquisitionType id= AcquisitionType verify="���ݲɼ�����|NOTNULL"   ondblClick="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onMouseDown="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onkeyup="showCodeListKey('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);"><input class=codename name=AcquisitionTypeName readonly=true elementtype=nacessary>
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
					  ���û��������ͱ���
				 </TD>
				 <td class=input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CostOrDataID id=CostOrDataID verify="���û��������ͱ���|NOTNULL" ondblClick="showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);" onMouseDown="showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);" onkeyup="showCodeListKey('CostOrData',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);"><input class=codename name=CostOrDataName readonly=true elementtype=nacessary></TD>
          </TD>
				<TD class= title5>
					  ���ݲɼ���ʽ
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DistillMode id= DistillMode verify="���ݲɼ���ʽ|NOTNULL" readonly=true  CodeData="0|^1|ͨ��Sql^2|ͨ����" ondblClick="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);"><input class=codename name=DistillModeName readonly=true elementtype = nacessary >
				</TD>
	 </tr>
	 <tr class= common>
	 	<TD class= title5>
	 		�ɼ����ݿ�����
	 	</TD>
	 	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DataSourceType id= DataSourceType verify="�ɼ����ݿ�����|NOTNULL" readonly=true  CodeData="0^01|��������Դ|^02|�������Դ" ondblClick="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);"><input class=codename name=DataSourceTypeName readonly=true elementtype = nacessary >
    </TD>
    <TD class= title5>
					  ����
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=Remark id=Remark >
				</TD>
   </tr>

	</table>
	
<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
	 <tr class= common>
				<TD class= title5>
					  �ɼ�������
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=DistillClass id=DistillClass >
				</TD>
				<TD class= title5>
					  ��һ�ɼ�������
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=DistillClassForOne id=DistillClassForOne >
				</TD>	
	</tr>
 </table> 	  
</div>	
<Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ɼ�SQL������</TD>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��һ�ɼ���SQL</TD>
			</tr>
            
			<tr  class= common>
				<TD  class= common style="padding-left:18px; padding-top:10px">
					<textarea name="DistillSQL" id="DistillSQL" verify="�ɼ�SQL������|len<4000" verifyorder="1" cols="66%" rows="5" witdh=50% class="common" ></textarea>
				</TD>
				<TD  class= common style="padding-left:18px; padding-top:10px">
					<textarea name="DistillSQLForOne" id="DistillSQLForOne" verify="��һ�ɼ���SQL|len<4000" verifyorder="1" cols="66%" rows="5" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
			
		</table>
</div>
	<INPUT VALUE="ƾ֤�������ݲɼ���Ϣ��ѯ" TYPE=button class= cssButton onclick="queryClick()">
	<INPUT VALUE="����ɼ�" TYPE=button class= cssButton onclick="AcquisitionClick()">	
  <INPUT VALUE="���" TYPE=button class= cssButton name= addbutton id= addbutton onclick="submitForm()">   
  <INPUT VALUE="�޸�" TYPE=button class= cssButton name= updatebutton id= updatebutton onclick="updateClick()">
  <INPUT VALUE="ɾ��" TYPE=button class= cssButton name= deletebutton id= deletebutton onclick="deleteClick()">
  <INPUT VALUE="����" TYPE=button class= cssButton name= resetbutton id= resetbutton onclick="resetAgain()">
  <br><br>
  <!--  <a href="javascript:void(0);" class="button" onClick="queryClick();">ƾ֤�������ݲɼ���Ϣ��ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="AcquisitionClick();">����ɼ�</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">��    ��</a>-->
	</table>
    </div>
</Div>
<hr class="line">
			 <!--<INPUT class=cssButton name="CostDataBaseDefInputbutton" id="CostDataBaseDefInputbutton" VALUE="���ݲɼ�����Դ����"  TYPE=button onclick="return CostDataBaseDefInputClick();">
			 <INPUT class=cssButton name="CostDataKeyDefInputbutton" id="CostDataKeyDefInputbutton" VALUE="ƾ֤����������������"  TYPE=button onclick="return CostDataKeyDefInputClick();">--><br>
             <a href="javascript:void(0);" name="CostDataBaseDefInputbutton" id="CostDataBaseDefInputbutton" class="button" onClick="return CostDataBaseDefInputClick();">���ݲɼ�����Դ����</a>
             <a href="javascript:void(0);" name="CostDataKeyDefInputbutton" id="CostDataKeyDefInputbutton" class="button" onClick="return CostDataKeyDefInputClick();">ƾ֤����������������</a>
             
 <input type=hidden id="OperateType" name="OperateType">
 <INPUT TYPE=hidden NAME=tempAcquisitionID id=tempAcquisitionID VALUE=''>
 <INPUT TYPE=hidden NAME=tempAcquisitionType id=tempAcquisitionType VALUE=''>
 <INPUT TYPE=hidden NAME=tempCostOrDataID id=tempCostOrDataID VALUE=''>
 <INPUT type=hidden name=VersionState id=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br> <br> <br> <br>
</body>
</html>
