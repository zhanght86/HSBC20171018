<%
//**************************************************************************************************/
//Name：LLCaseBackInput.jsp
//Function：案件回退类管理
//Author：wanzh
//Date: 2005-10-20 14:03
//Desc: 
//**************************************************************************************************/
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
      String tClmNo = request.getParameter("claimNo");	//赔案号
      String tBackType = request.getParameter("BackType");	//回退类型
	  String tSubmitFlag = request.getParameter("SubmitFlag");
//=======================END========================
%>
<head>
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
    <SCRIPT src="LLCaseBack.js"></SCRIPT>
    <%@include file="LLCaseBackInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">

    <!--立案信息-->
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>回退信息</TD>
        </TR>
    </table>
    
    <Div  id= "divLLReport1" class=maxbox style= "display: ''">
        <table  class= common>
       	    <TR  class= common>
			    <TD  class= title> 赔案号 </TD>
          	    <TD  class= input> <Input class="wid readonly" name=ClmNo  id=ClmNo   readonly ></TD>
          	    <TD  class= title> 回退号 </TD>
          	    <TD  class= input> <Input class="wid readonly" name=BackNo  id=BackNo  readonly ></TD>
			    <TD  class= title> 是否同时回退预付信息</TD>
          	    <TD  class= input> <Input class=codeno name=whetherBackPre id=whetherBackPre style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('whetherbackpreflag',[this,whetherBackPreName],[0,1]);" onkeyup="return showCodeListKey('whetherbackpreflag',[this,whetherBackPreName],[0,1]);" >
				<input class=codename name=whetherBackPreName id=whetherBackPreName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
           </TR>
          	<TR  class= common>
                <TD  class= title> 回退申请日期</TD>
          	    <TD  class= input> <Input class="wid readonly" name=ApplyDate id=ApplyDate readonly ></TD>
          	    <TD  class= title> 原理赔结论</TD>
          	    <TD  class= input> <Input class="wid readonly" name=OriGiveType id=OriGiveType readonly ></TD>
			    <TD  class= title> 原给付总金额</TD>
          	    <TD  class= input> <Input class="wid readonly" name=OriRealyPay id=OriRealyPay readonly ></TD>
          	</TR>
            <TR  class= common>
          	    <TD  class= title> 签报号</TD>
          	    <TD  class= input> <Input class="wid common"  name=ApplyUesr id=ApplyUesr ></TD>
                <TD  class= title> 拟申请回退后的理赔结论</TD>
          	    <TD  class= input> <Input class=codeno name=NewGiveType id=NewGiveType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llclaimconclusion',[this,NewGiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,NewGiveTypeName],[0,1]);" ><input class=codename name=NewGiveTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
          	    <TD  class= title> 回退原因</TD>
          	    <TD  class= input> <Input class= codeno name=BackReason id=BackReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('llbackreason', [this,BackReasonName],[0,1]);" onkeyup="return showCodeListKey('llbackreason', [this,BackReasonName],[0,1]);"><Input class=codename name=BackReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
			    
			</TR>
        </table>
        
        <table class= common>
            <TR class= common>
                <TD class= title> 回退原因详细说明 </TD>
            </tr>
            <TR class= input>
                <TD class= input> <textarea name="BackDesc" id=BackDesc cols="100" rows="2" witdh=25% class="common" ></textarea>
                </TD>
            </tr>
        </table>
        
        <table class= common>
            <TR class= common>
                <TD class= title> 备注 </TD>
            </tr>
            <TR class= input>
                <TD class= input> <textarea name="Remark" id=Remark cols="100" rows="2" witdh=25% class="common" ></textarea></TD>
            </tr>
        </table>
  	
  	</Div>
    
    <table>
        <tr>
        	<td><INPUT class=cssButton name=CaseBackQueryBt VALUE="查  询"  TYPE=button onclick="CaseBackQuery()"></td>
            <td><input class=cssButton name=CaseBackSaveBt VALUE="保  存"  TYPE=button onclick="CaseBackSave()"></td>
            
            <td>
               <span id="operateButton1" style= "display: ''"><input class=cssButton name=CaseBackSaveBt id=CaseBackSaveBt VALUE="暂交费核销"  TYPE=button onclick="CaseFeeCancel()"></span>
            </td>
        	<td><input class=cssButton name=CaseBackSubmitBt VALUE="完  成"  TYPE=button onclick="CaseBackSubmit()"></td>
            <td><INPUT class=cssButton name=goBack2 VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>

	<Div  id= "divLLReport2" style= "display: ''">
 	<!--赔案下的应收总表-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);"></TD>
            <TD class= titleImg>待回销的应收信息</TD>
        </TR>
    </table>
	<Div  id= "divPol" align= center style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD style="text-align: left" colSpan=1><span id="spanLJsPayGrid" ></span></TD>
			</TR>
		</Table>
	</Div> 	
	<table>
    	<tr>
    		<td><input class=cssButton name=CaseBackNoticeBt VALUE="打印理赔回退收费通知书"  TYPE=button onclick="PrintClick()"></td>
	    </tr>
    </table>
	</Div> 
    <%
    //隐藏区,保存信息用
    %>
    <input type=hidden id="PrtNoticeNo" name="PrtNoticeNo">
    <input type=hidden id="fmtransact" name="fmtransact">
    <Input type=hidden id="ClmNoQ" name="ClmNoQ">   <!----赔案号---------->
    <Input type=hidden id="BackReasonQ" name="BackReasonQ">
	<Input type=hidden id="BackTypeQ" name="BackTypeQ">
    <br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
