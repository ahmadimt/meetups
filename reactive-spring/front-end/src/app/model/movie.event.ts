export class MovieEvent {
    movieName: string;
    movieGenre: string;
    userName: string;
    when: string;

    constructor(movieName, movieGenre, userName, when){
        this.movieName = movieName || '';
        this.movieGenre = movieGenre || '';
        this.userName = userName || '';
        this.when = when || '';
        
    }
}