    function Book() {
        self = this;

        self.author=ko.observable({});
        self.authors=ko.observableArray([]);
        self.borrowedCount=ko.observable(0);
        self.genre=ko.observable("");
        self.pages=ko.observable(0);
        self.pg=ko.observable("");
        self.title=ko.observable("");

        self.update = function(dto) {
            self.author(dto.author);
            self.authors(dto.authors);
            self.borrowedCount(dto.borrowedCount);
            self.genre(dto.genre);
            self.pages(dto.pages);
            self.pg(dto.pg);
            self.title(dto.title);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }
