<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthInit.jsp
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	
    document.all('ContNo').value = '';
    document.all('MissionID').value = '';
    document.all('SubMissionID').value = '';
  }
  catch(ex)
  {
    alert("��UWManuHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
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
    alert("��UWSubInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo,tRptNo,tBatNo)
{
  try
  {
	initHide(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo,tRptNo,tBatNo);
	
	initHealthGrid();
	initHealthOtherGrid();
 // initOtherHealthGrid();
	
	initMainUWHealthGrid();
	
	//initDisDesbGrid();
  	//alert(4);
 if(tFlag == "1")
 {
    document.all('PrtNo').value = '' ;
     divOperation.style.display = "";
		 divMainHealth.style.display = "";
		 divHealthButton.style.display = "none";
	//	 divOtherHealth.style.display = "none";
		 initInsureNo();
//     alert("operatorQuery()");
//     operatorQuery(); 
 }
 
 if(tFlag == "3")
 {
     divMainHealth.style.display = "";
		 divHealthButton.style.display = "none";
		 initCustomerNo();
 }
  //alert(111); 
	//init();
	//alert(23);
	if(tFlag == "null")
	{
	  for(var pp=0;pp<HealthGrid.mulLineCount;pp++)
	  	{
	  	  if(HealthGrid.getRowColData(pp,1)=="011")
	  	  {
	  	    HealthGrid.setRowColData(pp,4,"����");
	  	  }
	  	  else if(HealthGrid.getRowColData(pp,1)=="012")
	  	  {
	  	    HealthGrid.setRowColData(pp,4,"����");
	  	  } 
	  	  else if(HealthGrid.getRowColData(pp,1)=="013")
	  	  {
	  	    HealthGrid.setRowColData(pp,4,"��ѹ,��ѹ");
	  	  }
	  	  
	  	}
	  	queryHealthInfo(tContNo,tPrtSeq);
	}
	
	}
	
  catch(re) 
  {
  alert("UWManuHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
  
}


// ������Ϣ�б��ĳ�ʼ��
function initMainUWHealthGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      

      iArray[2]=new Array();
      iArray[2][0]="��ˮ��";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

              

      iArray[3]=new Array();
      iArray[3][0]="���ͻ���";         			//����
      iArray[3][1]="120px";            			//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���������";         			//����
      iArray[4][1]="120px";            			//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ʱ��";         			//����
      iArray[5][1]="120px";            			//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ʱ��";         			//����
      iArray[6][1]="120px";            			//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��ӡ״̬";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[7][1]="60px";         			//�п�
      iArray[7][2]=10;          			//�����ֵ
      iArray[7][3]=2; 
      iArray[7][10] = "ifPrint";
      iArray[7][11] = "0|3^0|δ��ӡ|^1|�Ѵ�ӡ|^2|�ѻظ�|";  
      iArray[7][12] = "7";
      iArray[7][13] = "0";
     
      iArray[8]=new Array();
      iArray[8][0]="����ˮ��";         			//����
      iArray[8][1]="120px";            			//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      
      MainHealthGrid = new MulLineEnter( "fm" , "MainHealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      MainHealthGrid.mulLineCount = 1;
      MainHealthGrid.displayTitle = 1;
      MainHealthGrid.canChk = 0;
      MainHealthGrid.hiddenPlus = 1;
      MainHealthGrid.hiddenSubtraction = 1;
      MainHealthGrid.canSel =1
      MainHealthGrid.loadMulLine(iArray);  
      MainHealthGrid. selBoxEventFuncName = "easyQueryClick";
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ������Ϣ�б��ĳ�ʼ��
function initHealthGrid1()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����Ŀ���";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4]="ITEM";

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������     
      

      iArray[3]=new Array();
      iArray[3][0]="�����Ŀ����";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[3][4]="ITEMRESULT";
      iArray[3][17]="1";
      iArray[3][15]="ITEM";
      
      
      iArray[4]=new Array();
      iArray[4][0]="��ע";    	//����
      iArray[4][1]="400px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  

      
      //HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      //��Щ���Ա�����loadMulLineǰ
      HealthGrid.mulLineCount =0;   
      HealthGrid.displayTitle = 1;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.loadMulLine(iArray); 
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// �Ȳ������Ŀ�����Ϣ�б��ĳ�ʼ��
function initHealthGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����Ŀ�����";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4] = "HealthSubCode";             			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ�������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][4] = "HealthSubCode";             			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[2][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[2][18] = 500;
      
      iArray[3]=new Array();
      iArray[3][0]="�����Ŀ���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[3][4]="PEDivCode";
      iArray[3][5]="3|4";
      iArray[3][6]="0|1";
      iArray[3][15]="CodeType";
      iArray[3][17]="1";
      iArray[3][18]=500;
      
      iArray[4]=new Array();
      iArray[4][0]="�����Ŀ����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="100px";         			//�п�
      iArray[4][2]=100;          			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[4][4]="PEDivCode";
      iArray[4][5]="3|4";
      iArray[4][6]="0|1";
      iArray[4][15]="CodeType";
      iArray[4][17]="1";
      iArray[4][18]=500;

      iArray[5]=new Array();
      iArray[5][0]="����";         			//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[5][4]="ITEMRESULT";
      iArray[5][5]="5";
      iArray[5][6]="1";      
      iArray[5][15]="ITEM";
      iArray[5][17]="1";
      
      iArray[6]=new Array();
      iArray[6][0]="���";    	//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=400;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����ֵ";    	//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=400;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�Ƿ��¼�����Ŀ��־";    	//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  

      
      //HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      //��Щ���Ա�����loadMulLineǰ
      HealthGrid.mulLineCount =0;   
      HealthGrid.displayTitle = 1;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.loadMulLine(iArray); 
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ���������Ŀ�����Ϣ�б��ĳ�ʼ��
function initHealthOtherGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����Ŀ�����";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4] = "HealthSubCode";             			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ�������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][4] = "HealthSubCode";             			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[2][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[2][18] = 500;
      
      iArray[3]=new Array();
      iArray[3][0]="�����Ŀ���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[3][4]="PEDivCode";
      iArray[3][5]="3|4";
      iArray[3][6]="0|1";
      iArray[3][15]="CodeType";
      iArray[3][17]="1";
      iArray[3][18]=500;
      
      iArray[4]=new Array();
      iArray[4][0]="�����Ŀ����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="100px";         			//�п�
      iArray[4][2]=100;          			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[4][4]="PEDivCode";
      iArray[4][5]="3|4";
      iArray[4][6]="0|1";
      iArray[4][15]="CodeType";
      iArray[4][17]="1";
      iArray[4][18]=500;

      iArray[5]=new Array();
      iArray[5][0]="����";         			//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[5][4]="ITEMRESULT";
      iArray[5][5]="5";
      iArray[5][6]="1";      
      iArray[5][15]="ITEM";
      iArray[5][17]="1";
      
      iArray[6]=new Array();
      iArray[6][0]="���";    	//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=400;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����ֵ";    	//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=400;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      HealthOtherGrid = new MulLineEnter( "fm" , "HealthOtherGrid" ); 

      HealthOtherGrid.mulLineCount =0;   
      HealthOtherGrid.displayTitle = 1;
     // HealthGrid.hiddenPlus = 1;
     // HealthGrid.hiddenSubtraction = 1;
      HealthOtherGrid.loadMulLine(iArray); 

      }
      catch(ex)
      {
        alert(ex);
      }
}


// ������Ϣ�б��ĳ�ʼ��
function initOtherHealthGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����Ŀ���";    	//����
      iArray[1][1]="70px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4] = "HealthCode";             			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ����";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�����Ŀ����";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[3][4]="ITEMRESULT";
      iArray[3][17]="1";
      iArray[3][15]="ITEM";

      iArray[4]=new Array();
      iArray[4][0]="��ע";         			//����
      iArray[4][1]="400px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      OtherHealthGrid = new MulLineEnter( "fm" , "OtherHealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
     
      //��Щ���Ա�����loadMulLineǰ
      OtherHealthGrid.mulLineCount =0;   
      OtherHealthGrid.displayTitle = 1;
    //  OtherHealthGrid.hiddenPlus = 0;
      //OtherHealthGrid.hiddenSubtraction = 0;
//      OtherHealthGrid.canSel = 1;
//      OtherHealthGrid.canChk = 1;
      OtherHealthGrid.loadMulLine(iArray); 
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ������Ϣ�б��ĳ�ʼ��
function initDisDesbGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����֢״";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������";         		//����
      iArray[2][1]="260px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][4]="ICDName";
      iArray[2][9]="��������|len<=120";
      iArray[2][18]=300;

      iArray[3]=new Array();
      iArray[3][0]="ICD����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[3][4]="ICDCode";
      iArray[3][9]="ICD����|len<=20";
      iArray[3][15]="ICDName";
      iArray[3][17]="2";
      iArray[3][18]=700;
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      DisDesbGrid.mulLineCount = 1;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo,tRptNo,tBatNo)
{
	document.all('ContNo').value = tContNo;
  document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMission ;
	document.all('PrtNo').value = tPrtNo ;
	document.all('PrtSeq').value = tPrtSeq;
	document.all('CustomerNo').value = tCustomerNo;
	document.all('RptNo').value = tRptNo;
	document.all('BatNo').value = tBatNo;
	
  
}

</script>








