var showInfo;
function trimname(){
   fm.Name.value = trim(fm.all('Name').value);
}
function queryperinsure()
{
    mSwitch = parent.VD.gVSwitch;
    try{fm.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};
    try{fm.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){};
    arrResult = easyExecSql("select max(Grpcontno) from lcgrpcont where Proposalgrpcontno='" + fm.all('ProposalGrpContNo').value +"'",1,0)
    var tGrpcontno=arrResult[0][0];
    var strOperate = "like";
//    var strSql = "select a.InsuredNo,a.Name,a.Sex,a.Birthday,a.IDType,a.IDNo,a.ContNo,(select sum(Prem) from lcpol where lcpol.InsuredNo=a.InsuredNo and lcpol.grpcontno='"+fm.all('ProposalGrpContNo').value+"' ),a.ContPlanCode from LCInsured a where a.GrpContNo='" + tGrpcontno +"'"

    if(fm.IDNo.value!="")
    {
    	var temp=fm.IDNo.value;
    	var tReshvalue=temp;
    	if(temp.length==15)
    	{
    		var tidno=easyExecSql("select trans1('"+temp+"') from dual");
        tReshvalue=tidno[0][0];
    	}
    }
    	//tongmeng 2009-04-02 modify
    	//修改查询SQL
    	//有险种,但不包含连带被保人的数据
    	var tSQL_Insured = "";
tSQL_Insured = " select A.a,A.b,A.c,A.d,A.e,A.f,A.g,A.h,A.i,A.j,A.k,A.l, "
             + " (select occupationname from ldoccupation where occupationcode=A.k), "
	           + " A.n,A.o,A.p from ( "
						 + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,a.insuredappage f "
						 + " ,a.riskcode g,b.contplancode h,a.prem i,a.amnt j, "
	           + " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k "
	           + " ,b.occupationtype l,'' m职业名称, "
	           + " a.floatrate n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p "
	           + " from lcpol a, lcinsured b "
	           + " where a.insuredno = b.insuredno and a.contno = b.contno "
	           + " and b.grpcontno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 	 + getWherePart('b.ContPlanCode','ContPlanCode')
					 	 + " and b.Name like '%%"+fm.all('Name').value+"%%' " 
	           //有险种,连带被保人数据查询 
             + " union "
	           + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,get_age(b.birthday,(select cvalidate from lcgrpcont where grpcontno =a.grpcontno)) f "
	           + " ,'连带被保人' g,''h,0 i,0 j, "
             + " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k, "
             + " b.occupationtype,'' m职业名称, "
	           + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p "
             + " from lcpol a, lcinsured b,lcinsuredrelated c  "
             + " where b.insuredno = c.customerno and a.contno = b.contno and a.polno=c.polno "
             + " and b.grpcontno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 	 + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 	 + " and b.Name like '%%"+fm.all('Name').value+"%%' " 
             //没险种,没有连带被保人数据 
             + " union "
	           + " select b.prtno a,b.prtno b,b.insuredno c,b.name d,b.sex e, "
						 + " decode((select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno), "
	           + " null,0,(select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno)) f, "
						 + " '' g,'' h,0 i,0 j, "
						 + " (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno) k, "
						 + " b.occupationtype l,''m职业名称, "
						 + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,contno p "
	           + " from lcinsured b "
						 + " where not exists (select '1' from lcpol where grpcontno = b.grpcontno and insuredno = b.insuredno) "
						 + " and not exists (select '1' from lcinsuredrelated where polno in (select polno from lcpol where grpcontno=b.grpcontno and customerno=b.insuredno))"
						 + " and b.grpcontno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + " and b.Name like '%%"+fm.all('Name').value+"%%' " 
	           + " ) A order by A.p,A.a,A.c "
    	/*
    	 //保存了险种的被保人
    	 var strSql1 = " select a.polno,b.prtno,b.insuredno,b.name,b.sex,a.insuredappage,a.riskcode,b.contplancode,"
    	             +" a.prem,a.amnt,(select max(occupationcode) from lcinsured where lcinsured.insuredno = a.insuredno),(select max(occupationtype) from lcpol where lcpol.grpcontno = a.grpcontno),"
    	             +" (select max(occupationname) from LDOccupation where OccupationCode in (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno)),a.floatrate,nvl((select cvalidate from lccont where contno=b.contno),to_date('')),b.contno "
    	             +" from lcpol a, lcinsured b where a.insuredno=b.insuredno and a.prtno=b.prtno and a.prtno='"+tGrpcontno+"' "
                     + getWherePart( 'b.ManageCom','ManageCom',strOperate )
				 	 + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 + getWherePart('b.ContPlanCode','ContPlanCode')
		 //没有保存险种信息的被保人
	     var strSql2 =" union select b.prtno, b.prtno, b.insuredno, b.name,b.sex,decode((select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno),null,0,(select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno))"
	                 +" ,'',b.contplancode, 0,0,(select max(occupationcode) from lcinsured  where lcinsured.insuredno = b.insuredno),"
	                 +" b.occupationtype, (select max(occupationname) from LDOccupation where ldoccupation.occupationcode = b.occupationcode), 0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')),contno from lcinsured b where b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 + getWherePart( 'b.IDNo','IDNo' )
	         		 + "  and not exists "
   			 	     + " (select '1' from lcpol where grpcontno =b.grpcontno and insuredno=b.insuredno) "
   			 	     + " and not exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
   		 //连带被保人
	     var strSql3 =" union  select a.polno, b.prtno, a.customerno,a.name, a.sex, 0,(select c.insuredname from lcpol c where polno = a.polno)||'-'||'连带', '', 0, 0, "
	                 +"  (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno),"
	                 +" (select occupationtype from lcinsured where grpcontno =b.grpcontno and insuredno=b.insuredno),"
	                 +" (select max(occupationname) from LDOccupation  where ldoccupation.occupationcode = (select occupationcode from lcinsured where grpcontno = b.grpcontno and insuredno = b.insuredno)),"
	                 +"  0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')), contno from lcinsuredrelated a,lcinsured b where a.polno in (select polno from lcpol where grpcontno=b.grpcontno) and a.customerno = b.insuredno and b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 + " and b.insuredno = (select insuredno from lcinsured where grpcontno =b.grpcontno and insuredno = b.insuredno and idno='"+fm.IDNo.value+"')"
   			 	     + " and  exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	    if(fm.all('Name').value !="")
	     {
	        strSql1 =strSql1+" and b.Name like '%%"+fm.all('Name').value+"%%' ";
	        strSql2 =strSql2+" and b.Name like '%%"+fm.all('Name').value+"%%' ";
	        strSql3 =strSql3+" and a.Name like '%%"+fm.all('Name').value+"%%' ";
	     }
     }else{
    var strSql1 = " select a.polno,b.prtno,b.insuredno,b.name,b.sex,a.insuredappage,a.riskcode,b.contplancode,"
    	              +" a.prem,a.amnt,(select max(occupationcode) from lcinsured where lcinsured.insuredno = a.insuredno),(select max(occupationtype) from lcpol where lcpol.grpcontno = a.grpcontno),"
    	              +" (select max(occupationname) from LDOccupation where OccupationCode in (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno)),a.floatrate,nvl((select cvalidate from lccont where contno=b.contno),to_date('')),b.contno "
    	              +" from lcpol a, lcinsured b where a.insuredno=b.insuredno and a.prtno=b.prtno and  a.prtno='"+tGrpcontno+"' "
				      + getWherePart( 'b.ManageCom','ManageCom',strOperate)
				      + getWherePart( 'b.InsuredNo','InsuredNo' )
				      + getWherePart('b.ContPlanCode','ContPlanCode')
	var strSql2 =" union select b.prtno, b.prtno, b.insuredno, b.name,b.sex,decode((select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno),null,0,(select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno))"
	                 +" ,'' ,"
	                 +" b.contplancode, 0,0,(select max(occupationcode) from lcinsured  where lcinsured.insuredno = b.insuredno),"
	                 +" b.occupationtype, (select max(occupationname) from LDOccupation where ldoccupation.occupationcode = b.occupationcode), 0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')),contno from lcinsured b where b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
	         		 + "  and not exists "
   			 	     + " (select '1' from lcpol where grpcontno =b.grpcontno and insuredno=b.insuredno) "
   			 	     + " and not exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	var strSql3 =" union  select a.polno, b.prtno, a.customerno,a.name, a.sex, 0,(select c.insuredname from lcpol c where polno = a.polno)||'-'||'连带', '', 0, 0, "
	                 +"  (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno),"
	                 +" (select occupationtype from lcinsured where grpcontno =b.grpcontno and insuredno=b.insuredno),"
	                 +" (select max(occupationname) from LDOccupation  where ldoccupation.occupationcode = (select occupationcode from lcinsured where grpcontno = b.grpcontno and insuredno = b.insuredno)),"
	                 +"  0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')), contno from lcinsuredrelated a,lcinsured b where a.polno in (select polno from lcpol where grpcontno=b.grpcontno) and a.customerno = b.insuredno and b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 //+ getWherePart( 'b.IDNo','IDNo' )
   			 	     + " and  exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	       if(fm.all('Name').value !="")
	         {
	            strSql1 =strSql1+" and b.Name like '%%"+fm.all('Name').value+"%%' ";
	            strSql2 =strSql2+" and b.Name like '%%"+fm.all('Name').value+"%%' ";
	            strSql3 =strSql3+" and a.Name like '%%"+fm.all('Name').value+"%%' ";
	         }
				 
	       }
	  strSql1 = strSql1+strSql2+strSql3;
	  strSql1 =strSql1+" order by insuredno ";
	  */
		fm.querySql.value  = tSQL_Insured;
    turnPage.queryModal(tSQL_Insured, PersonInsuredGrid);
    if(!turnPage.strQueryResult)
    {
    	 alert("查询无结果");
    }
}
function getin()
{
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("您的网页可能超时，请重新登录！");
		return ;
	}
   //alert("成功导入");
   var strUrl = "../cardgrp/DiskApplyInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   //被保人清单导入
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}
function getout()
{
  //alert(fm.ProposalGrpContNo.value);
  if (confirm("您将删除所有的被保人,确定吗？"))
	      {
	      	lockScreen('lkscreen');  
  fm.action="DeleteAllInsured.jsp";
	fm.submit();
}
}

