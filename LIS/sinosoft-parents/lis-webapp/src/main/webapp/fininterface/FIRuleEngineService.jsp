<html>
<%
//�������� :FIRuleEngineService.jsp
//������ :���ݲ���˶�
//������ :���
//�������� :2008-09-28
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
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRuleEngineService.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleEngineServiceInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRuleEngineServiceSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divFIRuleEngineService" style= "display: ''">
 		<table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,hub1a);"></td>
    		 <td class= titleImg>
        		���ݲ���˶�
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "hub1a" style= "display: ''"><div class="maxbox">
   	<Table class= common>
   	
   	<TR  class= common>
  				<TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
					  �汾״̬
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionState2 id=VersionState2 readonly=true>
				</TD>
  		</TR>
  		<TR  class= common>
  				<TD class= title5>
					  �汾��ʼ����
				 </TD>
				 <TD class=input5>
				 	<!--<input class=readonly name=StartDay readonly=true>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class= title5>
					  �汾��������
				 </TD>
				 <TD class=input5>
				 	<!--<input class=readonly name=EndDay readonly=true>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
  		</TR>
  		
		 <TR  class= common>   
          		<TD  class= title5 width="25%">��ʼ����</TD>
          		<TD  class= input5 width="25%">
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                 </TD>      
          		<TD  class= title5 width="25%">��������</TD>
          		<TD  class= input5 width="25%">
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
     	 </TR> 
  		<TR  class= common>
  				<TD class= title5>
					  �¼������
				 </TD>
				 <TD class=input5>
				 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpoint id=callpoint verify="�¼������|NOTNULL" ondblClick="showCodeList('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointName id=callpointName readonly=true elementtype=nacessary>					
				</TD>
               <TD class= title5>����һѡ������Ҫ��ӵ��¼��㲢����׷��</TD> 
               <TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpointB id=callpointB verify="�¼���|NOTNULL" readonly=true ondblClick="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointBName id=callpointBName readonly=true elementtype=nacessary>			</TD>
			</TR>
		<!--	<TR  class= common>
				<TD class= common align=left>
		   	   
		    	</TD>
		    	 <TD class= common align=left>
		     	 
		    	</TD>
		    	<TD class= title5>
					  ����һѡ������Ҫ��ӵ��¼��㲢����׷��
				 </TD>
		    	<TD class=input5>
		     	 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpointB id=callpointB verify="�¼���|NOTNULL" readonly=true ondblClick="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointBName id=callpointBName readonly=true elementtype=nacessary>					
		    	</TD>
  		</TR>-->
   </Table>
   </Div>
   </Div>
  
 <!-- <INPUT VALUE="�汾��ѯ" TYPE=button class= cssButton name= addbutton onclick="VersionQuery()" >-->
  <!--<INPUT VALUE="׷  ��" TYPE=button class= cssButton name="superaddbutton" onclick="return superaddClick();">-->
  <!--<INPUT VALUE="��  ��" TYPE=button class= cssButton name="clearbutton" onclick="return clearClick();">-->
   <input type=hidden id="OperateType" name="OperateType">
   <INPUT type=hidden name=VersionState value=''>
	 <!--<INPUT VALUE="���ݲ���˶�" TYPE=button class= cssButton name="datajudge" onclick="return DataJudge();"> -->
     
     <a href="javascript:void(0);" name= addbutton class="button" onClick="VersionQuery();">�汾��ѯ</a>
     <a href="javascript:void(0);" name="superaddbutton" class="button" onClick="return superaddClick();">׷    ��</a>
     <a href="javascript:void(0);" name="clearbutton" class="button" onClick="return clearClick();">��    ��</a>
     <a href="javascript:void(0);" name="datajudge" class="button" onClick="return DataJudge();">���ݲ���˶�</a>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
