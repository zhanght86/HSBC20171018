<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealth.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="java.util.*"%>
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  String tPrtSeq = "";
  String tCustomerNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  String time = PubFun.getCurrentTime();
  
  tContNo = request.getParameter("ContNo");
  String tEdorNo = request.getParameter("EdorNo");
  tMissionID = request.getParameter("MissionID");
  String tActivityID=request.getParameter("ActivityID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tPrtSeq = request.getParameter("PrtSeq");
  tCustomerNo = request.getParameter("CustomerNo");

  tFlag = request.getParameter("Flag");
 %>                         

<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./BQManuHealthQ.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type='text/css'>
  <LINK href="../common/css/Project3.css" rel=stylesheet type='text/css'>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type='text/css'>
  <%@include file="BQManuHealthQInit.jsp"%>
  <title> �б��������¼�� </title>

</head>
<script language="JavaScript">
var tFlag = "<%=tFlag%>";
var tday = "<%=tday%>";
var time = "<%=time%>";
function initPage()
{

       initForm('<%=tActivityID%>','<%=tContNo%>','<%=tEdorNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');
       
}

function operatorQuery()
{
		alert("in!");
		var prtSeq = fm.PrtSeq.value;
		var CustomerNo = fm.CustomerNo.value;
		var arrReturn;
		var strSQL = "select Operator,MakeDate,ModifyTime from LCPENotice where PrtSeq = '"+prtSeq+"' and CustomerNo='"+CustomerNo+"'";
		alert(strSQL);
//		try{
//			arrReturn =easyExecSql(strSQL);
//		}
//		catch(ex)
//		{
//			alert( "��ѯ������ʧ�ܣ�");		
//		}
//		
//		document.all.('Operator').value = arrReturn[0];
//		document.all.('MakeDate').value = arrReturn[1];
		return;	
}
</script>

<body  onload="initPage();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./UWManuHealthChk.jsp">
    <!-- ���б� -->
    
    
    <div id = "divMainHealth" style = "display : none">
       <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainUWSpec1);"></td>
    		<td class= titleImg>	 �������</td>
    	</tr>
    </table>
    
    <Div  id= "divMainUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanMainHealthGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
      </div>
    </div>
    <!--  div id ="divOperation" style = "display : none" class="maxbox"   -->  
    <div id ="divOperation"  class="maxbox"> <!--wyc 2016-4-22 ҳ����ʾ����¼ҽԺ -->
    <table class=common style ="display : none">
       <tr  class= common>
          <td class=title5>������</td><td class=input5><Input  class="readonly wid" name= 'Operator' id= 'Operator' readonly></td>
          <td class=title5>����ʱ��</td><td class=input5><Input  class="readonly wid" name= 'MakeDate' id= 'MakeDate' readonly></td>
       </tr>
        <tr  class= common>
          <td class=title5>�ظ�ʱ��</td><td class=input5><Input  class="readonly wid" name= 'ReplyDate' id= 'ReplyDate' readonly></td>
       </tr>
    </table>
    
    <table class=common>
        <TR  class= title>


          <Input type= "hidden" class= Common id="ContNo" name= 'ContNo' value= "">
         <!--  <Input type= "hidden" class= Common name= 'PrtNo' value= "">-->
          <INPUT  type= "hidden" class= Common id="MissionID" name= 'MissionID' value= "">
          <INPUT  type= "hidden" class= Common id="SubMissionID" name= 'SubMissionID' value= "">
          <INPUT  type= "hidden" class= Common id="ActivityID" name= 'ActivityID' value= "">
          <INPUT  type= "hidden" class= Common id="PrtSeq" name= 'PrtSeq' value= "">
          <INPUT  type= "hidden" class= Common id="CustomerNo" name= 'CustomerNo' value= "">
          <INPUT  type= "hidden" class= Common id="ManageCom" name= 'ManageCom' value= "">
          <INPUT  type= "hidden" class= Common id="ItemNum" name= 'ItemNum' value= "">       
          <TD  class= title5>  ӡˢ��  </TD>
          <TD  class= input5> <Input class="readonly wid" name='PrtNo' id='PrtNo' readonly = 'true'><Input type= "hidden" class= Common id="EdorNo" name='EdorNo' value= ""> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input class="readonly wid" name='PEName' id='PEName'  readonly = 'true'> </TD>
        </TR>
        <TR  class= title>
          <TD  class= title5>  ������Ա�  </TD>
          <TD  class= input5> <Input class="readonly wid" name='Sex' id='Sex'  readonly = 'true'> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input class="readonly wid" name='Age' id='Age'  readonly = 'true'> </TD>
        </TR>
        <TR  class= title>
          <TD  class= title5>  ���ҽԺ  </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=PEHospital id=PEHospital ondblclick="return showCodeList('pehospital',[this,PEAddress],[0,1]);" onclick="return showCodeList('pehospital',[this,PEAddress],[0,1]);" onkeyup="return showCodeListKey('pehospital',[this,PEAddress],[0,1]);"><input class=codename name=PEAddress id=PEAddress readonly=true elementtype=nacessary></TD>
         <!--   <TD  class= title>  ���ҽʦ  </TD>
          <TD  class= input> <Input class="common" name='PEDoctor' > </TD>-->        
          <TD  class= title5>  ���ʱ��  </TD>
          <!--<TD  class= input> <Input class="common" name='PEDate' > </TD> -->
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#PEDate'});" verify="���ʱ��|date" dateFormat="short" name=PEDate id="PEDate"><span class="icon"><a onClick="laydate({elem: '#PEDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>          
          <!--<TD  class= title>  ����ʱ��  </TD>
          <TD  class= input> <Input class="common" name='REPEDate' > </TD>
          <TD  class= input>
            <Input class="coolDatePicker"  dateFormat="short" name='REPEDate' verify="����ʱ��|date" >
          </TD>
          <TD  class= title>  ���Ա�� </TD>
          <TD nowrap class= input>
            <Input class="codeno" name=MasculineFlag verify="���Ա��|NOTNUlL&CODE:yesno"  verifyorder="1" ondblclick="return showCodeList('yesno',[this,MasculineName],[0,1]);" onkeyup="return showCodeListKey('yesno',[this,MasculineName],[0,1]);"><input class=codename name=MasculineName readonly=true elementtype=nacessary>
          </TD>
          -->
        </TR>
        <TR  class= title>
          <TD  class= title5>  �����  </TD>
          <TD  class= input5> <Input class="common wid" name=AccName id=AccName> </TD>
          <TD  class= title5>  ������  </TD>
          <TD  class= input5> <Input class="readonly wid" name=RePETag id=RePETag readonly = 'true'> </TD>
        	 
        </TR>
    </table>
    <table class=common>
         <TR  class= common>
           <TD  class= title5> ������ʷ </TD>
           <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=PastDiseaseCode id=PastDiseaseCode  ondblclick="return showCodeList('peitema',[this,PastDiseaseRes],[0,1]);" onclick="return showCodeList('peitema',[this,PastDiseaseRes],[0,1]);" onkeyup="return showCodeListKey('peitema',[this,PastDiseaseRes],[0,1]);"><input class=codename name=PastDiseaseRes id=PastDiseaseRes readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5></TD>
           <TD  class= input5></TD>
                    
         </TR>
         </table>
         </div>
         <table>
         <TR  class= common>
           <TD style="padding-left:16px"  class= common colspan=4>
             <textarea name="PastDiseaseDes" cols="188" rows="4" class="common" ></textarea>
           </TD>
         </TR>
      </table>    

    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg> ��ѡ�����Ŀ </td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanHealthGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
    <!--	
      <INPUT CLASS=cssButton VALUE="��  ҳ" class= cssButton type=hidden onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="β  ҳ" class= cssButton TYPE=button onclick="turnPage.lastPage();">
    -->  
      
    </Div>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec2);"></td>
    		<td class= titleImg> ��ѡ�����Ŀ </td>
    	</tr>
    </table>
    <Div  id= "divUWSpec2" style= "display: ''">
    <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanHealthOtherGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>

    </div>
   <!--
    <Div  id= "divOtherHealth" style= "display: ''"> 
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		  <td class= titleImg>	 ���������Ŀ¼��</td>                            
    	</tr>	
    </table>
   
      	<table  class= common>
       		 <tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				    <span id="spanOtherHealthGrid">
  				    </span> 
  		  	    </td>
  		     </tr>
    	</table>
   </div>
   -->
   <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOtherHealth);"></td>
    		  <td class= titleImg>	 ���������Ŀ</td>                            
    	</tr>	
    </table>
   <Div  id= "divOtherHealth" style= "display: ''" class="maxbox1"> 
    <table class=common>
       <tr class=common>
    	<TD style="padding-left:16px"  class= common colspan=4>
             <textarea name="OtherPEItem"  id="OtherPEItem" cols="188" rows="4" class="readonly common" readonly></textarea>
         </TD>
         </tr></table>
         <table class=common>
    	<tr class=common>
        	<td class= title5> ���������Ŀ����</td>  
        	<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherPEItemResCode id=OtherPEItemResCode  ondblclick="return showCodeList('npo',[this,OtherPEItemRes],[0,1]);" onclick="return showCodeList('npo',[this,OtherPEItemRes],[0,1]);" onkeyup="return showCodeListKey('npo',[this,OtherPEItemRes],[0,1]);"><input class=codename name=OtherPEItemRes id=OtherPEItemRes readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5></TD>
           <TD  class= input5></TD>                          
    	</tr>
    	</table>
        <table class=common>
    	<tr class=common>
        	<td class="title5"> ���������Ŀ���</td>                            
    	</tr>
    	<tr>

         <TD style="padding-left:16px"  class= common  colspan=4>
             <textarea name="OtherPEItemDes" cols="188" rows="4" class="common" ></textarea>
         </TD>
         </tr>
    </table>
   </div>
   <Div  id= "divDisDesb" style= "display: none">
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   �����</td>
    	</tr>
    </table>

    <Div  id= "divUWDis" style= "display: ''" class="maxbox1">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span>
  		  	</td>
  		</tr>
    	</table>
      </div>
   </Div>
    	<table class=common>
         <TR  class= common>
           <TD class="title5"> ������Ϣ </TD>
         </TR>
         <TR  class= common >
           <TD style="padding-left:16px"  class= common colspan=4>
             <textarea name="Note" cols="188" rows="4" class="common" class="readonly"></textarea>
           </TD>
         </TR></table>
         <table class=common>
         <TR  class= common>
           <TD  class= title5> ���ҽԺ���ս��� </TD>
           <TD  class=input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=PEResultCode id=PEResultCode  ondblclick="return showCodeList('peitema',[this,PEResult],[0,1]);" onclick="return showCodeList('peitema',[this,PEResult],[0,1]);" onkeyup="return showCodeListKey('peitema',[this,PEResult],[0,1]);"><input class=codename name=PEResult id=PEResult readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5> </TD>
           <TD  class= input5></TD>
         </TR></table>
         <table class=common>
         <TR  class= common>
           <TD style="padding-left:16px"  class= common colspan=4>
             <textarea name="PEResultDes" cols="188" rows="4" class="common" ></textarea>
           </TD>
         </TR>
      </table>
       <div id = "divHealthButton" style = "display:;"><br><br>
	<!--<input value="���������" class=cssButton type=button onclick="saveDisDesb();" >
	<input value="��  ��" class=cssButton type=button onclick="reset();initForm('<%=tContNo%>','<%=tEdorNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');" >-->
    <a href="javascript:void(0);" class="button" onClick="saveDisDesb();">���������</a>
    <a href="javascript:void(0);" class="button" onClick="reset();initForm('<%=tContNo%>','<%=tEdorNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');">��    ��</a>
</div>
    <!--��ȡ��Ϣ-->

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
