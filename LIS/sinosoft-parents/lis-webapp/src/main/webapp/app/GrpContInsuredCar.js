var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
var mSwitch = parent.VD.gVSwitch;
var showInfo;
function queryCarInfo()
{    
    try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};
    try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};
    arrResult = easyExecSql("select Grpcontno from lcgrpcont where Proposalgrpcontno='" + document.all('ProposalGrpContNo').value +"'",1,0)
    if (arrResult != null && arrResult.length > 0)
    {
        var tGrpcontNo=arrResult[0][0];
        fm.GrpContNo.value=tGrpcontNo;
		
		var sqlid26="ContQuerySQL26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
		mySql26.addSubPara(tGrpcontNo);//ָ������Ĳ���
		mySql26.addSubPara(fm.CarNo.value);//ָ������Ĳ���
	     var strSql=mySql26.getString();
		
//        var strSql = "select distinct a.p1,a.p3,a.p2,a.p4,"
//                     + "(select codename from ldcode where codetype='TrafficType' and code=a.p4)"
//                     + " from LCPolOther a where a.GrpContNo='" + tGrpcontNo +"'"
//    				 + getWherePart('a.p1','CarNo')
//    				 + " order by a.p1";  
    	initInsuredCarGrid();
        turnPage.queryModal(strSql, InsuredCarGrid);
    }
    else
    {
        alert("û���ʺ������ĳ�������");
    }
}

function queryPolInfo()
{    
    var tSel = InsuredCarGrid.getSelNo();
    //var tGrpContNo = document.all('ProposalGrpContNo').value;
    var tGrpContNo=fm.GrpContNo.value
    var tCarNo = InsuredCarGrid.getRowColData(tSel - 1, 1);    
    
		var sqlid27="ContQuerySQL27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(tCarNo);//ָ������Ĳ���
		mySql27.addSubPara(tGrpContNo);//ָ������Ĳ���
		mySql27.addSubPara(tGrpContNo);//ָ������Ĳ���
		mySql27.addSubPara(tCarNo);//ָ������Ĳ���
	     var strSql=mySql27.getString();
	
	
//    var strSql = "select '" + tCarNo + "',"
//                 + "riskcode,(select riskname from lmrisk where riskcode=lcpol.riskcode),"
//                 + "sum(prem),sum(amnt) from lcpol where grpcontno='" + tGrpContNo 
//                 + "' and contno in (select distinct contno from lcpolother where grpcontno='" 
//                 + tGrpContNo + "' and p1='" + tCarNo + "') group by riskcode";
	initCarPolGrid();
  turnPage1.queryModal(strSql,CarPolGrid);
}
function getin()
{
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("���Ȳ�ѯ������Ϣ");
		return ;
	}
   var strUrl = "../app/DiskApplyInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value + "&ImportFlag=Car";
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no");
}

function getout()
{
    alert("�ɹ�����");
}

function returnparent()
{
    top.fraInterface.window.location = "../app/ContPolInput.jsp?LoadFlag="+fm.LoadFlag.value +"&scantype="+fm.ScanType+"&polNo="+mSwitch.getVar( "GrpContNo" );
}

function delAllInsuredCar(){
    if (confirm("ȷ��Ҫɾ��ȫ��������Ϣ��"))
    {
        var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.action = "./GrpContCarAllDelSave.jsp";
        document.getElementById("fm").submit();
    }
}

function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    queryCarInfo();
	    CarPolGrid.clearData();
	}

}