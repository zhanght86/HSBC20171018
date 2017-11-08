/** 
 * 程序名称：LDUWUserInit.jsp
 * 程序功能：功能描述 该文件中包含客户端需要处理的函数和事件
 * 创建日期：2005-01-24 18:15:01
 * 创建人  ：ctrHTML
 * 更新人  ：
 */
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus() {
	if(showInfo!=null) {
	  try {
	    showInfo.focus();
	  }
	  catch(ex) {
	    showInfo=null;
	  }
	}
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
  showInfo.close();
  if (FlagStr == "Fail" ) {
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
  else {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}
// 查询按钮
function easyQueryClick() {
	//此处书写SQL语句			     
/*	
	var strSql = "select a.GetNoticeNo, a.otherno, b.name, a.PayDate, a.SumDuePayMoney, a.bankcode, a.bankaccno, a.accname "
	           + " from LJSPay a, ldperson b where a.appntno=b.customerno and OtherNoType='2' "
          	 + getWherePart("a.GetNoticeNo", "GetNoticeNo2")
          	 + getWherePart("a.OtherNo", "OtherNo")
          	 + getWherePart("a.BankCode", "BankCode")
          	 + getWherePart("a.SumDuePayMoney", "SumDuePayMoney");
  
  if (fm.AppntName.value != "") strSql = strSql + " and a.appntno in (select c.customerno from ldperson c where name='" + fm.AppntName.value + "')";
  if (fm.PrtNo.value != "") strSql = strSql + " and a.otherno in (select polno from lcpol where prtno='" + fm.PrtNo.value + "')";
*/  			    
//   var strSql = "select UserCode,(select codename from ldcode where codetype='uwtype' and code=uwtype) end,UWPopedom from LDUWUser where 1=1 "
//    + getWherePart("UserCode", "UserCode")
//    + getWherePart("UWType", "UWType")
//    //2008-12-30 ln add --补充查询条件
//    + getWherePart("UpUWPopedom", "UpUWPopedom")
//    + getWherePart("AddPoint", "AddPoint")
//    //end 2008-12-30   
//    + getWherePart("UWPopedom", "UWPopedom")
//    + getWherePart("Operator", "Operator")
//    + getWherePart("ManageCom", "ManageCom")
//    + getWherePart("MakeDate", "MakeDate")
//    + getWherePart("MakeTime", "MakeTime")
//    + getWherePart("ModifyDate", "ModifyDate")
//    + getWherePart("ModifyTime", "ModifyTime")
  ;
   
 	var  UserCode0 = window.document.getElementsByName(trim("UserCode"))[0].value;
  	var  UWType0 = window.document.getElementsByName(trim("UWType"))[0].value;
  	var  UpUWPopedom0 = window.document.getElementsByName(trim("UpUWPopedom"))[0].value;
  	var  AddPoint0 = window.document.getElementsByName(trim("AddPoint"))[0].value;
  	var  UWPopedom0 = window.document.getElementsByName(trim("UWPopedom"))[0].value;
  	var  Operator0 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  MakeDate0 = window.document.getElementsByName(trim("MakeDate"))[0].value;
  	var  MakeTime0 = window.document.getElementsByName(trim("MakeTime"))[0].value;
  	var  ModifyDate0 = window.document.getElementsByName(trim("ModifyDate"))[0].value;
  	var  ModifyTime0 = window.document.getElementsByName(trim("ModifyTime"))[0].value;
	var sqlid0="LDIndUWUserQueryInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDIndUWUserQueryInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(UserCode0);//指定传入的参数
	mySql0.addSubPara(UWType0);//指定传入的参数
	mySql0.addSubPara(UpUWPopedom0);//指定传入的参数
	mySql0.addSubPara(AddPoint0);//指定传入的参数
	mySql0.addSubPara(UWPopedom0);//指定传入的参数
	mySql0.addSubPara(Operator0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	mySql0.addSubPara(MakeDate0);//指定传入的参数
	mySql0.addSubPara(MakeTime0);//指定传入的参数
	mySql0.addSubPara(ModifyDate0);//指定传入的参数
	mySql0.addSubPara(ModifyTime0);//指定传入的参数
	var strSql=mySql0.getString();
   
   
  //alert(strSql);
	turnPage.queryModal(strSql, LDUWUserGrid);
}
function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
  alert("此处选择某一行的代码");
//	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
//	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
//	  fm.GetNoticeNo.value = turnPage.arrDataCacheSet[index][0];
 // }
}
function returnParent()
{
        var arrReturn = new Array();
	var tSel = LDUWUserGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				//alert(tSel);
				arrReturn = getQueryResult();
				//alert("aaa="+arrReturn[0][0]);
				top.opener.afterQuery( arrReturn );
				//alert("a");
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
	tRow = LDUWUserGrid.getSelNo();
	//alert("111" + tRow);
	//edit by guo xiang at 2004-9-13 17:54
	//if( tRow == 0 || tRow == null || arrDataSet == null )
	if( tRow == 0 || tRow == null )
	    return arrSelected;
	
	arrSelected = new Array();
	
	//设置需要返回的数组
	//edit by guo xiang at 2004-9-13 17:54
	arrSelected[0] = new Array();
	arrSelected[0] = LDUWUserGrid.getRowData(tRow-1);
	//arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

function afterQuery(arrQueryResult)
{
	 if( arrQueryResult != null )
	 {
//	 	var strSQL="select * from LDUWUser where UserCode='"+arrQueryResult[0][0]+"'";
	 	
		var sqlid1="LDIndUWUserQueryInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.AgentQualityManageSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(arrQueryResult[0][0]);//指定传入的参数
		var strSQL=mySql1.getString();
	 	
	 	arrResult = easyExecSql(strSQL);
	 	document.all('UserCode').value= arrResult[0][0];
                document.all('UWType').value= arrResult[0][1];
                document.all('UpUserCode').value= arrResult[0][2];
                document.all('UpUWPopedom').value= arrResult[0][3];
                
               
        
                document.all('UWPopedom').value= arrResult[0][7];
                
     
                document.all('Remark').value= arrResult[0][15];
                document.all('Operator').value= arrResult[0][16];
                document.all('ManageCom').value= arrResult[0][17];
                document.all('MakeDate').value= arrResult[0][18];
                document.all('MakeTime').value= arrResult[0][19];
                document.all('ModifyDate').value= arrResult[0][20];
                document.all('ModifyTime').value= arrResult[0][21];
                
//                strSQL2="select UWState,UWStateName from LDUWGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid2="LDIndUWUserQueryInputSql2";
        		var mySql2=new SqlClass();
        		mySql2.setResourceName("config.AgentQualityManageSql"); //指定使用的properties文件名
        		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
        		mySql2.addSubPara(arrQueryResult[0][0]);//指定传入的参数
        		strSQL2=mySql2.getString();
                
                turnPage.queryModal(strSQL2, UWResultGrid);
               
//                strSQL2="select UWKind,MaxAmnt from LDUWAmntGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid3="LDIndUWUserQueryInputSql3";
        		var mySql3=new SqlClass();
        		mySql3.setResourceName("config.AgentQualityManageSql"); //指定使用的properties文件名
        		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
        		mySql3.addSubPara(arrQueryResult[0][0]);//指定传入的参数
        		strSQL2=mySql3.getString();
                
                turnPage.queryModal(strSQL2, UWMaxAmountGrid);
                showCodeName();
        }
}
function showCodeName()
{
	showCodeName('UWType','UWType','UWTypeName');
	showCodeName('uwpopedom','UWPopedom','UWPopedomName');
	showCodeName('edpopedom','edpopedom','edpopedomName');
	showCodeName('clPopedom','ClaimPopedom','claimpopedomName');
	showCodeName('upuwpopedom','UpUWPopedom','UpUserCodeName');
}
            