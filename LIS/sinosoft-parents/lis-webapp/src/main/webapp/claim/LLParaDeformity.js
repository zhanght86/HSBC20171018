var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
//2008-10-17 zhangzheng ��������������Ӧ����:����������ѡ��ΪLF:�˲�,LZ:����ʱ����Ҫѡ��˫����������ѡ�������������������Ҫѡ��
function respondDefoGrade()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
        alert('����ѡ�������������!');
        return false;
	}
	//LZ;����,LF:�˲���������
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
	    return showCodeList('lldefograde',[fm.DefoGrade,fm.DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');
	}
	else
	{
		alert("������������:"+fm.DefoType.value+"-"+fm.DefoTypeName.value+"����ѡ�������������,��ֱ��ѡ�������������!");
		return false;
	}
}

//2008-10-17 zhangzheng ��������������Ӧ����:����������ѡ��ΪLF:�˲�,LZ:����ʱ����Ҫѡ��˫����������ѡ�����������������ֱ��ѡ�����ı�����������
function respondDefoCode()
{
	if (fm.DefoType.value == null || trim(fm.DefoType.value) ==''){
		
        alert('����ѡ�������������!');
        return false;
	}
	//LZ;����,LF:�˲���������
	else if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF')
	{
		if(fm.DefoGrade.value == null || trim(fm.DefoGrade.value) ==''){
			
			alert('����ѡ�������������!');
	        return false;
		}
		else
		{
			//ͬʱ���������������ͺͱ������������ѯ
			return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');
		}
	}
	//��������
	else
	{
		//ֻ���������������Ͳ�ѯ,������������Ĭ��Ϊ1
		return showCodeList('lldefocode',[fm.DefoCode,fm.DefoCodeName],[0,1],null,fm.DefoType.value,'1','1','300');
	}
}


//2008-10-17 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//������������
    if(cCodeName=="lldefotype"){
    	if(fm.DefoType.value=='LZ'||fm.DefoType.value=='LF'){
    		//�������������Ͳ���LZ;����,LF:�˲���������ʱ����������������Ҫ¼�룬��Ҫ���
    		return true;
    	}
    	else{
    		//������������Ҫ�����������������
    		fm.DefoGrade.value='';
    		fm.DefoGradeName.value='';
    	}
        return true;
    }
}

/*[��������]�ύ����̨*/
function submitDeformityForm()
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

    fm.hideOperate.value=mOperate;
    fm.action = "./LLParaDeformitySave.jsp";
    document.getElementById("fm").submit(); //�ύ

}


/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterDeformitySubmit( FlagStr, content )
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
        mOperate = '';
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

        if ( mOperate=="Deformity||INSERT"){
            DeformityAddAfterClick();
        }
        if ( mOperate=="Deformity||DELETE"){
            DeformityDeleteAfterClick();
        }
        if ( mOperate=="Deformity||UPDATE"){
            DeformityEditAfterClick();
        }
    }

}


/*[����]��ť��Ӧ����*/
function DeformityAddClick()
{
	if (beforeDeformitySubmit())
	{
    mOperate="Deformity||INSERT";
    submitDeformityForm();
  }
}
/*[�޸�]��ť��Ӧ����*/
function DeformityEditClick()
{
	if (beforeDeformitySubmit())
	{
    mOperate="Deformity||UPDATE";
    submitDeformityForm();
	}
}
/*[ɾ��]��ť��Ӧ����*/
function DeformityDeleteClick()
{
    if (confirm("��ȷʵ��ɾ���ü�¼��?")){
        mOperate="Deformity||DELETE";
        submitDeformityForm();
    }else{
        mOperate="";
    }
}
/*[����]��ť��Ӧ����*/
function DeformityResetClick()
{
    //���¼����Ϣ
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';
    
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}


