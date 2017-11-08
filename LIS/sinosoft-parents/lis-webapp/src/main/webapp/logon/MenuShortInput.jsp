<!--*******************************************************
//程序名称：MenuShortInput.jsp
//程序功能：快捷菜单编辑页面
//创建日期：2002-09-23 08:08:52
//创建人  ：胡  博
//更新记录：  更新人    更新日期     更新原因/内容
*******************************************************-->

<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="./MenuShortInput.js"></SCRIPT>
 <%@include file="../common/easyQueryVer3/EasyQueryKernel.jsp"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.utility.*"%>
 <%
 	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String UserCode = tGI.Operator;
 	String sql="select ShowMenuName(nodecode),nodecode,runscript from ldmenu where childflag='0' and nodecode in(select nodecode from ldmenugrptomenu where menugrpcode in(select menugrpcode from ldmenugrp where menugrpcode in(select menugrpcode from ldusertomenugrp where UserCode='"+UserCode+"'))) order by nodecode";
 	
 	String strQueryResult = easyQueryKernel(sql, "1",null,null);     //查找快捷菜单表
 	
 %>
 
<script>   
//取出经过权限验证返回的菜单项字符串，拆分
var strMenuLeaf = "" + "<%=strQueryResult%>";
var arrNode = decodeEasyQueryResult(strMenuLeaf);
var NodeNum = 0;
var arrNodeName      = new Array();
var arrNodeCode      = new Array();
var arrNodeRunScript = new Array();
var i, j;

NodeNum = arrNode.length;

try {
  for (i=0; i<arrNode.length; i++) {
		arrNodeName[i] = arrNode[i][0];            //节点名称
		arrNodeCode[i] = arrNode[i][1];            //节点代码
		arrNodeRunScript[i] = arrNode[i][2];       //节点URL
  }
} catch(ex) {
  alert("get MenuNode Error!" + "\nError Happened by:" + ex);
}
</script>

<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
</head>

<body>
  <form action="./MenuShortSave.jsp" method=post name=fm id="fm">

  <div id="inputButton" >
  <table class="common" align=center>
    <tr>  
      <td class=button width="10%" align=right>
        <img id="saveImg" class=button alt="保存" src='../common/images/butSave.gif' 
  				onmouseover="return changeImage(this,'../common/images/butSaveOver.gif');"
  				onmouseout="return changeImage(this,'../common/images/butSave.gif');"
  				onclick="return submitForm();"></img>
      </td>
    </tr>
  </table>
  </div>
       
  <span id="menuShortSelect" >
  </span> 
  
  <input type=hidden name=MenuNum id="MenuNum" > 
      
<script> 
var innerHTML = "";   
var optionHTML = "";

//将菜单项内容形成SELECT对象
for (j=0; j<NodeNum; j++) {
  if (arrNodeCode[j] == null) break;
  optionHTML = optionHTML + "<OPTION class=common value=" + arrNodeCode[j] + "|" + arrNodeName[j] + "|" + arrNodeRunScript[j] + ">" + arrNodeName[j] + "</OPTION>";
}

innerHTML = innerHTML +  "<table style=\"width:80%\" class=common align=center>";   
for (i=0; i<MAXMENUSHORTNUM; i++) {
  innerHTML = innerHTML +  "<TR  class=common >";
  innerHTML = innerHTML +  "   <TD  class=title >";
  innerHTML = innerHTML +  "     <p align=center>第" + (i+1) + "个快捷菜单项名称：";
  innerHTML = innerHTML +  "   </TD>";
  innerHTML = innerHTML +  "   <TD  class=input>";
  innerHTML = innerHTML +  "   <SELECT style=\"width:90%  \" class=select name=selMenuName" + i + ">";
  innerHTML = innerHTML +  "     <OPTION value=0>－请选择新菜单项－</OPTION>";
  innerHTML = innerHTML + optionHTML;  //菜单项内容
  innerHTML = innerHTML +  "   </SELECT>";
  innerHTML = innerHTML +  "   </TD>";
  innerHTML = innerHTML +  " </TR>";
}
innerHTML = innerHTML +  " </table>";   

document.all("menuShortSelect").innerHTML = innerHTML; 
document.all("MenuNum").value = MAXMENUSHORTNUM;
</script>             

  </form>
</body>
</html>
