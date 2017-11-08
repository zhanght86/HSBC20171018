/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2007 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-05-24
 * @direction: 保全万能险部分领取js
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeOPInputSql";

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
        top.opener.getEdorItemGrid();
    }
    catch (ex) {}
}
function queryAccData()
{
	document.all('fmtransact').value ="OP||QUERY"; 
    var showStr="正在进行计算，请您稍候并且不要修改屏幕上的值或链接其他页面！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action="./PEdorTypeOPSubmit.jsp"
	fm.submit();
}
function afterCalData(aInsuAccBala,aCanGetMoney,aWorkNoteFee)
{
	showInfo.close();
  fm.InsuAccBala.value = aInsuAccBala;
  fm.CanGetMoney.value = aCanGetMoney;
  fm.WorkNoteFee.value = aWorkNoteFee;
  showActuWantMoney();
}
function edorTypeOPSave()
{
    if(document.all('tAAType').value == "1"){
        alert("该保单处于犹豫期内，不允许领取！");
        return; 
    }
    if(parseFloat(fm.GetMoney.value)>parseFloat(fm.CanGetMoney.value))
   {
  	    alert("输入领取金额大于可用金额！");
        return; 
  	
  	}
  	if(parseFloat(fm.GetMoney.value) < 1000 || parseFloat(fm.GetMoney.value) % 100 != 0)
  	{
  		  alert("领取金额最少1000元,且为100元的整数倍！");
        return; 
  		}
	document.all('fmtransact').value ="OP||MAIN";  
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="./PEdorTypeOPSubmit.jsp"
	fm.submit();
	//showActuWantMoney();
}

function afterSubmit( FlagStr, content,Result)
{
	showInfo.close();	
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showActuWantMoney();
	//initBackFeePolGrid();       
  //initBackFeeDetailGrid();   
	queryBackFee();              //提交之后再次查询补退费明细

}  

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	 {
 		  divEdorquery.style.display = "";
 	 }
}

function initRemark()
{
	  var edorAccpetNo  = document.all("EdorAcceptNo").value;
	  //var contNO = document.all("ContNo").value;
	  var edorType = document.all("EdorType").value;
//	  var strSql = "select standbyflag3 from lpedoritem where edoracceptno = '" + edorAccpetNo + "' and edortype = '" + edorType +"'";
    
    var strSql = "";
	var sqlid1="PEdorTypeOPInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(edorAccpetNo);//指定传入的参数
	mySql1.addSubPara(edorType);
	strSql=mySql1.getString();
    
    var brr = easyExecSql(strSql);
    //用的时候要判断结果集是否有数，否则如果查不到会报错
    if(brr){
        document.all('Remark').value = brr[0][0];
    }
}

function showActuWantMoney(){
    var EdorNo = document.all('EdorNo').value;
    var edorAccpetNo  = document.all("EdorAcceptNo").value;
    //领取金额
    var tGetMoney,tGetMoneyFee;
/*    var strSql="select nvl(abs(sum(getmoney)),0.0) from lpedoritem where edoracceptno='"+edorAccpetNo
            +"'";
*/    
    var strSql = "";
	var sqlid2="PEdorTypeOPInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(edorAccpetNo);//指定传入的参数
	strSql=mySql2.getString();
    
    var brr = easyExecSql(strSql);        
    if(brr){
        tGetMoney = brr[0][0];
    }else{
        tGetMoney = 0.0;
    }
   //查询领取手续费
/*   var strSql0="select nvl(StandbyFlag1,'') from lpedoritem where EdorNo='"+EdorNo
            +"' and edorType = 'OP'";
*/    
    var strSql0 = "";
	var sqlid3="PEdorTypeOPInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(EdorNo);//指定传入的参数
	strSql0=mySql3.getString();
    
    var crr = easyExecSql(strSql0);
    //alert(crr);        
    if(crr){
        tGetMoneyFee = crr[0][0];
    }else{
        tGetMoneyFee = 0.0;
    }
    document.all('GetMoney').value=tGetMoney;
    document.all('WorkNoteFee').value=tGetMoneyFee;
}

function getPolInfo()
{
    
    var tContNo= document.all('ContNo').value;		  
    // 书写SQL语句
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where polno = mainpolno and ContNo='"+tContNo+"'";
    
    var strSQL = "";
	var sqlid4="PEdorTypeOPInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql4.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {
        return false;
    }
    
    //查询成功则拆分字符串，返回二维数组
    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象

    turnPage.pageDisplayGrid = PolGrid;    
    //保存SQL语句
    turnPage.strQuerySql = strSQL; 
    //设置查询起始位置
    turnPage.pageIndex = 0;  
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
    
    
}