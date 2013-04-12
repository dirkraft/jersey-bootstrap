/**
 * html includes via <include>include/file.html</include>
 *
 * Invoke after all include tags. Accepts a callback function for things dependent on include initialization.
 *
 * @param {Function} fnCallback (optional) called after all include requests have completed (whether or not they failed)
 */
function loadIncludes(fnCallback) {

    fnCallback = fnCallback || function(){}; // optional param

    var numIncludes = 0;
    var atTheEnd = function() {
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
                success: function(data, textStatus, jqXHR) {
                    $(el).before(data);
                    $(el).remove();
                },
                complete: function(jqXHR, textStatus) {
                    --numIncludes;
                    atTheEnd();
                }
            });
        }
    });

}