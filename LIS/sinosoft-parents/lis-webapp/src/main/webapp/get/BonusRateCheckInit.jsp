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
    fm.DistributeValue.value='';
    fm.DistributeRate.value = '';
    fm.BonusCoefSum.value = '';
    fm.FiscalYear.value = '';
  }
  catch(ex)
  {
    alert("��BonusRateInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                       
// ��������Ϣ�б�ĳ�ʼ��
function initLOBonusMainGrid(tFlag)
  {

      var iArray = new Array();
      try
      {
      iArray[0]=new Array();                                                   
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬����?
      iArray[0][1]="30px";         			//�п�                                 
      iArray[0][2]=10;          			//�����ֵ                               
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                               
      iArray[1]=new Array();                                                   
      iArray[1][0]="������";    	//����                                     
      iArray[1][1]="100px";            		//�п�                               
      iArray[1][2]=80;            			//�����ֵ                             
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                               
      iArray[2]=new Array();                                                   
      iArray[2][0]="���";         			//����                                 
      iArray[2][1]="80px";            		//�п�                               
      iArray[2][2]=80;            			//�����ֵ                             
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                               
      iArray[3]=new Array();                                                   
      iArray[3][0]="�ɷ���ӯ��";         			//����                           
      iArray[3][1]="80px";            		//�п�                               
      iArray[3][2]=100;            			//�����ֵ                             
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                               
      iArray[4]=new Array();                                                   
      iArray[4][0]="�����������";         		//����                           
      iArray[4][1]="100px";            		//�п�                               
      iArray[4][2]=100;            			//�����ֵ                             
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                               
      iArray[5]=new Array();                                                   
      iArray[5][0]="��������ʱ�̵ĺ���ϵ����";         		//����                           
      iArray[5][1]="180px";            		//�п�                               
      iArray[5][2]=100;            			//�����ֵ                             
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
                                                                                                                                                                                                               
                                                                               
      iArray[6]=new Array();                                                   
      iArray[6][0]="״̬";         		//����                                   
      iArray[6][1]="100px";            		//�п�                               
      iArray[6][2]=100;            			//�����ֵ                             
      iArray[6][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������  


    
 
      LOBonusMainGrid = new MulLineEnter( "document" , "LOBonusMainGrid" );
      //��Щ���Ա�����loadMulLineǰ
      LOBonusMainGrid.mulLineCount = 0;
      LOBonusMainGrid.displayTitle = 1;
      LOBonusMainGrid.canSel = 1;
      LOBonusMainGrid.canChk=0;
      LOBonusMainGrid.hiddenPlus=1;
      LOBonusMainGrid.hiddenSubtraction=1;
      LOBonusMainGrid.selBoxEventFuncName="displayInfo";
      LOBonusMainGrid.loadMulLine(iArray);

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
    initLOBonusMainGrid(1);
    queryClick();
  }
  catch(re)
  {
    alert("BonusRateInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function displayAccClick()
{
	initLOBonusMainGrid(tFlag);
  fm.DistributeValue.value='';
  fm.DistributeRate.value = '';
  fm.BonusCoefSum.value = '';
  queryClick();
}
</script>
