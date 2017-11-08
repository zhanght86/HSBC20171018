<%
//程序名称：LLInqFeeQueryInit.jsp
//程序功能：调查费用信息查询页面
//创建日期：2005-06-23
//创建人  ：yuejw
//更新记录：
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
	<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("ClmNo");	//赔案号
	  String tInqNo = request.getParameter("InqNo");	//调查序号  
%>
<title>该赔案调查过程信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLInqFeeQuery.js"></SCRIPT>
    <%@include file="LLInqFeeQueryInit.jsp"%>
</head>
</head>
<body onload="initForm();">  
<form name=fm id=fm target=fraSubmit method=post>
     
    <Table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqFeeGrid);"></TD>
            <TD class= titleImg> 该赔案的所有调查费用列表 </TD>
        </TR>
    </Table>       
    <Div id= "divLLInqFeeGrid" style= "display: ''">
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqFeeGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table>  -->  
    </Div>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqFeeInfo);"></TD>
            <TD class= titleImg> 查看调查费用详细信息 </TD>
        </TR>
    </Table>
    <Div id= "divLLInqFeeInfo" style= "display: ''" class="maxbox">    
        <TABLE class=common>
           <tr class=common>
               <td class=title> 赔案号 </td>
               <td class= input><Input type="input" class="readonly wid" readonly name="ClmNo1" id="ClmNo1"></td>
               <td class=title> 调查序号 </td>
               <td class= input><Input type="input" class="readonly wid" readonly name="InqNo1" id="InqNo1"></td>
               <td class=title> 调查机构 </td>
               <!--<td class= input><Input type="input" class="readonly" readonly name="InqDept"></td>  -->                                 
               <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InqDept" id="InqDept" ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);" onclick="return showCodeList('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" id="InqDeptName" readonly=true></TD> 	             
           </tr>    
           <TR  class= common>
        		<td class=title> 费用类型 </td>
		        <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="FeeType" id="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);" onclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1]);"><input class=codename name="FeeTypeName" id="FeeTypeName" ></TD>
                <TD  class= title>费用金额</TD>
                <!--<TD  class= input><input class= common readonly name="FeeSum"></TD>-->
                <td class= input><Input type="input" class="readonly wid" readonly name="FeeSum" id="FeeSum"></td>
                <TD  class= title>发生时间</TD>
                <!--<TD  class= input><input class= 'coolDatePicker' dateFormat="Short" readonly name="FeeDate"></TD>-->
                <TD  class= input><input type="input" class="readonly wid" readonly  name="FeeDate" id="FeeDate"></TD>
           </TR>
           <TR  class= common>
                <TD  class= title>领款人</TD>
                <TD  class= input><input type="input" class="readonly wid" readonly  name="Payee" id="Payee"></TD>
                <TD  class= title>领款方式</TD>
                <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="PayeeType" id="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" id="PayeeTypeName" readonly=true></TD>
           </TR>
        </TABLE>        
        <Table class= common>                	            
           <tr class= common>
               <td class= title> 备注 </td>
           </tr> 
           <tr class= common>       
               <td colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
           </tr>       
        </TABLE>  
    </div>
    <Input class=cssButton value=" 返 回 " type=button onclick=top.close() align= center>     

    <!--//保存数据的隐藏表单-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="InqNo" name="InqNo"><!--调查序号-->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
