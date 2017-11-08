var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//开发中
function zhoulei()
{
	alert("开发中!");//who's zhoulei? add on 2009-03-16
}

//返回按钮
function goToBack()
{
	if(document.all('isNew').value == "3")
	{
		top.close();;
	}
	else
	{
		location.href="LLReportMissInput.jsp";
	}
}

//设置界面上的所有输入栏为readonly
function readonlyFormElements()
{
    var elementsNum = 0;//FORM中的元素数
    //遍历FORM中的所有ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
  	    if (fm.elements[elementsNum].type == "text" || fm.elements[elementsNum].type == "textarea")
  	    {
  	        fm.elements[elementsNum].readonly = true;
  	    }
  	    if (fm.elements[elementsNum].type == "button")
  	    {
  	        fm.elements[elementsNum].disabled = true;
  	    }
  	}
}

//初始化查询
function initQuery()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号(一条)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql1");
	mySql.addSubPara( rptNo);            
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];
    
    //检索报案号及其他报案信息(一条)
    /*
    var strSQL2 =" select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom, "
                +" (select username from llclaimuser where usercode = LLReport.Operator),RgtClass,PostCode "
                +" from LLReport where 1=1 "
                +" and RptNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql2");
	mySql.addSubPara( rptNo);  
    ////prompt("检索报案号及其他报案信息(一条)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());

    //更新页面内容

    
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
    fm.RgtClass.value = RptContent[0][11];
    fm.PostCode.value = RptContent[0][12];
    showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //团体个体
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('llrptmode','RptMode','RptModeName');//报案方式
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

//    fm.AddReport.disabled=false;
    fm.addbutton.disabled=false;
//    fm.updatebutton.disabled=false;
    fm.DoHangUp.disabled=false;
    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewInvest.disabled=false;
    fm.AccidentDesc.disabled=false;
//   	fm.QueryCont1.disabled=false;
    fm.QueryCont2.disabled=false;
    fm.QueryCont3.disabled=false;
    

    //检索分案关联出险人信息(多条)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                + " Sex,"
                + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                + " from LDPerson where "
                + " CustomerNo in("
                + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
    */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql3");
	mySql.addSubPara( rptNo);  
	
    //prompt("检索分案关联出险人信息(多条)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        initQuery();
        fm.RptConfirm.disabled = false;
        operateButton2.style.display="none";
        operateButton3.style.display="";
        
        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
    }
    

    
    tSaveFlag ="0";
}

//报案确认返回后执行的操作
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
    else
    {
    	//身故案件提示用户出险人作为投,被保人的所有保单已全部挂起
    	//var pReasonCode = new Array;
        //var strSQL = "select substr(ReasonCode,2,2) from LLReportReason where "
                    //+ "RpNo = '"+fm.RptNo.value+"'";
        //prompt("afterSubmit2-增加非身故类案件保单挂起提醒功能",strSQL);
        
        //tReasonCode = easyExecSql(strSQL);
        //for (var i = 0;i < tReasonCode.length; i++)
        //{  
        	//if (tReasonCode[i] == '02')
        	//{
        		//alert("该出险人作为投/被保险人的相关保单已执行保全续期挂起!");
                //break;
        	//}
        //}
        
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        goToBack();
    }
    tSaveFlag ="0";
}

//提起慰问返回后执行的操作
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();

  	    fm.AccNo.value = "";
 	    fm.RptNo.value = "";
	    fm.RptorName.value = "";
	    fm.RptorPhone.value = "";
	    fm.RptorAddress.value = "";
	    fm.Relation.value = "";
	    fm.RelationName.value = "";
	    fm.RptMode.value = "";
	    fm.RptModeName.value = "";
	    fm.AccidentSite.value = "";
	    fm.occurReason.value = "";
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    //fm.IsVip.value = "";
	    //fm.VIPValueName.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    fm.OtherAccidentDate.value = "";
	    fm.MedicalAccidentDate.value = "";
 	    fm.accidentDetail.value = "";
 	    fm.accidentDetailName.value = "";
