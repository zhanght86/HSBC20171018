var showInfo;
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";


/*�ύ��̨ǰ��ҳ������У��*/
function beforePopedomSubmit()
{
		var AccDate1 = fm.StartDate.value;//��������
		var AccDate2 = fm.EndDate.value;//��������
		var AccYear1 = AccDate1.substring(0,4);
    var AccMonth1 = AccDate1.substring(5,7);
    var AccDay1 = AccDate1.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    
    if (AccDate1 == null || AccDate1 == '')
    {
        alert("�������ڲ���Ϊ�գ�");
        return false;
    }
    
    
    if (AccDate2 != null && AccDate2 != '')
    {
	   	if (AccYear1 > AccYear2)
	    {
	        alert("�������ڲ������ڽ�������");
	        return false;
	    }
	   	else if (AccYear1 == AccYear2)
	    {
	        if (AccMonth1 > AccMonth2)
	        {
	
	            alert("�������ڲ������ڽ�������");
	            return false;
	        }
	        else if (AccMonth1 == AccMonth2 && AccDay1 > AccDay2)
	        {
	            alert("�������ڲ������ڽ�������");
	            return false;
	        }
	    }
	}
    
    var BaseMin = parseFloat(fm.BaseMin.value);//��С���
    var BaseMax = parseFloat(fm.BaseMax.value);//�����
    
  	if (BaseMin == null || BaseMin == '')
  	{
  			alert("��С����Ϊ��");
    		return false;
  	}
  	
  	if (BaseMax == null || BaseMax == '')
  	{
  			alert("������Ϊ��");
    		return false;
  	}
  	
  	
    if (BaseMin > BaseMax)
    {
    		alert("��С���ܴ��������");
    		return false;
  	} 
    
  	var JobCategory=fm.JobCategory.value;
  	var CaseCategory=fm.CaseCategory.value;
  	
  	if (JobCategory == null || JobCategory == '')
  	{
  			alert("Ȩ�޼�����Ϊ��");
    		return false;
  	}
  	
  	if (CaseCategory == null || CaseCategory == '')
  	{
  			alert("�������Ͳ���Ϊ��");
    		return false;
  	}
    return true;

}

/*[��������]�ύ����̨*/
function submitPopedomForm()
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

    fm.hideOperate.value=mOperate;
    fm.action = "./LLGrpClaimPopedomSave.jsp";
    document.getElementById("fm").submit(); //�ύ

}


/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterPopedomSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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

        InitQueryClick();//ˢ���б�����    
    }

}


/*[����]��ť��Ӧ����*/
function PopedomAddClick()
{
	if (beforePopedomSubmit())
	{	
		mOperate="Popedom||INSERT";
		submitPopedomForm();
	}
}
/*[�޸�]��ť��Ӧ����*/
function PopedomEditClick()
{
	if (beforePopedomSubmit)
	{
		fm.JobCategory.disabled=false;
    fm.JobCategoryName.disabled=false;
    fm.ClaimType.disabled=false;
    fm.StartDate.disabled=false;
    fm.MngCom.disabled=false;
    mOperate="Popedom||UPDATE";
    submitPopedomForm();
  }
}

/*[����]��ť��Ӧ����*/
function PopedomResetClick()
{
    //���¼����Ϣ
    fm.JobCategory.value = '';
    fm.JobCategoryName.value = '';
    fm.CaseCategory.value = '';
    fm.CaseCategoryName.value = '';
    fm.BaseMin.value = '';
    fm.BaseMax.value = '';
    fm.MngCom.value = '';
    fm.StartDate.value = '';
    fm.EndDate.value = '';
    fm.Operator.value = '';
    fm.MakeDate.value = '';
    fm.MakeTime.value = '';
    fm.ModifyDate.value = '';
	fm.ModifyTime.value = '';
	fm.MngComName.value='';
	
	fm.JobCategory.disabled=false;
	fm.MngCom.disabled=false;
	fm.StartDate.disabled=false;
	fm.JobCategoryName.disabled=false;
	
    fm.editPopedomButton.disabled = true;
    fm.savePopedomButton.disabled = false; //[����]��ť   
    fm.deletePopedomButton.disabled=true;
}


/*����Ȩ����ϢMulLine]�Ĵ�������*/
function getClaimPopedomGrid(spanID,parm)
{

    //�õ�MulLine������Ȩ����Ϣ
    var tNo = ClaimPopedomGrid.getSelNo();
    fm.JobCategory.disabled=true;
    fm.JobCategoryName.disabled=true;
    //fm.ClaimType.disabled=true;
    fm.StartDate.disabled=true;
    fm.MngCom.disabled=true;

    //��ֵ��Ϣ
    fm.JobCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,1);
    fm.CaseCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,2);
    fm.CaseCategoryName.value = ClaimPopedomGrid.getRowColData(tNo - 1,3);
    fm.JobCategoryName.value = ClaimPopedomGrid.getRowColData(tNo - 1,4);
    fm.ClaimType.value = ClaimPopedomGrid.getRowColData(tNo - 1,5);
    fm.ClaimCheckUwFlag.value = ClaimPopedomGrid.getRowColData(tNo - 1,6);
    fm.BaseMin.value = ClaimPopedomGrid.getRowColData(tNo - 1,7);
    fm.BaseMax.value = ClaimPopedomGrid.getRowColData(tNo - 1,8);
    fm.StartDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,9);
    fm.EndDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,10);
    fm.MngCom.value = ClaimPopedomGrid.getRowColData(tNo - 1,11);
    fm.Operator.value = ClaimPopedomGrid.getRowColData(tNo - 1,12);
    fm.MakeDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,13);
    fm.MakeTime.value = ClaimPopedomGrid.getRowColData(tNo - 1,14);
    fm.ModifyDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,15);
    fm.ModifyTime.value = ClaimPopedomGrid.getRowColData(tNo - 1,16);
    
    fm.OriJobCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,1);
    fm.OriCaseCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,2);
   	
    fm.editPopedomButton.disabled = false;
    fm.deletePopedomButton.disabled = false;
	fm.savePopedomButton.disabled =true;     
}

// ��ѯ��ť
function InitQueryClick()
{
	PopedomResetClick();//[����]��ť��Ӧ����
    
	var strSQL="select JobCategory,CaseCategory,(select codename from ldcode where codetype='grpcasecategory' and code=casecategory),(select codename from ldcode where codetype='grpjobcategory' and code=JobCategory),"
		         +" ClaimType,ClaimCheckUwFlag,BaseMin,BaseMax,StartDate,EndDate,MngCom,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime from LLGrpClaimPopedom where 1=1 "
				 + getWherePart( 'JobCategory','JobCategoryQ')
				 + getWherePart( 'CaseCategory','CaseCategoryQ')
				 + " order by JobCategory ";
	//prompt("����Ȩ�޲�ѯ",strSQL);
	turnPage.pageLineNum=5;
	turnPage.queryModal(strSQL, ClaimPopedomGrid);        
}

/*[ɾ��]��ť��Ӧ����*/
function PopedomDeleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
        mOperate="Popedom||DELETE"; 
        submitPopedomForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

