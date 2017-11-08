//               该文件中包含客户端需要处理的函数和事件

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
	if (verifyInput())
	{
	  var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='提示';   //网页名称，可为空; 
	  var iWidth=550;      //弹出窗口的宽度; 
	  var iHeight=250;     //弹出窗口的高度; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();
	   
	  fm.submit(); //提交
	}
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

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}

//付费日结打印财务收费日结单
function printList()
{   
	
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    if(fm.StartDay.value == ""||fm.StartDay.value ==null)
		{
			alert( "请先选择起始时间，再点击打印按钮。" );
			return false;
		}
	else if(fm.EndDay.value == ""||fm.EndDay.value ==null)
		{
			alert( "请先选择结束时间，再点击打印按钮。" );
			return false;
		}	
	else
	{
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	fm.action="../f1print/NBCListF1PSave.jsp";	
	fm.target="f1print";
	fm.fmtransact.value="PRINT";
	submitForm();
	showInfo.close();
	
	}
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





//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了删除操作！");
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
//新契约数量统计函数
function polcount()
{
	 if(fm.StartDay.value == ""||fm.StartDay.value ==null)
		{
			alert( "请先选择起始时间。" );
			return false;
		}
	else if(fm.EndDay.value == ""||fm.EndDay.value ==null)
		{
			alert( "请先选择结束时间。" );
			return false;
		}	
	else
	{
		 var StartDay = document.all('StartDay').value;
		 var EndDay = document.all('EndDay').value;
		 var SaleChnl = document.all('SaleChnl').value;
		 var ManageCom = document.all('ManageCom').value;
		 var RiskCode = document.all('RiskCode').value;
		 
		 var sql="";
		 
//		 var SaleChnl_sql="";
//		 var ManageCom_sql="";
//		 var RiskCode_sql="";
		 
//		 if( (SaleChnl !=null)&&(SaleChnl != "") )
//		 {
//		 	 SaleChnl_sql = " and SaleChnl='"+SaleChnl+"' ";
//		 }
//		 
//		 if( (ManageCom !=null)&&(ManageCom != "") )
//		 {
//		 	 ManageCom_sql = " and managecom like '"+ManageCom+"%%' ";
//		 }
//		 
//		 if( (RiskCode !=null)&&(RiskCode != "") )
//		 {
//		 	 RiskCode_sql = " and riskcode ='"+RiskCode+"' ";
//		 }
		 
//		 sql= "select count(*) from lcpol c where appflag='1' and substr(grppolno,1,4)='0000' "
//       +" and SignDate>='"+StartDay+"' and SignDate<='"+EndDay+"' "
//       +SaleChnl_sql+ManageCom_sql+RiskCode_sql
//       +" and not exists (select 1 from LCRnewstatelog where polno = c.polno ) ";
     
			var sqlid0="NBCListSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("f1print.NBCListSql"); //指定使用的properties文件名
			mySql0.setSqlId(sqlid0);//指定使用的Sql的id
			mySql0.addSubPara(StartDay);//指定传入的参数
			mySql0.addSubPara(EndDay);//指定传入的参数
			mySql0.addSubPara(SaleChnl);//指定传入的参数
			mySql0.addSubPara(ManageCom);//指定传入的参数
			mySql0.addSubPara(RiskCode);//指定传入的参数
			sql=mySql0.getString();
		 
     document.all('polamount').value =  easyExecSql(sql);
		 
	}
	
}
