<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="com.sinosoft.lis.pubfun.GlobalInput" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.PubFun" %>
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%
		


		String tUserCode = request.getParameter("userCode");
		loggerDebug("head","usercode ============================================="+tUserCode);
		int num=0;
	
 //查询我的收藏夹
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

<%

    //接受传入参数
    String sUserCode = request.getParameter("userCode");
    String sIPAddress = request.getParameter("Ip");

    //用户菜单容器
    String sMenuNodeData ="";

    //查询用户菜单
    
    
  String QuerySQL = new String("");
  tVData = new VData();
  tTransferData=new TransferData();
  tTransferData.setNameAndValue("UserCode", sUserCode);
	tVData.add(tTransferData);
	 String sFavorite ="";
	if(tBusinessDelegate.submitData(tVData,"Favorite","MenuShowUI"))
	{ 
		sFavorite=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
  }


	if(tBusinessDelegate.submitData(tVData,"SQL","MenuShowUI"))
	{ 
    tTransferData=(TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
    if("TRUE".equals((String)tTransferData.getValueByName("Expired")))
    {
			%>
			<script type="text/javascript">
				alert("您的密码已经失效，请修改密码后重新登录！");
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
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>首页</title>
<SCRIPT src="../common/javascript/jquery-1.7.2.js" ></SCRIPT>
<script src="../common/javascript/slide.js"></script>
<link rel="stylesheet" type="text/css" href="../common/css/head.css">
</head>
  <style type=text/css>
        div div div.con{BORDER-RIGHT: white 1px solid; BORDER-TOP: white 1px solid; PADDING-LEFT: 10px; FONT-SIZE: 12px; BACKGROUND: #bed4ea; LEFT: 0px; BORDER-LEFT: white 1px solid; CURSOR: hand; COLOR: #0f3990; PADDING-TOP: 2px; FONT-FAMILY: verdana; POSITION: relative; TOP: 0px; HEIGHT: 20px; TEXT-ALIGN: left; color:#CC0000}  
        html{font-size:12px;}
    </style>
<script language="JavaScript">

 
this.name     = "";
this.nodes = {};
this.node  = {};
this.names = "";
this._d    = "\x0f";
this.index = 0;
this.divider   = "_";
this.node["0"] =
{
  "id": "0",
  "path": "0",
  "isLoad": false,
  "childNodes": [],
  "childAppend": "",
  "sourceIndex": "0"
};



function dataFormat()
{
var a = new Array();
for (var id in this.nodes) a[a.length] = id;
this.names = a.join(this._d + this._d);
this.totalNode = a.length; a = null;
};


function loadData(id)
{
var node = this.node[id], d = this.divider, _d = this._d;
if(!node.isLoad) {
var sid = node.sourceIndex.substr(node.sourceIndex.indexOf(d) + d.length);
var reg = new RegExp("(^|"+_d+")"+ sid +d+"[^"+_d+d +"]+("+_d+"|$)", "g");
var cns = this.names.match(reg), tcn = this.node[id].childNodes; if (cns){
reg = new RegExp(_d, "g"); for (var i=0; i<cns.length; i++)
tcn[tcn.length] = this.nodeInit(cns[i].replace(reg, ""), id); }
node.isLoad = true;
}
};

function nodeInit (sourceIndex, parentId)
{
this.index++;
var source= this.nodes[sourceIndex], d = this.divider;
var text  = this.getAttribute(source, "text");
var hint  = this.getAttribute(source, "hint");
var url  = this.getAttribute(source, "url");
var sid   = sourceIndex.substr(sourceIndex.indexOf(d) + d.length);

//var target = this.getAttribute(source, "target");
var data = this.getAttribute(source, "data");
if(data) url += (url.indexOf("?")==-1?"?":"&") + data;

this.node[this.index] =
{
  "id"    : this.index,
  "text"  : text,
  "hint"  : hint ? hint : text,
  "icon"  : this.getAttribute(source, "icon"),
  "path"  : this.node[parentId].path + d + this.index,
  "isLoad": false,
  "isExpand": false,
  "parentId": parentId,
  "parentNode": this.node[parentId],
  "sourceIndex" : sourceIndex,
  "childAppend" : "",
	"url": url,
	"sid":sid
};
   this.nodes[sourceIndex] = "index:"+ this.index +";"+ source;
   this.node[this.index].hasChild = this.names.indexOf(this._d + sid + d)>-1;
if(this.node[this.index].hasChild)  this.node[this.index].childNodes = [];
return this.node[this.index];
};

function getAttribute (source, name)
{
  var reg = new RegExp("(^|;|\\s)"+ name +"\\s*:\\s*([^;]*)(\\s|;|$)", "i");
  if (reg.test(source)) return RegExp.$2.replace(/[\x0f]/g, ";"); return "";
};


function initMenuTree()
{
    nodes["0_1"] = "text:"+"首页"+"; url:../usermission/UserMission.jsp;";
    nodes["1_2"] = "text:"+"我的收藏夹"+"; icon:folder; url:../usermission/UserMission.jsp; hint:"+"我的收藏夹"+";"; 
    <%=sFavorite%>
    nodes["2_9"] = "text:"+"定制我的收藏夹"+"; icon:file; url:../g_claim/LLClaimNoticePrintInput.jsp; hint:"+"定制我的收藏夹"+";"; 
    <%=sMenuNodeData%>
}

var MenuClass1Html = '<ul class="change">';

function display(id)
{
	loadData(id);
	var rootCN = this.node[id].childNodes;
	if(rootCN.length>0)
	{
		
      this.node[id].hasChild = true;
      for(var i=0; i<rootCN.length; i++) {
        	//alert(rootCN[i].id + " " + rootCN[i].text  + " "+  rootCN[i].url);
        	MenuClass1Html += '<li class="show-poster-3"><a href="javascript:clickTopMenu('+rootCN[i].id+')">'+rootCN[i].text+'</a></li>'
	  }
	}
}

initMenuTree();

dataFormat();
loadData("0");
var rootCN = this.node["0"].childNodes;
if(rootCN.length>0)
{
      this.node["0"].hasChild = true;
      for(var i=0; i<rootCN.length; i++) {
    	  MenuClass1Html += '<li class="show-poster-3 current"><a class="selected" href="javascript:clickTopMenu('+rootCN[i].id+')">'+rootCN[i].text+'</a></li>'
    	  display(rootCN[i].id);
	  }
      
}
MenuClass1Html += '</ul>';



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
//len：弹出菜单条数，用于计算菜单框高度
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
   
    var arrayNameValuePair=arrayField[i].split(",");      //分割出一对域名和域值
    
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
	<!--登录 开始
	<div class="head">
		<div class="logo"><a href="#"><img src="../common/images/logo.jpg" width="158" height="42" border="0" /></a></div>
		<div class="loginarea" style="align:right;">
        	<ul>
            	<li class="ico01"><a href="#" onmouseover ="richContext(wdcd,this);">我的收藏夹</a></li>
                <li>|</li>
                <li class="ico03"><a target ="fraInterface" href="../changePwd/PwdInput.jsp">密码修改</a></li>
                <li>|</li>
                <li class="ico04"><a href="..\logon\logout.jsp">重新登录</a></li>
            </ul>
        </div>
	</div>
登录 结束-->
<!--登录 开始-->
	<div class="head">
    <div class="logo"><a href="#"><img src="../common/images/logo_03.png" width="182" height="40" border="0" /></a></div>
    <div class="right">
		<div class="loginarea" style="align:right;">
        	<ul>
                <li class="ico03"><a target ="fraInterface" href="../changePwd/PwdInput.jsp"><img src="../common/images/suo_03.png"></a></li>
                <li class="ico04"><a target ="fraInterface" href="../logon/logout.jsp"><img src="../common/images/cha_06.png"></a></li>
            </ul>
        </div>
        <div class="content">
           <div class="drama-poster">
			  <div class="nav" id="menuClass1">
        	 	<ul class="change">
            	 	<li class="show-poster-3 current"><a>首页</a></li>
            	 </ul>
	         </div>
       </div>
	       <ul class="drama-slide">
		      <li class="prev"><a href="javascript:void(null)" title="上翻" ></a></li>
		      <li class="next"><a href="javascript:void(null)" title="下翻" ></a></li>
	      </ul>
        </div>  
    </div>
</div>
<!--二级菜单js-->
  
<!--登录 结束-->
<!--
<DIV id=wdcd style="DISPLAY: NONE; font-size:12px;">
		<DIV id=wdcdxx 
		style="LEFT: 0px; WIDTH: 102px; POSITION: relative; TOP: 0px; HEIGHT: 130px">
			
			<DIV 
			onmouseover="this.style.background='#6699cc',this.style.color='#ffffff';" 
			style="BORDER-RIGHT: white 1px solid; BORDER-TOP: white 1px solid; PADDING-LEFT: 10px; FONT-SIZE: 12px; BACKGROUND: #bed4ea; LEFT: 0px; BORDER-LEFT: white 1px solid; CURSOR: hand; COLOR: #0f3990; PADDING-TOP: 2px; FONT-FAMILY: verdana; POSITION: relative; TOP: 0px; HEIGHT: 18px; TEXT-ALIGN: left; line-height:18px;" 
			onmouseout="this.style.background='#bed4ea',this.style.color='#0f3990';"
			onclick="parent.window.open('../logon/MenuShortInput.jsp','fraInterface')">定制我的收藏夹</DIV>
						
		
		</DIV>
	</DIV>-->
    </body>
    <script>
    	document.getElementById("menuClass1").innerHTML = MenuClass1Html;
		var initFlag = false;
    	
    	function clickTopMenu(id){
    		
    		var html = "";
    		html += '<div class="left">';
    		html += '<ul>';
    		html += '<p class="diyi">';
    		if(this.node[id].url) {
    			html +='<a class="innerA" target="fraInterface" href="'+this.node[id].url+'">';
    		}
    		html +='<b>'+this.node[id].text+'</b>';
    		if(this.node[id].url) {
    			html +='</a>';
    		}
    		html +='</p>';
    		//如果为1，则隐藏具体菜单
    		if(id != '1') {
	    		loadData(id);
	    		var rootCN = this.node[id].childNodes;
	    		if(rootCN.length>0)
	    		{
		    		this.node[id].hasChild = true;
	    	      	for(var i=0; i<rootCN.length; i++) {
	    	        	var nodeID = rootCN[i].id;
	    	        	//MenuClass1Html += '<li class="show-poster-3"><a href="javascript:clickTopMenu('+rootCN[i].id+')">'+rootCN[i].text+'</a></li>'
	    	        	html += '<li id="'+nodeID+'" ';
	    	        	//window.parent.frames["fraMenu"].alertInfo(rootCN[i].id);
	    	        	loadData(nodeID);
	    	        	var CN = this.node[nodeID].childNodes;
	    	    		if(CN && CN.length>0)
	    	    		{
	    	    			html += 'class="goon" onclick="clickLeftMenu('+rootCN[i].id +');" id="'+rootCN[i].id+'" ';
	    	    		}
	    	    		else {
	    	    			html += ' onclick="showStation('+rootCN[i].sid+');" '
	    	    		}
	    	        	html += ' >';
	    	        	if(CN && CN.length>0) {}
	    	        	else {
		    	        	if(rootCN[i].url) {
		    	        		html +='<a  class="innerA" target="fraInterface" href="'+rootCN[i].url+'">'
		    	        	}
	    	        	}
	    	        	html += rootCN[i].text;
	    	        	if(CN && CN.length>0) {}
	    	        	else {
		    	        	if(rootCN[i].url) {
		    	        		html +='</a>'
		    	        	}
	    	        	}
	    	        	html +='</li>'
	    		  	}
	    		}
	    		html += '</ul>';
	    		html += '</div>';
    		}
    		var fraMenu = window.parent.frames["fraMenu"];
    		fraMenu.divMenuTreeCopy.innerHTML = html;
    		fraMenu.a();
			
			//初始化隐藏左侧菜单，点击除首页外其他顶部菜单后展现左侧菜单
			if (!initFlag && id != '1') {
				fraMenu.fivo();
				initFlag = true;
			}
			/*
			if (id == '1' && fraMenu.getMenuStatus()) {
				fraMenu.fivo();
			} else if (id != '1' && !fraMenu.getMenuStatus()) {
				
			}*/
    	}
    	
    	function clickLeftMenu(id){
    		var html = "";
    		
    		loadData(id);
    		var rootCN = this.node[id].childNodes;
    		var num = 0;
    		if(rootCN.length>0)
    		{
	    		this.node[id].hasChild = true;
    	      	for(var i=0; i<rootCN.length; i++) {
    	        	var nodeID = rootCN[i].id;
    	        	html += '<dl>';
    	        	html += '<dt ';
    	        	loadData(nodeID);
    	        	var CN = this.node[nodeID].childNodes;
    	    		if(CN && CN.length>0)
    	    		{
    	    			html += ' class="thirdmenu'+num+'" id="'+num+'" ';
    	    		}
    	    		else {
    	    			html += ' onclick="showStation('+rootCN[i].sid+');" '
    	    		}
    	    		html += '><b>';
    	        	
    	        	if(CN && CN.length>0) {}
    	        	else {
	    	        	if(rootCN[i].url) {
	    	        		html +='<a class="innerA" target="fraInterface" href="'+rootCN[i].url+'">'
	    	        	}
    	        	} 
    	        	html += rootCN[i].text;
    	        	if(CN && CN.length>0) {}
    	        	else {
	    	        	if(rootCN[i].url) {
	    	        		html +='</a>'
	    	        	}
    	        	}
    	        	html +='</b></dt>'
    	        	if(CN && CN.length>0) {
    	        		this.node[nodeID].hasChild = true;
    	        		for(var j=0; j<CN.length; j++) {
    	        			html +='<dd class="fourthmenu'+num+'" ';
    	        			if(CN[j].url) {
    	        				html += ' onclick="showStation('+CN[j].sid+');" '
    	        			}
    	        			
    	        			html +=' >';
    	        			if(CN[j].url) {
    	    	        		html +='<a class="innerA" target="fraInterface" href="'+CN[j].url+'">'
    	    	        	}
    	        			html +='<img src="../common/images/butCollapse.png"/>&nbsp;&nbsp;'+CN[j].text;
    	        			if(CN[j].url) {
    	    	        		html +='</a>'
    	    	        	}
    	        			html +='</dd>';
    	        		}
    	        		num++;	
    	        	}
    	        
    	        	html +='</dl>'
    		  	}
    		}
    		window.parent.frames["fraMenu"].huadong1.innerHTML = html;
    		window.parent.frames["fraMenu"].b(id,num);
    	
    	}
				
    </script>
</html>

