<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="com.sinosoft.lis.pubfun.GlobalInput" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
		


		String tUserCode = request.getParameter("userCode");
		loggerDebug("head","usercode ============================================="+tUserCode);
		int num=0;
	
 //��ѯ�ҵ��ղؼ�
  String tFavorite ="";
    
	VData tVData = new VData();
  TransferData tTransferData=new TransferData();
  tTransferData.setNameAndValue("UserCode", tUserCode);
	tVData.add(tTransferData);

  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(tBusinessDelegate.submitData(tVData,"FavoriteTop","MenuShowUI"))
	{ 
    tFavorite=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
   loggerDebug("head","favoritetop menu========"+tFavorite);
  }
  
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ҳ</title>
<link rel="stylesheet" type="text/css" href="../common/css/head.css">
</head>
  <style type=text/css>
        div div div.con{BORDER-RIGHT: white 1px solid; BORDER-TOP: white 1px solid; PADDING-LEFT: 10px; FONT-SIZE: 12px; BACKGROUND: #bed4ea; LEFT: 0px; BORDER-LEFT: white 1px solid; CURSOR: hand; COLOR: #0f3990; PADDING-TOP: 2px; FONT-FAMILY: verdana; POSITION: relative; TOP: 0px; HEIGHT: 20px; TEXT-ALIGN: left; color:#CC0000}  
        html{font-size:12px;}
    </style>
<script language="JavaScript">
	var flag=0;
	/*
var oPopup = window.createPopup(); 	
	function hide(){
	oPopup.hide();
}*/


function richContext(rightid,obj) 
{ 
	
	var kkk='<%=tFavorite%>';
	var arrayField=new Array();
	arrayField=kkk.split("\|");
	var len=arrayField.length;
//	alert("len===="+len);
	//alert(kkk);
//len�������˵����������ڼ���˵���߶�
	var lefter = obj.style.left+19;
	var topper = obj.style.top-15;
   // var lefter = event.offsetY+20; 
   // var topper = event.offsetX-60; 
    var height=(len)*20;
    var cc=rightid.childNodes[0];
    var aa=cc.childNodes[0];
   // prompt('',rightid.innerHTML);
  if(flag==0){     
  var arrayReturn=new Array();
  var i;
  
  for(i=0;i<arrayField.length-1;i++)
  {
   
    var arrayNameValuePair=arrayField[i].split(",");      //�ָ��һ����������ֵ
    
     var tDiv = document.createElement('div');
      
   //	var tTop = 0+;
   	
    
      //tDiv= aa.cloneNode(true);
      tDiv.className='con';	 
	   tDiv.style.lineHeight = "20px";
	      tDiv.style.border = "1px solid #fff";
        tDiv.style.left = "0px" ;
        tDiv.style.top ="0px";
        tDiv.style.fontSize ='12px';
        tDiv.style.textAlign = "center";
        tDiv.style.height = "10px";
        tDiv.style.width = '102px';
        tDiv.style.cursor = "hand";
        tDiv.style.position = "relative";
         tDiv.style.background = "#bed4ea";
 
  


      tDiv.onmouseover="this.style.background='#6699cc',this.style.color='#ffffff'";
      tDiv.onmouseout="this.style.background='#bed4ea',this.style.color='#0f3990'";
      tDiv.onclick="parent.window.open('"+arrayNameValuePair[1]+"','fraInterface')";
      tDiv.innerText=arrayNameValuePair[0];
    	//cc.appendChild(tDiv);
    	rightid.childNodes[0].appendChild(tDiv);
    	
  }
  flag=1;
}
    oPopup.document.body.innerHTML = rightid.innerHTML;  
    oPopup.show(topper, lefter, 100, height, obj);  
    
    if(oPopup.isOpen== true){    
    	  setTimeout("hide()", 15000); 

    	}
} 
</script>
<body>
	<!--��¼ ��ʼ-->
	<div class="head">
		<div class="logo"><a href="#"><img src="../common/images/logo.jpg" width="158" height="42" border="0" /></a></div>
		<div class="loginarea" style="align:right;">
        	<ul>
            	<li class="ico01"><a href="#" onmouseover ="richContext(wdcd,this);">�ҵ��ղؼ�</a></li>
                <li>|</li>
                <li class="ico03"><a target ="fraInterface" href="../changePwd/PwdInput.jsp">�����޸�</a></li>
                <li>|</li>
                <li class="ico04"><a href="..\logon\logout.jsp">���µ�¼</a></li>
            </ul>
        </div>
	</div>
<!--��¼ ����-->
</body>
<DIV id=wdcd style="DISPLAY: NONE; font-size:12px;">
		<DIV id=wdcdxx 
		style="LEFT: 0px; WIDTH: 102px; POSITION: relative; TOP: 0px; HEIGHT: 130px">
			
			<DIV 
			onmouseover="this.style.background='#6699cc',this.style.color='#ffffff';" 
			style="BORDER-RIGHT: white 1px solid; BORDER-TOP: white 1px solid; PADDING-LEFT: 10px; FONT-SIZE: 12px; BACKGROUND: #bed4ea; LEFT: 0px; BORDER-LEFT: white 1px solid; CURSOR: hand; COLOR: #0f3990; PADDING-TOP: 2px; FONT-FAMILY: verdana; POSITION: relative; TOP: 0px; HEIGHT: 18px; TEXT-ALIGN: left; line-height:18px;" 
			onmouseout="this.style.background='#bed4ea',this.style.color='#0f3990';"
			onclick="parent.window.open('../logon/MenuShortInput.jsp','fraInterface')">�����ҵ��ղؼ�</DIV>
						
		
		</DIV>
	</DIV>
</html>

