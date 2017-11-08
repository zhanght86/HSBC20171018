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
//	lockPage("正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
	
	if(beforeSubmit())
	{
	//非空验证
	//判断是否存在
	if(checkEmptyDate() && addCheckDateIsExist()){
		document.all('fmtransact').value='INSERT||MAIN';
		var i = 0;
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		document.getElementById("fm").submit(); //提交
	}
    
  }
//	unLockPage();
	
}

function updateClick(){

//	lockPage("正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
		
		if(beforeSubmit())
		{
			//必须选中当前table中的一列
			//判断是否存在
			//非空验证
			if(isSelectDate("更改") && addCheckDateIsExist() && checkEmptyDate()){
				lockPage("正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面");
				document.all('fmtransact').value='UPDATE||MAIN';
				var i = 0;
				var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
				var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
				document.getElementById("fm").submit(); //提交
				unLockPage();
			}
	   }
}

function deleteClick()
{
 	   var selno = TaxRateGrid.getSelNo()-1;
 	 //必须选中当前table中的一列
 	   if( isSelectDate("删除")){
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
 		   document.all('fmtransact').value='DELETE||MAIN';
 		   document.getElementById("fm").submit();
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
	emptyData()
  }
}
function checkEmptyDate(){
    var TaxRate = document.all('TaxRate').value,
    ManageCom = document.all('ManageCom').value,
    StartDate = document.all('StartDate').value,
    EndDate = document.all('EndDate').value,
    reg =  /^(\d{1,2}|\d{1,2}\.\d{1,4})$/;  //税率正则 

    if(!TaxRate){
	alert("请填写税率！");
	return false;
    }
    if(!ManageCom){
	alert("请选择机构！");
	return false;
    }
    if(!StartDate){
    	alert("请选择起始日期！");
    	return false;
    }
    if(!EndDate){
    	alert("请选择截至日期！");
    	return false;
    }
    if(EndDate<StartDate){
    	alert("截至日期应大于起始日期！");
    	return false;
    }
    if(!reg.test(TaxRate)){  
    	alert("填写的税率格式不正确！");
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
	alert("已存在您要添加税率信息！可直接操作。");
	return false;
    }
    return true;
}

function isSelectDate(text){
	   var selno = TaxRateGrid.getSelNo()-1;
	   if (selno <0)
	   {
	      alert("请选择要"+text+"的流程！");
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

