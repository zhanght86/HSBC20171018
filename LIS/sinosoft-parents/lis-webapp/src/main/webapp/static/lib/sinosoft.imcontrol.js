"use strict";
var imctx = "";

var PushMode = {
    Polling: 0,
    LongPolling: 1,
    WebSocket: 2,
};
function createMappedPropertyString(name, map_func)
{
    var property = new String(name);
    if(map_func)
        property.getMappedProperties = map_func;
    return property;
}
function getTextLineCount(text) {
    if (!text)
        return 0;
    var result = 1;
    for (var i = 0; i < text.length; i++) {
        if (text[i] == '\r' || text[i] == '\n') {
            ++result;
            if (i + 1 < text.length && text[i] == '\r' && text[i + 1] == '\n')
                ++i;
        }
    }
    return result;
}

function getSearchResultString(user)
{
    var result = "";
    var properties = imcontrol.DataEntityProperties.UserSearchProperties;
    for (var i = 0; i < properties.length; i++)
    {
        var pstr = properties[i];
        if (pstr.indexOf('.') == -1) {
            if (i == properties.length - 1) {
                result += user[pstr];
            } else {
                result += user[pstr] + " - ";
            }
        } else {
            var pstrArray = pstr.split('.');
            if (user[pstrArray[0]] !== undefined && user[pstrArray[0]] !== null)
            {
                if (i == properties.length - 1) {
                    result += user[pstrArray[0]][pstrArray[1]];
                } else {
                    result += user[pstrArray[0]][pstrArray[1]] + " - ";
                }
            }
        }
    }
    return result;
}
function sortUserList(users)
{
    users.sort(function (u1, u2) {
        if ((u1 === null || u1 === undefined) && (u2 === null || u2 === undefined))
        {
            return 0;
        } else if (u1 === null || u1 === undefined)
        {
            return -1;
        } else if (u2 === null || u2 === undefined) {
            return 1;
        } else {
            if ((u1.name === null || u1.name === undefined) && (u2.name === null || u2.name === undefined)) {
                return 0;
            } else if (u1.name === null || u1.name === undefined) {
                return -1;
            } else if (u2.name === null || u2.name === undefined) {
                return 1;
            } else {
                var tmpResult = u1.name > u2.name;
                if (u1.name.localeCompare)
                    tmpResult = u1.name.localeCompare(u2.name);
                if (tmpResult != 0)
                    return tmpResult
                else
                {
                    if ((u1.department === null || u1.department === undefined) && (u2.department === null || u2.department === undefined)) {
                        return 0;
                    } else if (u1.department === null || u1.department === undefined) {
                        return -1;
                    } else if (u2.department === null || u2.department === undefined) {
                        return 1;
                    } else {
                        if ((u1.department.name === null || u1.department.name === undefined) && (u2.department.name === null || u2.department.name === undefined)) {
                            return 0;
                        } else if (u1.department.name === null || u1.department.name === undefined) {
                            return -1;
                        } else if (u2.department.name === null || u2.department.name === undefined) {
                            return 1;
                        } else {
                            tmpResult = u1.department.name > u2.department.name;
                            if (u1.department.name.localeCompare)
                                tmpResult = u1.department.name.localeCompare(u2.department.name);
                            return tmpResult
                        }
                    }
                }
            }
        }
    });
}
function validateString(str) {
    var result = "" + str;
    for (var i = 0; i < result.length; i++) {
        if (!((i + 1) < result.length && result[i] == '\r' && result[i + 1] == '\n') && result[i] != '\r' && result[i] != '\n' && result[i] != '\s' && result[i] != ' ') {
            return result;
        }
    }
    result = result.replace(/^\s+|\s+$/gm, '');
    result = result.trim();
    result = result.replace(/(?:\r\n|\r|\n)/g, '');
    return result.trim();
}
function createMessageGuid(fromUserId, toUserId) {
    var id_1 = fromUserId;
    var id_2 = toUserId;
    var offset = 0;
    if (id_1)
        offset += id_1;
    if (id_2)
    {
        if (id_2 >= offset) {
            offset = id_2 - ((id_2 - offset) / 2);
        } else {
            offset = offset - ((offset - id_2) / 2);
        }
    }
    var s4 = function () {
        var os = offset;
        if(os <= 0)
            os = 7;
        while (os >= 1) {
            os /= 10.0;
        }
        var rand = os + Math.random();
        while (rand >= 1) {
            rand /= 10.0;
        }
        return Math.floor((1 + rand) * 0x10000).toString(16).substring(1);
    };
    return s4() + s4() + "-" + s4() + "-" + s4() + "-" + s4() + "-" + s4() + s4() + s4();
}
function createMessage(content, fromUser, toUser)
{
    var msg = new Object();
    msg.content = content;
    msg.fromUser = fromUser;
    msg.toUser = toUser;
    msg.guid = createMessageGuid(fromUser.id, toUser.id);
    msg.msgType = "txt";
    return msg;
}
function createSingleMessageDiv(message, hostUser) {
    var tmpDiv = document.createElement("DIV");
    tmpDiv.style.fontFamily = "\'Segoe UI Emoji\', \'Segoe UI Segoe UI Symbol\'";
    var innerText = message.content; //text.replace(/ /g, "&nbsp;");
    var spanElement = document.createElement("SPAN");
    spanElement.innerText = innerText;
    spanElement.textContent = innerText;
    innerText = spanElement.innerHTML;
    innerText = innerText.replace(/(?:\r\n|\r|\n)/g, '<br />');
    innerText = "<span>" + innerText + "</span>";
    var direction = (message.fromUser === hostUser) || (message.fromUser.id !== undefined && message.fromUser.id !== null && hostUser.id !== undefined && hostUser.id !== null && message.fromUser.id == hostUser.id);
    if (direction == 0 || direction == "left" || direction == 'l')
        tmpDiv.innerHTML = "<div class=\"sc_a sc_al\"><div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d left_d\"><div class=\"sc_e clear_e left_e\"><div class=\"sc_f color_left_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + innerText + "</span></div></div></div></div></div></div></div></div>";
    else
        tmpDiv.innerHTML = "<div class=\"sc_a\"><div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d right_d\"><div class=\"sc_e clear_e right_e\"><div class=\"sc_f color_right_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + innerText + "</span></div></div></div></div></div></div></div></div>";
    return tmpDiv;

}
function createMessageDivContainer(message, hostUser, messageDiv) {
    var containerDiv = document.createElement("DIV");
    var direction = (message.fromUser === hostUser) || (message.fromUser.id !== undefined && message.fromUser.id !== null && hostUser.id !== undefined && hostUser.id !== null && message.fromUser.id == hostUser.id);
    if (direction == 0 || direction == "left" || direction == 'l') {
        var iconDiv = document.createElement("DIV");
        iconDiv.className = "sc_icon";
        var imgElement = document.createElement("IMG");
        imgElement.width = 32;
        imgElement.height = 32;
        imgElement.alt = hostUser.name;
        imgElement.title = hostUser.name;
        imgElement.src = message.fromUser.picURL;
        iconDiv.appendChild(imgElement);
        containerDiv.appendChild(iconDiv);
    }
    if (messageDiv)
        containerDiv.appendChild(messageDiv);
    return containerDiv;
}
function createEmptyMessageDiv(hostUser) {
    var tmpDiv = document.createElement("DIV");
    tmpDiv.style.fontFamily = "\'Segoe UI Emoji\', \'Segoe UI Segoe UI Symbol\'";
return tmpDiv;
}

function createHtmlInnerMessageDiv(message, hostUser) {
    var tempDiv = document.createElement("DIV");
    tempDiv.className = "sc_a";
    var direction = (message.fromUser === hostUser) || (message.fromUser.id !== undefined && message.fromUser.id !== null && hostUser.id !== undefined && hostUser.id !== null && message.fromUser.id == hostUser.id);
    if (direction == 0 || direction == "left" || direction == 'l') {
        tempDiv.className = "sc_a sc_al";
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d left_d\"><div class=\"sc_e clear_e left_e\"><div class=\"sc_f color_left_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + message.content + "</span></div></div></div></div></div></div></div>";
    } else
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d right_d\"><div class=\"sc_e clear_e right_e\"><div class=\"sc_f color_right_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + message.content + "</span></div></div></div></div></div></div></div>";
    return tempDiv;
}

function createHtmlInnerFileMessageDiv(message, hostUser) {
    var tempDiv = document.createElement("DIV");
    tempDiv.className = "sc_a";
    var direction = (message.fromUser === hostUser) || (message.fromUser.id !== undefined && message.fromUser.id !== null && hostUser.id !== undefined && hostUser.id !== null && message.fromUser.id == hostUser.id);
    var tmpName = null;
    try{
    	tmpName = decodeURIComponent(message.content.filename);
    	
    }catch(e){}
    if(tmpName === null)
    	tmpName = message.content.filename;
    var innerHTML = "<a target=\"_blank\" href=\"" + message.content.url + "\" >" + tmpName + "</a>";
    if (direction == 0 || direction == "left" || direction == 'l') {
        tempDiv.className = "sc_a sc_al";
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d left_d\"><div class=\"sc_e clear_e left_e\"><div class=\"sc_f color_left_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + innerHTML + "</span></div></div></div></div></div></div></div>";
    } else
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d right_d\"><div class=\"sc_e clear_e right_e\"><div class=\"sc_f color_right_f\"><div class=\"sc_g\"><div class=\"sc_h\"><span class=\"sc_i\">" + innerHTML + "</span></div></div></div></div></div></div></div>";
    return tempDiv;
}

function createHtmlInnerImageMessageDiv(message, hostUser) {
    var tempDiv = document.createElement("DIV");
    tempDiv.className = "sc_a";
    var direction = (message.fromUser === hostUser) || (message.fromUser.id !== undefined && message.fromUser.id !== null && hostUser.id !== undefined && hostUser.id !== null && message.fromUser.id == hostUser.id);
    var tmpName = null;
    try{
    	tmpName = decodeURIComponent(message.content.filename);
    	
    }catch(e){}
    if(tmpName === null)
    	tmpName = message.content.filename;
    var innerHTML = "<a target=\"_blank\"  href=\"" + message.content.url + "\" ><img src=\"" + message.content.url + "\"  alt=\"" + tmpName +"\" style=\"width:100%\"  /></a>";
    if (direction == 0 || direction == "left" || direction == 'l') {
        tempDiv.className = "sc_a sc_al";
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d left_d\"><div class=\"sc_e clear_e left_e\"><div class=\"sc_f color_left_f\"><div class=\"sc_g\"><div class=\"sc_h\">" + innerHTML + "</div></div></div></div></div></div></div>";
    } else
        tempDiv.innerHTML = "<div class=\"sc_b\"><div class=\"sc_c\"><div class=\"sc_d right_d\"><div class=\"sc_e clear_e right_e\"><div class=\"sc_f color_right_f\"><div class=\"sc_g\"><div class=\"sc_h\">" + innerHTML + "</div></div></div></div></div></div></div>";
    return tempDiv;
}


function createSystemMessageInnerDiv(messageElement, onLeft)
{
    var tmpDiv = document.createElement("DIV");
    tmpDiv.className = "sc_b";

    var sc_c = document.createElement("DIV");
    sc_c.className = "sc_c";

    var sc_d = document.createElement("DIV");
    sc_d.className = "sc_d right_d";
    if (onLeft)
        sc_d.className = "sc_d left_d";

    var sc_e = document.createElement("DIV");
    sc_e.className = "sc_e clear_e right_e";
    if (onLeft)
        sc_e.className = "sc_e clear_e left_e";
    sc_e.appendChild(messageElement);
    sc_d.appendChild(sc_e);
    sc_c.appendChild(sc_d);
    tmpDiv.appendChild(sc_c);

    return tmpDiv;
}
function onSameDate(date1, date2) {
    var year1 = date1.getFullYear();
    var month1 = date1.getMonth() + 1;
    var date1 = date1.getDate() + 1;
    var year2 = date2.getFullYear();
    var month2 = date2.getMonth() + 1;
    var date2 = date2.getDate() + 1;
    return (year1 == year2 && month1 == month2 && date1 == date2);
}
function createTimeTag(date) {
    var timeElement = document.createElement("TIME");
    timeElement["datetime"] = date.toISOString();
    timeElement.setAttribute("datetime", date.toISOString());
    timeElement.className = "tm_a";
    if (onSameDate(date, new Date())) {
        var span = document.createElement("SPAN");
        span.innerText = date.toLocaleTimeString();
        span.textContent = date.toLocaleTimeString();
        span.className = "tm_b";
        timeElement.appendChild(span);
    } else {
        var span = document.createElement("SPAN");
        span.innerText = date.toLocaleString();
        span.textContent = date.toLocaleString();
        span.className = "tm_b";
        timeElement.appendChild(span);
    }
    return timeElement;
}
function measureIsolatedElementWidth(element) {
    var tmpDiv = document.createElement("div");
    tmpDiv.style.position = "absolute";
    tmpDiv.style.cssFloat = "right";
    tmpDiv.style.right = "-65535px";
    tmpDiv.appendChild(element);
    document.documentElement.appendChild(tmpDiv);
    var result = tmpDiv.clientWidth;
    tmpDiv.removeChild(element);
    document.documentElement.removeChild(tmpDiv);
    return result;

}
function measureScrollHeight(textAreaElement) {
    var tmpTae = document.createElement('TEXTAREA');
    tmpTae.style.fontFamily = getComputedStyle(textAreaElement).fontFamily;
    tmpTae.style.fontSize = getComputedStyle(textAreaElement).fontSize;
    tmpTae.style.fontWeight = getComputedStyle(textAreaElement).fontWeight;
    tmpTae.style.width = getComputedStyle(textAreaElement).width;
    tmpTae.style.overflowX = getComputedStyle(textAreaElement).overflowX;
    tmpTae.style.overflowY = getComputedStyle(textAreaElement).overflowY;
    tmpTae.style.overflow = getComputedStyle(textAreaElement).overflow;
    tmpTae.style.height = getComputedStyle(textAreaElement).minHeight;
    tmpTae.value = new String(textAreaElement.value);

    var tmpDiv = document.createElement("DIV");
    tmpDiv.style.position = "absolute";
    tmpDiv.style.cssFloat = "right";
    tmpDiv.style.right = -65535 + "px";
    tmpDiv.appendChild(tmpTae);
    document.documentElement.appendChild(tmpDiv);

    var scrollHeight = tmpTae.scrollHeight;
    tmpDiv.removeChild(tmpTae);
    tmpTae = null;
    document.documentElement.removeChild(tmpDiv);
    tmpDiv = null;
    return scrollHeight + 5;
}

