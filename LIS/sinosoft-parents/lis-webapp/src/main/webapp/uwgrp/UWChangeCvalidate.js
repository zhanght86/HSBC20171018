//***********************************************
//程序名称：AmntAccumulate.js
//程序功能：保额累计
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWChangeCvalidateInputSql";

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 
 //提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  var tCvalidate = fm.Cvalidate.value ;
 
  if(tCvalidate == "")
    { 
      alert("请输入生效日期");      
      
    }
  fm.action= "./UWChangeCvalidateChk.jsp";
  fm.submit(); //提交
}


function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="操作成功";
  	showInfo.close();
  	alert(showStr);
  	initForm();
  	
  	
    //执行下一步操作
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
  var aSQL=" select ContNo,AppntName,AgentCode,ManageCom,PolApplyDate,Prem,Amnt from LCCont where 1 = 1"
          +" and ContNo = '"+tContNo+"'";         
 
*/
		var sqlid1="UWChangeCvalidateInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		
  turnPage.queryModal(mySql1.getString(), ContGrid);

}



function queryPersonInfo(){
  //var aSQL = "select a.ContNo,a.Cvalidate from lccont a where a.ContNo='"+tContNo+"'";	
 
 var sqlid2="UWChangeCvalidateInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
 
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('ContNo').value = arrResult[0][0];
  fm.all('Cvalidate').value = arrResult[0][1];
}

function getContDetail(){
	
	
}

function getPolInfo(){
	
/*
  var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,"
       +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
       +" d.SuppRiskScore "
	     +" from lcpol a,lmrisk b,lcprem d "
	     +" where a.contno='"+tContNo+"' and b.riskcode=a.riskcode and d.polno=a.polno ";
	     */
	     /*
	var aSQL = "select polno,PolApplyDate,CValiDate,RiskCode,Prem,Amnt from LCPol where 1=1 "
       + " and ContNo = '"+tContNo+"'";
       */
       
var sqlid3="UWChangeCvalidateInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
 
  turnPage2.queryModal(mySql3.getString(), PolGrid);
	
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
  //alert(aSQL);
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
	 window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"&ContNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1), "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}

function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1),"window11",sFeatures);
}

