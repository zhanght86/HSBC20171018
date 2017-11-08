"use strict";
var ctx = "..";
var imctx = "";

var ajaxPost = function (postUrl, postData, callback, timeout, customizedHeaders, uploadProgressCallback) {
    var httpRequest = null;
    if (window.XMLHttpRequest)
        httpRequest = new XMLHttpRequest();
    else if (window.ActiveXObject)
        httpRequest = new ActiveXObject("Microsoft.XMLHttp");
    if (uploadProgressCallback !== undefined && uploadProgressCallback !== null)
        httpRequest.upload.onprogress = function (e) { uploadProgressCallback(e); };
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status == 200) {
                var resp = { "result": undefined, "status": 200, "error": undefined };
                try {
                    var resp = {
                        result: JSON.parse(httpRequest.responseText).result,
                        status: httpRequest.status,
                        error: JSON.parse(httpRequest.responseText).error,

                    };
                } catch (e) {

                } try {
                    if (resp.result === undefined) {
                        var resp = {
                            result: httpRequest.responseText.result,
                            status: httpRequest.statusText,
                            error: JSON.parse(httpRequest.responseText).error
                        };
                    }
                } catch (e) { }
                try {
                    if (JSON.parse(httpRequest.responseText)["corsContext"]) {
                        resp.corsContext = JSON.parse(httpRequest.responseText)["corsContext"];
                    }
                } catch (e) { }
                if (callback)
                    callback(resp);
            } else {
                var resp = {
                    result: httpRequest.responseText,
                    status: httpRequest.status || "error",
                    error: httpRequest.responseText
                };

                if (callback)
                    callback(resp);
            }
        }
    };
    if (imctx != "" && postUrl.indexOf(imctx) >= 0) {
        //console.log(httpRequest);
        httpRequest.withCredentials = true;
        //httpRequest.setRequestHeader("Cookie", window.sessionId);
    }
    httpRequest.open("POST", postUrl, true);
    if (timeout)
        httpRequest.timeout = timeout;
    if (!customizedHeaders) {
        httpRequest.setRequestHeader("Content-Type", "application/json");
        httpRequest.setRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpRequest.setRequestHeader("X-Requested-With", "XMLHttpRequest");

    } else {
        for (var i in customizedHeaders) {
            httpRequest.setRequestHeader(i, customizedHeaders[i]);
        }
    }

    if (postData !== undefined && postData !== null)
        httpRequest.send(postData);
    else
        httpRequest.send();

}

function BlobPostCallbackHandler(blob, totalCount, index, uploadIndex, webControl) {
    this.blob = blob;
    this.index = index;
    this.customCallback = null;
    this.uploadIndex = uploadIndex;
    this.totalCount = totalCount;
    this.webControl = webControl;
    this.attemptCount = 1;
    this.handleCallback = function (resp) {
        if (resp.error) {
            if (this.attemptCount >= 5) {
                window.uploadProgresses[this.uploadIndex] = null;
                window.uploadProgresses[this.uploadIndex] = -1;
                this.customCallback(false, this.blob);
            } else {
                this.customCallback(false, this.blob);
            }
        } else {
            this.customCallback(true);
            this.blob = null;
            this.customCallback = null;
            this.handleCallback = null;
        }
    }
}

function ClientHeartbeat() {
    this.heartbeatInterval = 60000;
    this.setHeatbeat = function () {
        var interval = this.heartbeatInterval;
        setInterval(function () {
            var postUrl = imctx + "/message/im/" + "heartBeat";
            var postData = null;
            var callback = null;
            ajaxPost(postUrl, postData, callback);
        }, interval);

    }
}

