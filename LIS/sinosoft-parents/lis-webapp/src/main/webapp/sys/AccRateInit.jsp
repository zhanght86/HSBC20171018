<%
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ
%>
<!--�û�У����-->

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
	
function initInpBox()
{ 
  try
  {                                   
    fm.RiskCode.value = '';
    fm.InsuAccNo.value='';
    fm.BalaDate.value = '';
    fm.Rate.value = '';
    fm.SRateDate.value = '';
    fm.ARateDate.value = '';
    fm.RateStartDate.value = '';
    fm.RateEndDate.value = '';
    fm.AccType.value=fm.AccRate[0].value;        
  }
  catch(ex)
  {
    alert("��AccRateInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                       
// ��������Ϣ�б�ĳ�ʼ��
function initLMInsuAccRateGrid(tFlag)
  {

      var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";    	//����
      iArray[1][1]="70px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ʻ�����";         			//����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="75px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;       
      
      if(tFlag=='1')
      {
      iArray[7]=new Array();
      iArray[7][0]="Ӧ�ù�������";         		//����
      iArray[7][1]="75px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="ʵ�ʹ�������";         		//����
      iArray[8][1]="75px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;     
      	
      iArray[9]=new Array();
      iArray[9][0]="״̬";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������  
      	}
      if(tFlag=='0')
      		{
      iArray[7]=new Array();
      iArray[7][0]="��ʼ����";         		//����
      iArray[7][1]="75px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="75px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;        

      iArray[9]=new Array();
      iArray[9][0]="״̬";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������              			      			
      			}

    
 
      LMInsuAccRateGrid = new MulLineEnter( "document" , "LMInsuAccRateGrid" );
      //��Щ���Ա�����loadMulLineǰ
      LMInsuAccRateGrid.mulLineCount = 5;
      LMInsuAccRateGrid.displayTitle = 1;
      LMInsuAccRateGrid.canSel = 1;
      LMInsuAccRateGrid.canChk=0;
      LMInsuAccRateGrid.hiddenPlus=1;
      LMInsuAccRateGrid.hiddenSubtraction=1;
      LMInsuAccRateGrid.selBoxEventFuncName="displayInfo";
      LMInsuAccRateGrid.loadMulLine(iArray);

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initForm()
{
  try
  {
    initInpBox();
    initLMInsuAccRateGrid(1);
    queryClick();
  }
  catch(re)
  {
    alert("AccRateInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function displayAccClick()
{
	var tFlag =fm.AccRate[0].value 
	fm.AccType.value=tFlag;  //˵���ǽ������� ��ֵΪ 1 Ϊ��̨�ж���׼��
	initLMInsuAccRateGrid(tFlag);
	fm.RiskCode.value = '';
  fm.InsuAccNo.value='';
  fm.BalaDate.value = '';
  fm.Rate.value = '';
  fm.SRateDate.value = '';
  fm.ARateDate.value = '';
  queryClick();
	divAccRateG.style.display='none';
	divAccRate.style.display='';
	}
	
function displayAccGClick()
{

	var tFlag =fm.AccRate[1].value
	fm.AccType.value=tFlag;  //˵���Ǳ�֤���� ��ֵΪ 0  Ϊ��̨�ж���׼��  
	initLMInsuAccRateGrid(tFlag);
	fm.RiskCode.value = '';
  fm.InsuAccNo.value='';
  fm.BalaDate.value = '';
  fm.GruRate.value = '';
  fm.RateStartDate.value = '';
  fm.RateEndDate.value = '';
	queryClick();
	divAccRateG.style.display='';
	divAccRate.style.display='none';
	}
</script>
