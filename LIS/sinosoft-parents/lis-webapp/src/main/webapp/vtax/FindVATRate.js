var turnPage = new turnPageClass(); 
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
var showInfo; 
function checkBusiType()
{
	if (document.all('BusiTypeCode').value==""||document.all('BusiTypeCode').value==null)
	{
		alert("请先选择一个业务类型")
		return false;
	}
}

function query()
{
//	var feetype=document.getElementById("BusiTypeCode").value;
	
	var arr=new Array();
	var strSQL = "";
    mySql = new SqlClass();
    mySql.setResourceName("vrate.VatRate");
    mySql.setSqlId("VATRateSql1");
    mySql.addSubPara(document.getElementById("FeeTypeCode").value);   			
    initWorkFlowGrid();
    turnPage.queryModal(mySql.getString(), WorkFlowGrid);
    emptyData();
}


function deleteClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	   alert("请选择要删除的流程！");
	   return;
	}
	var confimObj = confirm("确定删除吗？");
	   if(!confimObj){
		   return;
	   }
	//需要做校验    附表中存在删除的id
	if(deleteCheckDateIsExist()){
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
	}
       
}

function afterSubmit( FlagStr, content )
{
  if(typeof(showInfo)=="object"){
      showInfo.close();
//      if(typeof(showInfo.parent)=="object" && typeof(showInfo.parent) != "unknown"){
//	  showInfo.parent.focus();
//	  if(typeof(showInfo.parent.parent)=="object" && typeof(showInfo.parent.parent) != "unknown"){
//	      showInfo.parent.parent.blur();
//	      }
//	  }
      }

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
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();
    emptyData();
    query();
  }
}

function showVATDetail()
{
	var tSelNo = WorkFlowGrid.getSelNo();
	
	if(tSelNo!=-1)
	{
		var tFeeTypeCode=WorkFlowGrid.getRowColData(tSelNo-1,1);
		document.all('FeeTypeCode1').value = tFeeTypeCode;
		var tFeeTypeName=WorkFlowGrid.getRowColData(tSelNo-1,2);
		document.all('FeeTypeName1').value = tFeeTypeName;
		var tTaxible=WorkFlowGrid.getRowColData(tSelNo-1,3);
		document.all('TaxibleName').value = tTaxible;
		document.all('Taxible').value = tTaxible;
		if(tTaxible == 'Y'){
		   document.all('Taxible').value = 'Y';
		}else{
		    document.all('Taxible').value = 'N';
		}	
		
		var tRiskRele=WorkFlowGrid.getRowColData(tSelNo-1,4);
		document.all('RiskReleName').value = tRiskRele;
		if(tRiskRele == 'Y'){
		    document.all('RiskRele').value = 'Y';
		}else{
		    document.all('RiskRele').value = 'N';
		}
		var tRecordID = WorkFlowGrid.getRowColData(tSelNo-1,5);
		document.all('RecordID').value = tRecordID;
		
		
	}
}

function newClick(){
	if(checkEmptyDate() && addCheckDateIsExist())
	{
		document.all('fmtransact').value='INSERT||MAIN';
                document.getElementById("fm").submit(); //提交
	}
	unLockPage();

}


function beforeSubmit()
{
  //添加操作	
  if(!verifyInput()){
	  return false;
  }
  return true;
} 

function updateClick(){
	
    var selno = WorkFlowGrid.getSelNo()-1;
	   if (selno <0)
	   {
	      alert("请选择要修改的流程！");
	      return;
	   }
	   if(checkEmptyDate() && addCheckDateIsExist()){
	       document.all('fmtransact').value='UPDATE||MAIN';
	       var i = 0;
	       var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	       document.getElementById("fm").submit(); //提交
	   }
  
}

function configTaxRate(){
	var FeeTypeCode = document.all('FeeTypeCode1').value;
	var configID = document.all('RecordID').value;
	var FeeTypeName = document.all('FeeTypeName1').value;
	if(FeeTypeCode==null||FeeTypeCode=='')
	{
		alert('请选择要配置的查询结果!');
		return false;
	}
	var urlStr = "./ConfigVATRateMain.jsp?FeeTypeCode="+FeeTypeCode+"&configID="+configID;
	tBusiCode ='';
	window.open(urlStr, "", sFeatures);
}


function configRiskGrp(){
	
	var urlStr = "./ConfigVATRiskGrpMain.jsp";
	tBusiCode ='';
	window.open(urlStr, "", sFeatures);
}


function emptyData()
{
		document.all('FeeTypeCode1').value = "";
		document.all('FeeTypeName1').value = "";
		document.all('Taxible').value = "";
		document.all('RiskRele').value = "";
		document.all('RecordID').value = "";
		document.all('TaxibleName').value = "";
		document.all('RiskReleName').value = "";
		
}

function checkEmptyDate(){
    var FeeTypeCode1 = document.all('FeeTypeCode1').value;
    var Taxible = document.all('Taxible').value;
    var RiskRele = document.all('RiskRele').value;
    if(!FeeTypeCode1){
	alert("请选择费用编码！");
	return false;
    }
    if(!Taxible){
	alert("请选择应税类型！");
	return false;
    }
    if(!RiskRele){
	alert("请选择险种相关！");
	return false;
    }
    return true;
    
}

function addCheckDateIsExist(){
    var FeeTypeCode1 = document.all('FeeTypeCode1').value,
    Taxible = document.getElementById("Taxible").value,
    RiskRele = document.getElementById("RiskRele").value;
//    strSql ="SELECT COUNT(FeeTypeCode) FROM LDVATConfig WHERE FeeTypeCode='"+FeeTypeCode1+"' and IsTaxable = '"+Taxible+"' and IsReleToRiskType = '"+RiskRele+"'";
    var strSql = "";
    mySq2 = new SqlClass();
    mySq2.setResourceName("vtax.FindVATRateSql");
    mySq2.setSqlId("FindVATRateSql1");
    mySq2.addSubPara(FeeTypeCode1);
    mySq2.addSubPara(Taxible);
    mySq2.addSubPara(RiskRele);
    strSql = mySq2.getString();
    var brr = easyExecSql(strSql );
    if(brr[0]>0){
	alert("已存在您要添加的费用类型！可直接操作。");
	return false;
    }
    return true;
}

function deleteCheckDateIsExist(){
    var RecordID = document.all('RecordID').value;
//    strSql ="SELECT COUNT(b.id) FROM LDVATTaxConfig a,LDVATConfig b WHERE a.configid = b.id AND a.configid = '"+RecordID+"'";
    var strSql = "";
    mySq3 = new SqlClass();
    mySq3.setResourceName("vtax.FindVATRateSql");
    mySq3.setSqlId("FindVATRateSql2");
    mySq3.addSubPara(RecordID);
    strSql = mySq2.getString();
    var brr = easyExecSql(strSql );
    if(brr[0]>0){
	alert("删除前请确保您没有使用此编码!");
	return false;
    }
    return true;
}