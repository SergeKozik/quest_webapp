function draw_quest_list(quests) {
    var quest_list = document.getElementById('quest-list');
    var html_content="";
    for (i in quests) {
        html_content+=draw_quest(quests[i]);
    }
    quest_list.innerHTML = html_content;
}
