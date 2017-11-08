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



//// 数据返回父窗口
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录，再点击返回按钮。" );
//	else
//	{
//	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cActuGetNo == "")
//		    return;
//		    
//		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cActuGetNo);
//		  //alert(cOtherNoType);
//		  //alert(cSumGetMoney);
//		  if (cOtherNoType==0||cOtherNoType==1||cOtherNoType==2) {
//				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}	
//			else if (cOtherNoType==3){
//				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);							
//			}
//			else if (cOtherNoType==4){
//				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==5){
//				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==6){
//				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==7){
//				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			
//	}
//}