(function () {

  String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
      return typeof args[number] != 'undefined' ? args[number] : match;
    });
  };

  /** turn a form into a js object */
  $.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
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

})();

JBUtil = {};

JBUtil.Includes = {
  load: function (fnCallback) {

    // optional param
    fnCallback = fnCallback || function () {
    };

    var numIncludes = 0;
    var atTheEnd = function () {
      if (numIncludes == 0) {
        fnCallback();
      }
    };

    $('include').each(function (idx, el) {
      ++numIncludes;
      var inclHref = $(el).html().trim();
      if (inclHref.length > 0) {
        console.log('loading <include>' + inclHref + '</include>');
        $.ajax({
          url: inclHref,
          success: function (data, textStatus, jqXHR) {
            $(el).before(data);
            $(el).remove();
          },
          complete: function (jqXHR, textStatus) {
            --numIncludes;
            atTheEnd();
          }
        });
      }
    });

  }
};

JBUtil.Forms = {

  /**
   * @param control may be any type which is accepted by jQuery's $( ... )
   */
  lockControl: function (control) {
    $(control).attr('disabled', 'disabled');
  },

  /**
   * @param container may be any type which is accepted by jQuery's $( ... )
   */
  lockForms: function (container) {
    container = container || $('body');
    $(container).find('input:not(.disabled),button:not(.disabled),select:not(.disabled)').attr('disabled', 'disabled');
  },

  /**
   * @param control may be any type which is accepted by jQuery's $( ... )
   */
  unlockControl: function (control) {
    $(control).removeAttr('disabled');
  },

  /**
   * @param container may be any type which is accepted by jQuery's $( ... )
   */
  unlockForms: function (container) {
    container = container || $('body');
    $(container).find('input:not(.disabled),button:not(.disabled),select:not(.disabled)').removeAttr('disabled');
  }

};

JBUtil.Misc = {

  keys: function (obj) {
    var keys = [];
    for (var i in obj) if (obj.hasOwnProperty(i)) {
      keys.push(i);
    }
    return keys;
  },

  /**
   * Logs a warning if the condition is false (an assertion fails).
   *
   * @param {boolean} condition
   * @param {String} failMessage
   */
  assertSoft: function (condition, failMessage) {
    if (!condition) {
      console.log(failMessage);
    }
  }

};