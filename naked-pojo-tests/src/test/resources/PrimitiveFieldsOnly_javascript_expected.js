    function PrimitiveFieldsOnly() {
        self = this;

        self.booleanNullable=false;
        self.booleanPrimitive=false;
        self.doubleNullable=0;
        self.doublePrimitive=0;
        self.floatNullable=0;
        self.floatPrimitive=0;
        self.integerNullable=0;
        self.integerPrimitive=0;
        self.longNullable=0;
        self.longPrimitive=0;
        self.shortNullable=0;
        self.shortPrimitive=0;

        self.update = function(dto) {
            self.booleanNullable=dto.booleanNullable;
            self.booleanPrimitive=dto.booleanPrimitive;
            self.doubleNullable=dto.doubleNullable;
            self.doublePrimitive=dto.doublePrimitive;
            self.floatNullable=dto.floatNullable;
            self.floatPrimitive=dto.floatPrimitive;
            self.integerNullable=dto.integerNullable;
            self.integerPrimitive=dto.integerPrimitive;
            self.longNullable=dto.longNullable;
            self.longPrimitive=dto.longPrimitive;
            self.shortNullable=dto.shortNullable;
            self.shortPrimitive=dto.shortPrimitive;
        }

        self.dto = function() {
            return JSON.stringify({
                self.doubleNullable,
                self.doublePrimitive,
                self.floatNullable,
                self.floatPrimitive,
                self.integerNullable,
                self.integerPrimitive,
                self.longNullable,
                self.longPrimitive,
                self.shortNullable,
                self.shortPrimitive
            });
        }
    }
