var showInfo;
var mDebug="1";
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeBBInputSql";

//查询角色	 AppntIsInsuredFlag 投保人还是被保人,0,投保人，1，被保人，2 两者兼之
function initInpRole()
{
		var tContNo=document.all('ContNo').value;
	  //判断其是否是投保人
//	  var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and appntno ='"+document.all('CustomerNo').value+"'";
    
    var sql = "";
	var sqlid1="PEdorTypeBBInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(document.all('CustomerNo').value);
	sql=mySql1.getString();
    
    var mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="0";
    }
    //alert(mrr);
//    sql = " select socialinsuflag from lcinsured where contno = '" + tContNo + "' and insuredno ='"+document.all('CustomerNo').value+"'";
    
    //var sql = "";
	var sqlid2="PEdorTypeBBInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(document.all('CustomerNo').value);
	sql=mySql2.getString();
    
    mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="1";
        document.all("divSociaFlag").style.display = "";
        if(mrr[0][0]=='1')
        {
        	fm.PreSFlag.value='1';
        	}else
        		{
           	fm.PreSFlag.value='0';         	     			          	     			
           	     			}
    }
//    var tsql = " select socialinsuflag from lcinsured where contno = '" + tContNo + "' and insuredno = (select appntno from lccont where contno = '" + tContNo + "')";
    
    var tsql = "";
    var sqlid3="PEdorTypeBBInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tContNo);
	tsql=mySql3.getString();
    
     mrr = easyExecSql(tsql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="2";
        document.all("divSociaFlag").style.display = "";
        if(mrr[0][0]=='1')
        {
        	fm.PreSFlag.value='1';
        	}else
        		{
           	fm.PreSFlag.value='0';        	     			          	     			
           	     			}
    }


}
function initInpCustomerInfo()
{	  
 	  var tContNo=document.all('ContNo').value;
    var CustomerInfo;
    var tSQL;
    //如果是被保人
    if(fm.AppntIsInsuredFlag.value=='1')
    {
//    	tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lcinsured where insuredno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
      
    var sqlid4="PEdorTypeBBInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql4.addSubPara(fm.ContNo.value);
	tSQL=mySql4.getString();
      
      CustomerInfo= easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaCustomerInf(CustomerInfo);		
      }	
    }else
    	{
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lcappnt where appntno='"+document.all('CustomerNo').value+"'and contno='"+fm.ContNo.value+"'";
    		
    var sqlid5="PEdorTypeBBInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql5.addSubPara(fm.ContNo.value);
	tSQL=mySql5.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaCustomerInf(CustomerInfo);		
      }		
        //alert(CustomerInfo);		
    }
    
}
function initInpPCustomerInfo()
{

    var CustomerInfo;
    var tSQL;
	    //如果是被保人则查被保人
    if(fm.AppntIsInsuredFlag.value=='1')
    {
//    	tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lpinsured where insuredno='"+document.all('CustomerNo').value+"' and edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"'";
      
    var sqlid6="PEdorTypeBBInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql6.addSubPara(fm.EdorNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	tSQL=mySql6.getString();
      
      CustomerInfo = easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaPCustomerInf(CustomerInfo);			
    }else
    	{
//    		tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lcinsured where insuredno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid7="PEdorTypeBBInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql7.addSubPara(fm.ContNo.value);
	tSQL=mySql7.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}					
    		}
    }else
    	{    		
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lpappnt where appntno='"+document.all('CustomerNo').value+"' and edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid8="PEdorTypeBBInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql8.addSubPara(fm.EdorNo.value);
	mySql8.addSubPara(fm.ContNo.value);
	tSQL=mySql8.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}else
    			{
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lcappnt where appntno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid9="PEdorTypeBBInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql9.addSubPara(fm.ContNo.value);
	tSQL=mySql9.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}		
    	}
    	}
 	}
 	
function DisPlyaCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.NativePlace.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.AppntRgtAddress.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.AppntMarriage.value = CustomerInfo[0][3];	 
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.PreSFlag.value = CustomerInfo[0][4];	 
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.OldLanguage.value = CustomerInfo[0][5]; 	             	
      showOneCodeName('nativeplace','NativePlace','NativePlaceName');         
      showOneCodeName('marriage','AppntMarriage','AppntMarriageName');      
      showOneCodeName('language','OldLanguage','OldLanguageName');  		
	} 	
	
	function DisPlyaPCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name_New.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.NativePlace_New.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.AppntRgtAddress_New.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.AppntMarriage_New.value = CustomerInfo[0][3];
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.NewSFlag.value = CustomerInfo[0][4];	  
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.NewLanguage.value = CustomerInfo[0][5];	 	    
      //alert(fm.NewSFlag.value);
      if(fm.NewSFlag.value=='1')
      {
      	 fm.SociaFlag.checked=true;
      	}     		
      showOneCodeName('nativeplace','NativePlace_New','NativePlaceName_New');         
      showOneCodeName('marriage','AppntMarriage_New','AppntMarriageName_New');  	
      showOneCodeName('language','NewLanguage','NewLanguageName');  	
	} 	
 	
/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
}

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


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = '0000000003';
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

function edorTypeBBSave()
{
	    //界面校验
    if (!verifyInput2())
    {
        return false;
    }
   if(fm.SociaFlag.checked)
   {
   	fm.NewSFlag.value='1'
   	}else
   		{
   			fm.NewSFlag.value='0'
   			}
    
   if ((fm.NativePlace.value == fm.NativePlace_New.value) 
   && (fm.AppntRgtAddress.value == fm.AppntRgtAddress_New.value)
   && (fm.AppntMarriage.value == fm.AppntMarriage_New.value)
   && (fm.OldLanguage.value == fm.NewLanguage.value)
   && (fm.NewSFlag.value == fm.PreSFlag.value))
    {
        alert('被保人资料未发生变更！');
        return false;
    }
    
          var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
          //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
          fm.action = "PEdorTypeBBSubmit.jsp";
          fm.submit();
}          