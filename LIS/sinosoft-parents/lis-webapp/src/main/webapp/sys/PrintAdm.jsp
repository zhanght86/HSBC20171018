<!--
*******************************************************
* �������ƣ�ProposalPrtQue.jsp
* �����ܣ�ǩ��¼��ҳ��
* �������ڣ�2002-07-16
* ���¼�¼��  ������    ��������     ����ԭ��/����
*              ��ǫ    2002-07-16    �½�
*******************************************************
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<HTML>

<HEAD>

<TITLE>���д�����</TITLE>
	<!--���ú���-->  
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	

	<!--�������뺯��-->
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<!--ͨ��У��-->
	<!--SCRIPT src="../JavaScript/StdCheck.js"></SCRIPT-->
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	 
	
	<!--��ҳ��ʼ��-->
	<%@include file="PrintAdmIni.jsp"%>
    
	<!-- ҳ����ʽ  -->
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
	  		  ��ӡ�������ù���
	  	  </td>
	    </tr>
  	 <TR><td class=subformtitle colspan="4" style="text-align:left">
	  		  ��ӡ�����ļ�
	  	  </td></TR>
	 	  
	<TR> 
	  <TD>
    	    <span id="spanIniinfo"  >
            </span>
	   </td><tr>
	   
    </TABLE>	   
	<TABLE class=common>   	  
	    	<TR>  
		    <TD class=title>����ip�� </TD>
		    
		     <TD class=title>����ģ������</TD>
		     
		     
		    
		</tr>
		<TR>
		    <TD class=input nowrap>
		        <INPUT class=common   name=Ipinput1 type=text  readonly=true style='width:30px' style="text-align:center">.
		        <INPUT class=common   name=Ipinput2 type=text  readonly=true style='width:30px' style="text-align:center">.
		        <INPUT class=common   name=Ipinput3  style='width:72px' >.
		        <INPUT class=common   name=Ipinput4  style='width:72px'  >
		     </TD>
		     
		     <TD  class= input>
            		<Input class="wid" class= code name=Templeinput verify="��������|NOTNULL" CodeData="0|^0|��ͨ^1|Ʊ��^2|���б���" ondblClick="showCodeListEx('Templeinput',[this],[0,1,2]);" onkeyup="showCodeListKeyEx('Templeinput',[this],[0,1,2]);" >
          	     </TD>
		</tr>
		<TR>  
		    <TD class=title>�����ӡ���������� </TD>
		    
		     <TD class=title>�����ӡ������</TD>
		    
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
		        <input type="button" value="���������Ϣ��һ��" name="insert" onclick="return add();">
			
        	</td>
        	<TD class=input align=center>
		        <input type="button" value="ɾ��ѡ�е�������Ϣ" name="delete" onclick="return del();">
			
        	</td>
	  	</TR>
	  	<tr>
	  	  <td class=formtitle colspan="4">
	  	ע�⣺����ӣ�ɾ��������Ϣ�󣬵������Ż������á�
	  	  </td>
	    </tr>
	<tr class="mline" >      
    	 <td class="formtitle" colspan="4" style="text-align:center"></td>
	</tr>
   </Table>
   <%@include file="PrtAdmButton.jsp"%>
	

<!-- ��Ϊ����ѡ���span -->
<span id="spanCode"  style="display: none; position:absolute; slategray">
  <select name=codeselect>
  </select>
</span>
<script language="JavaScript">



</script>
</FORM>
</BODY>
</HTML>
    