function OnlineSearchCallback(callback,text) {
    this.hasCanceled = false;
    this.text = text;
    this.callback_func = function (result) {
        if (!this.hasCanceled || text == imcontrol.peopleBox.searchInput.value) {
            callback(result)
        }
    }
}
function SearchEngine() {
    this.resultList = {};
    this.resultList.contains = function (obj) {
        return (!(this["user" + obj.id] === undefined || this["user" + obj.id] === null));
    }

    this.addToResultList = function (array) {
        for (var i = 0 ; i < array.length; i++) {
            if (!this.resultList.contains(array[i]) && array[i].id != imcontrol.peopleBox.hostUser.id) {
                this.resultList["user" + array[i].id] = array[i];
            }
        }
    };
    this.searchOffline = function (text) {
        var result = [];
        var keywords = text.split(" ");
        for (var i = 0 ; i < keywords.length; i++)
        {
            keywords[i] = keywords[i].trim();
        }
        for (var i in this.resultList) {
            if (i.indexOf("user") != -1)
                result.push(this.resultList[i]);
        }
        for (var i = 0; i < keywords.length; i++)
        {
            var tmp = [];
            for (var j = 0 ; j < result.length; j++)
            {
                for (var k = 0; k < imcontrol.DataEntityProperties.UserSearchProperties.length; k++) {
                    var pstr = imcontrol.DataEntityProperties.UserSearchProperties[k];
                    if (pstr.indexOf('.') == -1) {
                        if (result[j][pstr] !== undefined && result[j][pstr].toUpperCase().indexOf(keywords[i].toUpperCase()) != -1)
                            if (tmp.indexOf(result[j]) == -1)
                                tmp.push(result[j]);
                    } else {
                        var pstrArray = imcontrol.DataEntityProperties.UserSearchProperties[k].split('.');
                        if (result[j][pstrArray[0]] !== undefined && result[j][pstrArray[0]][pstrArray[1]] !== undefined && result[j][pstrArray[0]][pstrArray[1]].toUpperCase().indexOf(keywords[i].toUpperCase()) != -1)
                            if (tmp.indexOf(result[j]) == -1)
                                tmp.push(result[j]);
                    }
                }
            }
            result = tmp;
        }
        sortUserList(result);
        return result;
    };
    this.searchOnlineCallback = function (result,osc) {
        var newResult = [];

        for (var i = 0 ; i < result.length; i++) {
            if (result[i].id === undefined || result[i].id === null || result[i].id == imcontrol.peopleBox.hostUser.id)
                continue;
            if (!this.resultList.contains(result[i]))
            {
                newResult.push(result[i]);
            }
        }
        sortUserList(newResult);
        this.addToResultList(newResult);
        osc.callback_func(newResult);
    };
    this.searchOnline = function (text, callback) {
        var keywords = text.split(" ");
        for (var i = 0 ; i < keywords.length; i++) {
            keywords[i] = keywords[i].trim();
        }
        var osc = new OnlineSearchCallback(callback, text);
        var se = this;
        imcontrol.searchPeople(keywords, function (result) {
            se.searchOnlineCallback(result, osc);
        });
        return osc;
    }
}


