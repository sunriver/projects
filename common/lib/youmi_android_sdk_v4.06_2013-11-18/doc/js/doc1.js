var lan="zh";

$().ready(function(){
	$("a").click(function(){
		
		$("#docs").attr("src","sdkdoc/"+lan+"/"+$(this).attr("href"));
		return false;				
	});
})

 
