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
  String tUWFlag = "";
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

  tFlag = request.getParameter("Flag");
  tUWFlag = request.getParameter("UWFlag");
  String tActivityID = "";
  tActivityID = request.getParameter("ActivityID");//add lzf 2013-03-22
  System.out.println("tActivityID---->"+tActivityID);

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
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./UWManuHealthQ.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type='text/css'>
  <LINK href="../common/css/Project3.css" rel=stylesheet type='text/css'>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type='text/css'>
  <%@include file="UWManuHealthQInit.jsp"%>
  <title> �б��������¼�� </title>

</head>
<script language="JavaScript">
var tFlag = "<%=tFlag%>";
var tday = "<%=tday%>";
var time = "<%=time%>";
var tActivityID="<%=tActivityID%>";
function initPage()
{
       initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>','<%=tUWFlag%>');
       fm.ActivityID.value = tActivityID;
}

function operatorQuery()
{
		var prtSeq = fm.PrtSeq.value;
		var CustomerNo = fm.CustomerNo.value;
		var arrReturn;

		    var sqlid1="UWManuHealthQSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("uw.UWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1); //ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(prtSeq); //ָ������Ĳ���
			mySql1.addSubPara(CustomerNo); //ָ������Ĳ���
			var strSQL =mySql1.getString();	
		
		//var strSQL = "select Operator,MakeDate,ModifyTime from LCPENotice where PrtSeq = '"+prtSeq+"' and CustomerNo='"+CustomerNo+"'";
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
  <form method=post name=fm id="fm" target="fraSubmit" action= "./UWManuHealthChk.jsp">
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
          <td class=title5>������</td><td class=input5><Input  class="readonly wid" name= 'Operator' id="Operator" readonly></td>
          <td class=title5>����ʱ��</td><td class=input5><Input  class="readonly wid" name= 'MakeDate' id="MakeDate" readonly></td>
       </tr>
       <tr  class= common>
          <td class=title5>�ظ�ʱ��</td><td class=input5><Input  class="readonly wid" name= 'ReplyDate' id="ReplyDate" readonly></td>
          <td class=title5></td><td class=input5></td>
       </tr>
    </table>
    </div>
    <div class="maxbox">
    <table class=common>
        <TR  class= title>
          <Input type= "hidden" class= Common id="ContNo" name= "ContNo" value= "">
         <!--  <Input type= "hidden" class= Common name= "PrtNo" value= "">-->
          <INPUT  type= "hidden" class= Common id="MissionID"  name= "MissionID" value= "">
          <INPUT  type= "hidden" class= Common id="SubMissionID" name= "SubMissionID" value= "">
          <INPUT  type= "hidden" class= Common id="PrtSeq" name= "PrtSeq" value= "">
          <INPUT  type= "hidden" class= Common id="CustomerNo" name= "CustomerNo" value= "">
          <INPUT  type= "hidden" class= Common id="ManageCom" name= "ManageCom" value= "">
          <INPUT  type= "hidden" class= Common id="ItemNum" name= "ItemNum" value= ""> 
          <INPUT  type= "hidden" class= Common id="UWFlag" name= "UWFlag" value= ""> 
          <INPUT  type= "hidden" class= Common id="ActivityID" name= "ActivityID" value= ""> 
          <TD  class= title5>  ӡˢ��  </TD>
          <TD  class= input5> <Input class="common wid"  name="PrtNo" id="PrtNo" readonly = "true"> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input class="common wid"  name="PEName" id="PEName"  readonly = "true"> </TD>
        </TR>
        <TR  class= title>
          <TD  class= title5>  ������Ա�  </TD>
          <TD  class= input5> <Input class="common wid" name="Sex" id="Sex"  readonly = "true"> </TD>
          <TD  class= title5>  ���������  </TD>
          <TD  class= input5> <Input  class="common wid" name="Age" id="Age"  readonly = "true"> </TD>
        </TR>
        <TR  class= title>
          <TD  class= title5>  ���ҽԺ  </TD>
          <TD  class= input5> <Input class="codeno" name=PEHospital id="PEHospital" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="���ҽԺ����|CODE:pehospital" onClick="return showCodeList('pehospital',[this,PEAddress],[0,1],null,null,null,1);" onDblClick="return showCodeList('pehospital',[this,PEAddress],[0,1],null,null,null,1);" onKeyUp="return showCodeListKey('pehospital',[this,PEAddress],[0,1],null,null,null,1);"><input class=codename name=PEAddress id="PEAddress" elementtype=nacessary></TD>
         <!--   <TD  class= title>  ���ҽʦ  </TD>
          <TD  class= input> <Input class="common" name='PEDoctor' > </TD>-->        
          <TD  class= title5>  ���ʱ��  </TD>
          <!--<TD  class= input> <Input class="common" name='PEDate' > </TD> -->
          <TD  class= input5>
            <Input class="coolDatePicker"  dateFormat="short" id="PEDate" name='PEDate' verify="���ʱ��|date" id="PEDate" onClick="laydate({elem:'#PEDate'});"><span class="icon"><a onClick="laydate({elem: '#PEDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
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
          <TD  class= input5> <Input class="common wid" name=AccName id="AccName"> </TD>
          <TD  class= title5>  ������  </TD>
          <TD  class= input5> <Input class="readonly wid" name=RePETag id="RePETag" readonly = 'true'> </TD>
        	 
        </TR>
    </table>
    <table class=common>
         <TR  class= common>
           <TD  class= title5> ������ʷ </TD>
           <TD  class= input5> <Input class="codeno" name=PastDiseaseCode id="PastDiseaseCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('peitema',[this,PastDiseaseRes],[0,1]);"  ondblclick="return showCodeList('peitema',[this,PastDiseaseRes],[0,1]);" onKeyUp="return showCodeListKey('peitema',[this,PastDiseaseRes],[0,1]);"><input class=codename name=PastDiseaseRes id="PastDiseaseRes" readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5> <Input type= "hidden" class= Common value= ""> </TD>
           <TD  class= input5> <Input type= "hidden" class= Common value= ""> </TD>           
         </TR>
         <TR  class= common>
           <TD  class= title colspan=4>
             <textarea name="PastDiseaseDes" id=PastDiseaseDes cols="120" rows="3" class= common ></textarea>
           </TD>
         </TR>
      </table>    
