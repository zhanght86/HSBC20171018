<%
//程序名称：CallbackReceiptReport.jsp
//程序功能： 未回收回执查询打印
//创建日期：2010-05-11
//创建人  ：hanbin
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
  
  <SCRIPT src="./CallbackReceiptReport.js"></SCRIPT>   
  <%@include file="./CallbackReceiptReportInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>保单回执查询</title>
</head>      
 
<body  onload="initForm();" >
<form action="CallbackReceiptReportSave.jsp" method=post name=fm id=fm target="fraSubmit">
<div class="maxbox">
   <Table class= common>
     <TR class= common> 
          <TD class= "title5">管理机构 </TD>
          <TD class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ManageCom id=ManageCom  value="<%=Branch%>" ondblclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);"  onclick="return showCodeList('station',[this],null,null,comcodeSql,'1',null,250);"
            		onkeyup="return showCodeListKey('station',[this],null,null,comcodeSql,'1',null,250);">
          </TD>
          <td class="title5">业务员代码</td>
        	<td class= input5>
        	    <input class="wid" class="common" name="AgentCode" id="AgentCode">
        	</td>
     </TR>
     <tr class=common>
        <td class="title5">代理机构</td>
        <td class=input5>
            <input class="wid" class="common" name="AgentCom" id="AgentCom">
        </td>
        <td class="title5">销售渠道</td>
                  <TD  class= input5>
	        	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=SaleChnl id=SaleChnl verify="销售渠道|code:SaleChnl" ondblclick="return showCodeList('SaleChnl',[this]);" onclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
     </tr>
     <tr>
         <td class="title5">保单签发日期(起期)
        </td>
        <td class="input5">
        <Input class="coolDatePicker" onClick="laydate({elem: '#SignDateStart'});" verify="最大日期|DATE&NOTNULL" dateFormat="short" name=SignDateStart id="SignDateStart"><span class="icon"><a onClick="laydate({elem: '#SignDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
        <td class="title5">保单签发日期(止期)
        </td>
        <td class="input5">
        <Input class="coolDatePicker" onClick="laydate({elem: '#SignDateEnd'});" verify="最大日期|DATE&NOTNULL" dateFormat="short" name=SignDateEnd id="SignDateEnd"><span class="icon"><a onClick="laydate({elem: '#SignDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
     </tr>
	<TR class=common>
		<td class="title5">是否清洁件</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CleanPolFlag" id="CleanPolFlag"
			CodeData="0|^1|清洁件 ^0|非清洁件"
			ondblclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1],null,null,null,1)"
            onclick="showCodeListEx('CleanPolFlag',[this,CleanPolFlagName],[0,1],null,null,null,1)"
			onkeyup="return showCodeListKeyEx('CleanPolFlag',[this,CleanPolFlagName],[0,1],null,null,null,1)"><input
			name="CleanPolFlagName" id="CleanPolFlagName" class="codename" readonly>
		</td>
		<td class="title5">统计类型</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="StatType" id="StatType"
			CodeData="0|^15|回执15日核销率 ^19|回执19日核销率"
			ondblclick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1)"
            onclick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1)"
			onkeyup="return showCodeListKeyEx('StatType',[this,StatTypeName],[0,1],null,null,null,1)"><input
			name="StatTypeName" id="StatTypeName" class="codename" readonly>
		</td>
	</TR>
	<TR>
          <TD  class= title5>统计粒度</TD>
          <td class="input5">
              <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComGrade id=ComGrade CodeData="0|^2|总公司^4|二级机构^6|三级机构^8|四级机构" ondblclick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])" onclick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])" onkeyup="showCodeListKeyEx('ComGrade', [this,ComGradeName],[0,1])" ><input class="codename" name=ComGradeName id=ComGradeName readonly=true>
          </td>	
	 
       </TR>
</Table></div>
		<p>
    <!--数据区-->
    <INPUT VALUE="查   询" class=cssButton TYPE=hidden onclick="easyQuery()"> 	
		<!--<INPUT VALUE="打印报表" class=cssButton TYPE=button onclick="easyPrint();">-->
        <a href="javascript:void(0);" class="button" onClick="easyPrint();">打印报表</a>
    <input type=hidden id="fmtransact" name="fmtransact">
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var comcodeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
