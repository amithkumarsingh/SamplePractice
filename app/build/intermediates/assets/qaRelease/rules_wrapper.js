
// according to duktape, evaluate should return a appropriate Java value,
// the assignment instruction won't return an appropriate value here,
// we are using self-calling method to execute the assignment instruction here.
(function(){
    // setting current platform
    $BK$.setCurrentPlatform("Android");

    $BK$.log = function(msg){
        if(engineLogger){
            engineLogger.log(String(msg));
        }
    };

})();

//////////////joyrney methods

function processEventForJourney(bkevent){
    $BK$.log("processing event... in JSEngine");
    if( typeof bkevent !== "string"){
        bkevent = JSON.stringify(bkevent);
    }

    $BK$.Journey.processEvent(bkevent,function(ruleId, ruleName, notification, keywords, timer){
        if(engineListener){
            engineListener.onActionReceived(String(ruleId),String(ruleName), JSON.stringify(notification), String(keywords), String(timer));
        }
    });
    $BK$.log("processing event... in JSEngine --");
}


function processInboxForJourney(inbox){
    $BK$.log("processInboxForJourney...");

    if(typeof $BK$.Journey.processInbox === 'undefined'){ //no method found :-)
        // return the input as it is.
        $BK$.log("processInboxForJourney... -- 'processInbox' not found in $BK$'");
        return inbox;
    }

    var processedInbox =  $BK$.Journey.processInbox(inbox);
    $BK$.log("processInboxForJourney... --");
    return processedInbox;
}

function sendUserProfile(userProfileTkv){
    $BK$.log("sendUserProfile...");

    if(typeof $BK$.Journey.sendUserProfile === 'undefined'){ //no method found :-)
        // return the input as it is.
        $BK$.log("sendUserProfile... -- 'sendUserProfile' not found in $BK$'");
        return inbox;
    }

    var processedUserProfile =  $BK$.Journey.sendUserProfile(userProfileTkv);
    $BK$.log("sendUserProfile... --");
    return processedUserProfile;
}

function getActivity(type, tag){
    $BK$.log("getActivity...");

    if(typeof $BK$.Journey.getActivity === 'undefined'){ //no method found :-)
        // return the input as it is.
        $BK$.log("getActivity... -- 'getActivity' not found in $BK$'");
        return tag;
    }

    var processedActivity =  $BK$.Journey.getActivity(type, tag);
    $BK$.log("getActivity... --");
    return JSON.stringify(processedActivity);
}


function sendResponse(response){
    $BK$.log("sendResponse...");

    if(typeof $BK$.Journey.sendUserProfile === 'undefined'){ //no method found :-)
        // return the input as it is.
        $BK$.log("sendResponse... -- 'sendResponse' not found in $BK$'");
        return inbox;
    }

    var processedResponse =  $BK$.Journey.sendResponse(response);
    $BK$.log("sendResponse... --");
    return processedResponse;
}
////////////////////closed journey methods

function processInbox(notificationsJsonArrayString, payloadJsonObjString){
    $BK$.log("processInbox...");

    if(typeof $BK$.processInbox === 'undefined'){ //no method found :-)
        // return the input as it is.
        $BK$.log("processInbox... -- 'processInbox' not found in $BK$'");
        return notificationsJsonArrayString;
    }

    var processedInbox =  $BK$.processInbox(notificationsJsonArrayString, payloadJsonObjString);
    $BK$.log("processInbox... --");
    return JSON.stringify(processedInbox);
}

function prepareAggregateMap(aggrValues, aggrDefs, localMap){
    $BK$.log("prepareAggregateMap...");
    $BK$.prepareAggregateMap(aggrValues, aggrDefs, localMap, function(aggrs){
        if(engineListener){
            engineListener.onAggregatesUpdate(JSON.stringify(aggrs));
        }
     });
     $BK$.log("prepareAggregateMap... --");
}

function processEvent(bkevent){
    if( typeof bkevent !== "string"){
    $BK$.log("processing event... in JSEngine bkevent in if: " + bkevent);
        bkevent = JSON.stringify(bkevent);
    }

    $BK$.processEvent(bkevent,function(ruleId, ruleName, notification, keywords, timer, pageViews){
        if(engineListener){
        if (typeof notification !== 'undefined') {
            notification = JSON.stringify(notification);
        } else {
        notification = "undefined"
        }
            engineListener.onActionReceived(String(ruleId),String(ruleName), notification, String(keywords), String(timer), String(pageViews));


        }
    });
    $BK$.log("processing event... in JSEngine --");
}

