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
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";


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
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

	}
	if (FlagStr == "Succ")
	{
  	showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

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
	
	var sqlid826142040="DSHomeContSql826142040";
var mySql826142040=new SqlClass();
mySql826142040.setResourceName("uw.GroupNotProposalQueryInputSql");//指定使用的properties文件名
mySql826142040.setSqlId(sqlid826142040);//指定使用的Sql的id
mySql826142040.addSubPara(AppntNo);//指定传入的参数
var aSQL=mySql826142040.getString();
	
//  var aSQL="select distinct b.grpcontno,a.grpname,a.cvalidate,"
//  +" decode(nvl(substr(trim(a.state),4,1),'z'),'z','不逾期','1','逾期作废','逾期' ),"
//   +" a.prtno from lcgrppol a,lcgrpcont b  where customerno='"+AppntNo+"'and a.grpcontno=b.grpcontno and a.appflag='0'";
  turnPage.queryModal(aSQL, ContGrid);
  


}



function queryPersonInfo(){

  var sqlid826142132="DSHomeContSql826142132";
var mySql826142132=new SqlClass();
mySql826142132.setResourceName("uw.GroupNotProposalQueryInputSql");//指定使用的properties文件名
mySql826142132.setSqlId(sqlid826142132);//指定使用的Sql的id
mySql826142132.addSubPara(AppntNo);//指定传入的参数
var aSQL=mySql826142132.getString();
  
//  var aSQL = "select appntno,grpname from lcgrpcont where appntno='"+AppntNo+"'";	
  var arrResult = easyExecSql(aSQL);
  document.all('AppntNo').value = arrResult[0][0];
  document.all('GrpName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}

function getPolInfo(){
	
	var sqlid826142228="DSHomeContSql826142228";
var mySql826142228=new SqlClass();
mySql826142228.setResourceName("uw.GroupNotProposalQueryInputSql");//指定使用的properties文件名
mySql826142228.setSqlId(sqlid826142228);//指定使用的Sql的id
mySql826142228.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//指定传入的参数
var aSQL=mySql826142228.getString();
  
//  var aSQL= "select distinct a.grpcontno,a.grpproposalno,b.riskcode,b.riskname,"
//	   +"(case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)then '在销' else '停售' end),"
//	   +"a.amnt,a.mult,"
//	   +" ( select codename from ldcode where codetype='tdhbconlusion' and code in (select"
//	  	+" distinct PassFlag from lcguwmaster where grppolno=a.grppolno and PassFlag is not null"
//	   +" and rownum=1) )as newPassFlag"
//	       +" from lcgrppol a, lmrisk b"
//	       +" where a.grpcontno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'and a.riskcode=b.riskcode";
  turnPage2.queryModal(aSQL, PolGrid);
	
}


function contInfoClick(){
  getPolInfo();
  document.getElementById("fm").button1.disabled="";
  //判断是否有财务缴费信息
haveFeeInfo();
	  //判断是否有核保结论
	  //haveUWResult();
  	
}

//判断是否有财务缴费信息
function haveFeeInfo(){
	var sqlid826142320="DSHomeContSql826142320";
var mySql826142320=new SqlClass();
mySql826142320.setResourceName("uw.GroupNotProposalQueryInputSql");//指定使用的properties文件名
mySql826142320.setSqlId(sqlid826142320);//指定使用的Sql的id
mySql826142320.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo()-1,1));//指定传入的参数
var fSQL=mySql826142320.getString();
	
//	var fSQL="select * from ljtempfee where otherno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
  var arrResult = easyExecSql(fSQL);
	
	if(arrResult!=null){
    document.getElementById("fm").button3.disabled="";	
    return;
  }
  else{
    document.getElementById("fm").button3.disabled="true";	
    return;
  }
	return;
}

//判断是否有影像资料
function havePicData(){
	return;
}

//判断是否有核保结论
//function haveUWResult(){
//	var aSQL = "select * from LCCUWMaster where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
////  alert(aSQL);
//  var arrResult = easyExecSql(aSQL);
//  if(arrResult!=null){
//    fm.button4.disabled="";	
//    return;
//  }
//  else{
//    fm.button4.disabled="true";	
//    return;
//  }
//	return;
//}

function getContDetailInfo(){
	 		 
   // if(PolGrid.getSelNo()=='')
  // {
   //	alert("请选择一个险种");
  // 	return;
   // }
   
    var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
    
    window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window1",sFeatures);
}

function showTempFee()
{
	 var tsel=ContGrid.getSelNo()-1;
	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
   var tAppntName=ContGrid.getRowColData(tsel,2);
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&AppntNo="+AppntNo+"&AppntName="+tAppntName+"&ContType=2","window11",sFeatures);
	
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
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
}
function UWQuery(){
	
	var tsel=ContGrid.getSelNo()-1;

	 if(tsel<0){
     alert("请选择保单!");
     return;	 
   }
   var ContNo=ContGrid.getRowColData(tsel,1);
	window.open("./UWQueryMain.jsp?ContNo="+ContNo,"window1",sFeatures); 
}

//操作履历查询
function OperationQuery()
{
	
	var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var ContNo = ContGrid.getRowColData(tSel - 1,1);
	    var PrtNo = ContGrid.getRowColData(tSel - 1,5);				
		
		if (PrtNo == "" || ContNo == "") return;

	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo,"",sFeatures);	
  }										
} 




