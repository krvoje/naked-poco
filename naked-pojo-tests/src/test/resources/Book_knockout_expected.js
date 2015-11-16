    function Book() {
        self = this;

        self.title=ko.observable("");
        self.genre=ko.observable("");
        self.author=ko.observable({});
        self.authors=ko.observableArray([]);
        self.pages=ko.observable(0);
        self.pg=ko.observable("");
        self.borrowedCount=ko.observable(0);

        self.update = function(dto) {
            self.title(dto.title);
            self.genre(dto.genre);
            self.author(dto.author);
            self.authors(dto.authors);
            self.pages(dto.pages);
            self.pg(dto.pg);
            self.borrowedCount(dto.borrowedCount);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }
