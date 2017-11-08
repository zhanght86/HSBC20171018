
 //               该文件中包含客户端需要处理的函数和事件
var mOperate = "";
var showInfo1;
var mDebug="0";
var turnPage = new turnPageClass();
var arrResult;

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag=false;
//

var isBroker = false;

var arrCardRisk;
//window.onfocus=focuswrap;
var mSwitch = parent.VD.gVSwitch;
//工作流flag
var mWFlag = 0;
//使得从该窗口弹出的窗口能够聚焦
function focuswrap()
{
	myonfocus(showInfo1);
}


//查询集体保单险种信息
function QueryRisk(){
    //if((fm.GrpContNo.value == "") || (fm.GrpContNo.value == "null") || (fm.GrpContNo.value == null)) 
    //    return;
    /*
    var  strSql=" select a.riskcode, b.riskname,a.payintv,a.exppeoples,"
        +"((select count(c.riskcode) from lcpol c where c.grpcontno = '"+GrpContNo+"' and c.riskcode = a.riskcode " 
        +" and c.poltypeflag='0') + "
        +"(select nvl(sum(i.insuredpeoples),0) from lcpol c,lcinsured i where c.grpcontno = '"+GrpContNo+"' and c.riskcode = a.riskcode " 
        +" and i.contno=c.contno and i.insuredno=c.insuredno and c.poltypeflag='1')) as peoples,"
        +"(select nvl(sum(c.prem), 0) from lcpol c where c.grpcontno = '"+GrpContNo+"' and c.riskcode = a.riskcode )"
        +"from lcgrppol a, lmriskapp b Where a.riskcode = b.riskcode and a.grpcontno = '"+GrpContNo+"'";
        */
    var strSql =" select riskcode ,(select riskname from lmriskapp where riskcode =a.riskcode),0,0,a.polno from lcpol a "
    		   +" where contno ='"+ContNo+"'";

	turnPage.queryModal(strSql, PolGrid);
}

function addInsuredList()
{
	  if(isBroker)
    { //add by TianWK 确保是从LXContInfo表查询的数据
    	if(fm.CardNo.value==null||fm.CardNo.value=="")
    	{
    		alert("请以查询方式确定所补名单客户信息");
    		return false;
    	}
    }
    
    if (verifyInput2() == false)
        return;  
     
    if(document.all('IDType').value=="0")
    {
        var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
        if (strChkIdNo != "")
        {
            alert(strChkIdNo);
	        return false;
        }
    }    
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid) == false)
        return false;
    
    ImpartGrid.delBlankLine();
    
    document.all('ContType').value=ContType;
    document.all('BQFlag').value = BQFlag;
    document.all('EdorType').value = EdorType;
    document.all('EdorTypeCal').value = EdorTypeCal;
    document.all('EdorValiDate').value = EdorValiDate;
    
    document.all('fmAction').value="INSERT||CONTINSURED";
    
    fm.PrtNo.value=prtNo;
    fm.GrpContNo.value=GrpContNo;
    fm.OldContNo.value=vContNo;
 
    var showStr="正在添加被保人，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action="./GrpFillInsuredSave.jsp";
    fm.submit();	
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
  try { showInfo.close(); } catch(e) {}
  if (FlagStr=="Succ" && mWFlag == 0)
  {
  	if(fm.CardNo.value!=""&&fm.ContNo.value!="")
  	{
  		fm.action="../broker/CollateContSave.jsp";
  		/*修改生效日期等信息*/
  		fm.submit();
  		fm.CardNo.value="";
  	}
   else
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
  	 if(confirm("是否继续录入其他客户？"))
  	  {
  	  	if(!checkISFILL())
  	  	{//判断激活信息
  	  		return false;
  	  	}
  		  emptyInsured();
  		  if (fm.ContType.value==2)
  		  {  
  			 fm.ContNo.value="";
  		   fm.ProposalContNo.value="";
  		  }
  	  }
  	else
  		{
  			parent.close();	
  		}
    }
  }
  if( FlagStr == "Fail" )
	{             
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
}

/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */      
function throughwork()
{
   var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
   var arrResult = easyExecSql(strSql);
   if (arrResult != null)
   {
       fm.OccupationType.value = arrResult[0][0];
   }
   else
   {
       fm.OccupationType.value = '1';
   }
}

