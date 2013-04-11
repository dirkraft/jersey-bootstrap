/**
 * html includes via <template>include/file.html</template>
 *
 * Invoke after all template tags. Accepts a callback function for things dependent on template initialization.
 *
 * @param {Function} fnCallback (optional) called after all template requests have completed (whether or not they failed)
 */
function loadTemplates(fnCallback) {

    fnCallback = fnCallback || function(){}; // optional param

    var numTemplates = 0;
    var atTheEnd = function() {
        if (numTemplates == 0) {
            fnCallback();
        }
    };

    $('template').each(function (idx, el) {
        ++numTemplates;
        var tplHref = $(el).html().trim();
        if (tplHref.length > 0) {
            console.log('loading <template>' + tplHref + '</template>');
            $.ajax({
                url: tplHref,
                success: function(data, textStatus, jqXHR) {
                    $(el).before(data);
                    $(el).remove();
                },
                complete: function(jqXHR, textStatus) {
                    --numTemplates;
                    atTheEnd();
                }
            });
        }
    });

}