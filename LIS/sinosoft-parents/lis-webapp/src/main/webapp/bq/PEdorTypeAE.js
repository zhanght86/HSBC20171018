//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var oldSex = "";
var mOperate = 0;
var oldBirthday = "";
var oldOccupationType = "";
var oldName="";
var ScreenWidth=640;
var ScreenHeight=480;
var theFirstValue="";
var theSecondValue="";
var tRelation = "";
var tRelation2 = "";
var CIflag = 0;//健康告知标志
var selno;
var mySql=new SqlClass();
function queryLRInfo()
{
	  mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql10");
    mySql.addSubPara(fm.ContNo.value); 
		//var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		var ret = easyExecSql(mySql.getString());
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}

//保存申请按钮
function edorTypeAESave()
{

if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("该保单有补发记录，请录入补发记录！");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("输入的补发次数不正确请核实！");
    			return;
    		}
    	}

    try
    {
        ImpartGrid.delBlankLine();
    }
    catch (ex) {}

    //界面校验
    if (!verifyInput2())
    {
        return false;
    }
    
    fm.AppntName.value=fm.LastName.value+fm.FirstName.value;
    
    //缴费方式是银行转账时，需要输入相关续期缴费银行账号信息
    var tFlag='';
	  mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql10");
    mySql.addSubPara(fm.ContNo.value);    
    
    //var strSQL = "select PayLocation from lccont  where  contno= '"+fm.ContNo.value+"'";
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult[0][0] =='4'){
    	if(document.all('BankName').value == "")
    	{
    		    alert('此保单的原缴费方式是银行转账，请录入银行信息！');
            return false;
    		}
    	 if (document.all('GetBankAccNo').value == "")
        {
            alert('此保单的原缴费方式是银行转账，请录入帐户信息!');
            return false;
        }
        if (document.all('GetAccName').value == "")
        {
            alert('此保单的原缴费方式是银行转账，请录入户名信息!');
            return false;
        }
        if (document.all('GetAccName').value != fm.AppntName.value)
        {
            alert('帐户名必须和投保人一致!');
            return false;
        }
    }else
    	{
    	if(document.all('BankName').value != "" || document.all('BankName').value != null )
    	{
    		    if(!confirm('此保单的原缴费方式是现金，不需要输入银行信息，是否继续！'))
    		    {
    		    	return false;
    		    }
     
    		}
    		
    		}
    
    if (document.all('BankName').value != "")
    {
        if (document.all('GetBankAccNo').value == "")
        {
            alert('请录入帐户信息!');
            return false;
        }
        if (document.all('GetAccName').value == "")
        {
            alert('请录入户名信息!');
            return false;
        }
        if (document.all('GetAccName').value != fm.AppntName.value)
        {
            alert('帐户名必须和投保人一致!');
            return false;
        }
    }
    //start-------------增加与被保险人关系----根据性别判断
    var Insuredsex ;
    var Insuredname ;
    var RelationToApp ;
    var str ;
    var strSQL ;
    var arrResult ;
    for (i=0;i<InsuredGrid.mulLineCount;i++){
		Insuredsex=InsuredGrid.getRowColData(i,3).substring(0,1);
		Insuredname=InsuredGrid.getRowColData(i,2);
		RelationToApp=InsuredGrid.getRowColData(i,9);
		if(RelationToApp==null || RelationToApp=='')
		{
			alert("请选择与被保人之间关系");
			return false;
		}
	  mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql12");
    mySql.addSubPara(RelationToApp); 
    		
		//strSQL="select codealias From ldcode where codetype='relation' and code='"+RelationToApp+"'";
  	arrResult = easyExecSql(mySql.getString(),1,0);
  	//alert(arrResult) ;
  	//alert(Insuredsex) ;
		if (arrResult[0][0]!=Insuredsex && arrResult[0][0]!="2"){
			str="被保人"+Insuredname+"与投保人关系选择错误!!!" ;
			alert(str);
			return false;
		}
	}
    //end---------------
    if (CIflag == 1 && (ImpartGrid.mulLineCount == 0))
    {
       alert("请录入健康告知!");
       return false;
    }
    var aRelation, aRelation1;
    selno = InsuredGrid.mulLineCount;
    if (selno > 0)
    {
        aRelation = InsuredGrid.getRowColData(0, 9);
    }
    else if (selno > 1)
    {
         var aRelation1 = InsuredGrid.getRowColData(1, 9);
    }
      if (aRelation == tRelation || (selno > 0 && aRelation1 == tRelation2))
      {
          //alert("请对投、被保险人关系进行修改！");
          //if (window.confirm("请对投、被保险人关系进行修改,修改并且保存本次申请点击'确定',修改投、被保险人关系点击'取消'!"))
          if (window.confirm("未修改与被保人关系。继续请点击“确定”；修改请点击“取消”。"))
          {
              document.all('fmtransact').value ="QUERY";
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
              fm.action = "PEdorTypeAESubmit.jsp";
              fm.submit();
          }
      }
    //}
      else
      {
          document.all('fmtransact').value ="QUERY";
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
          fm.action = "PEdorTypeAESubmit.jsp";
          fm.submit();
      }
}