function destopNotify(notificationMessage, title,icon, dir)
{
    if (!("Notification" in window))
        return;
    if (notificationMessage === undefined || notificationMessage === null)
        return;
    title = title || "新消息: ";
    dir = dir || "ltr";
    if(imctx)
        icon = icon || imctx + "/static/img/user-male-silhouette_318-55563.png";
    else
        icon = icon || "/static/img/user-male-silhouette_318-55563.png";
    
    if (window.Notification.permission === 'denied')
        return;
    var notification = null;
    var onPermissionGranted = function (permission) {
        if (!('permission' in window.Notification)) {
            window.Notification.permission = permission;
        }
        if (permission === 'granted') {
            var options = {
                "body": notificationMessage,
                "icon": icon,
                "dir" : dir
            };
            notification = new Notification(title, options);
            return notification;
        }
        return null;
    }

    if (window.Notification.permission !== 'granted') {
        window.Notification.requestPermission(onPermissionGranted);
    }
    else {
      notification =  onPermissionGranted("granted");
    }
    return notification;
}
function LongPollingGetMessagesRequest(sender, hostUser){
    this.sender = sender;
    this.hostUser = hostUser;
    this.startIdExclusive = undefined;
    this.startFromDate = undefined;
}
function GetMessageRequestParams(sender, hostUser)
{
    this.sender = sender;
    this.guestUser = undefined;
    this.hostUser = hostUser;
    this.startIdExclusive = undefined;
    this.fromDate = undefined;
    this.toDate = undefined;
    this.maxCount = undefined;
    this.attemptCount = 0;
    this.maxAttemptCount = 3;
}
function GetMessagesByUsersAndDatesCallbackHandler(getMessageRequestParams) {
    this.getMessageRequestParams = getMessageRequestParams;
    this.callback_function = function (messages) {
        if (messages === undefined || messages === null) {
            if (this.getMessageRequestParams.attemptCount <= this.getMessageRequestParams.maxAttemptCount) {
                var handler = this;
                setTimeout(function () {
                    var hdlr = handler;
                    hdlr.getMessageRequestParams.attemptCount += 1;
                    // re-do methos
                    hdlr.getMessageRequestParams.sender.sendGetMessagesByUsersAndDatesRequest(hdlr.getMessageRequestParams);
                }, 1000);
                return;
            } else {
                // on fail
                var params = this.getMessageRequestParams;
                this.getMessageRequestParams.sender.onGetMessagesByUsersAndDatesRequestFailed(params);
                return;
            }
        } else {
            var mgs = messages;
            var params = this.getMessageRequestParams;
            // on success
            for (var i = 0; i < messages.length; i++) {
                if (messages[i].msgType.toUpperCase() == "TXT")
                    continue;
                var orig = messages[i].content.toString();
                try {
                    messages[i].content = JSON.parse(messages[i].content);
                    messages[i].textContent = orig;
                } catch (e) {
                    messages[i].content = orig;
                }
                if (messages[i].content === undefined)
                    messages[i].content = orig;
            }
            this.getMessageRequestParams.sender.onGetMessagesByUsersAndDatesRequestSucceeded(mgs, params);
        }
    }
}
function GetFirstMessageCallbackHandler(getMessageRequestParams) {
    this.getMessageRequestParams = getMessageRequestParams;
    this.callback_function = function (messages) {
        if (messages === undefined || messages === null) {
            if (this.getMessageRequestParams.attemptCount <= this.getMessageRequestParams.maxAttemptCount) {
                var handler = this;
                setTimeout(function () {
                    var hdlr = handler;
                    hdlr.getMessageRequestParams.attemptCount += 1;
                    // re-do method
                    hdlr.getMessageRequestParams.sender.sendGetFirstMessageRequest(hdlr.getMessageRequestParams);
                }, 1000);
                return;
            } else {
                // on fail
                this.getMessageRequestParams.sender.onGetFirstMessageRequestFailed();
                return;
            }
        } else {
            // on success
            for (var i = 0; i < messages.length; i++) {
                if (messages[i].msgType.toUpperCase() == "TXT")
                    continue;
                var orig = messages[i].content.toString();
                try {
                    messages[i].content = JSON.parse(messages[i].content);
                    messages[i].textContent = orig;
                } catch (e) {
                    messages[i].content = orig;
                }
                if (messages[i].content === undefined)
                    messages[i].content = orig;
            }
            var mgs = messages;
            this.getMessageRequestParams.sender.onGetFirstMessageRequestSucceeded(mgs);
        }
    }
}
function GetLastMessagesCallbackHandler(getMessageRequestParams) {
    this.getMessageRequestParams = getMessageRequestParams;
    this.callback_function = function (messages) {
        if (messages === undefined || messages === null) {
            if (this.getMessageRequestParams.attemptCount <= this.getMessageRequestParams.maxAttemptCount) {
                var handler = this;
                setTimeout(function () {
                    var hdlr = handler;
                    hdlr.getMessageRequestParams.attemptCount += 1;
                    hdlr.getMessageRequestParams.sender.sendGetLastMessagesRequest(hdlr.getMessageRequestParams);
                }, 1000);
                return;
            } else {
                this.getMessageRequestParams.sender.onGetLastMessagesRequestFailed();
                return;
            }
        } else {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i].msgType.toUpperCase() == "TXT")
                    continue;
                var orig = messages[i].content.toString();
                try {
                    messages[i].content = JSON.parse(messages[i].content);
                    messages[i].textContent = orig;
                } catch (e) {
                    messages[i].content = orig;
                }
                if (messages[i].content === undefined)
                    messages[i].content = orig;
            }
            if (this.getMessageRequestParams.toDate !== null && this.getMessageRequestParams.toDate !== undefined) {
                this.getMessageRequestParams.sender.onGetLastMessagesRequestSucceeded(messages, this.getMessageRequestParams.toDate);
            } else {
                this.getMessageRequestParams.sender.onGetLastMessagesRequestSucceeded(messages);
            }
        }
    }
}
function LongPollingGetMessagesCallbackHandler(longPollingGetMessagesRequest, isRecursive)
{
    if (isRecursive === undefined || isRecursive === null){
        throw new Error("isRecursive must not be " + isRecursive );
    }
    this.longPollingGetMessagesRequestParams = longPollingGetMessagesRequest;
    this.isActive = true;
    this.isRecursive = isRecursive;
    this.callback_function = function (messages) {
        this.isActive = (this.longPollingGetMessagesRequestParams && this.longPollingGetMessagesRequestParams.sender && window.flyoutMessageBoxList.contains(this.longPollingGetMessagesRequestParams.sender));
        if (this.isActive) {
            if (messages === undefined || messages === null) {
                var params = this.longPollingGetMessagesRequestParams;
                setTimeout(function () {
                    var p = params;
                    if (p.sender.sendLongPollingGetMessagesRequest)
                        p.sender.sendLongPollingGetMessagesRequest(true);
                }, 6000);
            } else {
                var params = this.longPollingGetMessagesRequestParams;
                for (var i = 0; i < messages.length; i++) {
                    if (messages[i].msgType.toUpperCase() == "TXT")
                        continue;
                    var orig = messages[i].content.toString();
                    try {
                        messages[i].content = JSON.parse(messages[i].content);
                        messages[i].textContent = orig;
                    } catch (e) {
                        messages[i].content = orig;
                    }
                    if (messages[i].content === undefined)
                        messages[i].content = orig;
                }
                var handler = this;
                setTimeout(function () {
                    var msgs = messages;
                    var prms = params;
                    var hdlr = handler;
                    if (flyoutMessageBoxList.contains(prms.sender)) {
                        prms.sender.addReceivedMessages(msgs);
                    } else {
                        prms.sender.isLongPollingRequestOn = false;
                        hdlr.isActive = false;
                        hdlr.isRecursive = false;
                    }
                }, 400);
                if (this.isRecursive && flyoutMessageBoxList.contains(params.sender)) {
                    setTimeout(function () {
                        var p = params;
                        if (p.sender.sendLongPollingGetMessagesRequest)
                            p.sender.sendLongPollingGetMessagesRequest(true);
                    }, 100);
                }
            }
        } else {
            var params = this.longPollingGetMessagesRequestParams;
        }
        var params = this.longPollingGetMessagesRequestParams;
        params.sender.removeCallbackHandler(this);

    };
}
function FlyoutMessageBox(title, parentElement) {
    this.title = title;

    // Generates the flyoutHeader Division Element
    this.flyoutHeaderDiv = document.createElement("DIV");
    this.flyoutHeaderDiv.className = 'flyoutHeader';
    this.closeLink = document.createElement("A");
    /*this.closeLink.innerText = "Close";
    this.closeLink.textContent = "Close";*/
    this.closeLink.title = "关闭";
    this.closeLink.className = "fmbCloseLnk";
    this.closeLink.flyoutMessageBoxInstance = this;
    this.headerControlContainer = document.createElement("DIV");
    this.headerControlContainer.className = 'headerControlContainer';
    this.headerControlContainer.appendChild(this.closeLink);
    this.flyoutHeaderDiv.innerHTML = '<span style=\"margin-left:0.5em\">' + this.title + '</span>';
    this.flyoutHeaderDiv.appendChild(this.headerControlContainer);

    // Generates the flyoutContent Division Element
    this.flyoutContentDiv = document.createElement("DIV");
    this.flyoutContentDiv.className = 'flyoutContent';
    this.scrollableDiv = document.createElement("DIV");
    this.scrollableDiv.className = 'scrollable';
    this.scrollableDiv.flyoutMessageBoxInstance = this;
    this.chatContentDiv = document.createElement("DIV");
    this.chatContentDiv.className = 'chatContent';
    this.conversationDiv = document.createElement("DIV");
    this.conversationDiv.className = 'conversation';
    this.conversationDiv.flyoutMessageBoxInstance = this;
    var tdElement = document.createElement("TD");
    tdElement.style.width = "100%";
    var trElement = document.createElement("TR");
    trElement.style.width = "100%";
    var tbodyElement = document.createElement("TBODY");
    tbodyElement.style.width = "100%";
    var tableElement = document.createElement("TABLE");
    tableElement.style.width = "100%";
    tdElement.appendChild(this.conversationDiv);
    trElement.appendChild(tdElement);
    tbodyElement.appendChild(trElement);
    tableElement.appendChild(tbodyElement);
    this.errorMessageDiv = document.createElement("DIV");
    this.errorMessageDiv.style.display = "none";
    this.flyoutContentDiv.appendChild(this.errorMessageDiv);
    this.chatContentDiv.appendChild(tableElement);
    this.scrollableDiv.appendChild(this.chatContentDiv);
    this.flyoutContentDiv.appendChild(this.scrollableDiv);

    // Generates the flyoutFooter Division Element
    this.flyoutFooterDiv = document.createElement("DIV");
    this.flyoutFooterDiv.className = 'flyoutFooter';
    this.flyoutFooterDiv.flyoutMessageBoxInstance = this;
    this.ff_d_Div = document.createElement("DIV");
    this.ff_d_Div.className = "ff_d";
    this.textArea = document.createElement("TEXTAREA");
    this.textArea.className = "ff_d_ta";
    this.textArea.placeholder = "在此处键入您的消息...";
    this.textArea.minHeight = 16;
    this.textArea.style.height = '16px';
    
    // ToolStrip
    this.ff_t_Div = document.createElement("DIV");
    this.ff_t_Div.className = "ff_t";
    this.uploadLink = document.createElement("A");
    /*this.uploadLink.innerText = "Send File";
    this.uploadLink.textContent = "Send File";*/
    this.uploadLink.title = "发送文件";
    this.uploadLink.href = "javascript:void(0)";
    this.uploadLink.className = "fileClipLink";
    this.uploadLink.flyoutMessageBoxInstance = this;
    this.uploadImageLink = document.createElement("A");
    /* this.uploadImageLink.innerText = "Send Image";
    this.uploadImageLink.textContent = "Send Image"; */
    this.uploadImageLink.title = "发送图片";
    this.uploadImageLink.href = "javascript:void(0)";
    this.uploadImageLink.flyoutMessageBoxInstance = this;
    this.uploadImageLink.className = "imgLink";
    this.toolStripContent1 = document.createElement("DIV");
    this.toolStripContent1.className = "toolStripContent";
    this.toolStripContent1.appendChild(this.uploadImageLink);
    this.toolStripContent2 = document.createElement("DIV");
    this.toolStripContent2.className = "toolStripContent";
    this.toolStripContent2.appendChild(this.uploadLink);
    this.ff_t_Div.appendChild(this.toolStripContent1);
    this.ff_t_Div.appendChild(this.toolStripContent2);


    this.textArea.flyoutMessageBoxInstance = this;
    this.textArea.style.fontFamily = "\'Segoe UI Emoji\',\'Segoe UI Symbol\'";
    this.ff_d_Div.appendChild(this.textArea);
    this.flyoutFooterDiv.appendChild(this.ff_t_Div);
    this.flyoutFooterDiv.appendChild(this.ff_d_Div);
    
    this.textArea.parentElement.style.maxHeight = this.textArea.minHeight * 4 + "px";



    this.parentElement = parentElement;
    this.dummyParent = document.createElement("DIV");
    this.onClosed = null;
    this.onHide = null;
    this.onSubmitMessage = null;
    this.peopleBox = null;
    this.link = null;
    this.messages = [];
    this.isMessagesLoaded = false;
    this.callbackHandlers = [];
    this.isInsertingMessages = false;
    this.firstMessage = undefined;
    this.isLongPollingRequestOn = false;
    this.isFirstMessageLoaded = false;
    this.previousScrollTop = 0;
    this.lastInsertTimestamp = null;
    this.lastPushTimestamp = null;
    this.removeCallbackHandler = function (handler) {
        if (handler === null || handler === undefined)
            return;
        handler.isActive = false;
        handler.isRecursive = false;
        if (this.callbackHandlers.indexOf(handler) >= 0) {
            this.callbackHandlers.splice(this.callbackHandlers.indexOf(handler), 1);
        }
    };
    this.clearCallbackHandlers = function () {
        for (var i = 0; i < this.callbackHandlers.length; i++)
        {
            if (this.callbackHandlers[i]) {
                this.callbackHandlers[i].isActive = false;
                this.callbackHandlers[i].isRecursive = false;
            }
        }
        this.callbackHandlers.splice(0, this.callbackHandlers.length);
    }
    this.containsMessage = function (message) {
        if (!message)
            return true;
        for (var i = 0 ; i < this.messages.length; i++) {
            if (this.messages[i].id !== undefined && this.messages[i].id !== null && this.messages[i].id == message.id) {
                return true;
            }
            if (this.messages[i].guid !== undefined && this.messages[i].guid !== null && this.messages[i].guid == message.guid) {
                return true;
            }
        }
        return false;
    };
    this.textArea.addEventListener("paste", function (e) {
        e = e || window.event;
        if (e && e.clipboardData && e.clipboardData.items && e.clipboardData.items.length > 0) {            
            var blob = e.clipboardData.items[0];
            if (blob && blob.kind && blob.kind.length > 0 && blob.kind.toUpperCase && blob.kind.toUpperCase().indexOf("FILE") >= 0) {
                if (!blob.size)
                    blob = blob.getAsFile();
                this.flyoutMessageBoxInstance.startUpload(false, blob, e);
            }
        } else if (e && e.clipboardData && e.clipboardData.files && e.clipboardData.files.length > 0) {
            var blob = e.clipboardData.files[0];
            if (!blob.size)
                blob = blob.getAsFile();
            this.flyoutMessageBoxInstance.startUpload(false, blob, e);
        }
    });
    this.textArea.onKeyDown = function (sender, e) {
        if (!e)
            e = sender.event || window.event;
        if (!e.shiftKey) {
            switch (e.keyCode) {
                case 13:
                    if (e.preventDefault)
                        e.preventDefault();
                    if (validateString(sender.value).length == 0) {

                        return 0;
                    }
                    return 1;
                    break;
            }
        } else {
            switch (e.keyCode) {
                case 13:
                    return 2;
                    break;
            }
        }
        return 0;
    }
    this.clearTextArea = function () {
        this.textArea.value = '';
        this.textArea.style.height = (measureScrollHeight(this.textArea) - 5) + "px";////'16px';
        this.textArea.parentElement.style.height = "auto";
        this.textArea.parentElement.style.maxHeight = this.textArea.minHeight * 4 + "px";
    }
    this.sendGetUnreadMessagesRequest = function () {
        if (imcontrol.requestUnreadMessages && imcontrol.pushMode == PushMode.Polling)
            imcontrol.requestUnreadMessages([this.link.user], this);
    }

    this.sendGetMessagesRequest = function () {
        if (imcontrol.requestMessages)
            imcontrol.requestMessages([this.link.user], this);
    }
    this.getLatestMessageLoaded = function(){
        if(this.messages === undefined || this.messages === null || this.messages.length === undefined || this.messages.length === null || this.messages.length <= 0)
            return null;
        var lastDate = new Date(0);
        var result = null;
        for(var i = 0; i < this.messages.length; i++)
        {
            if(this.messages[i] !== undefined && this.messages[i] !== null && 
                this.messages[i].sendDate !== undefined && this.messages[i].sendDate !== null){
                if (this.messages[i].sendDate >= lastDate) {
                    lastDate = this.messages[i].sendDate;
                    result = this.messages[i];
                }
            }
        }
        return result;
    }
    this.getOldestMessageLoaded = function () {
        if (this.messages === undefined || this.messages === null || this.messages.length === undefined || this.messages.length === null || this.messages.length <= 0)
            return null;
        var lastDate = new Date();
        lastDate = new Date(lastDate.getTime() + 86400000);
        var result = null;
        for (var i = 0; i < this.messages.length; i++) {
            if (this.messages[i] !== undefined && this.messages[i] !== null &&
                this.messages[i].sendDate !== undefined && this.messages[i].sendDate !== null) {
                if (this.messages[i].sendDate < lastDate) {
                    lastDate = this.messages[i].sendDate;
                    result = this.messages[i];
                }
            }
        }
        return result;
    }
    this.sendGetMessagesByUsersAndDatesRequest = function (params) {
        if (params !== undefined && params !== null) {
            var handler = new GetMessagesByUsersAndDatesCallbackHandler(params);
             imcontrol.requestMessagesByUsersAndDates(params, handler);
            return;
        }
        var params = new GetMessageRequestParams(this, this.link.hostUser);
        var oldestMessage = this.getOldestMessageLoaded();
        params.guestUser = this.link.user;
        if (oldestMessage !== null && oldestMessage !== undefined) {
            params.toDate = oldestMessage.sendDate;
        } else
            params.toDate = new Date(new Date().getTime() + 3600000);
        if(params.toDate.getTime)
            params.fromDate = new Date(params.toDate.getTime() - 86400000);
        else
            params.fromDate = new Date(params.toDate - 86400000 * 4);

        
        var handler = new GetMessagesByUsersAndDatesCallbackHandler(params);
         imcontrol.requestMessagesByUsersAndDates(params, handler);
    }
    this.onGetMessagesByUsersAndDatesRequestSucceeded = function (messages, params) {
        if (messages.length == 0) {
            this.isInsertingMessages = true;
            this.sendGetLastMessagesRequest(null, params.toDate);
            return;
        }
        this.isInsertingMessages = true;
        var tmp = this.scrollableDiv.scrollHeight;
        this.insertMessages(messages, this.link.hostUser);
        this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight - tmp;
        this.previousScrollTop = this.scrollableDiv.scrollTop;
        this.isInsertingMessages = false;
    }
    this.onGetMessagesByUsersAndDatesRequestFailed = function (params) {
        this.errorMessageDiv.innerHTML = "<p style=\"color:red;font-size:9pt;\">" + "获取消息失败. 若要尝试重新获取消息, <br/>请<a href=\"javascript:void(0)\">单击此处</a>" + "</p>";
        var sender = this;
        this.errorMessageDiv.firstElementChild.firstElementChild.nextElementSibling.addEventListener("click", function (e) {
            var sndr = sender;
            var prms = params;
            params.attemptCount = 0;
            sndr.sendGetMessagesByUsersAndDatesRequest(null, null, params);
            sndr.errorMessageDiv.style.display = "none";
            sndr.errorMessageDiv.innerHTML = "";
        });
        this.errorMessageDiv.style.display = "block";
    }
    this.sendGetFirstMessageRequest = function (params) {
        if (params !== undefined && params !== null) {
            var handler = new GetFirstMessageCallbackHandler(params);
            imcontrol.requestFirstMessage(params, handler);
            return;
        }
        var params = new GetMessageRequestParams(this, this.link.hostUser);
        params.guestUser = this.link.user;
        var handler = new GetFirstMessageCallbackHandler(params);
        imcontrol.requestFirstMessage(params, handler);
    }
    this.onGetFirstMessageRequestSucceeded = function (messages) {
        if (messages === undefined || messages === null ||  !messages.length)
            this.firstMessage = null;
        else
            this.firstMessage = messages[0];
        if (!this.isMessagesLoaded)
            this.sendGetLastMessagesRequest();
    }
    this.onGetFirstMessageRequestFailed = function () {
        this.errorMessageDiv.innerHTML = "<p style=\"color:red;font-size:9pt;\">" + "连接失败. 请检查您的Internet连接或与管理员连络. 若要尝试重新连接请<a href=\"javascript:void(0)\">单击此处</a><br/>FailureRequestCode : getFirstMessage" + "</p>";
        var sender = this;
        this.errorMessageDiv.firstElementChild.firstElementChild.addEventListener("click", function (e) {
            var sndr = sender;
            sndr.sendGetFirstMessageRequest();
            sndr.errorMessageDiv.style.display = "none";
            sndr.errorMessageDiv.innerHTML = "";
        });
        this.errorMessageDiv.style.display = "block";
    }
    this.sendGetLastMessagesRequest = function (params, toDate) {
        if (params !== undefined && params !== null) {
            var handler = new GetLastMessagesCallbackHandler(params);
            imcontrol.requestLastMessages(params, handler);
            return;
        }
        var params = new GetMessageRequestParams(this, this.link.hostUser);
        params.guestUser = this.link.user;
        params.maxCount = 10;
        if(toDate !== undefined && toDate !== null)
            params.toDate = toDate;
        var handler = new GetLastMessagesCallbackHandler(params);
        imcontrol.requestLastMessages(params, handler);
    }
    this.onGetLastMessagesRequestSucceeded = function (messages, toDate) {
        this.isInsertingMessages = true;
        var tmp = this.scrollableDiv.scrollHeight;
        this.insertMessages(messages);
        this.isInsertingMessages = false;        
        if (toDate === undefined || toDate === null)
            this.isMessagesLoaded = true;
        if (!this.isLongPollingRequestOn) {
            this.isLongPollingRequestOn = true;
            this.sendLongPollingGetMessagesRequest(true);
        }
        if (toDate === undefined || toDate === null) {
            this.previousScrollTop = this.scrollableDiv.scrollTop;
            this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;
        } else {
            this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight - tmp;
            this.previousScrollTop = this.scrollableDiv.scrollTop;
        }
    }
    this.onGetLastMessagesRequestFailed = function () {
        this.errorMessageDiv.innerHTML = "<p style=\"color:red;font-size:9pt;\">" + "获取消息失败. 请检查您的Internet连接或与管理员连络. 若要尝试重新连接请<a href=\"javascript:void(0)\">单击此处</a><br/>FailureRequestCode : getLastMessages" + "</p>";
        var sender = this;
        this.errorMessageDiv.firstElementChild.firstElementChild.addEventListener("click", function (e) {
            var sndr = sender;
            sndr.sendGetLastMessagesRequest();
            sndr.errorMessageDiv.style.display = "none";
            sndr.errorMessageDiv.innerHTML = "";
        });
        this.errorMessageDiv.style.display = "block";
    }
    this.sendLongPollingGetMessagesRequest = function (isRecursive) {        
        var containsRecursiveHandler = false;
        for (var i = 0; i < this.callbackHandlers.length; i++) {
            if (this.callbackHandlers[i] !== undefined && this.callbackHandlers[i] !== null && this.callbackHandlers[i].isRecursive)
                containsRecursiveHandler = true;
        }
        if ((containsRecursiveHandler && isRecursive) || (!this.isLongPollingRequestOn && isRecursive ))
            return;
        var requestParams = new LongPollingGetMessagesRequest(this, this.link.hostUser);
        var lastMessage = this.getLatestMessageLoaded();
        var startFromDate = null;
        var startIndexExclusive = null;
        if (lastMessage !== null && lastMessage !== undefined) {
            startFromDate = lastMessage.sendDate;
            startIndexExclusive = lastMessage.id;
        }
        requestParams.startFromDate = startFromDate;
        requestParams.startIdExclusive = startIndexExclusive;
        var callbackHandler = new LongPollingGetMessagesCallbackHandler(requestParams, isRecursive);
        this.callbackHandlers.push(callbackHandler);
        window.imcontrol.longPollingRequestMessages(requestParams, callbackHandler);
    }
    this.onMarkMessageAsRead = function (messages) {
        for (var i = 0; i < messages.length; i++) {
            if (messages[i].toUser.id == this.link.hostUser.id && messages[i].fromUser.id == this.link.user.id) {
                for (var j = 0 ; j < this.messages.length; j++) {
                    if (this.messages[j].id !== undefined && this.messages[j].id == messages[i].id) {
                        if (this.messages[j].infoMessageDiv) {
                            continue;
                        }
                        var tempDiv = document.createElement("DIV");

                        if (messages[i].sendDate.toLocaleString)
                            tempDiv.innerHTML = "<span style=\"color:#6495ED;font-size:small\">" + messages[i].sendDate.toLocaleString() + "</span>";
                        else
                            tempDiv.innerHTML = "<span style=\"color:#6495ED;font-size:small\">" + messages[i].sendDate.toString() + "</span>";
                        if (!this.messages[j].sendDate || this.messages[j].sendDate != messages[i].sendDate)
                            this.messages[j].sendDate = messages[i].sendDate;
                        this.messages[j].infoMessageDiv = createSystemMessageInnerDiv(tempDiv, true);
                        this.messages[j].messageDiv.appendChild(this.messages[j].infoMessageDiv);
                        if (j == this.messages.length - 1) {
                            this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;
                        }
                    }

                }
            }
        }
    }

    this.markMessagesAsRead = function (messages, hostUser) {
        if (!messages || !messages.length || messages.length <= 0)
            return;
        var unreadMessages = [];
        for (var i = 0; i < messages.length; i++) {
            if ((!messages[i].isRead || messages[i].isRead == "0") && (messages[i].toUser.id == hostUser.id && messages[i].fromUser.id == this.link.user.id)) {

                unreadMessages.push(messages[i]);
            }
        }

        if (imcontrol && imcontrol.markMessagesAsRead)
            imcontrol.markMessagesAsRead(unreadMessages, this);

    }
    this.isMessageToThisBox = function (message, hostUser) {
        if (!message)
            return false;
        if (!message.fromUser || !message.toUser) {
            return false;
        }
        if (message.fromUser.id == hostUser.id && message.toUser.id == this.link.user.id) {
            return true;
        }

        if (message.fromUser.id == this.link.user.id && message.toUser.id == hostUser.id) {
            return true;
        }
        return false;
    }
    this.generateMessageDiv = function (message, hostUser) {
        var messageDiv = null;
        if (message.msgType === undefined || message.msgType === null || message.msgType.toUpperCase() == "TXT") {
            messageDiv = createSingleMessageDiv(message, hostUser);
        } else {
            messageDiv = createEmptyMessageDiv(hostUser);
            if (message.msgType.toUpperCase() == "FILE") {
                message.content.url = imctx + message.content.url;
                messageDiv.appendChild(createHtmlInnerFileMessageDiv(message, hostUser));
            } else if (message.msgType.toUpperCase() == "HTML") {
                messageDiv.appendChild(createHtmlInnerMessageDiv(message, hostUser));
            } else if (message.msgType.toUpperCase() == "IMG") {
                message.content.url = imctx + message.content.url;
                messageDiv.appendChild(createHtmlInnerImageMessageDiv(message, hostUser));
            } else {
                messageDiv.appendChild(createHtmlInnerMessageDiv(message, hostUser));
            }
        }
        return messageDiv;
    }
    this.insertMessages = function (messages, hostUser) {
        if (!messages || !messages.length)
            return;
        if(!hostUser)
            hostUser = window.imcontrol.peopleBox.hostUser;
        for (var i = messages.length - 1; i >= 0; --i) {
            if (this.isMessageToThisBox(messages[i], hostUser)) {
                if (this.containsMessage(messages[i])) {
                    messages.splice(i, 1);
                    ++i;
                    continue;
                }
                if (this.firstMessage !== undefined && this.firstMessage !== null) {
                    if (messages[i].id == this.firstMessage.id) {
                        this.isFirstMessageLoaded = true;
                    }
                }
                if (i == 0 && this.firstMessage === null) {
                    this.firstMessage = messages[i];
                    this.isFirstMessageLoaded = true;
                }
                if (messages[i].fromUser.id == this.link.user.id)
                    messages[i].fromUser = this.link.user;
                if (messages[i].sendDate !== undefined && messages[i].sendDate !== null)
                    messages[i].sendDate = new Date(messages[i].sendDate);
                var messageDiv = this.generateMessageDiv(messages[i], this.link.hostUser);
                messages[i].messageContainerDiv = createMessageDivContainer(messages[i], this.link.hostUser, messageDiv);
                messages[i].messageDiv = messageDiv;
                this.conversationDiv.insertBefore(messages[i].messageContainerDiv, this.conversationDiv.firstChild);
                if ((this.lastInsertTimestamp !== null && ((this.lastInsertTimestamp.getTime()) - messages[i].sendDate.getTime()) >= 3600000) ||
                    (this.lastInsertTimestamp === null && (((new Date()).getTime()) - messages[i].sendDate.getTime()) >= 3600000) || i == 0) {
                    this.conversationDiv.insertBefore(createTimeTag(messages[i].sendDate), this.conversationDiv.firstChild);
                    this.lastInsertTimestamp = messages[i].sendDate;
                    if (this.lastPushTimestamp === null)
                        this.lastPushTimestamp = messages[i].sendDate;
                }
                this.messages.splice(0, 0, messages[i]);               
            }
        }
        this.markMessagesAsRead(messages, hostUser);
    }
    this.addReceivedMessages = function (messages, hostUser) {
        if (!messages || !messages.length)
            return;
        if (!hostUser)
            hostUser = window.imcontrol.peopleBox.hostUser;
        
        for (var i = 0; i < messages.length; i++) {
            if (this.isMessageToThisBox(messages[i], hostUser)) {
                if (this.containsMessage(messages[i])) {
                    messages.splice(i, 1);
                    --i;
                    continue;
                }
                if (this.firstMessage !== undefined && this.firstMessage !== null) {
                    if (messages[i].id == this.firstMessage.id) {
                        this.isFirstMessageLoaded = true;
                    }
                }
                if (i == 0 && this.firstMessage === null) {
                    this.firstMessage = messages[i];
                    this.isFirstMessageLoaded = true;
                }
                if(messages[i].fromUser.id == this.link.user.id)
                    messages[i].fromUser = this.link.user;
                if (messages[i].sendDate !== undefined && messages[i].sendDate !== null)
                    messages[i].sendDate = new Date(messages[i].sendDate);
                var messageDiv = this.generateMessageDiv(messages[i], this.link.hostUser);
                
                messages[i].messageContainerDiv = createMessageDivContainer(messages[i], this.link.hostUser, messageDiv);
                messages[i].messageDiv = messageDiv;
                this.conversationDiv.appendChild(messages[i].messageContainerDiv);
                if (messages[i].sendDate !== undefined && messages[i].sendDate !== null) {
                    if ((this.lastPushTimestamp !== null && ((messages[i].sendDate.getTime()) - this.lastPushTimestamp.getTime()) >= 3600000) ||
                        (this.lastPushTimestamp === null) || this.messages.length == 0) {
                        this.conversationDiv.insertBefore(createTimeTag(messages[i].sendDate), messages[i].messageContainerDiv)
                        this.lastPushTimestamp = messages[i].sendDate;
                        if (this.lastInsertTimestamp === undefined || this.lastInsertTimestamp === null)
                            this.lastInsertTimestamp = messages[i].sendDate;
                    }
                }
                this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;
                this.messages.push(messages[i]);
            }
        }
        this.markMessagesAsRead(messages, hostUser);
    };
    this.scrollableDiv.addEventListener("scroll", function (e) {
        if (this.scrollTop <= 30 && this.scrollTop < this.flyoutMessageBoxInstance.previousScrollTop && this.scrollHeight > 0 && this.flyoutMessageBoxInstance.messages && this.flyoutMessageBoxInstance.messages.length > 0) {
            var instance = this.flyoutMessageBoxInstance;
            if (!instance.isFirstMessageLoaded && !instance.isInsertingMessages) {
                instance.isInsertingMessages = true;
                instance.sendGetMessagesByUsersAndDatesRequest();
            }
        }
        this.flyoutMessageBoxInstance.previousScrollTop = this.scrollTop;
    });
    this.submitMessage = function (message) {

        if (this.messages.indexOf(message) < 0) {
            this.messages.push(message);
            this.conversationDiv.appendChild(message.messageDiv);
            if ((this.lastPushTimestamp !== null && (((new Date()).getTime()) - this.lastPushTimestamp.getTime()) >= 3600000) ||
                        (this.lastPushTimestamp === null) || this.messages.length == 0) {
                this.conversationDiv.insertBefore(createTimeTag(new Date()), message.messageDiv)
                this.lastPushTimestamp = new Date();
                if (this.lastInsertTimestamp === undefined || this.lastInsertTimestamp === null)
                    this.lastInsertTimestamp = new Date();
            }
        }
        if (this.onSubmitMessage) {
            this.onSubmitMessage(this, message);
        }
    }
    this.submitTestArea = function () {
        var message = createMessage(this.textArea.value, this.link.hostUser, this.link.user);
        var messageDiv = createSingleMessageDiv(message, this.link.hostUser);
        message.messageDiv = messageDiv;


        this.textArea.value = '';

        this.textArea.style.height = (measureScrollHeight(this.textArea) - 5) + "px";//'16px';
        this.textArea.parentElement.style.height = "auto";
        this.textArea.parentElement.style.maxHeight = this.textArea.minHeight * 4 + "px";
        this.submitMessage(message);
        this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;

    };
    this.onSubmitMessageFailure = function (messages) {
        if (!messages)
            return;
        for (var i = 0 ; i < messages.length; i++) {
            if (messages[i].fromUser && messages[i].fromUser.id == this.link.hostUser.id && messages[i].toUser.id == this.link.user.id) {
                if (messages[i].messageDiv) {
                    var retryLink = document.createElement("A");
                    retryLink.style.color = "blue";
                    retryLink.href = "javascript:void(0);";
                    retryLink.innerText = "重试";
                    retryLink.textContent = "重试";
                    var box = this;
                    var div = document.createElement("DIV");
                    div.innerHTML = "<span style=\"color:red\">发送失败.&nbsp;</span>";
                    div.appendChild(retryLink);
                    var errorMessageDiv = createSystemMessageInnerDiv(div);
                    errorMessageDiv.style.fontSize = "small";
                    errorMessageDiv.style.marginTop = "0";
                    errorMessageDiv.style.paddingTop = "0";
                    messages[i].messageDiv.appendChild(errorMessageDiv);
                    var reMessage = messages[i];
                    var reMessageDiv = reMessage.messageDiv;
                    retryLink.addEventListener("click", function () {

                        box.submitMessage(reMessage);
                        reMessageDiv.removeChild(errorMessageDiv);
                    });


                    if (this.messages.length > 0 && messages[i] === this.messages[this.messages.length - 1]) {
                        this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;
                    }
                }
            }
        }

    }
    this.onSubmitMessageSucceeded = function (messageSubmit, messageSaved) {
        if (!messageSubmit || !messageSaved)
            return;

        if (messageSubmit.fromUser && messageSubmit.fromUser.id == this.link.hostUser.id && messageSubmit.toUser.id == this.link.user.id) {
            messageSubmit.sendDate = new Date(messageSaved.sendDate);
            messageSubmit.id = messageSaved.id;
            if (messageSubmit.messageDiv) {
                var div = document.createElement("DIV");
                div.innerHTML = "<span style=\"color:green\">" + messageSubmit.sendDate.toLocaleString() + "</span>";
                var infoMessageDiv = createSystemMessageInnerDiv(div);
                infoMessageDiv.style.fontSize = "small";
                infoMessageDiv.style.marginTop = "0";
                infoMessageDiv.style.paddingTop = "0";
                messageSubmit.messageDiv.appendChild(infoMessageDiv);
                if (this.messages.length > 0 && messageSubmit === this.messages[this.messages.length - 1]) {
                    this.scrollableDiv.scrollTop = this.scrollableDiv.scrollHeight;
                }
            }
            if (this.firstMessage === null) {
                this.firstMessage = messageSaved;
                this.isFirstMessageLoaded = true;
            }
        }
    }
    this.textArea.addEventListener("keydown", function (e) {
        e = e || event;
        var result = this.onKeyDown(this, e);
        switch (result) {
            case 1:
                this.flyoutMessageBoxInstance.submitTestArea();
                if (e.preventDefault)
                    e.preventDefault();
                else
                    return false;
                break;
            case 2:
                break;
            default:
                break;
        }

    });

    this.textArea.onTextChanged = function (sender, e) {
        if (!e)
            e = sender.event || window.event;


        sender.style.height = (measureScrollHeight(sender) - 5) + "px";


    };

    this.textArea.addEventListener("input", function (e) {
        e = e || event;
        this.onTextChanged(this, e);
    });
    this.startUpload = function (isUploadingImage, file,e)
    {
        e = e || event;
        isUploadingImage = isUploadingImage || false;
        var message = createMessage("", this.link.hostUser, this.link.user);
        message.messageDiv = createEmptyMessageDiv(this.link.hostUser);
        var fmbInstance = this;
        var confirmCallback = function (filename) {
            message.content = filename + "<br/>&nbsp;上传:&nbsp;" + "0%";
            message.filename = filename;
            message.messageDiv.innerHTML = "";
            message.messageDiv.appendChild(createHtmlInnerMessageDiv(message, message.fromUser));
            fmbInstance.conversationDiv.appendChild(message.messageDiv);
            if ((fmbInstance.lastPushTimestamp !== null && (((new Date()).getTime()) - fmbInstance.lastPushTimestamp.getTime()) >= 3600000) ||
                        (fmbInstance.lastPushTimestamp === null) || fmbInstance.messages.length == 0) {
                fmbInstance.conversationDiv.insertBefore(createTimeTag(new Date()), message.messageDiv)
                fmbInstance.lastPushTimestamp = new Date();
                if (fmbInstance.lastInsertTimestamp === undefined || fmbInstance.lastInsertTimestamp === null)
                    fmbInstance.lastInsertTimestamp = new Date();
            }
            fmbInstance.messages.push(message);
            fmbInstance.scrollableDiv.scrollTop = fmbInstance.scrollableDiv.scrollHeight;
        };
        var imcontrol_box_single_callback = function (isSucceeded, feedbackMessage) {
            if (isSucceeded) {
                var ctt = feedbackMessage.content;
                var newId = feedbackMessage.id;
                ctt = JSON.parse(ctt);
                message["sendDate"] = new Date(feedbackMessage.sendDate);
                message.content = {};
                message.content.url = imctx + ctt.url;
                message.content.filename = ctt.filename;
                if (newId !== undefined && newId !== null)
                    message.id = newId;
                var tmpFn = null;
                try{
                	tmpFn = decodeURIComponent(ctt.filename);
                }catch(e){}
                if(tmpFn !== null)
                	message.content.filename = tmpFn;
                message.msgType = feedbackMessage.msgType;
                message.messageDiv.innerHTML = "";
                if(message.msgType.toUpperCase() == "FILE")
                    message.messageDiv.appendChild(createHtmlInnerFileMessageDiv(message, message.fromUser));
                else if (message.msgType.toUpperCase() == "IMG")
                    message.messageDiv.appendChild(createHtmlInnerImageMessageDiv(message, message.fromUser));
                else {
                    message.content = message.filename + "<br/>&nbsp;<span>上传成功<span>";
                    message.messageDiv.appendChild(createHtmlInnerMessageDiv(message, message.fromUser));
                }
            }
            else {
                message.content = message.filename + "<br/>&nbsp;<span style=\"color:red;\">上传失败<span>";
                message.messageDiv.innerHTML = "";
                message.messageDiv.appendChild(createHtmlInnerMessageDiv(message, message.fromUser));
            }
        };
        imcontrol.uploadFile(message, isUploadingImage,file,confirmCallback, imcontrol_box_single_callback, function (p) {
            message.content = message.filename + "<br/>&nbsp;上传:&nbsp;" + p.toFixed(1) + "%";
            message.messageDiv.innerHTML = "";
            message.messageDiv.appendChild(createHtmlInnerMessageDiv(message, message.fromUser));

        });
    }
    this.uploadLink.addEventListener("click", function (e) {
        e = e || event;
        this.flyoutMessageBoxInstance.startUpload(false, null,e);
    });

    this.uploadImageLink.addEventListener("click", function (e) {
        e = e || event;
        this.flyoutMessageBoxInstance.startUpload(true, null, e);
    });
    this.flyoutFooterDiv.addEventListener("click", function (e) {
        this.flyoutMessageBoxInstance.textArea.focus();
    });
    this.flyoutFooterDiv.addEventListener("dragover", function (e) {
        if (e.preventDefault)
            e.preventDefault();
        this.flyoutMessageBoxInstance.textArea.placeholder = "将文件拖拽到此处可发送文件";
    });
    this.flyoutFooterDiv.addEventListener("dragend", function (e) {
        if (e.preventDefault)
            e.preventDefault();
        this.flyoutMessageBoxInstance.textArea.placeholder = "在此处键入您的消息...";
    });
    this.flyoutFooterDiv.addEventListener("dragleave", function (e) {
        if (e.preventDefault)
            e.preventDefault();
        this.flyoutMessageBoxInstance.textArea.placeholder = "在此处键入您的消息...";
    });
    this.flyoutFooterDiv.addEventListener("drop", function (e) {
        e = e || event;
        if(e.preventDefault)
            e.preventDefault();
        if (!e.dataTransfer.files)
            return false;

        var file = e.dataTransfer.files[0];
        if (!file)
            return false;
        this.flyoutMessageBoxInstance.startUpload(false, file, e);
        this.flyoutMessageBoxInstance.textArea.placeholder = "在此处键入您的消息...";
        return false;
    }, false);
    this.createElement = function () {
        var resultBox = document.createElement("DIV");
        resultBox.className = 'flyoutMessageBox';
        this.mainBox = result;

        resultBox.appendChild(this.flyoutHeaderDiv);
        resultBox.appendChild(this.flyoutContentDiv);
        resultBox.appendChild(this.flyoutFooterDiv);
        var result = document.createElement("DIV");
        result.className = "flyoutMessageDock opened";
        this.mainDiv = result;
        result.appendChild(resultBox);
        if (this.firstMessage === undefined) {
            this.sendGetFirstMessageRequest();
        } else if (!this.isMessagesLoaded) {
            this.sendGetLastMessagesRequest();
        } else {
            if (!this.isLongPollingRequestOn) {
                this.isLongPollingRequestOn = true;
                this.sendLongPollingGetMessagesRequest(true);
            }
        }

        var ta = this.textArea;
        setTimeout(function () { ta.style.height = (measureScrollHeight(ta) - 5) + "px"; }, 300);//
        return result;
    };
    this.getElement = function (opening) {

        if (!this.mainDiv)
            return null;
        if (opening) {
            if (this.firstMessage === undefined) {
                this.sendGetFirstMessageRequest();
            } else if (!this.isMessagesLoaded) {
                this.sendGetLastMessagesRequest();
            } else {
                if (!this.isLongPollingRequestOn) {
                    this.isLongPollingRequestOn = true;
                    this.sendLongPollingGetMessagesRequest(true);
                }
            }
        }
        if (opening) {
            var box = this;
            setTimeout(function () {
                box.scrollableDiv.scrollTop = box.scrollableDiv.scrollHeight;
            }, 300);
        }
        return this.mainDiv;
    };
    this.setElement = function (value) {
        this.mainDiv = value;
    }
    this.close = function () {
        var element = this.getElement();


        if (element === undefined || element === null)
            return;
        if (!(element.parentElement === undefined || element.parentElement === null) && element.parentElement != this.parentElement) {
            this.parentElement = element.parentElement;
        }
        try {
            this.parentElement.removeChild(element);
        } catch (ex)
        { }

        this.setElement(null);
        if (this.onClosed !== undefined && this.onClosed !== null) {
            this.onClosed();
        }
        if(window.autoAdjust)
        {
    	    var flyoutMessageBoxCount = window.flyoutMessageBoxList.length + window.hiddenFlyoutMessageBoxList.length;
    	    window.autoAdjust(flyoutMessageBoxCount);
        }
        this.clearCallbackHandlers();
        this.isLongPollingRequestOn = false;
    };
    this.hide = function () {
        var element = this.getElement();
        if (element === undefined || element === null)
            return;
        if (!(element.parentElement === undefined || element.parentElement === null) && element.parentElement != this.parentElement) {
            this.parentElement = element.parentElement;
        }
        try {
            this.parentElement.removeChild(element);
        } catch (ex)
        { }
        if (this.onHide !== undefined && this.onHide !== null) {
            this.onHide();
        }
        if(window.autoAdjust)
        {
    	    var flyoutMessageBoxCount = window.flyoutMessageBoxList.length + window.hiddenFlyoutMessageBoxList.length;
    	    window.autoAdjust(flyoutMessageBoxCount);
        }
        this.clearCallbackHandlers();
        this.isLongPollingRequestOn = false;
    }
    this.dispose = function () {
        if (this.parentElement != null)
            this.close();
        var element = this.getElement();
        if (!(element === undefined || element === null)) {
            element.innerHTML = "";
            element = null;
        }
        if (this.flyoutHeaderDiv !== undefined && this.flyoutHeaderDiv !== null)
            this.flyoutHeaderDiv.innerHTML = "";
        this.flyoutHeaderDiv = null;

        if (this.flyoutContentDiv !== undefined && this.flyoutContentDiv !== null)
            this.flyoutContentDiv.innerHTML = "";
        this.flyoutContentDiv = null;

        if (this.scrollableDiv !== undefined && this.scrollableDiv !== null)
            this.scrollableDiv.innerHTML = "";
        this.scrollableDiv = null;

        if (this.chatContentDiv !== undefined && this.chatContentDiv !== null)
            this.chatContentDiv.innerHTML = "";
        this.chatContentDiv = null;

        if (this.conversationDiv !== undefined && this.conversationDiv !== null)
            this.conversationDiv.innerHTML = "";
        this.conversationDiv = null;


        // Generates the flyoutFooter Division Element
        if (this.flyoutFooterDiv !== undefined && this.flyoutFooterDiv !== null)
            this.flyoutFooterDiv.innerHTML = "";
        this.flyoutFooterDiv = null;

        if (this.ff_d_Div !== undefined && this.ff_d_Div !== null)
            this.ff_d_Div.innerHTML = "";
        this.ff_d_Div = null;

        if (this.textArea !== undefined && this.textArea !== null)
            this.textArea.innerHTML = "";
        this.textArea = null;
        this.parentElement = null;
        this.title = null;
        this.link.fmbInstance = null;
        this.link = null;
        this.clearCallbackHandlers();
    }
    this.closeLink.href = "javascript:void(0)";
    this.closeLink.onclick = function () {
        this.flyoutMessageBoxInstance.close();
    };
    return this;
}

