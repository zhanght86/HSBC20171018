<%
//�������ƣ�RelGetQueryInit.jsp
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
    document.all('PolNo').value = tPolNo;
    document.all('RiskCode').value = tRiskCode;
    //document.all('InsuredName').value = tInsuredName;
    //document.all('AppntName').value = tAppntName;
  }
  catch(ex)
  {
    alert("��RelGetQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��RelGetQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
	easyQueryClick2();
	easyQueryClick();

  }
  catch(re)
  {
    alert("RelGetQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="ʵ������";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[2]=new Array();
//      iArray[2][0]="��������";         		//����
//      iArray[2][1]="140px";            		//�п�
//      iArray[2][2]=100;            			//�����ֵ
//      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[3]=new Array();
//      iArray[3][0]="������������";         		//����
//      iArray[3][1]="120px";            		//�п�
//      iArray[3][2]=200;            			//�����ֵ
//      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[3][10] = "NoType";
//      iArray[3][11] = "0|^0|������ȡ��Ӧ�ĺ�ͬ��^1|������ȡ��Ӧ�ļ��屣����^2|������ȡ��Ӧ�ĸ��˱�����^3|���ĺ�^4|�ݽ����˷�,��Ӧ�ݽ����˷ѱ���ݽɷ��վݺ�^5|�⸶Ӧ�ձ��еĸ���֪ͨ��ţ������ⰸ�ţ�^6|�����˷ѵĸ���֪ͨ�����(��Ӧ��������˱����ţ�^7|������Ӧ�ĸ��˱�����^8|�����˷ѵĸ���֪ͨ�����(��Ӧ���������屣���ţ�";
//      iArray[3][12] = "3";
//      iArray[3][18]=300;
//      iArray[3][19] = "0";
     


      iArray[2]=new Array();
      iArray[2][0]="�ܸ������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ӧ������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����ȷ������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[5]=new Array();
      iArray[5][0]="����Ա";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
	  iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=2; 
      iArray[6][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[6][5]="8";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[6][9]="��������|code:station&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]= 0 ;    

	  iArray[7]=new Array();
      iArray[7][0]="�����˱���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=2; 									//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][4]="AgentCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[7][5]="9";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[7][9]="�����˱���|code:AgentCode&NOTNULL";
      iArray[7][18]=250;
      iArray[7][19]= 0 ;    
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.selBoxEventFuncName ="" ;     //���RadioBoxʱ��Ӧ��JS����
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

</script>
