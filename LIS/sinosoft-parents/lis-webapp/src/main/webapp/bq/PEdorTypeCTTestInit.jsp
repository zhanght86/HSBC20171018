<%
//�������ƣ�PEdorTypeCTTestInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Supl
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
  String CurrentDate = PubFun.getCurrentDate();
%>                         

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        

    fm.ContNo.value = "";
    fm.EdorItemAppDate.value = nullToEmpty("<%= CurrentDate %>");  
    
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initSelBox()
{  
  try                 
  {  
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    
  }
  catch(re)
  {
    alert("PEdorTypeCTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInusredGrid()

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
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        
        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;        
        
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=3;
        
        iArray[6]=new Array();
        iArray[6][0]="���屣������";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        InsuredGrid.mulLineCount = 0;   
        InsuredGrid.displayTitle = 1;
        //alert(fm.DisplayType.value);        
        InsuredGrid.canChk=1;                                                                  
        InsuredGrid.canSel =1;                                                                 
        InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����

        InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        InsuredGrid.hiddenSubtraction=1;
        InsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initPolGrid()
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
        iArray[1][0]="���ִ���";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��׼����";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="�����ӷ�";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="ְҵ�ӷ�";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initCustomerGrid()
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
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="70px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;        

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 3;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;       
      //PolGrid.canChk=1;
      CustomerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ; 
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCTFeeDetailGrid()
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
        iArray[1][0]="���ֺ�";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="���ý��";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        
      CTFeeDetailGrid = new MulLineEnter( "fm" , "CTFeeDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CTFeeDetailGrid.mulLineCount = 0;   
      CTFeeDetailGrid.displayTitle = 1;
      CTFeeDetailGrid.canChk=0;
      CTFeeDetailGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CTFeeDetailGrid.hiddenSubtraction=1;
      CTFeeDetailGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCTFeePolGrid()
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
        iArray[1][0]="���ֺ�";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�˷Ѻϼ�";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        
      CTFeePolGrid = new MulLineEnter( "fm" , "CTFeePolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CTFeePolGrid.mulLineCount = 0;   
      CTFeePolGrid.displayTitle = 1;
      CTFeePolGrid.canChk=0;
      CTFeePolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CTFeePolGrid.hiddenSubtraction=1;
      CTFeePolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>