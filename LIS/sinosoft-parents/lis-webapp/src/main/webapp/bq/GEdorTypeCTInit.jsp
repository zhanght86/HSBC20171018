<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Supl
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
    
%>                            

<script language="JavaScript">  
function initInpBox()
{ 
  try
  {   
    //Edorquery();
    //alert(top.opener.document.all('EdorNo').value);      
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
    //alert(document.all('EdorTypeCal').value);
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
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
    initGrpPolGrid(); 
    initCTFeePolGrid();
    initCTFeeDetailGrid();
    getPolGrid(document.all('GrpContNo').value);
    getZTMoney(); //�˷Ѻϼƽ�� 
    getContZTInfo(); //�˱���Ϣ     
    getCTFeePolGrid(); //�����˷Ѻϼ�     
    //getCTFeeDetailGrid(); //�˱�������ϸ 
    
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

function initGrpPolGrid()
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
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="300px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�������ֱ�����";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="����";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="����";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="�ܱ���";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="Ͷ������";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="���屣������";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;

		iArray[9]=new Array();
		iArray[9][0]="����";         		
		iArray[9][1]="60px";            		 
		iArray[9][2]=60;            			
		iArray[9][3]=2;              		
		iArray[9][4]="Currency";              	  
		iArray[9][9]="����|code:Currency";        
        
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpPolGrid.mulLineCount = 0;   
      GrpPolGrid.displayTitle = 1;
      //PolGrid.canChk=1;
      GrpPolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      GrpPolGrid.hiddenSubtraction=1;
      GrpPolGrid.loadMulLine(iArray);
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
      CustomerGrid.mulLineCount = 10;   
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
		
		iArray[7]=new Array();
		iArray[7][0]="����";         		
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			
		iArray[7][3]=2;              		
		iArray[7][4]="Currency";              	  
		iArray[7][9]="����|code:Currency";
        
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
        iArray[1][0]="�������ֱ�����";
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

		iArray[5]=new Array();
		iArray[5][0]="����";         		
		iArray[5][1]="60px";            		 
		iArray[5][2]=60;            			
		iArray[5][3]=2;              		
		iArray[5][4]="Currency";              	  
		iArray[5][9]="����|code:Currency";
        
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

function initLCPolGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          		  	//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������ֺ�";    	  //����1
      iArray[1][1]="80px";             //�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";        	//����2
      iArray[2][1]="50px";              //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         	//����3
      iArray[3][1]="120px";            	//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ͻ���";         		//����4
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="�ͻ�����";         	//����5
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��Ч��";        //����6
      iArray[6][1]="60px";            	//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[7]=new Array();
      iArray[7][0]="����";        //����6
      iArray[7][1]="60px";            	//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[8]=new Array();
      iArray[8][0]="����";        //����6
      iArray[8][1]="60px";            	//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[9]=new Array();
      iArray[9][0]="����";        //����6
      iArray[9][1]="60px";            	//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[10]=new Array();
      iArray[10][0]="�˷ѽ��";        //����6
      iArray[10][1]="70px";            	//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?

		iArray[11]=new Array();
		iArray[11][0]="����";         		
		iArray[11][1]="60px";            		 
		iArray[11][2]=60;            			
		iArray[11][3]=2;              		
		iArray[11][4]="Currency";              	  
		iArray[11][9]="����|code:Currency";     
                  
      
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.hiddenPlus = 1;
      LCPolGrid.hiddenSubtraction = 1;
      //LCPolGrid.canSel = 1;
      //LCPolGrid.selBoxEventFuncName = "reportDetailClick";
      //LCInsureAccClassGrid.chkBoxEventFuncName = "reportDetailClick1";
      LCPolGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
