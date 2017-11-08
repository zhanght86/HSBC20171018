var SQL = "";
var turnPage = new turnPageClass();
var showInfo;
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else{
		parent.fraMain.rows = "0,0,0,0,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}
// 查询按钮
function easyQuery(){		
	if(fm.StartDate.value == "" ||fm.StartDate.value == null){
		alert("请选择起始日期！");
		return false;
	}
	if(fm.EndDate.value == "" ||fm.EndDate.value == null){
		alert("请选择截止日期！");
		return false;
	}
	document.all("query").disabled="true";
	document.all("toExcel").disabled="";
	var StartDate = fm.StartDate.value;   			//开始时间
	var EndDate = fm.EndDate.value; 	  			//截止时间
	var RiskType = fm.RiskType.value;   			//险种类别
	var Blacklist = fm.Blacklist.value;           	//黑名单标记
	var InsuranceMoney = fm.InsuranceMoney.value;	//保全加费
	var ManageCom = fm.ManageCom.value;
	var flag=compareDate(StartDate,EndDate);
	
	if(flag==1){
		alert("开始日期晚于截止日期");
		return false;
	}
	
	var tSQL = " and not exists(select 'X' from LPEdorItem where ContNo=b.ContNo and EdorType='NI' and EdorState='0') ";
	if(InsuranceMoney=="1"){
		tSQL = "";
	}
	SQL="select (select Name from LDCom where ComCode=a.ManageCom) 管理机构名称,"
        +" a.ManageCom 管理机构代码, "
        +" a.SaleChnl 销售渠道, "
        +" a.AgentType 二级渠道分类, "
        +" a.GrpContNo 团体合同号, "
        +" a.PrtNo 投保单号,a.grpname 投保单位名称, "
        +" a.RiskCode 险种代码, "
        +" (select sum(Amnt) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+" ) 总保额, "
        +" (select sum(Prem) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+"  ) 总保费, "
        +" (select sum(InsuredPeoples) from LCPol b where b.GrpPolNo=a.GrpPolNo and AppFlag in('1','4')"+tSQL+"  ) 被保险人人数, "
        +" a.CValiDate 生效日, "
        +" (select SignDate from LCGrpCont where GrpContNo=a.GrpContNo) 签单日, "
        +" a.AgentCode 代理人编码, "
        +" (select Name from LAAgent where AgentCode=a.AgentCode) 代理人姓名, "
        +" (select RiskType from LMRiskApp where RiskCode=a.RiskCode) 险种类别, "
        +" (select nvl(BlacklistFlag,'0') from LDGrp where CustomerNo=a.CustomerNo) 黑名单标记 "
		+" from LCGrpPol a where 1=1 "
		+" and a.AppFlag in ('1','4') "	
		+ getWherePart('a.AgentCode','AgentCode')		
		+ getWherePart('a.AgentType','AgentType')		
		+ getWherePart('a.RiskCode','RiskCode')
		+ getWherePart('a.SaleChnl','SaleChnl');
	
	if(ManageCom!=null && ManageCom!=""){
		SQL+=" and a.ManageCom like '"+ManageCom+"%' ";
	}
	 if(document.all('GrpName').value!=null&&document.all('GrpName').value!="")
	{
		SQL = SQL+" and grpname like '%%"+document.all('GrpName').value+"%%' "
	}
	if(RiskType!=null && RiskType!=""){
		SQL+=" and exists(select 'X' from LMRiskApp where RiskCode=a.RiskCode and RiskType='"+RiskType+"') ";
	}	
	if(Blacklist!=null && Blacklist!=""){
		SQL+=" and exists(select 'X' from LDGrp where CustomerNo=a.CustomerNo and nvl(BlacklistFlag,'0')='"+Blacklist+"') "
	}
	SQL+=" and exists(select 'X' from LCGrpCont where GrpContNo=a.GrpContNo and SignDate between date '"+StartDate+"' and '"+EndDate+"' )";
	
	fm.sql.value=SQL;
	
	turnPage.queryModal(SQL, PolInfoGrid);	  
	document.all("query").disabled="";
	if(PolInfoGrid.mulLineCount<1){
		initPolInfoGrid();
  	  	alert("没有查询到符合条件的记录！");
  	  	return false; 
	}
}
//导出到EXCEL
function ToExcel() {  
	if(PolInfoGrid.mulLineCount<1){		
  	  	alert("没有可导出的记录！");
  	  	return false;
	} 
	document.all("toExcel").disabled="true";	
	fm.action="./GrpPolInfoToExcel.jsp";
	fm.target="fraSubmit";	
	document.getElementById("fm").submit(); //提交	
}
// 提交按钮
function chageState(){
	var i = 0;
	var flag = 0;
	for( i = 0; i < PolInfoGrid.mulLineCount; i++ )	{
		if( PolInfoGrid.getChkNo(i) == true ){
			flag = 1;
			break;
		}
	}
	if( flag == 0 ){
		alert("请先选择至少一条记录！");
		return false;
	}
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//disable打印按钮，防止用户重复提交
	document.all("chageButton").disabled=true;
	document.getElementById("fm").submit();	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
	showInfo.close();window.focus();
	//无论打印结果如何，都重新激活打印按钮
	document.all("chageButton").disabled=false;
	if (FlagStr == "Fail" ){
		//如果失败，则返回错误信息
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");			
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQuery();
	}
	else{
		//如果提交成功，则执行查询操作
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQuery();
	}
}
