
//程序名称：EsDocManage.js
//程序功能：
//创建日期：2009-09-09
//创建人  yanglh
//更新记录：  更新人    更新日期     更新原因/内容


//               该文件中包含客户端需要处理的函数和事件

var arrDataSet 
var tArr;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var arrDataSet;
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("操作控制数据丢失！");
  //}
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
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
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
    divReason.style.display="none";   
    if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
    {}
    else 	
    	easyQueryClick();
  }
  
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在EsDocManageNew.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  alert("增加功能暂时不提供，请使用EasyScan上载单证数据!");
  //下面增加相应的代码
  //mOperate="INSERT||MAIN";
//  showDiv(operateButton,"false"); 
//  showDiv(inputButton,"true"); 
//  fm.fmtransact.value = "INSERT||CODE" ;
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if(fm.PrtNo.value == null || fm.PrtNo.value == '')
	{
		alert("请先查询！");
		return false;
	}
	if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
	{
		alert("请先录入修改后的印刷号码！");
		return false;
	}
  //下面增加相应的代码
  if (confirm("您确实想修改该印刷号码吗?"))
  {
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||MAIN";

  	document.getElementById("fm").submit(); //提交
  }
}

function updateClick1()
{
	
	if(getCount()!=1){
		alert("请选择一页扫描件");
		return false;	
	}
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||DOCID";

  	document.getElementById("fm").submit(); //提交
  }
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{

  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  if(fm.DOC_CODE.value=="")
  {
      alert("请输入单证号码！");
      return;
  }
  
  easyQueryClick();

}

// 查询按钮
function easyQueryClick()
{						 
//	var strSQL = " select docid,(select bussno from es_doc_relation where docid=a.docid ),'',(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)),'',scanoperator,"
//					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
//					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
//					+ " from ES_DOC_MAIN a where 1=1 " 
//					+ " and doccode = '" + fm.DOC_CODE.value + "' and busstype = '" + fm.busstype.value + "' "
//					+ " and managecom like '" + fm.MngCom.value + "%' "
//					+ " order by makedate,maketime";		 
	
	var sqlid0="EsDocManageNewSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(fm.DOC_CODE.value);//指定传入的参数
	mySql0.addSubPara(fm.busstype.value);//指定传入的参数
	mySql0.addSubPara(fm.MngCom.value);//指定传入的参数
	var strSQL=mySql0.getString();
	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		CodeGrid.clearData();
		alert("查询失败！");
		return false;
  }
	//查询成功则拆分字符串，返回二维数组
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'PrtNo' ).value = 			arrResult[0][1];
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		fm.deletebutton.disabled=false;
		fm.DelReason.value='';
	}
	else
	{
		initInpBox1();
	}
		
  	queryPages(fm.DOC_CODE.value);
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	//提示需要录入删除原因
//	var strSQL = " select count(*) from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"'"; 
	
	var sqlid1="EsDocManageNewSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.DOC_CODE.value);//指定传入的参数
	var strSQL=mySql1.getString();
	
	var brr = easyExecSql(strSQL);
    if ( brr )  
    {
//    		strSQL = " select 1 from lccont where prtno='"+fm.DOC_CODE.value+"' and appflag='1'";
    		
    		var sqlid2="EsDocManageNewSql2";
    		var mySql2=new SqlClass();
    		mySql2.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
    		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    		mySql2.addSubPara(fm.DOC_CODE.value);//指定传入的参数
    		strSQL=mySql2.getString();
    		
			var brr1 = easyExecSql(strSQL);
		    if ( brr1 )  
		    {
		    	alert("该扫描件所属投保单已经签单，不能删除所有扫描件！");
				return false;
		    }
    	//alert(brr[0][0]);
    	if( brr[0][0]> 0 )
    		if(fm.DelReason.value == null || fm.DelReason.value == '')
			{
				alert("请先录入删除原因！");
				divReason.style.display="";
				return false;
			}
    }
    else
    {
    	alert("该印刷号下没有扫描件可以删除！");
    	return false;
    }
    
	//下面增加相应的删除代码
  if (confirm("您确实想删除该印刷号下所有单证吗?"))
  {	 
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||MAIN";

  	document.getElementById("fm").submit(); //提交
  	initForm();
  }
  else
  {
    //mOperate="";
    alert("您取消了删除操作！");
  }
}   

