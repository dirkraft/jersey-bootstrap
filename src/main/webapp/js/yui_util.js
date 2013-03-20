YUIUtil = {
    /**
     * @param {Element} el any element presumably contained within a YUI Datatable row.
     */
    findModel: function(el) {
        return $(el).closest('[data-yui3-record]').attr('data-yui3-record');
    },

    /**
     * Creates a Y.ModelList basic implementation of sync that passes along data to the callback or an error had one
     * occurred.
     *
     * @param url to sync against
     */
    makeReadOnlySync: function(url) {
        return function(action, options, callback) {
            if (action === 'read') {
                $.ajax({
                    url: url,
                    dataType: 'json',
                    success: function(data, textStatus, jqXHR) {
                        callback(null, data);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        callback(errorThrown);
                    }
                });
            } else {
                callback('Unsupported sync action: ' + action);
            }
        }
    }
};