 <html> 
 	<%
//�������ڣ�2008-09-16
//������  ��Fanxin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="java.util.*"%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String mStartDate = "";
	String mSubDate = "";
	String mEndDate = "";
        GlobalInput tGI = new GlobalInput();
        //PubFun PubFun=new PubFun();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("AssociatedDirectItemDefInput","1"+tGI.Operator);
	loggerDebug("AssociatedDirectItemDefInput","2"+tGI.ManageCom);
%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="AssociatedDirectItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="AssociatedDirectItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr> 
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,dftVerop);">
                </td>   		 
    		 <td class= titleImg>
        		 ��¼�̶����ݶ���
       	 </td>   		 
    	</tr>
    </table>

    <Div id= "dftVerop" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>  -->  

  <table class= common border=0 width=100%>			  	
			<tr class= common>       
         <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionNo id=VersionNo readonly=true>   					 	 
				 </TD>
				 
         <TD class= title5>
					  �汾״̬
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionState2 id=VersionState2 readonly=true>
				 </TD>				 								
			</tr>  
  </table>      
</div>
    <!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return queryClick1();"> -->
    <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick1();">�汾��Ϣ��ѯ</a><br><br>
  <div class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>-->
	
<table class= common border=0 width=100%>
  <table class= common>
			   
			<tr class= common>
				<TD class= title5>
		   	   ר����ֶα�ʶ
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="ר����ֶα�ʶ|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onMouseDown="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');"><input class=codename name=columnidName id=columnidName readonly=true elementtype=nacessary>					
		    </TD>	    		     	
				<TD class= title5>
		   	   ����������Դ�ֶ�
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="����������Դ�ֶ�|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');"><input class=codename name=sourcecolumnName id=sourcecolumnName readonly=true elementtype=nacessary>
					<!--Input class=codeno name=AssociatedID ondblclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="ר����|NOTNULL"><input class=codename name=AssociatedName readonly=true elementtype=nacessary-->		  		
		    </TD>		    	    		          
			</tr>

			<tr class= common>		
        <TD  class= title5>
          ����
        </TD>
        <TD  class= input5>
        	<Input class=wid name=ReMark id=ReMark verify="����|len<=500" >
        </TD>         	
			</tr>			
	</table> 
			   
  <INPUT class=cssButton name="querybutton" id="querybutton" VALUE="��¼�̶����ݶ����ѯ"  TYPE=button onclick="return queryClick2();">
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="ɾ  ��" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">             
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name= resetbutton onclick="return resetAgain()"><br>
    <!-- <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick2();">��¼�̶����ݶ����ѯ</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="return addClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="return updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="return deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="return resetAgain();">��    ��</a> --> 
   
    </table>
    </div>
    <INPUT type=hidden name=hideOperate id=hideOperate value=''>
    <INPUT type=hidden name=SourceTableID id=SourceTableID value=''>    
    <INPUT type=hidden name=ColumnID1 id=ColumnID1 value=''>    
		<INPUT type=hidden name=VersionState id=VersionState value=''> <!-- VersionState������汾״̬��01��02��03����VersionState2��汾״̬��������ά����ɾ��-->    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
