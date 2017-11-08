//***********************************************
//程序名称：UUBeforeHealthQ.js
//程序功能：既往体检信息查询
//创建日期：2005-06-01 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWBeforeHealthQInputSql";

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	if (FlagStr == "Fail" )
	{             
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
	if (FlagStr == "Succ")
	{
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
	
}



/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}



/*********************************************************************
 *  查询已承保保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryCont(){
	/*
  var aSQL = " select distinct a.contno,(select AppntNo from lccont c where c.contno=a.contno),a.appname,a.managecom,a.agentcode,a.agentname from LCPENotice a where 1=1"
           + " and a.CustomerNo = '"+tCustomerNo+"'" 
          ; 
      
     */ 
		var sqlid1="UWBeforeHealthQInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tCustomerNo);
      
           
 	turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无既往体检信息！");
    return "";
  }
  
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
	
  //var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+tCustomerNo+"'";	
  
  var sqlid2="UWBeforeHealthQInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tCustomerNo);
  
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}



function healthInfoClick(){

	var tFlag = "3";

 window.open("./UWManuHealthQMain.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo+"&Flag="+tFlag,"window1",sFeatures);
  	
}

//判断是否有财务缴费信息
function haveFeeInfo(){
	return;
}

//判断是否有影像资料
function havePicData(){
	return;
}

//判断是否有核保结论
function haveUWResult(){
	//var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  
   var sqlid3="UWBeforeHealthQInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
  
  //alert(aSQL);
  var arrResult = easyExecSql(mySql3.getString());
  if(arrResult!=null){
    fm.button4.disabled="";	
    return;
  }
  else{
    fm.button4.disabled="true";	
    return;
  }
	return;
}

function getContDetailInfo(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
	 window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}

function showTempFee() 
{
	 var tsel=ContGrid.getSelNo()-1;	 
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   } 
   var tAppntNo=ContGrid.getRowColData(tsel,2);
   var tAppntName=ContGrid.getRowColData(tsel,3);
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+tAppntNo+"&AppntName="+tAppntName,"window11",sFeatures);
} 
 
//影像资料查询
function showImage(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
}


//核保查询
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1",sFeatures); 
}