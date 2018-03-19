function draw_quest(quest_item) {
    var date = new Date(quest_item.dateLong);
    var result = "<div class='quest-card'><div>"+quest_item.title+"</div>"+
        "<table>"+
        "<tr>"+
        "<td>"+quest_item.languageName+"</td>"+
        "<td>"+quest_item.language+"</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+quest_item.categoryName+"</td>"+
        "<td>"+quest_item.category+"</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+quest_item.typeResultName+"</td>"+
        "<td>"+quest_item.typeResultNative+"</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+quest_item.authorName+"</td>"+
        "<td>"+quest_item.author+"</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+quest_item.dateName+"</td>"+
        "<td>"+date.toLocaleString()+"</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+quest_item.passedName+"</td>"+
        "<td>"+quest_item.passed+"</td>"+
        "</tr>"+
        "</table>"+
        "<form action=/user/quest-start.html method='post'>"+
        "<input type='hidden' name='quest_id' value='"+quest_item.id+"'>";
    for (i in quest_item.actionList) {
        result+="<input class='button-start' type='submit' value='"+quest_item.actionList[i].buttonCaption+"' formaction='"+quest_item.actionList[i].href+"'>";
    };
        result+="</form></div>";
    return result;
}
