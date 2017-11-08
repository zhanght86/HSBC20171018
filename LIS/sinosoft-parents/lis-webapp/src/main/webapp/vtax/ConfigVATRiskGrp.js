var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
var showInfo; 
function query(){
    try {
	
	    mySql = new SqlClass();
	    mySql.setResourceName("vrate.VatRate");
	    mySql.setSqlId("VATRateSql3");
	    initRiskGrpGrid();
    	    turnPage.queryModal(mySql.getString(), RiskGrpGrid);
    	    }catch(e) {
    		alert("RiskGrpGrid error");
    	    }
   
}

function showRiskGrpDetail(){
	var tSelNo = RiskGrpGrid.getSelNo();
		
		if(tSelNo!=-1)
		{
			var tBusiCode=RiskGrpGrid.getRowColData(tSelNo-1,1);
			document.all('RiskCode').value = tBusiCode;
			var tRiskGrpCode=RiskGrpGrid.getRowColData(tSelNo-1,2);
			document.all('RiskGrpCode').value = tRiskGrpCode;
			var tRecordID = RiskGrpGrid.getRowColData(tSelNo-1,3);
			document.all('RecordID').value = tRecordID;
		}
	}

function newClick(){
   if(beforeSubmit() && isEmptyDate() && addCheckDateIsExist()){
        document.all('fmtransact').value='INSERT||MAIN';
        var i = 0;
        var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        document.getElementById("fm").submit(); //提交
  }
}

function deleteClick()
{
    var selno = RiskGrpGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("请选择要删除的流程！");
        return;
    }
    var confimObj = confirm("确定删除吗？");
	   if(!confimObj){
		   return;
	   }
    var showStr="正在删除流程定制信息，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();
    document.all('fmtransact').value='DELETE||MAIN';
    document.getElementById("fm").submit();
    refreshEmptyDate();
}


function updateClick(){
	if(beforeSubmit() && isEmptyDate() && addCheckDateIsExist()){
            document.all('fmtransact').value='UPDATE||MAIN';
            document.getElementById("fm").submit();
	}
	
}

function afterSubmit( FlagStr, content )
{
    initForm();
    if (FlagStr == "Fail" )
    {             
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
    }else
    { 
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	query();
	refreshEmptyDate();
  }
}



function beforeSubmit()
{
  //添加操作	
  if(!verifyInput()){
	  return false;
  }
  return true;
} 


function isEmptyDate(){
    var RiskCode = document.getElementById("RiskCode"),
    	RiskGrpCode = document.getElementById("RiskGrpCode");
    if(!RiskCode){
	alert("请填写险种编码 ！");
	return false;
    }
    if(!RiskGrpCode){
	alert("请填写险种组！");
	return false;
    }
    return true;
}

function refreshEmptyDate(){   //新增、修改、删除后清空文本
    document.getElementById("RiskCode").value="";
    document.getElementById("RiskGrpCode").value="";
}



function addCheckDateIsExist(){
    var RiskCode = document.all('RiskCode').value;
    var RiskGrpCode = document.all('RiskGrpCode').value;
    strSql ="SELECT count(RiskCode) FROM LDVATGrp WHERE riskcode = '"+RiskCode+"' AND riskgrp = '"+RiskGrpCode+"'" ;
    var brr = easyExecSql(strSql );
    if(brr[0]>0){
	alert("已存在您要添加险种组信息！可直接操作。");
	return false;
    }
    return true;
}