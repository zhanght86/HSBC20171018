var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�

// ���ҳ���ϵ�����
function ClearPagedata()
{
	fm.ConsultNo.value="";
    fm.HospitalCode.value ="";
    fm.HospitalName.value ="";
    fm.HosAtti.value = "";
    fm.ConFlag.value ="";
    fm.AppFlag.value ="";
    fm.HosState.value = "";  	
    fm.HosAttiName.value = "";
    fm.ConFlagName.value ="";
    fm.AppFlagName.value ="";
    fm.HosStateName.value = "";  
    fm.HospitalCode.disabled= false; //[ҽԺ����]
}

// ��ѯ��ť
function InitQueryClick()
{
    ClearPagedata();//���ҳ����ؼ����ݣ�ҽԺ��Ϣ��
    HospitalResetClick();//[����]��ť��Ӧ����
    
	if(fm.MngComQ.value==null||fm.MngComQ.value=='')
	{
	      alert("����ѡ��������!");
	      return false;
	}
	

    sql_id = "LLCommendHospitalSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLCommendHospital"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(HospitalCodeQ);//ָ������Ĳ���
	my_sql.addSubPara(HospitalNameQ);//ָ������Ĳ���
	my_sql.addSubPara(HosAttiQ);//ָ������Ĳ���
	my_sql.addSubPara(ConFlagQ);//ָ������Ĳ���
	my_sql.addSubPara(HosStateQ);//ָ������Ĳ���
	my_sql.addSubPara(fm.MngComQ.value);//ָ������Ĳ���
	str_sql = my_sql.getString();
//	var strSQL="select ConsultNo,HospitalCode,HospitalName,HosAtti,ConFlag,AppFlag,HosState,MngCom from LLCommendHospital where 1=1 "
//				 + getWherePart( 'HospitalCode','HospitalCodeQ')
//				 + getWherePart( 'HospitalName','HospitalNameQ')
//				 + getWherePart( 'HosAtti','HosAttiQ')
//				 + getWherePart( 'ConFlag','ConFlagQ')
//				 + getWherePart( 'HosState','HosStateQ')
//				 + " and MngCom like '"+fm.MngComQ.value+"%'"
//				 + " order by MngCom,HospitalCode";
	   
	//alert(strSQL);
    turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLCommendHospitalGrid);        
}

/*ҽԺ��Ϣ�б�LLCommendHospitalGrid���Ĵ�������*/
function LLCommendHospitalGridClick()
{
    ClearPagedata();
    fm.editHospitalButton.disabled =false;
    fm.deleteHospitalButton.disabled =false;  
    fm.saveHospitalButton.disabled =true;     
    fm.HospitalCode.disabled=true; //��ʱ[ҽԺ����]�����޸�---�����޸� �� ɾ�� 
    var tNo = LLCommendHospitalGrid.getSelNo()-1;	
    fm.ConsultNo.value =LLCommendHospitalGrid.getRowColData(tNo,1);   
    fm.HospitalCode.value =LLCommendHospitalGrid.getRowColData(tNo,2);
    fm.HospitalName.value =LLCommendHospitalGrid.getRowColData(tNo,3);
    fm.HosAtti.value =LLCommendHospitalGrid.getRowColData(tNo,4);
    fm.ConFlag.value =LLCommendHospitalGrid.getRowColData(tNo,5);
    fm.AppFlag.value =LLCommendHospitalGrid.getRowColData(tNo,6);
    fm.HosState.value =LLCommendHospitalGrid.getRowColData(tNo,7);  
    showOneCodeName('llhosgrade','HosAtti','HosAttiName');  
    showOneCodeName('ConFlag','ConFlag','ConFlagName');
    showOneCodeName('appflag','AppFlag','AppFlagName');    
    showOneCodeName('hosstate','HosState','HosStateName');	 
}


/*[����]��ť��Ӧ����*/
function HospitalAddClick()
{
	if(trim(fm.HospitalCode.value)==""||trim(fm.HospitalName.value) ==""||fm.HosState.value =="")
	{
		alert("������д����������������д��");
		return;
	}
//	alert("trim(fm.HospitalCode.value).length="+trim(fm.HospitalCode.value).length);
//	alert("trim(fm.HospitalName.value).length="+trim(fm.HospitalName.value).length);
//	if(trim(fm.HospitalCode.value).length>10||trim(fm.HospitalName.value).length>60)
//	{
//		alert("ҽԺ���볬��10λ�����Ƴ���60λ����������д��");
//		return;
//	}
//    var strSQL = "select HospitalCode from LLCommendHospital where HospitalCode='"+trim(fm.HospitalCode.value)+"'";
    sql_id = "LLCommendHospitalSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLCommendHospital"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(trim(fm.HospitalCode.value));//ָ������Ĳ���
	str_sql = my_sql.getString();
    var arr = easyExecSql(str_sql);
//    alert(arr);
    if (arr== null) 
    {
//		alert("��ҽԺ�������ʹ�ã�");
    }
	else
	{
		alert("��ҽԺ�����Ѿ����ڣ�");
		return;
	}
	fm.ConsultNo.value=fm.HospitalCode.value;//��ѯ֪ͨ����
    fm.fmtransact.value="Hospital||INSERT";
    submitHospitalForm();
}
/*[�޸�]��ť��Ӧ����*/
function HospitalEditClick()
{
	if(trim(fm.HospitalCode.value)==""||trim(fm.HospitalName.value) ==""||fm.HosState.value =="")
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
    	fm.HospitalCode.disabled= false; //[ҽԺ����]---�����޷�ȡ������
	    fm.fmtransact.value="Hospital||UPDATE";
	    submitHospitalForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[ɾ��]��ť��Ӧ����*/
function HospitalDeleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
    	fm.HospitalCode.disabled= false; //[ҽԺ����]---�����޷�ȡ������
        fm.fmtransact.value="Hospital||DELETE";
        submitHospitalForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[����]��ť��Ӧ����*/
function HospitalResetClick()
{
	ClearPagedata();
    fm.editHospitalButton.disabled = true;//[�޸�]��ť
    fm.deleteHospitalButton.disabled = true;//[ɾ��]��ť
    fm.saveHospitalButton.disabled = false; //[����]��ť   
}


/*[��������]�ύ����̨*/
function submitHospitalForm()
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
    fm.action = "./LLCommendHospitalSave.jsp";
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
