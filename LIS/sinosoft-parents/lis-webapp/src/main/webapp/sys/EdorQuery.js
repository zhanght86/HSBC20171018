//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
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



//// ���ݷ��ظ�����
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
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