var flyoutMessageBoxList = [];

flyoutMessageBoxList.remove = function (obj) {
    var index = this.indexOf(obj);
    if (index >= 0)
        this.splice(index, 1);
};
flyoutMessageBoxList.contains = function (obj) {
    var index = this.indexOf(obj);
    return (index >= 0);
};
var hiddenFlyoutMessageBoxList = [];
hiddenFlyoutMessageBoxList.remove = function (obj) {
    var index = this.indexOf(obj);
    if (index >= 0)
        this.splice(index, 1);
};
hiddenFlyoutMessageBoxList.contains = function (obj) {
    var index = this.indexOf(obj);
    return (index >= 0);
};
function MessageBoxManager() {
    if (flyoutMessageBoxList === undefined || flyoutMessageBoxList === null)
        flyoutMessageBoxList = [];
    if (hiddenFlyoutMessageBoxList === undefined || hiddenFlyoutMessageBoxList === null)
        hiddenFlyoutMessageBoxList = [];

    this.addFlyoutMessageBox = function (newFlyoutMessageBox) {
        var footerDiv = imcontrol.parentElement || document.getElementById(imcontrol.parentElementId);
        var fmb = newFlyoutMessageBox;

        if (!fmb.onClosed)
            fmb.onClosed = function () {
                // this.dispose();
                try {
                    flyoutMessageBoxList.remove(this);
                } catch (ex) { }
                if (hiddenFlyoutMessageBoxList.length > 0) {
                    var tmp = hiddenFlyoutMessageBoxList.pop();
                    flyoutMessageBoxList.push(tmp);
                    tmp.parentElement.appendChild(tmp.getElement() || tmp.createElement());
                }
            }
        if (!fmb.onHide)
            fmb.onHide = function () {

                try {
                    try {
                        flyoutMessageBoxList.remove(this);
                    } catch (ex) { }
                    hiddenFlyoutMessageBoxList.push(this);
                } catch (ex) { }
            }
        var fmbElement = fmb.getElement(true) || fmb.createElement();
        var fmbWidth = measureIsolatedElementWidth(fmbElement);
        if (footerDiv.clientWidth + fmbWidth < window.innerWidth) {
            flyoutMessageBoxList.push(fmb);
            fmb.parentElement.appendChild(fmbElement);
        } else {
            if (flyoutMessageBoxList.length <= 0) {
                hiddenFlyoutMessageBoxList.push(fmb);
            } else {
                var tmp = flyoutMessageBoxList.pop();
                tmp.hide();
                flyoutMessageBoxList.push(fmb);
                fmb.parentElement.appendChild(fmbElement);

            }
        }
    }
}
function pxToNumber(px) {
    if (!px)
        return 0;
    alert(px);
    return parseInt(px);
}

