<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ProposalNoRespQuery.jsp
//程序功能：查询个单未回复清单
//创建日期：2007-03-20 18:02
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%>
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
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="./CardPolQuery.js"></SCRIPT>   
  <%@include file="./CardPolQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>投保单财务未录入信息查询</title>
</head>      
 
<body  onload="initForm();initElementtype();" >
<form action="./CardPolQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<table class= common border=0 width=100%>
  <tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	  <td class= titleImg align= center>请输入查询条件：</td>
	</tr>
	</table>
    <div class="maxbox1">
   <Div  id= "divFCDay" style= "display: ''"> 
   <Table class= common>
     <TR class= common> 
          <TD class=title5>单证类型</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=CardType id="CardType" verify="单证类型|" CodeData="0|^141812|金如意|^311603|阳光旅程|^111603|学生计划" onMouseDown="showCodeListEx('CardType',[this,CardTypeName],[0,1]);" ondblclick="showCodeListEx('CardType',[this,CardTypeName],[0,1]);" onkeyup="showCodeListKeyEx('CardType',[this,CardTypeName],[0,1]);"><input class=codename name=CardTypeName id="CardTypeName" readonly=true elementtype=nacessary>
					<!--input class=codeno name=CardType ondblclick="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,1,'400');">
          <input class=codename name=CardTypeName readonly=true elementtype=nacessary-->							
				</TD>
          <TD  class= title5>管理机构</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="管理机构|LEN>4|code:station" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true  elementtype=nacessary>
          </TD>          
     </TR>
     <TR  class= common>
       		<TD  class= title5 >
       		  开始日期
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
       		<TD  class= title5 >
       		  结束日期
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
     </TR>
   	</Table>  
   </Div>
   </div>
    <br>
    <!--数据区-->
    <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="打印数据" class= cssButton TYPE=button onclick="easyPrint()"> 	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 卡单录入清单
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</Div>
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1 and length(trim(code))>=6 and code like #"+<%=tComCode%>+"%#";
</script>
