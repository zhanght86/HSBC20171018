var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�����ҳ���ϱ����ݺ�����
function ClearPagedata()
{
	fm.UrgePayFlag.value="";//
	fm.NeedAcc.value="";//
	fm.PayTimes.value="";//
	fm.Rate.value="";//
	fm.PayStartDate.value="";//
	fm.PayEndDate.value="";//
	fm.PaytoDate.value="";//
	fm.PayIntv.value="";//
	fm.StandPrem.value="";//
	fm.Prem.value="";//
	fm.SumPrem.value="";//
	fm.SuppRiskScore.value="";//
	fm.FreeFlag.value="";//
	fm.FreeRate.value="";//
	fm.FreeStartDate.value="";
	fm.FreeEndDate.value="";//
	fm.State.value="";//
	fm.FreeFlagName.value="";//
	fm.FreeRateName.value="";//
	fm.StateName.value="";//
	fm.UrgePayFlagName.value="";//
	fm.PayIntvName.value="";//
	fm.ExemptReason.value="";
	fm.ExemptReasonname.value="";
	fm.ExemptDesc.value="";//
	
}

//��������Ϣ����ѯ������---�ڱ����ʾ���ⰸ����ϸ������Ϣ��¼
function LLClaimExemptGridQuery()
{
	/*var strSQL="select ClmNo,GrpContNo,ContNo,PolNo,DutyCode,PayPlanCode,PayPlanType,AppntType,AppntNo from llexempt"
		+" where 1=1 and clmno='"+fm.ClmNo.value+"'";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimExemptInputSql");
mySql.setSqlId("LLClaimExemptSql1");
mySql.addSubPara(fm.ClmNo.value ); 
	turnPage.queryModal(mySql.getString(),LLClaimExemptGrid);	
	var row=LLClaimExemptGrid.mulLineCount; //����
//	alert(row);
	if(row>0)
	{
		fm.exemptquery.disabled =true;//[��ȡ������Ϣ]��ť
	}
//    var arr = easyExecSql(strSQL);
//    if (arr == "" || arr == null) 
//    {
//       alert("���ⰸû�л�����Ϣ");
//       return;
//    }
//    else
//    {
//		displayMultiline(arr,LLClaimExemptGrid);
//    }			
}

//����ѯѡ�м�¼����ϸ������Ϣ��---��LLClaimExemptGridClick()����
function LLClaimExemptInfoQuery()
{
	/*var strSQL="select clmno,grpcontno,contno,polno,dutycode,payplancode,payplantype,appnttype,appntno"
		//---------------1------2--------3------4-------5---------6---------7-----------8--------9---------
		+",urgepayflag,needacc,paytimes,rate,paystartdate,payenddate,paytodate,payintv"
		//----10--------11------12-----13--------14---------15---------16--------17---------------------------------------		
		+",standprem,prem,sumprem,suppriskscore,freeflag,freerate,freestartdate,freeenddate,state,exemptreason,exemptdesc,ExemptPeriod"
		//---18------19---20-------21-----------22----------23--------24-----------25--------26----------------------------
		//+",managecom,operator,makedate,maketime,modifydate,modifytime,addfeedirect"
		//-----27-----28--------29-----30-------31----------32--------33-----------
		+" from llexempt where 1=1 "
		+" and clmno='"+fm.ClmNo.value+"' "
		+" and polno='"+fm.PolNo.value+"' " 
		+" and dutycode='"+fm.DutyCode.value+"' "
		+" and payplancode='"+fm.PayPlanCode.value+"'";
//		+" order by clmno,polno";	*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimExemptInputSql");
mySql.setSqlId("LLClaimExemptSql2");
mySql.addSubPara(fm.ClmNo.value ); 
mySql.addSubPara(fm.PolNo.value ); 	
mySql.addSubPara(fm.DutyCode.value ); 	
mySql.addSubPara(fm.PayPlanCode.value ); 		
	var arr = easyExecSql(mySql.getString());
    if (arr == "" || arr == null) 
    {
       alert("��ѯ����ϢΪ��!");
       return;
    }
    else
    {
    	ClearPagedata();//�������
		fm.UrgePayFlag.value=arr[0][9];//
		fm.NeedAcc.value=arr[0][10];//
		fm.PayTimes.value=arr[0][11];//
		fm.Rate.value=arr[0][12];//
		fm.PayStartDate.value=arr[0][13];//
		fm.PayEndDate.value=arr[0][14];//
		fm.PaytoDate.value=arr[0][15];//
		fm.PayIntv.value=arr[0][16];//
		fm.StandPrem.value=arr[0][17];//
		fm.Prem.value=arr[0][18];//
		fm.SumPrem.value=arr[0][19];//
		fm.SuppRiskScore.value=arr[0][20];//
		fm.FreeFlag.value=arr[0][21];//
		fm.FreeRate.value=arr[0][22];//
		fm.FreeStartDate.value=arr[0][23];//
		fm.FreeEndDate.value=arr[0][24];//
		fm.State.value=arr[0][25];//
		fm.ExemptReason.value=arr[0][26];//
		fm.ExemptDesc.value=arr[0][27];//
        fm.ExemptPeriod.value=arr[0][28];//
        
        var tExemptPeriod = fm.ExemptPeriod.value;
        var tPrem = fm.Prem.value;
        fm.ExemptSumAmnnt.value=tExemptPeriod * tPrem;
                
		showOneCodeName('FreeFlag','FreeFlag','FreeFlagName');
		showOneCodeName('FreeRate','FreeRate','FreeRateName');    
		showOneCodeName('State','State','StateName');	 
		showOneCodeName('UrgePayFlag','UrgePayFlag','UrgePayFlagName');    
		showOneCodeName('PayIntv','PayIntv','PayIntvName');	 	
		showOneCodeName('llexemptreason','ExemptReason','ExemptReasonname');	
    }			
}

//���⽻��־��Ӧ������---����⽻��־ѡΪ0���⽻�����⽻����, �⽻����, �⽻ֹ�ڲ���ʾ�����Ϊ1�⽻���⽻����, �⽻����, �⽻ֹ����Ҫ��ʾ
function FreeFlagClick()
{
	if(fm.FreeFlag.value=="1")
	{
		divLLClaimExemptFreeInfo.style.display="";
	}
	else
	{
		divLLClaimExemptFreeInfo.style.display="none";
		fm.FreeRate.value="";//
		fm.FreeStartDate.value="";
		fm.FreeEndDate.value="";//
		fm.FreeRateName.value="";//
		fm.ExemptReason.value="";//
		fm.ExemptReasonname.value="";//
		fm.ExemptDesc.value="";//
	}
}

//��������Ϣ���ѡť��Ӧ������---��ʾѡ�м�¼����ϸ������Ϣ
function LLClaimExemptGridClick()
{
//	alert("�����У�");
//	return;
	var selno = LLClaimExemptGrid.getSelNo()-1;
//	fm.ClmNo.value=LLClaimExemptGrid.getRowColData(selno,1);//<!--�ⰸ��-->  
	fm.PolNo.value=LLClaimExemptGrid.getRowColData(selno,4);//<!--�������ֺ���-->  
	fm.DutyCode.value=LLClaimExemptGrid.getRowColData(selno,5);//<!--���α���-->  
	fm.PayPlanCode.value=LLClaimExemptGrid.getRowColData(selno,6);//<!--���Ѽƻ�����-->  
	LLClaimExemptInfoQuery();//����ѯѡ�м�¼����ϸ������Ϣ��
	FreeFlagClick();//���⽻��־��Ӧ������
    

    divLLClaimExempt.style.display="";
}

//��������Ϣ��ť��----��ȡ������Ϣ
function LLExemptQueryClick()
{
	fm.exemptquery.disabled =true;
	fm.fmtransact.value="LLExempt||Get";//��û����¼
	fm.action="LLClaimExemptSave.jsp";
	submitForm();
}

//���������ݼ�⡿---���������Ϣ���޸��ύǰ���ݼ��
function BeforeSaveCheck()  
{
//	fm.PayIntv.value="0";
	if(fm.PayIntv.value=="0") // 0  ���� ����
	{
		alert("���Ѽ��Ϊ���ɣ����ܽ��л��������");
		return false;
	}
	if(fm.FreeFlag.value=="1")
	{
		if(fm.FreeStartDate.value=="" ||fm.FreeEndDate.value=="")
		{
			alert("�⽻���ʣ��⽻���ڻ��⽻ֹ�ڲ���Ϊ�գ�");
			return false;
		}
		if(dateDiff(fm.FreeEndDate.value,fm.FreeStartDate.value,'D') > 0)
	    {
	    	alert("�⽻���ڲ��������⽻ֹ�ڣ�");
	    	return false;
	    }
	    if(fm.ExemptReason.value=="" ||fm.ExemptDesc.value=="")
		{
			alert("����ԭ�򡢻�����������Ϊ�գ�");
			return false;
		}
	}
}

//�����水ť��---���������Ϣ���޸�
function LLExemptSaveClick()  
{
	var selno = LLClaimExemptGrid.getSelNo()-1;
	if(selno<0)
	{
		alert("��ѡ��һ�������¼��");
		return;
	}
	if(BeforeSaveCheck()==false){return;};
	fm.fmtransact.value="LLExempt||Save";//�޸ı�������¼��Ϣ
	fm.action="LLClaimExemptSave.jsp";
	submitForm();
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

//���ύ���ݺ����,���ء�-----���سɹ���ʧ����Ϣ��ԭ��
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


	   	fm.exemptquery.disabled =true;//[��ȡ������Ϣ]��ť
	   	ClearPagedata();//�����ҳ���ϱ����ݺ�����    
	    LLClaimExemptGridQuery();//��������Ϣ����ѯ������
    }
    tSaveFlag ="0";

}
