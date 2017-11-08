//程序名称：UWCustomerQuality.js
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.UWCustomerQualitySql";
function queryClick()
{
	// 初始化表格
	//initQuestGrid();
	//initContent();
	
	// 书写SQL语句
	/*
  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
             + getWherePart('Operator', 'Operator')
             + getWherePart('MakeDate', 'MakeDate');
	*/
	var sqlid1="UWCustomerQualitySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(PrtNo);
		mySql1.addSubPara(ContNo);
		mySql1.addSubPara(OperatePos);
		mySql1.addSubPara(fm.Operator.value);
		mySql1.addSubPara(fm.MakeDate.value);
	
	
  turnPage.queryModal(mySql1.getString(), QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = fm.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function UpdateClick() {
  if (trim(fm.Remark.value) == "") {
    alert("必须填写备注内容！");
    return;
  }
  
  fm.PrtNo.value = PrtNo;
  fm.ContNo.value = ContNo;
//  fm.OperatePos.value = OperatePos; 
  
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
}

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="customer"){
 	//alert(fm.customer.value);
 	//var strSQL="select * from LDPerson where trim(CustomerNo)=trim('"+fm.customer.value+"')";
 	
 	var sqlid2="UWCustomerQualitySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.customer.value);

 	
 	//alert(strSQL);
   var arrResult=easyExecSql(mySql2.getString());
   //alert(arrResult);                         
   try{fm.all('CustomerNo').value= arrResult[0][0];}catch(ex){};         
   try{fm.all('Name').value= arrResult[0][1];}catch(ex){};          
   try{fm.all('Sex').value= arrResult[0][2];}catch(ex){};     
   try{fm.all('BirthDay').value= arrResult[0][3];}catch(ex){};            
   try{fm.all('IDType').value= arrResult[0][4];}catch(ex){};              
   try{fm.all('IDNumber').value= arrResult[0][5];}catch(ex){};                
   try{fm.all('BlacklistFlagNo').value= arrResult[0][31];}catch(ex){};
  
  
   try{   
     if(arrResult[0][31]==0) fm.all('BlacklistFlagName').value="正常";  	
         else
         fm.all('BlacklistFlagName').value="黑名单";   
      }
      
   catch(ex){};       
  
  
   try{fm.all('Remark').value= arrResult[0][33];}catch(ex){};        
   
   return;       
 }	
// if(cCodeName=="paymode"){
//   	if(fm.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}


function returnParent(){
  top.close();
	
}