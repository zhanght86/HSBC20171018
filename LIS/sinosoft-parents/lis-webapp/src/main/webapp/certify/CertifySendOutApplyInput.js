//               该文件中包含客户端需要处理的函数和事件
var showInfo;
window.onfocus=myonfocus;
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
  if(verifyInput()==false || CertifyList.checkValue("CertifyList")==false){
    return false;  
  }
  
  CertifyList.delBlankLine("CertifyList");
  var nmulLineCount = CertifyList.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("请至少输入一个要发放的单证！ ");
    return false;
  }
  
  //已做过增领申请的单证给予提示
  var ReceiveCom = fm.ReceiveCom.value;
  for(var i=1; i<=nmulLineCount; i++){
   var certifyCode = CertifyList.getRowColData(i-1,1);
   //var strSql = "select StateFlag from lzcardapp a where OperateFlag='1' and a.certifycode='"+certifyCode+"' and a.receivecom='"+ReceiveCom+"'";
   var strSql = wrapSql("querysqldes3",[certifyCode,ReceiveCom]);
   var arrResult = easyExecSql(strSql);    
    if(arrResult!=null && arrResult[0][0]=='1'){  //1-申请待批复
        if(!confirm("单证"+certifyCode+"已做过增领申请且处于待批复状态，是否继续申请?"))
        return false;
      }
  }  
  
  if (confirm("您确认增领申请操作吗?")){
	try {
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
    } catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    alert("您取消了增领申请操作！");
  }	  
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content, TakeBackNo )
{
  showInfo.close();
  fm.btnOp.disabled = false;
  	
  if(FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }else { 
	content="保存成功！";
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

function afterCodeSelect( cName, Filed)
{
  if(cName=='DepartmentNo')//选择部门编码后
  {
    if(fm.Department.value==null || fm.Department.value==""){
      alert("请选择【部门来源】");
    }else if(fm.Department.value=="1"){
      fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.SendOutCom.readOnly = true;
      fm.ReceiveCom.value = "";
      fm.ReceiveCom.readOnly = false;
    }else if(fm.Department.value=="2"){
      fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
      fm.ReceiveCom.readOnly = true;
      fm.SendOutCom.value = "";
      fm.SendOutCom.readOnly = false;        
    }else{      
      alert("选择【部门编码】有误");
      fm.DepartmentNo.value="";
    }
  }
  if(cName=='Department')//选择部门后
  {
    if(fm.DepartmentNo.value!=null && fm.DepartmentNo.value!=""){
 	  if(fm.Department.value=="1"){
        fm.SendOutCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.SendOutCom.readOnly = true;
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;         
      }else if(fm.Department.value=="2"){
        fm.ReceiveCom.value ="B" + fm.ComCode.value + fm.DepartmentNo.value
        fm.ReceiveCom.readOnly = true;   
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false;       
      }else if(fm.Department.value=="3"){
        fm.DepartmentNo.value = "";
        fm.DepartmentNoName.value = "";
        fm.SendOutCom.value = "";
        fm.SendOutCom.readOnly = false; 
        fm.ReceiveCom.value = "";
        fm.ReceiveCom.readOnly = false;          
      }
    }
  }
  if(cName=='CertifyCode')//选择单证编码后,自动查询发放者和接收者的库存并显示
  {
  
    var SendOutCom = fm.SendOutCom.value;
    var ReceiveCom = fm.ReceiveCom.value;
    if(SendOutCom=="" || ReceiveCom==""){
      alert("请先录入【发放者】和【接收者】后再选择单证代码！");
      return false;
    }
    for(var i=0;i<CertifyList.mulLineCount;i++){//循环查询mulLine，若发现有未查询库存的将其查询出来显示
      var CertifyCode=CertifyList.getRowColData(i,1);
      var sum1=CertifyList.getRowColData(i,3);
      var sum2=CertifyList.getRowColData(i,3);
      if(CertifyCode!="" && (sum1=="" || sum2==""))
        {
         //alert("第"+(i+1)+"行单证代码="+CertifyCode);         
         //var sqlSum1="select nvl(sum(a.sumcount),0) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ SendOutCom +"' and a.stateflag in ('2','3')";
         
        var sqlid1="querysqldes1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("certify.CertifySendOutApplyInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(CertifyCode);//指定传入的参数
		mySql1.addSubPara(SendOutCom);//指定传入的参数
		var strSQL=mySql1.getString();	
        var arrResult1 = easyExecSql(strSQL,1,0);	
     //    var sqlSum1 = wrapSql("querysqldes1",[CertifyCode,SendOutCom]);
         //var sqlSum2="select nvl(sum(a.sumcount),0) from lzcard a where a.certifycode='"+CertifyCode+"' and a.receivecom='"+ ReceiveCom +"' and a.stateflag in ('2','3')";
       //  var sqlSum2 = wrapSql("querysqldes2",[CertifyCode,SendOutCom]);
         var sqlid2="querysqldes2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("certify.CertifySendOutApplyInputSql");//指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(CertifyCode);//指定传入的参数
		mySql2.addSubPara(ReceiveCom);//指定传入的参数
		var strSQL1=mySql2.getString();	
        var arrResult2 = easyExecSql(strSQL1);
  
        // var arrResult2 = easyExecSql(sqlSum2);
         //alert("本级库存="+arrResult1[0][0]);
        // alert("下级库存="+arrResult2[0][0]);
         CertifyList.setRowColData(i,3,arrResult1[0][0]);//发放者库存
         CertifyList.setRowColData(i,4,arrResult2[0][0]);//接收者库存
        }
    }
  }  
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

function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

// 查询印刷号的功能
function queryPrtNo()
{
	fm.sql_where.value = " State = '1' ";
  showInfo = window.open("./CertifyPrintQuery.html");
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
  	if( fm.chkModeBatch.checked == false ) {
	    fm.PrtNoEx.value = arrResult[0][0];
	    fm.ReceiveCom.value = 'A' + arrResult[0][11];
	    
	    CertifyList.clearData();
	    CertifyList.addOne();
	    
			var rowCount = 0;

	    CertifyList.setRowColData(rowCount, 1, arrResult[0][1]);
	    CertifyList.setRowColData(rowCount, 3, arrResult[0][18]);
	    CertifyList.setRowColData(rowCount, 4, arrResult[0][19]);
	  } else {
	    fm.ReceiveCom.value = arrResult[0][3];
	    fm.ReceiveCom.title = arrResult[0][1];
	    
	    CertifyList.clearData();
	    CertifyList.addOne();
		}
  }
}

//批量发放模式的切换函数
function changeMode(objCheck)
{
	CertifyList.clearData();
	if(objCheck.checked == true) {
		fm.btnOp.value = "批量发放";
		
		fm.btnQueryCom.style.display = "";
		
		fm.chkPrtNo.disabled = true;
		
	} else {
		fm.btnOp.value = "发放单证";
		
		fm.btnQueryCom.style.display = "none";
		
		fm.chkPrtNo.disabled = false;
	}
}

//批量发放。查询代理人区部组的函数。
function queryCom()
{
	fm.sql_where.value = "";
  showInfo = window.open("./AgentTrussQuery.html");
}

//接收者是业务员时，弹出业务员的姓名和工号
var arrResult = new Array();
function queryAgent()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		if(trim(receivecom).length >= 11)
		{
			var cAgentCode = receivecom.substring(1,11);
			//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
			var strSql = wrapSql("querysqldes4",[cAgentCode]);
	    	var arrResult = easyExecSql(strSql);
	    	if (arrResult != null) 
	      		alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
	    	else
				alert("编码为:["+ cAgentCode +"]的代理人不存在，请确认!");
		}
	}
}

/**
mysql工厂，根据传入参数生成Sql字符串

sqlId:页面中某条sql的唯一标识
param:数组类型,sql中where条件里面的参数
**/
function wrapSql(sqlId,param)
{
	alert("b");
	var mysql=new SqlClass();

	mysql.setResourceName("certify.CertifySendOutApplyInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++)
	{
		 mysql.addSubPara(param[i]);
	}
	return mysql.getString();
}