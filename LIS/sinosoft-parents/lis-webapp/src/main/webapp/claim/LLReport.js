var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//������
function zhoulei()
{
	alert("������!");//who's zhoulei? add on 2009-03-16
}

//���ذ�ť
function goToBack()
{
	if(document.all('isNew').value == "3")
	{
		top.close();;
	}
	else
	{
		location.href="LLReportMissInput.jsp";
	}
}

//���ý����ϵ�����������Ϊreadonly
function readonlyFormElements()
{
    var elementsNum = 0;//FORM�е�Ԫ����
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
  	    if (fm.elements[elementsNum].type == "text" || fm.elements[elementsNum].type == "textarea")
  	    {
  	        fm.elements[elementsNum].readonly = true;
  	    }
  	    if (fm.elements[elementsNum].type == "button")
  	    {
  	        fm.elements[elementsNum].disabled = true;
  	    }
  	}
}

//��ʼ����ѯ
function initQuery()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("�����ⰸΪ�գ�");
        return;
    }
    //�����¼���(һ��)
    /*
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql1");
	mySql.addSubPara( rptNo);            
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];
    fm.BaccDate.value = AccNo[0][1];
    
    //���������ż�����������Ϣ(һ��)
    /*
    var strSQL2 =" select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom, "
                +" (select username from llclaimuser where usercode = LLReport.Operator),RgtClass,PostCode "
                +" from LLReport where 1=1 "
                +" and RptNo = '"+rptNo+"'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql2");
	mySql.addSubPara( rptNo);  
    ////prompt("���������ż�����������Ϣ(һ��)",strSQL2);
    var RptContent = easyExecSql(mySql.getString());

    //����ҳ������

    
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
    fm.RgtClass.value = RptContent[0][11];
    fm.PostCode.value = RptContent[0][12];
    showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //�������
    showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
    showOneCodeName('llrptmode','RptMode','RptModeName');//������ʽ
    showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־

    //---------------------------------------------------*
    //����ҳ��Ȩ��
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

//    fm.AddReport.disabled=false;
    fm.addbutton.disabled=false;
//    fm.updatebutton.disabled=false;
    fm.DoHangUp.disabled=false;
    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewInvest.disabled=false;
    fm.AccidentDesc.disabled=false;
//   	fm.QueryCont1.disabled=false;
    fm.QueryCont2.disabled=false;
    fm.QueryCont3.disabled=false;
    

    //�����ְ�������������Ϣ(����)
    /*
    var strSQL3 = " select CustomerNo,Name,"
                + " Sex,"
                + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                + " Birthday,"
                + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                + " from LDPerson where "
                + " CustomerNo in("
                + " select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
    */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql3");
	mySql.addSubPara( rptNo);  
	
    //prompt("�����ְ�������������Ϣ(����)",strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
        initQuery();
        fm.RptConfirm.disabled = false;
        operateButton2.style.display="none";
        operateButton3.style.display="";
        
        document.all('MedicalAccidentDate').disabled=false;
        document.all('OtherAccidentDate').disabled=false;
    }
    

    
    tSaveFlag ="0";
}

//����ȷ�Ϸ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
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
    	//��ʰ�����ʾ�û���������ΪͶ,�����˵����б�����ȫ������
    	//var pReasonCode = new Array;
        //var strSQL = "select substr(ReasonCode,2,2) from LLReportReason where "
                    //+ "RpNo = '"+fm.RptNo.value+"'";
        //prompt("afterSubmit2-���ӷ�����స�������������ѹ���",strSQL);
        
        //tReasonCode = easyExecSql(strSQL);
        //for (var i = 0;i < tReasonCode.length; i++)
        //{  
        	//if (tReasonCode[i] == '02')
        	//{
        		//alert("�ó�������ΪͶ/�������˵���ر�����ִ�б�ȫ���ڹ���!");
                //break;
        	//}
        //}
        
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        goToBack();
    }
    tSaveFlag ="0";
}

//����ο�ʷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
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
    }
    tSaveFlag ="0";
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();

  	    fm.AccNo.value = "";
 	    fm.RptNo.value = "";
	    fm.RptorName.value = "";
	    fm.RptorPhone.value = "";
	    fm.RptorAddress.value = "";
	    fm.Relation.value = "";
	    fm.RelationName.value = "";
	    fm.RptMode.value = "";
	    fm.RptModeName.value = "";
	    fm.AccidentSite.value = "";
	    fm.occurReason.value = "";
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    //fm.IsVip.value = "";
	    //fm.VIPValueName.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    fm.OtherAccidentDate.value = "";
	    fm.MedicalAccidentDate.value = "";
 	    fm.accidentDetail.value = "";
 	    fm.accidentDetailName.value = "";
