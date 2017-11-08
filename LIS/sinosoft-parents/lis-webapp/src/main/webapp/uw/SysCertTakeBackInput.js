//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var mOperation = "";
window.onfocus=myonfocus;
var tResourceName="certify.SysCertTakeBackInputSql";
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
	if(verifyInput() == true) {
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
	  // showSubmitFrame(mDebug);
	  fm.hideOperation.value = "INSERT||MAIN";
	  document.getElementById("fm").submit(); //提交
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
    content="保存成功！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
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

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
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
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  }	else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
	alert("功能开发中");
	return;
  //下面增加相应的代码
  mOperation = "QUERY||MAIN";
  fm.sql_where.value = "";
  showInfo = window.open("../certify/SysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function query()
{
	if(document.all('CertifyNo').value==null||trim(document.all('CertifyNo').value)=='')
	{
		fm.CertifyCode.value = '';
			fm.CertifyNo.value ='';
			fm.ValidDate.value = '';
			fm.SendOutCom.value = '';
			fm.ReceiveCom.value = '';
			fm.Handler.value = '';
			fm.HandleDate.value = '';
			fm.SendNo.value = '';
			fm.TakeBackNo.value = '';
			fm.Operator.value = '';
			fm.MakeDate.value = '';
			fm.EdorNo.value = '';
			fm.ContNo.value = '';
			fm.MissionID.value  = '';
			fm.SubMissionID.value  = '';
		return ;
	}
	//mOperation = "QUERY||MAIN";
	//fm.sql_where.value = " StateFlag = '0' ";
	//showInfo = window.open("SysCertTakeBackQuery.html");
	
	//tongmeng 2007-10-22 modify
		var strSQL = "";	
    /*var strSQL = "SELECT LWMission.MissionProp4,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp7,LWMission.MissionProp8, LWMission.MissionProp9,LWMission.MissionProp10,LWMission.MissionProp11,LWMission.MissionProp12,LWMission.MissionProp13,LWMission.MissionProp1 ,LWMission.MissionProp2 ,LWMission.MissionID ,LWMission.SubMissionID FROM LWMission"
       +" WHERE  LWMission.ActivityID in "
       +"('0000005533','0000000303','0000000314','0000001111','0000001112','0000001019','0000001113','0000001025','0000001220','0000001230','0000001300','0000001260','0000001250','0000002250','0000002111','0000002311','0000000322','0000000353','0000007006','0000007009','0000007012','0000005553','0000005563')  " 
	   + "and LWMission.ProcessID in ('0000000000','0000000003','0000000004','0000000005','0000000007')"
	   + getWherePart('LWMission.MissionProp4', 'CertifyCode') 
	   + getWherePart('LWMission.MissionProp3', 'CertifyNo')*/

var mySql40=new SqlClass();
		var sqlid40="querysqldes1";
mySql40.setResourceName("uw.SysCertTakeBackInputSql"); //指定使用的properties文件名
mySql40.setSqlId(sqlid40);//指定使用的Sql的id
mySql40.addSubPara(document.all('CertifyCode').value);//指定传入的参数
mySql40.addSubPara(document.all('CertifyNo').value);//指定传入的参数

	var	strSQL=mySql40.getString();	
		

//alert('1');
	//var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('CertifyNo').value]);
	   
    	var strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!strQueryResult)
	{
		fm.CertifyCode.value = '';
			fm.CertifyNo.value ='';
			fm.ValidDate.value = '';
			fm.SendOutCom.value = '';
			fm.ReceiveCom.value = '';
			fm.Handler.value = '';
			fm.HandleDate.value = '';
			fm.SendNo.value = '';
			fm.TakeBackNo.value = '';
			fm.Operator.value = '';
			fm.MakeDate.value = '';
			fm.EdorNo.value = '';
			fm.ContNo.value = '';
			fm.MissionID.value  = '';
			fm.SubMissionID.value  = '';
		alert('查询无结果');
		return false;
	}
	var arr = decodeEasyQueryResult(strQueryResult);
	fm.CertifyCode.value = arr[0][0];
			fm.CertifyNo.value = arr[0][1];
			fm.ValidDate.value = arr[0][2];
			fm.SendOutCom.value = arr[0][3];
			fm.ReceiveCom.value = arr[0][4];
			fm.Handler.value = arr[0][5];
			fm.HandleDate.value = arr[0][6];
			fm.SendNo.value = arr[0][9];
			fm.TakeBackNo.value = arr[0][10];
			fm.Operator.value = arr[0][7];
			fm.MakeDate.value = arr[0][8];
			fm.EdorNo.value = arr[0][11];
			fm.ContNo.value = arr[0][12];
			fm.MissionID.value  = arr[0][13];
			fm.SubMissionID.value  = arr[0][14];//alert("fm.MissionID.value=="+fm.MissionID.value);alert("fm.SubMissionID.value=="+fm.SubMissionID.value);
			fm.ActivityID.value  = arr[0][15];
			fm.CodeType.value=arr[0][16];
	return true;
	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
	try {
	  if(arrResult!=null)
	   {	  	   
			fm.CertifyCode.value = arrResult[0][0];
			fm.CertifyNo.value = arrResult[0][1];
			fm.ValidDate.value = arrResult[0][2];
			fm.SendOutCom.value = arrResult[0][3];
			fm.ReceiveCom.value = arrResult[0][4];
			fm.Handler.value = arrResult[0][5];
			fm.HandleDate.value = arrResult[0][6];
			fm.SendNo.value = arrResult[0][9];
			fm.TakeBackNo.value = arrResult[0][10];
			fm.Operator.value = arrResult[0][7];
			fm.MakeDate.value = arrResult[0][8];
			fm.EdorNo.value = arrResult[0][11];
			fm.ContNo.value = arrResult[0][12];
			fm.MissionID.value  = arrResult[0][13];
			fm.SubMissionID.value  = arrResult[0][14];
		}
	} catch (ex) {
		alert("在afterQuery中发生错误");
	}
}


