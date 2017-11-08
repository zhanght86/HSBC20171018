//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  //showSubmitFrame(mDebug);
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //执行下一步操作
  }
}

function disPlayRearAgentName()
{
	if (document.all('RearAgent').value==null || trim(document.all('RearAgent').value)=='')
	{
		return true;
	}
	var tRearAgent=document.all('RearAgent').value;
//	var sql="select name from laagent where agentcode='"+tRearAgent+"'";
	var sql = "";
	var sqlid1="LAAgentQuerySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("ibrms.LAAgentQuerySql"); 
	mySql1.setSqlId(sqlid1);
	mySql1.addSubPara(tRearAgent);
	sql = mySql1.getString();
	var vResult = easyQueryVer3(sql, 1, 1, 1);
  
  //判断是否查询成功
	 if (!vResult) {
	   alert("录入的育成人代码不存在！");
	   return false;
	 }
  	
   //查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(vResult);
  document.all('RearAgentName').value=arrSelected[0][0];
	return true;
}

function disPlayRearDepartAgentName()
{
		if (document.all('RearDepartAgent').value==null || trim(document.all('RearDepartAgent').value)=='')
	{
		return true;
	}
	var tRearDepartAgent=document.all('RearDepartAgent').value;
//	var sql="select name from laagent where agentcode='"+tRearDepartAgent+"'";
	var sql = "";
	var sqlid1="LAAgentQuerySql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("ibrms.LAAgentQuerySql"); 
	mySql1.setSqlId(sqlid1);
	mySql1.addSubPara(tRearDepartAgent);
	sql = mySql1.getString();
	var vResult = easyQueryVer3(sql, 1, 1, 1);
  
  //判断是否查询成功
	 if (!vResult) {
	   alert("录入的增部人代码不存在！");
	   return false;
	 }
  	
   //查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(vResult);
  document.all('RearDepartAgentName').value=arrSelected[0][0];
	return true;
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
  	alert("在LAAgentQuery.js-->resetForm函数中发生异常:初始化界面错误!");
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

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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

function returnParent()
{
        var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();
		
	}
}


