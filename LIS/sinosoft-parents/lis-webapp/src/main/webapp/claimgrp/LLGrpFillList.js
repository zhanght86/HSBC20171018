//该文件中包含客户端需要处理的函数和事件
var showInfo;
// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var mySql = new SqlClass();
function easyQueryClick() 
{
	// 初始化表格
	//initPolGrid();
	
    if(fm.PrtNo.value == ""
        && fm.GrpContNo.value == ""
     	   )
     {
         alert("请至少输入一个查询条件!");
         return false;
     }
    
	var strOperate = "like";
	/*var strSql = "select PrtNo,GrpContNo,'',RelaPeoples,GrpName,Amnt from "
				+ "lcgrpcont where grpcontno in "
				+ "(select distinct grpcontno from lccont where contno in "
				+ "(select contno from lcpol where polno in( select polno from lcinsuredrelated)))" 
	            + getWherePart('ManageCom','ManageCom',strOperate)
    		    + getWherePart('PrtNo','PrtNo')
		        + getWherePart('GrpContNo','GrpContNo');*/
//		         + getWherePart('ContNo','ContNo',strOperate)
//	 var cerCodition ="";//单证/卡号条件
//	 var SQL=strSql;
//	//增加对兼业补名单查询的支持 周磊 2007-2-5   扩展支持参加结算的团体无名单查询
//	if(fm.CertifyNo.value != "")
//    {
//      cerCodition = " and trim(prtno) in (select ba.ProposalNo from LXbalance ba where ba.Conflag = '1' and "
//                  + " exists (select 'x' from lxbalancesub basub where ba.balanceno=basub.balanceno and "
//                  + " basub.startno<='"+fm.CertifyNo.value +"' and basub.endno>='"+fm.CertifyNo.value +"'))"; 
//       SQL = strSql + cerCodition ;
//    }
	
  	//prompt("无名单补名单查询",strSql);
  	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpFillLisInputSql");
	mySql.setSqlId("LLGrpFillLisSql1");
	mySql.addSubPara(fm.ManageCom.value ); 
  	mySql.addSubPara(fm.PrtNo.value ); 
  	mySql.addSubPara(fm.GrpContNo.value ); 
	turnPage.queryModal(mySql.getString(),PolGrid);	
	
	
//	if(fm.CertifyNo.value != "")
//  { 
//  	//卡号查询方式 主要为兼业旅游险使用，因录单时不发单证
//  	if(PolGrid.mulLineCount==0)
//  	{
//  		    cerCodition = " and trim(prtno) in (select ProposalNo from LXbalance where Conflag = '1' "
//                        + " and BalanceNo in (select trim(BalanceNo) from LXContInfo where 1=1 "
//                        + getWherePart('CardNo','CertifyNo',strOperate )
//                        + " ))"
//        SQL = strSql + cerCodition ;
//        turnPage.queryModal(SQL,PolGrid);	
//  	}
}

function addNameClick() 
{ 
    var tSelNo = PolGrid.getSelNo();
    sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (tSelNo == 0) 
    { 
        alert("请选择保单！");
    }
    else 
    {
        var PrtNo = PolGrid.getRowColData(tSelNo - 1, 1); 	
        var GrpContNo = PolGrid.getRowColData(tSelNo - 1, 2); 	
//        var ContNo = PolGrid.getRowColData(tSelNo - 1, 3); 	
//        
//        //非正常保单状态提示
//        var tStateSQL="select st.statetype,st.state from lccontstate st where st.contno='"+ContNo+"' ";
//        var tContState=easyExecSql(tStateSQL);
//        if(tContState==null)
//        {
//        	alert("查询保单状态信息失败");
//        	return false;
//        }
//        var tStateType=tContState[0][0];
//        var tState=tContState[0][1];
//        
//        
//        if(tStateType=="Available"&&tState=="1")
//        {//失效状态保单
//        	if(!confirm("该保单已经失效，确定要做关系补录操作？"))
//        	{
//        		return false;
//        	}
//        }
//        if(tStateType=="Terminate"&&tState=="1")
//        {//终止状态保单
//        	if(!confirm("该保单已经终止，确定要做关系补录操作？"))
//        	{
//        		return false;
//        	}
//        }
//        
        //alert(ContNo);
        //window.open("../claimgrp/AddRelationMain.jsp?PrtNo=" + PrtNo +"&GrpContNo=" + GrpContNo,"window1");
        window.open("../claimgrp/AddRelationMain.jsp?PrtNo=" + PrtNo +"&GrpContNo=" + GrpContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content)
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "处理成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

    }
}

//这个校验只校验了数量，没有校验“质量“，暂时如此
//function checkenough(ContNo)
//{
//    var strSql1 = " select count(*) from lcpol where appflag='4' and MasterPolNo in (select  proposalNo from lcpol where Contno='" + ContNo + "')";    
//    var arrResult1 = easyExecSql(strSql1);
//    var strSql2 = " select count(*) from lcpol where  Contno='" + ContNo+"'";    
//    var arrResult2 = easyExecSql(strSql2);    
//    if(arrResult1[0][0] != arrResult2[0][0])
//    {
//    	alert("补名单的险种与原无名单所录险种有差异,不能签单,请确认!");
//    	return false;
//    }
//    else
//    {
//    	return true;
//    }
//    	
//}