//	 	    fm.IsDead.value = "";
//	 	    fm.IsDeadName.value = "";
 	    fm.claimType.value = "";
 	    fm.cureDesc.value = "";
 	    fm.cureDescName.value = "";
 	    fm.Remark.value = "";
 	    fm.AccDesc.value = "";//事故描述
 	    fm.AccResult1.value = "";
 	    fm.AccResult1Name.value = "";
 	    fm.AccResult2.value = "";
 	    fm.AccResult2Name.value = "";

        //理赔类型置空
        for (var j = 0;j < fm.claimType.length; j++)
        {
        	  fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

//取消按钮对应操作
function cancelForm()
{
}




//提交前的校验、计算
function beforeSubmit()
{
	//获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccReason = fm.occurReason.value;//出险原因

    
    var AccDesc = fm.accidentDetail.value;//出险细节


    
    //----------------------------------------------------------BEG
    //增加报案人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入报案人姓名！");
        return false;
    }
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("请输入报案人与事故人关系！");
        return false;
    }
    //----------------------------------------------------------end
    //1 检查出险人、信息
    if (checkInput1() == false)
    {
    	  return false;
    }

    
    var claimType=0;//选择理赔类型数量

    
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  claimType++;
          }
    }
    
    //alert("claimType:"+claimType);
    
    //5 检查理赔类型
    if (claimType==0)
    {
        alert("理赔类型不能为空！");
        return false;
    }
    
    
    //当存在医疗理赔类型时需要校验医疗出险日期
    if(fm.claimType[5].checked == true)
    {
		//校验医疗出险日期和事故日期
		if (checkDate(fm.AccidentDate.value,fm.MedicalAccidentDate.value,"医疗出险日期") == false)
		{
		     return false;
		}
    }
    
    //当存在多于一种理赔类型或存在非医疗的理赔类型时校验其他出险日期
    if((claimType>1)||((fm.claimType[5].checked == false)&&claimType==1))
    {
        //校验其他出险日期和事故日期
		if (checkDate(fm.AccidentDate.value,fm.OtherAccidentDate.value,"其他出险日期") == false)
		{
		    return false;
		}
    }

  
    //3 检查出险原因
    if (AccReason == null || AccReason == '')
    {
        alert("出险原因不能为空！");
        return false;
    }
    
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //添加出险原因为疾病时，且理赔类型为医疗时，事故日期等于医疗出险日期
    //---------------------------------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.MedicalAccidentDate.value)&& fm.claimType[5].checked == true)
    {
        alert("出险原因为疾病时，事故日期必须等于医疗出险日期！");
        return false;
    }
    
    //Modify by zhaorx 2006-07-04
//    if(fm.occurReason.value == "2" && (fm.AccResult1.value =="" || fm.AccResult1.value==null))
//    {
//        alert("出险原因为疾病时，出险结果1不能为空！");
//        return false;
//    }  
     
    //---------------------------------------------------------------------------------**End
    //4 检查出险细节
    if ((AccDesc == null || AccDesc == '') && fm.occurReason.value == '1')
    {
        alert("出险原因为意外,出险细节不能为空！");
        return false;
    }
    
    //Modify by zhaorx 2006-07-04
