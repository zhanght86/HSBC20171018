//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();  
//应该还款金额
var tSSumLoanMoney=0;
//实际欠款金额
var tASumLoanMoney=0;
var sqlresourcename = "bq.PEdorTypeRFInputSql";

function verify()
{

	  var tFlag=false;
		var tLength=LoanGrid.mulLineCount;
	  var tEdorNo=fm.EdorNo.value;
	  for(var i=0;i<tLength;i++)
	  {
	  	if(LoanGrid.getChkNo(i))
	  	{
	  	 tFlag=true;
	  	 //alert(LoanGrid.getRowColData(i,7));
	  	 tASumLoanMoney+=(parseFloat(LoanGrid.getRowColData(i,7)));
       if (LoanGrid.getRowColData(i, 7) == "")
       {
           alert("第" + (i+1) + "行还款金额不能为空");
           return false;
       }
       else
       {
         if (parseFloat(LoanGrid.getRowColData(i, 7)) > (parseFloat(LoanGrid.getRowColData(i, 4))+parseFloat(LoanGrid.getRowColData(i, 5)).toFixed(2)))
         {       	 
           alert("第" + (i+1) + "行还款大于借款，请确认金额！");
           return false;
         }
        if (parseFloat(LoanGrid.getRowColData(i, 7)) < parseFloat(LoanGrid.getRowColData(i, 5)))
         {
           alert("第" + (i+1) + "行还款小于本次还款利息，请确认金额！");
           return false;
         }
         if (parseFloat(LoanGrid.getRowColData(i, 7)) < 500)
         {
           if(!confirm("第" + (i+1) + "行还款小于500，还款金额不得小于500，是否继续？"))
           {
           	   return;
           	}
         }
       }	 
	  	} 
	  	
	  	if(i==(tLength-1)&&!tFlag)
	  	{
	  		alert("请至少选中一条要还的还款记录，要么请返回");
        return false;
	  	}
	  }	
	  	CountSumLoan();	
	  	
	  	//alert(tASumLoanMoney-tSSumLoanMoney);
	  	//alert(tSSumLoanMoney);
  	  	//累计借款等于还款金额则置为还清。
	  	if((tASumLoanMoney-tSSumLoanMoney)==0)
	  	{
	  		fm.PayOffFlag.value='1';	 
	  		tSSumLoanMoney=0; 	
	  		tASumLoanMoney=0;	
	  	}else{
	  		fm.PayOffFlag.value='0';
	  		tSSumLoanMoney=0; 	
	  		tASumLoanMoney=0;		 	  				
	  	}

  return true;
}

function edorTypeRFSave()
{
  
  if (!verify()) 
  {
    return; 
  }
  
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
 
  document.all('fmtransact').value = "INSERT||MAIN";
  fm.action="./PEdorTypeRFSubmit.jsp";
  fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content)
{ 
  try { showInfo.close(); } catch (e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  queryBackFee();
}

function returnParent()
{
    top.opener.initEdorItemGrid();
    top.opener.getEdorItemGrid();
    top.close();
    top.opener.focus();
}

function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
}

function ShowCustomerInfo()
{
  try
  {
    var tContNo=document.all("ContNo").value;
    //alert(tContNo);
    if(tContNo != null && tContNo != "")
    {
/*        var strSQL =" Select a.appntno,'投保人',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
										+" Union"
										+" Select i.insuredno,'被保人',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert(strSQL);
*/        
    var strSQL = "";
	var sqlid1="PEdorTypeRFInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        
        //判断是否查询成功
        if (!turnPage.strQueryResult) 
        {
            return false;
        }
        
       turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
				//查询成功则拆分字符串，返回二维数组
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
				turnPage.pageDisplayGrid = CustomerGrid;
				//保存SQL语句
				turnPage.strQuerySql = strSQL;
				//设置查询起始位置
				turnPage.pageIndex = 0;
				//在查询结果数组中取出符合页面显示大小设置的数组
				arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
				//调用MULTILINE对象显示查询结果
				displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  		}
  }
  catch(ex)
  {
      alert("查询客户基本信息失败！");
  }
} 

//对借款信息进行查询
function getLoanInfo()
{ 
	  var tLength=LoanGrid.mulLineCount;
	  
	  var tEdorNo=fm.EdorNo.value;
	  for(var i=0;i<tLength;i++)
	  {
	  	 var tPreEdorNo=LoanGrid.getRowColData(i,1);
	  	 var tCurrency = LoanGrid.getRowColData(i,9);
//	  	 var strSQL="select nvl(ReturnMoney+ReturnInterest,'') from LPReturnLoan where EdorNo='"+tEdorNo+"' and LoanNo='"+tPreEdorNo+"'";
	  	 
	var strSQL = "";
	var sqlid2="PEdorTypeRFInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tEdorNo);//指定传入的参数
	mySql2.addSubPara(tPreEdorNo);
	mySql2.addSubPara(tCurrency);
	strSQL=mySql2.getString();
	  	 
	  	 var brr = easyExecSql(strSQL); 
	  	 if(brr)
	  	 {
	  	 	var dMoney = brr[0][0] ;
	  	 	if(dMoney != null && dMoney != "null" && dMoney != ""){
	  	 	   LoanGrid.setRowColData(i,7,brr[0][0]);
	  	 	}
	  	 }else
	  	 		{
	  	 		//alert(LoanGrid.getRowColData(i,4));
	  	 		//alert(LoanGrid.getRowColData(i,5));
	  	 	  var tSumLoan=((parseFloat(LoanGrid.getRowColData(i,4))+ parseFloat(LoanGrid.getRowColData(i,5)))).toFixed(2); 
          LoanGrid.setRowColData(i,7,tSumLoan);	  	 		
	  	 		}	  	   
	  	}
	}
	
	function CountSumLoan()
	{
		
	  var tLength=LoanGrid.mulLineCount;
	  for(var i=0;i<tLength;i++)
	  {
	  	 tSSumLoanMoney+=(parseFloat(LoanGrid.getRowColData(i,4))+ parseFloat(LoanGrid.getRowColData(i,5)));			  
	  	}
	  tSSumLoanMoney=tSSumLoanMoney.toFixed(2)							
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
