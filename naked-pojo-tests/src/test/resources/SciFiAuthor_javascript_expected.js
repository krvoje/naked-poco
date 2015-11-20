    function SciFiAuthor() {
        self = this;

        self.book={};
        self.books=[];
        self.conventionsVisited=[];
        self.name="";
        self.idNumber="";
        self.prolific=false;
        self.surname="";

        self.update = function(dto) {
            self.book=dto.book;
            self.books=dto.books;
            self.conventionsVisited=dto.conventionsVisited;
            self.idNumber=dto.idNumber;
            self.name=dto.name;
            self.prolific=dto.prolific;
            self.surname=dto.surname;
        }

        self.dto = function() {
            return JSON.stringify({
                self.book,
                self.books,
                self.conventionsVisited,
                self.idNumber,
                self.name,
                self.prolific,
                self.surname
            });
        }
    }
