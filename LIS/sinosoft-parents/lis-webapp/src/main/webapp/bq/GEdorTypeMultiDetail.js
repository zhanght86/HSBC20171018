//该文件中包含客户端需要处理的函数和事件
var showInfo;
var pEdorFlag = true;                        //用于实时刷新处理过的数据的列表
var turnPage5 = new turnPageClass();

var turnPage0 = new turnPageClass(); 
var turnPage  = new turnPageClass();         //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();         //使用翻页功能，必须建立为全局变量
window.onfocus = initFocus;                  //重定义获取焦点处理事件
var arrList1 = new Array();                  //选择的记录列表
var arrList2 = new Array();                  //选择的记录列表
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
/**
 * 查询按钮
 */
function queryClick() {

	var tEdorNo = document.all('EdorNo').value;	
	//alert(tEdorNo);
	var tGrpContNo = document.all('GrpContNo').value;
	
	var sqlid831172327="DSHomeContSql831172327";
var mySql831172327=new SqlClass();
mySql831172327.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831172327.setSqlId(sqlid831172327);//指定使用的Sql的id
mySql831172327.addSubPara(tGrpContNo);//指定传入的参数
mySql831172327.addSubPara(tGrpContNo);//指定传入的参数
mySql831172327.addSubPara(tEdorNo);//指定传入的参数
mySql831172327.addSubPara(fm.EdorType.value);//指定传入的参数
mySql831172327.addSubPara(fm.ContNo2.value);//指定传入的参数
mySql831172327.addSubPara(fm.CustomerNo.value);//指定传入的参数
mySql831172327.addSubPara(fm.Name.value);//指定传入的参数
var strSql=mySql831172327.getString();
	
//  var strSql = "select ContNo, InsuredNo, InsuredName, decode(InsuredSex,'0','男','1','女','不详'), InsuredBirthday, "
//             + "decode(InsuredIDType,'0','身份证','1','护照','2','军官证','3','驾照','4','户口本','5','学生证','6','工作证','8','其它','9','无证件','其它'), InsuredIDNo, Prem from LCCont a where AppFlag = '1' and "
//             + "GrpContNo = '" + tGrpContNo + "' and  exists (select * from lcpol where contno=a.contno and grpcontno='"
//             + tGrpContNo + "' and appflag = '1' and riskcode in (select riskcode from lmriskedoritem where edorcode = 'ZT'))"
//             + " and not exists (select * from lpedoritem where contno = a.contno and edorno = '"+tEdorNo+"' and edortype = '"+fm.EdorType.value+"')"
//	     + getWherePart('ContNo', 'ContNo2')
//	     + getWherePart('InsuredNo', 'CustomerNo')
//	     + getWherePart('InsuredName', 'Name', 'like', '0')
//	     + " order by ContNo";	           
	turnPage0.queryModal(strSql, LCInsuredGrid); 	 	
	//showInfo.close();	 
}

function queryClick1() {
	if (fm.Name.value.length==0)
	{
		alert("请输入姓名中的一部分文字！");
		return;
		}
	//var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus(); 
  
	var tEdorNo = document.all('EdorNo').value;	
	var tGrpContNo = document.all('GrpContNo').value;
	
	var sqlid831172849="DSHomeContSql831172849";
var mySql831172849=new SqlClass();
mySql831172849.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831172849.setSqlId(sqlid831172849);//指定使用的Sql的id
mySql831172849.addSubPara(tGrpContNo);//指定传入的参数
mySql831172849.addSubPara(tGrpContNo);//指定传入的参数
mySql831172849.addSubPara(fm.ContNo2.value);//指定传入的参数
mySql831172849.addSubPara(fm.CustomerNo.value);//指定传入的参数
mySql831172849.addSubPara(fm.Name.value);//指定传入的参数
var strSql=mySql831172849.getString();
	
//  var strSql = "select ContNo, InsuredNo, InsuredName, decode(InsuredSex,'0','男','1','女','不详'), InsuredBirthday, "
//             + "decode(InsuredIDType,'0','身份证','1','护照','2','军官证','3','驾照','4','户口本','5','学生证','6','工作证','8','其它','9','无证件','其它'), InsuredIDNo, Prem from LCCont a where AppFlag = '1' and "
//             + "GrpContNo = '" + tGrpContNo + "' and  exists (select * from lcpol where contno=a.contno and grpcontno='"
//             + tGrpContNo + "')"
//	     + getWherePart('ContNo', 'ContNo2')
//	     + getWherePart('InsuredNo', 'CustomerNo')
//	     + getWherePart('InsuredName', 'Name', 'like','0')
//	     + " order by ContNo";	           
	turnPage0.queryModal(strSql, LCInsuredGrid); 	 	
	//showInfo.close();	 
}


