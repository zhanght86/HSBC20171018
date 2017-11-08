//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
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
        alert("在LLLdPersonQuery.js-->resetForm函数中发生异常:初始化界面错误!");
    }
} 

//取消按钮对应操作
function cancelForm()
{
  
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作  
}           

// 查询按钮
function easyQueryClick()
{
    // 初始化表格
    initLLClaimRegisterGrid();
    var strSQL = "";
//select rgtno,rgtantname,rgtantsex,relation,rgtantaddress,rgtantphone,customerno,accidentdate,RgtConclusion,accidentsite,accidentreason,operator from llregister;
  /*  strSQL = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,operator,accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState,MngCom from llregister where 1=1 "
           + getWherePart( 'RptNo' )
           + getWherePart( 'RptorName' )
           + getWherePart( 'RptorPhone' )
           + getWherePart( 'RptorAddress' )
           + getWherePart( 'Relation' )
           + getWherePart( 'RgtConclusion' );*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimRegisterQueryInputSql");
	mySql.setSqlId("LLClaimRegisterQuerySql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.RptorName.value ); 
	mySql.addSubPara(fm.RptorPhone.value ); 
	mySql.addSubPara(fm.RptorAddress.value ); 
	mySql.addSubPara(fm.Relation.value ); 
	mySql.addSubPara(fm.RgtConclusion.value ); 
//    execEasyQuery(strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid);
//    alert(strSQL);
}

//对应RadioBox的单记录返回
function returnParent()
{
    var i = LLClaimRegisterGrid.getSelNo();
    if (i != 0)
    {      
        i = i - 1;
        var arr = new Array();
//select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,operator,
//       accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState,MngCom from llregister
        arr[0] = LLClaimRegisterGrid.getRowColData(i,1);
        arr[1] = LLClaimRegisterGrid.getRowColData(i,2);  
        arr[2] = LLClaimRegisterGrid.getRowColData(i,3);  
        arr[3] = LLClaimRegisterGrid.getRowColData(i,4);
        arr[4] = LLClaimRegisterGrid.getRowColData(i,5);
        arr[5] = LLClaimRegisterGrid.getRowColData(i,6);
        arr[6] = LLClaimRegisterGrid.getRowColData(i,7);  
        arr[7] = LLClaimRegisterGrid.getRowColData(i,8);
        arr[8] = LLClaimRegisterGrid.getRowColData(i,9);
        arr[9] = LLClaimRegisterGrid.getRowColData(i,10);
        arr[10] = LLClaimRegisterGrid.getRowColData(i,11);
        arr[11] = LLClaimRegisterGrid.getRowColData(i,12);
//        alert(arr);         
    }
    if (arr)
    {
       top.opener.afterQueryLL2(arr);
       top.close();
    }
}
//
////参数为出生年月,如1980-5-9 
//function getAge(birth)
//{
//    var now = new Date();
//    var nowYear = now.getFullYear();
//    var oneYear = birth.substring(0,4);
//    var age = nowYear - oneYear;
//    if (age <= 0)
//    {
//        age = 0
//    }
//    return age;
//}
