var showInfo;
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();

//[����]��ť
function returnParent()
{
	top.close();
}

//[��ӡ]��ť----���ݲ�ͬ�ĵ�֤�ŵ��ò�ͬ��Saveҳ��
function showPrtAffix()
{
	var tcode=fm.PrtCode.value;//��֤��
	var tclmno=fm.ClmNo.value;//�ⰸ��
	var tcustno=fm.CustNo.value;//�ͻ���
	
	//�����ڿ�������õ�֤������ �ͻ��� �� �ⰸ�� �Ӵ�ӡ������ѯ ��ӡ��ˮ��
	var strSql="select prtseq,otherno,code from loprtmanager where 1=1 "
				+" and otherno='"+tclmno+"'"
				+" and code='"+tcode+"'";
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt !=null)
	{
		var tprtseq=arrLoprt[0][0];//��֤��ˮ��
		fm.PrtSeq.value=arrLoprt[0][0];	
	}	
		
//	alert(tcode);		
	//��֤ǩ���嵥----���� �ⰸ�š��ͻ���-----���ߴ�ӡ
	if(tcode=="PCT004"){fm.action = './LLPRTCertificateSignforSave.jsp';}
	//���鱨��֪ͨ��--
	else if(tcode=="PCT017")
	{
		tsel=LLInqApplyGrid.getSelNo()-1;
		if(tsel<0)
		{
			alert("��ѡ��һ���������룡");
			return;
		}
		else
		{
			fm.ClmNo3.value = LLInqApplyGrid.getRowColData(tsel,1);
        	fm.InqNo.value = LLInqApplyGrid.getRowColData(tsel,2);
		}
		fm.action = './LLPRTInteInqReportSave.jsp';
	}
	//��������֪ͨ��
	else if(tcode=="PCT018")
	{
		tsel=LLInqApplyGrid.getSelNo()-1;
		if(tsel<0)
		{
			alert("��ѡ��һ���������룡");
			return;
		}
		else
		{
			fm.ClmNo3.value = LLInqApplyGrid.getRowColData(tsel,1);
        	fm.InqNo.value = LLInqApplyGrid.getRowColData(tsel,2);
//        	fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
		}
		fm.action = './LLPRTInteInqTaskSave.jsp';
	}
	//�ⰸ��˱���---���� �ⰸ�š��ͻ���-----���ߴ�ӡ
	else if(tcode=="PCT019"){fm.action = './LLPRTClaimAuditiReportSave.jsp';}
	//********************���ϴ�ӡΪ���ߴ�ӡ�����¿������򡶴�ӡ�����**********************
	//������ʾ֪ͨ----������ˮ��
	else if(tcode=="PCT001"){fm.action = './LLPRTAppraisalSave.jsp';}
	//���ⵥ֤֪ͨ��----������ˮ��
	else if(tcode=="PCT002"){fm.action = './LLPRTCertificatePutOutSave.jsp';}
	//��֤����֪ͨ��[�����]----������ˮ��	
	else if(tcode=="PCT003"){fm.action = './LLPRTCertificateRenewSave.jsp';}
	//���ɱ���֪ͨ��	----������ˮ��
	else if(tcode=="PCT008"){fm.action = './LLPRTPatchFeeSave.jsp';}
	//�������֪ͨ�飭�ܸ�[��������]----������ˮ��
	else if(tcode=="PCT007"){fm.action = './LLPRTProtestNoRegisterSave.jsp';}
	//����-��ͬ������ע	----������ˮ��
	else if(tcode=="PCT013"){fm.action = './LLClaimEndCasePrintContSave.jsp';}
	//�����᰸�׶δ�ӡ�ĵ�֤----������ˮ��	
	else {fm.action = './LLClaimEndCaseParaPrint.jsp';}
//		return;
	fm.target = "../f1print";
	fm.submit();
}


function showDIVInqGrid()
{
	var tcode = fm.PrtCode.value;
	if(tcode=="PCT017" || tcode=="PCT018")
	{
		var strSql="select clmno,inqno,batno,customerno,inqrcode ,initdept,inqdept,inqper,inqstartdate,inqenddate from llinqapply where 1=1"
				+" and clmno= '"+ fm.ClaimNo.value +"' "
				+" order by clmno,inqno"
		var arr = easyExecSql(strSql);
		if(arr==null){alert("���ⰸû���κε�������"); return;}
		displayMultiline(arr,LLInqApplyGrid);
		divLLInqApplyGrid.style.display ="";//����������Ϣ
	}
	else
	{
		divLLInqApplyGrid.style.display ="none";
	}
}