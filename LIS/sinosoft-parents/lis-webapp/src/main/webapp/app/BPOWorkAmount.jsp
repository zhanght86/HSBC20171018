<%
//�������ƣ�NoBackPolQuery.jsp
//�����ܣ����¼��δ�ش�������ѯ
//�������ڣ�2007-09-28 15:10
//������  ��Fuqx
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
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./BPOWorkAmount.js"></SCRIPT>   
  <%@include file="./BPOWorkAmountInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>���¼��δ�ش�������ѯ</title>
</head>      
 
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit" action="./BPOWorkAmountSave.jsp">
	<table class= common border=0 width=100%>
  <tr>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,laver);"></td>
	<td class= titleImg>�������ѯ������</td>
	</tr>
	</table>
  <form method=post name=fm>
  <Div  id= "laver" style= "display: ''" class="maxbox1">
   <table class= common>
     <TR class= common> 
          <TD  class= title5>
            �����˾
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code  name=BPOID id=BPOID  ondblclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');" onclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');" onkeyup="return showCodeListKey('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');">
           </TD>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
             <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=ManageCom id=ManageCom ondblclick="return showCodeList('comcode',[this]);" onclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
          </TD>
          </TR>
          <TR class= common>
         <td class="title5">���ִ���</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="RiskCode" id="RiskCode" verify="���ִ���|Code:IYRiskCode" ondblclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onkeyup="showCodeListKey('IYRiskCode',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" id="RiskCodeName" readonly></td>
                    <TD  class= title5>
            ��ʼʱ��
          </TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
     </TR>
     <TR class= common> 
          
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
     </TR>
   	</table>  
    </Div>
    <!--������-->
    <!--INPUT VALUE="��    ѯ" class= cssButton TYPE=button onclick="easyQuery();"--> 	
    <!--<INPUT VALUE="��ӡ����" class= cssButton TYPE=button onclick="easyPrint();">-->
     <a href="javascript:void(0);" class="button" onClick="easyPrint();">��ӡ����</a> <br><br>	

  	<Div  id= "divCodeGrid" style= "display: none" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
            <center>
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> 	</center>				
  	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
