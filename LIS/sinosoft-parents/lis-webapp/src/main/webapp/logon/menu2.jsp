<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-13
 * @direction: ϵͳ�˵������
 ******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.PubFun" %>
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //���ܴ������
    String sUserCode = request.getParameter("userCode");
    String sIPAddress = request.getParameter("Ip");

    //�û��˵�����
    String sMenuNodeData ="";
    //��ѯ�ҵ��ղؼ�
    String tFavorite ="";
    
	VData tVData = new VData();
  TransferData tTransferData=new TransferData();
  tTransferData.setNameAndValue("UserCode", sUserCode);
	tVData.add(tTransferData);

 
	if(tBusinessDelegate.submitData(tVData,"Favorite","MenuShowUI"))
	{ 
    tFavorite=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
  }

    //��ѯ�û��˵�
    
    
  String QuerySQL = new String("");
  tVData = new VData();
  tTransferData=new TransferData();
  tTransferData.setNameAndValue("UserCode", sUserCode);
	tVData.add(tTransferData);

	if(tBusinessDelegate.submitData(tVData,"SQL","MenuShowUI"))
	{ 
    tTransferData=(TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
    if("TRUE".equals((String)tTransferData.getValueByName("Expired")))
    {
			%>
			<script type="text/javascript">
				alert("���������Ѿ�ʧЧ�����޸���������µ�¼��");
			</script>
			<%       
    }
    
    QuerySQL=(String)tTransferData.getValueByName("QuerySQL");
  }
  
  
  tVData = new VData();
  tTransferData=new TransferData();
  tTransferData.setNameAndValue("SQL", QuerySQL);
  
	tVData.add(tTransferData);

	if(tBusinessDelegate.submitData(tVData,"LDMenu","MenuShowUI"))
	{ 
    sMenuNodeData=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
  }
	System.out.println("---Begin----tFavorite-------");
System.out.println(tFavorite);
System.out.println("-----End--tFavorite-------");
System.out.println("---Begin---sMenuNodeData--------");
System.out.println(sMenuNodeData);
System.out.println("---End---sMenuNodeData--------");
  
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="MzTreeView Author" content="meizz: http://www.meizz.com">
    <title>ϵͳ�˵�</title>
    <!-- ˽��ʹ����ʽ -->
    <style type=text/css>
        body
        {
            font: tahoma,verdana,arial,helvetica,sans-serif;
            font-size: 12px;
            SCROLLBAR-FACE-COLOR: #97CBFF; SCROLLBAR-HIGHLIGHT-COLOR:#fff; 

SCROLLBAR-SHADOW-COLOR:#97CBFF; SCROLLBAR-DARKSHADOW-COLOR:#819FD3; 

SCROLLBAR-3DLIGHT-COLOR:#819FD3; SCROLLBAR-ARROW-COLOR:#3F65AD;

SCROLLBAR-TRACK-COLOR: #E4E4E4;
            padding:0; margin:0;
        }/*2011-07-28 newupdate*/ 
        a:link, a:visited, a:active
        {
            color: #000000;
            padding-left: 2px;
            text-decoration: none;
        }
        a:hover
        {
            color: #FF6600;
            padding-left: 2px;
            text-decoration: none;
        }
        div{padding:0; margin:0;}
        	DIV.MzTreeView DIV{background:url(../common/TreeView/images/menutitloop.gif) no-repeat right;width:100%;display:block; vertical-align:middle;height:24px; padding-left:3px; line-height:24px;}/*2011-07-28 newupdate*/  
    </style>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="../common/TreeView/MzTreeView.js"></script>
    <!-- ˽��ʹ�ýű� -->
    <script language="JavaScript">

        //�����ʵ�ַ
        if (top.location == self.location)
        {
            top.location = "../indexlis.jsp";
        }

        //��ʾ״̬����Ϣ
        defaultStatus = "��ӭʹ�ñ�ϵͳ";
        top.window.status = defaultStatus;

        var MenuTree = new MzTreeView("MenuTree");

        //��ʾ�û��˵�
        function initMenuTree()
        {
            with (MenuTree)
            {
                wordLine = false;
                icons["root"] = "root.png";
                icons["folder"] = "folder_blue.png";
                iconsExpand["folder"] = "folderopen_blue.png";
                icons["file"] = "dot.gif";
                setIconPath("../common/TreeView/images/");
                setTarget("fraInterface");
              //sunsheng modify 2011-05-24 �Ż���ҳ����ʾ�û�Ȩ�޲˵�ҳ��  
              //nodes["0_1"] = "text:"+"ҵ��ϵͳ��ҳ"+"; url:../whatsnew.xml;";
                nodes["0_1"] = "text:"+"ҵ��ϵͳ��ҳ"+"; url:../usermission/UserMission.jsp;";
              //sunsheng modify 2011-05-24 �Ż���ҳ����ʾ�û�Ȩ�޲˵�ҳ��  
              //nodes["1_2"] = "text:"+"�ҵ��ղؼ�"+"; icon:folder; url:../whatsnew.xml; hint:"+"�ҵ��ղؼ�"+";"; 
               nodes["1_2"] = "text:"+"�ҵ��ղؼ�"+"; icon:folder; url:../usermission/UserMission.jsp; hint:"+"�ҵ��ղؼ�"+";"; 
                <%=tFavorite%>
                nodes["2_9"] = "text:"+"�����ҵ��ղؼ�"+"; icon:file; url:../logon/MenuShortInput.jsp; hint:"+"�����ҵ��ղؼ�"+";"; 
                
                
                <%=sMenuNodeData%>
                nodes["1_60001"] = "text:"+"�����޸�"+"; icon:pwd; url:../changePwd/PwdInput.jsp; method:showStation('60001');";
                nodes["1_60002"] = "text:"+"���µ�¼"+"; icon:exit; url:../logon/logout.jsp;";
            }
            try
            {
                window.document.getElementById("divMenuTree").innerHTML = MenuTree.toString();
            }
            catch (ex) {}
        }

        //��ʾ�û�λ��
        function showStation(sNodeCode)
        {
            var sLinkURL;
            if (sNodeCode == null || sNodeCode == "")
            {
                sLinkURL = "station.jsp";
            }
            else
            {
                sLinkURL = "station.jsp?nodecode=" + sNodeCode + "&Ip=<%=sIPAddress%>";
            }
            try
            {
                parent.fraQuick.window.location = sLinkURL;
            }
            catch (ex) {}
        }

        //�˳�ҵ��ϵͳ
        function destroySession()
        {
            try
            {
                window.showModelessDialog("close.jsp", window, "status=0; help=0; close=0; dialogWidth=160px; dialogHeight=100px");
            }
            catch (ex) {}
        }

    </script>
</head>
<body topmargin="2" onUnload="destroySession()"  ondragstart="return false" style="background:url(../common/TreeView/images/menubgloop.gif) repeat-y right #D6E8FB;">
    <div id="divMenuTree"></div>
    <!-- �������ͼ���ļ� -->
    <div style="display:'none'">
        <img src="../common/TreeView/images/folderopen_blue.png">
    </div>
</body>
</html>
<script language="JavaScript">initMenuTree();showStation();</script>
