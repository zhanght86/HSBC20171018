<%
//�������ƣ�TakeBackListQuery.jsp
//�����ܣ���ѯ������ִ�嵥�ʹ�ӡ
//�������ڣ�2005-3-31
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

<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  
  <SCRIPT src="./TakeBackListQuery.js"></SCRIPT>   
  <%@include file="./TakeBackListQueryInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>������ִ��ѯ</title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id=fm>
  <table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>  		
    		 <td class= titleImg>
        		�����ѯ����
       		 </td>   		      
    	</tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
   <Table class= common>
     <TR class= common> 
          <TD class= "title5">������� </TD>
          <TD class= input5>
            <Input class="code" name=ManageCom  id=ManageCom
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
             onDblClick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
            		onkeyup="return showCodeListKey('station',[this],null,null,comcodeSql,'1',null,250);">
          </TD>
          <td class="title5">��������</td>
          <TD  class= input5>
	        	<Input class="code" name=SaleChnl verify="��������|code:SaleChnl"  id=SaleChnl
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('SaleChnl',[this]);" 
                onDblClick="return showCodeList('SaleChnl',[this]);" 
                onKeyUp="return showCodeListKey('SaleChnl',[this]);">
          </TD>
     </TR>
     <TR class=common>
          <td class="title5">��(�������)</td>
          <td class="input5">
          <input class="coolDatePicker" dateFormat="short" id="MaxDateb"  name="MaxDateb" verify="�������|DATE&NOTNULL"
           onClick="laydate({elem:'#MaxDateb'});" > <span class="icon"><a onClick="laydate({elem: '#MaxDateb'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          <td class="title5">��(�������)</td>
          <td class="input5">
           <input class="coolDatePicker" dateFormat="short" id="MaxDatee"  name="MaxDatee"  verify="�������|DATE&NOTNULL" onClick="laydate({elem:'#MaxDatee'});"> <span class="icon"><a onClick="laydate({elem: '#MaxDatee'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
     </TR>
     <TR class= common>
          <td class="title5">����Ա��</td>
        	<td class= input5>
        		<input class="common wid" name="Operator" >
        	</td>
     </TR>
   	</Table>
    </div></div>
		<p>
    <!--������-->
   <!-- <INPUT VALUE="��   ѯ" class=cssButton TYPE=button onClick="easyQuery()"> 	
		<INPUT VALUE="��   ӡ" class=cssButton TYPE=button onClick="easyPrint();">-->
        <a href="javascript:void(0);" class="button"onClick="easyQuery()">��   ѯ</a>
        <a href="javascript:void(0);" class="button"onClick="easyPrint();">��   ӡ</a>
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
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var comcodeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
