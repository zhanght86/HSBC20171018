var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

// ���ҳ���ϵ�����
function ClearPagedata()
{
    fm.UserCode.value ="";
    fm.UserName.value ="";
    fm.ComCode.value = "";
    fm.ReportFlag.value ="";
    fm.RegisterFlag.value ="";
    fm.SubmitFlag.value = "";
    fm.SurveyFlag.value ="";
    fm.PrepayFlag.value = "";
    fm.SimpleFlag.value ="";
    fm.CheckFlag.value = "";
    fm.CheckLevel.value ="";
    fm.UWFlag.value ="";
    fm.UWLevel.value ="";
    //fm.StateFlag.value ="";

    fm.ComCodeName.value = "";    
    fm.ReportFlagName.value ="";
    fm.RegisterFlagName.value ="";
    fm.SubmitFlagName.value = "";
    fm.SurveyFlagName.value ="";
    fm.PrepayFlagName.value = "";
    fm.SimpleFlagName.value ="";
    fm.CheckFlagName.value = "";
    fm.CheckLevelName.value ="";
    fm.UWFlagName.value ="";
    fm.UWLevelName.value ="";
    //fm.StateFlagName.value ="";
    fm.RelPhone.value ="";
    
    fm.UpUserCode.value="";
    fm.UpUserName.value="";
    fm.JobCategory.value="";
    fm.JobCategoryName.value="";
    fm.CertifyScanName.value="";
    fm.TaskAssign.value="";
    fm.ExemptFlag.value="";
    fm.RgtFlag.value="";
    fm.CertifyScan.value="";
    fm.TaskAssignName.value="";
    fm.ExemptFlagName.value="";
    fm.RgtFlagName.value="";
}

function ClearQuerydataClick()
{
	fm.UserCodeQ.value ="";
    fm.UserNameQ.value ="";
    fm.ComCodeQ.value = "";
    fm.ComCodeQName.value = "";
}

// ��ѯ��ť
function InitQueryClick()
{
  ClearPagedata();//��ʼ��ҳ����ؼ���Ϣ

    
    //------------------------------------------------------------------------------  
	var strSQL="select usercode,username,comcode,JobCategory,(select codename from ldcode where codetype='grpjobcategory' and code=JobCategory),"
				 + "upusercode,(select username from lduser where usercode=upusercode),reportflag,registerflag,submitflag,surveyflag,"
				 + "prepayflag,simpleflag,CertifyScan,TaskAssign,checkflag,checklevel,uwflag,uwlevel,ExemptFlag,RgtFlag,relphone"
				 + " from llgrpclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')
				 + getWherePart( 'RgtFlag','RgtFlagQ')				 
				 + getWherePart( 'JobCategory','JobCategoryQ')	
				 + " order by UserCode";
	
	//prompt("���������û���ѯ:",strSQL);
//------------------------------------------------------------------------------End  
	turnPage.queryModal(strSQL, LLGrpClaimUserGrid);        
}

/*����Ȩ�ޱ�LLGrpClaimUserGrid���Ĵ�������*/
function LLGrpClaimUserGridClick()
{
    ClearPagedata();
    fm.editUserButton.disabled =false;
    fm.deleteUserButton.disabled =false;  
    fm.saveUserButton.disabled =true;      
    //�õ�����Ȩ�ޱ�ѡ�е��к�
    var tNo = LLGrpClaimUserGrid.getSelNo()-1;
    
    //��ֵ��Ϣ
    fm.UserCode.value = LLGrpClaimUserGrid.getRowColData(tNo,1);
    fm.UserName.value = LLGrpClaimUserGrid.getRowColData(tNo,2);
    fm.ComCode.value = LLGrpClaimUserGrid.getRowColData(tNo,3);
    
    fm.JobCategory.value = LLGrpClaimUserGrid.getRowColData(tNo,4);
    fm.JobCategoryName.value = LLGrpClaimUserGrid.getRowColData(tNo,5);
    
    fm.UpUserCode.value = LLGrpClaimUserGrid.getRowColData(tNo,6);
    fm.UpUserName.value = LLGrpClaimUserGrid.getRowColData(tNo,7);
    
    fm.ReportFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,8);
    fm.RegisterFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,9);
    fm.SubmitFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,10);
    
    fm.SurveyFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,11);
    fm.PrepayFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,12);
    fm.SimpleFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,13);   
    
    fm.CertifyScan.value = LLGrpClaimUserGrid.getRowColData(tNo,14);
    fm.TaskAssign.value = LLGrpClaimUserGrid.getRowColData(tNo,15);     
    
    fm.CheckFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,16);
    fm.CheckLevel.value = LLGrpClaimUserGrid.getRowColData(tNo,17);
    fm.UWFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,18);
    fm.UWLevel.value = LLGrpClaimUserGrid.getRowColData(tNo,19);
    
    fm.ExemptFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,20);
    fm.RgtFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,21);
    //fm.StateFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,14);
    fm.RelPhone.value = LLGrpClaimUserGrid.getRowColData(tNo,22);

//-------------------------------------------------------------------------------Beg
//����ڸ�״̬
//�޸��ˣ�zhouming
//�޸����ڣ�2006-04-30
    //------------------------------------------------------------------------------
    //fm.RgtFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,17);
