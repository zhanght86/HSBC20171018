<%
//�������ƣ�DirectContInit.jsp
//�����ܣ�ֱ������¼���ʼ��ҳ��
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%@include file="../common/jsp/UsrCheck.jsp"%>

<Script language="JavaScript">
	//���ղ�ͬ��LoadFlag���в�ͬ�Ĵ����õ�����ĵ���λ��
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
     ************************************************/	
     //�ж��Ƿ�����ɨ��� 
    /************************************************
     *ScanFlag=0--��ɨ�衡
     *ScanFlag=1--��ɨ�衡
     ***********************************************/
	
//��������ʼ��
function initInputField()
{
	//Q:scantype���ж��Ƿ���ɨ��������ڶ����涯
    if(scantype=="scan")
    {
      setFocus();
    }  
	//LoadFlag=="5" -- ����Ͷ��������
	if(LoadFlag=="5")
	{
		document.all('PrtNo').value = prtNo;
		document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		divApproveContButton.style.display = "";  //¼�����桾 ���˰�ť ��
	}

} 	
	
//*******����ҳ��ʱ���г�ʼ��*****/	
function initForm()
{
    initInsuredGrid(); //��ʼ���������б�
	initImpartInsuredGrid() ; // �����˸�֪��Ϣ�б�ĳ�ʼ��
	initPolGrid(); //������������Ϣ�б��ʼ��
	initLCBnfGrid(); //�������б��ʼ��
	initInputField();
	initQuery();
	//�жϼ��±��еļ�¼����
	checkNotePad(prtNo,LoadFlag);
	//��ť��ѡ�ж�
    ctrlButtonDisabled(prtNo,LoadFlag);
}	

//��ʼ���������б�
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
	//InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
	InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	InsuredGrid.hiddenSubtraction=1;
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
      iArray[1][4]="impver_insu_direct";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^07|����";
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
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      ImpartInsuredGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
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
      iArray[2][1]="60px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      //iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������       
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="150px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[4]=new Array();
      iArray[4][0]="����(Ԫ)";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����(Ԫ)";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="40";            		        //�п�
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
      PolGrid.hiddenPlus=1;
      PolGrid.hiddenSubtraction=1;
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



</Script>
