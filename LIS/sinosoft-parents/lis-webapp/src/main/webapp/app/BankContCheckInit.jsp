<%
//�������ƣ�BankContCheckInit.jsp
//�����ܣ�
//�������ڣ�2006-2-13 10:38
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

//��ʼ�������
function initInpBox() 
{ 
		//Q:scantype���ж��Ƿ���ɨ��������ڶ����涯
		if(scantype=="scan")
		{
			setFocus();
		}
		else
	    {
	    fm.insuworkimpart.disabled=true;
	    fm.insuhealthmpart.disabled=true;
	    }    
     //�ж��Ƿ�����ɨ��� 
     /**********************
     *ScanFlag=0--��ɨ�衡*ScanFlag=1--��ɨ�衡
     ***********************/
	  //��������PrtNo�д�Ͷ�����ţ��ʽ�Ͷ�������ֶθ�ֵ
      document.all('PrtNo').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('MissionID').value = MissionID;
      document.all('SubMissionID').value = SubMissionID;
      fm.Operator.value = nullToEmpty("<%=tGI.Operator%>");
      
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true; 
}

//����ʼ��
function initForm()
{
    try
    {
		//��ʼ�������
		initInpBox();
		//��ʼ����ҵ��Ա�б�
	    initMultiAgentGrid();
		//��ʼ��ҵ��Ա��֪
	    initAgentImpartGrid();    
	    //��ʼ��Ͷ���˸�֪��Ϣ
	    initImpartGrid();
		// ��������Ϣ�б�ĳ�ʼ��
		initInsuredGrid();
		//�������˸�֪��Ϣ�б��ʼ��
		initImpartInsuredGrid();
	    //������������Ϣ�б��ʼ��
		initPolGrid();
		// ��������Ϣ�б�ĳ�ʼ��
	    initLCBnfGrid();
	
	    //��ʼ����ѯ
	    initQuery();
	    
		divApproveContButton.style.display = "";
		divTempFeeInput.style.display = "";
		
		if(LoadFlag=='a')
	 	{
	 		divApproveContButton.style.display="none";  
	 		divCustomerUnionButton.style.display="";  
		}
		
        //��ť��ѡ�ж�
        ctrlButtonDisabled(prtNo,LoadFlag);

  }
  catch(ex)
  {}

}


//��ҵ��Ա�б��ʼ��
function initMultiAgentGrid()
{
    var iArray = new Array();
    try 
    {
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
      iArray[4][0]="Ӫҵ������";         		//����
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
      MultiAgentGrid.mulLineCount = 0;   
      MultiAgentGrid.displayTitle = 1;
      MultiAgentGrid.hiddenPlus = 1;
      MultiAgentGrid.hiddenSubtraction = 1;
      MultiAgentGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
	
}
// ҵ��Ա��֪��Ϣ�б�ĳ�ʼ��
function initAgentImpartGrid() 
{  
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impver_agent_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="AgentImpart";
      //iArray[1][11]="0|^05|ҵ��Ա��֪";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      AgentImpartGrid.mulLineCount = 0;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      AgentImpartGrid.hiddenPlus = 1;
      AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}

// Ͷ���˸�֪��Ϣ ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid()
{  
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impver_appnt_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^01|����������֪^02|������֪";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 0;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus = 1;
      ImpartGrid.hiddenSubtraction = 1;
      ImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}

// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
{                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�Ա�";      	   		//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="Sex"; 


      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="���һ�������˹�ϵ";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][9]="�����������˹�ϵ|code:Relation&NOTNULL";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="�ͻ��ڲ���";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";      
	  iArray[6][19]=1;

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// �����˸�֪��Ϣ�б�ĳ�ʼ��
function initImpartInsuredGrid() 
{                               
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impver_insu_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^13|������֪^14|ְҵ��֪";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      ImpartInsuredGrid = new MulLineEnter( "fm" , "ImpartInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      ImpartInsuredGrid.hiddenPlus = 1;
      ImpartInsuredGrid.hiddenSubtraction = 1;
      ImpartInsuredGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert("��ʼ��initImpartInsuredGrid����");
    }
}

//������������Ϣ�б��ʼ��
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���յ����ֺ���";         		//����
      iArray[1][1]="0px";            		        //�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="40px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      //iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������           
      
      iArray[3] =new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="150px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="����(Ԫ)";         		//����
      iArray[4][1]="60px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����(Ԫ)";         		//����
      iArray[5][1]="60px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�ɷ�����";         		//����
      iArray[8][1]="40px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��ȡ����";         		//����
      iArray[9][1]="0px";            		        //�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ȡ��ʽ";         		//����
      iArray[10][1]="0px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}

// ��������Ϣ�б�ĳ�ʼ��
function initLCBnfGrid() 
{
  var iArray = new Array();
  try 
  {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���������"; 		//����
    iArray[1][1]="0px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=3;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����"; 	//����
    iArray[2][1]="60px";		//�п�
    iArray[2][2]=30;			//�����ֵ
    iArray[2][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="֤������"; 		//����
    iArray[3][1]="40px";		//�п�
    iArray[3][2]=40;			//�����ֵ
    iArray[3][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][4]="IDType";
    iArray[3][9]="֤������|code:IDType";

    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="120px";		//�п�
    iArray[4][2]=80;			//�����ֵ
    iArray[4][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�뱻���˹�ϵ"; 	//����
    iArray[5][1]="60px";		//�п�
    iArray[5][2]=60;			//�����ֵ
    iArray[5][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][4]="Relation";

    iArray[6]=new Array();
    iArray[6][0]="����˳��"; 		//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=40;			//�����ֵ
    iArray[6][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][4]="bnforder";

    iArray[7]=new Array();
    iArray[7][0]="����ݶ�"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="����������"; 		//����
    iArray[8][1]="80px";		//�п�
    iArray[8][2]=30;			//�����ֵ
  	iArray[8][3]=0;
 
    iArray[9]=new Array();
    iArray[9][0]="�����������ڲ��ͻ���"; 		//����
    iArray[9][1]="0px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=3;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="���������˿ͻ���"; 		//����
    iArray[10][1]="60px";		//�п�
    iArray[10][2]=30;			//�����ֵ
    iArray[10][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    LCBnfGrid = new MulLineEnter( "fm" , "LCBnfGrid" );
    LCBnfGrid.mulLineCount = 1;
    LCBnfGrid.displayTitle = 1;
    LCBnfGrid.hiddenPlus=1;
    LCBnfGrid.hiddenSubtraction=1;
    LCBnfGrid.loadMulLine(iArray);
  } 
  catch(ex) 
  {
    alert("initLCBnfGrid�����з����쳣:��ʼ���������!");
  }
}

</script>
