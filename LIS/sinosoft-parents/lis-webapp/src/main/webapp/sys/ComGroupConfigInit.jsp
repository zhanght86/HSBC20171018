<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>  
<script language="JavaScript">

function initForm()
{

  try
  { 
    initInpBox();    
    initComGroupMapGrid();  
    initComGroupGrid();
    //queryData('1');
    queryComGroupConfig();
    document.all('divOtherGrid').style.display="none";
    //alert("000000000000000000000");     
  }
  catch(re)
  {
    alert("��BonusRiskPreInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{ 
  try
  {     
    document.all('ComGroupCode').value = '';
    document.all('ComGroupName').value = '';
    document.all('GroupInfo').value = '';
   
  }
  catch(ex)
  {
    alert("��NBonusRiskPreInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
  
  function initComGroupMapGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";         		//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=50;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
     // iArray[1][4]="ComCode";  //���ô���:
		//	iArray[1][5]="1|2";
    //  iArray[1][6]="0|1";
   //   iArray[1][19]="1";
            
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������



      ComGroupMapGrid = new MulLineEnter( "document" , "ComGroupMapGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ComGroupMapGrid.mulLineCount = 5;   
      ComGroupMapGrid.displayTitle = 1;
      ComGroupMapGrid.canSel =0;
      ComGroupMapGrid.hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ComGroupMapGrid.hiddenSubtraction=0;
      
      ComGroupMapGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initComGroupGrid()
{                              
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���������";          	//����
      iArray[1][1]="50px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[2]=new Array();
      iArray[2][0]="����������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="����������";         		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���κ�";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ComGroupGrid = new MulLineEnter( "document" , "ComGroupGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ComGroupGrid.mulLineCount = 5;   
      ComGroupGrid.displayTitle = 1;
      ComGroupGrid.canSel  =1;
      ComGroupGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ComGroupGrid.hiddenSubtraction=1;
      ComGroupGrid. selBoxEventFuncName ="getComGroupDetail" ; 
      
      ComGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
