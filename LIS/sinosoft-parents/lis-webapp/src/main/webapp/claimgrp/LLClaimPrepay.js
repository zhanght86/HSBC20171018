//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sel="";

//�����򿪻�رա�DIV����
function showMyPage(spanID,flag)
{
  if(!flag)//�ر�
  { 
    spanID.style.display="none";
  }
  else    //��
  {
    spanID.style.display="";
  }
}

//ҳ���ʼ��,�������κ�<Ĭ��Ϊ10λ>,
function initApplyPrepayNo()
{
//	//����������κ�,������������������κżӡ�10����<��һ����Ϊ--1000000000>
//    var strSQL = "select max(prepayno) from llprepaydetail";// order by PrepayNo DESC";
//    var arr = easyExecSql(strSQL);
////    alert(arr);
//    if (arr == "" || arr == null) 
//    {
//    	  tMaxNo = 1000000000; 	  
//    	  tMaxNo = parseInt(tMaxNo)+10;
//    }
//    else
//    {
//    	  tMaxNo = parseInt(arr[0][0])+10;
//    }	
//	tMaxNo = parseInt(tMaxNo);
//	fm.PrepayNo.value = "";
	fm.PrepayNo.value ="0";
}

//[��ѯԤ���ڵ���Ϣ]��Ϊ���� ��˽ڵ� ׼���ṩ����
function initLLClaimPrepayNodeQuery()
{
//    ��ѯ��Ԥ���ڵ㡱���ݣ�Ϊ���ɴ���˽ڵ� ׼������
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop12,missionprop19,missionprop20,missionprop18,missionid,submissionid,activityid from lwmission where 1=1 "
           + getWherePart( 'missionid','tMissionID' )
		   + getWherePart( 'submissionid','tSubMissionID' )
           + getWherePart( 'activityid','tActivityID' ); 
//    alert(strSQL);     
    var arr = new Array();    
    arr=easyExecSql(strSQL);  
    if(arr==null)
    {
       alert("���ݲ�ѯ����");   
       return;  	
    }
	else
	{
		fm.tRptNo.value=arr[0][0];
		fm.tRptorState.value=arr[0][1];
		fm.tCustomerNo.value=arr[0][2];
		fm.tCustomerName.value=arr[0][3];
		fm.tCustomerSex.value=arr[0][4];
		fm.tAccDate.value=arr[0][5];
		fm.tRgtClass.value=arr[0][6];
		fm.tRgtObj.value=arr[0][7]; 
		fm.tRgtObjNo.value=arr[0][8]; 
		fm.tPopedom.value=arr[0][9]; 
		fm.tPrepayFlag.value="1";
		fm.tComeWhere.value=arr[0][10];		
		fm.tAuditer.value=arr[0][11];	
		fm.tMngCom.value=arr[0][12];
		fm.tComFlag.value=arr[0][13];  // <!--//Ȩ�޿缶��־-->
		fm.tMissionID.value=arr[0][14];
		fm.tSubMissionID.value=arr[0][15];
		fm.tActivityID.value=arr[0][16];	
	}	
}


//��ʼ���� ����������Ϣ�б��������ⰸ�Ų�ѯLLClaimDetail��
function initLLClaimDetailGridQuery()
{
	try
	{
		var	strSQL = " select t.clmno,t.caseno,t.polno,t.customerno,dutycode,t.getdutykind,t.getdutycode,t.CaseRelaNo,t.RealPay,t.PrepayFlag,t.PrepaySum"
	             + " from llclaimdetail t  where 1=1"
		         + " and t.clmno='"+ fm.tRptNo.value +"'";	
//		arr = easyExecSql( strSQL );
//	    if (arr==null ||arr=="")
//	    {
//			alert("�ڸ��ⰸ��δ�ҵ��κ�Ԥ����ϸ��Ϣ��");    
//			fm.Bnf.disabled=true;
//			fm.PrepayCofirm.disabled=true;
//			return;     	
//	    } 
        turnPage.queryModal(strSQL,LLClaimDetailGrid);   
        var rowNum=LLClaimDetailGrid. mulLineCount ; //���� 
        if(rowNum==0)
        {
			alert("�ڸ��ⰸ��δ�ҵ��κ�Ԥ����ϸ��Ϣ��");    
			fm.Bnf.disabled=true;  //[�����˷���]��ť������
			fm.PrepayCofirm.disabled=true;//[Ԥ��ȷ��]��ť������
			return;          	
        }
	}
	catch(ex)
    {
      alert("LLClaimPrepay.js-->initLLClaimDetailGridQuery�����з����쳣:���ݲ�ѯ����!");
    }

}