//    if (fm.occurReason.value == '1' && (fm.AccResult1.value ==""  || fm.AccResult1.value == null || fm.AccResult2.value == "" || fm.AccResult2.value == null))
//    {
//        alert("出险原因为意外时，出险结果1，出险结果2都不能为空！");
//        return false;
//    } 
    


    //-----------------------------------------------------------**Beg*2007-02-05 15:02
    //添加出险结果1,出险结果2为必录项
    //-----------------------------------------------------------**
    if(fm.AccResult1.value ==""  || fm.AccResult1.value == null)
    {
        alert("出险结果1不能为空！");
        return false;
    }   
    if(fm.AccResult2.value == "" || fm.AccResult2.value == null)
    {
        alert("出险结果2不能为空！");
        return false;
    }
    //-----------------------------------------------------------**End
    

    //---------------------------------------------*Beg*2005-6-13 20:26
    //添加理赔类型中选中"医疗"必须选择医院的判断
    //---------------------------------------------*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("请选择医院！");
        return false;
    }
    //---------------------------------------------*End
   
    //by niuzj,2005-11-21
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //添加理赔类型中选中豁免,必须选中死亡或高残或重大疾病之一的判断
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false && fm.claimType[2].checked == false)
    {
        alert("选中豁免,必须选中死亡或高残或重大疾病之一!");
        return false;
    }
    //---------------------------------------------------------End
    


    var tAccDate="";//比较日期,取小的那个日期作为比较日期
    
    //当都不为空时,取日期小的那个日期作为比较日期
    if((!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value==""))&&(!(fm.OtherAccidentDate.value==null||fm.OtherAccidentDate.value.value=="")))
    {
    	
    	//取两个日期中较小的那个
        if (dateDiff(fm.OtherAccidentDate.value,fm.MedicalAccidentDate.value,'D') > 0)
        {
        	tAccDate=fm.OtherAccidentDate.value;
        }
    	else
    	{
    		tAccDate=fm.MedicalAccidentDate.value;
    	}
    }
    else if(!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value=="")){
    	
    	tAccDate=fm.MedicalAccidentDate.value;
    }
    else
    {
    	tAccDate=fm.OtherAccidentDate.value;
    }

    
    //alert("tAccDate:"+tAccDate);

    //---------------------------------------------------------Beg*2005-12-28 16:22
    //出险原因为意外,只要存在一个出险日期在事故日期180天后，即提示
    //---------------------------------------------------------
    if(fm.occurReason.value == '1')
    {
    	if (dateDiff(fm.AccidentDate.value,tAccDate,'D') > 180)
        {
            alert("出险日期在事故日期180天后!");
        }
    }

    
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //添加未在出险后十日内报案的判断,医疗出险日期和其他出险日期哪个早就用哪个跟事故日期比
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("未在出险后十日内报案!"); 
    }
    
    
    //对于死亡案件这样处理：
    //1.死亡日期后的出险日期或事故日期不能报案处理；
    //2.死亡日期当天或之前的出险日期或事故日期在报案时，需要给出提示。
    //---------------------------------------------------------
    //var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql4");
	mySql.addSubPara(fm.customerNo.value); 
    
    var tDeathDate = easyExecSql(mySql.getString());
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，此日期以后的赔案不予受理！");
            return;
        }
        else
        {
            if (!confirm("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，是否继续进行？"))
            {
                return;
            }
        }
    }


    //---------------------------------------------------------End
    return true;
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,0,0,*";
    }
     else
     {
        parent.fraMain.rows = "0,0,0,0,*";
     }
}

//新增报案
function addClick()
{
    resetForm();
}

//出险人信息修改
function updateClick()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit() == true)
        {
            fm.fmtransact.value = "update";
            fm.action = './LLReportSave.jsp';
            submitForm();
        }
    }
    else
    {
        mOperate="";
    }
}

