//�������ƣ���LLSecondManuUW.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug          = "0";
var turnPage        = new turnPageClass();
var turnsubPage     = new turnPageClass();
var turnConfirmPage = new turnPageClass(); 
var turnsubPage1    = new turnPageClass();  
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var tResourceName="claim.LLSecondManuUWInputSql";

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
    fm.ContNo.value="";
    fm.MngCom.value="";
    fm.SaleChnl.value="";
    fm.AgentCode.value="";
    fm.AgentCom.value="";
    fm.Remark.value="";	
    fm.PrtNo.value = "";
    fm.AgentComName.value = "";
    fm.AgentName.value ="";
}

//[��ʼ����ѯ������ͬ�б�]
function initLLCUWBatchGridQuery()
{	
	/*var strSQL="select t.batno,t.caseno,t.contno,t.appntno,t.appntname,t.insuredno,t.insuredname,"
		+ " (select (select riskname from lmriskapp where riskcode=l.riskcode) from lcpol l where contno=t.contno and polno=mainpolno and appflag='1'), "
		+ " (select cvalidate from lccont where contno = t.contno),"
		+ " t.managecom,"
		+ " case t.claimrelflag when '0' then '���ⰸ���' when '1' then '���ⰸ�޹�' end, "
		+ " case a.appflag when '0' then 'Ͷ��' when '1' then '�б�' when '4' then '��ֹ' end"
		+ " from llcuwbatch t,lccont a where 1=1 "
		+ " and a.contno=t.contno "
		+ " and t.caseno='"+ document.all('tCaseNo').value +"' "
		+ " and t.batno='"+document.all('tBatNo').value +"'" 
		+ " order by t.contno";*/
	var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('tCaseNo').value,document.all('tBatNo').value]);
	//prompt("",strSQL);
    turnPage.queryModal(strSQL, LLCUWBatchGrid);	
    //var tSQL = "select remark1 from llcuwbatch where caseno='"+ document.all('tCaseNo').value +"' and batno='"+document.all('tBatNo').value +"'";
    var tSQL = wrapSql(tResourceName,"querysqldes2",[document.all('tCaseNo').value,document.all('tBatNo').value]);
    var arrResult = easyExecSql(tSQL);//prompt("",strLCcont);
    if(arrResult){
    	fm.InFo1.value=arrResult[0][0];
    }
}