/*********************************************************************
 *  返回上一页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnparent()
{
	top.fraInterface.window.location="../app/GrpContPolMain.jsp?ContNo=" + vContNo + "&PrtNo=" + prtNo + "&LoadFlag=18&GrpContNo=" + GrpContNo;
}

/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
    try
    {   //如果是家庭单。 
        if(cCodeName == "FamilyType")
        {
            choosetype();
        }
        if(cCodeName == "OccupationCode")
        {
            //alert(fm.OccupationCode.value);
            var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
            var arrResult = easyExecSql(strSql);
            if (arrResult != null)
            {
                fm.OccupationType.value = arrResult[0][0];
            }
            else
            {
                fm.OccupationType.value = '1';
            }
        }
    
    
        if(cCodeName=="GetAddressNo"){
            //alert("ok");
            var strSQL="select postaladdress,zipcode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
            //alert(strSQL);
            arrResult=easyExecSql(strSQL);
            try{document.all('PostalAddress').value= arrResult[0][0];}catch(ex){};
            try{document.all('ZipCode').value= arrResult[0][1];}catch(ex){};
            return;
        }

        //如果是无名单
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value=="1")
            {   
            	  DivLCBasicInfo.style.display = "none"; 
               	  document.all('IDType').value="9";
            	  document.all('InsuredPeoples').value="10";
            	  document.all('InsuredAppAge').value="30";
            	  document.all('InsuredPeoples').readOnly=false;
                  document.all('InsuredAppAge').readOnly=false;
                  document.all('OccupationType').readOnly=false; 
              	  document.all('Name').value="无名单";
              	  document.all('OccupationCode').verify = "";
              	  document.all('Sex').value="0";
              	  document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='被保险人职业类别|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                  document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"被保险人出生日期|Date\" verifyorder='2' >";
            }
            else if(Field.value=="2")
            {
                DivLCBasicInfo.style.display = "none";
                document.all('IDType').value="9";
                document.all('InsuredPeoples').value="0";
                document.all('InsuredAppAge').value="30";
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=false; 
                document.all('OccupationCode').verify = "";
                document.all('OccupationType').readOnly=false;
                document.all('Name').value="公共帐户";
                document.all('Sex').value="0";
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='被保险人职业类别|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"被保险人出生日期|Date\" verifyorder='2' >";
            }
            else
            {  
                DivLCBasicInfo.style.display = "";   
                document.all('InsuredPeoples').value="";
                document.all('InsuredAppAge').value=""; 
                document.all('Name').value="";
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredPeoples').value="1";
                document.all('InsuredAppAge').readOnly=true;  
                document.all('OccupationCode').verify = "被保险人职业代码|NOTNUlL&CODE:OccupationCode";               
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  elementtype=nacessary verify='被保险人职业类别|NOTNULL&code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                document.all('OccupationType').insertAdjacentHTML("afterEnd","<font color=red>&nbsp;*</font>");
                document.all('OccupationType').readOnly=true;
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short elementtype=nacessary onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"被保险人出生日期|NOTNULL&DATE\" verifyorder='2' >";
                document.all('Birthday').insertAdjacentHTML("afterEnd","<font color=red>&nbsp;*</font>");
            }
            return;
        }
        if( cCodeName == "ImpartCode")
        {
        
        }
        if( cCodeName == "SequenceNo")
        {
            if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
            {
                emptyInsured();
                param="121";
                fm.pagename.value="121";
                fm.InsuredSequencename.value="第一被保险人资料";
                fm.RelationToMainInsured.value="00";
            }
            if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
            {
                if(InsuredGrid.mulLineCount==0)
                {
                    alert("请先添加第一被保人");
                    fm.SequenceNo.value="1";
                    return false;
                }
                emptyInsured();
                noneedhome();
                param="122";
                fm.pagename.value="122";
                fm.InsuredSequencename.value="第二被保险人资料";
            }
            if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
            {
                if(InsuredGrid.mulLineCount==0)
                {
                    alert("请先添加第一被保人");
                    Field.value="1";
                    return false;
                }
                if(InsuredGrid.mulLineCount==1)
                {
                    alert("请先添加第二被保人");
                    Field.value="1";
                    return false;
                }
                emptyInsured();
                noneedhome();
                param="123";
                fm.pagename.value="123";
                fm.InsuredSequencename.value="第三被保险人资料";
            }
            if (scantype== "scan")
            {
                setFocus();
            }
        }
        if( cCodeName == "CheckPostalAddress")
        {
            if(fm.CheckPostalAddress.value=="1")
            {
                document.all('PostalAddress').value=document.all('GrpAddress').value;
                document.all('ZipCode').value=document.all('GrpZipCode').value;
                document.all('Phone').value= document.all('GrpPhone').value;
            }
            else if(fm.CheckPostalAddress.value=="2")
            {
                document.all('PostalAddress').value=document.all('HomeAddress').value;
                document.all('ZipCode').value=document.all('HomeZipCode').value;
                document.all('Phone').value= document.all('HomePhone').value;
            }
            else if(fm.CheckPostalAddress.value=="3")
            {
                document.all('PostalAddress').value="";
                document.all('ZipCode').value="";
                document.all('Phone').value= "";
            }
        }
    }
    catch(ex) {}

}

/*********************************************************************
 *  显示家庭单下被保险人的信息
 *  返回值：  无
 *********************************************************************
 */