function hideOverlappedBoxes() {
    if (flyoutMessageBoxList.length <= 1)
        return;
    var footerDiv = imcontrol.parentElement || document.getElementById(imcontrol.parentElementId);
    var footerWidth = footerDiv.clientWidth;
    while (flyoutMessageBoxList.length > 1 && footerWidth < flyoutMessageBoxList[0].mainDiv.clientWidth * flyoutMessageBoxList.length) {
        var tmpE = flyoutMessageBoxList[flyoutMessageBoxList.length - 1];
        footerWidth -= tmpE.mainDiv.clientWidth;
        tmpE.hide();
    }
}

function resumeHiddenBoxes(footerWidth) {
    if (hiddenFlyoutMessageBoxList.length <= 0)
        return;
    var fmb = hiddenFlyoutMessageBoxList[hiddenFlyoutMessageBoxList.length - 1];
    var fmbElement = fmb.getElement(true) || fmb.createElement();
    var fmbWidth = measureIsolatedElementWidth(fmbElement);
    if (footerWidth + fmbWidth < window.innerWidth) {

        //fmb.parentElement.appendChild(fmbElement);
        mbm.addFlyoutMessageBox(fmb);
        hiddenFlyoutMessageBoxList.pop();
    }
    if (footerWidth  < window.innerWidth) {
        resumeHiddenBoxes(footerWidth + fmbWidth);
    }
}
function handleWindowResize() {
    var footerDiv = window.imcontrol.parentElement || document.getElementById(window.imcontrol.parentElementId);
    if (footerDiv === undefined || footerDiv === null)
        return;
    if (footerDiv.clientWidth >= window.innerWidth) {
        var totalWidth = footerDiv.clientWidth;
        while (totalWidth >= window.innerWidth) {
            if (flyoutMessageBoxList.length > 0) {
                var tmpE = flyoutMessageBoxList[flyoutMessageBoxList.length - 1];
                totalWidth = totalWidth - tmpE.mainDiv.parentElement.clientWidth / flyoutMessageBoxList.length;
                tmpE.hide();
            }
            else
                break;
        }
        hideOverlappedBoxes();
    }
    else if (hiddenFlyoutMessageBoxList.length > 0) {
        var footerDiv = imcontrol.parentElement || document.getElementById(imcontrol.parentElementId);
        resumeHiddenBoxes(footerDiv.clientWidth);

    }
}
function onWindowResize(e) {
    if (!e)
        e = window.event;
    handleWindowResize();
}
window.mbm = new MessageBoxManager();
window.addFlyoutMessageBox = function (newFlyoutMessageBox) {
    window.mbm.addFlyoutMessageBox(newFlyoutMessageBox);
}

window.createFlyoutMessageBox = function (name, peopleLinkInstance) {
    var fmb = new FlyoutMessageBox(name,imcontrol.flyoutMessageBoxesDockElement || document.getElementById(imcontrol.flyoutMessageBoxesDockElementId));
    fmb.link = peopleLinkInstance;
    fmb.peopleBox = fmb.link.peopleBox;
    peopleLinkInstance.fmbInstance = fmb;
    fmb.onSubmitMessage = function (obj, message) {
        if (imcontrol && imcontrol.sendMessage)
        {
            imcontrol.sendMessage(message, this);
        }
    };
    window.mbm.addFlyoutMessageBox(fmb);
    return fmb;
}

window.addEventListener("resize", onWindowResize);

function peopleLink(user) {
    this.fmbInstance = null;
    this.hostUser = null;
    this.peopleBox = null;
    this.onClicked = function (sender, e) {
        this.aElement.parentElement.style.backgroundColor = "inherit";
        if (this.hasUnreadMessage)
        {
            this.hasUnreadMessage = false;
            this.peopleBox.sortPeopleLinkList();
        }
        if (!this.fmbInstance || !this.fmbInstance.getElement)
            createFlyoutMessageBox(user.name, this);
        else if (this.fmbInstance.getElement(true) == null)
        {
            this.fmbInstance.createElement();
            this.fmbInstance.link = this;
            window.mbm.addFlyoutMessageBox(this.fmbInstance);
        }
        else {
            if (hiddenFlyoutMessageBoxList.contains(this.fmbInstance)) {
                hiddenFlyoutMessageBoxList.remove(this.fmbInstance);
                var footerDiv = imcontrol.parentElement || document.getElementById(imcontrol.parentElementId);
                if (footerDiv.clientWidth  >= window.innerWidth && flyoutMessageBoxList.length > 0) {
                    hiddenFlyoutMessageBoxList.push(flyoutMessageBoxList.pop());
                }
                addFlyoutMessageBox(this.fmbInstance);
            } else if (!flyoutMessageBoxList.contains(this.fmbInstance)) {
                var footerDiv = imcontrol.parentElement || document.getElementById(imcontrol.parentElementId);
                if (footerDiv.clientWidth >= window.innerWidth && flyoutMessageBoxList.length > 0) {
                    hiddenFlyoutMessageBoxList.push(flyoutMessageBoxList.pop());
                }
                addFlyoutMessageBox(this.fmbInstance);
            }
        }
        if (this.fmbInstance && this.fmbInstance.textArea && this.fmbInstance.textArea.focus)
        {
            this.fmbInstance.textArea.focus();
        }
        if(window.autoAdjust)
        {
            var flyoutMessageBoxCount = window.flyoutMessageBoxList.length + window.hiddenFlyoutMessageBoxList.length;
            window.autoAdjust(flyoutMessageBoxCount);
        }
    };

    this.user = user;
    if (!this.user.name)
        this.user.name = "无名氏";
    if (!this.user.picURL) {
        if (!window.nameIconGenerator || !window.nameIconGenerator.generateIcon) {
            if (imctx === undefined || imctx === null)
                this.user.picURL = "/static/img/user-male-silhouette_318-55563.png";
            else
                this.user.picURL = imctx + "/static/img/user-male-silhouette_318-55563.png";
        } else {
            this.user.picURL = window.nameIconGenerator.generateIcon(this.user.name);
        }
    }    
    if (!this.user.status)
        this.user.status = "off";
    this.nameElement = document.createElement("DIV");
    this.nameElement.className = "p_name";
    this.nameElement.innerHTML = this.user.name;
    this.statusElement = document.createElement("DIV");
    this.statusElement.className = "p_status";
    this.statusElement.innerHTML = "&nbsp;";
    this.avatarElement = document.createElement("DIV")
    this.avatarElement.className = "p_avatar";
    this.avatarElement.innerHTML = "<img src=" + user.picURL + " width=" + 32 + " height=" + 32 + " alt=" + user.name + " />";
    this.itemDiv = document.createElement("DIV");
    this.itemDiv.className = "p_item";
    this.aElement = document.createElement("A");
    this.aElement.className = "p_link";
    this.aElement.href = "javascript:void(0);";
    this.aElement.peopleLinkInstance = this;
    this.hasUnreadMessage = false;
    this.aElement.addEventListener("click", function (e) {
        if (this.peopleLinkInstance && this.peopleLinkInstance.onClicked)
            this.peopleLinkInstance.onClicked(this, e || event);
    });
    
    this.itemDiv.appendChild(this.avatarElement);
    this.itemDiv.appendChild(this.statusElement);
    this.itemDiv.appendChild(this.nameElement);
    this.aElement.appendChild(this.itemDiv);
    
    this.createLiElement = function () {
        var tmpLi = document.createElement("LI");
        tmpLi.style.backgroundColor = this.hasUnreadMessage ? "#FEA681" : "inherit";
        tmpLi.appendChild(this.aElement);
        return tmpLi;
    };
    this.receiveMessages = function (messages) {
        if (!messages || !messages.length || messages.length == 0)
            return;        
        if (this.fmbInstance === null || !flyoutMessageBoxList.contains(this.fmbInstance)) {
            this.hasUnreadMessage = true;
            this.peopleBox.sortPeopleLinkList(true);
            if (messages.length <= 3) {
                for (var i = 0; i < messages.length; i++) {
                    if (window.sessionStorage.getItem("alertedMessages[" + messages[i].id + "]") === null) {
                        window.sessionStorage.setItem("alertedMessages[" + messages[i].id + "]", "true");
                        var notify = "您有来自 " + this.user.name + " 的新消息。";
                        if ((!(messages[0].msgType)) || ((messages[i].msgType.toUpperCase() == 'TXT')))
                            notify = this.user.name + ": " + messages[i].content;
                        destopNotify(notify, null, user.picURL);
                        var e = document.createEvent("Event");
                        e.initEvent("newImMessage", false, false);
                        e.message = messages[i];
                        window.dispatchEvent(e);
                        if (window.onNewImMessage) {
                            window.onNewImMessage(e);
                        }
                    }
                }
            } else {
                var count = 0;
                for (var i = 0; i < messages.length; i++) {
                    if (window.sessionStorage.getItem("alertedMessages[" + messages[i].id + "]") === null) {
                        window.sessionStorage.setItem("alertedMessages[" + messages[i].id + "]", "true");
                        ++count;
                    }
                }
                if (count > 0) {                    
                    var notify = "您有来自 " + this.user.name + " 的 " + count + " 条新消息。";
                    destopNotify(notify, null, user.picURL);
                    var e = document.createEvent("Event");
                    e.initEvent("newImMessage", false, false);
                    e.message = messages[i];
                    window.dispatchEvent(e);
                    if (window.onNewImMessage) {
                        window.onNewImMessage(e);
                    }
                }
            }
        } else {
            if(this.fmbInstance.pushMode == PushMode.Polling)
                this.fmbInstance.addReceivedMessages(messages, this.hostUser);
        }

    };
    this.setOnlineStatues = function (isOnline)
    {
        this.user.status = isOnline ? "on" : "off";
        this.statusElement.innerHTML =isOnline? "<div style=\"color:#34A853\" class=\"" + this.user.status + "\">&nbsp;</div>":"<div style=\"color:#DDE4EC\" class=\"" + this.user.status + "\">&nbsp;</div>";
    }
    this.setOnlineStatues(false);
}

