var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//��ػ���
function MoreInqClick()
{
	if (fm.MoreInq.checked==true)
    {
    	fm.LocFlag.value = "1";//��ر�ʾ
        fm.InqOrg.disabled = false;
    }
    else
    {
    	fm.LocFlag.value = "0";//��ر�ʾ
        fm.InqOrg.value = document.all('ManageCom').value;    	
        showOneCodeName('stati','InqOrg','InqOrgName');    	
        fm.InqOrg.disabled=true;
    }	  	  
}

//�������κ�
function AddBatNoClick()
{
	lockScreen(divLLInqApply1);
	  //����������κ�
	  //tongmeng 2007-09-17 modify
	  //
    //var strSQL = "select nvl(max(to_number(BatNo)),0) from LLInqApply";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqApplyInputSql");
	mySql.setSqlId("LLInqApplySql1");
    var tMaxNo = easyExecSql(mySql.getString());
//    alert(tMaxNo[0][0]);
    if (tMaxNo == "" || tMaxNo == null) 
    {
    	  tMaxNo = Math.random()*1000;
    	  tMaxNo = parseInt(tMaxNo);
    }
    else
    {
    	  //tMaxNo = parseInt(tMaxNo[0][0]) + parseInt(Math.random()*10) + 1;
    		tMaxNo = parseInt(tMaxNo[0][0]) + 1;
    }
//    alert("tMaxNo="+tMaxNo);
    fm.BatNo.value = tMaxNo;//���κ�
    fm.AddBatNo.disabled = true;
    fm.InqOrg.value = document.all('ManageCom').value; //�������
    showOneCodeName('stati','InqOrg','InqOrgName');
    unlockScreen(divLLInqApply1);
}

//���水ť
function saveClick()
{
  //���Ƚ��зǿ��ֶμ���   
    if (fm.BatNo.value == "")
    {
        alert("���κ�Ϊ�գ�");
        return;
    }
    else
    {
        //����
        /*var strSql1 = "select FiniFlag from LLInqConclusion where "
                   + "clmno = '" + document.all('ClmNo').value + "' and batno='"+fm.BatNo.value+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqApplyInputSql");
		mySql.setSqlId("LLInqApplySql2");
		mySql.addSubPara(document.all('ClmNo').value ); 
		mySql.addSubPara(fm.BatNo.value ); 
        var tFiniFlag = easyExecSql(mySql.getString());
        
        if (tFiniFlag)
        {
            for (var i = 0; i < tFiniFlag.length; i++)
            {
                if (tFiniFlag[i] != '1')
                {
                    alert("����ĵ���û�����,�����ظ��������!");
                    return false;
                }
            }
        }
    }
    
    //�ⰸ��
    if (fm.ClmNo.value == "")
    {
        alert("ȡ�õ��ⰸ��Ϊ�գ�");
        return;    	  
    }
    if ( document.all('ManageCom').value == "" )
    {
        alert("ȡ�õĻ�������Ϊ�գ�");
        return;
    }
    //******************************************************
    //������Ҫ�ύ�ֶ�
    //******************************************************
    if(fm.InqReason.value=="" || fm.InqReason.value ==null )
	{
		alert("����д����ԭ��");
		return;
	}
    
    
    if(fm.InqItem.value=="" || fm.InqItem.value ==null )
	{
		alert("����д������Ŀ");
		return;
	}
    
    
    if(fm.InqDesc.value=="" || fm.InqDesc.value ==null )
	{
		alert("����д��������");
		return;
	}
    
    
    //�������
    if ((fm.InqOrg.value == "" || fm.InqOrg.value == null) &&��document.all('ManageCom').value != "")
    {
        fm.InqOrg.value = document.all('ManageCom').value;
        fm.InqOrg2.value = document.all('ManageCom').value;//���ݵ����������
    }
    //���ر�־
    if (fm.MoreInq.checked == false) 
    {
        fm.LocFlag.value = "0";
        fm.InqOrg2.value = document.all('ManageCom').value;//���ݵ����������
    }
    else
    {
        fm.LocFlag.value = "1";
        fm.InqOrg2.value=fm.InqOrg.value;
    }
    //���ѡ��ĵ�������Ƿ��е���Ȩ��
    var strRet=fm.InqOrg2.value;
    var len=strRet.length;
//    alert("����������룺"+len);
    if(len>=8)
    {
    	alert("�û���û�е���Ȩ�ޣ�����Ϊ�û��������������");
    	return;
    } 
//  ��׼�����̨�ύ���ݼ���������
  	fm.saveAdd.disabled=true;
  	lockScreen(divLLInqApply1);
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './LLInqApplySave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
		unlockScreen(divLLInqApply1);
    showInfo.close();
    if (FlagStr == "Fail" )
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
        //�ÿտɱ༭����
        fm.InqItem.value = "";
        fm.InqReason.value = "";
        fm.InqReasonName.value = "";
        fm.InqDesc.value = "";
        fm.MoreInq.checked = false;
    	fm.LocFlag.value = "0";
        fm.InqOrg.value = document.all('ManageCom').value;
        fm.InqOrg2.value = document.all('ManageCom').value;
        showOneCodeName('stati','InqOrg','InqOrgName');    	
        fm.InqOrg.disabled=true;
        initLLInqApplyGridQuery();
    }
    tSaveFlag ="0";
}

//����Mulline-----��ѯ�Ѿ�����ĵ���
function initLLInqApplyGridQuery()
{
     //����ɹ��󷵻�
     /*var strSQL = "select clmno,batno,inqno,customerno,vipflag,initdept,inqrcode,inqitem,inqdesc,inqdept "
     			 +" ,(case locflag when '0' then '����' when '1' then '���' end )"
     			 +" ,(case inqstate when '0' then 'δ���' when '1' then '�����' end )"
     			 +"	from llinqapply where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo')
                 +" order by clmno,batno";*/
    	mySql = new SqlClass();
		mySql.setResourceName("claim.LLInqApplyInputSql");
		mySql.setSqlId("LLInqApplySql3");
		mySql.addSubPara(fm.ClmNo.value ); 
     turnPage.queryModal(mySql.getString(), LLInqApplyGrid);
}
