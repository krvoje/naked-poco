    function PrimitiveFieldsOnly() {
        self = this;

        self.booleanNullable=ko.observable(false);
        self.booleanPrimitive=ko.observable(false);
        self.doubleNullable=ko.observable(0);
        self.doublePrimitive=ko.observable(0);
        self.floatNullable=ko.observable(0);
        self.floatPrimitive=ko.observable(0);
        self.integerNullable=ko.observable(0);
        self.integerPrimitive=ko.observable(0);
        self.longNullable=ko.observable(0);
        self.longPrimitive=ko.observable(0);
        self.shortNullable=ko.observable(0);
        self.shortPrimitive=ko.observable(0);

        self.update = function(dto) {
            self.booleanNullable(dto.booleanNullable);
            self.booleanPrimitive(dto.booleanPrimitive);
            self.doubleNullable(dto.doubleNullable);
            self.doublePrimitive(dto.doublePrimitive);
            self.floatNullable(dto.floatNullable);
            self.floatPrimitive(dto.floatPrimitive);
            self.integerNullable(dto.integerNullable);
            self.integerPrimitive(dto.integerPrimitive);
            self.longNullable(dto.longNullable);
            self.longPrimitive(dto.longPrimitive);
            self.shortNullable(dto.shortNullable);
            self.shortPrimitive(dto.shortPrimitive);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }
