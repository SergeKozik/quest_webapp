function quest_list_service(url) {
    var request = new XMLHttpRequest();
    //var formData = new FormData(document.forms.quest_menu_form);
    var formData = document.forms.quest_menu_form;
    var params = "";
    for (var i = 0; i < formData.elements.length; i++ ) {
        if (formData.elements[i].type == 'checkbox') {
            if (formData.elements[i].checked == true) {
                params += formData.elements[i].name+"="+formData.elements[i].value+"&";
            }
        }
    }
    request.open("POST", url);
    request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    //request.setRequestHeader("Content-length", params.length);
    //request.setRequestHeader("Connection", "close");
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 201) {
            var type = request.getResponseHeader("Content-Type");
            if (type === "application/json;charset=UTF-8")
                draw_quest_list(JSON.parse(request.responseText));
        }
    }
    request.send(params);
    //request.send(formData);
}
