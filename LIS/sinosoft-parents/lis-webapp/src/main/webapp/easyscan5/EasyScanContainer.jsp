<%@ page language="java" contentType="text/html; charset=GBK"  isELIgnored="false" pageEncoding="GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>EasyScan ������</title>

<!--��ɫ����Ҫ��̨������ɫ#8CAAE7; -->
<style type="text/css">
body
{
    margin: 0px;
	padding: 0px;
	border: 1px solid #8CAAE7;
	clear: none;
	font-size:12px;
}
a:visited,a{color:blue;}
<%
  GlobalInput tG1 = (GlobalInput)session.getValue("GI");
  String clientURL=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
  System.out.println("getcentersetting------------id1="+session.getId());
  String channel = request.getParameter("channel");
  String channelName = request.getParameter("channelName"); 
%>
</style>


</head>
<body >

	<div id="installInfo" style="display:none">
	<h1>����ɨ��ͻ���δ��װ��δ��ȷ���á�</h1>
	�����δ��װ����<a href="" onclick="javascript:alert('����Ա��δ�������ص�ַ,��֪ͨ����Ա��������');return false;">�������</a>����װ����װ��Ϻ������<a href="">�����ֲ�</a>�������������ȷ���á�
</div>
</body>
</html>


<!--���÷�����������Ҫ����ʵ������޸Ĵ������-->
<script language="javascript" type="text/javascript">


 var activex =null;
    try { 
        activex = new ActiveXObject("EasyScan.ActiveControl");
//        if (activex.RuntimeVersion<'2.0.0.0'){
//            //ת����װ����FWLinkҳ�档
//        }
 
        activex.clear(); //������в���
        activex.SystemId="";
        activex.ServerId="";
        activex.ServerUri = "<%=clientURL%>" + "easyscan5/EasyScanAdapter.jsp";
 		activex.ClientId = "";
        activex.ManageCom = "<%= tG1.ManageCom%>";
        activex.UserCode = "<%=tG1.Operator%>";               
        activex.Channel = "1";
        activex.Module = "0";  
        activex.InitParams = "json={'isUseBoxno':'true','isUseScanOrder':'true'}";

        activex.SourceMode = "Form"; //ָ��Ϊ����ģʽ      
 		activex.UpdateMode = "";
        activex.AutoLoad = false; //����Ϊ�ֶ����£�����load�ֶ����¡�
        activex.AutoUpdate = true;
        activex.AutoSettings = true;
        activex.AutoFeedback = false;
 
        //ִ���¼�����
        //dispEvent();
 
        //��ʼ�����
        activex.load(); //��ʼ����
 
    }
    catch (ex) {
        alert("EasyScanӰ��ϵͳ����ʧ�ܣ������°�װ,������Ϣ:\r\n" + ex.message);
				document.getElementById("installInfo").style.display="block";
    }
    finally {
        if(activex != null){
	 		 		activex.free();
          activex = null;
				}
    }   
</script>