//	 	    fm.IsDead.value = "";
//	 	    fm.IsDeadName.value = "";
 	    fm.claimType.value = "";
 	    fm.cureDesc.value = "";
 	    fm.cureDescName.value = "";
 	    fm.Remark.value = "";
 	    fm.AccDesc.value = "";//�¹�����
 	    fm.AccResult1.value = "";
 	    fm.AccResult1Name.value = "";
 	    fm.AccResult2.value = "";
 	    fm.AccResult2Name.value = "";

        //���������ÿ�
        for (var j = 0;j < fm.claimType.length; j++)
        {
        	  fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("��LLReport.js-->resetForm�����з����쳣:��ʼ���������!");
    }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
}




//�ύǰ��У�顢����
function beforeSubmit()
{
	//��ȡ������Ϣ
    var RptNo = fm.RptNo.value;//�ⰸ��
    
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccReason = fm.occurReason.value;//����ԭ��

    
    var AccDesc = fm.accidentDetail.value;//����ϸ��


    
    //----------------------------------------------------------BEG
    //���ӱ����������͹�ϵ�ķǿ�У��
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("�����뱨����������");
        return false;
    }
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("�����뱨�������¹��˹�ϵ��");
        return false;
    }
    //----------------------------------------------------------end
    //1 �������ˡ���Ϣ
    if (checkInput1() == false)
    {
    	  return false;
    }

    
    var claimType=0;//ѡ��������������

    
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  claimType++;
          }
    }
    
    //alert("claimType:"+claimType);
    
    //5 �����������
    if (claimType==0)
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    
    
    //������ҽ����������ʱ��ҪУ��ҽ�Ƴ�������
    if(fm.claimType[5].checked == true)
    {
		//У��ҽ�Ƴ������ں��¹�����
		if (checkDate(fm.AccidentDate.value,fm.MedicalAccidentDate.value,"ҽ�Ƴ�������") == false)
		{
		     return false;
		}
    }
    
    //�����ڶ���һ���������ͻ���ڷ�ҽ�Ƶ���������ʱУ��������������
    if((claimType>1)||((fm.claimType[5].checked == false)&&claimType==1))
    {
        //У�������������ں��¹�����
		if (checkDate(fm.AccidentDate.value,fm.OtherAccidentDate.value,"������������") == false)
		{
		    return false;
		}
    }

  
    //3 ������ԭ��
    if (AccReason == null || AccReason == '')
    {
        alert("����ԭ����Ϊ�գ�");
        return false;
    }
    
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //��ӳ���ԭ��Ϊ����ʱ������������Ϊҽ��ʱ���¹����ڵ���ҽ�Ƴ�������
    //---------------------------------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.MedicalAccidentDate.value)&& fm.claimType[5].checked == true)
    {
        alert("����ԭ��Ϊ����ʱ���¹����ڱ������ҽ�Ƴ������ڣ�");
        return false;
    }
    
    //Modify by zhaorx 2006-07-04
//    if(fm.occurReason.value == "2" && (fm.AccResult1.value =="" || fm.AccResult1.value==null))
//    {
//        alert("����ԭ��Ϊ����ʱ�����ս��1����Ϊ�գ�");
//        return false;
//    }  
     
    //---------------------------------------------------------------------------------**End
    //4 ������ϸ��
    if ((AccDesc == null || AccDesc == '') && fm.occurReason.value == '1')
    {
        alert("����ԭ��Ϊ����,����ϸ�ڲ���Ϊ�գ�");
        return false;
    }
    
    //Modify by zhaorx 2006-07-04
