    function Book() {
        self = this;

        self.title="";
        self.genre="";
        self.author={};
        self.authors=[];
        self.pages=0;
        self.pg="";
        self.borrowedCount=0;

        self.update = function(dto) {
            self.title=dto.title;
            self.genre=dto.genre;
            self.author=dto.author;
            self.authors=dto.authors;
            self.pages=dto.pages;
            self.pg=dto.pg;
            self.borrowedCount=dto.borrowedCount;
        }

        self.dto = function() {
            return JSON.stringify({
                self.title,
                self.genre,
                self.author,
                self.authors,
                self.pages,
                self.pg,
                self.borrowedCount
            });
        }
    }
