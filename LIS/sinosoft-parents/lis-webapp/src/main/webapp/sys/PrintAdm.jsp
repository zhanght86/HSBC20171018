<!--
*******************************************************
* 程序名称：ProposalPrtQue.jsp
* 程序功能：签单录入页面
* 创建日期：2002-07-16
* 更新记录：  更新人    更新日期     更新原因/内容
*              彭谦    2002-07-16    新建
*******************************************************
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<HTML>

<HEAD>

<TITLE>银行代理险</TITLE>
	<!--公用函数-->  
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	

	<!--多行输入函数-->
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<!--通用校验-->
	<!--SCRIPT src="../JavaScript/StdCheck.js"></SCRIPT-->
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	 
	
	<!--本页初始化-->
	<%@include file="PrintAdmIni.jsp"%>
    
	<!-- 页面样式  -->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

	                
</HEAD>
<BODY onload = "initForm();">
<FORM action=PrintAdmSave.jsp method = post name=fm target=fraSubmit>
 <input type=hidden name="opt">	  	     
  <TABLE class=common>
    <TBODY>
	    <tr>
	  	  <td class=formtitle colspan="4">
	  		  打印服务配置管理
	  	  </td>
	    </tr>
  	 <TR><td class=subformtitle colspan="4" style="text-align:left">
	  		  打印配置文件
	  	  </td></TR>
	 	  
	<TR> 
	  <TD>
    	    <span id="spanIniinfo"  >
            </span>
	   </td><tr>
	   
    </TABLE>	   
	<TABLE class=common>   	  
	    	<TR>  
		    <TD class=title>输入ip： </TD>
		    
		     <TD class=title>输入模板名：</TD>
		     
		     
		    
		</tr>
		<TR>
		    <TD class=input nowrap>
		        <INPUT class=common   name=Ipinput1 type=text  readonly=true style='width:30px' style="text-align:center">.
		        <INPUT class=common   name=Ipinput2 type=text  readonly=true style='width:30px' style="text-align:center">.
		        <INPUT class=common   name=Ipinput3  style='width:72px' >.
		        <INPUT class=common   name=Ipinput4  style='width:72px'  >
		     </TD>
		     
		     <TD  class= input>
            		<Input class="wid" class= code name=Templeinput verify="号码类型|NOTNULL" CodeData="0|^0|普通^1|票据^2|宽行报表" ondblClick="showCodeListEx('Templeinput',[this],[0,1,2]);" onkeyup="showCodeListKeyEx('Templeinput',[this],[0,1,2]);" >
          	     </TD>
		</tr>
		<TR>  
		    <TD class=title>输入打印服务器名： </TD>
		    
		     <TD class=title>输入打印机名：</TD>
		    
		</tr>
		<TR>
		    <TD class=input>
		        <INPUT class="wid" class=common name=Serverinput  >
		     </TD>
		     <TD class=input>
		     <INPUT class="wid" class=common name=Printinput >
		     </TD>
		</tr>
		
		<TR>
	  	    
	  	   
	  	<TD class=input align=center>
		        <input type="button" value="添加配置信息的一行" name="insert" onclick="return add();">
			
        	</td>
        	<TD class=input align=center>
		        <input type="button" value="删除选中的配置信息" name="delete" onclick="return del();">
			
        	</td>
	  	</TR>
	  	<tr>
	  	  <td class=formtitle colspan="4">
	  	注意：在添加，删除配置信息后，点击保存才会起作用。
	  	  </td>
	    </tr>
	<tr class="mline" >      
    	 <td class="formtitle" colspan="4" style="text-align:center"></td>
	</tr>
   </Table>
   <%@include file="PrtAdmButton.jsp"%>
	

<!-- 作为代码选择的span -->
<span id="spanCode"  style="display: none; position:absolute; slategray">
  <select name=codeselect>
  </select>
</span>
<script language="JavaScript">



</script>
</FORM>
</BODY>
</HTML>
    
