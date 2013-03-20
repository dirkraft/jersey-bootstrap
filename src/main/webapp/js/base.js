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
function lockForms(containerEl) {
    containerEl = containerEl || $('body');
    $(containerEl).find('input:not(.disabled),button:not(.disabled)').attr('disabled', 'disabled');
}

function unlockForms(containerEl) {
    containerEl = containerEl || $('body');
    $(containerEl).find('input:not(.disabled),button:not(.disabled)').removeAttr('disabled');
}

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

function defaultAjaxError(jqXHR, textStatus, errorThrown) {
    var exception = JSON.parse(jqXHR.responseText);
    var exceptMsg = exception.message && exception.message.trim();
    var message = jqXHR.status + ':' + (exceptMsg ? exceptMsg : 'see console log');
    console.log(jqXHR.responseText);
    alert(message);
}