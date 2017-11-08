var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//异地机构
function MoreInqClick()
{
	if (fm.MoreInq.checked==true)
    {
    	fm.LocFlag.value = "1";//异地标示
        fm.InqOrg.disabled = false;
    }
    else
    {
    	fm.LocFlag.value = "0";//异地标示
        fm.InqOrg.value = document.all('ManageCom').value;    	
        showOneCodeName('stati','InqOrg','InqOrgName');    	
        fm.InqOrg.disabled=true;
    }	  	  
}

//申请批次号
function AddBatNoClick()
{
		lockScreen(divLLInqApply1);
	  //检索最大批次号
    var strSQL = "select nvl(max(to_number(BatNo)),0) from LLInqApply";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLInqApplyInputSql");
	mySql.setSqlId("LLInqApplySql1");
	
    //prompt("strSQL",strSQL);
    var tMaxNo = easyExecSql(mySql.getString());
//    alert(tMaxNo[0][0]);
    if (tMaxNo == "" || tMaxNo == null) 
    {
    	  tMaxNo = Math.random()*1000;
    	  tMaxNo = parseInt(tMaxNo);
    }
    else
    {
    	  //tMaxNo = parseInt(tMaxNo[0][0]) + parseInt(Math.random()*10) + 1;
		tMaxNo = parseInt(tMaxNo[0][0]) + 1;
    }
    //alert("tMaxNo="+tMaxNo);
    fm.BatNo.value = tMaxNo;//批次号
    fm.AddBatNo.disabled = true;
    fm.InqOrg.value = document.all('ManageCom').value; //调查机构
    showOneCodeName('stati','InqOrg','InqOrgName');
    unlockScreen(divLLInqApply1);
}

//保存按钮
function saveClick()
{
  //首先进行非空字段检验   
    if (fm.BatNo.value == "")
    {
        alert("批次号为空！");
        return;
    }
    else
    {
        //调查
       /* var strSql1 = "select FiniFlag from LLInqConclusion where "
                   + "clmno = '" + document.all('ClmNo').value + "' and batno='"+fm.BatNo.value+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLInqApplyInputSql");
		mySql.setSqlId("LLInqApplySql2");
		mySql.addSubPara(document.all('ClmNo').value ); 
		mySql.addSubPara(fm.BatNo.value ); 
        var tFiniFlag = easyExecSql(mySql.getString());
        
        if (tFiniFlag)
        {
            for (var i = 0; i < tFiniFlag.length; i++)
            {
                if (tFiniFlag[i] != '1')
                {
                    alert("发起的调查没有完成,不能重复发起调查!");
                    return false;
                }
            }
        }
    }
    
    //赔案号
    if (fm.ClmNo.value == "")
    {
        alert("取得的赔案号为空！");
        return;    	  
    }
    if ( document.all('ManageCom').value == "" )
    {
        alert("取得的机构代码为空！");
        return;
    }
    //******************************************************
    //补充需要提交字段
    //******************************************************
    if(fm.InqReason.value=="" || fm.InqReason.value ==null )
	{
		alert("请填写调查原因");
		return;
	}
    
    if(fm.InqItem.value=="" || fm.InqItem.value ==null )
	{
		alert("请填写调查项目");
		return;
	}
    
    
    if(fm.InqDesc.value=="" || fm.InqDesc.value ==null )
	{
		alert("请填写调查描述");
		return;
	}
    
    //调查机构
    if ((fm.InqOrg.value == "" || fm.InqOrg.value == null) &&　document.all('ManageCom').value != "")
    {
        fm.InqOrg.value = document.all('ManageCom').value;
        fm.InqOrg2.value = document.all('ManageCom').value;//传递调查机构代码
    }
    //本地标志
    if (fm.MoreInq.checked == false) 
    {
        fm.LocFlag.value = "0";
        fm.InqOrg2.value = document.all('ManageCom').value;//传递调查机构代码
    }
    else
    {
        fm.LocFlag.value = "1";
        fm.InqOrg2.value=fm.InqOrg.value;
    }
  //检查选择的调查机构是否有调查权限
    var strRet=fm.InqOrg2.value;
    var len=strRet.length;
//    alert("调查机构编码："+len);
    if(len>=8)
    {
    	alert("该机构没有调查权限，不能为该机构分配调查任务！");
    	return;
    } 
  　//提交
  	fm.saveAdd.disabled=true;
  	lockScreen(divLLInqApply1);
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action = './LLInqApplySave.jsp';
    fm.submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	 unlockScreen(divLLInqApply1);
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //置空可编辑区域
        fm.InqItem.value = "";
        fm.InqReason.value = "";
        fm.InqReasonName.value = "";
        fm.InqDesc.value = "";
        fm.MoreInq.checked = false;
    	fm.LocFlag.value = "0";
        fm.InqOrg.value = document.all('ManageCom').value;
        fm.InqOrg2.value = document.all('ManageCom').value;
        showOneCodeName('stati','InqOrg','InqOrgName');    	
        fm.InqOrg.disabled=true;
     
        initLLInqApplyGridQuery();
    }
    tSaveFlag ="0";
}

//更新Mulline-----查询已经发起的调查
function initLLInqApplyGridQuery()
{
	//保存成功后返回
    /*var strSQL = "select clmno,batno,inqno,customerno,vipflag,initdept,inqrcode,inqitem,inqdesc,inqdept "
    			 +" ,(case locflag when '0' then '本地' when '1' then '异地' end )"
    			 +" ,(case inqstate when '0' then '未完成' when '1' then '已完成' end )"
    			 +"	from llinqapply where 1=1 "
                + getWherePart( 'ClmNo','ClmNo')
                +" order by clmno,batno";*/
     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLInqApplyInputSql");
		mySql.setSqlId("LLInqApplySql3");
		mySql.addSubPara(fm.ClmNo.value ); 
		
     turnPage.queryModal(mySql.getString(), LLInqApplyGrid);
}
