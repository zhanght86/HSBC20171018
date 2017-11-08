/*
*@author:lk
*@date:20080904 
*/
//按钮		
var tempButton=null;
var keyint="";

function buttonDown(button) 
{
	if(tempButton)
	{
	   tempButton.className="button";
	}
	button.className = "button-down";
	tempButton=button;
}

function buttonOut(button) 
{
	if(tempButton&&tempButton==button)
	{
	    button.className = "button-down";
	}
  else
  {
      button.className = "button";	
  }
	   
}

function buttonOver(button) 
{
	button.className = "button-hover";
}   
		
 
//改变按钮的颜色
function window.onload()
{
	ChangeImgColor(6) ;
	//WorkFlowGroup.coordsize = "1000,500";
}

function ChangeImgColor(iNo)
{	
	var tempImg=eval('img'+iNo);
	buttonDown(tempImg);
}

//变换基准线
function changeGrid()
{
	switch (sltGrid.value) 
	{
			
		case "100" : //<!-- 网格： 100象素 --> 	
					WorkFlow.style.backgroundImage="url('images/workflow2.gif')"
					break;
		case "50" : //<!-- 网格： 50象素 --> 	
					WorkFlow.style.backgroundImage="url('images/workflow4.gif')"
					break;
		case "25" : //<!-- 网格： 25象素 --> 	
					WorkFlow.style.backgroundImage="url('images/workflow5.gif')"
					break;
		case "0" : //<!-- 网格： 0象素 --> 	
					WorkFlow.style.backgroundImage="url('')"
					break;
		default :
				return false;
	}
}
//缩放倍数  jiyongtian
function MagnifyGrid()
{  
    var mulriple="";
   switch (sltMagnifyGrid.value) 
	{		  
	    		
		case "200" : //<!-- 放大2倍 --> 
		            mulriple="2";						
					break;
		case "175" : //<!-- 放大1.75倍 --> 
			        mulriple="1.75";					
					break;
		case "150" : //<!-- 放大1.5倍 --> 	
					mulriple="1.5";
					break;
		case "125" : //<!-- 放大1.25倍 --> 	
					mulriple="1.25";
					break;
		case "100" : //<!-- 原图 --> 	
					mulriple="1";
					break;
		case "75" : //<!-- 缩小到原图的75% --> 	
					mulriple="0.75";
					break;
		case "50" : //<!-- 缩小到原图的50% --> 	
					mulriple="0.5";
					break;	
		case "25" : //<!-- 缩小到原图的25% --> 	
					mulriple="0.25";
					break;					
		default :
				return false;
	} 
	ZoomOut(mulriple); 
}

