<%
//�������ƣ�SentOutListQuery.jsp
//�����ܣ�Ϊ���ջ�ִ��ѯ��ӡ
//�������ڣ�2005-7-7
//������  ��weikai
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
%>

<script language="javascript">
  var comCode ="<%=Branch%>";
</script>

<html><head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  
  <SCRIPT src="./SendOutListQuery.js"></SCRIPT>   
  <%@include file="./SendOutListQueryInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>������ִ��ѯ</title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id=fm>
    <div class="maxbox1" >
   <Table class= common>
     <TR class= common> 
          <TD class= "title5">������� </TD>
          <TD class= input5>
            <Input class="common wid" name=ManageCom  id=ManageCom
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);"  
            onDblClick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
            		onkeyup="return showCodeListKey('station',[this],null,null,comcodeSql,'1',null,250);">
          </TD>
          <td class="title5">ҵ��Ա����</td>
        	<td class= input5>
        	    <input class="common wid"  name="AgentCode">
        	</td>
     </TR>
     <tr class=common>
        <td class="title5">�������</td>
        <td class=input5>
            <input class="common wid" name="AgentCom">
        </td>
        <td class="title5">��������</td>
                  <TD  class= input5>
	        	<Input class="common wid" name=SaleChnl verify="��������|code:SaleChnl" 
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('SaleChnl',[this]);"   
                onDblClick="return showCodeList('SaleChnl',[this]);" 
                onKeyUp="return showCodeListKey('SaleChnl',[this]);">
          </TD>
     </tr>
     <tr>
         <td class="title5">����ǩ������(����)
        </td>
        <td class="input5">
        <input class=" multiDatePicker laydate-icon" dateFormat="short" name="signdate" id="signdate" onClick="laydate

({elem:'#signdate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#signdate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
        <td class="title5">����ǩ������(ֹ��)
        </td>
        <td class="input5">
         <input class=" multiDatePicker laydate-icon" dateFormat="short" name="signdateend" id="signdateend"onClick="laydate({elem:'#signdateend'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#signdateend'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
     </tr>
	<TR class=common>
		<td class="title5">�Ƿ�����</td>
		<td class="input5"><input class="codeno" name="CleanPolFlag"
			CodeData="0|^1|���� ^0|������"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"
			ondblclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"
			onkeyup="return showCodeListKeyEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"><input
			name="CleanPolFlagName" class="codename" readonly>
		</td>
		<td class="title5">ͳ������</td>
		<td class="input5"><input class="codeno" name="StatType"
			CodeData="0|^1|����δ������ ^0|Ԥ����"
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('StatType',[this,StatTypeName],[0,1])"
			ondblclick="showCodeListEx('StatType',[this,StatTypeName],[0,1])"
			onkeyup="return showCodeListKeyEx('StatType',[this,StatTypeName],[0,1])"><input
			name="StatTypeName" class="codename" readonly>
		</td>
	</TR>
</Table>
</div>
		<p>
    <!--������-->
     <a href="javascript:void(0);" class="button"onClick="easyQuery()">��   ѯ</a>
      <a href="javascript:void(0);" class="button"onClick="easyPrint();">��   ӡ</a>
      
   <!-- <INPUT VALUE="��   ѯ" class=cssButton TYPE=button onClick="easyQuery()"> 	
		<INPUT VALUE="��   ӡ" class=cssButton TYPE=button onClick="easyPrint();">-->
    <input type=hidden id="fmtransact" name="fmtransact">
<br><br>
  	<Div  id= "divCodeGrid" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var comcodeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
