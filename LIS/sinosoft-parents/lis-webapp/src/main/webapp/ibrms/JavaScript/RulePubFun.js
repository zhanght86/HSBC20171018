function getOneRuleID(tRuleName) {
	var urlStr="./JavaScript/getOneRuleID.jsp";
					var paramsStr = "RuleName="+tRuleName;
					if (window.ActiveXObject) {
							Request = new ActiveXObject('Microsoft.XMLHTTP');
					} else if (window.XMLHttpRequest) {
							Request = new XMLHttpRequest();
					}
				
					try{
       			 	Request = new ActiveXObject("Microsoft.XMLHTTP");
 					}
			 		catch (ex)
        	{
           	 //alert("!!" + ex.message);
            	Request = new ActiveXObject("MSXML2.XMLHTTP.4.0");
       	 }
        
				Request.open('POST', urlStr, false);
				Request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
				Request.send(paramsStr);
				var result = Request.responseText;
	return result.trim();
}