//    if (fm.occurReason.value == '1' && (fm.AccResult1.value ==""  || fm.AccResult1.value == null || fm.AccResult2.value == "" || fm.AccResult2.value == null))
//    {
//        alert("����ԭ��Ϊ����ʱ�����ս��1�����ս��2������Ϊ�գ�");
//        return false;
//    } 
    


    //-----------------------------------------------------------**Beg*2007-02-05 15:02
    //��ӳ��ս��1,���ս��2Ϊ��¼��
    //-----------------------------------------------------------**
    if(fm.AccResult1.value ==""  || fm.AccResult1.value == null)
    {
        alert("���ս��1����Ϊ�գ�");
        return false;
    }   
    if(fm.AccResult2.value == "" || fm.AccResult2.value == null)
    {
        alert("���ս��2����Ϊ�գ�");
        return false;
    }
    //-----------------------------------------------------------**End
    

    //---------------------------------------------*Beg*2005-6-13 20:26
    //�������������ѡ��"ҽ��"����ѡ��ҽԺ���ж�
    //---------------------------------------------*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("��ѡ��ҽԺ��");
        return false;
    }
    //---------------------------------------------*End
   
    //by niuzj,2005-11-21
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //�������������ѡ�л���,����ѡ��������߲л��ش󼲲�֮һ���ж�
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false && fm.claimType[2].checked == false)
    {
        alert("ѡ�л���,����ѡ��������߲л��ش󼲲�֮һ!");
        return false;
    }
    //---------------------------------------------------------End
    


    var tAccDate="";//�Ƚ�����,ȡС���Ǹ�������Ϊ�Ƚ�����
    
    //������Ϊ��ʱ,ȡ����С���Ǹ�������Ϊ�Ƚ�����
    if((!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value==""))&&(!(fm.OtherAccidentDate.value==null||fm.OtherAccidentDate.value.value=="")))
    {
    	
    	//ȡ���������н�С���Ǹ�
        if (dateDiff(fm.OtherAccidentDate.value,fm.MedicalAccidentDate.value,'D') > 0)
        {
        	tAccDate=fm.OtherAccidentDate.value;
        }
    	else
    	{
    		tAccDate=fm.MedicalAccidentDate.value;
    	}
    }
    else if(!(fm.MedicalAccidentDate.value==null||fm.MedicalAccidentDate.value=="")){
    	
    	tAccDate=fm.MedicalAccidentDate.value;
    }
    else
    {
    	tAccDate=fm.OtherAccidentDate.value;
    }

    
    //alert("tAccDate:"+tAccDate);

    //---------------------------------------------------------Beg*2005-12-28 16:22
    //����ԭ��Ϊ����,ֻҪ����һ�������������¹�����180��󣬼���ʾ
    //---------------------------------------------------------
    if(fm.occurReason.value == '1')
    {
    	if (dateDiff(fm.AccidentDate.value,tAccDate,'D') > 180)
        {
            alert("�����������¹�����180���!");
        }
    }

    
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //���δ�ڳ��պ�ʮ���ڱ������ж�,ҽ�Ƴ������ں��������������ĸ�������ĸ����¹����ڱ�
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("δ�ڳ��պ�ʮ���ڱ���!"); 
    }
    
    
    //��������������������
    //1.�������ں�ĳ������ڻ��¹����ڲ��ܱ�������
    //2.�������ڵ����֮ǰ�ĳ������ڻ��¹������ڱ���ʱ����Ҫ������ʾ��
    //---------------------------------------------------------
    //var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql4");
	mySql.addSubPara(fm.customerNo.value); 
    
    var tDeathDate = easyExecSql(mySql.getString());
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��������Ժ���ⰸ��������");
            return;
        }
        else
        {
            if (!confirm("�ͻ�"+fm.customerName.value+"�ѱ�ȷ����"+tDeathDate+"��ʣ��Ƿ�������У�"))
            {
                return;
            }
        }
    }


    //---------------------------------------------------------End
    return true;
}
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,0,0,*";
    }
     else
     {
        parent.fraMain.rows = "0,0,0,0,*";
     }
}

//��������
function addClick()
{
    resetForm();
}

//��������Ϣ�޸�
function updateClick()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }
    if (confirm("��ȷʵ���޸ĸü�¼��"))
    {
        if (beforeSubmit() == true)
        {
            fm.fmtransact.value = "update";
            fm.action = './LLReportSave.jsp';
            submitForm();
        }
    }
    else
    {
        mOperate="";
    }
}

//������ɾ������Ҫ�жϣ�����������ɾ��
function deleteClick()
{
    alert("���޷�����ɾ��������");
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
    if (cShow=="true")
    {
        cDiv.style.display="";
    }
    else
    {
        cDiv.style.display="none";
    }
}

//������ѯ
//���ա��ͻ��š���LCpol�н��в�ѯ����ʾ�ÿͻ��ı�����ϸ
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ⰸ��ѯ
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("��ѡ������ˣ�");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���¼���ѯ����
function afterQueryLL2(tArr)
{
	//�õ�����ֵ
    fm.AccNo.value = tArr[0];//�¼���
    fm.AccDesc.value = tArr[2];//�¹�����
    fm.AccidentSite.value = tArr[3];//�¹ʵص�
    
    fm.occurReason.value = tArr[4];//����ԭ�����
    fm.ReasonName.value = tArr[5];//����ԭ������
    
    fm.accidentDetail.value = tArr[6];//����ϸ�ڱ���
    fm.accidentDetailName.value = tArr[7];//����ϸ������
    
    fm.AccResult1.value = tArr[8];//���ս��1����
    fm.AccResult1Name.value = tArr[9];//���ս��1����
    
    fm.AccResult2.value = tArr[10];//���ս��2����
    fm.AccResult2Name.value = tArr[11];//���ս��2����
    
    fm.Remark.value = tArr[12];//��ע��Ϣ
    

}

