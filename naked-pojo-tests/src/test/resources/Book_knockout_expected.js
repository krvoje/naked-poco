    function Book() {
        self = this;

        self.title=ko.observable("");
        self.genre=ko.observable({});
        self.author=ko.observable({});
        self.authors=ko.observableArray([]);

        self.update = function(dto) {
            self.title(dto.title);
            self.genre(dto.genre);
            self.author(dto.author);
            self.authors(dto.authors);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }
