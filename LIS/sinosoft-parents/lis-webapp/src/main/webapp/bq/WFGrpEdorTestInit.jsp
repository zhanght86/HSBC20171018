<% 
//�������ƣ�WFEdorNoscanAppInit.jsp
//�����ܣ���ȫ������-��ȫ��ɨ������
//�������ڣ�2005-04-27 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

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
    alert("WFEdorAppInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {    
    initInpBox();
    initSelfGrid(); //��ʼ���ҵ��������
    easyQueryClickSelf();  //��ѯ�ҵ��������
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//
function initSelfGrid()
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
      iArray[1][0]="������ˮ��";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/�ŵ�������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�´ν��Ѷ�Ӧ��";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
      iArray[6]=new Array();
      iArray[6][0]="���뷽ʽ";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
      iArray[7]=new Array();
      iArray[7][0]="�������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
              
      iArray[8]=new Array();
      iArray[8][0]="¼��Ա";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
      iArray[9]=new Array();
      iArray[9][0]="¼������";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����������";         	//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�������������";        //����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="�������Id";         	//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      SelfGrid = new MulLineEnter( "document" , "SelfGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;        
      SelfGrid.loadMulLine(iArray);  

      //��Щ����������loadMulLine����

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>