//�ɿͻ���ѯ��LLLdPersonQuery.js�����ص�����¼ʱ����
function afterQueryLL(arr)
{
	  //�õ�����ֵ
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    
    //2008-11-18 ȥ��VIP��־����ʾ
    //if(arr[4]==null || arr[4]=="" || arr[4]==0)
    //{
     	////fm.VIPValueName.value ="��";
     	////fm.IsVip.value = "0";
    //}
    //else
    //{
     	////fm.IsVip.value = arr[4];
     	////fm.VIPValueName.value ="��";
    //}
    
    showOneCodeName('sex','customerSex','SexName');//�Ա�
//    fm.customerSex.disabled = true;
    //��ʼ��¼����
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.OtherAccidentDate.value = "";
    fm.MedicalAccidentDate.value = "";
    fm.accidentDetail.value = "";
    fm.accidentDetailName.value = "";
//    fm.IsDead.value = "";
//    fm.IsDeadName.value = "";
    fm.cureDesc.value = "";
    fm.cureDescName.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //���ÿɲ�����ť
    fm.addbutton.disabled = false;
    fm.QueryCont2.disabled = false;
    fm.QueryCont3.disabled = false;
}

//�����˲�ѯ
function showInsPerQuery()
{
    window.open("LLLdPersonQueryMain.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//��������
function showLcContSuspend()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }
//    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);

    //Modifyed by niuzj,2005-12-23   
    var tCustomerNo = fm.customerNo.value;
	   if (tCustomerNo == "")
	   {
	      alert("��ѡ������ˣ�");
	      return;
	   }
    var strUrl="LLLpContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�����ɵ�֤֪ͨ��
function showAffix()
{
    var claimTypes=getClaimTypes();
    var CaseNo=fm.ClmNo.value;
    var whoNo=fm.customerNo.value;
    var strUrl="LLRptMAffixListMain.jsp?claimTypes="+claimTypes+"&CaseNo="+CaseNo+"&whoNo="+whoNo+"&Proc=Rpt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�򿪷������
function showBeginInq()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=01";//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�������");
}

//�鿴����
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
	//---------------------------------------
	//�жϸ��ⰸ�Ƿ���ڵ���
	//---------------------------------------
	/*
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
     */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql5");
	mySql.addSubPara(fm.RptNo.value); 
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("���ⰸ��û�е�����Ϣ��");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"�鿴����");
}

//����ο��
function showCondole()
{
	//Modifyed by niuzj,2005-12-23
	  if (fm.customerNo.value == "")
	  {
	      alert("��ѡ������ˣ�");
	      return;
	  }
	//---------------------------------------
	//�жϸ÷ְ��Ƿ�������ο��
	//---------------------------------------
	/*
    var strSQL = "select CondoleFlag from LLSubReport where "
                + " SubRptNo = '" + fm.RptNo.value + "'"
                + " and CustomerNo = '" + fm.customerNo.value + "'";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql6");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    var tCondoleFlag = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCondoleFlag == "1")
    {
    	  alert("������ο�ʣ�");
    	  return;
    }
    fm.action = './LLReportCondoleSave.jsp';
    submitForm();
}

/*****************************************************************************
 * ��ʼ
 * �޸�ԭ�򣺼�һ�����ѹ��ܣ����[����ʱ�]ʱ��Ҫ������ʾ���Ƿ��ȷ�����飿�� ��,��򿪷���������,
             ��,��򿪷���ʱ�����
 * �޸��ˣ������
 * �޸�ʱ�䣺2005-11-15
 ****************************************************************************/
function showBeginSubmit()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	  
    var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value;//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�򿪳ʱ���ѯ
function showQuerySubmit()
{
	  //---------------------------------------
	  //�жϸ��ⰸ�Ƿ���ڳʱ�
	  //---------------------------------------
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���¹�������Ϣ
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("��ѡ���ⰸ��");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//������ѯ
function IsReportExist()
{
	  //�������ˡ���Ϣ
	  if (checkInput1() == false)
    {
    	  return;
    }
    queryReport();

}

//�������ˡ���Ϣ
function checkInput1()
{
	
	  //��ȡ������Ϣ
    var CustomerNo = fm.customerNo.value;//�����˱��
    var AccDate = fm.AccidentDate.value;//�¹�����
    
    //����������Ϣ
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("����ѡ������ˣ�");
        return false;
    }

    
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�������¹����ڣ�");
        return false;
    }
    else
    {
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("�¹����ڲ������ڵ�ǰ����!");
            return false; 
        }  
    }
    return true;
}

