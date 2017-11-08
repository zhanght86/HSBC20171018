<%
//程序名称：SentOutListQuery.jsp
//程序功能：为回收回执查询打印
//创建日期：2005-7-7
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
  <title>保单回执查询</title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id=fm>
    <div class="maxbox1" >
   <Table class= common>
     <TR class= common> 
          <TD class= "title5">管理机构 </TD>
          <TD class= input5>
            <Input class="common wid" name=ManageCom  id=ManageCom
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);"  
            onDblClick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);" 
            		onkeyup="return showCodeListKey('station',[this],null,null,comcodeSql,'1',null,250);">
          </TD>
          <td class="title5">业务员代码</td>
        	<td class= input5>
        	    <input class="common wid"  name="AgentCode">
        	</td>
     </TR>
     <tr class=common>
        <td class="title5">代理机构</td>
        <td class=input5>
            <input class="common wid" name="AgentCom">
        </td>
        <td class="title5">销售渠道</td>
                  <TD  class= input5>
	        	<Input class="common wid" name=SaleChnl verify="销售渠道|code:SaleChnl" 
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('SaleChnl',[this]);"   
                onDblClick="return showCodeList('SaleChnl',[this]);" 
                onKeyUp="return showCodeListKey('SaleChnl',[this]);">
          </TD>
     </tr>
     <tr>
         <td class="title5">保单签发日期(起期)
        </td>
        <td class="input5">
        <input class=" multiDatePicker laydate-icon" dateFormat="short" name="signdate" id="signdate" onClick="laydate

({elem:'#signdate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#signdate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
        <td class="title5">保单签发日期(止期)
        </td>
        <td class="input5">
         <input class=" multiDatePicker laydate-icon" dateFormat="short" name="signdateend" id="signdateend"onClick="laydate({elem:'#signdateend'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#signdateend'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
     </tr>
	<TR class=common>
		<td class="title5">是否清洁件</td>
		<td class="input5"><input class="codeno" name="CleanPolFlag"
			CodeData="0|^1|清洁件 ^0|非清洁件"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"
			ondblclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"
			onkeyup="return showCodeListKeyEx('CleanPolFlag',[this,CleanPolFlagName],[0,1])"><input
			name="CleanPolFlagName" class="codename" readonly>
		</td>
		<td class="title5">统计类型</td>
		<td class="input5"><input class="codeno" name="StatType"
			CodeData="0|^1|超期未核销件 ^0|预警件"
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
    <!--数据区-->
     <a href="javascript:void(0);" class="button"onClick="easyQuery()">查   询</a>
      <a href="javascript:void(0);" class="button"onClick="easyPrint();">打   印</a>
      
   <!-- <INPUT VALUE="查   询" class=cssButton TYPE=button onClick="easyQuery()"> 	
		<INPUT VALUE="打   印" class=cssButton TYPE=button onClick="easyPrint();">-->
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
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var comcodeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
