    function Author() {
        self = this;

        self.name="";
        self.surname="";
        self.book={};
        self.books=[];
        self.prolific=false;

        self.update = function(dto) {
            self.name=dto.name;
            self.surname=dto.surname;
            self.book=dto.book;
            self.books=dto.books;
            self.prolific=dto.prolific;
        }

        self.dto = function() {
            return JSON.stringify({
                self.name,
                self.surname,
                self.book,
                self.books,
                self.prolific
            });
        }
    }
