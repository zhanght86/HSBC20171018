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
        var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        document.getElementById("fm").submit(); //�ύ
  }
}

function deleteClick()
{
    var selno = RiskGrpGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("��ѡ��Ҫɾ�������̣�");
        return;
    }
    var confimObj = confirm("ȷ��ɾ����");
	   if(!confimObj){
		   return;
	   }
    var showStr="����ɾ�����̶�����Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
    }else
    { 
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	query();
	refreshEmptyDate();
  }
}



function beforeSubmit()
{
  //��Ӳ���	
  if(!verifyInput()){
	  return false;
  }
  return true;
} 


function isEmptyDate(){
    var RiskCode = document.getElementById("RiskCode"),
    	RiskGrpCode = document.getElementById("RiskGrpCode");
    if(!RiskCode){
	alert("����д���ֱ��� ��");
	return false;
    }
    if(!RiskGrpCode){
	alert("����д�����飡");
	return false;
    }
    return true;
}

function refreshEmptyDate(){   //�������޸ġ�ɾ��������ı�
    document.getElementById("RiskCode").value="";
    document.getElementById("RiskGrpCode").value="";
}



function addCheckDateIsExist(){
    var RiskCode = document.all('RiskCode').value;
    var RiskGrpCode = document.all('RiskGrpCode').value;
    strSql ="SELECT count(RiskCode) FROM LDVATGrp WHERE riskcode = '"+RiskCode+"' AND riskgrp = '"+RiskGrpCode+"'" ;
    var brr = easyExecSql(strSql );
    if(brr[0]>0){
	alert("�Ѵ�����Ҫ�����������Ϣ����ֱ�Ӳ�����");
	return false;
    }
    return true;
}