//��Ӧ�� ����������Ϣ�б������ѡť�ĺ�������ѯԤ����ϸ��¼��LLPrepayDetail��
function LLClaimDetailGridClick()
{
//	  alert(strSQl);	     
	var selno= LLClaimDetailGrid.getSelNo()-1;
	sel=LLClaimDetailGrid.getSelNo()-1;
	fm.ClmNo.value= LLClaimDetailGrid.getRowColData(selno, 1);//"�ⰸ��" 
	fm.CaseNo.value= LLClaimDetailGrid.getRowColData(selno, 2);//"�ְ���"
	fm.PolNo.value= LLClaimDetailGrid.getRowColData(selno, 3);//"������"
	fm.DutyCode.value= LLClaimDetailGrid.getRowColData(selno, 5);//"���α���"
	fm.GetDutyKind.value= LLClaimDetailGrid.getRowColData(selno, 6);//"������������ 
	fm.GetDutyCode.value= LLClaimDetailGrid.getRowColData(selno, 7);//"�������α���"
	fm.CaseRelaNo.value= LLClaimDetailGrid.getRowColData(selno, 8);//"�����¹ʺ�"
	initLLPrepayDetailGridQuery();//��ѯ�ü�¼��Ԥ�����
	fm.LLPrepayAdd.disabled=false;//����Ԥ����ť����
	divLLPrepayDetail.style.display="none";

}
	

function initLLPrepayDetailGridQuery()
{
	var strSQL="select clmno,caseno,polno,getdutykind,getdutycode,PrepayNo,SerialNo,prepaysum,MakeDate "
	             + " from llprepaydetail where 1=1"
		         + " and clmno='"+ fm.ClmNo.value +"'"	
		         + " and caseno='"+ fm.CaseNo.value +"'"	
		         + " and polno='"+ fm.PolNo.value +"'"			         
		         + " and getdutykind='"+ fm.GetDutyKind.value +"'"	
		         + " and getdutycode='"+ fm.GetDutyCode.value +"'";	   	         
    turnPage2.queryModal(strSQL,LLPrepayDetailGrid);     	         
}

//"����"��ť
function LLPrepayDetailAdd()
{
	var selno= LLClaimDetailGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ��ҪԤ���ĸ������");
	      return;
	}	
	fm.PrepaySum.value="";
    divLLPrepayDetail.style.display="";
    fm.LLPrepayAdd.disabled=true;
    fm.LLPrepayCancel.disabled=false;
//	showMyPage(divLLPrepayDetail,true);
}
//"ȡ��"��ť
function LLPrepayDetailCancel()
{
//	showMyPage(divLLPrepayDetail,false);
    divLLPrepayDetail.style.display="none";
    fm.LLPrepayAdd.disabled=false;
    fm.LLPrepayCancel.disabled=true;
}
//[����]��ˢ��ҳ������
function RenewPage()
{

}

//"����"��ť
function LLPrepayDetailSave()
{
	if(fm.CasePayMode.value=="" ||fm.PrepaySum.value=="" )
	{
		alert("Ԥ�����δ��д��֧����ʽδѡ��");
		return;		
	}
	 if (!isNumeric(fm.PrepaySum.value))
    {
        alert("���ý����д����");
        return;
    }
	fm.fmtransact.value="Prepay||Insert";
	fm.action="LLClaimPrepaySave.jsp";
	submitForm(); //�ύ	
}

//�����˷���
function showBnf()
{
	var rptNo = fm.tRptNo.value;//�ⰸ��
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=B";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�����˷���");
}

//"Ԥ��ȷ��"��ť��Ԥ���ڵ����������ɴ���˽ڵ㣬����ص�����˹��������У�������Ԥ����־��
function LLClaimPrepayCofirm()
{
	//������У�飬�Ƿ������ɡ������˷��䡱���������û����������� Ԥ��ȷ��<�Ƿ�ת����̨���У��д�ȷ��>
	//��ѯLLBnf���������˻������������ⰸ�ţ��������ʣ�BnfKind��==B����ͳ�����е�������
	var str_llbnf="select clmno,sum(getmoney) from llbnf where clmno='"+ document.all('tRptNo').value +"' and bnfkind='B' group by clmno"; 
	//��ѯLLPrepayClaim��Ԥ���ⰸ��¼������һ�����ܵ�Ԥ���ⰸ��Ԥ�����
	var str_prepay="select clmno,prepaysum from llprepayclaim where clmno='"+ document.all('tRptNo').value +"' ";
	var arr_llbnf = easyExecSql( str_llbnf ); //���е�������Ѿ����䣩
	var arr_prepay = easyExecSql( str_prepay );//���ⰸ��Ԥ�����
	if(arr_llbnf=="" || arr_llbnf==null)
	{
		alert("δ���������˷��䣡");
		return;
	}    
	if(parseFloat(arr_llbnf[0][1]) != parseFloat(arr_prepay[0][1]))
	{
		alert("�����˷���δ������ϣ�");
		return;
	}
	fm.fmtransact.value="Prepay||Confirm";	//
	fm.action="LLClaimPrepayCofirmSave.jsp";
	submitForm(); //�ύ		
}

//�����ء���ť
function Return()
{
    location.href="LLClaimPrepayMissInput.jsp?";//����	
}

//�����ύ����
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

//Ԥ�������ύ�����,����<����[����]��ť�ķ���>
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


        initLLPrepayDetailGridQuery();
    	initLLClaimDetailGridQuery();//ˢ��ҳ��  
    }
    tSaveFlag ="0";

}

//Ԥ��ȷ���ύ�����,����
function afterSubmit2( FlagStr, content )
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


    }
    tSaveFlag ="0";
    Return();//�����ء���ť������Ԥ������
}

//Ӱ���ѯ---------------2005-08-14���
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��ӡ�ⰸ��������
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    fm.submit();
}