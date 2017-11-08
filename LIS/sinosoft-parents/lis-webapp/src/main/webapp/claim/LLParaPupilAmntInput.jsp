<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLParaPupilAmntInput.jsp
//程序功能：未成年人保额标准维护
//创建日期：2005-9-16
//创建人  ：zhangyang
//更新记录：  更新人 zhangyang    更新日期     更新原因/内容
%> 
<head >
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
    <SCRIPT src="LLParaPupilAmnt.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLParaPupilAmntInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
    <!--未成年人保额标准维护-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common>
	     		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);">
	     	</TD>
	        <TD class= titleImg>
	        	未成年人保额标准查询
	       	</TD>
	    </TR>
	</Table> 
	
	 
	<Div  id= "QueryHospital" style= "display:''" class="maxbox1">
	<Table  class= common>
      <TR class= common>  
				<TD class=title5>管理机构</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCodeQ id=ComCodeQ verify="管理机构|code:comcode&notnull" onDblClick="return showCodeList('comcode',[this,ComCodeNameQ],[0,1]);" onClick="return showCodeList('comcode',[this,ComCodeNameQ],[0,1]);" onKeyUp="return showCodeListKey('comcode',[this,ComCodeNameQ],[0,1]);"><input class=codename name=ComCodeNameQ id=ComCodeNameQ readonly=true elementtype=nacessary>
				</TD>
      <TD class=title5></TD>
      <TD class=input5></TD>
	    </TR>   
	</Table>  
	
	
	</Div>         	 
    <input value="查   询" class= cssButton type=button onclick="InitQueryClick();">
<!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">查    询</a>-->
	<Table>
	    <TR>
	     	<TD class=common>
	     		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLParaPupilAmntGrid);">
	     	</TD>
	      <TD class= titleImg>
	      	未成年人保额标准列表
	      </TD>
	    </TR>
	</Table>	    
	

<Div id= "DivLLParaPupilAmntGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLParaPupilAmntGrid"></span></TD>
		    </TR>
		</Table>
       
	</Div>
    
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></TD>
	          <TD class= titleImg>未成年人保额标准信息</TD>
	     </TR>
	</Table>  
    <Div id= "peer" style= "display:''" class="maxbox">       
         
        <Table  class= common>
            <TR class= common>   
				<TD class=title5>管理机构</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCode id=ComCode verify="管理机构|code:comcode&notnull" onDblClick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onClick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onKeyUp="return showCodeListKey('comcode',[this,ComCodeName],[0,1]);"><input class=codename name=ComCodeName id=ComCodeName readonly=true elementtype=nacessary>
				</TD>
				<TD CLASS=title5>标准保额</TD>
             <TD CLASS=input5><Input class="wid" NAME=BaseValue id=BaseValue VALUE="" CLASS=common></TD>
				</TR>
            <TR class= common>   
				
        <TD class=title5>启用日期</TD>
				<TD class=input5>
					<!--<Input class="coolDatePicker"  dateFormat="short" name=StartDate verify="启用日期|date" onblur="CheckDate(fm.StartDate);">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="启用日期|date" onBlur="CheckDate(fm.StartDate);" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class=title5>结束日期</TD>
				<TD class=input5>
					<!--<Input class="coolDatePicker"  dateFormat="short" name=EndDate verify="结束日期|date" onblur="CheckDate(fm.EndDate);">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|date" onBlur="CheckDate(fm.EndDate);" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				</TR>            
		<!--</Table>                             
    </Div> 
    <Div id= "DivLLParaPupilAmntInfo" style= "display:'none'"> -->
     <tr class=common>
     <TD CLASS=title5>上级机构</TD>
             <TD CLASS=input5><Input class="wid" NAME=UpComCode id=UpComCode CLASS=common></TD>
             <TD CLASS=title5></TD>
             <TD CLASS=input5></TD>
             </tr>
    </Table>                             
    </Div>   
   			<!--<input class=cssButton name="saveBaseValueButton"   value="增   加"  type=button onclick="BaseValueAddClick();">
			<input class=cssButton name="editBaseValueButton"   value="修   改"  disabled type=button onclick="BaseValueEditClick();">
			<input class=cssButton name="deleteBaseValueButton" value="删   除"  disabled type=button onclick="BaseValueDeleteClick();">                      
            <input class=cssButton name="resetBaseValueButton"  value="重   置" type=button onclick="BaseValueResetClick();"> -->
            <a href="javascript:void(0);" name="saveBaseValueButton" id="saveBaseValueButton" class="button"  onClick="BaseValueAddClick();">增    加</a>
               <a href="javascript:void(0);" name="editBaseValueButton" id="editBaseValueButton" class="button"  disabled="true" onClick="BaseValueEditClick();">修    改</a>
               <a href="javascript:void(0);" name="deleteBaseValueButton" id="deleteBaseValueButton" class="button"  disabled="true" onClick="BaseValueDeleteClick();">删    除</a>
               <a href="javascript:void(0);" name="resetBaseValueButton" id = "resetBaseValueButton  "class="button" onClick="BaseValueResetClick();">重    置</a> 
    
    <!--隐藏表单区域-->	
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--咨询通知号码-->
 
 	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
