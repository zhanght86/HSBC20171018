//***********************************************
//程序名称：GroupNotProposalQuery.js
//程序功能：团体未承保保单查询
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();



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
  var aSQL="select distinct b.grpcontno,a.grpname,a.cvalidate,a.state from lcgrppol a,lcgrpcont b  where customerno='"+AppntNo+"'and a.grpcontno=b.grpcontno and a.appflag='0'";
  turnPage.queryModal(aSQL, ContGrid);
  


}



function queryPersonInfo(){

  var aSQL = "select appntno,grpname from lcgrpcont where appntno='"+AppntNo+"'";	
  var arrResult = easyExecSql(aSQL);
  fm.all('AppntNo').value = arrResult[0][0];
  fm.all('GrpName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
   
  var aSQL= "select distinct a.grpcontno,a.grpproposalno,b.riskcode,b.riskname,a.state,a.amnt,a.mult,(select distinct PassFlag from LCUWMaster where polno=a.grpcontno and PassFlag is not null and rownum=1) as newPassFlag"
       +" from lcgrppol a, lmrisk b,lcprem d"
       +" where a.grpcontno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'and a.riskcode=b.riskcode";
     
  turnPage2.queryModal(aSQL, PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  fm.button1.disabled="";
  //判断是否有财务缴费信息

	  //判断是否有核保结论
	  haveUWResult();
  	
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
	var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
//  alert(aSQL);
  var arrResult = easyExecSql(aSQL);
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
	 		 
   // if(PolGrid.getSelNo()=='')
  // {
   //	alert("请选择一个险种");
  // 	return;
   // }
   
    var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
    
    window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window1");
}

function showTempFee()
{
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var tAppntName=ContGrid.getRowColData(tsel,2);
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+AppntNo+"&AppntName="+tAppntName,"window11");
	
}
 
//影像资料查询
function showImage(){ 
	
	 var tsel=ContGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
}
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1"); 
}



