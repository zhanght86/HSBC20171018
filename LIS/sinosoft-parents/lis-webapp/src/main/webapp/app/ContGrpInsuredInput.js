var showInfo;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
function queryperinsure()
{
    mSwitch = parent.VD.gVSwitch;
    try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};
    try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};
    arrResult = easyExecSql("select Grpcontno from lcgrpcont where prtno='" + document.all('ProposalGrpContNo').value +"'",1,0)
    var tGrpcontno=arrResult[0][0];
		//在以下SQL增加了hint词/*+RULE*/来提高效率
		
		var sqlid87="ContQuerySQL87";
		var mySql87=new SqlClass();
		mySql87.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql87.setSqlId(sqlid87);//指定使用的Sql的id
		mySql87.addSubPara(mSwitch.getVar( "GrpContNo" ));//指定传入的参数
		mySql87.addSubPara(tGrpcontno);//指定传入的参数
		
		mySql87.addSubPara(fm.ManageCom.value);//指定传入的参数
        mySql87.addSubPara(fm.InsuredNo.value);//指定传入的参数
        mySql87.addSubPara(fm.Name.value);//指定传入的参数
        mySql87.addSubPara(fm.IDNo.value);//指定传入的参数
        mySql87.addSubPara(fm.ContPlanCode.value);//指定传入的参数
	    var strSql =mySql87.getString();	
		
//    var strSql = "  select /*+RULE*/ a.InsuredNo,a.Name,a.Sex,a.Birthday,a.IDType,a.IDNo,a.ContNo,(select Case When sum(Prem) Is Null Then 0 Else sum(Prem) End  from lcpol where lcpol.InsuredNo=a.InsuredNo and lcpol.grpcontno='"+mSwitch.getVar( "GrpContNo" )+"' ),ContPlanCode from LCInsured a where a.GrpContNo='" + tGrpcontno +"'"
//				 + getWherePart( 'a.ManageCom','ManageCom' )
//				 + getWherePart( 'a.InsuredNo','InsuredNo' )
//				 + getWherePart( 'a.Name','Name' )
//				 + getWherePart( 'a.IDNo','IDNo' )
//				 + getWherePart('a.ContPlanCode','ContPlanCode')
//				 + " order by a.customerseqno, a.InsuredNo";  
    turnPage.queryModal(strSql, PersonInsuredGrid);
}
function getin()
{
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("请先查询保单信息");
		return ;
	}
   //alert("成功导入");
   var strUrl = "../app/DiskApplyInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no");
}
function getout()
{
alert("成功导出");
}
function getdetail()
{
	//alert("ok");write by yaory
        var arrReturn = new Array();
	var tSel = PersonInsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			var tRow = PersonInsuredGrid.getSelNo() - 1;
			mSwitch.deleteVar("ContNo");      //个单合同号，集体投保单号已经赋值，不再重复
            mSwitch.addVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 7));
            mSwitch.updateVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 7));

        var sqlid88="ContQuerySQL88";
		var mySql88=new SqlClass();
		mySql88.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql88.setSqlId(sqlid88);//指定使用的Sql的id
		mySql88.addSubPara(PersonInsuredGrid.getRowColData(tRow, 7));//指定传入的参数
	    var strSQL =mySql88.getString();	


          //  var strSQL="select PolType,peoples from lccont where contno='"+PersonInsuredGrid.getRowColData(tRow, 7)+"'";
            arrResult = easyExecSql(strSQL,1,0);
            if(arrResult!=null)
            {
                mSwitch.deleteVar("PolType");
                mSwitch.addVar("PolType", "", arrResult[0][0]);
                mSwitch.updateVar("PolType", "", arrResult[0][0]);

                mSwitch.deleteVar("InsuredPeoples");
                mSwitch.addVar("InsuredPeoples", "", arrResult[0][1]);
                mSwitch.updateVar("InsuredPeoples", "", arrResult[0][1]);
            }
            else
            {
            	alert("没有合同信息");
            	return false;
            }
            //alert(arrResult[0][0]);
			//////////edit by yaory
			var tNameFlag = "0";
			if (arrResult[0][0]=="1")
			    tNameFlag="1";
			if (arrResult[0][0]=="2")
			    tNameFlag="2";
			top.fraInterface.window.location="../app/ContInsuredInput.jsp?ContNo="+PersonInsuredGrid.getRowColData(tRow,7)+"&ContType=2&LoadFlag="+LoadFlag+"&tNameFlag="+tNameFlag+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&scantype="+scantype+"&checktype=2&display=0&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;
		}
		catch(ex)
		{
			alert( "出");
		}
 
	}
}
function getintopersoninsured()
{/////edit by yaory
	top.fraInterface.window.location = "../app/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1&tNameFlag=0";
}


function returnparent()
{

//top.fraInterface.window.location = "../app/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&polNo="+document.all('GrpContNo').value;
top.fraInterface.window.location = "../app/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&polNo="+mSwitch.getVar( "GrpContNo" )+"&prtNo="+mSwitch.getVar( "ProposalGrpContNo" );


}

//----------------------------Beg
//删除全部被保人
//add by :chenrong
//date:2006.7.31
function delAllInsured(){
    if (confirm("确定要删除全部被保人吗？"))
    {
        var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.action = "./ContInsuredAllDelSave.jsp";
        document.getElementById("fm").submit();
    }
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initPersonInsuredGrid();
	    queryperinsure();
	}

}
//----------------------------End