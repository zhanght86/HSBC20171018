<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<style type="text/css">
<!--
.style4 {color: #FF0000}
.style5 {
    font-weight: bold;
    font-size: 36px;
    color: #CC3333;
    /* font-family: "��������", "��������", "����";*/
}
-->
</style>
<script>
parent.fraChat.window.location.reload();
</script>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//******************************************************
// �������ƣ�LogonSubmit.jsp
// ������:�������û���¼�ύ
// ��������ˣ�DingZhong
// ����������ڣ�2002-10-15
//******************************************************//
%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.encrypt.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
loggerDebug("LogonSubmit","submit!!!!!!!!!!!!!!!!!!!!!");
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

BusinessDelegate tBusinessDelegateNew=BusinessDelegate.getBusinessDelegate();

boolean bSuccess = false;
String sErrorMsg = new String("");
//�û����ƺ�����
String UserCode = request.getParameter("UserCode");
String Password = request.getParameter("PWD");
String StationCode = request.getParameter("StationCode");
String ClientURL = request.getParameter("ClientURL");   //LQ 2004-04-19

String tNumCodeNo = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
String tNumCode = request.getParameter("NumCode");
try{
if(UserCode==null){
	throw new IllegalArgumentException("UserCode is null");
}
if(Password==null){
	throw new IllegalArgumentException("Password is null");
}

if( UserCode != null && !UserCode.equals("") ) {
  UserCode = UserCode.trim();
}

loggerDebug("LogonSubmit","************+" + UserCode + "+*********");

//����
/*
 VData tPassVData = new VData();
 tPassVData.add(Password);
 if(!tBusinessDelegateNew.submitData(tPassVData,"encrypt","LisEncryptUI"))
 {
	 bSuccess = false;
	 sErrorMsg="���ܳ���";
 }
 else
 {
	 Password = (String)tBusinessDelegateNew.getResult().get(0);
 }
*/
if (Password.length() == 0 || UserCode.length() == 0)
{
    bSuccess = false;
    sErrorMsg="Password is null";
}
/*else if(!tNumCodeNo.equals(tNumCode)){
	bSuccess = false;
	 sErrorMsg="��֤�����";
}*/
else
{
	VData tVData = new VData();
	LDUserSchema tLDUserSchema = new LDUserSchema();
	tLDUserSchema.setUserCode(UserCode);
	tLDUserSchema.setPassword(Password);
	tLDUserSchema.setComCode(StationCode);
	tVData.add(tLDUserSchema);

	bSuccess=tBusinessDelegate.submitData(tVData,"query","LDUserUI");
    
    if (!bSuccess)
    {
        sErrorMsg = tBusinessDelegate.getCErrors().getFirstError();
    }
}

}catch(Exception ex){
	bSuccess=false;
	sErrorMsg=String.valueOf(ex.getMessage());
}
if(bSuccess == true)
{
	//�û�IP
	String UseName = "";
	String Ip = request.getRemoteAddr();	
           		
	if(UserCode!=null && !UserCode.equals(""))
	{   	
       String searchSql ="select username from lduser where usercode='"+UserCode+"'";
       TransferData tTransferData=new TransferData();
       tTransferData.setNameAndValue("SQL", searchSql);
       VData tVData = new VData();
       tVData.add(tTransferData);
      
       if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
       {
           UseName=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
       }	
			 if(UseName == null)
			 {
			    UseName = "";
		   }
		String tUpSql = "Update LDUser set EdorValiFlag = '0' where usercode = '"+UserCode+"'";
		tTransferData=new TransferData();
	    tTransferData.setNameAndValue("SQL", tUpSql);
	    tVData = new VData();
	    tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, "execUpdateSQL", "ExeSQLUI");
		
		if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
			sqlbv2.sql(tSql2);
			sqlbv2.put("operateType", "UPDATE");
			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
			sqlbv2.put("userCode", UserCode);
			String ipIdSql ="select USER() from dual";
		    tTransferData=new TransferData();
		    tTransferData.setNameAndValue("SQL", ipIdSql);
		    tVData = new VData();
		    tVData.add(tTransferData);
		    String ipAndId = "";
		    if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI")){
		    	ipAndId = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
		    }
			String[] ipId = ipAndId.split("@");
			sqlbv2.put("ip", ipId[1]);
			sqlbv2.put("id", ipId[0]);
			
			tTransferData=new TransferData();
		    tTransferData.setNameAndValue("SQL", sqlbv2);
		    tVData = new VData();
		    tVData.add(tTransferData);
		    tBusinessDelegate.submitData(tVData, "execUpdateSQL", "ExeSQLUI");
		}
	 }
	
	//2010-02-08 �ж��û����Ƿ�һ�µ�ͬʱҲ��Ҫ�жϻ����Ƿ�һ��
	String title = UseName + "���ã���ӭ��¼��ϵͳ��";
	String tOldOperator ="";
	String tOldManageCom = "";
	GlobalInput tGOld = new GlobalInput();
	loggerDebug("LogonSubmit","Old tOldOperator :"+tGOld+"new");
	try{
	tGOld=(GlobalInput)session.getValue("GI");
	loggerDebug("LogonSubmit","Old tOldOperator :"+tGOld+"aaaa");
	//2010-1-27 Ϊ�����һ�ε�½ʱ������ֿ�ָ����� ���ж�tGOld�Ƿ�Ϊnull
	if(tGOld!=null){
		loggerDebug("LogonSubmit","Old Session :"+tGOld.Operator);
		loggerDebug("LogonSubmit","Old Session :"+tGOld.ComCode);
		tOldOperator=tGOld.Operator;
		tOldManageCom=tGOld.ManageCom;
		loggerDebug("LogonSubmit","Old tOldOperator :"+tOldOperator+"aaaa");
	}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	loggerDebug("LogonSubmit","Old tOldOperator :"+tOldOperator+"aaaabbbb");
	if((!"".equals(tOldOperator)&&!UserCode.equals(tOldOperator))||(!"".equals(tOldManageCom)&&!StationCode.equals(tOldManageCom))){
	    //session.putValue("GI",null);
	    %>
	    <script language=javascript>
	    var tOldO ="<%=tOldOperator%>";
	    var tOldManage ="<%=tOldManageCom%>";
	    alert("�û�'"+tOldO+"'����'"+tOldManage+"'�Ѿ���¼���뽫���û��˳�����ѡ��������������е�¼��");
	    //parent.window.close();
	    parent.window.location ="../indexlis.jsp";
	    </script>
	    <%
	}else{
	GlobalInput tG = new GlobalInput();
	tG.Operator = UserCode;
	tG.ComCode  = StationCode;
	tG.ManageCom = StationCode;
	Locale locale = Locale.getDefault();
	tG.locale=Locale.SIMPLIFIED_CHINESE;
	session.putValue("GI",tG);
	session.putValue("locale",Locale.SIMPLIFIED_CHINESE);
	session.putValue("ClientURL",ClientURL);    //LQ 2004-04-19
	GlobalInput tG1 = new GlobalInput();
	tG1=(GlobalInput)session.getValue("GI");
	loggerDebug("LogonSubmit","Current Operate is "+tG1.Operator);
	loggerDebug("LogonSubmit","Current ComCode is "+tG1.ComCode);

	LDUserLogSchema tLDUserLogSchema=new LDUserLogSchema();

	tLDUserLogSchema.setManageCom(StationCode);
	tLDUserLogSchema.setOperator(UserCode);
	tLDUserLogSchema.setCientIP(Ip);
	tLDUserLogSchema.setMakeDate(PubFun.getCurrentDate());
	tLDUserLogSchema.setMakeTime(PubFun.getCurrentTime());

	VData inputData = new VData();
	inputData.addElement(tG1);
	inputData.addElement(tLDUserLogSchema);
	tBusinessDelegate.submitData(inputData,"LogOutProcess","logoutUI");    
    
    
    //���н�������
  	//loggerDebug("LogonSubmit","start unlock operate...");
    //VData inputData = new VData();
    //inputData.addElement(tG1);
    //logoutUI tlogoutUI = new logoutUI();
    //tlogoutUI.submitData(inputData,"LogOutProcess");
    //loggerDebug("LogonSubmit","completed clear data");
%>
<script language=javascript>
parent.fraLeftColor.window.location="./LeftColor.jsp";
//parent.fraRightColor.window.location="./RightColor.jsp";
if(parent.fraSetColor.cols=="0,*"){
	parent.fraSetColor.cols = "8,*";
}

	parent.head.window.location="./headNew.jsp?userCode=<%=UserCode%>";
if(parent.fraMain.rows == "0,0,0,0,0,*")
    parent.fraTitle.showTitle();
if(parent.fraSet.cols== "0%,*,0%,0%") {
    parent.fraTitle.showHideFrame();
 }
parent.fraMenu.window.location="./menu2New.jsp?userCode=<%=UserCode%>&Ip=<%=Ip%>";
parent.fraTalkCol.cols = "10,*";
parent.fraChatMain.fraChat.window.location.reload();
parent.fraQuick.rows = "25,*";

</script>
<%
	}
}
else
{
	
%>
<script language=javascript>
alert("<%=PubFun.changForJavaScript(sErrorMsg)%>");
parent.window.location ="../indexlis.jsp";
</script>
<%
}
	
