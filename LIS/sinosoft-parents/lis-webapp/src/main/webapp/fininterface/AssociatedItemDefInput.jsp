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
	loggerDebug("AssociatedItemDefInput","1"+tGI.Operator);
	loggerDebug("AssociatedItemDefInput","2"+tGI.ManageCom);
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
  <SCRIPT src="AssociatedItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="AssociatedItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr>  
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divVerop);">
                </td>  		 
    		 <td class= titleImg>
        		 ��Ŀר���
       	 </td>   		 
    	</tr>
    </table>

    <Div id= "divVerop" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td> -->   

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
  <div class="maxbox">
	<!--<td class=button width="10%" align=right>
				
	</td>-->
	
<table class= common border=0 width=100%>
  <table class= common>
			
  	
			<tr class= common>
				<TD class= title5>
		   	   ר����
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=AssociatedID id=AssociatedID elementtype=nacessary verify="ר����|len<=15" >
		    </TD>
				<TD class= title5>
					 ר������
				</TD>
				<TD class=input5>
					<Input class=wid name=AssociatedName id=AssociatedName elementtype=nacessary verify="ר������|len<=15" >
				</TD>		
			</tr>
			   
			<tr class= common>
				<!--TD class= title>
		   	   ר����ֶα�ʶ
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=ColumnID verify="ר����ֶα�ʶ|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1]);" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1]);"><input class=codename name=columnidName readonly=true elementtype=nacessary>
		    </TD-->
				<TD class= title5>
		   	   ר����ֶα�ʶ
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="ר����ֶα�ʶ|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');"  onMouseDown="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onkeyup="return showCodeListKey('columnid',[this],[0,1],null,'FIDataTransResult','TableID');"><input class=codename name=columnidName  elementtype=nacessary>					
		    </TD>	 
		    
		    
				<!--TD class= title>
		   	   ����������Դ�ֶ�
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=SourceColumnID verify="����������Դ�ֶ�|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1]);"><input class=codename name=sourcecolumnName readonly=true elementtype=nacessary>
		    </TD-->   	
				<TD class= title5>
		   	   ����������Դ�ֶ� 
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="����������Դ�ֶ�|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');"><input class=codename name=sourcecolumnName id=sourcecolumnName  elementtype=nacessary>
		  				    
					<!--Input class=codeno name=AssociatedID ondblclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="ר����|NOTNULL"><input class=codename name=AssociatedName readonly=true elementtype=nacessary-->		  		
		    </TD>		
		    
        <!--TD  class= title>
          ����������Դ����
        </TD>
        <TD  class= input>
        	<Input class=codeno name=SourceTableID verify="����������Դ����|NOTNULL" CodeData="0|^FIAboriginalData|ƾ֤ҵ�����ݱ�" ondblClick="showCodeListEx('SourceTableID',[this,SourceTableName],[0,1]);" onkeyup="showCodeListKeyEx('SourceTableID',[this,SourceTableName],[0,1]);"><input class=codename name=SourceTableName readonly=true elementtype=nacessary>
        </TD-->        
			</tr>

			<tr class= common>		
        <TD  class= title5>
          ת����־
        </TD>
        <TD  class= input5>
        	<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=TransFlag id=TransFlag verify="ת����־|NOTNULL" readonly=true  CodeData="0|^N|��ת��^S|SQLת��^C|����ת��" ondblClick="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);"><input class=codename name=TransFlagName id=TransFlagName readonly=true elementtype=nacessary>
        </TD>
        <TD  class= title5>
          ����
        </TD>
        <TD  class= input5>
        	<Input class=wid name=ReMark verify="����|len<=500" >
        </TD>         	
			</tr>			
	</table> 
			   
	<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
			<tr class= common>
        <TD  class= title5 >
          ת�����ʹ�����
        </TD>
        <TD  class= input5 >
        	<Input class=wid name=TransClass id=TransClass >
        </TD> 
         
        <TD  class= title5 >  
        </TD> 
        <TD  class= input5 >
        </TD>          	                        	       
			</tr> 						          
	</table> 	  
	</div>		 
    
  <Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= title>ת��SQL��4000�ַ����ڣ�</TD>
			</tr>
			<tr  class= common>
				<TD  class= common>
					<textarea style="margin-left:16px" name="TransSQL" verify="ת��SQL|len<=4000" verifyorder="1" cols="176" rows="4" witdh=100% class="common" ></textarea>
				</TD>
			</tr>
	</table>	
  </div>	 
 <INPUT class=cssButton name="querybutton" id="querybutton" VALUE="��Ŀר����ѯ"  TYPE=button onclick="return queryClick2();">
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="addbutton" id="addbutton" onclick="return addClick();">   
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="updatebutton" id="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="ɾ  ��" TYPE=button class= cssButton name="deletebutton" id="deletebutton" onclick="return deleteClick();">
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name= resetbutton id= resetbutton onclick="return resetAgain()"><br><br>
   <!--  <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick2();">��Ŀר����ѯ</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="return addClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="return updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="return deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="return resetAgain();">��    ��</a> -->
    </div>
    
    <INPUT type=hidden name=hideOperate id=hideOperate value=''>
    <INPUT type=hidden name=SourceTableID id=SourceTableID value=''>    
		<INPUT type=hidden name=VersionState id=VersionState value=''> <!-- VersionState������汾״̬��01��02��03����VersionState2��汾״̬��������ά����ɾ��-->    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
