//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var arrDataSet;
var k = 0;
var tDate ;

//�ύ�����水ť��Ӧ����
function signCardPol()
{
	//getChkNo(row)
	var j;
	var ChkNum=0;//ѡ������
	var ChkSelNo;
	//alert("��������"+CardPolGrid.mulLineCount);
	//ѭ���ж�ѡ������
	for(j=1;j<=CardPolGrid.mulLineCount;j++)
	{
		if(ChkNum>=2) { break; }
		if(CardPolGrid.getChkNo(j-1)==true){ ChkNum++;}
	} 	
	//if(ChkNum==0 || ChkNum>=2)
	//{
	//	alert("û��ѡ��Ͷ������ѡ���˶��Ͷ������\nǩ��ʱֻ��ѡ��һ��Ͷ����������");
	//	return ;
	//}
	//ѭ���жϵڼ��б�ѡ��,��ȡ��ѡ���е�ֵ
	for(j=1;j<=CardPolGrid.mulLineCount;j++)
	{
		if(CardPolGrid.getChkNo(j-1)==true)
		{
			 //alert("ѡ���У�"+j);
			 fm.ProposalContNo.value=CardPolGrid.getRowColData(j-1,1);//Ͷ������
			 fm.ContCardNo.value=CardPolGrid.getRowColData(j-1,1);//����
			 break;
		}
	} 
	//alert("Ͷ�����ţ�"+fm.ProposalContNo.value+"\n"+"��Ӧ�� ���ţ�"+fm.ContCardNo.value);
	//�޸İ�Ŧ�ύ�¼�
    if (trim(fm.ProposalContNo.value) == "" || trim(fm.ContCardNo.value) == "")
    {
        alert("���Ż�Ͷ������Ϊ�գ��޷�ǩ����");
        return;
    }

	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	document.all("signbutton").disabled = true;
	document.getElementById("fm").submit(); //�ύ 

}

//��ѯǰ���Ĳ���
function beforeQuery()
{

    if (   ((document.all('AgentCode').value == '') || (document.all('AgentCode').value == null))
        && ((document.all('ContNo').value == '')    || (document.all('ContNo').value == null)) 
        && ((document.all('ManageCom').value == '') || (document.all('ManageCom').value == null))
        && ((document.all('AppntName').value == '') || (document.all('AppntName').value == null))
        && ((document.all('StartDate').value == '') || (document.all('StartDate').value == null)) 
        && ((document.all('EndDate').value == '')   || (document.all('EndDate').value == null))   
        && ((document.all('PrtNo').value == '')   || (document.all('PrtNo').value == null))   )
    {
    	if((document.all('StartDate').value == '') || (document.all('StartDate').value == null)){
    	   alert("��¼����ʼʱ��ͽ���ʱ�䣡");
    	   return false;
    	}
    }
    
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
	if (FlagStr == "Fail" )
	{   
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ; 
		//showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
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
		//showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();//ˢ��ҳ��
	}
	document.all("signbutton").disabled=false;
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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}


// ��ѯ��ť
function easyQueryClick()
{
	if (beforeQuery()==false) { return ;}
	CardPolGrid.clearData();
	var strOperate="like";
	
		var sqlid1="CardProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(ComCode);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
        mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
        mySql1.addSubPara(fm.AppntName.value);//ָ������Ĳ���
        
		mySql1.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.StartDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EndDate.value);//ָ������Ĳ���s
		
	    var strSql=mySql1.getString();	
	
//	var strSql = "select b.contno,b.forceuwreason,(select riskcode from lcpol where contno =b.contno),b.appntname,b.managecom,b.agentcode,b.makedate,b.maketime"
//			+ " from lccont b where b.appflag='0' and b.cardflag='4'" 
//			+ " and b.managecom like '"+ComCode+"%%'"
//			+ getWherePart('b.contno','ContNo',strOperate)
//			+ getWherePart('b.managecom','ManageCom',strOperate)
//			+ getWherePart('b.appntname','AppntName',strOperate)
//			+ getWherePart('b.agentcode','AgentCode',strOperate)
//			+ getWherePart('b.prtno','PrtNo',strOperate)
//			+ getWherePart('b.makedate','StartDate',">=")
//			+ getWherePart('b.makedate','EndDate',"<=")
//			//+ " and state not in('1001&&&&','1002&&&&')"  //������IE� & �����ű��ضϣ�����ת��Ϊ16���ƴ���
//			//+ " and state not in ('1001%26%26%26%26','1002%26%26%26%26')"  
//			+ " order by b.managecom,b.makedate,b.contno ";
	turnPage.queryModal(strSql,CardPolGrid);
}

//˫�� ҵ��Ա���� ����򴥷�����
function queryAgent()
{
	if(document.all('AgentCode').value == "")	
	{
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != "")
	{
		var cAgentCode = fm.AgentCode.value;  //ҵ��Ա���
		if(cAgentCode.length!=8) { return; }
		
			var sqlid1="CardProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(cAgentCode);//ָ������Ĳ���
		
	    var strSQL=mySql1.getString();	
		
//	    var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.branchtype in ('1','4') and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
       
	    var arrResult = easyExecSql(strSQL);
	    if (arrResult != null) 
	    {
	    	fm.AgentCode.value = arrResult[0][0];
	    }
	    else
	    {
		     //fm.AgentCode.value="";
		     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
	    }
	}
}
//�����˴����ѯ����ִ�к���
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
		//fm.BranchAttr.value = arrResult[0][10];
		//fm.AgentGroup.value = arrResult[0][1];
		//fm.AgentName.value = arrResult[0][3];
		//fm.AgentManageCom.value = arrResult[0][2];
  }
}
