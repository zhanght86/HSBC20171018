var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//********************************************Beg
//�ÿ���ر�
//********************************************
function cleanFmElement(){
//��ʾ��

	//********************************************Beg
	//�ÿ���ر�
	//********************************************
	if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{
        	fm.ClmNo.value = "";
			fm.DutyFeeType.value = "";
			fm.DutyFeeCode.value = "";
			fm.DutyFeeName.value = "";
			fm.DutyFeeStaNo.value = "";
			fm.HosID.value = "";
			fm.HosName.value = "";
			fm.HosGrade.value = "";
			fm.StartDate.value = "";
			fm.EndDate.value = "";
			fm.DayCount.value = "";
			fm.OriSum.value = "";
        	fm.AdjSum.value = "";
			fm.AdjReason.value = "";
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark.value = "";

			fm.HosGradeName.value = "";
			fm.AdjReasonName.value = "";
    }
    else if (fm.OperationType.value == 'C')
    {
			fm.ClmNo.value = "";
			fm.DutyFeeType.value = "";
			fm.DutyFeeCode.value = "";
			fm.DutyFeeName.value = "";
			fm.DutyFeeStaNo.value = "";
			fm.DefoType.value = "";
			fm.DefoCode.value = "";
			fm.DefoeName.value = "";
			fm.DefoRate.value = "";
			fm.RealRate.value = "";
			fm.AdjReason1.value = "";
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark1.value = "";

			fm.AdjReasonName.value = "";
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {
        fm.ClmNo.value = "";
		fm.DutyFeeType.value = "";
		fm.DutyFeeCode.value = "";
		fm.DutyFeeName.value = "";
		fm.DutyFeeStaNo.value = "";
        fm.OriSum1.value = "";
		fm.AdjSum1.value = "";
		fm.AdjReason2.value = "";
//		fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.AdjRemark2.value = "";

		fm.AdjReasonName.value = "";
    }
}

/*ҽ�Ʒ��õ�����ʾ��Ϣ*/
function getMedFeeInHosInpGrid()
{
    cleanFmElement();
	
    var rptNo = fm.ClmNo2.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }

    //ȡ������
    
    //�õ�MulLine��
	var tNo = MedFeeInHosInpGrid.getSelNo();
    if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{

		    fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
			fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
			fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
			fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
			fm.HosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);
			fm.HosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
			fm.HosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,22);
			fm.StartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);
			fm.EndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,10);
			fm.DayCount.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11);
			fm.OriSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);
        	fm.AdjSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);
			fm.AdjReason.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);
			showOneCodeName('llmedfeeadjreason','AdjReason','AdjReasonName');//���������ԭ��
			showOneCodeName('llhosgrade','HosGrade','HosGradeName');//ҽԺ�ȼ�

    }
    else if (fm.OperationType.value == 'C')
    {
		
		    fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
			fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
			fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
			fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
			fm.DefoType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16);
			fm.DefoCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,17);
			fm.DefoeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18);
			fm.DefoRate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19);
			fm.RealRate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20);
			fm.AdjReason1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);
			showOneCodeName('llmedfeeadjreason','AdjReason1','AdjReasonName1');//���������ԭ��
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {
		fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
		fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
		fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
		fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
        fm.OriSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);
		fm.AdjSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);
		fm.AdjReason2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//		fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.AdjRemark2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		showOneCodeName('llmedfeeadjreason','AdjReason2','AdjReasonName2');//���������ԭ��
    }
    
}

