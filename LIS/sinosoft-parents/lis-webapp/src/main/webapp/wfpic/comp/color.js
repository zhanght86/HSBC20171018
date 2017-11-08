/*
@author:lk
@date:20080904 
*/
var ColorHex=new Array('00','33','66','99','CC','FF')
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
var current=null

var BranchNo_pop = window.createPopup();

function intocolor()
{
    var colorTable=''
    for (i=0;i<2;i++)
    {
      for (j=0;j<6;j++)
      {
        colorTable=colorTable+'<tr height=12>'
        colorTable=colorTable+'<td width=11 style="background-color:#000000">'
        
        if (i==0){
        colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'">'} 
        else{
        colorTable=colorTable+'<td width=11 style="background-color:#'+SpColorHex[j]+'">'} 
    
        
        colorTable=colorTable+'<td width=11 style="background-color:#000000">'
        for (k=0;k<3;k++)
        {
           for (l=0;l<6;l++)
           {
            colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'">'
           }
        }
      }
    }
colorTable='<table  width=253 border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
           +'<tr height=30><td colspan=21 bgcolor=#cccccc>'
           +'<table cellpadding="0" cellspacing="1" border="0" style="border-collapse: collapse">'
           +'<tr><td width="3"><td><input type="text" name="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>'
           +'<td width="3"><td><input type="text" name="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"></td><td><span style="COLOR: white; CURSOR: hand;FONT-FAMILY: Webdings;FONT-SIZE: 14pt" onclick="parent.DisplayClrDlg(false)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;r</span></td></tr></table></td></table>'
           +'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" onmouseover="parent.doOver(event.srcElement)" onmouseout="parent.doOut()" onclick="parent.doclick(event.srcElement)" style="cursor:hand;">'
           +colorTable+'</table>';          
//TabPage2.contentObject().document.all.colorpanel.innerHTML=colorTable;
BranchNo_pop.document.body.innerHTML = colorTable;

}

//将颜色值字母大写
function doOver(ele) 
{
      if ((ele.tagName=="TD") && (current!=ele)) 
      {
        if (current!=null)
        {
        	  current.style.backgroundColor = current._background
        }     
        ele._background = ele.style.backgroundColor
        BranchNo_pop.document.all.DisColor.style.backgroundColor = ele.style.backgroundColor
        BranchNo_pop.document.all.HexColor.value = ele.style.backgroundColor.toUpperCase();
        ele.style.backgroundColor = "white"
        current = ele
      }
}

//将颜色值字母大写
function doOut() 
{
    if (current!=null) 
       current.style.backgroundColor = current._background.toUpperCase();
}

function doclick(ele)
{
    if (ele.tagName == "TD")
    {
        var clr = ele._background;
        clr = clr.toUpperCase(); //将颜色值大写
        if (targetElement)
        {
            //给目标无件设置颜色值
            targetElement.value = clr;
        }
        DisplayClrDlg(false);
        return clr;
    }
}

var targetElement = null; //接收颜色对话框返回值的元素

//当点下鼠标时，确定显示还是隐藏颜色对话框
//点击颜色对话框以外其它区域时，让对话框隐藏
//点击颜色对话框色区时，由 doclick 函数来隐藏对话框
function ColorClick(tElement)
{
	  targetElement=eval("TabPage2.contentObject().document.all."+tElement);
    DisplayClrDlg(true);   
}

//显示颜色对话框
//display 决定显示还是隐藏
//自动确定显示位置
//function DisplayClrDlg(display)
//{
//    var clrPanel = TabPage2.contentObject().document.all.colorpanel;
//    if (display)
//    {
//        var left = document.body.scrollLeft + event.clientX;
//        var top = document.body.scrollTop + event.clientY;
//        if (event.clientX+clrPanel.style.pixelWidth-50 > document.body.clientWidth)
//        {
//            //对话框显示在鼠标右方时，会出现遮挡，将其显示在鼠标左方
//            left -= clrPanel.style.pixelWidth;
//            if(left<0)
//               left=50;
//        }
//        if (event.clientY+clrPanel.style.pixelHeight+50 > document.body.clientHeight)
//        {
//            //对话框显示在鼠标下方时，会出现遮挡，将其显示在鼠标上方
//            top -= clrPanel.style.pixelHeight;
//        }
//
//        clrPanel.style.pixelLeft = left;
//        clrPanel.style.pixelTop = top;
//
//        clrPanel.style.display = "block";
//    }
//    else
//    {
//        clrPanel.style.display = "none";
//    }
//}

function DisplayClrDlg(display)
{
	  var textwidth=253;
	  var textHeight=177;
    if (display)
    {
        var left = document.body.scrollLeft + event.clientX;
        var top = document.body.scrollTop + event.clientY;
        if (event.clientX+textwidth-50 > document.body.clientWidth)
        {
            //对话框显示在鼠标右方时，会出现遮挡，将其显示在鼠标左方
            left -= textwidth;
            if(left<0)
               left=50;
        }
        if (event.clientY+textHeight+50 > document.body.clientHeight)
        {
            //对话框显示在鼠标下方时，会出现遮挡，将其显示在鼠标上方
            top -= textHeight;
        }
        BranchNo_pop.show(left,top,textwidth,textHeight,TabPage2.contentObject().document.body);
    }
    else
    {
        BranchNo_pop.hide()
    }
}

//document.onload = intocolor;