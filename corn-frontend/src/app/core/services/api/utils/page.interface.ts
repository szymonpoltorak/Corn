export interface Page<T> {
    content: T[];
    empty: boolean;
    first: boolean;
    last: false;
    number: number;
    numberOfElements: number;
    size: number;
    totalElements: number;
    totalPages: number;
}
