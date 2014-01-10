/*
Put violations vocabulary here.
*/
var vocabulary = {"low": [["法轮功", ""],["江泽民", ""],["邱俊涛", ""],["64", ""],["六四", ""],
["天安门事件", ""],["sex", ""],["胡凯", ""], ["刘尧", ""],["尧姐", ""],["HK", ""],["hk", ""],
["高莉", ""]],  "medium":[],  "high": []
}
var VIOLATIONS_WARNING = "根据国家相关法律和政策，您的帖子不能发布!"

 function getById(id) {
    return document.getElementById(id);
 }

 function violationsCheck(content) {
    for(var i=0; i<vocabulary.low.length; i++) {
        if(content.search(vocabulary.low[i][0]) > -1)
            return false;
    }
    return true;
 }

 function validateById(id) {
    return violationsCheck(getById(id).value);
 }

 function contentEmpty(contents) {
    for(var i=0; i<contents.length; i++) {
        content = getById(contents[i]).value.replace(/\s/g,'');
        if(content == "")
            return true;
    }
    return false;
 }

 function contentLegal(contents,messageBar,message) {
    var ret = true;
    for(var i=0; i<contents.length && ret; i++) {
        ret = ret && validateById(contents[i]);
    }
    if(!ret){
        getById(messageBar).className="page-action create-error";
        getById(messageBar).innerHTML=message;
        getById(messageBar).style.width="475px";
    }
    return ret;
 }

