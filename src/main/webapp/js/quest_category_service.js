function quest_category_service(url,select_id) {
    var request = new XMLHttpRequest();
    var language = document.forms.form_new_quest.quest_lang.options[document.forms.form_new_quest.quest_lang.selectedIndex].value;
    var param = encodeURIComponent(language);
    var new_url = url+'/'+param;
    request.open("GET", new_url,true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 201) {
            var type = request.getResponseHeader("Content-Type");
            if (type === "application/json;charset=UTF-8")
                draw_quest_category_combobox(select_id,JSON.parse(request.responseText));
        }
    }
    request.send(null);
}
