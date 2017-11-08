<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");	  
	String tClmNo = request.getParameter("ClaimNo"); //赔案号
	String tBatNo= request.getParameter("BatNo");	//批次号
	String tInqDept=request.getParameter("InqDept");	//调查机构
	String tType = request.getParameter("Type");	//汇总标志 0--赔案层，2--机构层 
%>
    <title>调查信息查询</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <script src="./LLInqConclusionQuery.js"></script> 
    <%@include file="LLInqConclusionQueryInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>

    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusionGrid);"></TD>
            <TD class= titleImg> 调查结论信息 </TD>
        </TR>
    </Table>    
    <Div  id= "divLLInqConclusionGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
             </TR>
         </Table>
        <table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>     
    </Div>
    <Div  id= "divLLInqConclusionForm" style= "display: ''">    
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusionForm);"></TD>
                <TD class= titleImg> 调查结论详细信息 </TD>
            </TR>
        </Table>
         <Table class=common>
             <tr class=common>
                 <td class=title> 赔案号 </td>
                 <td class= input><Input class= "readonly" readonly name="ClmNo2"></td>
                 <td class=title> 结论序号 </td>
                 <td class= input><Input class= "readonly" readonly name="ConNo"></td>
                 <td class=title> 调查机构 </td>
                 <td class= input><Input class= "readonly" readonly name="InqDept"></td>                                   
             </tr>        
         </Table>
         <Table class= common>        
             <tr class= common>
                 <td class= title> 调查结论 </td>
             </tr> 
             <tr class= common>       
                 <td class= input> <textarea name="InqConclusion" cols="100%" rows="2" witdh=25% class="common" readonly ></textarea></td>
             </tr>
             <tr class= common>
                 <td class= title> 备注 </td>
             </tr> 
             <tr class= common>       
                 <td class= input> <textarea name="Remark" cols="100%" rows="2" witdh=25% class="common" readonly ></textarea></td>
             </tr>       
         </Table>  
    </div>

     <Div  id= "divLLInqApplyGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyGrid);"></TD>
                <TD class= titleImg> 调查申请信息 </TD>
            </TR>
         </Table>
          <Table class= common>
               <TR class= common>
                    <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
               </TR>
          </Table>
	      <table> 
	        <tr>  
	            <td><INPUT class=cssButton VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
	            <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
	            <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
	            <td><INPUT class=cssButton VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
	        </tr> 
	      </table>     
     </Div>

     <Div  id= "divLLInqApplyForm" style= "display: ''">    
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyForm);"></TD>
                   <TD class= titleImg> 调查申请详细信息 </TD>
              </TR>
         </Table>
          <Table class=common>
               <tr class=common>
                    <td class=title> 赔案号 </td>
                    <td class= input><Input type="input" class="readonly" readonly name="ClmNo3"></td>
                    <td class=title> 调查序号 </td>
                    <td class= input><Input type="input" class="readonly" readonly name="InqNo"></td>
                    <td class=title> 出险人客户号 </td>
                    <td class= input><Input type="input" class="readonly" readonly name="CustomerNo"></td>                                   
               </tr>     
          </Table>    
          <Table class=common> 
              <tr class= common>
                   <td class= title> 调查项目 </td>
              </tr> 
              <tr class= common>       
                  <td class= input> <textarea name="InqItem" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
              </tr>
              <tr class= common>
                  <td class= title> 调查描述 </td>
              </tr> 
              <tr class= common>       
                  <td class= input> <textarea name="InqDesc" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
              </tr>       
          </Table>  

     </div>
     <hr>

        <Div  id= "divLLInqCourseGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseGrid);"></TD>
                <TD class= titleImg> 调查过程信息 </TD>
            </TR>
        </Table>
             <Table  class= common>
                  <TR  class= common>
                       <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
                   </TR>
             </Table>
        </Table>
        <!--table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table--> 
        </Div>

        <Div  id= "divLLInqCourseForm" style= "display: ''">    
            <Table>
                 <TR>
                     <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseForm);"></TD>
                     <TD class= titleImg> 调查过程详细信息 </TD>
                 </TR>
            </Table>
            <Table class=common>
                <tr class=common>
                    <td class=title> 赔案号 </td>
                    <td class= input><Input class= "readonly" readonly name="ClmNo4"></td>
                    <td class=title> 调查序号 </td>
                    <td class= input><Input class= "readonly" readonly name="InqNo2"></td>
                    <td class=title> 过程序号 </td>
                    <td class= input><Input class= "readonly" readonly name="CouNo"></td>                                   
                </tr>   
            </Table>    
            <Table class=common>              
                <tr class= common>
                    <td class= title> 调查地点 </td>
                </tr> 
                <tr class= common>       
                    <td class= input> <textarea name="InqSite" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
                </tr>
                <tr class= common>
                    <td class= title> 调查过程 </td>
                </tr> 
                <tr class= common>       
                    <td class= input> <textarea name="InqCourse" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
                </tr>       
            </Table>  
    </div>    
    <Input class=cssButton value=" 返 回 " type=button onclick=top.close() align= center>
    <!--保存数据的隐藏表单-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->    
    <Input type=hidden id="tClmNo" name="tClmNo"><!--赔案号-->  
    <Input type=hidden id="tBatNo" name="tBatNo"><!--批次号-->  
    <Input type=hidden id="tInqDept" name="tInqDept"><!--调查机构-->  
    <Input type=hidden id="tType" name="tType"><!--标志-->
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