%>

<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<script src="../common/javascript/EasyQuery.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>

<body onload="initName()">
    <div style="display:none">
        <table width="100%" height="242"  border="0">
          <tr>
            <td width="23%">&nbsp;</td>
            <td width="59%">&nbsp;</td>
            <td width="18%">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td rowspan="2" align="center" valign="middle" bgcolor="#FFFFFF"><p class="style5">�����ֲ� </p></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td rowspan="6">
            <table width="433">
            <!-- tongmeng 2007-09-03 modify ע�͵���½����ʾ������
            <tr align="center">
              <td width="90" align="right" valign="top"><a href="../manual/Manual(nb).pdf" class="style4">����Լ����</a></td>
            </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(card).pdf" class="style4">��֤����</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(rn).pdf" class="style4">���ڲ���</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(pos).pdf" class="style4">��ȫ����</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(claim).pdf" class="style4">�������</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(agent).pdf" class="style4">��������</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(finance).pdf" class="style4">�������</a></td>
                </tr>-->
            </table>
            </td>
            <td>&nbsp;</td>
          </tr>
     <!--   <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        -->   
        </table>`
        </div>
        
 <!-- sunsheng modify 2011-05-24 �Ż���ҳ����ʾ�û�Ȩ�޲˵�ҳ�� 
    <div>
        <center><iframe src="../usermission/UserMission.jsp" frameborder="no" scrolling="no" height="100%" width="100%" align="middle"></iframe></center>
    </div>
  
 --> 
<script language=javascript>
		parent.fraInterface.window.location="../usermission/UserMission.jsp?userCode=<%=UserCode%>&Ip=";
		
		parent.fraMain.rows = "0,0,0,0,70,*";
</script>

<form name="fm">
    <input type="hidden" name="VirtualCode" value="0" />
    <input type="hidden" name="VirtualName" value="" />

</form>
</body>

<script language="javascript">
//tongmeng ����ע�� 
//���ڵ�½���ѯ���Ƚ�Ƶ���Ŀ��Է��ڴ˴����ڵ�½ʱ�Ͳ�ѯ������
//��ʱע�͵����еġ�
    function initName()
    {	
    	
        //showOneCodeName('province','VirtualCode','VirtualName');
        //showOneCodeName('city','VirtualCode','VirtualName');
        //showOneCodeName('district','VirtualCode','VirtualName');
        //showOneCodeName('incomeway','VirtualCode','VirtualName');
        showOneCodeName('comcode','VirtualCode','VirtualName');
        showOneCodeName('sellType','VirtualCode','VirtualName');
        //showOneCodeName('vipvalue','VirtualCode','VirtualName');
        showOneCodeName('IDType','VirtualCode','VirtualName');
        showOneCodeName('Sex','VirtualCode','VirtualName');
        showOneCodeName('Marriage','VirtualCode','VirtualName');
        showOneCodeName('NativePlace','VirtualCode','VirtualName');
        //showOneCodeName('OccupationCode','VirtualCode','VirtualName');
        showOneCodeName('LicenseType','VirtualCode','VirtualName');
        showOneCodeName('paymode','VirtualCode','VirtualName');
        //showOneCodeName('continuepaymode','VirtualCode','VirtualName');
        //showOneCodeName('bank','VirtualCode','VirtualName');
        showOneCodeName('SequenceNo','VirtualCode','VirtualName');
        showOneCodeName('vipvalue','VirtualCode','VirtualName');
        showOneCodeName('Relation','VirtualCode','VirtualName');
        
        //tongmeng 2008-01-03 add
       	//Ϊ�����¼����������޸ĵ�Ч��
				showOneCodeName('occupationtype','VirtualCode','VirtualName');
				showOneCodeName('paymode','VirtualCode','VirtualName');
				showOneCodeName('continuepaymode','VirtualCode','VirtualName');
				showOneCodeName('incomeway','VirtualCode','VirtualName');
    }

</script>