//[LLCUWBatchGrid]�б�ѡť������Ӧ����     
function LLCUWBatchGridClick()
{
	clearPageData();
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	var tCaseNo=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,3);
	var tMissionID = fm.tMissionid.value;
	var tSubMissionID = fm.tSubmissionid.value;
	//var tMissionID = fm.tMissionid.value;
	//��ѯLLCUWMaster�����Ƿ��м�¼�����û�У�����������������κβ���
	//var tProducSql="select * from LLCUWMaster where caseno='"+tCaseNo+"' and contno='"+tContNo+"' and batno='"+tBatNo+"'";
	var tProducSql = wrapSql(tResourceName,"querysqldes3",[tCaseNo,tContNo,tBatNo]);
	turnConfirmPage.strQueryResult = easyQueryVer3(tProducSql, 1, 0, 1);//prompt("",tProducSql);
	if(!turnConfirmPage.strQueryResult){
		
		ProducUWMaster(tCaseNo,tContNo,tBatNo);
	}else{
		ContinueInitForm();
	}
	//MakeWorkFlow(tCaseNo,tContNo,tBatNo,tMissionID,tSubMissionID);
}
function ContinueInitForm(){
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	var tCaseNo=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,3);
	fm.ContNo.value = tContNo;//
	var tMissionID = fm.tMissionid.value;
	var tSubMissionID = fm.tSubmissionid.value;
	
	fm.tContNo.value= LLCUWBatchGrid.getRowColData(tSelNo,3);
	//alert(66);
	checkNotePad(tContNo);
	
	checkReport(tCaseNo);
	//[��ѯ��ͬ����ϸ��Ϣ]-------lccont��
	/*var strLCcont = "select contno,managecom,salechnl,agentcode,agentcom,"
	              + " remark,prtno from lccont where contno='"+tContNo+"'";*/
	var strLCcont = wrapSql(tResourceName,"querysqldes4",[tContNo]);
    var arrLCcont = easyExecSql(strLCcont);//prompt("",strLCcont);
    if(arrLCcont!=null)
    {
	    
 		fm.MngCom.value         = arrLCcont[0][1];//
	    fm.SaleChnl.value       = arrLCcont[0][2];//
	    fm.AgentCode.value      = arrLCcont[0][3];//
	    fm.AgentCom.value       = arrLCcont[0][4];//
	    fm.Remark.value         = arrLCcont[0][5];//
	    fm.PrtNo.value          = arrLCcont[0][6];//
	    //alert("Remark:"+fm.Remark.value);
	    showOneCodeName("salechnl","SaleChnl","SaleChnlName");
	    // �õ�����������
	    if(arrLCcont[0][4] == ""||arrLCcont[0][4] == null)
	    {
	   	   fm.AgentComName.value = "";
	    }else
	    {
	       //var sql = "select name from lacom where agentcom = '"+arrLCcont[0][4]+"'";//Modify by zhaorx 2006-12-15
	       var sql = wrapSql(tResourceName,"querysqldes5",[arrLCcont[0][4]]);
           var tName = easyExecSql(sql);
           if(tName!=null||tName!=""||tName!="null"){
           	fm.AgentComName.value = tName[0][0];
           }
	    }
	    //�õ������˵�����
	    if(arrLCcont[0][3] == ""||arrLCcont[0][3] == null)
	    {
	   	   fm.AgentName.value = "";
	    }else
	    {
	       //var sql = "select name from laagent where agentcode = '"+arrLCcont[0][3]+"'";
	       var sql = wrapSql(tResourceName,"querysqldes6",[arrLCcont[0][3]]);
           var tName = easyExecSql(sql);
           if(!(tName==null||tName==""||tName=="null")){
           	fm.AgentName.value = tName[0][0];
           }
	    }
    }
	//[��ѯ---Ͷ���齡����֪��ѯ�ʺ�,��콡����֪��ѯ�ʺ�,��Ӧδ��֪���,�����˽���״����֪����-]
	/*var strllcuwmatch="select healthimpartno1,healthimpartno2,noimpartdesc,remark1 from llcuwbatch where 1=1 "
				+" and caseno= '"+ tCaseNo +"'"
			    +" and batno= '"+ tBatNo+"'"
				+" and contno= '"+ tContNo +"'"; */
	var strllcuwmatch = wrapSql(tResourceName,"querysqldes7",[tCaseNo,tBatNo,tContNo]);
	var arrLLcuwatch=easyExecSql(strllcuwmatch);
	 if(arrLLcuwatch!=null)
    {
	 	fm.HealthImpartNo1.value = arrLLcuwatch[0][0];//Ͷ���齡����֪��ѯ�ʺ�
		fm.HealthImpartNo2.value = arrLLcuwatch[0][1];//��콡����֪��ѯ�ʺ�
		fm.NoImpartDesc.value    = arrLLcuwatch[0][2];//��Ӧδ��֪���
		fm.Remark1.value         = arrLLcuwatch[0][3];//�����˽���״����֪����
    }		
	
    //[��ѯ Ͷ������Ϣ]
    //���������޸�Ͷ���˲�ѯ���޸� by ���� 2006-10-12
    /*strLCAppnt="select a.appntno,a.appntname,a.appntsex,a.appntbirthday,a.occupationcode,"
        +" a.occupationtype,a.nativeplace,b.vipvalue,b.blacklistflag"
    	+" from lcappnt a,ldperson b where 1=1"
    	+" and b.customerno = a.appntno"
    	+" and a.contno='"+tContNo+"'";*/
   strLCAppnt = wrapSql(tResourceName,"querysqldes8",[tContNo]);
    var arrLCcont=easyExecSql(strLCAppnt);
    if(arrLCcont!=null)
    {
	 	document.all('AppntNo').value = arrLCcont[0][0];    	
	 	document.all('AppntName').value = arrLCcont[0][1];
		document.all('AppntSex').value = arrLCcont[0][2];
		showOneCodeName('sex','AppntSex','AppntSexName');
		document.all('AppntBirthday').value = calAge(arrLCcont[0][3]);
		document.all('OccupationCode').value = arrLCcont[0][4];
		document.all('NativePlace').value = arrLCcont[0][6];
		showOneCodeName('nativeplace','NativePlace','NativePlaceName');
  
		document.all('VIPValue').value = arrLCcont[0][7];
		document.all('BlacklistFlag').value = arrLCcont[0][8];    
		
		//��ѯְҵ����
	    //var sql1 = "select occupationname from ldoccupation where occupationcode = '"+arrLCcont[0][4]+"'";
	    var sql1 = wrapSql(tResourceName,"querysqldes9",[arrLCcont[0][4]]);
	    var result = easyExecSql(sql1);
	    if(result == "" || result == null)
	    {
	        document.all('OccupationCode').value = "";
	    }
	    else
	    {
	  	    document.all('OccupationCode').value = result[0][0];
	    }
	  	
	    document.all('OccupationType').value = arrLCcont[0][5];
	    showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
	    
	    //��ʾ������
	    DivForAppnt.style.display = "";
        divGroupPol2.style.display = "none"
	    	
    }
    else
    {
        /*var tGrpSql = "select customerno,name,postaladdress,zipcode,phone from lcgrpappnt "
                    + " where grpcontno in (select grpcontno from lccont where contno = '"+ tContNo +"')";*/
        var tGrpSql = wrapSql(tResourceName,"querysqldes10",[tContNo]);
        var arrGrpAppnt = easyExecSql(tGrpSql);
        document.all('GrpName').value = arrGrpAppnt[0][1];
        document.all('GrpAddress').value = arrGrpAppnt[0][2];
        document.all('GrpZipCode').value = arrGrpAppnt[0][3];
        document.all('Phone').value = arrGrpAppnt[0][4];
        
        //��ʾ������
        DivForAppnt.style.display = "none";
        divGroupPol2.style.display = "";
    }
    
    //��ӱ����˵���Ϣ
    getInsured();
    
   	// ��ѯ ѡ�к�ͬ  �ĺ˱��켣
	/*var strSQL=" select tt.caseno,tt.batno,tt.contno,tt.uwno,tt.passflag"
		+" ,(select codename from ldcode jj where jj.codetype='lluwsign' and code=tt.passflag)"
		+" ,tt.uwidea,tt.operator,tt.makedate,tt.maketime "
		+" from llcuwsub tt where 1=1 and tt.caseno='"+tCaseNo+"'"
		+" and tt.contno='"+tContNo+"' order by tt.batno desc ,tt.uwno desc ";*/
	var strSQL = wrapSql(tResourceName,"querysqldes11",[tCaseNo,tContNo]);
	turnsubPage.queryModal(strSQL,LLCUWSubGrid);
	/*var tUWFlagSql = "select passflag,uwidea from llcuwmaster where caseno='"+tCaseNo+"' and batno='"+tBatNo+"'"
					+ " and contno='"+tContNo+"'";*/
	var tUWFlagSql = wrapSql(tResourceName,"querysqldes12",[tCaseNo,tBatNo,tContNo]);
	var arrUWFlag = easyExecSql(tUWFlagSql);//prompt("",tUWFlagSql);
	if(arrUWFlag){
		fm.uwState.value=arrUWFlag[0][0];
		fm.UWIdea.value=arrUWFlag[0][1];
	}
}

