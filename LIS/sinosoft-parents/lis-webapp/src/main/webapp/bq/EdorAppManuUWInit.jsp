<%
//�������ƣ�EdorAppManuUWInit.jsp
//�����ܣ���ȫ�˹��˱�
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������liurongxiao    ��������   2005-06-17  ����ԭ��/���� ������Լ����ȡ��һ��
%>

<script language="JavaScript">

//ҳ���ʼ��
function initForm()
{
  try
  {
      initParam();
      initInpBox();
      initEdorMainGrid();
      initPolAddGrid();
      initRiskGrid();
      initQuery();
      displayDetail();
      ctrlButtonDisabled();
      //alert("EDORTYPE=="+fm.EdorType.value);
  }
  catch (ex)
  {
      alert("�� EdorAppManuUWInit --> InitForm �����з����쳣:��ʼ��������� ");
  }
}

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('EdorAcceptNo').value     = NullToEmpty(EdorAcceptNo);
   document.all('MissionID').value        = NullToEmpty(MissionID);
   document.all('SubMissionID').value     = NullToEmpty(SubMissionID);
   document.all('ActivityStatus').value   = NullToEmpty(ActivityStatus);
   document.all('EdorType').value   		= NullToEmpty(EdorType);
   document.all('ActivityID').value   		= NullToEmpty(ActivityID);
   document.all('EdorNo').value = ""; //alert(document.all('SubMissionID').value);
   //makeWorkFlow();alert(36);
}

