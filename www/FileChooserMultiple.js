var exec = require('cordova/exec');

module.exports.open = function (arg0, success, error) {
    exec(success, error, 'FileChooserMultiple', 'open', [arg0]);   
};