function WebControl(){
    this.guid = function (noDash) {
        var s4 = function () {
            return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
        };
        if (!noDash)
            return s4() + s4() + "-" + s4() + "-" + s4() + "-" + s4() + "-" + s4() + s4() + s4();
        else
            return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4();
    };
    this.sendMessage_Callback = function (resp) {
        if (!resp.error && resp.status != "error")
            imcontrol.sendMessage_Callback(true, resp.result);
        else
            imcontrol.sendMessage_Callback(false);
    };
    this.sendMessage = function (message) {
        var postUrl = imctx + "/message/im/" + "sendMessage";
        message.content = imcontrol.Crypto.encrypt(message.content);
        var postData = JSON.stringify(message);
        var callback = this.sendMessage_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.markMessagesAsRead_Callback = function (resp) {
        if (!resp.error && resp.status != "error") {
            imcontrol.markMessagesAsRead_Callback(true);
        } else {
            imcontrol.markMessagesAsRead_Callback(false);
        }
    };
    this.markMessagesAsRead = function (messages) {
        var postUrl = imctx + "/message/im/" + "hasRead";
        for (var i = 0 ; i < messages.length; i++) {
            messages[i].content = imcontrol.Crypto.encrypt(messages[i].content);
        }
        var postData = JSON.stringify(messages);
        var callback = this.markMessagesAsRead_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.requestMessages_Callback = function (resp) {
        if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
            imcontrol.requestMessages_Callback(null);
        } else {
            for (var i = 0 ; i < resp.result.length; i++)
                resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
            imcontrol.requestMessages_Callback(resp.result);
        }
    };
    this.requestMessages = function (userList) {
        var postUrl = imctx + "/message/im/" + "getMessages/";
        var postData = JSON.stringify(userList);
        var callback = this.requestMessages_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.requestUnreadMessages_Callback = function (resp) {
        if (!resp.error && resp.status != "error" && resp.result !== undefined && resp.result !== null && resp.result.length !== undefined && resp.result.length !== null) {
            for (var i = 0 ; i < resp.result.length; i++)
                resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
            imcontrol.requestUnreadMessages_Callback(resp.result);
        } else {
            console.log(resp.error);
            imcontrol.requestUnreadMessages_Callback(null);
        }
    };
    this.requestUnreadMessages = function (userList) {
        var postUrl = imctx + "/message/im/" + "getMessageUnRead/";
        var postData = JSON.stringify(userList);
        var callback = this.requestUnreadMessages_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.longPollingRequestUnreadMessages_Callback = function (resp) {
        if (resp.error || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
            imcontrol.longPollingRequestUnreadMessages_Callback(null);
        } else {
            for (var i = 0 ; i < resp.result.length; i++)
                resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
            imcontrol.longPollingRequestUnreadMessages_Callback(resp.result);
        }
    };
    this.longPollingRequestUnreadMessages = function (hostUser) {
        var postUrl = imctx + "/message/im/" + "longPollingGetMessageUnRead/";
        var postData = JSON.stringify(hostUser);
        var callback = this.longPollingRequestUnreadMessages_Callback;
        ajaxPost(postUrl, postData, callback, 60000);
    };
    this.longPollingRequestMessages = function (hostUser, guestUser, callbackOnReceiveMessages, startIdExclusive, fromDate) {
        var postUrl = imctx + "/message/im/" + "longPollingGetMessage/";
        var data = null;
        if (fromDate !== null && fromDate !== undefined) {
            data = { "hostUser": hostUser, "guestUser": guestUser,  "startIdExclusive": startIdExclusive, "fromDate": fromDate.getTime() };
        } else {
            data = { "hostUser": hostUser, "guestUser": guestUser, "startIdExclusive": startIdExclusive };
        }
        var postData = JSON.stringify(data);
        var callback = function (resp) {
            var cb = callbackOnReceiveMessages;
            if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
                cb(null);
            } else {
                for (var i = 0 ; i < resp.result.length; i++)
                    resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
                cb(resp.result);
            }
        };
        ajaxPost(postUrl, postData, callback, 60000);
    };
    this.requestMessagesByUsersAndDates = function (hostUser, guestUser, fromDate, toDate, callbackOnReceiveMessages) {
        var postUrl = imctx + "/message/im/" + "getMessagesByUsersAndDates/";
        var data = { "hostUser": hostUser, "guestUser": guestUser, "fromDate": null, "toDate": null };
        if (fromDate !== null && fromDate !== undefined) {
            if (fromDate.getTime)
                data.fromDate = fromDate.getTime();
            else
                data.fromDate = fromDate;
        }
        if (toDate !== null && toDate !== undefined) {
            if (toDate.getTime)
                data.toDate = toDate.getTime();
            else
                data.toDate = toDate;
        }
        var postData = JSON.stringify(data);
        var callback = function (resp) {
            var cb = callbackOnReceiveMessages;
            if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
                cb(null);
            } else {
                for (var i = 0 ; i < resp.result.length; i++)
                    resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
                cb(resp.result);
            }
        }
        ajaxPost(postUrl, postData, callback);
    }
    this.requestFirstMessage = function (hostUser, guestUser, callbackOnReceiveMessages) {
        var postUrl = imctx + "/message/im/" + "getFirstMessage/";
        var data = { "hostUser": hostUser, "guestUser": guestUser };
        var postData = JSON.stringify(data);
        var callback = function (resp) {
            var cb = callbackOnReceiveMessages;
            if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
                cb(null);
            } else {
                for (var i = 0 ; i < resp.result.length; i++)
                    resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
                cb(resp.result);
            }
        }
        ajaxPost(postUrl, postData, callback);
    }
    this.requestLastMessages = function (hostUser, guestUser, maxCount, callbackOnReceiveMessages, toDate) {
        var postUrl = imctx + "/message/im/" + "getLastMessages/";
        var data = { "hostUser": hostUser, "guestUser": guestUser, "maxCount": maxCount };
        if (toDate !== undefined && toDate !== null) {
            if (toDate.getTime) {
                data["toDate"] = toDate.getTime();
            } else {
                data["toDate"] = toDate;
            }
        }
        var postData = JSON.stringify(data);
        var callback = function (resp) {
            var cb = callbackOnReceiveMessages;
            if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
                cb(null);
            } else {
                for (var i = 0 ; i < resp.result.length; i++)
                    resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
                cb(resp.result);
            }
        }
        ajaxPost(postUrl, postData, callback);
    }
    this.requestAllUnreadMessages_Callback = function (resp) {
        if (resp.error || resp.status == "error" || resp.result === undefined || resp.result === null || resp.result.length === undefined || resp.result.length === null) {
            imcontrol.requestAllUnreadMessages_Callback(null);
        } else {
            for (var i = 0 ; i < resp.result.length; i++)
                resp.result[i].content = imcontrol.Crypto.decrypt(resp.result[i].content);
            imcontrol.requestAllUnreadMessages_Callback(resp.result);
        }
    };
    this.requestAllUnreadMessages = function (hostUser) {
        var postUrl = imctx + "/message/im/" + "getAllMessageUnRead/";
        var postData = JSON.stringify(hostUser);
        var callback = this.requestAllUnreadMessages_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.loadCurrentUser_Callback = function (resp) {
        if (resp.error || resp.status == "error") {
            if (resp.error != "The session is no longer active." && resp.error != "This session is no longer authenticated.") {
                setTimeout(function () {
                    imcontrol.loadCurrentUser_CallBack(null);
                }, 2000);
            }
            throw resp.error;
        } else {
            imcontrol.loadCurrentUser_CallBack(resp.result);
        }
    };
    this.loadCurrentUser = function () {
        var postUrl = imctx + "/message/im/" + "currentUser";
        ajaxPost(postUrl, null, this.loadCurrentUser_Callback);
    };
    this.loadFriendList_CallBack = function (resp) {
        if (!resp.error && resp.status != "error") {
            imcontrol.loadFriends_Callback(resp.result);

        } else {
            imcontrol.loadFriends_Callback(null);
            throw resp.error;
        }
    };
    this.loadFriendList = function (hostUser) {
        var postUrl = imctx + "/message/im/" + "getFriends/" + hostUser.id + "/";
        ajaxPost(postUrl, null, this.loadFriendList_CallBack);
    };
    this.loadUsersStatus_Callback = function (resp) {
        if (resp.error || resp.status == "error") {
            imcontrol.loadUsersStatus_Callback(null);
        } else {
            imcontrol.loadUsersStatus_Callback(resp.result);
        }
    };
    this.loadUsersStatus = function (userList) {
        var postUrl = imctx + "/message/userstatus/" + "getUsersStatus/";
        var postData = JSON.stringify(userList);
        var callback = this.loadUsersStatus_Callback;
        ajaxPost(postUrl, postData, callback);
    };
    this.searchPeople = function (keywords, callback) {
        var postUrl = imctx + "/message/im/" + "searchFriends/";
        var postData = JSON.stringify(keywords);
        ajaxPost(postUrl, postData, function (resp) {
            if (resp.error) {
                callback([]);
            }
            else {
                callback(resp.result);
            }
        });
    };
    this.uploadComplete = function (uploadIndex, callback, onError) {
        if (onError) {
            callback(false);
            return;
        }
        if (window.uploadProgresses[uploadIndex].length !== undefined && window.uploadProgresses[uploadIndex].length !== null) {
            window.uploadProgresses[uploadIndex] = null;
            window.uploadProgresses[uploadIndex] = 1;
            var postUrl = imctx + "/api/upload/complete?filename=" + window.fileInfoMap[uploadIndex].filename +
            "&tempName=" + window.fileInfoMap[uploadIndex].tempName + "&uploadIndex=" + uploadIndex +
            "&fromId=" + window.fileInfoMap[uploadIndex].fromUser.id + "&toId=" + window.fileInfoMap[uploadIndex].toUser.id;

            ajaxPost(postUrl, null,
           function (resp) {
               if ((resp.error !== undefined && resp.error !== null) || resp.result ===undefined || resp.result === null)
                   callback(false);
               else {
                   resp.result.content = imcontrol.Crypto.decrypt(resp.result.content);
                   callback(true, resp.result);
               }
           }, null, { "Accept": "application/json, text/javascript, */*; q=0.01" });
        }
    };
    this.uploadBlob = function (blob, blobPostCallbackHandler, callback, progressCallback) {
        if (blobPostCallbackHandler === undefined || blobPostCallbackHandler === null || blobPostCallbackHandler.blob === null || blobPostCallbackHandler.blob === undefined)
            return;
        var formData = new FormData();
        formData.append("Slice" + blobPostCallbackHandler.index, blob);
        var uploadIndex = blobPostCallbackHandler.uploadIndex;
        var index = blobPostCallbackHandler.index;
        var postUrl = imctx + "/api/upload?filename=" + window.fileInfoMap[uploadIndex].filename +
        "&tempName=" + window.fileInfoMap[uploadIndex].tempName + "&uploadIndex=" + blobPostCallbackHandler.uploadIndex +
        "&index=" + blobPostCallbackHandler.index + "&totalCount=" + blobPostCallbackHandler.totalCount;
        ajaxPost(postUrl, formData,
           function (resp) {
               callback(resp, blobPostCallbackHandler);
               progressCallback(window.uploadProgresses[blobPostCallbackHandler.uploadIndex], blobPostCallbackHandler.totalCount);
           }, null, {
               "Accept": "application/json, text/javascript, */*; q=0.01"
           }, function (e) {
               if (e.lengthComputable) {
                   var blobPercent = Math.floor(e.loaded * 100 / e.total);
                   window.uploadProgresses[blobPostCallbackHandler.uploadIndex][blobPostCallbackHandler.index] = blobPercent;
                   progressCallback(window.uploadProgresses[blobPostCallbackHandler.uploadIndex], blobPostCallbackHandler.totalCount);
               } else {
                   var blobPercent = Math.floor(e.loaded * 100 / (blobPostCallbackHandler.blob.size + 100));
                   blobPercent = blobPercent <= 100 ? blobPercent : 100;
                   window.uploadProgresses[blobPostCallbackHandler.uploadIndex][blobPostCallbackHandler.index] = blobPercent;
                   progressCallback(window.uploadProgresses[blobPostCallbackHandler.uploadIndex], blobPostCallbackHandler.totalCount);
               }
           });
    };
    this.uploadFile = function (file, message, callback, progressCallback) {
        var blob = file;
        if (window.uploadProgresses === undefined)
            window.uploadProgresses = [];
        var uploadIndex = window.uploadProgresses.length;
        if (window.fileInfoMap === undefined)
            window.fileInfoMap = {};
        blob.slice = blob.slice || blob.webkitSlice || blob.WebKitSlice || blob.mozSlice || blob.msSlice;
        var BYTES_PER_CHUNCK = 52428792;//4095992; //3932152;
        var SIZE = blob.size;

        var start = 0;
        var end = BYTES_PER_CHUNCK;
        var completed = [];
        window.uploadProgresses.push(completed);
        var count = SIZE % BYTES_PER_CHUNCK == 0 ? SIZE / BYTES_PER_CHUNCK : Math.ceil(SIZE / BYTES_PER_CHUNCK);
        var index = 0;
        var fileInfo = { "filename": "", "tempName": this.guid(true), "fromUser": message.fromUser, "toUser": message.toUser };
        message.guid = fileInfo.tempName;
        fileInfo.filename = encodeURIComponent(file.name);
        window.fileInfoMap[uploadIndex] = fileInfo;
        var progCallback = function (progresses, tCount) {
            var scale = 0.9982 / tCount;
            var result = 0;
            for (var i = 0; i < tCount; i++) {
                if (progresses[i] == "completed")
                    result += scale * 100;
                else if (!isNaN(progresses[i]))
                    result += scale * progresses[i];
                else
                    result += 0;
            }
            if (progresses === 1) {
                result = scale * 100 * tCount;
            }
            progressCallback(result);
        }
        while (start < SIZE) {
            var chunk = blob.slice(start, end);

            var callbackHandler = new BlobPostCallbackHandler(chunk, count, index, uploadIndex, this);
            var userCallback = callback;

            var uploadCallback = function (resp, bcHandler) {
                bcHandler.handleCallback(resp);
                if (window.uploadProgresses[bcHandler.uploadIndex].length !== null && window.uploadProgresses[bcHandler.uploadIndex].length !== undefined) {
                    var completedUpload = true;
                    for (var i = 0 ; i < bcHandler.totalCount; i++) {
                        if (window.uploadProgresses[bcHandler.uploadIndex][i] != "completed")
                            completedUpload = false;
                    }
                    if (completedUpload)
                        bcHandler.webControl.uploadComplete(bcHandler.uploadIndex, userCallback);
                } else {
                    if(window.uploadProgresses[bcHandler.uploadIndex] == -1)
                        bcHandler.webControl.uploadComplete(bcHandler.uploadIndex, userCallback, true);
                }
            };
            callbackHandler.customCallback = function (isSucceeded, blobChunck) {
                var partIndex = this.index;
                var uIndex = this.uploadIndex;
                var totalCount = this.totalCount;
                if (isSucceeded && window.uploadProgresses[uIndex] != 1 && window.uploadProgresses[uIndex] != -1)
                    window.uploadProgresses[uIndex][partIndex] = "completed";
                else if (window.uploadProgresses[uIndex] != 1 && window.uploadProgresses[uIndex] != -1) {
                    window.uploadProgresses[uIndex][partIndex] = 0;
                    var handler = this;
                    handler.webControl.uploadBlob(handler.blob, handler, uploadCallback, progCallback);
                }
            };
            var handler = callbackHandler;
            window.uploadProgresses[uploadIndex][index] = 0;
            this.uploadBlob(chunk, handler, uploadCallback, progCallback);
            start = end;
            end = start + BYTES_PER_CHUNCK;
            ++index;
        }
    };
}