//����ԭ���ж�
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//������ѯ
function queryReport()
{
	var AccNo;
	var RptContent = new Array();
	var queryResult = new Array();
    //�����¼���(һ��)
    /*
    var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') "
                + getWherePart( 'AccType','occurReason' )
                + " and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";
*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql7");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.occurReason.value);
	mySql.addSubPara(fm.customerNo.value);
	
//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
    	  fm.isReportExist.value = "false";
    	  alert("����������!");
    	  return
    }
    else
    {
        //���������ż�����������Ϣ(һ��)
        /*
        var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,(select username from llclaimuser where usercode = LLReport.Operator),RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";
       */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql8");
	mySql.addSubPara(AccNo); 
	mySql.addSubPara(fm.customerNo.value);

	
	
        //prompt("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
//        alert("RptContent= "+RptContent);
        if (RptContent == null)
        {
        	  fm.isReportExist.value = "false";
    	      alert("����������!");
    	      return;
        }
        else
      	{
      		  //����ҳ������
      		  fm.AccNo.value = AccNo;
      		  fm.RptNo.value = RptContent[0][0];
      		  fm.RptorName.value = RptContent[0][1];
      		  fm.RptorPhone.value = RptContent[0][2];
      		  fm.RptorAddress.value = RptContent[0][3];
      		  fm.Relation.value = RptContent[0][4];
      		  fm.RptMode.value = RptContent[0][5];
      		  fm.AccidentSite.value = RptContent[0][6];
      		  fm.occurReason.value = RptContent[0][7];
      		  fm.RptDate.value = RptContent[0][8];
      		  fm.MngCom.value = RptContent[0][9];
      		  fm.Operator.value = RptContent[0][10];
              fm.RgtClass.value = RptContent[0][11];
              showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //�������
      		  showOneCodeName('llrgrelation','Relation','RelationName');//���������¹��˹�ϵ
      		  showOneCodeName('RptMode','RptMode','RptModeName');//������ʽ
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//����ԭ��
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//������־
      		  //����ҳ��Ȩ��
      		  fm.AccidentDate.readonly = true;
//      		  fm.occurReason.disabled=true;
//      		  fm.accidentDetail.disabled=true;
      		  fm.claimType.disabled=true;

//      		  fm.AddReport.disabled=false;
      	 	  fm.addbutton.disabled=false;
//     		    fm.updatebutton.disabled=false;
    		    fm.DoHangUp.disabled=false;
    		    fm.CreateNote.disabled=false;
    		    fm.BeginInq.disabled=false;
    		    fm.ViewInvest.disabled=false;
   		      //fm.Condole.disabled=false;
    		    fm.SubmitReport.disabled=false;
    		    fm.ViewReport.disabled=false;
    		    fm.AccidentDesc.disabled=false;
//   		      fm.QueryCont1.disabled=false;
    		    fm.QueryCont2.disabled=false;
    		    fm.QueryCont3.disabled=false;
    		//����ԭ��У��
    		checkOccurReason();

            //�����ְ�������������Ϣ(����)
            /*
            var strSQL3 = " select CustomerNo,Name,"
                        + " Sex,"
                        + " (case trim(Sex) when '0' then '��' when '1' then 'Ů' when '2' then '����' end) as SexNA,"
                        + " Birthday,"
                        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '��' else '��' end) as �Ƿ����籣��־ "
                        + " from LDPerson where "
                        + " CustomerNo in("
                        + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
            */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql9");
	mySql.addSubPara(RptContent[0][0]); 

	
            //prompt("�����ְ�������������Ϣ(����)",strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
        }
    }
}

//�¼���ѯ
//2005-8-1 16:08 �޸�:ȥ������ԭ��Ĳ�ѯ��������λ�¼�
function queryLLAccident()
{
	//�ǿռ��
	if (checkInput1() == false)
    {
    	  return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("����ԭ��Ϊ�գ�");
//        return;
//    }
    //�����¼���
    /*
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
                */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql10");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    var tAccNo = easyExecSql(mySql.getString());
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("û������¼���");
        return;
    }
    //���¼���ѯ����
//	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//	alert(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"����¼�");
}

//[����]��ť��Ӧ����
function toSaveClick()
{
    //�ж��Ƿ����������޸�
    if(fm.isNew.value == '1')
    {
    	updateClick();
    }
    else
    {
        saveClick();
    }
}