//��������ѯ����ť
function showContQuery()
{
	//���ȼ����Ƿ�ѡ���¼  
	var row = LLCUWBatchGrid.getSelNo()-1;
    if (row < 0)
    {
    	alert("��ѡ��һ����¼��");
        return;
    } 
    var tContNo=fm.tContNo.value;//��ͬ��    
    var tPrtNo = fm.PrtNo.value;
    strUrl="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&IsCancelPolFlag=0";
    window.open(strUrl, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);  	  
}



//[Ͷ���˼���Ͷ����Ϣ]��ť
function showApp(cindex)
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("����ѡ�񱣵�!");
		return;
	}
	var cContNo=fm.tContNo.value;//alert(cContNo);
	var cAppntNo = fm.AppntNo.value;
	if(cindex=='1')
	{
		var cAppntNo = fm.AppntNo.value;
	}
	else if(cindex=='2')
	{
		var cAppntNo = fm.InsuredNo.value;
	}
//	var strUrl="../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1";
//	window.open(strUrl,"Ͷ���˼���Ͷ����Ϣ", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");	
	if (cindex == 1)
		  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
	 else  if (cindex == 2)
	 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+fm.InsuredNo.value+"&type=2","",sFeatures);
	 else
		  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);
}   
 
//[����������Ϣ]��ť
function enterRiskInfo()
{
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}
	var tContNo =LLCUWBatchGrid.getRowColData(tSelNo,3);	 
	var tInsuredNo=document.all('tInsuredNo').value;
	var tCaseNo=document.all('tCaseNo').value;
	var tBatNo=document.all('tBatNo').value;
    var strUrl="./LLSecondUWRiskMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&CaseNo="+tCaseNo+"&BatNo="+tBatNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//[ȡ��]��ť
function uwCancelClick()
{
	document.all('uwState').value     = "";
	document.all('uwStatename').value = "";	
	document.all('UWIdea').value      = "";
}

//[����]��ť
function turnBack()
{
//	top.opener.initSelfLLCUWBatchGridQuery();
//	top.close();
	//modify wyc
	try
    {
        top.close();
        top.opener.easyQueryClickSelf();
        top.opener.focus();
    }
    catch (ex) {}
 // end
	
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
	if(fm.uwState.value == "")
	{
		alert("����¼��б��˱�����!");      
		return ;
	}
	
	/*====================================================================
	 * ��ʼ��
	 * �޸�˵���������Ķ����ڼ䣬��������ĵ��黹û����ɣ�
	 *           ��ô�Ͳ������º˱�����
	 * �޸���  �������
	 * �޸�ʱ�䣺2005/11/23/15:40
	 *====================================================================*/
	/*var tSql = " select * from lwmission where  1=1 "
             + " and activityid in ('0000001106','0000001107','0000001108','0000001111','0000001112','0000001113','0000001130','0000001280','0000001290','0000001300','0000001140','0000001121','0000001114','0000001116','0000001120') "
             + " and missionid = '"+fm.tMissionid.value+"'"
             + " and submissionid = '"+fm.tSubmissionid.value+"'";*/
    var tSql = wrapSql(tResourceName,"querysqldes13",[fm.tMissionid.value,fm.tSubmissionid.value]);
    turnConfirmPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    if (turnConfirmPage.strQueryResult) 
    {
    	 //alert("����֪ͨ��û�лظ�, �������º˱�����")   
    	 //return ; 	
    }
	fm.action = "./LLSecondManuUWSave.jsp";
	submitForm(); //�ύ
}

//���˱���ɡ�---�������񣬽�����ֱ�ת��ͬ���С���
//�ⰸ��ر�־(ClaimRelFlag),0----��أ����񷵻�����ڣ�ֻ�������ڵ㣩
//�ⰸ��ر�־(ClaimRelFlag),1----�޹أ����񷵻ص���ȫ�ڡ��Ե�����������ʽ���ص���ȫ���С����������ڵ㣬���ɶ����ȫ�ڵ㣩
function llSecondUWFinish()
{
//	if(fm.lluwflag=="0"){
//		var tCheckSql="select * from llcuwmaster where caseno='"+fm.tCaseNo.value
//						+"' and batno='"+fm.tBatNo.value
//						//+"' and contno='"+mLLCUWBatchSet.get(i).getContNo()
//						+"' and passflag<>'5'";
//		turnConfirmPage.strQueryResult = easyQueryVer3(tCheckSql, 1, 0, 1);
//		if (turnConfirmPage.strQueryResult) 
//	    {
//	    	 if(!confirm("�Ѿ��к�ͬ�¹��˱����ۣ�ȷ���������⣿"))
//	    	 return ; 	
//	    }
//	}
	//�ڵ�����˱���ɡ�ʱ��ϵͳУ������Ѿ��������ּӷѽ��۵ģ��˱����Ƿ��˼ӷ�֪ͨ�飬��û�з���������ɺ˱����� 
	//Add by zhaorx 2006-03-01
    /*var tSQLF = " select * from lluwmaster b where 1=1 "
              + " and b.caseno= '"+fm.tCaseNo.value+"' and  b.contno= '"+fm.tContNo.value+"' and b.passflag='3'"; */
    var tSQLF = wrapSql(tResourceName,"querysqldes14",[fm.tCaseNo.value,fm.tContNo.value]);                         
    var tResultF = easyExecSql(tSQLF);
    if(tResultF != null)//�����мӷ�
    {
        /*var tSQLS = " select * from loprtmanager a where 1=1 and a.code ='LP83' "
                  + " and a.otherno= '"+fm.tContNo.value+"' and a.standbyflag2= '"+fm.tCaseNo.value+"'";*/
        var tSQLS = wrapSql(tResourceName,"querysqldes15",[fm.tContNo.value,fm.tCaseNo.value]);      	
    	var tResultS = easyExecSql(tSQLS);
    	if(tResultS == null)//δ���б��ӷ�֪ͨ��
    	{
    		//alert("�����ȷ��б��ӷ�֪ͨ�飬�ٵ�����˱���ɡ���ť��");
    	    //return;
    	}
    }          
              
	var tClaimRelFlag=fm.tClaimRelFlag.value;
	//alert("tClaimRelFlag=="+tClaimRelFlag);
	//return false;
	if(tClaimRelFlag=="0")  
	{
		fm.fmtransact.value="Finish||ToClaim";	
		fm.action = "./LLSecondManuUWFinishSave.jsp";
		submitForm(); //�ύ
	}
	if(tClaimRelFlag=="1")  
	{
		fm.fmtransact.value="Finish||ToWFEdor";
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
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";    
}

//���˱����ۡ�-----�ύ���ݺ����,���سɹ���ʧ����Ϣ��ԭ��
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
    }
    tSaveFlag ="0";
	uwCancelClick();//ˢ��ҳ��
	LLCUWBatchGridClick();
}


