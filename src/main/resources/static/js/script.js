
/*function toggleSidebar(){
	let sidebar_box = document.getElementById("sidebar");
	let content_box = document.getElementById("content");
	if(sidebar_box.classList[0]=="sidebar"){
		sidebar_box.classList.remove("sidebar");
		sidebar_box.classList.add("abc");
		content_box.classList.add("contentClose");
		if(sidebar_box.style.display=="none"){
		sidebar_box.style.display=="block";
		
	}
	
	}else{
		sidebar_box.classList.remove("abc");
		sidebar_box.classList.add("sidebar");
		content_box.classList.remove("contentClose");
	}
}
*/

const toggleSidebar = () =>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
};

const closeSidebar = () =>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
};


function openImage(idd){
	console.log("this is id in req"+ idd);
	let open_images  = document.getElementsByClassName("container_box")[idd];
	if(open_images.className=="container_box"){
		console.log(open_images.className);
		open_images.classList.add("extended_container_box");
	}
	else{
			open_images.classList.remove("extended_container_box");
	}console.log(open_images.className);
	
}




const navlink = document.querySelectorAll(".nav_link");
const windowPathname =  window.location.pathname;
navlink.forEach(navlinkEL =>{
	if(navlinkEL.href.includes(windowPathname)){
		navlinkEL.classList.add('active');
	}
})




//this is the search controller javaScript

const search =()=>{
	let query = $("#search-input").val();

	//search the contacts and send the request to the server
	if(query ==""){
		$(".search-result").hide();
	}
	else{
		console.log("QUERY " + query);
		

		let url = `http://localhost:8080/search/${query}`;
		fetch(url).then((response) =>{
			return response.json();
		}).then((data) =>{
			console.log(data);


			let text = `<div class='list-group' style="border-radius: 10px;">`;
			data.forEach((contact)=>{
				text += `<a href='/user/${contact.cid}/contact' class='list-group-item list-group-item-action search_result_links' style="color:blue;border:none;"> ${contact.name}</a>`;
			});
			text += `</div>`;


			$(".search-result").html(text);
		});

		$(".search-result").show();
	}
}

const search_result_links = document.querySelectorAll(".search_result_links");
search_result_links.forEach(search_result_links =>{
	if(search_result_links.href.includes(windowPathname)){
		search_result_links.classList.add('active');
	}
})

const showspinner=()=>{
	console.log("Login");
	$(".spinner").show();
	$(".spinner_container").show();		
}



const openChatBox = () => {
	let help_container = document.getElementById("help-container-box");
	help_container.style.display="block";

	// remove the tinymice div
	let warning_div = document.querySelectorAll(".tox-notification");
	warning_div.style.display="none";
	
}
const closeHelpContainerBox = () =>{
	let close_help_container_box = document.getElementById("help-container-box");
	close_help_container_box.style.display="none";
}


const  openOTPBox = () => {
	let forgot_password_box =  document.getElementById("forgot-password-box");
	console.log(forgot_password_box);
	forgot_password_box.style.display="none";

	let a = document.getElementById("otp-box");
	a.style.display = "block";
}