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
	var iHeight=350;     //弹出窗口的高度; 
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
	var iHeight=350;     //弹出窗口的高度; 
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

//  var strSql = "select hospitcode,a.hospitalname,a.hospitalgrade,mngcom,a.validitydate,a.address "
//         		 + " from  LDHospital a where 1=1 "
//             + getWherePart("hospitcode", "HospitalCode")
//             + getWherePart("hospitalname", "HospitalName",'like')
//             + getWherePart("hospitalgrade", "HospitalGrade")
//             + getWherePart("mngcom", "ManageCom",'like')
//             + getWherePart("validitydate", "ValidityDate",'<=')      
//             + " order by hospitcode ";
  
	var  HospitalCode0 = window.document.getElementsByName(trim("HospitalCode"))[0].value;
  	var  HospitalName0 = window.document.getElementsByName(trim("HospitalName"))[0].value;
  	var  HospitalGrade0 = window.document.getElementsByName(trim("HospitalGrade"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  ValidityDate0 = window.document.getElementsByName(trim("ValidityDate"))[0].value;
	var sqlid0="LDHospitalDetailQueryInitSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDHospitalDetailQueryInitSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(HospitalCode0);//指定传入的参数
	mySql0.addSubPara(HospitalName0);//指定传入的参数
	mySql0.addSubPara(HospitalGrade0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	mySql0.addSubPara(ValidityDate0);//指定传入的参数
	var strSql=mySql0.getString(); 
  
  //alert(strSql);
	turnPage.queryModal(strSql, LDHospitalGrid);
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
	var tSel = LDHospitalGrid.getSelNo();
	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				//alert(tSel);
				arrReturn[0] = new Array();
			 arrReturn[0]  = LDHospitalGrid.getRowData(tSel-1);
			 //alert(arrReturn[0][0]);
			 if(arrReturn[0][0]=="")
			 {
			 	 alert("请先查询,再返回!");
			 	 return false;
			 }
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
	tRow = LDHospitalGrid.getSelNo();
	alert("111" + tRow);
	//edit by guo xiang at 2004-9-13 17:54
	//if( tRow == 0 || tRow == null || arrDataSet == null )
	if( tRow == 0 || tRow == null )
	    return arrSelected;
	
	arrSelected = new Array();
	
	//设置需要返回的数组
	//edit by guo xiang at 2004-9-13 17:54
	arrSelected[0] = new Array();
	arrSelected[0] = LDHospitalGrid.getRowData(tRow-1);

	return arrSelected;
}

function afterQuery(arrQueryResult)
{
	 if( arrQueryResult != null )
	 {
	 	alert(1)
//	 	var strSQL="select * from LDUWUser where UserCode='"+arrQueryResult[0][0]+"'";
	 	
		var sqlid1="LDHospitalDetailQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.LDHospitalDetailQueryInitSql"); //指定使用的properties文件名
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
                
        		var sqlid2="LDHospitalDetailQueryInitSql2";
        		var mySql2=new SqlClass();
        		mySql2.setResourceName("config.LDHospitalDetailQueryInitSql"); //指定使用的properties文件名
        		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
        		mySql2.addSubPara(arrQueryResult[0][0]);//指定传入的参数
        		strSQL2=mySql2.getString(); 
                
                turnPage.queryModal(strSQL2, UWResultGrid);
               
//                strSQL2="select UWKind,MaxAmnt from LDUWAmntGrade where UserCode='"+arrQueryResult[0][0]+"'";
                
        		var sqlid3="LDHospitalDetailQueryInitSql3";
        		var mySql3=new SqlClass();
        		mySql3.setResourceName("config.LDHospitalDetailQueryInitSql"); //指定使用的properties文件名
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
            