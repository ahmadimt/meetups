export class Stock {
    time: Date;
    price: number;
    constructor(date, no) {
        this.time = new Date(date);
        this.price = no;
    }
}
