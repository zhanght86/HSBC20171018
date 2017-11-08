<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("claimNo");	//赔案号
%>
    <title>调查信息查询</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLInqQuery.js"></script> 
    <%@include file="LLInqQueryInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
	
	<!--新添加的界面，by niuzj 20050829--> 
    <Div  id= "DivLLInqPayclusionGrid" style= "display: ''">
    	<Table>
	        <TR>
	            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
	            <TD class= titleImg> 赔案结论信息 </TD>
	        </TR>
    	</Table><Div  id= "divPayPlan1" style= "display: ''">   
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqPayclusionGrid" ></span> </TD>
             </TR>
         </Table>
        <table align="center"> 
            <tr>  
                <td><INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>     
    </Div></Div>
       
        <Div  id= "DivLLInqPayclusionForm" style= "display: ''"> 
			<Table>
				<TR>
				    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ser);"></td>
				    <TD class= titleImg> 赔案结论详细信息 </TD>
				</TR>
			</Table><Div  id= "ser" style= "display: ''" class="maxbox1">
			 <Table class=common>
			     <tr class=common>
			         <td class=title> 立案号 </td>
			         <td class= input><Input class= "readonly wid" readonly name="ClmNo1" id="ClmNo1"></td>
			         <td class=title> 结论序号 </td>
			         <td class= input><Input class= "readonly wid" readonly name="ConNo1" id="ConNo1"></td>
			         <td class=title> 调查机构 </td>
			         <td class= input><Input class= "readonly wid" readonly name="InqDept1" id="InqDept1"></td>                                   
			     </tr>        
			 </Table>
	         <Table class= common>        
	             <tr class= common>
	                 <td class= title> 调查结论 </td>
	             </tr> 
	             <tr class= common>       
	                 <td colspan="6" style="padding-left:16px"> <textarea name="InqConclusion1" id="InqConclusion1" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
	             </tr>
	             <tr class= common>
	                 <td class= title> 备注 </td>
	             </tr> 
	             <tr class= common>       
	                 <td colspan="6" style="padding-left:16px"> <textarea name="Remark1" id="Remark1" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
	             </tr>       
	         </Table>  
         
    </Div></Div>

	<!--原来的界面-->

     
    <Div  id= "DivLLInqConclusionGrid" style= "display: ''">
    	<Table>
	        <TR>
	            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,mnk);"></td>
	            <TD class= titleImg> 调查机构结论信息 </TD>
	        </TR>
    	</Table>   <Div  id= "mnk" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
             </TR>
         </Table>
        <table align="center"> 
            <tr>  
                <td><INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>     
    </Div>
    <Div  id= "DivLLInqConclusionForm" style= "display: ''">    
        <Table>
            <TR>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
                <TD class= titleImg> 调查机构结论详细信息 </TD>
            </TR>
        </Table><Div  id= "peer" style= "display: ''" class="maxbox1">
         <Table class=common>
             <tr class=common>
                 <td class=title> 立案号 </td>
                 <td class= input><Input class= "readonly wid" readonly name="ClmNo2" id="ClmNo2"></td>
                 <td class=title> 结论序号 </td>
                 <td class= input><Input class= "readonly wid" readonly name="ConNo" id="ConNo"></td>
                 <td class=title> 调查机构 </td>
                 <td class= input><Input class= "readonly wid" readonly name="InqDept" id="InqDept"></td>                                   
             </tr>        
         </Table>
         <Table class= common>        
             <tr class= common>
                 <td class= title> 调查结论 </td>
             </tr> 
             <tr class= common>       
                 <td colspan="6" style="padding-left:16px"> <textarea name="InqConclusion" id="InqConclusion" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
             </tr>
             <tr class= common>
                 <td class= title> 备注 </td>
             </tr> 
             <tr class= common>       
                 <td colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
             </tr>       
         </Table>  
         
    </Div>

     <Div  id= "DivLLInqApplyGrid" style= "display: ''">
        <Table>
            <TR>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvb);"></td>
                <TD class= titleImg> 调查申请信息 </TD>
            </TR>
         </Table><Div  id= "cvb" style= "display: ''">
          <Table class= common>
               <TR class= common>
                    <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
               </TR>
          </Table>
	      <table align="center"> 
	        <tr>  
	            <td><INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
	            <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
	            <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
	            <td><INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
	        </tr> 
	      </table>     
     </Div></Div>

     <Div  id= "DivLLInqApplyForm" style= "display: ''">    
         <Table>
              <TR>
                   <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,hjk);"></td>
                   <TD class= titleImg> 调查申请详细信息 </TD>
              </TR>
         </Table><Div  id= "hjk" style= "display: ''" class="maxbox1">
          <Table class=common>
               <tr class=common>
                    <td class=title> 立案号 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="ClmNo3" id="ClmNo3"></td>
                    <td class=title> 调查序号 </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="InqNo" id="InqNo"></td>
                    <td class=title> 出险人编码</td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="customerNo" id="customerNo"></td>                                   
               </tr>     
          </Table>    
          <Table class=common> 
              <tr class= common>
                   <td class= title> 调查项目 </td>
              </tr> 
              <tr class= common>       
                  <td colspan="6" style="padding-left:16px"> <textarea name="InqItem" id="InqItem" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
              </tr>
              <tr class= common>
                  <td class= title> 调查描述 </td>
              </tr> 
              <tr class= common>       
                  <td colspan="6" style="padding-left:16px"> <textarea name="InqDesc" id="InqDesc" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
              </tr>       
          </Table>  </Div></Div>
          <Table style="display:none">
              <tr>  
          	      <td><input class=cssButton value="查看调查过程详细"  TYPE=button onclick="InqCourseQueryClick()";"></td>
          	      <td><input class=cssButton value="查看调查费用详细"  TYPE=button onclick="QueryInqFeeClick()";"></td>
          	      <td><input class=cssButton value="打印调查任务通知单"  TYPE=button onclick="PRTInteInqTask()";"></td>
          	      <td><input class=cssButton value=" 打印理赔调查报告 "  TYPE=button onclick="LLPRTInteInqReport()";"></td>
          	  </td>
          </Table>  <br>
     <a href="javascript:void(0);" class="button" onClick="InqCourseQueryClick();">查看调查过程详细</a>
     <a href="javascript:void(0);" class="button" onClick="QueryInqFeeClick();">查看调查费用详细</a>
     <a href="javascript:void(0);" class="button" onClick="PRTInteInqTask();">打印调查任务通知单</a>
     <a href="javascript:void(0);" class="button" onClick="LLPRTInteInqReport();">打印理赔调查报告</a><br><br>
     
        <!--Div  id= "DivLLInqCourseGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
                <TD class= titleImg> 调查过程信息 </TD>
            </TR>
        </Table>
             <Table  class= common>
                  <TR  class= common>
                       <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
                   </TR>
             </Table>
        </Table>
        </Div>

        <Div  id= "DivLLInqCourseForm" style= "display: ''">    
            <Table>
                 <TR>
                     <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
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
    </Div-->    
    
	<hr class="line">
    <!--<Input class=cssButton value=" 返 回 " type=button onclick=top.close() align= center>-->
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    <%
    //******************
    //保存数据的隐藏表单
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->  
    <Input type=hidden id="Sign" name="Sign"><!--标志--> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>          
</Form>
</body>
</html>