//���˱���ɡ�-----�ύ���ݺ����,���سɹ���ʧ����Ϣ��ԭ��
function UWFinishafterSubmit( FlagStr, content )
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
        tSaveFlag ="0";
		turnBack();
    }

}


//Ӱ���ѯ----�ο�֤���ļ�
function colQueryImage()
{
	var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('tCaseNo').value+"&subtype=LP1001";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//���֪ͨ¼��   
function showHealth()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}
	//var tSelNo2 = 
	var pContNo = LLCUWBatchGrid.getRowColData(tSelNo,3);
	var pBatchNo = LLCUWBatchGrid.getRowColData(tSelNo,1);//���κ�
	var pRgtNo = LLCUWBatchGrid.getRowColData(tSelNo,2);//�ⰸ��
    var pMissionID = fm.tMissionid.value;
    var pSubMissionID = fm.tSubmissionid.value;
    var pPrtNo = fm.PrtNo.value;
    var pFlag = "1";  
  
  	window.open("../uw/LLUWManuHealthMain.jsp?ContNo1="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo+"&Flag=2&BatchNo="+pBatchNo+"&RgtNo="+pRgtNo,"window1",sFeatures);
 
}

//������鱨��
function showRReport()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}

    var cContNo=LLCUWBatchGrid.getRowColData(tSelNo,3);
  
    var cMissionID =fm.tMissionid.value; 
    var cSubMissionID =fm.tSubmissionid.value;
    var cPrtNo = fm.PrtNo.value;
    window.open("../uw/UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID,"window2", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
                    
}  
                    
 
//��Ҫ�ʾ�֪ͨ��
function showUWNotice()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}

    var cContNo=LLCUWBatchGrid.getRowColData(tSelNo,3);
  
    var cMissionID =fm.tMissionid.value; 
    var cSubMissionID =fm.tSubmissionid.value;
    var cPrtNo = fm.PrtNo.value;
    window.open("../claimnb/LLUWPSendMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
                    
} 

//���ͺ˱�֪ͨ�顣
function SendAllNotice()
{
	
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}
    var cContNo = LLCUWBatchGrid.getRowColData(tSelNo,3);
    //�����ⰸ��  add by wanzh 2005/12/13 11:00 begin
    var cClmNo  = LLCUWBatchGrid.getRowColData(tSelNo,2);
    //�����ⰸ��  add by wanzh 2005/12/13 11:00 end
    var cMissionID =fm.tMissionid.value; 
    var cSubMissionID =fm.tSubmissionid.value;
    var cPrtNo = fm.PrtNo.value;
  
	window.open("../claimnb/LLUWPSendAllMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&ClmNo="+cClmNo,"window1",sFeatures);
}

/**************************************************************************
 * �޸�ԭ������ⰸ��ѯ
 * �޸���  �������
 * �޸�ʱ�䣺2005/11/25 15:10
 *************************************************************************/
function queryClaim()
{
    var i = LLCUWBatchGrid.getSelNo();
    
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLCUWBatchGrid.getRowColData(i,2);
        var strUrl = "LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
    else
    {
    	alert("��ѡ��һ����¼��");
        return;
    }
}
	
 /**************************************************************************
 * �޸�ԭ�����Ͷ���˼��������ѯ
 * �޸���  �������
 * �޸�ʱ�䣺2005/12/03 14:10
 *************************************************************************/
 function AppntQuery()
 {
 	var i = LLCUWBatchGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo   = LLCUWBatchGrid.getRowColData(i,2);
        var tAppntNo = LLCUWBatchGrid.getRowColData(i,4); //Ͷ���˺���
        //var tSQLF    = " select count(*) from lcgrpappnt where customerno='"+tAppntNo+"' ";
        var tSQLF = wrapSql(tResourceName,"querysqldes16",[tAppntNo]);
        var tCount   = easyExecSql(tSQLF);
        if(tCount>0)//Modify by zhaorx 2006-11-17
        {
        	  window.open("../uw/ClaimGrpQueryInput.jsp?CustomerNo="+tAppntNo,"window1",sFeatures);
        }
        else
        {
            var strUrl   = "../claimnb/LLUWQueryClaimMain.jsp?ClmNo="+tClmNo+"&transferNo="+tAppntNo+"&Flag=0";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
    }
    else
    {
    	alert("��ѡ��һ����¼��");
        return;
    }
 }

