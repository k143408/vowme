export interface Page<T> {
    content: T[];
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort?: any;
    first: boolean;
    numberOfElements: number;
}