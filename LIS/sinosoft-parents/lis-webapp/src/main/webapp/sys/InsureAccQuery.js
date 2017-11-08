//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


/*********************************************************************
 *  显示保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showPolDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	var cPolNo = "";
	if( tSel != null && tSel != 0 )
		cPolNo = PolGrid.getRowColData( tSel - 1, 1 );

	if( cPolNo == null || cPolNo == "" )
		alert("请选择一张保单后，再进行查询  个人投保单明细  操作");
	else
	{
			window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=0","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}
       