<%
//PEdorTypeWPInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">  
//����ʱ��ѯ

function initForm()
{
  try
  {
    initInpBox();
    initCustomerGrid();
    queryCustomerInfo();
    initPolGrid();
    initNoteGrid(); //׷�Ӽ�¼
    getCustomerGrid();
    queryNoteGrid();
    showRiskInfo();   
  }
  catch(ex)
  {
    alert("�޷���ʼ������!ԭ�������:�������ѹر�!");
    return;
  }
}

function initInpBox()
{ 
   
  	Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;  
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    //document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('PEdorType','EdorType','EdorTypeName');

}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeWPInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
 //�ͻ�������Ϣ�б�
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="90px";
        iArray[2][2]=50;
        iArray[2][3]=0;
        iArray[2][21]=2;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=2;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=2;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="150px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerGrid.mulLineCount = 0;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.selBoxEventFuncName = "";
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorTypeFTInit.jsp --> initCustomerGrid �����з����쳣:��ʼ���������");
    }
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
	var strSQL="";
	if(tContNo!=null || tContNo !='')
	  {
	  strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
										+" Union"
										+" Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";

	}
	else
	{
		alert('û����Ӧ��Ͷ���˻򱻱�����Ϣ��');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		  return false;
	}
	
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = CustomerGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function initPolGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="���ִ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         			//����2
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����8
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";     //����6
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ѱ�׼";         		//����8
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����5
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		//����5
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.hiddenPlus = 1; 
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);  
      PolGrid.detailInfo="������ʾ��ϸ��Ϣ";      
      PolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}

function showRiskInfo()
{
    
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {        
        var strSQL = "select a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lcpol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' order by a.PolNo asc "; 
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult) 
        {
           return;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;    
        //����SQL���
        turnPage.strQuerySql = strSQL; 
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;  
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }

}

function initMRPolGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="���ִ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         			//����2
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����8
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";     //����6
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ѱ�׼";         		//����8
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����5
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		//����5
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    

      MRPolGrid = new MulLineEnter( "fm" , "MRPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
      MRPolGrid.mulLineCount = 0;   
      MRPolGrid.displayTitle = 1;
      MRPolGrid.canSel=0;
      MRPolGrid.hiddenPlus = 1; 
      MRPolGrid.hiddenSubtraction = 1;
      MRPolGrid.selBoxEventFuncName ="";
      MRPolGrid.loadMulLine(iArray);  
      MRPolGrid.detailInfo="������ʾ��ϸ��Ϣ";      
      MRPolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}

//׷�Ӽ�¼
function initNoteGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="׷�ӽ��";
        iArray[4][1]="70px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        iArray[4][21]=3;

        iArray[5]=new Array();
        iArray[5][0]="���ֺ�";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="��ʼ�����";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="��ʼ�������ȡ����";
        iArray[7][1]="110px";
        iArray[7][2]=110;
        iArray[7][3]=0;
        iArray[7][21]=3;

        NoteGrid = new MulLineEnter("fm", "NoteGrid");
        //��Щ���Ա�����loadMulLineǰ
        NoteGrid.mulLineCount = 0;
        NoteGrid.displayTitle = 1;
        NoteGrid.canSel=1;
        NoteGrid.hiddenPlus=1;
        NoteGrid.hiddenSubtraction=1;
        //NoteGrid.selBoxEventFuncName ="getInsuAccb" ;
        NoteGrid.loadMulLine(iArray);
     }
     catch(ex)
     {
           alert("��PEdorTypeWPInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
     }
}

function showMRRiskInfo()
{
    
    var tContNo=document.all("ContNo").value;
    var tEdorNo=document.all("EdorNo").value;
    if(tContNo!=null&&tContNo!="")
    {        
        var strSQL = "select distinct a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lppol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' and a.edorno = '"+tEdorNo+"'"; 
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult) 
        {
           return;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = MRPolGrid;    
        //����SQL���
        turnPage.strQuerySql = strSQL; 
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;  
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    }

}






</script>