//出险人删除，需要判断，报案不允许删除
function deleteClick()
{
    alert("您无法进行删除操作！");
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

//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//由事件查询返回
function afterQueryLL2(tArr)
{
	//得到返回值
    fm.AccNo.value = tArr[0];//事件号
    fm.AccDesc.value = tArr[2];//事故描述
    fm.AccidentSite.value = tArr[3];//事故地点
    
    fm.occurReason.value = tArr[4];//出险原因编码
    fm.ReasonName.value = tArr[5];//出险原因名称
    
    fm.accidentDetail.value = tArr[6];//意外细节编码
    fm.accidentDetailName.value = tArr[7];//意外细节名称
    
    fm.AccResult1.value = tArr[8];//出险结果1编码
    fm.AccResult1Name.value = tArr[9];//出险结果1名称
    
    fm.AccResult2.value = tArr[10];//出险结果2编码
    fm.AccResult2Name.value = tArr[11];//出险结果2名称
    
    fm.Remark.value = tArr[12];//备注信息
    

}

//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
	  //得到返回值
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    
    //2008-11-18 去掉VIP标志的显示
    //if(arr[4]==null || arr[4]=="" || arr[4]==0)
    //{
     	////fm.VIPValueName.value ="否";
     	////fm.IsVip.value = "0";
    //}
    //else
    //{
     	////fm.IsVip.value = arr[4];
     	////fm.VIPValueName.value ="是";
    //}
    
    showOneCodeName('sex','customerSex','SexName');//性别
//    fm.customerSex.disabled = true;
    //初始化录入域
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.OtherAccidentDate.value = "";
    fm.MedicalAccidentDate.value = "";
    fm.accidentDetail.value = "";
    fm.accidentDetailName.value = "";
//    fm.IsDead.value = "";
//    fm.IsDeadName.value = "";
    fm.cureDesc.value = "";
    fm.cureDescName.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //设置可操作按钮
    fm.addbutton.disabled = false;
    fm.QueryCont2.disabled = false;
    fm.QueryCont3.disabled = false;
}

//出险人查询
function showInsPerQuery()
{
    window.open("LLLdPersonQueryMain.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//保单挂起
function showLcContSuspend()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);

    //Modifyed by niuzj,2005-12-23   
    var tCustomerNo = fm.customerNo.value;
	   if (tCustomerNo == "")
	   {
	      alert("请选择出险人！");
	      return;
	   }
    var strUrl="LLLpContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开生成单证通知书
function showAffix()
{
    var claimTypes=getClaimTypes();
    var CaseNo=fm.ClmNo.value;
    var whoNo=fm.customerNo.value;
    var strUrl="LLRptMAffixListMain.jsp?claimTypes="+claimTypes+"&CaseNo="+CaseNo+"&whoNo="+whoNo+"&Proc=Rpt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开发起调查
function showBeginInq()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=01";//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"发起调查");
}

//查看调查
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	//---------------------------------------
	//判断该赔案是否存在调查
	//---------------------------------------
	/*
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
     */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql5");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有调查信息！");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"查看调查");
}

//提起慰问
function showCondole()
{
	//Modifyed by niuzj,2005-12-23
	  if (fm.customerNo.value == "")
	  {
	      alert("请选择出险人！");
	      return;
	  }
	//---------------------------------------
	//判断该分案是否已提起慰问
	//---------------------------------------
	/*
    var strSQL = "select CondoleFlag from LLSubReport where "
                + " SubRptNo = '" + fm.RptNo.value + "'"
                + " and CustomerNo = '" + fm.customerNo.value + "'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql6");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    var tCondoleFlag = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCondoleFlag == "1")
    {
    	  alert("已提起慰问！");
    	  return;
    }
    fm.action = './LLReportCondoleSave.jsp';
    submitForm();
}

/*****************************************************************************
 * 开始
 * 修改原因：加一个提醒功能，点击[发起呈报]时需要弹出提示“是否先发起调查？” 是,则打开发起调查界面,
             否,则打开发起呈报界面
 * 修改人：万泽辉
 * 修改时间：2005-11-15
 ****************************************************************************/
function showBeginSubmit()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	  
    var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value;//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开呈报查询
function showQuerySubmit()
{
	  //---------------------------------------
	  //判断该赔案是否存在呈报
	  //---------------------------------------
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开事故描述信息
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//报案查询
function IsReportExist()
{
	  //检查出险人、信息
	  if (checkInput1() == false)
    {
    	  return;
    }
    queryReport();

}

//检查出险人、信息
function checkInput1()
{
	
	  //获取表单域信息
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    
    //检查出险人信息
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("请先选择出险人！");
        return false;
    }

    
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }  
    }
    return true;
}

