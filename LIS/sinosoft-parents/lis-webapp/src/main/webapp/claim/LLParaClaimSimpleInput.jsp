 <html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLParaClaimSimpleInput.jsp
//程序功能：简单案件标准维护
//创建日期：2005-9-19
//创建人  ：wuhao
//更新记录：  更新人 wuhao    更新日期     更新原因/内容
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
    <SCRIPT src="LLParaClaimSimple.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLParaClaimSimpleInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
    <!-- 简单案件标准维护-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QuerySimple);"></TD>
	        <TD class= titleImg>简易案件标准查询</TD>
	     </TR>
	</Table>  
	<Div  id= "QuerySimple" style= "display:''" class="maxbox">
	 <Table  class= common>
         <TR class= common>  
		<TD  class= title5>管理机构</TD>
	    <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCodeQ id=ComCodeQ ondblclick="return showCodeList('station',[this,ComCodeNameQ],[0,1]);" onclick="return showCodeList('station',[this,ComCodeNameQ],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeNameQ],[0,1]);"><input class="codename" name="ComCodeNameQ" id="ComCodeNameQ" readonly></TD>
		       
		<td class="title5"></td>
        <td class="input5"></td>
	   </TR>   
      </Table>  
	</Div>         	 
    <input value="查  询" class= cssButton type=button onclick="InitQueryClick();">
    <!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">查    询</a>-->

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLParaClaimSimpleGrid);"></TD>
	          <TD class= titleImg>简易案件标准列表</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLParaClaimSimpleGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLParaClaimSimpleGrid"></span></TD>
		    </TR>
		</Table>
		
	</Div>

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLParaClaimSimpleInfo);"></TD>
	          <TD class= titleImg>简易案件标准</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLParaClaimSimpleInfo" style= "display:''" class="maxbox">       
        
          
        <table class= common>
            <tr class= common>        
		<!--
		<TD  class= title>机构编码</TD>
		<TD  class= input><Input class=common name=ComCode><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title>机构名称</TD>      
                <TD  class= input> <Input class=common name=ComCodeName></TD> 
                -->
			<TD class=title>机构名称</TD>
	    	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCode id=ComCode ondblclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeName],[0,1]);"><input class="codename" name="ComCodeName" id="ComCodeName" readonly></TD>
			<TD  class= title>上级机构</TD>      
			<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=UpComCode id=UpComCode ondblclick="return showCodeList('station',[this,UpComCodeName],[0,1]);" onclick="return showCodeList('station',[this,UpComCodeName],[0,1]);" onkeyup="return showCodeListKey('station',[this,UpComCodeName],[0,1]);"><input class="codename" name="UpComCodeName" id="UpComCodeName" readonly></TD>	
             <TD  class= title>最小金额</TD>
                <TD  class= input> <Input class="wid" class=common  name=BaseMin id=BaseMin verify="最小金额|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
	       </tr>
                 
        <tr class= common>    
               
		<TD  class= title>最大金额</TD>
                <TD  class= input> <Input class="wid" class=common  name=BaseMax id=BaseMax verify="最大金额|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>                
                <TD  class= title>启用日期</TD>       
                <TD  class= input> <!--<input class="coolDatePicker" verify="启用日期|date&NOTNULL" dateFormat="short" name="StartDate" onblur="CheckDate(fm.StartDate);">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="启用日期|date&NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                <font size=1 color='#ff0000'><b>*</b></font></TD>          
                <TD  class= title>结束日期</TD>
                <TD  class= input> <!--<input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="EndDate" onblur="CheckDate(fm.EndDate);">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|date&NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>                 
         </tr>
            
        
               
                <tr class= common>     
                <TD  class= title>操作员</TD>
                <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>   
                <TD  class= title>入机日期</TD>       
              <TD  class= input> <Input class="readonly wid" readonly name=MakeDate id=MakeDate ></TD> 
               <TD  class= title>入机时间</TD>
              <TD  class= input> <Input class="readonly wid" readonly name=MakeTime id=MakeTime ></TD>                
            </tr>
            
        <tr class= common>
              
             
              <TD  class= title>最后一次修改日期</TD>
              
              <TD  class= input> <Input class="readonly wid" readonly name=ModifyDate id=ModifyDate ></TD> 
               <TD  class= title>最后一次修改时间</TD>
              <TD  class= input> <Input class="readonly wid" readonly name=ModifyTime id=ModifyTime ></TD>    
          </tr>
          
       
        </table>
                
     </Div>   
					<input class=cssButton name="saveSimpleButton"   value="增   加"  type=button onclick="SimpleAddClick();">
					<input class=cssButton name="editSimpleButton"   value="修   改"  disabled type=button onclick="SimpleEditClick();">
					<input class=cssButton name="deleteSimpleButton" value="删   除"  disabled type=button onclick="SimpleDeleteClick();">                  
                    <input class=cssButton name="resetSimpleButton"  value="重   置" type=button onclick="SimpleResetClick();">
				    <!--<a href="javascript:void(0);" name="saveSimpleButton" class="button" onClick="SimpleAddClick();">增    加</a>
                    <a href="javascript:void(0);" name="editSimpleButton" class="button" disabled onClick="SimpleEditClick();">修    改</a>
                    <a href="javascript:void(0);" name="deleteSimpleButton" class="button" disabled onClick="SimpleDeleteClick();">删    除</a>
                    <a href="javascript:void(0);" name="resetSimpleButton" class="button" onClick="SimpleResetClick();">重    置</a>-->
    
    <input type=hidden name=hideOperate value=''> 
  <input type=hidden id="fmtransact" name="fmtransact">    
      
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