/**************************************************************************
 * �޸�ԭ����ӱ����˼��������ѯ
 * �޸���  �������
 * �޸�ʱ�䣺2005/12/03 14:10
 *************************************************************************/
 function InsuredQuery()
 {
 	var i = LLCUWBatchGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo     = LLCUWBatchGrid.getRowColData(i,2);
        var tInsuredNo = LLCUWBatchGrid.getRowColData(i,6); //�����˺���
        var strUrl     = "../claimnb/LLUWQueryClaimMain.jsp?ClmNo="+tClmNo+"&transferNo="+tInsuredNo+"&Flag=1";
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
    else
    {
    	alert("��ѡ��һ����¼��");
        return;
    }
 }


/**************************************************************************
 * �޸�ԭ����ӱ�������Ϣ��ѯ
 * �޸���  �������
 * �޸�ʱ�䣺2005/12/15 16:10
 *************************************************************************/
 function getInsured()
 {
 	initInsuredGrid();
 	var i = LLCUWBatchGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo     = LLCUWBatchGrid.getRowColData(i,3);
        var strSQL = "";
        /*strSQL = "select a.insuredno,a.name,case a.sex when '0' then '��' when '1' "
               + " then 'Ů' when '2' then '����' end,"
               + " (select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno),"
               + " (select codename from ldcode where trim(code) = trim(a.RelationToAppnt) and codetype = 'relation'),(select codename from ldcode where trim(code) = trim(RelationToMainInsured) and codetype = 'relation'), "
               + " (select codename from ldcode where trim(code) = trim(a.nativeplace) and codetype = 'nativeplace')"
               //+ " b.codename,a.idno "
               + " from lcinsured a,ldcode b where a.idtype = b.code and "
               + " b.codetype = 'idtype' and  a.contno = '"+tContNo+"'";*/
        strSQL = wrapSql(tResourceName,"querysqldes17",[tContNo]);
//        prompt("",strSQL);
        turnsubPage1.queryModal(strSQL,InsuredGrid);
    }
    else
    {
    	return;
    } 
    if(InsuredGrid.canSel==1&&InsuredGrid.mulLineCount>1)
    {
    			//alert('@@');
    			document.all('InsuredGridSel')[0].checked=true;
    			getInsuredDetail();
    			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
    			//getInsuredDetail();
    }
    if(InsuredGrid.canSel==1&&InsuredGrid.mulLineCount==1)
    {
    			//alert('@@');
    			document.all('InsuredGridSel').checked=true;
    			getInsuredDetail();
    			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
    			//getInsuredDetail();
    }
 }

/**************************************************************************
 * �޸�ԭ������������ѯ
 * �޸���  �������
 * �޸�ʱ�䣺2005/12/15 16:10
 *************************************************************************/
function showHealthResult()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("��ѡ��һ����ͬ��¼��");
		return;
	}
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,3);
	var tBatNo = fm.tBatNo.value;
	var tCaseNo = fm.tCaseNo.value;
	var tFlag = "1";

    window.open("../uw/LLUWManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag+"&BatNo="+tBatNo+"&CaseNo="+tCaseNo,"window1",sFeatures);
}



/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
   // var InsuredNo=document.all(parm1).all('PolAddGrid1').value;
   // alert("InsuredNo="+InsuredNo);
    var InsuredNo = InsuredGrid.getRowColData(InsuredGrid.getSelNo() - 1, 1);
   // alert("InsuredNo="+InsuredNo);
    var ContNo = LLCUWBatchGrid.getRowColData(LLCUWBatchGrid.getSelNo() - 1,3);
    document.all('InsuredNo').value=InsuredNo;//alert("InsuredNo=="+InsuredNo);
    //��������ϸ��Ϣ
