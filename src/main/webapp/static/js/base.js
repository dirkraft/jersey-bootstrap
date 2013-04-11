Object.prototype.keys = function () {
    var keys = [];
    for(var i in this) if (this.hasOwnProperty(i)) {
        keys.push(i);
    }
    return keys;
};

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};

/**
* Logs a warning if the condition is false (an assertion fails).
*
* @param {boolean} condition
* @param {String} failMessage
*/
function assertSoft(condition, failMessage) {
    if (!condition) {
        console.log(failMessage);
    }
}


/**
* @param control may be any type which is accepted by jQuery's $( ... )
*/
function lockControl(control) {
    $(control).attr('disabled', 'disabled');
}
/**
* @param container may be any type which is accepted by jQuery's $( ... )
*/
function lockForms(container) {
    container = container || $('body');
    $(container).find('input:not(.disabled),button:not(.disabled),select:not(.disabled)').attr('disabled', 'disabled');
}

/**
* @param control may be any type which is accepted by jQuery's $( ... )
*/
function unlockControl(control) {
    $(control).removeAttr('disabled');
}
/**
* @param container may be any type which is accepted by jQuery's $( ... )
*/
function unlockForms(container) {
    container = container || $('body');
    $(container).find('input:not(.disabled),button:not(.disabled),select:not(.disabled)').removeAttr('disabled');
}

/** turn a form into a js object */
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

// default $.ajax error function
$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
    var exception, exceptMsg;
    if (jqXHR.responseText) {
        try {
            exception = JSON.parse(jqXHR.responseText);
        } catch (error) {
            exception = jqXHR.responseText
        }
        exceptMsg = exception.message && exception.message.trim();
    }
    var message = jqXHR.status + ':' + (exceptMsg ? exceptMsg : 'see console log');
    console.log(jqXHR.responseText);
});

/** poor man's html escape */
function escapeHtml(text) {
    return $('<div/>').text(text).html();
}