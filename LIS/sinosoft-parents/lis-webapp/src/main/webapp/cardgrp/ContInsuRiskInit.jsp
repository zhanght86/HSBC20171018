 <%
//�������ƣ�TempFeeInit.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
       
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initLCInsuredPolGrid();  
  
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initLCInsuredPolGrid()
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
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";              	        //��������з������ô����еڼ�λֵ
      iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[2][9]="��������|NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="100px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���ѽ��|NUM&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="����";      	   		//����
      iArray[4][1]="160px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 
      iArray[3][9]="���Ѻϼ�|NUM&NOTNULL";             			//�Ƿ���������,1��ʾ����0��ʾ������

      LCInsuredPolGrid = new MulLineEnter( "fm" , "LCInsuredPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCInsuredPolGrid.mulLineCount = 0;   
      LCInsuredPolGrid.displayTitle = 1;
      LCInsuredPolGrid.canChk=1;
      LCInsuredPolGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LCInsuredPolGrid. hiddenSubtraction=1;
 
      LCInsuredPolGrid.loadMulLine(iArray);  
	LCInsuredPolGrid.addOne("LCInsuredPolGrid");     
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,1,'111302');
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,2,"[��������]");
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,3,"10.00");
     LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,4,"1000.00");
     
      }
      catch(ex)
      {
        alert(ex);
      }
}


  
</script>
