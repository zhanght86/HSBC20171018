<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�PolQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script type="text/javascript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
  //  fm.all('CustomerNo').value = tCustomerNo;
  //  fm.all('Name').value = tName;
  }
  catch(ex)
  {
    myAlert("��PolQueryInit.jsp-->"+"��ʼ���������!");
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
    myAlert("��PolQueryInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
  }
  catch(re)
  {
    myAlert("PolQueryInit.jsp-->"+"��ʼ���������!");
  }
}

var PolGrid; 
// ������Ϣ�б�ĳ�ʼ��
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
      iArray[1][0]="�ֱ�����";         		//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
      iArray[2]=new Array();
      iArray[2][0]="���˱�������";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;    
      
      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=2; 
      iArray[4][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][5]="6";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;          			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��Ч����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�ֱ��������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        myAlert(ex);
      }
}

</script>