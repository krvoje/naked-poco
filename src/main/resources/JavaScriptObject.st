JavaScriptObject(jsObj)::="function <jsObj.name>() {
    self = this;

    <jsObj.members: {member|
    self.<member>=undefined;
    }>
}"