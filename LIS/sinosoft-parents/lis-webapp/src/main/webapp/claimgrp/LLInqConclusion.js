var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
//��ʼ����ѯ
function initializeQuery()
{
    var strSQL = "";	
	if(document.all('ClmNo').value=="" ||document.all('ConNo').value=="")
	{
		document.all('saveAdd').disabled="true";
		alert("���ݴ��ʹ���!");
	    return;
	}
    strSQL = "select clmno,conno,batno,initdept,inqdept,locflag,colflag,finiflag from llinqconclusion"
           +" where ClmNO='"+document.all('ClmNo').value+"'"
           +" and ConNo='"+document.all('ConNo').value+"'"; 
    var arr = easyExecSql(strSQL);
    if (arr == null)
    {
	      alert("�������벻����!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //�ⰸ��
        fm.ConNo.value=arr[0][1];  //�������
        fm.BatNo.value=arr[0][2]; //���κ�
        fm.InitDept.value=arr[0][3];  //�������
        fm.InqDept.value=arr[0][4];   //������� 
        fm.LocFlag.value=arr[0][5];   //���ر�־
        fm.ColFlag.value=arr[0][6];   //���ܱ�־ 
        fm.FiniFlag.value=arr[0][7];   //   ��ɱ�־          
       showOneCodeName('stati','InitDept','InitDeptName');          
       showOneCodeName('stati','InqDept','InqDeptName');    
    }	
     //��ѯ����������Ϣ,���mulline
    var strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,(case initphase when '01' then '�����׶�' when '02' then '��˽׶�' when '03' then '�ʱ��׶�' end) ,inqrcode,inqitem,inqconclusion ,inqstartdate,inqenddate from llinqapply where "
                + " clmno = '" +fm.ClmNo.value +"'"
                + " and batno = '" + fm.BatNo.value +"'"
                + " and inqdept = '" + fm.InqDept.value +"'"
                + " order by clmno";
      turnPage.queryModal(strSQL, LLInqApplyGrid);  		
}

//ѡ��LLInqApplyGrid��Ӧ�¼�
function LLInqApplyGridClick()
{
    var i = LLInqApplyGrid.getSelNo()-1;
    fm.InqReason.value = LLInqApplyGrid.getRowColData(i,8);
	fm.InqItem.value = LLInqApplyGrid.getRowColData(i,9);
	fm.InqConclusion1.value = LLInqApplyGrid.getRowColData(i,10);
	showOneCodeName('llinqreason','InqReason','InqReasonName');//
}


//���ذ�ť
function goBack()
{
    var strUrl= "LLInqConclusionMissInput.jsp?";    
    location.href=strUrl;
}

//���水ť
function saveClick()
{
	//���Ƚ��зǿ��ֶμ���   
    if (fm.InqConclusion.value == "" || fm.InqConclusion.value == null)
    {
        alert("����д����������ۣ�");
        return;
    }

  ��//�ύ
    fm.fmtransact.value ="";
    submitForm();
}



//�ύ����
function submitForm()
{
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

    fm.action = './LLInqConclusionSave.jsp';
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

        //���Ŀɲ�����ť
        fm.saveAdd.disabled = true;
        location.href="LLInqConclusionMissInput.jsp?";
    }
    tSaveFlag ="0";
}



//�鿴��������-------�鿴�û����������µ����е�������-------2005-08-15���
function queryInqApply()
{   
//	alert();
	document.all('divQueryInqApply').style.display="";
}


//���ص�������-------���ظû����������µ����е�������-------2005-08-15���
function hideInqApply()
{   
	document.all('divQueryInqApply').style.display="none";
}

function queryInqInfo()
{
	var strUrl="LLInqConclusionQueryMain.jsp?ClaimNo="+fm.ClmNo.value+"&BatNo="+fm.BatNo.value+"&InqDept="+fm.InqDept.value+"&Type=2";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�鿴����");
}

function LoadAffixClick()
{
      var tClmNo=document.all("ClmNo").value;
      var urlStr;
      urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
      window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   	
  	
}