function getQueryResult()
{
	var arrSelected = null;

	tRow = AgentGrid.getSelNo();

	if( tRow == 0 || tRow == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
//strSQL = "select "
//	+"a.AgentCode,"
//	+"a.AgentGroup,"
//	+"a.ManageCom,"
//	+"a.Password,"
//	+"a.EntryNo,"
//	+"a.Name,"
//	+"a.Sex,"
//	+"a.Birthday,"
//	+"a.NativePlace,"
//	+"a.Nationality,"
//	+"a.Marriage,"
//	+"a.CreditGrade,"
//	+"a.HomeAddressCode,"
//	+"a.HomeAddress,"
//	+"a.PostalAddress,"
//	+"a.ZipCode,"
//	+"a.Phone,"
//	+"a.BP,"
//	+"a.Mobile,"
//	+"a.EMail,"
//	+"a.Marriage,"
//	+"a.IDNo,"
//	+"a.Source,"
//	+"a.BloodType,"
//	+"a.PolityVisage,"
//	+"a.Degree,"
//	+"a.GraduateSchool,"
//	+"a.Speciality,"
//	+"a.PostTitle,"
//	+"a.ForeignLevel,"
//	+"a.WorkAge,"
//	+"a.OldCom,"
//	+"a.OldOccupation,"
//	+"a.HeadShip,"
//	+"a.RecommendAgent,"
//	+"a.Business,"
//	+"a.SaleQuaf,"
//	+"a.QuafNo,"
//	+"a.QuafStartDate,"
//	+"a.QuafEndDate,"
//	+"a.DevNo1,"
//	+"a.DevNo2,"
//	+"a.RetainContNo,"
//	+"a.AgentKind,"
//	+"a.DevGrade,"
//	+"a.InsideFlag,"
//	+"a.FullTimeFlag,"
//	+"a.NoWorkFlag,"
//	+"a.TrainDate,"
//	+"a.EmployDate,"
//	+"a.InDueFormDate,"
//	+"a.OutWorkDate,"
//	+"a.RecommendNo,"
//	+"a.AssuMoney,"
//	+"a.Remark,"
//	+"a.AgentState,"
//	+"a.QualiPassFlag,"
//	+"a.SmokeFlag,"
//	+"a.RgtAddress,"
//	+"a.BankCode,"
//	+"a.BankAccNo,"
//	+"a.Operator,"
//	+"a.BranchType,"
//	+"a.TrainPeriods,"
//	+"a.BranchCode,"
//	+"a.Age,"
//	+"a.ChannelName,"
//	+"a.ReceiptNo,"
//	+"a.IDNoType,"
//	+"c.BranchManager,"
//	+"b.IntroAgency,"
//	+"b.AgentSeries,"
//	+"b.AgentGrade,"
//	+"(select branchattr from labranchgroup where a.AgentGroup = AgentGroup and (state<>'1' or state is null)) ,"
//		     + "b.AscriptSeries,"
//	       +"c.BranchLevel,"
//	       +"c.upBranch,"
//	       +"c.BranchManagerName,"
//	       +"c.upBranchAttr,"
//	       +"a.BranchCode,"
//	       +"a.IDNoType,"
//	       +"b.VIPProperty,"
//	       +"b.AgentLine,"	       
//	       +"b.UpAgent,"	       
//	      +"a.AssuMoney," 
//	      +"a.TogaeFlag,"
//	       +"a.TrainDate,"
//	       +"a.ArchieveCode,"
//	      +"a.RetainContNo,"
//	       +"a.RetainStartDate,"
//	      +"a.RetainEndDate,"
//	      +"(select name from laagent where agentcode=b.IntroAgency ),"
//	      +"a.approvedate,"
//	      +"(select rearagentcode from larearrelation where agentcode=a.agentcode and rearlevel='01' and rearedgens=1 and RearFlag='1'),"
//	      +"(select rearagentcode from larearrelation where agentcode=a.agentcode and rearlevel='02' and rearedgens=1 and RearFlag='1'),"
//	      +"b.speciflag "
//         +" from LAAgent a,LATree b,LABranchGroup c"
//         +" where 1=1 "
//	       +" and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+AgentGrid.getRowColData(tRow-1,1)+"' and (c.state<>'1' or c.state is null)"; 
	
//	alert(strSQL);    
	var sqlid1="LAAgentQuerySql3";
	var mySql1=new SqlClass();
	mySql1.setResourceName("ibrms.LAAgentQuerySql"); 
	mySql1.setSqlId(sqlid1);
	mySql1.addSubPara(AgentGrid.getRowColData(tRow-1,1));
	strSQL = mySql1.getString();
	var vResult = easyQueryVer3(strSQL, 1, 1, 1);
  
  //判断是否查询成功
	 if (!vResult) {
	   alert("查询失败！");
	   return false;
	 }
  	
   //查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(vResult);

	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initAgentGrid();
	// 书写SQL语句
	var tReturn = getManageComLimitlike("a.managecom");
	var strSQL = "";
	var tRearAgent=document.all('RearAgent').value;
	var tRearDepartAgent=document.all('RearDepartAgent').value;
	
	if ((tRearAgent==null || tRearAgent=='')&&(tRearDepartAgent==null || tRearDepartAgent=='') )
	{
//	strSQL = "select a.agentcode,b.BranchAttr,a.managecom,a.name,d.gradename,a.idno,a.agentstate from LAAgent a,LABranchGroup b,LATree c, laagentgrade d "; 
//	strSQL  += "where 1=1 "
//	        + "and (a.AgentState is null or a.AgentState < '03') and a.agentcode=c.agentcode and a.BranchCode = b.agentGroup and  c.agentgrade=d.gradecode and a.branchtype = '1'";
//	strSQL  += "and (b.state<>'1' or b.state is null)"
//	         + tReturn
//	         + getWherePart('a.AgentCode','AgentCode','like')
//	         + getWherePart('a.Name','Name')
//	         + getWherePart('a.Sex','Sex')
//	         + getWherePart('a.Birthday','Birthday')
//	         + getWherePart('a.IDNoType','IDNoType')
//	         + getWherePart('a.IDNo','IDNo')
//	         + getWherePart('a.Nationality','Nationality')
//	         + getWherePart('a.NativePlace','NativePlace')
//	         + getWherePart('a.PolityVisage','PolityVisage')
//	         + getWherePart('a.RgtAddress','RgtAddress')
//	         + getWherePart('a.Degree','Degree')
//	         + getWherePart('a.GraduateSchool','GraduateSchool')
//	         + getWherePart('a.Speciality','Speciality')
//	         + getWherePart('a.PostTitle','PostTitle')
//	         + getWherePart('HomeAddress')
//	         + getWherePart('a.PostalAddress','PostalAddress')
//	         + getWherePart('a.ZipCode','ZipCode')
//	         + getWherePart('a.Phone','Phone')
//	         //+ getWherePart('a.BP','BP')
//	         + getWherePart('a.Mobile','Mobile')
//	         + getWherePart('a.EMail','EMail')
//	         + getWherePart('a.BankCode','BankCode')
//	         + getWherePart('a.BankAccNo','BankAccNo')
//	         + getWherePart('a.WorkAge','WorkAge')
//	         + getWherePart('a.OldCom','OldCom')
//	         + getWherePart('a.OldOccupation','OldOccupation')
//	         + getWherePart('a.HeadShip','HeadShip')
//	         + getWherePart('a.QuafNo','QuafNo')
//	         + getWherePart('a.DevNo1','DevNo1')
//	         + getWherePart('a.EmployDate','EmployDate')
//	         + getWherePart('a.BranchType','BranchType')
//	         + getWherePart('a.ReceiptNo','ReceiptNo')
//	         + getWherePart('a.ManageCom','ManageCom','like')
//	         + getWherePart('b.BranchAttr','BranchCode','like')
//	         + getWherePart('c.IntroAgency','IntroAgency')
//	         //+ getWherePart('c.VIPProperty','VIPProperty')
//	         + getWherePart('c.AgentGrade','AgentGrade')
//	         + "order by a.agentcode";
		var sqlid1="LAAgentQuerySql4";
		var mySql1=new SqlClass();
		mySql1.setResourceName("ibrms.LAAgentQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tReturn);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('AgentCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Name'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Sex'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Birthday'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IDNoType'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IDNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Nationality'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('NativePlace'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PolityVisage'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('RgtAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Degree'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('GraduateSchool'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Speciality'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PostTitle'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('HomeAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PostalAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ZipCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Phone'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Mobile'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('EMail'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BankCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BankAccNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('WorkAge'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('OldCom'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('OldOccupation'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('HeadShip'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('QuafNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('DevNo1'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('EmployDate'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BranchType'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ReceiptNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BranchCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IntroAgency'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('AgentGrade'))[0].value;);//指定传入的参数
		strSQL=mySql1.getString();
	  }

    if ((tRearAgent==null || trim(tRearAgent)=='') && (tRearDepartAgent!=null && !trim(tRearDepartAgent)==''))
    {
//    		strSQL = "select a.agentcode,b.BranchAttr,a.managecom,a.name,c.agentgrade,a.idno,a.agentstate from LAAgent a,LABranchGroup b,LATree c ,LARearRelation d "; 
//	strSQL  += "where 1=1 "
//	        + "and (a.AgentState is null or a.AgentState < '03') and a.agentcode=c.agentcode and a.BranchCode = b.agentGroup and d.agentcode=a.agentcode and d.rearagentcode='"+tRearDepartAgent+"' and d.rearedgens=1 and d.rearlevel='02' and a.branchtype = '1' ";
//	strSQL  += "and (b.state<>'1' or b.state is null) "
//	         + tReturn
//	         + getWherePart('a.AgentCode','AgentCode','like')
//	         + getWherePart('a.Name','Name')
//	         + getWherePart('a.Sex','Sex')
//	         + getWherePart('a.Birthday','Birthday')
//	         + getWherePart('a.IDNoType','IDNoType')
//	         + getWherePart('a.IDNo','IDNo')
//	         + getWherePart('a.Nationality','Nationality')
//	         + getWherePart('a.NativePlace','NativePlace')
//	         + getWherePart('a.PolityVisage','PolityVisage')
//	         + getWherePart('a.RgtAddress','RgtAddress')
//	         + getWherePart('a.Degree','Degree')
//	         + getWherePart('a.GraduateSchool','GraduateSchool')
//	         + getWherePart('a.Speciality','Speciality')
//	         + getWherePart('a.PostTitle','PostTitle')
//	         + getWherePart('HomeAddress')
//	         + getWherePart('a.PostalAddress','PostalAddress')
//	         + getWherePart('a.ZipCode','ZipCode')
//	         + getWherePart('a.Phone','Phone')
//	         //+ getWherePart('a.BP','BP')
//	         + getWherePart('a.Mobile','Mobile')
//	         + getWherePart('a.EMail','EMail')
//	         + getWherePart('a.BankCode','BankCode')
//	         + getWherePart('a.BankAccNo','BankAccNo')
//	         + getWherePart('a.WorkAge','WorkAge')
//	         + getWherePart('a.OldCom','OldCom')
//	         + getWherePart('a.OldOccupation','OldOccupation')
//	         + getWherePart('a.HeadShip','HeadShip')
//	         + getWherePart('a.QuafNo','QuafNo')
//	         + getWherePart('a.DevNo1','DevNo1')
//	         + getWherePart('a.EmployDate','EmployDate')
//	         + getWherePart('a.BranchType','BranchType')
//	         + getWherePart('a.ReceiptNo','ReceiptNo')
//	         + getWherePart('a.ManageCom','ManageCom','like')
//	         + getWherePart('b.BranchAttr','BranchCode','like')
//	         + getWherePart('c.IntroAgency','IntroAgency')
//	         //+ getWherePart('c.VIPProperty','VIPProperty')
//	         + getWherePart('c.AgentGrade','AgentGrade');
		var sqlid1="LAAgentQuerySql5";
		var mySql1=new SqlClass();
		mySql1.setResourceName("ibrms.LAAgentQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tRearDepartAgent);//指定传入的参数
		mySql1.addSubPara(tReturn);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('AgentCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Name'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Sex'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Birthday'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IDNoType'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IDNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Nationality'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('NativePlace'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PolityVisage'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('RgtAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Degree'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('GraduateSchool'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Speciality'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PostTitle'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('HomeAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('PostalAddress'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ZipCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Phone'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('Mobile'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('EMail'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BankCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BankAccNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('WorkAge'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('OldCom'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('OldOccupation'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('HeadShip'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('QuafNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('DevNo1'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('EmployDate'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BranchType'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ReceiptNo'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('BranchCode'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('IntroAgency'))[0].value;);//指定传入的参数
		mySql1.addSubPara(window.document.getElementsByName(trim('AgentGrade'))[0].value;);//指定传入的参数
		strSQL=mySql1.getString();
    }
    
        if ( (tRearAgent!=null && !trim(tRearAgent)=='') && (tRearDepartAgent==null || trim(tRearDepartAgent)==''))
    {
//    		strSQL = "select a.agentcode,b.BranchAttr,a.managecom,a.name,c.agentgrade,a.idno,a.agentstate from LAAgent a,LABranchGroup b,LATree c ,LARearRelation d "; 
//	strSQL  += "where 1=1 "
//	        + "and (a.AgentState is null or a.AgentState < '03') and a.agentcode=c.agentcode and a.BranchCode = b.agentGroup and d.agentcode=a.agentcode and d.rearagentcode='"+tRearAgent+"' and d.rearedgens=1 and d.rearlevel='01' and a.branchtype = '1' ";
//	strSQL  += "and (b.state<>'1' or b.state is null) "
//	         + tReturn
//	         + getWherePart('a.AgentCode','AgentCode','like')
//	         + getWherePart('a.Name','Name')
//	         + getWherePart('a.Sex','Sex')
//	         + getWherePart('a.Birthday','Birthday')
//	         + getWherePart('a.IDNoType','IDNoType')
//	         + getWherePart('a.IDNo','IDNo')
//	         + getWherePart('a.Nationality','Nationality')
//	         + getWherePart('a.NativePlace','NativePlace')
//	         + getWherePart('a.PolityVisage','PolityVisage')
//	         + getWherePart('a.RgtAddress','RgtAddress')
//	         + getWherePart('a.Degree','Degree')
//	         + getWherePart('a.GraduateSchool','GraduateSchool')
//	         + getWherePart('a.Speciality','Speciality')
//	         + getWherePart('a.PostTitle','PostTitle')
//	         + getWherePart('HomeAddress')
//	         + getWherePart('a.PostalAddress','PostalAddress')
//	         + getWherePart('a.ZipCode','ZipCode')
//	         + getWherePart('a.Phone','Phone')
//	         //+ getWherePart('a.BP','BP')
//	         + getWherePart('a.Mobile','Mobile')
//	         + getWherePart('a.EMail','EMail')
//	         + getWherePart('a.BankCode','BankCode')
//	         + getWherePart('a.BankAccNo','BankAccNo')
//	         + getWherePart('a.WorkAge','WorkAge')
//	         + getWherePart('a.OldCom','OldCom')
//	         + getWherePart('a.OldOccupation','OldOccupation')
//	         + getWherePart('a.HeadShip','HeadShip')
//	         + getWherePart('a.QuafNo','QuafNo')
//	         + getWherePart('a.DevNo1','DevNo1')
//	         + getWherePart('a.EmployDate','EmployDate')
//	         + getWherePart('a.BranchType','BranchType')
//	         + getWherePart('a.ReceiptNo','ReceiptNo')
//	         + getWherePart('a.ManageCom','ManageCom','like')
//	         + getWherePart('b.BranchAttr','BranchCode','like')
//	         + getWherePart('c.IntroAgency','IntroAgency')
//	         //+ getWherePart('c.VIPProperty','VIPProperty')
//	         + getWherePart('c.AgentGrade','AgentGrade');
        	var sqlid1="LAAgentQuerySql6";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("ibrms.LAAgentQuerySql"); //指定使用的properties文件名
        	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        	mySql1.addSubPara(tRearAgent);//指定传入的参数
        	mySql1.addSubPara(tReturn);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('AgentCode'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Name'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Sex'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Birthday'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('IDNoType'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('IDNo'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Nationality'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('NativePlace'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('PolityVisage'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('RgtAddress'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Degree'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('GraduateSchool'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Speciality'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('PostTitle'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('HomeAddress'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('PostalAddress'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('ZipCode'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Phone'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('Mobile'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('EMail'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('BankCode'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('BankAccNo'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('WorkAge'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('OldCom'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('OldOccupation'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('HeadShip'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('QuafNo'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('DevNo1'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('EmployDate'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('BranchType'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('ReceiptNo'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('BranchCode'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('IntroAgency'))[0].value;);//指定传入的参数
        	mySql1.addSubPara(window.document.getElementsByName(trim('AgentGrade'))[0].value;);//指定传入的参数
        	strSQL=mySql1.getString();
    }
    
       if ( (tRearAgent!=null && !trim(tRearAgent)=='') && (tRearDepartAgent!=null && !trim(tRearDepartAgent)==''))
    {
//    		strSQL = "select a.agentcode,b.BranchAttr,a.managecom,a.name,c.agentgrade,a.idno,a.agentstate from LAAgent a,LABranchGroup b,LATree c ,LARearRelation d,larearrelation e "; 
//	strSQL  += "where 1=1 "
//	        + "and (a.AgentState is null or a.AgentState < '03') and a.agentcode=c.agentcode and a.BranchCode = b.agentGroup and d.agentcode=a.agentcode and d.rearagentcode='"+tRearAgent+"' and d.rearedgens=1 and d.rearlevel='01' and e.agentcode=a.agentcode and e.rearagentcode='"+tRearDepartAgent+"' and e.rearlevel='02' and e.rearedgens=1 and a.branchtype = '1' ";
//	strSQL  += "and (b.state<>'1' or b.state is null) "
//	         + tReturn
//	         + getWherePart('a.AgentCode','AgentCode','like')
//	         + getWherePart('a.Name','Name')
//	         + getWherePart('a.Sex','Sex')
//	         + getWherePart('a.Birthday','Birthday')
//	         + getWherePart('a.IDNoType','IDNoType')
//	         + getWherePart('a.IDNo','IDNo')
//	         + getWherePart('a.Nationality','Nationality')
//	         + getWherePart('a.NativePlace','NativePlace')
//	         + getWherePart('a.PolityVisage','PolityVisage')
//	         + getWherePart('a.RgtAddress','RgtAddress')
//	         + getWherePart('a.Degree','Degree')
//	         + getWherePart('a.GraduateSchool','GraduateSchool')
//	         + getWherePart('a.Speciality','Speciality')
//	         + getWherePart('a.PostTitle','PostTitle')
//	         + getWherePart('HomeAddress')
//	         + getWherePart('a.PostalAddress','PostalAddress')
//	         + getWherePart('a.ZipCode','ZipCode')
//	         + getWherePart('a.Phone','Phone')
//	         //+ getWherePart('a.BP','BP')
//	         + getWherePart('a.Mobile','Mobile')
//	         + getWherePart('a.EMail','EMail')
//	         + getWherePart('a.BankCode','BankCode')
//	         + getWherePart('a.BankAccNo','BankAccNo')
//	         + getWherePart('a.WorkAge','WorkAge')
//	         + getWherePart('a.OldCom','OldCom')
//	         + getWherePart('a.OldOccupation','OldOccupation')
//	         + getWherePart('a.HeadShip','HeadShip')
//	         + getWherePart('a.QuafNo','QuafNo')
//	         + getWherePart('a.DevNo1','DevNo1')
//	         + getWherePart('a.EmployDate','EmployDate')
//	         + getWherePart('a.BranchType','BranchType')
//	         + getWherePart('a.ReceiptNo','ReceiptNo')
//	         + getWherePart('a.ManageCom','ManageCom','like')
//	         + getWherePart('b.BranchAttr','BranchCode','like')
//	         + getWherePart('c.IntroAgency','IntroAgency')
//	         //+ getWherePart('c.VIPProperty','VIPProperty')
//	         + getWherePart('c.AgentGrade','AgentGrade');
    	   var sqlid1="LAAgentQuerySql7";
       	var mySql1=new SqlClass();
       	mySql1.setResourceName("ibrms.LAAgentQuerySql"); //指定使用的properties文件名
       	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
       	mySql1.addSubPara(tRearAgent);//指定传入的参数
       	mySql1.addSubPara(tRearDepartAgent);//指定传入的参数
       	mySql1.addSubPara(tReturn);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('AgentCode'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Name'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Sex'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Birthday'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('IDNoType'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('IDNo'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Nationality'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('NativePlace'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('PolityVisage'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('RgtAddress'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Degree'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('GraduateSchool'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Speciality'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('PostTitle'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('HomeAddress'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('PostalAddress'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('ZipCode'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Phone'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('Mobile'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('EMail'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('BankCode'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('BankAccNo'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('WorkAge'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('OldCom'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('OldOccupation'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('HeadShip'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('QuafNo'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('DevNo1'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('EmployDate'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('BranchType'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('ReceiptNo'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('BranchCode'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('IntroAgency'))[0].value;);//指定传入的参数
       	mySql1.addSubPara(window.document.getElementsByName(trim('AgentGrade'))[0].value;);//指定传入的参数
       	strSQL=mySql1.getString();
    }
    
    
     	//alert(strSQL); 
     	turnPage.queryModal(strSQL, AgentGrid); 
//	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
// alert(turnPage.strQueryResult);
//  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
}
//
////查询成功则拆分字符串，返回二维数组
//  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
//  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
//  turnPage.arrDataCacheSet = arrDataSet;
//  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
//  turnPage.pageDisplayGrid = AgentGrid;    
//          
//  //保存SQL语句
//  turnPage.strQuerySql     = strSQL; 
//  
//  //设置查询起始位置
//  turnPage.pageIndex       = 0;  
//  
//  //在查询结果数组中取出符合页面显示大小设置的数组
//  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//  var tArr = new Array();
//  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//  //调用MULTILINE对象显示查询结果
//  
//  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  displayMultiline(tArr, turnPage.pageDisplayGrid);
}

  function SelectBranch()
  {
  	
  	if(document.all('ManageCom').value ==null ||trim(document.all('ManageCom').value) == "")
  	{
  		alert("请先选择管理机构");
  		return false;
  	}

    showInfo=window.open('../treeBranch/jsp/SelectBranchCode.jsp?ManageCom='+fm.ManageCom.value+'&BranchType='+fm.BranchType.value,'newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  function SelectCom()
  {
  	
    showInfo=window.open('../treeCom/jsp/SelectCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
 
 /// add by ly 20070625
 function checkManageCom()
 {
 		if(document.all('ManageCom').value ==null ||trim(document.all('ManageCom').value) == "")
  	{
  		alert("请先选择管理机构");
  		fm.BranchCode.value="";
  		return false;
  	}
 }