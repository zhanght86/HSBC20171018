<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--
*******************************************************
* �������ƣ�Menus.jsp
* �����ܣ��û���¼��Ϣ����ҳ��
* �������ڣ�2002-08-11
* ���¼�¼��  ������	��������	����ԭ��/����
*             �����	2002-08-11	�½�
              Dingzhong	2002-10-17	�޸�
              �����	2004-1-30	�޸�
*******************************************************
-->
<%@page import="com.sinosoft.utility.StrTool"%>
<%@page import="com.sinosoft.utility.SysConst"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.logon.MenuShow"%>
<%@page import="com.sinosoft.lis.schema.LDUserSchema"%>
<%@page import="com.sinosoft.lis.menumang.LDMenuQueryUI"%>
<script LANGUAGE="JavaScript">
defaultStatus="��ӭʹ�ñ�ϵͳ";
var ls="";
function CreateMenuMain(MNm,No)
{
	var i;
	for(i=1;i<No+1;i++)
	{
		WMnu=MNm+eval(i);
		var y="";	//��������ֵ
		var z=3;	//0����ֵ���ᵼ��һЩ���ò���
		var ls_WMnu = WMnu;
		//��������ѭ����ʽ����������߲˵�������Ч��
		while(ls_WMnu.indexOf("_")!=-1)
		{
			//���ø�ʽ����"_"�����Ӳ˵�
			ls_WMnu=ls_WMnu.slice(ls_WMnu.indexOf("_")+1);
			y += "&nbsp;&nbsp;";
			z += 1;
		}
		var nodecode = eval(WMnu+"[16]");	//�ڵ����
		var nodesrc = eval(WMnu+"[1]");	//�ڵ�����
		var nodename = eval(WMnu+"[0]");	//�ڵ���
		NOs = eval(WMnu+"[3]");	//�ڵ��ӽڵ����
		if(NOs > 0)
		{
			ls_string = "<table><tr><td class='menu"+z+"' onclick='showMenu("+WMnu+"s);'>"+y+"<img src='../common/images/butCollapse.gif' style='cursor:hand;' id='"+WMnu+"i'><img src='../common/images/menu"+z+".gif'>&nbsp;";
			if(nodesrc!="")
			{
				ls_string += "<a onmousedown=this.className='down' onmouseout='shiftMouseOut()' onmouseover=this.className='over' onclick=changeframe('"+nodecode+"','"+nodesrc+"') style='cursor:hand'>"+nodename+"</a></td></tr></table><div id='"+WMnu+"s' style='display:none'>";
			}
			else
			{
				ls_string += nodename + "</td></tr></table><div id='"+WMnu+"s' style='display:none'>";
			}
			document.write(ls_string);
			ls_string += CreateMenuMain(WMnu+"_",NOs);
			document.write("</div>");
		}
		else
		{
			ls_string = "<table><tr><td class='menu"+z+"'>"+y+"<img src='../common/images/butNull.gif' id='"+WMnu+"i'><img src='../common/images/menu"+z+".gif'>&nbsp;";
			if(nodesrc!="")
			{
				ls_string += "<a onmousedown=this.className='down' onmouseout='shiftMouseOut()' onmouseover=this.className='over' onclick=changeframe('"+nodecode+"','"+nodesrc+"') style='cursor:hand'>"+nodename+"</a></td></tr></table>";
			}
			else
			{
				ls_string += nodename + "</td></tr></table>";
			}
			document.write(ls_string);
		}
	}
	return(ls_string);
	document.close();
}
var MenuHeight=20;
//var MenuHeightChild=20;
//var MenuHeightBottom=20;

//�˴����������û��뿪ʱ���ٸ��û��ڷ������˵�״̬

function OnCloseWindow()
{
window.open("./close.jsp", "PopupWindow", "top=10000, left=10000, menubar=0, toolbar=0, location=0, directories=0, status=0, scrollbars=0, resizable=0, fullscreen=0");
}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<LINK href="../common/css/otherM.css" rel=stylesheet type=text/css>
<base target="fraInterface">
</head>
<body bgcolor="#EFF1FA" leftmargin="0" topmargin="0" onunload = "OnCloseWindow();">
</body>
<%




//loggerDebug("menu","start menu get ...");
int i = 0;
//Vector vector = null;	//װ�ش����ѯ����Ķ�����������
//int intPosition=0;
//int intFieldNum=5;
int[] menuCount = new int[1];
menuCount[0] = 0;
int node_count = 0;
String[][] node = null;	//����һ����ά����
//String sl = "";
VData tData=new VData();
LDUserSchema tLDUserSchema = new LDUserSchema();
LDMenuQueryUI tLDMenuQueryUI = new LDMenuQueryUI();
MenuShow menuShow = new MenuShow();

String userCode = request.getParameter("userCode");
String Ip = request.getParameter("Ip");
tLDUserSchema.setUserCode(userCode);

if ( userCode.compareTo("001") != 0) {
	tData.add(tLDUserSchema);
}

//�������ɲ˵�����
tLDMenuQueryUI.submitData(tData,"query");