//	 var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
//		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
//		  +" and tt.impartver='02' and tt.impartcode='000'";
//	 var ssArr=easyExecSql(ssql);
//	try
//	{
//	  if(ssArr!=null)
//	  {
//	  	 document.all('InsuredStature').value=ssArr[0][1];//���
//		 document.all('InsuredWeight').value=ssArr[1][1];//����	
//		 document.all('InsuredBMI').value=Math.round(parseFloat(ssArr[1][1])/parseFloat(ssArr[0][1])/parseFloat(ssArr[0][1])*10000);
//	  }	 	
//	}catch(ex){};
    
    if(ContNo!=null && ContNo!='')
    {
    	 //��ѯͶ���˵���ߡ����أ�������ʾ  
			 var nnPrtNo = document.all('PrtNo').value;
			if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//��ͥ��
			{
			  /*var sql = "select impartparamname, impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '1'"             
		         + " and impartcode = 'D0101'"
		         + " and impartver = 'D01'"
		         + " and impartparamno in ('1', '2')"
		         + " and contno = '"+ContNo+"'"
		         + " order by customernotype desc,customerno,impartparamno ";*/
		       var sql = wrapSql(tResourceName,"querysqldes18",[ContNo]);
			   var arr11 = easyExecSql(sql);
			   if(arr11!=null)
			   {
					document.all('InsuredStature').value=arr11[0][1];
					document.all('InsuredWeight').value=arr11[1][1];
					document.all('InsuredBMI').value=Math.round(parseFloat(arr11[1][1])/parseFloat(arr11[0][1])/parseFloat(arr11[0][1])*10000);
				}
				
				/*var insql = "select impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '1'"             
		         + " and impartcode = 'D0119'"
		         + " and impartver = 'D02'"
		         + " and impartparamno ='1'"
		         + " and contno = '"+ContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";*/
		         var insql = wrapSql(tResourceName,"querysqldes19",[ContNo]);		
			    var incomeway = easyExecSql(insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('Insuredincome').value = incomeway;
			    }
			}
			else
			{
				 /*var sql = "select impartparamname, impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '0'"         
		         + " and impartcode = 'A0101'"
		         + " and impartver = 'A01'"
		         + " and impartparamno in ('3', '4')"
		         + " and contno = '"+ContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";*/
		         var sql = wrapSql(tResourceName,"querysqldes20",[ContNo]);
			   var arr11 = easyExecSql(sql);
			   if(arr11!=null)
			   {
					document.all('InsuredStature').value=arr11[0][1];
					document.all('InsuredWeight').value=arr11[1][1];
					document.all('InsuredBMI').value=Math.round(parseFloat(arr11[1][1])/parseFloat(arr11[0][1])/parseFloat(arr11[0][1])*10000);
				}
				
				/*var insql = "select impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '0'"             
		         + " and impartcode = 'A0120'"
		         + " and impartver = 'A02'"
		         + " and impartparamno ='3'"
		         + " and contno = '"+ContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";*/
		         var insql = wrapSql(tResourceName,"querysqldes21",[ContNo]);		
			    var incomeway = easyExecSql(insql);//prompt("",insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('Insuredincome').value = incomeway;//��ѯ������
			    }
			}
	 }
	
	//���㱻���˷��ձ���������� 2009-06-09 
	//alert(InsuredNo); 
    var tSumAmnt =0;
    //var strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','1','1'),0) from dual ";
    var strSQL = wrapSql(tResourceName,"querysqldes22",[InsuredNo]);
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
	}
	 
	 //strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','2','1'),0) from dual ";
	 strSQL = wrapSql(tResourceName,"querysqldes23",[InsuredNo]);
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
	}
	
	 //strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','3','1'),0) from dual ";//prompt("",strSQL);
	 strSQL = wrapSql(tResourceName,"querysqldes24",[InsuredNo]);
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
	//strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','4','1'),0) from dual ";
	strSQL = wrapSql(tResourceName,"querysqldes25",[InsuredNo]);
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;
	
    //�ۼƱ��� �����������Ͳ����ڽ�����   
    /*strSQL =  "SELECT decode(sum(a_Prem),'','x',sum(a_Prem)) FROM"
          + "(select (case"
          + " when s_PayIntv = '1' then"
          + " s_Prem/0.09"
          + " when s_PayIntv = '3' then"
          + " s_Prem/0.27"
          + " when s_PayIntv = '6' then"
          + " s_Prem/0.52"
          + " when s_PayIntv = '12' then"
          + " s_Prem"
          + " end) a_Prem"          
          + " FROM (select distinct payintv as s_PayIntv,"
          + " sum(prem) as s_Prem"
          + " from lcpol c where polno in(select polno "
	          + " from lcpol a"
	          + " where a.insuredno = '"+InsuredNo+"'"
	          + " or (a.appntno = '"+InsuredNo+"' and a.riskcode in ('00115000','00115001'))"
	          + " union"
	          + " select b.polno"
	          + " from lcinsuredrelated b"
	          + " where b.customerno = '"+InsuredNo+"')"
          + " and c.payintv in ('1', '3','6','12')"
          + " and c.uwflag not in ('1', '2', 'a')"
          + " and c.appflag <> '4'" 
          + " and not exists (select 'X'"
	          + " from lccont"
	          + " where ContNo = c.contno"
	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
          + " group by payintv))";*/
    strSQL = wrapSql(tResourceName,"querysqldes26",[InsuredNo]);
    //prompt('',strSQL);
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x'){
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	       }else {
	    	   document.all('InsuredSumPrem').value ='0';
	       }
	}
	      
//    //��ѯ������
//    var insql = "select impartparam from LCCustomerImpartParams where impartparamname = 'AnnualIncome' and customernotype = '1' and customerno='"+InsuredNo+"' and contno='"+ContNo+"'";
//    //prompt("",insql);
//    var incomeway = easyExecSql(insql);
//    if(incomeway!=null && incomeway !="")
//    {
//       document.all('Insuredincome').value = incomeway;
//    }
    /*strSQL ="select a.sequenceno,a.relationtomaininsured,a.name,a.occupationcode,a.occupationtype,b.BlacklistFlag"
           + " from LCInsured a,LDPerson b where a.ContNo = '"+ContNo+"' and a.InsuredNo='"+InsuredNo+"'"
           + "and b.CustomerNo = a.InsuredNo";*/    
    strSQL = wrapSql(tResourceName,"querysqldes27",[ContNo,InsuredNo]); 
    arrResult=easyExecSql(strSQL);//prompt("",strSQL);
    if(arrResult!=null)
    {
        DisplayInsured(ContNo,InsuredNo);
    }
//    initPolRisk(ContNo,InsuredNo); 
    //��ʱִ�У�Ϊ��ʼ������lluwmaster�Ⱥ˱�������ʱ��
    setTimeout("initPolRisk(" + ContNo + ","+InsuredNo+")",1000); 
}


