function showMoreItems(){
	var more = document.getElementById("more-table");
	if(more.style.display == "none"){
		more.style.display = "table";
	}else{
		more.style.display = "none";
	}
	return false;
}