StringBuffer menustr = null;
if (tLDMenuQueryUI.mErrors.needDealError())
{
//	loggerDebug("menu",tLDMenuQueryUI.mErrors.getFirstError()) ;
	out.println("<script>");

	int totalMenu = 2;
	out.println("var NoOffFirstLineMenus=" + totalMenu + ";");

	menuCount[0]++;
	menustr = new StringBuffer(103);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('���µ�¼','../logon/logout.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','');");
	out.println(menustr.toString());

	menuCount[0]++;
	menustr = new StringBuffer(114);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('�����޸�','../changePwd/PwdInput.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','7777');");
	out.println(menustr.toString());

	out.println("parent.fraQuick.window.location = '../logon/station.jsp';");
	out.println("</script>");
}
else{
//	tData.clear() ;
//	tData = tLDMenuQueryUI.getResult();
	node_count = tLDMenuQueryUI.getResultNum();
	node = new String[node_count][5];

//	String tStr="";
//	tStr = tLDMenuQueryUI.getResultStr();

	StringBuffer tStr = new StringBuffer(tLDMenuQueryUI.getResultStr());
//	sl = tStr;
//	sl += SysConst.RECORDSPLITER;
	tStr.append(SysConst.RECORDSPLITER);

	StringBuffer str = null;
	//����������ȷ���ַ������������¸�ֵ
	for (i=0 ; i< node_count ; i++) {
		str = new StringBuffer(256);
		str.append(StrTool.decodeStr(tStr.toString(),SysConst.RECORDSPLITER,i+1));
		for (int j = 0; j < 5; j++){
//			str += "|";
			str.append("|");
			node[i][0] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,1);
			node[i][1] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,2);
			node[i][2] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,3);
			node[i][3] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,4);
			node[i][4] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,5);
		}
	}
//	String leafNodeStr = menuShow.getAllLeafNodePath(node,node_count);

	out.println("<script>");
	out.println(menuShow.getMenu(node,0,node_count,menuCount));
	int totalMenu = menuCount[0] + 2;
//	loggerDebug("menu","var NoOffFirstLineMenus=" + totalMenu);
	out.println("var NoOffFirstLineMenus=" + totalMenu + ";");

//	loggerDebug("menu","complete getMenu");

	menuCount[0]++;
	menustr = new StringBuffer(103);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('���µ�¼','../logon/logout.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','');");
	out.println(menustr.toString());

	menuCount[0]++;
	menustr = new StringBuffer(114);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('�����޸�','../changePwd/PwdInput.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','7777');");
	out.println(menustr.toString());

	out.println("parent.fraQuick.window.location = '../logon/station.jsp';");
	out.println("</script>");
}
//loggerDebug("menu","��̨����׼����ϣ��������");
%>
<script language="JavaScript" type="text/JavaScript">
//var t = new Date();
//document.write(t);
CreateMenuMain("Menu",NoOffFirstLineMenus);
//var s = new Date();
//document.write(s);

function showMenu(divID){
	var imgname;
	var a;
	if (divID.style.display == ""){
		divID.style.display="none";
		a = divID.id.replace('s','i');
		a = "document.all('"+a+"')";
		whichIm = eval(a);
		whichIm.src = "../common/images/butCollapse.gif";
	}
	else{
		divID.style.display="";
		a = divID.id.replace('s','i');
		a = "document.all('"+a+"')";
		whichIm = eval(a);
		whichIm.src = "../common/images/butExpand.gif";
		if (divID.id.length<=7) closeMenu(divID);	//�����һ����Ŀ��ִ��closeMenu
	}
}

//ѭ���رշǴ򿪵�һ��Ŀ¼�˵�������ܲ������
function closeMenu(divID){
	var a;
	divColl = document.all.tags("DIV");	//����div�ĸ���
	for (i=0; i<divColl.length; i++){
		//�ж��Ƿ�Ϊ�򿪵�div������һ��Ŀ¼�˵����Խڵ�ĳ�������һ�������ƣ����Ǻܺ�
		if ((divColl(i).id != divID.id)&&(divColl(i).id.length<=7)) {
			divColl(i).style.display="none";
			a = divColl(i).id.replace('s','i');
			a = "document.all('"+a+"')";
			whichIm = eval(a);
			whichIm.src = "../common/images/butCollapse.gif";
		}
	}
}

function changeframe(nodecode,nodesrc){
        var currElement=event.srcElement;//��ťЧ��
	changeNode(currElement);//��ťЧ��
	parent.fraInterface.window.location.href=nodesrc;	//ת�����ӣ���ֱ����a�ķ�ʽ��һЩ
	if ((nodecode == null)||(nodecode == '')){
	}
	else{
		parent.fraQuick.location.href="station.jsp?Ip=<%=Ip%>&nodecode="+nodecode;	//�޸ĵ���������ʾ��Ϣ
	}
}

var currNode;
function changeNode(nodeID)
{
	if(typeof(currNode)!="undefined")
	{
		currNode.className="out";
	}
	currNode=nodeID;
	currNode.className="up";
}

function shiftMouseOut()
{
	var x = event.srcElement;
	if(x!=currNode)
	{
		x.className="";
	}
	else
	{
		x.className="up"
	}
}
</script>
