
$(document).ready(function() {
	//$('#comments').collapsible({xoffset:'-10',yoffset:'5',defaulthide: false});
});

function doReply(loopbackId, userId){
	$("tr#" + loopbackId).after(
			"<div id='addComment'>" +
				"<input id='commentText' type='text'>" +
				"<button id='addCommentButton' onclick='addComment(" + loopbackId + "," +
					userId + ")'>OK</button><button id='clearCommentBoxButton' " +
						"onclick='clearCommentBox()'>Cancel</button>" +
			"</div>"
			);
	return false;
}

function addComment(loopbackId, userId) {
	var comment = $("input#commentText")[0].value;
	clearCommentBox();
	$("tr#" + loopbackId).after(
		"<tr><td>" +
		"&nbsp;&nbsp;&nbsp;<table><tr><td>" + comment + "</td></tr></table>" +
		"</td></tr><td></td><td></td>"
	);
	
}

function clearCommentBox(){
	$("div#addComment").remove();
}

function postComment(loobackId, userId, comment) {
	
}