//�������
function saveClick()
{
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit() == true)
    {
        //�ж����ֱ��汨�����Ǳ��������
        if (fm.RptNo.value != "")
        {
        	fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        fm.action = './LLReportSave.jsp';
        submitForm();
    }
}

//����ȷ��
function reportConfirm()
{
//	  if(!KillTwoWindows(fm.RptNo.value,'10'))
//	  {
//	  	  return false;
//	  }	
	  
    /**
     * 2008-11-22 zhangzheng
     * ���ӷ������స�������������ѹ���,����ʰ��������Ҫ���𱣵�,��ѯ���û��Ƿ���Ҫ�������������Ҫ�������ύ��̨�������û����ñ���������ִ���ֹ����𣬷����򲻹��𱣵�;
	 * ������͵İ����Զ�������ΪͶ,�����˵����б���
     */
/**
	var tReasonCode = 0;
    var strSQL = "select count(1) from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"' and substr(ReasonCode,2,2)='02'";
    //prompt("����ȷ��-���ӷ�����స�������������ѹ���",strSQL);
    
    tReasonCode = easyExecSql(strSQL);
    //alert("tReasonCode:"+tReasonCode);
    
    if (tReasonCode == 0)
    {
        
        //����Ƿ��Ѿ��ֹ�����
        var strSQL = "";
        var arrResult = new Array;
    	//strSQL = "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
    		   //+ " from LcCont a where 1=1 "
    		   //+ " and a.insuredno in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')"
    		   //+ " union "
    		   //+ "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
    		   //+ " from LcCont a where 1=1 "
    		   //+ " and a.AppntNo in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')";  //Ͷ����     
        strSQL="select count(*) from lcconthangupstate where hangupno='"+fm.RptNo.value+"'";
	    arrResult = easyExecSql(strSQL);
	    ////prompt("����ȷ��-����Ƿ��Ѿ��ֹ�����",strSQL);
	    if (arrResult != null)
	    {
    	    for (var j=0; j<arrResult.length; j++)
    	    {
        	    for (var k=0; k<arrResult[j].length; k++)
        	    {
        	        if (arrResult[j][k] == "0")
        	        {
        	        	tReasonCode++;
        	        }
        	    }
    	    }
	    }
	    
	    if (tReasonCode > 0)
	    {
        	if (confirm("�������Ͳ��������,�Ƿ���б����������?"))
            {
                return;
            }
        }
        
    }
*/
    //---------------------------------------------------------------------end
    //���ǿ�
    if (fm.RptNo.value == "")
    {
   	    alert("������Ϊ�գ�");
   	    return;
    }
    fm.action = './LLReportConfirmSave.jsp';
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
//    showSubmitFrame(mDebug);
    fm.target="fraSubmit";
    fm.submit(); //�ύ
    tSaveFlag ="0";
}

//ѡ��SubReportGrid��Ӧ�¼�,tPhase=0Ϊ��ʼ��ʱ����
function SubReportGridClick(tPhase)
{
	//------------------------------------------**Beg
	//�ÿ���ر�
	//------------------------------------------**
	
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.OtherAccidentDate.value = "";
	fm.MedicalAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	//���������ÿ�
    for (var j = 0;j < fm.claimType.length; j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //------------------------------------------**End

	
    //ȡ������
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//�Ա�
    }

    //��ѯ�����������
    var tClaimType = new Array;
    /*
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";               
      */          
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql11");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    ////prompt("��ѯ��������",strSQL1);
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
    	  alert("��������Ϊ�գ�����˼�¼����Ч�ԣ�");
    	  return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
        	  for (var l=0;l<tClaimType.length;l++)
        	  {
        	  	  var tClaim = tClaimType[l].toString();
        	  	  tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
                	  
                	  //���Ϊ�˲У���ʾ�˲м���֪ͨ2005-8-13 11:04
                	  if (tClaim == '01')
                	  {
                	      fm.MedCertForPrt.disabled = false;
                	  }
            	  }
            }
        }
    }
    //��ѯ�ְ�����Ϣ
    var tSubReport = new Array;
    /*
    var strSQL2 = "select HospitalCode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2,medaccdate,(select  codename from LDcode where  codetype='accidentcode'  and code=AccidentDetail) from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
    */            
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql12");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value); 
	
    //prompt("�����ⰸ��ѯ-��ѯ�ְ�����Ϣ",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.OtherAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.MedicalAccidentDate.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//����ҽԺ
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//����ϸ��
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//������ʶ
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//�������
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//���ս��1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//���ս��2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
    
    var tClaimType=0;//ѡ��������������

    
    //��������
    for(var j=0;j<fm.claimType.length;j++)
    {   	     	  
    	  if(fm.claimType[j].checked == true)
    	  {
    		  tClaimType++;
          }
    }
    
    //5 �����������
    if (tClaimType==0)
    {
        alert("�������Ͳ���Ϊ�գ�");
        return false;
    }
    
    
    //������ҽ����������ҽ�Ƴ������ڿ���¼��
    if(fm.claimType[5].checked == true)
    {
        document.all('MedicalAccidentDate').disabled=false;
    }
    
    //������ҽ���������������������ڿ���¼��
    if((tClaimType>1)||((fm.claimType[5].checked == false)&&tClaimType==1))
    {
        document.all('OtherAccidentDate').disabled=false;
    }
}