function DisplayInsured(tContNo,tInsuredNo)
{
	//InsuredName/InsuredOccupationCode/InsuredOccupationType/Insuredincome
	  //alert("asdfasfsf");
	  try{document.all('SequenceNoCode').value= arrResult[0][0]; }catch(ex){};
	  try{document.all('RelationToMainInsured').value= arrResult[0][1]; }catch(ex){};
    try{document.all('InsuredName').value= arrResult[0][2]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][3]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][4]; }catch(ex){};
    document.all('InsuredBlacklistFlag').value = arrResult[0][5];
    showOneCodeName('relation','RelationToMainInsured','RelationToMainInsuredName');//�Ա�
   //���Ӳ�ѯְҵ��� 
    /*var strSql = "select a.Name"
        +" ,(select occupationcode||'-'||occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
        +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
        +" ,a.InsuredNo "
            +" from lcinsured a where 1=1"
            +" and a.ContNo='"+tContNo+"'"
            +" and a.InsuredNo='"+tInsuredNo+"'";*/
    var strSql = wrapSql(tResourceName,"querysqldes28",[tContNo,tInsuredNo]);
//	prompt('',strSql);
	brr = easyExecSql(strSql, 1, 0,"","",1);
	if (brr)
	{
	  //brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
	  brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
	  brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
	  //brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
	}
	//quickGetName('blacklistflag',fm.InsuredBlacklistFlag,fm.InsuredBlacklistFlagName);
    //quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    //quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    //quickGetName('SequenceNo',fm.SequenceNoCode,fm.SequenceNoName);
    //quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
}

function initPolRisk(contno,insuredno)
{
	var tClmno=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
	var tContNo = LLCUWBatchGrid.getRowColData(LLCUWBatchGrid.getSelNo() - 1,3);
	var InsuredNo = InsuredGrid.getRowColData(InsuredGrid.getSelNo() - 1, 1);
	initPolRiskGrid();
	//alert("contno:"+contno);
	/*var strSQL = "select riskcode,(select riskname from lmrisk where riskcode=a.riskcode),"
	         + " amnt,mult,insuyear || a.insuyearflag,payyears, "
           + " payintv,standprem,"
           + " nvl((select sum(prem) from lluwpremmaster where polno=a.polno and clmno='"+tClmno
           + "' and payplancode like '000000%%' and payplantype='02'),0) ,"
           + " nvl((select sum(prem) from lluwpremmaster where polno=a.polno and clmno='"+tClmno
           + "' and payplancode like '000000%%' and payplantype='01'),0) ,"
           + " a.polno ,"
           + " nvl((select sum(prem) from lluwpremmaster where polno=a.polno and clmno='"+tClmno
           + "' and payplancode like '000000%%' and payplantype='03'),0) ,"
           + " nvl((select sum(prem) from lluwpremmaster where polno=a.polno and clmno='"+tClmno
           + "' and payplancode like '000000%%' and payplantype='04'),0), "
           + " a.CValiDate,a.EndDate,a.PayIntv,a.PayYears,"
           + " a.UWFlag, (select passflag from lluwmaster where polno=a.polno and batno=b.batno),"	
           + " (select uwflag from lcpol where polno = a.polno) "                        
           + " from lcpol a,lluwmaster b "
           + " where a.contno='"+tContNo+"' "
           + " and a.polno=b.polno"
           + " and b.caseno='"+tClmno+"'"
           + " and b.batno='"+tBatNo+"'"
           + " and a.insuredno='"+InsuredNo+"'"
           + " and a.appflag='1'";*/
     var strSQL = wrapSql(tResourceName,"querysqldes29",[tClmno,tContNo,tBatNo,InsuredNo]);
//   prompt('',strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);0
    alert("û��δͨ������Լ�˱��ĸ��˺�ͬ����");
    return;
  }
	
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolRiskGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  

}


function NewChangeRiskPlan()
{
	//var prtSql="select prtNo from lccont where contno='"+fm.ContNo.value+"'";
    //var arrResult=easyExecSql(strSQL);//prompt("",strSQL);
    var PrtNo = fm.PrtNo.value;
    var ContNo = fm.ContNo.value;
    var MissionID = fm.tMissionid.value;
    var SubMissionID = fm.tSubmissionid.value;
    //alert("ContNo=="+ContNo);
    if(arrResult){
    	
    }
    var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    
    /*strSql = "select case lmriskapp.riskprop"
            +" when 'I' then '1'"
	        +" when 'G' then '2'"
	        +" when 'Y' then '3'"
	        +" when 'T' then '5'"
           + " end"
           + " from lmriskapp"
           + " where riskcode in (select riskcode"
           + " from lcpol"
           + " where polno = mainpolno"
           + " and prtno = '"+PrtNo+"')"*/    
    strSql = wrapSql(tResourceName,"querysqldes30",[PrtNo]);       
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
        /*strSql = " select * from ("
               + " select case missionprop5"
               + " when '05' then '3'"
               + " when '12' then '3'"
               + " when '13' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+PrtNo+"'"
               + " and activityid = '0000001099'"
               + " union"
               + " select case missionprop5"
               + " when 'TB05' then '3'"
               + " when 'TB12' then '3'"
               + " when 'TB06' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+PrtNo+"'"
               + " and activityid = '0000001098'"
               + ") where rownum=1";*/
        strSql = wrapSql(tResourceName,"querysqldes31",[PrtNo]);
        arrResult = easyExecSql(strSql);               
    }
    if(arrResult){
    	var BankFlag = arrResult[0][0];
    }
    //alert("BankFlag="+arrResult[0][0]); 
    
    
    //var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+PrtNo+"'";
    var strSql2 = wrapSql(tResourceName,"querysqldes32",[PrtNo]);
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    var tCaseNo=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
     var NoType = "1";
	var InsuredNo = document.all('InsuredNo').value;
	window.open("../uw/LLUWChangeRiskPlanMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&InsuredSumLifeAmnt="+fm.InsuredSumLifeAmnt.value+"&InsuredSumHealthAmnt="+fm.InsuredSumHealthAmnt.value+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3&ClmNo="+tCaseNo+"&BatNo="+tBatNo,"window1",sFeatures);  	

}

