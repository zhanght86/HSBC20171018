//该文件中包含客户端需要处理的函数和事件
var showInfo;
// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

function easyQueryClick() 
{
	// 初始化表格
	initPolGrid();
	
    //输入条件校验
    if(fm.PrtNo.value == ""
       && fm.GrpContNo.value == ""
    	   )
    {
        alert("请输入投保单号或团体合同号!");
        return false;
    }
    
	var strOperate = "like";
	
	 	var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.GrpFillListInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		
//	  var strSql=mySql1.getString();
	  
//	  var strSql = "select PrtNo,GrpContNo,ContNo,Peoples,Prem,Amnt from LCCont"
//	           + " where (appflag='1' or appflag='4') and poltype='1' and conttype='2' /*and peoples>0*/ and grpcontno !='00000000000000000000' "
//	           + " and ManageCom like "+fm.ManageCom.value+" and PrtNo like "+fm.PrtNo.value+" and GrpContNo like "+fm.GrpContNo.value+" and ContNo like "+fm.ContNo.value+""
	          
//EJB 改造前SQL	
//	var strSql = "select PrtNo,GrpContNo,ContNo,Peoples,Prem,Amnt from LCCont"
//	           + " where (appflag='1' or appflag='4') and poltype='1' and conttype='2' /*and peoples>0*/ and grpcontno !='00000000000000000000' "
//	           + getWherePart('ManageCom','ManageCom',strOperate)
//    		     + getWherePart('PrtNo','PrtNo',strOperate )
//		         + getWherePart('GrpContNo','GrpContNo',strOperate)
//		         + getWherePart('ContNo','ContNo',strOperate)
	 var cerCodition =" and 1=1 ";//单证/卡号条件
//	 var SQL=strSql;
	//增加对兼业补名单查询的支持 周磊 2007-2-5   扩展支持参加结算的团体无名单查询
	if(fm.CertifyNo.value != "")
    {      
      cerCodition = " and trim(prtno) in (select ba.ProposalNo from LXbalance ba where ba.Conflag = '1' and "
                  + " exists (select 'x' from lxbalancesub basub where ba.balanceno=basub.balanceno and "
                  + " basub.startno<='"+fm.CertifyNo.value +"' and basub.endno>='"+fm.CertifyNo.value +"'))"; 
//       SQL = strSql + cerCodition ;
    }
	
  	//prompt("无名单补名单查询",SQL);
  	
	mySql1.addSubPara(cerCodition);//指定传入的参数
    var SQL=mySql1.getString();  	
	turnPage.queryModal(SQL,PolGrid);	
	
	
	if(fm.CertifyNo.value != "")
  { 
  	//卡号查询方式 主要为兼业旅游险使用，因录单时不发单证
  	if(PolGrid.mulLineCount==0)
  	{  		    
  		    cerCodition = " and trim(prtno) in (select ProposalNo from LXbalance where Conflag = '1' "
                        + " and BalanceNo in (select trim(BalanceNo) from LXContInfo where 1=1 "
                        + getWherePart('CardNo','CertifyNo',strOperate )
                        + " ))"
//        SQL = strSql + cerCodition ;
//        turnPage.queryModal(SQL,PolGrid);	

	 	var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.GrpFillListInputSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql2.addSubPara(fm.GrpContNo.value);//指定传入的参数
		mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
        mySql2.addSubPara(cerCodition);//指定传入的参数
        var SQL=mySql2.getString();  	
    	turnPage.queryModal(SQL,PolGrid);	
  	}
  	

  }
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
        var ContNo = PolGrid.getRowColData(tSelNo - 1, 3); 	
        var Peoples =PolGrid.getRowColData(tSelNo - 1,4);
        if(Peoples<=0){
          alert("被保人人数为 0,无名单已经补完，请确认！");
          return false;
        }
        
        //非正常保单状态提示
          var sqlid4="DSHomeContSql4";
					var mySql4=new SqlClass();
					mySql4.setResourceName("app.GrpFillListInputSql"); //指定使用的properties文件名
					mySql4.setSqlId(sqlid4);//指定使用的Sql的id
					mySql4.addSubPara(ContNo);//指定传入的参数
				  var tStateSQL=mySql4.getString();	
        
        
//        var tStateSQL="select st.statetype,st.state from lccontstate st where st.contno='"+ContNo+"' ";
        var tContState=easyExecSql(tStateSQL);
        if(tContState==null)
        {
        	alert("查询保单状态信息失败");
        	return false;
        }
        var tStateType=tContState[0][0];
        var tState=tContState[0][1];
        
        
        if(tStateType=="Available"&&tState=="1")
        {//失效状态保单
        	if(!confirm("该保单已经失效，确定要做补名单操作？"))
        	{
        		return false;
        	}
        }
        if(tStateType=="Terminate"&&tState=="1")
        {//终止状态保单
        	if(!confirm("该保单已经终止，确定要做补名单操作？"))
        	{
        		return false;
        	}
        }
        
        //alert(ContNo);
        window.open("../app/GrpContPolMain.jsp?ContNo=" + ContNo + "&PrtNo=" + PrtNo +"&LoadFlag=18&GrpContNo=" + GrpContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content)
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
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
		content = "处理成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
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

//这个校验只校验了数量，没有校验“质量“，暂时如此
function checkenough(ContNo)
{
    var sqlid5="DSHomeContSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.GrpFillListInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(ContNo);//指定传入的参数
	  var strSql1=mySql5.getString();	
    
//    var strSql1 = " select count(*) from lcpol where appflag='4' and MasterPolNo in (select  proposalNo from lcpol where Contno='" + ContNo + "')";    
    var arrResult1 = easyExecSql(strSql1);
    
    var sqlid6="DSHomeContSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.GrpFillListInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(ContNo);//指定传入的参数
	  var strSql2=mySql6.getString();	
    
    //var strSql2 = " select count(*) from lcpol where  Contno='" + ContNo+"'";    
    var arrResult2 = easyExecSql(strSql2);    
    if(arrResult1[0][0] != arrResult2[0][0])
    {
    	alert("补名单的险种与原无名单所录险种有差异,不能签单,请确认!");
    	return false;
    }
    else
    {
    	return true;
    }
    	
}