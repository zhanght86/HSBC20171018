//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var mFlag;
var sqlresourcename = "bq.PEdorTypeICInputSql";

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量



function edorTypeReturn()
{
	initForm();
}

function edorTypeICSave()
{

	document.all('fmtransact').value = "INSERT||MAIN";


    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
	  fm.action = "PEdorTypeICSubmit.jsp";
	  fm.submit();
 
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
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
	if (FlagStr == "Success" )
	{
		var tTransact=document.all('fmtransact').value;

		if (tTransact=="INSERT||MAIN")
		{
            queryRelationPol();
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
			edorTypeReturn();

	  	}
	  	//XinYQ added on 2005-12-22
	  	try
	  	{
	  	    queryBackFee();              //提交之后再次查询补退费明细
	  	    top.opener.getEdorItemGrid();
	  	}
	  	catch (ex) {}
	  	  var tFlag=""//保单是否满两年标记，而且不符和投保规则
  //      var tSQL="select standbyflag3 from lpedoritem where EdorAcceptNo ='" + document.all('EdorAcceptNo').value + "'";
    		
    var tSQL = "";
	var sqlid1="PEdorTypeICInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
	tSQL=mySql1.getString();
    		
    		tFlag = easyExecSql(tSQL);
    		if(tFlag=="1")
    		{
          alert("保单变更后的投保年龄不在承保范围内，但保单投保已满两年，请注意处理");	
          return ;
    		}	
	}
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

//---------------------------
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}


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



function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
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


function initInpPCustomerInfo()
{	  
    var CustomerInfo;
//    var tSQL="select name,sex,birthday,idtype,idNo from LPPerson where CustomerNo ='" + document.all('CustomerNo').value + "' and EdorNo='"+getLastEdorNo()+"'";
      
    var tSQL = "";
	var sqlid2="PEdorTypeICInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql2.addSubPara(getLastEdorNo());
	tSQL=mySql2.getString();
      
      CustomerInfo= easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaPCustomerInf(CustomerInfo);		
      }	
    
}
function initInpCustomerInfo()
{
    
    var CustomerInfo=null;
//    var tSQL="select name,sex,birthday,idtype,idNo from LDPerson where CustomerNo ='" + document.all('CustomerNo').value + "'";
    		
    var tSQL = "";
	var sqlid3="PEdorTypeICInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	tSQL=mySql3.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaCustomerInf(CustomerInfo);				
    		}	
}	


function DisPlyaPCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.Sex.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.Birthday.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.IDType.value = CustomerInfo[0][3];	
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.IDNo.value = CustomerInfo[0][4];	                                        
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.LastName.value = CustomerInfo[0][5];
      CustomerInfo[0][6]==null||CustomerInfo[0][6]=='null'?'0':fm.FirstName.value = CustomerInfo[0][6];	                                        
     	
      showOneCodeName('sex','Sex','SexName');         
      showOneCodeName('idtype','IDType','IDTypeName');  		
	} 	
	
	function DisPlyaCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.NameBak.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.SexBak.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.BirthdayBak.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.IDTypeBak.value = CustomerInfo[0][3];	                    
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.IDNoBak.value = CustomerInfo[0][4];	                                         
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.LastNameBak.value = CustomerInfo[0][5];
      CustomerInfo[0][6]==null||CustomerInfo[0][6]=='null'?'0':fm.FirstNameBak.value = CustomerInfo[0][6];	
      showOneCodeName('sex','SexBak','SexNameBak');         
      showOneCodeName('idtype','IDTypeBak','IDTypeNameBak');  
}

function getLastEdorNo()
{
	    var tEdorNo;
//		  var strSQL =  " select edorno from lpconttempinfo where edortype='CM' and state='0' and contno='"+document.all('ContNo').value+"'";
			
	var strSQL = "";
	var sqlid4="PEdorTypeICInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(document.all('ContNo').value);//指定传入的参数
	strSQL=mySql4.getString();
			
			var brr = easyExecSql(strSQL);
			if ( brr )
			{
				//alert("已经申请保存过");
				brr[0][0]==null||brr[0][0]=='null'?'0':tEdorNo= brr[0][0];
				fm.CMEdorNo.value=tEdorNo;
        return tEdorNo;
			}
			else
			{
				alert("保全批单号失败");
				return "";
			}
	}