//------------------------------------------------------------------------------End    
    
    showOneCodeName('stati','ComCode','ComCodeName');  
    showOneCodeName('ReportFlag','ReportFlag','ReportFlagName');
    showOneCodeName('RegisterFlag','RegisterFlag','RegisterFlagName'); 
    showOneCodeName('SubmitFlag','SubmitFlag','SubmitFlagName');
    showOneCodeName('CertifyScan','CertifyScan','CertifyScanName');
    showOneCodeName('TaskAssign','TaskAssign','TaskAssignName');
    showOneCodeName('RgtFlag','RgtFlag','RgtFlagName');
    showOneCodeName('SurveyFlag','SurveyFlag','SurveyFlagName');        
    showOneCodeName('PrepayFlag','PrepayFlag','PrepayFlagName');
    showOneCodeName('SimpleFlag','SimpleFlag','SimpleFlagName');    
    showOneCodeName('CheckFlag','CheckFlag','CheckFlagName');
    //showOneCodeName('popedomlevel','CheckLevel','CheckLevelName');    
    showOneCodeName('UWFlag','UWFlag','UWFlagName');
    //showOneCodeName('popedomlevel','UWLevel','UWLevelName'); 
    //showOneCodeName('StateFlag','StateFlag','StateFlagName');       
//-------------------------------------------------------------------------------Beg
//����ڸ�״̬
//�޸��ˣ�zhouming
//�޸����ڣ�2006-04-30
    //------------------------------------------------------------------------------
   // showOneCodeName('llclaimuserflag','RgtFlag','RgtFlagName');       
//------------------------------------------------------------------------------End    
    
}



/*[����]��ť��Ӧ����*/
function UserAddClick()
{
	if(fm.UserCode.value ==""||fm.UserName.value ==""||fm.ComCode.value == "")
	{
		alert("���Ȳ�ѯѡ���û�");
		return;
	}
	
	if(fm.UpUserCode.value ==""||fm.UpUserName.value =="")
	{
		alert("��¼���ϼ��û����������");
		return;
	}
	
	if(fm.JobCategory.value ==null||fm.JobCategory.value =="")
	{
		alert("��ѡ��Ȩ�޼���");
		return;
	}
	
	if(fm.RgtFlag.value ==null||fm.RgtFlag.value =="")
	{
		alert("��ѡ���ڸ�״̬!");
		return;
	}
	
    fm.fmtransact.value="User||INSERT";
    submitUserForm();
}
/*[�޸�]��ť��Ӧ����*/
function UserEditClick()
{
//	if(fm.CheckFlag.value=='1'&&(fm.CheckLevel.value==""||fm.CheckLevel.value==null))
//	{//Modify by zhaorx 2006-04-26
//		alert("����ѡ����˼���");
//		return;
//	}
//	if(fm.UWFlag.value=='1'&&(fm.UWLevel.value==""||fm.UWLevel.value==null))
//	{
//		alert("����ѡ����������");
//		return;
//	}	
    if (confirm("��ȷʵ���޸ĸü�¼��?"))
    {
	    fm.fmtransact.value="User||UPDATE";
	    submitUserForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[ɾ��]��ť��Ӧ����*/
function UserDeleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
        fm.fmtransact.value="User||DELETE";
        submitUserForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[����]��ť��Ӧ����*/
function UserResetClick()
{
	ClearPagedata();
    fm.editUserButton.disabled = true;
    fm.deleteUserButton.disabled = true;
    fm.saveUserButton.disabled = false;     
}

//�û���Ϣ��ѯ
function��userQueryClick()
{
	var strUrl="LLUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�û�Ĭ�ϵ�Ȩ��---û��Ȩ�ޣ��û������û�ʱ��
function DefaultPopedom() 
{
    fm.ReportFlag.value ="0";
    fm.RegisterFlag.value ="0";
    fm.SubmitFlag.value = "0";
    fm.SurveyFlag.value ="0";
    fm.PrepayFlag.value = "0";
    fm.SimpleFlag.value ="0";
    fm.CheckFlag.value = "0";
    fm.UWFlag.value ="0";
    //fm.StateFlag.value ="1";	
    
    showOneCodeName('ReportFlag','ReportFlag','ReportFlagName');
    showOneCodeName('RegisterFlag','RegisterFlag','RegisterFlagName');    
    showOneCodeName('SubmitFlag','SubmitFlag','SubmitFlagName');
    showOneCodeName('SurveyFlag','SurveyFlag','SurveyFlagName');        
    showOneCodeName('PrepayFlag','PrepayFlag','PrepayFlagName');
    showOneCodeName('SimpleFlag','SimpleFlag','SimpleFlagName');    
    showOneCodeName('CheckFlag','CheckFlag','CheckFlagName');
    showOneCodeName('UWFlag','UWFlag','UWFlagName');
    //showOneCodeName('StateFlag','StateFlag','StateFlagName');  
}
//�û���ѯ������Ϣ���û����� ���û��������������룩
function afterQuery( arrQueryResult ) 
{
    fm.saveUserButton.disabled = false;      
    fm.editUserButton.disabled = true;
    fm.deleteUserButton.disabled = true;    
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
    	ClearPagedata();
        arrResult = arrQueryResult;
		fm.UserCode.value = arrQueryResult[0][0];
		fm.UserName.value = arrQueryResult[0][1];
		fm.ComCode.value = arrQueryResult[0][2];    
		showOneCodeName('stati','ComCode','ComCodeName'); 
	    DefaultPopedom();   //�û�Ĭ�ϵ�Ȩ��---û��Ȩ�ޣ��û������û�ʱ�� 
	}
}
/*[��������]�ύ����̨*/
function submitUserForm()
{
  	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action = "./LLGrpClaimUserSave.jsp";
    document.getElementById("fm").submit(); //�ύ

}


/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterUserSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        InitQueryClick();//ˢ��ҳ��
    }

}