//帐号信息校验
function checkBankAccNo()
{
    if (document.all('BankAccNo1').value != document.all('BankAccNo2').value)
    {
        alert("帐号信息不一致，请重新确认！");
        return false;
    }
}

//取消按钮
function resetForm()
{
        initForm();
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
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");var name='提示';   //网页名称，可为空; 
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
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

function queryAppntNo() {

     //alert("Start the query!");
     //alert(document.all("AppntNo").value);

     //if (document.all("AppntNo").value == ""||document.all("AppntNo").value == null ) {

      showAppnt1();
      //alert("Open the LDPersonNew!");
    //}
    //what's this ? unknow symbol : loadFlag . commented by XinYQ on 2006-03-13
    //else if (loadFlag != "1") {
    //   alert("只能在投保单录入时进行操作！");
    //}
    //else {
    //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    //  if (arrResult == null) {
    //     alert("未查到投保人信息");
    //     displayAppnt(new Array());
    //     emptyUndefined();
    //  }
    //  else {
    //     displayAppnt(arrResult[0]);
    //  }
    //
    //}
}
function showAppnt1()
{

        showInfo = window.open( "../sys/LDPersonQueryNew.html","LDPersonQueryNew",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        showInfo.focus();

}
function displayAppnt(arrResult)
{
try{document.all('AppntNo').value= arrResult[0][0]; }catch(ex){};

try{document.all('LastName').value= arrResult[0][48]; }catch(ex){};
try{document.all('FirstName').value= arrResult[0][49]; }catch(ex){};
try{document.all('LastNameEn').value= arrResult[0][50]; }catch(ex){};
try{document.all('FirstNameEn').value= arrResult[0][51]; }catch(ex){};
try{document.all('LastNamePY').value= arrResult[0][53]; }catch(ex){};
try{document.all('FirstNamePY').value= arrResult[0][54]; }catch(ex){};
try{document.all('AppntLanguage').value= arrResult[0][55]; }catch(ex){};

try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{ showOneCodeName('Sex','AppntSex','AppntSexName'); } catch (ex) {}    //XinYQ added on 2006-03-13
try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{ showOneCodeName('idtype','AppntIDType','AppntIDTypeName'); } catch (ex) {}    //XinYQ added on 2006-03-13
try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
//try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{document.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
//try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{document.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
//try{document.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
//try{document.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
//try{document.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
//try{document.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{document.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
//try{document.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
//try{document.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
//try{document.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
//try{document.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{document.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{document.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{document.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{document.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
//try{document.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{document.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
//try{document.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
//try{document.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
//try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
//try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
//try{document.all('VIPValue').value= arrResult[0][35];}catch(ex){};
try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
//try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
//try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
//try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
//try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
try{document.all('GrpName').value= arrResult[0][41];}catch(ex){};
 
fm.AppntOccupationCodeName.value=getOccupationCodeName(document.all('AppntOccupationCode').value);

//showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');
showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
showOneCodeName('nativeplace','AppntNativePlace','AppntNativePlaceName');
showOneCodeName('marriage','AppntMarriage','AppntMarriageName');
showOneCodeName('language','AppntLanguage','AppntLanguageName');

//try{document.all('AppntPostalAddress').value= "";}catch(ex){};
//try{document.all('AppntPostalAddress').value= "";}catch(ex){};
//try{document.all('AppntZipCode').value= "";}catch(ex){};
//try{document.all('AppntPhone').value= "";}catch(ex){};
//try{document.all('AppntFax').value= "";}catch(ex){};
//try{document.all('AppntMobile').value= "";}catch(ex){};
//try{document.all('AppntEMail').value= "";}catch(ex){};
//try{document.all('AppntGrpName').value= "";}catch(ex){};
//try{document.all('AppntHomeAddress').value= "";}catch(ex){};
//try{document.all('AppntHomeZipCode').value= "";}catch(ex){};
//try{document.all('AppntHomePhone').value= "";}catch(ex){};
//try{document.all('AppntHomeFax').value= "";}catch(ex){};
//try{document.all('AppntGrpPhone').value= "";}catch(ex){};
//try{document.all('CompanyAddress').value= "";}catch(ex){};
//try{document.all('AppntGrpZipCode').value= "";}catch(ex){};
//try{document.all('AppntGrpFax').value= "";}catch(ex){};
}


function afterQuery21(arrQueryResult){
    alert(arrQueryResult[0][0]);
}
function afterQuery( arrQueryResult ) {
    
//	alert("1");
    var arrResult = new Array();
    var addressno = new Array();
    var arrResult = new Array();
//    alert(arrQueryResult);
//    alert("2");
    if( arrQueryResult != null ) {
                // 投保人信息
//    	alert("3"); 
//    	"select * from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'"
    	mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeAE");
        mySql.setSqlId("PEdorTypeAESql13");
        mySql.addSubPara(arrQueryResult[0][0]); 
            arrResult = easyExecSql(mySql.getString(), 1, 0);
            if (arrResult == null) {
              alert("未查到投保人信息");
            } else {
               displayAppnt(arrResult);
            }
//            alert("4");
//            select Max(a.AddressNo) from LCAddress a where 1=1 and a.CustomerNo = '" + arrQueryResult[0][0] + "'
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql14");
            mySql.addSubPara(arrQueryResult[0][0]); 
            addressno = easyExecSql(mySql.getString(), 1, 0);
            if(addressno != null){
//            	select * from LCAddress a where 1=1 and a.AddressNo = '" + addressno[0][0] + "' and a.CustomerNo ='" + arrQueryResult[0][0] + "'
            	mySql=new SqlClass();
                mySql.setResourceName("bq.PEdorTypeAE");
                mySql.setSqlId("PEdorTypeAESql15");
                mySql.addSubPara(addressno[0][0]); 
                mySql.addSubPara(arrQueryResult[0][0]); 
               addrResult = easyExecSql(mySql.getString(), 1, 0 );

               if(addrResult == null){
                  alert("未查到投保人地址信息");
               } else {
                  try { document.all('AddressNo').value = addrResult[0][1]; } catch(ex) { };
                    try { document.all('PostalAddress').value = addrResult[0][2]; } catch(ex) { };
                    try { document.all('ZipCode').value = addrResult[0][3]; } catch(ex) { };
                    try { document.all('AppntPhone').value = addrResult[0][4]; } catch(ex) { };
                    //try { document.all('AppntFax').value = addrResult[0][5]; } catch(ex) { };
                    try { document.all('Mobile').value = addrResult[0][14]; } catch(ex) { };
                    try { document.all('AppntHomeAddress').value = addrResult[0][6]; } catch(ex) { };
                    try { document.all('AppntHomeZipCode').value = addrResult[0][7]; } catch(ex) { };
                    try { document.all('HomePhone').value = addrResult[0][8]; } catch(ex) { };
                    try { document.all('Fax').value = addrResult[0][9]; } catch(ex) { };
                    try { document.all('CompanyAddress').value = addrResult[0][10]; } catch(ex) { };
                    try { document.all('AppntGrpZipCode').value = addrResult[0][11]; } catch(ex) { };
                    try { document.all('GrpPhone').value = addrResult[0][12]; } catch(ex) { };
                    try { document.all('AppntGrpFax').value = addrResult[0][13]; } catch(ex) { };
                    try { document.all('AppntProvince').value = addrResult[0][28]; } catch(ex) { };
                    try { document.all('AppntCity').value = addrResult[0][29]; } catch(ex) { };
                    try { document.all('AppntDistrict').value = addrResult[0][30]; } catch(ex) { };
                    try { document.all('EMail').value = addrResult[0][16]; } catch(ex) { };


               }
            }

    }
    showLPAppnt();
    mOperate = 0;       // 恢复初态
}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
    try { document.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };
    try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) { };
    try { document.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };
    try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };
    try { document.all('ContType').value = arrResult[0][4]; } catch(ex) { };
    try { document.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };
    try { document.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };
    try { document.all('PolType ').value = arrResult[0][7]; } catch(ex) { };
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { };
    try { document.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };
    try { document.all('AgentType').value = arrResult[0][15]; } catch(ex) { };
    try { document.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };
    try { document.all('Handler').value = arrResult[0][17]; } catch(ex) { };
    try { document.all('Password').value = arrResult[0][18]; } catch(ex) { };
    try { document.all('AppntNo').value = arrResult[0][19]; } catch(ex) { };
    try { document.all('AppntName').value = arrResult[0][20]; } catch(ex) { };
    try { document.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };
    try { document.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };
    try { document.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };
    try { document.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };
    try { document.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };
    try { document.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };
    try { document.all('InsuredSex').value = arrResult[0][27]; } catch(ex) { };
    try { document.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };
    try { document.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };
    try { document.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };
    try { document.all('PayIntv').value = arrResult[0][31]; } catch(ex) { };
    try { document.all('PayMode').value = arrResult[0][32]; } catch(ex) { };
    try { document.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };
    try { document.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };
    try { document.all('OutPayFlag').value = arrResult[0][35]; } catch(ex) { };
    try { document.all('GetPolMode').value = arrResult[0][36]; } catch(ex) { };
    try { document.all('SignCom').value = arrResult[0][37]; } catch(ex) { };
    try { document.all('SignDate').value = arrResult[0][38]; } catch(ex) { };
    try { document.all('SignTime').value = arrResult[0][39]; } catch(ex) { };
    try { document.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };
    try { document.all('BankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('BankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('AccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PrintCount').value = arrResult[0][44]; } catch(ex) { };
    try { document.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };
    try { document.all('Lang').value = arrResult[0][46]; } catch(ex) { };
    try { document.all('Currency').value = arrResult[0][47]; } catch(ex) { };
    try { document.all('Remark').value = arrResult[0][48]; } catch(ex) { };
    try { document.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };
    try { document.all('Mult').value = arrResult[0][50]; } catch(ex) { };
    try { document.all('Prem').value = arrResult[0][51]; } catch(ex) { };
    try { document.all('Amnt').value = arrResult[0][52]; } catch(ex) { };
    try { document.all('SumPrem').value = arrResult[0][53]; } catch(ex) { };
    try { document.all('Dif').value = arrResult[0][54]; } catch(ex) { };
    try { document.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };
    try { document.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };
    try { document.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };
    try { document.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };
    try { document.all('InputDate').value = arrResult[0][59]; } catch(ex) { };
    try { document.all('InputTime').value = arrResult[0][60]; } catch(ex) { };
    try { document.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };
    try { document.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };
    try { document.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };
    try { document.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };
    try { document.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };
    try { document.all('UWOperator').value = arrResult[0][66]; } catch(ex) { };
    try { document.all('UWDate').value = arrResult[0][67]; } catch(ex) { };
    try { document.all('UWTime').value = arrResult[0][68]; } catch(ex) { };
    try { document.all('AppFlag').value = arrResult[0][69]; } catch(ex) { };
    try { document.all('PolApplyDate').value = arrResult[0][70]; } catch(ex) { };
    try { document.all('GetPolDate').value = arrResult[0][71]; } catch(ex) { };
    try { document.all('GetPolTime').value = arrResult[0][72]; } catch(ex) { };
    try { document.all('CustomGetPolDate').value = arrResult[0][73]; } catch(ex) { };
    try { document.all('State').value = arrResult[0][74]; } catch(ex) { };
    try { document.all('Operator').value = arrResult[0][75]; } catch(ex) { };
    try { document.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };
    try { document.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };
    try { document.all('ModifyDate').value = arrResult[0][78]; } catch(ex) { };
    try { document.all('ModifyTime').value = arrResult[0][79]; } catch(ex) { };

}

