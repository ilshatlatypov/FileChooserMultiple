var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'FileChooserMultiple', 'coolMethod', [arg0]);
};

module.exports.open = function (arg0, success, error) {
    exec(success, error, 'FileChooserMultiple', 'open', [arg0]);   
};
