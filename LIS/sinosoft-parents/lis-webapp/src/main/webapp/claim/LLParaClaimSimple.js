var showInfo;
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�


// ��ѯ��ť
function InitQueryClick()
{
	if(fm.ComCodeQ.value==null||fm.ComCodeQ.value=='')
	{
	      alert("����ѡ��������!");
	      return false;
	}
	
//	var strSQL="select * from LLParaClaimSimple where 1=1 "
//		+ " and ComCode like '"+fm.ComCodeQ.value+"%'"
//		+ " order by ComCode ";
	//alert(strSQL);
	sql_id = "LLParaClaimSimpleSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaClaimSimpleSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(fm.ComCodeQ.value);//ָ������Ĳ���
	str_sql = my_sql.getString();
	turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLParaClaimSimpleGrid);        
}


/*�ύ��̨ǰ��ҳ������У��*/
function beforeSimpleSubmit()
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
    if (BaseMin > BaseMax)
    {
    		alert("��С���ܴ��������");
    		return false;
  	} 
  	var ComCode=fm.ComCode.value;
  	var ComCodeName=fm.ComCodeName.value;
  	if (ComCode == null || ComCode == '')
  	{
  			alert("�������벻��Ϊ��");
    		return false;
  	}
  	if (ComCodeName == null || ComCodeName == '')
  	{
  			alert("�������Ʋ���Ϊ��");
    		return false;
  	}
    return true;

}


 /*[����]��ť��Ӧ����*/
function SimpleAddClick()
{

	if (beforeSimpleSubmit())
	{
    mOperate="Simple||INSERT";
    submitSimpleForm();
    SimpleResetClick();//�������
  }
}

/*[�޸�]��ť��Ӧ����*/
function SimpleEditClick()
{
	if (beforeSimpleSubmit)
	{
    fm.ComCode.disabled=false;	
    fm.ComCodeName.disabled=false;
    fm.UpComCode.disabled=false;
    fm.StartDate.disabled=false;
    mOperate="Simple||UPDATE";
    submitSimpleForm();
    SimpleResetClick();//�������
  }
}

/*[ɾ��]��ť��Ӧ����*/
function SimpleDeleteClick()
{
	if (beforeSimpleSubmit()){
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
        fm.ComCode.disabled=false;	
        fm.ComCodeName.disabled=false;
        fm.UpComCode.disabled=false;
        fm.StartDate.disabled=false;
        mOperate="Simple||DELETE";
        submitSimpleForm();
        SimpleResetClick();//�������
        }
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[����]��ť��Ӧ����*/
function SimpleResetClick()
{
    //���¼����Ϣ
    fm.ComCode.value = '';
    fm.ComCodeName.value = '';
    fm.UpComCode.value = '';
    fm.BaseMin.value = '';
    fm.BaseMax.value = '';
    fm.StartDate.value = '';
    fm.EndDate.value = '';
    fm.Operator.value = '';
    fm.MakeDate.value = '';
    fm.MakeTime.value = '';
    fm.ModifyDate.value = '';
    fm.ModifyTime.value = '';
    fm.UpComCodeName.value='';
    
    fm.ComCode.disabled=false;
		fm.UpComCode.disabled=false;
    fm.editSimpleButton.disabled = true;
    fm.saveSimpleButton.disabled = false; //[����]��ť   
}

/*�򵥰�����׼MulLine�Ĵ�������*/
function LLParaClaimSimpleGridClick()
{

    //�õ�MulLine�ļ򵥰�����׼
    
    fm.ComCode.disabled=true;
    //fm.ComCodeName.disabled=true;
    fm.UpComCode.disabled=true;
    fm.StartDate.disabled=true;
    var tNo = LLParaClaimSimpleGrid.getSelNo();

    //��ֵ��Ϣ
    fm.ComCode.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,1);
    fm.ComCodeName.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,2);
    fm.UpComCode.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,3);
    fm.BaseMin.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,4);
    fm.BaseMax.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,5);
    fm.StartDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,6);
    fm.EndDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,7);
    fm.Operator.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,8);
    fm.MakeDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,9);
    fm.MakeTime.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,10);
    fm.ModifyDate.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,11);
    fm.ModifyTime.value = LLParaClaimSimpleGrid.getRowColData(tNo - 1,12);
   	
    fm.editSimpleButton.disabled = false;
    fm.deleteSimpleButton.disabled =false;
    fm.saveSimpleButton.disabled =true;     
}

/*[��������]�ύ����̨*/
function submitSimpleForm()
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
    fm.action = "./LLParaClaimSimpleSave.jsp";
    document.getElementById("fm").submit(); //�ύ

}


/*[��������]�������,���������ݷ��غ�ִ�еĲ���*/
function afterSimpleSubmit( FlagStr, content )
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