function FillPostalAddress()
{
     if(fm.CheckPostalAddress.value=="1")
     {
        document.all('AppntPostalAddress').value=document.all('CompanyAddress').value;
                document.all('AppntZipCode').value=document.all('AppntGrpZipCode').value;
                document.all('AppntPhone').value= document.all('AppntGrpPhone').value;
                document.all('AppntFax').value= document.all('AppntGrpFax').value;

     }
     else if(fm.CheckPostalAddress.value=="2")
     {
        document.all('AppntPostalAddress').value=document.all('AppntHomeAddress').value;
                document.all('AppntZipCode').value=document.all('AppntHomeZipCode').value;
                document.all('AppntPhone').value= document.all('AppntHomePhone').value;
                document.all('AppntFax').value= document.all('AppntHomeFax').value;
     }
     else if(fm.CheckPostalAddress.value=="3")
     {
        document.all('AppntPostalAddress').value="";
                document.all('AppntZipCode').value="";
                document.all('AppntPhone').value= "";
                document.all('AppntFax').value= "";
     }
}

function afterCodeSelect( cCodeName, Field )
{
//  alert(cCodeName);
    try {
        if( cCodeName == "EdorReason" )
        {
            if (document.all("EdorReason").value=="01") {
                divdeath.style.display = "";
            } else {
                divdeath.style.display = "none";
            }
        }else if(cCodeName == "relation")
        	{
        		//alert(fm.AppntSex.value) ; 统一提交时校验。
        	}
    }
    catch( ex ) {
    }
}


