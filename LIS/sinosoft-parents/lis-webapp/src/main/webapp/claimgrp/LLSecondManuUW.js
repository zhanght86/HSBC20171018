//�������ƣ���LLSecondManuUW.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
//[���ҳ���ϵ�����]
function clearPageData()
{
	//[Ͷ������Ϣ]����
 	fm.AppntName.value = "";
	fm.AppntSex.value = "";
	fm.AppntBirthday.value = "";
	fm.OccupationCode.value = "";
	fm.NativePlace.value ="";
	fm.VIPValue.value = "";
	fm.BlacklistFlag.value = "";
	
	//[��Ϣ----Ͷ���齡����֪��ѯ�ʺ�,��콡����֪��ѯ�ʺ�,��Ӧδ��֪���,�����˽���״����֪����]
 	fm.HealthImpartNo1.value = "";
	fm.HealthImpartNo2.value = "";
	fm.NoImpartDesc.value = "";
	fm.Remark1.value = "";	
	
	//[��ͬ��ϸ��Ϣ]����
    fm.PrtNo.value="";
    fm.ProposalContNo.value="";//	    
    fm.ManageCom.value="";
    fm.SaleChnl.value="";
    fm.AgentCode.value="";
    fm.AgentCom.value="";
    fm.Remark.value="";	
}

//[��ʼ����ѯ������ͬ�б�]
function initLLCUWBatchGridQuery()
{	
	var strSQL="select t.contno,t.batno,t.appntno,t.appntname,t.insuredno,t.insuredname,t.managecom"
		+ " from llcuwbatch t where 1=1 "
		+ " and t.caseno='"+ document.all('tCaseNo').value +"' "
		+ " and t.batno='"+document.all('tBatNo').value +"'" 
		+ " and t.claimrelflag='"+document.all('tClaimRelFlag').value +"'"
		+ " order by t.contno";
    turnPage.queryModal(strSQL, LLCUWBatchGrid);		
}

//[LLCUWBatchGrid]�б�ѡť������Ӧ����     
function LLCUWBatchGridClick()
{
//	alert("������");
//	return;
	clearPageData();
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	var tCaseNo=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);
	fm.tContNo.value= LLCUWBatchGrid.getRowColData(tSelNo,1);
	
	//[��ѯ��ͬ����ϸ��Ϣ]-------lccont��
	var strLCcont = "select prtno,proposalcontno,managecom,salechnl,agentcode,agentcom,remark from lccont where contno='"+tContNo+"'";
    var arrLCcont=easyExecSql(strLCcont);
    if(arrLCcont!=null)
    {
	    fm.PrtNo.value=arrLCcont[0][0];//
 		fm.ProposalContNo.value=arrLCcont[0][1];//	    
	    fm.ManageCom.value=arrLCcont[0][2];//
	    fm.SaleChnl.value=arrLCcont[0][3];//
	    fm.AgentCode.value=arrLCcont[0][4];//
	    fm.AgentCom.value=arrLCcont[0][5];//
	    fm.Remark.value=arrLCcont[0][6];//    	
    }
	//[��ѯ---Ͷ���齡����֪��ѯ�ʺ�,��콡����֪��ѯ�ʺ�,��Ӧδ��֪���,�����˽���״����֪����-]
	var strllcuwmatch="select healthimpartno1,healthimpartno2,noimpartdesc,remark1 from llcuwbatch where 1=1 "
				+" and caseno= '"+ tCaseNo +"'"
			    +" and batno= '"+ tBatNo+"'"
				+" and contno= '"+ tContNo +"'"; 
	var arrLLcuwatch=easyExecSql(strllcuwmatch);
//	alert(arrLLcuwatch);	
	 if(arrLLcuwatch!=null)
    {
	 	fm.HealthImpartNo1.value =arrLLcuwatch[0][0];
		fm.HealthImpartNo2.value =arrLLcuwatch[0][1];
		fm.NoImpartDesc.value =arrLLcuwatch[0][1];
		fm.Remark1.value =arrLLcuwatch[0][3];	
    }		
	
    //[��ѯ Ͷ������Ϣ]
    strLCAppnt="select a.appntno,a.appntname,a.appntsex,a.appntbirthday,a.occupationcode,a.nativeplace,b.vipvalue,b.blacklistflag"
    	+" from lcappnt a,ldperson b where 1=1"
    	+" and b.customerno = a.appntno"
    	+" and a.contno='"+tContNo+"'";
    var arrLCcont=easyExecSql(strLCAppnt);
    if(arrLCcont!=null)
    {
	 	document.all('AppntNo').value = arrLCcont[0][0];    	
	 	document.all('AppntName').value = arrLCcont[0][1];
		document.all('AppntSex').value = arrLCcont[0][2];
		document.all('AppntBirthday').value = calAge(arrLCcont[0][3]);
		document.all('OccupationCode').value = arrLCcont[0][4];
		document.all('NativePlace').value = arrLCcont[0][5];
		document.all('VIPValue').value = arrLCcont[0][6];
		document.all('BlacklistFlag').value = arrLCcont[0][7];        	
    }
   
}