//����Ϊ��������,��1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}

//---------------------------------------------------**
//���ܣ����������߼��ж�
//����1 �������߲С�ҽ������ֻ��ѡһ
//      2 ѡ��������߲�ʱ��Ĭ��ѡ�л���
//      3 ѡ�л���,����ѡ��������߲�֮һ(���ڱ���ʱ�ж�)
//---------------------------------------------------**
function callClaimType(ttype)
{
	
    switch (ttype)
    {
        case "02" : //����
            if (fm.claimType[0].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            else
//            {
//                if (fm.claimType[0].checked == true)
//                {
//                    fm.claimType[4].checked = true;
//                }
//            }
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "04" :
        	if (fm.claimType[2].checked == true)
        	{
        		fm.claimType[4].checked = true;
        	}
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("ѡȡ�������߲б���ѡ�����!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("�������߲С�ҽ������ֻ��ѡһ!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
        	
        	getChangeDate();
        
            return;
    }
}



/**
 * 2008-11-18
 * zhangzheng
 * ����ѡ����������;���ҽ�Ƴ������ں��������������Ƿ����¼��
 * 
**/
function getChangeDate()
{
	var flag=false;//�����Ƿ�׼��¼�������������ڱ�־,Ĭ�ϲ�׼��¼��
	
	//�������Ͱ���ҽ��ʱ,׼��¼��ҽ�Ƴ�������
	if(fm.claimType[5].checked == true)
	{
	    document.all('MedicalAccidentDate').disabled=false;
	}
	else
	{
	    document.all('MedicalAccidentDate').value="";
	    document.all('MedicalAccidentDate').disabled=true;
	}
	
	//��ֻ����ҽ������ʱ,�����������ڲ�׼��¼��
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    document.all('OtherAccidentDate').value="";
    	document.all('OtherAccidentDate').disabled=true;
    }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊȡ�������ͺ���������
    �� �� �ˣ���־��
    �޸����ڣ�2005.05.24
   =========================================================================
**/

function getClaimTypes(){
    var f=fm.elements;
    var types="";
    for (var i=0;i<f.length;i++){
    	if ((f[i].type=="checkbox")&&(f[i].checked)){
    	    if (types=="")
    	    	types=types+f[i].value;
    	    else
    	    	types=types+","+f[i].value;
        }
    }
    return types;
}

//�õ�0��icd10��
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//��ѯ���ս��
function queryResult(tCode,tName)
{/*
    var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";
    */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql13");
	mySql.addSubPara(document.all(tCode).value); 
	
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


//��ѯ����ҽԺ��Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{/*
	  var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";
	             */
	      mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql14");
	mySql.addSubPara(document.all(tCode).value); 
	//modify wyc �޸�ҳ�����û���������ҽԺ��Ϣ���ٴβ鿴��������ȫ����ѯ��ʾ�����
	if(document.all(tCode).value != "" && document.all(tCode).value != null ){
		var tICDName = easyExecSql(mySql.getString());
		
	}
	//end
	
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺴�ӡ��֤
    �� �� �ˣ�����
    �޸����ڣ�2005.07.28
    �޸ģ�2005.08.22����ӡ��֤ʱ��ǰ̨�жϡ��Ƿ���ڻ�����Ҫ���򡷣���ѯ��ˮ�ţ������̨��
   =========================================================================
**/
function showPrtAffix()
{
	fm.action = './LLPRTCertificatePutOutSave.jsp';  
	if(beforePrtCheck(fm.ClmNo.value,"PCT002")==false)
    {
    	return;
    } 
//	fm.target = "../f1print";
//	fm.submit();

	 
}

//��ӡ�˲м���֪ͨ��
function PrintMedCert()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("��ѡ��һ�м�¼��");
//        return;
//    }

    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")==false)
    {
    	return;
    } 
//    fm.target = "../f1print";
//    fm.submit();

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



/**********************************
//��ӡǰ���飨������Ҫ�����������֤���롢�ⰸ�ţ�--------2005-08-22���
***********************************/
function beforePrtCheck(clmno,code)
{
//	alert(clmno);return false;
	//��ѯ��֤��ˮ�ţ���Ӧ�������루�ⰸ�ţ����������͡���ӡ��ʽ����ӡ״̬�������־
   	var tclmno=trim(clmno);
   	var tcode =trim(code);
   	if(tclmno=="" ||tcode=="")
   	{
   		alert("������ⰸ�Ż�֤���ͣ����룩Ϊ��");
   		return false;
   	}
   	/*
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";
	*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql15");
	mySql.addSubPara(tclmno); 
	mySql.addSubPara(tcode); 
	
	var arrLoprt = easyExecSql(mySql.getString());
	if(arrLoprt==null)
	{
		alert("û���ҵ��õ�֤����ˮ��");
		return false;
	}	

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
	 		window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 		}
	}
	
}

//ҽԺģ����ѯ
function showHospital(tCode,tName)
{
	var strUrl="LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//����ϸ�ڲ�ѯ
function showAccDetail(tCode,tName)
{
	var strUrl="LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//��֤������ӡ����
function showPrtControl()
{
    var tClmNo = fm.RptNo.value;
    /*
	var strSQL="select count(1) from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode "
			+" and a.stateflag='3' and a.othernotype='05' and a.otherno='"+tClmNo+"'"
			+" order by a.code";
*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql16");
	mySql.addSubPara(tClmNo); 

	
	var arrCerti = easyExecSql(mySql.getString());
	if(arrCerti==null || arrCerti[0][0]=="0")
	{
		alert("û����Ҫ����������Ƶĵ�֤");
		return false;
	}	
    var strUrl="LLClaimCertiPrtControlMain.jsp?ClmNo="+tClmNo;
//    window.open(strUrl,"��֤��ӡ����");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����������˺ͱ�������ͬһ���˵Ļ�����ô�����˵���Ϣ�ӳ����˵�
              ��Ϣ�еõ�
    �� �� �ˣ������
    �޸����ڣ�2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;
   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "����")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
          //var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
          
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql17");
	mySql.addSubPara(tCustomerNo); 
	         
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
             fm.RptorAddress.value = strQueryResult[0][0];
             fm.RptorPhone.value = strQueryResult[0][1];
             fm.RptorName.value = fm.customerName.value;
          }
      }
      else
      {
          return;
      }
   }
   

   //�޸�ԭ������������˺ͳ����˹�ϵ��Ϊ��GX02-����������Ա��ʱ����ô�����˵���Ϣȡ����������Ա����Ϣ
   //�� �� �ˣ�zhangzheng 
   //�޸�ʱ�䣺2008-12-16
   //MSû�б���������Ա,ȥ������߼�
   
   //else if(tRelation == "GX02" || tRelationName == "����������Ա")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else
   {
       return;
   }
}

/*
 * ����У��,У���������ڶ��������ڵ�ǰ���ڣ��ҵ�2�����ڲ������ڵ�1������
 * Date1,����ĵ�һ������,�˴�Ϊ�¹�����
 * Date2 ����ĵڶ�������,�˴��������Ϊҽ�Ƴ������ڻ�������������
 * tDateName ������������ƣ�������ɵ�������ʾ����
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//�¹�����
    var AccDate2 =  tDate2;//������������
   
    //alert("AccDate:"+AccDate);
    //alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //����¹�����
    if (AccDate == null || AccDate == '')
    {
        alert("�������¹����ڣ�");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("�¹����ڲ������ڵ�ǰ����!");
            return false; 
        }
    }
    
    //��֤�¹��������������ϵĴ������ⰸ--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //��������ʱ����У���¹�����
    }    
    
    var OAccDate = fm.BaccDate.value;//ԭ�¹�����Modify by zhaorx 2006-07-04
   
    if (OAccDate != AccDate)
    {    /*
        var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"
       */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportInputSql");
	mySql.setSqlId("LLReportSql18");
	mySql.addSubPara(fm.AccNo.value); 
	       
        //prompt("��֤�¹��������������ϵĴ������ⰸ",strSQL);
        
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("�¹��������������ϵĴ������ⰸ���������޸��¹����ڣ�");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
    

    //У���������
    if (AccDate2 == null || AccDate2 == '')
    {
        alert(tDateName+"����Ϊ�գ�");
        return false;
    }
    else
    {
       	//�Ƚϳ������ں͵�ǰ����
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert(tDateName+"�������ڵ�ǰ����!");
            return false; 
        }

        //�Ƚϳ������ں��¹�����       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert(tDateName+"���������¹�����!");
            return false; 
        }

    }
    
    return true;
}