function PeopleBox(parentElement, hostUser)
{
    this.title = "联系人";
    this.hostUser = hostUser;
    this.parentElement = parentElement;
    // people box opener
    this.peopleBoxOpenerDiv = document.createElement("DIV");
    this.peopleBoxOpenerDiv.className = "peopleBoxOpener";
    this.openLink = document.createElement("A");
    this.openLink.href = "javascript:void(0)";
    this.openLink.text = this.title;
    this.openLink.peopleBoxInstance = this;
    this.peopleBoxOpenerDiv.appendChild(this.openLink);

    // people box header
    this.peopleHeader = document.createElement("DIV");
    this.peopleHeader.className = "peopleHeader";
    this.peopleHeader.innerHTML = "<span style=\"margin-left:0.5em;\">" + this.title + "</span>";
    this.hideLink = document.createElement("A");
    this.hideLink.href = "javascript:void(0)";
    /*this.hideLink.text = "Hide";
    this.hideLink.textContent = "Hide";*/
    this.hideLink.title = "隐藏";
    this.hideLink.className = "pbhLnk";
    this.hideLink.peopleBoxInstance = this;
    this.headerControlContainer = document.createElement("DIV");
    this.headerControlContainer.className = "headerControlContainer";
    this.headerControlContainer.appendChild(this.hideLink);
    this.peopleHeader.appendChild(this.headerControlContainer);

    // people box search bar
    this.searchInput = document.createElement("INPUT");
    this.searchInput.className = "peopleSearchInput";
    this.searchInput.type = "text";
    this.searchInput.placeholder = "搜索...";
    this.peopleSearchBar = document.createElement("DIV");
    this.peopleSearchBar.className = "peopleSearchBar";
    this.peopleSearchBar.appendChild(this.searchInput);

    // people List
    this.ulElement = document.createElement("UL");
    this.peopleDiv = document.createElement("DIV");
    this.peopleDiv.className = "people";
    this.scrollableDiv = document.createElement("DIV");
    this.scrollableDiv.className = "scrollable pbScrollable";
    this.peopleListDiv = document.createElement("DIV");
    this.peopleListDiv.className = "peopleList";
    this.peopleDiv.appendChild(this.ulElement);
    this.scrollableDiv.appendChild(this.peopleDiv);
    this.peopleListDiv.appendChild(this.scrollableDiv);
    
    // search result box
    this.reservedResultDiv = document.createElement("DIV");
    this.reservedResultDiv.className = "rrd";
    this.resultBoxDiv = document.createElement("DIV");
    this.resultScrollableDiv = document.createElement("DIV");
    this.resultScrollableDiv.className = "resultScrollable";
    this.resultBoxDiv.appendChild(this.resultScrollableDiv);
    this.offlineSearchDiv = document.createElement("DIV");
    this.onlineSearchDiv = document.createElement("DIV");
    this.searchTipDiv = document.createElement("DIV");

    this.searchTipDiv.innerHTML = "<p>搜索中...</p>";
    this.searchTipDiv.style.display = "none";
    this.resultScrollableDiv.appendChild(this.offlineSearchDiv);
    this.resultScrollableDiv.appendChild(this.onlineSearchDiv);
    this.resultScrollableDiv.appendChild(this.searchTipDiv);

    this.reservedResultDiv.appendChild(this.resultBoxDiv);
    
    this.reservedResultDiv.style.display = "none";
    this.searchEngine = new SearchEngine();
    this.currentSearchCallback = null;
    this.addSearchResultToList = function (result, isOnline) {
        for (var i = 0; i < result.length; i++) {
            var child = document.createElement("A");
            child.href = "javascript:void(0)";
            child.innerHTML = "<div>" + getSearchResultString(result[i]) + "</div>";
            child.relatedResult = result[i];
            child.addEventListener("click", function (e) {
                var tmpLink = imcontrol.peopleBox.addUser(this.relatedResult);
                tmpLink.onClicked();
            });
            child.addEventListener("mouseover", function (e) {
                for (var i = 0 ; i < this.childNodes.length; i++) {
                    if (this.childNodes[i].style)
                        this.childNodes[i].style.backgroundColor = "#F0F0F0";
                }
            });
            child.addEventListener("mouseout", function (e) {
                for (var i = 0 ; i < this.childNodes.length; i++) {
                    if (this.childNodes[i].style)
                        this.childNodes[i].style.backgroundColor = "inherit";
                }
            });
            if (isOnline)
                this.onlineSearchDiv.appendChild(child);
            else
                this.offlineSearchDiv.appendChild(child);
        }
    };

    this.onSearchTextChanged = function () {
        this.reservedResultDiv.style.bottom =
        window.getComputedStyle(this.peopleListDiv).height;
        if (this.searchInput.value == 0 || this.searchInput.value == this.searchInput.placeholder) {
            this.reservedResultDiv.style.display = "none";
        } else {
            this.reservedResultDiv.style.display = "block";
            this.searchTipDiv.style.display = "block";
            this.onlineSearchDiv.innerHTML = "";
            this.offlineSearchDiv.innerHTML = "";
            var offlineResult = this.searchEngine.searchOffline(this.searchInput.value);
            this.addSearchResultToList(offlineResult, false);
            if (this.currentSearchCallback)
                this.currentSearchCallback.hasCanceled = true;
            this.currentSearchCallback = this.searchEngine.searchOnline(this.searchInput.value, function (result) {
                imcontrol.peopleBox.searchTipDiv.style.display = "none";
                imcontrol.peopleBox.addSearchResultToList(result, true);
            });
        }
    };
    this.onSearchInputBlur = function (e) {
        imcontrol.peopleBox.searchInput.value = "";
        imcontrol.peopleBox.onSearchTextChanged();
    };
    this.searchInput.addEventListener("input", function () {
        imcontrol.peopleBox.onSearchTextChanged();
    });
    this.searchInput.addEventListener("blur", this.onSearchInputBlur);
    this.reservedResultDiv.addEventListener("mousedown", function (e) {
        imcontrol.peopleBox.searchInput.removeEventListener("blur",imcontrol.peopleBox.onSearchInputBlur);
    }, true);
    this.reservedResultDiv.addEventListener("click", function (e) {
        setTimeout(function () {
            imcontrol.peopleBox.searchInput.addEventListener("blur",
                imcontrol.peopleBox.onSearchInputBlur);
            imcontrol.peopleBox.searchInput.value = "";
            imcontrol.peopleBox.onSearchTextChanged();
        },250);
    }, true);
    this.reservedResultDiv.addEventListener("touchstart", function (e) {
        imcontrol.peopleBox.searchInput.removeEventListener("blur", imcontrol.peopleBox.onSearchInputBlur);
    }, true);
    
    this.peopleLinkList = [];

    this.peopleBoxDiv = document.createElement("DIV");
    this.peopleBoxDiv.className = "peopleBox";
    this.peopleDockDiv = document.createElement("DIV");
    this.peopleDockDiv.className = "peopleDock opened";

    this.peopleBoxDiv.appendChild(this.peopleHeader);
    this.peopleBoxDiv.appendChild(this.peopleSearchBar);
    this.peopleBoxDiv.appendChild(this.peopleListDiv);
    this.peopleBoxDiv.appendChild(this.reservedResultDiv);
    this.webControl = null;
    this.imControl = null;
    this.open = function () {
        if (this.peopleDockDiv.contains(this.peopleBoxOpenerDiv))
            this.peopleDockDiv.removeChild(this.peopleBoxOpenerDiv);
        this.peopleDockDiv.className = "peopleDock opened";
        this.peopleDockDiv.appendChild(this.peopleBoxDiv);
        handleWindowResize();
    };
    this.hide = function () {
        if (this.peopleDockDiv.contains(this.peopleBoxDiv))
            this.peopleDockDiv.removeChild(this.peopleBoxDiv);
        this.peopleDockDiv.className = "peopleDock closed";
        this.peopleDockDiv.appendChild(this.peopleBoxOpenerDiv);
        handleWindowResize();
    }
    this.sortPeopleLinkList = function (scrollingToTop) {
        this.peopleLinkList.sort(function (link1, link2) {
            if (link1.hasUnreadMessage != link2.hasUnreadMessage) {
                if (link1.hasUnreadMessage)
                    return -1;
                else
                    return 1;
            } else {
                if (!link1.user.name.localeCompare || !link2.user.name.localeCompare)
                    return link1.user.name > link2.user.name;
                else
                    return link1.user.name.localeCompare(link2.user.name);
            }
        });
        this.updateUlElement();
        if (scrollingToTop)
        {
            this.scrollableDiv.scrollTop = 0;
        }
    };
    this.openLink.addEventListener("click", function () {
        this.peopleBoxInstance.open();

    });
    this.hideLink.addEventListener("click", function () {
        this.peopleBoxInstance.hide();
    });
    this.getElement = function () {
        return this.peopleDockDiv;
    };

    this.setup = function () {
        if (!this.parentElement)
            return;
        this.parentElement.appendChild(this.getElement());
        this.open();
    };
    this.updateUlElement = function () {
        var dummyUl = document.createElement("UL");
        for (var i = 0; i < this.peopleLinkList.length; i++) {
            if (this.peopleLinkList[i].user) {
                dummyUl.appendChild(this.peopleLinkList[i].createLiElement());
            }
        }
        this.peopleDiv.appendChild(dummyUl);
        this.peopleDiv.removeChild(this.ulElement);
        this.ulElement = dummyUl;
    };
    this.addUser = function (user) {
        for (var i = 0; i < this.peopleLinkList.length; i++)
        {
            if (this.peopleLinkList[i].user && (this.peopleLinkList[i].user == user || (user.id !== undefined && user.id != null && this.peopleLinkList[i].user.id == user.id)))
            {
                return this.peopleLinkList[i];
            }
        }
        var newUser = new peopleLink(user);
        newUser.hostUser = this.hostUser;
        newUser.peopleBox = this;
        this.peopleLinkList.push(newUser);
        this.sortPeopleLinkList();
        for (var i = 0 ; i < this.peopleLinkList.length; i++)
        {
            this.searchEngine.addToResultList([this.peopleLinkList[i].user]);
        }
        return newUser;
    };

    this.addUserCollection = function (userCollection)
    {
        if (!userCollection || !userCollection.length || userCollection.length == 0)
            return;
        else
        {
            userCollection.removeAt = function (index)
            {
               return userCollection.splice(index, 1);
            }
        }
        for (var i = 0; i < this.peopleLinkList.length; i++) {
            for (var j = 0; j < userCollection.length; j++) {
                if (this.peopleLinkList[i].user && this.peopleLinkList[i].user.id == userCollection[j].id) {
                    userCollection.removeAt(j);
                    --j;
                }
            }
        }
        var newLinks = [];
        for (var i = 0; i < userCollection.length; i++) {
            var newUser = new peopleLink(userCollection[i]);
            newUser.hostUser = this.hostUser;
            newUser.peopleBox = this;
            newLinks.push(newUser);
            this.peopleLinkList.push(newUser);
        }

        this.sortPeopleLinkList();
        for (var i = 0 ; i < this.peopleLinkList.length; i++) {
            this.searchEngine.addToResultList([this.peopleLinkList[i].user]);
        }
        return newLinks;
    }
    this.updateFriendsStatus = function(statusMap)
    {
        for (var i = 0; i < this.peopleLinkList.length; i++)
        {
            var tmpUser = this.peopleLinkList[i].user;
            var isOnline = statusMap["user" + tmpUser.id + tmpUser.name];
            if (isOnline)
                isOnline = true;
            else
                isOnline = false;
            this.peopleLinkList[i].setOnlineStatues(isOnline);
        }
    }
    this.receiveMessages = function (messages) {
        if (!messages || !messages.length || messages.length == 0)
            return;
        var map = [];
        var messagesMap = [];
        var fromAndToUsers = [];
        for (var i = 0; i < this.peopleLinkList.length; i++) {
            if (!map["user" + this.peopleLinkList[i].user.id.toString()]) {
                map["user" + this.peopleLinkList[i].user.id.toString()] = this.peopleLinkList[i];
            }
        }
        for (var i = 0; i < messages.length; i++)
        {
            if (messages[i].msgType !== undefined && messages[i].msgType !== null && messages[i].msgType.toUpperCase() != "txt".toUpperCase())
            {
                var orig = messages[i].content.toString();
                try {
                    messages[i].content = JSON.parse(messages[i].content);
                    messages[i].textContent = orig;
                } catch (e) {
                    messages[i].content = orig;
                }
                if (messages[i].content === undefined)
                    messages[i].content = orig;
            }
            if (messages[i].toUser.id == hostUser.id )
            {
                if (!messagesMap["user" + messages[i].fromUser.id]) {
                    messagesMap["user" + messages[i].fromUser.id] = [];
                    messagesMap["user" + messages[i].fromUser.id].push(messages[i]);
                    fromAndToUsers.push(messages[i].fromUser);
                } else {
                    messagesMap["user" + messages[i].fromUser.id].push(messages[i]);
                }
            } else if ((messages[i].fromUser.id == hostUser.id && messages[i].toUser.id != hostUser.id))
            {
                if (!messagesMap["user" + messages[i].toUser.id]) {
                    messagesMap["user" + messages[i].toUser.id] = [];
                    messagesMap["user" + messages[i].toUser.id].push(messages[i]);

                    fromAndToUsers.push(messages[i].toUser);
                } else {
                    messagesMap["user" + messages[i].toUser.id].push(messages[i]);
                }
            }
        }
        for (var i = 0; i < fromAndToUsers.length; i++)
        {
            if (!map["user" + fromAndToUsers[i].id.toString()]) {
                var newUserLink = this.addUser(fromAndToUsers[i]);
                newUserLink.receiveMessages(messagesMap["user" + fromAndToUsers[i].id.toString()]);
            } else {
                map["user" + fromAndToUsers[i].id.toString()].receiveMessages(messagesMap["user" + fromAndToUsers[i].id.toString()]);
            }
        }
    }
    window.peopleBox = this;
}
function Communication(sender, data, attemptCount) {
    this.sender = sender;
    this.data = data;
    this.attemptCount = attemptCount;
};