//出险原因判断
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//报案查询
function queryReport()
{
	var AccNo;
	var RptContent = new Array();
	var queryResult = new Array();
    //检索事件号(一条)
    /*
    var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') "
                + getWherePart( 'AccType','occurReason' )
                + " and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";
*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql7");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.occurReason.value);
	mySql.addSubPara(fm.customerNo.value);
	
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
    	  fm.isReportExist.value = "false";
    	  alert("报案不存在!");
    	  return
    }
    else
    {
        //检索报案号及其他报案信息(一条)
        /*
        var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";
       */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql8");
	mySql.addSubPara(AccNo); 
	mySql.addSubPara(fm.customerNo.value);

	
	
        //prompt("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//        alert("RptContent= "+RptContent);
        if (RptContent == null)
        {
        	  fm.isReportExist.value = "false";
    	      alert("报案不存在!");
    	      return;
        }
        else
      	{
      		  //更新页面内容
      		  fm.AccNo.value = AccNo;
      		  fm.RptNo.value = RptContent[0][0];
      		  fm.RptorName.value = RptContent[0][1];
      		  fm.RptorPhone.value = RptContent[0][2];
      		  fm.RptorAddress.value = RptContent[0][3];
      		  fm.Relation.value = RptContent[0][4];
      		  fm.RptMode.value = RptContent[0][5];
      		  fm.AccidentSite.value = RptContent[0][6];
      		  fm.occurReason.value = RptContent[0][7];
      		  fm.RptDate.value = RptContent[0][8];
      		  fm.MngCom.value = RptContent[0][9];
      		  fm.Operator.value = RptContent[0][10];
              fm.RgtClass.value = RptContent[0][11];
              showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //团体个体
      		  showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
      		  showOneCodeName('RptMode','RptMode','RptModeName');//报案方式
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志
      		  //更新页面权限
      		  fm.AccidentDate.readonly = true;
//      		  fm.occurReason.disabled=true;
//      		  fm.accidentDetail.disabled=true;
      		  fm.claimType.disabled=true;

//      		  fm.AddReport.disabled=false;
      	 	  fm.addbutton.disabled=false;
//     		    fm.updatebutton.disabled=false;
    		    fm.DoHangUp.disabled=false;
    		    fm.CreateNote.disabled=false;
    		    fm.BeginInq.disabled=false;
    		    fm.ViewInvest.disabled=false;
   		      //fm.Condole.disabled=false;
    		    fm.SubmitReport.disabled=false;
    		    fm.ViewReport.disabled=false;
    		    fm.AccidentDesc.disabled=false;
//   		      fm.QueryCont1.disabled=false;
    		    fm.QueryCont2.disabled=false;
    		    fm.QueryCont3.disabled=false;
    		//出险原因校验
    		checkOccurReason();

            //检索分案关联出险人信息(多条)
            /*
            var strSQL3 = " select CustomerNo,Name,"
                        + " Sex,"
                        + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                        + " Birthday,"
                        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                        + " from LDPerson where "
                        + " CustomerNo in("
                        + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
            */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql9");
	mySql.addSubPara(RptContent[0][0]); 

	
            //prompt("检索分案关联出险人信息(多条)",strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }
    }
}

