<%
//�������ƣ�GroupPolInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
function initInpBox()
{
	try 
	{
		document.all('ProposalGrpContNo').value = prtNo;
		document.all('GrpContNo').value = prtNo;
		document.all('prtNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		document.all('NoType').value = NoType;
		//��ʼ���������������
		document.all('SaleChnl').value = '01';

		//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
		if (BQFlag=="2")
		{
			addClick();
		}
	}
	catch(ex)
	{
		alert("��GroupPolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initMissionID();
		initInpBox();
		initRiskGrid();
		
		//initHistoryImpartGrid();
		//initDiseaseGrid();
		//initImpartGrid();
		//initServInfoGrid();
		initMultiAgentGrid();
		initPlanGrid();
		initPlanRiskGrid();
		
//alert("a");		
		//alert("LoadFlag=="+LoadFlag);
		if(this.ScanFlag == "1"||this.ScanFlag == "0")
		{
			fm.PrtNo.readOnly=true;
// 			var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '" + prtNo + "'", 1, 0);
			
			var sqlid1="ContPolInitSql0";
			var mySql1=new SqlClass();
			mySql1.setResourceName("app.ContPolInitSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(prtNo);//ָ������Ĳ���
			var strSql1=mySql1.getString();
			var arrResult = easyExecSql(strSql1, 1, 0);
					
			//��ʾ����ѡ������
			if (arrResult != null)
			{
				polNo=arrResult[0][0];
				
			}
		}
//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
		
		
		
		
		if(this.LoadFlag == "2")
		{
			if(polNo!="")
			{
				//��ʾ����ѡ������
				parent.fraInterface.showCodeName();             
				getapproveinfo();

			}
			divnormalquesbtn.style.display="";
			divUpdateButton.style.display="none";
		
    		//���뺺���ķ��Ժ���
    		//alert("1");
    		//��ѯ������Ч����
    		//QueryCValiDate();
    		//��ѯ�����շ�����
    		QueryChargeDate();  
    		//alert("2");
    		showCodeName();
    		//alert("3");
    			    try
	    {
	    	if(scantype=="scan")
	    	{
	    		//alert("4");
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	//alert("1");
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
    		return;
		}
		if(this.LoadFlag == "4")
		{
			//��ʾ����ѡ������
			parent.fraInterface.showCodeName();
			divRiskDeal.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			divGroupPol4.style.display="none";
			divapprovenormalbtn.style.display="";
			divapprovebtn.style.display="";
			divUpdateButton.style.display="none";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    		//alert("2");
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		if(this.LoadFlag == "6")
		{
			//��ʾ����ѡ������
			parent.fraInterface.showCodeName();
			divButton.style.display="none";
			divchangplanbtn.style.display="none";
			divnormalbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divUpdateButton.style.display="none";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
			
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
	    
		  return;
		}
		if(this.LoadFlag == "13")
		{
			//��ʾ����ѡ������
			// parent.fraInterface.showCodeName();
			divchangplanbtn.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="";
			divapprovenormalbtn.style.display="none";
			divUpdateButton.style.display="";
			divapproveupdatebtn1.style.display="";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		if(this.LoadFlag == "14")
		{
			//��ʾ����ѡ������
			// parent.fraInterface.showCodeName();
			divchangplanbtn.style.display="none";
			divButton.style.display="";
			divnormalbtn.style.display="";
			divuwupdatebtn.style.display="";
			//divapprovenormalbtn.style.display="";
			//divapproveupdatebtn.style.display="";
			divUpdateButton.style.display="none";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		if(this.LoadFlag == "16")
		{
			//��ʾ����ѡ������
			divRiskDeal.style.display="none";
			parent.fraInterface.showCodeName();
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divquerybtn.style.display="";
			divUpdateButton.style.display="none";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		if(this.LoadFlag == "99")
		{
			//��ʾ����ѡ������
			divRiskDeal.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			autoMoveButton.style.display="";
			divUpdateButton.style.display="none";
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		if(this.LoadFlag =="23")
		{
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divapproveupdatebtn.style.display="none";
			divchangplanbtn.style.display="";
			divUpdateButton.style.display="none";
			getapproveinfo();
		  //���뺺���ķ��Ժ���
		  showCodeName();
		  
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3�����з����쳣:��ʼ���������!");
	    }
		  return;
		}
		
		
	}
	catch(re)
	{
		alert("GroupPolInputInit.jsp-->InitForm1�����з����쳣:��ʼ���������!"+re);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="160px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="��������|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="�����ڼ�";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		iArray[3][9]="���Ѻϼ�|NUM&NOTNULL";

		iArray[4]=new Array();
		iArray[4][0]="Ӧ������";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;
		iArray[4][9]="Ԥ�ƽ��ѽ��|NUM&NOTNULL";

		iArray[5]=new Array();
		iArray[5][0]="�α�����";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		iArray[5][9]="���ѽ��|NUM&NOTNULL";
		
		iArray[6]=new Array();
		iArray[6][0]="Ԥ�ڱ���";
		iArray[6][1]="80px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		iArray[6][9]="���Ѻϼ�|NUM&NOTNULL";

		iArray[7]=new Array();
		iArray[7][0]="���Ѻϼ�";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		iArray[7][9]="���Ѻϼ�|NUM&NOTNULL";
		
		iArray[8]=new Array();
		iArray[8][0]="Ԥ�ڱ���/����";
		iArray[8][1]="80px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		iArray[8][9]="���Ѻϼ�|NUM&NOTNULL";
		
		iArray[9]=new Array();
		iArray[9][0]="�����ϼ�";
		iArray[9][1]="50px";
		iArray[9][2]=20;
		iArray[9][3]=0;
		iArray[9][9]="���Ѻϼ�|NUM&NOTNULL";
		
		iArray[10]=new Array();
		iArray[10][0]="����ϼ�";
		iArray[10][1]="80px";
		iArray[10][2]=20;
		iArray[10][3]=0;
		iArray[10][9]="���Ѻϼ�|NUM&NOTNULL";
		
		iArray[11]=new Array();
		iArray[11][0]="Ͷ������";
		iArray[11][1]="50px";
		iArray[11][2]=20;
		iArray[11][3]=0;
		iArray[11][9]="���Ѻϼ�|NUM&NOTNULL";
		
		iArray[12]=new Array();
		iArray[12][0]="����";          
		iArray[12][1]="50px";              
		iArray[12][2]=60;             
		iArray[12][3]=2;               
		iArray[12][4]="Currency";                 
		iArray[12][9]="����|code:Currency";

		

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//��Щ���Ա�����loadMulLineǰ
		RiskGrid.mulLineCount = 0;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}


// ������Ϣ�б�ĳ�ʼ��
function initPlanGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��Ʒ��ϱ���";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��Ʒ�������";
		iArray[2][1]="160px";
		iArray[2][2]=50;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����ڼ�";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ӧ������";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="�α�����";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="Ԥ�ڱ���";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="���Ѻϼ�";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="Ͷ������";
		iArray[8][1]="140px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="����";          
		iArray[9][1]="50px";              
		iArray[9][2]=60;             
		iArray[9][3]=2;               
		iArray[9][4]="Currency";                 
		iArray[9][9]="����|code:Currency";
		

		PlanGrid = new MulLineEnter( "fm" , "PlanGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.selBoxEventFuncName = "getPlanRiskDetail"; 
		PlanGrid.canSel =1;
		PlanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		PlanGrid.hiddenSubtraction=1;
		PlanGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initPlanRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="0px";
		iArray[2][2]=20;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���α���";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="100px";
		iArray[4][2]=20;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="���Ѻϼ�";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����ϼ�";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		
		
		iArray[7]=new Array();
		iArray[7][0]="����";          
		iArray[7][1]="50px";              
		iArray[7][2]=60;             
		iArray[7][3]=2;               
		iArray[7][4]="Currency";                 
		iArray[7][9]="����|code:Currency";
		

		PlanRiskGrid = new MulLineEnter( "fm" , "PlanRiskGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PlanRiskGrid.mulLineCount = 0;
		PlanRiskGrid.displayTitle = 1;
		PlanRiskGrid.canSel =0; 
		PlanRiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		PlanRiskGrid.hiddenSubtraction=1;
		PlanRiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

//�����֪����
function initImpartGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��֪���";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=2;
		iArray[1][4]="GrpImpartVer";
		iArray[1][9]="��֪���|len<=5";
		iArray[1][18]=300;

		iArray[2]=new Array();
		iArray[2][0]="��֪����";
		iArray[2][1]="60px";
		iArray[2][2]=60;
		iArray[2][3]=2;
		iArray[2][4]="ImpartCode";
		iArray[2][5]="2|3";
		iArray[2][6]="0|1";
		//iArray[2][7]="getImpartCode";
		iArray[2][9]="��֪����|len<=4";
		iArray[2][15]="ImpartVer";
		iArray[2][17]="1";
		iArray[2][18]=700;

		iArray[3]=new Array();
		iArray[3][0]="��֪����";
		iArray[3][1]="250px";
		iArray[3][2]=200;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="��д����";
		iArray[4][1]="150px";
		iArray[4][2]=150;
		iArray[4][3]=1;
		iArray[4][9]="��д����|len<=200";
		ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ImpartGrid.mulLineCount = 0;
		ImpartGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		ImpartGrid.loadMulLine(iArray);

		//��Щ����������loadMulLine����
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initHistoryImpartGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�����ڼ�";
		iArray[1][1]="50px";
		iArray[1][2]=60;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="�����ڼ��־";
		iArray[2][1]="65px";
		iArray[2][2]=1;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="75px";
		iArray[3][2]=300;
		iArray[3][3]=1;


		iArray[4]=new Array();
		iArray[4][0]="����";
		iArray[4][1]="30px";
		iArray[4][2]=200;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="����";
		iArray[5][1]="30px";
		iArray[5][2]=300;
		iArray[5][3]=1;

		iArray[6]=new Array();
		iArray[6][0]="�μ�����";
		iArray[6][1]="50px";
		iArray[6][2]=20;
		iArray[6][3]=1;

		iArray[7]=new Array();
		iArray[7][0]="��������";
		iArray[7][1]="50px";
		iArray[7][2]=20;
		iArray[7][3]=1;

		iArray[8]=new Array();
		iArray[8][0]="�������";
		iArray[8][1]="50px";
		iArray[8][2]=20;
		iArray[8][3]=1;

		iArray[9]=new Array();
		iArray[9][0]="�������";
		iArray[9][1]="50px";
		iArray[9][2]=20;
		iArray[9][3]=1;

		iArray[10]=new Array();
		iArray[10][0]="δ�����";
		iArray[10][1]="50px";
		iArray[10][2]=20;
		iArray[10][3]=1;

		iArray[11]=new Array();
		iArray[11][0]="��ˮ��";
		iArray[11][1]="0px";
		iArray[11][2]=20;
		iArray[11][3]=3;
		
		iArray[12]=new Array();
		iArray[12][0]="����";          
		iArray[12][1]="50px";              
		iArray[12][2]=60;             
		iArray[12][3]=2;               
		iArray[12][4]="Currency";                 
		iArray[12][9]="����|code:Currency";

		HistoryImpartGrid = new MulLineEnter( "fm" , "HistoryImpartGrid" );
		//��Щ���Ա�����loadMulLineǰ
		HistoryImpartGrid.mulLineCount = 0;
		HistoryImpartGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		HistoryImpartGrid.loadMulLine(iArray);
		//��Щ����������loadMulLine����
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initDiseaseGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="����ʱ��";
		iArray[1][1]="100px";
		iArray[1][2]=60;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="100px";
		iArray[2][2]=60;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="100px";
		iArray[3][2]=300;
		iArray[3][3]=1;


		iArray[4]=new Array();
		iArray[4][0]="ҽ�Ʒ����ܶ�";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="��ע";
		iArray[5][1]="100px";
		iArray[5][2]=20;
		iArray[5][3]=1;

		iArray[6]=new Array();
		iArray[6][0]="��ˮ��";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=3;


		DiseaseGrid = new MulLineEnter( "fm" , "DiseaseGrid" );
		//��Щ���Ա�����loadMulLineǰ
		DiseaseGrid.mulLineCount = 0;
		DiseaseGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		DiseaseGrid.loadMulLine(iArray);
		//��Щ����������loadMulLine����
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initServInfoGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="130px";
		iArray[1][2]=200;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="������ϸ";
		iArray[2][1]="130px";
		iArray[2][2]=20;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="����ѡ��ֵ";
		iArray[3][1]="130px";
		iArray[3][2]=20;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="����ע";
		iArray[4][1]="130px";
		iArray[4][2]=20;
		iArray[4][3]=1;


		ServInfoGrid = new MulLineEnter( "fm" , "ServInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ServInfoGrid.mulLineCount = 0;
		ServInfoGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		ServInfoGrid.loadMulLine(iArray);
		//��Щ����������loadMulLine����
		//ImpartGrid.setRowColData(1,1,"asdf");
    }
	catch(ex)
	{
		alert(ex);
	}
}

function getGrpCont()
{
	try { document.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
	try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
}

function initMultiAgentGrid(){
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ�
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ҵ��Ա����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][7]="queryAgentGrid";  ��//˫�����ò�ѯҵ��Ա�ĺ���  
      iArray[1][22]="queryAgent1Grid";        

      iArray[2]=new Array();
      iArray[2][0]="ҵ��Ա����";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="�����ֲ�";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="Ӷ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=150;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=150;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=150;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MultiAgentGrid.mulLineCount = 0;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}

</script>