/**
 * 单击MultiLine的复选按钮时操作
 */
function reportDetailClick(spanId) {	
	
	var rowLine = spanId.substr(17,1); //当前被选中的行数；
 	lockScreen('lkscreen');
	if(document.all(spanId).all('InpLCInsuredGridChk').value == "1")
	{
		//alert("SSBB");
		var tContNo = LCInsuredGrid.getRowColData(rowLine,1);
		
		var sqlid831173437="DSHomeContSql831173437";
var mySql831173437=new SqlClass();
mySql831173437.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831173437.setSqlId(sqlid831173437);//指定使用的Sql的id
mySql831173437.addSubPara(tContNo);//指定传入的参数
var tSql=mySql831173437.getString();
		
//		var tSql = "select distinct 1 from llclaimpolicy where contno = '"+tContNo+"' and clmstate in ('10','20','30','35','40','50')";
		var arrResult = easyExecSql(tSql, 1, 0, 1);
		if(arrResult != null)
		{
			if(arrResult == "1")
			{
				LCInsuredGrid.checkBoxNotSel(Arithmetic(rowLine,'+',1,0));
				alert("该被保人保单正在理赔中不能对其进行减人操作！");
				unlockScreen('lkscreen');
				return;
			}
		}
		
		var sqlid831173520="DSHomeContSql831173520";
var mySql831173520=new SqlClass();
mySql831173520.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831173520.setSqlId(sqlid831173520);//指定使用的Sql的id
mySql831173520.addSubPara(tContNo);//指定传入的参数
tSql=mySql831173520.getString();
		
//		tSql="select 1 from llclaimdetail where contno= '"+tContNo+"'";
	  arrResult = new Array();        
	  arrResult = easyExecSql(tSql,1,0);    
	  if(arrResult!=null)
	  {
	  	if(arrResult == "1")
			{
	  		LCInsuredGrid.checkBoxNotSel(Arithmetic(rowLine,'+',1,0));
	    	alert("该被保人有理赔记录,不能对其进行减人操作！");
	    	unlockScreen('lkscreen');
	    	return;
	    }

	  }
	}
	unlockScreen('lkscreen');
}

/*****************
 * 按险种退保
 */
var arrP = 0;
function pEdorMultiDetail() {
	
  //校验是否选择
    var i = 0;    
    var chkFlag = false;    
    for (i = 0; i < LCInsuredGrid.mulLineCount; i++ )
    {
        if (LCInsuredGrid.getChkNo(i))
        {
          chkFlag = true;  
          break;        
        }
    }    
   if (chkFlag)
    {
        //PEdorDetail(); 
        document.all("Transact").value = "INSERT||EDOR"
        var showStr = "正在进行集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
        fm.submit();
    }
    else
    {
        alert("请选择需要操作的记录!");
    }       
}

/**
 * 进入个人保全
 */
function PEdorDetail() {  
      
    document.all("EdorAcceptNo").value = top.opener.document.all('EdorAcceptNo').value;    
    document.all("Transact").value = "INSERT||EDOR";
    fm.submit();
    openPEdorDetail(); 
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content, Result) 
{
  showInfo.close();  
  if (FlagStr == "Fail") 
  {             
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
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
    pEdorFlag = true;
    if (fm.Transact.value == "DELETE||EDOR") {
      var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    } else {
      var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();	  
    }    
    //parent.fraInterface.document.all('GetMoney').value = Result;
  } 
     
  initForm(); 
  unlockScreen('lkscreen');  
}

/**
 * 处理获取焦点事件
 */
function initFocus() {
	
  if (pEdorFlag) 
  {   
    pEdorFlag = false;        
    queryPEdorList();
  }
}
var GrpBQ = false;
var GTArr = new Array();

/**
 * 打开个人保全的明细界面
 */
