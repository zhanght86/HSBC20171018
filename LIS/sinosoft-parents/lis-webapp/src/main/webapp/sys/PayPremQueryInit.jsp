<%
//�������ƣ�PayPremQueryInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��Howie 
//���¼�¼��  ������   ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
	document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
  }
  catch(ex)
  {
    alert("��PayPremQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PayPremQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initPolGrid();
	  easyQueryClick();
  }
  catch(re)
  {
    alert("PayPremQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="�����ͬ����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[2]=new Array();
      iArray[2][0]="���屣������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
     
      //iArray[4]=new Array();
      //iArray[4][0]="�������";         		//����
      //iArray[4][1]="80px";            		//�п�
      //iArray[4][2]=100;            			//�����ֵ
      //iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���Ѵ���";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      //iArray[6]=new Array();
      //iArray[6][0]="֪ͨ�����";         		//����
      //iArray[6][1]="40px";            		//�п�
      //iArray[6][2]=100;            			//�����ֵ
      //iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
       
      //iArray[7]=new Array();
      //iArray[7][0]="����Ŀ��";         		//����
      //iArray[7][1]="80px";            		//�п�
      //iArray[7][2]=100;            			//�����ֵ
      //iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���α���";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���Ѽƻ�����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��Ӧ�����";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    

	    iArray[8]=new Array();
      iArray[8][0]="��ʵ�����";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="���Ѽ��";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="��������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      iArray[11]=new Array();
      iArray[11][0]="��������";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
   

	  //iArray[15]=new Array();
    //  iArray[15][0]="ԭ��������";         		//����
    //  iArray[15][1]="80px";            		//�п�
    //  iArray[15][2]=100;            			//�����ֵ
    //  iArray[15][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[12]=new Array();
      iArray[12][0]="�ֽ�������";         		//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
   
      iArray[13]=new Array();
      iArray[13][0]="������;��־";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
   
      iArray[14]=new Array();
      iArray[14][0]="����ת�ʳɹ����";         		//����
      iArray[14][1]="100px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ����������
      
      iArray[15]=new Array();
      iArray[15][0]="����ʧЧ����";         		//����
      iArray[15][1]="100px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ����������
      
      iArray[16]=new Array();
      iArray[16][0]="������Ч����";         		//����
      iArray[16][1]="100px";            		//�п�
      iArray[16][2]=100;            			//�����ֵ
      iArray[16][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ����������
      
      iArray[17]=new Array();
      iArray[17][0]="������ֹ����";         		//����
      iArray[17][1]="100px";            		//�п�
      iArray[17][2]=100;            			//�����ֵ
      iArray[17][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ����������
  
    
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.selBoxEventFuncName = "";
      PolGrid.loadMulLine(iArray);  
      
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
