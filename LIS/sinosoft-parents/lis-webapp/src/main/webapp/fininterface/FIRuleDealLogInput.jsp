<html>
<%
//�������� :FIRuleDealLogInput.jsp
//������ :У����־��ѯ
//������ :���
//�������� :2008-09-09
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
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
<SCRIPT src = "FIRuleDealLogInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealLogInputInit.jsp"%>
</head>
<body  onload="initForm();">
  <form action="./FIRuleDealLogSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <input type=hidden name=sStartDate id=sStartDate>
  <input type=hidden name=sEndDate id=sEndDate>
  	  <Div id= "divLLReport1" style= "display: ''">
  <table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,huba);"></td>
    		 <td class= titleImg>
        		У����־����
       		 </td>   		 
    	</tr>
    </table>
     <Div id= "huba" style= "display: ''"><div class="maxbox1">
  <td class=button width="10%" align=right>
		
	</td>
<table class= common border=0 width="1203" height="339">
    <table class= common>
	<tr class= common>
				 <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	�汾״̬
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=VersionState2 id=VersionState2 readonly=true>
		   		 </TD>
	</tr>
	</table>
    </div>
    </div>
    <!--<INPUT class=cssButton name="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return VersionStateQuery();">-->
     <a href="javascript:void(0);" name="querybutton" class="button" onClick="return VersionStateQuery();">�汾��Ϣ��ѯ</a><br><br>
	<div class="maxbox">
<Div  id= "divSearch" style= "display: ''">

	<table  class= common>
        <TR  class= common>
        	<TD class= title5>
					  У�����κ���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=RuleDealBatchNo id=RuleDealBatchNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	����Դ���κ���
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=DataSourceBatchNo id=DataSourceBatchNo readonly=true>
		   		</TD>
        </TR>
          <TR  class= common>
        	<TD class= title5>
					  У���¼���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=CallPointID id=CallPointID readonly=true>
				</TD>
				<TD class= title5>
		   	   	У����
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=RuleDealResult id=RuleDealResult readonly=true>
		   		</TD>
        </TR>
			<TR  class= common>
        	<TD class= title5>
					  У����
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=DealOperator id=DealOperator readonly=true>
				</TD>
				<TD class= title5>
		   	   	У��ƻ�
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=RulePlanID id=RulePlanID readonly=true>
		   		</TD>
        </TR>
                <TR  class= common>
        	<TD class= title5>
					  У������
				 </TD>
				 <TD class=input5>
				 	<!--<input class=readonly name=RuleDealDate readonly=true>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#RuleDealDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RuleDealDate id="RuleDealDate"><span class="icon"><a onClick="laydate({elem: '#RuleDealDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class= title5>
		   	   	��־�ļ�·��
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=LogFilePath id=LogFilePath readonly=true>
		   		</TD>
        </TR>
          <TR  class= common>
        	<TD class= title5>
					  ��־�ļ�����
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=LogFileName id=LogFileName readonly=true>
				</TD> 
        </TR>
      </table>
      <!--<INPUT VALUE="У����־��Ϣ��ѯ" class = cssButton TYPE=button onclick="queryResult();" >       
<INPUT VALUE="�������ݴ���" class = cssButton name=DealErrdataButton TYPE=button onclick="DealErrdata();" > --><br>
<a href="javascript:void(0);" class="button" onClick="queryResult();">У����־��Ϣ��ѯ</a>
<a href="javascript:void(0);" class="button" name=DealErrdataButton onClick="DealErrdata();">�������ݴ���</a> 
</div> 
</div>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden id=VersionState name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