function MagnifyGridtxtchange()
{   
	var oldword=document.getElementById('MagnifyGridtxt').value;
	var key = String.fromCharCode(event.keyCode);
	var oldkey;
	//回车键
	if(event.keyCode == "13")
	{	
//		var count=keyint.indexOf("%");
//		if(count > -1)
//		{
//			keyint=keyint.substring(0,count);
//			alert(keyint);
//		}不会而转为数组
//		var count = keyint.length;		
//		for (var i=0;i<=count;i++)
//		{
//			var ddd=keyint[0][i];
//		}
		if(keyint == null||keyint =="")
		{
			keyint=oldword;
		}
		var mulripletext = keyint/(100);
		if(mulripletext > 5 || mulripletext < 0.05)
		{
			alert("为了您的健康，只扩大到原图的五倍或缩小到原图的5%");
			return false;
		}
		sltMagnifyGrid.value = keyint;
		oldkey = keyint;
		keyint = "";
		ZoomOut(mulripletext);
		return true;
	}
//	退格键
	else if (event.keyCode == "8")
	{	
		if(oldword != null || oldword !="")
		{
			oldkey= oldword;
			keyint = oldkey.substring(0,oldkey.length-1);			
		}
		return true;
	}
   // 小键盘 0
	else if (event.keyCode == "96")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"0";
		return true;
	}
	  // 小键盘 1
	else if (event.keyCode == "97")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"1";
		return true;
	}
	  // 小键盘 2
	else if (event.keyCode == "98")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"2";
		return true;
	}
	  // 小键盘 3
	else if (event.keyCode == "99")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"3";
		return true;
	}
	  // 小键盘 4
	else if (event.keyCode == "100")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"4";
		return true;
	}
	  // 小键盘 5
	else if (event.keyCode == "101")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"5";
		return true;
	}
	  // 小键盘 6
	else if (event.keyCode == "102")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"6";
		return true;
	}
	  // 小键盘 7
	else if (event.keyCode == "103")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"7";
		return true;
	}
	  // 小键盘 8
	else if (event.keyCode == "104")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"8";
		return true;
	}
	  // 小键盘 9
	else if (event.keyCode == "105")
	{
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		keyint=keyint+"9";
		return true;
	}
	else
	{   
		if(oldword != null || oldword !="")
		{
			keyint = oldword;
		}
		if(key < 9 && key > -1)
		{   
			keyint=keyint+key;	
		}
		else 
		{
			alert("只能输入数字！请删除后重新输入");
			document.getElementById('MagnifyGridtxt').value = oldword;
		}
		return true;
	}  
}

window.onerror=handleError;

function handleError(_message, _URI, _line)
{
	 alert(_URI+_line+_message); // 提示用户这个页面可能无法正常响应
    return true; // 停止默认的消息
}		


function nocontextmenu() 
{
	// //在这里借用一下这里的事件,连线
	// if (event.button == 2&&event.srcElement==null)
	// {
  //   alert();
  //   flagAddLine = null;
  //   ChangeImgColor(6);
  // }
   event.cancelBubble = true
   event.returnValue = false;
 
    return false;
}
 
function norightclick(e) 
{
		 //在这里借用一下这里的事件,连线
	//	 alert(event.srcElement)
	// if (event.button == 2&&event.srcElement==null)
	// {
	// 	 alert();
  //   flagAddLine = null;
  //   ChangeImgColor(6);
  // }
 if (window.Event) 
 {
  if (e.which == 2 || e.which == 3)
   return false;
 }
 else
  if (event.button == 2 || event.button == 3)
  {
   event.cancelBubble = true
   event.returnValue = false;
   return false;
  }
 
}
 
document.oncontextmenu = nocontextmenu;  //对ie5.0以上
document.onmousedown = norightclick;  //对其它浏览器


// 形成菜单行
function getMenuRow(s_Event, s_Html) {
	var s_MenuRow = "";
	s_MenuRow = "<tr><td align=center valign=middle nowrap><TABLE border=0 cellpadding=0 cellspacing=0 width=132><tr><td nowrap valign=middle height=20 class=MouseOut onMouseOver=this.className='MouseOver'; onMouseOut=this.className='MouseOut';";
	s_MenuRow += " onclick=\"parent."+s_Event+";parent.oPopupMenu.hide();\"";
	s_MenuRow += ">&nbsp;";
	s_MenuRow += s_Html+"<\/td><\/tr><\/TABLE><\/td><\/tr>";
	return s_MenuRow;
}


//-- 右键菜单 --
var sMenuHr = "<tr><td align=center valign=middle height=2><TABLE border=0 cellpadding=0 cellspacing=0 width=128 height=2><tr><td height=1 class=HrShadow><\/td><\/tr><tr><td height=1 class=HrHighLight><\/td><\/tr><\/TABLE><\/td><\/tr>";
var sMenu1 = "<TABLE onmousedown='if (event.button==1) return true; else return false;' border=0 cellpadding=0 cellspacing=0 class=Menu width=150><tr><td width=18 valign=bottom align=center style='background:url(/images/bg_left.gif.gif);background-position:bottom;'><\/td><td width=132 class=RightBg><TABLE border=0 cellpadding=0 cellspacing=0>";
var sMenu2 = "<\/TABLE><\/td><\/tr><\/TABLE>";
var oPopupMenu = null;
oPopupMenu = window.createPopup();

