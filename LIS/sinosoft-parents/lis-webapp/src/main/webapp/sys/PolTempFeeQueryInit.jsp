<%
//�������ƣ�TempFeeQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  }
  catch(ex)
  {
    alert("��TempFeeQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��TempFeeQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("TempFeeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="�ݽ����վݺ���";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�վݺ�����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][10] = "PayPlanType";
      iArray[2][11] = "0|^1|�µ�����^2|���ڴ��ս���^3|Ԥ�ս���^4|��ȫ����^5|Ԥ�汣�ѣ���ʱû�����ҵ��^6|�����շ�^7|�����ֽ����ѡ�������巽ʽ�շ�^8|���ڷǴ��ս���^9|����ɷѣ���ʱû�����ҵ��";
      iArray[2][12] = "2";
      iArray[2][19] = "0";		
     
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;

//      iArray[4]=new Array();
//      iArray[4][0]="���Ѽ��";         		//����
//      iArray[4][1]="100px";            		//�п�
//      iArray[4][2]=200;            			//�����ֵ
//      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][10] = "PayIntv";
//      iArray[4][11] = "0|^-1|�����ڽ�^0|����^12|�꽻^6|���꽻^3|����^1|�½�";
//      iArray[4][12] = "4";
//      iArray[4][19] = "0";		

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			  //�����ֵ
      iArray[4][3]=7;              		  	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][23]="0"; 
      
      iArray[5]=new Array();                                                 
      iArray[5][0]="������ʽ";         		//����                             
      iArray[5][1]="180px";            		//�п�                             
      iArray[5][2]=100;             			//�����ֵ                           
      iArray[5][3]=0;               			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;             			//�����ֵ
      iArray[6][3]=8;              	   		//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[7]=new Array();
      iArray[7][0]="�����˱���";         		//����
      iArray[7][1]="100px";             		//�п�
      iArray[7][2]=100;            			    //�����ֵ
      iArray[7][3]=2; 								    	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][4]="AgentCode";             //�Ƿ����ô���:null||""Ϊ������
      iArray[7][5]="7";              	      //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[7][9]="�����˱���|code:AgentCode&NOTNULL";
      iArray[7][18]=250;
      iArray[7][19]= 0 ;
	  
      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            		   	//�����ֵ
      iArray[8][3]=8;              		  	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            		   	//�����ֵ
      iArray[9][3]=0;              		  	//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�տ���";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            		   	//�����ֵ
      iArray[10][3]=0;              		  	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11]=new Array();
			iArray[11][0]="����";
			iArray[11][1]="60px";
			iArray[11][2]=100;
			iArray[11][3]=2;
			iArray[11][4]="currency";
	  
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.hiddenSubStraction = 1;
      PolGrid.cancelSubStraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
