var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
// ���ҳ���ϵ�����
function ClearPagedata()
{
    fm.ComCode.value ="";
    fm.ComCodeName.value ="";
    fm.UpComCode.value = "";
    fm.BaseValue.value ="";
    fm.StartDate.value ="";
    fm.EndDate.value = "";  	 
    fm.ComCode.disabled= false; //[�����������]
}

// ��ѯ��ť
function InitQueryClick()
{
	
    ClearPagedata();//���ҳ����ؼ����ݣ�δ�����˱����׼��Ϣ��
   /* BaseValueResetClick();//[����]��ť��Ӧ����*/
//	var strSQL="select ComCode,ComCodeName,UpComCode,BaseValue,StartDate,EndDate from LLParaPupilAmnt where 1=1 "
//				 + getWherePart( 'ComCode','ComCodeQ')
//				 + " order by ComCode desc";
	sql_id = "LLParaPupilAmntSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaPupilAmntSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(document.getElementsByName("ComCodeQ")[0].value);//ָ������Ĳ���
	str_sql = my_sql.getString();
	
//	alert(strSQL);
    turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLParaPupilAmntGrid);        
}

/*δ�����˱����׼��Ϣ�б�LLParaPupilAmntGrid���Ĵ�������*/
function LLParaPupilAmntGridClick()
{
     // ClearPagedata();
//    fm.editBaseValueButton.disabled =false;
//    fm.deleteBaseValueButton.disabled =false;  
//    fm.saveBaseValueButton.disabled =true;     
//    fm.ComCode.disabled=true; //��ʱ[�����������]�����޸�---�����޸� �� ɾ�� 
    ClearPagedata();
    document.getElementById("editBaseValueButton").disabled =false;
    document.getElementById("deleteBaseValueButton").disabled =false;  
    document.getElementById("saveBaseValueButton").disabled =true;     
    fm.ComCode.disabled=true; //��ʱ[�����������]�����޸�---�����޸� �� ɾ�� 
    var tNo = LLParaPupilAmntGrid.getSelNo()-1;	
    fm.ComCode.value =LLParaPupilAmntGrid.getRowColData(tNo,1);   
    fm.ComCodeName.value =LLParaPupilAmntGrid.getRowColData(tNo,2);
    fm.UpComCode.value =LLParaPupilAmntGrid.getRowColData(tNo,3);
    fm.BaseValue.value =LLParaPupilAmntGrid.getRowColData(tNo,4);
    fm.StartDate.value =LLParaPupilAmntGrid.getRowColData(tNo,5);
    fm.EndDate.value =LLParaPupilAmntGrid.getRowColData(tNo,6);
  
   // showOneCodeName('llhosgrade','HosAtti','HosAttiName');  
   // showOneCodeName('ConFlag','ConFlag','ConFlagName');
   // showOneCodeName('appflag','AppFlag','AppFlagName');    
   // showOneCodeName('hosstate','HosState','HosStateName');	 
}


/*[����]��ť��Ӧ����*/
function BaseValueAddClick()
{
	if(trim(fm.ComCode.value)==""||trim(fm.StartDate.value) ==""||fm.BaseValue.value =="")
	{
		alert("������д����������������д��");
		return;
	}
//    var strSQL = "select ComCode from LLParaPupilAmnt where ComCode='"+trim(fm.ComCode.value)+"'and StartDate='"+trim(fm.StartDate.value)+"'" ;
//    
    sql_id = "LLParaPupilAmntSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaPupilAmntSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(trim(fm.ComCode.value));//ָ������Ĳ���
	my_sql.addSubPara(trim(fm.StartDate.value));
	str_sql = my_sql.getString();
    var arr = easyExecSql(str_sql);
//    alert(arr);
    if (!arr) 
    {
    }
	else
	{
		alert("�ù������δ�����˱����׼�Ѿ����ڣ�");
		return;
	}
	fm.ConsultNo.value=fm.ComCode.value;//��ѯ֪ͨ����
    fm.fmtransact.value="BaseValue||INSERT";
    submitBaseValueForm();
}
/*[�޸�]��ť��Ӧ����*/
function BaseValueEditClick()
{
	if(trim(fm.ComCode.value)==""||trim(fm.StartDate.value) ==""||fm.BaseValue.value =="")
	{
		alert("������д����������������д��");
		return;
	}
//	alert("trim(fm.HospitalCode.value).length="+trim(fm.HospitalCode.value).length);
//	alert("trim(fm.HospitalName.value).length="+trim(fm.HospitalName.value).length);
//	if(trim(fm.HospitalName.value).length>60)
//	{
//		alert("ҽԺ���Ƴ��ȹ�������������д��");
//		return;
//	}
    if (confirm("��ȷʵ���޸ĸü�¼��?"))
    {
    	fm.ComCode.disabled= false; //[�����������]---�����޷�ȡ������
	    fm.fmtransact.value="BaseValue||UPDATE";
	    submitBaseValueForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[ɾ��]��ť��Ӧ����*/
function BaseValueDeleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
    	fm.ComCode.disabled= false; //[�����������]---�����޷�ȡ������
        fm.fmtransact.value="BaseValue||DELETE";
        submitBaseValueForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[����]��ť��Ӧ����*/
function BaseValueResetClick()
{
	ClearPagedata();
	document.getElementById("editBaseValueButton").disabled= true;
	document.getElementById("deleteBaseValueButton").disabled= true;
	document.getElementById("saveBaseValueButton").disabled= false;
//    fm.editBaseValueButton.disabled = true;//[�޸�]��ť
//    fm.deleteBaseValueButton.disabled = true;//[ɾ��]��ť
//    fm.saveBaseValueButton.disabled = false; //[����]��ť   
}


/*[��������]�ύ����̨*/
function submitBaseValueForm()
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
    fm.action = "./LLParaPupilAmntSave.jsp";
    document.getElementById("fm").submit(); //�ύ

}


/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterHospitalSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
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
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