window.imcontrol = {
    peopleBox: null,
    webControl: null,
    parentElement: null,
    parentElementId: "footerDiv",
    footerDockElementId: "footerDock",
    chatMainDockElementId: "chatMainDock",
    flyoutMessageBoxesDockElementId: "flyoutMessageBoxesDock",
    dockClearSpaceElementClass: "dockClearSpace",

    footerDockElement: null,
    chatMainDockElement: null,
    flyoutMessageBoxesDockElement: null,
    dockClearSpaceElement: null,
    pushMode: PushMode.Polling,
    DataEntityProperties: {
        DepartmentProperties: ["id", "name", "deptCode"],
        UserProperties: ["id", "userCode", "name", "idNo", createMappedPropertyString("department", function () { return imcontrol.DataEntityProperties.DepartmentProperties; } ), "area", "password", "salt", "email", "weixin", "mobile", "sex", "birthday", "address", "zipcode", "valid", "role", "createDate", "watchState", "userState", "userCodeState"],
        MessageProperties: ["id", createMappedPropertyString("fromUser", function () { return imcontrol.DataEntityProperties.UserProperties; }), createMappedPropertyString("toUser", function () { return imcontrol.DataEntityProperties.UserProperties;}), "content", "isRead", "sendDate", "readDate", "uuid", "guid","msgType"],
        UserSearchProperties: ["name", "department.name"],

        GetObjectStandardized: function (object, properties) {
            var result = new Object();
            for (var i = 0; i < properties.length; i++)
            {
                if (!object[properties[i]] || object[properties[i]] === undefined || object[properties[i]] === null || !properties[i].getMappedProperties) {
                    result[properties[i]] = object[properties[i]];
                } else {
                    result[properties[i]] = imcontrol.DataEntityProperties.GetObjectStandardized(object[properties[i]], properties[i].getMappedProperties());
                }
            }
            return result;
        },
    },
    pendingCommunication: {
        sendMessage: null,
        requestUnreadMessages: null,
        markMessagesAsRead: null,
        requestMessages: null,
        requestAllUnreadMessages: null,
        loadFriends:null,
        loadUsersStatus: null,
        longPollingRequestUnreadMessages:null,
    },

    setMessageRequestLoop: function () {
        setInterval(function () { imcontrol.requestAllUnreadMessages(imcontrol, imcontrol.peopleBox.hostUser); }, 2500);
    },

    setMessageRequestLongPolling: function () {
        imcontrol.longPollingRequestUnreadMessages(imcontrol, imcontrol.peopleBox.hostUser);
    },

    setLoadUsersStatusLoop: function () {
        setInterval(function () {
            var userList = [];
            for (var i = 0 ; i < imcontrol.peopleBox.peopleLinkList.length; i++)
            {
                userList.push(imcontrol.peopleBox.peopleLinkList[i].user);
            }
            imcontrol.loadUsersStatus(userList);
        }, 10000);
    },
    loadFriends_Callback: function (users) {
        if (users === undefined || users === null) {
            var sender = imcontrol.pendingCommunication.loadFriends.sender;
            var hostUser = imcontrol.pendingCommunication.loadFriends.data;
            var attemptCount = imcontrol.pendingCommunication.loadFriends.attemptCount;
            attemptCount = attemptCount || 0;
            ++attemptCount;
            if (imcontrol.pendingCommunication.loadFriends) {
                setTimeout(function () { imcontrol.pendingCommunication.loadFriends = null; imcontrol.loadFriends(hostUser, sender, attemptCount); }, 500);
            }
        } else {
            imcontrol.pendingCommunication.loadFriends = null;
            var fMap = [];
            for (var j = 0; j < imcontrol.peopleBox.peopleLinkList.length; j++) {
                if (!fMap[imcontrol.peopleBox.peopleLinkList[j].user.name + imcontrol.peopleBox.peopleLinkList[j].user.id])
                    fMap[imcontrol.peopleBox.peopleLinkList[j].user.name + imcontrol.peopleBox.peopleLinkList[j].user.id] = imcontrol.peopleBox.peopleLinkList[j].user;

            }

            var fl = users;
            var friendCollection = [];
            for (var i = 0; i < fl.length; i++) {
                if (fMap[fl[i].name + fl[i].id] !== undefined)
                    continue;
                var friend = new Object();
                friend.area = fl[i].area;
                friend.birthday = fl[i].birthday;
                friend.createDate = fl[i].createDate;
                friend.department = fl[i].department;
                friend.email = fl[i].email;
                friend.id = fl[i].id;
                friend.mobile = fl[i].mobile;
                friend.name = fl[i].name;
                friend.sex = fl[i].sex;
                friend.userCode = fl[i].userCode;
                friendCollection.push(friend);
            }
            imcontrol.peopleBox.addUserCollection(friendCollection);
            imcontrol.loadUsersStatus(friendCollection);
            imcontrol.setLoadUsersStatusLoop();
           switch (imcontrol.pushMode) {
                case PushMode.Polling:
                    imcontrol.setMessageRequestLoop();
                    break;
                case PushMode.LongPolling:
                    imcontrol.setMessageRequestLongPolling();
                    break;
            }

        }
    },
    loadFriends:function(hostUser,sender,attemptCount)
    {
        if (this.hostUser === null)
            throw new Error("hostUser cannot be undefined or null");
        attemptCount = attemptCount || 0;
        if (this.pendingCommunication.loadFriends)
        {
            return;
        }
        this.pendingCommunication.loadFriends = new Communication(sender, hostUser, attemptCount);
        this.webControl.loadFriendList(hostUser);
    },
    loadPeopleBox:function(user)
    {
        if (document.getElementById(imcontrol.footerDockElementId))
        {
            var hostUser = user;
            imcontrol.peopleBox = new PeopleBox(document.getElementById(imcontrol.chatMainDockElementId), hostUser);
            imcontrol.peopleBox.webControl = imcontrol.webControl;
            imcontrol.peopleBox.setup();
            imcontrol.loadFriends(hostUser);
        }
    },
    loadCurrentUser_CallBack: function (user) {
        if (user === undefined || user === null) {
            setTimeout(function () { imcontrol.loadCurrentUser(); }, 3000);
        } else {
            var currentUser = new Object();
            currentUser.area = user.area;
            currentUser.birthday = user.birthday;
            currentUser.createDate = user.createDate;
            currentUser.department = user.department;
            currentUser.email = user.email;
            currentUser.mobile = user.mobile;
            currentUser.id = user.id;
            currentUser.name = user.name;
            currentUser.sex = user.sex;
            currentUser.userCode = user.userCode;
            imcontrol.loadPeopleBox(currentUser);
        }
    },
    loadCurrentUser: function () {
        if(this.webControl !== undefined && this.webControl !== null)
            this.webControl.loadCurrentUser();
    },
    getParentElementReady: function (count) {
        if (!count || count === undefined || count === null)
            count = 0;
        if (count >= 3)
            throw new Error("the parentElement passed to the imcontrol is not valid")
        if(this.parentElement === undefined || this.parentElement === null)
            throw new Error("No parentElement Assigned!")
        this.chatMainDockElement = document.getElementById(this.chatMainDockElementId);
        this.flyoutMessageBoxesDockElement = document.getElementById(this.flyoutMessageBoxesDockElementId);
        this.footerDockElement = document.getElementById(this.footerDockElementId);
        var tmp = document.getElementsByClassName(this.dockClearSpaceElementClass);
        if (tmp && tmp.length && tmp.length > 0)
        {
            this.dockClearSpaceElement = tmp[tmp.length - 1];
        }
        if (this.chatMainDockElement && this.flyoutMessageBoxesDockElement && this.footerDockElement &&
             this.chatMainDockElement.parentElement == this.footerDockElement && this.flyoutMessageBoxesDockElement.parentElement == this.footerDockElement) {
            if (this.footerDockElement.parentElement == this.parentElement) {
                return true;
            } else {
                if (this.footerDockElement.parentElement && this.footerDockElement.parentElement.removeChild)
                    this.footerDockElement.parentElement.removeChild(this.footerDockElement);
                this.parentElement.appendChild(this.footerDockElement);
                return this.getParentElementReady(count + 1);
            }

        } else {
            if (!this.footerDockElement || this.footerDockElement === undefined || this.footerDockElement === null)
            {
                this.footerDockElement = document.createElement("DIV");
                this.footerDockElement.id = this.footerDockElementId;
            }
            this.footerDockElement.innerHTML = "";
            this.flyoutMessageBoxesDockElement = document.createElement("DIV");
            this.dockClearSpaceElement = document.createElement("DIV");
            this.chatMainDockElement = document.createElement("DIV");

            this.flyoutMessageBoxesDockElement.id = this.flyoutMessageBoxesDockElementId;
            this.dockClearSpaceElement.className = this.dockClearSpaceElementClass;
            this.chatMainDockElement.id = this.chatMainDockElementId;

            this.footerDockElement.appendChild(this.flyoutMessageBoxesDockElement);
            this.footerDockElement.appendChild(this.dockClearSpaceElement);
            this.footerDockElement.appendChild(this.chatMainDockElement);

            if (!this.footerDockElement.parentElement || this.footerDockElement.parentElement != this.parentElement)
            {
                if (this.footerDockElement.parentElement && this.footerDockElement.parentElement !== undefined && this.footerDockElement.parentElement !== null)
                {
                    this.footerDockElement.removeChild(this.footerDockElement);
                }
                this.parentElement.appendChild(this.footerDockElement);
            }
            return this.getParentElementReady(count + 1);
        }
        return false;
    },
    setup: function (webControl, parentElement) {
        this.webControl = webControl;
        imcontrol.chars = ["f", "i", "n", "o", "s", "t"];
        if (this.parentElementId && this.parentElementId !== undefined && this.parentElementId !== null)
            parentElement = parentElement || document.getElementById(this.parentElementId);

        if (this.webControl !== undefined && this.webControl !== null) {
            if (!parentElement || parentElement === undefined || parentElement === null)
            {
                parentElement = document.createElement("DIV");
                parentElement.id = "_imcontrolParent_sinosoft";
                parentElement.style.position = "fixed";
                parentElement.classList.add("imcontrolParent");
                var bodyElement = null;
                var bodyElements = document.getElementsByTagName("BODY");
                if (bodyElements && bodyElements.length && bodyElements.length > 0)
                {
                    bodyElement = bodyElements[bodyElements.length - 1];
                }
                if (!bodyElement || bodyElement === null)
                {
                    bodyElement = document.createElement("BODY");
                    document.documentElement.appendChild(bodyElement);
                }
                bodyElement.appendChild(parentElement);

            }
            if (!parentElement.id || parentElement.id === undefined || parentElement.id === null)
                parentElement.id = "_imcontrolParent_sinosoft";
            parentElement.classList.add("imcontrolParent");
            this.parentElement = parentElement;
            this.parentElementId = this.parentElement.id;
            if(this.getParentElementReady())
                this.loadCurrentUser();
        }
    },
    loadUsersStatus_Callback:function(boolList)
    {
        if (boolList !== undefined && boolList !== null && boolList.length)
        {
            if (imcontrol.pendingCommunication.loadUsersStatus)
            {
                var userList = imcontrol.pendingCommunication.loadUsersStatus.data;
                imcontrol.pendingCommunication.loadUsersStatus = null;
                if (userList !== undefined && userList !== null && userList.length == boolList.length)
                {
                    var map = {};
                    for (var i = 0 ; i < userList.length; i++)
                    {
                        map["user" + userList[i].id + userList[i].name] = boolList[i];
                    }
                    imcontrol.peopleBox.updateFriendsStatus(map);
                }
            }
        }
        imcontrol.pendingCommunication.loadUsersStatus = null;
    },
    loadUsersStatus: function(userList)
    {
        if (this.pendingCommunication.loadUsersStatus || userList === undefined || userList === null || !userList.length) {
            return;
        }
        else {
            this.pendingCommunication.loadUsersStatus = new Communication(this, userList, 0);
            var tmp = [];
            for (var i = 0 ; i < userList.length; i++)
            {
                tmp.push(imcontrol.DataEntityProperties.GetObjectStandardized(userList[i],
                    imcontrol.DataEntityProperties.UserProperties));
            }
            imcontrol.webControl.loadUsersStatus(tmp);
        }
    },

    isRequestingMessages : function(){
        return !(imcontrol.pendingCommunication.requestAllUnreadMessages === null &&
            imcontrol.pendingCommunication.requestMessages === null &&
            imcontrol.pendingCommunication.requestUnreadMessages === null);
    },
    longPollingRequestUnreadMessages_Callback : function(messages)
    {
        if (imcontrol.pendingCommunication.requestMessages)
            return;
        if (messages && messages.length !== undefined && messages.length !== null)
        {
            imcontrol.pendingCommunication.requestAllUnreadMessages = null;
            imcontrol.receiveMessages(messages);
        }
        setTimeout(function () {
            imcontrol.pendingCommunication.longPollingRequestUnreadMessages = null;
            imcontrol.longPollingRequestUnreadMessages(imcontrol, imcontrol.peopleBox.hostUser);
        }, (messages && messages.length !== undefined && messages.length !== null) ? 2000: 10000);
    },
    
    longPollingRequestUnreadMessages: function (sender, hostUser) {
        if (imcontrol.pendingCommunication.longPollingRequestUnreadMessages)
        {
            return;
        }
        if (imcontrol.pendingCommunication.requestAllUnreadMessages || imcontrol.pendingCommunication.requestMessages || imcontrol.pendingCommunication.requestUnreadMessages)
        {
            setTimeout(function () { imcontrol.longPollingRequestUnreadMessages(sender, hostUser); },500);
        }
        imcontrol.pendingCommunication.longPollingRequestUnreadMessages = new Communication(sender, hostUser, 0);
        var tmp = imcontrol.DataEntityProperties.GetObjectStandardized(hostUser, imcontrol.DataEntityProperties.UserProperties);
        imcontrol.webControl.longPollingRequestUnreadMessages(tmp);
    },
    longPollingRequestMessages: function (params, callbackHandler) {
        var handler = callbackHandler;
        var hostUser = params.hostUser;
        hostUser = imcontrol.DataEntityProperties.GetObjectStandardized(hostUser, imcontrol.DataEntityProperties.UserProperties);
        var guestUser = params.sender.link.user;
        guestUser = imcontrol.DataEntityProperties.GetObjectStandardized(guestUser, imcontrol.DataEntityProperties.UserProperties);
        var sie = params.startIdExclusive;
        var fromDate = params.startFromDate;
        imcontrol.webControl.longPollingRequestMessages(hostUser, guestUser,
            function (messages) {
                var h = handler;
                var mgs = messages;
                h.callback_function(mgs);
        }, sie, fromDate);
    },
    requestAllUnreadMessages_Callback: function (messages) {
        if (imcontrol.pendingCommunication.requestAllUnreadMessages == null)
            return;

        if (messages === undefined || messages === null || messages.length === undefined || messages.length === null) {
            var sender = imcontrol.pendingCommunication.requestAllUnreadMessages.sender;
            var hostUser = imcontrol.pendingCommunication.requestAllUnreadMessages.data;
            var attemptCount = imcontrol.pendingCommunication.requestAllUnreadMessages.attemptCount;
            attemptCount = attemptCount || 0;
            if (imcontrol.pendingCommunication.requestAllUnreadMessages)
            {
                setTimeout(function () { imcontrol.pendingCommunication.requestAllUnreadMessages = null; imcontrol.requestAllUnreadMessages(sender, hostUser, attemptCount); }, 1000);
            }
        } else {
            imcontrol.pendingCommunication.requestAllUnreadMessages = null;
            imcontrol.receiveMessages(messages);
        }
    },
    requestAllUnreadMessages: function (sender, hostUser,attemptCount) {
        attemptCount = attemptCount || 0;
        if (imcontrol.isRequestingMessages())
        {
            setTimeout(function () { imcontrol.requestAllUnreadMessages(sender,hostUser,attemptCount); }, 1000);
            return;
        }
        imcontrol.pendingCommunication.requestAllUnreadMessages = new Communication(sender, hostUser, attemptCount);
        var tmp = imcontrol.DataEntityProperties.GetObjectStandardized(hostUser,imcontrol.DataEntityProperties.UserProperties);
        imcontrol.webControl.requestAllUnreadMessages(tmp);
    },

    requestMessages_Callback:function(messages)
    {
        if (messages && messages.length !== undefined && messages.length !== null) {
            var sender = imcontrol.pendingCommunication.requestMessages.sender;
            if (sender)
            {
            	if (sender.isMessagesLoaded !== undefined && flyoutMessageBoxList.contains(sender))
            	{
            	    sender.isMessagesLoaded = true;
            	    try { sender.scrollableDiv.style.backgroundColor = "#FFFFFF"; } catch (exception) { }
            	}
            }
            imcontrol.pendingCommunication.requestMessages = null;
            imcontrol.receiveMessages(messages);
            if (imcontrol.pendingCommunication.longPollingRequestUnreadMessages === null && imcontrol.pushMode == PushMode.LongPolling)
                imcontrol.setMessageRequestLongPolling();
        }
        else {
            var userList = imcontrol.pendingCommunication.requestMessages.data;
            var sender = imcontrol.pendingCommunication.requestMessages.sender;
            var attemptCount = imcontrol.pendingCommunication.requestMessages.attemptCount || 0;
            ++attemptCount;
            if (imcontrol.pendingCommunication.requestMessages)
            {
                setTimeout(function () { imcontrol.pendingCommunication.requestMessages = null; imcontrol.requestMessages(userList, sender, attemptCount); }, 500);
                return;
            }
        }
    },

    requestMessages: function (userList, sender, attemptCount) {
        if (!userList)
            throw new Error("The userList cannot be undefined or null");
        attemptCount = attemptCount || 0;
        if (imcontrol.isRequestingMessages()||imcontrol.pendingCommunication.longPollingRequestUnreadMessages)
        {
            if (imcontrol.pendingCommunication.requestMessages || imcontrol.pendingCommunication.requestUnreadMessages) {
                setTimeout(function () { imcontrol.requestMessages(userList, sender, attemptCount); }, 1000);
                return;
            } else if (imcontrol.pendingCommunication.requestAllUnreadMessages)
            {
                imcontrol.pendingCommunication.requestAllUnreadMessages = null;
            } else if (imcontrol.pendingCommunication.longPollingRequestUnreadMessages)
            {
                imcontrol.pendingCommunication.longPollingRequestUnreadMessages = null;
            }

        }
        imcontrol.pendingCommunication.requestMessages = new Communication(sender, userList, attemptCount);
        var tmp = [];
        for (var i = 0; i < userList.length; i++) {
            tmp.push(imcontrol.DataEntityProperties.GetObjectStandardized(userList[i], imcontrol.DataEntityProperties.UserProperties));
        }
        imcontrol.webControl.requestMessages(tmp);
    },

    requestUnreadMessages_Callback:function(messages)
    {
        if (messages && messages.length !== undefined && messages.length !== null) {
            imcontrol.pendingCommunication.requestUnreadMessages = null;
            imcontrol.receiveMessages(messages);
        } else {
            var sender = imcontrol.pendingCommunication.requestUnreadMessages.sender;
            var userList = imcontrol.pendingCommunication.requestUnreadMessages.data;
            var attemptCount = imcontrol.pendingCommunication.requestUnreadMessages.attemptCount || 0;
            attemptCount++;
            if (imcontrol.pendingCommunication.requestUnreadMessages) {
                setTimeout(function () { imcontrol.pendingCommunication.requestUnreadMessages = null; console.log("Retry to get unread messsages for " + userList + ". \r\nAttempt count = " + attemptCount); imcontrol.requestUnreadMessages(userList, sender, attemptCount); }, 1000);
            }
        }
    },

    requestUnreadMessages: function (userList,sender,attemptCount) {
        if (!userList)
            throw new Error("No userList assigned");
        attemptCount = attemptCount || 0;
        if (imcontrol.isRequestingMessages())
        {
            setTimeout(function () { imcontrol.requestUnreadMessages(userList, sender, attemptCount); }, 1000);
            return;
        }
        imcontrol.pendingCommunication.requestUnreadMessages = new Communication(sender,userList,attemptCount);
        var tmp = [];
        for (var i = 0; i < userList.length; i++)
        {
            tmp.push(imcontrol.DataEntityProperties.GetObjectStandardized(userList[i], imcontrol.DataEntityProperties.UserProperties));
        }
        imcontrol.webControl.requestUnreadMessages(tmp);
    },
    requestMessagesByUsersAndDates: function (params, callbackHandler) {
        var cbh = callbackHandler;
        var hostUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.hostUser, imcontrol.DataEntityProperties.UserProperties);
        var guestUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.guestUser, imcontrol.DataEntityProperties.UserProperties);
        var fromDate = params.fromDate;
        var toDate = params.toDate;
        imcontrol.webControl.requestMessagesByUsersAndDates(hostUser, guestUser, fromDate, toDate,function (messages) {
            var hdlr = cbh;
            var msgs = messages;
            hdlr.callback_function(msgs);
        });
    },
    requestFirstMessage: function (params, callbackHandler) {
        var cbh = callbackHandler;
        var hostUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.hostUser, imcontrol.DataEntityProperties.UserProperties);
        var guestUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.guestUser, imcontrol.DataEntityProperties.UserProperties);
        imcontrol.webControl.requestFirstMessage(hostUser, guestUser, function (messages) {
            var hdlr = cbh;
            var msgs = messages;
            hdlr.callback_function(msgs);
        });
    },
    requestLastMessages: function (params, callbackHandler) {
        var cbh = callbackHandler;
        var hostUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.hostUser, imcontrol.DataEntityProperties.UserProperties);
        var guestUser = imcontrol.DataEntityProperties.GetObjectStandardized(params.guestUser, imcontrol.DataEntityProperties.UserProperties);
        var maxCount = params.maxCount;
        var toDate = params.toDate;
        imcontrol.webControl.requestLastMessages(hostUser, guestUser, maxCount, function (messages) {
            var hdlr = cbh;
            var msgs = messages;
            hdlr.callback_function(msgs);
        }, toDate);
    },
    sendMessage_Callback: function (hasSucceeded, messageSaved) {
        var sender = imcontrol.pendingCommunication.sendMessage.sender;
        var message = imcontrol.pendingCommunication.sendMessage.data;
        var attemptCount = imcontrol.pendingCommunication.sendMessage.attemptCount || 0;
        if (!hasSucceeded || messageSaved === undefined || messageSaved === null) {
            if (imcontrol.pendingCommunication.sendMessage) {
                imcontrol.pendingCommunication.sendMessage = null;
                if (attemptCount < 1) {
                    setTimeout(function () {
                        imcontrol.sendMessage(message,
                           sender, attemptCount + 1);
                    }, 500)
                } else {
                    sender.onSubmitMessageFailure([message]);
                }
            }

        } else {
            imcontrol.pendingCommunication.sendMessage = null;
            sender.onSubmitMessageSucceeded(message, messageSaved);
        }
    },

    sendMessage: function (message, sender, attemptCount) {
        attemptCount = attemptCount || 0;
        if (imcontrol.pendingCommunication.sendMessage !== null)
        {
            setTimeout(function () { imcontrol.sendMessage(message,sender,attemptCount) }, 1000);
            return;
        }
        imcontrol.pendingCommunication.sendMessage = new Communication(sender, message,attemptCount);
        if (!message)
        {
            return;
        }
        
        var msg = imcontrol.DataEntityProperties.GetObjectStandardized(message, imcontrol.DataEntityProperties.MessageProperties);
        imcontrol.webControl.sendMessage(msg);
        
    },
    markMessagesAsRead_Callback: function (hasSucceeded) {
        if (!hasSucceeded) {
            var sender = imcontrol.pendingCommunication.markMessagesAsRead.sender;
            var unreadMessages = imcontrol.pendingCommunication.markMessagesAsRead.data;
            var attemptCount = imcontrol.pendingCommunication.markMessagesAsRead.attemptCount;
            attemptCount = attemptCount || 0;
            ++attemptCount;
            if (imcontrol.pendingCommunication.markMessagesAsRead) {
                imcontrol.pendingCommunication.markMessagesAsRead = null;
                setTimeout(function () { console.log("Failed to mark messages as read. \r\nAttempt count = "+attemptCount); imcontrol.markMessagesAsRead(unreadMessages, sender, attemptCount); }, 500);
            }
        } else {
            imcontrol.pendingCommunication.markMessagesAsRead.sender.onMarkMessageAsRead(imcontrol.pendingCommunication.markMessagesAsRead.data);
            imcontrol.pendingCommunication.markMessagesAsRead = null;
        }
    },
    markMessagesAsRead: function (unreadMessages, sender, attemptCount) {
        if (!unreadMessages)
            throw new Error("The unreadMesseges is not assigned!")
        attemptCount = attemptCount || 0;
        if (imcontrol.pendingCommunication.markMessagesAsRead !== null)
        {
            if (unreadMessages == imcontrol.pendingCommunication.markMessagesAsRead.data && sender == imcontrol.pendingCommunication.markMessagesAsRead.sender)
                return;
            setTimeout(function () { imcontrol.markMessagesAsRead(unreadMessages, sender, attemptCount); }, 500);
            return;
        }
        imcontrol.pendingCommunication.markMessagesAsRead = new Communication(sender, unreadMessages, attemptCount);
        var msgs = [];
        for (var i = 0; i < unreadMessages.length; i++)
        {
            msgs.push(imcontrol.DataEntityProperties.GetObjectStandardized(unreadMessages[i], imcontrol.DataEntityProperties.MessageProperties));
            if(msgs[i].content.url || msgs[i].content.filename)
            msgs[i].content = unreadMessages[i].textContent;
        }
        imcontrol.webControl.markMessagesAsRead(msgs);
    },
    searchPeople:function(keywords, callback){
        if (keywords === undefined || keywords === null)
        {
            if (callback)
                callback([]);
            return;
        }
        imcontrol.webControl.searchPeople(keywords, callback);
    },
    receiveMessages: function (messages)
    {
        imcontrol.peopleBox.receiveMessages(messages);
    },
    uploadFile: function (message, isUploadingImage, file, onconfirmCallback, callback, progressCallback) {
        if (!file) {
            var fileInputElement = document.createElement("INPUT");
            fileInputElement.type = "file";
            fileInputElement.style.position = "absolute";
            fileInputElement.style.cssFloat = "left";
            fileInputElement.style.top = "-65535px";
            fileInputElement.style.left = "-65535px";
            var clickEvent = document.createEvent("MouseEvents");
            clickEvent.initEvent("click", true, false);
            var callback = callback;
            document.documentElement.appendChild(fileInputElement);
            fileInputElement.addEventListener("click", function (e2) {
                if (isUploadingImage)
                    this.accept = "image/*";
            });
            fileInputElement.addEventListener("change", function (e1) {
                if (fileInputElement.files[0]) {
                    onconfirmCallback(fileInputElement.files[0].name);
                    window.imcontrol.webControl.uploadFile(fileInputElement.files[0], message, callback, progressCallback);
                }
                this.remove;
            });

            fileInputElement.dispatchEvent(clickEvent);
        } else {
            
                onconfirmCallback(file.name);
                window.imcontrol.webControl.uploadFile(file, message, callback, progressCallback);
            
        }
    }
};

