<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�UWAutoInit.jsp
	//�����ܣ������Զ��˱�
	//�������ڣ�2002-06-19 11:10:36
	//������  ��WHN
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//����ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ������ѯ����
    document.all('ManageCom').value = '';
    document.all('ManageComName').value = '';
    document.all('ContNo').value = '';    
    document.all('PrtNo').value = '';    
    
    document.all('RiskCode').value = '';
    document.all('RiskCodeName').value = '';
    document.all('AgentCode').value = '';
    
    document.all('SecPayMode').value = '';
    document.all('PayModeName').value = '';
        
    document.all('ContType').value = '';
    document.all('ContTypeName').value = '';
  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();  
    initContGrid();  
	  initPolGrid();
	  if(DealType=='ZC')
	  {
	  	divZC.style.display = '';
      divGQ.style.display = 'none';
	  }
	  else
	  {
	  	divZC.style.display = 'none';
      divGQ.style.display = '';
	  }
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б��ĳ�ʼ��
function initContGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	
	  iArray[2]=new Array();
      iArray[2][0]="ӡˢ����";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="Ͷ����";         		//����
	  iArray[3][1]="160px";            		//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ContGrid.mulLineCount = 5;   
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 1;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      ContGrid. selBoxEventFuncName = "easyQueryAddClick";
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ�����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=2; 
      iArray[3][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="6";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;

      iArray[4]=new Array();
      iArray[4][0]="���ְ汾";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		    //����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=7;              			//�Ƿ���������,1��ʾ������0��ʾ������
	  iArray[6][22]="col5";
	  iArray[6][23]=1;
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ����";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="������";         		//����
      iArray[8][1]="120px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 0;
      PolGrid.loadMulLine(iArray);  
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>