//ҽ�Ʒ��õ�����ʾ��Ϣ
function queryGrid()
{
    initMedFeeInHosInpGrid();
	var tclaimNo = fm.ClmNo2.value;
	var dutyFeeType = null;
	if (fm.OperationType.value == 'A'){
		dutyFeeType = "A";
	}else if(fm.OperationType.value == 'B'){
		dutyFeeType = "B";
	}else if(fm.OperationType.value == 'C'){
		dutyFeeType = "C";
	}else if(fm.OperationType.value == 'F'){
		dutyFeeType = "F";
	}else if(fm.OperationType.value == 'D'){
		dutyFeeType = "D";
	}else if(fm.OperationType.value == 'E'){
		dutyFeeType = "E";
	}
	//alert("dutyFeeType"+dutyFeeType);
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15
    /*var strSql = " select distinct ClmNo,DutyFeeType,DutyFeeCode,DutyFeeName,DutyFeeStaNo,HosID,HosName,(select c.codename from ldcode c where c.codetype = 'lldutyadjreason' and trim(c.code)=trim(LLClaimDutyFee.HosGrade)),"
				+"StartDate,EndDate,DayCount,OriSum,AdjSum,(select c.codename from ldcode c where c.codetype = 'llmedfeeadjreason' and trim(c.code)=trim(LLClaimDutyFee.AdjReason)),AdjRemark,DefoType,DefoCode,DefoeName,DefoRate,"
				+"RealRate,AdjReason,HosGrade from LLClaimDutyFee where ClmNo = '" + tclaimNo + "' and DutyFeeType ='"+dutyFeeType+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLMedicalFeeAdjInputSql");
mySql.setSqlId("LLMedicalFeeAdjSql1");
mySql.addSubPara(tclaimNo); 
mySql.addSubPara(dutyFeeType); 
    //��ʾ��������
	//alert("strSql"+strSql);
    var arr = easyExecSql(mySql.getString());//alert("arr"+arr);
    if (arr)
    {
        displayMultiline(arr,MedFeeInHosInpGrid);
    }
}

//ѡ���˵�����
function choiseType()
{
    if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport1.style.display="";
    //    divLLSubReport1.disabled="false";
		divLLSubReport2.style.display="none";
    //   divLLSubReport2.disabled="ture";
		divLLSubReport3.style.display="none";
    //    divLLSubReport3.disabled="ture";
		cleanFmElement();
    }
    else if (fm.OperationType.value == 'C')
    {   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport2.style.display="";
   //     divLLSubReport2.disabled="false";
		divLLSubReport1.style.display="none";
   //     divLLSubReport1.disabled="ture";
		divLLSubReport3.style.display="none";
   //     divLLSubReport3.disabled="ture";
   		cleanFmElement();
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport3.style.display="";
   //     divLLSubReport3.disabled="false";
		divLLSubReport1.style.display="none";
   //     divLLSubReport1.disabled="ture";
		divLLSubReport2.style.display="none";
   //     divLLSubReport2.disabled="ture";
   		cleanFmElement();
    }
}

//����ļ���
function inputCheck()
{

	if(fm.ClmNo.value == ""){
		alert("��ѡ��һ����¼��");
		cleanFmElement();
	}

}

//��ʼ������
function initQuery()
{

	return;
}

//�����ύ��
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //�ύ
    tSaveFlag ="0";    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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

        //���²�ѯ
        queryGrid();
    }
    tSaveFlag ="0";
}

//���ذ�ť
function goToBack()
{
    top.close();
}


//���ذ�ť
function goToBack2()
{
    location.href="LLClaimAuditInput.jsp";
}

//�޸Ĳ���
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
        fm.action = './LLMedicalFeeAdjSave.jsp';
        submitForm();
    }
}

//�ύǰ��У�顢����
function beforeSubmit()
{
	if(fm.ClmNo.value != ""){
		if(fm.OperationType.value == 'A' || fm.OperationType.value == 'B'){
			if(fm.AdjSum.value==""){
				alert("��������Ϊ�գ�");
				return;
			}
			if(fm.AdjReason.value==""){
				alert("����ԭ����Ϊ�գ�");
				return;
			}
			if(fm.AdjRemark.value==""){
				alert("������ע����Ϊ�գ�");
				return;
			}
		}else if(fm.OperationType.value == 'C'){
			if(fm.RealRate.value==""){
				alert("ʵ�ʸ�����������Ϊ�գ�");
				return;
			}
			if(fm.AdjReason1.value==""){
				alert("����ԭ����Ϊ�գ�");
				return;
			}
			if(fm.AdjRemark1.value==""){
				alert("������ע����Ϊ�գ�");
				return;
			}
		}else if(fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E'){
			if(fm.AdjSum1.value==""){
				alert("��������Ϊ�գ�");
				return;
			}
			if(fm.AdjReason2.value==""){
				alert("����ԭ����Ϊ�գ�");
				return;
			}
			if(fm.AdjRemark2.value==""){
				alert("������ע����Ϊ�գ�");
				return;
			}
		}
	}else if(fm.ClmNo.value == ""){
		alert("��ѡ��һ����¼��");
		return;
	}
    return true;
}