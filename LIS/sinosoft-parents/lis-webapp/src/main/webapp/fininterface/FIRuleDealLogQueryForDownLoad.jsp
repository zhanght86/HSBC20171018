<html>
<%
//�������� :FIRuleDealLogQueryForDownLoad.jsp
//������ :ϵͳ������־��ѯ
//������ :���
//�������� :2008-09-24
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
<SCRIPT src = "FIRuleDealLogQueryForDownLoad.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealLogQueryForDownLoadinit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  	<table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,IRul);"></td>
    		 <td class= titleImg>
        		��ѯ����
       		 </td>   		 
    	</tr>
    </table>
     <Div id= "IRul" style= "display: ''"><div class="maxbox1">
   
 <table class= common border=0 width=100%>
	<table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ��ʼʱ��
          </TD>
          <TD  class= input5>
            <!--<Input class= "coolDatePicker" verify="��ʼʱ��|notnull&date" dateFormat="short" name=StartDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <!--<Input class= "coolDatePicker" verify="����ʱ��|notnull&date"  dateFormat="short" name=EndDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR> 
      </table>
      </div> 
      </div>
      <!--<input class="cssButton" type=button value="��  ѯ" onclick="OperationLogQuery()">-->
      <a href="javascript:void(0);" class="button" onClick="OperationLogQuery();">��    ѯ</a>
      <table  class= common>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDealLogQueryForDownLoadGrid);">
    		</td>
    		<td class= titleImg>
    			 У����־��Ϣ��ѯ
    		</td>
    	</tr>
    </table>
<Div  id= "divFIRuleDealLogQueryForDownLoadGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIRuleDealLogQueryForDownLoadGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<center><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> 	</center>	
</Div>

    <!--<input class="cssButton" type=button value="��������־���ص�ַ" onclick="DownloadAddress()">--><br>
    <a href="javascript:void(0);" class="button" onClick="DownloadAddress();">��������־���ص�ַ</a><br><br>


<Div  id= "Querydiv" style= "display: none" align=left class="maxbox1">
	<table class=common>
		 <TR  class= common>
          <TD class= title5>
					  У����ˮ����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=CheckSerialNo readonly = true>
				</TD>
          <TD class= title5>
					  У��ƻ�
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RulePlanID readonly = true>
				</TD>
        </TR>
      <TR  class= common>
      <TD  class= title>
        �����ļ�����
      </TD>
      <TD  class= input>
        <A id=fileUrl name = filepath  href=""></A>
      </TD>           
    </TR>   
 </table> 	  
</div>
</table>	
   <input type=hidden id="OperateType" name="OperateType">
   <input type=hidden name="CheckSerialNo1" VALUE=''>
   			
 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
