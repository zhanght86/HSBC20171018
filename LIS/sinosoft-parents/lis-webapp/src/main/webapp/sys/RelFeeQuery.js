//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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



// 数据返回父窗口
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		
		if (cPayNo == "")
		    return;
		    
		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
		  if (cIncomeType==1) {
				window.open("../sys/AllFeeQueryGDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"",sFeatures);	
			}
			else
			if (cIncomeType==2) {
				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"",sFeatures);	
			}	
			else {
				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"",sFeatures);	
							
			}
	}
}