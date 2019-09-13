var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'FileChooserMultiple', 'coolMethod', [arg0]);
};

module.exports.open = function (filter, success, failure) {
    if (typeof filter === 'function') {
        failure = success;
        success = filter;
        filter = {};
    }
    exec(success, failure, 'FileChooserMultiple', 'open', [ filter ]);
};