//删除保障计划及参与保障计划的被保人
function getcontplan()
{
	
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("您的网页可能超时，请重新登录！");
		return ;
	}
   
   var strUrl = "../cardgrp/ContPlanInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   //window.open(strUrl);
   //删除保障计划
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}

function printout()
{
	var strUrl = "../cardgrp/PrintOut.jsp?ProposalGrpContNo="+ fm.ProposalGrpContNo.value
  window.open(strUrl);
	//fm.submit();	
}

function downAfterSubmit(cfilePath,fileName) { 
	//showInfo.close();
  //alert("adadad");
  fileUrl.href = cfilePath ;//+ fileName; 
  //prompt("",fileUrl.href);
  //fileUrl.click();
  fm.action="./download.jsp?file="+fileUrl.href;
  fm.submit();
}


function printout1()
{
	//导出人名单
	var sql="select InsuredNo,name,sex,birthday,idtype,idno,OccupationType,OccupationCode From lcinsured where grpcontno='"+fm.ProposalGrpContNo.value+"'";
	var Result = easyExecSql(sql, 1, 1);
	//alert( Result.length);
	//return;
	if(Result!=null)
	{
		
		try {
          var xls    = new ActiveXObject ( "Excel.Application" );
       }
       catch(e) {
          alert( "要导入数据到Excel，您必须安装Excel电子表格软件，同时设置IE浏览器的安全级别允许使用“ActiveX 控件”！！");
          return "";
       }

          xls.visible = true;

      var xlBook = xls.Workbooks.Add;
      var xlsheet = xlBook.Worksheets(1);
      xls.Cells.Select;
      xls.Selection.NumberFormatLocal = "@";
xlsheet.Cells(1,1).Value="编号";
xlsheet.Cells(1,2).Value="客户号码";
xlsheet.Cells(1,3).Value="名字";
xlsheet.Cells(1,4).Value="性别";
xlsheet.Cells(1,5).Value="出生日期";
xlsheet.Cells(1,6).Value="证件类型";
xlsheet.Cells(1,7).Value="证件号码";
xlsheet.Cells(1,8).Value="职业类别";
xlsheet.Cells(1,9).Value="工种";

     
for (i = 0; i < Result.length; i++)
{

xlsheet.Cells(i+2,1).Value=i+1;
xlsheet.Cells(i+2,2).Value=Result[i][0];
xlsheet.Cells(i+2,3).Value=Result[i][1];
xlsheet.Cells(i+2,4).Value=Result[i][2];
xlsheet.Cells(i+2,5).Value=Result[i][3];
xlsheet.Cells(i+2,6).Value=Result[i][4];
xlsheet.Cells(i+2,7).Value=Result[i][5];
xlsheet.Cells(i+2,8).Value=Result[i][6];
xlsheet.Cells(i+2,9).Value=Result[i][7];


}
		
		
	}else{
	 alert("没有数据导出！");
	}
}
	