function showBankDiv()
{
    if(document.all('GetForm').value=='2')
    {
        BankInfo.style.display = "";
    }
    if(document.all('GetForm').value=='1')
    {
        BankInfo.style.display = "none";

    }
    if(document.all('GetForm').value=='3')
    {
        BankInfo.style.display = "none";

    }
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

function getBirthdaySexByIDNo(iIdNo)
{
    if(document.all('AppntIDType').value=="0")
    {
        document.all('AppntBirthday').value=getBirthdatByIdNo(iIdNo);
        document.all('AppntSex').value=getSexByIDNo(iIdNo);
        if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
        {
            alert("输入的身份证号位数错误");
            theFirstValue="";
            theSecondValue="";
            document.all('AppntIDNo').focus();
            return;
        }
        else
        {
            document.all('AppntBirthday').value=tBirthday;
            document.all('AppntSex').value=tSex;
        }
    }
}

function checkidtype()
{
    //alert("haha!");
    if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
    {
        alert("请先选择证件类型！");
        fm.AppntIDNo.value="";
  }
}

function compareTwoDate2()
{
    if(compareTwoDate(fm.PBirthday.value,fm.deathdate.value)==false)
    alert("投保人死亡日期录入有误，请确认后重新录入！");
    //fm.deathdate.value ="";
    return;
}
function deathdatecheck()
{
    if (fm.deathdate.value<fm.PBirthday.value)
    //alert("ljljlj");
    fm.deathdate.value="";

    }
    function compareTwoDate(birthday,birthday1) {
    var arrBirthday = birthday.split("-");
  if (arrBirthday[1].length == 1) arrBirthday[1] = "0" + arrBirthday[1];
  if (arrBirthday[2].length == 1) arrBirthday[2] = "0" + arrBirthday[2];


  var arrToday = birthday1.split("-");
  if (arrToday[1].length == 1) arrToday[1] = "0" + arrToday[1];
  if (arrToday[2].length == 1) arrToday[2] = "0" + arrToday[2];


  var age = arrToday[0] - arrBirthday[0];
  //当前月大于出生月
  //alert(arrToday[1] + " | " + arrBirthday[1] + " | " + (arrToday[1] > arrBirthday[1]));
    if(age>=0)
{return true;}
 if(age<0)
{
    fm.deathdate.value="";
    return false;}
}
function QueryEdorInfo()
{
    var tEdortype=top.opener.document.all('EdorType').value;
    var strSQL;
    if(tEdortype!=null || tEdortype !='')
    {
//       strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
       mySql=new SqlClass();
       mySql.setResourceName("bq.PEdorTypeAE");
       mySql.setSqlId("PEdorTypeAESql16");
       mySql.addSubPara(tEdortype); 
       strSQL=mySql.getString();
    }
    else
    {
        alert('未查询到保全批改项目信息！');
    }
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //职业类别
}
//function CheckRelationshiop()
//{
    //var value = document.all.InsuredGrid. getRowColData(0,7);
    //alert(value);
    //}
function getBirthdaySexByIDNo(iIdNo)
{
    if(document.all('AppntIDType').value=="0")
    {
        var tBirthday=getBirthdatByIdNo(iIdNo);
        var tSex=getSexByIDNo(iIdNo);
        if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
        {
            alert("输入的身份证号位数错误");
            theFirstValue="";
            theSecondValue="";
            document.all('AppntIDNo').focus();
            return;
        }
        else
        {
            document.all('AppntBirthday').value=tBirthday;
            document.all('AppntSex').value=tSex;
        }
    }
}


function showCustomerImpart(){
    var tContNo = document.all('ContNo').value;
//    var strsql = "select 1 from dual where '00' in (select substr(d.casepoltype,0,2) from lmdutygetclm d where d.getdutycode in (select getdutycode from lcget where contno = '"+tContNo+"'))";
    var strsql = "";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql17");
    mySql.addSubPara(tContNo); 
    strsql=mySql.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    //alert(aResult[0][0]);
    if(aResult != null){
         if(aResult[0][0] == "1"){
              divCustomer.style.display = '';
              CIflag = 1;
         }
    }
}

function checkappntbirthday(){

  if(fm.AppntBirthday.value.length==8){
  if(fm.AppntBirthday.value.indexOf('-')==-1){
    var Year =     fm.AppntBirthday.value.substring(0,4);
    var Month =    fm.AppntBirthday.value.substring(4,6);
    var Day =      fm.AppntBirthday.value.substring(6,8);
    fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
         alert("您输入的投保人出生日期有误!");
       fm.AppntBirthday.value = "";
       return;
      }
  }
}

else {var Year =     fm.AppntBirthday.value.substring(0,4);
        var Month =    fm.AppntBirthday.value.substring(5,7);
        var Day =      fm.AppntBirthday.value.substring(8,10);
        if(Year=="0000"||Month=="00"||Day=="00"){
         alert("您输入的投保人出生日期有误!");
       fm.AppntBirthday.value = "";
       return;
         }
  }
}
function getAge(){
    //alert("fm.AppntBirthday.value=="+fm.AppntBirthday.value);
  if(fm.AppntBirthday.value==""){
    return;
  }
  //alert("fm.AppntBirthday.value.indexOf('-')=="+fm.AppntBirthday.value.indexOf('-'));
  if(fm.AppntBirthday.value.indexOf('-')==-1){
    var Year =     fm.AppntBirthday.value.substring(0,4);
    var Month =    fm.AppntBirthday.value.substring(4,6);
    var Day =      fm.AppntBirthday.value.substring(6,8);
    fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
    if(calAge(fm.AppntBirthday.value)<0)
    {
      alert("出生日期只能为当前日期以前");
      return;
    }
    //fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    return;
  }
  if(calAge(fm.AppntBirthday.value)<0)
    {
      alert("出生日期只能为当前日期以前!");
      return;
    }
  //fm.AppntAge.value=calAge(fm.AppntBirthday.value);
  return;
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

function AppntInfShow()
{     
	  mySql=new SqlClass();
    
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
    	mySql.setResourceName("bq.PEdorTypeAE");
      mySql.setSqlId("PEdorTypeAESql4");
    	mySql.addSubPara(tContNo); 
      //var strSQL="select a.appntno,a.appntname,(select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),a.appntbirthday,(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)),a.idno from lcappnt a where a.contno='" + tContNo+"'";
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('PCustomerNo').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
    try {document.all('PName').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
    try {document.all('PSex').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
    try {document.all('PBirthday').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
    try {document.all('PIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
    try {document.all('PIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码

 }


function showLPAppnt()
{
    var EdorNo = fm.EdorNo.value;
    var ContNo = fm.ContNo.value;
    var EdorType = fm.EdorType.value;
    var strsql = "";
    //查询已录入变更原因
    /*
    strsql = "select trim(edorreasoncode),"
        + " (select codename from ldcode where codetype = 'edorreason' and trim(code) = trim(edorreasoncode)) "
        + " from lpedoritem where edorno = '"+EdorNo+"' "
        + " and edortype = '"+EdorType+"' "
        + " and contno = '"+ContNo+"' ";
     */   
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql5");
    mySql.addSubPara(EdorNo);     
    mySql.addSubPara(EdorType); 
    mySql.addSubPara(ContNo); 
        
    var brr = easyExecSql(mySql.getString());
    if(brr)
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.EdorReason.value = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.EdorReasonName.value = brr[0][1];
        //如果原投保人死亡还需显示死亡时间
        if(brr[0][0] == "01")
        {
        	  mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql6");
            mySql.addSubPara(EdorNo);  
            mySql.addSubPara(EdorType); 
            mySql.addSubPara(ContNo);
            /*
            strsql = "select deathdate from lpperson "
                   + " where edorno = '"+EdorNo+"' "
                   + " and edortype = '"+EdorType+"' "
                   + " and customerno = (select appntno from lcappnt where contno = '"+ContNo+"')";
                   */
            brr = easyExecSql(mySql.getString());
            if(brr)
            {
                fm.deathdate.value = brr[0][0];
                divdeath.style.display = '';
            }
        }
    }
    //查询已录入投保人变更后的信息
    /*
    strsql = "select appntno,appntname,trim(idtype),"
            + " (select codename from ldcode where codetype = 'idtype' and trim(code) = trim(idtype)),"
            + " idno,trim(appntsex),(select codename from ldcode where codetype = 'sex' and trim(code)=trim(appntsex)),"
            + " appntbirthday,trim(marriage),(select codename from ldcode where codetype = 'marriage' and trim(code) = trim(marriage)),"
            + " trim(nativeplace),(select codename from ldcode where codetype = 'nativeplace' and trim(code)=trim(nativeplace)),"
            + " RgtAddress,trim(nationality),(select codename from ldcode where codetype = 'nationality' and trim(code)=trim(nationality)),"
            + " trim(OccupationCode),(select occupationname from ldoccupation where trim(occupationcode) = trim(a.occupationcode)),"
            + " trim(occupationtype),(select codename from ldcode where codetype = 'occupationtype' and trim(code)=trim(occupationtype)),"
            + " trim(PluralityType),trim(WorkType),(select codename from ldcode where codetype = 'yesno' and trim(code)=trim(smokeflag)),"
            + " AddressNo,BankCode,(select bankname from ldbank where trim(bankcode)=trim(a.bankcode)),"
            + " BankAccNo,AccName,RelationToInsured "
            + " from LPAppnt a where a.edorno = '"+EdorNo+"' "
            + " and a.edortype = '"+EdorType+"' "
            + " and a.contno = '"+ContNo+"' ";
            */
        	  mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql7");
            mySql.addSubPara(EdorNo);  
            mySql.addSubPara(EdorType); 
            mySql.addSubPara(ContNo);
    brr = easyExecSql(mySql.getString());
    if(brr)
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntIDType.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppntIDTypeName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.AppntIDNo.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.AppntSex.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.AppntSexName.value = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.AppntBirthday.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntMarriage.value     = brr[0][8];
        //brr[0][9]==null||brr[0][9]=='null'?'0':fm.AppntMarriageName.value   = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.AppntNativePlace.value = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.AppntNativePlaceName.value     = brr[0][11];
        brr[0][12]==null||brr[0][12]=='null'?'0':fm.AppntRgtAddress.value   = brr[0][12];
        //brr[0][13]==null||brr[0][13]=='null'?'0':fm.AppntNationality.value   = brr[0][13];
        //brr[0][14]==null||brr[0][14]=='null'?'0':fm.AppntNationalityName.value = brr[0][14];
        brr[0][15]==null||brr[0][15]=='null'?'0':fm.AppntOccupationCode.value     = brr[0][15];
        brr[0][16]==null||brr[0][16]=='null'?'0':fm.AppntOccupationCodeName.value   = brr[0][16];
        brr[0][17]==null||brr[0][17]=='null'?'0':fm.AppntOccupationType.value = brr[0][17];
        brr[0][18]==null||brr[0][18]=='null'?'0':fm.AppntOccupationTypeName.value     = brr[0][18];
        brr[0][19]==null||brr[0][19]=='null'?'0':fm.PluralityType.value   = brr[0][19];
        brr[0][20]==null||brr[0][20]=='null'?'0':fm.AppntWorkType.value   = brr[0][20];
        //brr[0][19]==null||brr[0][19]=='null'?'0':fm.AppntSmokeFlag.value   = brr[0][19];
       // brr[0][20]==null||brr[0][20]=='null'?'0':fm.AppntSmokeFlagName.value   = brr[0][20];
        brr[0][21]==null||brr[0][21]=='null'?'0':fm.AddressNo.value = brr[0][22];
        brr[0][22]==null||brr[0][22]=='null'?'0':fm.GetBankCode.value     = brr[0][23];
        brr[0][23]==null||brr[0][23]=='null'?'0':fm.BankName.value   = brr[0][24];
        brr[0][25]==null||brr[0][25]=='null'?'0':fm.GetBankAccNo.value     = brr[0][25];
        brr[0][26]==null||brr[0][26]=='null'?'0':fm.GetAccName.value   = brr[0][26];
        brr[0][27]==null||brr[0][27]=='null'?'0':fm.RelationToInsured.value   = brr[0][27];
        brr[0][28]==null||brr[0][28]=='null'?'0':fm.LastName.value   = brr[0][28];
        brr[0][29]==null||brr[0][29]=='null'?'0':fm.FirstName.value   = brr[0][29];
        brr[0][30]==null||brr[0][30]=='null'?'0':fm.LastNameEn.value   = brr[0][30];
        brr[0][31]==null||brr[0][31]=='null'?'0':fm.FirstNameEn.value   = brr[0][31];
        brr[0][32]==null||brr[0][32]=='null'?'0':fm.LastNamePY.value   = brr[0][32];
        brr[0][33]==null||brr[0][33]=='null'?'0':fm.FirstNamePY.value   = brr[0][33];
        brr[0][34]==null||brr[0][34]=='null'?'0':fm.AppntLanguage.value   = brr[0][34];

        //查询已录入投保人地址信息
        /*
        strsql = "select trim(Province),(select placename from ldaddress where placetype = '01' and trim(placecode)=trim(province)),"
              + " trim(City),(select placename from ldaddress where placetype = '02' and trim(placecode)=trim(City) and trim(upplacename)=trim(province)),"
              + " trim(County),(select placename from ldaddress where placetype = '03' and trim(placecode)=trim(County) and trim(upplacename)=trim(City)),"
              + " PostalAddress,ZipCode,Mobile,CompanyPhone,Fax,HomePhone,GrpName,EMail,HomeAddress,HomeZipCode "
              + " from lpaddress where  edorno = '"+EdorNo+"' "
              + " and edortype = '"+EdorType+"' "
              + " and customerno = '"+fm.AppntNo.value+"' and AddressNo='"+fm.AddressNo.value +"'";
        	*/  
        	
        	
        	  mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql8");
            mySql.addSubPara(EdorNo);  
            mySql.addSubPara(EdorType); 
            mySql.addSubPara(fm.AppntNo.value);              
            mySql.addSubPara(fm.AddressNo.value); 
            
        brr = easyExecSql(mySql.getString());
        if(brr)
        {
            //brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntProvince.value     = brr[0][0];
            //brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntProvinceName.value = brr[0][1];
            //brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntCity.value    = brr[0][2];
            //brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppntCityName.value = brr[0][3];
            //brr[0][4]==null||brr[0][4]=='null'?'0':fm.AppntDistrict.value     = brr[0][4];
            //brr[0][5]==null||brr[0][5]=='null'?'0':fm.AppntDistrictName.value   = brr[0][5];
            brr[0][6]==null||brr[0][6]=='null'?'0':fm.PostalAddress.value = brr[0][6];
            brr[0][7]==null||brr[0][7]=='null'?'0':fm.ZipCode.value = brr[0][7];
            brr[0][8]==null||brr[0][8]=='null'?'0':fm.Mobile.value     = brr[0][8];
            brr[0][9]==null||brr[0][9]=='null'?'0':fm.GrpPhone.value   = brr[0][9];
            //brr[0][10]==null||brr[0][10]=='null'?'0':fm.Fax.value = brr[0][10];
            //brr[0][11]==null||brr[0][11]=='null'?'0':fm.HomePhone.value     = brr[0][11];
            brr[0][12]==null||brr[0][12]=='null'?'0':fm.GrpName.value   = brr[0][12];
            brr[0][13]==null||brr[0][13]=='null'?'0':fm.EMail.value   = brr[0][13];
            brr[0][14]==null||brr[0][14]=='null'?'0':fm.HomeAddress.value   = brr[0][14];
            brr[0][15]==null||brr[0][15]=='null'?'0':fm.HomeZipCode.value   = brr[0][15];
        }

    }
//fm.AppntOccupationCodeName.value=getOccupationCodeName(AppntOccupationCode);    
//alert("aa");
//showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
showOneCodeName('nativeplace','AppntNativePlace','AppntNativePlaceName');
showOneCodeName('marriage','AppntMarriage','AppntMarriageName');
showOneCodeName('Relation','RelationToInsured','RelationToInsuredName');
showOneCodeName('language','AppntLanguage','AppntLanguageName');

}

/***************************************************************
 * 由职业代码查询出职业类别代码
 ***************************************************************
 */
function getdetailwork()
{
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
    var strSql = "";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeAE");
    mySql.setSqlId("PEdorTypeAESql18");
    mySql.addSubPara(fm.AppntOccupationCode.value); 
    strSql=mySql.getString();
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    if (arrResult != null)
    {
       fm.AppntOccupationType.value = arrResult[0][0];
       //alert(fm.AppntOccupationType.value );
       showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
    }
    else
    {
       fm.AppntOccupationType.value = '';
    }
}


function showimpart(){
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;
     mySql=new SqlClass();
     mySql.setResourceName("bq.PEdorTypeAE");
     mySql.setSqlId("PEdorTypeAESql9");
     mySql.addSubPara(tEdorNo);  
     
     /*
     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'AE' and edorno = '"
         + tEdorNo + "'";
         */
     turnPage.queryModal(mySql.getString(), ImpartGrid);     
         
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
    }
    catch (ex) {}
}

function getOccupationCodeName(OccupationCode)
{
//	var tSQL="select  OccupationName from LDOccupation where OccupationCode='"+OccupationCode+"'";
		var tSQL = "";
	    mySql=new SqlClass();
	    mySql.setResourceName("bq.PEdorTypeAE");
	    mySql.setSqlId("PEdorTypeAESql19");
	    mySql.addSubPara(OccupationCode); 
	    tSQL=mySql.getString();
//	alert(OccupationCode);
//	alert(tSQL);
	var ssResult = easyExecSql(tSQL,1,0);
    if (ssResult != null)
    {
        return ssResult[0][0];
    }		
	}