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



// ���Ѳ�Ѱ
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelFeeQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
	}
}



// ������Ѱ
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelGetQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
	}
}


//���Ĳ��˷Ѳ�ѯ
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
	}
}

//ɨ�����ѯ
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}


function PolClick_B()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    

		    window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo+"&IsCancelPolFlag=1","",sFeatures );	
	}
}