function openPEdorDetail() {	
	
	fm.CustomerNoBak.value = fm.CustomerNo.value;
	fm.ContNoBak.value = fm.ContNo.value;
	
	  if (document.all('EdorType').value == "PT") {
		  window.open("./PEdorType" + document.all('EdorType').value + ".jsp", "", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		if (document.all('EdorType').value == "GT") {
		  window.open("./PEdorType" + document.all('EdorType').value + ".jsp", "", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		if (trim(document.all('EdorType').value) == "ZT") {
		  //PEdorDetail();		  
		  window.open("./GEdorType" + document.all('EdorType').value + ".jsp?EdorAcceptNo="+fm.EdorAcceptNo.value+"", "", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

		}
		try { showInfo.close();	} catch(e) {}
}

/**
 * 查询出申请后的个人保全列表
 */
function queryPEdorList() {
	
  var sqlid831173912="DSHomeContSql831173912";
var mySql831173912=new SqlClass();
mySql831173912.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831173912.setSqlId(sqlid831173912);//指定使用的Sql的id
mySql831173912.addSubPara(fm.EdorType.value);//指定传入的参数
mySql831173912.addSubPara(document.all('EdorNo').value);//指定传入的参数
mySql831173912.addSubPara(fm.EdorNo.value );//指定传入的参数
mySql831173912.addSubPara(fm.EdorType.value);//指定传入的参数
var strSql=mySql831173912.getString();
  
//  var strSql = "select distinct a.ContNo,a.InsuredNo,a.InsuredName,decode(a.InsuredSex,'0','男','1','女','不详'),"
//             + "a.InsuredBirthday,decode(a.InsuredIDType,'0','身份证','1','护照','2','军官证','3','驾照','4','户口本','5','学生证','6','工作证','8','其它','9','无证件','其它'),a.InsuredIDNo,a.cvalidate,a.Prem,"
//             + "nvl((select sum(GetMoney) from LJSGetEndorse where FeeOperationType = '"+fm.EdorType.value+"' and OtherNo = '" + document.all('EdorNo').value + "' and polno in (select polno from lcpol where contno = a.contno)),0) "
//             + "from LCCont a, LPEdorItem b where a.ContNo = b.ContNo "
//	           + "and b.EdorNo = '" + fm.EdorNo.value + "' and b.EdorType = '" 
//	           + fm.EdorType.value + "' order by a.ContNo";  
  //turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid); 	 
	//turnPage.queryModal(strSql, LCInsured2Grid); 		
	//queryClick();
	var rowNum=LCInsured2Grid.mulLineCount;
	//alert(rowNum);
	if(rowNum<1)
	{
		LPContDiv.style.display = 'none';
	}else {
		LPContDiv.style.display = '';	
	}
}

/**
 * 单击MultiLine的单选按钮时操作
 */
function reportDetail2Click(parm1, parm2) {	
	
	if (document.all(parm1).all('LCInsured2GridChk').checked) {
    	
    	arrList2[document.all(parm1).all('LCInsured2GridNo').value] = document.all(parm1).all('LCInsured2Grid1').value;  
//    if (document.all(parm1).all('LCInsured2GridChk').value == 'on' || 
//        document.all(parm1).all('LCInsured2GridChk').value == '1') {

//       fm.ContNo.value = document.all(parm1).all('LCInsured2Grid1').value;
//       fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid2.getSelNo() - 1, 2);
//	     alert(fm.ContNo.value);
//    }
  } else {
    arrList2[document.all(parm1).all('LCInsured2GridNo').value] = null;
  }  
  var line2;
  var rowNum2 = LCInsured2Grid. mulLineCount;
  for(var i = 0; i < rowNum2; i++)
  {
  	if(LCInsured2Grid.getChkNo(i) == true)
  	{
  		line2 = i;
  	}
  }  
  //if (document.all(parm1).all('LCInsuredGridChk').value=='on' || document.all(parm1).all('LCInsuredGridChk').value=='1') {
  //    fm.ContNo.value = document.all(parm1).all('LCInsuredGrid1').value;
  //    
  //fm.ContNo.value = LCInsured2Grid.getRowColData(line2,1);
  //fm.CustomerNo.value = LCInsured2Grid.getRowColData(line2,2);
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
}

/**
 * 撤销集体下个人保全
 */
function cancelPEdor() 
{
    
    var i = 0;
    var chkFlag = false;
    for (i = 0; i < LCInsured2Grid.mulLineCount; i++ )
    {
        if (LCInsured2Grid.getChkNo(i))
        {
          chkFlag = true;
          break;
        }
        
    }
    if (chkFlag)
    {
    		lockScreen('lkscreen');
        document.all("Transact").value = "DELETE||EDOR"
        var showStr = "正在撤销集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
        fm.action = "./GEdorTypeMultiDetailSubmit.jsp";
        fm.submit();
    }
    else
    {
        alert("请选择需要操作的记录!");
    }      
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

//=================
function returnParent() {
	
  top.opener.initEdorItemGrid();    
  top.opener.getEdorItemGrid();	
	top.close();
}

/*********************************************************************
 *  批量减人 by  wenhuan 
 *  
 *********************************************************************
 */
function ZTTotal()
{
	if(fm.chkPrtNo.checked == true)
	{
		if (fm.GrpRiskCode.value == "")
		{
			alert("请选择险种");
			return;
		}
		pEdorMultiDetail();
	}
	else
	{
		ZTEdor();
	} 
}

//减人按钮
function ZTEdor(){
	
	
	  
	  var i = 0;
    var chkFlag = false;
    for (i = 0; i < LCInsuredGrid.mulLineCount; i++ )
    {
        if (LCInsuredGrid.getChkNo(i))
        {
          chkFlag = true;
          break;
        }        
    }
    if (chkFlag)
    {
    		lockScreen('lkscreen');
        document.all("Transact").value = "INSERT||MUlTIEDOR"
        var showStr = "正在进行集体下个人保全，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
        fm.action = "./GEdorTypeMultiDetailSubmit.jsp";
        fm.submit();
        
    }
    else
    {
        alert("请选择需要操作的记录!");
    }   
}

//================
function diskInput()
{
	if ( fm.GrpContNo.value == "")
	{
		alert("请先查询保单信息");
		return ;
	}
	  var tGrpContNo = fm.GrpContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var tEdorType = fm.EdorType.value;

		var tFileName=fm2.all('FileName').value;
		//alert(tFileName);
		if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		if(tFileName != tGrpContNo)
		{
			alert("文件名与团体保单号不一致,请检查文件名!");
			return ;
		}
   //alert("成功导入");	
    document.all("Transact").value = "IMPORT||EDOR"

    
    var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
    fm2.action = "./GEdorTypeMultiDetailDisk.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorAcceptNo="+tEdorAcceptNo+"&EdorType="+tEdorType+"";
    lockScreen('lkscreen');
    fm2.submit();
}

//=================================
function initPolRiskCode()
{
    var tGrpContNo = document.all('GrpContNo').value;
    
    var sqlid831174147="DSHomeContSql831174147";
var mySql831174147=new SqlClass();
mySql831174147.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174147.setSqlId(sqlid831174147);//指定使用的Sql的id
mySql831174147.addSubPara(tGrpContNo);//指定传入的参数
var tSql=mySql831174147.getString();
    
//    var tSql = "select a.riskcode,b.riskname from lcgrppol a,lmrisk b where a.riskcode = b.riskcode and a.grpcontno = '"+tGrpContNo+"'";
    document.all("GrpRiskCode").CodeData = easyQueryVer3(tSql, 1, 0, 1);
}

//=========================
function inputKinds(objCheck)
{	
	if(objCheck.checked == true) 
	{
		riskcodeDiv.style.display = "";
	}
	else
	{
		riskcodeDiv.style.display = "none"; 
	}
}

// 保存按钮
function ZTSave(){
	

		var rowNum=LCInsured2Grid.mulLineCount;
	//alert(rowNum);
		if(rowNum<1)
		{
			alert("请先进行减人，再保存！");
			return;
		}
		
		var sqlid831174346="DSHomeContSql831174346";
var mySql831174346=new SqlClass();
mySql831174346.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174346.setSqlId(sqlid831174346);//指定使用的Sql的id
mySql831174346.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831174346.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql831174346.addSubPara(fm.EdorType.value );//指定传入的参数
var tSql=mySql831174346.getString();
		
//		var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
		var tArr = easyExecSql(tSql, 1, 0, 1);
		var tEdorState;
		if (tArr != null)
		{
		  tEdorState = tArr[0][0]; 
		}
		
		if(tEdorState == "1")
		{
			if(!confirm("您已经保存过本次操作，不建议重复保存，是否继续？"))
			{
				return;
			}
		}
			

		var TotalPeople = 0;
		var ZTPeople;
		
		var sqlid831174441="DSHomeContSql831174441";
var mySql831174441=new SqlClass();
mySql831174441.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174441.setSqlId(sqlid831174441);//指定使用的Sql的id
mySql831174441.addSubPara(fm.GrpContNo.value);//指定传入的参数
var tSql=mySql831174441.getString();
		
//		var tSql = "select count(*) from lccont where grpcontno = '"+fm.GrpContNo.value+"' and appflag = '1' ";
		var tArr = easyExecSql(tSql, 1, 0, 1);
		if (tArr != null)
		{
		  TotalPeople = tArr[0][0];
		  
		}
		//alert(TotalPeople);
		
		var sqlid831174537="DSHomeContSql831174537";
var mySql831174537=new SqlClass();
mySql831174537.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174537.setSqlId(sqlid831174537);//指定使用的Sql的id
mySql831174537.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831174537.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql831174537.addSubPara(fm.EdorType.value );//指定传入的参数
tSql=mySql831174537.getString();
		
//		tSql = "select count(*) from lpcont where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
		tArr = easyExecSql(tSql, 1, 0, 1);
		 
		if (tArr != null)
		{
		  ZTPeople = tArr[0][0];  
		  
		} 
		
		//alert(TotalPeople+"_*_"+ZTPeople);
		if(TotalPeople == ZTPeople)
		{
				alert("退保人数等于该团单下实际投保人数，请去做解除合同（CT）操作！");
				return;

		}
		var ZTRet = Arithmetic(TotalPeople*0.3,'-',ZTPeople,2);
		//alert(ZTRet);
		
		
		if(ZTRet < 0)
		{
			if(!confirm("退保人数超过投保人数的30%，确认本次操作？"))
			{
				return;
			}
		}
		lockScreen('lkscreen');
    document.all("Transact").value = "INSERT||SAVEEDOR"
    var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
    fm.action = "./GEdorTypeMultiDetailSubmit.jsp";
    fm.submit();	
	}
	
//=====================	
function checkPubInsu(tContNo){
	
	//alert("This is checkPubInsu");
	if (tContNo == null || tContNo == ""){
		alert("传入个单合同号为空！");
		return false;
	}
	var strSQL = "";
	
	var sqlid831174640="DSHomeContSql831174640";
var mySql831174640=new SqlClass();
mySql831174640.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174640.setSqlId(sqlid831174640);//指定使用的Sql的id
mySql831174640.addSubPara(tContNo);//指定传入的参数
strSQL=mySql831174640.getString();
	
//	strSQL = "select Count(*) from LCPol a where PolTypeFlag = '2' and exists "
//	       + "(select * from LCPol where ContNo = '"
//	       + tContNo + "' and GrpContNo = a.GrpContNo)";
  if (strSQL != null && strSQL != ""){	       
		var tNumPubInsu = easyExecSql(strSQL);       
	  if (tNumPubInsu - 1 == 0){
	  		return true;	
	  }		
	}		
	
	return false;
}

//=====================	
function QueryEdorInfo()
{	
	var tEdortype = document.all('EdorType').value;
	if(tEdortype != null || tEdortype != '')
	{
		
		var sqlid831174802="DSHomeContSql831174802";
var mySql831174802=new SqlClass();
mySql831174802.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174802.setSqlId(sqlid831174802);//指定使用的Sql的id
mySql831174802.addSubPara(tEdortype);//指定传入的参数
var strSQL=mySql831174802.getString();
		
//		var strSQL = "select distinct EdorCode, EdorName from LMEdorItem where EdorCode = '" 
//		           + tEdortype + "'";
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}		
	var arrSelected = new Array();	
	turnPage5.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage5.strQueryResult);  
  try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { };    
  
  var sqlid831175421="DSHomeContSql831175421";
var mySql831175421=new SqlClass();
mySql831175421.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831175421.setSqlId(sqlid831175421);//指定使用的Sql的id
mySql831175421.addSubPara(document.all('EdorNo').value);//指定传入的参数
var moneysql=mySql831175421.getString();
  
//  var moneysql = "select sum(GetMoney) from LJSGetEndorse where FeeOperationType = "
//               + "'ZT' and OtherNo = '" + document.all('EdorNo').value + "'";	 
	var arrResult = easyExecSql(moneysql);
	try {document.all('GetMoney').value = arrResult[0][0];} catch(ex) { };
	if (document.all('GetMoney').value == "null" || document.all('GetMoney').value == "" ){
  	document.all('GetMoney').value = 0.0;
  }
  //document.all('GetMoney').value = "";
  //riskcodeDiv.style.display = "none"; 
}
// 全部保存按钮
function ZTAllSave()
{
    	if(fm.chkPubInsu.checked == true){  
    		document.all('IfToPubInsu').value = "true";
    	} else {
    		document.all('IfToPubInsu').value = "";
    	}	
    document.all("Transact").value = "INSERT||SAVEEDOR"
    var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
    fm.action = "./GEdorTypeMultiDetailAllSubmit.jsp";   
    fm.submit();	
}

function queryGrpInfo()
{
		var sqlid831174917="DSHomeContSql831174917";
var mySql831174917=new SqlClass();
mySql831174917.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831174917.setSqlId(sqlid831174917);//指定使用的Sql的id
mySql831174917.addSubPara(fm.GrpContNo.value);//指定传入的参数
mySql831174917.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql831174917.addSubPara(fm.EdorType.value );//指定传入的参数
var tSql=mySql831174917.getString();
		
//		var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
		var tArr = easyExecSql(tSql, 1, 0, 1);
		var tEdorState;
		if (tArr != null)
		{
		  tEdorState = tArr[0][0];
		  
		}
		LCGrpContDiv.style.display = 'none';
		LCGrpPolDiv.style.display = 'none';
		if(tEdorState == "3")
		{
			//alert(tEdorState);
			LCGrpContDiv.style.display = 'none';
			LCGrpPolDiv.style.display = 'none';
		}
		
		if(tEdorState == "1")
		{
			initLPGrpPolGrid();
			initLPGrpContGrid();
			LCGrpContDiv.style.display = '';
			LCGrpPolDiv.style.display = '';
			
			var sqlid831175039="DSHomeContSql831175039";
var mySql831175039=new SqlClass();
mySql831175039.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831175039.setSqlId(sqlid831175039);//指定使用的Sql的id
mySql831175039.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql831175039.addSubPara(fm.EdorType.value );//指定传入的参数
mySql831175039.addSubPara(fm.GrpContNo.value);//指定传入的参数
var tContSql=mySql831175039.getString();
			
//			var tContSql = " select p.grpcontno,"
//									 + " (select sum(prem) from lcpol where grpcontno = p.grpcontno and appflag = '1'),"
//									 + " (select sum(amnt) from lcpol where grpcontno = p.grpcontno and appflag = '1'),"
//									 + " (select sum(mult) from lcpol where grpcontno = p.grpcontno and appflag = '1'),"
//									 + " (select count(*) from lccont where grpcontno = p.grpcontno and appflag = '1'),"
//									 + " p.prem,p.amnt,p.mult,p.peoples "
//									 + " from lpgrpcont p where p.edorno = '" + fm.EdorNo.value + "' and p.edortype = '" + fm.EdorType.value + "' and p.grpcontno = '"+fm.GrpContNo.value+"'";
									 
			turnPage6.queryModal(tContSql, LPGrpContGrid); 
			
			var sqlid831175139="DSHomeContSql831175139";
var mySql831175139=new SqlClass();
mySql831175139.setResourceName("bq.GEdorTypeMultiDetailInputSql");//指定使用的properties文件名
mySql831175139.setSqlId(sqlid831175139);//指定使用的Sql的id
mySql831175139.addSubPara(fm.EdorNo.value);//指定传入的参数
mySql831175139.addSubPara(fm.EdorType.value );//指定传入的参数
mySql831175139.addSubPara(fm.GrpContNo.value);//指定传入的参数
var tPolSql=mySql831175139.getString();
			
//			var tPolSql  = " select p.grppolno,p.riskcode,"
//									 + " (select sum(prem) from lcpol where grppolno = p.grppolno and appflag = '1'),"
//									 + " (select sum(amnt) from lcpol where grppolno = p.grppolno and appflag = '1'),"
//									 + " (select sum(mult) from lcpol where grppolno = p.grppolno and appflag = '1'),"
//									 + " (select count(*) from lcpol where grppolno = p.grppolno and appflag = '1'),"
//									 + " p.prem,p.amnt,p.mult,p.peoples2 "
//									 + " from lpgrppol p where p.edorno = '"+fm.EdorNo.value+"' and p.edortype = '"+fm.EdorType.value+"' and p.grpcontno = '"+fm.GrpContNo.value+"'";
			turnPage7.queryModal(tPolSql, LPGrpPolGrid); 
		}
}



                    