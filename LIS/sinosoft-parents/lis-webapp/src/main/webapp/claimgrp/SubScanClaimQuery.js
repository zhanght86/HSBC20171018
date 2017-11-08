var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
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
 	else
   {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}


//扫描件查询
function QryScan()
{
  if(document.all('ScanType').value==0000){
  		alert("请您选择扫描类型！");
  		return;
  	}
	var tClaimNo = fm.RgtNo.value;
	var tCustNo= fm.InsuredNo.value;

	var tSubType="LP1005";
	if(document.all('ScanType').value=="4002"){
		tSubType="LP1002";
	}
  else if(document.all('ScanType').value=="4003"){
  	tSubType="LP1003";
  	tCustNo=tClaimNo;
  }
 else if(document.all('ScanType').value=="4004"){
 	tSubType="LP1004";
 }
 else{
 	tSubType="LP1005";
 	tCustNo=tClaimNo;
 }
//	//window.open("../sys/ProposalEasyScan.jsp?BussType=LP&BussNoType=12&SubType=TB1031&prtNo="+claimNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
//	//window.open("../sys/ClaimScanQry.jsp?claimNo="+claimNo)//,'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
  var strUrl="../sys/ClaimScanQry.jsp?claimNo="+tClaimNo+"&caseNo="+tClaimNo+"&custNo="+tCustNo+"&subType="+tSubType;
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}


//扫描件查询
function ScanPrtHTM()
{
  
  	if(document.all('ScanType').value==""){
  		alert("请您选择扫描类型！");
  		return;
  	}
  	var tClaimNo = document.all('RgtNo').value;
  	var tCustNo = document.all('InsuredNo').value;
  	var tScanType = document.all('ScanType').value;
	
	if(tScanType=="4003"){
		
		tCustNo=tClaimNo;
	}
  else if(tScanType=="4004"){
  	
  	
  }
 else if(tScanType=="4005"){
 	
 	tCustNo=tClaimNo;
 }
 else{
 	
 	tCustNo=tClaimNo;
 }
//	//window.open("../sys/ProposalEasyScan.jsp?BussType=LP&BussNoType=12&SubType=TB1031&prtNo="+claimNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
//	//window.open("../sys/ClaimScanQry.jsp?claimNo="+claimNo)//,'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
  var strUrl="../sys/ScanClaimQuerySave1.jsp?claimNo="+tClaimNo+"&scanType="+tScanType+"&custNo="+tCustNo+"&EXT=.htm";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }



