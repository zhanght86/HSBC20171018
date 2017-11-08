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

	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("ClmNo");	//赔案号
      
%>
<title>呈报信息查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLColQueryClaimUWMDetail.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLColQueryClaimUWMDetailInit.jsp"%>
</head>
<body onLoad="initForm();">
  
<form name=fm id=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLClaimUWMDetailGrid);"></TD>
            <TD class= titleImg> 案件核赔履历信息 </TD>
        </TR>
    </Table>       
    <Div id= "DivLLClaimUWMDetailGrid" style= "display: ''">
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLClaimUWMDetailGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <TR>  
                 <TD><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></TD>
             </TR> 
         </table>   --> 
    </Div>

    <Div id= "DivLLClaimUWMDetailInfo" style= "display:none">
        <!--审核管理信息-->
        <Span id="spanAudit" style="display: ''">
            
            <table>
              <TR>
               <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
               <TD class= titleImg>审核管理</TD>
             </TR>
            </table>
            <Div id= "divLLAudit" style= "display: ''" class="maxbox">
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>审核结论</TD>
                        <TD  class= input><Input type=hidden name=AuditConclusion onDblClick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="wid" class=readonly readonly name=AuditConclusionName id=AuditConclusionName readonly=true></TD>
                        <TD  class= title>特殊备注</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name=SpecialRemark1 id=SpecialRemark1></TD>
                        <TD  class= title>拒付原因</TD>
                            <TD  class= input><Input type=hidden name=ProtestReason onDblClick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onKeyUp="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ProtestReasonName id=ProtestReasonName readonly=true></TD>
                    </tr>
                </table>
                <Div id= "divLLAudit2" style= "display: none">
                     <table class= common>
                        <TR class= common>
                            
                            
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                </div>
                 <table class= common>
                    <TR class= common>
                    <TD  class= title>拒付依据</TD>
                            <TD  class= input><Input class="wid" class=common disabled name=ProtestReasonDesc id=ProtestReasonDesc></TD>
                        <TD  class= title>审核人</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditPer" id="AuditPer" ></TD>
                        <TD  class= title>审核日期</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditDate" id="AuditDate" ></TD>
                        
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title>审核意见</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" id="AuditIdea" cols="224" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                 </table>
            </Div>
        </span>
        <!--审批结论信息-->
        <Span id= "spanConfirm" style="display: '';">
            
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowQueryDetail);"></td>
                    <td class= titleImg> 审批内容</td>
                </tr>
            </table>
            <Div  id= "divShowQueryDetail" style="display: '';" class=" maxbox">
                <table class= common>
               	    <TR class= common>
        	            <TD  class= title>审批结论</TD>
                        <TD  class= input><Input type=hidden name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onKeyUp="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" onfocus= "choiseQueryConclusionType();"><input class="wid" class=readonly readonly name=DecisionSPName id=DecisionSPName readonly></TD>
                        <TD  class= title>审批人</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamPer" id="ExamPer" ></TD>
                        <TD  class= title>审批日期</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamDate" id="ExamDate" ></TD>
        	        </TR>
                </table>
                <Div id= "divLLQuery2" style= "display: none">
                     <table class= common>
                        <TR class= common>
                            <TD  class= title>不通过原因</TD>
                            <TD  class= input><Input type=hidden name=ExamNoPassReason onDblClick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);" onKeyUp="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ExamNoPassReasonName id=ExamNoPassReasonName readonly=true></TD>
                            <TD  class= title>不通过依据</TD>
                            <TD  class= input><Input class="wid" class=readonly readonly name=ExamNoPassDesc id=ExamNoPassDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input><Input class="wid" class=readonly readonly ></TD>
                        </tr>        
                    </table>
                </div>
                <table class= common>
                    <TR class= common>
                        <TD  class= title> 审批意见</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="RemarkSP" id="RemarkSP" cols="224" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
        </span>
   	    
    <!--<Input class=cssButton value=" 返 回 " type=button onclick="top.close();" >-->
     <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    <!-----保存数据的隐藏表单----->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  <br><br><br><br>        
</Form>
<br><br><br><br><br>
</body>
</html>
