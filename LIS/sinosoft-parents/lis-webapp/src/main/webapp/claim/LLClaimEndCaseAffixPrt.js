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
//modify by niuzj,2005-10-20,���ⰸ��ӡ���е���Ҫ�������,�еĲ���Ҫ����
function showPrtAffix()
{
	var tcode=fm.PrtCode.value;//��֤��
	var tclmno=fm.ClmNo.value;//�ⰸ��
	var tcustno=fm.CustNo.value;//�ͻ���
	
	//�����ڿ�������õ�֤������ �ͻ��� �� �ⰸ�� �Ӵ�ӡ������ѯ ��ӡ��ˮ��
//	var strSql="select prtseq,otherno,code from loprtmanager where 1=1 "
//				+" and otherno='"+tclmno+"'"
//				+" and code='"+tcode+"'";
	
	var sqlid1="LLClaimEndCaseAffixPrtSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(tclmno);//ָ���������
	 mySql1.addSubPara(tcode);//ָ���������
	 var strSql = mySql1.getString();
	
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt !=null)
	{
		var tprtseq=arrLoprt[0][0];//��֤��ˮ��
		fm.PrtSeq.value=arrLoprt[0][0];	
	}	
		
  //	alert(tcode);		
	//��֤ǩ���嵥----���� �ⰸ�š��ͻ���-----���ߴ�ӡ,����Ҫ�������
  if(tcode=="PCT004" || tcode=="PCT017" || tcode=="PCT018" || tcode=="PCT019")
  {
  	 if(tcode=="PCT004")
	   {
	   	fm.action = './LLPRTCertificateSignforSave.jsp';
	   }
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
//          	fm.CustomerNo.value = LLInqApplyGrid.getRowColData(i,4);
		  }
		  fm.action = './LLPRTInteInqTaskSave.jsp';
	    }
/*	  else if(tcode=="PCT020")
	  	{
	  		tsel = LLInqFeeGrid.getSelNo()-1;
	  		if(tsel<0)
	  		{
	  			alert("��ѡ��һ���������룡");
	  			return;
	  		}
	  	else
	  		{fm.ClmNo3.value = LLInqFeeGrid.getRowColData(tsel,1);
	  		 fm.InqNo.value = LLInqFeeGrid.getRowColData(tsel,2);
	  		}
	  		fm.action = './LLPRTInteInqPaySave.jsp';
	  	}
	  	*/
	    //�ⰸ��˱���---���� �ⰸ�š��ͻ���-----���ߴ�ӡ
	    else if(tcode=="PCT019")
	    {
	    	fm.action = './LLPRTClaimAuditiReportSave.jsp';
	    }
	    	fm.target = "../f1print";
	      fm.submit();
   }
   else  //��Ҫ�������
   {
   	   //********************���ϴ�ӡΪ���ߴ�ӡ�����¿������򡶴�ӡ�����**********************
   	   //������ʾ֪ͨ----������ˮ��
   	   if(tcode=="PCT001")
   	   {
   	   	fm.action = './LLPRTAppraisalSave.jsp';
   	   }
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
   
       //��֤����ʱҪ����У��,niuzj,2005-10-20
       if(beforePrtCheck(fm.ClmNo.value,fm.PrtCode.value)==false)
       {
        	return;
       }
       
	      //fm.target = "../f1print";
	      //fm.submit();
    }
}

/**********************************
//niuzj,2005-10-20
//��ӡǰ���飨������Ҫ�����������֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,code)
{
	//��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
   	var tclmno=trim(clmno);
   	var tcode =trim(code);
   	if(tclmno=="" ||tcode=="")
   	{
   		alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
   		return false;
   	}
//    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
//			+" and t.otherno='"+tclmno+"' "
//			+" and trim(t.code)='"+tcode+"' ";
    
     var sqlid2="LLClaimEndCaseAffixPrtSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(tclmno);//ָ���������
	 mySql2.addSubPara(tcode);//ָ���������
	 var strSql = mySql2.getString();
    
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt==null)
	{
		alert("û���ҵ��õ�֤����ˮ��");
		return false;
	}	
	else 
	{
		var tprtseq=arrLoprt[0][0];//��֤��ˮ��
		var totherno=arrLoprt[0][1];//��Ӧ�������루�ⰸ�ţ�
		var tcode=arrLoprt[0][2];//��������
		var tprttype=arrLoprt[0][3];//��ӡ��ʽ
		var tstateflag=arrLoprt[0][4];//��ӡ״̬
		var tpatchflag=arrLoprt[0][5];//�����־
		fm.PrtSeq.value=arrLoprt[0][0];
		//���ڵ�δ��ӡ����ֱ�ӽ����ӡҳ��
	 	if(tstateflag!="1")
	 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
			fm.target = "../f1print";
			fm.submit();
	 	}
		else
		{
			//���ڵ��Ѿ���ӡ��
			if(confirm("�õ�֤�Ѿ���ӡ��ɣ���ȷʵҪ������"))
	 		{
	 			//���벹��ԭ��¼��ҳ��
	 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
//	 			window.open(strUrl,"��֤����");
	 			window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 		}
		}
	} 
}


function showDIVInqGrid()
{
	var tcode = fm.PrtCode.value;
	if(tcode=="PCT017" || tcode=="PCT018")
	{
//		var strSql="select clmno,inqno,batno,customerno,inqrcode ,initdept,inqdept,inqper,inqstartdate,inqenddate from llinqapply where 1=1"
//				+" and clmno = '"+ fm.ClaimNo.value +"' "
//				+" order by clmno,inqno";
		
		 var ClaimNo3 = fm.ClaimNo.value;
		 var sqlid3="LLClaimEndCaseAffixPrtSql3";
		 var mySql3=new SqlClass();
		 mySql3.setResourceName("claim.LLClaimEndCaseAffixPrtSql");
		 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
		 mySql3.addSubPara(ClaimNo3);//ָ���������
		 var strSql = mySql3.getString();
		
		var arr = easyExecSql(strSql);
		if(arr==null){alert("���ⰸû���κε�������"); return;}
		displayMultiline(arr,LLInqApplyGrid);
		divLLInqApplyGrid.style.display ="";//����������Ϣ
		divLLInqFeeGrid.style.display ="none";
	}
   /* else if(tcode=="PCT020")
	{
        var strSql = "select clmno,payee,sum(feesum) from llinqfee where clmno = '" + fm.ClaimNo.value +"' "
                     + " group by payee,clmno"; 
		var arr = easyExecSql(strSql);
		if(arr==null){alert("���ⰸû���κβ鿱����"); return;}
		displayMultiline(arr,LLInqFeeGrid);
		divLLInqFeeGrid.style.display ="";
		divLLInqApplyGrid.style.display ="none";
		
	}
	*/
	else
	{
		divLLInqApplyGrid.style.display ="none";
		divLLInqFeeGrid.style.display ="none";
	}
}
function LAAQSuggestShiftClick()
{
    var i = LLInqFeeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.Payee.value  = LLInqFeeGrid.getRowColData(i,2);//�鿱��
                  
    }
}