//事件查询
//2005-8-1 16:08 修改:去掉出险原因的查询条件来定位事件
function queryLLAccident()
{
	//非空检查
	if (checkInput1() == false)
    {
    	  return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("出险原因为空！");
//        return;
//    }
    //查找事件号
    /*
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql10");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    var tAccNo = easyExecSql(mySql.getString());
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
//	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//	alert(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"相关事件");
}

//[保存]按钮对应操作
function toSaveClick()
{
    //判断是否新增还是修改
    if(fm.isNew.value == '1')
    {
    	updateClick();
    }
    else
    {
        saveClick();
    }
}

//保存操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit() == true)
    {
        //判断区分保存报案还是保存出险人
        if (fm.RptNo.value != "")
        {
        	fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        fm.action = './LLReportSave.jsp';
        submitForm();
    }
}

//报案确认
function reportConfirm()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	
	  
    /**
     * 2008-11-22 zhangzheng
     * 增加非死亡类案件保单挂起提醒功能,非身故案件如果需要挂起保单,则询问用户是否需要保单挂起，如果需要挂起，则不提交后台处理，由用户调用保单挂起功能执行手工挂起，否则则不挂起保单;
	 * 身故类型的案件自动挂起作为投,被保人的所有保单
     */
/**
	var tReasonCode = 0;
    var strSQL = "select count(1) from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"' and substr(ReasonCode,2,2)='02'";
    //prompt("报案确认-增加非身故类案件保单挂起提醒功能",strSQL);
    
    tReasonCode = easyExecSql(strSQL);
    //alert("tReasonCode:"+tReasonCode);
    
    if (tReasonCode == 0)
    {
        
        //检查是否已经手工挂起
        var strSQL = "";
        var arrResult = new Array;
    	//strSQL = "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
    		   //+ " from LcCont a where 1=1 "
    		   //+ " and a.insuredno in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')"
    		   //+ " union "
    		   //+ "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
    		   //+ " from LcCont a where 1=1 "
    		   //+ " and a.AppntNo in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')";  //投保人     
        strSQL="select count(*) from lcconthangupstate where hangupno='"+fm.RptNo.value+"'";
	    arrResult = easyExecSql(strSQL);
	    ////prompt("报案确认-检查是否已经手工挂起",strSQL);
	    if (arrResult != null)
	    {
    	    for (var j=0; j<arrResult.length; j++)
    	    {
        	    for (var k=0; k<arrResult[j].length; k++)
        	    {
        	        if (arrResult[j][k] == "0")
        	        {
        	        	tReasonCode++;
        	        }
        	    }
    	    }
	    }
	    
	    if (tReasonCode > 0)
	    {
        	if (confirm("理赔类型不包含身故,是否进行保单挂起操作?"))
            {
                return;
            }
        }
        
    }
*/
    //---------------------------------------------------------------------end
    //检查非空
    if (fm.RptNo.value == "")
    {
   	    alert("报案号为空！");
   	    return;
    }
    fm.action = './LLReportConfirmSave.jsp';
    submitForm();
}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//    showSubmitFrame(mDebug);
    fm.target="fraSubmit";
    fm.submit(); //提交
    tSaveFlag ="0";
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
	//------------------------------------------**Beg
	//置空相关表单
	//------------------------------------------**
	
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	//理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //------------------------------------------**End

	
    //取得数据
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    /*
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";               
      */          
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql11");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    ////prompt("查询理赔类型",strSQL1);
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("理赔类型为空，请检查此记录的有效性！");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
                	  
                	  //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                	  if (tClaim == '01')
                	  {
                	      fm.MedCertForPrt.disabled = false;
                	  }
            	  }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    /*
    var strSQL2 = "select HospitalCode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,medaccdate,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
    */            
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql12");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    //prompt("既往赔案查询-查询分案表信息",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.MedicalAccidentDate.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
    
    var tClaimType=0;//选择理赔类型数量

    
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  tClaimType++;
          }
    }
    
    //5 检查理赔类型
    if (tClaimType==0)
    {
        alert("理赔类型不能为空！");
        return false;
    }
    
    
    //当存在医疗理赔类型医疗出险日期可以录入
    if(fm.claimType[5].checked == true)
    {
        document.all('MedicalAccidentDate').disabled=false;
    }
    
    //当存在医疗理赔类型其他出险日期可以录入
    if((tClaimType>1)||((fm.claimType[5].checked == false)&&tClaimType==1))
    {
        document.all('OtherAccidentDate').disabled=false;
    }
}

