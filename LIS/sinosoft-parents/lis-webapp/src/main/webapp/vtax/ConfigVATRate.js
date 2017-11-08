var turnPage = new turnPageClass(); 

function query()
{
	
	var arr=new Array();
	var strSQL = "";
    mySql = new SqlClass();
    mySql.setResourceName("vrate.VatRate");
    mySql.setSqlId("VATRateSql2");
    mySql.addSubPara(document.getElementById("FeeTypeCode").value); 
    mySql.addSubPara(document.getElementById("configID").value);
    initTaxRateGrid();
	turnPage.queryModal(mySql.getString(), TaxRateGrid);
}



function showVATRateDetail(){
var tSelNo = TaxRateGrid.getSelNo();
	
	if(tSelNo!=-1)
	{
		var FeeTypeCode=TaxRateGrid.getRowColData(tSelNo-1,1);
		document.all('TFeeTypeCode').value = FeeTypeCode;
		
		var FeeTypeName=TaxRateGrid.getRowColData(tSelNo-1,2);
		document.all('TFeeTypeName').value = FeeTypeName;
		
		var RiskGrpCode=TaxRateGrid.getRowColData(tSelNo-1,3);
		document.all('RiskGrpCode').value = RiskGrpCode;
		
		var ManageCom=TaxRateGrid.getRowColData(tSelNo-1,4);
		document.all('ManageCom').value = ManageCom;
		
		var TaxRate = TaxRateGrid.getRowColData(tSelNo-1,5);
		document.all('TaxRate').value = TaxRate;
		
		var tStartDate=TaxRateGrid.getRowColData(tSelNo-1,6);
		document.all('StartDate').value = tStartDate;
		
		var tEndDate=TaxRateGrid.getRowColData(tSelNo-1,7);
		document.all('EndDate').value = tEndDate;
		
		var TaxID = TaxRateGrid.getRowColData(tSelNo-1,8);
		document.all('TaxID').value = TaxID;
	}
}

function newClick(){
//	lockPage("���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
	
	if(beforeSubmit())
	{
	//�ǿ���֤
	//�ж��Ƿ����
	if(checkEmptyDate() && addCheckDateIsExist()){
		document.all('fmtransact').value='INSERT||MAIN';
		var i = 0;
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		document.getElementById("fm").submit(); //�ύ
	}
    
  }
//	unLockPage();
	
}

function updateClick(){

//	lockPage("���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
		
		if(beforeSubmit())
		{
			//����ѡ�е�ǰtable�е�һ��
			//�ж��Ƿ����
			//�ǿ���֤
			if(isSelectDate("����") && addCheckDateIsExist() && checkEmptyDate()){
				lockPage("���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��");
				document.all('fmtransact').value='UPDATE||MAIN';
				var i = 0;
				var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
				var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
				document.getElementById("fm").submit(); //�ύ
				unLockPage();
			}
	   }
}

function deleteClick()
{
 	   var selno = TaxRateGrid.getSelNo()-1;
 	 //����ѡ�е�ǰtable�е�һ��
 	   if( isSelectDate("ɾ��")){
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
 		   document.all('fmtransact').value='DELETE||MAIN';
 		   document.getElementById("fm").submit();
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
	emptyData()
  }
}
function checkEmptyDate(){
    var TaxRate = document.all('TaxRate').value,
    ManageCom = document.all('ManageCom').value,
    StartDate = document.all('StartDate').value,
    EndDate = document.all('EndDate').value,
    reg =  /^(\d{1,2}|\d{1,2}\.\d{1,4})$/;  //˰������ 

    if(!TaxRate){
	alert("����д˰�ʣ�");
	return false;
    }
    if(!ManageCom){
	alert("��ѡ�������");
	return false;
    }
    if(!StartDate){
    	alert("��ѡ����ʼ���ڣ�");
    	return false;
    }
    if(!EndDate){
    	alert("��ѡ��������ڣ�");
    	return false;
    }
    if(EndDate<StartDate){
    	alert("��������Ӧ������ʼ���ڣ�");
    	return false;
    }
    if(!reg.test(TaxRate)){  
    	alert("��д��˰�ʸ�ʽ����ȷ��");
    	return false;
    }
    return true;
    
}


function addCheckDateIsExist(){
    var ConfigID = document.all('ConfigID').value;
    var ManageCom = document.all('ManageCom').value;
    var StartDate = document.all('StartDate').value;
    var EndDate = document.all('EndDate').value;
    strSql ="SELECT COUNT(*) FROM LDVATTaxConfig WHERE ConfigID ='"+ConfigID+"' AND ManageCom = '"+ManageCom+"'  AND StartDate = '"+StartDate+"' and EndDate = '"+EndDate+"'" ;
    var brr = easyExecSql(strSql );
    if(brr[0]>0){
	alert("�Ѵ�����Ҫ���˰����Ϣ����ֱ�Ӳ�����");
	return false;
    }
    return true;
}

function isSelectDate(text){
	   var selno = TaxRateGrid.getSelNo()-1;
	   if (selno <0)
	   {
	      alert("��ѡ��Ҫ"+text+"�����̣�");
	      return false;
	   }
	   return  true
}

function emptyData()
{
		document.all('TaxRate').value = "";
		document.all('RiskGrpCode').value = "";
		document.all('ManageCom').value = "";
		document.all('StartDate').value = "";	
		document.all('EndDate').value = "";	
}

