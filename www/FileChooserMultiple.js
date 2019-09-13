var exec = require('cordova/exec');

module.exports.open = function (success, error) {
    exec(success, error, 'FileChooserMultiple', 'open', []);   
};