/*[����]��ť��̨�ɹ�ִ�к��Ӧ�Ĳ���*/
function DeformityAddAfterClick()
{
    /*//��Ҫ���سɹ���־�󣬲Ž������²���
    var tNo = ParaDeformityGrid.mulLineCount;
    if (tNo<=0)
    {
        tNo = 0;
    }

    ParaDeformityGrid.addOne();
    ParaDeformityGrid.setRowColData(tNo, 1,fm.DefoType.value);           	//�˲�����
    ParaDeformityGrid.setRowColData(tNo, 2,fm.DefoGrade.value);						//�˲м���
    ParaDeformityGrid.setRowColData(tNo, 3,fm.DefoCode.value);						//�˲д���
    ParaDeformityGrid.setRowColData(tNo, 4,fm.DefoName.value);						//�˲м�������
    ParaDeformityGrid.setRowColData(tNo, 5,fm.DefoRate.value);						//�м���������*/
    
    var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	str_sql = my_sql.getString();
    
  
    //��ʾ��������
    turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(str_sql,ParaDeformityGrid);
  

    //��ս���¼����Ϣ
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';

    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
}

/*[ɾ��]��ť��̨�ɹ�ִ�к��Ӧ�Ĳ���*/
function DeformityDeleteAfterClick()
{
        //��Ҫ���سɹ���־�󣬲Ž������²���
        //ParaDeformityGrid.delRadioTrueLine();
        var strSql="";
//    		strSql = "select * from LLParaDeformity order by DefoType,DefoCode" ;
    		sql_id = "LLParaDeformitySql0";
    		my_sql = new SqlClass();
    		my_sql.setResourceName("claim.LLParaDeformitySql"); //ָ��ʹ�õ�properties�ļ���
    		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    		str_sql = my_sql.getString();
    		//��ʾ��������
    		turnPage.pageLineNum=10;    //ÿҳ10��
    		turnPage.queryModal(str_sql,ParaDeformityGrid);
    
        //���¼����Ϣ
		    fm.DefoType.value = '';
		    fm.DefoTypeName.value = '';
		    fm.DefoGrade.value = '';
		    fm.DefoGradeName.value = '';
		    fm.DefoCode.value = '';
		    fm.DefoCodeName.value = '';
		    fm.DefoRate.value = '';
}

/*[�޸�]��ť��̨�ɹ�ִ�к��Ӧ�Ĳ���*/
function DeformityEditAfterClick(){
    var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	str_sql = my_sql.getString();
    //��ʾ��������
    turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(str_sql,ParaDeformityGrid);
  
    //��ս���¼����Ϣ
    fm.DefoType.value = '';
    fm.DefoTypeName.value = '';
    fm.DefoGrade.value = '';
    fm.DefoGradeName.value = '';
    fm.DefoCode.value = '';
    fm.DefoCodeName.value = '';
    fm.DefoRate.value = '';
    
}


/*����Ȩ����ϢMulLine]�Ĵ�������*/
function getParaDeformityGrid(spanID,parm)
{

    //�õ�MulLine������Ȩ����Ϣ
    var tNo = ParaDeformityGrid.getSelNo();

    //��ֵ��Ϣ
    fm.DefoType.value = ParaDeformityGrid.getRowColData(tNo - 1,1);
    fm.DefoGrade.value = ParaDeformityGrid.getRowColData(tNo - 1,2);
    fm.DefoGradeName.value = ParaDeformityGrid.getRowColData(tNo - 1,3);
    fm.DefoCode.value = ParaDeformityGrid.getRowColData(tNo - 1,4);
    fm.DefoCodeName.value = ParaDeformityGrid.getRowColData(tNo - 1,5);
    fm.DefoRate.value = ParaDeformityGrid.getRowColData(tNo - 1,6);
  
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = false;

}

//����Ȩ����Ϣ��ѯ
function queryParaDeformity()
{
  	var strSql="";
//    strSql = " select * from LLParaDeformity order by DefoType,DefoCode" ;
    sql_id = "LLParaDeformitySql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaDeformitySql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	str_sql = my_sql.getString();
    //��ʾ��������
    turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(str_sql,ParaDeformityGrid);
}

/*�ύ��̨ǰ��ҳ������У��*/
function beforeDeformitySubmit()
{
		var strDefoType = fm.DefoType.value;//�˲�����
		var strDefoCode = fm.DefoCode.value;//�˲д���
	
    
    if (strDefoType == null || strDefoType == '')
    {
        alert("�˲����Ͳ���Ϊ�գ�");
        return false;
    }
    if (strDefoCode == null || strDefoCode == '')
    {
        alert("�˲д��벻��Ϊ�գ�");
        return false;
    }
    return true;
}