function getInsuredInfo()
{
	
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = InsuredGrid;
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

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;
    var ContNo = fm.ContNo.value;
    //被保人详细信息
    var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        displayAppnt();
    }
    strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
    fm.InsuredAddressNo.value=tAddressNo;
    getdetailaddress();
    getInsuredPolInfo();
    getImpartInfo();
    //getImpartDetailInfo();
    InsuredChkNew();
    
    
}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayCustomer()
{
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('GrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}

/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{document.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{document.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};
    try{document.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][41];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][42];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][44];}catch(ex){};
    try{document.all('WorkNo').value=arrResult[0][55];}catch(ex){};
    //alert(arrResult[0][55]);
    getAge();   
    
}
function displayissameperson()
{
    try{document.all('InsuredNo').value= mSwitch.getVar( "AppntNo" ); }catch(ex){};
    try{document.all('Name').value= mSwitch.getVar( "AppntName" ); }catch(ex){};
    try{document.all('Sex').value= mSwitch.getVar( "AppntSex" ); }catch(ex){};
    try{document.all('Birthday').value= mSwitch.getVar( "AppntBirthday" ); }catch(ex){};
    try{document.all('IDType').value= mSwitch.getVar( "AppntIDType" ); }catch(ex){};
    try{document.all('IDNo').value= mSwitch.getVar( "AppntIDNo" ); }catch(ex){};
    try{document.all('Password').value= mSwitch.getVar( "AppntPassword" ); }catch(ex){};
    try{document.all('NativePlace').value= mSwitch.getVar( "AppntNativePlace" ); }catch(ex){};
    try{document.all('Nationality').value= mSwitch.getVar( "AppntNationality" ); }catch(ex){};
    try{document.all('InsuredAddressNo').value= mSwitch.getVar( "AppntAddressNo" ); }catch(ex){};
    try{document.all('RgtAddress').value= mSwitch.getVar( "AppntRgtAddress" ); }catch(ex){};
    try{document.all('Marriage').value= mSwitch.getVar( "AppntMarriage" );}catch(ex){};
    try{document.all('MarriageDate').value= mSwitch.getVar( "AppntMarriageDate" );}catch(ex){};
    try{document.all('Health').value= mSwitch.getVar( "AppntHealth" );}catch(ex){};
    try{document.all('Stature').value= mSwitch.getVar( "AppntStature" );}catch(ex){};
    try{document.all('Avoirdupois').value= mSwitch.getVar( "AppntAvoirdupois" );}catch(ex){};
    try{document.all('Degree').value= mSwitch.getVar( "AppntDegree" );}catch(ex){};
    try{document.all('CreditGrade').value= mSwitch.getVar( "AppntDegreeCreditGrade" );}catch(ex){};
    try{document.all('OthIDType').value= mSwitch.getVar( "AppntOthIDType" );}catch(ex){};
    try{document.all('OthIDNo').value= mSwitch.getVar( "AppntOthIDNo" );}catch(ex){};
    try{document.all('ICNo').value= mSwitch.getVar( "AppntICNo" );}catch(ex){};
    try{document.all('GrpNo').value= mSwitch.getVar( "AppntGrpNo" );}catch(ex){};
    try{document.all( 'JoinCompanyDate' ).value = mSwitch.getVar( "JoinCompanyDate" ); if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
    try{document.all('StartWorkDate').value= mSwitch.getVar( "AppntStartWorkDate" );}catch(ex){};
    try{document.all('Position').value= mSwitch.getVar( "AppntPosition" );}catch(ex){};
    try{document.all( 'Position' ).value = mSwitch.getVar( "Position" ); if(document.all( 'Position' ).value=="false"){document.all( 'Position' ).value="";} } catch(ex) { };
    try{document.all('Salary').value= mSwitch.getVar( "AppntSalary" );}catch(ex){};
    try{document.all( 'Salary' ).value = mSwitch.getVar( "Salary" ); if(document.all( 'Salary' ).value=="false"){document.all( 'Salary' ).value="";} } catch(ex) { };
    try{document.all('OccupationType').value= mSwitch.getVar( "AppntOccupationType" );}catch(ex){};
    try{document.all('OccupationCode').value= mSwitch.getVar( "AppntOccupationCode" );}catch(ex){};
    try{document.all('WorkType').value= mSwitch.getVar( "AppntWorkType" );}catch(ex){};
    try{document.all('PluralityType').value= mSwitch.getVar( "AppntPluralityType" );}catch(ex){};
    try{document.all('DeathDate').value= mSwitch.getVar( "AppntDeathDate" );}catch(ex){};
    try{document.all('SmokeFlag').value= mSwitch.getVar( "AppntSmokeFlag" );}catch(ex){};
    try{document.all('BlacklistFlag').value= mSwitch.getVar( "AppntBlacklistFlag" );}catch(ex){};
    try{document.all('Proterty').value= mSwitch.getVar( "AppntProterty" );}catch(ex){};
    try{document.all('Remark').value= mSwitch.getVar( "AppntRemark" );}catch(ex){};
    try{document.all('State').value= mSwitch.getVar( "AppntState" );}catch(ex){};
    try{document.all('Operator').value= mSwitch.getVar( "AppntOperator" );}catch(ex){};
    try{document.all('MakeDate').value= mSwitch.getVar( "AppntMakeDate" );}catch(ex){};
    try{document.all('MakeTime').value= mSwitch.getVar( "AppntMakeTime" );}catch(ex){};
    try{document.all('ModifyDate').value= mSwitch.getVar( "AppntModifyDate" );}catch(ex){};
    try{document.all('ModifyTime').value= mSwitch.getVar( "AppntModifyTime" );}catch(ex){};
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{document.all('PostalAddress').value= mSwitch.getVar( "AppntPostalAddress" );}catch(ex){};
    try{document.all('ZipCode').value= mSwitch.getVar( "AppntZipCode" );}catch(ex){};
    try{document.all('Phone').value= mSwitch.getVar( "AppntPhone" );}catch(ex){};
    try{document.all('Fax').value= mSwitch.getVar( "AppntFax" );}catch(ex){};
    try{document.all('Mobile').value= mSwitch.getVar( "AppntMobile" );}catch(ex){};
    try{document.all('EMail').value= mSwitch.getVar( "AppntEMail" );}catch(ex){};
    try{document.all('GrpName').value= mSwitch.getVar( "AppntGrpName" );}catch(ex){};
    try{document.all('GrpPhone').value= mSwitch.getVar( "AppntGrpPhone" );}catch(ex){};
    try{document.all('GrpAddress').value= mSwitch.getVar( "CompanyAddress" );}catch(ex){};
    try{document.all('GrpZipCode').value= mSwitch.getVar( "AppntGrpZipCode" );}catch(ex){};
    try{document.all('GrpFax').value= mSwitch.getVar( "AppntGrpFax" );}catch(ex){};
    try{document.all('HomeAddress').value= mSwitch.getVar( "AppntHomeAddress" );}catch(ex){};
    try{document.all('HomePhone').value= mSwitch.getVar( "AppntHomePhone" );}catch(ex){};
    try{document.all('HomeZipCode').value= mSwitch.getVar( "AppntHomeZipCode" );}catch(ex){};
    try{document.all('HomeFax').value= mSwitch.getVar( "AppntHomeFax" );}catch(ex){};
    getAge();
}
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid();
    var InsuredNo=document.all("InsuredNo").value;
    //alert("InsuredNo="+InsuredNo);
    var ContNo=document.all("ContNo").value; 
    //alert("ContNo="+ContNo); 
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where GrpContNo='"+ GrpContNo+"' and  CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' and PatchNo='0'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        { 
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartGrid;
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
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartDetailGrid;
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
/*********************************************************************
 *  获得被保人险种信息，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //险种信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        //alert(strSQL);
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
    //alert(fm.InsuredNo.value);
    strSql = "select * from ldperson where Name='"+fm.Name.value+"' and Sex='"+fm.Sex.value+"' and Birthday=to_date('"+fm.Birthday.value+"','YYYY-MM-DD') and CustomerNo<>'"+fm.InsuredNo.value+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDNo = '"+fm.IDNo.value+"' and CustomerNo<>'"+fm.InsuredNo.value+"'"; 
    var PayIntv = easyExecSql(strSql);
    //alert(strSql);
    //fm.Name.value=strSql;
    //if(PayIntv==null)
    //{
    //	fm.InsuredChkButton2.disabled=true;
    //}
    //edit by yaory originwriter:yaory
}
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人险种详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}
/*********************************************************************
 *  根据家庭单类型，隐藏界面控件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  校验被保险人与主被保险人关系
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	    alert("个人单中'与主被保险人关系'只能是'本人'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	    fm.RelationToMainInsured.value="00";
	    return true;
    }
    else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
    {
	    alert("家庭单中第一位被保险人的'与主被保险人关系'只能是'本人'");
	    fm.RelationToMainInsured.value="00";
	    return false;
    }
    else
        return true;
}
/*********************************************************************
 *  校验保险人
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        //if (document.all('ContNo').value != "")
        //{
        //    alert("团单的个单不能有多被保险人");
        //    return false;
        //}
        //else
            //return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
            var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
//        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
//        {
//            
//            alert("家庭单只能有一个主被保险人");
//            return false;
//        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("投保人已经是该合同号下的被保险人");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //对应未选同一人，又打钩的情况
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
    document.all('DivLCInsured').style.display = "";
    divLCInsuredPerson.style.display = "";
    divSalary.style.display = "";
    try{document.all('Name').value=""; }catch(ex){};
    try{document.all('Sex').value= ""; }catch(ex){};
    try{document.all('Birthday').value= ""; }catch(ex){};
    try{document.all('IDType').value= "0"; }catch(ex){};
    try{document.all('IDNo').value= ""; }catch(ex){};
    try{document.all('Password').value= ""; }catch(ex){};
    try{document.all('NativePlace').value= ""; }catch(ex){};
    try{document.all('Nationality').value=""; }catch(ex){};
    try{document.all('RgtAddress').value= ""; }catch(ex){};
    try{document.all('Marriage').value= "";}catch(ex){};
    try{document.all('MarriageDate').value= "";}catch(ex){};
    try{document.all('Health').value= "";}catch(ex){};
    try{document.all('Stature').value= "";}catch(ex){};
    try{document.all('Avoirdupois').value= "";}catch(ex){};
    try{document.all('Degree').value= "";}catch(ex){};
    try{document.all('CreditGrade').value= "";}catch(ex){};
    try{document.all('OthIDType').value= "";}catch(ex){};
    try{document.all('OthIDNo').value= "";}catch(ex){};
    try{document.all('ICNo').value="";}catch(ex){};
    try{document.all('GrpNo').value= "";}catch(ex){};
    try{document.all('JoinCompanyDate').value= "";}catch(ex){}
    try{document.all('StartWorkDate').value= "";}catch(ex){};
    try{document.all('Position').value= "";}catch(ex){};
    try{document.all('Salary').value= "";}catch(ex){};
    try{document.all('OccupationType').value= "";}catch(ex){};
    try{document.all('OccupationCode').value= "";}catch(ex){};
    try{document.all('WorkType').value= "";}catch(ex){};
    try{document.all('PluralityType').value= "";}catch(ex){};
    try{document.all('DeathDate').value= "";}catch(ex){};
    try{document.all('SmokeFlag').value= "";}catch(ex){};
    try{document.all('BlacklistFlag').value= "";}catch(ex){};
    try{document.all('Proterty').value= "";}catch(ex){};
    try{document.all('Remark').value= "";}catch(ex){};
    try{document.all('State').value= "";}catch(ex){};
    try{document.all('Operator').value= "";}catch(ex){};
    try{document.all('MakeDate').value= "";}catch(ex){};
    try{document.all('MakeTime').value="";}catch(ex){};
    try{document.all('ModifyDate').value= "";}catch(ex){};
    try{document.all('ModifyTime').value= "";}catch(ex){};
    try{document.all('PostalAddress').value= "";}catch(ex){};
    try{document.all('PostalAddress').value= "";}catch(ex){};
    try{document.all('ZipCode').value= "";}catch(ex){};
    try{document.all('Phone').value= "";}catch(ex){};
    try{document.all('Mobile').value= "";}catch(ex){};
    try{document.all('EMail').value="";}catch(ex){};
    try{document.all('GrpName').value= "";}catch(ex){};
    try{document.all('GrpPhone').value= "";}catch(ex){};
    try{document.all('GrpAddress').value="";}catch(ex){};
    try{document.all('GrpZipCode').value= "";}catch(ex){};

    }
}
/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
    	if(!isBroker)
    	{
       showAppnt1();
      }
    else
    	{
    		showBrokerInsuredPeople();
    	}
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("未查到投保人信息");
          displayAppnt(new Array());
          emptyUndefined();
        }
        else
        {
          displayAppnt(arrResult[0]);
        }
    }
}
/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQueryNew.html" );
	}
}  
/*********************************************************************
 *  显示投保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
    try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][35];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][37];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][39];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][40];}catch(ex){};
    getAge();
    
    //地址显示部分的变动
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('GrpPhone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
}
/*********************************************************************
 *  查询返回后触发
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
  //alert("here:" + arrQueryResult + "\n" + mOperate);
    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// 查询投保单
            document.all( 'ContNo' ).value = arrQueryResult[0][0];

            arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("未查到投保单信息");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 2 )
        {		// 投保人信息
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	if (arrResult == null)
        	{
        	    alert("未查到投保人信息");
        	}
        	else
        	{
        	    displayAppnt(arrResult[0]);
        	}
        }
    }

	mOperate = 0;		// 恢复初态
}
/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailwork()
{
    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {
        fm.OccupationType.value = '';
    }
}

/*********************************************************************
 *  查询被保险人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailaddress()
{
//alert("1");
    var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
//alert("2");
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
//alert("3");
    try{document.all('ZipCode').value= arrResult[0][2];}catch(ex){};
//alert("4");
    try{document.all('HomePhone').value= arrResult[0][3];}catch(ex){};
//alert("5");
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
//alert("6");
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
//alert("7");
    try{document.all('GrpPhone').value= arrResult[0][6];}catch(ex){};
//alert("8")
   // try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    //try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
}
/*********************************************************************
 *  查询保险计划
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("保险计划没查到");
        divContPlan.style.display="";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  查询处理机构
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+GrpContNo+"' and a.ExecuteCom=b.ComCode";
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
	}

	return tCodeData;
}

function emptyInsured()
{

	try{document.all('InsuredNo').value= ""; }catch(ex){};
	try{document.all('ExecuteCom').value= ""; }catch(ex){};
	try{document.all('FamilyID').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{document.all('RelationToAppnt').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('Name').value= ""; }catch(ex){};
	try{document.all('Sex').value= ""; }catch(ex){};
	try{document.all('Birthday').value= ""; }catch(ex){};
	try{document.all('IDType').value= "9"; }catch(ex){};
	try{document.all('IDNo').value= ""; }catch(ex){};
	try{document.all('NativePlace').value= ""; }catch(ex){};
	try{document.all('Nationality').value= ""; }catch(ex){};
	try{document.all('RgtAddress').value= ""; }catch(ex){};
	try{document.all('Marriage').value= ""; }catch(ex){};
	try{document.all('MarriageDate').value= ""; }catch(ex){};
	try{document.all('Health').value= ""; }catch(ex){};
	try{document.all('Stature').value= ""; }catch(ex){};
	try{document.all('Avoirdupois').value= ""; }catch(ex){};
	try{document.all('Degree').value= ""; }catch(ex){};
	try{document.all('CreditGrade').value= ""; }catch(ex){};
	try{document.all('BankCode').value= ""; }catch(ex){};
	try{document.all('BankAccNo').value= ""; }catch(ex){};
	try{document.all('AccName').value= ""; }catch(ex){};
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{document.all('StartWorkDate').value= ""; }catch(ex){};
	try{document.all('Position').value= ""; }catch(ex){};
	try{document.all('Salary').value= ""; }catch(ex){};
	try{document.all('OccupationType').value= ""; }catch(ex){};
	try{document.all('OccupationCode').value= ""; }catch(ex){};
	try{document.all('WorkType').value= ""; }catch(ex){};
	try{document.all('PluralityType').value= ""; }catch(ex){};
	try{document.all('SmokeFlag').value= ""; }catch(ex){};
	try{document.all('ContPlanCode').value= ""; }catch(ex){};
    try{document.all('GrpName').value= ""; }catch(ex){};
    try{document.all('HomeAddress').value= ""; }catch(ex){};
    try{document.all('HomeZipCode').value= ""; }catch(ex){};
    try{document.all('HomePhone').value= ""; }catch(ex){};
    try{document.all('HomeFax').value= ""; }catch(ex){};
    try{document.all('GrpFax').value= ""; }catch(ex){};
    try{document.all('Fax').value= ""; }catch(ex){};
	emptyAddress();
	ImpartGrid.clearData();
	ImpartGrid.addOne();
}

/*********************************************************************
 *  清空客户地址数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function emptyAddress()
{
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	try{document.all('GrpPhone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('IDType').value=="0")
	{
		document.all('Birthday').value=getBirthdatByIdNo(iIdNo);
		document.all('Sex').value=getSexByIDNo(iIdNo);
		if(document.all('Sex').value=="0")
		 {
			 document.all('SexName').value="男";
		 }
		else
			 document.all('SexName').value="女";
	}
}

function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    
    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("InsuredAddressNo").CodeData=tCodeData;
}                  

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer);
}
function checkidtype()
{
	if(fm.IDType.value=="")
	{
		alert("请先选择证件类型！");
		fm.IDNo.value="";
  }
}
function getallinfo()
{
 	if(fm.Name.value!=""&&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{
	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
                +"  and Name='"+fm.Name.value
                +"' and IDType='"+fm.IDType.value
                +"' and IDNo='"+fm.IDNo.value
		+"' order by a.CustomerNo";
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  //window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}

function InsuredChk()
{
	//var tSel =InsuredGrid.getSelNo();
	//if( tSel == 0 || tSel == null )
	//{
	//	alert("请先选择被保险人！");
	//	return false;
	//}
	//var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=fm.InsuredNo.value;
	var tInsuredName=fm.Name.value;
	var tInsuredSex=fm.Sex.value;
	var tBirthday=fm.Birthday.value;
	//alert(tInsuredNo);
	//alert(tInsuredName);
	//alert(tInsuredSex);
	//alert(tBirthday);
	
        var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }
 //alert(GrpContNo);
	window.open("../app/InsuredChkMain.jsp?ProposalNo1="+GrpContNo+"&InsuredNo="+tInsuredNo+"&Flag=I","window2");
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="定制第二被保险人")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="定制第三被保险人";
                     return false;
	}
	if(fm.AutoMovePerson.value=="定制第三被保险人")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="定制第一被保险人";
                     return false;
	}
		if(fm.AutoMovePerson.value=="定制第一被保险人")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="定制第二被保险人";
                     return false;
	}
}
function noneedhome()
{
    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);
        		break;
        	}
        }
       var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};
       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};
       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};
    }
}
function getdetail()
{
var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// 在初始化body时自动效验投保人信息
function InsuredChkNew(){

        var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
        var sqlstr="select * from ldperson where Name='"+tInsuredName+"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday+"' and CustomerNo<>'"+tInsuredNo+"'"
                  + " union select * from ldperson where IDType = '"+fm.IDType.value+"' and IDType <> null and IDNo = '"+fm.IDNo.value+"' and IDNo <> null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);


        if(arrResult==null)
        {//disabled"投保人效验"按钮

					fm.InsuredChkButton.disabled = true;
//				  return false;
        }else{
					//如果有相同姓名、性别、生日不同客户号的用户显示"投保人校验"按钮
				}
}

function checkappntbirthday(){
  if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}
else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

function getAge(){
	
	//alert("fm.Birthday.value=="+fm.Birthday.value);
	
  if(fm.Birthday.value==""){
  	return;
  }
  //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	
  	if(calAge(fm.Birthday.value)<0)
  	{
      alert("出生日期只能为当前日期以前");
      fm.InsuredAppAge.value="";
      return;
    }
  	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    return;
  }
 
   
  	  if(calAge(fm.Birthday.value)<0)
  	 {
      alert("出生日期只能为当前日期以前");
      fm.InsuredAppAge.value="";
      return;
     }
  
  fm.InsuredAppAge.value=calAge(fm.Birthday.value);
  return; 

}



//校验被保人出生日期
function checkinsuredbirthday(){
	if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的被保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的被保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

/*********************************************************************
 *  把查询返回的被保人客户数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayInsuredInfo()
{
    try {document.all('InsuredNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    
    try {
        document.all('OccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;    
}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
    //alert("aaa");
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ProposalContNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyType').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolType ').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('CardFlag').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom ').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCom').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentGroup').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode1 ').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('SaleChnl').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('Handler').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday ').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredName').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredSex').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredBirthday').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDType ').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDNo').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayIntv').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayLocation').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('DisputedFlag').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OutPayFlag').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolMode').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignCom').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ConsignNo').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrintCount').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('LostTimes').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('Lang').value = arrResult[0][46];
    } catch(ex) {
    }
    ;
    try {
        document.all('Currency').value = arrResult[0][47];
    } catch(ex) {
    }
    ;
    try {
        document.all('Remark').value = arrResult[0][48];
    } catch(ex) {
    }
    ;
    try {
        document.all('Peoples ').value = arrResult[0][49];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mult').value = arrResult[0][50];
    } catch(ex) {
    }
    ;
    try {
        document.all('Prem').value = arrResult[0][51];
    } catch(ex) {
    }
    ;
    try {
        document.all('Amnt').value = arrResult[0][52];
    } catch(ex) {
    }
    ;
    try {
        document.all('SumPrem').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
    try {
        document.all('Dif').value = arrResult[0][54];
    } catch(ex) {
    }
    ;
    try {
        document.all('PaytoDate').value = arrResult[0][55];
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayDate').value = arrResult[0][56];
    } catch(ex) {
    }
    ;
    try {
        document.all('CValiDate').value = arrResult[0][57];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputOperator ').value = arrResult[0][58];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputDate').value = arrResult[0][59];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputTime').value = arrResult[0][60];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveFlag').value = arrResult[0][61];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveCode').value = arrResult[0][62];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveDate').value = arrResult[0][63];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveTime').value = arrResult[0][64];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWFlag').value = arrResult[0][65];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWOperator').value = arrResult[0][66];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWDate').value = arrResult[0][67];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWTime').value = arrResult[0][68];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppFlag').value = arrResult[0][69];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolAppntDate').value = arrResult[0][70];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolDate').value = arrResult[0][71];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolTime').value = arrResult[0][72];
    } catch(ex) {
    }
    ;
    try {
        document.all('CustomGetPolDate').value = arrResult[0][73];
    } catch(ex) {
    }
    ;
    try {
        document.all('State').value = arrResult[0][74];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][75];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][76];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][77];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][78];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][79];
    } catch(ex) {
    }
    ;
    try {
        document.all('SellType').value = arrResult[0][87];
    } catch(ex) {
    }
    ;

    try {
        document.all('AppntBankCode').value = arrResult[0][90];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][91];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][92];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = arrResult[0][93];
    } catch(ex) {
    }
    ;

}
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
    if (showInfo != null)
    {
        try
        {
            showInfo.focus();
        }
    catch
        (ex)
    {
        showInfo = null;
    }
    }
}

//职业类别双击时为个单时不显示职业类别代码
function OccupationTypeDBLClick()
{
    //alert(fm.PolTypeFlag.value);
    if (fm.PolTypeFlag.value == "0")
    {
        return ;
    }
    showCodeList('OccupationType',[fm.OccupationType]);
}

//职业类别点键盘时为个单时不显示职业类别代码
function OccupationTypeKeyUP()
{
    if (fm.PolTypeFlag.value == "0")
    {
        return ;
    }
    showCodeListKey('OccupationType',[fm.OccupationType]);
}
function checkGrpContType()
{
	 /*var sql="select * from lcgrpcont where prtno='"+fm.PrtNo.value
	      +"' and exists (select 'X' from lxbalance where trim(PrtNo)=balanceno)";
*/
  var sql=" select * from lcgrpcont where prtno='"+fm.PrtNo.value
       +"' and exists ( select 'x' from lxbalance  where prtno = "
       +" lxbalance.proposalno)" ;
	 var aRe=easyExecSql(sql);
	 if(aRe==null)
	  {
	  	isBroker=false;
	  }
	else
		{
			isBroker=true ;
		}
}
function afterQuery2(arrQueryResult)
{
	  try {document.all('Name').value= arrQueryResult[0][0]; } catch(ex) { };
	  try {document.all('Sex').value= arrQueryResult[0][1]; } catch(ex) { };
	  try {document.all('Birthday').value= arrQueryResult[0][2]; } catch(ex) { };
	  try {document.all('IDType').value= arrQueryResult[0][3]; } catch(ex) { };
	  try {document.all('IDNo').value= arrQueryResult[0][4]; } catch(ex) { };
	  try {document.all('OccupationType').value= arrQueryResult[0][5]; } catch(ex) { };
	  try {document.all('OccupationCode').value= arrQueryResult[0][6]; } catch(ex) { };
	  try {document.all('Salary').value= arrQueryResult[0][7]; } catch(ex) { };
	  try {document.all('JoinCompanyDate').value= arrQueryResult[0][8]; } catch(ex) { };
	  try {document.all('CardNo').value= arrQueryResult[0][9]; } catch(ex) { };
}
function showBrokerInsuredPeople()
{
		showInfo = window.open("../broker/BrokerInsuredPeoples.jsp?BalanceNo="+fm.PrtNo.value);
}

