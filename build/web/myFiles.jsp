<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    
<head>
	<link rel="stylesheet" type="text/css" href="style.css">
	<title>MyFiles</title>
</head>
<body>
   
<ul class="navbar">
        <p id="welcome" class="welcome">Welcome ${user.getName()}</p>
	<li class="navbar"><a href="index.jsp" class="scale_transition home navbar">Home</a></li>
	<li class="navbar"><a href="index.jsp" class="scale_transition logout navbar">Logout</a></li>
</ul>

<div align="left" class="myfiles"><p class="myfiles_title">&nbsp Uploaded Files</p>
	<div class="options" id="Options">
		<p align="center" class="options_title">Options</p>
		<input type="checkbox" name="check" onchange="sortListBySize();unCheck(this)"/> Order files by size</input>
		<input type="checkbox" name="check" onchange="unCheck(this);sortListByDate()"/>Order files by date</input>
		<br>
		<input type="checkbox" name="check" onchange="sortListAlphabetically();unCheck(this)"/> Order files alphabetically</input>
		<br>
		<input type="text" id="searchInput" class="search_field" onkeyup="Search()" placeholder="Search for files..">
		tag:
		<select id="tagFilter" onchange="selectByTag()">
			<option value="notag">no tag</option>
			<option value="document">document</option>
            <option value="application">application</option>
            <option value="compressed">compressed</option>
		</select>

	</div>
<ul id="fileslist" class="myfiles">
     <c:forEach items="${files}" var="file">
     <li class="myfiles scale_transition">${file.getName()}<button class="delete_button" onclick="deleteElement(this)"></button><div class="detalii myfiles"><p class="detalii">&gt;${file.getSize()}</p><p class="detalii">&gt;${file.getType()}</p><p class="detalii">&gt;16 aprilie</p><button class="download_button"></button></div></li>
    </c:forEach>
</ul>
</div>

<div class="upload">
    <form id="uploadform" method="POST" ecntype="multipart/form-data">
	&nbsp Upload File:
    <input type="file" id="myFile">
    <input type="submit" action="#" onclick="uploadFile()" value="Upload">
</form>
</div>

<script>
    
	function uploadFile(size,type,name)
	{
		var file=document.getElementById("myFile");
                
                
		if(file.value){
			var Name="";
			var Size="";
			var Type="";
			var ul=document.getElementById("fileslist");
			var li=document.createElement("li");
			var div=document.createElement("div");
			var pSize=document.createElement("p");	
            var pType=document.createElement("p");
			var downloadButton=document.createElement("button");
			var	deleteButton=document.createElement("button");
			var pUploadDate=document.createElement("p");
		
		for (var index=0;index<file.files.length;index++){
			var fileAttributes=file.files[index];
			if('name' in fileAttributes){
				Name +=fileAttributes.name;
			}
			if('size' in fileAttributes){
				Size +="Size:" + Math.round(parseInt(fileAttributes.size)/1024) + " KB";
			}
			if('type' in fileAttributes){
				 Type+="Type:" + fileAttributes.type;
			}
		}
                Name=name;
                Type=type;
                Size=size;
		li.appendChild(document.createTextNode(Name));
		deleteButton.setAttribute("class","delete_button");
		deleteButton.setAttribute("onclick","deleteElement(this)");
		li.appendChild(deleteButton);
		li.setAttribute("class","myfiles scale_transition");
		div.setAttribute("class","detalii myfiles");
		downloadButton.setAttribute("class","download_button");
		pSize.appendChild(document.createTextNode(">"+Size));
		pType.appendChild(document.createTextNode(">"+Type));
		pUploadDate.appendChild(document.createTextNode(">Uploaded:"+new Date().toString().split(' ').splice(1,3).join('/')));
		pSize.setAttribute("class","detalii");
		pType.setAttribute("class","detalii");
		pUploadDate.setAttribute("class","detalii");
		div.appendChild(pSize);
		div.appendChild(pType);
		div.appendChild(pUploadDate);
		div.appendChild(downloadButton);
		li.appendChild(div);
		ul.appendChild(li);
                return false;
	}
	return false;
	}

	function sortListBySize(){
		var list,index,switching,li,shouldSwitch,dir,switchCount=0;
		list=document.getElementById("fileslist");
		switching=true;
		dir="asc";
		while(switching){
			switching=false;
			li=list.getElementsByTagName("li");
			for(index=0;index<(li.length-1);index++){
				size1=(((li[index]).childNodes[2]).childNodes[0]).innerHTML;
				size2=(((li[index+1]).childNodes[2]).childNodes[0]).innerHTML;
				shouldSwitch=false;

				if(dir=="asc"){
					if(size1>size2){
						shouldSwitch=true;
						break;
					}
				}
				else{
					if(dir=="desc"){
						if(size1<size2){
							shouldSwitch=true;
							break;
						}
					}
				}
			}
			if (shouldSwitch){
				li[index].parentNode.insertBefore(li[index+1],li[index]);
				switching=true;
				switchCount++;
			}
			else{
				if(switchCount==0 && dir=="asc"){
					dir="desc";
					switching=true;
				}
			}
		}
	}

	function sortListByDate(){
		var list,index,switching,li,shouldSwitch,dir,switchCount=0;
		list=document.getElementById("fileslist");
		switching=true;
		dir="asc";
		while(switching){
			switching=false;
			li=list.getElementsByTagName("li");
			for(index=0;index<(li.length-1);index++){
				size1=(((li[index]).childNodes[2]).childNodes[2]).innerHTML;
				size2=(((li[index+1]).childNodes[2]).childNodes[2]).innerHTML;
				shouldSwitch=false;

				if(dir=="asc"){
					if(size1>size2){
						shouldSwitch=true;
						break;
					}
				}
				else{
					if(dir=="desc"){
						if(size1<size2){
							shouldSwitch=true;
							break;
						}
					}
				}
			}
			if (shouldSwitch){
				li[index].parentNode.insertBefore(li[index+1],li[index]);
				switching=true;
				switchCount++;
			}
			else{
				if(switchCount==0 && dir=="asc"){
					dir="desc";
					switching=true;
				}
			}
		}
	}

	function sortListAlphabetically(){
		var list,index,switching,li,shouldSwitch,dir,switchCount=0;
		list=document.getElementById("fileslist");
		switching=true;
		dir="asc";
		while(switching){
			switching=false;
			li=list.getElementsByTagName("li");
			for(index=0;index<(li.length-1);index++){
				size1=(li[index]).innerHTML;
				size2=(li[index+1]).innerHTML;
				shouldSwitch=false;

				if(dir=="asc"){
					if(size1>size2){
						shouldSwitch=true;
						break;
					}
				}
				else{
					if(dir=="desc"){
						if(size1<size2){
							shouldSwitch=true;
							break;
						}
					}
				}
			}
			if (shouldSwitch){
				li[index].parentNode.insertBefore(li[index+1],li[index]);
				switching=true;
				switchCount++;
			}
			else{
				if(switchCount==0 && dir=="asc"){
					dir="desc";
					switching=true;
				}
			}
		}
	}

	function unCheck(e){
		var checkboxes=document.getElementsByName("check");
		for(var index=0;index<checkboxes.length;index++){
			checkboxes[index].checked=false;
		}
		e.checked=true;
	}

	function Search()
{
var searchInput,ul,li,filename,i,txtValue;

searchInput=(document.getElementById("searchInput")).value.toUpperCase();
ul=document.getElementById("fileslist");
li=ul.getElementsByTagName("li");

for(i=0;i<li.length;i++)

if((li[i].innerText).toUpperCase().indexOf(searchInput) > -1)
	li[i].style.display="";
else
	li[i].style.display="none";
}

