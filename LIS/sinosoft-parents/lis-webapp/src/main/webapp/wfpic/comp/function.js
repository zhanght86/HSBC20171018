//ѡ�е�ѡ��
function setRadioGroupValue(RadioGroup,SetValue)
{
   if (SetValue!='')
   {
	  for(var i=0;i<RadioGroup.length;i++)
      {if (RadioGroup[i].value == SetValue){RadioGroup[i].checked = true;return true}}
	  return false
   }
   else {return false}   
}

//��������б�Option
function addSelectOption(listName,optText,optValue,optSelected)
{
  var oOption = document.createElement("OPTION")
  listName.options.add(oOption)
  oOption.innerText = optText
  oOption.value = optValue
  oOption.selected = optSelected
}  

//ȡselect�����б�ֵ
function getSelectValue(listName){
   if(listName.selectedIndex!=-1){return listName.options[listName.selectedIndex].value}
   else{return ''}
}

//ȡselect�����б��ı�
function getSelectText(listName){
   if(listName.selectedIndex!=-1){return listName.options[listName.selectedIndex].innerText}
   else{return ''}
}

//ȡ��ѡ���ֵ
function getRadioGroupValue(RadioGroup){
   for(var i=0;i<RadioGroup.length;i++)
      {if (RadioGroup[i].checked){return RadioGroup[i].value}}
   return ""
}


var Util = new Object();

Util.isEmpty = function(obj)
{
	return obj==null||obj==undefined||typeof(obj)=="undefined"||obj=="";
}

function isSearch(pat,str)
{
   regex = new RegExp(pat);
   if(regex.exec(str))
   {
      return true;	
   }	
   return false;
}