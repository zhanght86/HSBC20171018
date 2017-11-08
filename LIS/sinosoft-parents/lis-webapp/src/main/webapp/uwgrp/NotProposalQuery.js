

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.NotProposalQueryInputSql";

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
 // var aSQL="select grpcontno,appntno,appntname,cvalidate,prtno From lccont where insuredno='"+customerNo+"' and appflag='0'";	
  
  		var sqlid1="NotProposalQueryInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);
  
  turnPage.queryModal(mySql1.getString(), ContGrid); 

}



function queryPersonInfo(){
 // var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
  var sqlid2="NotProposalQueryInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
	//alert("ContGrid.getRowColData(0,1)=="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
//	var aSQL = "select a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,c.PassFlag,d.SuppRiskScore "
//	+"from lcpol a,lmrisk b,LCUWMaster c,lcprem d "
//	+"where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and c.polno=a.polno and d.polno=a.polno ";

  //var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,"
  //    +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
  //     +" d.SuppRiskScore "
  //	     +" from lcpol a,lmrisk b,lcprem d "
  //	     +" where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and d.polno=a.polno ";
//    var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,"
//       +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
//       +" (select d.SuppRiskScore from lcprem d where d.polno=a.polno and d.SuppRiskScore is not null and rownum=1 ) as newSuppRiskScore"
//	     +" from lcpol a,lmrisk b "
//	     +" where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode";
//  initPolGrid();
//  alert(aSQL);
  var prtno=ContGrid.getRowColData(ContGrid.getSelNo()-1,5);
  //alert(prtno);
  //var aSQL="select grpcontno,polno,cvalidate,riskcode,amnt,prem,uwflag from lcpol where prtno='"+prtno+"' and insuredno='"+customerNo+"'" ;

var sqlid3="NotProposalQueryInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(prtno);
		mySql3.addSubPara(customerNo);

//  initPolGrid();
//  alert(aSQL);
  turnPage2.queryModal(mySql3.getString(), PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  //fm.button1.disabled="";
  //判断是否有财务缴费信息

	  //判断是否有核保结论
	  //haveUWResult();
  	
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
//  alert(aSQL);
  
  var sqlid4="NotProposalQueryInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
  
  var arrResult = easyExecSql(mySql4.getString());
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
     retutn;	 
   }
   
  // var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  
  var sqlid5="NotProposalQueryInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
  
  
   var BankFlag=""
   var brrResult = easyExecSql(mySql5.getString());
   if(brrResult!=null)
    {
   	  BankFlag = brrResult[0][0];
   	}
   	alert(BankFlag);

	 window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
} 


function showTempFee() 
{
	 var tsel=ContGrid.getSelNo()-1;	 
	 if(tsel<0){
     alert("请选择保单!");
     retutn;	  
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
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
}    

//核保查询
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("请选择保单!");
     retutn;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
   //var strSQL="select prtno from LCCont where contno='"+ContNo+"'";
    var sqlid6="NotProposalQueryInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(ContNo);
   
   
   var arrResult = easyExecSql(mySql6.getString());
   var proposalcontno=arrResult[0][0];
   
	window.open("./UWQueryMain1.jsp?ProposalContNo="+proposalcontno,"window1",sFeatures); 
	//window.open("./UWQueryMain1.jsp?ContNo="+ContNo,"window1"); 
}

