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
  String tActivityID ="";
  String ClmNo="";
  String BatNo ="";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  String time = PubFun.getCurrentTime();
  
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tPrtSeq = request.getParameter("PrtSeq");
  tCustomerNo = request.getParameter("CustomerNo");
  tActivityID = request.getParameter("ActivityID");
  tFlag = request.getParameter("Flag");
  ClmNo = request.getParameter("ClmNo");
  BatNo = request.getParameter("BatNo");
 %>                         

<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!-- <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> -->
  <script src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./LLManuHealthQ.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type='text/css'>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type='text/css'>
  <%@include file="LLManuHealthQInit.jsp"%>
  <title> �б��������¼�� </title>

</head>
<script language="JavaScript">
var tFlag = "<%=tFlag%>";
var tday = "<%=tday%>";
var time = "<%=time%>";
var tActivityID = "<%=tActivityID%>";
var ClmNo = "<%=ClmNo%>";
var BatNo = "<%=BatNo%>";

function initPage()
{

       initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');
       fm.ActivityID.value = tActivityID;
       fm.ClmNo.value = ClmNo;
       fm.BatNo.value = BatNo;
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
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWManuHealthChk.jsp">
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
    <div id ="divOperation" style = "display : none">
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=input><Input  class="readonly" id="Operator" name= 'Operator' readonly></td>
          <td class=title>����ʱ��</td><td class=input><Input  class="readonly" id="MakeDate" name= 'MakeDate' readonly></td>
       </tr>
        <tr  class= common>
          <td class=title>�ظ�ʱ��</td><td class=input><Input  class="readonly" id="ReplyDate" name= 'ReplyDate' readonly></td>
       </tr>
    </table>
    </div>
    <div class="maxbox">
    <table class=common>
        <TR  class= title>


          <Input type= "hidden" class= Common id="ContNo" name= 'ContNo' value= "">
         <!--  <Input type= "hidden" class= Common name= 'PrtNo' value= "">-->
          <INPUT  type= "hidden" class= Common id="MissionID" name= 'MissionID' value= "">
          <INPUT  type= "hidden" class= Common id="SubMissionID" name= 'SubMissionID' value= "">
          <INPUT  type= "hidden" class= Common id="PrtSeq" name= 'PrtSeq' value= "">
          <INPUT  type= "hidden" class= Common id="CustomerNo" name= 'CustomerNo' value= "">
          <INPUT  type= "hidden" class= Common id="ManageCom" name= 'ManageCom' value= "">
          <INPUT  type= "hidden" class= Common id="ItemNum" name= 'ItemNum' value= "">          
          <INPUT  type= "hidden" class= Common id="ActivityID" name= 'ActivityID' value= "">
          <INPUT  type= "hidden" class= Common id="ClmNo" name= 'ClmNo' value= "">
          <INPUT  type= "hidden" class= Common id="BatNo" name= 'BatNo' value= "">
          <TD  class= title5>  ӡˢ��  </TD>
          <TD  class= input5> <Input class="readonly wid" id="PrtNo" name='PrtNo' readonly = 'true'> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input class="readonly wid" id="PEName" name='PEName'  readonly = 'true'> </TD>
        </TR>
        <TR  class= title5>
          <TD  class= title5>  ������Ա�  </TD>
          <TD  class= input5> <Input class="readonly wid" id="Sex" name='Sex'  readonly = 'true'> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input class="readonly wid" id="Age" name='Age'  readonly = 'true'> </TD>
        </TR>
        <TR  class= title5>
          <TD  class= title5>  ���ҽԺ  </TD>
          <TD  class= input5> <Input class="codeno wid" id="PEHospital" name=PEHospital style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('pehospital',[this,PEAddress],[0,1]);" onkeyup="return showCodeListKey('pehospital',[this,PEAddress],[0,1]);"><input class=codename id="PEAddress" name=PEAddress readonly=true elementtype=nacessary></TD>
         <!--   <TD  class= title>  ���ҽʦ  </TD>
          <TD  class= input> <Input class="common" name='PEDoctor' > </TD>-->        
          <TD  class= title5>  ���ʱ��  </TD>
          <!--<TD  class= input> <Input class="common" name='PEDate' > </TD> -->
          <TD  class= input5>
            <!-- <Input class="coolDatePicker"  dateFormat="short" id="PEDate" name='PEDate' verify="���ʱ��|date" > -->
            <Input id="PEDate" name='PEDate' class="coolDatePicker" onClick="laydate({elem: '#PEDate'});" verify="���ʱ��|date" dateFormat="short" ><span class="icon"><a onClick="laydate({elem: '#PEDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
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
        <TR  class= title5>
          <TD  class= title5>  �����  </TD>
          <TD  class= input5> <Input class="common wid" id="AccName" name=AccName> </TD>
          <TD  class= title5>  ������  </TD>
          <TD  class= input5> <Input class="readonly wid" id="RePETag" name=RePETag readonly = 'true'> </TD>
        	 
        </TR>
    </table>
    </div>
    <table class=common>
         <TR  class= common>
           <TD  class= title5> ������ʷ</TD>
           <TD  class= input5> <Input class="codeno" id="PastDiseaseCode" name=PastDiseaseCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('peitema',[this,PastDiseaseRes],[0,1]);" onkeyup="return showCodeListKey('peitema',[this,PastDiseaseRes],[0,1]);"><input class=codename id="PastDiseaseRes" name=PastDiseaseRes readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5> <Input type= "hidden" class= Common value= ""> </TD>
           <TD  class= input5> <Input type= "hidden" class= Common value= ""> </TD>           
         </TR>
         <TR  class= common>
           <TD  class= common colspan=4>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="PastDiseaseDes" cols="120" rows="3" class="common" ></textarea>
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
      <Div  id = "divPage" align=center style = "display: none ">
        <INPUT VALUE="��ҳ" class= cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="turnPage.previousPage();"> 
        <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="βҳ" class= cssButton93 TYPE=button onclick="turnPage.lastPage();">
    </Div>
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
   <Div  id= "divOtherHealth" class="maxbox" style= "display: ''"> 
    <table class=common>
       <tr>
    	<TD  class= common colspan=4>
             <textarea name="OtherPEItem" cols="120" rows="3" class="readonly common" readonly></textarea>
         </TD>
         </tr>
    	<tr class= title>
        	<td class= title5> ���������Ŀ����</td>  
        	<TD  class= input5> <Input class="codeno" id="OtherPEItemResCode" name=OtherPEItemResCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('npo',[this,OtherPEItemRes],[0,1]);" onkeyup="return showCodeListKey('npo',[this,OtherPEItemRes],[0,1]);"><input class=codename id="OtherPEItemRes" name=OtherPEItemRes readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title> <Input type= "hidden" class= Common> </TD>
           <TD  class= input> <Input type= "hidden" class= Common> </TD>                          
    	</tr>
    	
         <tr>
        	<td class=common  colspan=4> ���������Ŀ���</td>                            
    	</tr>
    	<tr>
         <TD  class= common  colspan=4>
             <textarea name="OtherPEItemDes" cols="120" rows="3" class="common" ></textarea>
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

    <Div  id= "divUWDis" style= "display: ''">
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
           <TD  class= common colspan=4> ������Ϣ </TD>
         </TR>
         <TR  class= common >
           <TD  class= common colspan=4>
             <textarea name="Note" cols="120" rows="3" class="common" class="readonly"></textarea>
           </TD>
         </TR>
         <TR  class= common>
           <TD  class= title5> ���ҽԺ���ս��� </TD>
           <TD  class= input5> <Input class="codeno" id="PEResultCode" name=PEResultCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('peitema',[this,PEResult],[0,1]);" onkeyup="return showCodeListKey('peitema',[this,PEResult],[0,1]);"><input class=codename id="PEResult" name=PEResult readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title> <Input type= "hidden" class= Common> </TD>
           <TD  class= input> <Input type= "hidden" class= Common> </TD>
         </TR>
         <TR  class= common>
           <TD  class= common colspan=4>
             <textarea name="PEResultDes" cols="120" rows="3" class="common" ></textarea>
           </TD>
         </TR>
      </table>
       <div id = "divHealthButton" style = "display:;">
	<input value="���������" class=cssButton type=button onclick="saveDisDesb();" >
	<input value="��  ��" class=cssButton type=button onclick="reset();initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>');" >
</div>
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
