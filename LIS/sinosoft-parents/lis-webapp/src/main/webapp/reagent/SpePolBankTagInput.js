 //               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var saveClick=false;
var arrDataSet;
var turnPage = new turnPageClass();
var conftype= "";


window.onfocus=myonfocus;

//是否为数字
function isdigit(c){
  return(c>='0'&&c<='9');
}
//字符转换数字
function atoi(s){
  var t=0;
  for(var i=0;i<s.length;i++){
    var c=s.charAt(i);
	if(!isdigit(c))return t;
	else t=t*10+(c-'0');
  } 
  return t;  
}

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}


//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //提交
 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ,confirmflag )
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

    //执行下一步操作 
    if (fm.action == './SpePolBankTagSave.jsp') 
    {     
    	//alert("confirmflag"+confirmflag);      
       PolConfirm();
       saveClick=true;
    }  
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

//保单渠道调配
function PolConfirm()
{
	var tStartDate = document.all('StartDate').value;
	var tEndDate = document.all('EndDate').value;
	
  if( ((document.all('MainPolNo').value=="")||(document.all('MainPolNo').value==null)) 
    &&((document.all('ContNo').value=="")||(document.all('ContNo').value==null)) 
    &&((document.all('ManageCom').value=="")||(document.all('ManageCom').value==null)) )
   {
    alert("主险险种号,保单号及管理机构不能同时为空!!");	
    document.all('MainPolNo').focus();
    return ;
   }
  var sub_str=" ";
  if(document.all('ContNo').value!=""&& document.all('ContNo').value!=null) 
  {
  	sub_str = sub_str + " and a.ContNo ='"+document.all('ContNo').value+"'";
  }

  if(document.all('ManageCom').value!=""&& document.all('ManageCom').value!=null) 
  {
  	sub_str = sub_str + " and a.managecom  like '"+document.all('ManageCom').value+"%%'";
  }

  if(document.all('AgentCode').value!=""&& document.all('AgentCode').value!=null) 
  {
  	sub_str = sub_str + " and a.agentcode ='"+document.all('AgentCode').value+"'";
  }

  if(document.all('StartDate').value!=""&& document.all('StartDate').value!=null) 
  {
  	sub_str = sub_str + " and b.startpaydate>='"+document.all('StartDate').value+"'";
  }

  if(document.all('EndDate').value!=""&& document.all('EndDate').value!=null) 
  {
  	sub_str = sub_str + " and b.startpaydate<='"+document.all('EndDate').value+"'";
  }
  var strSQL = "";  
  var tReturn = parseManageComLimitlike();
  //保单号不为空时以保单号为准，不管ContNo,managecom等其他条件
  if((document.all('MainPolNo').value!="")&&(document.all('MainPolNo').value!=null)) 
  {
  	//strSQL=" select (select shortname from ldcom where comcode=a.managecom),a.mainpolno,a.ContNo,a.cvalidate, "
    //  +"  a.appntname,a.prem,(select codename from ldcode where code=c.bankcode and codetype='bank'),c.bankaccno from lcpol a ,ljspay b ,lccont c"
    //  +" where a.appflag=1 and exists (select 1 from lccontstate where contno=a.contno and polno=a.polno and statetype='Available' and state='0' and enddate is null ) "
    //  +" and (b.paytypeflag is null or (b.paytypeflag = '1' and exists(select 1 from lyrenewbankinfo where getnoticeno = b.getnoticeno and paytodate = b.startpaydate and state = '0') ))"
    //  +" and a.polno=a.mainpolno and a.contno=b.otherno and a.contno=c.contno and b.bankonthewayflag='0' and b.sumduepaymoney>0 and (b.payform='0' or b.payform is null)"
    //  +" and c.bankcode is not null and not exists( select 1 from ljtempfee where tempfeeno=b.getnoticeno) and a.mainpolno='"+document.all('MainPolNo').value+"' "
    //  ;
    strSQL = wrapSql('SpePolBankTagInputSql1',[document.all('MainPolNo').value]);

  }
  else
  {
  	//strSQL=" select (select shortname from ldcom where comcode=a.managecom),a.mainpolno,a.ContNo,a.cvalidate, "
    //  +"  a.appntname,a.prem,(select codename from ldcode where code=c.bankcode and codetype='bank'),c.bankaccno from lcpol a ,ljspay b ,lccont c"
    //  +" where a.appflag=1  and exists (select 1 from lccontstate where contno=a.contno and polno=a.polno and statetype='Available' and state='0' and enddate is null ) "
    //  +" and (b.paytypeflag is null or (b.paytypeflag = '1' and exists(select 1 from lyrenewbankinfo where getnoticeno = b.getnoticeno and paytodate = b.startpaydate and state = '0') ))"
    //  +" and a.polno=a.mainpolno and a.contno=b.otherno and a.contno=c.contno and b.bankonthewayflag='0' and b.sumduepaymoney>0 and (b.payform='0' or b.payform is null) "
    //  +" and c.bankcode is not null and not exists( select 1 from ljtempfee where tempfeeno=b.getnoticeno) "
    //  + sub_str +" order by a.polno";
    
    strSQL = wrapSql('SpePolBankTagInputSql2',[sub_str]);   
  	
  }   
  //prompt("",strSQL);
  var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  if (! strQueryResult) 
  {
     alert("查询保单失败，原因可能如下:1,保单已失效或者未催收;2,输入保单号非主险保单号;3,转帐方式非银行转帐;4,保单已经有当期暂交费记录;5,存在银行在途记录;6,续保未确认。");
     document.all("MainPolNo").value="";
     document.all("ContNo").value="";
     document.all("ManageCom").value="";
     document.all("AgentCode").value="";
     document.all("StartDate").value="";
     document.all("EndDate").value="";
     
     clearMulLine();
     return ;
  } 

  if(!query(strSQL))
  {
  	return false;
  }		 
}   


function submitSave()
{   
  
  var tempObj = document.all('AscriptionGridNo'); //假设在表单fm中
  if (tempObj == null)
  {
     alert('没有选中的保单！');
     return ;
  }
  var lineCount = AscriptionGrid.mulLineCount;
  var str='';
   
  fm.action="./SpePolBankTagSave.jsp";
  submitForm();
  return ;
}

function clearMulLine()
{  
   AscriptionGrid.clearData("AscriptionGrid");
   saveClick=false;
}

function query( strSQL )
{
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  //alert(strSQL);
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
    	alert("找不到符合条件的应收记录！")
    AscriptionGrid.clearData("AscriptionGrid");
    return ;
  }
  
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = chooseArray(arrDataSet,[0,1,2,3,4,5,6,7]);

  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = AscriptionGrid;              
  //保存SQL语句
  turnPage.strQuerySql     = strSQL;   
  //设置查询起始位置
  turnPage.pageIndex       = 0;    
  //在查询结果数组中取出符合页面显示大小设置的数组
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果  
  displayMultiline(tArr, turnPage.pageDisplayGrid);
	
}