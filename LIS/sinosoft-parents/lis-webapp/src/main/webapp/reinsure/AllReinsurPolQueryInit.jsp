<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�ProposalQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                         

<script type="text/javascript">
//���ذ�ť��ʼ��
var str = "";
/**
function initDisplayButton()
{
	var tDisplay = <%=//tDisplay%>;
	if (tDisplay=="1")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="0")
	{
		fm.Return.style.display='none';
	}
}
*/
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    fm.all('PolNo').value = '';
    fm.all('GrpPolNo').value = '';
    fm.all('RiskCode').value = '';
    fm.all('CessStart').value = '';
    fm.all('ReinsurItem').value = '';
    fm.all('ProtItem').value = '';
    fm.all('InsuredName').value = '';
    fm.all('InsuredNo').value = '';
    fm.all('InsuredBirthday').value = '';
  }
  catch(ex)
  {
    myAlert("��AllReinsurPolQueryInit.jsp-->"+"��ʼ���������!");
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
    myAlert("��ProposalQueryInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
//	initDisplayButton();
	
  }
  catch(re)
  {
    myAlert("ProposalQueryInit.jsp-->"+"��ʼ���������!");
  }
}

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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="�ٱ���Ŀ";         		//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���屣����";         		//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=2; 
      iArray[4][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;
     
      
      iArray[5]=new Array();
      iArray[5][0]="ǩ������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��Ч����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;    
      
      iArray[8]=new Array();
      iArray[8][0]="��׼����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;       
      
      iArray[9]=new Array();
      iArray[9][0]="���ս��";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="�ֱ�����";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;       
      
      iArray[11]=new Array();
      iArray[11][0]="�ֱ����";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;       
      
      iArray[12]=new Array();
      iArray[12][0]="�ֱ����� ";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=0;       
      
      iArray[13]=new Array();
      iArray[13][0]="�ֱ�Ӷ�� ";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=0;       
      
      iArray[14]=new Array();
      iArray[14][0]="���Ᵽ�� ";         		//����
      iArray[14][1]="60px";            		//�п�
      iArray[14][2]=200;            			//�����ֵ
      iArray[14][3]=0;       
      
      iArray[15]=new Array();
      iArray[15][0]="����ֱ��� ";         		//����
      iArray[15][1]="60px";            		//�п�
      iArray[15][2]=200;            			//�����ֵ
      iArray[15][3]=0;       
      
      iArray[16]=new Array();
      iArray[16][0]="����ֱ�Ӷ�� ";         		//����
      iArray[16][1]="60px";            		//�п�
      iArray[16][2]=200;            			//�����ֵ
      iArray[16][3]=0;       
      
      iArray[17]=new Array();
      iArray[17][0]="������Ͷ������ ";         		//����
      iArray[17][1]="60px";            		//�п�
      iArray[17][2]=200;            			//�����ֵ
      iArray[17][3]=0;       

      iArray[18]=new Array();
      iArray[18][0]="�������Ա�";         		//����
      iArray[18][1]="60px";            		//�п�
      iArray[18][2]=200;            			//�����ֵ
      iArray[18][3]=0;       
      
                			//�Ƿ���������,1��ʾ����0��ʾ������
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        myAlert(ex);
      }
}

</script>