</div>
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
      <Div  id = "divPage" align=center style = "display:none ">
        <INPUT VALUE="��ҳ" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
        <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="turnPage.previousPage();"> 
        <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
        <INPUT VALUE="βҳ" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">
    </Div>
    </Div>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec2);"></td>
    		<td class= titleImg> ��ѡ�����Ŀ </td>
    	</tr>
    </table>
    <Div  id= "divUWSpec2" style= "display: ">
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
   <Div  id= "divOtherHealth" class="maxbox1" style= "display: "> 
    <table class=common>
       <tr>
    	<TD  class= title colspan=4>
             <textarea name="OtherPEItem" cols="120" rows="3" class="readonly common" readonly></textarea>
         </TD>
         </tr>
    	<tr class= title>
        	<td class= title5> ���������Ŀ����</td>  
        	<TD  class= input5> <Input class="codeno" name=OtherPEItemResCode id="OtherPEItemResCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('npo',[this,OtherPEItemRes],[0,1]);"  ondblclick="return showCodeList('npo',[this,OtherPEItemRes],[0,1]);" onKeyUp="return showCodeListKey('npo',[this,OtherPEItemRes],[0,1]);"><input class=codename id="OtherPEItemRes" name=OtherPEItemRes readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5> <Input type= "hidden" class= Common> </TD>
           <TD  class= input5> <Input type= "hidden" class= Common> </TD>                          
    	</tr>
    	
         <tr>
        	<td class=title  colspan=4> ���������Ŀ���</td>                            
    	</tr>
    	<tr>
         <TD  class= title  colspan=4>
             <textarea name="OtherPEItemDes" id=OtherPEItemDes cols="120" rows="3" class= common ></textarea>
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

    <Div  id= "divUWDis" style= "display:">
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
           <TD  class= title colspan=4> ������Ϣ </TD>
         </TR>
         <TR  class= common >
           <TD  class= title colspan=4>
             <textarea name="Note" id=Note cols="120" rows="3"  class="readonly common"></textarea>
           </TD>
         </TR>
         <TR  class= common>
           <TD  class= title5> ���ҽԺ���ս��� </TD>
           <TD  class= input5> <Input class="codeno" name=PEResultCode id="PEResultCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('peitema',[this,PEResult],[0,1]);"  ondblclick="return showCodeList('peitema',[this,PEResult],[0,1]);" onKeyUp="return showCodeListKey('peitema',[this,PEResult],[0,1]);"><input class=codename name=PEResult id="PEResult" readonly=true elementtype=nacessary> 
           </TD>
           <TD  class= title5> <Input type= "hidden" class= Common> </TD>
           <TD  class= input5> <Input type= "hidden" class= Common> </TD>
         </TR>
         <TR  class= common>
           <TD  class= title colspan=4>
             <textarea name="PEResultDes" id=PEResultDes class="common" cols="120" rows="3"  ></textarea>
           </TD>
         </TR>
      </table>
       <div id = "divHealthButton" style = "display:;">
	<input value="���������" class=cssButton type=button onClick="saveDisDesb();" >
	<input value="��  ��" class=cssButton type=button onClick="reset();initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tPrtSeq%>','<%=tCustomerNo%>','<%=tUWFlag%>');" >
</div>
    <!--��ȡ��Ϣ-->
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
