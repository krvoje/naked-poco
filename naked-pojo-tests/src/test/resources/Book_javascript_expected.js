    function Book() {
        self = this;

        self.author={};
        self.authors=[];
        self.borrowedCount=0;
        self.genre="";
        self.pages=0;
        self.pg="";
        self.title="";

        self.update = function(dto) {
            self.author=dto.author;
            self.authors=dto.authors;
            self.borrowedCount=dto.borrowedCount;
            self.genre=dto.genre;
            self.pages=dto.pages;
            self.pg=dto.pg;
            self.title=dto.title;
        }

        self.dto = function() {
            return JSON.stringify({
                self.author,
                self.authors,
                self.borrowedCount,
                self.genre,
                self.pages,
                self.pg,
                self.title
            });
        }
    }
