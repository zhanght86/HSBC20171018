 <html> 
 	<%
//�������ڣ�2008-08-11
//������  ��ZhongYan
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
	loggerDebug("FinItemDefInput","1"+tGI.Operator);
	loggerDebug("FinItemDefInput","2"+tGI.ManageCom);
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
  <SCRIPT src="FinItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="FinItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm   target=fraSubmit method=post>
	 <table>
    	<tr> 
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divVerer);">
                </td>   		 
    		 <td class= titleImg>
        		 ��Ŀ��Ϣ����
       	 </td>   		 
    	</tr>
    </table>

     <Div id= "divVerer" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>    
-->
  <table class= common border=0 width=100%>			  	
			<tr class= common>       
         <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionNo readonly=true>   					 	 
				 </TD>
				 
         <TD class= title5>
					  �汾״̬
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionState2 readonly=true>
				 </TD>				 								
			</tr>  
  </table>       
</div>
    <!--<INPUT class=cssButton name="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return queryClick1();">  -->
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick1();">�汾��Ϣ��ѯ</a><br><br>
  <div class="maxbox1">
	<td class=button width="10%" align=right>
				
	</td>
	
<table class= common border=0 width=100%>
  <table class= common>
			
  	
			<tr class= common>
				<!--TD class= title>
					  �汾���
				 </TD>
				 <TD class=input>
				 	 <Input class=common name=VersionNo  elementtype=nacessary>
				</TD-->
				<TD class= title5>
					  ��Ŀ���
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=FinItemID  elementtype=nacessary verify="��Ŀ���|len<=20">
					 <!--Input class= readonly name=FinItemID readonly=true-->				 	 
				</TD>
				<TD class= title5>
		   	   ��Ŀ����
		    </TD>
		    <TD class= input5>                                            
		  		 <Input class=wid name=FinItemName  elementtype=nacessary verify="��Ŀ����|len<=40">
		    </TD>				
			</tr>
			   
			<tr class= common>
				<TD class= title5>
		   	   ��Ŀ����
		    </TD>
		    <TD class= input5>
					 <!--Input class=codeno name=accountCodeType CodeData="0|^1|�ʲ�|M^2|��ծ|M^6|����" verify="��Ŀ����|notnull" ondblclick="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"><input class=codename name=accountCodeTypeName readonly=true elementtype=nacessary -->	
		  		 <Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name= FinItemType readonly=true verify="��Ŀ����|NOTNULL" CodeData="0|^1|�ʲ�^2|��ծ^6|����" ondblClick="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);"><input class=codename name=FinItemTypeMame readonly=true elementtype=nacessary>
		    </TD>
				<TD class= title5>
					  ��Ŀ���루һ����
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=ItemMainCode  elementtype=nacessary verify="��Ŀ���루һ����|len<=20">
				</TD>		    		    
			</tr>
			
			<tr class= common>
				<!--TD class= title>
		   	   ��Ŀ����ʽ
		    </TD>
		    <TD class= input>		    	
		  		<Input class=codeno name= DealMode verify="��Ŀ����ʽ|NOTNULL" CodeData="0|^1|��������^2|���⴦��" ondblClick="showCodeListEx('DealMode',[this,DealMame],[0,1]);" onkeyup="showCodeListKeyEx('DealMode',[this,DealMame],[0,1]);"><input class=codename name=DealMame readonly=true elementtype=nacessary>
		    </TD-->
		    
				<TD class= title5>
		   	   ����
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=ReMark verify="����|len<=500" >
		    </TD>		    		    
			</tr>
			
			<tr class= common>
				 <!--TD class= title>
					  ���⴦����
				 </TD>
				 <TD class=input>
				 	 <Input class=common name=DealSpecialClass >
				</TD-->
			</tr>
		  
    </table>         
    <!--<INPUT class=cssButton name="querybutton" VALUE="��Ŀ���Ͷ����ѯ"  TYPE=button onclick="return queryClick2();">--><br>
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick2();">��Ŀ���Ͷ����ѯ</a>				 
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="ɾ  ��" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="resetbutton" onclick="return resetAgain();"> 
    <!--  <a href="javascript:void(0);" class="button" name="addbutton" onClick="addClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">��    ��</a> -->
   
    
    </div>
    <hr class="line">
<!--    <INPUT VALUE="��Ŀ����ר���" TYPE=button class= cssButton name="intobutton1" onclick="return intoAssociatedDef();">   
    <INPUT VALUE="��ϸ��Ŀ�ж���������" TYPE=button class= cssButton name="intobutton2" onclick="return intoDetailDef();">--> <br>
    <a href="javascript:void(0);" class="button" name="intobutton1" onClick="return intoAssociatedDef();">��Ŀ����ר���</a>
    <a href="javascript:void(0);" class="button" name="intobutton2" onClick="return intoDetailDef();">��ϸ��Ŀ�ж���������</a>   
  
    
    <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionState value=''> <!-- VersionState������汾״̬��01��02��03����VersionState2��汾״̬��������ά����ɾ��-->
    <INPUT type=hidden name=DealMode value=''>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
