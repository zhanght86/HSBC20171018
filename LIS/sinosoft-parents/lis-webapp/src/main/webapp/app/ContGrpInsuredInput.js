var showInfo;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
function queryperinsure()
{
    mSwitch = parent.VD.gVSwitch;
    try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};
    try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};
    arrResult = easyExecSql("select Grpcontno from lcgrpcont where prtno='" + document.all('ProposalGrpContNo').value +"'",1,0)
    var tGrpcontno=arrResult[0][0];
		//������SQL������hint��/*+RULE*/�����Ч��
		
		var sqlid87="ContQuerySQL87";
		var mySql87=new SqlClass();
		mySql87.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql87.setSqlId(sqlid87);//ָ��ʹ�õ�Sql��id
		mySql87.addSubPara(mSwitch.getVar( "GrpContNo" ));//ָ������Ĳ���
		mySql87.addSubPara(tGrpcontno);//ָ������Ĳ���
		
		mySql87.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
        mySql87.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
        mySql87.addSubPara(fm.Name.value);//ָ������Ĳ���
        mySql87.addSubPara(fm.IDNo.value);//ָ������Ĳ���
        mySql87.addSubPara(fm.ContPlanCode.value);//ָ������Ĳ���
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
		alert("���Ȳ�ѯ������Ϣ");
		return ;
	}
   //alert("�ɹ�����");
   var strUrl = "../app/DiskApplyInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no");
}
function getout()
{
alert("�ɹ�����");
}
function getdetail()
{
	//alert("ok");write by yaory
        var arrReturn = new Array();
	var tSel = PersonInsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			var tRow = PersonInsuredGrid.getSelNo() - 1;
			mSwitch.deleteVar("ContNo");      //������ͬ�ţ�����Ͷ�������Ѿ���ֵ�������ظ�
            mSwitch.addVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 7));
            mSwitch.updateVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 7));

        var sqlid88="ContQuerySQL88";
		var mySql88=new SqlClass();
		mySql88.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql88.setSqlId(sqlid88);//ָ��ʹ�õ�Sql��id
		mySql88.addSubPara(PersonInsuredGrid.getRowColData(tRow, 7));//ָ������Ĳ���
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
            	alert("û�к�ͬ��Ϣ");
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
			alert( "��");
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
//ɾ��ȫ��������
//add by :chenrong
//date:2006.7.31
function delAllInsured(){
    if (confirm("ȷ��Ҫɾ��ȫ����������"))
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initPersonInsuredGrid();
	    queryperinsure();
	}

}
//----------------------------End