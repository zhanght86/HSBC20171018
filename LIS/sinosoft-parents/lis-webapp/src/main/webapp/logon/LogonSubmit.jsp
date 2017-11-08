<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<style type="text/css">
<!--
.style4 {color: #FF0000}
.style5 {
    font-weight: bold;
    font-size: 36px;
    color: #CC3333;
    /* font-family: "方正舒体", "华文隶书", "隶书";*/
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
// 程序名称：LogonSubmit.jsp
// 程序功能:：处理用户登录提交
// 最近更新人：DingZhong
// 最近更新日期：2002-10-15
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
//用户名称和密码
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

//加密
/*
 VData tPassVData = new VData();
 tPassVData.add(Password);
 if(!tBusinessDelegateNew.submitData(tPassVData,"encrypt","LisEncryptUI"))
 {
	 bSuccess = false;
	 sErrorMsg="加密出错";
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
	 sErrorMsg="验证码错误";
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
	//用户IP
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
	
	//2010-02-08 判断用户名是否一致的同时也需要判断机构是否一致
	String title = UseName + "您好，欢迎登录本系统！";
	String tOldOperator ="";
	String tOldManageCom = "";
	GlobalInput tGOld = new GlobalInput();
	loggerDebug("LogonSubmit","Old tOldOperator :"+tGOld+"new");
	try{
	tGOld=(GlobalInput)session.getValue("GI");
	loggerDebug("LogonSubmit","Old tOldOperator :"+tGOld+"aaaa");
	//2010-1-27 为避免第一次登陆时都会出现空指针情况 先判断tGOld是否为null
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
	    alert("用户'"+tOldO+"'机构'"+tOldManage+"'已经登录，请将该用户退出或是选用其他浏览器进行登录！");
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
    
    
    //进行解锁操作
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
            <td rowspan="2" align="center" valign="middle" bgcolor="#FFFFFF"><p class="style5">操作手册 </p></td>
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
            <!-- tongmeng 2007-09-03 modify 注释掉登陆后显示的文字
            <tr align="center">
              <td width="90" align="right" valign="top"><a href="../manual/Manual(nb).pdf" class="style4">新契约操作</a></td>
            </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(card).pdf" class="style4">单证操作</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(rn).pdf" class="style4">续期操作</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(pos).pdf" class="style4">保全操作</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(claim).pdf" class="style4">理赔操作</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(agent).pdf" class="style4">渠道操作</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(finance).pdf" class="style4">财务操作</a></td>
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
        
 <!-- sunsheng modify 2011-05-24 优化首页，显示用户权限菜单页面 
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
//tongmeng 增加注释 
//对于登陆后查询量比较频繁的可以放在此处，在登陆时就查询出来。
//暂时注释掉所有的。
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
       	//为了提高录单和问题件修改的效率
				showOneCodeName('occupationtype','VirtualCode','VirtualName');
				showOneCodeName('paymode','VirtualCode','VirtualName');
				showOneCodeName('continuepaymode','VirtualCode','VirtualName');
				showOneCodeName('incomeway','VirtualCode','VirtualName');
    }

</script>