function showContextMenu()
{
   //object
	flagRight = who();

	var Process_ID =  flagRight.id;
	var action="edit";
	
	if(Process_ID==null||Process_ID=="")
  {
  	    Process_ID =  flagRight.uniqueID;
  	    action="new";
  }

  if(flagRight.flowType=="begin"||flagRight.flowType=="end"||flagRight.flowType=="course"||flagRight.flowType=="judge")
  {	
  	  type=1;
  }
  else if(flagRight.flowType=="connect")
  {
     	type=2;
  }	
  else
	{
	    type=3;
	}
	
	
	
	var style = "";
	style = "BODY {margin:0px;border:0px}";
	style += " TD {font-size:9pt;font-family:宋体,Verdana,Arial}";
	style += " TABLE.Menu {border-top:window 1px solid;border-left:window 1px solid;border-bottom:buttonshadow 1px solid;border-right:buttonshadow 1px solid;background-color:#0072BC}";
	style += "TD.RightBg {background-color:buttonface}";
	style += "TD.MouseOver {background-color:highlight;color:highlighttext;cursor:default;}";
	style += "TD.MouseOut {background-color:buttonface;color:buttontext;cursor:default;}";
	style += "TD.HrShadow {background-color:buttonshadow;}";
	style += "TD.HrHighLight {background-color:buttonhighlight;}";
	style = "<style>" + style + "</style>";

	var width = 150;
	var height = 0;
	var lefter = event.clientX;
	var topper = event.clientY;

	var oPopDocument = oPopupMenu.document;
	var oPopBody = oPopupMenu.document.body;


	var sMenu = style;

  switch(type)
  {
  case 1:
                      
  	sMenu += getMenuRow("EditProcess('"+Process_ID+"','"+action+"')", "步骤基本属性");
  	height += 20;
    	
    if(!isRebuild)
    {
  	   sMenu += sMenuHr;
  	   height += 2;

     	 sMenu += getMenuRow("DelProcess('"+Process_ID+"')", "删除该步骤");
     	 height += 20;
    }
  	break;

  case 2:

  	sMenu += getMenuRow("EditLine('"+Process_ID+"','"+action+"')", "联线基本属性");
  	height += 20;
    if(!isRebuild)
    {
  	  sMenu += sMenuHr;
  	  height += 2;
                   
		  sMenu += getMenuRow("DelLine('"+Process_ID+"')",  "删除联线");
	    height += 20;
    }
	  break;

  case 3:

    break;
  }

	sMenu = sMenu1 + sMenu + sMenu2;

	height += 2;
	if (lefter+width > document.body.clientWidth) lefter = lefter - width + 2;
	if (topper+height > document.body.clientHeight) topper = topper - height + 2;

	oPopupMenu.document.body.innerHTML = sMenu;
	oPopupMenu.show(lefter, topper, width, height,document.body);

	return false;
}


var dialogURL = "";
var dialog=null;
function workFlowDialog(id,type,action)
{
	switch (type)
	{
      case 'begin': dialogURL = id==null?'_nodedialogMain.html':'_nodedialogMain.html?nodeid='+id+'&action='+action;break;
	    case 'end': dialogURL = id==null?'_nodedialogMain.html':'_nodedialogMain.html?nodeid='+id+'&action='+action;break;
	    case 'judge': dialogURL = id==null?'_nodedialogMain.html':'_nodedialogMain.html?nodeid='+id+'&action='+action;break;
	    case 'connect': dialogURL = id==null?'_linedialogMain.html':'_linedialogMain.html?lineid='+id;break;
		  case 'course': dialogURL = id==null?'_nodedialogMain.html':'_nodedialogMain.html?nodeid='+id+'&action='+action;break;		
		  case 'flow': dialogURL = id==null?'_flowdialogMain.html':'_flowdialogMain.html?flowid='+id+'&action='+action;break;			     	  
	} 
	
	//dialog = window.showModalDialog(dialogURL, window, "dialogWidth:373px; dialogHeight:460px; center:yes; help:no; resizable:no; status:no") ;\
	var name='提示';   //网页名称，可为空; 
	var iWidth=373;      //弹出窗口的宽度; 
	var iHeight=460;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	dialog = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	dialog.focus();

}