//[Ͷ������ϸ]��ť
function showPolDetail()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("����ѡ�񱣵�!");
		return;
	}
	  var cContNo = fm.ProposalContNo.value;
	  var cPrtNo = fm.PrtNo.value;
	  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}  

//[ɨ�����ѯ]��ť
function ScanQuery()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("����ѡ�񱣵�!");
		return;
	}
	var prtNo = fm.PrtNo.value;	
	window.open("./LCcontQueryImageMain.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");	
}

//[Ͷ���˼���Ͷ����Ϣ]��ť
function showApp(cindex)
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("����ѡ�񱣵�!");
		return;
	}
	var cContNo=fm.tContNo.value;
	var cAppntNo = fm.AppntNo.value;
	if (cindex == 1)
	{
		window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1");
	}
	else
	{
		window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo);
	}
	showInfo.close();

}   

//�������
function showHealth()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("����ѡ�񱣵�!");
		return;
	}
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/GrpUWManuHealthMain.jsp?ContNo1="+tContNo,"window1");
	showInfo.close();
}     


//������鱨��
function showRReport()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("����ѡ�񱣵�!");
		return;
	}
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/GrpUWManuRReportMain.jsp?ContNo1="+tContNo,"window2");  	
	showInfo.close();
                      
}      

//[���±�]��ť
function showNotePad()
 {  
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("����ѡ�񱣵�!");
		return;
	}
	ContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/UWNotePadMain.jsp?ContNo="+ContNo+"&OperatePos=3", "window1");
}

//[����������Ϣ]��ť
function enterRiskInfo()
{
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����¼��");
		return;
	}
	var tContNo =LLCUWBatchGrid.getRowColData(tSelNo,1);	 
	var tInsuredNo=document.all('tInsuredNo').value;
	var tCaseNo=document.all('tCaseNo').value;
	var tBatNo=document.all('tBatNo').value;
    var strUrl="./LLSecondUWRiskMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&CaseNo="+tCaseNo+"&BatNo="+tBatNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"����������Ϣ");
}
//[ȡ��]��ť
function uwCancelClick()
{
	document.all('uwState').value = "";
	document.all('uwStatename').value = "";	
	document.all('UWIdea').value = "";
}

//[����]��ť
function turnBack()
{
	window.location="./LLSecondUWAllInput.jsp";
}

//[ȷ��]��ť ----�ύ"�˱�����"�����水ť��Ӧ����
function uwSaveClick()
{		
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����¼��");
		return;
	}	
//	var tUWState = fm.uwState.value;                  //�˱�����
//	var tUWIdea = fm.UWIdea.value;                    
	if(fm.uwState.value == "")
	{
		alert("����¼��б��˱�����!");      
		return ;
	}
	fm.action = "./LLSecondManuUWSave.jsp";
	submitForm(); //�ύ
}

//���˱���ɡ�---�������񣬽�����ֱ�ת��ͬ���С���
//�ⰸ��ر�־(ClaimRelFlag),0----��أ����񷵻�����ڣ�ֻ�������ڵ㣩
//�ⰸ��ر�־(ClaimRelFlag),1----�޹أ����񷵻ص���ȫ�ڡ��Ե�����������ʽ���ص���ȫ���С����������ڵ㣬���ɶ����ȫ�ڵ㣩
function llSecondUWFinish()
{
	var tClaimRelFlag=fm.tClaimRelFlag.value;
	if(tClaimRelFlag=="0")  
	{
		fm.fmtransact.value="Finish||ToClaim";
//		alert("���ⰸ�йأ��˱���ɣ�ת�������");
//		return;		
		fm.action = "./LLSecondManuUWFinishSave.jsp";
		submitForm(); //�ύ
	}
	if(tClaimRelFlag=="1")  
	{
		fm.fmtransact.value="Finish||ToWFEdor";
//		alert("���ⰸ�޹أ��˱���ɣ�ת��ȫ��");
//		return;		
		fm.action = "./LLSecondManuUWFinishSave.jsp";
		submitForm(); //�ύ		
	}

}


//���ύ���ݡ�-----ͨ��Saveҳ�����̨�ύ����
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

    fm.submit(); //�ύ
    tSaveFlag ="0";    
}

//���˱����ۡ�-----�ύ���ݺ����,���سɹ���ʧ����Ϣ��ԭ��
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

    }
    tSaveFlag ="0";

}


//���˱���ɡ�-----�ύ���ݺ����,���سɹ���ʧ����Ϣ��ԭ��
function UWFinishafterSubmit( FlagStr, content )
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

        tSaveFlag ="0";
		turnBack();
    }

}