function checkISFILL()
{
	//  检验无名单是否需要激活，若需要激活且没有激活信息不允许补名单
	var sql="select insuredstat from lcinsured where contno='"+vContNo+"' and name='无名单' and pluralitytype is null ";
	var aRe=easyExecSql(sql);
	if(aRe==null)
	{
		alert("查询补名单信息失败");
		return false;
  }
else
	{
		if(aRe[0][0]=='0')
		{//不需要激活，直接返回
			return true;
		}
  		/**
  		 * zhangzheng 2009-02-12 
  		 * 系统没有这些lx系列的表,先封住,不妨碍无名单补名单
  		 */
//	  if(aRe[0][0]=='1')
//	  {
//	  	//需要激活，判断是否已有激活信息，若无不允许补名单
//	  //	var sql2="select count(*) from lxcontinfo where appflag='1' and balanceno='"+PrtNo+"'";
//	  	
//	  	var sql2= " select count(*) from lxcontinfo where appflag='1' and inuredflag='0' and exists (select 'x' from lxbalancesub "
//	  	+" where startno<=certifyno and endno>=certifyno and balanceno = (select balanceno from lxbalance "
//	  	+ " where proposalno='"+fm.PrtNo.value+"')) ";
//	  	
//	  	prompt("需要激活，判断是否已有激活信息，若无不允许补名单",sql2);
//	  	var tCount=easyExecSql(sql2);
//	  	if(tCount[0][0]==0)
//	  	{
//
//	  		alert("没有激活信息，请先做激活处理");	
//	  		parent.close();	
//	  		return false;
//	  		//window.close();
//	  	}
//	  	else
//	  	{
//	  		return true;
//	  	}
//	  	
//	  }
	
	}
	
	
}
//-----------------------------------------------End