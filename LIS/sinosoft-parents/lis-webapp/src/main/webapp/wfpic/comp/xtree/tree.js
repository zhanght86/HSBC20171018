function nocontextmenu() 
{
 event.cancelBubble = true
 event.returnValue = false;
 
 return false;
}
 
function norightclick(e) 
{
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


function doAction(type)
{
    if(event.button==1)
    {   
    	  if(type!=0)
    	  {
    	     parent.doSelect(parent.document.getElementById(event.srcElement.action));
        }
    }
    else if(event.button==2)
    {       
       showContextMenu(type,event.srcElement.action);
    }
    else
    {
        alert("不支持此鼠标事件！");
    }
	
}
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

function showContextMenu(type,Process_ID)
{

	
  var action="edit";
	
	
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
                      
  	sMenu += getMenuRow("parent.EditProcess('"+Process_ID+"','"+action+"')", "步骤基本属性");
  	height += 20;
    
  	sMenu += sMenuHr;
  	height += 2;
  	
  	sMenu += getMenuRow("parent.DelProcess('"+Process_ID+"')", "删除该步骤");
  	height += 20;

  	break;

  case 2:

  	sMenu += getMenuRow("parent.EditLine('"+Process_ID+"','"+action+"')", "联线基本属性");
  	height += 20;
   
  	sMenu += sMenuHr;
  	height += 2;
  	                   
		sMenu += getMenuRow("parent.DelLine('"+Process_ID+"')",  "删除联线");
	  height += 20;
	  break;

  case 0:

  	sMenu += getMenuRow("parent.EditFlow('"+Process_ID+"','"+action+"')", "视图基本属性");     
  	height += 20;

  	sMenu += getMenuRow("parent.RelateToPlan()", "关联计划");     
  	height += 20;
  	
  	sMenu += sMenuHr;
  	height += 2;

  	sMenu += getMenuRow("parent.DelFlow()", "删除视图");
  	height += 20;

  	sMenu += getMenuRow("parent.SaveToDB()", "保存视图");
  	height += 20;

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


function getTreeView(xml)
{
	
  if(xml!='')
  {
	   var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
     xmlDoc.async = false;
     xmlDoc.loadXML(xml);   
     var xmlRoot = xmlDoc.documentElement;
    
	   
	   var Flow = xmlRoot.getElementsByTagName("FlowConfig").item(0);	   
	   flowId = Flow.getElementsByTagName("BaseProperties").item(0).getAttribute("flowId");
	   flowName = Flow.getElementsByTagName("BaseProperties").item(0).getAttribute("flowName");

     var tree = new WebFXTree(flowName,flowId,'doAction(0)');
     tree.setBehavior('classic');
     var a = new WebFXTreeItem('节点');
     tree.add(a);
	   
	   var Nodes = xmlRoot.getElementsByTagName("Nodes").item(0);       
	   var id, text;
	   for (i = 0;i < Nodes.childNodes.length;i++) 
	   {
		   var Node = Nodes.childNodes.item(i);
		   id = Node.getElementsByTagName("BaseProperties").item(0).getAttribute("id");
		   text = Node.getElementsByTagName("BaseProperties").item(0).getAttribute("nodeName");		   
		   a.add(new WebFXTreeItem(text,id,'doAction(1)'));
	   }
     var b = new WebFXTreeItem('流向');
     tree.add(b);	   
	   var Lines = xmlRoot.getElementsByTagName("Lines").item(0);
	   for (i = 0;i < Lines.childNodes.length;i++)
	   {
		   var Line = Lines.childNodes.item(i);
		   id = Line.getElementsByTagName("BaseProperties").item(0).getAttribute("id");
		   text = Line.getElementsByTagName("BaseProperties").item(0).getAttribute("lineName");
		   b.add(new WebFXTreeItem(text,id,'doAction(2)'));
	   }
	   return tree;
	   //document.write(tree);
  }
  else
  {
  	 return '';	
  }
}


function drawTree(xml)
{
	var text=getTreeView(xml);
	document.all.flowTree.innerHTML=text;
	//document.all.flowTree.expandAll();
}

function clearTree()
{
   	document.all.flowTree.innerHTML='';
}