function getdetail()
{
	//alert("ok");//write by yaory
        var arrReturn = new Array();
	var tSel = PersonInsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			var tRow = PersonInsuredGrid.getSelNo() - 1;
		   //如果是连带被保人不能进入险种信息
		   var tRiskCode = PersonInsuredGrid.getRowColData(tRow, 7);
		   //alert(tRiskCode);
		   if(tRiskCode=="连带被保人"){
		      alert("请进入其主被保人查看险种信息！");
		      return false;
		   }
			mSwitch.deleteVar("ContNo");      //个单合同号，集体投保单号已经赋值，不再重复
	                mSwitch.addVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 16));
	                mSwitch.updateVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 16));
	            //tongmeng 2009-03-20 add
	            //增加个单生效日的录入
	  //              mSwitch.addVar("ContCValidate", "", PersonInsuredGrid.getRowColData(tRow, 15));
	//                mSwitch.updateVar("ContCValidate", "", PersonInsuredGrid.getRowColData(tRow, 15));
//alert( mSwitch.getVar( "ContCValidate" ));
	                var strSQL="select PolType,peoples,cvalidate from lccont where contno='"+PersonInsuredGrid.getRowColData(tRow, 16)+"'";
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
			//////////edit by yaory

	        //2008-09-18 zhangzheng 根据被保险人类型进入到相应页面
			var tInsuredType = mSwitch.getVar("PolType");
			
			//alert("合同:"+PersonInsuredGrid.getRowColData(tRow, 16)+"的被保险人帐户类型:"+tInsuredType)

			if(tInsuredType!="0" && tInsuredType!="1" && tInsuredType!="2")
			{
				tInsuredType="0";
			}
			
			//alert(tInsuredType); 
			
			top.fraInterface.window.location="../cardgrp/ContInsuredInput.jsp?ContNo="+PersonInsuredGrid.getRowColData(tRow,16)+"&ContType=2&LoadFlag="+LoadFlag+"&tNameFlag="+tInsuredType+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&scantype="+scantype+"&checktype=2&display=0&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+PersonInsuredGrid.getRowColData(tRow, 3);
		}
		catch(ex)
		{
			alert( "界面出错！");
		}
 
	}
}
function getintopersoninsured()
{/////edit by yaory
	top.fraInterface.window.location = "../cardgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
}