function FindChildElement(element, tagName)
{
    var isFounded = false;
    var elements = element;
    var result = element;
    if ( element.tagName == tagName )
    {
        return element;
    }
    while(!isFounded && elements != null && result != null && result.tagName != tagName)
    {
        elements = elements.childNodes;
        for( var i=0 ; elements != null && i < elements.length ; i++ )
        {
            result = elements.item(i);
            var result2 = FindChildElement(result, tagName);
            if ( result == null || result2 == null )
            {
                continue;
            }
            if ( result.tagName == tagName || result2.tagName == tagName )
            {
                if ( result2.tagName == tagName )
                {
                    result = result2;
                }
                isFounded = true;
                break;
            }
        }
    }
    if ( isFounded )
    {
        return result;
    }
    else
    {
        return null;
    }
}

//切换屏幕
function switchSysBar()
{   
	var newdivRect=document.getElementById('divRect');
    var divRight=newdivRect.style.posRight;
    var newdivRectx=miniRect.coordsize.x;
    var divwork=document.getElementById('divWorkChart');
    if (switchPoint.innerText==3)
    { 
       switchPoint.innerText=4;            
       document.all("frmTree").style.display="none";      
       divWorkChart.style.width="980px";    
       //jiyongtian 缩略图红框实现改变      
       changeminiline(); 
       //2.5只是一个约数，没有实际意义，为了解决，switchPoint.innerText为3时有滚动条，为4时滚动条隐藏 时红框位置不对
       if(divRight>2.5)
       {
    	   //100也是一个越是，switchPoint.innerText为3、4时红框的宽度差 结合节点图形的宽调整的约数 ，300是红框所在div的宽
           newdivRect.style.posRight=divRight+Math.abs(300*(125/newdivRectx));
           if(newdivRect.style.posRight>26)
           {
        	   newdivRect.style.posRight=26;
           }
       } 
       if(divwork.scrollLeft !="0")
       {
    	   scrollit();
       }
    }
    else
    { 
       switchPoint.innerText=3 
       document.all("frmTree").style.display="";      
       divWorkChart.style.width="851px";
       //jiyongtian 缩略图红框实现改变
       chalkminiline();
    }   
} 

//重新画图的节点树
function redrawTree()
{
 // document.all.iframeTree.src="_flowtree.html";
  iframeTree.drawTree(document.all.FlowXML.value);
}

function enableButton(tButton)
{
	  tButton.disabled='';
}

function disableButton(tButton)
{
	  tButton.disabled=true;
}


function editNodeHis(arr)
{
	  if(arr==null)
	  {
	     return ;
	  }
	  var hisHtml="<table class= common>"
	              +   "<tr>"
	              +      "<td class= centertitle>开始日期</td><td class= centertitle>开始时间</td><td class= centertitle>结束日期</td><td class= centertitle>结束时间</td><td class= centertitle>执行人</td>"
	              +   "</tr>";
	  for(var i=0;i<arr.length;i++)
	  {
	      hisHtml=hisHtml
	              +   "<tr>"
	              +      "<td class= common>"+arr[i][0]+"</td><td class= common>"+arr[i][1]+"</td><td class= common>"+arr[i][2]+"</td><td class= common>"+arr[i][3]+"</td><td class= common>"+arr[i][4]+"</td>"
	              +   "</tr>";       
	  }
	  hisHtml=hisHtml+"</table>";
	  His.innerHTML=hisHtml;
}