//参数为出生年月,如1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}

//---------------------------------------------------**
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//      2 选择死亡或高残时，默认选中豁免
//      3 选中豁免,必须选中死亡或高残之一(放在保存时判断)
//---------------------------------------------------**
function callClaimType(ttype)
{
	
    switch (ttype)
    {
        case "02" : //死亡
            if (fm.claimType[0].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            else
//            {
//                if (fm.claimType[0].checked == true)
//                {
//                    fm.claimType[4].checked = true;
//                }
//            }
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "04" :
        	if (fm.claimType[2].checked == true)
        	{
        		fm.claimType[4].checked = true;
        	}
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("选取死亡、高残必须选择豁免!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
        	
        	getChangeDate();
        
            return;
    }
}



/**
 * 2008-11-18
 * zhangzheng
 * 根据选择的理赔类型决定医疗出险日期和其他出险日期是否可以录入
 * 
**/
function getChangeDate()
{
	var flag=false;//控制是否准许录入其他出险日期标志,默认不准许录入
	
	//理赔类型包含医疗时,准许录入医疗出险日期
	if(fm.claimType[5].checked == true)
	{
	    document.all('MedicalAccidentDate').disabled=false;
	}
	else
	{
	    document.all('MedicalAccidentDate').value="";
	    document.all('MedicalAccidentDate').disabled=true;
	}
	
	//当只存在医疗类型时,其他出险日期不准许录入
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    document.all('OtherAccidentDate').value="";
    	document.all('OtherAccidentDate').disabled=true;
    }
}

/**=========================================================================
    修改状态：开始
    修改原因：以下为取理赔类型函数功能区
    修 改 人：潘志豪
    修改日期：2005.05.24
   =========================================================================
**/

function getClaimTypes(){
    var f=fm.elements;
    var types="";
    for (var i=0;i<f.length;i++){
    	if ((f[i].type=="checkbox")&&(f[i].checked)){
    	    if (types=="")
    	    	types=types+f[i].value;
    	    else
    	    	types=types+","+f[i].value;
        }
    }
    return types;
}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//查询出险结果
function queryResult(tCode,tName)
{/*
    var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";
    */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql13");
	mySql.addSubPara(document.all(tCode).value); 
	
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


//查询治疗医院，Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{/*
	  var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";
	             */
	      mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql14");
	mySql.addSubPara(document.all(tCode).value); 
	//modify wyc 修改页面如果没有添加治疗医院信息，再次查看报案出现全部查询显示的情况
	if(document.all(tCode).value != "" && document.all(tCode).value != null ){
		var tICDName = easyExecSql(mySql.getString());
		
	}
	//end
	
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


/**=========================================================================
    修改状态：开始
    修改原因：打印单证
    修 改 人：续涛
    修改日期：2005.07.28
    修改：2005.08.22，打印单证时在前台判断《是否存在或者需要补打》，查询流水号，传入后台，
   =========================================================================
**/
function showPrtAffix()
{
	fm.action = './LLPRTCertificatePutOutSave.jsp';  
	if(beforePrtCheck(fm.ClmNo.value,"PCT002")==false)
    {
    	return;
    } 
//	fm.target = "../f1print";
//	fm.submit();

	 
}

//打印伤残鉴定通知书
function PrintMedCert()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }

    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")==false)
    {
    	return;
    } 
//    fm.target = "../f1print";
//    fm.submit();

}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
	var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
	fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    fm.submit();
}