function returnparent()
{
//alert(LoadFlag);
//top.fraInterface.window.location = "../cardgrp/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&polNo="+fm.all('GrpContNo').value;
top.fraInterface.window.location = "../cardgrp/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+mSwitch.getVar("GrpContNo")+"&polNo="+mSwitch.getVar( "GrpContNo" );


}

function afterSubmit( FlagStr, content )
{
   unlockScreen('lkscreen');  
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
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	queryperinsure();
}
}

function contGrpInsuredCreateExcel()
{
	var querySql = fm.querySql.value;
	var GrpContNo = fm.GrpContNo.value;
	if(querySql==""){
	alert ("请先查询!");
}
else
	{
	try
		{
			//alert(querySql);
			fm.action = "./ContGrpInsuredCreateExcel.jsp?querySql="+querySql+"&GrpContNo="+GrpContNo;
        	fm.target=".";
   
       		fm.submit();
					}
		catch(ex)
		{
			alert( ex );
		}
	}
}

function saveinsuinfo()
{
  if(fm.all('ProposalGrpContNo').value == "" ||fm.all('ManageCom').value == "")
  {
   alert("请输入投保单号和管理机构!");
   return;
  }
  //if(PersonInsuredGrid.mulLineCount<=0)
  //{
  //  alert("没有可保存的数据,请先查询或是添加被保险人后在进行保存!");
  //  return;
  //}
    var sqldate ="select substr(sysdate,0,10) from dual";
    var date = easyExecSql(sqldate);
	var filename=fm.all('ManageCom').value+"_"+fm.all('ProposalGrpContNo').value+"_"+date;
	
	filename=filename+".xls";
	//alert(filename);
	fm.FileName.value=filename; 
	var strSql = "select SysVarValue from LDSysVar where SysVar = 'InsuInfoCreat'";
   	filePath = easyExecSql(strSql);
   	//filePath = filePath+'/';
   	fm.Url.value=filePath;
   	
   	
	　　fm.fmtransact.value = "Create";
	lockScreen('lkscreen');  
	    fm.action="./ContGrpInsuInfoSave.jsp";
	    fm.submit();
}

function ConfirmSelect()
{
	 //showInfo.close();
	 
	 if(confirm("数据已经生成，是否重新计算?如果重算将覆盖原始数据。"))
	 {
	 	   var i = 0;
//       var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 //      showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 	   //点确定之后将其值变为1 起到开关的作用
	 	   fm.all('myconfirm').value = "1";
	 	   //相当于window.location重定向请求
	 	   fm.submit();
	  }
	else
		{
		}
}

function filedownload()
{
  var i = 0;
//  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
 
    var sqldate ="select substr(sysdate,0,10) from dual";
    var date = easyExecSql(sqldate);
	var filename=fm.all('ManageCom').value+"_"+fm.all('ProposalGrpContNo').value+"_"+date;
                      
       filename=filename+".xls";
	//prompt("",filename);
	fm.FileName.value=filename; 
	//alert("bbb");
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
	var strSql = "select SysVarValue from LDSysVar where SysVar = 'InsuInfoCreat'";
   filePath = easyExecSql(strSql);
   fm.Url.value=filePath;
   //alert(fm.Url.value+filename);
   fm.fmtransact.value = "download";
   fm.action="./ContGrpInsuInfoSave.jsp";
   fm.submit(); 
   
 }
   
