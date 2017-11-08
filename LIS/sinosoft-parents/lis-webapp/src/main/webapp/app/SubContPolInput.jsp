<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<script>
var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="SubContPolInit.jsp"%>
  <SCRIPT src="SubContPolInput.js"></SCRIPT>
  <SCRIPT src="ProposalAutoMove.js"></SCRIPT>
  
</head>

<body  onload="initForm('<%=tGrpContNo%>', '<%=tPrtNo%>');initElementtype();" >
  <form action="./SubContPolSave.jsp" method=post name=fm id=fm target="fraSubmit">
   <Div  id= "divButton" style= "display:  " align=left >
<!-- 	<%@include file="../common/jsp/OperateButton.jsp"%><%@include file="../common/jsp/InputButton.jsp"%>  -->
  </DIV>
  <DIV id=DivLCContButton STYLE="display: ">
  <table id="table1">
  		<tr>
  			<td class=common>
  			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
  			</td>
  			<td class="titleImg">集体合同信息
  			</td>
  		</tr>
  </table>
</DIV>
     
    <Div  id= "divGroupPol1" class=maxbox1 style= "display:  ">
      <table  class= common>
        <TR  class= common>
	 
          <TD  class=title5>
            集体投保单号码
          </TD>
          <TD  class=input5>
            <Input class="common wid" readonly name=GrpContNo id=GrpContNo>
          </TD>
          <TD  class=title5>
            印刷号码
          </TD>
          <TD  class=input5>
            <Input class="common wid" name=PrtNo id=PrtNo readonly >
          </TD>
        </TR>
      </table>
    </Div>
    
 	<Div  id= "divGeneral" style= "display:  ">
      <table class= common>
	   	<tr>
    	 <td style="text-align:left" colSpan=1>
			<span id="spanGeneralGrid" >
		 </span> 
		</td>
       </tr>
      </table>
	  <table class=common>
        <TR  class=common>
          <TD  class=title5>
            处理机构
          </TD>
          <TD  class=input5>
            <Input class="code" id = execom name=ExecuteCom  verify="处理机构|code:comcode&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
			onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);">
          </TD>
		  <TD class=title5></TD>
          <TD class=input5></TD>
        </TR>
	  </table>
	</Div>

	<DIV id = "divGrpCustomer" style = "display:  ">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol2);">
    		</td>
    		<td class= titleImg>
    			 分单投保单位资料（客户号 <Input class=common name=CustomerNo id=CustomerNo> <INPUT id="butGrpNoQuery" class=cssButton VALUE="查询" TYPE=button onclick="showAppnt();"> ）
    		</td>
    	</tr>
    </table>
    

	<Div  id= "divGroupPol2" class=maxbox style= "display:  ">
      <table  class= common>
       <TR>
          <TD  class=title>
            单位名称
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpName id=GrpName elementtype=nacessary  verify="单位名称|notnull&len<=60">
          </TD>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpAddress id=GrpAddress  elementtype=nacessary verify="单位地址|notnull&len<=60">
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpZipCode id=GrpZipCode  elementtype=nacessary  verify="邮政编码|notnull&zipcode">
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位性质
          </TD>
          <TD  class= input>
            <Input class=code name=GrpNature id=GrpNature  elementtype=nacessary verify="单位性质|code:GrpNature&notnull&len<=10" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="showCodeList('GrpNature',[this]);" onkeyup="showCodeListKey('GrpNature',[this]);">
          </TD>
      
          <TD  class= title>
            行业类别
          </TD>
          <TD  class= input>
            <Input class="code" name=BusinessType id=BusinessType elementtype=nacessary verify="行业类别|code:BusinessType&notnull&len<=20" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('BusinessType',[this]);" onkeyup="return showCodeListKey('BusinessType',[this]);">
          </TD>
          <TD  class= title>
            员工总数
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Peoples id=Peoples  elementtype=nacessary verify="单位总人数|notnull&int">
          </TD>       
        </TR>
        <TR  class= common>
          <TD  class= title>
            单位总机
          </TD>
          <TD  class= input>
            <Input class="wid common"  name=Phone id=Phone elementtype=nacessary verify="单位总机|NUM&notnull&len<=30">
          </TD>
      
          <TD  class= title>
            单位传真
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Fax id=Fax>
          </TD>
          <TD  class= title>
            成立时间
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=FoundDate id=FoundDate verify="成立时间|date" onClick="laydate({elem: '#FoundDate'});">
			<span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>       
        </TR>
   
        <TR  class= common>
          <TD  class= title5 >
            保险联系人一
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="保险联系人一姓名|notnull&len<=10">
          </TD>
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Phone1 id=Phone1 elementtype=nacessary verify="保险联系人一联系电话|NUM&notnull&len<=30">
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input class="wid common" name=E_Mail1 id=E_Mail1 verify="保险联系人一E-MAIL|Email&len<=60">
          </TD>           
        </TR>
     
        <TR  class= common>
          <TD  class= title5>
            保险联系人二
          </TD>       
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=LinkMan2 id=LinkMan2 verify="保险联系人二姓名|len<=10">
          </TD>
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Phone2 id=Phone2 verify="保险联系人二联系电话|NUM&len<=30">
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input class="wid common" name=E_Mail2 id=E_Mail2 verify="保险联系人二E-MAIL|Email&len<=60">
          </TD>
        </TR>    
		
        <TR  class= common>
          <TD  class= title>
            付款方式
          </TD>
          <TD  class= input>
            <Input class="code" name=GetFlag id=GetFlag verify="付款方式|code:PayMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>
          <TD  class= title>
            开户银行
          </TD>
          <TD  class= input>
            <Input class=code name=BankCode id=BankCode verify="开户银行|code:bank&len<=24" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="showCodeList('bank',[this]);" onkeyup="showCodeListKey('bank',[this]);">
          </TD>
          <TD  class= title>
            帐号
          </TD>
          <TD  class= input>
            <Input class="wid common" name=BankAccNo id=BankAccNo verify="帐号|len<=40">
          </TD>       
        </TR>
      </table>
      <table class=common>
         <TR  class= common> 
           <TD  class= title> 备注 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="Remark" id=Remark cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
    </table>
    </Div>
	</DIV>

	<Div>
      <INPUT VALUE="增  加" class=cssButton TYPE=button name=addbutton onclick="appendOne();">
      <INPUT VALUE="修  改" class=cssButton TYPE=button name=modibutton onclick="updateOne();">
      <INPUT VALUE="删  除" class=cssButton TYPE=button name=delbutton onclick="deleteOne();">
      <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="goback();">
    </Div> 
           
      <input type=hidden id="fmAction" name="fmAction">      
      <Input type=hidden name=GrpAddressNo id=GrpAddressNo>
      <Input type=hidden name=GrpProposalContNo id=GrpProposalContNo>
          
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>
