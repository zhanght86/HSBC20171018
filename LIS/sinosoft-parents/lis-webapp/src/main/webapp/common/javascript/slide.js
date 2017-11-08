$(document).ready(function () {
	$(".drama-poster li").live("hover", function () {
		$(".current").removeClass("current");
		$(this).addClass("current") 
	});
	
    $(".drama-poster li.show-poster-3 a").click(function () {
	    $(".selected").removeClass("selected");
		$(this).addClass("selected");
		var cols = parent.document.getElementById("fraSet").cols;
		var index = cols.indexOf(",");
		var obj = cols.split(",");
		parent.document.getElementById("fraSet").cols="180,*,"+obj[2]+",0%";
	});	
 
});

$(function () {
	var a = $(".drama-poster .show-poster-3");
	a.mouseover(function () {
		a.removeClass("current");
		$(this).addClass("current") 
	});
	$(".drama-slide li.next a").click(function () {
		var b = $(".drama-poster .change>li:first"), c = $(".drama-poster .change .current").index();
		$(".drama-poster .change>li:last").after(b);
		$(".drama-poster .change .show-poster-3").removeClass("current");
		$(".drama-poster .change").find(".show-poster-3").eq(c).addClass("current") 
	});
	$(".drama-slide li.prev a").click(function () {
		var c = $(".drama-poster .change>li:last"), b = $(".drama-poster .change .current").index();
		$(".drama-poster .change>li:first").before(c);
		$(".drama-poster .change .show-poster-3").removeClass("current");
		$(".drama-poster .change").find(".show-poster-3").eq(b).addClass("current") 
	}) 
});


