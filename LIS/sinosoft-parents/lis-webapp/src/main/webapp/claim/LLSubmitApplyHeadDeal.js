var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var mySql = new SqlClass();
function easyQueryClick() 
{  	
    /*var strSQL = "";
    strSQL = "select ClmNO,SubNO,SubCount,CustomerNo,Customername,VIPFlag,InitPhase,SubType,SubRCode,SubDesc,SubPer,SubDate,SubDept,SubState,DispDept,DispPer from llsubmitapply"// where SubCount=1 and disptype is null";
           +" where ClmNO='"+document.all('ClmNo').value+"'"
           +" and SubNO='"+document.all('SubNO').value+"'"
           +" and SubCount='"+document.all('SubCount').value+"'"; */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLSubmitApplyHeadDealInputSql");
	mySql.setSqlId("LLSubmitApplyHeadDealSql1");
	mySql.addSubPara(document.all('ClmNo').value ); 
	mySql.addSubPara(document.all('SubNO').value ); 
	mySql.addSubPara(document.all('SubCount').value ); 
    var arr = easyExecSql(mySql.getString() );
    if (arr == null)
    {
	      alert("报案不存在!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //赔案号
        fm.SubNO.value=arr[0][1];  //呈报序号
        fm.SubCount.value=arr[0][2]; //呈报次数
        fm.CustomerNo.value=arr[0][3]; //出险人客户号
        fm.CustomerName.value=arr[0][4];//出险人客户姓名
        fm.VIPFlag.value=arr[0][5];   //VIP客户
        fm.InitPhase.value=arr[0][6]; //提起阶段
        fm.SubType.value=arr[0][7];    //呈报类型
        fm.SubRCode.value=arr[0][8];  //呈报原因
        fm.SubDesc.value=arr[0][9];  //呈报描述
        fm.SubPer.value=arr[0][10];   //呈报人
        fm.SubDate.value=arr[0][11];  //呈报日期
        fm.SubDept.value=arr[0][12];  //呈报机构
        fm.SubState.value=arr[0][13]; //呈报状态
        //fm.DispDept.value=arr[0][14]; //承接机构代码
        fm.DispPer.value=arr[0][15];  //承接人员编号    		
        showOneCodeName('llinitphase','InitPhase','InitPhaseName');    
        showOneCodeName('llsubtype','SubType','SubTypeName');    
        showOneCodeName('llsubstate','SubState','SubStateName');    
        showOneCodeName('station','SubDept','SubDeptName');   
    }
}     


//"呈报确认"提交按钮（只有回复意见）
function Replyport()
{

    if(fm.DispIdea.value=="" ||fm.DispIdea.value==null)
    {
    	alert("请填写回复意见!");
    	return;
    }
    fm.DispType.value="2";//处理类型
	fm.fmtransact.value = "UPDATE||Reply";   //修改记录
        	 
	submitForm();   //提交页面数据
}
function TurnBack()
{
    var strUrl= "LLSubmitApplyHeadDealMissInput.jsp?";
    location.href=strUrl;
}
  //提交数据
function submitForm()
{
   var i = 0;
   var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './LLSubmitApplyHeadDealSave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";   
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
        TurnBack(); //返回
    }
    tSaveFlag ="0";
}