function deleteElement(e)
{
	var li=e.parentNode;
	li.remove();
}

function selectByTag()
{
var option=document.getElementById("tagFilter").value;
var ul=document.getElementById("fileslist");
var li=ul.getElementsByTagName("li");

for(var i=0;i<li.length;i++)
{

if(option=="notag")
	li[i].style.display="";

else if(option=="document")
{
	if(((li[i].innerText).toUpperCase()).search(".PDF")>-1 || ((li[i].innerText).toUpperCase()).search(".TXT")>-1) li[i].style.display="";
else li[i].style.display="none";
}

else if(option=="application")
{
	if(((li[i].innerText).toUpperCase()).search(".EXE")>-1 || ((li[i].innerText).toUpperCase()).search(".DAT")>-1) li[i].style.display="";
else li[i].style.display="none";
}

else if(option=="compressed")
{
	if(((li[i].innerText).toUpperCase()).search(".RAR")>-1 || ((li[i].innerText).toUpperCase()).search(".ZIP")>-1) li[i].style.display="";
else li[i].style.display="none";
}

}

}
window.addEventListener('resize', function(event){
	var ul=document.getElementById("fileslist");
	var li=ul.getElementsByTagName("li");
	var options=document.getElementById("Options");
	if(window.innerWidth<=700)
	{
		options.style.fontSize="70%";
     for(var i=0;i<li.length;i++)
     {
     	li[i].style.width="30%";
     	(li[i].childNodes[2]).style.fontSize="65%";
     	((li[i].childNodes[2]).childNodes[3]).style.float="left";
     	((li[i].childNodes[2]).childNodes[3]).style.clear="left";

     }
 }
     else 
     {
     	options.style.fontSize="80%";
     	for(var i=0;i<li.length;i++)
     {
     	li[i].style.width="";
     	(li[i].childNodes[2]).style.fontSize="100%";
     	((li[i].childNodes[2]).childNodes[3]).style.float="";
     	((li[i].childNodes[2]).childNodes[3]).style.clear="";
    }
}
     
});

function welcome(name)
{
	var p_welcome=document.getElementById("welcome");
	p_welcome.innerText=name;
}

</script>
</body>
</html>