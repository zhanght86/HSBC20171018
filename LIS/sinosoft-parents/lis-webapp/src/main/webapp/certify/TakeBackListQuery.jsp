<%
//程序名称：TakeBackListQuery.jsp
//程序功能：查询保单回执清单和打印
//创建日期：2005-3-31
//创建人  ：weikai
//更新记录：  更新人    更新日期     更新原因/内容
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
  <title>保单回执查询</title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id=fm>
  <table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>  		
    		 <td class= titleImg>
        		输入查询条件
       		 </td>   		      
    	</tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
   <Table class= common>
     <TR class= common> 
          <TD class= "title5">管理机构 </TD>
          <TD class= input5>
            <Input class="code" name=ManageCom  id=ManageCom
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
             onDblClick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
            		onkeyup="return showCodeListKey('station',[this],null,null,comcodeSql,'1',null,250);">
          </TD>
          <td class="title5">销售渠道</td>
          <TD  class= input5>
	        	<Input class="code" name=SaleChnl verify="销售渠道|code:SaleChnl"  id=SaleChnl
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('SaleChnl',[this]);" 
                onDblClick="return showCodeList('SaleChnl',[this]);" 
                onKeyUp="return showCodeListKey('SaleChnl',[this]);">
          </TD>
     </TR>
     <TR class=common>
          <td class="title5">从(入机日期)</td>
          <td class="input5">
          <input class="coolDatePicker" dateFormat="short" id="MaxDateb"  name="MaxDateb" verify="最大日期|DATE&NOTNULL"
           onClick="laydate({elem:'#MaxDateb'});" > <span class="icon"><a onClick="laydate({elem: '#MaxDateb'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          <td class="title5">到(入机日期)</td>
          <td class="input5">
           <input class="coolDatePicker" dateFormat="short" id="MaxDatee"  name="MaxDatee"  verify="最大日期|DATE&NOTNULL" onClick="laydate({elem:'#MaxDatee'});"> <span class="icon"><a onClick="laydate({elem: '#MaxDatee'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
     </TR>
     <TR class= common>
          <td class="title5">操作员号</td>
        	<td class= input5>
        		<input class="common wid" name="Operator" >
        	</td>
     </TR>
   	</Table>
    </div></div>
		<p>
    <!--数据区-->
   <!-- <INPUT VALUE="查   询" class=cssButton TYPE=button onClick="easyQuery()"> 	
		<INPUT VALUE="打   印" class=cssButton TYPE=button onClick="easyPrint();">-->
        <a href="javascript:void(0);" class="button"onClick="easyQuery()">查   询</a>
        <a href="javascript:void(0);" class="button"onClick="easyPrint();">打   印</a>
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
      	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var comcodeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
