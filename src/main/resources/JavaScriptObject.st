JavaScriptObject(jsObj)::= <<
function <jsObj.typeName>() {
    self = this;

<jsObj.members: {member|
    self.<member.fieldName>=ko.observable(<member.type.defaultValue>);
}>
    self.update = function(dto) {
<jsObj.members: {member|
        self.<member.fieldName>(dto.<member.fieldName>);
    }>    }

    self.dto = function() {
        return ko.toJS(self);
    }
}
>>