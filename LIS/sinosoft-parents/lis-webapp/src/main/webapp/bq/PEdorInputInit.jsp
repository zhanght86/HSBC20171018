<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Tjj
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
  String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;   
  String dayAfterCurrent = new FDate().getString(dt);   
  String CurrentTime = PubFun.getCurrentTime();
%>                             

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
    fm1.all('ContNo').value ='';
    document.all('ContNo').value = '';
    document.all('InsuredName').value = '';
    document.all('AppntName').value = '';
 
   
    document.all('ValiDate').value = '';
    document.all('PaytoDate').value = '';
    document.all('Prem').value = '';

    document.all('GetPolDate').value = '';
    document.all('AgentCode').value = '';
    
   document.all('EdorType').value = '';
   document.all('EdorValiDate').value = '<%=CurrentDate%>';
   //document.all('ChgPrem').value = '';
   document.all('EdorNo').value='';
   //document.all('ChgAmnt').value = '';
   document.all('Operator').value = "<%=tG.Operator%>";
   document.all('EdorAppDate').value = '<%=CurrentDate%>';
   document.all('ContType').value ='1';
   document.all('currentDay').value = '<%=CurrentDate%>';
   document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
  }
  catch(ex)
  {
    alert("��PEdorInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!"+ex);
  }      
}

function initEdorItemGrid()
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
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="����������ʾ����";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="�����ͬ����";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;
        
        iArray[6]=new Array();
        iArray[6][0]="��ͬ����";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="�����˿ͻ�����";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="�������ֺ���";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="������Ч����";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=0;
        
        iArray[10]=new Array();
        iArray[10][0]="���˷ѽ��";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
      

      
      EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EdorItemGrid.mulLineCount = 0;   
      EdorItemGrid.displayTitle = 1;
      EdorItemGrid.canSel =1;
      EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      EdorItemGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorItemGrid.hiddenSubtraction=1;
      EdorItemGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initInusredGrid()

{                               
    alert("in ");
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";            		//�п�
        iArray[0][2]=10;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�������˺���";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="������������";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="��������֤������";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�����������˹�ϵ";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        InsuredGrid.mulLineCount = 0;   
        InsuredGrid.displayTitle = 1;
        if (fm.DisplayType.value=='2')
        {
          InsuredGrid.canChk=1;
          InsuredGrid.canSel =0;
          InsuredGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //���RadioBoxʱ��Ӧ��JS����
        }
        else
        {
          InsuredGrid.canChk=0;
          InsuredGrid.canSel =1;
        }
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
        iArray[1][0]="�������ֺ���";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�������˺���";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="ʵ�ʱ���";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;        
        
        iArray[6]=new Array();
        iArray[6][0]="ʵ�ʱ���"
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="��Ч����";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=3;
        
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
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��PEdorInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initFormFirst()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initDiv();  
    addClick();
    
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initDiv();
    initEdorItemGrid();  
    
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initDiv()
{
//	divedortype.style.display ='none';
	divappconfirm.style.display ='none';
	showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
//	divdetail.style.display = 'none';
}
</script>