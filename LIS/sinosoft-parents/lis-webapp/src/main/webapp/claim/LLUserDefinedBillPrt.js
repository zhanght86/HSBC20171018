
//Name:LLUserDefinedBillPrt.js
//Function���û��Զ����ӡ


function showMyPage(spanID,flag)
{
  if(!flag)//�ر�
  { 
    spanID.style.display="none";
  }
  else    //��
  {
    spanID.style.display="";
  }
}

//���ӵ�֤
function addAffix()
{
	try
	{   
		var affixCode="";
		var affixName="";
		if(!fm.OtherAffix.checked)
		{
			affixCode=fm.AffixCode.value;
			affixName=fm.AffixName.value;
			if (affixCode=="")
			{
				alert("��ѡ�����赥֤");
				return;
			}
			if(codeCheck(affixCode)==false)
			{
				alert("�õ�֤�Ѵ��ڣ�������ѡ��");
				return;
			}
		}  
		else
		{ 
			affixCode="000000";
			affixName=fm.OtherName.value;
			if(affixName=="")
			{
				alert("�����뵥֤����");
				return;
			}
		}
		
		var rows=AffixGrid.mulLineCount;
		AffixGrid.addOne();
		AffixGrid.setRowColData(rows,1,affixCode);
		AffixGrid.setRowColData(rows,2,affixName);
		clearData();
	}
	catch(ex)
	{
		alert("���ӵ�֤��������:"+ex);
	}  
}


//�������
function clearData()
{
  fm.AffixCode.value="";
  fm.AffixName.value=""; 
  fm.AffixCode.disabled=false;	
  fm.AffixName.disabled=false;	
  fm.OtherAffix.checked=false;
  fm.OtherName.value="";
  fm.OtherName.disabled=true;
}

//����Ƿ����ظ� 
function codeCheck(mcode)
{
	try
	{
		var rows=AffixGrid.mulLineCount;
		var affixCode="";
		for (var i=0;i<rows;i++)
		{
			affixCode=AffixGrid.getRowColData(i,1);
			if (affixCode==mcode)
			{
				return false;
			}
		}
	}
	catch(ex)
	{
		alert("���ݼ�����:"+ex);
		return false;
	}
}

function CheckboxClick()
{
//	alert(fm.OtherAffix.checked);
	fm.AffixCode.disabled=fm.OtherAffix.checked;
	fm.AffixName.disabled=fm.OtherAffix.checked;
	fm.OtherName.disabled=!fm.OtherAffix.checked;
	fm.OtherName.value="";
	fm.AffixCode.value="";
  	fm.AffixName.value=""; 
}

function showAffixPrint()
{
	var rows=AffixGrid.mulLineCount;
	if(rows==0)
	{
		alert("û�п��Դ�ӡ�����ݣ�");
		return;	
	}
	fm.action = './LLUserDefinedBillPrtSave.jsp';   //
	fm.target = "../f1print";
	fm.submit();
}