function deleteClick1()
{
	if(getCount()!=1){
		alert("请选择一页扫描件");
		return false;
	}
	//校验如果该单证为印刷号下最后一张单证则提示需要录入删除原因
//	var strSQL = " select count(*) from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"'";
	
	var sqlid3="EsDocManageNewSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.DOC_CODE.value);//指定传入的参数
	var strSQL=mySql3.getString();
	
	var brr = easyExecSql(strSQL);
    if ( brr )  
    {
    	//alert(brr[0][0]);
    	if( brr[0][0]== 1 )
    	{
    		if(fm.DelReason.value == null || fm.DelReason.value == '')
				{
				alert("请先录入单证删除原因！");
				divReason.style.display="";
				return false;
				}
			}
    }
    else
    {
    	alert("该印刷号下没有扫描件可以删除！");
    	return false;
    } 
	
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
		var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||DOCID";

  	document.getElementById("fm").submit(); //提交

  	//initForm();
  }
} 

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}
          
       
// 查询按钮
function queryPages(strDoc_Code)
{
	// 书写SQL语句
//	var strSQL = "select pageid,docid,pagecode,hostname,pagename,pageflag,"
//					+ "picpathftp,picpath,operator,makedate,maketime "
//					+ ",(select subtype from es_doc_main where docid=ES_DOC_PAGES.docid),scanno"
//					+ " from ES_DOC_PAGES where 1=1 " 
//					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' ) "
//					+ " order by docid,pagecode";
	
	var sqlid4="EsDocManageNewSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(strDoc_Code);//指定传入的参数
	var strSQL=mySql4.getString();
	
	//prompt('',strSQL);
	turnPage1.queryModal(strSQL, CodeGrid, 0, 0, 10 );
	
}



//Click事件 点击'保存修改'按钮时触发，实现保存MultiLine当前修改的数据 非通用
function saveUpdate()
{
	if(getCount()<=0){
		alert("请至少选择一页扫描件");
		return false;
	}
  if (confirm("您确实想保存修改吗?"))
  {
	var i = 0;
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	fm.fmtransact.value = "UPDATE||PAGES";
  	
  	//选中所有行
  	//CodeGrid.checkBoxAll();

  	document.getElementById("fm").submit(); //提交
  	//initForm();
  }
}        
//Click事件 点击'删除选中'按钮时触发，实现删除选中的MultiLine行数据 非通用
function deleteChecked()
{
	if(getCount()<=0){
		alert("请至少选择一页扫描件");
		return false;
	}
//	var strSQL = " select (case when count(*) is not null then count(*) else 0 end) from ES_DOC_PAGES where docid in (select docid from ES_DOC_MAIN where doccode='"+fm.DOC_CODE.value+"')"; 	
	
	var sqlid5="EsDocManageNewSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("easyscan.EsDocManageNewSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.DOC_CODE.value);//指定传入的参数
	var strSQL=mySql5.getString();
	
	var brr = easyExecSql(strSQL);
	if ( brr && (getCount() - brr[0][0] == 0))  
	{	    	
	 	alert("如需删除该印刷号下所有单证页信息，请使用“删除单证”按钮！");
	 	return false;
	}
  if (confirm("您确实想删除选中的单证页数据吗?"))
  {
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	fm.fmtransact.value = "DELETE||PAGES";

  	document.getElementById("fm").submit(); //提交
  	//重新查询
  	//queryPages(document.all( 'DOC_ID' ).value);
  	//initForm();
  }
}

function getCount()
{
	//得到复选框中选中了多少项
	var count = CodeGrid.mulLineCount;
	var flag = 0;
	for(var index=0; index< count; index++)
	{
	   if(CodeGrid.getChkNo(index)==true)
	   {
	   	flag =flag+1;
	   }
	}

  return flag;
}    