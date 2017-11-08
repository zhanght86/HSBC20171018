//               该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
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
function checkuseronly(comname)
{
	//add by yaory
	

} 
//提交，保存按钮对应操作
function submitForm()
{
 if(!verifyInput())
 {
 	 return false;
 }
 if(!checkData())
 {
 	 return false;
 }
 
  var i = 0;
  fm.fmtransact.value="INSERT";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
  document.getElementById("fm").submit(); //提交
 
}

//校验是否体检信息已存在
function checkData()
{
	var arrResult = new Array();
//	var SQL = "select '1' from ldhospital where hospitcode='"+document.all('HospitalCode').value+"' ";
	
	var sqlid0="LDHospitalInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDHospitalInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(document.all('HospitalCode').value);//指定传入的参数
	var SQL=mySql0.getString();
	
	arrResult = easyExecSql(SQL);
	//alert(arrResult);
	if(arrResult!=null)
	{
		alert('已存在该体检医院,不允许保存!');
		initForm();
		return false;
	}
	return true;
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
  fm.fmtransact.value = "";
  initForm();
}
//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在LDUWUser.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 
//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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
       
//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  if((fm.fmtransact.value == null)||(fm.fmtransact.value != "QUERY"))
  {
  	alert("请先查询记录再作修改！");  	
  	return false;
  }

  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  	document.all('HospitalCode').disabled=false;
  	if(!verifyInput())
 		{
 			document.all('HospitalCode').disabled=true;
 	 		return false;
 		}
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  //showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE";
  	document.getElementById("fm").submit(); //提交
  }
  else
  {
    //mOperate="";
    alert("您取消了修改操作！");
  }
}           
//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./LDHospitalDetailQuery.html");
}           
//Click事件，当点击“删除”图片时触发该函数

function deleteClick()
{
	if((fm.fmtransact.value == null)||(fm.fmtransact.value != "QUERY"))
  {
  	alert("请先查询记录再作删除！");  	
  	return false;
  }

  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
  	document.all('HospitalCode').disabled=false;
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
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE";
  	document.getElementById("fm").submit(); //提交
  	initForm();
  }
  else
  {
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
/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
	//alert(arrQueryResult[0][0]);
	if( arrQueryResult != null )
	{
		
//		  var strSql = "select hospitcode,a.hospitalname,a.hospitalgrade,mngcom,a.validitydate,a.address,(select codename from ldcode where codetype='hospitalgrade' and code=a.hospitalgrade) "
//         		     + ",(select name from ldcom where comcode=a.mngcom)"
//         		 		 + ",a.hosstate,(select codename from ldcode where codetype='hosstate' and code=a.hosstate), "
//         		 		 + " a.remark from  LDHospital a where 1=1 "
//         		     + " and trim(hospitcode) = '"+arrQueryResult[0][0]+"'";
		   
			var sqlid1="LDHospitalInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("config.LDHospitalInputSql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(arrQueryResult[0][0]);//指定传入的参数
			var strSql=mySql1.getString();
		  
	 	arrResult = easyExecSql(strSql);
	 if(arrResult!=null){
	  document.all('HospitalCode').value = arrResult[0][0];
    document.all('HospitalName').value = arrResult[0][1];
    document.all('HospitalGrade').value = arrResult[0][2];
    document.all('ManageCom').value = arrResult[0][3];
    document.all('ValidityDate').value = arrResult[0][4];
    document.all('Address').value = arrResult[0][5];
    document.all('HospitalGradeName').value = arrResult[0][6];
    document.all('ManageComName').value = arrResult[0][7];
    document.all('HosState').value = arrResult[0][8];
    document.all('HosStateName').value = arrResult[0][9];
    document.all('Remark').value = arrResult[0][10];
                       
                
             fm.fmtransact.value = "QUERY";
             document.all('HospitalCode').disabled=true;
	 } else {
		 alert("查询体检医院信息失败！");
		 return false;
	 }
   }
} 
function showCodeName()
{
	showOneCodeName('HospitalGrade','HospitalGrade','HospitalGradeName');
}

