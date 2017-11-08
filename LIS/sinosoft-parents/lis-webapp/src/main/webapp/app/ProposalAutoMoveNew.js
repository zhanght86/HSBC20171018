var SubType="";
var sqlid47="ContPolinputSql47";
var mySql47=new SqlClass();
mySql47.setResourceName("app.ContPolinputSql");
mySql47.setSqlId(sqlid47);
mySql47.addSubPara(prtNo);
var SubTypeSql=mySql47.getString();
var SubTypeArr = easyExecSql(SubTypeSql);
if(SubTypeArr!=null){SubType=SubTypeArr[0][0];}
var objName;

function setFocus() {
  var RowNo=0;
  var MultLineName = "";
  for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { 

    	var tFunction = "goToAreaNew";
      if(window.document.forms[0].elements[elementsNum].name.indexOf("Grid")==-1){
         eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);   
      } else {
      		 var tName = window.document.forms[0].elements[elementsNum].name.substring(0,window.document.forms[0].elements[elementsNum].name.indexOf('Grid'));
      		 
      		 if(MultLineName == null || MultLineName ==""){  
      		 	  MultLineName = tName;
      		 } else {
      		 	 if(MultLineName!=tName) {
      		 	 	  MultLineName = tName;
      		 	 	  RowNo = 0 ;
      		    }
      		 	 if(window.document.forms[0].elements[elementsNum].name.indexOf("No")!=-1){
      				  RowNo = RowNo + 1;
      			 }
      		}
  				eval("window.document.forms[0].elements[elementsNum].setAttribute('MultLineId','"+RowNo+"');");
					eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);   
      	}  
  } 
   var mullist = $('.muline');
   $('.muline').show();
  $.each(mullist, function(i){
  	//alert();
		try{
			mullist[i].click(function(){
				
				var objID = mullist[i].id;
				if(objID!=null && objID !=""){
					goToAreaNewById(objID);
				}	
			});
		} 
		catch(ex){}
	});
}
function goToAreaNew() {

  objName = this.name;
  try {
		 var vm = top.viewMode%3;
		 
		 if(vm!=0){
		 		try{top.fraPic.ViewShow(objName,vm);}catch(e){};
		 }else{
		 		ScanViewShowStation(objName,0);
		 }
	} catch(e) {}
			

}
function goToAreaNewById(objID) {
			alert(objID);
			objName = objID;
  		try {
				 var vm = top.viewMode%3;
				 
				 if(vm!=0){
				 		try{top.fraPic.ViewShow(objName,vm);}catch(e){};
				 }else{
				 		ScanViewShowStation(objName,0);
				 }
			} catch(e) {}

}
