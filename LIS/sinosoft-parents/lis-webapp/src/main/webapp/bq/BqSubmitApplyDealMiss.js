//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var returnPage= new turnPageClass();
//提交完成后所作操作
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

////初始化"接收呈报队列"表格------呈报类型 missionprop6 = '0'--中心到分公司
function easyQueryClick()
{ 
	var sqlid1="DSHomeContSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.BqSubmitApplyDealMissInputSql");//指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(usercode);//指定传入的参数
	var strSQL=mySql1.getString();
	
//	var strSQL ="select a.SubNo,'',a.GrpContNo,(select edorname from lmedoritem where edorcode = a.EdorType and appobj ='G'),a.SubPer,a.substate,(select codename from ldcode where codetype = 'substate' and code = a.substate),a.SubDate,a.subtimes,(select subper from lpsubmitapplytrace where subno = a.subno and serialno = (select max(serialno) from lpsubmitapplytrace where subno = a.subno)),a.ModifyDate from LPSubmitApply a where a.DispPer='"+usercode+"' and substate <> 'C' order by a.SubNo";
    turnPage.pageLineNum=5;    //每页5条
    turnPage.queryModal(strSQL,LPSubmitApplyGrid);
}


//LLSubmitApplyGrid点击事件
function LPSubmitApplyGridClick()
{
    var i = LPSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = LPSubmitApplyGrid.getRowColData(i,1); 
        
        
    }
    //LoadFlag=1:接收呈报，并处理
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=1&SubNo="+tSubNo;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//初始化“反馈队列”表格  呈报类型 missionprop6 = '2'--总公司到分公司
function ReQueryClick()
{
	var sqlid2="DSHomeContSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.BqSubmitApplyDealMissInputSql");//指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(usercode);//指定传入的参数
	var strSQL=mySql2.getString();
	
//	var strSQL ="select a.SubNo,'',a.GrpContNo,(select edorname from lmedoritem where edorcode = a.EdorType and appobj ='G'),a.DispPer,a.substate,(select codename from ldcode where codetype = 'substate' and code = a.substate),a.SubDate,a.ModifyDate from LPSubmitApply a where a.SubPer='"+usercode+"' and a.SubState in ('1','2','4') order by a.SubNo";
    returnPage.pageLineNum=5;    //每页5条
    returnPage.queryModal(strSQL,ReLPSubmitApplyGrid);
}

//LLSubmitApplyGrid点击事件
function ReLPSubmitApplyGridClick()
{
    var i = ReLPSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = ReLPSubmitApplyGrid.getRowColData(i,1); 
        
        
        
    }
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=3&SubNo="+tSubNo;    
//    location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//申请按纽事件
function apply()
{
	//var arrQueryResult = easyExecSql("select * from lcgrpcont where grpcontno='" + fm.PrtNo.value + "'", 1, 0);
  //
  //if(arrQueryResult==null)
  //{
  //	alert("不存在此合同号！");
  //	return;
  //}
	
	var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=2";      
  location.href=strUrl;
}