imcontrol.Crypto = {
    get_: function () {
        var hex = "";
        var list = [5, 2, 3, 4, 5, 4, 1, 6];
        for (var i = 0 ; i < list.length; i++)
        {
            hex += imcontrol.chars[list[i] - (1)].charCodeAt(0).toString(16).toUpperCase().length < 4 ? "00" + imcontrol.chars[list[i] - (1)].charCodeAt(0).toString(16).toUpperCase() : imcontrol.chars[list[i] - (1)].charCodeAt(0).toString(16).toUpperCase();
        }
        return hex;
    },
    _get: function () {
        var hex = "";
        var list = [5, 2, 3, 4, 5, 4, 1, 6,1,4,5,4,3,2,5,6];
        for (var i = 0 ; i < list.length; i++) {
            hex += imcontrol.chars[list[i] - (1)].charCodeAt(0).toString(16).toUpperCase();
        }
        return hex;
    },
    encrypt: function (text) {
        return CryptoJS.AES.encrypt(text.toString() + "", CryptoJS.enc.Hex.parse(imcontrol.Crypto.get_()), { iv: CryptoJS.enc.Hex.parse(imcontrol.Crypto._get()) }).toString();
    },

    decrypt: function (text) {

    return CryptoJS.AES.decrypt(text, CryptoJS.enc.Hex.parse(imcontrol.Crypto.get_()), { iv: CryptoJS.enc.Hex.parse(imcontrol.Crypto._get()) }).toString(CryptoJS.enc.Utf8) + "";
}
}