/**********************************
//打印前检验（），需要传入参数（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,code)
{
//	alert(clmno);return false;
	//查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
   	var tclmno=trim(clmno);
   	var tcode =trim(code);
   	if(tclmno=="" ||tcode=="")
   	{
   		alert("传入的赔案号或单证类型（号码）为空");
   		return false;
   	}
   	/*
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";
	*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql15");
	mySql.addSubPara(tclmno); 
	mySql.addSubPara(tcode); 
	
	var arrLoprt = easyExecSql(mySql.getString());
	if(arrLoprt==null)
	{
		alert("没有找到该单证的流水号");
		return false;
	}	

	var tprtseq=arrLoprt[0][0];//单证流水号
	var totherno=arrLoprt[0][1];//对应其它号码（赔案号）
	var tcode=arrLoprt[0][2];//单据类型
	var tprttype=arrLoprt[0][3];//打印方式
	var tstateflag=arrLoprt[0][4];//打印状态
	var tpatchflag=arrLoprt[0][5];//补打标志
	fm.PrtSeq.value=arrLoprt[0][0];
	//存在但未打印过，直接进入打印页面
 	if(tstateflag!="1")
 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
		fm.target = "../f1print";
		fm.submit();
 	}
	else
	{
		//存在但已经打印过
		if(confirm("该单证已经打印完成，你确实要补打吗？"))
 		{
 			//进入补打原因录入页面
 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
	 		window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 		}
	}
	
}

//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//出险细节查询
function showAccDetail(tCode,tName)
{
	var strUrl="LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//单证批量打印控制
function showPrtControl()
{
    var tClmNo = fm.RptNo.value;
    /*
	var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";
*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql16");
	mySql.addSubPara(tClmNo); 

	
	var arrCerti = easyExecSql(mySql.getString());
	if(arrCerti==null || arrCerti[0][0]=="0")
	{
		alert("没有需要进行批打控制的单证");
		return false;
	}	
    var strUrl="LLClaimCertiPrtControlMain.jsp?ClmNo="+tClmNo;
//    window.open(strUrl,"单证打印控制");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}

/**=========================================================================
    修改状态：开始
    修改原因：如果出险人和报案人是同一个人的话，那么报案人的信息从出险人的
              信息中得到
    修 改 人：万泽辉
    修改日期：2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;
   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "本人")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
          //var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
          
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql17");
	mySql.addSubPara(tCustomerNo); 
	         
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
             fm.RptorAddress.value = strQueryResult[0][0];
             fm.RptorPhone.value = strQueryResult[0][1];
             fm.RptorName.value = fm.customerName.value;
          }
      }
      else
      {
          return;
      }
   }
   

   //修改原因：如果“报案人和出险人关系”为“GX02-保单服务人员”时，那么报案人的信息取保单服务人员的信息
   //修 改 人：zhangzheng 
   //修改时间：2008-12-16
   //MS没有保单服务人员,去掉这段逻辑
   
   //else if(tRelation == "GX02" || tRelationName == "保单服务人员")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else
   {
       return;
   }
}

/*
 * 日期校验,校验两个日期都不能晚于当前日期，且第2个日期不能早于第1个日期
 * Date1,传入的第一个日期,此处为事故日期
 * Date2 传入的第二个日期,此处根据情况为医疗出险日期或其他出险日期
 * tDateName 传入的日期名称，用于组成弹出的提示语言
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//事故日期
    var AccDate2 =  tDate2;//出险日期日期
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }
    }
    
    //验证事故下有两个或以上的处理中赔案--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //新增报案时，不校验事故日期
    }    
    
    var OAccDate = fm.BaccDate.value;//原事故日期Modify by zhaorx 2006-07-04
   
    if (OAccDate != AccDate)
    {    /*
        var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"
       */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql18");
	mySql.addSubPara(fm.AccNo.value); 
	       
        //prompt("验证事故下有两个或以上的处理中赔案",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("事故下有两个或以上的处理中赔案，不允许修改事故日期！");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //校验出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"不能为空！");
        return false;
    }
    else
    {
       	//比较出险日期和当前日期
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"不能晚于当前日期!");
            return false; 
        }

        //比较出险日期和事故日期       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"不能早于事故日期!");
            return false; 
        }

    }
    
    return true;
}
