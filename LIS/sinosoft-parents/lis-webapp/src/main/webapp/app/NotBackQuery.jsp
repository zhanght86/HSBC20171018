<%
//�������ƣ�NotBackQuery.jsp
//�����ܣ����¼��δ�ش�������ѯ
//�������ڣ�2008-09-03 10:10
//������  ��Huly
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>
<script>
	var comCode= <%=tComCode%>;
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
  <SCRIPT src="./NotBackQuery.js"></SCRIPT>   
  <%@include file="./NotBackQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>���¼��δ�ش�������ѯ</title>
</head>      
 
<body  onload="initForm();" >
	<table class= common border=0 width=100%>
  <tr>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,fghjk);"></td>
	<td class= titleImg>�������ѯ������</td>
	</tr>
	</table>
  <form method=post name=fm id=fm>
  <Div  id= "fghjk" style= "display: ''" class="maxbox1">
   <table class= common>
     <TR class= common> 
          <TD  class= title5>
            �����˾
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code  name=BPOID id=BPOID  ondblclick="return showCodeList('querybpoid',[this]);" onclick="return showCodeList('querybpoid',[this]);" onkeyup="return showCodeListKey('querybpoid',[this]);">
           </TD>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
             <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=ManageCom id=ManageCom value=<%=tComCode%> ondblclick="return showCodeList('comcode',[this]);" onclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);" readonly>
          </TD>
     </TR>
     <TR class= common> 
          <TD  class= title5>
            ɨ�迪ʼʱ��
          </TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD>
          <TD  class= title5>
            ɨ�����ʱ��
          </TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
     </TR>
   	</table>  
    </Div>
    <!--������-->
<!--    <INPUT VALUE="��ѯ" class=cssButton TYPE=button onclick="easyQuery();"> 	
    <INPUT VALUE="�������غʹ�ӡ����" class=cssButton TYPE=button onclick="easyPrint();"> -->
    <a href="javascript:void(0);" class="button" onClick="easyQuery();">��    ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="easyPrint();">�������غʹ�ӡ����</a>	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ���¼��δ�ش�����
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
            <center>
      	<INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="βҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> 	</center>				
  	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
