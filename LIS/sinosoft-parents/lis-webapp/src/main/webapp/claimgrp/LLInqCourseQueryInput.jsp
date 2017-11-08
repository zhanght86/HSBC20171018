<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<%
//==========BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("ClmNo");	//赔案号
	  String tInqNo = request.getParameter("InqNo");	//调查序号  
//==========END
%>
<title>该赔案调查过程信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLInqCourseQuery.js"></SCRIPT>
    <%@include file="LLInqCourseQueryInit.jsp"%>
</head>
<body onload="initForm();">  
<form name=fm target=fraSubmit method=post>
    <br>  
    <Table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseGrid);"></TD>
            <TD class= titleImg> 该赔案的所有调查过程详细列表 </TD>
        </TR>
    </Table>       
    <Div id= "divLLInqCourseGrid" style= "display: ''">
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
             </TR>
         </Table>
         <table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table>    
    </Div>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseInfo);"></TD>
            <TD class= titleImg> 调查过程详细信息 </TD>
        </TR>
    </Table>
    <Div id= "divLLInqCourseInfo" style= "display: ''">    
        <TABLE class=common>
           <tr class=common>
               <td class=title> 赔案号 </td>
               <td class= input><Input type="input" class="readonly" readonly name="ClmNo1"></td>
               <td class=title> 调查序号 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqNo1"></td>
               <td class=title> 过程序号 </td>
               <td class= input><Input type="input" class="readonly" readonly name="CouNo"></td>                                   
           </tr>
           <tr class=common>
               <td class=title> 调查方式 </td>
               <!--<td class= input><Input type="input" class="readonly" readonly name="InqMode"></td>-->
               <td class= input><Input class=codeno disabled name="InqMode" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1]);"><input class=codename name="InqModeName" readonly=true></TD>				
               <td class=title> 调查地点 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqSite"></td>
               <td class=title> 调查日期 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqDate"></td>                                   
           </tr>
           <tr class=common>
               <td class=title> 第一调查人 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqPer1"></td>
               <td class=title> 第二调查人 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqPer2"></td>
               <td class=title> 被调查人 </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqByPer"></td>                                   
           </tr>         
        </TABLE>
        <Table class= common>        
           <tr class= common>
               <td class= title> 调查过程 </td>
           </tr> 
           <tr class= common>       
               <td class= input> <textarea name="InqCourse" cols="100" rows="3" witdh=25% class="common" readonly ></textarea></td>
           </tr>
           <tr class= common>
               <td class= title> 备注 </td>
           </tr> 
           <tr class= common>       
               <td class= input> <textarea name="Remark" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
           </tr>       
        </TABLE>  
    </div>
    <hr>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCertificateInfo);"></TD>
            <TD class= titleImg> 调查过程单证信息列表</TD>
        </TR>
    </Table>
    <Div id= "divLLInqCertificateInfo" style= "display: ''"> 
    	<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLInqCertificateGrid" ></span> </TD>
			</TR>
		</Table>
    </Div>	
    <hr>
    <Input class=cssButton value=" 返 回 " type=button onclick=top.close() align= center>
    <input class=cssButton type = "hidden" name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();">
    <input class=cssButton type = "hidden" name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();">
                     

    <!--//保存数据的隐藏表单-->
     
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="InqNo" name="InqNo"><!--调查序号-->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
