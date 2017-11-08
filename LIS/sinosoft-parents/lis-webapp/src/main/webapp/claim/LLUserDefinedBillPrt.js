
//Name:LLUserDefinedBillPrt.js
//Function：用户自定义打印


function showMyPage(spanID,flag)
{
  if(!flag)//关闭
  { 
    spanID.style.display="none";
  }
  else    //打开
  {
    spanID.style.display="";
  }
}

//增加单证
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
				alert("请选择所需单证");
				return;
			}
			if(codeCheck(affixCode)==false)
			{
				alert("该单证已存在，请重新选择");
				return;
			}
		}  
		else
		{ 
			affixCode="000000";
			affixName=fm.OtherName.value;
			if(affixName=="")
			{
				alert("请输入单证名称");
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
		alert("增加单证发生错误:"+ex);
	}  
}


//清除数据
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

//检查是否有重复 
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
		alert("数据检测错误:"+ex);
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
		alert("没有可以打印的数据！");
		return;	
	}
	fm.action = './LLUserDefinedBillPrtSave.jsp';   //
	fm.target = "../f1print";
	fm.submit();
}