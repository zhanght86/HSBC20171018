<%@include file="../i18n/language.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">
function initInpBox()
{ 
  try
  {    //ҳ���ı���ĳ�ʼ���������䣩
	 fm.RiskCode.value = "";
	 fm.RiskCodeName.value = "";
	 fm.SaleChnl.value = "";
	 fm.SaleChnlName.value = "";
	 fm.ManageComGrp.value = "";
	 fm.ManageComGrpName.value = "";
	 fm.Currency.value = "";
	 fm.CurrencyName.value = "";
	 fm.ReRiskCode.value = "";
	 fm.ReRiskCodeName.value = "";
	 fm.ReSaleChnl.value = "";
     fm.ReSaleChnlName.value = "";
     fm.ReManageComGrp.value = "";
     fm.ReManageComGrpName.value = "";
     fm.ReCurrency.value = "";
     fm.ReCurrencyName.value = "";
     fm.LISStartDate.value = "";
     fm.LISEndDate.value = "";
     fm.PPLStartDate.value = "";
     fm.PPLEndDate.value = "";
    
		
	
    
  }
  catch(ex)
  {	
    myAlert("RiskSaleNameInit.jsp-->"+""+"InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initConfigCvalidateGrid();
    if("0" == "<%=request.getParameter("flag")%>"){
    easyQueryClick();
    }
  }
  catch(re)
  {
    myAlert("RiskSaleNameInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
  }
}

function initConfigCvalidateGrid()
  {                             
    var iArray = new Array();
    var i=0; 
      try
      {
     
      iArray[i]=new Array();
      iArray[i][0]="���";         		     //����
      iArray[i][1]="30px";            		//�п�
      iArray[i][2]=120;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[++i]=new Array();
      iArray[i][0]="���ֱ���";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=120;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[++i]=new Array();
      iArray[i][0]="�������� ";         		//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[++i]=new Array();
      iArray[i][0]="�������";         		//����
      iArray[i][1]="120px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
            
      iArray[++i]=new Array();
      iArray[i][0]="����";         	//����
      iArray[i][1]="60px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
            
      iArray[++i]=new Array();
      iArray[i][0]="";         	//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//����
      iArray[i][1]="80px";            		//�п�
      iArray[i][2]=100;            			//�����ֵ
      iArray[i][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
            
      
      
     
      
      ConfigCvalidateGrid = new MulLineEnter( "fm" , "ConfigCvalidateGrid" ); 
      
      ConfigCvalidateGrid.mulLineCount = 4;//�����ԣ���������
      ConfigCvalidateGrid.displayTitle = 1;//1��ʾ���� (ȱʡֵ) ,0���ر���
      ConfigCvalidateGrid.hiddenPlus = 1;//����ʾ��+���Ű�ť
      ConfigCvalidateGrid.hiddenSubtraction = 1;//����ʾ��--���Ű�ť
      ConfigCvalidateGrid.canSel=1;//��ʾRadio ��ѡ��
      ConfigCvalidateGrid.selBoxEventFuncName = "ShowSaleDate";//��MulLine�е���RadioBoxʱ��Ӧ������Ա�ⲿ��д��JS����  ShowGift�Ǻ�����
      ConfigCvalidateGrid.loadMulLine(iArray);//���ö����ʼ�����������Ա����ڴ�ǰ����
      
      }
      catch(ex)
      {
        myAlert("ConfigCvalidateInit.jsp-->"+""+"");
      }
}


</script>