<%
//�������ƣ�BankTraceQuery.jsp
//�����ܣ�����ת�˹켣��ѯ
//�������ڣ�2010-04-12
//������  ��
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
	var comCodeLen = <%=tComCode.toString().length()%>;
	var Str = "1 and comcode like #<%=tComCode%>%#";
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="./BankTraceQuery.js"></SCRIPT>   
  <%@include file="./BankTraceQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>����ת�˹켣��ѯ</title>
</head>      
 
<body  onload="initForm();" >
	<table class= common border=0 width=100%>
  <tr>
  <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
	<td class= titleImg align= center>�������ѯ������</td>
	</tr>
	</table>
  <form method=post name=fm id=fm>
  <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
   <table class= common>
     <TR class= common> 
          <TD  class= title5>
            �վݺ�
          </TD>
          <TD  class= input5>          
             <Input class="common wid" name=Tempfeeno></TD>
          <TD  class= title5>
            ʵ����
          </TD>
          <TD  class= input5>
          <Input class="common wid" name=Actugetno></TD>          
     </TR>
          <TR class= common> 
          
          <TD  class= title5>
            ҵ���
          </TD>
          <TD  class= input5>
             <Input class="common wid" name=Otherno>                        
          </TD>
     </TR>
   	</table> 
    </div></div> 
    <br>
    <!--������-->
     <a href="javascript:void(0);" class="button"onclick="easyQuery();">��ѯ</a>
      <a href="javascript:void(0);" class="button"onclick="easyPrint();">�������غʹ�ӡ����</a>
    <!--<INPUT VALUE="��ѯ" class= Button TYPE=button onClick="easyQuery();"> 	
    <INPUT VALUE="�������غʹ�ӡ����" class= Button TYPE=button onClick="easyPrint();"> -->	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ����ת�˹켣��ѯ
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
            <div align="center">
      	<INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="βҳ" class= "cssButton93"TYPE=button onClick="getLastPage();"> 
         </div>					
  	</div>
    <br><br><br><br>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