function ProducUWMaster(tCaseNo,tContNo,tBatNo){//alert(981);
	fm.action = "../claim/LLMakeUWMaster.jsp?ContNo="+tContNo+"&CaseNo="+tCaseNo+"&BatNo="+tBatNo;
    document.getElementById("fm").submit();
}

function afterSubmit2( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
  if (FlagStr == "Fail" )
  {
    alert(content);
    return false;
  }
  else
  {
	  ContinueInitForm();
  }
}

//�����¼��
function QuestInput()
{
	//if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	//{
	//	return false;
	//}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	tMissionID = fm.tMissionid.value;
	tFlag = "7";  //����˱���־   wyc
	tSubMissionID = fm.tSubmissionid.value;
    cContNo = fm.tContNo.value;  //��������
    var tBatNo = fm.tBatNo.value;
    var tCaseNo = fm.tCaseNo.value;
        if(cContNo == "")
        {
            alert("��ѡ��һ����ͬ!");
        }
        else
        {
            window.open("../uw/LLMSQuestInputMain.jsp?ContNo="+cContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BatNo="+tBatNo+"&CaseNo="+tCaseNo+"&Flag="+tFlag,"window1",sFeatures); //wyc
        }
}
//��ʼ������������˱����۽ڵ�
function MakeWorkFlow(tCaseNo,tContNo,tBatNo,tMissionID,tSubMissionID){//alert(981);
	fm.action = "../claim/LLMakeUWWorkFlow.jsp?ContNo="+tContNo+"&CaseNo="+tCaseNo+"&BatNo="+tBatNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    document.getElementById("fm").submit();
}

//���ͺ˱�֪ͨ��
function SendAllNotice(){	//alert("fm.SubMissionID.value=="+fm.SubMissionID.value);
   	var tBatNo = fm.tBatNo.value;
    var tCaseNo = fm.tCaseNo.value;
    window.open("../uw/LLSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+fm.tMissionid.value+"&ClmNo="+tCaseNo+"&BatNo="+tBatNo,"window1",sFeatures);
}

//�˱���������
function showUWReport(){
	window.open("../uw/UWReportMain.jsp?ContNo="+fm.tContNo.value+"&NoType=4&OperatePos=4&ClmNo="+fm.tCaseNo.value,"window5",sFeatures);
}

//�������ѯ
function QuestQuery(){
	cContNo = fm.tContNo.value;  //��������


  if (cContNo != ""){
//  	var tSelNo = PolAddGrid.getSelNo()-1;
//  	QuestQueryFlag[tSelNo] = 1 ;
//	  alert(fm.tBatNo.value);return false;
	window.open("../uw/LLQuestQueryMain.jsp?ContNo="+cContNo+"&Flag=1&ClmNo="+fm.tCaseNo.value+"&BatNo="+fm.tBatNo.value,"window2",sFeatures);
  }else{
  	alert("����ѡ�񱣵�!");
  }

}

//������֪��ѯ
function queryHealthImpart(){

    //alert(document.all('AppntNo').value);
	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//���±�
function showNotePad()
{	
	var ActivityID = "0000005505";		
  var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	//������˵������ԣ��������Ӵ���������˵ı��   09-06-23
	var ClaimType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "&MissionID="+ fm.tCaseNo.value + "&SubMissionID="+ fm.tSubmissionid.value + "&ActivityID="+ ActivityID + "&PrtNo="+ fm.tContNo.value + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/ClaimWorkFlowNotePadFrame.jsp?Interface=../uw/ClaimWorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

//��ѯ���±�
function checkNotePad(prtNo){
	
	/*var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"' "
	+"and missionid='"+fm.tCaseNo.value+"'";*/
	var strSQL = wrapSql(tResourceName,"querysqldes33",[prtNo,fm.tCaseNo.value]);
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}

//��ѯ�˱���������
function checkReport(clmno){
	
	//var strSQL="select count(*) from LCUWReport where otherno='"+clmno+"'";
	var strSQL = wrapSql(tResourceName,"querysqldes34",[clmno]);
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('ReportButton').value='�˱���������鿴 ����"+arrResult[0][0]+"����'");
	
}

//�����˽�����֪��ѯ
function queryInsuredHealthImpart(){

	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1",sFeatures);
}

function showSpec(tindex)
{
	var ContNo = fm.tContNo.value;
	//var tSql = "select prtno from lccont where contno='"+ContNo+"'";
	var tSql = wrapSql(tResourceName,"querysqldes35",[ContNo]);
	var PrtNo = easyExecSql(tSql);
	var MissionID = fm.tMissionid.value;
	var SubMissionID = fm.tSubmissionid.value;
  var InsuredNo = document.all('tInsuredNo').value;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("../uw/UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&tIndex="+tindex+"&QueryFlag=0","window1",sFeatures);  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("���ݴ������!");
  }
}

//���ҽԺƷ�ʹ���
function HospitalQuality() {
  var ContNo = fm.tContNo.value;	
  var PrtNo = fm.PrtNo.value;
  var ClmNo = fm.tCaseNo.value;
  var BatNo = fm.tBatNo.value;

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/LLUWHospitalQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&ClmNo="+ClmNo+"&BatNo="+BatNo, "window1",sFeatures);
  }
}

//ҵ��ԱƷ�ʹ���
function AgentQuality() {
  ContNo = document.all('tContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWAgentQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}

//�ͻ�Ʒ�ʹ���
function CustomerQuality() {
  ContNo = document.all('ContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWCustomerQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}