var getThingsReady = function (imDivRequired) {
    var imDiv = document.getElementById("footerDiv");
    if (imDiv) {
        var wc = new WebControl();
        window.imcontrol.pushMode = PushMode.LongPolling;
        window.imcontrol.setup(wc);
        var ch = new ClientHeartbeat();
        ch.setHeatbeat();
    } else {
        if (!imDivRequired)
        {
            var wc = new WebControl();
            window.imcontrol.pushMode = PushMode.LongPolling;
            window.imcontrol.setup(wc);
            var ch = new ClientHeartbeat();
            ch.setHeatbeat();
        }
    }
}



var signInImServer = function (requestData, attemptCount) {
    ajaxPost(imctx + "/login/imLogin", requestData, function (resp) {
        if ((resp.error === undefined || resp.error === null) && (resp.errors === undefined || resp.errors === null)) {           
            getThingsReady(false);
        } else if (attemptCount < 2) {
            setTimeout(function () { signInImServer(requestData, attemptCount + 1); }, 1000);
            return;
        } else {
            throw new Error("Failed to login for Im Service");
        }


    });
}
var acquireImToken_callback = function (resp, attemptCount) {
    if ((resp.error === undefined || resp.error === null) && (resp.errors === undefined || resp.errors === null) && (resp.result != null && resp.result != undefined)) {
        if (resp.corsContext) {
            imctx = resp.corsContext;
            if (imctx !== "") {
                if (imctx.indexOf(":") < 0) {
                    try {
                        imctx = decodeURIComponent(imctx);
                    } catch (exc) { }
                }
            }
        }
        signInImServer(JSON.stringify(resp.result), 0);
    } else {
        if (resp.error !== undefined && resp.error !== null && resp.error.indexOf && resp.error.indexOf("invalid userCode") >= 0) {
            var e = document.createEvent("Event");
            e.initEvent("invalidUserCodeError", false, false);
            e.message = "登录即时通讯服务失败。因为用户名在此服务中不存在。";
            window.dispatchEvent(e);
            if (window.onInvalidUserCodeError) {
                window.onInvalidUserCodeError(e);
            }
            return;
        }
        if (attemptCount <= 5) {
            setTimeout(function () { acquireImToken(attemptCount); }, 1000);
            return;
        } else {
            
            if (resp.error !== undefined && resp.error !== null)
                throw new Error(resp.error);
            else
                throw new Error("Unkown Error: Failed to get the token from IM server.");
        }
    }
}

var acquireImToken = function (attemptCount) {
    var postUrl = ctx + "/api/imLogin/acquireImToken";
    ajaxPost(postUrl, null, function (resp) { acquireImToken_callback(resp, attemptCount + 1); });
}
document.addEventListener("readystatechange", function (e) {
    if (document.readyState.toLocaleLowerCase() == "complete") {
        acquireImToken(0);
    }
});