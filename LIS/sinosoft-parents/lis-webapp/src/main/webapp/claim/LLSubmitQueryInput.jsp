<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<%
//==========BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("claimNo");	//赔案号
//==========END
%>
<title>呈报信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLSubmitQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLSubmitQueryInit.jsp"%>
</head>
<body onload="initForm();">
  
<form name=fm id=fm target=fraSubmit method=post>
    <!--呈报信息查看-->        
    <Div id= "DivLLSubmitApplyGrid" style= "display: ''">
		<Table>
			<TR>
			    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			    <TD class= titleImg> 该赔案下的呈报信息列表 </TD>
			</TR>
		</Table>  <Div  id= "divPayPlan1" style= "display: ''"> 
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLSubmitApplyGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <TR>  
                 <TD><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></TD>
             </TR> 
         </table>  -->  
    </Div>
	    
    <!------呈报发起层信息应该有：发起机构名称、发起人、发起时间、呈报描述。---------->
    <Div id= "DivLLSubmitApplyInfo" style= "display: ''">
    	<Table>
	        <TR>
	            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,zxc);"></td>
	            <TD class= titleImg> 呈报发起详细信息 </TD>
	        </TR>
	    </Table><Div  id= "zxc" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> 发起人 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="InitSubPer" id="InitSubPer"></TD>
               <TD class=title> 发起机构 </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InitSubDept" id="InitSubDept" ondblclick="return showCodeList('stati',[this,InitSubDeptName],[0,1]);"onclick="return showCodeList('stati',[this,InitSubDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitSubDeptName],[0,1]);"><input class=codename name="InitSubDeptName" id="InitSubDeptName" readonly=true></TD> 	                        
               <TD class=title> 发起日期 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="InitSubDate" id="InitSubDate"></TD>              
           </TR>
        <Table>   
        <Table class=common>
			<TR class= common>
               <TD class= title> 发起描述 </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="InitSubDesc" id="InitSubDesc" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
           	</TR>
        </Table>   		
   	</Div></Div>
    
    <!------分公司处理情况信息应该有：处理机构名称、处理人、处理时间、处理类型、处理意见---------->
    <Div id= "DivFilialeDispInfo" style= "display: ''">
    	<Table>
			<TR>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,xcv);"></td>
				<TD class= titleImg> 分公司呈报处理情况详细信息 </TD>
			</TR>
		</Table><Div  id= "xcv" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> 处理人 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="FilialeDispPer" id="FilialeDispPer"></TD>
               <TD class=title> 处理机构 </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="FilialeDispDept" id="FilialeDispDept" ondblclick="return showCodeList('stati',[this,FilialeDispDeptName],[0,1]);" onclick="return showCodeList('stati',[this,FilialeDispDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,FilialeDispDeptName],[0,1]);"><input class=codename name="FilialeDispDeptName" id="FilialeDispDeptName" readonly=true></TD> 	                        
               <TD class=title> 处理日期 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="FilialeDispDate" id="FilialeDispDate"></TD>              
           </TR>
           <TR class=common>
               <TD class=title> 处理类型 </TD>
			   <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="FilialeDispType" id="FilialeDispType" ondblclick="return showCodeList('lldisptype',[this,FilialeDispTypeName],[0,1]);" onclick="return showCodeList('lldisptype',[this,FilialeDispTypeName],[0,1]);" onkeyup="return showCodeListKey('lldisptype',[this,FilialeDispTypeName],[0,1]);"><input class=codename name="FilialeDispTypeName" id="FilialeDispTypeName" readonly=true></TD>               
                <TD class=title></TD>
               <TD class= input></TD> 	                        
               <TD class=title> </TD>
               <TD class= input></TD> 
          </TR>
        </Table>   	
        <Table class=common>
			<TR class= common>
               <TD class= title> 分公司处理意见 </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="FilialeDispIdea" id="FilialeDispIdea" cols="226" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>
        </Table>   		
   	</Div> 	</Div>
   	
		
    <!------总公司处理情况信息应该有：处理机构名称、处理人、处理时间、处理意见---------->
    <Div id= "DivHeadDispInfo" style= "display: ''">
    	<Table>
			<TR>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vbn);"></td>
				<TD class= titleImg> 总公司呈报处理情况详细信息 </TD>
			</TR>
		</Table><Div  id= "vbn" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> 处理人 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="HeadDispPer" id="HeadDispPer"></TD>
               <TD class=title> 处理机构 </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="HeadDispDept" id="HeadDispDept" ondblclick="return showCodeList('stati',[this,HeadDispDeptName],[0,1]);" onclick="return showCodeList('stati',[this,HeadDispDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,HeadDispDeptName],[0,1]);"><input class=codename name="HeadDispDeptName" id="HeadDispDeptName" readonly=true></TD> 	                        
               <TD class=title> 处理日期 </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="HeadDispDate" id="HeadDispDate"></TD>              
           </TR>
        </Table>   
        <Table class=common>
			<TR class= common>
               <TD class= title> 总公司处理意见 </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="HeadDispIdea" id="HeadDispIdea" cols="226" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>
        </Table>   		
   	</Div> </Div>	
    
    <Input class=cssButton value=" 返 回 " type=button onclick=top.close()>
     
    <!-----保存数据的隐藏表单----->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>     <br><br><br><br>     
</Form>
</body>
</html>