//��ʼ��ҳ��Ԫ��
function initInpBox()
{

  try
  {
    document.all('ContNo').value = '';
    document.all('lpManageCom').value = '';
    document.all('SaleChnl').value = '';
    document.all('AgentCode').value = '';
    document.all('Remark').value = '';

    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('NativePlace').value = '';
   // document.all('VIPValue').value = '';
   // document.all('BlacklistFlag').value = '';

    document.all('EdorUWState').value = "";
    document.all('edoruwstateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
  }
  catch (ex)
  {
    alert("�� EdorAppManuUWInit.jsp --> InitInpBox �����з����쳣:��ʼ��������� ");
  }
}

// ��ȫ�����б�ĳ�ʼ��
function initEdorMainGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="ҵ�����";
        iArray[1][1]="80px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="150px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="������";
        iArray[3][1]="150px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�ͻ���";
        iArray[4][1]="100px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���ֱ���";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="150px";
        iArray[6][2]=200;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="Ͷ���˿ͻ���";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="Ͷ����";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="�����˿ͻ���";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        iArray[11]=new Array();
        iArray[11][0]="������Ч����";
        iArray[11][1]="100px";
        iArray[11][2]=200;
        iArray[11][3]=8;

        iArray[12]=new Array();
        iArray[12][0]="����ʧЧ����";
        iArray[12][1]="100px";
        iArray[12][2]=100;
        iArray[12][3]=8;
        
        iArray[13]=new Array();
        iArray[13][0]="����״̬";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorMainGrid.mulLineCount = 3;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.loadMulLine(iArray);
        EdorMainGrid. selBoxEventFuncName = "showDetailInfo1";
    }
    catch (ex)
    {
        alert(ex);
    }
}
function initPolAddGrid()
  {
      var iArray = new Array();

      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";                  //�п�
          iArray[0][2]=30;                      //�����ֵ
          iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

          iArray[1]=new Array();
          iArray[1][0]="�ͻ���";
          iArray[1][1]="100px";
          iArray[1][2]=200;
          iArray[1][3]=0;

          iArray[2]=new Array();
          iArray[2][0]="����";
          iArray[2][1]="100px";
          iArray[2][2]=200;
          iArray[2][3]=0;

          iArray[3]=new Array();
          iArray[3][0]="�Ա�";
          iArray[3][1]="80px";
          iArray[3][2]=200;
          iArray[3][3]=0;

          iArray[4]=new Array();
          iArray[4][0]="����";
          iArray[4][1]="80px";
          iArray[4][2]=200;
          iArray[4][3]=0;

          iArray[5]=new Array();
          iArray[5][0]="��Ͷ���˹�ϵ";
          iArray[5][1]="80px";
          iArray[5][2]=200;
          iArray[5][3]=0;
          
          iArray[6]=new Array();
          iArray[6][0]="���������˹�ϵ";
          iArray[6][1]="80px";
          iArray[6][2]=200;
          iArray[6][3]=0;
          
          iArray[7]=new Array();
          iArray[7][0]="����";
          iArray[7][1]="80px";
          iArray[7][2]=200;
          iArray[7][3]=0; 
          
          PolAddGrid = new MulLineEnter("fm", "PolAddGrid");
          //��Щ���Ա�����loadMulLineǰ
          PolAddGrid.mulLineCount = 3;
          PolAddGrid.displayTitle = 1;
          PolAddGrid.locked = 1;
          PolAddGrid.canSel = 1;
          PolAddGrid.hiddenPlus = 1;
          PolAddGrid.hiddenSubtraction = 1;
          PolAddGrid.loadMulLine(iArray);
          PolAddGrid.selBoxEventFuncName = "initLppolDetailf";
      }
      catch (ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[3]=new Array();
      iArray[3][0]="�������ֺ���";         		//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ձ�������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[7][23]="0";
			
      iArray[8]=new Array();
      iArray[8][0]="��׼����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[8][23]="0";
			
      iArray[9]=new Array();
      iArray[9][0]="�����ӷ�";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][23]="0";
      
      iArray[10]=new Array();
      iArray[10][0]="ְҵ�ӷ�";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[10][23]="0";             			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[11]=new Array();
      iArray[11][0]="��ס�ؼӷ�";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11][23]="0";            			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="���üӷ�";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[12][23]="0";               			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[13]=new Array();
	  iArray[13][0]="��������";         	//����
	  iArray[13][1]="80px";            		//�п�
	  iArray[13][2]=80;            			//�����ֵ
	  iArray[13][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[14]=new Array();
	  iArray[14][0]="����ֹ��";         		//����
	  iArray[14][1]="80px";            		//�п�
	  iArray[14][2]=100;            			//�����ֵ
	  iArray[14][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[15]=new Array();
	  iArray[15][0]="���Ѽ��(��)";         		//����
	  iArray[15][1]="80px";            		//�п�
	  iArray[15][2]=100;            			//�����ֵ
	  iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[16]=new Array();
	  iArray[16][0]="��������";         		//����
	  iArray[16][1]="80px";            		//�п�
	  iArray[16][2]=100;            			//�����ֵ
	  iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[17]=new Array();
	  iArray[17][0]="����״̬";         		//����
	  iArray[17][1]="80px";            		//�п�
	  iArray[17][2]=100;            			//�����ֵ
	  iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[18]=new Array();
	  iArray[18][0]="ԭ�˱�����";         		//����
	  iArray[18][1]="80px";            		//�п�
	  iArray[18][2]=100;            			//�����ֵ
	  iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[19]=new Array();
	  iArray[19][0]="�ֺ˱�����";         		//����
	  iArray[19][1]="80px";            		//�п�
	  iArray[19][2]=100;            			//�����ֵ
	  iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
        iArray[20]=new Array();
        iArray[20][0]="����";
        iArray[20][1]="60px";
        iArray[20][2]=100;
        iArray[20][3]=2;
        iArray[20][4]="currency";
           
      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskGrid.mulLineCount = 0;   
      RiskGrid.displayTitle = 1;
      RiskGrid.locked = 1;
      RiskGrid.canSel = 0;
      RiskGrid.hiddenPlus = 1;
      RiskGrid.hiddenSubtraction = 1;
      RiskGrid.loadMulLine(iArray);     
      
     // RiskGrid.selBoxEventFuncName = "getRiskInfo";
      
      //��Щ����������loadMulLine����
      //RiskGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{
}
</script>
