    function AuthorCustomName() {
        self = this;

        self.book={};
        self.books=[];
        self.idNumber="";
        self.name="";
        self.prolific=false;
        self.surname="";

        self.update = function(dto) {
            self.book=dto.book;
            self.books=dto.books;
            self.idNumber=dto.idNumber;
            self.name=dto.name;
            self.prolific=dto.prolific;
            self.surname=dto.surname;
        }

        self.dto = function() {
            return JSON.stringify({
                self.book,
                self.books,
                self.idNumber,
                self.name,
                self.prolific,
                self.surname
            });
        }
    }
