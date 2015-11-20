    function SciFiAuthor() {
        self = this;

        self.book=ko.observable({});
        self.books=ko.observableArray([]);
        self.conventionsVisited=ko.observableArray([]);
        self.idNumber=ko.observable("");
        self.name=ko.observable("");
        self.prolific=ko.observable(false);
        self.surname=ko.observable("");

        self.update = function(dto) {
            self.book(dto.book);
            self.books(dto.books);
            self.conventionsVisited(dto.conventionsVisited);
            self.idNumber(dto.idNumber);
            self.name(dto.name);
            self.prolific(dto.prolific);
            self.surname(dto.surname);
        }

        self.dto = function() {
            return ko.toJS(self);
        }
    }
